package org.ros.concurrent;

public class WallTimeRate implements Rate {
    private final long delay;
    private long time = 0;

    public WallTimeRate(int hz) {
        this.delay = (long) (1000 / hz);
    }

    public void sleep() {
        long delta = System.currentTimeMillis() - this.time;
        while (true) {
            long j = this.delay;
            if (delta >= j) {
                break;
            }
            try {
                Thread.sleep(j - delta);
                delta = System.currentTimeMillis() - this.time;
            } catch (InterruptedException e) {
            }
        }
        this.time = System.currentTimeMillis();
    }
}
