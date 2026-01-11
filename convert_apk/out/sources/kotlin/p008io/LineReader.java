package kotlin.p008io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11628d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0004H\u0002J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0010H\u0002J\u0018\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004H\u0002J\u0018\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020!H\u0002J\u0010\u0010#\u001a\u00020!2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u00060\u0012j\u0002`\u0013X\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, mo11629d2 = {"Lkotlin/io/LineReader;", "", "()V", "BUFFER_SIZE", "", "byteBuf", "Ljava/nio/ByteBuffer;", "bytes", "", "charBuf", "Ljava/nio/CharBuffer;", "chars", "", "decoder", "Ljava/nio/charset/CharsetDecoder;", "directEOL", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "compactBytes", "decode", "endOfInput", "decodeEndOfInput", "nBytes", "nChars", "readLine", "", "inputStream", "Ljava/io/InputStream;", "charset", "Ljava/nio/charset/Charset;", "resetAll", "", "trimStringBuilder", "updateCharset", "kotlin-stdlib"}, mo11630k = 1, mo11631mv = {1, 5, 1})
/* renamed from: kotlin.io.LineReader */
/* compiled from: Console.kt */
public final class LineReader {
    private static final int BUFFER_SIZE = 32;
    public static final LineReader INSTANCE = new LineReader();
    private static final ByteBuffer byteBuf;
    private static final byte[] bytes;
    private static final CharBuffer charBuf;
    private static final char[] chars = new char[32];
    /* access modifiers changed from: private */
    public static CharsetDecoder decoder;
    private static boolean directEOL;

    /* renamed from: sb */
    private static final StringBuilder f86sb = new StringBuilder();

    static {
        byte[] bArr = new byte[32];
        bytes = bArr;
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        Intrinsics.checkNotNullExpressionValue(wrap, "ByteBuffer.wrap(bytes)");
        byteBuf = wrap;
        CharBuffer wrap2 = CharBuffer.wrap(chars);
        Intrinsics.checkNotNullExpressionValue(wrap2, "CharBuffer.wrap(chars)");
        charBuf = wrap2;
    }

    private LineReader() {
    }

