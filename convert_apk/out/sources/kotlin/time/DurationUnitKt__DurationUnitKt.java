package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.DurationUnitKt;

@Metadata(mo11628d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u0003H\u0001¨\u0006\u0004"}, mo11629d2 = {"shortName", "", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "kotlin-stdlib"}, mo11630k = 5, mo11631mv = {1, 5, 1}, mo11633xi = 1, mo11634xs = "kotlin/time/DurationUnitKt")
/* compiled from: DurationUnit.kt */
class DurationUnitKt__DurationUnitKt extends DurationUnitKt__DurationUnitJvmKt {
    public static final String shortName(TimeUnit $this$shortName) {
        Intrinsics.checkNotNullParameter($this$shortName, "$this$shortName");
        switch (DurationUnitKt.WhenMappings.$EnumSwitchMapping$0[$this$shortName.ordinal()]) {
            case 1:
                return "ns";
            case 2:
                return "us";
            case 3:
                return "ms";
            case 4:
                return "s";
            case 5:
                return "m";
            case 6:
                return "h";
            case 7:
                return "d";
            default:
                throw new IllegalStateException(("Unknown unit: " + $this$shortName).toString());
        }
    }
}
