package org.jboss.netty.channel.local;

import java.io.IOException;
import java.net.ConnectException;
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
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;

final class LocalClientChannelSink extends AbstractChannelSink {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) LocalClientChannelSink.class);

    LocalClientChannelSink() {
    }

    public void eventSunk(ChannelPipeline pipeline, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            ChannelStateEvent event = (ChannelStateEvent) e;
            DefaultLocalChannel channel = (DefaultLocalChannel) event.getChannel();
            ChannelFuture future = event.getFuture();
            ChannelState state = event.getState();
            Object value = event.getValue();
            int i = C08471.$SwitchMap$org$jboss$netty$channel$ChannelState[state.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i == 4) {
                            future.setSuccess();
                        }
                    } else if (value != null) {
                        connect(channel, future, (LocalAddress) value);
                    } else {
                        channel.closeNow(future);
                    }
                } else if (value != null) {
                    bind(channel, future, (LocalAddress) value);
                } else {
                    channel.closeNow(future);
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

    /* renamed from: org.jboss.netty.channel.local.LocalClientChannelSink$1 */
    static /* synthetic */ class C08471 {
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

    private static void bind(DefaultLocalChannel channel, ChannelFuture future, LocalAddress localAddress) {
        try {
            if (LocalChannelRegistry.register(localAddress, channel)) {
                channel.setBound();
                channel.localAddress = localAddress;
                future.setSuccess();
                Channels.fireChannelBound((Channel) channel, (SocketAddress) localAddress);
                return;
            }
            throw new ChannelException("address already in use: " + localAddress);
        } catch (Throwable t) {
            LocalChannelRegistry.unregister(localAddress);
            future.setFailure(t);
            Channels.fireExceptionCaught((Channel) channel, t);
        }
    }

    private void connect(DefaultLocalChannel channel, ChannelFuture future, LocalAddress remoteAddress) {
        Channel remoteChannel = LocalChannelRegistry.getChannel(remoteAddress);
        if (!(remoteChannel instanceof DefaultLocalServerChannel)) {
            future.setFailure(new ConnectException("connection refused"));
            return;
        }
        DefaultLocalServerChannel serverChannel = (DefaultLocalServerChannel) remoteChannel;
        try {
            ChannelPipeline pipeline = serverChannel.getConfig().getPipelineFactory().getPipeline();
            future.setSuccess();
            DefaultLocalChannel defaultLocalChannel = new DefaultLocalChannel(serverChannel, serverChannel.getFactory(), pipeline, this, channel);
            channel.pairedChannel = defaultLocalChannel;
            if (!channel.isBound()) {
                bind(channel, Channels.succeededFuture(channel), new LocalAddress(LocalAddress.EPHEMERAL));
            }
            channel.remoteAddress = serverChannel.getLocalAddress();
            channel.setConnected();
            Channels.fireChannelConnected((Channel) channel, (SocketAddress) serverChannel.getLocalAddress());
            defaultLocalChannel.localAddress = serverChannel.getLocalAddress();
            try {
                defaultLocalChannel.setBound();
                Channels.fireChannelBound((Channel) defaultLocalChannel, (SocketAddress) channel.getRemoteAddress());
                defaultLocalChannel.remoteAddress = channel.getLocalAddress();
                defaultLocalChannel.setConnected();
                Channels.fireChannelConnected((Channel) defaultLocalChannel, (SocketAddress) channel.getLocalAddress());
                channel.flushWriteBuffer();
                defaultLocalChannel.flushWriteBuffer();
            } catch (IOException e) {
                throw new Error(e);
            }
        } catch (Exception e2) {
            future.setFailure(e2);
            Channels.fireExceptionCaught((Channel) channel, (Throwable) e2);
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to initialize an accepted socket.", e2);
            }
        }
    }
}
