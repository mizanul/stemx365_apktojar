package org.jboss.netty.handler.codec.compression;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.jboss.netty.util.internal.jzlib.JZlib;
import org.jboss.netty.util.internal.jzlib.ZStream;

public class ZlibDecoder extends OneToOneDecoder {
    private byte[] dictionary;
    private volatile boolean finished;

    /* renamed from: z */
    private final ZStream f126z;

    public ZlibDecoder() {
        this(ZlibWrapper.ZLIB);
    }

    public ZlibDecoder(ZlibWrapper wrapper) {
        ZStream zStream = new ZStream();
        this.f126z = zStream;
        if (wrapper != null) {
            synchronized (zStream) {
                int resultCode = this.f126z.inflateInit(ZlibUtil.convertWrapperType(wrapper));
                if (resultCode != 0) {
                    ZlibUtil.fail(this.f126z, "initialization failure", resultCode);
                }
            }
            return;
        }
        throw new NullPointerException("wrapper");
    }

    public ZlibDecoder(byte[] dictionary2) {
        ZStream zStream = new ZStream();
        this.f126z = zStream;
        if (dictionary2 != null) {
            this.dictionary = dictionary2;
            synchronized (zStream) {
                int resultCode = this.f126z.inflateInit(JZlib.W_ZLIB);
                if (resultCode != 0) {
                    ZlibUtil.fail(this.f126z, "initialization failure", resultCode);
                }
            }
            return;
        }
        throw new NullPointerException("dictionary");
    }

    public boolean isClosed() {
        return this.finished;
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (!(msg instanceof ChannelBuffer) || this.finished) {
            return msg;
        }
        synchronized (this.f126z) {
            try {
                ChannelBuffer compressed = (ChannelBuffer) msg;
                byte[] in = new byte[compressed.readableBytes()];
                compressed.readBytes(in);
                this.f126z.next_in = in;
                this.f126z.next_in_index = 0;
                this.f126z.avail_in = in.length;
                byte[] out = new byte[(in.length << 1)];
                ChannelBuffer decompressed = ChannelBuffers.dynamicBuffer(compressed.order(), out.length, ctx.getChannel().getConfig().getBufferFactory());
                this.f126z.next_out = out;
                this.f126z.next_out_index = 0;
                this.f126z.avail_out = out.length;
                while (true) {
                    int resultCode = this.f126z.inflate(2);
                    if (this.f126z.next_out_index > 0) {
                        decompressed.writeBytes(out, 0, this.f126z.next_out_index);
                        this.f126z.avail_out = out.length;
                    }
                    this.f126z.next_out_index = 0;
                    if (resultCode != -5) {
                        if (resultCode != 0) {
                            if (resultCode == 1) {
                                this.finished = true;
                                this.f126z.inflateEnd();
                                break;
                            } else if (resultCode != 2) {
                                ZlibUtil.fail(this.f126z, "decompression failure", resultCode);
                            } else if (this.dictionary == null) {
                                ZlibUtil.fail(this.f126z, "decompression failure", resultCode);
                            } else {
                                int resultCode2 = this.f126z.inflateSetDictionary(this.dictionary, this.dictionary.length);
                                if (resultCode2 != 0) {
                                    ZlibUtil.fail(this.f126z, "failed to set the dictionary", resultCode2);
                                }
                            }
                        }
                    } else if (this.f126z.avail_in <= 0) {
                        break;
                    }
                }
                if (decompressed.writerIndex() != 0) {
                    this.f126z.next_in = null;
                    this.f126z.next_out = null;
                    byte[] bArr = in;
                    ChannelBuffer channelBuffer = decompressed;
                    return decompressed;
                }
                this.f126z.next_in = null;
                this.f126z.next_out = null;
                ChannelBuffer channelBuffer2 = decompressed;
                return null;
            } catch (Throwable th) {
                this.f126z.next_in = null;
                this.f126z.next_out = null;
                throw th;
            }
        }
    }
}
