package org.jboss.netty.channel.socket.oio;

import java.io.PushbackInputStream;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.util.ThreadRenamingRunnable;
import org.jboss.netty.util.internal.DeadLockProofWorker;

class OioClientSocketPipelineSink extends AbstractOioChannelSink {
    private final Executor workerExecutor;

    OioClientSocketPipelineSink(Executor workerExecutor2) {
        this.workerExecutor = workerExecutor2;
    }

    public void eventSunk(ChannelPipeline pipeline, ChannelEvent e) throws Exception {
        OioClientSocketChannel channel = (OioClientSocketChannel) e.getChannel();
        ChannelFuture future = e.getFuture();
        if (e instanceof ChannelStateEvent) {
            ChannelStateEvent stateEvent = (ChannelStateEvent) e;
            ChannelState state = stateEvent.getState();
            Object value = stateEvent.getValue();
            int i = C08671.$SwitchMap$org$jboss$netty$channel$ChannelState[state.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i == 4) {
                            AbstractOioWorker.setInterestOps(channel, future, ((Integer) value).intValue());
                        }
                    } else if (value != null) {
                        connect(channel, future, (SocketAddress) value);
                    } else {
                        AbstractOioWorker.close(channel, future);
                    }
                } else if (value != null) {
                    bind(channel, future, (SocketAddress) value);
                } else {
                    AbstractOioWorker.close(channel, future);
                }
            } else if (Boolean.FALSE.equals(value)) {
                AbstractOioWorker.close(channel, future);
            }
        } else if (e instanceof MessageEvent) {
            OioWorker.write(channel, future, ((MessageEvent) e).getMessage());
        }
    }

    /* renamed from: org.jboss.netty.channel.socket.oio.OioClientSocketPipelineSink$1 */
    static /* synthetic */ class C08671 {
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

    private static void bind(OioClientSocketChannel channel, ChannelFuture future, SocketAddress localAddress) {
        try {
            channel.socket.bind(localAddress);
            future.setSuccess();
            Channels.fireChannelBound((Channel) channel, (SocketAddress) channel.getLocalAddress());
        } catch (Throwable t) {
            future.setFailure(t);
            Channels.fireExceptionCaught((Channel) channel, t);
        }
    }

    private void connect(OioClientSocketChannel channel, ChannelFuture future, SocketAddress remoteAddress) {
        boolean bound = channel.isBound();
        future.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        try {
            channel.socket.connect(remoteAddress, channel.getConfig().getConnectTimeoutMillis());
            channel.f125in = new PushbackInputStream(channel.socket.getInputStream(), 1);
            channel.out = channel.socket.getOutputStream();
            future.setSuccess();
            if (!bound) {
                Channels.fireChannelBound((Channel) channel, (SocketAddress) channel.getLocalAddress());
            }
            Channels.fireChannelConnected((Channel) channel, (SocketAddress) channel.getRemoteAddress());
            Executor executor = this.workerExecutor;
            OioWorker oioWorker = new OioWorker(channel);
            DeadLockProofWorker.start(executor, new ThreadRenamingRunnable(oioWorker, "Old I/O client worker (" + channel + ')'));
            if (1 != 0 && 1 == 0) {
                AbstractOioWorker.close(channel, future);
            }
        } catch (Throwable th) {
            if (0 != 0 && 0 == 0) {
                AbstractOioWorker.close(channel, future);
            }
            throw th;
        }
    }
}
