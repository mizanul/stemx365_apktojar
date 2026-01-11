package kotlin.text.jdk8;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchGroup;
import kotlin.text.MatchGroupCollection;
import kotlin.text.MatchNamedGroupCollection;

@Metadata(mo11628d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002¨\u0006\u0005"}, mo11629d2 = {"get", "Lkotlin/text/MatchGroup;", "Lkotlin/text/MatchGroupCollection;", "name", "", "kotlin-stdlib-jdk8"}, mo11630k = 2, mo11631mv = {1, 5, 1}, mo11632pn = "kotlin.text")
/* compiled from: RegexExtensions.kt */
public final class RegexExtensionsJDK8Kt {
    public static final MatchGroup get(MatchGroupCollection $this$get, String name) {
        Intrinsics.checkNotNullParameter($this$get, "$this$get");
        Intrinsics.checkNotNullParameter(name, "name");
        MatchNamedGroupCollection namedGroups = (MatchNamedGroupCollection) (!($this$get instanceof MatchNamedGroupCollection) ? null : $this$get);
        if (namedGroups != null) {
            return namedGroups.get(name);
        }
        throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
    }
}
