package org.jboss.netty.channel.socket.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;

class NioDatagramPipelineSink extends AbstractNioChannelSink {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final WorkerPool<NioDatagramWorker> workerPool;

    NioDatagramPipelineSink(WorkerPool<NioDatagramWorker> workerPool2) {
        this.workerPool = workerPool2;
    }

    public void eventSunk(ChannelPipeline pipeline, ChannelEvent e) throws Exception {
        NioDatagramChannel channel = (NioDatagramChannel) e.getChannel();
        ChannelFuture future = e.getFuture();
        if (e instanceof ChannelStateEvent) {
            ChannelStateEvent stateEvent = (ChannelStateEvent) e;
            ChannelState state = stateEvent.getState();
            Object value = stateEvent.getValue();
            int i = C08641.$SwitchMap$org$jboss$netty$channel$ChannelState[state.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i == 4) {
                            channel.worker.setInterestOps(channel, future, ((Integer) value).intValue());
                        }
                    } else if (value != null) {
                        connect(channel, future, (InetSocketAddress) value);
                    } else {
                        NioDatagramWorker.disconnect(channel, future);
                    }
                } else if (value != null) {
                    bind(channel, future, (InetSocketAddress) value);
                } else {
                    channel.worker.close(channel, future);
                }
            } else if (Boolean.FALSE.equals(value)) {
                channel.worker.close(channel, future);
            }
        } else if (e instanceof MessageEvent) {
            boolean offer = channel.writeBufferQueue.offer((MessageEvent) e);
            channel.worker.writeFromUserCode(channel);
        }
    }

    /* renamed from: org.jboss.netty.channel.socket.nio.NioDatagramPipelineSink$1 */
    static /* synthetic */ class C08641 {
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

    private static void close(NioDatagramChannel channel, ChannelFuture future) {
        try {
            channel.getDatagramChannel().socket().close();
            if (channel.setClosed()) {
                future.setSuccess();
                if (channel.isBound()) {
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

    private static void bind(NioDatagramChannel channel, ChannelFuture future, InetSocketAddress address) {
        boolean bound = false;
        try {
            channel.getDatagramChannel().socket().bind(address);
            bound = true;
            future.setSuccess();
            Channels.fireChannelBound((Channel) channel, (SocketAddress) address);
            channel.worker.register(channel, (ChannelFuture) null);
            if (1 == 0 && 1 != 0) {
                close(channel, future);
            }
        } catch (Throwable th) {
            if (0 == 0 && bound) {
                close(channel, future);
            }
            throw th;
        }
    }

    private static void connect(NioDatagramChannel channel, ChannelFuture future, SocketAddress remoteAddress) {
        boolean bound = channel.isBound();
        future.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        channel.remoteAddress = null;
        try {
            channel.getDatagramChannel().connect(remoteAddress);
            future.setSuccess();
            if (!bound) {
                Channels.fireChannelBound((Channel) channel, (SocketAddress) channel.getLocalAddress());
            }
            Channels.fireChannelConnected((Channel) channel, (SocketAddress) channel.getRemoteAddress());
            if (!bound) {
                channel.worker.register(channel, future);
            }
            if (1 != 0 && 1 == 0) {
                channel.worker.close(channel, future);
            }
        } catch (Throwable th) {
            if (0 != 0 && 0 == 0) {
                channel.worker.close(channel, future);
            }
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public NioDatagramWorker nextWorker() {
        return this.workerPool.nextWorker();
    }
}
