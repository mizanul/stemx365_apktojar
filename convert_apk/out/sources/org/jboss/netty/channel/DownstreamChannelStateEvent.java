package org.jboss.netty.channel;

public class DownstreamChannelStateEvent implements ChannelStateEvent {
    private final Channel channel;
    private final ChannelFuture future;
    private final ChannelState state;
    private final Object value;

    public DownstreamChannelStateEvent(Channel channel2, ChannelFuture future2, ChannelState state2, Object value2) {
        if (channel2 == null) {
            throw new NullPointerException("channel");
        } else if (future2 == null) {
            throw new NullPointerException("future");
        } else if (state2 != null) {
            this.channel = channel2;
            this.future = future2;
            this.state = state2;
            this.value = value2;
        } else {
            throw new NullPointerException("state");
        }
    }

    public Channel getChannel() {
        return this.channel;
    }

    public ChannelFuture getFuture() {
        return this.future;
    }

    public ChannelState getState() {
        return this.state;
    }

    public Object getValue() {
        return this.value;
    }

    public String toString() {
        String channelString = getChannel().toString();
        StringBuilder buf = new StringBuilder(channelString.length() + 64);
        buf.append(channelString);
        int i = C08391.$SwitchMap$org$jboss$netty$channel$ChannelState[getState().ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        buf.append(' ');
                        buf.append(getState().name());
                        buf.append(": ");
                        buf.append(getValue());
                    } else {
                        buf.append(" CHANGE_INTEREST: ");
                        buf.append(getValue());
                    }
                } else if (getValue() != null) {
                    buf.append(" CONNECT: ");
                    buf.append(getValue());
                } else {
                    buf.append(" DISCONNECT");
                }
            } else if (getValue() != null) {
                buf.append(" BIND: ");
                buf.append(getValue());
            } else {
                buf.append(" UNBIND");
            }
        } else if (Boolean.TRUE.equals(getValue())) {
            buf.append(" OPEN");
        } else {
            buf.append(" CLOSE");
        }
        return buf.toString();
    }

    /* renamed from: org.jboss.netty.channel.DownstreamChannelStateEvent$1 */
    static /* synthetic */ class C08391 {
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
