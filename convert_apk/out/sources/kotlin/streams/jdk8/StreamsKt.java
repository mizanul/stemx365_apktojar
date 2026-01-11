package kotlin.streams.jdk8;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(mo11628d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00040\u0001*\u00020\u0005H\u0007\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001*\u00020\u0007H\u0007\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\b0\u0001\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\tH\u0007\u001a\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\b0\t\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u0001H\u0007\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\f*\u00020\u0003H\u0007\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\f*\u00020\u0005H\u0007\u001a\u0012\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f*\u00020\u0007H\u0007\u001a\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\b0\f\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\tH\u0007¨\u0006\r"}, mo11629d2 = {"asSequence", "Lkotlin/sequences/Sequence;", "", "Ljava/util/stream/DoubleStream;", "", "Ljava/util/stream/IntStream;", "", "Ljava/util/stream/LongStream;", "T", "Ljava/util/stream/Stream;", "asStream", "toList", "", "kotlin-stdlib-jdk8"}, mo11630k = 2, mo11631mv = {1, 5, 1}, mo11632pn = "kotlin.streams")
/* compiled from: Streams.kt */
public final class StreamsKt {
    public static final <T> Sequence<T> asSequence(Stream<T> $this$asSequence) {
        Intrinsics.checkNotNullParameter($this$asSequence, "$this$asSequence");
        return new StreamsKt$asSequence$$inlined$Sequence$1($this$asSequence);
    }

    public static final Sequence<Integer> asSequence(IntStream $this$asSequence) {
        Intrinsics.checkNotNullParameter($this$asSequence, "$this$asSequence");
        return new StreamsKt$asSequence$$inlined$Sequence$2($this$asSequence);
    }

    public static final Sequence<Long> asSequence(LongStream $this$asSequence) {
        Intrinsics.checkNotNullParameter($this$asSequence, "$this$asSequence");
        return new StreamsKt$asSequence$$inlined$Sequence$3($this$asSequence);
    }

    public static final Sequence<Double> asSequence(DoubleStream $this$asSequence) {
        Intrinsics.checkNotNullParameter($this$asSequence, "$this$asSequence");
        return new StreamsKt$asSequence$$inlined$Sequence$4($this$asSequence);
    }

    public static final <T> Stream<T> asStream(Sequence<? extends T> $this$asStream) {
        Intrinsics.checkNotNullParameter($this$asStream, "$this$asStream");
        Stream<T> stream = StreamSupport.stream(new StreamsKt$asStream$1($this$asStream), 16, false);
        Intrinsics.checkNotNullExpressionValue(stream, "StreamSupport.stream({ S…literator.ORDERED, false)");
        return stream;
    }

    public static final <T> List<T> toList(Stream<T> $this$toList) {
        Intrinsics.checkNotNullParameter($this$toList, "$this$toList");
        Object collect = $this$toList.collect(Collectors.toList());
        Intrinsics.checkNotNullExpressionValue(collect, "collect(Collectors.toList<T>())");
        return (List) collect;
    }

    public static final List<Integer> toList(IntStream $this$toList) {
        Intrinsics.checkNotNullParameter($this$toList, "$this$toList");
        int[] array = $this$toList.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray()");
        return ArraysKt.asList(array);
    }

    public static final List<Long> toList(LongStream $this$toList) {
        Intrinsics.checkNotNullParameter($this$toList, "$this$toList");
        long[] array = $this$toList.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray()");
        return ArraysKt.asList(array);
    }

    public static final List<Double> toList(DoubleStream $this$toList) {
        Intrinsics.checkNotNullParameter($this$toList, "$this$toList");
        double[] array = $this$toList.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray()");
        return ArraysKt.asList(array);
    }
}
