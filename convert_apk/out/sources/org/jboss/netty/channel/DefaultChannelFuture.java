package org.jboss.netty.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.internal.DeadLockProofWorker;

public class DefaultChannelFuture implements ChannelFuture {
    private static final Throwable CANCELLED = new Throwable();
    private static boolean disabledDeadLockCheckerOnce;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) DefaultChannelFuture.class);
    private static volatile boolean useDeadLockChecker = true;
    private final boolean cancellable;
    private Throwable cause;
    private final Channel channel;
    private boolean done;
    private ChannelFutureListener firstListener;
    private List<ChannelFutureListener> otherListeners;
    private List<ChannelFutureProgressListener> progressListeners;
    private int waiters;

    public static boolean isUseDeadLockChecker() {
        return useDeadLockChecker;
    }

    public static void setUseDeadLockChecker(boolean useDeadLockChecker2) {
        if (!useDeadLockChecker2 && !disabledDeadLockCheckerOnce) {
            disabledDeadLockCheckerOnce = true;
            if (logger.isDebugEnabled()) {
                InternalLogger internalLogger = logger;
                internalLogger.debug("The dead lock checker in " + DefaultChannelFuture.class.getSimpleName() + " has been disabled as requested at your own risk.");
            }
        }
        useDeadLockChecker = useDeadLockChecker2;
    }

    public DefaultChannelFuture(Channel channel2, boolean cancellable2) {
        this.channel = channel2;
        this.cancellable = cancellable2;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public synchronized boolean isDone() {
        return this.done;
    }

    public synchronized boolean isSuccess() {
        return this.done && this.cause == null;
    }

    public synchronized Throwable getCause() {
        if (this.cause == CANCELLED) {
            return null;
        }
        return this.cause;
    }

    public synchronized boolean isCancelled() {
        return this.cause == CANCELLED;
    }

    public void addListener(ChannelFutureListener listener) {
        if (listener != null) {
            boolean notifyNow = false;
            synchronized (this) {
                if (this.done) {
                    notifyNow = true;
                } else {
                    if (this.firstListener == null) {
                        this.firstListener = listener;
                    } else {
                        if (this.otherListeners == null) {
                            this.otherListeners = new ArrayList(1);
                        }
                        this.otherListeners.add(listener);
                    }
                    if (listener instanceof ChannelFutureProgressListener) {
                        if (this.progressListeners == null) {
                            this.progressListeners = new ArrayList(1);
                        }
                        this.progressListeners.add((ChannelFutureProgressListener) listener);
                    }
                }
            }
            if (notifyNow) {
                notifyListener(listener);
                return;
            }
            return;
        }
        throw new NullPointerException("listener");
    }

    public void removeListener(ChannelFutureListener listener) {
        if (listener != null) {
            synchronized (this) {
                if (!this.done) {
                    if (listener == this.firstListener) {
                        if (this.otherListeners == null || this.otherListeners.isEmpty()) {
                            this.firstListener = null;
                        } else {
                            this.firstListener = this.otherListeners.remove(0);
                        }
                    } else if (this.otherListeners != null) {
                        this.otherListeners.remove(listener);
                    }
                    if (listener instanceof ChannelFutureProgressListener) {
                        this.progressListeners.remove(listener);
                    }
                }
            }
            return;
        }
        throw new NullPointerException("listener");
    }

    public ChannelFuture rethrowIfFailed() throws Exception {
        Throwable cause2;
        if (!isDone() || (cause2 = getCause()) == null) {
            return this;
        }
        if (cause2 instanceof Exception) {
            throw ((Exception) cause2);
        } else if (cause2 instanceof Error) {
            throw ((Error) cause2);
        } else {
            throw new RuntimeException(cause2);
        }
    }

    public ChannelFuture sync() throws InterruptedException {
        await();
        rethrowIfFailed0();
        return this;
    }

    public ChannelFuture syncUninterruptibly() {
        awaitUninterruptibly();
        rethrowIfFailed0();
        return this;
    }

    private void rethrowIfFailed0() {
        Throwable cause2 = getCause();
        if (cause2 != null) {
            if (cause2 instanceof RuntimeException) {
                throw ((RuntimeException) cause2);
            } else if (cause2 instanceof Error) {
                throw ((Error) cause2);
            } else {
                throw new ChannelException(cause2);
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public ChannelFuture await() throws InterruptedException {
        if (!Thread.interrupted()) {
            synchronized (this) {
                while (!this.done) {
                    checkDeadLock();
                    this.waiters++;
                    try {
                        wait();
                        this.waiters--;
                    } catch (Throwable th) {
                        this.waiters--;
                        throw th;
                    }
                }
            }
            return this;
        }
        throw new InterruptedException();
    }

    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return await0(unit.toNanos(timeout), true);
    }

    public boolean await(long timeoutMillis) throws InterruptedException {
        return await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), true);
    }

    public ChannelFuture awaitUninterruptibly() {
        int i;
        boolean interrupted = false;
        synchronized (this) {
            while (!this.done) {
                checkDeadLock();
                this.waiters++;
                try {
                    wait();
                    i = this.waiters;
                } catch (InterruptedException e) {
                    interrupted = true;
                    i = this.waiters;
                } catch (Throwable th) {
                    this.waiters--;
                    throw th;
                }
                this.waiters = i - 1;
            }
        }
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
        return this;
    }

    public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
        try {
            return await0(unit.toNanos(timeout), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    public boolean awaitUninterruptibly(long timeoutMillis) {
        try {
            return await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002f, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0037, code lost:
        if (r8 == false) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0039, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0040, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0067, code lost:
        if (r8 == false) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0069, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0070, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0084, code lost:
        if (r8 == false) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0086, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x008d, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean await0(long r15, boolean r17) throws java.lang.InterruptedException {
        /*
            r14 = this;
            r1 = r14
            if (r17 == 0) goto L_0x0010
            boolean r0 = java.lang.Thread.interrupted()
            if (r0 != 0) goto L_0x000a
            goto L_0x0010
        L_0x000a:
            java.lang.InterruptedException r0 = new java.lang.InterruptedException
            r0.<init>()
            throw r0
        L_0x0010:
            r2 = 0
            int r0 = (r15 > r2 ? 1 : (r15 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x0018
            r4 = r2
            goto L_0x001c
        L_0x0018:
            long r4 = java.lang.System.nanoTime()
        L_0x001c:
            r6 = r15
            r8 = 0
            monitor-enter(r14)     // Catch:{ all -> 0x0099 }
            boolean r0 = r1.done     // Catch:{ all -> 0x0096 }
            if (r0 == 0) goto L_0x0030
            boolean r0 = r1.done     // Catch:{ all -> 0x0096 }
            monitor-exit(r14)     // Catch:{ all -> 0x0096 }
            if (r8 == 0) goto L_0x002f
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x002f:
            return r0
        L_0x0030:
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x0041
            boolean r0 = r1.done     // Catch:{ all -> 0x0096 }
            monitor-exit(r14)     // Catch:{ all -> 0x0096 }
            if (r8 == 0) goto L_0x0040
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x0040:
            return r0
        L_0x0041:
            checkDeadLock()     // Catch:{ all -> 0x0096 }
            int r0 = r1.waiters     // Catch:{ all -> 0x0096 }
            r9 = 1
            int r0 = r0 + r9
            r1.waiters = r0     // Catch:{ all -> 0x0096 }
        L_0x004a:
            r10 = 1000000(0xf4240, double:4.940656E-318)
            long r12 = r6 / r10
            long r10 = r6 % r10
            int r0 = (int) r10     // Catch:{ InterruptedException -> 0x0058 }
            r14.wait(r12, r0)     // Catch:{ InterruptedException -> 0x0058 }
            goto L_0x005c
        L_0x0056:
            r0 = move-exception
            goto L_0x0090
        L_0x0058:
            r0 = move-exception
            if (r17 != 0) goto L_0x008e
            r8 = 1
        L_0x005c:
            boolean r0 = r1.done     // Catch:{ all -> 0x0056 }
            if (r0 == 0) goto L_0x0071
            int r0 = r1.waiters     // Catch:{ all -> 0x0096 }
            int r0 = r0 - r9
            r1.waiters = r0     // Catch:{ all -> 0x0096 }
            monitor-exit(r14)     // Catch:{ all -> 0x0096 }
            if (r8 == 0) goto L_0x0070
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r0.interrupt()
        L_0x0070:
            return r9
        L_0x0071:
            long r10 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0056 }
            long r10 = r10 - r4
            long r6 = r15 - r10
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x004a
            boolean r0 = r1.done     // Catch:{ all -> 0x0056 }
            int r2 = r1.waiters     // Catch:{ all -> 0x0096 }
            int r2 = r2 - r9
            r1.waiters = r2     // Catch:{ all -> 0x0096 }
            monitor-exit(r14)     // Catch:{ all -> 0x0096 }
            if (r8 == 0) goto L_0x008d
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x008d:
            return r0
        L_0x008e:
            throw r0     // Catch:{ all -> 0x0056 }
        L_0x0090:
            int r2 = r1.waiters     // Catch:{ all -> 0x0096 }
            int r2 = r2 - r9
            r1.waiters = r2     // Catch:{ all -> 0x0096 }
            throw r0     // Catch:{ all -> 0x0096 }
        L_0x0096:
            r0 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x0096 }
            throw r0     // Catch:{ all -> 0x0099 }
        L_0x0099:
            r0 = move-exception
            if (r8 == 0) goto L_0x00a3
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x00a3:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.channel.DefaultChannelFuture.await0(long, boolean):boolean");
    }

    private static void checkDeadLock() {
        if (isUseDeadLockChecker() && DeadLockProofWorker.PARENT.get() != null) {
            throw new IllegalStateException("await*() in I/O thread causes a dead lock or sudden performance drop. Use addListener() instead or call await*() from a different thread.");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0013, code lost:
        notifyListeners();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0016, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean setSuccess() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.done     // Catch:{ all -> 0x0017 }
            if (r0 == 0) goto L_0x0008
            r0 = 0
            monitor-exit(r2)     // Catch:{ all -> 0x0017 }
            return r0
        L_0x0008:
            r0 = 1
            r2.done = r0     // Catch:{ all -> 0x0017 }
            int r1 = r2.waiters     // Catch:{ all -> 0x0017 }
            if (r1 <= 0) goto L_0x0012
            r2.notifyAll()     // Catch:{ all -> 0x0017 }
        L_0x0012:
            monitor-exit(r2)     // Catch:{ all -> 0x0017 }
            r2.notifyListeners()
            return r0
        L_0x0017:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0017 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.channel.DefaultChannelFuture.setSuccess():boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0015, code lost:
        notifyListeners();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean setFailure(java.lang.Throwable r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.done     // Catch:{ all -> 0x0019 }
            if (r0 == 0) goto L_0x0008
            r0 = 0
            monitor-exit(r2)     // Catch:{ all -> 0x0019 }
            return r0
        L_0x0008:
            r2.cause = r3     // Catch:{ all -> 0x0019 }
            r0 = 1
            r2.done = r0     // Catch:{ all -> 0x0019 }
            int r1 = r2.waiters     // Catch:{ all -> 0x0019 }
            if (r1 <= 0) goto L_0x0014
            r2.notifyAll()     // Catch:{ all -> 0x0019 }
        L_0x0014:
            monitor-exit(r2)     // Catch:{ all -> 0x0019 }
            r2.notifyListeners()
            return r0
        L_0x0019:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0019 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.channel.DefaultChannelFuture.setFailure(java.lang.Throwable):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001c, code lost:
        notifyListeners();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001f, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean cancel() {
        /*
            r2 = this;
            boolean r0 = r2.cancellable
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            monitor-enter(r2)
            boolean r0 = r2.done     // Catch:{ all -> 0x0020 }
            if (r0 == 0) goto L_0x000d
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
            return r1
        L_0x000d:
            java.lang.Throwable r0 = CANCELLED     // Catch:{ all -> 0x0020 }
            r2.cause = r0     // Catch:{ all -> 0x0020 }
            r0 = 1
            r2.done = r0     // Catch:{ all -> 0x0020 }
            int r1 = r2.waiters     // Catch:{ all -> 0x0020 }
            if (r1 <= 0) goto L_0x001b
            r2.notifyAll()     // Catch:{ all -> 0x0020 }
        L_0x001b:
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
            r2.notifyListeners()
            return r0
        L_0x0020:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0020 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.channel.DefaultChannelFuture.cancel():boolean");
    }

    private void notifyListeners() {
        ChannelFutureListener channelFutureListener = this.firstListener;
        if (channelFutureListener != null) {
            notifyListener(channelFutureListener);
            this.firstListener = null;
            List<ChannelFutureListener> list = this.otherListeners;
            if (list != null) {
                for (ChannelFutureListener l : list) {
                    notifyListener(l);
                }
                this.otherListeners = null;
            }
        }
    }

    private void notifyListener(ChannelFutureListener l) {
        try {
            l.operationComplete(this);
        } catch (Throwable t) {
            if (logger.isWarnEnabled()) {
                InternalLogger internalLogger = logger;
                internalLogger.warn("An exception was thrown by " + ChannelFutureListener.class.getSimpleName() + ".", t);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0024, code lost:
        r0 = r11;
        r12 = r0.length;
        r13 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0028, code lost:
        if (r13 >= r12) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002a, code lost:
        notifyProgressListener(r0[r13], r16, r18, r20);
        r13 = r13 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003a, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean setProgress(long r16, long r18, long r20) {
        /*
            r15 = this;
            r9 = r15
            monitor-enter(r15)
            r1 = 0
            boolean r0 = r9.done     // Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x000a
            r0 = 0
            monitor-exit(r15)     // Catch:{ all -> 0x0040 }
            return r0
        L_0x000a:
            java.util.List<org.jboss.netty.channel.ChannelFutureProgressListener> r0 = r9.progressListeners     // Catch:{ all -> 0x0040 }
            r10 = 1
            if (r0 == 0) goto L_0x003e
            boolean r2 = r0.isEmpty()     // Catch:{ all -> 0x0040 }
            if (r2 == 0) goto L_0x0016
            goto L_0x003e
        L_0x0016:
            int r2 = r0.size()     // Catch:{ all -> 0x0040 }
            org.jboss.netty.channel.ChannelFutureProgressListener[] r2 = new org.jboss.netty.channel.ChannelFutureProgressListener[r2]     // Catch:{ all -> 0x0040 }
            java.lang.Object[] r2 = r0.toArray(r2)     // Catch:{ all -> 0x0040 }
            org.jboss.netty.channel.ChannelFutureProgressListener[] r2 = (org.jboss.netty.channel.ChannelFutureProgressListener[]) r2     // Catch:{ all -> 0x0040 }
            r11 = r2
            monitor-exit(r15)     // Catch:{ all -> 0x003b }
            r0 = r11
            int r12 = r0.length
            r1 = 0
            r13 = r1
        L_0x0028:
            if (r13 >= r12) goto L_0x003a
            r14 = r0[r13]
            r1 = r15
            r2 = r14
            r3 = r16
            r5 = r18
            r7 = r20
            r1.notifyProgressListener(r2, r3, r5, r7)
            int r13 = r13 + 1
            goto L_0x0028
        L_0x003a:
            return r10
        L_0x003b:
            r0 = move-exception
            r1 = r11
            goto L_0x0041
        L_0x003e:
            monitor-exit(r15)     // Catch:{ all -> 0x0040 }
            return r10
        L_0x0040:
            r0 = move-exception
        L_0x0041:
            monitor-exit(r15)     // Catch:{ all -> 0x0043 }
            throw r0
        L_0x0043:
            r0 = move-exception
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.channel.DefaultChannelFuture.setProgress(long, long, long):boolean");
    }

    private void notifyProgressListener(ChannelFutureProgressListener l, long amount, long current, long total) {
        try {
            l.operationProgressed(this, amount, current, total);
        } catch (Throwable t) {
            if (logger.isWarnEnabled()) {
                InternalLogger internalLogger = logger;
                internalLogger.warn("An exception was thrown by " + ChannelFutureProgressListener.class.getSimpleName() + ".", t);
            }
        }
    }
}
