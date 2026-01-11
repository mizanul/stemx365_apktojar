package org.jboss.netty.channel.socket.nio;

import java.io.IOException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.socket.Worker;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.ThreadRenamingRunnable;
import org.jboss.netty.util.internal.DeadLockProofWorker;
import org.jboss.netty.util.internal.QueueFactory;

abstract class AbstractNioWorker implements Worker {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int CLEANUP_INTERVAL = 256;
    private static final int CONSTRAINT_LEVEL = NioProviderMetadata.CONSTRAINT_LEVEL;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) AbstractNioWorker.class);
    private static final AtomicInteger nextId = new AtomicInteger();
    private final boolean allowShutdownOnIdle;
    private volatile int cancelledKeys;
    private final Queue<Runnable> eventQueue;
    private final Executor executor;

    /* renamed from: id */
    final int f121id;
    private final Queue<Runnable> registerTaskQueue;
    volatile Selector selector;
    private final ReadWriteLock selectorGuard;
    protected final SocketSendBufferPool sendBufferPool;
    private final Object startStopLock;
    private boolean started;
    protected volatile Thread thread;
    protected final AtomicBoolean wakenUp;
    protected final Queue<Runnable> writeTaskQueue;

    /* access modifiers changed from: protected */
    public abstract Runnable createRegisterTask(AbstractNioChannel<?> abstractNioChannel, ChannelFuture channelFuture);

    /* access modifiers changed from: protected */
    public abstract boolean read(SelectionKey selectionKey);

    /* access modifiers changed from: protected */
    public abstract boolean scheduleWriteIfNecessary(AbstractNioChannel<?> abstractNioChannel);

    AbstractNioWorker(Executor executor2) {
        this(executor2, true);
    }

    public AbstractNioWorker(Executor executor2, boolean allowShutdownOnIdle2) {
        this.f121id = nextId.incrementAndGet();
        this.wakenUp = new AtomicBoolean();
        this.selectorGuard = new ReentrantReadWriteLock();
        this.startStopLock = new Object();
        this.registerTaskQueue = QueueFactory.createQueue(Runnable.class);
        this.writeTaskQueue = QueueFactory.createQueue(Runnable.class);
        this.eventQueue = QueueFactory.createQueue(Runnable.class);
        this.sendBufferPool = new SocketSendBufferPool();
        this.executor = executor2;
        this.allowShutdownOnIdle = allowShutdownOnIdle2;
    }

    /* access modifiers changed from: package-private */
    public void register(AbstractNioChannel<?> channel, ChannelFuture future) {
        Runnable registerTask = createRegisterTask(channel, future);
        Selector selector2 = start();
        boolean offer = this.registerTaskQueue.offer(registerTask);
        if (this.wakenUp.compareAndSet(false, true)) {
            selector2.wakeup();
        }
    }

    private Selector start() {
        synchronized (this.startStopLock) {
            if (!this.started) {
                try {
                    this.selector = Selector.open();
                    Executor executor2 = this.executor;
                    DeadLockProofWorker.start(executor2, new ThreadRenamingRunnable(this, "New I/O  worker #" + this.f121id));
                    if (1 == 0) {
                        this.selector.close();
                        this.selector = null;
                    }
                } catch (Throwable t) {
                    logger.warn("Failed to close a selector.", t);
                }
            }
            this.started = true;
        }
        return this.selector;
        this.selector = null;
        throw th;
    }

    /* JADX INFO: finally extract failed */
    public void run() {
        this.thread = Thread.currentThread();
        boolean shutdown = false;
        Selector selector2 = this.selector;
        while (true) {
            this.wakenUp.set(false);
            if (CONSTRAINT_LEVEL != 0) {
                this.selectorGuard.writeLock().lock();
                this.selectorGuard.writeLock().unlock();
            }
            try {
                SelectorUtil.select(selector2);
                if (this.wakenUp.get()) {
                    selector2.wakeup();
                }
                this.cancelledKeys = 0;
                processRegisterTaskQueue();
                processEventQueue();
                processWriteTaskQueue();
                processSelectedKeys(selector2.selectedKeys());
                if (selector2.keys().isEmpty()) {
                    if (!shutdown) {
                        if (!(this.executor instanceof ExecutorService) || !((ExecutorService) this.executor).isShutdown()) {
                            if (this.allowShutdownOnIdle) {
                                shutdown = true;
                            }
                        }
                    }
                    synchronized (this.startStopLock) {
                        if (!this.registerTaskQueue.isEmpty() || !selector2.keys().isEmpty()) {
                            shutdown = false;
                        } else {
                            this.started = false;
                            try {
                                selector2.close();
                                this.selector = null;
                                break;
                            } catch (IOException e) {
                                try {
                                    logger.warn("Failed to close a selector.", e);
                                    this.selector = null;
                                    break;
                                } catch (Throwable th) {
                                    this.selector = null;
                                    throw th;
                                }
                            }
                        }
                    }
                } else {
                    shutdown = false;
                }
            } catch (Throwable t) {
                logger.warn("Unexpected exception in the selector loop.", t);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e2) {
                }
            }
        }
    }

    public void executeInIoThread(Runnable task) {
        executeInIoThread(task, false);
    }

    public void executeInIoThread(Runnable task, boolean alwaysAsync) {
        Selector selector2;
        if (alwaysAsync || Thread.currentThread() != this.thread) {
            start();
            if (this.eventQueue.offer(task) && (selector2 = this.selector) != null) {
                selector2.wakeup();
                return;
            }
            return;
        }
        task.run();
    }

    private void processRegisterTaskQueue() throws IOException {
        while (true) {
            Runnable task = this.registerTaskQueue.poll();
            if (task != null) {
                task.run();
                cleanUpCancelledKeys();
            } else {
                return;
            }
        }
    }

    private void processWriteTaskQueue() throws IOException {
        while (true) {
            Runnable task = this.writeTaskQueue.poll();
            if (task != null) {
                task.run();
                cleanUpCancelledKeys();
            } else {
                return;
            }
        }
    }

    private void processEventQueue() throws IOException {
        while (true) {
            Runnable task = this.eventQueue.poll();
            if (task != null) {
                task.run();
                cleanUpCancelledKeys();
            } else {
                return;
            }
        }
    }

    private void processSelectedKeys(Set<SelectionKey> selectedKeys) throws IOException {
        Iterator<SelectionKey> i = selectedKeys.iterator();
        while (i.hasNext()) {
            SelectionKey k = i.next();
            i.remove();
            try {
                int readyOps = k.readyOps();
                if (((readyOps & 1) == 0 && readyOps != 0) || read(k)) {
                    if ((readyOps & 4) != 0) {
                        writeFromSelectorLoop(k);
                    }
                    if (cleanUpCancelledKeys()) {
                        return;
                    }
                }
            } catch (CancelledKeyException e) {
                close(k);
            }
        }
    }

    private boolean cleanUpCancelledKeys() throws IOException {
        if (this.cancelledKeys < 256) {
            return false;
        }
        this.cancelledKeys = 0;
        this.selector.selectNow();
        return true;
    }

    private void close(SelectionKey k) {
        AbstractNioChannel<?> ch = (AbstractNioChannel) k.attachment();
        close(ch, Channels.succeededFuture(ch));
    }

    /* access modifiers changed from: package-private */
    public void writeFromUserCode(AbstractNioChannel<?> channel) {
        if (!channel.isConnected()) {
            cleanUpWriteBuffer(channel);
        } else if (!scheduleWriteIfNecessary(channel) && !channel.writeSuspended && !channel.inWriteNowLoop) {
            write0(channel);
        }
    }

    /* access modifiers changed from: package-private */
    public void writeFromTaskLoop(AbstractNioChannel<?> ch) {
        if (!ch.writeSuspended) {
            write0(ch);
        }
    }

    /* access modifiers changed from: package-private */
    public void writeFromSelectorLoop(SelectionKey k) {
        AbstractNioChannel<?> ch = (AbstractNioChannel) k.attachment();
        ch.writeSuspended = false;
        write0(ch);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:106:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00cc, code lost:
        if (r6 == false) goto L_0x00d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00ce, code lost:
        org.jboss.netty.channel.Channels.fireWriteComplete((org.jboss.netty.channel.Channel) r2, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00d2, code lost:
        org.jboss.netty.channel.Channels.fireWriteCompleteLater(r2, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00e2, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
        r15.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00ea, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00eb, code lost:
        r4 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
        org.jboss.netty.channel.Channels.fireExceptionCaught((org.jboss.netty.channel.Channel) r2, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:?, code lost:
        org.jboss.netty.channel.Channels.fireExceptionCaughtLater((org.jboss.netty.channel.Channel) r2, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0109, code lost:
        r3 = false;
        r18 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:?, code lost:
        close(r2, org.jboss.netty.channel.Channels.succeededFuture(r29));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0113, code lost:
        r4 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0116, code lost:
        r18 = r0;
        r3 = r16;
        r4 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x012a, code lost:
        r16 = r3;
        r3 = r25;
        r4 = r22;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00e2 A[ExcHandler: all (th java.lang.Throwable), PHI: r7 r15 
      PHI: (r7v6 'writtenBytes' long) = (r7v7 'writtenBytes' long), (r7v7 'writtenBytes' long), (r7v7 'writtenBytes' long), (r7v1 'writtenBytes' long) binds: [B:32:0x008b, B:35:0x0091, B:36:?, B:21:0x0063] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r15v4 'buf' org.jboss.netty.channel.socket.nio.SocketSendBufferPool$SendBuffer) = (r15v1 'buf' org.jboss.netty.channel.socket.nio.SocketSendBufferPool$SendBuffer), (r15v1 'buf' org.jboss.netty.channel.socket.nio.SocketSendBufferPool$SendBuffer), (r15v5 'buf' org.jboss.netty.channel.socket.nio.SocketSendBufferPool$SendBuffer), (r15v1 'buf' org.jboss.netty.channel.socket.nio.SocketSendBufferPool$SendBuffer) binds: [B:32:0x008b, B:35:0x0091, B:36:?, B:21:0x0063] A[DONT_GENERATE, DONT_INLINE], Splitter:B:21:0x0063] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00e6 A[SYNTHETIC, Splitter:B:63:0x00e6] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00fc A[SYNTHETIC, Splitter:B:71:0x00fc] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0100 A[SYNTHETIC, Splitter:B:73:0x0100] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0116  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write0(org.jboss.netty.channel.socket.nio.AbstractNioChannel<?> r29) {
        /*
            r28 = this;
            r1 = r28
            r2 = r29
            r3 = 1
            r4 = 0
            r5 = 0
            boolean r6 = isIoThread(r29)
            r7 = 0
            org.jboss.netty.channel.socket.nio.SocketSendBufferPool r9 = r1.sendBufferPool
            C r0 = r2.channel
            r10 = r0
            java.nio.channels.WritableByteChannel r10 = (java.nio.channels.WritableByteChannel) r10
            java.util.Queue<org.jboss.netty.channel.MessageEvent> r11 = r2.writeBufferQueue
            org.jboss.netty.channel.socket.nio.NioChannelConfig r0 = r29.getConfig()
            int r12 = r0.getWriteSpinCount()
            java.lang.Object r13 = r2.writeLock
            monitor-enter(r13)
            r14 = 1
            r2.inWriteNowLoop = r14     // Catch:{ all -> 0x013b }
        L_0x0024:
            org.jboss.netty.channel.MessageEvent r0 = r2.currentWriteEvent     // Catch:{ all -> 0x0135 }
            r15 = 0
            if (r0 != 0) goto L_0x004b
            java.lang.Object r16 = r11.poll()     // Catch:{ all -> 0x013b }
            r14 = r16
            org.jboss.netty.channel.MessageEvent r14 = (org.jboss.netty.channel.MessageEvent) r14     // Catch:{ all -> 0x013b }
            r0 = r14
            r2.currentWriteEvent = r14     // Catch:{ all -> 0x013b }
            if (r14 != 0) goto L_0x003b
            r5 = 1
            r2.writeSuspended = r15     // Catch:{ all -> 0x013b }
            goto L_0x00bb
        L_0x003b:
            java.lang.Object r14 = r0.getMessage()     // Catch:{ all -> 0x013b }
            org.jboss.netty.channel.socket.nio.SocketSendBufferPool$SendBuffer r14 = r9.acquire((java.lang.Object) r14)     // Catch:{ all -> 0x013b }
            r16 = r14
            r2.currentWriteBuffer = r14     // Catch:{ all -> 0x013b }
            r14 = r0
            r15 = r16
            goto L_0x0052
        L_0x004b:
            org.jboss.netty.channel.socket.nio.SocketSendBufferPool$SendBuffer r14 = r2.currentWriteBuffer     // Catch:{ all -> 0x0135 }
            r16 = r14
            r14 = r0
            r15 = r16
        L_0x0052:
            org.jboss.netty.channel.ChannelFuture r16 = r14.getFuture()     // Catch:{ all -> 0x0135 }
            r25 = r16
            r18 = 0
            r16 = r12
        L_0x005c:
            r20 = 0
            r22 = r4
            r4 = 0
            if (r16 <= 0) goto L_0x0089
            long r23 = r15.transferTo(r10)     // Catch:{ AsynchronousCloseException -> 0x0080, all -> 0x00e2 }
            r18 = r23
            int r23 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r23 == 0) goto L_0x0072
            long r7 = r7 + r18
            r26 = r18
            goto L_0x008b
        L_0x0072:
            boolean r23 = r15.finished()     // Catch:{ AsynchronousCloseException -> 0x0080, all -> 0x00e2 }
            if (r23 == 0) goto L_0x007b
            r26 = r18
            goto L_0x008b
        L_0x007b:
            int r16 = r16 + -1
            r4 = r22
            goto L_0x005c
        L_0x0080:
            r0 = move-exception
            r16 = r3
            r4 = r22
            r3 = r25
            goto L_0x0130
        L_0x0089:
            r26 = r18
        L_0x008b:
            boolean r16 = r15.finished()     // Catch:{ AsynchronousCloseException -> 0x0129, all -> 0x00e2 }
            if (r16 == 0) goto L_0x00a1
            r15.release()     // Catch:{ AsynchronousCloseException -> 0x0080, all -> 0x00e2 }
            r2.currentWriteEvent = r4     // Catch:{ AsynchronousCloseException -> 0x0080, all -> 0x00e2 }
            r2.currentWriteBuffer = r4     // Catch:{ AsynchronousCloseException -> 0x0080, all -> 0x00e2 }
            r14 = 0
            r15 = 0
            r25.setSuccess()     // Catch:{ AsynchronousCloseException -> 0x0080, all -> 0x00e2 }
            r4 = r22
            goto L_0x0132
        L_0x00a1:
            r16 = 1
            r4 = 1
            r2.writeSuspended = r4     // Catch:{ AsynchronousCloseException -> 0x00da, all -> 0x00d6 }
            int r17 = (r26 > r20 ? 1 : (r26 == r20 ? 0 : -1))
            if (r17 <= 0) goto L_0x00b9
            long r21 = r15.writtenBytes()     // Catch:{ AsynchronousCloseException -> 0x00da, all -> 0x00d6 }
            long r23 = r15.totalBytes()     // Catch:{ AsynchronousCloseException -> 0x00da, all -> 0x00d6 }
            r18 = r25
            r19 = r26
            r18.setProgress(r19, r21, r23)     // Catch:{ AsynchronousCloseException -> 0x00da, all -> 0x00d6 }
        L_0x00b9:
            r4 = r16
        L_0x00bb:
            r0 = 0
            r2.inWriteNowLoop = r0     // Catch:{ all -> 0x013b }
            if (r3 == 0) goto L_0x00cb
            if (r4 == 0) goto L_0x00c6
            r28.setOpWrite(r29)     // Catch:{ all -> 0x013b }
            goto L_0x00cb
        L_0x00c6:
            if (r5 == 0) goto L_0x00cb
            r28.clearOpWrite(r29)     // Catch:{ all -> 0x013b }
        L_0x00cb:
            monitor-exit(r13)     // Catch:{ all -> 0x013b }
            if (r6 == 0) goto L_0x00d2
            org.jboss.netty.channel.Channels.fireWriteComplete((org.jboss.netty.channel.Channel) r2, (long) r7)
            goto L_0x00d5
        L_0x00d2:
            org.jboss.netty.channel.Channels.fireWriteCompleteLater(r2, r7)
        L_0x00d5:
            return
        L_0x00d6:
            r0 = move-exception
            r22 = r16
            goto L_0x00e4
        L_0x00da:
            r0 = move-exception
            r4 = r16
            r16 = r3
            r3 = r25
            goto L_0x0130
        L_0x00e2:
            r0 = move-exception
            r4 = 1
        L_0x00e4:
            if (r15 == 0) goto L_0x00ee
            r15.release()     // Catch:{ all -> 0x00ea }
            goto L_0x00ee
        L_0x00ea:
            r0 = move-exception
            r4 = r22
            goto L_0x013c
        L_0x00ee:
            r4 = 0
            r2.currentWriteEvent = r4     // Catch:{ all -> 0x0123 }
            r2.currentWriteBuffer = r4     // Catch:{ all -> 0x0123 }
            r4 = 0
            r14 = 0
            r15 = r25
            r15.setFailure(r0)     // Catch:{ all -> 0x0123 }
            if (r6 == 0) goto L_0x0100
            org.jboss.netty.channel.Channels.fireExceptionCaught((org.jboss.netty.channel.Channel) r2, (java.lang.Throwable) r0)     // Catch:{ all -> 0x00ea }
            goto L_0x0103
        L_0x0100:
            org.jboss.netty.channel.Channels.fireExceptionCaughtLater((org.jboss.netty.channel.Channel) r2, (java.lang.Throwable) r0)     // Catch:{ all -> 0x0123 }
        L_0x0103:
            r16 = r3
            boolean r3 = r0 instanceof java.io.IOException     // Catch:{ all -> 0x011d }
            if (r3 == 0) goto L_0x0116
            r3 = 0
            r18 = r0
            org.jboss.netty.channel.ChannelFuture r0 = org.jboss.netty.channel.Channels.succeededFuture(r29)     // Catch:{ all -> 0x00ea }
            r1.close(r2, r0)     // Catch:{ all -> 0x00ea }
            r4 = r22
            goto L_0x0132
        L_0x0116:
            r18 = r0
            r3 = r16
            r4 = r22
            goto L_0x0132
        L_0x011d:
            r0 = move-exception
            r3 = r16
            r4 = r22
            goto L_0x013c
        L_0x0123:
            r0 = move-exception
            r16 = r3
            r4 = r22
            goto L_0x013c
        L_0x0129:
            r0 = move-exception
            r16 = r3
            r3 = r25
            r4 = r22
        L_0x0130:
            r3 = r16
        L_0x0132:
            r14 = 1
            goto L_0x0024
        L_0x0135:
            r0 = move-exception
            r16 = r3
            r22 = r4
            goto L_0x013c
        L_0x013b:
            r0 = move-exception
        L_0x013c:
            monitor-exit(r13)     // Catch:{ all -> 0x013b }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.channel.socket.nio.AbstractNioWorker.write0(org.jboss.netty.channel.socket.nio.AbstractNioChannel):void");
    }

    static boolean isIoThread(AbstractNioChannel<?> channel) {
        return Thread.currentThread() == channel.worker.thread;
    }

    /* access modifiers changed from: protected */
    public void setOpWrite(AbstractNioChannel<?> channel) {
        SelectionKey key = channel.channel.keyFor(this.selector);
        if (key != null) {
            if (!key.isValid()) {
                close(key);
                return;
            }
            synchronized (channel.interestOpsLock) {
                int interestOps = channel.getRawInterestOps();
                if ((interestOps & 4) == 0) {
                    int interestOps2 = interestOps | 4;
                    key.interestOps(interestOps2);
                    channel.setRawInterestOpsNow(interestOps2);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void clearOpWrite(AbstractNioChannel<?> channel) {
        SelectionKey key = channel.channel.keyFor(this.selector);
        if (key != null) {
            if (!key.isValid()) {
                close(key);
                return;
            }
            synchronized (channel.interestOpsLock) {
                int interestOps = channel.getRawInterestOps();
                if ((interestOps & 4) != 0) {
                    int interestOps2 = interestOps & -5;
                    key.interestOps(interestOps2);
                    channel.setRawInterestOpsNow(interestOps2);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void close(AbstractNioChannel<?> channel, ChannelFuture future) {
        boolean connected = channel.isConnected();
        boolean bound = channel.isBound();
        boolean iothread = isIoThread(channel);
        try {
            channel.channel.close();
            this.cancelledKeys++;
            if (channel.setClosed()) {
                future.setSuccess();
                if (connected) {
                    if (iothread) {
                        Channels.fireChannelDisconnected((Channel) channel);
                    } else {
                        Channels.fireChannelDisconnectedLater(channel);
                    }
                }
                if (bound) {
                    if (iothread) {
                        Channels.fireChannelUnbound((Channel) channel);
                    } else {
                        Channels.fireChannelUnboundLater(channel);
                    }
                }
                cleanUpWriteBuffer(channel);
                if (iothread) {
                    Channels.fireChannelClosed((Channel) channel);
                } else {
                    Channels.fireChannelClosedLater(channel);
                }
            } else {
                future.setSuccess();
            }
        } catch (Throwable t) {
            future.setFailure(t);
            if (iothread) {
                Channels.fireExceptionCaught((Channel) channel, t);
            } else {
                Channels.fireExceptionCaughtLater((Channel) channel, t);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void cleanUpWriteBuffer(AbstractNioChannel<?> channel) {
        Exception cause;
        Exception cause2 = null;
        boolean fireExceptionCaught = false;
        synchronized (channel.writeLock) {
            MessageEvent evt = channel.currentWriteEvent;
            if (evt != null) {
                if (channel.isOpen()) {
                    cause2 = new NotYetConnectedException();
                } else {
                    cause2 = new ClosedChannelException();
                }
                ChannelFuture future = evt.getFuture();
                channel.currentWriteBuffer.release();
                channel.currentWriteBuffer = null;
                channel.currentWriteEvent = null;
                future.setFailure(cause2);
                fireExceptionCaught = true;
            }
            Queue<MessageEvent> writeBuffer = channel.writeBufferQueue;
            while (true) {
                MessageEvent evt2 = writeBuffer.poll();
                if (evt2 != null) {
                    if (cause2 == null) {
                        if (channel.isOpen()) {
                            cause = new NotYetConnectedException();
                        } else {
                            cause = new ClosedChannelException();
                        }
                        fireExceptionCaught = true;
                    }
                    evt2.getFuture().setFailure(cause2);
                }
            }
        }
        if (!fireExceptionCaught) {
            return;
        }
        if (isIoThread(channel)) {
            Channels.fireExceptionCaught((Channel) channel, (Throwable) cause2);
        } else {
            Channels.fireExceptionCaughtLater((Channel) channel, (Throwable) cause2);
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        r11.setSuccess();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0098, code lost:
        if (r0 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x009a, code lost:
        if (r1 == false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x009c, code lost:
        org.jboss.netty.channel.Channels.fireChannelInterestChanged((org.jboss.netty.channel.Channel) r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00a0, code lost:
        org.jboss.netty.channel.Channels.fireChannelInterestChangedLater(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00bd, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setInterestOps(org.jboss.netty.channel.socket.nio.AbstractNioChannel<?> r10, org.jboss.netty.channel.ChannelFuture r11, int r12) {
        /*
            r9 = this;
            r0 = 0
            boolean r1 = isIoThread(r10)
            java.lang.Object r2 = r10.interestOpsLock     // Catch:{ CancelledKeyException -> 0x00cf, all -> 0x00c1 }
            monitor-enter(r2)     // Catch:{ CancelledKeyException -> 0x00cf, all -> 0x00c1 }
            java.nio.channels.Selector r3 = r9.selector     // Catch:{ all -> 0x00be }
            C r4 = r10.channel     // Catch:{ all -> 0x00be }
            java.nio.channels.SelectionKey r4 = r4.keyFor(r3)     // Catch:{ all -> 0x00be }
            r12 = r12 & -5
            int r5 = r10.getRawInterestOps()     // Catch:{ all -> 0x00be }
            r5 = r5 & 4
            r12 = r12 | r5
            if (r4 == 0) goto L_0x00a4
            if (r3 != 0) goto L_0x001f
            goto L_0x00a4
        L_0x001f:
            int r5 = CONSTRAINT_LEVEL     // Catch:{ all -> 0x00be }
            r6 = 0
            r7 = 1
            if (r5 == 0) goto L_0x0072
            if (r5 == r7) goto L_0x0031
            r8 = 2
            if (r5 != r8) goto L_0x002b
            goto L_0x0031
        L_0x002b:
            java.lang.Error r5 = new java.lang.Error     // Catch:{ all -> 0x00be }
            r5.<init>()     // Catch:{ all -> 0x00be }
            throw r5     // Catch:{ all -> 0x00be }
        L_0x0031:
            int r5 = r10.getRawInterestOps()     // Catch:{ all -> 0x00be }
            if (r5 == r12) goto L_0x008f
            java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x00be }
            java.lang.Thread r8 = r9.thread     // Catch:{ all -> 0x00be }
            if (r5 != r8) goto L_0x0044
            r4.interestOps(r12)     // Catch:{ all -> 0x00be }
            r0 = 1
            goto L_0x008f
        L_0x0044:
            java.util.concurrent.locks.ReadWriteLock r5 = r9.selectorGuard     // Catch:{ all -> 0x00be }
            java.util.concurrent.locks.Lock r5 = r5.readLock()     // Catch:{ all -> 0x00be }
            r5.lock()     // Catch:{ all -> 0x00be }
            java.util.concurrent.atomic.AtomicBoolean r5 = r9.wakenUp     // Catch:{ all -> 0x0067 }
            boolean r5 = r5.compareAndSet(r6, r7)     // Catch:{ all -> 0x0067 }
            if (r5 == 0) goto L_0x0058
            r3.wakeup()     // Catch:{ all -> 0x0067 }
        L_0x0058:
            r4.interestOps(r12)     // Catch:{ all -> 0x0067 }
            r0 = 1
            java.util.concurrent.locks.ReadWriteLock r5 = r9.selectorGuard     // Catch:{ all -> 0x00be }
            java.util.concurrent.locks.Lock r5 = r5.readLock()     // Catch:{ all -> 0x00be }
            r5.unlock()     // Catch:{ all -> 0x00be }
            goto L_0x008f
        L_0x0067:
            r5 = move-exception
            java.util.concurrent.locks.ReadWriteLock r6 = r9.selectorGuard     // Catch:{ all -> 0x00be }
            java.util.concurrent.locks.Lock r6 = r6.readLock()     // Catch:{ all -> 0x00be }
            r6.unlock()     // Catch:{ all -> 0x00be }
            throw r5     // Catch:{ all -> 0x00be }
        L_0x0072:
            int r5 = r10.getRawInterestOps()     // Catch:{ all -> 0x00be }
            if (r5 == r12) goto L_0x008f
            r4.interestOps(r12)     // Catch:{ all -> 0x00be }
            java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x00be }
            java.lang.Thread r8 = r9.thread     // Catch:{ all -> 0x00be }
            if (r5 == r8) goto L_0x008e
            java.util.concurrent.atomic.AtomicBoolean r5 = r9.wakenUp     // Catch:{ all -> 0x00be }
            boolean r5 = r5.compareAndSet(r6, r7)     // Catch:{ all -> 0x00be }
            if (r5 == 0) goto L_0x008e
            r3.wakeup()     // Catch:{ all -> 0x00be }
        L_0x008e:
            r0 = 1
        L_0x008f:
            if (r0 == 0) goto L_0x0094
            r10.setRawInterestOpsNow(r12)     // Catch:{ all -> 0x00be }
        L_0x0094:
            monitor-exit(r2)     // Catch:{ all -> 0x00be }
            r11.setSuccess()     // Catch:{ CancelledKeyException -> 0x00cf, all -> 0x00c1 }
            if (r0 == 0) goto L_0x00e1
            if (r1 == 0) goto L_0x00a0
            org.jboss.netty.channel.Channels.fireChannelInterestChanged((org.jboss.netty.channel.Channel) r10)     // Catch:{ CancelledKeyException -> 0x00cf, all -> 0x00c1 }
            goto L_0x00e1
        L_0x00a0:
            org.jboss.netty.channel.Channels.fireChannelInterestChangedLater(r10)     // Catch:{ CancelledKeyException -> 0x00cf, all -> 0x00c1 }
            goto L_0x00e1
        L_0x00a4:
            int r5 = r10.getRawInterestOps()     // Catch:{ all -> 0x00be }
            if (r5 == r12) goto L_0x00ab
            r0 = 1
        L_0x00ab:
            r10.setRawInterestOpsNow(r12)     // Catch:{ all -> 0x00be }
            r11.setSuccess()     // Catch:{ all -> 0x00be }
            if (r0 == 0) goto L_0x00bc
            if (r1 == 0) goto L_0x00b9
            org.jboss.netty.channel.Channels.fireChannelInterestChanged((org.jboss.netty.channel.Channel) r10)     // Catch:{ all -> 0x00be }
            goto L_0x00bc
        L_0x00b9:
            org.jboss.netty.channel.Channels.fireChannelInterestChangedLater(r10)     // Catch:{ all -> 0x00be }
        L_0x00bc:
            monitor-exit(r2)     // Catch:{ all -> 0x00be }
            return
        L_0x00be:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00be }
            throw r3     // Catch:{ CancelledKeyException -> 0x00cf, all -> 0x00c1 }
        L_0x00c1:
            r2 = move-exception
            r11.setFailure(r2)
            if (r1 == 0) goto L_0x00cb
            org.jboss.netty.channel.Channels.fireExceptionCaught((org.jboss.netty.channel.Channel) r10, (java.lang.Throwable) r2)
            goto L_0x00e2
        L_0x00cb:
            org.jboss.netty.channel.Channels.fireExceptionCaughtLater((org.jboss.netty.channel.Channel) r10, (java.lang.Throwable) r2)
            goto L_0x00e2
        L_0x00cf:
            r2 = move-exception
            java.nio.channels.ClosedChannelException r3 = new java.nio.channels.ClosedChannelException
            r3.<init>()
            r11.setFailure(r3)
            if (r1 == 0) goto L_0x00de
            org.jboss.netty.channel.Channels.fireExceptionCaught((org.jboss.netty.channel.Channel) r10, (java.lang.Throwable) r3)
            goto L_0x00e1
        L_0x00de:
            org.jboss.netty.channel.Channels.fireExceptionCaughtLater((org.jboss.netty.channel.Channel) r10, (java.lang.Throwable) r3)
        L_0x00e1:
        L_0x00e2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.channel.socket.nio.AbstractNioWorker.setInterestOps(org.jboss.netty.channel.socket.nio.AbstractNioChannel, org.jboss.netty.channel.ChannelFuture, int):void");
    }
}
