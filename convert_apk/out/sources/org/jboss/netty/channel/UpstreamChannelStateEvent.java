package org.jboss.netty.channel;

public class UpstreamChannelStateEvent implements ChannelStateEvent {
    private final Channel channel;
    private final ChannelState state;
    private final Object value;

    public UpstreamChannelStateEvent(Channel channel2, ChannelState state2, Object value2) {
        if (channel2 == null) {
            throw new NullPointerException("channel");
        } else if (state2 != null) {
            this.channel = channel2;
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
        return Channels.succeededFuture(getChannel());
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
        int i = C08431.$SwitchMap$org$jboss$netty$channel$ChannelState[getState().ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        buf.append(getState().name());
                        buf.append(": ");
                        buf.append(getValue());
                    } else {
                        buf.append(" INTEREST_CHANGED");
                    }
                } else if (getValue() != null) {
                    buf.append(" CONNECTED: ");
                    buf.append(getValue());
                } else {
                    buf.append(" DISCONNECTED");
                }
            } else if (getValue() != null) {
                buf.append(" BOUND: ");
                buf.append(getValue());
            } else {
                buf.append(" UNBOUND");
            }
        } else if (Boolean.TRUE.equals(getValue())) {
            buf.append(" OPEN");
        } else {
            buf.append(" CLOSED");
        }
        return buf.toString();
    }

    /* renamed from: org.jboss.netty.channel.UpstreamChannelStateEvent$1 */
    static /* synthetic */ class C08431 {
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
