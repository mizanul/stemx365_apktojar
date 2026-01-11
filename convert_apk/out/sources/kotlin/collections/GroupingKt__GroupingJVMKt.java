package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(mo11628d1 = {"\u0000&\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0000\u001a0\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u0005H\u0007\u001aZ\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\t\"\u0004\b\u0002\u0010\b*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\u00072\u001e\u0010\n\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\f\u0012\u0004\u0012\u0002H\b0\u000bH\bø\u0001\u0000\u0002\u0007\n\u0005\b20\u0001¨\u0006\r"}, mo11629d2 = {"eachCount", "", "K", "", "T", "Lkotlin/collections/Grouping;", "mapValuesInPlace", "", "R", "V", "f", "Lkotlin/Function1;", "", "kotlin-stdlib"}, mo11630k = 5, mo11631mv = {1, 5, 1}, mo11633xi = 1, mo11634xs = "kotlin/collections/GroupingKt")
/* compiled from: GroupingJVM.kt */
class GroupingKt__GroupingJVMKt {
    public static final <T, K> Map<K, Integer> eachCount(Grouping<T, ? extends K> $this$eachCount) {
        Object obj;
        Intrinsics.checkNotNullParameter($this$eachCount, "$this$eachCount");
        Map destination$iv = new LinkedHashMap();
        Grouping $this$foldTo$iv = $this$eachCount;
        Grouping $this$aggregateTo$iv$iv = $this$foldTo$iv;
        Iterator<T> sourceIterator = $this$aggregateTo$iv$iv.sourceIterator();
        while (sourceIterator.hasNext()) {
            T next = sourceIterator.next();
            Object keyOf = $this$aggregateTo$iv$iv.keyOf(next);
            Object accumulator$iv$iv = destination$iv.get(keyOf);
            Object acc$iv = accumulator$iv$iv;
            Object obj2 = keyOf;
            T t = next;
            if (accumulator$iv$iv == null && !destination$iv.containsKey(keyOf)) {
                T t2 = t;
                Object obj3 = obj2;
                obj = new Ref.IntRef();
            } else {
                obj = acc$iv;
            }
            Ref.IntRef acc = (Ref.IntRef) obj;
            Object obj4 = obj2;
            T t3 = t;
            acc.element++;
            destination$iv.put(keyOf, acc);
            Grouping<T, ? extends K> grouping = $this$eachCount;
            $this$foldTo$iv = $this$foldTo$iv;
        }
        for (Map.Entry it : destination$iv.entrySet()) {
            if (it != null) {
                TypeIntrinsics.asMutableMapEntry(it).setValue(Integer.valueOf(((Ref.IntRef) it.getValue()).element));
            } else {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
            }
        }
        return TypeIntrinsics.asMutableMap(destination$iv);
    }

    private static final <K, V, R> Map<K, R> mapValuesInPlace(Map<K, V> $this$mapValuesInPlace, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> f) {
        for (Map.Entry it : $this$mapValuesInPlace.entrySet()) {
            if (it != null) {
                TypeIntrinsics.asMutableMapEntry(it).setValue(f.invoke(it));
            } else {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
            }
        }
        if ($this$mapValuesInPlace != null) {
            return TypeIntrinsics.asMutableMap($this$mapValuesInPlace);
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, R>");
    }
}
