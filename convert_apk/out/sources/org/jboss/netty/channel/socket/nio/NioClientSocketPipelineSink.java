package org.jboss.netty.channel.socket.nio;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.internal.QueueFactory;

class NioClientSocketPipelineSink extends AbstractNioChannelSink {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) NioClientSocketPipelineSink.class);
    private static final AtomicInteger nextId = new AtomicInteger();
    final Executor bossExecutor;
    private final AtomicInteger bossIndex = new AtomicInteger();
    private final Boss[] bosses;

    /* renamed from: id */
    final int f122id = nextId.incrementAndGet();
    private final WorkerPool<NioWorker> workerPool;

    NioClientSocketPipelineSink(Executor bossExecutor2, int bossCount, WorkerPool<NioWorker> workerPool2) {
        this.bossExecutor = bossExecutor2;
        this.bosses = new Boss[bossCount];
        int i = 0;
        while (true) {
            Boss[] bossArr = this.bosses;
            if (i < bossArr.length) {
                bossArr[i] = new Boss(i);
                i++;
            } else {
                this.workerPool = workerPool2;
                return;
            }
        }
    }

    public void eventSunk(ChannelPipeline pipeline, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            ChannelStateEvent event = (ChannelStateEvent) e;
            NioClientSocketChannel channel = (NioClientSocketChannel) event.getChannel();
            ChannelFuture future = event.getFuture();
            ChannelState state = event.getState();
            Object value = event.getValue();
            int i = C08622.$SwitchMap$org$jboss$netty$channel$ChannelState[state.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i == 4) {
                            channel.worker.setInterestOps(channel, future, ((Integer) value).intValue());
                        }
                    } else if (value != null) {
                        connect(channel, future, (SocketAddress) value);
                    } else {
                        channel.worker.close(channel, future);
                    }
                } else if (value != null) {
                    bind(channel, future, (SocketAddress) value);
                } else {
                    channel.worker.close(channel, future);
                }
            } else if (Boolean.FALSE.equals(value)) {
                channel.worker.close(channel, future);
            }
        } else if (e instanceof MessageEvent) {
            MessageEvent event2 = (MessageEvent) e;
            NioSocketChannel channel2 = (NioSocketChannel) event2.getChannel();
            boolean offer = channel2.writeBufferQueue.offer(event2);
            channel2.worker.writeFromUserCode(channel2);
        }
    }

    /* renamed from: org.jboss.netty.channel.socket.nio.NioClientSocketPipelineSink$2 */
    static /* synthetic */ class C08622 {
        static final /* synthetic */ int[] $SwitchMap$org$jboss$netty$channel$ChannelState;

        static {
            int[] iArr = new int[ChannelState.values().length];
            $SwitchMap$org$jboss$netty$channel$ChannelState = iArr;
            try {
                iArr[ChannelState.OPEN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$jboss$netty$channel$ChannelState[ChannelState.BOUND.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$jboss$netty$channel$ChannelState[ChannelState.CONNECTED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$jboss$netty$channel$ChannelState[ChannelState.INTEREST_OPS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private static void bind(NioClientSocketChannel channel, ChannelFuture future, SocketAddress localAddress) {
        try {
            ((SocketChannel) channel.channel).socket().bind(localAddress);
            channel.boundManually = true;
            channel.setBound();
            future.setSuccess();
            Channels.fireChannelBound((Channel) channel, (SocketAddress) channel.getLocalAddress());
        } catch (Throwable t) {
            future.setFailure(t);
            Channels.fireExceptionCaught((Channel) channel, t);
        }
    }

    private void connect(NioClientSocketChannel channel, final ChannelFuture cf, SocketAddress remoteAddress) {
        try {
            if (((SocketChannel) channel.channel).connect(remoteAddress)) {
                channel.worker.register(channel, cf);
                return;
            }
            channel.getCloseFuture().addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture f) throws Exception {
                    if (!cf.isDone()) {
                        cf.setFailure(new ClosedChannelException());
                    }
                }
            });
            cf.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            channel.connectFuture = cf;
            nextBoss().register(channel);
        } catch (Throwable t) {
            cf.setFailure(t);
            Channels.fireExceptionCaught((Channel) channel, t);
            channel.worker.close(channel, Channels.succeededFuture(channel));
        }
    }

    /* access modifiers changed from: package-private */
    public Boss nextBoss() {
        return this.bosses[Math.abs(this.bossIndex.getAndIncrement() % this.bosses.length)];
    }

    /* access modifiers changed from: package-private */
    public NioWorker nextWorker() {
        return this.workerPool.nextWorker();
    }

    private final class Boss implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Queue<Runnable> registerTaskQueue = QueueFactory.createQueue(Runnable.class);
        volatile Selector selector;
        private final Object startStopLock = new Object();
        private boolean started;
        private final int subId;
        private final AtomicBoolean wakenUp = new AtomicBoolean();

        static {
            Class<NioClientSocketPipelineSink> cls = NioClientSocketPipelineSink.class;
        }

        Boss(int subId2) {
            this.subId = subId2;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0076, code lost:
            r9.selector = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x009e, code lost:
            if (r9.wakenUp.compareAndSet(false, true) == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x00a0, code lost:
            r4.wakeup();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void register(org.jboss.netty.channel.socket.nio.NioClientSocketChannel r10) {
            /*
                r9 = this;
                org.jboss.netty.channel.socket.nio.NioClientSocketPipelineSink$RegisterTask r0 = new org.jboss.netty.channel.socket.nio.NioClientSocketPipelineSink$RegisterTask
                r0.<init>(r9, r10)
                java.lang.Object r1 = r9.startStopLock
                monitor-enter(r1)
                r2 = 0
                boolean r3 = r9.started     // Catch:{ all -> 0x00a4 }
                if (r3 != 0) goto L_0x0088
                java.nio.channels.Selector r3 = java.nio.channels.Selector.open()     // Catch:{ all -> 0x007d }
                r4 = r3
                r9.selector = r3     // Catch:{ all -> 0x007b }
                r3 = 0
                org.jboss.netty.channel.socket.nio.NioClientSocketPipelineSink r5 = org.jboss.netty.channel.socket.nio.NioClientSocketPipelineSink.this     // Catch:{ all -> 0x005f }
                java.util.concurrent.Executor r5 = r5.bossExecutor     // Catch:{ all -> 0x005f }
                org.jboss.netty.util.ThreadRenamingRunnable r6 = new org.jboss.netty.util.ThreadRenamingRunnable     // Catch:{ all -> 0x005f }
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x005f }
                r7.<init>()     // Catch:{ all -> 0x005f }
                java.lang.String r8 = "New I/O client boss #"
                r7.append(r8)     // Catch:{ all -> 0x005f }
                org.jboss.netty.channel.socket.nio.NioClientSocketPipelineSink r8 = org.jboss.netty.channel.socket.nio.NioClientSocketPipelineSink.this     // Catch:{ all -> 0x005f }
                int r8 = r8.f122id     // Catch:{ all -> 0x005f }
                r7.append(r8)     // Catch:{ all -> 0x005f }
                r8 = 45
                r7.append(r8)     // Catch:{ all -> 0x005f }
                int r8 = r9.subId     // Catch:{ all -> 0x005f }
                r7.append(r8)     // Catch:{ all -> 0x005f }
                java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x005f }
                r6.<init>(r9, r7)     // Catch:{ all -> 0x005f }
                org.jboss.netty.util.internal.DeadLockProofWorker.start(r5, r6)     // Catch:{ all -> 0x005f }
                r3 = 1
                if (r3 != 0) goto L_0x005c
                r4.close()     // Catch:{ all -> 0x0049 }
                goto L_0x0059
            L_0x0049:
                r5 = move-exception
                org.jboss.netty.logging.InternalLogger r6 = org.jboss.netty.channel.socket.nio.NioClientSocketPipelineSink.logger     // Catch:{ all -> 0x00a9 }
                boolean r6 = r6.isWarnEnabled()     // Catch:{ all -> 0x00a9 }
                if (r6 == 0) goto L_0x0059
                org.jboss.netty.logging.InternalLogger r6 = org.jboss.netty.channel.socket.nio.NioClientSocketPipelineSink.logger     // Catch:{ all -> 0x00a9 }
                java.lang.String r7 = "Failed to close a selector."
                r6.warn(r7, r5)     // Catch:{ all -> 0x00a9 }
            L_0x0059:
                r4 = r2
                r9.selector = r2     // Catch:{ all -> 0x00a9 }
            L_0x005c:
                goto L_0x008b
            L_0x005f:
                r5 = move-exception
                if (r3 != 0) goto L_0x0079
                r4.close()     // Catch:{ all -> 0x0066 }
                goto L_0x0076
            L_0x0066:
                r6 = move-exception
                org.jboss.netty.logging.InternalLogger r7 = org.jboss.netty.channel.socket.nio.NioClientSocketPipelineSink.logger     // Catch:{ all -> 0x00a9 }
                boolean r7 = r7.isWarnEnabled()     // Catch:{ all -> 0x00a9 }
                if (r7 == 0) goto L_0x0076
                org.jboss.netty.logging.InternalLogger r7 = org.jboss.netty.channel.socket.nio.NioClientSocketPipelineSink.logger     // Catch:{ all -> 0x00a9 }
                java.lang.String r8 = "Failed to close a selector."
                r7.warn(r8, r6)     // Catch:{ all -> 0x00a9 }
            L_0x0076:
                r4 = r2
                r9.selector = r2     // Catch:{ all -> 0x00a9 }
            L_0x0079:
                throw r5     // Catch:{ all -> 0x00a9 }
            L_0x007b:
                r2 = move-exception
                goto L_0x0080
            L_0x007d:
                r3 = move-exception
                r4 = r2
                r2 = r3
            L_0x0080:
                org.jboss.netty.channel.ChannelException r3 = new org.jboss.netty.channel.ChannelException     // Catch:{ all -> 0x00a9 }
                java.lang.String r5 = "Failed to create a selector."
                r3.<init>(r5, r2)     // Catch:{ all -> 0x00a9 }
                throw r3     // Catch:{ all -> 0x00a9 }
            L_0x0088:
                java.nio.channels.Selector r2 = r9.selector     // Catch:{ all -> 0x00a4 }
                r4 = r2
            L_0x008b:
                r2 = 1
                r9.started = r2     // Catch:{ all -> 0x00a9 }
                java.util.Queue<java.lang.Runnable> r3 = r9.registerTaskQueue     // Catch:{ all -> 0x00a9 }
                boolean r3 = r3.offer(r0)     // Catch:{ all -> 0x00a9 }
                monitor-exit(r1)     // Catch:{ all -> 0x00a9 }
                java.util.concurrent.atomic.AtomicBoolean r1 = r9.wakenUp
                r3 = 0
                boolean r1 = r1.compareAndSet(r3, r2)
                if (r1 == 0) goto L_0x00a3
                r4.wakeup()
            L_0x00a3:
                return
            L_0x00a4:
                r3 = move-exception
                r4 = r2
                r2 = r3
            L_0x00a7:
                monitor-exit(r1)     // Catch:{ all -> 0x00a9 }
                throw r2
            L_0x00a9:
                r2 = move-exception
                goto L_0x00a7
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.channel.socket.nio.NioClientSocketPipelineSink.Boss.register(org.jboss.netty.channel.socket.nio.NioClientSocketChannel):void");
        }

        /* JADX INFO: finally extract failed */
        public void run() {
            boolean shutdown = false;
            Selector selector2 = this.selector;
            long lastConnectTimeoutCheckTimeNanos = System.nanoTime();
            while (true) {
                this.wakenUp.set(false);
                try {
                    int selectedKeyCount = selector2.select(10);
                    if (this.wakenUp.get()) {
                        selector2.wakeup();
                    }
                    processRegisterTaskQueue();
                    if (selectedKeyCount > 0) {
                        processSelectedKeys(selector2.selectedKeys());
                    }
                    long currentTimeNanos = System.nanoTime();
                    if (currentTimeNanos - lastConnectTimeoutCheckTimeNanos >= 10000000) {
                        lastConnectTimeoutCheckTimeNanos = currentTimeNanos;
                        processConnectTimeout(selector2.keys(), currentTimeNanos);
                    }
                    if (selector2.keys().isEmpty()) {
                        if (!shutdown) {
                            if (!(NioClientSocketPipelineSink.this.bossExecutor instanceof ExecutorService) || !((ExecutorService) NioClientSocketPipelineSink.this.bossExecutor).isShutdown()) {
                                shutdown = true;
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
                                        if (NioClientSocketPipelineSink.logger.isWarnEnabled()) {
                                            NioClientSocketPipelineSink.logger.warn("Failed to close a selector.", e);
                                        }
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
                    if (NioClientSocketPipelineSink.logger.isWarnEnabled()) {
                        NioClientSocketPipelineSink.logger.warn("Unexpected exception in the selector loop.", t);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e2) {
                    }
                }
            }
        }

        private void processRegisterTaskQueue() {
            while (true) {
                Runnable task = this.registerTaskQueue.poll();
                if (task != null) {
                    task.run();
                } else {
                    return;
                }
            }
        }

        private void processSelectedKeys(Set<SelectionKey> selectedKeys) {
            Iterator<SelectionKey> i = selectedKeys.iterator();
            while (i.hasNext()) {
                SelectionKey k = i.next();
                i.remove();
                if (!k.isValid()) {
                    close(k);
                } else if (k.isConnectable()) {
                    connect(k);
                }
            }
        }

        private void processConnectTimeout(Set<SelectionKey> keys, long currentTimeNanos) {
            ConnectException cause = null;
            for (SelectionKey k : keys) {
                if (k.isValid()) {
                    NioClientSocketChannel ch = (NioClientSocketChannel) k.attachment();
                    if (ch.connectDeadlineNanos > 0 && currentTimeNanos >= ch.connectDeadlineNanos) {
                        if (cause == null) {
                            cause = new ConnectException("connection timed out");
                        }
                        ch.connectFuture.setFailure(cause);
                        Channels.fireExceptionCaught((Channel) ch, (Throwable) cause);
                        ch.worker.close(ch, Channels.succeededFuture(ch));
                    }
                }
            }
        }

        private void connect(SelectionKey k) {
            NioClientSocketChannel ch = (NioClientSocketChannel) k.attachment();
            try {
                if (((SocketChannel) ch.channel).finishConnect()) {
                    k.cancel();
                    ch.worker.register(ch, ch.connectFuture);
                }
            } catch (Throwable t) {
                ch.connectFuture.setFailure(t);
                Channels.fireExceptionCaught((Channel) ch, t);
                k.cancel();
                ch.worker.close(ch, Channels.succeededFuture(ch));
            }
        }

        private void close(SelectionKey k) {
            NioClientSocketChannel ch = (NioClientSocketChannel) k.attachment();
            ch.worker.close(ch, Channels.succeededFuture(ch));
        }
    }

    private static final class RegisterTask implements Runnable {
        private final Boss boss;
        private final NioClientSocketChannel channel;

        RegisterTask(Boss boss2, NioClientSocketChannel channel2) {
            this.boss = boss2;
            this.channel = channel2;
        }

        public void run() {
            try {
                ((SocketChannel) this.channel.channel).register(this.boss.selector, 8, this.channel);
            } catch (ClosedChannelException e) {
                AbstractNioWorker abstractNioWorker = this.channel.worker;
                NioClientSocketChannel nioClientSocketChannel = this.channel;
                abstractNioWorker.close(nioClientSocketChannel, Channels.succeededFuture(nioClientSocketChannel));
            }
            int connectTimeout = this.channel.getConfig().getConnectTimeoutMillis();
            if (connectTimeout > 0) {
                this.channel.connectDeadlineNanos = System.nanoTime() + (((long) connectTimeout) * 1000000);
            }
        }
    }
}
