package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11628d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0003*\u0002H\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo11629d2 = {"<anonymous>", "", "S", "T", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo11630k = 3, mo11631mv = {1, 5, 1})
@DebugMetadata(mo12350c = "kotlin.sequences.SequencesKt___SequencesKt$runningReduce$1", mo12351f = "_Sequences.kt", mo12352i = {0, 0, 0, 1, 1}, mo12353l = {2173, 2176}, mo12354m = "invokeSuspend", mo12355n = {"$this$sequence", "iterator", "accumulator", "$this$sequence", "iterator"}, mo12356s = {"L$0", "L$1", "L$2", "L$0", "L$1"})
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$runningReduce$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super S>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function2 $operation;
    final /* synthetic */ Sequence $this_runningReduce;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$runningReduce$1(Sequence sequence, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$this_runningReduce = sequence;
        this.$operation = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkNotNullParameter(continuation, "completion");
        SequencesKt___SequencesKt$runningReduce$1 sequencesKt___SequencesKt$runningReduce$1 = new SequencesKt___SequencesKt$runningReduce$1(this.$this_runningReduce, this.$operation, continuation);
        sequencesKt___SequencesKt$runningReduce$1.L$0 = obj;
        return sequencesKt___SequencesKt$runningReduce$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SequencesKt___SequencesKt$runningReduce$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: java.util.Iterator} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v8, resolved type: java.util.Iterator} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x006d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L_0x003c
            r4 = 0
            if (r1 == r3) goto L_0x0029
            if (r1 != r2) goto L_0x0021
            r1 = r8
            r3 = r4
            java.lang.Object r5 = r1.L$2
            java.lang.Object r6 = r1.L$1
            r4 = r6
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r6 = r1.L$0
            r3 = r6
            kotlin.sequences.SequenceScope r3 = (kotlin.sequences.SequenceScope) r3
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x0086
        L_0x0021:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x0029:
            r1 = r8
            r3 = r4
            r5 = r4
            java.lang.Object r5 = r1.L$2
            java.lang.Object r6 = r1.L$1
            r4 = r6
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r6 = r1.L$0
            r3 = r6
            kotlin.sequences.SequenceScope r3 = (kotlin.sequences.SequenceScope) r3
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x0066
        L_0x003c:
            kotlin.ResultKt.throwOnFailure(r9)
            r1 = r8
            java.lang.Object r4 = r1.L$0
            kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
            kotlin.sequences.Sequence r5 = r1.$this_runningReduce
            java.util.Iterator r5 = r5.iterator()
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0089
            java.lang.Object r6 = r5.next()
            r1.L$0 = r4
            r1.L$1 = r5
            r1.L$2 = r6
            r1.label = r3
            java.lang.Object r3 = r4.yield(r6, r1)
            if (r3 != r0) goto L_0x0063
            return r0
        L_0x0063:
            r3 = r4
            r4 = r5
            r5 = r6
        L_0x0066:
        L_0x0067:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L_0x0088
            kotlin.jvm.functions.Function2 r6 = r1.$operation
            java.lang.Object r7 = r4.next()
            java.lang.Object r5 = r6.invoke(r5, r7)
            r1.L$0 = r3
            r1.L$1 = r4
            r1.L$2 = r5
            r1.label = r2
            java.lang.Object r6 = r3.yield(r5, r1)
            if (r6 != r0) goto L_0x0086
            return r0
        L_0x0086:
            goto L_0x0067
        L_0x0088:
            r4 = r3
        L_0x0089:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$runningReduce$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
