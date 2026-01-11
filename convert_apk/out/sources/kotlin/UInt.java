package kotlin;

import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.UIntRange;

@JvmInline
@Metadata(mo11628d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b@\u0018\u0000 y2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001yB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\rH\nø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u000bJ\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0014H\nø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\u0000H\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0018\u0010\u0005J\u001b\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\nø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u000fJ\u001b\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u000bJ\u001b\u0010\u0019\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u0016J\u001a\u0010\u001f\u001a\u00020 2\b\u0010\t\u001a\u0004\u0018\u00010!HÖ\u0003¢\u0006\u0004\b\"\u0010#J\u001b\u0010$\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\bø\u0001\u0000¢\u0006\u0004\b%\u0010\u000fJ\u001b\u0010$\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\bø\u0001\u0000¢\u0006\u0004\b&\u0010\u000bJ\u001b\u0010$\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\bø\u0001\u0000¢\u0006\u0004\b'\u0010\u001dJ\u001b\u0010$\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\bø\u0001\u0000¢\u0006\u0004\b(\u0010\u0016J\u0010\u0010)\u001a\u00020\u0003HÖ\u0001¢\u0006\u0004\b*\u0010\u0005J\u0016\u0010+\u001a\u00020\u0000H\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b,\u0010\u0005J\u0016\u0010-\u001a\u00020\u0000H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b.\u0010\u0005J\u001b\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\nø\u0001\u0000¢\u0006\u0004\b0\u0010\u000fJ\u001b\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b1\u0010\u000bJ\u001b\u0010/\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b2\u0010\u001dJ\u001b\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\nø\u0001\u0000¢\u0006\u0004\b3\u0010\u0016J\u001b\u00104\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\rH\bø\u0001\u0000¢\u0006\u0004\b5\u00106J\u001b\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\bø\u0001\u0000¢\u0006\u0004\b7\u0010\u000bJ\u001b\u00104\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\bø\u0001\u0000¢\u0006\u0004\b8\u0010\u001dJ\u001b\u00104\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\bø\u0001\u0000¢\u0006\u0004\b9\u0010:J\u001b\u0010;\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\b<\u0010\u000bJ\u001b\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\nø\u0001\u0000¢\u0006\u0004\b>\u0010\u000fJ\u001b\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b?\u0010\u000bJ\u001b\u0010=\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b@\u0010\u001dJ\u001b\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\nø\u0001\u0000¢\u0006\u0004\bA\u0010\u0016J\u001b\u0010B\u001a\u00020C2\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bD\u0010EJ\u001b\u0010F\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\nø\u0001\u0000¢\u0006\u0004\bG\u0010\u000fJ\u001b\u0010F\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bH\u0010\u000bJ\u001b\u0010F\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\bI\u0010\u001dJ\u001b\u0010F\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\nø\u0001\u0000¢\u0006\u0004\bJ\u0010\u0016J\u001e\u0010K\u001a\u00020\u00002\u0006\u0010L\u001a\u00020\u0003H\fø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bM\u0010\u000bJ\u001e\u0010N\u001a\u00020\u00002\u0006\u0010L\u001a\u00020\u0003H\fø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bO\u0010\u000bJ\u001b\u0010P\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\nø\u0001\u0000¢\u0006\u0004\bQ\u0010\u000fJ\u001b\u0010P\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bR\u0010\u000bJ\u001b\u0010P\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\bS\u0010\u001dJ\u001b\u0010P\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\nø\u0001\u0000¢\u0006\u0004\bT\u0010\u0016J\u0010\u0010U\u001a\u00020VH\b¢\u0006\u0004\bW\u0010XJ\u0010\u0010Y\u001a\u00020ZH\b¢\u0006\u0004\b[\u0010\\J\u0010\u0010]\u001a\u00020^H\b¢\u0006\u0004\b_\u0010`J\u0010\u0010a\u001a\u00020\u0003H\b¢\u0006\u0004\bb\u0010\u0005J\u0010\u0010c\u001a\u00020dH\b¢\u0006\u0004\be\u0010fJ\u0010\u0010g\u001a\u00020hH\b¢\u0006\u0004\bi\u0010jJ\u000f\u0010k\u001a\u00020lH\u0016¢\u0006\u0004\bm\u0010nJ\u0016\u0010o\u001a\u00020\rH\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bp\u0010XJ\u0016\u0010q\u001a\u00020\u0000H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\br\u0010\u0005J\u0016\u0010s\u001a\u00020\u0011H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bt\u0010fJ\u0016\u0010u\u001a\u00020\u0014H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bv\u0010jJ\u001b\u0010w\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\bx\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0001\u0002\u0001\u00020\u0003ø\u0001\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006z"}, mo11629d2 = {"Lkotlin/UInt;", "", "data", "", "constructor-impl", "(I)I", "getData$annotations", "()V", "and", "other", "and-WZ4Q5Ns", "(II)I", "compareTo", "Lkotlin/UByte;", "compareTo-7apg3OU", "(IB)I", "compareTo-WZ4Q5Ns", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(IJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(IS)I", "dec", "dec-pVg5ArA", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(IJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(ILjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "inc", "inc-pVg5ArA", "inv", "inv-pVg5ArA", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(IB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(IS)S", "or", "or-WZ4Q5Ns", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-WZ4Q5Ns", "(II)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-pVg5ArA", "shr", "shr-pVg5ArA", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(I)B", "toDouble", "", "toDouble-impl", "(I)D", "toFloat", "", "toFloat-impl", "(I)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(I)J", "toShort", "", "toShort-impl", "(I)S", "toString", "", "toString-impl", "(I)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-WZ4Q5Ns", "Companion", "kotlin-stdlib"}, mo11630k = 1, mo11631mv = {1, 5, 1})
/* compiled from: UInt.kt */
public final class UInt implements Comparable<UInt> {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final int MAX_VALUE = -1;
    public static final int MIN_VALUE = 0;
    public static final int SIZE_BITS = 32;
    public static final int SIZE_BYTES = 4;
    private final int data;

