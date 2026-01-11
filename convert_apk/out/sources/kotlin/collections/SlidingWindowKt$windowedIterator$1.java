package kotlin.collections;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceScope;

@Metadata(mo11628d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo11629d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo11630k = 3, mo11631mv = {1, 5, 1})
@DebugMetadata(mo12350c = "kotlin.collections.SlidingWindowKt$windowedIterator$1", mo12351f = "SlidingWindow.kt", mo12352i = {}, mo12353l = {34, 40, 49, 55, 58}, mo12354m = "invokeSuspend", mo12355n = {}, mo12356s = {})
/* compiled from: SlidingWindow.kt */
final class SlidingWindowKt$windowedIterator$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super List<? extends T>>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Iterator $iterator;
    final /* synthetic */ boolean $partialWindows;
    final /* synthetic */ boolean $reuseBuffer;
    final /* synthetic */ int $size;
    final /* synthetic */ int $step;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SlidingWindowKt$windowedIterator$1(int i, int i2, Iterator it, boolean z, boolean z2, Continuation continuation) {
        super(2, continuation);
        this.$size = i;
        this.$step = i2;
        this.$iterator = it;
        this.$reuseBuffer = z;
        this.$partialWindows = z2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkNotNullParameter(continuation, "completion");
        SlidingWindowKt$windowedIterator$1 slidingWindowKt$windowedIterator$1 = new SlidingWindowKt$windowedIterator$1(this.$size, this.$step, this.$iterator, this.$reuseBuffer, this.$partialWindows, continuation);
        slidingWindowKt$windowedIterator$1.L$0 = obj;
        return slidingWindowKt$windowedIterator$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SlidingWindowKt$windowedIterator$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00d9  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00dd  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0115 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0116  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0174  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0182  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x01c7  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x00f1 A[SYNTHETIC] */
    public final java.lang.Object invokeSuspend(java.lang.Object r19) {
        /*
            r18 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r18
            int r2 = r1.label
            r3 = 5
            r4 = 4
            r5 = 3
            r6 = 2
            r7 = 1
            r8 = 0
            if (r2 == 0) goto L_0x0078
            if (r2 == r7) goto L_0x0061
            if (r2 == r6) goto L_0x0058
            if (r2 == r5) goto L_0x0043
            if (r2 == r4) goto L_0x002b
            if (r2 != r3) goto L_0x0023
            r0 = r18
            r2 = r19
            kotlin.ResultKt.throwOnFailure(r2)
            goto L_0x01c3
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x002b:
            r2 = r18
            r5 = r19
            java.lang.Object r6 = r2.L$1
            kotlin.collections.RingBuffer r6 = (kotlin.collections.RingBuffer) r6
            java.lang.Object r9 = r2.L$0
            kotlin.sequences.SequenceScope r9 = (kotlin.sequences.SequenceScope) r9
            kotlin.ResultKt.throwOnFailure(r5)
            r10 = r6
            r6 = r5
            r16 = r2
            r2 = r0
            r0 = r16
            goto L_0x01a3
        L_0x0043:
            r2 = r18
            r6 = r19
            java.lang.Object r9 = r2.L$2
            java.util.Iterator r9 = (java.util.Iterator) r9
            java.lang.Object r10 = r2.L$1
            kotlin.collections.RingBuffer r10 = (kotlin.collections.RingBuffer) r10
            java.lang.Object r11 = r2.L$0
            kotlin.sequences.SequenceScope r11 = (kotlin.sequences.SequenceScope) r11
            kotlin.ResultKt.throwOnFailure(r6)
            goto L_0x0169
        L_0x0058:
            r0 = r18
            r2 = r19
            kotlin.ResultKt.throwOnFailure(r2)
            goto L_0x0117
        L_0x0061:
            r2 = r18
            r3 = r19
            int r4 = r2.I$0
            java.lang.Object r5 = r2.L$2
            java.util.Iterator r5 = (java.util.Iterator) r5
            java.lang.Object r9 = r2.L$1
            java.util.ArrayList r9 = (java.util.ArrayList) r9
            java.lang.Object r10 = r2.L$0
            kotlin.sequences.SequenceScope r10 = (kotlin.sequences.SequenceScope) r10
            kotlin.ResultKt.throwOnFailure(r3)
            r11 = 0
            goto L_0x00d5
        L_0x0078:
            kotlin.ResultKt.throwOnFailure(r19)
            r2 = r18
            r9 = r19
            java.lang.Object r10 = r2.L$0
            kotlin.sequences.SequenceScope r10 = (kotlin.sequences.SequenceScope) r10
            int r11 = r2.$size
            r12 = 1024(0x400, float:1.435E-42)
            int r11 = kotlin.ranges.RangesKt.coerceAtMost((int) r11, (int) r12)
            int r12 = r2.$step
            int r13 = r2.$size
            int r12 = r12 - r13
            if (r12 < 0) goto L_0x011a
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>(r11)
            r4 = 0
            java.util.Iterator r5 = r2.$iterator
            r16 = r2
            r2 = r0
            r0 = r16
        L_0x009f:
            boolean r13 = r5.hasNext()
            if (r13 == 0) goto L_0x00f1
            java.lang.Object r13 = r5.next()
            if (r4 <= 0) goto L_0x00ae
            int r4 = r4 + -1
            goto L_0x00ef
        L_0x00ae:
            r3.add(r13)
            int r14 = r3.size()
            int r15 = r0.$size
            if (r14 != r15) goto L_0x00ef
            r0.L$0 = r10
            r0.L$1 = r3
            r0.L$2 = r5
            r0.I$0 = r12
            r0.label = r7
            java.lang.Object r4 = r10.yield(r3, r0)
            if (r4 != r2) goto L_0x00ca
            return r2
        L_0x00ca:
            r4 = r12
            r16 = r2
            r2 = r0
            r0 = r16
            r17 = r9
            r9 = r3
            r3 = r17
        L_0x00d5:
            boolean r12 = r2.$reuseBuffer
            if (r12 == 0) goto L_0x00dd
            r9.clear()
            goto L_0x00e4
        L_0x00dd:
            java.util.ArrayList r9 = new java.util.ArrayList
            int r12 = r2.$size
            r9.<init>(r12)
        L_0x00e4:
            r12 = r4
            r16 = r2
            r2 = r0
            r0 = r16
            r17 = r9
            r9 = r3
            r3 = r17
        L_0x00ef:
            goto L_0x009f
        L_0x00f1:
            r4 = r3
            java.util.Collection r4 = (java.util.Collection) r4
            boolean r4 = r4.isEmpty()
            r4 = r4 ^ r7
            if (r4 == 0) goto L_0x01c9
            boolean r4 = r0.$partialWindows
            if (r4 != 0) goto L_0x0107
            int r4 = r3.size()
            int r5 = r0.$size
            if (r4 != r5) goto L_0x01c9
        L_0x0107:
            r0.L$0 = r8
            r0.L$1 = r8
            r0.L$2 = r8
            r0.label = r6
            java.lang.Object r3 = r10.yield(r3, r0)
            if (r3 != r2) goto L_0x0116
            return r2
        L_0x0116:
            r2 = r9
        L_0x0117:
            r9 = r2
            goto L_0x01c9
        L_0x011a:
            kotlin.collections.RingBuffer r6 = new kotlin.collections.RingBuffer
            r6.<init>(r11)
            java.util.Iterator r11 = r2.$iterator
            r16 = r10
            r10 = r6
            r6 = r9
            r9 = r11
            r11 = r16
        L_0x0128:
            boolean r12 = r9.hasNext()
            if (r12 == 0) goto L_0x0170
            java.lang.Object r12 = r9.next()
            r10.add(r12)
            boolean r13 = r10.isFull()
            if (r13 == 0) goto L_0x016e
            int r13 = r10.size()
            int r14 = r2.$size
            if (r13 >= r14) goto L_0x0148
            kotlin.collections.RingBuffer r10 = r10.expanded(r14)
            goto L_0x016e
        L_0x0148:
            boolean r13 = r2.$reuseBuffer
            if (r13 == 0) goto L_0x0150
            r13 = r10
            java.util.List r13 = (java.util.List) r13
            goto L_0x015a
        L_0x0150:
            java.util.ArrayList r13 = new java.util.ArrayList
            r14 = r10
            java.util.Collection r14 = (java.util.Collection) r14
            r13.<init>(r14)
            java.util.List r13 = (java.util.List) r13
        L_0x015a:
            r2.L$0 = r11
            r2.L$1 = r10
            r2.L$2 = r9
            r2.label = r5
            java.lang.Object r12 = r11.yield(r13, r2)
            if (r12 != r0) goto L_0x0169
            return r0
        L_0x0169:
            int r12 = r2.$step
            r10.removeFirst(r12)
        L_0x016e:
            goto L_0x0128
        L_0x0170:
            boolean r5 = r2.$partialWindows
            if (r5 == 0) goto L_0x01c7
            r9 = r11
            r16 = r2
            r2 = r0
            r0 = r16
        L_0x017a:
            int r5 = r10.size()
            int r11 = r0.$step
            if (r5 <= r11) goto L_0x01a9
            boolean r5 = r0.$reuseBuffer
            if (r5 == 0) goto L_0x018a
            r5 = r10
            java.util.List r5 = (java.util.List) r5
            goto L_0x0194
        L_0x018a:
            java.util.ArrayList r5 = new java.util.ArrayList
            r11 = r10
            java.util.Collection r11 = (java.util.Collection) r11
            r5.<init>(r11)
            java.util.List r5 = (java.util.List) r5
        L_0x0194:
            r0.L$0 = r9
            r0.L$1 = r10
            r0.L$2 = r8
            r0.label = r4
            java.lang.Object r5 = r9.yield(r5, r0)
            if (r5 != r2) goto L_0x01a3
            return r2
        L_0x01a3:
            int r5 = r0.$step
            r10.removeFirst(r5)
            goto L_0x017a
        L_0x01a9:
            r4 = r10
            java.util.Collection r4 = (java.util.Collection) r4
            boolean r4 = r4.isEmpty()
            r4 = r4 ^ r7
            if (r4 == 0) goto L_0x01c5
            r0.L$0 = r8
            r0.L$1 = r8
            r0.L$2 = r8
            r0.label = r3
            java.lang.Object r3 = r9.yield(r10, r0)
            if (r3 != r2) goto L_0x01c2
            return r2
        L_0x01c2:
            r2 = r6
        L_0x01c3:
            r9 = r2
            goto L_0x01c9
        L_0x01c5:
            r9 = r6
            goto L_0x01c9
        L_0x01c7:
            r0 = r2
            r9 = r6
        L_0x01c9:
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.SlidingWindowKt$windowedIterator$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
