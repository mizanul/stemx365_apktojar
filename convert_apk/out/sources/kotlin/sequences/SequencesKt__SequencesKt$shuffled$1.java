package kotlin.sequences;

import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;

@Metadata(mo11628d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo11629d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo11630k = 3, mo11631mv = {1, 5, 1})
@DebugMetadata(mo12350c = "kotlin.sequences.SequencesKt__SequencesKt$shuffled$1", mo12351f = "Sequences.kt", mo12352i = {}, mo12353l = {145}, mo12354m = "invokeSuspend", mo12355n = {}, mo12356s = {})
/* compiled from: Sequences.kt */
final class SequencesKt__SequencesKt$shuffled$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Random $random;
    final /* synthetic */ Sequence $this_shuffled;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt__SequencesKt$shuffled$1(Sequence sequence, Random random, Continuation continuation) {
        super(2, continuation);
        this.$this_shuffled = sequence;
        this.$random = random;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkNotNullParameter(continuation, "completion");
        SequencesKt__SequencesKt$shuffled$1 sequencesKt__SequencesKt$shuffled$1 = new SequencesKt__SequencesKt$shuffled$1(this.$this_shuffled, this.$random, continuation);
        sequencesKt__SequencesKt$shuffled$1.L$0 = obj;
        return sequencesKt__SequencesKt$shuffled$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SequencesKt__SequencesKt$shuffled$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        SequenceScope $this$sequence;
        List buffer;
        SequencesKt__SequencesKt$shuffled$1 sequencesKt__SequencesKt$shuffled$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            sequencesKt__SequencesKt$shuffled$1 = this;
            List mutableList = SequencesKt.toMutableList(sequencesKt__SequencesKt$shuffled$1.$this_shuffled);
            $this$sequence = (SequenceScope) sequencesKt__SequencesKt$shuffled$1.L$0;
            buffer = mutableList;
        } else if (i == 1) {
            sequencesKt__SequencesKt$shuffled$1 = this;
            buffer = (List) sequencesKt__SequencesKt$shuffled$1.L$1;
            $this$sequence = (SequenceScope) sequencesKt__SequencesKt$shuffled$1.L$0;
            ResultKt.throwOnFailure($result);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        while (!buffer.isEmpty()) {
            int j = sequencesKt__SequencesKt$shuffled$1.$random.nextInt(buffer.size());
            Object last = CollectionsKt.removeLast(buffer);
            Object value = j < buffer.size() ? buffer.set(j, last) : last;
            sequencesKt__SequencesKt$shuffled$1.L$0 = $this$sequence;
            sequencesKt__SequencesKt$shuffled$1.L$1 = buffer;
            sequencesKt__SequencesKt$shuffled$1.label = 1;
            if ($this$sequence.yield(value, sequencesKt__SequencesKt$shuffled$1) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return Unit.INSTANCE;
    }
}
