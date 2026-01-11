package org.jboss.netty.handler.traffic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.InternalLoggerFactory;
import org.jboss.netty.util.DefaultObjectSizeEstimator;
import org.jboss.netty.util.ExternalResourceReleasable;
import org.jboss.netty.util.ObjectSizeEstimator;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;
import org.jboss.netty.util.TimerTask;

public abstract class AbstractTrafficShapingHandler extends SimpleChannelHandler implements ExternalResourceReleasable {
    public static final long DEFAULT_CHECK_INTERVAL = 1000;
    private static final long MINIMAL_WAIT = 10;
    static InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) AbstractTrafficShapingHandler.class);
    protected long checkInterval = 1000;
    private ObjectSizeEstimator objectSizeEstimator;
    private long readLimit;
    final AtomicBoolean release = new AtomicBoolean(false);
    private volatile Timeout timeout;
    protected Timer timer;
    protected TrafficCounter trafficCounter;
    private long writeLimit;

    private void init(ObjectSizeEstimator newObjectSizeEstimator, Timer newTimer, long newWriteLimit, long newReadLimit, long newCheckInterval) {
        this.objectSizeEstimator = newObjectSizeEstimator;
        this.timer = newTimer;
        this.writeLimit = newWriteLimit;
        this.readLimit = newReadLimit;
        this.checkInterval = newCheckInterval;
    }

    /* access modifiers changed from: package-private */
    public void setTrafficCounter(TrafficCounter newTrafficCounter) {
        this.trafficCounter = newTrafficCounter;
    }

    public AbstractTrafficShapingHandler(Timer timer2, long writeLimit2, long readLimit2, long checkInterval2) {
        init(new DefaultObjectSizeEstimator(), timer2, writeLimit2, readLimit2, checkInterval2);
    }

    public AbstractTrafficShapingHandler(ObjectSizeEstimator objectSizeEstimator2, Timer timer2, long writeLimit2, long readLimit2, long checkInterval2) {
        init(objectSizeEstimator2, timer2, writeLimit2, readLimit2, checkInterval2);
    }

    public AbstractTrafficShapingHandler(Timer timer2, long writeLimit2, long readLimit2) {
        init(new DefaultObjectSizeEstimator(), timer2, writeLimit2, readLimit2, 1000);
    }

    public AbstractTrafficShapingHandler(ObjectSizeEstimator objectSizeEstimator2, Timer timer2, long writeLimit2, long readLimit2) {
        init(objectSizeEstimator2, timer2, writeLimit2, readLimit2, 1000);
    }

    public AbstractTrafficShapingHandler(Timer timer2) {
        init(new DefaultObjectSizeEstimator(), timer2, 0, 0, 1000);
    }

    public AbstractTrafficShapingHandler(ObjectSizeEstimator objectSizeEstimator2, Timer timer2) {
        init(objectSizeEstimator2, timer2, 0, 0, 1000);
    }

    public AbstractTrafficShapingHandler(Timer timer2, long checkInterval2) {
        init(new DefaultObjectSizeEstimator(), timer2, 0, 0, checkInterval2);
    }

    public AbstractTrafficShapingHandler(ObjectSizeEstimator objectSizeEstimator2, Timer timer2, long checkInterval2) {
        init(objectSizeEstimator2, timer2, 0, 0, checkInterval2);
    }

    public void configure(long newWriteLimit, long newReadLimit, long newCheckInterval) {
        configure(newWriteLimit, newReadLimit);
        configure(newCheckInterval);
    }

    public void configure(long newWriteLimit, long newReadLimit) {
        this.writeLimit = newWriteLimit;
        this.readLimit = newReadLimit;
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.resetAccounting(System.currentTimeMillis() + 1);
        }
    }

    public void configure(long newCheckInterval) {
        this.checkInterval = newCheckInterval;
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.configure(newCheckInterval);
        }
    }

    /* access modifiers changed from: protected */
    public void doAccounting(TrafficCounter counter) {
    }

    private class ReopenReadTimerTask implements TimerTask {
        ChannelHandlerContext ctx;

        ReopenReadTimerTask(ChannelHandlerContext ctx2) {
            this.ctx = ctx2;
        }

        public void run(Timeout timeoutArg) throws Exception {
            ChannelHandlerContext channelHandlerContext;
            if (!AbstractTrafficShapingHandler.this.release.get() && (channelHandlerContext = this.ctx) != null && channelHandlerContext.getChannel() != null && this.ctx.getChannel().isConnected()) {
                this.ctx.setAttachment((Object) null);
                this.ctx.getChannel().setReadable(true);
            }
        }
    }

    private static long getTimeToWait(long limit, long bytes, long lastTime, long curtime) {
        long interval = curtime - lastTime;
        if (interval == 0) {
            return 0;
        }
        return ((((1000 * bytes) / limit) - interval) / MINIMAL_WAIT) * MINIMAL_WAIT;
    }

    public void messageReceived(ChannelHandlerContext arg0, MessageEvent arg1) throws Exception {
        try {
            long curtime = System.currentTimeMillis();
            long size = (long) this.objectSizeEstimator.estimateSize(arg1.getMessage());
            if (this.trafficCounter != null) {
                this.trafficCounter.bytesRecvFlowControl(arg0, size);
                if (this.readLimit != 0) {
                    long wait = getTimeToWait(this.readLimit, this.trafficCounter.getCurrentReadBytes(), this.trafficCounter.getLastTime(), curtime);
                    if (wait >= MINIMAL_WAIT) {
                        Channel channel = arg0.getChannel();
                        if (channel == null || !channel.isConnected()) {
                            if (this.release.get()) {
                                super.messageReceived(arg0, arg1);
                                return;
                            }
                            Thread.sleep(wait);
                        } else if (this.timer == null) {
                            if (this.release.get()) {
                                super.messageReceived(arg0, arg1);
                                return;
                            }
                            Thread.sleep(wait);
                            super.messageReceived(arg0, arg1);
                            return;
                        } else if (arg0.getAttachment() == null) {
                            arg0.setAttachment(Boolean.TRUE);
                            channel.setReadable(false);
                            this.timeout = this.timer.newTimeout(new ReopenReadTimerTask(arg0), wait, TimeUnit.MILLISECONDS);
                        } else if (this.release.get()) {
                            super.messageReceived(arg0, arg1);
                            return;
                        } else {
                            Thread.sleep(wait);
                        }
                    }
                } else {
                    return;
                }
            }
            super.messageReceived(arg0, arg1);
        } finally {
            super.messageReceived(arg0, arg1);
        }
    }

    public void writeRequested(ChannelHandlerContext arg0, MessageEvent arg1) throws Exception {
        try {
            long curtime = System.currentTimeMillis();
            long size = (long) this.objectSizeEstimator.estimateSize(arg1.getMessage());
            if (this.trafficCounter != null) {
                this.trafficCounter.bytesWriteFlowControl(size);
                if (this.writeLimit != 0) {
                    long wait = getTimeToWait(this.writeLimit, this.trafficCounter.getCurrentWrittenBytes(), this.trafficCounter.getLastTime(), curtime);
                    if (wait >= MINIMAL_WAIT) {
                        if (this.release.get()) {
                            super.writeRequested(arg0, arg1);
                            return;
                        }
                        Thread.sleep(wait);
                    }
                } else {
                    return;
                }
            }
            super.writeRequested(arg0, arg1);
        } finally {
            super.writeRequested(arg0, arg1);
        }
    }

    public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            ChannelStateEvent cse = (ChannelStateEvent) e;
            if (cse.getState() == ChannelState.INTEREST_OPS) {
                boolean readSuspended = true;
                if ((((Integer) cse.getValue()).intValue() & 1) != 0) {
                    if (ctx.getAttachment() == null) {
                        readSuspended = false;
                    }
                    if (readSuspended) {
                        e.getFuture().setSuccess();
                        return;
                    }
                }
            }
        }
        super.handleDownstream(ctx, e);
    }

    public TrafficCounter getTrafficCounter() {
        return this.trafficCounter;
    }

    public void releaseExternalResources() {
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.stop();
        }
        this.release.set(true);
        if (this.timeout != null) {
            this.timeout.cancel();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TrafficShaping with Write Limit: ");
        sb.append(this.writeLimit);
        sb.append(" Read Limit: ");
        sb.append(this.readLimit);
        sb.append(" and Counter: ");
        TrafficCounter trafficCounter2 = this.trafficCounter;
        sb.append(trafficCounter2 != null ? trafficCounter2.toString() : "none");
        return sb.toString();
    }
}
