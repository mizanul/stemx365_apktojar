package org.jboss.netty.handler.ssl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import kotlin.UShort;
import kotlin.jvm.internal.ShortCompanionObject;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelDownstreamHandler;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.DefaultChannelFuture;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.internal.DetectionUtil;
import org.jboss.netty.util.internal.NonReentrantLock;
import org.jboss.netty.util.internal.QueueFactory;

public class SslHandler extends FrameDecoder implements ChannelDownstreamHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0);
    private static final Pattern IGNORABLE_CLASS_IN_STACK = Pattern.compile("^.*(Socket|DatagramChannel|SctpChannel).*$");
    private static final Pattern IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*reset|connection.*closed|broken.*pipe).*$", 2);
    private static SslBufferPool defaultBufferPool;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) SslHandler.class);
    private final SslBufferPool bufferPool;
    /* access modifiers changed from: private */
    public volatile ChannelHandlerContext ctx;
    private final Executor delegatedTaskExecutor;
    private volatile boolean enableRenegotiation;
    private final SSLEngine engine;
    private volatile ChannelFuture handshakeFuture;
    final Object handshakeLock;
    private volatile boolean handshaken;
    private boolean handshaking;
    int ignoreClosedChannelException;
    final Object ignoreClosedChannelExceptionLock;
    private volatile boolean issueHandshake;
    private int packetLength;
    private final Queue<MessageEvent> pendingEncryptedWrites;
    private final NonReentrantLock pendingEncryptedWritesLock;
    private final Queue<PendingWrite> pendingUnencryptedWrites;
    private final AtomicBoolean sentCloseNotify;
    private final AtomicBoolean sentFirstMessage;
    private final SSLEngineInboundCloseFuture sslEngineCloseFuture;
    private final boolean startTls;

    public static synchronized SslBufferPool getDefaultBufferPool() {
        SslBufferPool sslBufferPool;
        synchronized (SslHandler.class) {
            if (defaultBufferPool == null) {
                defaultBufferPool = new SslBufferPool();
            }
            sslBufferPool = defaultBufferPool;
        }
        return sslBufferPool;
    }

    public SslHandler(SSLEngine engine2) {
        this(engine2, getDefaultBufferPool(), (Executor) ImmediateExecutor.INSTANCE);
    }

    public SslHandler(SSLEngine engine2, SslBufferPool bufferPool2) {
        this(engine2, bufferPool2, (Executor) ImmediateExecutor.INSTANCE);
    }

    public SslHandler(SSLEngine engine2, boolean startTls2) {
        this(engine2, getDefaultBufferPool(), startTls2);
    }

    public SslHandler(SSLEngine engine2, SslBufferPool bufferPool2, boolean startTls2) {
        this(engine2, bufferPool2, startTls2, ImmediateExecutor.INSTANCE);
    }

    public SslHandler(SSLEngine engine2, Executor delegatedTaskExecutor2) {
        this(engine2, getDefaultBufferPool(), delegatedTaskExecutor2);
    }

    public SslHandler(SSLEngine engine2, SslBufferPool bufferPool2, Executor delegatedTaskExecutor2) {
        this(engine2, bufferPool2, false, delegatedTaskExecutor2);
    }

    public SslHandler(SSLEngine engine2, boolean startTls2, Executor delegatedTaskExecutor2) {
        this(engine2, getDefaultBufferPool(), startTls2, delegatedTaskExecutor2);
    }

    public SslHandler(SSLEngine engine2, SslBufferPool bufferPool2, boolean startTls2, Executor delegatedTaskExecutor2) {
        this.enableRenegotiation = true;
        this.handshakeLock = new Object();
        this.sentFirstMessage = new AtomicBoolean();
        this.sentCloseNotify = new AtomicBoolean();
        this.ignoreClosedChannelExceptionLock = new Object();
        this.pendingUnencryptedWrites = new LinkedList();
        this.pendingEncryptedWrites = QueueFactory.createQueue(MessageEvent.class);
        this.pendingEncryptedWritesLock = new NonReentrantLock();
        this.sslEngineCloseFuture = new SSLEngineInboundCloseFuture();
        this.packetLength = -1;
        if (engine2 == null) {
            throw new NullPointerException("engine");
        } else if (bufferPool2 == null) {
            throw new NullPointerException("bufferPool");
        } else if (delegatedTaskExecutor2 != null) {
            this.engine = engine2;
            this.bufferPool = bufferPool2;
            this.delegatedTaskExecutor = delegatedTaskExecutor2;
            this.startTls = startTls2;
        } else {
            throw new NullPointerException("delegatedTaskExecutor");
        }
    }

    public SSLEngine getEngine() {
        return this.engine;
    }

    public ChannelFuture handshake() {
        ChannelHandlerContext ctx2;
        Channel channel;
        Exception exception;
        ChannelFuture handshakeFuture2;
        if (!this.handshaken || isEnableRenegotiation()) {
            ctx2 = this.ctx;
            channel = ctx2.getChannel();
            exception = null;
            synchronized (this.handshakeLock) {
                try {
                    if (this.handshaking) {
                        ChannelFuture channelFuture = this.handshakeFuture;
                        return channelFuture;
                    }
                    this.handshaking = true;
                    try {
                        this.engine.beginHandshake();
                        runDelegatedTasks();
                        ChannelFuture future = Channels.future(channel);
                        this.handshakeFuture = future;
                        handshakeFuture2 = future;
                    } catch (Exception e) {
                        ChannelFuture failedFuture = Channels.failedFuture(channel, e);
                        this.handshakeFuture = failedFuture;
                        handshakeFuture2 = failedFuture;
                        exception = e;
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        } else {
            throw new IllegalStateException("renegotiation disabled");
        }
        if (exception == null) {
            try {
                wrapNonAppData(ctx2, channel);
            } catch (SSLException e2) {
                handshakeFuture2.setFailure(e2);
                Channels.fireExceptionCaught(ctx2, (Throwable) e2);
            }
        } else {
            Channels.fireExceptionCaught(ctx2, (Throwable) exception);
        }
        return handshakeFuture2;
        return handshakeFuture2;
    }

    @Deprecated
    public ChannelFuture handshake(Channel channel) {
        return handshake();
    }

    public ChannelFuture close() {
        ChannelHandlerContext ctx2 = this.ctx;
        Channel channel = ctx2.getChannel();
        try {
            this.engine.closeOutbound();
            return wrapNonAppData(ctx2, channel);
        } catch (SSLException e) {
            Channels.fireExceptionCaught(ctx2, (Throwable) e);
            return Channels.failedFuture(channel, e);
        }
    }

    @Deprecated
    public ChannelFuture close(Channel channel) {
        return close();
    }

    public boolean isEnableRenegotiation() {
        return this.enableRenegotiation;
    }

    public void setEnableRenegotiation(boolean enableRenegotiation2) {
        this.enableRenegotiation = enableRenegotiation2;
    }

    public void setIssueHandshake(boolean issueHandshake2) {
        this.issueHandshake = issueHandshake2;
    }

    public boolean isIssueHandshake() {
        return this.issueHandshake;
    }

    public ChannelFuture getSSLEngineInboundCloseFuture() {
        return this.sslEngineCloseFuture;
    }

    public void handleDownstream(ChannelHandlerContext context, ChannelEvent evt) throws Exception {
        PendingWrite pendingWrite;
        if (evt instanceof ChannelStateEvent) {
            ChannelStateEvent e = (ChannelStateEvent) evt;
            int i = C08974.$SwitchMap$org$jboss$netty$channel$ChannelState[e.getState().ordinal()];
            if ((i == 1 || i == 2 || i == 3) && (Boolean.FALSE.equals(e.getValue()) || e.getValue() == null)) {
                closeOutboundAndChannel(context, e);
                return;
            }
        }
        if (!(evt instanceof MessageEvent)) {
            context.sendDownstream(evt);
            return;
        }
        MessageEvent e2 = (MessageEvent) evt;
        if (!(e2.getMessage() instanceof ChannelBuffer)) {
            context.sendDownstream(evt);
        } else if (!this.startTls || !this.sentFirstMessage.compareAndSet(false, true)) {
            ChannelBuffer msg = (ChannelBuffer) e2.getMessage();
            if (msg.readable()) {
                pendingWrite = new PendingWrite(evt.getFuture(), msg.toByteBuffer(msg.readerIndex(), msg.readableBytes()));
            } else {
                pendingWrite = new PendingWrite(evt.getFuture(), (ByteBuffer) null);
            }
            synchronized (this.pendingUnencryptedWrites) {
                boolean offer = this.pendingUnencryptedWrites.offer(pendingWrite);
            }
            wrap(context, evt.getChannel());
        } else {
            context.sendDownstream(evt);
        }
    }

    public void channelDisconnected(ChannelHandlerContext ctx2, ChannelStateEvent e) throws Exception {
        String str;
        synchronized (this.handshakeLock) {
            if (this.handshaking) {
                this.handshakeFuture.setFailure(new ClosedChannelException());
            }
        }
        try {
            super.channelDisconnected(ctx2, e);
        } finally {
            unwrap(ctx2, e.getChannel(), ChannelBuffers.EMPTY_BUFFER, 0, 0);
            this.engine.closeOutbound();
            if (!this.sentCloseNotify.get() && this.handshaken) {
                try {
                    this.engine.closeInbound();
                } catch (SSLException ex) {
                    if (logger.isDebugEnabled()) {
                        str = "Failed to clean up SSLEngine.";
                        logger.debug(str, ex);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext r5, org.jboss.netty.channel.ExceptionEvent r6) throws java.lang.Exception {
        /*
            r4 = this;
            java.lang.Throwable r0 = r6.getCause()
            boolean r1 = r0 instanceof java.io.IOException
            if (r1 == 0) goto L_0x0036
            boolean r1 = r0 instanceof java.nio.channels.ClosedChannelException
            if (r1 == 0) goto L_0x002f
            java.lang.Object r1 = r4.ignoreClosedChannelExceptionLock
            monitor-enter(r1)
            int r2 = r4.ignoreClosedChannelException     // Catch:{ all -> 0x002c }
            if (r2 <= 0) goto L_0x002a
            int r2 = r4.ignoreClosedChannelException     // Catch:{ all -> 0x002c }
            int r2 = r2 + -1
            r4.ignoreClosedChannelException = r2     // Catch:{ all -> 0x002c }
            org.jboss.netty.logging.InternalLogger r2 = logger     // Catch:{ all -> 0x002c }
            boolean r2 = r2.isDebugEnabled()     // Catch:{ all -> 0x002c }
            if (r2 == 0) goto L_0x0028
            org.jboss.netty.logging.InternalLogger r2 = logger     // Catch:{ all -> 0x002c }
            java.lang.String r3 = "Swallowing an exception raised while writing non-app data"
            r2.debug(r3, r0)     // Catch:{ all -> 0x002c }
        L_0x0028:
            monitor-exit(r1)     // Catch:{ all -> 0x002c }
            return
        L_0x002a:
            monitor-exit(r1)     // Catch:{ all -> 0x002c }
            goto L_0x0036
        L_0x002c:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x002c }
            throw r2
        L_0x002f:
            boolean r1 = r4.ignoreException(r0)
            if (r1 == 0) goto L_0x0036
            return
        L_0x0036:
            r5.sendUpstream(r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.handler.ssl.SslHandler.exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent):void");
    }

    private boolean ignoreException(Throwable t) {
        if ((t instanceof SSLException) || !(t instanceof IOException) || !this.engine.isOutboundDone()) {
            return false;
        }
        if (IGNORABLE_ERROR_MESSAGE.matcher(String.valueOf(t.getMessage()).toLowerCase()).matches()) {
            return true;
        }
        for (StackTraceElement element : t.getStackTrace()) {
            String classname = element.getClassName();
            String methodname = element.getMethodName();
            if (!classname.startsWith("org.jboss.netty.") && methodname.equals("read")) {
                if (IGNORABLE_CLASS_IN_STACK.matcher(classname).matches()) {
                    return true;
                }
                try {
                    Class<?> clazz = getClass().getClassLoader().loadClass(classname);
                    if (!SocketChannel.class.isAssignableFrom(clazz)) {
                        if (!DatagramChannel.class.isAssignableFrom(clazz)) {
                            if (DetectionUtil.javaVersion() >= 7 && "com.sun.nio.sctp.SctpChannel".equals(clazz.getSuperclass().getName())) {
                                return true;
                            }
                        }
                    }
                    return true;
                } catch (ClassNotFoundException e) {
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext ctx2, Channel channel, ChannelBuffer buffer) throws Exception {
        boolean tls;
        if (this.packetLength == -1) {
            if (buffer.readableBytes() < 5) {
                return null;
            }
            switch (buffer.getUnsignedByte(buffer.readerIndex())) {
                case 20:
                case 21:
                case 22:
                case 23:
                    tls = true;
                    break;
                default:
                    tls = false;
                    break;
            }
            if (tls) {
                if (buffer.getUnsignedByte(buffer.readerIndex() + 1) == 3) {
                    int i = (getShort(buffer, buffer.readerIndex() + 3) & UShort.MAX_VALUE) + 5;
                    this.packetLength = i;
                    if (i <= 5) {
                        tls = false;
                    }
                } else {
                    tls = false;
                }
            }
            if (!tls) {
                boolean sslv2 = true;
                int headerLength = (buffer.getUnsignedByte(buffer.readerIndex()) & 128) != 0 ? 2 : 3;
                int majorVersion = buffer.getUnsignedByte(buffer.readerIndex() + headerLength + 1);
                if (majorVersion == 2 || majorVersion == 3) {
                    if (headerLength == 2) {
                        this.packetLength = (getShort(buffer, buffer.readerIndex()) & ShortCompanionObject.MAX_VALUE) + 2;
                    } else {
                        this.packetLength = (getShort(buffer, buffer.readerIndex()) & 16383) + 3;
                    }
                    if (this.packetLength <= headerLength) {
                        sslv2 = false;
                    }
                } else {
                    sslv2 = false;
                }
                if (!sslv2) {
                    NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + ChannelBuffers.hexDump(buffer));
                    buffer.skipBytes(buffer.readableBytes());
                    throw e;
                }
            }
        }
        if (buffer.readableBytes() < this.packetLength) {
            return null;
        }
        int packetOffset = buffer.readerIndex();
        buffer.skipBytes(this.packetLength);
        try {
            return unwrap(ctx2, channel, buffer, packetOffset, this.packetLength);
        } finally {
            this.packetLength = -1;
        }
    }

    private static short getShort(ChannelBuffer buf, int offset) {
        return (short) ((buf.getByte(offset) << 8) | (buf.getByte(offset + 1) & 255));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00aa, code lost:
        r12 = false;
        r13 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00f5, code lost:
        r12 = r1;
        r13 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00fe, code lost:
        r12 = true;
        r13 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x010c, code lost:
        r12 = true;
        r13 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.jboss.netty.channel.ChannelFuture wrap(org.jboss.netty.channel.ChannelHandlerContext r17, org.jboss.netty.channel.Channel r18) throws javax.net.ssl.SSLException {
        /*
            r16 = this;
            r7 = r16
            r8 = r18
            r0 = 0
            org.jboss.netty.handler.ssl.SslBufferPool r1 = r7.bufferPool
            java.nio.ByteBuffer r9 = r1.acquireBuffer()
            r1 = 1
            r2 = 0
            r3 = 0
            r10 = r0
            r11 = r2
        L_0x0010:
            r2 = 0
            java.util.Queue<org.jboss.netty.handler.ssl.SslHandler$PendingWrite> r4 = r7.pendingUnencryptedWrites     // Catch:{ SSLException -> 0x016d }
            monitor-enter(r4)     // Catch:{ SSLException -> 0x016d }
            java.util.Queue<org.jboss.netty.handler.ssl.SslHandler$PendingWrite> r0 = r7.pendingUnencryptedWrites     // Catch:{ all -> 0x0168 }
            java.lang.Object r0 = r0.peek()     // Catch:{ all -> 0x0168 }
            org.jboss.netty.handler.ssl.SslHandler$PendingWrite r0 = (org.jboss.netty.handler.ssl.SslHandler.PendingWrite) r0     // Catch:{ all -> 0x0168 }
            r5 = r0
            if (r5 != 0) goto L_0x0022
            monitor-exit(r4)     // Catch:{ all -> 0x0168 }
            goto L_0x010c
        L_0x0022:
            java.nio.ByteBuffer r0 = r5.outAppBuf     // Catch:{ all -> 0x0168 }
            r6 = r0
            if (r6 != 0) goto L_0x0040
            java.util.Queue<org.jboss.netty.handler.ssl.SslHandler$PendingWrite> r0 = r7.pendingUnencryptedWrites     // Catch:{ all -> 0x0168 }
            r0.remove()     // Catch:{ all -> 0x0168 }
            org.jboss.netty.channel.DownstreamMessageEvent r0 = new org.jboss.netty.channel.DownstreamMessageEvent     // Catch:{ all -> 0x0168 }
            org.jboss.netty.channel.ChannelFuture r12 = r5.future     // Catch:{ all -> 0x0168 }
            org.jboss.netty.buffer.ChannelBuffer r13 = org.jboss.netty.buffer.ChannelBuffers.EMPTY_BUFFER     // Catch:{ all -> 0x0168 }
            java.net.SocketAddress r14 = r18.getRemoteAddress()     // Catch:{ all -> 0x0168 }
            r0.<init>(r8, r12, r13, r14)     // Catch:{ all -> 0x0168 }
            r7.offerEncryptedWriteRequest(r0)     // Catch:{ all -> 0x0168 }
            r0 = 1
            r11 = r0
            goto L_0x0108
        L_0x0040:
            r12 = 0
            java.lang.Object r13 = r7.handshakeLock     // Catch:{ all -> 0x015a }
            monitor-enter(r13)     // Catch:{ all -> 0x015a }
            javax.net.ssl.SSLEngine r0 = r7.engine     // Catch:{ all -> 0x0157 }
            javax.net.ssl.SSLEngineResult r0 = r0.wrap(r6, r9)     // Catch:{ all -> 0x0157 }
            r12 = r0
            monitor-exit(r13)     // Catch:{ all -> 0x0157 }
            boolean r0 = r6.hasRemaining()     // Catch:{ all -> 0x0168 }
            if (r0 != 0) goto L_0x0058
            java.util.Queue<org.jboss.netty.handler.ssl.SslHandler$PendingWrite> r0 = r7.pendingUnencryptedWrites     // Catch:{ all -> 0x0168 }
            r0.remove()     // Catch:{ all -> 0x0168 }
        L_0x0058:
            int r0 = r12.bytesProduced()     // Catch:{ all -> 0x0168 }
            if (r0 <= 0) goto L_0x00a0
            r9.flip()     // Catch:{ all -> 0x0168 }
            int r0 = r9.remaining()     // Catch:{ all -> 0x0168 }
            org.jboss.netty.channel.ChannelHandlerContext r13 = r7.ctx     // Catch:{ all -> 0x0168 }
            org.jboss.netty.channel.Channel r13 = r13.getChannel()     // Catch:{ all -> 0x0168 }
            org.jboss.netty.channel.ChannelConfig r13 = r13.getConfig()     // Catch:{ all -> 0x0168 }
            org.jboss.netty.buffer.ChannelBufferFactory r13 = r13.getBufferFactory()     // Catch:{ all -> 0x0168 }
            org.jboss.netty.buffer.ChannelBuffer r13 = r13.getBuffer((int) r0)     // Catch:{ all -> 0x0168 }
            r13.writeBytes((java.nio.ByteBuffer) r9)     // Catch:{ all -> 0x0168 }
            r9.clear()     // Catch:{ all -> 0x0168 }
            java.nio.ByteBuffer r14 = r5.outAppBuf     // Catch:{ all -> 0x0168 }
            boolean r14 = r14.hasRemaining()     // Catch:{ all -> 0x0168 }
            if (r14 == 0) goto L_0x008d
            org.jboss.netty.channel.ChannelFuture r14 = org.jboss.netty.channel.Channels.succeededFuture(r18)     // Catch:{ all -> 0x0168 }
            r10 = r14
            goto L_0x0090
        L_0x008d:
            org.jboss.netty.channel.ChannelFuture r14 = r5.future     // Catch:{ all -> 0x0168 }
            r10 = r14
        L_0x0090:
            org.jboss.netty.channel.DownstreamMessageEvent r14 = new org.jboss.netty.channel.DownstreamMessageEvent     // Catch:{ all -> 0x0168 }
            java.net.SocketAddress r15 = r18.getRemoteAddress()     // Catch:{ all -> 0x0168 }
            r14.<init>(r8, r10, r13, r15)     // Catch:{ all -> 0x0168 }
            r7.offerEncryptedWriteRequest(r14)     // Catch:{ all -> 0x0168 }
            r0 = 1
            r11 = r0
            goto L_0x0108
        L_0x00a0:
            javax.net.ssl.SSLEngineResult$Status r0 = r12.getStatus()     // Catch:{ all -> 0x0168 }
            javax.net.ssl.SSLEngineResult$Status r13 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ all -> 0x0168 }
            if (r0 != r13) goto L_0x00ae
            r1 = 0
            monitor-exit(r4)     // Catch:{ all -> 0x0168 }
            r12 = r1
            r13 = r3
            goto L_0x010e
        L_0x00ae:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r0 = r12.getHandshakeStatus()     // Catch:{ all -> 0x0168 }
            r7.handleRenegotiation(r0)     // Catch:{ all -> 0x0168 }
            int[] r13 = org.jboss.netty.handler.ssl.SslHandler.C08974.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ all -> 0x0168 }
            int r14 = r0.ordinal()     // Catch:{ all -> 0x0168 }
            r13 = r13[r14]     // Catch:{ all -> 0x0168 }
            r14 = 1
            if (r13 == r14) goto L_0x0101
            r14 = 2
            if (r13 == r14) goto L_0x00fc
            r14 = 3
            if (r13 == r14) goto L_0x00f8
            r14 = 4
            if (r13 == r14) goto L_0x00e4
            r14 = 5
            if (r13 != r14) goto L_0x00cd
            goto L_0x00e4
        L_0x00cd:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0168 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x0168 }
            r14.<init>()     // Catch:{ all -> 0x0168 }
            java.lang.String r15 = "Unknown handshake status: "
            r14.append(r15)     // Catch:{ all -> 0x0168 }
            r14.append(r0)     // Catch:{ all -> 0x0168 }
            java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x0168 }
            r13.<init>(r14)     // Catch:{ all -> 0x0168 }
            throw r13     // Catch:{ all -> 0x0168 }
        L_0x00e4:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r13 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ all -> 0x0168 }
            if (r0 != r13) goto L_0x00eb
            r7.setHandshakeSuccess(r8)     // Catch:{ all -> 0x0168 }
        L_0x00eb:
            javax.net.ssl.SSLEngineResult$Status r13 = r12.getStatus()     // Catch:{ all -> 0x0168 }
            javax.net.ssl.SSLEngineResult$Status r14 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ all -> 0x0168 }
            if (r13 != r14) goto L_0x00f4
            r1 = 0
        L_0x00f4:
            monitor-exit(r4)     // Catch:{ all -> 0x0168 }
            r12 = r1
            r13 = r3
            goto L_0x010e
        L_0x00f8:
            r16.runDelegatedTasks()     // Catch:{ all -> 0x0168 }
            goto L_0x0108
        L_0x00fc:
            r3 = 1
            monitor-exit(r4)     // Catch:{ all -> 0x0168 }
            r12 = r1
            r13 = r3
            goto L_0x010e
        L_0x0101:
            boolean r13 = r6.hasRemaining()     // Catch:{ all -> 0x0168 }
            if (r13 == 0) goto L_0x010b
        L_0x0108:
            monitor-exit(r4)     // Catch:{ all -> 0x0168 }
            goto L_0x0010
        L_0x010b:
            monitor-exit(r4)     // Catch:{ all -> 0x0168 }
        L_0x010c:
            r12 = r1
            r13 = r3
        L_0x010e:
            org.jboss.netty.handler.ssl.SslBufferPool r0 = r7.bufferPool
            r0.releaseBuffer(r9)
            if (r11 == 0) goto L_0x0119
            r16.flushPendingEncryptedWrites(r17)
        L_0x0119:
            if (r12 != 0) goto L_0x013f
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "SSLEngine already closed"
            r0.<init>(r1)
            r1 = r0
        L_0x0123:
            java.util.Queue<org.jboss.netty.handler.ssl.SslHandler$PendingWrite> r3 = r7.pendingUnencryptedWrites
            monitor-enter(r3)
            java.util.Queue<org.jboss.netty.handler.ssl.SslHandler$PendingWrite> r0 = r7.pendingUnencryptedWrites     // Catch:{ all -> 0x013a }
            java.lang.Object r0 = r0.poll()     // Catch:{ all -> 0x013a }
            org.jboss.netty.handler.ssl.SslHandler$PendingWrite r0 = (org.jboss.netty.handler.ssl.SslHandler.PendingWrite) r0     // Catch:{ all -> 0x013a }
            r2 = r0
            if (r2 != 0) goto L_0x0133
            monitor-exit(r3)     // Catch:{ all -> 0x013d }
            goto L_0x013f
        L_0x0133:
            monitor-exit(r3)     // Catch:{ all -> 0x013d }
            org.jboss.netty.channel.ChannelFuture r0 = r2.future
            r0.setFailure(r1)
            goto L_0x0123
        L_0x013a:
            r0 = move-exception
        L_0x013b:
            monitor-exit(r3)     // Catch:{ all -> 0x013d }
            throw r0
        L_0x013d:
            r0 = move-exception
            goto L_0x013b
        L_0x013f:
            if (r13 == 0) goto L_0x0150
            org.jboss.netty.buffer.ChannelBuffer r4 = org.jboss.netty.buffer.ChannelBuffers.EMPTY_BUFFER
            r5 = 0
            r6 = 0
            r1 = r16
            r2 = r17
            r3 = r18
            r1.unwrap(r2, r3, r4, r5, r6)
        L_0x0150:
            if (r10 != 0) goto L_0x0156
            org.jboss.netty.channel.ChannelFuture r10 = org.jboss.netty.channel.Channels.succeededFuture(r18)
        L_0x0156:
            return r10
        L_0x0157:
            r0 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x0157 }
            throw r0     // Catch:{ all -> 0x015a }
        L_0x015a:
            r0 = move-exception
            boolean r13 = r6.hasRemaining()     // Catch:{ all -> 0x0168 }
            if (r13 != 0) goto L_0x0166
            java.util.Queue<org.jboss.netty.handler.ssl.SslHandler$PendingWrite> r13 = r7.pendingUnencryptedWrites     // Catch:{ all -> 0x0168 }
            r13.remove()     // Catch:{ all -> 0x0168 }
        L_0x0166:
            throw r0     // Catch:{ all -> 0x0168 }
        L_0x0168:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0168 }
            throw r0     // Catch:{ SSLException -> 0x016d }
        L_0x016b:
            r0 = move-exception
            goto L_0x0174
        L_0x016d:
            r0 = move-exception
            r1 = 0
            r7.setHandshakeFailure(r8, r0)     // Catch:{ all -> 0x016b }
            throw r0     // Catch:{ all -> 0x016b }
        L_0x0174:
            org.jboss.netty.handler.ssl.SslBufferPool r4 = r7.bufferPool
            r4.releaseBuffer(r9)
            if (r11 == 0) goto L_0x017e
            r16.flushPendingEncryptedWrites(r17)
        L_0x017e:
            if (r1 != 0) goto L_0x01a3
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "SSLEngine already closed"
            r4.<init>(r5)
        L_0x0187:
            java.util.Queue<org.jboss.netty.handler.ssl.SslHandler$PendingWrite> r5 = r7.pendingUnencryptedWrites
            monitor-enter(r5)
            java.util.Queue<org.jboss.netty.handler.ssl.SslHandler$PendingWrite> r6 = r7.pendingUnencryptedWrites     // Catch:{ all -> 0x019e }
            java.lang.Object r6 = r6.poll()     // Catch:{ all -> 0x019e }
            org.jboss.netty.handler.ssl.SslHandler$PendingWrite r6 = (org.jboss.netty.handler.ssl.SslHandler.PendingWrite) r6     // Catch:{ all -> 0x019e }
            r2 = r6
            if (r2 == 0) goto L_0x019c
            monitor-exit(r5)     // Catch:{ all -> 0x01a1 }
            org.jboss.netty.channel.ChannelFuture r5 = r2.future
            r5.setFailure(r4)
            goto L_0x0187
        L_0x019c:
            monitor-exit(r5)     // Catch:{ all -> 0x01a1 }
            goto L_0x01a3
        L_0x019e:
            r0 = move-exception
        L_0x019f:
            monitor-exit(r5)     // Catch:{ all -> 0x01a1 }
            throw r0
        L_0x01a1:
            r0 = move-exception
            goto L_0x019f
        L_0x01a3:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.handler.ssl.SslHandler.wrap(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel):org.jboss.netty.channel.ChannelFuture");
    }

    /* renamed from: org.jboss.netty.handler.ssl.SslHandler$4 */
    static /* synthetic */ class C08974 {
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus;
        static final /* synthetic */ int[] $SwitchMap$org$jboss$netty$channel$ChannelState;

        static {
            int[] iArr = new int[SSLEngineResult.HandshakeStatus.values().length];
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = iArr;
            try {
                iArr[SSLEngineResult.HandshakeStatus.NEED_WRAP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_UNWRAP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_TASK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.FINISHED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            int[] iArr2 = new int[ChannelState.values().length];
            $SwitchMap$org$jboss$netty$channel$ChannelState = iArr2;
            try {
                iArr2[ChannelState.OPEN.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$jboss$netty$channel$ChannelState[ChannelState.CONNECTED.ordinal()] = 2;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$org$jboss$netty$channel$ChannelState[ChannelState.BOUND.ordinal()] = 3;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    private void offerEncryptedWriteRequest(MessageEvent encryptedWrite) {
        boolean locked = this.pendingEncryptedWritesLock.tryLock();
        try {
            this.pendingEncryptedWrites.offer(encryptedWrite);
        } finally {
            if (locked) {
                this.pendingEncryptedWritesLock.unlock();
            }
        }
    }

    private void flushPendingEncryptedWrites(ChannelHandlerContext ctx2) {
        if (this.pendingEncryptedWritesLock.tryLock()) {
            while (true) {
                try {
                    MessageEvent poll = this.pendingEncryptedWrites.poll();
                    MessageEvent e = poll;
                    if (poll != null) {
                        ctx2.sendDownstream(e);
                    } else {
                        return;
                    }
                } finally {
                    this.pendingEncryptedWritesLock.unlock();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
        if (r2.bytesProduced() <= 0) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001a, code lost:
        r1.flip();
        r3 = r12.getChannel().getConfig().getBufferFactory().getBuffer(r1.remaining());
        r3.writeBytes(r1);
        r1.clear();
        r0 = org.jboss.netty.channel.Channels.future(r13);
        r0.addListener(new org.jboss.netty.handler.ssl.SslHandler.C08941(r11));
        org.jboss.netty.channel.Channels.write(r12, r0, (java.lang.Object) r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0047, code lost:
        r3 = r2.getHandshakeStatus();
        handleRenegotiation(r3);
        r4 = org.jboss.netty.handler.ssl.SslHandler.C08974.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[r3.ordinal()];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0057, code lost:
        if (r4 == 1) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005a, code lost:
        if (r4 == 2) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005d, code lost:
        if (r4 == 3) goto L_0x0084;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0060, code lost:
        if (r4 == 4) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0063, code lost:
        if (r4 != 5) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007c, code lost:
        throw new java.lang.IllegalStateException("Unexpected handshake status: " + r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007d, code lost:
        setHandshakeSuccess(r13);
        runDelegatedTasks();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0084, code lost:
        runDelegatedTasks();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008e, code lost:
        if (java.lang.Thread.holdsLock(r11.handshakeLock) != false) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0090, code lost:
        unwrap(r12, r13, org.jboss.netty.buffer.ChannelBuffers.EMPTY_BUFFER, 0, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b6, code lost:
        r3 = e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.jboss.netty.channel.ChannelFuture wrapNonAppData(org.jboss.netty.channel.ChannelHandlerContext r12, org.jboss.netty.channel.Channel r13) throws javax.net.ssl.SSLException {
        /*
            r11 = this;
            r0 = 0
            org.jboss.netty.handler.ssl.SslBufferPool r1 = r11.bufferPool
            java.nio.ByteBuffer r1 = r1.acquireBuffer()
            r2 = 0
        L_0x0008:
            java.lang.Object r3 = r11.handshakeLock     // Catch:{ SSLException -> 0x00bc, all -> 0x00ba }
            monitor-enter(r3)     // Catch:{ SSLException -> 0x00bc, all -> 0x00ba }
            javax.net.ssl.SSLEngine r4 = r11.engine     // Catch:{ all -> 0x00b3 }
            java.nio.ByteBuffer r5 = EMPTY_BUFFER     // Catch:{ all -> 0x00b3 }
            javax.net.ssl.SSLEngineResult r2 = r4.wrap(r5, r1)     // Catch:{ all -> 0x00b3 }
            monitor-exit(r3)     // Catch:{ all -> 0x00b8 }
            int r3 = r2.bytesProduced()     // Catch:{ SSLException -> 0x00b6 }
            if (r3 <= 0) goto L_0x0047
            r1.flip()     // Catch:{ SSLException -> 0x00b6 }
            org.jboss.netty.channel.Channel r3 = r12.getChannel()     // Catch:{ SSLException -> 0x00b6 }
            org.jboss.netty.channel.ChannelConfig r3 = r3.getConfig()     // Catch:{ SSLException -> 0x00b6 }
            org.jboss.netty.buffer.ChannelBufferFactory r3 = r3.getBufferFactory()     // Catch:{ SSLException -> 0x00b6 }
            int r4 = r1.remaining()     // Catch:{ SSLException -> 0x00b6 }
            org.jboss.netty.buffer.ChannelBuffer r3 = r3.getBuffer((int) r4)     // Catch:{ SSLException -> 0x00b6 }
            r3.writeBytes((java.nio.ByteBuffer) r1)     // Catch:{ SSLException -> 0x00b6 }
            r1.clear()     // Catch:{ SSLException -> 0x00b6 }
            org.jboss.netty.channel.ChannelFuture r4 = org.jboss.netty.channel.Channels.future(r13)     // Catch:{ SSLException -> 0x00b6 }
            r0 = r4
            org.jboss.netty.handler.ssl.SslHandler$1 r4 = new org.jboss.netty.handler.ssl.SslHandler$1     // Catch:{ SSLException -> 0x00b6 }
            r4.<init>()     // Catch:{ SSLException -> 0x00b6 }
            r0.addListener(r4)     // Catch:{ SSLException -> 0x00b6 }
            org.jboss.netty.channel.Channels.write((org.jboss.netty.channel.ChannelHandlerContext) r12, (org.jboss.netty.channel.ChannelFuture) r0, (java.lang.Object) r3)     // Catch:{ SSLException -> 0x00b6 }
        L_0x0047:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = r2.getHandshakeStatus()     // Catch:{ SSLException -> 0x00b6 }
            r11.handleRenegotiation(r3)     // Catch:{ SSLException -> 0x00b6 }
            int[] r4 = org.jboss.netty.handler.ssl.SslHandler.C08974.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ SSLException -> 0x00b6 }
            int r5 = r3.ordinal()     // Catch:{ SSLException -> 0x00b6 }
            r4 = r4[r5]     // Catch:{ SSLException -> 0x00b6 }
            r5 = 1
            if (r4 == r5) goto L_0x009b
            r5 = 2
            if (r4 == r5) goto L_0x0088
            r5 = 3
            if (r4 == r5) goto L_0x0084
            r5 = 4
            if (r4 == r5) goto L_0x007d
            r5 = 5
            if (r4 != r5) goto L_0x0066
            goto L_0x009b
        L_0x0066:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ SSLException -> 0x00b6 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ SSLException -> 0x00b6 }
            r5.<init>()     // Catch:{ SSLException -> 0x00b6 }
            java.lang.String r6 = "Unexpected handshake status: "
            r5.append(r6)     // Catch:{ SSLException -> 0x00b6 }
            r5.append(r3)     // Catch:{ SSLException -> 0x00b6 }
            java.lang.String r5 = r5.toString()     // Catch:{ SSLException -> 0x00b6 }
            r4.<init>(r5)     // Catch:{ SSLException -> 0x00b6 }
            throw r4     // Catch:{ SSLException -> 0x00b6 }
        L_0x007d:
            r11.setHandshakeSuccess(r13)     // Catch:{ SSLException -> 0x00b6 }
            r11.runDelegatedTasks()     // Catch:{ SSLException -> 0x00b6 }
            goto L_0x009c
        L_0x0084:
            r11.runDelegatedTasks()     // Catch:{ SSLException -> 0x00b6 }
            goto L_0x009c
        L_0x0088:
            java.lang.Object r4 = r11.handshakeLock     // Catch:{ SSLException -> 0x00b6 }
            boolean r4 = java.lang.Thread.holdsLock(r4)     // Catch:{ SSLException -> 0x00b6 }
            if (r4 != 0) goto L_0x009c
            org.jboss.netty.buffer.ChannelBuffer r8 = org.jboss.netty.buffer.ChannelBuffers.EMPTY_BUFFER     // Catch:{ SSLException -> 0x00b6 }
            r9 = 0
            r10 = 0
            r5 = r11
            r6 = r12
            r7 = r13
            r5.unwrap(r6, r7, r8, r9, r10)     // Catch:{ SSLException -> 0x00b6 }
            goto L_0x009c
        L_0x009b:
        L_0x009c:
            int r4 = r2.bytesProduced()     // Catch:{ SSLException -> 0x00b6 }
            if (r4 != 0) goto L_0x00b1
            org.jboss.netty.handler.ssl.SslBufferPool r3 = r11.bufferPool
            r3.releaseBuffer(r1)
            if (r0 != 0) goto L_0x00b0
            org.jboss.netty.channel.ChannelFuture r0 = org.jboss.netty.channel.Channels.succeededFuture(r13)
        L_0x00b0:
            return r0
        L_0x00b1:
            goto L_0x0008
        L_0x00b3:
            r4 = move-exception
        L_0x00b4:
            monitor-exit(r3)     // Catch:{ all -> 0x00b8 }
            throw r4     // Catch:{ SSLException -> 0x00b6 }
        L_0x00b6:
            r3 = move-exception
            goto L_0x00bd
        L_0x00b8:
            r4 = move-exception
            goto L_0x00b4
        L_0x00ba:
            r3 = move-exception
            goto L_0x00c3
        L_0x00bc:
            r3 = move-exception
        L_0x00bd:
            r11.setHandshakeFailure(r13, r3)     // Catch:{ all -> 0x00c2 }
            throw r3     // Catch:{ all -> 0x00c2 }
        L_0x00c2:
            r3 = move-exception
        L_0x00c3:
            org.jboss.netty.handler.ssl.SslBufferPool r4 = r11.bufferPool
            r4.releaseBuffer(r1)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.handler.ssl.SslHandler.wrapNonAppData(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel):org.jboss.netty.channel.ChannelFuture");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0048, code lost:
        if (r4.getStatus() != javax.net.ssl.SSLEngineResult.Status.CLOSED) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x004a, code lost:
        r9.sslEngineCloseFuture.setClosed();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x004f, code lost:
        r6 = r4.getHandshakeStatus();
        handleRenegotiation(r6);
        r7 = org.jboss.netty.handler.ssl.SslHandler.C08974.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[r6.ordinal()];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x005f, code lost:
        if (r7 == 1) goto L_0x00e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0062, code lost:
        if (r7 == 2) goto L_0x008f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0065, code lost:
        if (r7 == 3) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0068, code lost:
        if (r7 == 4) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x006b, code lost:
        if (r7 != 5) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x006d, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0085, code lost:
        throw new java.lang.IllegalStateException("Unknown handshake status: " + r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0086, code lost:
        setHandshakeSuccess(r11);
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x008b, code lost:
        runDelegatedTasks();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0093, code lost:
        if (r0.hasRemaining() == false) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x009b, code lost:
        if (r9.engine.isInboundDone() != false) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
        wrapNonAppData(r10, r11);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.jboss.netty.buffer.ChannelBuffer unwrap(org.jboss.netty.channel.ChannelHandlerContext r10, org.jboss.netty.channel.Channel r11, org.jboss.netty.buffer.ChannelBuffer r12, int r13, int r14) throws javax.net.ssl.SSLException {
        /*
            r9 = this;
            java.nio.ByteBuffer r0 = r12.toByteBuffer(r13, r14)
            org.jboss.netty.handler.ssl.SslBufferPool r1 = r9.bufferPool
            java.nio.ByteBuffer r1 = r1.acquireBuffer()
            r2 = 0
            r3 = 0
            r4 = r3
        L_0x000d:
            r5 = 0
            java.lang.Object r6 = r9.handshakeLock     // Catch:{ SSLException -> 0x00f2 }
            monitor-enter(r6)     // Catch:{ SSLException -> 0x00f2 }
            boolean r7 = r9.handshaken     // Catch:{ all -> 0x00ed }
            if (r7 != 0) goto L_0x0032
            boolean r7 = r9.handshaking     // Catch:{ all -> 0x00ed }
            if (r7 != 0) goto L_0x0032
            javax.net.ssl.SSLEngine r7 = r9.engine     // Catch:{ all -> 0x00ed }
            boolean r7 = r7.getUseClientMode()     // Catch:{ all -> 0x00ed }
            if (r7 != 0) goto L_0x0032
            javax.net.ssl.SSLEngine r7 = r9.engine     // Catch:{ all -> 0x00ed }
            boolean r7 = r7.isInboundDone()     // Catch:{ all -> 0x00ed }
            if (r7 != 0) goto L_0x0032
            javax.net.ssl.SSLEngine r7 = r9.engine     // Catch:{ all -> 0x00ed }
            boolean r7 = r7.isOutboundDone()     // Catch:{ all -> 0x00ed }
            if (r7 != 0) goto L_0x0032
            r5 = 1
        L_0x0032:
            monitor-exit(r6)     // Catch:{ all -> 0x00ed }
            if (r5 == 0) goto L_0x0038
            r9.handshake()     // Catch:{ SSLException -> 0x00f2 }
        L_0x0038:
            java.lang.Object r6 = r9.handshakeLock     // Catch:{ SSLException -> 0x00f2 }
            monitor-enter(r6)     // Catch:{ SSLException -> 0x00f2 }
            javax.net.ssl.SSLEngine r7 = r9.engine     // Catch:{ all -> 0x00e8 }
            javax.net.ssl.SSLEngineResult r4 = r7.unwrap(r0, r1)     // Catch:{ all -> 0x00e8 }
            monitor-exit(r6)     // Catch:{ all -> 0x00eb }
            javax.net.ssl.SSLEngineResult$Status r6 = r4.getStatus()     // Catch:{ SSLException -> 0x00f2 }
            javax.net.ssl.SSLEngineResult$Status r7 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ SSLException -> 0x00f2 }
            if (r6 != r7) goto L_0x004f
            org.jboss.netty.handler.ssl.SslHandler$SSLEngineInboundCloseFuture r6 = r9.sslEngineCloseFuture     // Catch:{ SSLException -> 0x00f2 }
            r6.setClosed()     // Catch:{ SSLException -> 0x00f2 }
        L_0x004f:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r6 = r4.getHandshakeStatus()     // Catch:{ SSLException -> 0x00f2 }
            r9.handleRenegotiation(r6)     // Catch:{ SSLException -> 0x00f2 }
            int[] r7 = org.jboss.netty.handler.ssl.SslHandler.C08974.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ SSLException -> 0x00f2 }
            int r8 = r6.ordinal()     // Catch:{ SSLException -> 0x00f2 }
            r7 = r7[r8]     // Catch:{ SSLException -> 0x00f2 }
            r8 = 1
            if (r7 == r8) goto L_0x00e2
            r8 = 2
            if (r7 == r8) goto L_0x008f
            r8 = 3
            if (r7 == r8) goto L_0x008b
            r8 = 4
            if (r7 == r8) goto L_0x0086
            r8 = 5
            if (r7 != r8) goto L_0x006f
            r2 = 1
            goto L_0x009e
        L_0x006f:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch:{ SSLException -> 0x00f2 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ SSLException -> 0x00f2 }
            r7.<init>()     // Catch:{ SSLException -> 0x00f2 }
            java.lang.String r8 = "Unknown handshake status: "
            r7.append(r8)     // Catch:{ SSLException -> 0x00f2 }
            r7.append(r6)     // Catch:{ SSLException -> 0x00f2 }
            java.lang.String r7 = r7.toString()     // Catch:{ SSLException -> 0x00f2 }
            r3.<init>(r7)     // Catch:{ SSLException -> 0x00f2 }
            throw r3     // Catch:{ SSLException -> 0x00f2 }
        L_0x0086:
            r9.setHandshakeSuccess(r11)     // Catch:{ SSLException -> 0x00f2 }
            r2 = 1
            goto L_0x009e
        L_0x008b:
            r9.runDelegatedTasks()     // Catch:{ SSLException -> 0x00f2 }
            goto L_0x00e6
        L_0x008f:
            boolean r7 = r0.hasRemaining()     // Catch:{ SSLException -> 0x00f2 }
            if (r7 == 0) goto L_0x009e
            javax.net.ssl.SSLEngine r7 = r9.engine     // Catch:{ SSLException -> 0x00f2 }
            boolean r7 = r7.isInboundDone()     // Catch:{ SSLException -> 0x00f2 }
            if (r7 != 0) goto L_0x009e
            goto L_0x00e6
        L_0x009e:
            if (r2 == 0) goto L_0x00b3
            java.lang.Object r4 = r9.handshakeLock     // Catch:{ SSLException -> 0x00f2 }
            boolean r4 = java.lang.Thread.holdsLock(r4)     // Catch:{ SSLException -> 0x00f2 }
            if (r4 != 0) goto L_0x00b3
            org.jboss.netty.util.internal.NonReentrantLock r4 = r9.pendingEncryptedWritesLock     // Catch:{ SSLException -> 0x00f2 }
            boolean r4 = r4.isHeldByCurrentThread()     // Catch:{ SSLException -> 0x00f2 }
            if (r4 != 0) goto L_0x00b3
            r9.wrap(r10, r11)     // Catch:{ SSLException -> 0x00f2 }
        L_0x00b3:
            r1.flip()     // Catch:{ SSLException -> 0x00f2 }
            boolean r4 = r1.hasRemaining()     // Catch:{ SSLException -> 0x00f2 }
            if (r4 == 0) goto L_0x00db
            org.jboss.netty.channel.Channel r3 = r10.getChannel()     // Catch:{ SSLException -> 0x00f2 }
            org.jboss.netty.channel.ChannelConfig r3 = r3.getConfig()     // Catch:{ SSLException -> 0x00f2 }
            org.jboss.netty.buffer.ChannelBufferFactory r3 = r3.getBufferFactory()     // Catch:{ SSLException -> 0x00f2 }
            int r4 = r1.remaining()     // Catch:{ SSLException -> 0x00f2 }
            org.jboss.netty.buffer.ChannelBuffer r3 = r3.getBuffer((int) r4)     // Catch:{ SSLException -> 0x00f2 }
            r3.writeBytes((java.nio.ByteBuffer) r1)     // Catch:{ SSLException -> 0x00f2 }
            org.jboss.netty.handler.ssl.SslBufferPool r4 = r9.bufferPool
            r4.releaseBuffer(r1)
            r4 = r3
            return r3
        L_0x00db:
            org.jboss.netty.handler.ssl.SslBufferPool r4 = r9.bufferPool
            r4.releaseBuffer(r1)
            return r3
        L_0x00e2:
            r9.wrapNonAppData(r10, r11)     // Catch:{ SSLException -> 0x00f2 }
        L_0x00e6:
            goto L_0x000d
        L_0x00e8:
            r3 = move-exception
        L_0x00e9:
            monitor-exit(r6)     // Catch:{ all -> 0x00eb }
            throw r3     // Catch:{ SSLException -> 0x00f2 }
        L_0x00eb:
            r3 = move-exception
            goto L_0x00e9
        L_0x00ed:
            r3 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x00ed }
            throw r3     // Catch:{ SSLException -> 0x00f2 }
        L_0x00f0:
            r2 = move-exception
            goto L_0x00f8
        L_0x00f2:
            r2 = move-exception
            r9.setHandshakeFailure(r11, r2)     // Catch:{ all -> 0x00f0 }
            throw r2     // Catch:{ all -> 0x00f0 }
        L_0x00f8:
            org.jboss.netty.handler.ssl.SslBufferPool r3 = r9.bufferPool
            r3.releaseBuffer(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.handler.ssl.SslHandler.unwrap(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer, int, int):org.jboss.netty.buffer.ChannelBuffer");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0036, code lost:
        if (r1 == false) goto L_0x003c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0038, code lost:
        handshake();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x003c, code lost:
        org.jboss.netty.channel.Channels.fireExceptionCaught(r4.ctx, (java.lang.Throwable) new javax.net.ssl.SSLException("renegotiation attempted by peer; closing the connection"));
        org.jboss.netty.channel.Channels.close(r4.ctx, org.jboss.netty.channel.Channels.succeededFuture(r4.ctx.getChannel()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleRenegotiation(javax.net.ssl.SSLEngineResult.HandshakeStatus r5) {
        /*
            r4 = this;
            javax.net.ssl.SSLEngineResult$HandshakeStatus r0 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING
            if (r5 == r0) goto L_0x005f
            javax.net.ssl.SSLEngineResult$HandshakeStatus r0 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED
            if (r5 != r0) goto L_0x0009
            goto L_0x005f
        L_0x0009:
            boolean r0 = r4.handshaken
            if (r0 != 0) goto L_0x000e
            return
        L_0x000e:
            java.lang.Object r0 = r4.handshakeLock
            monitor-enter(r0)
            r1 = 0
            boolean r2 = r4.handshaking     // Catch:{ all -> 0x005a }
            if (r2 == 0) goto L_0x0018
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            return
        L_0x0018:
            javax.net.ssl.SSLEngine r2 = r4.engine     // Catch:{ all -> 0x005a }
            boolean r2 = r2.isInboundDone()     // Catch:{ all -> 0x005a }
            if (r2 != 0) goto L_0x0058
            javax.net.ssl.SSLEngine r2 = r4.engine     // Catch:{ all -> 0x005a }
            boolean r2 = r2.isOutboundDone()     // Catch:{ all -> 0x005a }
            if (r2 == 0) goto L_0x0029
            goto L_0x0058
        L_0x0029:
            boolean r1 = r4.isEnableRenegotiation()     // Catch:{ all -> 0x005a }
            if (r1 == 0) goto L_0x0031
            r1 = 1
            goto L_0x0035
        L_0x0031:
            r1 = 0
            r2 = 1
            r4.handshaking = r2     // Catch:{ all -> 0x005d }
        L_0x0035:
            monitor-exit(r0)     // Catch:{ all -> 0x005d }
            if (r1 == 0) goto L_0x003c
            r4.handshake()
            goto L_0x0057
        L_0x003c:
            org.jboss.netty.channel.ChannelHandlerContext r0 = r4.ctx
            javax.net.ssl.SSLException r2 = new javax.net.ssl.SSLException
            java.lang.String r3 = "renegotiation attempted by peer; closing the connection"
            r2.<init>(r3)
            org.jboss.netty.channel.Channels.fireExceptionCaught((org.jboss.netty.channel.ChannelHandlerContext) r0, (java.lang.Throwable) r2)
            org.jboss.netty.channel.ChannelHandlerContext r0 = r4.ctx
            org.jboss.netty.channel.ChannelHandlerContext r2 = r4.ctx
            org.jboss.netty.channel.Channel r2 = r2.getChannel()
            org.jboss.netty.channel.ChannelFuture r2 = org.jboss.netty.channel.Channels.succeededFuture(r2)
            org.jboss.netty.channel.Channels.close(r0, r2)
        L_0x0057:
            return
        L_0x0058:
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            return
        L_0x005a:
            r2 = move-exception
        L_0x005b:
            monitor-exit(r0)     // Catch:{ all -> 0x005d }
            throw r2
        L_0x005d:
            r2 = move-exception
            goto L_0x005b
        L_0x005f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.handler.ssl.SslHandler.handleRenegotiation(javax.net.ssl.SSLEngineResult$HandshakeStatus):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000b, code lost:
        if (r0 != null) goto L_0x000f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void runDelegatedTasks() {
        /*
            r3 = this;
            r0 = 0
        L_0x0001:
            java.lang.Object r1 = r3.handshakeLock
            monitor-enter(r1)
            javax.net.ssl.SSLEngine r2 = r3.engine     // Catch:{ all -> 0x001a }
            java.lang.Runnable r0 = r2.getDelegatedTask()     // Catch:{ all -> 0x001a }
            monitor-exit(r1)     // Catch:{ all -> 0x001d }
            if (r0 != 0) goto L_0x000f
            return
        L_0x000f:
            java.util.concurrent.Executor r1 = r3.delegatedTaskExecutor
            org.jboss.netty.handler.ssl.SslHandler$2 r2 = new org.jboss.netty.handler.ssl.SslHandler$2
            r2.<init>(r0)
            r1.execute(r2)
            goto L_0x0001
        L_0x001a:
            r2 = move-exception
        L_0x001b:
            monitor-exit(r1)     // Catch:{ all -> 0x001d }
            throw r2
        L_0x001d:
            r2 = move-exception
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.handler.ssl.SslHandler.runDelegatedTasks():void");
    }

    private void setHandshakeSuccess(Channel channel) {
        synchronized (this.handshakeLock) {
            this.handshaking = false;
            this.handshaken = true;
            if (this.handshakeFuture == null) {
                this.handshakeFuture = Channels.future(channel);
            }
        }
        this.handshakeFuture.setSuccess();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0034, code lost:
        r4.handshakeFuture.setFailure(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0039, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setHandshakeFailure(org.jboss.netty.channel.Channel r5, javax.net.ssl.SSLException r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.handshakeLock
            monitor-enter(r0)
            boolean r1 = r4.handshaking     // Catch:{ all -> 0x003a }
            if (r1 != 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            return
        L_0x0009:
            r1 = 0
            r4.handshaking = r1     // Catch:{ all -> 0x003a }
            r4.handshaken = r1     // Catch:{ all -> 0x003a }
            org.jboss.netty.channel.ChannelFuture r1 = r4.handshakeFuture     // Catch:{ all -> 0x003a }
            if (r1 != 0) goto L_0x0018
            org.jboss.netty.channel.ChannelFuture r1 = org.jboss.netty.channel.Channels.future(r5)     // Catch:{ all -> 0x003a }
            r4.handshakeFuture = r1     // Catch:{ all -> 0x003a }
        L_0x0018:
            javax.net.ssl.SSLEngine r1 = r4.engine     // Catch:{ all -> 0x003a }
            r1.closeOutbound()     // Catch:{ all -> 0x003a }
            javax.net.ssl.SSLEngine r1 = r4.engine     // Catch:{ SSLException -> 0x0023 }
            r1.closeInbound()     // Catch:{ SSLException -> 0x0023 }
            goto L_0x0033
        L_0x0023:
            r1 = move-exception
            org.jboss.netty.logging.InternalLogger r2 = logger     // Catch:{ all -> 0x003a }
            boolean r2 = r2.isDebugEnabled()     // Catch:{ all -> 0x003a }
            if (r2 == 0) goto L_0x0033
            org.jboss.netty.logging.InternalLogger r2 = logger     // Catch:{ all -> 0x003a }
            java.lang.String r3 = "SSLEngine.closeInbound() raised an exception after a handshake failure."
            r2.debug(r3, r1)     // Catch:{ all -> 0x003a }
        L_0x0033:
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            org.jboss.netty.channel.ChannelFuture r0 = r4.handshakeFuture
            r0.setFailure(r6)
            return
        L_0x003a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.handler.ssl.SslHandler.setHandshakeFailure(org.jboss.netty.channel.Channel, javax.net.ssl.SSLException):void");
    }

    private void closeOutboundAndChannel(ChannelHandlerContext context, ChannelStateEvent e) {
        if (!e.getChannel().isConnected()) {
            context.sendDownstream(e);
            return;
        }
        boolean success = false;
        try {
            unwrap(context, e.getChannel(), ChannelBuffers.EMPTY_BUFFER, 0, 0);
        } catch (SSLException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("Failed to unwrap before sending a close_notify message", ex);
            }
        } catch (Throwable th) {
            if (0 == 0) {
                context.sendDownstream(e);
            }
            throw th;
        }
        if (this.engine.isInboundDone()) {
            success = true;
        } else if (this.sentCloseNotify.compareAndSet(false, true)) {
            this.engine.closeOutbound();
            try {
                wrapNonAppData(context, e.getChannel()).addListener(new ClosingChannelFutureListener(context, e));
                success = true;
            } catch (SSLException ex2) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Failed to encode a close_notify message", ex2);
                }
            }
        }
        if (!success) {
            context.sendDownstream(e);
        }
    }

    private static final class PendingWrite {
        final ChannelFuture future;
        final ByteBuffer outAppBuf;

        PendingWrite(ChannelFuture future2, ByteBuffer outAppBuf2) {
            this.future = future2;
            this.outAppBuf = outAppBuf2;
        }
    }

    private static final class ClosingChannelFutureListener implements ChannelFutureListener {
        private final ChannelHandlerContext context;

        /* renamed from: e */
        private final ChannelStateEvent f147e;

        ClosingChannelFutureListener(ChannelHandlerContext context2, ChannelStateEvent e) {
            this.context = context2;
            this.f147e = e;
        }

        public void operationComplete(ChannelFuture closeNotifyFuture) throws Exception {
            if (!(closeNotifyFuture.getCause() instanceof ClosedChannelException)) {
                Channels.close(this.context, this.f147e.getFuture());
            } else {
                this.f147e.getFuture().setSuccess();
            }
        }
    }

    public void beforeAdd(ChannelHandlerContext ctx2) throws Exception {
        super.beforeAdd(ctx2);
        this.ctx = ctx2;
    }

    public void afterRemove(ChannelHandlerContext ctx2) throws Exception {
        Throwable cause = null;
        while (true) {
            PendingWrite pw = this.pendingUnencryptedWrites.poll();
            if (pw == null) {
                break;
            }
            if (cause == null) {
                cause = new IOException("Unable to write data");
            }
            pw.future.setFailure(cause);
        }
        while (true) {
            MessageEvent ev = this.pendingEncryptedWrites.poll();
            if (ev == null) {
                break;
            }
            if (cause == null) {
                cause = new IOException("Unable to write data");
            }
            ev.getFuture().setFailure(cause);
        }
        if (cause != null) {
            Channels.fireExceptionCaughtLater(ctx2, cause);
        }
    }

    public void channelConnected(final ChannelHandlerContext ctx2, final ChannelStateEvent e) throws Exception {
        if (this.issueHandshake) {
            handshake().addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        Channels.fireExceptionCaught(future.getChannel(), future.getCause());
                    } else {
                        ctx2.sendUpstream(e);
                    }
                }
            });
        } else {
            super.channelConnected(ctx2, e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        if (r0 != null) goto L_0x002c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r0 = new java.nio.channels.ClosedChannelException();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002c, code lost:
        r2.getFuture().setFailure(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000f, code lost:
        r2 = r4.pendingEncryptedWrites.poll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0017, code lost:
        if (r2 != null) goto L_0x0024;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void channelClosed(org.jboss.netty.channel.ChannelHandlerContext r5, org.jboss.netty.channel.ChannelStateEvent r6) throws java.lang.Exception {
        /*
            r4 = this;
            r0 = 0
            java.util.Queue<org.jboss.netty.handler.ssl.SslHandler$PendingWrite> r1 = r4.pendingUnencryptedWrites
            monitor-enter(r1)
        L_0x0004:
            java.util.Queue<org.jboss.netty.handler.ssl.SslHandler$PendingWrite> r2 = r4.pendingUnencryptedWrites     // Catch:{ all -> 0x0044 }
            java.lang.Object r2 = r2.poll()     // Catch:{ all -> 0x0044 }
            org.jboss.netty.handler.ssl.SslHandler$PendingWrite r2 = (org.jboss.netty.handler.ssl.SslHandler.PendingWrite) r2     // Catch:{ all -> 0x0044 }
            if (r2 != 0) goto L_0x0035
        L_0x000f:
            java.util.Queue<org.jboss.netty.channel.MessageEvent> r2 = r4.pendingEncryptedWrites     // Catch:{ all -> 0x0044 }
            java.lang.Object r2 = r2.poll()     // Catch:{ all -> 0x0044 }
            org.jboss.netty.channel.MessageEvent r2 = (org.jboss.netty.channel.MessageEvent) r2     // Catch:{ all -> 0x0044 }
            if (r2 != 0) goto L_0x0024
            monitor-exit(r1)     // Catch:{ all -> 0x0044 }
            if (r0 == 0) goto L_0x0020
            org.jboss.netty.channel.Channels.fireExceptionCaught((org.jboss.netty.channel.ChannelHandlerContext) r5, (java.lang.Throwable) r0)
        L_0x0020:
            super.channelClosed(r5, r6)
            return
        L_0x0024:
            if (r0 != 0) goto L_0x002c
            java.nio.channels.ClosedChannelException r3 = new java.nio.channels.ClosedChannelException     // Catch:{ all -> 0x0044 }
            r3.<init>()     // Catch:{ all -> 0x0044 }
            r0 = r3
        L_0x002c:
            org.jboss.netty.channel.ChannelFuture r3 = r2.getFuture()     // Catch:{ all -> 0x0044 }
            r3.setFailure(r0)     // Catch:{ all -> 0x0044 }
            goto L_0x000f
        L_0x0035:
            if (r0 != 0) goto L_0x003d
            java.nio.channels.ClosedChannelException r3 = new java.nio.channels.ClosedChannelException     // Catch:{ all -> 0x0044 }
            r3.<init>()     // Catch:{ all -> 0x0044 }
            r0 = r3
        L_0x003d:
            org.jboss.netty.channel.ChannelFuture r3 = r2.future     // Catch:{ all -> 0x0044 }
            r3.setFailure(r0)     // Catch:{ all -> 0x0044 }
            goto L_0x0004
        L_0x0044:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0044 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.handler.ssl.SslHandler.channelClosed(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent):void");
    }

    private final class SSLEngineInboundCloseFuture extends DefaultChannelFuture {
        public SSLEngineInboundCloseFuture() {
            super((Channel) null, true);
        }

        /* access modifiers changed from: package-private */
        public void setClosed() {
            super.setSuccess();
        }

        public Channel getChannel() {
            if (SslHandler.this.ctx == null) {
                return null;
            }
            return SslHandler.this.ctx.getChannel();
        }

        public boolean setSuccess() {
            return false;
        }

        public boolean setFailure(Throwable cause) {
            return false;
        }
    }
}
