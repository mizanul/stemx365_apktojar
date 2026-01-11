package org.jboss.netty.handler.codec.frame;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

@ChannelHandler.Sharable
public class LengthFieldPrepender extends OneToOneEncoder {
    private final int lengthFieldLength;
    private final boolean lengthIncludesLengthFieldLength;

    public LengthFieldPrepender(int lengthFieldLength2) {
        this(lengthFieldLength2, false);
    }

    public LengthFieldPrepender(int lengthFieldLength2, boolean lengthIncludesLengthFieldLength2) {
        if (lengthFieldLength2 == 1 || lengthFieldLength2 == 2 || lengthFieldLength2 == 3 || lengthFieldLength2 == 4 || lengthFieldLength2 == 8) {
            this.lengthFieldLength = lengthFieldLength2;
            this.lengthIncludesLengthFieldLength = lengthIncludesLengthFieldLength2;
            return;
        }
        throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + lengthFieldLength2);
    }

    /* access modifiers changed from: protected */
    public Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (!(msg instanceof ChannelBuffer)) {
            return msg;
        }
        ChannelBuffer body = (ChannelBuffer) msg;
        ChannelBuffer header = channel.getConfig().getBufferFactory().getBuffer(body.order(), this.lengthFieldLength);
        int length = this.lengthIncludesLengthFieldLength ? body.readableBytes() + this.lengthFieldLength : body.readableBytes();
        int i = this.lengthFieldLength;
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        header.writeInt(length);
                    } else if (i == 8) {
                        header.writeLong((long) length);
                    } else {
                        throw new Error("should not reach here");
                    }
                } else if (length < 16777216) {
                    header.writeMedium(length);
                } else {
                    throw new IllegalArgumentException("length does not fit into a medium integer: " + length);
                }
            } else if (length < 65536) {
                header.writeShort((short) length);
            } else {
                throw new IllegalArgumentException("length does not fit into a short integer: " + length);
            }
        } else if (length < 256) {
            header.writeByte((byte) length);
        } else {
            throw new IllegalArgumentException("length does not fit into a byte: " + length);
        }
        return ChannelBuffers.wrappedBuffer(header, body);
    }
}
