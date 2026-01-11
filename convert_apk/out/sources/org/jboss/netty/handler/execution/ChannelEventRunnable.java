package org.jboss.netty.handler.execution;

import java.util.concurrent.Executor;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.util.EstimatableObjectWrapper;

public abstract class ChannelEventRunnable implements Runnable, EstimatableObjectWrapper {
    protected static final ThreadLocal<Executor> PARENT = new ThreadLocal<>();
    protected final ChannelHandlerContext ctx;

    /* renamed from: e */
    protected final ChannelEvent f146e;
    int estimatedSize;
    private final Executor executor;

    /* access modifiers changed from: protected */
    public abstract void doRun();

    public ChannelEventRunnable(ChannelHandlerContext ctx2, ChannelEvent e, Executor executor2) {
        this.ctx = ctx2;
        this.f146e = e;
        this.executor = executor2;
    }

    public ChannelHandlerContext getContext() {
        return this.ctx;
    }

    public ChannelEvent getEvent() {
        return this.f146e;
    }

    public Object unwrap() {
        return this.f146e;
    }

    public final void run() {
        try {
            PARENT.set(this.executor);
            doRun();
        } finally {
            PARENT.remove();
        }
    }
}