    /* renamed from: box-impl  reason: not valid java name */
    public static final /* synthetic */ UInt m276boximpl(int i) {
        return new UInt(i);
    }

    /* renamed from: compareTo-WZ4Q5Ns  reason: not valid java name */
    private int m279compareToWZ4Q5Ns(int i) {
        return m280compareToWZ4Q5Ns(this.data, i);
    }

    /* renamed from: equals-impl  reason: not valid java name */
    public static boolean m288equalsimpl(int i, Object obj) {
        return (obj instanceof UInt) && i == ((UInt) obj).m333unboximpl();
    }

    /* renamed from: equals-impl0  reason: not valid java name */
    public static final boolean m289equalsimpl0(int i, int i2) {
        return i == i2;
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl  reason: not valid java name */
    public static int m294hashCodeimpl(int i) {
        return i;
    }

    public boolean equals(Object obj) {
        return m288equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m294hashCodeimpl(this.data);
    }

    public String toString() {
        return m327toStringimpl(this.data);
    }

    /* renamed from: unbox-impl  reason: not valid java name */
    public final /* synthetic */ int m333unboximpl() {
        return this.data;
    }

    private /* synthetic */ UInt(int data2) {
        this.data = data2;
    }

    /* renamed from: constructor-impl  reason: not valid java name */
    public static int m282constructorimpl(int data2) {
        return data2;
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return m279compareToWZ4Q5Ns(((UInt) obj).m333unboximpl());
    }

    @Metadata(mo11628d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u0004XTø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u00020\u0004XTø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bXT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bXT¢\u0006\u0002\n\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\n"}, mo11629d2 = {"Lkotlin/UInt$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UInt;", "I", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, mo11630k = 1, mo11631mv = {1, 5, 1})
    /* compiled from: UInt.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    /* renamed from: compareTo-7apg3OU  reason: not valid java name */
    private static final int m277compareTo7apg3OU(int $this, byte other) {
        return UnsignedKt.uintCompare($this, m282constructorimpl(other & 255));
    }

    /* renamed from: compareTo-xj2QHRw  reason: not valid java name */
    private static final int m281compareToxj2QHRw(int $this, short other) {
        return UnsignedKt.uintCompare($this, m282constructorimpl(65535 & other));
    }

    /* renamed from: compareTo-WZ4Q5Ns  reason: not valid java name */
    private static int m280compareToWZ4Q5Ns(int $this, int other) {
        return UnsignedKt.uintCompare($this, other);
    }

    /* renamed from: compareTo-VKZWuLQ  reason: not valid java name */
    private static final int m278compareToVKZWuLQ(int $this, long other) {
        return UnsignedKt.ulongCompare(ULong.m360constructorimpl(((long) $this) & 4294967295L), other);
    }

    /* renamed from: plus-7apg3OU  reason: not valid java name */
    private static final int m306plus7apg3OU(int $this, byte other) {
        return m282constructorimpl(m282constructorimpl(other & 255) + $this);
    }

    /* renamed from: plus-xj2QHRw  reason: not valid java name */
    private static final int m309plusxj2QHRw(int $this, short other) {
        return m282constructorimpl(m282constructorimpl(65535 & other) + $this);
    }

    /* renamed from: plus-WZ4Q5Ns  reason: not valid java name */
    private static final int m308plusWZ4Q5Ns(int $this, int other) {
        return m282constructorimpl($this + other);
    }

    /* renamed from: plus-VKZWuLQ  reason: not valid java name */
    private static final long m307plusVKZWuLQ(int $this, long other) {
        return ULong.m360constructorimpl(ULong.m360constructorimpl(((long) $this) & 4294967295L) + other);
    }

    /* renamed from: minus-7apg3OU  reason: not valid java name */
    private static final int m297minus7apg3OU(int $this, byte other) {
        return m282constructorimpl($this - m282constructorimpl(other & 255));
    }

    /* renamed from: minus-xj2QHRw  reason: not valid java name */
    private static final int m300minusxj2QHRw(int $this, short other) {
        return m282constructorimpl($this - m282constructorimpl(65535 & other));
    }

    /* renamed from: minus-WZ4Q5Ns  reason: not valid java name */
    private static final int m299minusWZ4Q5Ns(int $this, int other) {
        return m282constructorimpl($this - other);
    }

    /* renamed from: minus-VKZWuLQ  reason: not valid java name */
    private static final long m298minusVKZWuLQ(int $this, long other) {
        return ULong.m360constructorimpl(ULong.m360constructorimpl(((long) $this) & 4294967295L) - other);
    }

    /* renamed from: times-7apg3OU  reason: not valid java name */
    private static final int m317times7apg3OU(int $this, byte other) {
        return m282constructorimpl(m282constructorimpl(other & 255) * $this);
    }

    /* renamed from: times-xj2QHRw  reason: not valid java name */
    private static final int m320timesxj2QHRw(int $this, short other) {
        return m282constructorimpl(m282constructorimpl(65535 & other) * $this);
    }

    /* renamed from: times-WZ4Q5Ns  reason: not valid java name */
    private static final int m319timesWZ4Q5Ns(int $this, int other) {
        return m282constructorimpl($this * other);
    }

    /* renamed from: times-VKZWuLQ  reason: not valid java name */
    private static final long m318timesVKZWuLQ(int $this, long other) {
        return ULong.m360constructorimpl(ULong.m360constructorimpl(((long) $this) & 4294967295L) * other);
    }

    /* renamed from: div-7apg3OU  reason: not valid java name */
    private static final int m284div7apg3OU(int $this, byte other) {
        return UnsignedKt.m535uintDivideJ1ME1BU($this, m282constructorimpl(other & 255));
    }

    /* renamed from: div-xj2QHRw  reason: not valid java name */
    private static final int m287divxj2QHRw(int $this, short other) {
        return UnsignedKt.m535uintDivideJ1ME1BU($this, m282constructorimpl(65535 & other));
    }

    /* renamed from: div-WZ4Q5Ns  reason: not valid java name */
    private static final int m286divWZ4Q5Ns(int $this, int other) {
        return UnsignedKt.m535uintDivideJ1ME1BU($this, other);
    }

    /* renamed from: div-VKZWuLQ  reason: not valid java name */
    private static final long m285divVKZWuLQ(int $this, long other) {
        return UnsignedKt.m537ulongDivideeb3DHEI(ULong.m360constructorimpl(((long) $this) & 4294967295L), other);
    }

    /* renamed from: rem-7apg3OU  reason: not valid java name */
    private static final int m311rem7apg3OU(int $this, byte other) {
        return UnsignedKt.m536uintRemainderJ1ME1BU($this, m282constructorimpl(other & 255));
    }

    /* renamed from: rem-xj2QHRw  reason: not valid java name */
    private static final int m314remxj2QHRw(int $this, short other) {
        return UnsignedKt.m536uintRemainderJ1ME1BU($this, m282constructorimpl(65535 & other));
    }

    /* renamed from: rem-WZ4Q5Ns  reason: not valid java name */
    private static final int m313remWZ4Q5Ns(int $this, int other) {
        return UnsignedKt.m536uintRemainderJ1ME1BU($this, other);
    }

    /* renamed from: rem-VKZWuLQ  reason: not valid java name */
    private static final long m312remVKZWuLQ(int $this, long other) {
        return UnsignedKt.m538ulongRemaindereb3DHEI(ULong.m360constructorimpl(((long) $this) & 4294967295L), other);
    }

    /* renamed from: floorDiv-7apg3OU  reason: not valid java name */
    private static final int m290floorDiv7apg3OU(int $this, byte other) {
        return UnsignedKt.m535uintDivideJ1ME1BU($this, m282constructorimpl(other & 255));
    }

    /* renamed from: floorDiv-xj2QHRw  reason: not valid java name */
    private static final int m293floorDivxj2QHRw(int $this, short other) {
        return UnsignedKt.m535uintDivideJ1ME1BU($this, m282constructorimpl(65535 & other));
    }

    /* renamed from: floorDiv-WZ4Q5Ns  reason: not valid java name */
    private static final int m292floorDivWZ4Q5Ns(int $this, int other) {
        return UnsignedKt.m535uintDivideJ1ME1BU($this, other);
    }

    /* renamed from: floorDiv-VKZWuLQ  reason: not valid java name */
    private static final long m291floorDivVKZWuLQ(int $this, long other) {
        return UnsignedKt.m537ulongDivideeb3DHEI(ULong.m360constructorimpl(((long) $this) & 4294967295L), other);
    }

    /* renamed from: mod-7apg3OU  reason: not valid java name */
    private static final byte m301mod7apg3OU(int $this, byte other) {
        return UByte.m206constructorimpl((byte) UnsignedKt.m536uintRemainderJ1ME1BU($this, m282constructorimpl(other & 255)));
    }

    /* renamed from: mod-xj2QHRw  reason: not valid java name */
    private static final short m304modxj2QHRw(int $this, short other) {
        return UShort.m466constructorimpl((short) UnsignedKt.m536uintRemainderJ1ME1BU($this, m282constructorimpl(65535 & other)));
    }

    /* renamed from: mod-WZ4Q5Ns  reason: not valid java name */
    private static final int m303modWZ4Q5Ns(int $this, int other) {
        return UnsignedKt.m536uintRemainderJ1ME1BU($this, other);
    }

    /* renamed from: mod-VKZWuLQ  reason: not valid java name */
    private static final long m302modVKZWuLQ(int $this, long other) {
        return UnsignedKt.m538ulongRemaindereb3DHEI(ULong.m360constructorimpl(((long) $this) & 4294967295L), other);
    }

    /* renamed from: inc-pVg5ArA  reason: not valid java name */
    private static final int m295incpVg5ArA(int $this) {
        return m282constructorimpl($this + 1);
    }

    /* renamed from: dec-pVg5ArA  reason: not valid java name */
    private static final int m283decpVg5ArA(int $this) {
        return m282constructorimpl($this - 1);
    }

    /* renamed from: rangeTo-WZ4Q5Ns  reason: not valid java name */
    private static final UIntRange m310rangeToWZ4Q5Ns(int $this, int other) {
        return new UIntRange($this, other, (DefaultConstructorMarker) null);
    }

    /* renamed from: shl-pVg5ArA  reason: not valid java name */
    private static final int m315shlpVg5ArA(int $this, int bitCount) {
        return m282constructorimpl($this << bitCount);
    }

    /* renamed from: shr-pVg5ArA  reason: not valid java name */
    private static final int m316shrpVg5ArA(int $this, int bitCount) {
        return m282constructorimpl($this >>> bitCount);
    }

    /* renamed from: and-WZ4Q5Ns  reason: not valid java name */
    private static final int m275andWZ4Q5Ns(int $this, int other) {
        return m282constructorimpl($this & other);
    }

    /* renamed from: or-WZ4Q5Ns  reason: not valid java name */
    private static final int m305orWZ4Q5Ns(int $this, int other) {
        return m282constructorimpl($this | other);
    }

    /* renamed from: xor-WZ4Q5Ns  reason: not valid java name */
    private static final int m332xorWZ4Q5Ns(int $this, int other) {
        return m282constructorimpl($this ^ other);
    }

    /* renamed from: inv-pVg5ArA  reason: not valid java name */
    private static final int m296invpVg5ArA(int $this) {
        return m282constructorimpl(~$this);
    }

    /* renamed from: toByte-impl  reason: not valid java name */
    private static final byte m321toByteimpl(int $this) {
        return (byte) $this;
    }

    /* renamed from: toShort-impl  reason: not valid java name */
    private static final short m326toShortimpl(int $this) {
        return (short) $this;
    }

    /* renamed from: toInt-impl  reason: not valid java name */
    private static final int m324toIntimpl(int $this) {
        return $this;
    }

    /* renamed from: toLong-impl  reason: not valid java name */
    private static final long m325toLongimpl(int $this) {
        return ((long) $this) & 4294967295L;
    }

    /* renamed from: toUByte-w2LRezQ  reason: not valid java name */
    private static final byte m328toUBytew2LRezQ(int $this) {
        return UByte.m206constructorimpl((byte) $this);
    }

    /* renamed from: toUShort-Mh2AYeg  reason: not valid java name */
    private static final short m331toUShortMh2AYeg(int $this) {
        return UShort.m466constructorimpl((short) $this);
    }

    /* renamed from: toUInt-pVg5ArA  reason: not valid java name */
    private static final int m329toUIntpVg5ArA(int $this) {
        return $this;
    }

    /* renamed from: toULong-s-VKNKU  reason: not valid java name */
    private static final long m330toULongsVKNKU(int $this) {
        return ULong.m360constructorimpl(((long) $this) & 4294967295L);
    }

    /* renamed from: toFloat-impl  reason: not valid java name */
    private static final float m323toFloatimpl(int $this) {
        return (float) UnsignedKt.uintToDouble($this);
    }

    /* renamed from: toDouble-impl  reason: not valid java name */
    private static final double m322toDoubleimpl(int $this) {
        return UnsignedKt.uintToDouble($this);
    }

    /* renamed from: toString-impl  reason: not valid java name */
    public static String m327toStringimpl(int $this) {
        return String.valueOf(((long) $this) & 4294967295L);
    }
}
