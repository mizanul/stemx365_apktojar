package org.jboss.netty.handler.codec.serialization;

import java.io.InputStream;
import java.io.ObjectInputStream;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

@Deprecated
public class CompatibleObjectDecoder extends ReplayingDecoder<CompatibleObjectDecoderState> {
    private final SwitchableInputStream bin = new SwitchableInputStream();
    private ObjectInputStream oin;

    public CompatibleObjectDecoder() {
        super(CompatibleObjectDecoderState.READ_HEADER);
    }

    /* access modifiers changed from: protected */
    public ObjectInputStream newObjectInputStream(InputStream in) throws Exception {
        return new ObjectInputStream(in);
    }

    /* renamed from: org.jboss.netty.handler.codec.serialization.CompatibleObjectDecoder$1 */
    static /* synthetic */ class C08821 {

        /* renamed from: $SwitchMap$org$jboss$netty$handler$codec$serialization$CompatibleObjectDecoderState */
        static final /* synthetic */ int[] f138xbdda96cb;

        static {
            int[] iArr = new int[CompatibleObjectDecoderState.values().length];
            f138xbdda96cb = iArr;
            try {
                iArr[CompatibleObjectDecoderState.READ_HEADER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f138xbdda96cb[CompatibleObjectDecoderState.READ_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, CompatibleObjectDecoderState state) throws Exception {
        this.bin.switchStream(new ChannelBufferInputStream(buffer));
        int i = C08821.f138xbdda96cb[state.ordinal()];
        if (i == 1) {
            this.oin = newObjectInputStream(this.bin);
            checkpoint(CompatibleObjectDecoderState.READ_OBJECT);
        } else if (i != 2) {
            throw new IllegalStateException("Unknown state: " + state);
        }
        return this.oin.readObject();
    }

    /* access modifiers changed from: protected */
    public Object decodeLast(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, CompatibleObjectDecoderState state) throws Exception {
        int readableBytes = buffer.readableBytes();
        if (readableBytes == 0) {
            return null;
        }
        if (readableBytes == 1 && buffer.getByte(buffer.readerIndex()) == 121) {
            buffer.skipBytes(1);
            this.oin.close();
            return null;
        }
        Object decoded = decode(ctx, channel, buffer, state);
        this.oin.close();
        return decoded;
    }
}
