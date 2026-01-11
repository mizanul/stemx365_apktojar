package kotlin.sequences;

import java.util.Enumeration;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

@Metadata(mo11628d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001f\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\b¨\u0006\u0004"}, mo11629d2 = {"asSequence", "Lkotlin/sequences/Sequence;", "T", "Ljava/util/Enumeration;", "kotlin-stdlib"}, mo11630k = 5, mo11631mv = {1, 5, 1}, mo11633xi = 1, mo11634xs = "kotlin/sequences/SequencesKt")
/* compiled from: SequencesJVM.kt */
class SequencesKt__SequencesJVMKt extends SequencesKt__SequenceBuilderKt {
    private static final <T> Sequence<T> asSequence(Enumeration<T> $this$asSequence) {
        return SequencesKt.asSequence(CollectionsKt.iterator($this$asSequence));
    }
}
