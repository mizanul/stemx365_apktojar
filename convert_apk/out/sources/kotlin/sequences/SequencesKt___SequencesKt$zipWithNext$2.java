package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11628d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo11629d2 = {"<anonymous>", "", "T", "R", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo11630k = 3, mo11631mv = {1, 5, 1})
@DebugMetadata(mo12350c = "kotlin.sequences.SequencesKt___SequencesKt$zipWithNext$2", mo12351f = "_Sequences.kt", mo12352i = {0}, mo12353l = {2690}, mo12354m = "invokeSuspend", mo12355n = {"next"}, mo12356s = {"L$2"})
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$zipWithNext$2 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Sequence $this_zipWithNext;
    final /* synthetic */ Function2 $transform;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$zipWithNext$2(Sequence sequence, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$this_zipWithNext = sequence;
        this.$transform = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkNotNullParameter(continuation, "completion");
        SequencesKt___SequencesKt$zipWithNext$2 sequencesKt___SequencesKt$zipWithNext$2 = new SequencesKt___SequencesKt$zipWithNext$2(this.$this_zipWithNext, this.$transform, continuation);
        sequencesKt___SequencesKt$zipWithNext$2.L$0 = obj;
        return sequencesKt___SequencesKt$zipWithNext$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SequencesKt___SequencesKt$zipWithNext$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0049  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 1
            if (r1 == 0) goto L_0x0024
            if (r1 != r2) goto L_0x001c
            r1 = r9
            r3 = 0
            r4 = r3
            java.lang.Object r3 = r1.L$2
            java.lang.Object r5 = r1.L$1
            java.util.Iterator r5 = (java.util.Iterator) r5
            java.lang.Object r6 = r1.L$0
            kotlin.sequences.SequenceScope r6 = (kotlin.sequences.SequenceScope) r6
            kotlin.ResultKt.throwOnFailure(r10)
            goto L_0x0062
        L_0x001c:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0024:
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r9
            java.lang.Object r3 = r1.L$0
            kotlin.sequences.SequenceScope r3 = (kotlin.sequences.SequenceScope) r3
            kotlin.sequences.Sequence r4 = r1.$this_zipWithNext
            java.util.Iterator r4 = r4.iterator()
            boolean r5 = r4.hasNext()
            if (r5 != 0) goto L_0x003b
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x003b:
            java.lang.Object r5 = r4.next()
            r6 = r3
            r8 = r5
            r5 = r4
            r4 = r8
        L_0x0043:
            boolean r3 = r5.hasNext()
            if (r3 == 0) goto L_0x0065
            java.lang.Object r3 = r5.next()
            kotlin.jvm.functions.Function2 r7 = r1.$transform
            java.lang.Object r7 = r7.invoke(r4, r3)
            r1.L$0 = r6
            r1.L$1 = r5
            r1.L$2 = r3
            r1.label = r2
            java.lang.Object r7 = r6.yield(r7, r1)
            if (r7 != r0) goto L_0x0062
            return r0
        L_0x0062:
            r4 = r3
            goto L_0x0043
        L_0x0065:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$zipWithNext$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
