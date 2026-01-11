package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(mo11628d1 = {"\u0000(\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a \u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u0005H\u0003ø\u0001\u0000¢\u0006\u0002\u0010&\u001a\u0018\u0010'\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u0018\u0010)\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u0018\u0010+\u001a\u00020\u00072\u0006\u0010,\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u0018\u0010-\u001a\u00020\u00072\u0006\u0010.\u001a\u00020\u0001H\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a\u0010\u0010/\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u0001H\u0002\u001a\u0010\u00100\u001a\u00020\u00012\u0006\u0010.\u001a\u00020\u0001H\u0002\u001a\u001f\u00101\u001a\u00020\u0007*\u00020\b2\u0006\u00102\u001a\u00020\u0007H\nø\u0001\u0000¢\u0006\u0004\b3\u00104\u001a\u001f\u00101\u001a\u00020\u0007*\u00020\u00052\u0006\u00102\u001a\u00020\u0007H\nø\u0001\u0000¢\u0006\u0004\b5\u00106\u001a \u00107\u001a\u00020\u0007*\u00020\b2\n\u00108\u001a\u000609j\u0002`:H\u0007ø\u0001\u0000¢\u0006\u0002\u0010;\u001a \u00107\u001a\u00020\u0007*\u00020\u00052\n\u00108\u001a\u000609j\u0002`:H\u0007ø\u0001\u0000¢\u0006\u0002\u0010<\u001a \u00107\u001a\u00020\u0007*\u00020\u00012\n\u00108\u001a\u000609j\u0002`:H\u0007ø\u0001\u0000¢\u0006\u0002\u0010=\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000\"!\u0010\u0006\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00058FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\r\u001a\u0004\b\u000b\u0010\u000e\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00018FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\u000f\u001a\u0004\b\u000b\u0010\u0010\"!\u0010\u0011\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0012\u0010\n\u001a\u0004\b\u0013\u0010\f\"!\u0010\u0011\u001a\u00020\u0007*\u00020\u00058FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000e\"!\u0010\u0011\u001a\u00020\u0007*\u00020\u00018FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0012\u0010\u000f\u001a\u0004\b\u0013\u0010\u0010\"!\u0010\u0014\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0015\u0010\n\u001a\u0004\b\u0016\u0010\f\"!\u0010\u0014\u001a\u00020\u0007*\u00020\u00058FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0015\u0010\r\u001a\u0004\b\u0016\u0010\u000e\"!\u0010\u0014\u001a\u00020\u0007*\u00020\u00018FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0015\u0010\u000f\u001a\u0004\b\u0016\u0010\u0010\"!\u0010\u0017\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0018\u0010\n\u001a\u0004\b\u0019\u0010\f\"!\u0010\u0017\u001a\u00020\u0007*\u00020\u00058FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0018\u0010\r\u001a\u0004\b\u0019\u0010\u000e\"!\u0010\u0017\u001a\u00020\u0007*\u00020\u00018FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0018\u0010\u000f\u001a\u0004\b\u0019\u0010\u0010\"!\u0010\u001a\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001b\u0010\n\u001a\u0004\b\u001c\u0010\f\"!\u0010\u001a\u001a\u00020\u0007*\u00020\u00058FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001b\u0010\r\u001a\u0004\b\u001c\u0010\u000e\"!\u0010\u001a\u001a\u00020\u0007*\u00020\u00018FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001b\u0010\u000f\u001a\u0004\b\u001c\u0010\u0010\"!\u0010\u001d\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001e\u0010\n\u001a\u0004\b\u001f\u0010\f\"!\u0010\u001d\u001a\u00020\u0007*\u00020\u00058FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001e\u0010\r\u001a\u0004\b\u001f\u0010\u000e\"!\u0010\u001d\u001a\u00020\u0007*\u00020\u00018FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001e\u0010\u000f\u001a\u0004\b\u001f\u0010\u0010\"!\u0010 \u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b!\u0010\n\u001a\u0004\b\"\u0010\f\"!\u0010 \u001a\u00020\u0007*\u00020\u00058FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b!\u0010\r\u001a\u0004\b\"\u0010\u000e\"!\u0010 \u001a\u00020\u0007*\u00020\u00018FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b!\u0010\u000f\u001a\u0004\b\"\u0010\u0010\u0002\u0004\n\u0002\b\u0019¨\u0006>"}, mo11629d2 = {"MAX_MILLIS", "", "MAX_NANOS", "MAX_NANOS_IN_MILLIS", "NANOS_IN_MILLIS", "", "days", "Lkotlin/time/Duration;", "", "getDays$annotations", "(D)V", "getDays", "(D)J", "(I)V", "(I)J", "(J)V", "(J)J", "hours", "getHours$annotations", "getHours", "microseconds", "getMicroseconds$annotations", "getMicroseconds", "milliseconds", "getMilliseconds$annotations", "getMilliseconds", "minutes", "getMinutes$annotations", "getMinutes", "nanoseconds", "getNanoseconds$annotations", "getNanoseconds", "seconds", "getSeconds$annotations", "getSeconds", "durationOf", "normalValue", "unitDiscriminator", "(JI)J", "durationOfMillis", "normalMillis", "durationOfMillisNormalized", "millis", "durationOfNanos", "normalNanos", "durationOfNanosNormalized", "nanos", "millisToNanos", "nanosToMillis", "times", "duration", "times-kIfJnKk", "(DJ)J", "times-mvk6XK0", "(IJ)J", "toDuration", "unit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "(DLjava/util/concurrent/TimeUnit;)J", "(ILjava/util/concurrent/TimeUnit;)J", "(JLjava/util/concurrent/TimeUnit;)J", "kotlin-stdlib"}, mo11630k = 2, mo11631mv = {1, 5, 1})
/* compiled from: Duration.kt */
public final class DurationKt {
    public static final long MAX_MILLIS = 4611686018427387903L;
    public static final long MAX_NANOS = 4611686018426999999L;
    private static final long MAX_NANOS_IN_MILLIS = 4611686018426L;
    public static final int NANOS_IN_MILLIS = 1000000;

    @Deprecated(message = "Use Duration.days() function instead.", replaceWith = @ReplaceWith(expression = "Duration.days(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getDays$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.days() function instead.", replaceWith = @ReplaceWith(expression = "Duration.days(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getDays$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.days() function instead.", replaceWith = @ReplaceWith(expression = "Duration.days(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getDays$annotations(long j) {
    }

    @Deprecated(message = "Use Duration.hours() function instead.", replaceWith = @ReplaceWith(expression = "Duration.hours(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getHours$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.hours() function instead.", replaceWith = @ReplaceWith(expression = "Duration.hours(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getHours$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.hours() function instead.", replaceWith = @ReplaceWith(expression = "Duration.hours(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getHours$annotations(long j) {
    }

    @Deprecated(message = "Use Duration.microseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.microseconds(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getMicroseconds$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.microseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.microseconds(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getMicroseconds$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.microseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.microseconds(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getMicroseconds$annotations(long j) {
    }

    @Deprecated(message = "Use Duration.milliseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.milliseconds(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getMilliseconds$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.milliseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.milliseconds(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getMilliseconds$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.milliseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.milliseconds(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getMilliseconds$annotations(long j) {
    }

    @Deprecated(message = "Use Duration.minutes() function instead.", replaceWith = @ReplaceWith(expression = "Duration.minutes(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getMinutes$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.minutes() function instead.", replaceWith = @ReplaceWith(expression = "Duration.minutes(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getMinutes$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.minutes() function instead.", replaceWith = @ReplaceWith(expression = "Duration.minutes(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getMinutes$annotations(long j) {
    }

    @Deprecated(message = "Use Duration.nanoseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.nanoseconds(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getNanoseconds$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.nanoseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.nanoseconds(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getNanoseconds$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.nanoseconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.nanoseconds(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getNanoseconds$annotations(long j) {
    }

    @Deprecated(message = "Use Duration.seconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.seconds(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getSeconds$annotations(double d) {
    }

    @Deprecated(message = "Use Duration.seconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.seconds(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getSeconds$annotations(int i) {
    }

    @Deprecated(message = "Use Duration.seconds() function instead.", replaceWith = @ReplaceWith(expression = "Duration.seconds(this)", imports = {"kotlin.time.Duration"}))
    public static /* synthetic */ void getSeconds$annotations(long j) {
    }

    public static final long toDuration(int $this$toDuration, TimeUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (unit.compareTo(TimeUnit.SECONDS) <= 0) {
            return durationOfNanos(DurationUnitKt.convertDurationUnitOverflow((long) $this$toDuration, unit, TimeUnit.NANOSECONDS));
        }
        return toDuration((long) $this$toDuration, unit);
    }

    public static final long toDuration(long $this$toDuration, TimeUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        long maxNsInUnit = DurationUnitKt.convertDurationUnitOverflow(MAX_NANOS, TimeUnit.NANOSECONDS, unit);
        if ((-maxNsInUnit) <= $this$toDuration && maxNsInUnit >= $this$toDuration) {
            return durationOfNanos(DurationUnitKt.convertDurationUnitOverflow($this$toDuration, unit, TimeUnit.NANOSECONDS));
        }
        return durationOfMillis(RangesKt.coerceIn(DurationUnitKt.convertDurationUnit($this$toDuration, unit, TimeUnit.MILLISECONDS), -4611686018427387903L, (long) MAX_MILLIS));
    }

    public static final long toDuration(double $this$toDuration, TimeUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        double valueInNs = DurationUnitKt.convertDurationUnit($this$toDuration, unit, TimeUnit.NANOSECONDS);
        if (!Double.isNaN(valueInNs)) {
            long nanos = (long) valueInNs;
            if (-4611686018426999999L <= nanos && MAX_NANOS >= nanos) {
                return durationOfNanos(nanos);
            }
            return durationOfMillisNormalized((long) DurationUnitKt.convertDurationUnit($this$toDuration, unit, TimeUnit.MILLISECONDS));
        }
        throw new IllegalArgumentException("Duration value cannot be NaN.".toString());
    }

    public static final long getNanoseconds(int $this$nanoseconds) {
        return toDuration($this$nanoseconds, TimeUnit.NANOSECONDS);
    }

    public static final long getNanoseconds(long $this$nanoseconds) {
        return toDuration($this$nanoseconds, TimeUnit.NANOSECONDS);
    }

    public static final long getNanoseconds(double $this$nanoseconds) {
        return toDuration($this$nanoseconds, TimeUnit.NANOSECONDS);
    }

    public static final long getMicroseconds(int $this$microseconds) {
        return toDuration($this$microseconds, TimeUnit.MICROSECONDS);
    }

    public static final long getMicroseconds(long $this$microseconds) {
        return toDuration($this$microseconds, TimeUnit.MICROSECONDS);
    }

    public static final long getMicroseconds(double $this$microseconds) {
        return toDuration($this$microseconds, TimeUnit.MICROSECONDS);
    }

    public static final long getMilliseconds(int $this$milliseconds) {
        return toDuration($this$milliseconds, TimeUnit.MILLISECONDS);
    }

    public static final long getMilliseconds(long $this$milliseconds) {
        return toDuration($this$milliseconds, TimeUnit.MILLISECONDS);
    }

    public static final long getMilliseconds(double $this$milliseconds) {
        return toDuration($this$milliseconds, TimeUnit.MILLISECONDS);
    }

    public static final long getSeconds(int $this$seconds) {
        return toDuration($this$seconds, TimeUnit.SECONDS);
    }

    public static final long getSeconds(long $this$seconds) {
        return toDuration($this$seconds, TimeUnit.SECONDS);
    }

    public static final long getSeconds(double $this$seconds) {
        return toDuration($this$seconds, TimeUnit.SECONDS);
    }

    public static final long getMinutes(int $this$minutes) {
        return toDuration($this$minutes, TimeUnit.MINUTES);
    }

    public static final long getMinutes(long $this$minutes) {
        return toDuration($this$minutes, TimeUnit.MINUTES);
    }

    public static final long getMinutes(double $this$minutes) {
        return toDuration($this$minutes, TimeUnit.MINUTES);
    }

    public static final long getHours(int $this$hours) {
        return toDuration($this$hours, TimeUnit.HOURS);
    }

    public static final long getHours(long $this$hours) {
        return toDuration($this$hours, TimeUnit.HOURS);
    }

    public static final long getHours(double $this$hours) {
        return toDuration($this$hours, TimeUnit.HOURS);
    }

    public static final long getDays(int $this$days) {
        return toDuration($this$days, TimeUnit.DAYS);
    }

    public static final long getDays(long $this$days) {
        return toDuration($this$days, TimeUnit.DAYS);
    }

    public static final long getDays(double $this$days) {
        return toDuration($this$days, TimeUnit.DAYS);
    }

    /* renamed from: times-mvk6XK0  reason: not valid java name */
    private static final long m1563timesmvk6XK0(int $this$times, long duration) {
        return Duration.m1521timesUwyO8pc(duration, $this$times);
    }

    /* renamed from: times-kIfJnKk  reason: not valid java name */
    private static final long m1562timeskIfJnKk(double $this$times, long duration) {
        return Duration.m1520timesUwyO8pc(duration, $this$times);
    }

    /* access modifiers changed from: private */
    public static final long nanosToMillis(long nanos) {
        return nanos / ((long) NANOS_IN_MILLIS);
    }

    /* access modifiers changed from: private */
    public static final long millisToNanos(long millis) {
        return ((long) NANOS_IN_MILLIS) * millis;
    }

    /* access modifiers changed from: private */
    public static final long durationOfNanos(long normalNanos) {
        return Duration.m1482constructorimpl(normalNanos << 1);
    }

    /* access modifiers changed from: private */
    public static final long durationOfMillis(long normalMillis) {
        return Duration.m1482constructorimpl((normalMillis << 1) + 1);
    }

    /* access modifiers changed from: private */
    public static final long durationOf(long normalValue, int unitDiscriminator) {
        return Duration.m1482constructorimpl((normalValue << 1) + ((long) unitDiscriminator));
    }

    /* access modifiers changed from: private */
    public static final long durationOfNanosNormalized(long nanos) {
        if (-4611686018426999999L <= nanos && MAX_NANOS >= nanos) {
            return durationOfNanos(nanos);
        }
        return durationOfMillis(nanosToMillis(nanos));
    }

    /* access modifiers changed from: private */
    public static final long durationOfMillisNormalized(long millis) {
        if (-4611686018426L <= millis && MAX_NANOS_IN_MILLIS >= millis) {
            return durationOfNanos(millisToNanos(millis));
        }
        return durationOfMillis(RangesKt.coerceIn(millis, -4611686018427387903L, (long) MAX_MILLIS));
    }
}
