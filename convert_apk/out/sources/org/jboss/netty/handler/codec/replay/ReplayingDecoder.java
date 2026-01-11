package org.jboss.netty.handler.codec.replay;

import java.lang.Enum;
import java.net.SocketAddress;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public abstract class ReplayingDecoder<T extends Enum<T>> extends FrameDecoder {
    private int checkpoint;
    private boolean needsCleanup;
    private final ReplayingDecoderBuffer replayable;
    private T state;

    /* access modifiers changed from: protected */
    public abstract Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, ChannelBuffer channelBuffer, T t) throws Exception;

    protected ReplayingDecoder() {
        this((Enum) null);
    }

    protected ReplayingDecoder(boolean unfold) {
        this((Enum) null, unfold);
    }

    protected ReplayingDecoder(T initialState) {
        this(initialState, false);
    }

    protected ReplayingDecoder(T initialState, boolean unfold) {
        super(unfold);
        this.replayable = new ReplayingDecoderBuffer(this);
        this.state = initialState;
    }

    /* access modifiers changed from: protected */
    public ChannelBuffer internalBuffer() {
        return super.internalBuffer();
    }

    /* access modifiers changed from: protected */
    public void checkpoint() {
        ChannelBuffer cumulation = this.cumulation;
        if (cumulation != null) {
            this.checkpoint = cumulation.readerIndex();
        } else {
            this.checkpoint = -1;
        }
    }

    /* access modifiers changed from: protected */
    public void checkpoint(T state2) {
        checkpoint();
        setState(state2);
    }

    /* access modifiers changed from: protected */
    public T getState() {
        return this.state;
    }

    /* access modifiers changed from: protected */
    public T setState(T newState) {
        T oldState = this.state;
        this.state = newState;
        return oldState;
    }

    /* access modifiers changed from: protected */
    public Object decodeLast(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, T state2) throws Exception {
        return decode(ctx, channel, buffer, state2);
    }

    /* access modifiers changed from: protected */
    public final Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        return decode(ctx, channel, buffer, this.state);
    }

    /* access modifiers changed from: protected */
    public final Object decodeLast(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        return decodeLast(ctx, channel, buffer, this.state);
    }

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        int readableBytes;
        boolean copy;
        int i;
        int bytesToPreserve;
        ChannelHandlerContext channelHandlerContext = ctx;
        Object m = e.getMessage();
        if (!(m instanceof ChannelBuffer)) {
            ctx.sendUpstream(e);
            return;
        }
        ChannelBuffer input = (ChannelBuffer) m;
        if (input.readable()) {
            boolean z = true;
            this.needsCleanup = z;
            if (this.cumulation == null) {
                this.cumulation = input;
                int oldReaderIndex = input.readerIndex();
                int inputSize = input.readableBytes();
                try {
                    callDecode(ctx, e.getChannel(), input, this.replayable, e.getRemoteAddress());
                    if (readableBytes <= 0) {
                        this.cumulation = null;
                    } else if (i > 0) {
                        if (!copy) {
                            ChannelBuffer slice = input.slice(i, bytesToPreserve);
                            ChannelBuffer channelBuffer = slice;
                            this.cumulation = slice;
                        }
                    } else if (i == 0) {
                        if (copy) {
                            ChannelBuffer newCumulationBuffer = newCumulationBuffer(channelHandlerContext, inputSize);
                            ChannelBuffer cumulation = newCumulationBuffer;
                            this.cumulation = newCumulationBuffer;
                            cumulation.writeBytes(input, oldReaderIndex, inputSize);
                            cumulation.readerIndex(input.readerIndex());
                            return;
                        }
                        ChannelBuffer cumulation2 = input.slice(oldReaderIndex, inputSize);
                        this.cumulation = cumulation2;
                        cumulation2.readerIndex(input.readerIndex());
                    } else if (copy) {
                        ChannelBuffer cumulation3 = newCumulationBuffer(channelHandlerContext, input.readableBytes());
                        this.cumulation = cumulation3;
                        cumulation3.writeBytes(input);
                    } else {
                        ChannelBuffer channelBuffer2 = input;
                        this.cumulation = input;
                    }
                } finally {
                    readableBytes = input.readableBytes();
                    if (readableBytes > 0) {
                        int inputCapacity = input.capacity();
                        if (readableBytes == inputCapacity || inputCapacity <= getMaxCumulationBufferCapacity()) {
                            z = false;
                        }
                        copy = z;
                        i = this.checkpoint;
                        if (i > 0) {
                            bytesToPreserve = inputSize - (i - oldReaderIndex);
                            if (copy) {
                                ChannelBuffer cumulation4 = newCumulationBuffer(channelHandlerContext, bytesToPreserve);
                                this.cumulation = cumulation4;
                                cumulation4.writeBytes(input, this.checkpoint, bytesToPreserve);
                            } else {
                                ChannelBuffer slice2 = input.slice(i, bytesToPreserve);
                                ChannelBuffer channelBuffer3 = slice2;
                                this.cumulation = slice2;
                            }
                        } else if (i == 0) {
                            if (copy) {
                                ChannelBuffer newCumulationBuffer2 = newCumulationBuffer(channelHandlerContext, inputSize);
                                ChannelBuffer cumulation5 = newCumulationBuffer2;
                                this.cumulation = newCumulationBuffer2;
                                cumulation5.writeBytes(input, oldReaderIndex, inputSize);
                                cumulation5.readerIndex(input.readerIndex());
                            } else {
                                ChannelBuffer cumulation6 = input.slice(oldReaderIndex, inputSize);
                                this.cumulation = cumulation6;
                                cumulation6.readerIndex(input.readerIndex());
                            }
                        } else if (copy) {
                            ChannelBuffer cumulation7 = newCumulationBuffer(channelHandlerContext, input.readableBytes());
                            this.cumulation = cumulation7;
                            cumulation7.writeBytes(input);
                        } else {
                            ChannelBuffer channelBuffer4 = input;
                            this.cumulation = input;
                        }
                    } else {
                        this.cumulation = null;
                    }
                }
            } else {
                ChannelBuffer input2 = appendToCumulation(input);
                try {
                    callDecode(ctx, e.getChannel(), input2, this.replayable, e.getRemoteAddress());
                } finally {
                    updateCumulation(channelHandlerContext, input2);
                }
            }
        }
    }

    private void callDecode(ChannelHandlerContext context, Channel channel, ChannelBuffer input, ChannelBuffer replayableInput, SocketAddress remoteAddress) throws Exception {
        while (input.readable()) {
            int oldReaderIndex = input.readerIndex();
            this.checkpoint = oldReaderIndex;
            Object result = null;
            T oldState = this.state;
            try {
                result = decode(context, channel, replayableInput, this.state);
                if (result == null) {
                    if (oldReaderIndex != input.readerIndex()) {
                        continue;
                    } else if (oldState == this.state) {
                        throw new IllegalStateException("null cannot be returned if no data is consumed and state didn't change.");
                    }
                }
            } catch (ReplayError e) {
                int checkpoint2 = this.checkpoint;
                if (checkpoint2 >= 0) {
                    input.readerIndex(checkpoint2);
                }
            }
            if (result != null) {
                if (oldReaderIndex == input.readerIndex() && oldState == this.state) {
                    throw new IllegalStateException("decode() method must consume at least one byte if it returned a decoded message (caused by: " + getClass() + ")");
                }
                unfoldAndFireMessageReceived(context, remoteAddress, result);
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void cleanup(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        try {
            ChannelBuffer cumulation = this.cumulation;
            if (!this.needsCleanup) {
                ctx.sendUpstream(e);
                return;
            }
            this.needsCleanup = false;
            this.replayable.terminate();
            if (cumulation != null && cumulation.readable()) {
                callDecode(ctx, e.getChannel(), cumulation, this.replayable, (SocketAddress) null);
            }
            Object partiallyDecoded = decodeLast(ctx, e.getChannel(), this.replayable, this.state);
            this.cumulation = null;
            if (partiallyDecoded != null) {
                unfoldAndFireMessageReceived(ctx, (SocketAddress) null, partiallyDecoded);
            }
            ctx.sendUpstream(e);
        } catch (ReplayError e2) {
        } catch (Throwable th) {
            ctx.sendUpstream(e);
            throw th;
        }
    }
}
