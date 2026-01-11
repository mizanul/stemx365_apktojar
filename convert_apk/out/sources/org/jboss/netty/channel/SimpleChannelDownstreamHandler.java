package org.jboss.netty.channel;

public class SimpleChannelDownstreamHandler implements ChannelDownstreamHandler {
    public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof MessageEvent) {
            writeRequested(ctx, (MessageEvent) e);
        } else if (e instanceof ChannelStateEvent) {
            ChannelStateEvent evt = (ChannelStateEvent) e;
            int i = C08401.$SwitchMap$org$jboss$netty$channel$ChannelState[evt.getState().ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            ctx.sendDownstream(e);
                        } else {
                            setInterestOpsRequested(ctx, evt);
                        }
                    } else if (evt.getValue() != null) {
                        connectRequested(ctx, evt);
                    } else {
                        disconnectRequested(ctx, evt);
                    }
                } else if (evt.getValue() != null) {
                    bindRequested(ctx, evt);
                } else {
                    unbindRequested(ctx, evt);
                }
            } else if (!Boolean.TRUE.equals(evt.getValue())) {
                closeRequested(ctx, evt);
            }
        } else {
            ctx.sendDownstream(e);
        }
    }

    /* renamed from: org.jboss.netty.channel.SimpleChannelDownstreamHandler$1 */
    static /* synthetic */ class C08401 {
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

    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        ctx.sendDownstream(e);
    }

    public void bindRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendDownstream(e);
    }

    public void connectRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendDownstream(e);
    }

    public void setInterestOpsRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendDownstream(e);
    }

    public void disconnectRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendDownstream(e);
    }

    public void unbindRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendDownstream(e);
    }

    public void closeRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendDownstream(e);
    }
}
