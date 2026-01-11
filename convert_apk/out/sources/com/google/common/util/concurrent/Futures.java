package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

public final class Futures {
    private static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function<Constructor<?>, Boolean>() {
        public Boolean apply(Constructor<?> input) {
            return Boolean.valueOf(Arrays.asList(input.getParameterTypes()).contains(String.class));
        }
    }).reverse();

    private Futures() {
    }

    public static <V, X extends Exception> CheckedFuture<V, X> makeChecked(ListenableFuture<V> future, Function<Exception, X> mapper) {
        return new MappingCheckedFuture((ListenableFuture) Preconditions.checkNotNull(future), mapper);
    }

    public static <V> ListenableFuture<V> immediateFuture(@Nullable V value) {
        SettableFuture<V> future = SettableFuture.create();
        future.set(value);
        return future;
    }

    public static <V, X extends Exception> CheckedFuture<V, X> immediateCheckedFuture(@Nullable V value) {
        SettableFuture<V> future = SettableFuture.create();
        future.set(value);
        return makeChecked(future, new Function<Exception, X>() {
            public X apply(Exception e) {
                throw new AssertionError("impossible");
            }
        });
    }

    public static <V> ListenableFuture<V> immediateFailedFuture(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        SettableFuture<V> future = SettableFuture.create();
        future.setException(throwable);
        return future;
    }

    public static <V, X extends Exception> CheckedFuture<V, X> immediateFailedCheckedFuture(final X exception) {
        Preconditions.checkNotNull(exception);
        return makeChecked(immediateFailedFuture(exception), new Function<Exception, X>() {
            public X apply(Exception e) {
                return exception;
            }
        });
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function) {
        return transform(input, function, (Executor) MoreExecutors.sameThreadExecutor());
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function, Executor executor) {
        ChainingListenableFuture<I, O> output = new ChainingListenableFuture<>(function, input);
        input.addListener(output, executor);
        return output;
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, Function<? super I, ? extends O> function) {
        return transform(input, function, (Executor) MoreExecutors.sameThreadExecutor());
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, final Function<? super I, ? extends O> function, Executor executor) {
        Preconditions.checkNotNull(function);
        return transform(input, new AsyncFunction<I, O>() {
            public ListenableFuture<O> apply(I input) {
                return Futures.immediateFuture(function.apply(input));
            }
        }, executor);
    }

    public static <I, O> Future<O> lazyTransform(final Future<I> input, final Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(input);
        Preconditions.checkNotNull(function);
        return new Future<O>() {
            public boolean cancel(boolean mayInterruptIfRunning) {
                return input.cancel(mayInterruptIfRunning);
            }

            public boolean isCancelled() {
                return input.isCancelled();
            }

            public boolean isDone() {
                return input.isDone();
            }

            public O get() throws InterruptedException, ExecutionException {
                return applyTransformation(input.get());
            }

            public O get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return applyTransformation(input.get(timeout, unit));
            }

            private O applyTransformation(I input) throws ExecutionException {
                try {
                    return function.apply(input);
                } catch (Throwable t) {
                    throw new ExecutionException(t);
                }
            }
        };
    }

    private static class ChainingListenableFuture<I, O> extends AbstractFuture<O> implements Runnable {
        private AsyncFunction<? super I, ? extends O> function;
        private ListenableFuture<? extends I> inputFuture;
        private final BlockingQueue<Boolean> mayInterruptIfRunningChannel;
        private final CountDownLatch outputCreated;
        /* access modifiers changed from: private */
        public volatile ListenableFuture<? extends O> outputFuture;

        private ChainingListenableFuture(AsyncFunction<? super I, ? extends O> function2, ListenableFuture<? extends I> inputFuture2) {
            this.mayInterruptIfRunningChannel = new LinkedBlockingQueue(1);
            this.outputCreated = new CountDownLatch(1);
            this.function = (AsyncFunction) Preconditions.checkNotNull(function2);
            this.inputFuture = (ListenableFuture) Preconditions.checkNotNull(inputFuture2);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            if (!super.cancel(mayInterruptIfRunning)) {
                return false;
            }
            Uninterruptibles.putUninterruptibly(this.mayInterruptIfRunningChannel, Boolean.valueOf(mayInterruptIfRunning));
            cancel(this.inputFuture, mayInterruptIfRunning);
            cancel(this.outputFuture, mayInterruptIfRunning);
            return true;
        }

        private void cancel(@Nullable Future<?> future, boolean mayInterruptIfRunning) {
            if (future != null) {
                future.cancel(mayInterruptIfRunning);
            }
        }

        public void run() {
            try {
                try {
                    final ListenableFuture<? extends O> outputFuture2 = this.function.apply(Uninterruptibles.getUninterruptibly(this.inputFuture));
                    this.outputFuture = outputFuture2;
                    if (isCancelled()) {
                        outputFuture2.cancel(((Boolean) Uninterruptibles.takeUninterruptibly(this.mayInterruptIfRunningChannel)).booleanValue());
                        this.outputFuture = null;
                        this.function = null;
                        this.inputFuture = null;
                        this.outputCreated.countDown();
                        return;
                    }
                    outputFuture2.addListener(new Runnable() {
                        public void run() {
                            try {
                                ChainingListenableFuture.this.set(Uninterruptibles.getUninterruptibly(outputFuture2));
                            } catch (CancellationException e) {
                                ChainingListenableFuture.this.cancel(false);
                                ListenableFuture unused = ChainingListenableFuture.this.outputFuture = null;
                                return;
                            } catch (ExecutionException e2) {
                                ChainingListenableFuture.this.setException(e2.getCause());
                            } catch (Throwable th) {
                                ListenableFuture unused2 = ChainingListenableFuture.this.outputFuture = null;
                                throw th;
                            }
                            ListenableFuture unused3 = ChainingListenableFuture.this.outputFuture = null;
                        }
                    }, MoreExecutors.sameThreadExecutor());
                    this.function = null;
                    this.inputFuture = null;
                    this.outputCreated.countDown();
                } catch (UndeclaredThrowableException e) {
                    setException(e.getCause());
                } catch (Exception e2) {
                    setException(e2);
                } catch (Error e3) {
                    setException(e3);
                } catch (Throwable th) {
                    this.function = null;
                    this.inputFuture = null;
                    this.outputCreated.countDown();
                    throw th;
                }
            } catch (CancellationException e4) {
                cancel(false);
                this.function = null;
                this.inputFuture = null;
                this.outputCreated.countDown();
            } catch (ExecutionException e5) {
                setException(e5.getCause());
                this.function = null;
                this.inputFuture = null;
                this.outputCreated.countDown();
            }
        }
    }

    public static <V> ListenableFuture<List<V>> allAsList(ListenableFuture<? extends V>... futures) {
        return new ListFuture(ImmutableList.copyOf((E[]) futures), true, MoreExecutors.sameThreadExecutor());
    }

    public static <V> ListenableFuture<List<V>> allAsList(Iterable<? extends ListenableFuture<? extends V>> futures) {
        return new ListFuture(ImmutableList.copyOf(futures), true, MoreExecutors.sameThreadExecutor());
    }

    public static <V> ListenableFuture<List<V>> successfulAsList(ListenableFuture<? extends V>... futures) {
        return new ListFuture(ImmutableList.copyOf((E[]) futures), false, MoreExecutors.sameThreadExecutor());
    }

    public static <V> ListenableFuture<List<V>> successfulAsList(Iterable<? extends ListenableFuture<? extends V>> futures) {
        return new ListFuture(ImmutableList.copyOf(futures), false, MoreExecutors.sameThreadExecutor());
    }

    public static <V> void addCallback(ListenableFuture<V> future, FutureCallback<? super V> callback) {
        addCallback(future, callback, MoreExecutors.sameThreadExecutor());
    }

    public static <V> void addCallback(final ListenableFuture<V> future, final FutureCallback<? super V> callback, Executor executor) {
        Preconditions.checkNotNull(callback);
        future.addListener(new Runnable() {
            public void run() {
                try {
                    callback.onSuccess(Uninterruptibles.getUninterruptibly(future));
                } catch (ExecutionException e) {
                    callback.onFailure(e.getCause());
                } catch (RuntimeException e2) {
                    callback.onFailure(e2);
                } catch (Error e3) {
                    callback.onFailure(e3);
                }
            }
        }, executor);
    }

    public static <V, X extends Exception> V get(Future<V> future, Class<X> exceptionClass) throws Exception {
        Preconditions.checkNotNull(future);
        Preconditions.checkArgument(!RuntimeException.class.isAssignableFrom(exceptionClass), "Futures.get exception type (%s) must not be a RuntimeException", exceptionClass);
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw newWithCause(exceptionClass, e);
        } catch (ExecutionException e2) {
            wrapAndThrowExceptionOrError(e2.getCause(), exceptionClass);
            throw new AssertionError();
        }
    }

    public static <V, X extends Exception> V get(Future<V> future, long timeout, TimeUnit unit, Class<X> exceptionClass) throws Exception {
        Preconditions.checkNotNull(future);
        Preconditions.checkNotNull(unit);
        Preconditions.checkArgument(!RuntimeException.class.isAssignableFrom(exceptionClass), "Futures.get exception type (%s) must not be a RuntimeException", exceptionClass);
        try {
            return future.get(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw newWithCause(exceptionClass, e);
        } catch (TimeoutException e2) {
            throw newWithCause(exceptionClass, e2);
        } catch (ExecutionException e3) {
            wrapAndThrowExceptionOrError(e3.getCause(), exceptionClass);
            throw new AssertionError();
        }
    }

    private static <X extends Exception> void wrapAndThrowExceptionOrError(Throwable cause, Class<X> exceptionClass) throws Exception {
        if (cause instanceof Error) {
            throw new ExecutionError((Error) cause);
        } else if (cause instanceof RuntimeException) {
            throw new UncheckedExecutionException(cause);
        } else {
            throw newWithCause(exceptionClass, cause);
        }
    }

    public static <V> V getUnchecked(Future<V> future) {
        Preconditions.checkNotNull(future);
        try {
            return Uninterruptibles.getUninterruptibly(future);
        } catch (ExecutionException e) {
            wrapAndThrowUnchecked(e.getCause());
            throw new AssertionError();
        }
    }

    private static void wrapAndThrowUnchecked(Throwable cause) {
        if (cause instanceof Error) {
            throw new ExecutionError((Error) cause);
        }
        throw new UncheckedExecutionException(cause);
    }

    private static <X extends Exception> X newWithCause(Class<X> exceptionClass, Throwable cause) {
        for (Constructor<X> constructor : preferringStrings(Arrays.asList(exceptionClass.getConstructors()))) {
            X instance = (Exception) newFromConstructor(constructor, cause);
            if (instance != null) {
                if (instance.getCause() == null) {
                    instance.initCause(cause);
                }
                return instance;
            }
        }
        throw new IllegalArgumentException("No appropriate constructor for exception of type " + exceptionClass + " in response to chained exception", cause);
    }

    private static <X extends Exception> List<Constructor<X>> preferringStrings(List<Constructor<X>> constructors) {
        return WITH_STRING_PARAM_FIRST.sortedCopy(constructors);
    }

    @Nullable
    private static <X> X newFromConstructor(Constructor<X> constructor, Throwable cause) {
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> paramType = paramTypes[i];
            if (paramType.equals(String.class)) {
                params[i] = cause.toString();
            } else if (!paramType.equals(Throwable.class)) {
                return null;
            } else {
                params[i] = cause;
            }
        }
        try {
            return constructor.newInstance(params);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (InstantiationException e2) {
            return null;
        } catch (IllegalAccessException e3) {
            return null;
        } catch (InvocationTargetException e4) {
            return null;
        }
    }

    private static class ListFuture<V> extends AbstractFuture<List<V>> {
        final boolean allMustSucceed;
        ImmutableList<? extends ListenableFuture<? extends V>> futures;
        final AtomicInteger remaining;
        List<V> values;

        ListFuture(ImmutableList<? extends ListenableFuture<? extends V>> futures2, boolean allMustSucceed2, Executor listenerExecutor) {
            this.futures = futures2;
            this.values = Lists.newArrayListWithCapacity(futures2.size());
            this.allMustSucceed = allMustSucceed2;
            this.remaining = new AtomicInteger(futures2.size());
            init(listenerExecutor);
        }

        private void init(Executor listenerExecutor) {
            addListener(new Runnable() {
                public void run() {
                    ListFuture.this.values = null;
                    ListFuture.this.futures = null;
                }
            }, MoreExecutors.sameThreadExecutor());
            if (this.futures.isEmpty()) {
                set(Lists.newArrayList(this.values));
                return;
            }
            for (int i = 0; i < this.futures.size(); i++) {
                this.values.add((Object) null);
            }
            ImmutableList<? extends ListenableFuture<? extends V>> localFutures = this.futures;
            for (int i2 = 0; i2 < localFutures.size(); i2++) {
                final ListenableFuture<? extends V> listenable = (ListenableFuture) localFutures.get(i2);
                final int index = i2;
                listenable.addListener(new Runnable() {
                    public void run() {
                        ListFuture.this.setOneValue(index, listenable);
                    }
                }, listenerExecutor);
            }
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0031, code lost:
            if (r1 != null) goto L_0x004f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004d, code lost:
            if (r1 != null) goto L_0x004f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x004f, code lost:
            set(com.google.common.collect.Lists.newArrayList(r1));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0057, code lost:
            com.google.common.base.Preconditions.checkState(isDone());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0078, code lost:
            if (r1 != null) goto L_0x004f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x0098, code lost:
            if (r1 != null) goto L_0x004f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x00b4, code lost:
            if (r1 != null) goto L_0x004f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void setOneValue(int r7, java.util.concurrent.Future<? extends V> r8) {
            /*
                r6 = this;
                java.lang.String r0 = "Less than 0 remaining futures"
                java.util.List<V> r1 = r6.values
                boolean r2 = r6.isDone()
                if (r2 != 0) goto L_0x00db
                if (r1 != 0) goto L_0x000e
                goto L_0x00db
            L_0x000e:
                r2 = 1
                r3 = 0
                boolean r4 = r8.isDone()     // Catch:{ CancellationException -> 0x009b, ExecutionException -> 0x007b, RuntimeException -> 0x005f, Error -> 0x0037 }
                java.lang.String r5 = "Tried to set value from future which is not done"
                com.google.common.base.Preconditions.checkState(r4, r5)     // Catch:{ CancellationException -> 0x009b, ExecutionException -> 0x007b, RuntimeException -> 0x005f, Error -> 0x0037 }
                java.lang.Object r4 = com.google.common.util.concurrent.Uninterruptibles.getUninterruptibly(r8)     // Catch:{ CancellationException -> 0x009b, ExecutionException -> 0x007b, RuntimeException -> 0x005f, Error -> 0x0037 }
                r1.set(r7, r4)     // Catch:{ CancellationException -> 0x009b, ExecutionException -> 0x007b, RuntimeException -> 0x005f, Error -> 0x0037 }
                java.util.concurrent.atomic.AtomicInteger r4 = r6.remaining
                int r4 = r4.decrementAndGet()
                if (r4 < 0) goto L_0x0029
                goto L_0x002a
            L_0x0029:
                r2 = r3
            L_0x002a:
                com.google.common.base.Preconditions.checkState(r2, r0)
                if (r4 != 0) goto L_0x005e
                java.util.List<V> r1 = r6.values
                if (r1 == 0) goto L_0x0057
                goto L_0x004f
            L_0x0034:
                r4 = move-exception
                goto L_0x00b8
            L_0x0037:
                r4 = move-exception
                r6.setException(r4)     // Catch:{ all -> 0x0034 }
                java.util.concurrent.atomic.AtomicInteger r4 = r6.remaining
                int r4 = r4.decrementAndGet()
                if (r4 < 0) goto L_0x0045
                goto L_0x0046
            L_0x0045:
                r2 = r3
            L_0x0046:
                com.google.common.base.Preconditions.checkState(r2, r0)
                if (r4 != 0) goto L_0x005e
                java.util.List<V> r1 = r6.values
                if (r1 == 0) goto L_0x0057
            L_0x004f:
                java.util.ArrayList r0 = com.google.common.collect.Lists.newArrayList(r1)
                r6.set(r0)
                goto L_0x005e
            L_0x0057:
                boolean r0 = r6.isDone()
                com.google.common.base.Preconditions.checkState(r0)
            L_0x005e:
                goto L_0x00b7
            L_0x005f:
                r4 = move-exception
                boolean r5 = r6.allMustSucceed     // Catch:{ all -> 0x0034 }
                if (r5 == 0) goto L_0x0067
                r6.setException(r4)     // Catch:{ all -> 0x0034 }
            L_0x0067:
                java.util.concurrent.atomic.AtomicInteger r4 = r6.remaining
                int r4 = r4.decrementAndGet()
                if (r4 < 0) goto L_0x0070
                goto L_0x0071
            L_0x0070:
                r2 = r3
            L_0x0071:
                com.google.common.base.Preconditions.checkState(r2, r0)
                if (r4 != 0) goto L_0x005e
                java.util.List<V> r1 = r6.values
                if (r1 == 0) goto L_0x0057
                goto L_0x004f
            L_0x007b:
                r4 = move-exception
                boolean r5 = r6.allMustSucceed     // Catch:{ all -> 0x0034 }
                if (r5 == 0) goto L_0x0087
                java.lang.Throwable r5 = r4.getCause()     // Catch:{ all -> 0x0034 }
                r6.setException(r5)     // Catch:{ all -> 0x0034 }
            L_0x0087:
                java.util.concurrent.atomic.AtomicInteger r4 = r6.remaining
                int r4 = r4.decrementAndGet()
                if (r4 < 0) goto L_0x0090
                goto L_0x0091
            L_0x0090:
                r2 = r3
            L_0x0091:
                com.google.common.base.Preconditions.checkState(r2, r0)
                if (r4 != 0) goto L_0x005e
                java.util.List<V> r1 = r6.values
                if (r1 == 0) goto L_0x0057
                goto L_0x004f
            L_0x009b:
                r4 = move-exception
                boolean r5 = r6.allMustSucceed     // Catch:{ all -> 0x0034 }
                if (r5 == 0) goto L_0x00a3
                r6.cancel(r3)     // Catch:{ all -> 0x0034 }
            L_0x00a3:
                java.util.concurrent.atomic.AtomicInteger r4 = r6.remaining
                int r4 = r4.decrementAndGet()
                if (r4 < 0) goto L_0x00ac
                goto L_0x00ad
            L_0x00ac:
                r2 = r3
            L_0x00ad:
                com.google.common.base.Preconditions.checkState(r2, r0)
                if (r4 != 0) goto L_0x005e
                java.util.List<V> r1 = r6.values
                if (r1 == 0) goto L_0x0057
                goto L_0x004f
            L_0x00b7:
                return
            L_0x00b8:
                java.util.concurrent.atomic.AtomicInteger r5 = r6.remaining
                int r5 = r5.decrementAndGet()
                if (r5 < 0) goto L_0x00c1
                goto L_0x00c2
            L_0x00c1:
                r2 = r3
            L_0x00c2:
                com.google.common.base.Preconditions.checkState(r2, r0)
                if (r5 != 0) goto L_0x00da
                java.util.List<V> r1 = r6.values
                if (r1 == 0) goto L_0x00d3
                java.util.ArrayList r0 = com.google.common.collect.Lists.newArrayList(r1)
                r6.set(r0)
                goto L_0x00da
            L_0x00d3:
                boolean r0 = r6.isDone()
                com.google.common.base.Preconditions.checkState(r0)
            L_0x00da:
                throw r4
            L_0x00db:
                boolean r0 = r6.allMustSucceed
                java.lang.String r2 = "Future was done before all dependencies completed"
                com.google.common.base.Preconditions.checkState(r0, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Futures.ListFuture.setOneValue(int, java.util.concurrent.Future):void");
        }
    }

    private static class MappingCheckedFuture<V, X extends Exception> extends AbstractCheckedFuture<V, X> {
        final Function<Exception, X> mapper;

        MappingCheckedFuture(ListenableFuture<V> delegate, Function<Exception, X> mapper2) {
            super(delegate);
            this.mapper = (Function) Preconditions.checkNotNull(mapper2);
        }

        /* access modifiers changed from: protected */
        public X mapException(Exception e) {
            return (Exception) this.mapper.apply(e);
        }
    }
}
