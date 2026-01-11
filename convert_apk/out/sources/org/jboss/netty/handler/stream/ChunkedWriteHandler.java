package org.jboss.netty.handler.stream;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelDownstreamHandler;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.LifeCycleAwareChannelHandler;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.internal.QueueFactory;

public class ChunkedWriteHandler implements ChannelUpstreamHandler, ChannelDownstreamHandler, LifeCycleAwareChannelHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) ChunkedWriteHandler.class);
    private volatile ChannelHandlerContext ctx;
    private MessageEvent currentEvent;
    private final AtomicBoolean flush = new AtomicBoolean(false);
    private final Queue<MessageEvent> queue = QueueFactory.createQueue(MessageEvent.class);

    public void resumeTransfer() {
        ChannelHandlerContext ctx2 = this.ctx;
        if (ctx2 != null) {
            try {
                flush(ctx2, false);
            } catch (Exception e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Unexpected exception while sending chunks.", e);
                }
            }
        }
    }

    public void handleDownstream(ChannelHandlerContext ctx2, ChannelEvent e) throws Exception {
        if (!(e instanceof MessageEvent)) {
            ctx2.sendDownstream(e);
            return;
        }
        boolean offer = this.queue.offer((MessageEvent) e);
        Channel channel = ctx2.getChannel();
        if (channel.isWritable() || !channel.isConnected()) {
            this.ctx = ctx2;
            flush(ctx2, false);
        }
    }

    public void handleUpstream(ChannelHandlerContext ctx2, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            ChannelStateEvent cse = (ChannelStateEvent) e;
            int i = C09003.$SwitchMap$org$jboss$netty$channel$ChannelState[cse.getState().ordinal()];
            if (i == 1) {
                flush(ctx2, true);
            } else if (i == 2 && !Boolean.TRUE.equals(cse.getValue())) {
                flush(ctx2, true);
            }
        }
        ctx2.sendUpstream(e);
    }

    /* renamed from: org.jboss.netty.handler.stream.ChunkedWriteHandler$3 */
    static /* synthetic */ class C09003 {
        static final /* synthetic */ int[] $SwitchMap$org$jboss$netty$channel$ChannelState;

        static {
            int[] iArr = new int[ChannelState.values().length];
            $SwitchMap$org$jboss$netty$channel$ChannelState = iArr;
            try {
                iArr[ChannelState.INTEREST_OPS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$jboss$netty$channel$ChannelState[ChannelState.OPEN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private void discard(ChannelHandlerContext ctx2, boolean fireNow) {
        ClosedChannelException cause = null;
        while (true) {
            MessageEvent currentEvent2 = this.currentEvent;
            if (this.currentEvent == null) {
                currentEvent2 = this.queue.poll();
            } else {
                this.currentEvent = null;
            }
            if (currentEvent2 == null) {
                break;
            }
            Object m = currentEvent2.getMessage();
            if (m instanceof ChunkedInput) {
                closeInput((ChunkedInput) m);
            }
            if (cause == null) {
                cause = new ClosedChannelException();
            }
            currentEvent2.getFuture().setFailure(cause);
        }
        if (cause == null) {
            return;
        }
        if (fireNow) {
            Channels.fireExceptionCaught(ctx2.getChannel(), (Throwable) cause);
        } else {
            Channels.fireExceptionCaughtLater(ctx2.getChannel(), (Throwable) cause);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x00a8 A[Catch:{ all -> 0x009a, all -> 0x00cf }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00ac A[Catch:{ all -> 0x009a, all -> 0x00cf }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:74:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void flush(org.jboss.netty.channel.ChannelHandlerContext r14, boolean r15) throws java.lang.Exception {
        /*
            r13 = this;
            r0 = 0
            org.jboss.netty.channel.Channel r1 = r14.getChannel()
            java.util.concurrent.atomic.AtomicBoolean r2 = r13.flush
            r3 = 1
            r4 = 0
            boolean r2 = r2.compareAndSet(r4, r3)
            r0 = r2
            if (r2 == 0) goto L_0x00d6
            boolean r2 = r1.isConnected()     // Catch:{ all -> 0x00cf }
            if (r2 != 0) goto L_0x0020
            r13.discard(r14, r15)     // Catch:{ all -> 0x00cf }
            java.util.concurrent.atomic.AtomicBoolean r2 = r13.flush
            r2.set(r4)
            return
        L_0x0020:
            r2 = 0
            r5 = r2
            r6 = r4
            r7 = r6
        L_0x0024:
            boolean r8 = r1.isWritable()     // Catch:{ all -> 0x00cf }
            if (r8 == 0) goto L_0x00c8
            org.jboss.netty.channel.MessageEvent r8 = r13.currentEvent     // Catch:{ all -> 0x00cf }
            if (r8 != 0) goto L_0x0038
            java.util.Queue<org.jboss.netty.channel.MessageEvent> r8 = r13.queue     // Catch:{ all -> 0x00cf }
            java.lang.Object r8 = r8.poll()     // Catch:{ all -> 0x00cf }
            org.jboss.netty.channel.MessageEvent r8 = (org.jboss.netty.channel.MessageEvent) r8     // Catch:{ all -> 0x00cf }
            r13.currentEvent = r8     // Catch:{ all -> 0x00cf }
        L_0x0038:
            org.jboss.netty.channel.MessageEvent r8 = r13.currentEvent     // Catch:{ all -> 0x00cf }
            if (r8 != 0) goto L_0x003e
            goto L_0x00c8
        L_0x003e:
            org.jboss.netty.channel.MessageEvent r8 = r13.currentEvent     // Catch:{ all -> 0x00cf }
            org.jboss.netty.channel.ChannelFuture r8 = r8.getFuture()     // Catch:{ all -> 0x00cf }
            boolean r8 = r8.isDone()     // Catch:{ all -> 0x00cf }
            if (r8 == 0) goto L_0x004e
            r13.currentEvent = r2     // Catch:{ all -> 0x00cf }
            goto L_0x00b8
        L_0x004e:
            org.jboss.netty.channel.MessageEvent r8 = r13.currentEvent     // Catch:{ all -> 0x00cf }
            java.lang.Object r9 = r8.getMessage()     // Catch:{ all -> 0x00cf }
            boolean r10 = r9 instanceof org.jboss.netty.handler.stream.ChunkedInput     // Catch:{ all -> 0x00cf }
            if (r10 == 0) goto L_0x00b3
            r10 = r9
            org.jboss.netty.handler.stream.ChunkedInput r10 = (org.jboss.netty.handler.stream.ChunkedInput) r10     // Catch:{ all -> 0x00cf }
            java.lang.Object r5 = r10.nextChunk()     // Catch:{ all -> 0x009c }
            boolean r6 = r10.isEndOfInput()     // Catch:{ all -> 0x009a }
            if (r5 != 0) goto L_0x0070
            org.jboss.netty.buffer.ChannelBuffer r7 = org.jboss.netty.buffer.ChannelBuffers.EMPTY_BUFFER     // Catch:{ all -> 0x006e }
            r5 = r7
            if (r6 != 0) goto L_0x006c
            r7 = r3
            goto L_0x006d
        L_0x006c:
            r7 = r4
        L_0x006d:
            goto L_0x0071
        L_0x006e:
            r3 = move-exception
            goto L_0x009d
        L_0x0070:
            r7 = 0
        L_0x0071:
            if (r7 == 0) goto L_0x0075
            goto L_0x00c8
        L_0x0075:
            if (r6 == 0) goto L_0x0086
            r13.currentEvent = r2     // Catch:{ all -> 0x00cf }
            org.jboss.netty.channel.ChannelFuture r11 = r8.getFuture()     // Catch:{ all -> 0x00cf }
            org.jboss.netty.handler.stream.ChunkedWriteHandler$1 r12 = new org.jboss.netty.handler.stream.ChunkedWriteHandler$1     // Catch:{ all -> 0x00cf }
            r12.<init>(r10)     // Catch:{ all -> 0x00cf }
            r11.addListener(r12)     // Catch:{ all -> 0x00cf }
            goto L_0x0092
        L_0x0086:
            org.jboss.netty.channel.ChannelFuture r11 = org.jboss.netty.channel.Channels.future(r1)     // Catch:{ all -> 0x00cf }
            org.jboss.netty.handler.stream.ChunkedWriteHandler$2 r12 = new org.jboss.netty.handler.stream.ChunkedWriteHandler$2     // Catch:{ all -> 0x00cf }
            r12.<init>(r8)     // Catch:{ all -> 0x00cf }
            r11.addListener(r12)     // Catch:{ all -> 0x00cf }
        L_0x0092:
            java.net.SocketAddress r12 = r8.getRemoteAddress()     // Catch:{ all -> 0x00cf }
            org.jboss.netty.channel.Channels.write(r14, r11, r5, r12)     // Catch:{ all -> 0x00cf }
            goto L_0x00b8
        L_0x009a:
            r3 = move-exception
            goto L_0x009d
        L_0x009c:
            r3 = move-exception
        L_0x009d:
            r13.currentEvent = r2     // Catch:{ all -> 0x00cf }
            org.jboss.netty.channel.ChannelFuture r2 = r8.getFuture()     // Catch:{ all -> 0x00cf }
            r2.setFailure(r3)     // Catch:{ all -> 0x00cf }
            if (r15 == 0) goto L_0x00ac
            org.jboss.netty.channel.Channels.fireExceptionCaught((org.jboss.netty.channel.ChannelHandlerContext) r14, (java.lang.Throwable) r3)     // Catch:{ all -> 0x00cf }
            goto L_0x00af
        L_0x00ac:
            org.jboss.netty.channel.Channels.fireExceptionCaughtLater((org.jboss.netty.channel.ChannelHandlerContext) r14, (java.lang.Throwable) r3)     // Catch:{ all -> 0x00cf }
        L_0x00af:
            closeInput(r10)     // Catch:{ all -> 0x00cf }
            goto L_0x00c8
        L_0x00b3:
            r13.currentEvent = r2     // Catch:{ all -> 0x00cf }
            r14.sendDownstream(r8)     // Catch:{ all -> 0x00cf }
        L_0x00b8:
            boolean r8 = r1.isConnected()     // Catch:{ all -> 0x00cf }
            if (r8 != 0) goto L_0x0024
            r13.discard(r14, r15)     // Catch:{ all -> 0x00cf }
            java.util.concurrent.atomic.AtomicBoolean r2 = r13.flush
            r2.set(r4)
            return
        L_0x00c8:
            java.util.concurrent.atomic.AtomicBoolean r2 = r13.flush
            r2.set(r4)
            goto L_0x00d6
        L_0x00cf:
            r2 = move-exception
            java.util.concurrent.atomic.AtomicBoolean r3 = r13.flush
            r3.set(r4)
            throw r2
        L_0x00d6:
            if (r0 == 0) goto L_0x00ef
            boolean r2 = r1.isConnected()
            if (r2 == 0) goto L_0x00ec
            boolean r2 = r1.isWritable()
            if (r2 == 0) goto L_0x00ef
            java.util.Queue<org.jboss.netty.channel.MessageEvent> r2 = r13.queue
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L_0x00ef
        L_0x00ec:
            r13.flush(r14, r15)
        L_0x00ef:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.handler.stream.ChunkedWriteHandler.flush(org.jboss.netty.channel.ChannelHandlerContext, boolean):void");
    }

    static void closeInput(ChunkedInput chunks) {
        try {
            chunks.close();
        } catch (Throwable t) {
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to close a chunked input.", t);
            }
        }
    }

    public void beforeAdd(ChannelHandlerContext ctx2) throws Exception {
    }

    public void afterAdd(ChannelHandlerContext ctx2) throws Exception {
    }

    public void beforeRemove(ChannelHandlerContext ctx2) throws Exception {
        flush(ctx2, false);
    }

    public void afterRemove(ChannelHandlerContext ctx2) throws Exception {
        Throwable cause = null;
        boolean fireExceptionCaught = false;
        while (true) {
            MessageEvent currentEvent2 = this.currentEvent;
            if (this.currentEvent == null) {
                currentEvent2 = this.queue.poll();
            } else {
                this.currentEvent = null;
            }
            if (currentEvent2 == null) {
                break;
            }
            Object m = currentEvent2.getMessage();
            if (m instanceof ChunkedInput) {
                closeInput((ChunkedInput) m);
            }
            if (cause == null) {
                cause = new IOException("Unable to flush event, discarding");
            }
            currentEvent2.getFuture().setFailure(cause);
            fireExceptionCaught = true;
        }
        if (fireExceptionCaught) {
            Channels.fireExceptionCaughtLater(ctx2.getChannel(), cause);
        }
    }
}
