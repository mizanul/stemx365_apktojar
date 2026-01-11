package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.URandomKt;

@Metadata(mo11628d1 = {"\u0000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\n\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u001e\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a\u001e\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u001e\u0010\u0000\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a\u001e\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0004\u001a\u001e\u0010\u000e\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0007\u001a\u001e\u0010\u000e\u001a\u00020\b*\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\n\u001a\u001e\u0010\u000e\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\r\u001a&\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a&\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018\u001a$\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u001aH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001c\u001a&\u0010\u0014\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001e\u001a$\u0010\u0014\u001a\u00020\b*\u00020\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u001aH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010 \u001a&\u0010\u0014\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007ø\u0001\u0000¢\u0006\u0004\b!\u0010\"\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b'\u0010(\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\b\u0010)\u001a\u0004\u0018\u00010\u0005H\nø\u0001\u0000¢\u0006\u0002\b*\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010,\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u000bH\u0002ø\u0001\u0000¢\u0006\u0004\b-\u0010.\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b0\u00101\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0005H\u0002ø\u0001\u0000¢\u0006\u0004\b2\u00103\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\b\u0010)\u001a\u0004\u0018\u00010\bH\nø\u0001\u0000¢\u0006\u0002\b4\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u000bH\u0002ø\u0001\u0000¢\u0006\u0004\b5\u00106\u001a\u001f\u00107\u001a\u000208*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0004ø\u0001\u0000¢\u0006\u0004\b:\u0010;\u001a\u001f\u00107\u001a\u000208*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0004ø\u0001\u0000¢\u0006\u0004\b<\u0010=\u001a\u001f\u00107\u001a\u00020>*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0004ø\u0001\u0000¢\u0006\u0004\b?\u0010@\u001a\u001f\u00107\u001a\u000208*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0004ø\u0001\u0000¢\u0006\u0004\bA\u0010B\u001a\u0015\u0010C\u001a\u00020\u0005*\u00020%H\bø\u0001\u0000¢\u0006\u0002\u0010D\u001a\u001c\u0010C\u001a\u00020\u0005*\u00020%2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000¢\u0006\u0002\u0010F\u001a\u0015\u0010C\u001a\u00020\b*\u00020/H\bø\u0001\u0000¢\u0006\u0002\u0010G\u001a\u001c\u0010C\u001a\u00020\b*\u00020/2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000¢\u0006\u0002\u0010H\u001a\u0012\u0010I\u001a\u0004\u0018\u00010\u0005*\u00020%H\bø\u0001\u0000\u001a\u0019\u0010I\u001a\u0004\u0018\u00010\u0005*\u00020%2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000\u001a\u0012\u0010I\u001a\u0004\u0018\u00010\b*\u00020/H\bø\u0001\u0000\u001a\u0019\u0010I\u001a\u0004\u0018\u00010\b*\u00020/2\u0006\u0010C\u001a\u00020EH\u0007ø\u0001\u0000\u001a\f\u0010J\u001a\u000208*\u000208H\u0007\u001a\f\u0010J\u001a\u00020>*\u00020>H\u0007\u001a\u0015\u0010K\u001a\u000208*\u0002082\u0006\u0010K\u001a\u00020LH\u0004\u001a\u0015\u0010K\u001a\u00020>*\u00020>2\u0006\u0010K\u001a\u00020MH\u0004\u001a\u001f\u0010N\u001a\u00020%*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0004ø\u0001\u0000¢\u0006\u0004\bO\u0010P\u001a\u001f\u0010N\u001a\u00020%*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0004ø\u0001\u0000¢\u0006\u0004\bQ\u0010R\u001a\u001f\u0010N\u001a\u00020/*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0004ø\u0001\u0000¢\u0006\u0004\bS\u0010T\u001a\u001f\u0010N\u001a\u00020%*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0004ø\u0001\u0000¢\u0006\u0004\bU\u0010V\u0002\u0004\n\u0002\b\u0019¨\u0006W"}, mo11629d2 = {"coerceAtLeast", "Lkotlin/UByte;", "minimumValue", "coerceAtLeast-Kr8caGY", "(BB)B", "Lkotlin/UInt;", "coerceAtLeast-J1ME1BU", "(II)I", "Lkotlin/ULong;", "coerceAtLeast-eb3DHEI", "(JJ)J", "Lkotlin/UShort;", "coerceAtLeast-5PvTz6A", "(SS)S", "coerceAtMost", "maximumValue", "coerceAtMost-Kr8caGY", "coerceAtMost-J1ME1BU", "coerceAtMost-eb3DHEI", "coerceAtMost-5PvTz6A", "coerceIn", "coerceIn-b33U2AM", "(BBB)B", "coerceIn-WZ9TVnA", "(III)I", "range", "Lkotlin/ranges/ClosedRange;", "coerceIn-wuiCnnA", "(ILkotlin/ranges/ClosedRange;)I", "coerceIn-sambcqE", "(JJJ)J", "coerceIn-JPwROB0", "(JLkotlin/ranges/ClosedRange;)J", "coerceIn-VKSA0NQ", "(SSS)S", "contains", "", "Lkotlin/ranges/UIntRange;", "value", "contains-68kG9v0", "(Lkotlin/ranges/UIntRange;B)Z", "element", "contains-biwQdVI", "contains-fz5IDCE", "(Lkotlin/ranges/UIntRange;J)Z", "contains-ZsK3CEQ", "(Lkotlin/ranges/UIntRange;S)Z", "Lkotlin/ranges/ULongRange;", "contains-ULb-yJY", "(Lkotlin/ranges/ULongRange;B)Z", "contains-Gab390E", "(Lkotlin/ranges/ULongRange;I)Z", "contains-GYNo2lE", "contains-uhHAxoY", "(Lkotlin/ranges/ULongRange;S)Z", "downTo", "Lkotlin/ranges/UIntProgression;", "to", "downTo-Kr8caGY", "(BB)Lkotlin/ranges/UIntProgression;", "downTo-J1ME1BU", "(II)Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ULongProgression;", "downTo-eb3DHEI", "(JJ)Lkotlin/ranges/ULongProgression;", "downTo-5PvTz6A", "(SS)Lkotlin/ranges/UIntProgression;", "random", "(Lkotlin/ranges/UIntRange;)I", "Lkotlin/random/Random;", "(Lkotlin/ranges/UIntRange;Lkotlin/random/Random;)I", "(Lkotlin/ranges/ULongRange;)J", "(Lkotlin/ranges/ULongRange;Lkotlin/random/Random;)J", "randomOrNull", "reversed", "step", "", "", "until", "until-Kr8caGY", "(BB)Lkotlin/ranges/UIntRange;", "until-J1ME1BU", "(II)Lkotlin/ranges/UIntRange;", "until-eb3DHEI", "(JJ)Lkotlin/ranges/ULongRange;", "until-5PvTz6A", "(SS)Lkotlin/ranges/UIntRange;", "kotlin-stdlib"}, mo11630k = 5, mo11631mv = {1, 5, 1}, mo11633xi = 1, mo11634xs = "kotlin/ranges/URangesKt")
/* compiled from: _URanges.kt */
class URangesKt___URangesKt {
    private static final int random(UIntRange $this$random) {
        return URangesKt.random($this$random, (Random) Random.Default);
    }

