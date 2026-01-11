package org.jboss.netty.channel;

import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

public class SimpleChannelHandler implements ChannelUpstreamHandler, ChannelDownstreamHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(SimpleChannelHandler.class.getName());

    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof MessageEvent) {
            messageReceived(ctx, (MessageEvent) e);
        } else if (e instanceof WriteCompletionEvent) {
            writeComplete(ctx, (WriteCompletionEvent) e);
        } else if (e instanceof ChildChannelStateEvent) {
            ChildChannelStateEvent evt = (ChildChannelStateEvent) e;
            if (evt.getChildChannel().isOpen()) {
                childChannelOpen(ctx, evt);
            } else {
                childChannelClosed(ctx, evt);
            }
        } else if (e instanceof ChannelStateEvent) {
            ChannelStateEvent evt2 = (ChannelStateEvent) e;
            int i = C08411.$SwitchMap$org$jboss$netty$channel$ChannelState[evt2.getState().ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            ctx.sendUpstream(e);
                        } else {
                            channelInterestChanged(ctx, evt2);
                        }
                    } else if (evt2.getValue() != null) {
                        channelConnected(ctx, evt2);
                    } else {
                        channelDisconnected(ctx, evt2);
                    }
                } else if (evt2.getValue() != null) {
                    channelBound(ctx, evt2);
                } else {
                    channelUnbound(ctx, evt2);
                }
            } else if (Boolean.TRUE.equals(evt2.getValue())) {
                channelOpen(ctx, evt2);
            } else {
                channelClosed(ctx, evt2);
            }
        } else if (e instanceof ExceptionEvent) {
            exceptionCaught(ctx, (ExceptionEvent) e);
        } else {
            ctx.sendUpstream(e);
        }
    }

    /* renamed from: org.jboss.netty.channel.SimpleChannelHandler$1 */
    static /* synthetic */ class C08411 {
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

    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        if (this == ctx.getPipeline().getLast()) {
            InternalLogger internalLogger = logger;
            internalLogger.warn("EXCEPTION, please implement " + getClass().getName() + ".exceptionCaught() for proper handling.", e.getCause());
        }
        ctx.sendUpstream(e);
    }

    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    public void channelBound(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    public void channelInterestChanged(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    public void channelUnbound(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    public void childChannelOpen(ChannelHandlerContext ctx, ChildChannelStateEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    public void childChannelClosed(ChannelHandlerContext ctx, ChildChannelStateEvent e) throws Exception {
        ctx.sendUpstream(e);
    }

    public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof MessageEvent) {
            writeRequested(ctx, (MessageEvent) e);
        } else if (e instanceof ChannelStateEvent) {
            ChannelStateEvent evt = (ChannelStateEvent) e;
            int i = C08411.$SwitchMap$org$jboss$netty$channel$ChannelState[evt.getState().ordinal()];
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
