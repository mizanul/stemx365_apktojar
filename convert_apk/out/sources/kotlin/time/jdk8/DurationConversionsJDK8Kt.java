package kotlin.time.jdk8;

import java.time.Duration;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11628d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u0015\u0010\u0005\u001a\u00020\u0002*\u00020\u0001H\bø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u0007"}, mo11629d2 = {"toJavaDuration", "Ljava/time/Duration;", "Lkotlin/time/Duration;", "toJavaDuration-LRDsOJo", "(J)Ljava/time/Duration;", "toKotlinDuration", "(Ljava/time/Duration;)J", "kotlin-stdlib-jdk8"}, mo11630k = 2, mo11631mv = {1, 5, 1}, mo11632pn = "kotlin.time")
/* compiled from: DurationConversions.kt */
public final class DurationConversionsJDK8Kt {
    private static final long toKotlinDuration(Duration $this$toKotlinDuration) {
        return kotlin.time.Duration.m1518plusLRDsOJo(kotlin.time.Duration.Companion.m1561secondsUwyO8pc($this$toKotlinDuration.getSeconds()), kotlin.time.Duration.Companion.m1557nanosecondsUwyO8pc($this$toKotlinDuration.getNano()));
    }

    /* renamed from: toJavaDuration-LRDsOJo  reason: not valid java name */
    private static final Duration m1573toJavaDurationLRDsOJo(long $this$toJavaDuration) {
        long $this$iv = $this$toJavaDuration;
        Duration ofSeconds = Duration.ofSeconds(kotlin.time.Duration.m1503getInWholeSecondsimpl($this$iv), (long) kotlin.time.Duration.m1505getNanosecondsComponentimpl($this$iv));
        Intrinsics.checkNotNullExpressionValue(ofSeconds, "toComponents { seconds, …, nanoseconds.toLong()) }");
        return ofSeconds;
    }
}
