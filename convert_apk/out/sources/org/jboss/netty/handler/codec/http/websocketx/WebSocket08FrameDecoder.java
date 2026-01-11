package org.jboss.netty.handler.codec.http.websocketx;

import kotlin.jvm.internal.LongCompanionObject;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.frame.CorruptedFrameException;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.xbill.DNS.TTL;

public class WebSocket08FrameDecoder extends ReplayingDecoder<State> {
    private static final byte OPCODE_BINARY = 2;
    private static final byte OPCODE_CLOSE = 8;
    private static final byte OPCODE_CONT = 0;
    private static final byte OPCODE_PING = 9;
    private static final byte OPCODE_PONG = 10;
    private static final byte OPCODE_TEXT = 1;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) WebSocket08FrameDecoder.class);
    private final boolean allowExtensions;
    private int fragmentedFramesCount;
    private UTF8Output fragmentedFramesText;
    private boolean frameFinalFlag;
    private int frameOpcode;
    private ChannelBuffer framePayload;
    private int framePayloadBytesRead;
    private long framePayloadLength;
    private int frameRsv;
    private final boolean maskedPayload;
    private ChannelBuffer maskingKey;
    private final long maxFramePayloadLength;
    private boolean receivedClosingHandshake;

    public enum State {
        FRAME_START,
        MASKING_KEY,
        PAYLOAD,
        CORRUPT
    }

    public WebSocket08FrameDecoder(boolean maskedPayload2, boolean allowExtensions2) {
        this(maskedPayload2, allowExtensions2, LongCompanionObject.MAX_VALUE);
    }

    public WebSocket08FrameDecoder(boolean maskedPayload2, boolean allowExtensions2, long maxFramePayloadLength2) {
        super(State.FRAME_START);
        this.maskedPayload = maskedPayload2;
        this.allowExtensions = allowExtensions2;
        this.maxFramePayloadLength = maxFramePayloadLength2;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x01e4  */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x01e9  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x022b  */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x022e  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x0235  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x023e  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x024a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object decode(org.jboss.netty.channel.ChannelHandlerContext r21, org.jboss.netty.channel.Channel r22, org.jboss.netty.buffer.ChannelBuffer r23, org.jboss.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.State r24) throws java.lang.Exception {
        /*
            r20 = this;
            r0 = r20
            r1 = r22
            r2 = r23
            boolean r3 = r0.receivedClosingHandshake
            r4 = 0
            if (r3 == 0) goto L_0x0013
            int r3 = r20.actualReadableBytes()
            r2.skipBytes((int) r3)
            return r4
        L_0x0013:
            int[] r3 = org.jboss.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.C08801.f136x43ecfa85
            int r5 = r24.ordinal()
            r3 = r3[r5]
            r5 = 10
            r6 = 8
            r7 = 4
            r8 = 2
            r9 = 9
            r10 = 0
            r11 = 1
            if (r3 == r11) goto L_0x004f
            if (r3 == r8) goto L_0x004a
            r12 = 3
            if (r3 == r12) goto L_0x0045
            r5 = 0
            if (r3 != r7) goto L_0x0039
            r3 = r10
            r7 = r4
            r8 = r10
            r9 = r4
            r11 = r10
            r23.readByte()
            return r4
        L_0x0039:
            r3 = r10
            r7 = r4
            r8 = r10
            r9 = r10
            java.lang.Error r11 = new java.lang.Error
            java.lang.String r12 = "Shouldn't reach here."
            r11.<init>(r12)
            throw r11
        L_0x0045:
            r3 = r10
            r7 = r10
            r12 = r10
            goto L_0x01d2
        L_0x004a:
            r3 = r10
            r12 = r10
            r13 = r10
            goto L_0x01c1
        L_0x004f:
            r0.framePayloadBytesRead = r10
            r12 = -1
            r0.framePayloadLength = r12
            r0.framePayload = r4
            byte r3 = r23.readByte()
            r12 = r3 & 128(0x80, float:1.794E-43)
            if (r12 == 0) goto L_0x0061
            r12 = r11
            goto L_0x0062
        L_0x0061:
            r12 = r10
        L_0x0062:
            r0.frameFinalFlag = r12
            r12 = r3 & 112(0x70, float:1.57E-43)
            int r12 = r12 >> r7
            r0.frameRsv = r12
            r12 = r3 & 15
            r0.frameOpcode = r12
            org.jboss.netty.logging.InternalLogger r12 = logger
            boolean r12 = r12.isDebugEnabled()
            if (r12 == 0) goto L_0x008d
            org.jboss.netty.logging.InternalLogger r12 = logger
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.String r14 = "Decoding WebSocket Frame opCode="
            r13.append(r14)
            int r14 = r0.frameOpcode
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r12.debug(r13)
        L_0x008d:
            byte r3 = r23.readByte()
            r12 = r3 & 128(0x80, float:1.794E-43)
            if (r12 == 0) goto L_0x0097
            r12 = r11
            goto L_0x0098
        L_0x0097:
            r12 = r10
        L_0x0098:
            r13 = r3 & 127(0x7f, float:1.78E-43)
            int r14 = r0.frameRsv
            if (r14 == 0) goto L_0x00b9
            boolean r14 = r0.allowExtensions
            if (r14 != 0) goto L_0x00b9
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "RSV != 0 and no extension negotiated, RSV:"
            r5.append(r6)
            int r6 = r0.frameRsv
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r0.protocolViolation(r1, r5)
            return r4
        L_0x00b9:
            boolean r14 = r0.maskedPayload
            if (r14 == 0) goto L_0x00c6
            if (r12 != 0) goto L_0x00c6
            java.lang.String r5 = "unmasked client to server frame"
            r0.protocolViolation(r1, r5)
            return r4
        L_0x00c6:
            int r14 = r0.frameOpcode
            r15 = 7
            if (r14 <= r15) goto L_0x0108
            boolean r15 = r0.frameFinalFlag
            if (r15 != 0) goto L_0x00d5
            java.lang.String r5 = "fragmented control frame"
            r0.protocolViolation(r1, r5)
            return r4
        L_0x00d5:
            r15 = 125(0x7d, float:1.75E-43)
            if (r13 <= r15) goto L_0x00df
            java.lang.String r5 = "control frame with payload length > 125 octets"
            r0.protocolViolation(r1, r5)
            return r4
        L_0x00df:
            if (r14 == r6) goto L_0x00fc
            if (r14 == r9) goto L_0x00fc
            if (r14 == r5) goto L_0x00fc
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "control frame using reserved opcode "
            r5.append(r6)
            int r6 = r0.frameOpcode
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r0.protocolViolation(r1, r5)
            return r4
        L_0x00fc:
            int r14 = r0.frameOpcode
            if (r14 != r6) goto L_0x0143
            if (r13 != r11) goto L_0x0143
            java.lang.String r5 = "received close control frame with payload len 1"
            r0.protocolViolation(r1, r5)
            return r4
        L_0x0108:
            if (r14 == 0) goto L_0x0125
            if (r14 == r11) goto L_0x0125
            if (r14 == r8) goto L_0x0125
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "data frame using reserved opcode "
            r5.append(r6)
            int r6 = r0.frameOpcode
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r0.protocolViolation(r1, r5)
            return r4
        L_0x0125:
            int r14 = r0.fragmentedFramesCount
            if (r14 != 0) goto L_0x0133
            int r14 = r0.frameOpcode
            if (r14 != 0) goto L_0x0133
            java.lang.String r5 = "received continuation data frame outside fragmented message"
            r0.protocolViolation(r1, r5)
            return r4
        L_0x0133:
            int r14 = r0.fragmentedFramesCount
            if (r14 == 0) goto L_0x0143
            int r14 = r0.frameOpcode
            if (r14 == 0) goto L_0x0143
            if (r14 == r9) goto L_0x0143
            java.lang.String r5 = "received non-continuation data frame while inside fragmented message"
            r0.protocolViolation(r1, r5)
            return r4
        L_0x0143:
            r14 = 126(0x7e, float:1.77E-43)
            java.lang.String r15 = "invalid data frame length (not using minimal length encoding)"
            if (r13 != r14) goto L_0x015a
            int r14 = r23.readUnsignedShort()
            long r10 = (long) r14
            r0.framePayloadLength = r10
            r16 = 126(0x7e, double:6.23E-322)
            int r10 = (r10 > r16 ? 1 : (r10 == r16 ? 0 : -1))
            if (r10 >= 0) goto L_0x0172
            r0.protocolViolation(r1, r15)
            return r4
        L_0x015a:
            r10 = 127(0x7f, float:1.78E-43)
            if (r13 != r10) goto L_0x016f
            long r10 = r23.readLong()
            r0.framePayloadLength = r10
            r16 = 65536(0x10000, double:3.2379E-319)
            int r10 = (r10 > r16 ? 1 : (r10 == r16 ? 0 : -1))
            if (r10 >= 0) goto L_0x0172
            r0.protocolViolation(r1, r15)
            return r4
        L_0x016f:
            long r10 = (long) r13
            r0.framePayloadLength = r10
        L_0x0172:
            long r10 = r0.framePayloadLength
            long r14 = r0.maxFramePayloadLength
            int r10 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1))
            if (r10 <= 0) goto L_0x0196
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Max frame length of "
            r5.append(r6)
            long r6 = r0.maxFramePayloadLength
            r5.append(r6)
            java.lang.String r6 = " has been exceeded."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r0.protocolViolation(r1, r5)
            return r4
        L_0x0196:
            org.jboss.netty.logging.InternalLogger r10 = logger
            boolean r10 = r10.isDebugEnabled()
            if (r10 == 0) goto L_0x01b6
            org.jboss.netty.logging.InternalLogger r10 = logger
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r14 = "Decoding WebSocket Frame length="
            r11.append(r14)
            long r14 = r0.framePayloadLength
            r11.append(r14)
            java.lang.String r11 = r11.toString()
            r10.debug(r11)
        L_0x01b6:
            org.jboss.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder$State r10 = org.jboss.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.State.MASKING_KEY
            r0.checkpoint(r10)
            r19 = r13
            r13 = r3
            r3 = r12
            r12 = r19
        L_0x01c1:
            boolean r10 = r0.maskedPayload
            if (r10 == 0) goto L_0x01cb
            org.jboss.netty.buffer.ChannelBuffer r7 = r2.readBytes((int) r7)
            r0.maskingKey = r7
        L_0x01cb:
            org.jboss.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder$State r7 = org.jboss.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.State.PAYLOAD
            r0.checkpoint(r7)
            r7 = r12
            r12 = r13
        L_0x01d2:
            int r10 = r20.actualReadableBytes()
            r11 = 0
            int r13 = r0.framePayloadBytesRead
            int r14 = r13 + r10
            long r14 = (long) r14
            r17 = r7
            long r6 = r0.framePayloadLength
            int r18 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
            if (r18 != 0) goto L_0x01e9
            org.jboss.netty.buffer.ChannelBuffer r11 = r2.readBytes((int) r10)
            goto L_0x0222
        L_0x01e9:
            int r18 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
            if (r18 >= 0) goto L_0x0214
            org.jboss.netty.buffer.ChannelBuffer r5 = r2.readBytes((int) r10)
            org.jboss.netty.buffer.ChannelBuffer r6 = r0.framePayload
            if (r6 != 0) goto L_0x0209
            org.jboss.netty.channel.ChannelConfig r6 = r22.getConfig()
            org.jboss.netty.buffer.ChannelBufferFactory r6 = r6.getBufferFactory()
            long r7 = r0.framePayloadLength
            int r7 = toFrameLength(r7)
            org.jboss.netty.buffer.ChannelBuffer r6 = r6.getBuffer((int) r7)
            r0.framePayload = r6
        L_0x0209:
            org.jboss.netty.buffer.ChannelBuffer r6 = r0.framePayload
            r6.writeBytes((org.jboss.netty.buffer.ChannelBuffer) r5)
            int r6 = r0.framePayloadBytesRead
            int r6 = r6 + r10
            r0.framePayloadBytesRead = r6
            return r4
        L_0x0214:
            int r18 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
            if (r18 <= 0) goto L_0x0222
            long r4 = (long) r13
            long r6 = r6 - r4
            int r4 = toFrameLength(r6)
            org.jboss.netty.buffer.ChannelBuffer r11 = r2.readBytes((int) r4)
        L_0x0222:
            org.jboss.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder$State r4 = org.jboss.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.State.FRAME_START
            r0.checkpoint(r4)
            org.jboss.netty.buffer.ChannelBuffer r4 = r0.framePayload
            if (r4 != 0) goto L_0x022e
            r0.framePayload = r11
            goto L_0x0231
        L_0x022e:
            r4.writeBytes((org.jboss.netty.buffer.ChannelBuffer) r11)
        L_0x0231:
            boolean r4 = r0.maskedPayload
            if (r4 == 0) goto L_0x023a
            org.jboss.netty.buffer.ChannelBuffer r4 = r0.framePayload
            r0.unmask(r4)
        L_0x023a:
            int r4 = r0.frameOpcode
            if (r4 != r9) goto L_0x024a
            org.jboss.netty.handler.codec.http.websocketx.PingWebSocketFrame r4 = new org.jboss.netty.handler.codec.http.websocketx.PingWebSocketFrame
            boolean r5 = r0.frameFinalFlag
            int r6 = r0.frameRsv
            org.jboss.netty.buffer.ChannelBuffer r7 = r0.framePayload
            r4.<init>(r5, r6, r7)
            return r4
        L_0x024a:
            r5 = 10
            if (r4 != r5) goto L_0x025a
            org.jboss.netty.handler.codec.http.websocketx.PongWebSocketFrame r4 = new org.jboss.netty.handler.codec.http.websocketx.PongWebSocketFrame
            boolean r5 = r0.frameFinalFlag
            int r6 = r0.frameRsv
            org.jboss.netty.buffer.ChannelBuffer r7 = r0.framePayload
            r4.<init>(r5, r6, r7)
            return r4
        L_0x025a:
            r5 = 8
            if (r4 != r5) goto L_0x0272
            org.jboss.netty.buffer.ChannelBuffer r4 = r0.framePayload
            r0.checkCloseFrameBody(r1, r4)
            r4 = 1
            r0.receivedClosingHandshake = r4
            org.jboss.netty.handler.codec.http.websocketx.CloseWebSocketFrame r4 = new org.jboss.netty.handler.codec.http.websocketx.CloseWebSocketFrame
            boolean r5 = r0.frameFinalFlag
            int r6 = r0.frameRsv
            org.jboss.netty.buffer.ChannelBuffer r7 = r0.framePayload
            r4.<init>(r5, r6, r7)
            return r4
        L_0x0272:
            r5 = 0
            boolean r6 = r0.frameFinalFlag
            if (r6 == 0) goto L_0x029c
            if (r4 == r9) goto L_0x029a
            r6 = 0
            r0.fragmentedFramesCount = r6
            r6 = 1
            if (r4 == r6) goto L_0x0286
            org.jboss.netty.handler.codec.http.websocketx.UTF8Output r4 = r0.fragmentedFramesText
            if (r4 == 0) goto L_0x0284
            goto L_0x0286
        L_0x0284:
            r6 = 1
            goto L_0x02c3
        L_0x0286:
            org.jboss.netty.buffer.ChannelBuffer r4 = r0.framePayload
            byte[] r4 = r4.array()
            r0.checkUTF8String(r1, r4)
            org.jboss.netty.handler.codec.http.websocketx.UTF8Output r4 = r0.fragmentedFramesText
            java.lang.String r5 = r4.toString()
            r6 = 0
            r0.fragmentedFramesText = r6
            r6 = 1
            goto L_0x02c3
        L_0x029a:
            r6 = 1
            goto L_0x02c3
        L_0x029c:
            r6 = 0
            int r7 = r0.fragmentedFramesCount
            if (r7 != 0) goto L_0x02b0
            r0.fragmentedFramesText = r6
            r6 = 1
            if (r4 != r6) goto L_0x02bd
            org.jboss.netty.buffer.ChannelBuffer r4 = r0.framePayload
            byte[] r4 = r4.array()
            r0.checkUTF8String(r1, r4)
            goto L_0x02bd
        L_0x02b0:
            org.jboss.netty.handler.codec.http.websocketx.UTF8Output r4 = r0.fragmentedFramesText
            if (r4 == 0) goto L_0x02bd
            org.jboss.netty.buffer.ChannelBuffer r4 = r0.framePayload
            byte[] r4 = r4.array()
            r0.checkUTF8String(r1, r4)
        L_0x02bd:
            int r4 = r0.fragmentedFramesCount
            r6 = 1
            int r4 = r4 + r6
            r0.fragmentedFramesCount = r4
        L_0x02c3:
            int r4 = r0.frameOpcode
            if (r4 != r6) goto L_0x02d3
            org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame r4 = new org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame
            boolean r6 = r0.frameFinalFlag
            int r7 = r0.frameRsv
            org.jboss.netty.buffer.ChannelBuffer r8 = r0.framePayload
            r4.<init>((boolean) r6, (int) r7, (org.jboss.netty.buffer.ChannelBuffer) r8)
            return r4
        L_0x02d3:
            if (r4 != r8) goto L_0x02e1
            org.jboss.netty.handler.codec.http.websocketx.BinaryWebSocketFrame r4 = new org.jboss.netty.handler.codec.http.websocketx.BinaryWebSocketFrame
            boolean r6 = r0.frameFinalFlag
            int r7 = r0.frameRsv
            org.jboss.netty.buffer.ChannelBuffer r8 = r0.framePayload
            r4.<init>(r6, r7, r8)
            return r4
        L_0x02e1:
            if (r4 != 0) goto L_0x02ef
            org.jboss.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame r4 = new org.jboss.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame
            boolean r6 = r0.frameFinalFlag
            int r7 = r0.frameRsv
            org.jboss.netty.buffer.ChannelBuffer r8 = r0.framePayload
            r4.<init>(r6, r7, r8, r5)
            return r4
        L_0x02ef:
            java.lang.UnsupportedOperationException r4 = new java.lang.UnsupportedOperationException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Cannot decode web socket frame with opcode: "
            r6.append(r7)
            int r7 = r0.frameOpcode
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r4.<init>(r6)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder.decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer, org.jboss.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder$State):java.lang.Object");
    }

    /* renamed from: org.jboss.netty.handler.codec.http.websocketx.WebSocket08FrameDecoder$1 */
    static /* synthetic */ class C08801 {

        /* renamed from: $SwitchMap$org$jboss$netty$handler$codec$http$websocketx$WebSocket08FrameDecoder$State */
        static final /* synthetic */ int[] f136x43ecfa85;

        static {
            int[] iArr = new int[State.values().length];
            f136x43ecfa85 = iArr;
            try {
                iArr[State.FRAME_START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f136x43ecfa85[State.MASKING_KEY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f136x43ecfa85[State.PAYLOAD.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f136x43ecfa85[State.CORRUPT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private void unmask(ChannelBuffer frame) {
        byte[] bytes = frame.array();
        for (int i = 0; i < bytes.length; i++) {
            frame.setByte(i, frame.getByte(i) ^ this.maskingKey.getByte(i % 4));
        }
    }

    private void protocolViolation(Channel channel, String reason) throws CorruptedFrameException {
        checkpoint(State.CORRUPT);
        if (channel.isConnected()) {
            channel.write(ChannelBuffers.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            channel.close().awaitUninterruptibly();
        }
        throw new CorruptedFrameException(reason);
    }

    private static int toFrameLength(long l) throws TooLongFrameException {
        if (l <= TTL.MAX_VALUE) {
            return (int) l;
        }
        throw new TooLongFrameException("Length:" + l);
    }

    private void checkUTF8String(Channel channel, byte[] bytes) throws CorruptedFrameException {
        try {
            if (this.fragmentedFramesText == null) {
                this.fragmentedFramesText = new UTF8Output(bytes);
            } else {
                this.fragmentedFramesText.write(bytes);
            }
        } catch (UTF8Exception e) {
            protocolViolation(channel, "invalid UTF-8 bytes");
        }
    }

    /* access modifiers changed from: protected */
    public void checkCloseFrameBody(Channel channel, ChannelBuffer buffer) throws CorruptedFrameException {
        if (buffer != null && buffer.capacity() != 0) {
            if (buffer.capacity() == 1) {
                protocolViolation(channel, "Invalid close frame body");
            }
            int idx = buffer.readerIndex();
            buffer.readerIndex(0);
            int statusCode = buffer.readShort();
            if ((statusCode >= 0 && statusCode <= 999) || ((statusCode >= 1004 && statusCode <= 1006) || (statusCode >= 1012 && statusCode <= 2999))) {
                protocolViolation(channel, "Invalid close frame status code: " + statusCode);
            }
            if (buffer.readableBytes() > 0) {
                byte[] b = new byte[buffer.readableBytes()];
                buffer.readBytes(b);
                try {
                    new UTF8Output(b);
                } catch (UTF8Exception e) {
                    protocolViolation(channel, "Invalid close frame reason text. Invalid UTF-8 bytes");
                }
            }
            buffer.readerIndex(idx);
        }
    }
}
