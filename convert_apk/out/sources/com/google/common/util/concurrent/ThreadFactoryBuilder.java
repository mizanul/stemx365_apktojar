package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.lang.Thread;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadFactoryBuilder {
    private ThreadFactory backingThreadFactory = null;
    private Boolean daemon = null;
    private String nameFormat = null;
    private Integer priority = null;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;

    public ThreadFactoryBuilder setNameFormat(String nameFormat2) {
        String.format(nameFormat2, new Object[]{0});
        this.nameFormat = nameFormat2;
        return this;
    }

    public ThreadFactoryBuilder setDaemon(boolean daemon2) {
        this.daemon = Boolean.valueOf(daemon2);
        return this;
    }

    public ThreadFactoryBuilder setPriority(int priority2) {
        Preconditions.checkArgument(priority2 >= 1, "Thread priority (%s) must be >= %s", Integer.valueOf(priority2), 1);
        Preconditions.checkArgument(priority2 <= 10, "Thread priority (%s) must be <= %s", Integer.valueOf(priority2), 10);
        this.priority = Integer.valueOf(priority2);
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler2) {
        this.uncaughtExceptionHandler = (Thread.UncaughtExceptionHandler) Preconditions.checkNotNull(uncaughtExceptionHandler2);
        return this;
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory backingThreadFactory2) {
        this.backingThreadFactory = (ThreadFactory) Preconditions.checkNotNull(backingThreadFactory2);
        return this;
    }

    public ThreadFactory build() {
        return build(this);
    }

    private static ThreadFactory build(ThreadFactoryBuilder builder) {
        String nameFormat2 = builder.nameFormat;
        Boolean daemon2 = builder.daemon;
        Integer priority2 = builder.priority;
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler2 = builder.uncaughtExceptionHandler;
        ThreadFactory threadFactory = builder.backingThreadFactory;
        if (threadFactory == null) {
            threadFactory = Executors.defaultThreadFactory();
        }
        final ThreadFactory backingThreadFactory2 = threadFactory;
        final AtomicLong count = nameFormat2 != null ? new AtomicLong(0) : null;
        final String str = nameFormat2;
        final Boolean bool = daemon2;
        final Integer num = priority2;
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler3 = uncaughtExceptionHandler2;
        return new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                Thread thread = backingThreadFactory2.newThread(runnable);
                String str = str;
                if (str != null) {
                    thread.setName(String.format(str, new Object[]{Long.valueOf(count.getAndIncrement())}));
                }
                Boolean bool = bool;
                if (bool != null) {
                    thread.setDaemon(bool.booleanValue());
                }
                Integer num = num;
                if (num != null) {
                    thread.setPriority(num.intValue());
                }
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler = uncaughtExceptionHandler3;
                if (uncaughtExceptionHandler != null) {
                    thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
                }
                return thread;
            }
        };
    }
}
