package org.jboss.netty.handler.codec.marshalling;

import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.marshalling.LimitingByteInput;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;
import org.jboss.netty.handler.codec.replay.VoidEnum;

public class CompatibleMarshallingDecoder extends ReplayingDecoder<VoidEnum> {
    protected final int maxObjectSize;
    protected final UnmarshallerProvider provider;

    public CompatibleMarshallingDecoder(UnmarshallerProvider provider2, int maxObjectSize2) {
        this.provider = provider2;
        this.maxObjectSize = maxObjectSize2;
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, VoidEnum state) throws Exception {
        Unmarshaller unmarshaller = this.provider.getUnmarshaller(ctx);
        ByteInput input = new ChannelBufferByteInput(buffer);
        if (this.maxObjectSize != Integer.MAX_VALUE) {
            input = new LimitingByteInput(input, (long) this.maxObjectSize);
        }
        try {
            unmarshaller.start(input);
            Object readObject = unmarshaller.readObject();
            unmarshaller.finish();
            unmarshaller.close();
            Object obj = readObject;
            return readObject;
        } catch (LimitingByteInput.TooBigObjectException e) {
            throw new TooLongFrameException("Object to big to unmarshal");
        } catch (Throwable th) {
            unmarshaller.close();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public Object decodeLast(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, VoidEnum state) throws Exception {
        int readableBytes = buffer.readableBytes();
        if (readableBytes == 0) {
            return null;
        }
        if (readableBytes != 1 || buffer.getByte(buffer.readerIndex()) != 121) {
            return decode(ctx, channel, buffer, state);
        }
        buffer.skipBytes(1);
        return null;
    }

    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        if (e.getCause() instanceof TooLongFrameException) {
            e.getChannel().close();
        } else {
            super.exceptionCaught(ctx, e);
        }
    }
}
