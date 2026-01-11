package org.jboss.netty.util;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class VirtualExecutorService extends AbstractExecutorService {
    Set<Thread> activeThreads = new MapBackedSet(new IdentityHashMap());

    /* renamed from: e */
    private final Executor f153e;

    /* renamed from: s */
    private final ExecutorService f154s;
    volatile boolean shutdown;
    final Object startStopLock = new Object();

    public VirtualExecutorService(Executor parent) {
        if (parent == null) {
            throw new NullPointerException("parent");
        } else if (parent instanceof ExecutorService) {
            this.f153e = null;
            this.f154s = (ExecutorService) parent;
        } else {
            this.f153e = parent;
            this.f154s = null;
        }
    }

    public boolean isShutdown() {
        boolean z;
        synchronized (this.startStopLock) {
            z = this.shutdown;
        }
        return z;
    }

    public boolean isTerminated() {
        boolean z;
        synchronized (this.startStopLock) {
            z = this.shutdown && this.activeThreads.isEmpty();
        }
        return z;
    }

    public void shutdown() {
        synchronized (this.startStopLock) {
            if (!this.shutdown) {
                this.shutdown = true;
            }
        }
    }

    public List<Runnable> shutdownNow() {
        synchronized (this.startStopLock) {
            if (!isTerminated()) {
                shutdown();
                for (Thread t : this.activeThreads) {
                    t.interrupt();
                }
            }
        }
        return Collections.emptyList();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        boolean isTerminated;
        synchronized (this.startStopLock) {
            while (!isTerminated()) {
                this.startStopLock.wait(TimeUnit.MILLISECONDS.convert(timeout, unit));
            }
            isTerminated = isTerminated();
        }
        return isTerminated;
    }

    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException("command");
        } else if (!this.shutdown) {
            ExecutorService executorService = this.f154s;
            if (executorService != null) {
                executorService.execute(new ChildExecutorRunnable(command));
            } else {
                this.f153e.execute(new ChildExecutorRunnable(command));
            }
        } else {
            throw new RejectedExecutionException();
        }
    }

    private class ChildExecutorRunnable implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Runnable runnable;

        static {
            Class<VirtualExecutorService> cls = VirtualExecutorService.class;
        }

        ChildExecutorRunnable(Runnable runnable2) {
            this.runnable = runnable2;
        }

        public void run() {
            Thread thread = Thread.currentThread();
            synchronized (VirtualExecutorService.this.startStopLock) {
                VirtualExecutorService.this.activeThreads.add(thread);
            }
            try {
                this.runnable.run();
                synchronized (VirtualExecutorService.this.startStopLock) {
                    boolean remove = VirtualExecutorService.this.activeThreads.remove(thread);
                    if (VirtualExecutorService.this.isTerminated()) {
                        VirtualExecutorService.this.startStopLock.notifyAll();
                    }
                }
            } catch (Throwable th) {
                synchronized (VirtualExecutorService.this.startStopLock) {
                    boolean remove2 = VirtualExecutorService.this.activeThreads.remove(thread);
                    if (VirtualExecutorService.this.isTerminated()) {
                        VirtualExecutorService.this.startStopLock.notifyAll();
                    }
                    throw th;
                }
            }
        }
    }
}
