package kotlin;

import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.ULongRange;

@JvmInline
@Metadata(mo11628d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\"\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b@\u0018\u0000 |2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001|B\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u0000H\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b \u0010\u000bJ\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\b!\u0010\"J\u001a\u0010#\u001a\u00020$2\b\u0010\t\u001a\u0004\u0018\u00010%HÖ\u0003¢\u0006\u0004\b&\u0010'J\u001b\u0010(\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\bø\u0001\u0000¢\u0006\u0004\b)\u0010\u001dJ\u001b\u0010(\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\bø\u0001\u0000¢\u0006\u0004\b*\u0010\u001fJ\u001b\u0010(\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\bø\u0001\u0000¢\u0006\u0004\b+\u0010\u000bJ\u001b\u0010(\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\bø\u0001\u0000¢\u0006\u0004\b,\u0010\"J\u0010\u0010-\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b.\u0010/J\u0016\u00100\u001a\u00020\u0000H\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b1\u0010\u0005J\u0016\u00102\u001a\u00020\u0000H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b3\u0010\u0005J\u001b\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\b5\u0010\u001dJ\u001b\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b6\u0010\u001fJ\u001b\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b7\u0010\u000bJ\u001b\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\b8\u0010\"J\u001b\u00109\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000eH\bø\u0001\u0000¢\u0006\u0004\b:\u0010;J\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\bø\u0001\u0000¢\u0006\u0004\b<\u0010\u0013J\u001b\u00109\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\bø\u0001\u0000¢\u0006\u0004\b=\u0010\u000bJ\u001b\u00109\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\u0016H\bø\u0001\u0000¢\u0006\u0004\b>\u0010?J\u001b\u0010@\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\bA\u0010\u000bJ\u001b\u0010B\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\bC\u0010\u001dJ\u001b\u0010B\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\bD\u0010\u001fJ\u001b\u0010B\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bE\u0010\u000bJ\u001b\u0010B\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\bF\u0010\"J\u001b\u0010G\u001a\u00020H2\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bI\u0010JJ\u001b\u0010K\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\bL\u0010\u001dJ\u001b\u0010K\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\bM\u0010\u001fJ\u001b\u0010K\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bN\u0010\u000bJ\u001b\u0010K\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\bO\u0010\"J\u001e\u0010P\u001a\u00020\u00002\u0006\u0010Q\u001a\u00020\rH\fø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bR\u0010\u001fJ\u001e\u0010S\u001a\u00020\u00002\u0006\u0010Q\u001a\u00020\rH\fø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bT\u0010\u001fJ\u001b\u0010U\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\bV\u0010\u001dJ\u001b\u0010U\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\bW\u0010\u001fJ\u001b\u0010U\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bX\u0010\u000bJ\u001b\u0010U\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\bY\u0010\"J\u0010\u0010Z\u001a\u00020[H\b¢\u0006\u0004\b\\\u0010]J\u0010\u0010^\u001a\u00020_H\b¢\u0006\u0004\b`\u0010aJ\u0010\u0010b\u001a\u00020cH\b¢\u0006\u0004\bd\u0010eJ\u0010\u0010f\u001a\u00020\rH\b¢\u0006\u0004\bg\u0010/J\u0010\u0010h\u001a\u00020\u0003H\b¢\u0006\u0004\bi\u0010\u0005J\u0010\u0010j\u001a\u00020kH\b¢\u0006\u0004\bl\u0010mJ\u000f\u0010n\u001a\u00020oH\u0016¢\u0006\u0004\bp\u0010qJ\u0016\u0010r\u001a\u00020\u000eH\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bs\u0010]J\u0016\u0010t\u001a\u00020\u0011H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bu\u0010/J\u0016\u0010v\u001a\u00020\u0000H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bw\u0010\u0005J\u0016\u0010x\u001a\u00020\u0016H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\by\u0010mJ\u001b\u0010z\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\b{\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0001\u0002\u0001\u00020\u0003ø\u0001\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006}"}, mo11629d2 = {"Lkotlin/ULong;", "", "data", "", "constructor-impl", "(J)J", "getData$annotations", "()V", "and", "other", "and-VKZWuLQ", "(JJ)J", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(JB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(JI)I", "compareTo-VKZWuLQ", "(JJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(JS)I", "dec", "dec-s-VKNKU", "div", "div-7apg3OU", "(JB)J", "div-WZ4Q5Ns", "(JI)J", "div-VKZWuLQ", "div-xj2QHRw", "(JS)J", "equals", "", "", "equals-impl", "(JLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(J)I", "inc", "inc-s-VKNKU", "inv", "inv-s-VKNKU", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(JB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(JS)S", "or", "or-VKZWuLQ", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/ULongRange;", "rangeTo-VKZWuLQ", "(JJ)Lkotlin/ranges/ULongRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-s-VKNKU", "shr", "shr-s-VKNKU", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(J)B", "toDouble", "", "toDouble-impl", "(J)D", "toFloat", "", "toFloat-impl", "(J)F", "toInt", "toInt-impl", "toLong", "toLong-impl", "toShort", "", "toShort-impl", "(J)S", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-VKZWuLQ", "Companion", "kotlin-stdlib"}, mo11630k = 1, mo11631mv = {1, 5, 1})
/* compiled from: ULong.kt */
public final class ULong implements Comparable<ULong> {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final long MAX_VALUE = -1;
    public static final long MIN_VALUE = 0;
    public static final int SIZE_BITS = 64;
    public static final int SIZE_BYTES = 8;
    private final long data;

    /* renamed from: box-impl  reason: not valid java name */
    public static final /* synthetic */ ULong m354boximpl(long j) {
        return new ULong(j);
    }

    /* renamed from: compareTo-VKZWuLQ  reason: not valid java name */
    private int m356compareToVKZWuLQ(long j) {
        return m357compareToVKZWuLQ(this.data, j);
    }

    /* renamed from: equals-impl  reason: not valid java name */
    public static boolean m366equalsimpl(long j, Object obj) {
        return (obj instanceof ULong) && j == ((ULong) obj).m411unboximpl();
    }

    /* renamed from: equals-impl0  reason: not valid java name */
    public static final boolean m367equalsimpl0(long j, long j2) {
        return j == j2;
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl  reason: not valid java name */
    public static int m372hashCodeimpl(long j) {
        return (int) (j ^ (j >>> 32));
    }

    public boolean equals(Object obj) {
        return m366equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m372hashCodeimpl(this.data);
    }

    public String toString() {
        return m405toStringimpl(this.data);
    }

    /* renamed from: unbox-impl  reason: not valid java name */
    public final /* synthetic */ long m411unboximpl() {
        return this.data;
    }

    private /* synthetic */ ULong(long data2) {
        this.data = data2;
    }

    /* renamed from: constructor-impl  reason: not valid java name */
    public static long m360constructorimpl(long data2) {
        return data2;
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return m356compareToVKZWuLQ(((ULong) obj).m411unboximpl());
    }

    @Metadata(mo11628d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u0004XTø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u00020\u0004XTø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bXT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bXT¢\u0006\u0002\n\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\n"}, mo11629d2 = {"Lkotlin/ULong$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/ULong;", "J", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, mo11630k = 1, mo11631mv = {1, 5, 1})
    /* compiled from: ULong.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    /* renamed from: compareTo-7apg3OU  reason: not valid java name */
    private static final int m355compareTo7apg3OU(long $this, byte other) {
        return UnsignedKt.ulongCompare($this, m360constructorimpl(((long) other) & 255));
    }

    /* renamed from: compareTo-xj2QHRw  reason: not valid java name */
    private static final int m359compareToxj2QHRw(long $this, short other) {
        return UnsignedKt.ulongCompare($this, m360constructorimpl(((long) other) & 65535));
    }

    /* renamed from: compareTo-WZ4Q5Ns  reason: not valid java name */
    private static final int m358compareToWZ4Q5Ns(long $this, int other) {
        return UnsignedKt.ulongCompare($this, m360constructorimpl(((long) other) & 4294967295L));
    }

    /* renamed from: compareTo-VKZWuLQ  reason: not valid java name */
    private static int m357compareToVKZWuLQ(long $this, long other) {
        return UnsignedKt.ulongCompare($this, other);
    }

    /* renamed from: plus-7apg3OU  reason: not valid java name */
    private static final long m384plus7apg3OU(long $this, byte other) {
        return m360constructorimpl(m360constructorimpl(((long) other) & 255) + $this);
    }

    /* renamed from: plus-xj2QHRw  reason: not valid java name */
    private static final long m387plusxj2QHRw(long $this, short other) {
        return m360constructorimpl(m360constructorimpl(((long) other) & 65535) + $this);
    }

    /* renamed from: plus-WZ4Q5Ns  reason: not valid java name */
    private static final long m386plusWZ4Q5Ns(long $this, int other) {
        return m360constructorimpl(m360constructorimpl(((long) other) & 4294967295L) + $this);
    }

    /* renamed from: plus-VKZWuLQ  reason: not valid java name */
    private static final long m385plusVKZWuLQ(long $this, long other) {
        return m360constructorimpl($this + other);
    }

    /* renamed from: minus-7apg3OU  reason: not valid java name */
    private static final long m375minus7apg3OU(long $this, byte other) {
        return m360constructorimpl($this - m360constructorimpl(((long) other) & 255));
    }

    /* renamed from: minus-xj2QHRw  reason: not valid java name */
    private static final long m378minusxj2QHRw(long $this, short other) {
        return m360constructorimpl($this - m360constructorimpl(((long) other) & 65535));
    }

    /* renamed from: minus-WZ4Q5Ns  reason: not valid java name */
    private static final long m377minusWZ4Q5Ns(long $this, int other) {
        return m360constructorimpl($this - m360constructorimpl(((long) other) & 4294967295L));
    }

    /* renamed from: minus-VKZWuLQ  reason: not valid java name */
    private static final long m376minusVKZWuLQ(long $this, long other) {
        return m360constructorimpl($this - other);
    }

    /* renamed from: times-7apg3OU  reason: not valid java name */
    private static final long m395times7apg3OU(long $this, byte other) {
        return m360constructorimpl(m360constructorimpl(((long) other) & 255) * $this);
    }

    /* renamed from: times-xj2QHRw  reason: not valid java name */
    private static final long m398timesxj2QHRw(long $this, short other) {
        return m360constructorimpl(m360constructorimpl(((long) other) & 65535) * $this);
    }

    /* renamed from: times-WZ4Q5Ns  reason: not valid java name */
    private static final long m397timesWZ4Q5Ns(long $this, int other) {
        return m360constructorimpl(m360constructorimpl(((long) other) & 4294967295L) * $this);
    }

    /* renamed from: times-VKZWuLQ  reason: not valid java name */
    private static final long m396timesVKZWuLQ(long $this, long other) {
        return m360constructorimpl($this * other);
    }

    /* renamed from: div-7apg3OU  reason: not valid java name */
    private static final long m362div7apg3OU(long $this, byte other) {
        return UnsignedKt.m537ulongDivideeb3DHEI($this, m360constructorimpl(((long) other) & 255));
    }

    /* renamed from: div-xj2QHRw  reason: not valid java name */
    private static final long m365divxj2QHRw(long $this, short other) {
        return UnsignedKt.m537ulongDivideeb3DHEI($this, m360constructorimpl(((long) other) & 65535));
    }

    /* renamed from: div-WZ4Q5Ns  reason: not valid java name */
    private static final long m364divWZ4Q5Ns(long $this, int other) {
        return UnsignedKt.m537ulongDivideeb3DHEI($this, m360constructorimpl(((long) other) & 4294967295L));
    }

    /* renamed from: div-VKZWuLQ  reason: not valid java name */
    private static final long m363divVKZWuLQ(long $this, long other) {
        return UnsignedKt.m537ulongDivideeb3DHEI($this, other);
    }

    /* renamed from: rem-7apg3OU  reason: not valid java name */
    private static final long m389rem7apg3OU(long $this, byte other) {
        return UnsignedKt.m538ulongRemaindereb3DHEI($this, m360constructorimpl(((long) other) & 255));
    }

    /* renamed from: rem-xj2QHRw  reason: not valid java name */
    private static final long m392remxj2QHRw(long $this, short other) {
        return UnsignedKt.m538ulongRemaindereb3DHEI($this, m360constructorimpl(((long) other) & 65535));
    }

    /* renamed from: rem-WZ4Q5Ns  reason: not valid java name */
    private static final long m391remWZ4Q5Ns(long $this, int other) {
        return UnsignedKt.m538ulongRemaindereb3DHEI($this, m360constructorimpl(((long) other) & 4294967295L));
    }

    /* renamed from: rem-VKZWuLQ  reason: not valid java name */
    private static final long m390remVKZWuLQ(long $this, long other) {
        return UnsignedKt.m538ulongRemaindereb3DHEI($this, other);
    }

    /* renamed from: floorDiv-7apg3OU  reason: not valid java name */
    private static final long m368floorDiv7apg3OU(long $this, byte other) {
        return UnsignedKt.m537ulongDivideeb3DHEI($this, m360constructorimpl(((long) other) & 255));
    }

    /* renamed from: floorDiv-xj2QHRw  reason: not valid java name */
    private static final long m371floorDivxj2QHRw(long $this, short other) {
        return UnsignedKt.m537ulongDivideeb3DHEI($this, m360constructorimpl(((long) other) & 65535));
    }

    /* renamed from: floorDiv-WZ4Q5Ns  reason: not valid java name */
    private static final long m370floorDivWZ4Q5Ns(long $this, int other) {
        return UnsignedKt.m537ulongDivideeb3DHEI($this, m360constructorimpl(((long) other) & 4294967295L));
    }

    /* renamed from: floorDiv-VKZWuLQ  reason: not valid java name */
    private static final long m369floorDivVKZWuLQ(long $this, long other) {
        return UnsignedKt.m537ulongDivideeb3DHEI($this, other);
    }

    /* renamed from: mod-7apg3OU  reason: not valid java name */
    private static final byte m379mod7apg3OU(long $this, byte other) {
        return UByte.m206constructorimpl((byte) ((int) UnsignedKt.m538ulongRemaindereb3DHEI($this, m360constructorimpl(((long) other) & 255))));
    }

    /* renamed from: mod-xj2QHRw  reason: not valid java name */
    private static final short m382modxj2QHRw(long $this, short other) {
        return UShort.m466constructorimpl((short) ((int) UnsignedKt.m538ulongRemaindereb3DHEI($this, m360constructorimpl(((long) other) & 65535))));
    }

    /* renamed from: mod-WZ4Q5Ns  reason: not valid java name */
    private static final int m381modWZ4Q5Ns(long $this, int other) {
        return UInt.m282constructorimpl((int) UnsignedKt.m538ulongRemaindereb3DHEI($this, m360constructorimpl(((long) other) & 4294967295L)));
    }

    /* renamed from: mod-VKZWuLQ  reason: not valid java name */
    private static final long m380modVKZWuLQ(long $this, long other) {
        return UnsignedKt.m538ulongRemaindereb3DHEI($this, other);
    }

    /* renamed from: inc-s-VKNKU  reason: not valid java name */
    private static final long m373incsVKNKU(long $this) {
        return m360constructorimpl(1 + $this);
    }

    /* renamed from: dec-s-VKNKU  reason: not valid java name */
    private static final long m361decsVKNKU(long $this) {
        return m360constructorimpl(-1 + $this);
    }

    /* renamed from: rangeTo-VKZWuLQ  reason: not valid java name */
    private static final ULongRange m388rangeToVKZWuLQ(long $this, long other) {
        return new ULongRange($this, other, (DefaultConstructorMarker) null);
    }

    /* renamed from: shl-s-VKNKU  reason: not valid java name */
    private static final long m393shlsVKNKU(long $this, int bitCount) {
        return m360constructorimpl($this << bitCount);
    }

    /* renamed from: shr-s-VKNKU  reason: not valid java name */
    private static final long m394shrsVKNKU(long $this, int bitCount) {
        return m360constructorimpl($this >>> bitCount);
    }

    /* renamed from: and-VKZWuLQ  reason: not valid java name */
    private static final long m353andVKZWuLQ(long $this, long other) {
        return m360constructorimpl($this & other);
    }

    /* renamed from: or-VKZWuLQ  reason: not valid java name */
    private static final long m383orVKZWuLQ(long $this, long other) {
        return m360constructorimpl($this | other);
    }

    /* renamed from: xor-VKZWuLQ  reason: not valid java name */
    private static final long m410xorVKZWuLQ(long $this, long other) {
        return m360constructorimpl($this ^ other);
    }

    /* renamed from: inv-s-VKNKU  reason: not valid java name */
    private static final long m374invsVKNKU(long $this) {
        return m360constructorimpl(~$this);
    }

    /* renamed from: toByte-impl  reason: not valid java name */
    private static final byte m399toByteimpl(long $this) {
        return (byte) ((int) $this);
    }

    /* renamed from: toShort-impl  reason: not valid java name */
    private static final short m404toShortimpl(long $this) {
        return (short) ((int) $this);
    }

    /* renamed from: toInt-impl  reason: not valid java name */
    private static final int m402toIntimpl(long $this) {
        return (int) $this;
    }

    /* renamed from: toLong-impl  reason: not valid java name */
    private static final long m403toLongimpl(long $this) {
        return $this;
    }

    /* renamed from: toUByte-w2LRezQ  reason: not valid java name */
    private static final byte m406toUBytew2LRezQ(long $this) {
        return UByte.m206constructorimpl((byte) ((int) $this));
    }

    /* renamed from: toUShort-Mh2AYeg  reason: not valid java name */
    private static final short m409toUShortMh2AYeg(long $this) {
        return UShort.m466constructorimpl((short) ((int) $this));
    }

    /* renamed from: toUInt-pVg5ArA  reason: not valid java name */
    private static final int m407toUIntpVg5ArA(long $this) {
        return UInt.m282constructorimpl((int) $this);
    }

    /* renamed from: toULong-s-VKNKU  reason: not valid java name */
    private static final long m408toULongsVKNKU(long $this) {
        return $this;
    }

    /* renamed from: toFloat-impl  reason: not valid java name */
    private static final float m401toFloatimpl(long $this) {
        return (float) UnsignedKt.ulongToDouble($this);
    }

    /* renamed from: toDouble-impl  reason: not valid java name */
    private static final double m400toDoubleimpl(long $this) {
        return UnsignedKt.ulongToDouble($this);
    }

    /* renamed from: toString-impl  reason: not valid java name */
    public static String m405toStringimpl(long $this) {
        return UnsignedKt.ulongToString($this);
    }
}
