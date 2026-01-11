package org.ros.message;

import kotlin.time.DurationKt;

public class Duration implements Comparable<Duration> {
    public static final Duration MAX_VALUE = new Duration(Integer.MAX_VALUE, 999999999);
    public int nsecs;
    public int secs;

    public Duration() {
    }

    public Duration(int secs2, int nsecs2) {
        this.secs = secs2;
        this.nsecs = nsecs2;
        normalize();
    }

    public Duration(double secs2) {
        int i = (int) secs2;
        this.secs = i;
        this.nsecs = (int) ((secs2 - ((double) i)) * 1.0E9d);
        normalize();
    }

    public Duration(Duration t) {
        this.secs = t.secs;
        this.nsecs = t.nsecs;
    }

    public Duration add(Duration d) {
        return new Duration(this.secs + d.secs, this.nsecs + d.nsecs);
    }

    public Duration subtract(Duration d) {
        return new Duration(this.secs - d.secs, this.nsecs - d.nsecs);
    }

    public static Duration fromMillis(long durationInMillis) {
        return new Duration((int) (durationInMillis / 1000), ((int) (durationInMillis % 1000)) * DurationKt.NANOS_IN_MILLIS);
    }

    public static Duration fromNano(long durationInNs) {
        return new Duration((int) (durationInNs / 1000000000), (int) (durationInNs % 1000000000));
    }

    public void normalize() {
        while (true) {
            int i = this.nsecs;
            if (i >= 0) {
                break;
            }
            this.nsecs = i + 1000000000;
            this.secs--;
        }
        while (true) {
            int i2 = this.nsecs;
            if (i2 >= 1000000000) {
                this.nsecs = i2 - 1000000000;
                this.secs++;
            } else {
                return;
            }
        }
    }

    public long totalNsecs() {
        return (((long) this.secs) * 1000000000) + ((long) this.nsecs);
    }

    public boolean isZero() {
        return totalNsecs() == 0;
    }

    public boolean isPositive() {
        return totalNsecs() > 0;
    }

    public boolean isNegative() {
        return totalNsecs() < 0;
    }

    public String toString() {
        return this.secs + ":" + this.nsecs;
    }

    public int hashCode() {
        return (((1 * 31) + this.nsecs) * 31) + this.secs;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Duration other = (Duration) obj;
        if (this.nsecs == other.nsecs && this.secs == other.secs) {
            return true;
        }
        return false;
    }

    public int compareTo(Duration d) {
        int i = this.secs;
        int i2 = d.secs;
        if (i > i2) {
            return 1;
        }
        if (i == i2 && this.nsecs > d.nsecs) {
            return 1;
        }
        if (this.secs == d.secs && this.nsecs == d.nsecs) {
            return 0;
        }
        return -1;
    }
}
