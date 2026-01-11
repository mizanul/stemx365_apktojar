package org.jboss.netty.channel.group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.internal.DeadLockProofWorker;

public class DefaultChannelGroupFuture implements ChannelGroupFuture {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) DefaultChannelGroupFuture.class);
    private final ChannelFutureListener childListener = new ChannelFutureListener() {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<DefaultChannelGroupFuture> cls = DefaultChannelGroupFuture.class;
        }

        public void operationComplete(ChannelFuture future) throws Exception {
            boolean callSetDone;
            boolean success = future.isSuccess();
            synchronized (DefaultChannelGroupFuture.this) {
                boolean z = true;
                if (success) {
                    DefaultChannelGroupFuture.this.successCount++;
                } else {
                    DefaultChannelGroupFuture.this.failureCount++;
                }
                if (DefaultChannelGroupFuture.this.successCount + DefaultChannelGroupFuture.this.failureCount != DefaultChannelGroupFuture.this.futures.size()) {
                    z = false;
                }
                callSetDone = z;
            }
            if (callSetDone) {
                DefaultChannelGroupFuture.this.setDone();
            }
        }
    };
    private boolean done;
    int failureCount;
    private ChannelGroupFutureListener firstListener;
    final Map<Integer, ChannelFuture> futures;
    private final ChannelGroup group;
    private List<ChannelGroupFutureListener> otherListeners;
    int successCount;
    private int waiters;

    public DefaultChannelGroupFuture(ChannelGroup group2, Collection<ChannelFuture> futures2) {
        if (group2 == null) {
            throw new NullPointerException("group");
        } else if (futures2 != null) {
            this.group = group2;
            Map<Integer, ChannelFuture> futureMap = new LinkedHashMap<>();
            for (ChannelFuture f : futures2) {
                futureMap.put(f.getChannel().getId(), f);
            }
            Map<Integer, ChannelFuture> unmodifiableMap = Collections.unmodifiableMap(futureMap);
            this.futures = unmodifiableMap;
            for (ChannelFuture f2 : unmodifiableMap.values()) {
                f2.addListener(this.childListener);
            }
            if (this.futures.isEmpty()) {
                setDone();
            }
        } else {
            throw new NullPointerException("futures");
        }
    }

    DefaultChannelGroupFuture(ChannelGroup group2, Map<Integer, ChannelFuture> futures2) {
        this.group = group2;
        Map<Integer, ChannelFuture> unmodifiableMap = Collections.unmodifiableMap(futures2);
        this.futures = unmodifiableMap;
        for (ChannelFuture f : unmodifiableMap.values()) {
            f.addListener(this.childListener);
        }
        if (this.futures.isEmpty()) {
            setDone();
        }
    }

    public ChannelGroup getGroup() {
        return this.group;
    }

    public ChannelFuture find(Integer channelId) {
        return this.futures.get(channelId);
    }

    public ChannelFuture find(Channel channel) {
        return this.futures.get(channel.getId());
    }

    public Iterator<ChannelFuture> iterator() {
        return this.futures.values().iterator();
    }

    public synchronized boolean isDone() {
        return this.done;
    }

    public synchronized boolean isCompleteSuccess() {
        return this.successCount == this.futures.size();
    }

    public synchronized boolean isPartialSuccess() {
        return (this.successCount == 0 || this.successCount == this.futures.size()) ? false : true;
    }

    public synchronized boolean isPartialFailure() {
        return (this.failureCount == 0 || this.failureCount == this.futures.size()) ? false : true;
    }

    public synchronized boolean isCompleteFailure() {
        int futureCnt;
        futureCnt = this.futures.size();
        return futureCnt != 0 && this.failureCount == futureCnt;
    }

    public void addListener(ChannelGroupFutureListener listener) {
        if (listener != null) {
            boolean notifyNow = false;
            synchronized (this) {
                if (this.done) {
                    notifyNow = true;
                } else if (this.firstListener == null) {
                    this.firstListener = listener;
                } else {
                    if (this.otherListeners == null) {
                        this.otherListeners = new ArrayList(1);
                    }
                    this.otherListeners.add(listener);
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

    public void removeListener(ChannelGroupFutureListener listener) {
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
                }
            }
            return;
        }
        throw new NullPointerException("listener");
    }

    /* JADX INFO: finally extract failed */
    public ChannelGroupFuture await() throws InterruptedException {
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

    /* JADX INFO: finally extract failed */
    public ChannelGroupFuture awaitUninterruptibly() {
        boolean interrupted = false;
        synchronized (this) {
            while (!this.done) {
                checkDeadLock();
                this.waiters++;
                try {
                    wait();
                    this.waiters--;
                } catch (InterruptedException e) {
                    interrupted = true;
                    this.waiters--;
                } catch (Throwable th) {
                    this.waiters--;
                    throw th;
                }
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

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0030, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0038, code lost:
        if (r8 == false) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x003a, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0041, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0068, code lost:
        if (r8 == false) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x006a, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0071, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0085, code lost:
        if (r8 == false) goto L_0x008e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0087, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x008e, code lost:
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
            monitor-enter(r14)     // Catch:{ all -> 0x009b }
            boolean r0 = r1.done     // Catch:{ all -> 0x0098 }
            if (r0 == 0) goto L_0x0031
            boolean r0 = r1.done     // Catch:{ all -> 0x0098 }
            monitor-exit(r14)     // Catch:{ all -> 0x0098 }
            if (r8 == 0) goto L_0x002f
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x002f:
            return r0
        L_0x0031:
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x0042
            boolean r0 = r1.done     // Catch:{ all -> 0x0098 }
            monitor-exit(r14)     // Catch:{ all -> 0x0098 }
            if (r8 == 0) goto L_0x0041
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x0041:
            return r0
        L_0x0042:
            checkDeadLock()     // Catch:{ all -> 0x0098 }
            int r0 = r1.waiters     // Catch:{ all -> 0x0098 }
            r9 = 1
            int r0 = r0 + r9
            r1.waiters = r0     // Catch:{ all -> 0x0098 }
        L_0x004b:
            r10 = 1000000(0xf4240, double:4.940656E-318)
            long r12 = r6 / r10
            long r10 = r6 % r10
            int r0 = (int) r10     // Catch:{ InterruptedException -> 0x0059 }
            r14.wait(r12, r0)     // Catch:{ InterruptedException -> 0x0059 }
            goto L_0x005d
        L_0x0057:
            r0 = move-exception
            goto L_0x0091
        L_0x0059:
            r0 = move-exception
            if (r17 != 0) goto L_0x008f
            r8 = 1
        L_0x005d:
            boolean r0 = r1.done     // Catch:{ all -> 0x0057 }
            if (r0 == 0) goto L_0x0072
            int r0 = r1.waiters     // Catch:{ all -> 0x0098 }
            int r0 = r0 - r9
            r1.waiters = r0     // Catch:{ all -> 0x0098 }
            monitor-exit(r14)     // Catch:{ all -> 0x0098 }
            if (r8 == 0) goto L_0x0071
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r0.interrupt()
        L_0x0071:
            return r9
        L_0x0072:
            long r10 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0057 }
            long r10 = r10 - r4
            long r6 = r15 - r10
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x004b
            boolean r0 = r1.done     // Catch:{ all -> 0x0057 }
            int r2 = r1.waiters     // Catch:{ all -> 0x0098 }
            int r2 = r2 - r9
            r1.waiters = r2     // Catch:{ all -> 0x0098 }
            monitor-exit(r14)     // Catch:{ all -> 0x0098 }
            if (r8 == 0) goto L_0x008e
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x008e:
            return r0
        L_0x008f:
            throw r0     // Catch:{ all -> 0x0057 }
        L_0x0091:
            int r2 = r1.waiters     // Catch:{ all -> 0x0098 }
            int r2 = r2 - r9
            r1.waiters = r2     // Catch:{ all -> 0x0098 }
            throw r0     // Catch:{ all -> 0x0098 }
        L_0x0098:
            r0 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x0098 }
            throw r0     // Catch:{ all -> 0x009b }
        L_0x009b:
            r0 = move-exception
            if (r8 == 0) goto L_0x00a5
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            r2.interrupt()
        L_0x00a5:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.channel.group.DefaultChannelGroupFuture.await0(long, boolean):boolean");
    }

    private static void checkDeadLock() {
        if (DeadLockProofWorker.PARENT.get() != null) {
            throw new IllegalStateException("await*() in I/O thread causes a dead lock or sudden performance drop. Use addListener() instead or call await*() from a different thread.");
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0013, code lost:
        notifyListeners();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0016, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean setDone() {
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
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.channel.group.DefaultChannelGroupFuture.setDone():boolean");
    }

    private void notifyListeners() {
        ChannelGroupFutureListener channelGroupFutureListener = this.firstListener;
        if (channelGroupFutureListener != null) {
            notifyListener(channelGroupFutureListener);
            this.firstListener = null;
            List<ChannelGroupFutureListener> list = this.otherListeners;
            if (list != null) {
                for (ChannelGroupFutureListener l : list) {
                    notifyListener(l);
                }
                this.otherListeners = null;
            }
        }
    }

    private void notifyListener(ChannelGroupFutureListener l) {
        try {
            l.operationComplete(this);
        } catch (Throwable t) {
            if (logger.isWarnEnabled()) {
                InternalLogger internalLogger = logger;
                internalLogger.warn("An exception was thrown by " + ChannelFutureListener.class.getSimpleName() + ".", t);
            }
        }
    }
}
