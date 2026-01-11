package kotlin;

import kotlin.jvm.internal.CharCompanionObject;

@Metadata(mo11628d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\b\n\u0002\u0010\f\n\u0002\b\u0006\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0000\u001a\u00020\u0001H\b\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, mo11629d2 = {"code", "", "", "getCode$annotations", "(C)V", "getCode", "(C)I", "Char", "kotlin-stdlib"}, mo11630k = 2, mo11631mv = {1, 5, 1})
/* compiled from: CharCode.kt */
public final class CharCodeKt {
    public static /* synthetic */ void getCode$annotations(char c) {
    }

    private static final char Char(int code) {
        if (code >= getCode(0) && code <= getCode(CharCompanionObject.MAX_VALUE)) {
            return (char) code;
        }
        throw new IllegalArgumentException("Invalid Char code: " + code);
    }

    /* access modifiers changed from: private */
    public static final int getCode(char $this$code) {
        return $this$code;
    }
}
