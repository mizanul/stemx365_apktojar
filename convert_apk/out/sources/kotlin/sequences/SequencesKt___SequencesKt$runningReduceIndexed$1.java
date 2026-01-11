package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11628d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0003*\u0002H\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo11629d2 = {"<anonymous>", "", "S", "T", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo11630k = 3, mo11631mv = {1, 5, 1})
@DebugMetadata(mo12350c = "kotlin.sequences.SequencesKt___SequencesKt$runningReduceIndexed$1", mo12351f = "_Sequences.kt", mo12352i = {0, 0, 0}, mo12353l = {2202, 2206}, mo12354m = "invokeSuspend", mo12355n = {"$this$sequence", "iterator", "accumulator"}, mo12356s = {"L$0", "L$1", "L$2"})
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$runningReduceIndexed$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super S>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function3 $operation;
    final /* synthetic */ Sequence $this_runningReduceIndexed;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$runningReduceIndexed$1(Sequence sequence, Function3 function3, Continuation continuation) {
        super(2, continuation);
        this.$this_runningReduceIndexed = sequence;
        this.$operation = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkNotNullParameter(continuation, "completion");
        SequencesKt___SequencesKt$runningReduceIndexed$1 sequencesKt___SequencesKt$runningReduceIndexed$1 = new SequencesKt___SequencesKt$runningReduceIndexed$1(this.$this_runningReduceIndexed, this.$operation, continuation);
        sequencesKt___SequencesKt$runningReduceIndexed$1.L$0 = obj;
        return sequencesKt___SequencesKt$runningReduceIndexed$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SequencesKt___SequencesKt$runningReduceIndexed$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: java.util.Iterator} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0072  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r12.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L_0x003c
            if (r1 == r3) goto L_0x0028
            if (r1 != r2) goto L_0x0020
            r1 = r12
            int r3 = r1.I$0
            java.lang.Object r4 = r1.L$2
            java.lang.Object r5 = r1.L$1
            java.util.Iterator r5 = (java.util.Iterator) r5
            java.lang.Object r6 = r1.L$0
            kotlin.sequences.SequenceScope r6 = (kotlin.sequences.SequenceScope) r6
            kotlin.ResultKt.throwOnFailure(r13)
            goto L_0x0099
        L_0x0020:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r0)
            throw r13
        L_0x0028:
            r1 = r12
            r3 = 0
            r4 = r3
            r5 = r3
            java.lang.Object r5 = r1.L$2
            java.lang.Object r6 = r1.L$1
            r3 = r6
            java.util.Iterator r3 = (java.util.Iterator) r3
            java.lang.Object r6 = r1.L$0
            r4 = r6
            kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
            kotlin.ResultKt.throwOnFailure(r13)
            goto L_0x0065
        L_0x003c:
            kotlin.ResultKt.throwOnFailure(r13)
            r1 = r12
            java.lang.Object r4 = r1.L$0
            kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
            kotlin.sequences.Sequence r5 = r1.$this_runningReduceIndexed
            java.util.Iterator r5 = r5.iterator()
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x009e
            java.lang.Object r6 = r5.next()
            r1.L$0 = r4
            r1.L$1 = r5
            r1.L$2 = r6
            r1.label = r3
            java.lang.Object r3 = r4.yield(r6, r1)
            if (r3 != r0) goto L_0x0063
            return r0
        L_0x0063:
            r3 = r5
            r5 = r6
        L_0x0065:
            r6 = 1
            r10 = r5
            r5 = r3
            r3 = r10
            r11 = r6
            r6 = r4
            r4 = r11
        L_0x006c:
            boolean r7 = r5.hasNext()
            if (r7 == 0) goto L_0x009e
            kotlin.jvm.functions.Function3 r7 = r1.$operation
            int r8 = r4 + 1
            if (r4 >= 0) goto L_0x007b
            kotlin.collections.CollectionsKt.throwIndexOverflow()
        L_0x007b:
            java.lang.Integer r4 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r4)
            java.lang.Object r9 = r5.next()
            java.lang.Object r4 = r7.invoke(r4, r3, r9)
            r1.L$0 = r6
            r1.L$1 = r5
            r1.L$2 = r4
            r1.I$0 = r8
            r1.label = r2
            java.lang.Object r3 = r6.yield(r4, r1)
            if (r3 != r0) goto L_0x0098
            return r0
        L_0x0098:
            r3 = r8
        L_0x0099:
            r10 = r4
            r4 = r3
            r3 = r10
            goto L_0x006c
        L_0x009e:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$runningReduceIndexed$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
