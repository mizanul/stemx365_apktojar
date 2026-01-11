package com.google.common.base;

import java.util.concurrent.TimeUnit;

public final class Stopwatch {
    private long elapsedNanos;
    private boolean isRunning;
    private long startTick;
    private final Ticker ticker;

    public Stopwatch() {
        this(Ticker.systemTicker());
    }

    public Stopwatch(Ticker ticker2) {
        this.ticker = (Ticker) Preconditions.checkNotNull(ticker2);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public Stopwatch start() {
        Preconditions.checkState(!this.isRunning);
        this.isRunning = true;
        this.startTick = this.ticker.read();
        return this;
    }

    public Stopwatch stop() {
        long tick = this.ticker.read();
        Preconditions.checkState(this.isRunning);
        this.isRunning = false;
        this.elapsedNanos += tick - this.startTick;
        return this;
    }

    public Stopwatch reset() {
        this.elapsedNanos = 0;
        this.isRunning = false;
        return this;
    }

    private long elapsedNanos() {
        return this.isRunning ? (this.ticker.read() - this.startTick) + this.elapsedNanos : this.elapsedNanos;
    }

    public long elapsedTime(TimeUnit desiredUnit) {
        return desiredUnit.convert(elapsedNanos(), TimeUnit.NANOSECONDS);
    }

    public long elapsedMillis() {
        return elapsedTime(TimeUnit.MILLISECONDS);
    }

    public String toString() {
        return toString(4);
    }

    public String toString(int significantDigits) {
        long nanos = elapsedNanos();
        TimeUnit unit = chooseUnit(nanos);
        double value = ((double) nanos) / ((double) TimeUnit.NANOSECONDS.convert(1, unit));
        return String.format("%." + significantDigits + "g %s", new Object[]{Double.valueOf(value), abbreviate(unit)});
    }

    private static TimeUnit chooseUnit(long nanos) {
        if (TimeUnit.SECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0) {
            return TimeUnit.SECONDS;
        }
        if (TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0) {
            return TimeUnit.MILLISECONDS;
        }
        if (TimeUnit.MICROSECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0) {
            return TimeUnit.MICROSECONDS;
        }
        return TimeUnit.NANOSECONDS;
    }

    /* renamed from: com.google.common.base.Stopwatch$1 */
    static /* synthetic */ class C03881 {
        static final /* synthetic */ int[] $SwitchMap$java$util$concurrent$TimeUnit;

        static {
            int[] iArr = new int[TimeUnit.values().length];
            $SwitchMap$java$util$concurrent$TimeUnit = iArr;
            try {
                iArr[TimeUnit.NANOSECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MICROSECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MILLISECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private static String abbreviate(TimeUnit unit) {
        int i = C03881.$SwitchMap$java$util$concurrent$TimeUnit[unit.ordinal()];
        if (i == 1) {
            return "ns";
        }
        if (i == 2) {
            return "μs";
        }
        if (i == 3) {
            return "ms";
        }
        if (i == 4) {
            return "s";
        }
        throw new AssertionError();
    }
}
