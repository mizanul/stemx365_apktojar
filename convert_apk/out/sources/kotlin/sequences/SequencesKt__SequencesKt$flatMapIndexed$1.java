package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11628d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0005H@¢\u0006\u0004\b\u0006\u0010\u0007"}, mo11629d2 = {"<anonymous>", "", "T", "C", "R", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo11630k = 3, mo11631mv = {1, 5, 1})
@DebugMetadata(mo12350c = "kotlin.sequences.SequencesKt__SequencesKt$flatMapIndexed$1", mo12351f = "Sequences.kt", mo12352i = {}, mo12353l = {332}, mo12354m = "invokeSuspend", mo12355n = {}, mo12356s = {})
/* compiled from: Sequences.kt */
final class SequencesKt__SequencesKt$flatMapIndexed$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function1 $iterator;
    final /* synthetic */ Sequence $source;
    final /* synthetic */ Function2 $transform;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt__SequencesKt$flatMapIndexed$1(Sequence sequence, Function2 function2, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$source = sequence;
        this.$transform = function2;
        this.$iterator = function1;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkNotNullParameter(continuation, "completion");
        SequencesKt__SequencesKt$flatMapIndexed$1 sequencesKt__SequencesKt$flatMapIndexed$1 = new SequencesKt__SequencesKt$flatMapIndexed$1(this.$source, this.$transform, this.$iterator, continuation);
        sequencesKt__SequencesKt$flatMapIndexed$1.L$0 = obj;
        return sequencesKt__SequencesKt$flatMapIndexed$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SequencesKt__SequencesKt$flatMapIndexed$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        SequenceScope $this$sequence;
        Iterator it;
        int index;
        SequencesKt__SequencesKt$flatMapIndexed$1 sequencesKt__SequencesKt$flatMapIndexed$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            sequencesKt__SequencesKt$flatMapIndexed$1 = this;
            Iterator it2 = sequencesKt__SequencesKt$flatMapIndexed$1.$source.iterator();
            $this$sequence = (SequenceScope) sequencesKt__SequencesKt$flatMapIndexed$1.L$0;
            index = 0;
            it = it2;
        } else if (i == 1) {
            sequencesKt__SequencesKt$flatMapIndexed$1 = this;
            index = sequencesKt__SequencesKt$flatMapIndexed$1.I$0;
            it = (Iterator) sequencesKt__SequencesKt$flatMapIndexed$1.L$1;
            $this$sequence = (SequenceScope) sequencesKt__SequencesKt$flatMapIndexed$1.L$0;
            ResultKt.throwOnFailure($result);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        while (it.hasNext()) {
            Object element = it.next();
            Function2 function2 = sequencesKt__SequencesKt$flatMapIndexed$1.$transform;
            int index2 = index + 1;
            if (index < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Object result = function2.invoke(Boxing.boxInt(index), element);
            sequencesKt__SequencesKt$flatMapIndexed$1.L$0 = $this$sequence;
            sequencesKt__SequencesKt$flatMapIndexed$1.L$1 = it;
            sequencesKt__SequencesKt$flatMapIndexed$1.I$0 = index2;
            sequencesKt__SequencesKt$flatMapIndexed$1.label = 1;
            if ($this$sequence.yieldAll((Iterator) sequencesKt__SequencesKt$flatMapIndexed$1.$iterator.invoke(result), (Continuation<? super Unit>) sequencesKt__SequencesKt$flatMapIndexed$1) == coroutine_suspended) {
                return coroutine_suspended;
            }
            index = index2;
        }
        return Unit.INSTANCE;
    }
}
