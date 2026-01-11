package com.google.common.util.concurrent;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

abstract class AbstractListeningExecutorService implements ListeningExecutorService {
    AbstractListeningExecutorService() {
    }

    public ListenableFuture<?> submit(Runnable task) {
        ListenableFutureTask<Void> ftask = ListenableFutureTask.create(task, null);
        execute(ftask);
        return ftask;
    }

    public <T> ListenableFuture<T> submit(Runnable task, T result) {
        ListenableFutureTask<T> ftask = ListenableFutureTask.create(task, result);
        execute(ftask);
        return ftask;
    }

    public <T> ListenableFuture<T> submit(Callable<T> task) {
        ListenableFutureTask<T> ftask = ListenableFutureTask.create(task);
        execute(ftask);
        return ftask;
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x00c9 A[LOOP:2: B:49:0x00c3->B:51:0x00c9, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <T> T doInvokeAny(java.util.Collection<? extends java.util.concurrent.Callable<T>> r19, boolean r20, long r21) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        /*
            r18 = this;
            int r1 = r19.size()
            r2 = 1
            if (r1 <= 0) goto L_0x0009
            r0 = r2
            goto L_0x000a
        L_0x0009:
            r0 = 0
        L_0x000a:
            com.google.common.base.Preconditions.checkArgument(r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>(r1)
            r3 = r0
            java.util.concurrent.ExecutorCompletionService r0 = new java.util.concurrent.ExecutorCompletionService
            r4 = r18
            r0.<init>(r4)
            r5 = r0
            r0 = 0
            if (r20 == 0) goto L_0x0028
            long r6 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0023 }
            goto L_0x002a
        L_0x0023:
            r0 = move-exception
            r6 = r21
            goto L_0x00bf
        L_0x0028:
            r6 = 0
        L_0x002a:
            java.util.Iterator r8 = r19.iterator()     // Catch:{ all -> 0x0023 }
            java.lang.Object r9 = r8.next()     // Catch:{ all -> 0x0023 }
            java.util.concurrent.Callable r9 = (java.util.concurrent.Callable) r9     // Catch:{ all -> 0x0023 }
            java.util.concurrent.Future r9 = r5.submit(r9)     // Catch:{ all -> 0x0023 }
            r3.add(r9)     // Catch:{ all -> 0x0023 }
            int r1 = r1 + -1
            r9 = 1
            r10 = r6
            r6 = r21
            r17 = r1
            r1 = r0
            r0 = r9
            r9 = r17
        L_0x0047:
            java.util.concurrent.Future r12 = r5.poll()     // Catch:{ all -> 0x00bd }
            if (r12 != 0) goto L_0x008e
            if (r9 <= 0) goto L_0x0061
            int r9 = r9 + -1
            java.lang.Object r13 = r8.next()     // Catch:{ all -> 0x00bd }
            java.util.concurrent.Callable r13 = (java.util.concurrent.Callable) r13     // Catch:{ all -> 0x00bd }
            java.util.concurrent.Future r13 = r5.submit(r13)     // Catch:{ all -> 0x00bd }
            r3.add(r13)     // Catch:{ all -> 0x00bd }
            int r0 = r0 + 1
            goto L_0x008e
        L_0x0061:
            if (r0 != 0) goto L_0x006f
            if (r1 != 0) goto L_0x006d
            java.util.concurrent.ExecutionException r12 = new java.util.concurrent.ExecutionException     // Catch:{ all -> 0x00bd }
            r13 = 0
            r12.<init>(r13)     // Catch:{ all -> 0x00bd }
            r1 = r12
        L_0x006d:
            throw r1     // Catch:{ all -> 0x00bd }
        L_0x006f:
            if (r20 == 0) goto L_0x0089
            java.util.concurrent.TimeUnit r13 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ all -> 0x00bd }
            java.util.concurrent.Future r13 = r5.poll(r6, r13)     // Catch:{ all -> 0x00bd }
            r12 = r13
            if (r12 == 0) goto L_0x0083
            long r13 = java.lang.System.nanoTime()     // Catch:{ all -> 0x00bd }
            long r15 = r13 - r10
            long r6 = r6 - r15
            r10 = r13
            goto L_0x008e
        L_0x0083:
            java.util.concurrent.TimeoutException r13 = new java.util.concurrent.TimeoutException     // Catch:{ all -> 0x00bd }
            r13.<init>()     // Catch:{ all -> 0x00bd }
            throw r13     // Catch:{ all -> 0x00bd }
        L_0x0089:
            java.util.concurrent.Future r13 = r5.take()     // Catch:{ all -> 0x00bd }
            r12 = r13
        L_0x008e:
            if (r12 == 0) goto L_0x00bc
            int r13 = r0 + -1
            java.lang.Object r0 = r12.get()     // Catch:{ ExecutionException -> 0x00b6, RuntimeException -> 0x00ab }
            java.util.Iterator r14 = r3.iterator()
        L_0x009a:
            boolean r15 = r14.hasNext()
            if (r15 == 0) goto L_0x00aa
            java.lang.Object r15 = r14.next()
            java.util.concurrent.Future r15 = (java.util.concurrent.Future) r15
            r15.cancel(r2)
            goto L_0x009a
        L_0x00aa:
            return r0
        L_0x00ab:
            r0 = move-exception
            r14 = r0
            r0 = r14
            java.util.concurrent.ExecutionException r14 = new java.util.concurrent.ExecutionException     // Catch:{ all -> 0x00bd }
            r14.<init>(r0)     // Catch:{ all -> 0x00bd }
            r1 = r14
            r0 = r13
            goto L_0x00bc
        L_0x00b6:
            r0 = move-exception
            r14 = r0
            r0 = r14
            r1 = r0
            r0 = r13
        L_0x00bc:
            goto L_0x0047
        L_0x00bd:
            r0 = move-exception
            r1 = r9
        L_0x00bf:
            java.util.Iterator r8 = r3.iterator()
        L_0x00c3:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x00d3
            java.lang.Object r9 = r8.next()
            java.util.concurrent.Future r9 = (java.util.concurrent.Future) r9
            r9.cancel(r2)
            goto L_0x00c3
        L_0x00d3:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractListeningExecutorService.doInvokeAny(java.util.Collection, boolean, long):java.lang.Object");
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        try {
            return doInvokeAny(tasks, false, 0);
        } catch (TimeoutException e) {
            throw new AssertionError();
        }
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return doInvokeAny(tasks, true, unit.toNanos(timeout));
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public <T> java.util.List<java.util.concurrent.Future<T>> invokeAll(java.util.Collection<? extends java.util.concurrent.Callable<T>> r7) throws java.lang.InterruptedException {
        /*
            r6 = this;
            if (r7 == 0) goto L_0x0078
            java.util.ArrayList r0 = new java.util.ArrayList
            int r1 = r7.size()
            r0.<init>(r1)
            r1 = 0
            r2 = 1
            java.util.Iterator r3 = r7.iterator()     // Catch:{ all -> 0x0060 }
        L_0x0011:
            boolean r4 = r3.hasNext()     // Catch:{ all -> 0x0060 }
            if (r4 == 0) goto L_0x0028
            java.lang.Object r4 = r3.next()     // Catch:{ all -> 0x0060 }
            java.util.concurrent.Callable r4 = (java.util.concurrent.Callable) r4     // Catch:{ all -> 0x0060 }
            com.google.common.util.concurrent.ListenableFutureTask r5 = com.google.common.util.concurrent.ListenableFutureTask.create(r4)     // Catch:{ all -> 0x0060 }
            r0.add(r5)     // Catch:{ all -> 0x0060 }
            r6.execute(r5)     // Catch:{ all -> 0x0060 }
            goto L_0x0011
        L_0x0028:
            java.util.Iterator r3 = r0.iterator()     // Catch:{ all -> 0x0060 }
        L_0x002c:
            boolean r4 = r3.hasNext()     // Catch:{ all -> 0x0060 }
            if (r4 == 0) goto L_0x0047
            java.lang.Object r4 = r3.next()     // Catch:{ all -> 0x0060 }
            java.util.concurrent.Future r4 = (java.util.concurrent.Future) r4     // Catch:{ all -> 0x0060 }
            boolean r5 = r4.isDone()     // Catch:{ all -> 0x0060 }
            if (r5 != 0) goto L_0x0046
            r4.get()     // Catch:{ CancellationException -> 0x0044, ExecutionException -> 0x0042 }
        L_0x0041:
            goto L_0x0046
        L_0x0042:
            r5 = move-exception
            goto L_0x0046
        L_0x0044:
            r5 = move-exception
            goto L_0x0041
        L_0x0046:
            goto L_0x002c
        L_0x0047:
            r1 = 1
            if (r1 != 0) goto L_0x005f
            java.util.Iterator r3 = r0.iterator()
        L_0x004f:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x005f
            java.lang.Object r4 = r3.next()
            java.util.concurrent.Future r4 = (java.util.concurrent.Future) r4
            r4.cancel(r2)
            goto L_0x004f
        L_0x005f:
            return r0
        L_0x0060:
            r3 = move-exception
            if (r1 != 0) goto L_0x0077
            java.util.Iterator r4 = r0.iterator()
        L_0x0067:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0077
            java.lang.Object r5 = r4.next()
            java.util.concurrent.Future r5 = (java.util.concurrent.Future) r5
            r5.cancel(r2)
            goto L_0x0067
        L_0x0077:
            throw r3
        L_0x0078:
            r0 = 0
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractListeningExecutorService.invokeAll(java.util.Collection):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0117  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> java.util.List<java.util.concurrent.Future<T>> invokeAll(java.util.Collection<? extends java.util.concurrent.Callable<T>> r24, long r25, java.util.concurrent.TimeUnit r27) throws java.lang.InterruptedException {
        /*
            r23 = this;
            r1 = r27
            if (r24 == 0) goto L_0x012c
            if (r1 == 0) goto L_0x012c
            r2 = r25
            long r4 = r1.toNanos(r2)
            java.util.ArrayList r0 = new java.util.ArrayList
            int r6 = r24.size()
            r0.<init>(r6)
            r6 = r0
            r7 = 0
            r8 = 1
            java.util.Iterator r0 = r24.iterator()     // Catch:{ all -> 0x0112 }
        L_0x001c:
            boolean r9 = r0.hasNext()     // Catch:{ all -> 0x0112 }
            if (r9 == 0) goto L_0x0030
            java.lang.Object r9 = r0.next()     // Catch:{ all -> 0x0112 }
            java.util.concurrent.Callable r9 = (java.util.concurrent.Callable) r9     // Catch:{ all -> 0x0112 }
            com.google.common.util.concurrent.ListenableFutureTask r10 = com.google.common.util.concurrent.ListenableFutureTask.create(r9)     // Catch:{ all -> 0x0112 }
            r6.add(r10)     // Catch:{ all -> 0x0112 }
            goto L_0x001c
        L_0x0030:
            long r9 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0112 }
            java.util.Iterator r0 = r6.iterator()     // Catch:{ all -> 0x0112 }
            r11 = r0
        L_0x0039:
            boolean r0 = r11.hasNext()     // Catch:{ all -> 0x0112 }
            r12 = 0
            if (r0 == 0) goto L_0x0074
            java.lang.Object r0 = r11.next()     // Catch:{ all -> 0x0112 }
            java.lang.Runnable r0 = (java.lang.Runnable) r0     // Catch:{ all -> 0x0112 }
            java.lang.Runnable r0 = (java.lang.Runnable) r0     // Catch:{ all -> 0x0112 }
            r14 = r23
            r14.execute(r0)     // Catch:{ all -> 0x0110 }
            long r15 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0110 }
            long r17 = r15 - r9
            long r4 = r4 - r17
            r9 = r15
            int r0 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r0 > 0) goto L_0x0073
            if (r7 != 0) goto L_0x0072
            java.util.Iterator r0 = r6.iterator()
        L_0x0062:
            boolean r12 = r0.hasNext()
            if (r12 == 0) goto L_0x0072
            java.lang.Object r12 = r0.next()
            java.util.concurrent.Future r12 = (java.util.concurrent.Future) r12
            r12.cancel(r8)
            goto L_0x0062
        L_0x0072:
            return r6
        L_0x0073:
            goto L_0x0039
        L_0x0074:
            r14 = r23
            java.util.Iterator r0 = r6.iterator()     // Catch:{ all -> 0x0110 }
            r15 = r0
        L_0x007b:
            boolean r0 = r15.hasNext()     // Catch:{ all -> 0x0110 }
            if (r0 == 0) goto L_0x00f7
            java.lang.Object r0 = r15.next()     // Catch:{ all -> 0x0110 }
            java.util.concurrent.Future r0 = (java.util.concurrent.Future) r0     // Catch:{ all -> 0x0110 }
            r16 = r0
            boolean r0 = r16.isDone()     // Catch:{ all -> 0x0110 }
            if (r0 != 0) goto L_0x00f2
            int r0 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r0 > 0) goto L_0x00ab
            if (r7 != 0) goto L_0x00aa
            java.util.Iterator r0 = r6.iterator()
        L_0x009a:
            boolean r12 = r0.hasNext()
            if (r12 == 0) goto L_0x00aa
            java.lang.Object r12 = r0.next()
            java.util.concurrent.Future r12 = (java.util.concurrent.Future) r12
            r12.cancel(r8)
            goto L_0x009a
        L_0x00aa:
            return r6
        L_0x00ab:
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ CancellationException -> 0x00e3, ExecutionException -> 0x00df, TimeoutException -> 0x00b9 }
            r12 = r16
            r12.get(r4, r0)     // Catch:{ CancellationException -> 0x00b7, ExecutionException -> 0x00b5, TimeoutException -> 0x00b3 }
            goto L_0x00e6
        L_0x00b3:
            r0 = move-exception
            goto L_0x00bc
        L_0x00b5:
            r0 = move-exception
            goto L_0x00e6
        L_0x00b7:
            r0 = move-exception
            goto L_0x00e6
        L_0x00b9:
            r0 = move-exception
            r12 = r16
        L_0x00bc:
            if (r7 != 0) goto L_0x00dc
            java.util.Iterator r13 = r6.iterator()
        L_0x00c3:
            boolean r16 = r13.hasNext()
            if (r16 == 0) goto L_0x00d9
            java.lang.Object r16 = r13.next()
            r17 = r0
            r0 = r16
            java.util.concurrent.Future r0 = (java.util.concurrent.Future) r0
            r0.cancel(r8)
            r0 = r17
            goto L_0x00c3
        L_0x00d9:
            r17 = r0
            goto L_0x00de
        L_0x00dc:
            r17 = r0
        L_0x00de:
            return r6
        L_0x00df:
            r0 = move-exception
            r12 = r16
            goto L_0x00e6
        L_0x00e3:
            r0 = move-exception
            r12 = r16
        L_0x00e6:
            long r19 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0110 }
            long r21 = r19 - r9
            long r4 = r4 - r21
            r9 = r19
            goto L_0x00f4
        L_0x00f2:
            r12 = r16
        L_0x00f4:
            r12 = 0
            goto L_0x007b
        L_0x00f7:
            r0 = 1
            if (r0 != 0) goto L_0x010f
            java.util.Iterator r7 = r6.iterator()
        L_0x00ff:
            boolean r12 = r7.hasNext()
            if (r12 == 0) goto L_0x010f
            java.lang.Object r12 = r7.next()
            java.util.concurrent.Future r12 = (java.util.concurrent.Future) r12
            r12.cancel(r8)
            goto L_0x00ff
        L_0x010f:
            return r6
        L_0x0110:
            r0 = move-exception
            goto L_0x0115
        L_0x0112:
            r0 = move-exception
            r14 = r23
        L_0x0115:
            if (r7 != 0) goto L_0x012b
            java.util.Iterator r9 = r6.iterator()
        L_0x011b:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x012b
            java.lang.Object r10 = r9.next()
            java.util.concurrent.Future r10 = (java.util.concurrent.Future) r10
            r10.cancel(r8)
            goto L_0x011b
        L_0x012b:
            throw r0
        L_0x012c:
            r14 = r23
            r2 = r25
            r0 = 0
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractListeningExecutorService.invokeAll(java.util.Collection, long, java.util.concurrent.TimeUnit):java.util.List");
    }
}