    public static final /* synthetic */ CharsetDecoder access$getDecoder$p(LineReader $this) {
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
        }
        return charsetDecoder;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003e, code lost:
        if (f86sb.length() != 0) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0040, code lost:
        r6 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0042, code lost:
        r6 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0043, code lost:
        if (r6 == false) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0045, code lost:
        if (r0 != 0) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0047, code lost:
        if (r2 != 0) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004b, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r2 = decodeEndOfInput(r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0022, code lost:
        if ((!kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r0.charset(), (java.lang.Object) r12)) != false) goto L_0x0024;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized java.lang.String readLine(java.io.InputStream r11, java.nio.charset.Charset r12) {
        /*
            r10 = this;
            monitor-enter(r10)
            java.lang.String r0 = "inputStream"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r11, r0)     // Catch:{ all -> 0x00e0 }
            java.lang.String r0 = "charset"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r12, r0)     // Catch:{ all -> 0x00e0 }
            java.nio.charset.CharsetDecoder r0 = decoder     // Catch:{ all -> 0x00e0 }
            r1 = 1
            if (r0 == 0) goto L_0x0024
            java.nio.charset.CharsetDecoder r0 = decoder     // Catch:{ all -> 0x00e0 }
            if (r0 != 0) goto L_0x0019
            java.lang.String r2 = "decoder"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)     // Catch:{ all -> 0x00e0 }
        L_0x0019:
            java.nio.charset.Charset r0 = r0.charset()     // Catch:{ all -> 0x00e0 }
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r0, (java.lang.Object) r12)     // Catch:{ all -> 0x00e0 }
            r0 = r0 ^ r1
            if (r0 == 0) goto L_0x0027
        L_0x0024:
            r10.updateCharset(r12)     // Catch:{ all -> 0x00e0 }
        L_0x0027:
            r0 = 0
            r2 = 0
        L_0x0029:
            int r3 = r11.read()     // Catch:{ all -> 0x00e0 }
            r4 = 32
            r5 = 10
            r6 = -1
            r7 = 0
            if (r3 != r6) goto L_0x0052
            java.lang.StringBuilder r6 = f86sb     // Catch:{ all -> 0x00e0 }
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6     // Catch:{ all -> 0x00e0 }
            int r6 = r6.length()     // Catch:{ all -> 0x00e0 }
            if (r6 != 0) goto L_0x0042
            r6 = r1
            goto L_0x0043
        L_0x0042:
            r6 = r7
        L_0x0043:
            if (r6 == 0) goto L_0x004c
            if (r0 != 0) goto L_0x004c
            if (r2 != 0) goto L_0x004c
            r1 = 0
            monitor-exit(r10)
            return r1
        L_0x004c:
            int r6 = r10.decodeEndOfInput(r0, r2)     // Catch:{ all -> 0x00e0 }
            r2 = r6
            goto L_0x0085
        L_0x0052:
            byte[] r6 = bytes     // Catch:{ all -> 0x00e0 }
            int r8 = r0 + 1
            byte r9 = (byte) r3     // Catch:{ all -> 0x00e0 }
            r6[r0] = r9     // Catch:{ all -> 0x00e0 }
            if (r3 == r5) goto L_0x0066
            if (r8 == r4) goto L_0x0066
            boolean r0 = directEOL     // Catch:{ all -> 0x00e0 }
            if (r0 != 0) goto L_0x0063
            goto L_0x0066
        L_0x0063:
            r0 = r8
            goto L_0x00de
        L_0x0066:
            java.nio.ByteBuffer r0 = byteBuf     // Catch:{ all -> 0x00e0 }
            r0.limit(r8)     // Catch:{ all -> 0x00e0 }
            java.nio.CharBuffer r0 = charBuf     // Catch:{ all -> 0x00e0 }
            r0.position(r2)     // Catch:{ all -> 0x00e0 }
            int r0 = r10.decode(r7)     // Catch:{ all -> 0x00e0 }
            r2 = r0
            if (r2 <= 0) goto L_0x00da
            char[] r0 = chars     // Catch:{ all -> 0x00e0 }
            int r6 = r2 + -1
            char r0 = r0[r6]     // Catch:{ all -> 0x00e0 }
            if (r0 != r5) goto L_0x00da
            java.nio.ByteBuffer r0 = byteBuf     // Catch:{ all -> 0x00e0 }
            r0.position(r7)     // Catch:{ all -> 0x00e0 }
            r0 = r8
        L_0x0085:
            if (r2 <= 0) goto L_0x009f
            char[] r3 = chars     // Catch:{ all -> 0x00e0 }
            int r6 = r2 + -1
            char r3 = r3[r6]     // Catch:{ all -> 0x00e0 }
            if (r3 != r5) goto L_0x009f
            int r2 = r2 + -1
            if (r2 <= 0) goto L_0x009f
            char[] r3 = chars     // Catch:{ all -> 0x00e0 }
            int r5 = r2 + -1
            char r3 = r3[r5]     // Catch:{ all -> 0x00e0 }
            r5 = 13
            if (r3 != r5) goto L_0x009f
            int r2 = r2 + -1
        L_0x009f:
            java.lang.StringBuilder r3 = f86sb     // Catch:{ all -> 0x00e0 }
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ all -> 0x00e0 }
            int r3 = r3.length()     // Catch:{ all -> 0x00e0 }
            if (r3 != 0) goto L_0x00aa
            goto L_0x00ab
        L_0x00aa:
            r1 = r7
        L_0x00ab:
            if (r1 == 0) goto L_0x00b6
            char[] r1 = chars     // Catch:{ all -> 0x00e0 }
            java.lang.String r3 = new java.lang.String     // Catch:{ all -> 0x00e0 }
            r3.<init>(r1, r7, r2)     // Catch:{ all -> 0x00e0 }
            monitor-exit(r10)
            return r3
        L_0x00b6:
            java.lang.StringBuilder r1 = f86sb     // Catch:{ all -> 0x00e0 }
            char[] r3 = chars     // Catch:{ all -> 0x00e0 }
            r1.append(r3, r7, r2)     // Catch:{ all -> 0x00e0 }
            java.lang.StringBuilder r1 = f86sb     // Catch:{ all -> 0x00e0 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00e0 }
            java.lang.String r3 = "sb.toString()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r3)     // Catch:{ all -> 0x00e0 }
            java.lang.StringBuilder r3 = f86sb     // Catch:{ all -> 0x00e0 }
            int r3 = r3.length()     // Catch:{ all -> 0x00e0 }
            if (r3 <= r4) goto L_0x00d3
            r10.trimStringBuilder()     // Catch:{ all -> 0x00e0 }
        L_0x00d3:
            java.lang.StringBuilder r3 = f86sb     // Catch:{ all -> 0x00e0 }
            r3.setLength(r7)     // Catch:{ all -> 0x00e0 }
            monitor-exit(r10)
            return r1
        L_0x00da:
            int r0 = r10.compactBytes()     // Catch:{ all -> 0x00e0 }
        L_0x00de:
            goto L_0x0029
        L_0x00e0:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.LineReader.readLine(java.io.InputStream, java.nio.charset.Charset):java.lang.String");
    }

    private final int decode(boolean endOfInput) {
        while (true) {
            CharsetDecoder charsetDecoder = decoder;
            if (charsetDecoder == null) {
                Intrinsics.throwUninitializedPropertyAccessException("decoder");
            }
            CoderResult coderResult = charsetDecoder.decode(byteBuf, charBuf, endOfInput);
            Intrinsics.checkNotNullExpressionValue(coderResult, "decoder.decode(byteBuf, charBuf, endOfInput)");
            if (coderResult.isError()) {
                resetAll();
                coderResult.throwException();
            }
            int nChars = charBuf.position();
            if (!coderResult.isOverflow()) {
                return nChars;
            }
            f86sb.append(chars, 0, nChars - 1);
            charBuf.position(0);
            charBuf.limit(32);
            charBuf.put(chars[nChars - 1]);
        }
    }

    private final int compactBytes() {
        ByteBuffer $this$with = byteBuf;
        $this$with.compact();
        int position = $this$with.position();
        int i = position;
        $this$with.position(0);
        return position;
    }

    private final int decodeEndOfInput(int nBytes, int nChars) {
        byteBuf.limit(nBytes);
        charBuf.position(nChars);
        int decode = decode(true);
        int i = decode;
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
        }
        charsetDecoder.reset();
        byteBuf.position(0);
        return decode;
    }

    private final void updateCharset(Charset charset) {
        CharsetDecoder newDecoder = charset.newDecoder();
        Intrinsics.checkNotNullExpressionValue(newDecoder, "charset.newDecoder()");
        decoder = newDecoder;
        byteBuf.clear();
        charBuf.clear();
        byteBuf.put((byte) 10);
        byteBuf.flip();
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
        }
        boolean z = false;
        charsetDecoder.decode(byteBuf, charBuf, false);
        if (charBuf.position() == 1 && charBuf.get(0) == 10) {
            z = true;
        }
        directEOL = z;
        resetAll();
    }

    private final void resetAll() {
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
        }
        charsetDecoder.reset();
        byteBuf.position(0);
        f86sb.setLength(0);
    }

    private final void trimStringBuilder() {
        f86sb.setLength(32);
        f86sb.trimToSize();
    }
}
