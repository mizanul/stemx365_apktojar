package kotlin;

import kotlin.jvm.functions.Function0;

@Metadata(mo11628d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\b\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\bø\u0001\u0000\u0002\u0007\n\u0005\b20\u0001¨\u0006\u0007"}, mo11629d2 = {"assert", "", "value", "", "lazyMessage", "Lkotlin/Function0;", "", "kotlin-stdlib"}, mo11630k = 5, mo11631mv = {1, 5, 1}, mo11633xi = 1, mo11634xs = "kotlin/PreconditionsKt")
/* compiled from: AssertionsJVM.kt */
class PreconditionsKt__AssertionsJVMKt {
    /* renamed from: assert  reason: not valid java name */
    private static final void m185assert(boolean value) {
        if (_Assertions.ENABLED && !value) {
            throw new AssertionError("Assertion failed");
        }
    }

    /* renamed from: assert  reason: not valid java name */
    private static final void m186assert(boolean value, Function0<? extends Object> lazyMessage) {
        if (_Assertions.ENABLED && !value) {
            throw new AssertionError(lazyMessage.invoke());
        }
    }
}
