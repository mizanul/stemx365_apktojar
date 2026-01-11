package kotlin;

import kotlin.jvm.functions.Function1;

@Metadata(mo11628d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0002\u0010\u0007\u001a\u001f\u0010\b\u001a\u00020\u00012\n\u0010\t\u001a\u00020\u0001\"\u00020\u0006H\bø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000b\u0002\u000b\n\u0002\b\u0019\n\u0005\b20\u0001¨\u0006\f"}, mo11629d2 = {"UByteArray", "Lkotlin/UByteArray;", "size", "", "init", "Lkotlin/Function1;", "Lkotlin/UByte;", "(ILkotlin/jvm/functions/Function1;)[B", "ubyteArrayOf", "elements", "ubyteArrayOf-GBYM_sE", "([B)[B", "kotlin-stdlib"}, mo11630k = 2, mo11631mv = {1, 5, 1})
/* compiled from: UByteArray.kt */
public final class UByteArrayKt {
    private static final byte[] UByteArray(int size, Function1<? super Integer, UByte> init) {
        byte[] bArr = new byte[size];
        for (int index = 0; index < size; index++) {
            bArr[index] = init.invoke(Integer.valueOf(index)).m255unboximpl();
        }
        return UByteArray.m258constructorimpl(bArr);
    }

    /* renamed from: ubyteArrayOf-GBYM_sE  reason: not valid java name */
    private static final byte[] m274ubyteArrayOfGBYM_sE(byte... elements) {
        return elements;
    }
}
