package org.jboss.netty.handler.ipfilter;

import java.net.InetSocketAddress;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.channel.Channels;

public abstract class IpFilteringHandlerImpl implements ChannelUpstreamHandler, IpFilteringHandler {
    private IpFilterListener listener;

    /* access modifiers changed from: protected */
    public abstract boolean accept(ChannelHandlerContext channelHandlerContext, ChannelEvent channelEvent, InetSocketAddress inetSocketAddress) throws Exception;

    /* access modifiers changed from: protected */
    public ChannelFuture handleRefusedChannel(ChannelHandlerContext ctx, ChannelEvent e, InetSocketAddress inetSocketAddress) throws Exception {
        IpFilterListener ipFilterListener = this.listener;
        if (ipFilterListener == null) {
            return null;
        }
        return ipFilterListener.refused(ctx, e, inetSocketAddress);
    }

    /* access modifiers changed from: protected */
    public ChannelFuture handleAllowedChannel(ChannelHandlerContext ctx, ChannelEvent e, InetSocketAddress inetSocketAddress) throws Exception {
        IpFilterListener ipFilterListener = this.listener;
        if (ipFilterListener == null) {
            return null;
        }
        return ipFilterListener.allowed(ctx, e, inetSocketAddress);
    }

    /* access modifiers changed from: protected */
    public boolean isBlocked(ChannelHandlerContext ctx) {
        return ctx.getAttachment() != null;
    }

    /* access modifiers changed from: protected */
    public boolean continues(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        IpFilterListener ipFilterListener = this.listener;
        if (ipFilterListener != null) {
            return ipFilterListener.continues(ctx, e);
        }
        return false;
    }

    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            ChannelStateEvent evt = (ChannelStateEvent) e;
            int i = C08921.$SwitchMap$org$jboss$netty$channel$ChannelState[evt.getState().ordinal()];
            if (i == 1 || i == 2) {
                if (!isBlocked(ctx) || continues(ctx, evt)) {
                    ctx.sendUpstream(e);
                    return;
                }
                return;
            } else if (i == 3) {
                if (evt.getValue() != null) {
                    InetSocketAddress inetSocketAddress = (InetSocketAddress) e.getChannel().getRemoteAddress();
                    if (!accept(ctx, e, inetSocketAddress)) {
                        ctx.setAttachment(Boolean.TRUE);
                        ChannelFuture future = handleRefusedChannel(ctx, e, inetSocketAddress);
                        if (future != null) {
                            future.addListener(ChannelFutureListener.CLOSE);
                        } else {
                            Channels.close(e.getChannel());
                        }
                        if (isBlocked(ctx) && !continues(ctx, evt)) {
                            return;
                        }
                    } else {
                        handleAllowedChannel(ctx, e, inetSocketAddress);
                    }
                    ctx.setAttachment((Object) null);
                } else if (isBlocked(ctx) && !continues(ctx, evt)) {
                    return;
                }
            }
        }
        if (!isBlocked(ctx) || continues(ctx, e)) {
            ctx.sendUpstream(e);
        }
    }

    /* renamed from: org.jboss.netty.handler.ipfilter.IpFilteringHandlerImpl$1 */
    static /* synthetic */ class C08921 {
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
        }
    }

    public void setIpFilterListener(IpFilterListener listener2) {
        this.listener = listener2;
    }

    public void removeIpFilterListener() {
        this.listener = null;
    }
}
