package kotlin.text;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11628d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\b¨\u0006\u0004"}, mo11629d2 = {"charset", "Ljava/nio/charset/Charset;", "charsetName", "", "kotlin-stdlib"}, mo11630k = 2, mo11631mv = {1, 5, 1})
/* compiled from: Charsets.kt */
public final class CharsetsKt {
    private static final Charset charset(String charsetName) {
        Charset forName = Charset.forName(charsetName);
        Intrinsics.checkNotNullExpressionValue(forName, "Charset.forName(charsetName)");
        return forName;
    }
}
