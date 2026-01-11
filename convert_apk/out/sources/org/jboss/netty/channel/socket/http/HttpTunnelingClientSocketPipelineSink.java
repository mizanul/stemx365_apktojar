package org.jboss.netty.channel.socket.http;

import java.net.SocketAddress;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.AbstractChannelSink;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;

final class HttpTunnelingClientSocketPipelineSink extends AbstractChannelSink {
    HttpTunnelingClientSocketPipelineSink() {
    }

    public void eventSunk(ChannelPipeline pipeline, ChannelEvent e) throws Exception {
        HttpTunnelingClientSocketChannel channel = (HttpTunnelingClientSocketChannel) e.getChannel();
        ChannelFuture future = e.getFuture();
        if (e instanceof ChannelStateEvent) {
            ChannelStateEvent stateEvent = (ChannelStateEvent) e;
            ChannelState state = stateEvent.getState();
            Object value = stateEvent.getValue();
            int i = C08601.$SwitchMap$org$jboss$netty$channel$ChannelState[state.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i == 4) {
                            channel.setInterestOpsReal(((Integer) value).intValue(), future);
                        }
                    } else if (value != null) {
                        channel.connectReal((SocketAddress) value, future);
                    } else {
                        channel.closeReal(future);
                    }
                } else if (value != null) {
                    channel.bindReal((SocketAddress) value, future);
                } else {
                    channel.unbindReal(future);
                }
            } else if (Boolean.FALSE.equals(value)) {
                channel.closeReal(future);
            }
        } else if (e instanceof MessageEvent) {
            channel.writeReal((ChannelBuffer) ((MessageEvent) e).getMessage(), future);
        }
    }

    /* renamed from: org.jboss.netty.channel.socket.http.HttpTunnelingClientSocketPipelineSink$1 */
    static /* synthetic */ class C08601 {
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
}
