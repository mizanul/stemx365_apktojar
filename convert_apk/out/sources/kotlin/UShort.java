package kotlin;

import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;

@JvmInline
@Metadata(mo11628d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\n\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\b@\u0018\u0000 t2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001tB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0014H\nø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u0000H\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u0010J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u0013J\u001b\u0010\u001b\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b \u0010\u0018J\u001a\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003¢\u0006\u0004\b$\u0010%J\u001b\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\bø\u0001\u0000¢\u0006\u0004\b'\u0010\u0010J\u001b\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\bø\u0001\u0000¢\u0006\u0004\b(\u0010\u0013J\u001b\u0010&\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\bø\u0001\u0000¢\u0006\u0004\b)\u0010\u001fJ\u001b\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\bø\u0001\u0000¢\u0006\u0004\b*\u0010\u0018J\u0010\u0010+\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b,\u0010-J\u0016\u0010.\u001a\u00020\u0000H\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b/\u0010\u0005J\u0016\u00100\u001a\u00020\u0000H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b1\u0010\u0005J\u001b\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\b3\u0010\u0010J\u001b\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b4\u0010\u0013J\u001b\u00102\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\nø\u0001\u0000¢\u0006\u0004\b5\u0010\u001fJ\u001b\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b6\u0010\u0018J\u001b\u00107\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000eH\bø\u0001\u0000¢\u0006\u0004\b8\u00109J\u001b\u00107\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\bø\u0001\u0000¢\u0006\u0004\b:\u0010\u0013J\u001b\u00107\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\bø\u0001\u0000¢\u0006\u0004\b;\u0010\u001fJ\u001b\u00107\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\bø\u0001\u0000¢\u0006\u0004\b<\u0010\u000bJ\u001b\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\b>\u0010\u000bJ\u001b\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\b@\u0010\u0010J\u001b\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\bA\u0010\u0013J\u001b\u0010?\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\nø\u0001\u0000¢\u0006\u0004\bB\u0010\u001fJ\u001b\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bC\u0010\u0018J\u001b\u0010D\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bF\u0010GJ\u001b\u0010H\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\bI\u0010\u0010J\u001b\u0010H\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\bJ\u0010\u0013J\u001b\u0010H\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\nø\u0001\u0000¢\u0006\u0004\bK\u0010\u001fJ\u001b\u0010H\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bL\u0010\u0018J\u001b\u0010M\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\bN\u0010\u0010J\u001b\u0010M\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\bO\u0010\u0013J\u001b\u0010M\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\nø\u0001\u0000¢\u0006\u0004\bP\u0010\u001fJ\u001b\u0010M\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bQ\u0010\u0018J\u0010\u0010R\u001a\u00020SH\b¢\u0006\u0004\bT\u0010UJ\u0010\u0010V\u001a\u00020WH\b¢\u0006\u0004\bX\u0010YJ\u0010\u0010Z\u001a\u00020[H\b¢\u0006\u0004\b\\\u0010]J\u0010\u0010^\u001a\u00020\rH\b¢\u0006\u0004\b_\u0010-J\u0010\u0010`\u001a\u00020aH\b¢\u0006\u0004\bb\u0010cJ\u0010\u0010d\u001a\u00020\u0003H\b¢\u0006\u0004\be\u0010\u0005J\u000f\u0010f\u001a\u00020gH\u0016¢\u0006\u0004\bh\u0010iJ\u0016\u0010j\u001a\u00020\u000eH\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bk\u0010UJ\u0016\u0010l\u001a\u00020\u0011H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bm\u0010-J\u0016\u0010n\u001a\u00020\u0014H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bo\u0010cJ\u0016\u0010p\u001a\u00020\u0000H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bq\u0010\u0005J\u001b\u0010r\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\bs\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0001\u0002\u0001\u00020\u0003ø\u0001\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006u"}, mo11629d2 = {"Lkotlin/UShort;", "", "data", "", "constructor-impl", "(S)S", "getData$annotations", "()V", "and", "other", "and-xj2QHRw", "(SS)S", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(SB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(SI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(SJ)I", "compareTo-xj2QHRw", "(SS)I", "dec", "dec-Mh2AYeg", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(SJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(SLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(S)I", "inc", "inc-Mh2AYeg", "inv", "inv-Mh2AYeg", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(SB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "or", "or-xj2QHRw", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-xj2QHRw", "(SS)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(S)B", "toDouble", "", "toDouble-impl", "(S)D", "toFloat", "", "toFloat-impl", "(S)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(S)J", "toShort", "toShort-impl", "toString", "", "toString-impl", "(S)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-xj2QHRw", "Companion", "kotlin-stdlib"}, mo11630k = 1, mo11631mv = {1, 5, 1})
/* compiled from: UShort.kt */
public final class UShort implements Comparable<UShort> {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final short MAX_VALUE = -1;
    public static final short MIN_VALUE = 0;
    public static final int SIZE_BITS = 16;
    public static final int SIZE_BYTES = 2;
    private final short data;

    /* renamed from: box-impl  reason: not valid java name */
    public static final /* synthetic */ UShort m460boximpl(short s) {
        return new UShort(s);
    }

    /* renamed from: compareTo-xj2QHRw  reason: not valid java name */
    private int m464compareToxj2QHRw(short s) {
        return m465compareToxj2QHRw(this.data, s);
    }

    /* renamed from: equals-impl  reason: not valid java name */
    public static boolean m472equalsimpl(short s, Object obj) {
        return (obj instanceof UShort) && s == ((UShort) obj).m515unboximpl();
    }

    /* renamed from: equals-impl0  reason: not valid java name */
    public static final boolean m473equalsimpl0(short s, short s2) {
        return s == s2;
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl  reason: not valid java name */
    public static int m478hashCodeimpl(short s) {
        return s;
    }

    public boolean equals(Object obj) {
        return m472equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m478hashCodeimpl(this.data);
    }

    public String toString() {
        return m509toStringimpl(this.data);
    }

    /* renamed from: unbox-impl  reason: not valid java name */
    public final /* synthetic */ short m515unboximpl() {
        return this.data;
    }

    private /* synthetic */ UShort(short data2) {
        this.data = data2;
    }

    /* renamed from: constructor-impl  reason: not valid java name */
    public static short m466constructorimpl(short data2) {
        return data2;
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return m464compareToxj2QHRw(((UShort) obj).m515unboximpl());
    }

    @Metadata(mo11628d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u0004XTø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u00020\u0004XTø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bXT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bXT¢\u0006\u0002\n\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\n"}, mo11629d2 = {"Lkotlin/UShort$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UShort;", "S", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, mo11630k = 1, mo11631mv = {1, 5, 1})
    /* compiled from: UShort.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    /* renamed from: compareTo-7apg3OU  reason: not valid java name */
    private static final int m461compareTo7apg3OU(short $this, byte other) {
        return Intrinsics.compare((int) 65535 & $this, (int) other & 255);
    }

    /* renamed from: compareTo-xj2QHRw  reason: not valid java name */
    private static int m465compareToxj2QHRw(short $this, short other) {
        return Intrinsics.compare((int) $this & MAX_VALUE, (int) 65535 & other);
    }

    /* renamed from: compareTo-WZ4Q5Ns  reason: not valid java name */
    private static final int m463compareToWZ4Q5Ns(short $this, int other) {
        return UnsignedKt.uintCompare(UInt.m282constructorimpl(65535 & $this), other);
    }

    /* renamed from: compareTo-VKZWuLQ  reason: not valid java name */
    private static final int m462compareToVKZWuLQ(short $this, long other) {
        return UnsignedKt.ulongCompare(ULong.m360constructorimpl(((long) $this) & 65535), other);
    }

    /* renamed from: plus-7apg3OU  reason: not valid java name */
    private static final int m490plus7apg3OU(short $this, byte other) {
        return UInt.m282constructorimpl(UInt.m282constructorimpl(65535 & $this) + UInt.m282constructorimpl(other & 255));
    }

    /* renamed from: plus-xj2QHRw  reason: not valid java name */
    private static final int m493plusxj2QHRw(short $this, short other) {
        return UInt.m282constructorimpl(UInt.m282constructorimpl($this & MAX_VALUE) + UInt.m282constructorimpl(65535 & other));
    }

    /* renamed from: plus-WZ4Q5Ns  reason: not valid java name */
    private static final int m492plusWZ4Q5Ns(short $this, int other) {
        return UInt.m282constructorimpl(UInt.m282constructorimpl(65535 & $this) + other);
    }

    /* renamed from: plus-VKZWuLQ  reason: not valid java name */
    private static final long m491plusVKZWuLQ(short $this, long other) {
        return ULong.m360constructorimpl(ULong.m360constructorimpl(((long) $this) & 65535) + other);
    }

    /* renamed from: minus-7apg3OU  reason: not valid java name */
    private static final int m481minus7apg3OU(short $this, byte other) {
        return UInt.m282constructorimpl(UInt.m282constructorimpl(65535 & $this) - UInt.m282constructorimpl(other & 255));
    }

    /* renamed from: minus-xj2QHRw  reason: not valid java name */
    private static final int m484minusxj2QHRw(short $this, short other) {
        return UInt.m282constructorimpl(UInt.m282constructorimpl($this & MAX_VALUE) - UInt.m282constructorimpl(65535 & other));
    }

    /* renamed from: minus-WZ4Q5Ns  reason: not valid java name */
    private static final int m483minusWZ4Q5Ns(short $this, int other) {
        return UInt.m282constructorimpl(UInt.m282constructorimpl(65535 & $this) - other);
    }

    /* renamed from: minus-VKZWuLQ  reason: not valid java name */
    private static final long m482minusVKZWuLQ(short $this, long other) {
        return ULong.m360constructorimpl(ULong.m360constructorimpl(((long) $this) & 65535) - other);
    }

    /* renamed from: times-7apg3OU  reason: not valid java name */
    private static final int m499times7apg3OU(short $this, byte other) {
        return UInt.m282constructorimpl(UInt.m282constructorimpl(65535 & $this) * UInt.m282constructorimpl(other & 255));
    }

    /* renamed from: times-xj2QHRw  reason: not valid java name */
    private static final int m502timesxj2QHRw(short $this, short other) {
        return UInt.m282constructorimpl(UInt.m282constructorimpl($this & MAX_VALUE) * UInt.m282constructorimpl(65535 & other));
    }

    /* renamed from: times-WZ4Q5Ns  reason: not valid java name */
    private static final int m501timesWZ4Q5Ns(short $this, int other) {
        return UInt.m282constructorimpl(UInt.m282constructorimpl(65535 & $this) * other);
    }

    /* renamed from: times-VKZWuLQ  reason: not valid java name */
    private static final long m500timesVKZWuLQ(short $this, long other) {
        return ULong.m360constructorimpl(ULong.m360constructorimpl(((long) $this) & 65535) * other);
    }

    /* renamed from: div-7apg3OU  reason: not valid java name */
    private static final int m468div7apg3OU(short $this, byte other) {
        return UnsignedKt.m535uintDivideJ1ME1BU(UInt.m282constructorimpl(65535 & $this), UInt.m282constructorimpl(other & 255));
    }

    /* renamed from: div-xj2QHRw  reason: not valid java name */
    private static final int m471divxj2QHRw(short $this, short other) {
        return UnsignedKt.m535uintDivideJ1ME1BU(UInt.m282constructorimpl($this & MAX_VALUE), UInt.m282constructorimpl(65535 & other));
    }

    /* renamed from: div-WZ4Q5Ns  reason: not valid java name */
    private static final int m470divWZ4Q5Ns(short $this, int other) {
        return UnsignedKt.m535uintDivideJ1ME1BU(UInt.m282constructorimpl(65535 & $this), other);
    }

    /* renamed from: div-VKZWuLQ  reason: not valid java name */
    private static final long m469divVKZWuLQ(short $this, long other) {
        return UnsignedKt.m537ulongDivideeb3DHEI(ULong.m360constructorimpl(((long) $this) & 65535), other);
    }

    /* renamed from: rem-7apg3OU  reason: not valid java name */
    private static final int m495rem7apg3OU(short $this, byte other) {
        return UnsignedKt.m536uintRemainderJ1ME1BU(UInt.m282constructorimpl(65535 & $this), UInt.m282constructorimpl(other & 255));
    }

    /* renamed from: rem-xj2QHRw  reason: not valid java name */
    private static final int m498remxj2QHRw(short $this, short other) {
        return UnsignedKt.m536uintRemainderJ1ME1BU(UInt.m282constructorimpl($this & MAX_VALUE), UInt.m282constructorimpl(65535 & other));
    }

    /* renamed from: rem-WZ4Q5Ns  reason: not valid java name */
    private static final int m497remWZ4Q5Ns(short $this, int other) {
        return UnsignedKt.m536uintRemainderJ1ME1BU(UInt.m282constructorimpl(65535 & $this), other);
    }

    /* renamed from: rem-VKZWuLQ  reason: not valid java name */
    private static final long m496remVKZWuLQ(short $this, long other) {
        return UnsignedKt.m538ulongRemaindereb3DHEI(ULong.m360constructorimpl(((long) $this) & 65535), other);
    }

    /* renamed from: floorDiv-7apg3OU  reason: not valid java name */
    private static final int m474floorDiv7apg3OU(short $this, byte other) {
        return UnsignedKt.m535uintDivideJ1ME1BU(UInt.m282constructorimpl(65535 & $this), UInt.m282constructorimpl(other & 255));
    }

    /* renamed from: floorDiv-xj2QHRw  reason: not valid java name */
    private static final int m477floorDivxj2QHRw(short $this, short other) {
        return UnsignedKt.m535uintDivideJ1ME1BU(UInt.m282constructorimpl($this & MAX_VALUE), UInt.m282constructorimpl(65535 & other));
    }

    /* renamed from: floorDiv-WZ4Q5Ns  reason: not valid java name */
    private static final int m476floorDivWZ4Q5Ns(short $this, int other) {
        return UnsignedKt.m535uintDivideJ1ME1BU(UInt.m282constructorimpl(65535 & $this), other);
    }

    /* renamed from: floorDiv-VKZWuLQ  reason: not valid java name */
    private static final long m475floorDivVKZWuLQ(short $this, long other) {
        return UnsignedKt.m537ulongDivideeb3DHEI(ULong.m360constructorimpl(((long) $this) & 65535), other);
    }

    /* renamed from: mod-7apg3OU  reason: not valid java name */
    private static final byte m485mod7apg3OU(short $this, byte other) {
        return UByte.m206constructorimpl((byte) UnsignedKt.m536uintRemainderJ1ME1BU(UInt.m282constructorimpl(65535 & $this), UInt.m282constructorimpl(other & 255)));
    }

    /* renamed from: mod-xj2QHRw  reason: not valid java name */
    private static final short m488modxj2QHRw(short $this, short other) {
        return m466constructorimpl((short) UnsignedKt.m536uintRemainderJ1ME1BU(UInt.m282constructorimpl($this & MAX_VALUE), UInt.m282constructorimpl(65535 & other)));
    }

    /* renamed from: mod-WZ4Q5Ns  reason: not valid java name */
    private static final int m487modWZ4Q5Ns(short $this, int other) {
        return UnsignedKt.m536uintRemainderJ1ME1BU(UInt.m282constructorimpl(65535 & $this), other);
    }

    /* renamed from: mod-VKZWuLQ  reason: not valid java name */
    private static final long m486modVKZWuLQ(short $this, long other) {
        return UnsignedKt.m538ulongRemaindereb3DHEI(ULong.m360constructorimpl(((long) $this) & 65535), other);
    }

    /* renamed from: inc-Mh2AYeg  reason: not valid java name */
    private static final short m479incMh2AYeg(short $this) {
        return m466constructorimpl((short) ($this + 1));
    }

    /* renamed from: dec-Mh2AYeg  reason: not valid java name */
    private static final short m467decMh2AYeg(short $this) {
        return m466constructorimpl((short) ($this - 1));
    }

    /* renamed from: rangeTo-xj2QHRw  reason: not valid java name */
    private static final UIntRange m494rangeToxj2QHRw(short $this, short other) {
        return new UIntRange(UInt.m282constructorimpl($this & MAX_VALUE), UInt.m282constructorimpl(65535 & other), (DefaultConstructorMarker) null);
    }

    /* renamed from: and-xj2QHRw  reason: not valid java name */
    private static final short m459andxj2QHRw(short $this, short other) {
        return m466constructorimpl((short) ($this & other));
    }

    /* renamed from: or-xj2QHRw  reason: not valid java name */
    private static final short m489orxj2QHRw(short $this, short other) {
        return m466constructorimpl((short) ($this | other));
    }

    /* renamed from: xor-xj2QHRw  reason: not valid java name */
    private static final short m514xorxj2QHRw(short $this, short other) {
        return m466constructorimpl((short) ($this ^ other));
    }

    /* renamed from: inv-Mh2AYeg  reason: not valid java name */
    private static final short m480invMh2AYeg(short $this) {
        return m466constructorimpl((short) (~$this));
    }

    /* renamed from: toByte-impl  reason: not valid java name */
    private static final byte m503toByteimpl(short $this) {
        return (byte) $this;
    }

    /* renamed from: toShort-impl  reason: not valid java name */
    private static final short m508toShortimpl(short $this) {
        return $this;
    }

    /* renamed from: toInt-impl  reason: not valid java name */
    private static final int m506toIntimpl(short $this) {
        return 65535 & $this;
    }

    /* renamed from: toLong-impl  reason: not valid java name */
    private static final long m507toLongimpl(short $this) {
        return ((long) $this) & 65535;
    }

    /* renamed from: toUByte-w2LRezQ  reason: not valid java name */
    private static final byte m510toUBytew2LRezQ(short $this) {
        return UByte.m206constructorimpl((byte) $this);
    }

    /* renamed from: toUShort-Mh2AYeg  reason: not valid java name */
    private static final short m513toUShortMh2AYeg(short $this) {
        return $this;
    }

    /* renamed from: toUInt-pVg5ArA  reason: not valid java name */
    private static final int m511toUIntpVg5ArA(short $this) {
        return UInt.m282constructorimpl(65535 & $this);
    }

    /* renamed from: toULong-s-VKNKU  reason: not valid java name */
    private static final long m512toULongsVKNKU(short $this) {
        return ULong.m360constructorimpl(((long) $this) & 65535);
    }

    /* renamed from: toFloat-impl  reason: not valid java name */
    private static final float m505toFloatimpl(short $this) {
        return (float) (65535 & $this);
    }

    /* renamed from: toDouble-impl  reason: not valid java name */
    private static final double m504toDoubleimpl(short $this) {
        return (double) (65535 & $this);
    }

    /* renamed from: toString-impl  reason: not valid java name */
    public static String m509toStringimpl(short $this) {
        return String.valueOf(65535 & $this);
    }
}
