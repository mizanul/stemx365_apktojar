package org.jboss.netty.channel;

public class FailedChannelFuture extends CompleteChannelFuture {
    private final Throwable cause;

    public FailedChannelFuture(Channel channel, Throwable cause2) {
        super(channel);
        if (cause2 != null) {
            this.cause = cause2;
            return;
        }
        throw new NullPointerException("cause");
    }

    public Throwable getCause() {
        return this.cause;
    }

    public boolean isSuccess() {
        return false;
    }

    public ChannelFuture rethrowIfFailed() throws Exception {
        Throwable th = this.cause;
        if (th instanceof Exception) {
            throw ((Exception) th);
        } else if (th instanceof Error) {
            throw ((Error) th);
        } else {
            throw new RuntimeException(this.cause);
        }
    }

    public ChannelFuture sync() throws InterruptedException {
        rethrow();
        return this;
    }

    public ChannelFuture syncUninterruptibly() {
        rethrow();
        return this;
    }

    private void rethrow() {
        Throwable th = this.cause;
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else if (th instanceof Error) {
            throw ((Error) th);
        } else {
            throw new ChannelException(this.cause);
        }
    }
}
