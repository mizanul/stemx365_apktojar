package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;

public final class Monitor {
    private final ArrayList<Guard> activeGuards;
    private final boolean fair;
    /* access modifiers changed from: private */
    public final ReentrantLock lock;

    public static abstract class Guard {
        final Condition condition;
        final Monitor monitor;
        int waiterCount = 0;

        public abstract boolean isSatisfied();

        protected Guard(Monitor monitor2) {
            this.monitor = (Monitor) Preconditions.checkNotNull(monitor2, "monitor");
            this.condition = monitor2.lock.newCondition();
        }

        public final boolean equals(Object other) {
            return this == other;
        }

        public final int hashCode() {
            return super.hashCode();
        }
    }

    public Monitor() {
        this(false);
    }

    public Monitor(boolean fair2) {
        this.activeGuards = Lists.newArrayListWithCapacity(1);
        this.fair = fair2;
        this.lock = new ReentrantLock(fair2);
    }

    public void enter() {
        this.lock.lock();
    }

    public void enterInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }

    public boolean enter(long time, TimeUnit unit) {
        boolean tryLock;
        ReentrantLock lock2 = this.lock;
        if (!this.fair && lock2.tryLock()) {
            return true;
        }
        long startNanos = System.nanoTime();
        long timeoutNanos = unit.toNanos(time);
        long remainingNanos = timeoutNanos;
        boolean interruptIgnored = false;
        while (true) {
            try {
                tryLock = lock2.tryLock(remainingNanos, TimeUnit.NANOSECONDS);
                break;
            } catch (InterruptedException e) {
                interruptIgnored = true;
                remainingNanos = timeoutNanos - (System.nanoTime() - startNanos);
            } catch (Throwable th) {
                if (1 != 0) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (interruptIgnored) {
            Thread.currentThread().interrupt();
        }
        return tryLock;
    }

    public boolean enterInterruptibly(long time, TimeUnit unit) throws InterruptedException {
        return this.lock.tryLock(time, unit);
    }

    public boolean tryEnter() {
        return this.lock.tryLock();
    }

    public void enterWhen(Guard guard) throws InterruptedException {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            boolean reentrant = lock2.isHeldByCurrentThread();
            boolean success = false;
            lock2.lockInterruptibly();
            try {
                waitInterruptibly(guard, reentrant);
                success = true;
            } finally {
                if (!success) {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public void enterWhenUninterruptibly(Guard guard) {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            boolean reentrant = lock2.isHeldByCurrentThread();
            boolean success = false;
            lock2.lock();
            try {
                waitUninterruptibly(guard, reentrant);
                success = true;
            } finally {
                if (!success) {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterWhen(Guard guard, long time, TimeUnit unit) throws InterruptedException {
        long startNanos;
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            boolean reentrant = lock2.isHeldByCurrentThread();
            if (this.fair || !lock2.tryLock()) {
                long startNanos2 = System.nanoTime();
                if (!lock2.tryLock(time, unit)) {
                    return false;
                }
                startNanos = unit.toNanos(time) - (System.nanoTime() - startNanos2);
            } else {
                startNanos = unit.toNanos(time);
            }
            boolean satisfied = false;
            try {
                satisfied = waitInterruptibly(guard, startNanos, reentrant);
                return satisfied;
            } finally {
                if (!satisfied) {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterWhenUninterruptibly(Guard guard, long time, TimeUnit unit) {
        long startNanos;
        long remainingNanos;
        long timeoutNanos;
        long remainingNanos2;
        Guard guard2 = guard;
        long j = time;
        TimeUnit timeUnit = unit;
        if (guard2.monitor == this) {
            ReentrantLock lock2 = this.lock;
            boolean reentrant = lock2.isHeldByCurrentThread();
            boolean interruptIgnored = false;
            try {
                if (this.fair || !lock2.tryLock()) {
                    remainingNanos = System.nanoTime();
                    timeoutNanos = timeUnit.toNanos(j);
                    remainingNanos2 = timeoutNanos;
                    while (true) {
                        break;
                    }
                    if (lock2.tryLock(remainingNanos2, TimeUnit.NANOSECONDS)) {
                        startNanos = timeoutNanos - (System.nanoTime() - remainingNanos);
                    } else {
                        long remainingNanos3 = timeoutNanos - (System.nanoTime() - remainingNanos);
                        if (interruptIgnored) {
                            Thread.currentThread().interrupt();
                        }
                        return false;
                    }
                } else {
                    startNanos = timeUnit.toNanos(j);
                }
                boolean satisfied = waitUninterruptibly(guard2, startNanos, reentrant);
                if (!satisfied) {
                    lock2.unlock();
                }
                if (interruptIgnored) {
                    Thread.currentThread().interrupt();
                }
                return satisfied;
            } catch (InterruptedException e) {
                interruptIgnored = true;
                remainingNanos2 = timeoutNanos - (System.nanoTime() - remainingNanos);
            } catch (Throwable th) {
                if (interruptIgnored) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterIf(Guard guard) {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            lock2.lock();
            boolean satisfied = false;
            try {
                satisfied = guard.isSatisfied();
                return satisfied;
            } finally {
                if (!satisfied) {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterIfInterruptibly(Guard guard) throws InterruptedException {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            lock2.lockInterruptibly();
            boolean satisfied = false;
            try {
                satisfied = guard.isSatisfied();
                return satisfied;
            } finally {
                if (!satisfied) {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterIf(Guard guard, long time, TimeUnit unit) {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            if (!enter(time, unit)) {
                return false;
            }
            boolean satisfied = false;
            try {
                satisfied = guard.isSatisfied();
                return satisfied;
            } finally {
                if (!satisfied) {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterIfInterruptibly(Guard guard, long time, TimeUnit unit) throws InterruptedException {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            if (!lock2.tryLock(time, unit)) {
                return false;
            }
            boolean satisfied = false;
            try {
                satisfied = guard.isSatisfied();
                return satisfied;
            } finally {
                if (!satisfied) {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean tryEnterIf(Guard guard) {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            if (!lock2.tryLock()) {
                return false;
            }
            boolean satisfied = false;
            try {
                satisfied = guard.isSatisfied();
                return satisfied;
            } finally {
                if (!satisfied) {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public void waitFor(Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        } else if (this.lock.isHeldByCurrentThread()) {
            waitInterruptibly(guard, true);
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public void waitForUninterruptibly(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        } else if (this.lock.isHeldByCurrentThread()) {
            waitUninterruptibly(guard, true);
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean waitFor(Guard guard, long time, TimeUnit unit) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        } else if (this.lock.isHeldByCurrentThread()) {
            return waitInterruptibly(guard, unit.toNanos(time), true);
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean waitForUninterruptibly(Guard guard, long time, TimeUnit unit) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        } else if (this.lock.isHeldByCurrentThread()) {
            return waitUninterruptibly(guard, unit.toNanos(time), true);
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public void leave() {
        ReentrantLock lock2 = this.lock;
        if (lock2.isHeldByCurrentThread()) {
            try {
                signalConditionsOfSatisfiedGuards((Guard) null);
            } finally {
                lock2.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean isFair() {
        return this.lock.isFair();
    }

    public boolean isOccupied() {
        return this.lock.isLocked();
    }

    public boolean isOccupiedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }

    public int getOccupiedDepth() {
        return this.lock.getHoldCount();
    }

    public int getQueueLength() {
        return this.lock.getQueueLength();
    }

    public boolean hasQueuedThreads() {
        return this.lock.hasQueuedThreads();
    }

    public boolean hasQueuedThread(Thread thread) {
        return this.lock.hasQueuedThread(thread);
    }

    public boolean hasWaiters(Guard guard) {
        if (guard.monitor == this) {
            this.lock.lock();
            try {
                return guard.waiterCount > 0;
            } finally {
                this.lock.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public int getWaitQueueLength(Guard guard) {
        if (guard.monitor == this) {
            this.lock.lock();
            try {
                return guard.waiterCount;
            } finally {
                this.lock.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    private void signalConditionsOfSatisfiedGuards(@Nullable Guard interruptedGuard) {
        ArrayList<Guard> guards = this.activeGuards;
        int guardCount = guards.size();
        int i = 0;
        while (i < guardCount) {
            try {
                Guard guard = guards.get(i);
                if (guard != interruptedGuard || guard.waiterCount != 1) {
                    if (guard.isSatisfied()) {
                        guard.condition.signal();
                        return;
                    }
                }
                i++;
            } catch (Throwable throwable) {
                for (int i2 = 0; i2 < guardCount; i2++) {
                    guards.get(i2).condition.signalAll();
                }
                throw Throwables.propagate(throwable);
            }
        }
    }

    private void incrementWaiters(Guard guard) {
        int waiters = guard.waiterCount;
        guard.waiterCount = waiters + 1;
        if (waiters == 0) {
            this.activeGuards.add(guard);
        }
    }

    private void decrementWaiters(Guard guard) {
        int waiters = guard.waiterCount - 1;
        guard.waiterCount = waiters;
        if (waiters == 0) {
            this.activeGuards.remove(guard);
        }
    }

    private void waitInterruptibly(Guard guard, boolean signalBeforeWaiting) throws InterruptedException {
        if (!guard.isSatisfied()) {
            if (signalBeforeWaiting) {
                signalConditionsOfSatisfiedGuards((Guard) null);
            }
            incrementWaiters(guard);
            try {
                Condition condition = guard.condition;
                do {
                    condition.await();
                } while (!guard.isSatisfied());
                decrementWaiters(guard);
            } catch (InterruptedException interrupt) {
                signalConditionsOfSatisfiedGuards(guard);
                throw interrupt;
            } catch (Throwable th) {
                decrementWaiters(guard);
                throw th;
            }
        }
    }

    private void waitUninterruptibly(Guard guard, boolean signalBeforeWaiting) {
        if (!guard.isSatisfied()) {
            if (signalBeforeWaiting) {
                signalConditionsOfSatisfiedGuards((Guard) null);
            }
            incrementWaiters(guard);
            try {
                Condition condition = guard.condition;
                do {
                    condition.awaitUninterruptibly();
                } while (!guard.isSatisfied());
            } finally {
                decrementWaiters(guard);
            }
        }
    }

    private boolean waitInterruptibly(Guard guard, long remainingNanos, boolean signalBeforeWaiting) throws InterruptedException {
        if (guard.isSatisfied()) {
            return true;
        }
        if (signalBeforeWaiting) {
            signalConditionsOfSatisfiedGuards((Guard) null);
        }
        incrementWaiters(guard);
        try {
            Condition condition = guard.condition;
            while (remainingNanos > 0) {
                remainingNanos = condition.awaitNanos(remainingNanos);
                if (guard.isSatisfied()) {
                    decrementWaiters(guard);
                    return true;
                }
            }
            decrementWaiters(guard);
            return false;
        } catch (InterruptedException interrupt) {
            signalConditionsOfSatisfiedGuards(guard);
            throw interrupt;
        } catch (Throwable th) {
            decrementWaiters(guard);
            throw th;
        }
    }

    private boolean waitUninterruptibly(Guard guard, long timeoutNanos, boolean signalBeforeWaiting) {
        long remainingNanos;
        if (guard.isSatisfied()) {
            return true;
        }
        long startNanos = System.nanoTime();
        if (signalBeforeWaiting) {
            signalConditionsOfSatisfiedGuards((Guard) null);
        }
        boolean interruptIgnored = false;
        try {
            incrementWaiters(guard);
            try {
                Condition condition = guard.condition;
                remainingNanos = timeoutNanos;
                while (remainingNanos > 0) {
                    remainingNanos = condition.awaitNanos(remainingNanos);
                    if (guard.isSatisfied()) {
                        decrementWaiters(guard);
                    }
                }
                decrementWaiters(guard);
                if (interruptIgnored) {
                    Thread.currentThread().interrupt();
                }
                return false;
            } catch (InterruptedException e) {
                signalConditionsOfSatisfiedGuards(guard);
                interruptIgnored = true;
                remainingNanos = timeoutNanos - (System.nanoTime() - startNanos);
            } catch (Throwable th) {
                decrementWaiters(guard);
                throw th;
            }
        } finally {
            if (interruptIgnored) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
