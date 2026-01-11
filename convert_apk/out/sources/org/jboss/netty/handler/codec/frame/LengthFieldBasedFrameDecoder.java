package org.jboss.netty.handler.codec.frame;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;

public class LengthFieldBasedFrameDecoder extends FrameDecoder {
    private long bytesToDiscard;
    private boolean discardingTooLongFrame;
    private final boolean failFast;
    private final int initialBytesToStrip;
    private final int lengthAdjustment;
    private final int lengthFieldEndOffset;
    private final int lengthFieldLength;
    private final int lengthFieldOffset;
    private final int maxFrameLength;
    private long tooLongFrameLength;

    public LengthFieldBasedFrameDecoder(int maxFrameLength2, int lengthFieldOffset2, int lengthFieldLength2) {
        this(maxFrameLength2, lengthFieldOffset2, lengthFieldLength2, 0, 0);
    }

    public LengthFieldBasedFrameDecoder(int maxFrameLength2, int lengthFieldOffset2, int lengthFieldLength2, int lengthAdjustment2, int initialBytesToStrip2) {
        this(maxFrameLength2, lengthFieldOffset2, lengthFieldLength2, lengthAdjustment2, initialBytesToStrip2, false);
    }

    public LengthFieldBasedFrameDecoder(int maxFrameLength2, int lengthFieldOffset2, int lengthFieldLength2, int lengthAdjustment2, int initialBytesToStrip2, boolean failFast2) {
        if (maxFrameLength2 <= 0) {
            throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + maxFrameLength2);
        } else if (lengthFieldOffset2 < 0) {
            throw new IllegalArgumentException("lengthFieldOffset must be a non-negative integer: " + lengthFieldOffset2);
        } else if (initialBytesToStrip2 < 0) {
            throw new IllegalArgumentException("initialBytesToStrip must be a non-negative integer: " + initialBytesToStrip2);
        } else if (lengthFieldLength2 != 1 && lengthFieldLength2 != 2 && lengthFieldLength2 != 3 && lengthFieldLength2 != 4 && lengthFieldLength2 != 8) {
            throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + lengthFieldLength2);
        } else if (lengthFieldOffset2 <= maxFrameLength2 - lengthFieldLength2) {
            this.maxFrameLength = maxFrameLength2;
            this.lengthFieldOffset = lengthFieldOffset2;
            this.lengthFieldLength = lengthFieldLength2;
            this.lengthAdjustment = lengthAdjustment2;
            this.lengthFieldEndOffset = lengthFieldOffset2 + lengthFieldLength2;
            this.initialBytesToStrip = initialBytesToStrip2;
            this.failFast = failFast2;
        } else {
            throw new IllegalArgumentException("maxFrameLength (" + maxFrameLength2 + ") " + "must be equal to or greater than " + "lengthFieldOffset (" + lengthFieldOffset2 + ") + " + "lengthFieldLength (" + lengthFieldLength2 + ").");
        }
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        long frameLength;
        if (this.discardingTooLongFrame) {
            long bytesToDiscard2 = this.bytesToDiscard;
            int localBytesToDiscard = (int) Math.min(bytesToDiscard2, (long) buffer.readableBytes());
            buffer.skipBytes(localBytesToDiscard);
            this.bytesToDiscard = bytesToDiscard2 - ((long) localBytesToDiscard);
            failIfNecessary(ctx, false);
            return null;
        } else if (buffer.readableBytes() < this.lengthFieldEndOffset) {
            return null;
        } else {
            int actualLengthFieldOffset = buffer.readerIndex() + this.lengthFieldOffset;
            int i = this.lengthFieldLength;
            if (i == 1) {
                frameLength = (long) buffer.getUnsignedByte(actualLengthFieldOffset);
            } else if (i == 2) {
                frameLength = (long) buffer.getUnsignedShort(actualLengthFieldOffset);
            } else if (i == 3) {
                frameLength = (long) buffer.getUnsignedMedium(actualLengthFieldOffset);
            } else if (i == 4) {
                frameLength = buffer.getUnsignedInt(actualLengthFieldOffset);
            } else if (i == 8) {
                frameLength = buffer.getLong(actualLengthFieldOffset);
            } else {
                throw new Error("should not reach here");
            }
            if (frameLength >= 0) {
                int i2 = this.lengthAdjustment;
                int i3 = this.lengthFieldEndOffset;
                long frameLength2 = frameLength + ((long) (i2 + i3));
                if (frameLength2 < ((long) i3)) {
                    buffer.skipBytes(i3);
                    throw new CorruptedFrameException("Adjusted frame length (" + frameLength2 + ") is less " + "than lengthFieldEndOffset: " + this.lengthFieldEndOffset);
                } else if (frameLength2 > ((long) this.maxFrameLength)) {
                    this.discardingTooLongFrame = true;
                    this.tooLongFrameLength = frameLength2;
                    this.bytesToDiscard = frameLength2 - ((long) buffer.readableBytes());
                    buffer.skipBytes(buffer.readableBytes());
                    failIfNecessary(ctx, true);
                    return null;
                } else {
                    int frameLengthInt = (int) frameLength2;
                    if (buffer.readableBytes() < frameLengthInt) {
                        return null;
                    }
                    int i4 = this.initialBytesToStrip;
                    if (i4 <= frameLengthInt) {
                        buffer.skipBytes(i4);
                        int readerIndex = buffer.readerIndex();
                        int actualFrameLength = frameLengthInt - this.initialBytesToStrip;
                        ChannelBuffer frame = extractFrame(buffer, readerIndex, actualFrameLength);
                        buffer.readerIndex(readerIndex + actualFrameLength);
                        return frame;
                    }
                    buffer.skipBytes(frameLengthInt);
                    throw new CorruptedFrameException("Adjusted frame length (" + frameLength2 + ") is less " + "than initialBytesToStrip: " + this.initialBytesToStrip);
                }
            } else {
                buffer.skipBytes(this.lengthFieldEndOffset);
                throw new CorruptedFrameException("negative pre-adjustment length field: " + frameLength);
            }
        }
    }

    private void failIfNecessary(ChannelHandlerContext ctx, boolean firstDetectionOfTooLongFrame) {
        if (this.bytesToDiscard == 0) {
            long tooLongFrameLength2 = this.tooLongFrameLength;
            this.tooLongFrameLength = 0;
            this.discardingTooLongFrame = false;
            boolean z = this.failFast;
            if (!z || (z && firstDetectionOfTooLongFrame)) {
                fail(ctx, tooLongFrameLength2);
            }
        } else if (this.failFast && firstDetectionOfTooLongFrame) {
            fail(ctx, this.tooLongFrameLength);
        }
    }

    /* access modifiers changed from: protected */
    public ChannelBuffer extractFrame(ChannelBuffer buffer, int index, int length) {
        ChannelBuffer frame = buffer.factory().getBuffer(length);
        frame.writeBytes(buffer, index, length);
        return frame;
    }

    private void fail(ChannelHandlerContext ctx, long frameLength) {
        if (frameLength > 0) {
            Channel channel = ctx.getChannel();
            Channels.fireExceptionCaught(channel, (Throwable) new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + ": " + frameLength + " - discarded"));
            return;
        }
        Channel channel2 = ctx.getChannel();
        Channels.fireExceptionCaught(channel2, (Throwable) new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + " - discarding"));
    }
}
