package org.jboss.netty.channel.local;

import java.net.SocketAddress;
import org.jboss.netty.channel.AbstractChannelSink;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelException;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;

final class LocalServerChannelSink extends AbstractChannelSink {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    LocalServerChannelSink() {
    }

    public void eventSunk(ChannelPipeline pipeline, ChannelEvent e) throws Exception {
        Channel channel = e.getChannel();
        if (channel instanceof DefaultLocalServerChannel) {
            handleServerChannel(e);
        } else if (channel instanceof DefaultLocalChannel) {
            handleAcceptedChannel(e);
        }
    }

    private static void handleServerChannel(ChannelEvent e) {
        if (e instanceof ChannelStateEvent) {
            ChannelStateEvent event = (ChannelStateEvent) e;
            DefaultLocalServerChannel channel = (DefaultLocalServerChannel) event.getChannel();
            ChannelFuture future = event.getFuture();
            ChannelState state = event.getState();
            Object value = event.getValue();
            int i = C08481.$SwitchMap$org$jboss$netty$channel$ChannelState[state.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    if (value != null) {
                        bind(channel, future, (LocalAddress) value);
                    } else {
                        close(channel, future);
                    }
                }
            } else if (Boolean.FALSE.equals(value)) {
                close(channel, future);
            }
        }
    }

    /* renamed from: org.jboss.netty.channel.local.LocalServerChannelSink$1 */
    static /* synthetic */ class C08481 {
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

    private static void handleAcceptedChannel(ChannelEvent e) {
        if (e instanceof ChannelStateEvent) {
            ChannelStateEvent event = (ChannelStateEvent) e;
            DefaultLocalChannel channel = (DefaultLocalChannel) event.getChannel();
            ChannelFuture future = event.getFuture();
            ChannelState state = event.getState();
            Object value = event.getValue();
            int i = C08481.$SwitchMap$org$jboss$netty$channel$ChannelState[state.ordinal()];
            if (i != 1) {
                if (i == 2 || i == 3) {
                    if (value == null) {
                        channel.closeNow(future);
                    }
                } else if (i == 4) {
                    future.setSuccess();
                }
            } else if (Boolean.FALSE.equals(value)) {
                channel.closeNow(future);
            }
        } else if (e instanceof MessageEvent) {
            MessageEvent event2 = (MessageEvent) e;
            DefaultLocalChannel channel2 = (DefaultLocalChannel) event2.getChannel();
            boolean offer = channel2.writeBuffer.offer(event2);
            channel2.flushWriteBuffer();
        }
    }

    private static void bind(DefaultLocalServerChannel channel, ChannelFuture future, LocalAddress localAddress) {
        try {
            if (!LocalChannelRegistry.register(localAddress, channel)) {
                throw new ChannelException("address already in use: " + localAddress);
            } else if (channel.bound.compareAndSet(false, true)) {
                channel.localAddress = localAddress;
                future.setSuccess();
                Channels.fireChannelBound((Channel) channel, (SocketAddress) localAddress);
            } else {
                throw new ChannelException("already bound");
            }
        } catch (Throwable t) {
            LocalChannelRegistry.unregister(localAddress);
            future.setFailure(t);
            Channels.fireExceptionCaught((Channel) channel, t);
        }
    }

    private static void close(DefaultLocalServerChannel channel, ChannelFuture future) {
        try {
            if (channel.setClosed()) {
                future.setSuccess();
                LocalAddress localAddress = channel.localAddress;
                if (channel.bound.compareAndSet(true, false)) {
                    channel.localAddress = null;
                    LocalChannelRegistry.unregister(localAddress);
                    Channels.fireChannelUnbound((Channel) channel);
                }
                Channels.fireChannelClosed((Channel) channel);
                return;
            }
            future.setSuccess();
        } catch (Throwable t) {
            future.setFailure(t);
            Channels.fireExceptionCaught((Channel) channel, t);
        }
    }
}
