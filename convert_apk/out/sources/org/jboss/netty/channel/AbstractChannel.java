package org.jboss.netty.channel;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentMap;
import org.jboss.netty.util.internal.ConcurrentHashMap;

public abstract class AbstractChannel implements Channel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final ConcurrentMap<Integer, Channel> allChannels = new ConcurrentHashMap();
    private volatile Object attachment;
    private final ChannelCloseFuture closeFuture = new ChannelCloseFuture();
    private final ChannelFactory factory;

    /* renamed from: id */
    private final Integer f117id;
    private volatile int interestOps = 1;
    private final Channel parent;
    private final ChannelPipeline pipeline;
    private String strVal;
    private boolean strValConnected;
    private final ChannelFuture succeededFuture = new SucceededChannelFuture(this);

    private static Integer allocateId(Channel channel) {
        Integer id = Integer.valueOf(System.identityHashCode(channel));
        while (allChannels.putIfAbsent(id, channel) != null) {
            id = Integer.valueOf(id.intValue() + 1);
        }
        return id;
    }

    protected AbstractChannel(Channel parent2, ChannelFactory factory2, ChannelPipeline pipeline2, ChannelSink sink) {
        this.parent = parent2;
        this.factory = factory2;
        this.pipeline = pipeline2;
        this.f117id = allocateId(this);
        pipeline2.attach(this, sink);
    }

    protected AbstractChannel(Integer id, Channel parent2, ChannelFactory factory2, ChannelPipeline pipeline2, ChannelSink sink) {
        this.f117id = id;
        this.parent = parent2;
        this.factory = factory2;
        this.pipeline = pipeline2;
        pipeline2.attach(this, sink);
    }

    public final Integer getId() {
        return this.f117id;
    }

    public Channel getParent() {
        return this.parent;
    }

    public ChannelFactory getFactory() {
        return this.factory;
    }

    public ChannelPipeline getPipeline() {
        return this.pipeline;
    }

    /* access modifiers changed from: protected */
    public ChannelFuture getSucceededFuture() {
        return this.succeededFuture;
    }

    /* access modifiers changed from: protected */
    public ChannelFuture getUnsupportedOperationFuture() {
        return new FailedChannelFuture(this, new UnsupportedOperationException());
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    public final boolean equals(Object o) {
        return this == o;
    }

    public final int compareTo(Channel o) {
        return getId().compareTo(o.getId());
    }

    public boolean isOpen() {
        return !this.closeFuture.isDone();
    }

    /* access modifiers changed from: protected */
    public boolean setClosed() {
        allChannels.remove(this.f117id);
        return this.closeFuture.setClosed();
    }

    public ChannelFuture bind(SocketAddress localAddress) {
        return Channels.bind(this, localAddress);
    }

    public ChannelFuture unbind() {
        return Channels.unbind(this);
    }

    public ChannelFuture close() {
        ChannelFuture close = Channels.close(this);
        return this.closeFuture;
    }

    public ChannelFuture getCloseFuture() {
        return this.closeFuture;
    }

    public ChannelFuture connect(SocketAddress remoteAddress) {
        return Channels.connect(this, remoteAddress);
    }

    public ChannelFuture disconnect() {
        return Channels.disconnect(this);
    }

    public int getInterestOps() {
        return this.interestOps;
    }

    public ChannelFuture setInterestOps(int interestOps2) {
        return Channels.setInterestOps(this, interestOps2);
    }

    /* access modifiers changed from: protected */
    public void setInterestOpsNow(int interestOps2) {
        this.interestOps = interestOps2;
    }

    public boolean isReadable() {
        return (getInterestOps() & 1) != 0;
    }

    public boolean isWritable() {
        return (getInterestOps() & 4) == 0;
    }

    public ChannelFuture setReadable(boolean readable) {
        if (readable) {
            return setInterestOps(getInterestOps() | 1);
        }
        return setInterestOps(getInterestOps() & -2);
    }

    public ChannelFuture write(Object message) {
        return Channels.write(this, message);
    }

    public ChannelFuture write(Object message, SocketAddress remoteAddress) {
        return Channels.write((Channel) this, message, remoteAddress);
    }

    public Object getAttachment() {
        return this.attachment;
    }

    public void setAttachment(Object attachment2) {
        this.attachment = attachment2;
    }

    public String toString() {
        String str;
        boolean connected = isConnected();
        if (this.strValConnected == connected && (str = this.strVal) != null) {
            return str;
        }
        StringBuilder buf = new StringBuilder(128);
        buf.append("[id: 0x");
        buf.append(getIdString());
        SocketAddress localAddress = getLocalAddress();
        SocketAddress remoteAddress = getRemoteAddress();
        if (remoteAddress != null) {
            buf.append(", ");
            String str2 = " => ";
            if (getParent() == null) {
                buf.append(localAddress);
                if (!connected) {
                    str2 = " :> ";
                }
                buf.append(str2);
                buf.append(remoteAddress);
            } else {
                buf.append(remoteAddress);
                if (!connected) {
                    str2 = " :> ";
                }
                buf.append(str2);
                buf.append(localAddress);
            }
        } else if (localAddress != null) {
            buf.append(", ");
            buf.append(localAddress);
        }
        buf.append(']');
        String strVal2 = buf.toString();
        this.strVal = strVal2;
        this.strValConnected = connected;
        return strVal2;
    }

    private String getIdString() {
        String answer = Integer.toHexString(this.f117id.intValue());
        switch (answer.length()) {
            case 0:
                return "00000000";
            case 1:
                return "0000000" + answer;
            case 2:
                return "000000" + answer;
            case 3:
                return "00000" + answer;
            case 4:
                return "0000" + answer;
            case 5:
                return "000" + answer;
            case 6:
                return "00" + answer;
            case 7:
                return "0" + answer;
            default:
                return answer;
        }
    }

    private final class ChannelCloseFuture extends DefaultChannelFuture {
        public ChannelCloseFuture() {
            super(AbstractChannel.this, false);
        }

        public boolean setSuccess() {
            return false;
        }

        public boolean setFailure(Throwable cause) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean setClosed() {
            return super.setSuccess();
        }
    }
}
