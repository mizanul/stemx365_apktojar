package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11628d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo11629d2 = {"<anonymous>", "", "T", "R", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo11630k = 3, mo11631mv = {1, 5, 1})
@DebugMetadata(mo12350c = "kotlin.sequences.SequencesKt___SequencesKt$runningFold$1", mo12351f = "_Sequences.kt", mo12352i = {0, 1}, mo12353l = {2115, 2119}, mo12354m = "invokeSuspend", mo12355n = {"$this$sequence", "$this$sequence"}, mo12356s = {"L$0", "L$0"})
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$runningFold$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Object $initial;
    final /* synthetic */ Function2 $operation;
    final /* synthetic */ Sequence $this_runningFold;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$runningFold$1(Sequence sequence, Object obj, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$this_runningFold = sequence;
        this.$initial = obj;
        this.$operation = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkNotNullParameter(continuation, "completion");
        SequencesKt___SequencesKt$runningFold$1 sequencesKt___SequencesKt$runningFold$1 = new SequencesKt___SequencesKt$runningFold$1(this.$this_runningFold, this.$initial, this.$operation, continuation);
        sequencesKt___SequencesKt$runningFold$1.L$0 = obj;
        return sequencesKt___SequencesKt$runningFold$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SequencesKt___SequencesKt$runningFold$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x005a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L_0x0033
            r4 = 0
            if (r1 == r3) goto L_0x0028
            if (r1 != r2) goto L_0x0020
            r1 = r9
            r3 = r4
            java.lang.Object r4 = r1.L$2
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r5 = r1.L$1
            java.lang.Object r6 = r1.L$0
            r3 = r6
            kotlin.sequences.SequenceScope r3 = (kotlin.sequences.SequenceScope) r3
            kotlin.ResultKt.throwOnFailure(r10)
            goto L_0x0073
        L_0x0020:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0028:
            r1 = r9
            r3 = r4
            java.lang.Object r4 = r1.L$0
            r3 = r4
            kotlin.sequences.SequenceScope r3 = (kotlin.sequences.SequenceScope) r3
            kotlin.ResultKt.throwOnFailure(r10)
            goto L_0x0049
        L_0x0033:
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r9
            java.lang.Object r4 = r1.L$0
            kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
            java.lang.Object r5 = r1.$initial
            r1.L$0 = r4
            r1.label = r3
            java.lang.Object r3 = r4.yield(r5, r1)
            if (r3 != r0) goto L_0x0048
            return r0
        L_0x0048:
            r3 = r4
        L_0x0049:
            java.lang.Object r4 = r1.$initial
            kotlin.sequences.Sequence r5 = r1.$this_runningFold
            java.util.Iterator r5 = r5.iterator()
            r8 = r5
            r5 = r4
            r4 = r8
        L_0x0054:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L_0x0075
            java.lang.Object r6 = r4.next()
            kotlin.jvm.functions.Function2 r7 = r1.$operation
            java.lang.Object r5 = r7.invoke(r5, r6)
            r1.L$0 = r3
            r1.L$1 = r5
            r1.L$2 = r4
            r1.label = r2
            java.lang.Object r6 = r3.yield(r5, r1)
            if (r6 != r0) goto L_0x0073
            return r0
        L_0x0073:
            goto L_0x0054
        L_0x0075:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$runningFold$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