    private static final long random(ULongRange $this$random) {
        return URangesKt.random($this$random, (Random) Random.Default);
    }

    public static final int random(UIntRange $this$random, Random random) {
        Intrinsics.checkNotNullParameter($this$random, "$this$random");
        Intrinsics.checkNotNullParameter(random, "random");
        try {
            return URandomKt.nextUInt(random, $this$random);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public static final long random(ULongRange $this$random, Random random) {
        Intrinsics.checkNotNullParameter($this$random, "$this$random");
        Intrinsics.checkNotNullParameter(random, "random");
        try {
            return URandomKt.nextULong(random, $this$random);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    private static final UInt randomOrNull(UIntRange $this$randomOrNull) {
        return URangesKt.randomOrNull($this$randomOrNull, (Random) Random.Default);
    }

    private static final ULong randomOrNull(ULongRange $this$randomOrNull) {
        return URangesKt.randomOrNull($this$randomOrNull, (Random) Random.Default);
    }

    public static final UInt randomOrNull(UIntRange $this$randomOrNull, Random random) {
        Intrinsics.checkNotNullParameter($this$randomOrNull, "$this$randomOrNull");
        Intrinsics.checkNotNullParameter(random, "random");
        if ($this$randomOrNull.isEmpty()) {
            return null;
        }
        return UInt.m276boximpl(URandomKt.nextUInt(random, $this$randomOrNull));
    }

    public static final ULong randomOrNull(ULongRange $this$randomOrNull, Random random) {
        Intrinsics.checkNotNullParameter($this$randomOrNull, "$this$randomOrNull");
        Intrinsics.checkNotNullParameter(random, "random");
        if ($this$randomOrNull.isEmpty()) {
            return null;
        }
        return ULong.m354boximpl(URandomKt.nextULong(random, $this$randomOrNull));
    }

    /* renamed from: contains-biwQdVI  reason: not valid java name */
    private static final boolean m1430containsbiwQdVI(UIntRange $this$contains, UInt element) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return element != null && $this$contains.m1401containsWZ4Q5Ns(element.m333unboximpl());
    }

    /* renamed from: contains-GYNo2lE  reason: not valid java name */
    private static final boolean m1426containsGYNo2lE(ULongRange $this$contains, ULong element) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return element != null && $this$contains.m1408containsVKZWuLQ(element.m411unboximpl());
    }

    /* renamed from: contains-68kG9v0  reason: not valid java name */
    public static final boolean m1425contains68kG9v0(UIntRange $this$contains, byte value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.m1401containsWZ4Q5Ns(UInt.m282constructorimpl(value & 255));
    }

    /* renamed from: contains-ULb-yJY  reason: not valid java name */
    public static final boolean m1428containsULbyJY(ULongRange $this$contains, byte value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.m1408containsVKZWuLQ(ULong.m360constructorimpl(((long) value) & 255));
    }

    /* renamed from: contains-Gab390E  reason: not valid java name */
    public static final boolean m1427containsGab390E(ULongRange $this$contains, int value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.m1408containsVKZWuLQ(ULong.m360constructorimpl(((long) value) & 4294967295L));
    }

    /* renamed from: contains-fz5IDCE  reason: not valid java name */
    public static final boolean m1431containsfz5IDCE(UIntRange $this$contains, long value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return ULong.m360constructorimpl(value >>> 32) == 0 && $this$contains.m1401containsWZ4Q5Ns(UInt.m282constructorimpl((int) value));
    }

    /* renamed from: contains-ZsK3CEQ  reason: not valid java name */
    public static final boolean m1429containsZsK3CEQ(UIntRange $this$contains, short value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.m1401containsWZ4Q5Ns(UInt.m282constructorimpl(65535 & value));
    }

    /* renamed from: contains-uhHAxoY  reason: not valid java name */
    public static final boolean m1432containsuhHAxoY(ULongRange $this$contains, short value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.m1408containsVKZWuLQ(ULong.m360constructorimpl(((long) value) & 65535));
    }

    /* renamed from: downTo-Kr8caGY  reason: not valid java name */
    public static final UIntProgression m1435downToKr8caGY(byte $this$downTo, byte to) {
        return UIntProgression.Companion.m1399fromClosedRangeNkh28Cs(UInt.m282constructorimpl($this$downTo & 255), UInt.m282constructorimpl(to & 255), -1);
    }

    /* renamed from: downTo-J1ME1BU  reason: not valid java name */
    public static final UIntProgression m1434downToJ1ME1BU(int $this$downTo, int to) {
        return UIntProgression.Companion.m1399fromClosedRangeNkh28Cs($this$downTo, to, -1);
    }

    /* renamed from: downTo-eb3DHEI  reason: not valid java name */
    public static final ULongProgression m1436downToeb3DHEI(long $this$downTo, long to) {
        return ULongProgression.Companion.m1406fromClosedRange7ftBX0g($this$downTo, to, -1);
    }

    /* renamed from: downTo-5PvTz6A  reason: not valid java name */
    public static final UIntProgression m1433downTo5PvTz6A(short $this$downTo, short to) {
        return UIntProgression.Companion.m1399fromClosedRangeNkh28Cs(UInt.m282constructorimpl($this$downTo & UShort.MAX_VALUE), UInt.m282constructorimpl(65535 & to), -1);
    }

    public static final UIntProgression reversed(UIntProgression $this$reversed) {
        Intrinsics.checkNotNullParameter($this$reversed, "$this$reversed");
        return UIntProgression.Companion.m1399fromClosedRangeNkh28Cs($this$reversed.m1398getLastpVg5ArA(), $this$reversed.m1397getFirstpVg5ArA(), -$this$reversed.getStep());
    }

    public static final ULongProgression reversed(ULongProgression $this$reversed) {
        Intrinsics.checkNotNullParameter($this$reversed, "$this$reversed");
        return ULongProgression.Companion.m1406fromClosedRange7ftBX0g($this$reversed.m1405getLastsVKNKU(), $this$reversed.m1404getFirstsVKNKU(), -$this$reversed.getStep());
    }

    public static final UIntProgression step(UIntProgression $this$step, int step) {
        Intrinsics.checkNotNullParameter($this$step, "$this$step");
        RangesKt.checkStepIsPositive(step > 0, Integer.valueOf(step));
        return UIntProgression.Companion.m1399fromClosedRangeNkh28Cs($this$step.m1397getFirstpVg5ArA(), $this$step.m1398getLastpVg5ArA(), $this$step.getStep() > 0 ? step : -step);
    }

    public static final ULongProgression step(ULongProgression $this$step, long step) {
        Intrinsics.checkNotNullParameter($this$step, "$this$step");
        RangesKt.checkStepIsPositive(step > 0, Long.valueOf(step));
        return ULongProgression.Companion.m1406fromClosedRange7ftBX0g($this$step.m1404getFirstsVKNKU(), $this$step.m1405getLastsVKNKU(), $this$step.getStep() > 0 ? step : -step);
    }

    /* renamed from: until-Kr8caGY  reason: not valid java name */
    public static final UIntRange m1439untilKr8caGY(byte $this$until, byte to) {
        if (Intrinsics.compare((int) to & 255, 0) <= 0) {
            return UIntRange.Companion.getEMPTY();
        }
        return new UIntRange(UInt.m282constructorimpl($this$until & 255), UInt.m282constructorimpl(UInt.m282constructorimpl(to & 255) - 1), (DefaultConstructorMarker) null);
    }

    /* renamed from: until-J1ME1BU  reason: not valid java name */
    public static final UIntRange m1438untilJ1ME1BU(int $this$until, int to) {
        if (UnsignedKt.uintCompare(to, 0) <= 0) {
            return UIntRange.Companion.getEMPTY();
        }
        return new UIntRange($this$until, UInt.m282constructorimpl(to - 1), (DefaultConstructorMarker) null);
    }

    /* renamed from: until-eb3DHEI  reason: not valid java name */
    public static final ULongRange m1440untileb3DHEI(long $this$until, long to) {
        if (UnsignedKt.ulongCompare(to, 0) <= 0) {
            return ULongRange.Companion.getEMPTY();
        }
        return new ULongRange($this$until, ULong.m360constructorimpl(to - ULong.m360constructorimpl(((long) 1) & 4294967295L)), (DefaultConstructorMarker) null);
    }

    /* renamed from: until-5PvTz6A  reason: not valid java name */
    public static final UIntRange m1437until5PvTz6A(short $this$until, short to) {
        if (Intrinsics.compare((int) to & UShort.MAX_VALUE, 0) <= 0) {
            return UIntRange.Companion.getEMPTY();
        }
        return new UIntRange(UInt.m282constructorimpl($this$until & UShort.MAX_VALUE), UInt.m282constructorimpl(UInt.m282constructorimpl(65535 & to) - 1), (DefaultConstructorMarker) null);
    }

    /* renamed from: coerceAtLeast-J1ME1BU  reason: not valid java name */
    public static final int m1412coerceAtLeastJ1ME1BU(int $this$coerceAtLeast, int minimumValue) {
        return UnsignedKt.uintCompare($this$coerceAtLeast, minimumValue) < 0 ? minimumValue : $this$coerceAtLeast;
    }

    /* renamed from: coerceAtLeast-eb3DHEI  reason: not valid java name */
    public static final long m1414coerceAtLeasteb3DHEI(long $this$coerceAtLeast, long minimumValue) {
        return UnsignedKt.ulongCompare($this$coerceAtLeast, minimumValue) < 0 ? minimumValue : $this$coerceAtLeast;
    }

    /* renamed from: coerceAtLeast-Kr8caGY  reason: not valid java name */
    public static final byte m1413coerceAtLeastKr8caGY(byte $this$coerceAtLeast, byte minimumValue) {
        return Intrinsics.compare((int) $this$coerceAtLeast & 255, (int) minimumValue & 255) < 0 ? minimumValue : $this$coerceAtLeast;
    }

    /* renamed from: coerceAtLeast-5PvTz6A  reason: not valid java name */
    public static final short m1411coerceAtLeast5PvTz6A(short $this$coerceAtLeast, short minimumValue) {
        return Intrinsics.compare((int) $this$coerceAtLeast & UShort.MAX_VALUE, (int) 65535 & minimumValue) < 0 ? minimumValue : $this$coerceAtLeast;
    }

    /* renamed from: coerceAtMost-J1ME1BU  reason: not valid java name */
    public static final int m1416coerceAtMostJ1ME1BU(int $this$coerceAtMost, int maximumValue) {
        return UnsignedKt.uintCompare($this$coerceAtMost, maximumValue) > 0 ? maximumValue : $this$coerceAtMost;
    }

    /* renamed from: coerceAtMost-eb3DHEI  reason: not valid java name */
    public static final long m1418coerceAtMosteb3DHEI(long $this$coerceAtMost, long maximumValue) {
        return UnsignedKt.ulongCompare($this$coerceAtMost, maximumValue) > 0 ? maximumValue : $this$coerceAtMost;
    }

    /* renamed from: coerceAtMost-Kr8caGY  reason: not valid java name */
    public static final byte m1417coerceAtMostKr8caGY(byte $this$coerceAtMost, byte maximumValue) {
        return Intrinsics.compare((int) $this$coerceAtMost & 255, (int) maximumValue & 255) > 0 ? maximumValue : $this$coerceAtMost;
    }

    /* renamed from: coerceAtMost-5PvTz6A  reason: not valid java name */
    public static final short m1415coerceAtMost5PvTz6A(short $this$coerceAtMost, short maximumValue) {
        return Intrinsics.compare((int) $this$coerceAtMost & UShort.MAX_VALUE, (int) 65535 & maximumValue) > 0 ? maximumValue : $this$coerceAtMost;
    }

    /* renamed from: coerceIn-WZ9TVnA  reason: not valid java name */
    public static final int m1421coerceInWZ9TVnA(int $this$coerceIn, int minimumValue, int maximumValue) {
        if (UnsignedKt.uintCompare(minimumValue, maximumValue) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UInt.m327toStringimpl(maximumValue) + " is less than minimum " + UInt.m327toStringimpl(minimumValue) + '.');
        } else if (UnsignedKt.uintCompare($this$coerceIn, minimumValue) < 0) {
            return minimumValue;
        } else {
            if (UnsignedKt.uintCompare($this$coerceIn, maximumValue) > 0) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }

    /* renamed from: coerceIn-sambcqE  reason: not valid java name */
    public static final long m1423coerceInsambcqE(long $this$coerceIn, long minimumValue, long maximumValue) {
        if (UnsignedKt.ulongCompare(minimumValue, maximumValue) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ULong.m405toStringimpl(maximumValue) + " is less than minimum " + ULong.m405toStringimpl(minimumValue) + '.');
        } else if (UnsignedKt.ulongCompare($this$coerceIn, minimumValue) < 0) {
            return minimumValue;
        } else {
            if (UnsignedKt.ulongCompare($this$coerceIn, maximumValue) > 0) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }

    /* renamed from: coerceIn-b33U2AM  reason: not valid java name */
    public static final byte m1422coerceInb33U2AM(byte $this$coerceIn, byte minimumValue, byte maximumValue) {
        if (Intrinsics.compare((int) minimumValue & 255, (int) maximumValue & 255) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UByte.m249toStringimpl(maximumValue) + " is less than minimum " + UByte.m249toStringimpl(minimumValue) + '.');
        } else if (Intrinsics.compare((int) $this$coerceIn & 255, (int) minimumValue & 255) < 0) {
            return minimumValue;
        } else {
            if (Intrinsics.compare((int) $this$coerceIn & 255, (int) maximumValue & 255) > 0) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }

    /* renamed from: coerceIn-VKSA0NQ  reason: not valid java name */
    public static final short m1420coerceInVKSA0NQ(short $this$coerceIn, short minimumValue, short maximumValue) {
        if (Intrinsics.compare((int) minimumValue & UShort.MAX_VALUE, (int) maximumValue & UShort.MAX_VALUE) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UShort.m509toStringimpl(maximumValue) + " is less than minimum " + UShort.m509toStringimpl(minimumValue) + '.');
        } else if (Intrinsics.compare((int) $this$coerceIn & UShort.MAX_VALUE, (int) minimumValue & UShort.MAX_VALUE) < 0) {
            return minimumValue;
        } else {
            if (Intrinsics.compare((int) $this$coerceIn & UShort.MAX_VALUE, (int) 65535 & maximumValue) > 0) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }

    /* renamed from: coerceIn-wuiCnnA  reason: not valid java name */
    public static final int m1424coerceInwuiCnnA(int $this$coerceIn, ClosedRange<UInt> range) {
        Intrinsics.checkNotNullParameter(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((UInt) RangesKt.coerceIn(UInt.m276boximpl($this$coerceIn), (ClosedFloatingPointRange) range)).m333unboximpl();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        } else if (UnsignedKt.uintCompare($this$coerceIn, range.getStart().m333unboximpl()) < 0) {
            return range.getStart().m333unboximpl();
        } else {
            if (UnsignedKt.uintCompare($this$coerceIn, range.getEndInclusive().m333unboximpl()) > 0) {
                return range.getEndInclusive().m333unboximpl();
            }
            return $this$coerceIn;
        }
    }

    /* renamed from: coerceIn-JPwROB0  reason: not valid java name */
    public static final long m1419coerceInJPwROB0(long $this$coerceIn, ClosedRange<ULong> range) {
        Intrinsics.checkNotNullParameter(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((ULong) RangesKt.coerceIn(ULong.m354boximpl($this$coerceIn), (ClosedFloatingPointRange) range)).m411unboximpl();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        } else if (UnsignedKt.ulongCompare($this$coerceIn, range.getStart().m411unboximpl()) < 0) {
            return range.getStart().m411unboximpl();
        } else {
            if (UnsignedKt.ulongCompare($this$coerceIn, range.getEndInclusive().m411unboximpl()) > 0) {
                return range.getEndInclusive().m411unboximpl();
            }
            return $this$coerceIn;
        }
    }
}
