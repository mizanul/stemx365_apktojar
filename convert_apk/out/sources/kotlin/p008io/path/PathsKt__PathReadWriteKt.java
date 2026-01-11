package kotlin.p008io.path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p008io.CloseableKt;
import kotlin.p008io.TextStreamsKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;

@Metadata(mo11628d1 = {"\u0000\u0001\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\b\u001a%\u0010\u0005\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\nH\b\u001a%\u0010\u0005\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u000b2\b\b\u0002\u0010\t\u001a\u00020\nH\b\u001a\u001e\u0010\f\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a:\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\b¢\u0006\u0002\u0010\u0015\u001a:\u0010\u0016\u001a\u00020\u0017*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\b¢\u0006\u0002\u0010\u0018\u001a=\u0010\u0019\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2!\u0010\u001a\u001a\u001d\u0012\u0013\u0012\u00110\u001c¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u00010\u001bH\bø\u0001\u0000\u001a&\u0010 \u001a\u00020!*\u00020\u00022\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\b¢\u0006\u0002\u0010\"\u001a&\u0010#\u001a\u00020$*\u00020\u00022\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\b¢\u0006\u0002\u0010%\u001a\r\u0010&\u001a\u00020\u0004*\u00020\u0002H\b\u001a\u001d\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001c0(*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\b\u001a\u0016\u0010)\u001a\u00020\u001c*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a0\u0010*\u001a\u00020+*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\b¢\u0006\u0002\u0010,\u001a?\u0010-\u001a\u0002H.\"\u0004\b\u0000\u0010.*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\u0018\u0010/\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u000b\u0012\u0004\u0012\u0002H.0\u001bH\bø\u0001\u0000¢\u0006\u0002\u00100\u001a.\u00101\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\b¢\u0006\u0002\u00102\u001a>\u00103\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\b¢\u0006\u0002\u00104\u001a>\u00103\u001a\u00020\u0002*\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u000b2\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\b¢\u0006\u0002\u00105\u001a7\u00106\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\u0007¢\u0006\u0002\u00107\u001a0\u00108\u001a\u000209*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\u0012\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u0013\"\u00020\u0014H\b¢\u0006\u0002\u0010:\u0002\u0007\n\u0005\b20\u0001¨\u0006;"}, mo11629d2 = {"appendBytes", "", "Ljava/nio/file/Path;", "array", "", "appendLines", "lines", "", "", "charset", "Ljava/nio/charset/Charset;", "Lkotlin/sequences/Sequence;", "appendText", "text", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "options", "", "Ljava/nio/file/OpenOption;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;I[Ljava/nio/file/OpenOption;)Ljava/io/BufferedReader;", "bufferedWriter", "Ljava/io/BufferedWriter;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;I[Ljava/nio/file/OpenOption;)Ljava/io/BufferedWriter;", "forEachLine", "action", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "line", "inputStream", "Ljava/io/InputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/InputStream;", "outputStream", "Ljava/io/OutputStream;", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/OutputStream;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/io/InputStreamReader;", "useLines", "T", "block", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "(Ljava/nio/file/Path;[B[Ljava/nio/file/OpenOption;)V", "writeLines", "(Ljava/nio/file/Path;Ljava/lang/Iterable;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;", "(Ljava/nio/file/Path;Lkotlin/sequences/Sequence;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/nio/file/Path;", "writeText", "(Ljava/nio/file/Path;Ljava/lang/CharSequence;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)V", "writer", "Ljava/io/OutputStreamWriter;", "(Ljava/nio/file/Path;Ljava/nio/charset/Charset;[Ljava/nio/file/OpenOption;)Ljava/io/OutputStreamWriter;", "kotlin-stdlib-jdk7"}, mo11630k = 5, mo11631mv = {1, 5, 1}, mo11633xi = 1, mo11634xs = "kotlin/io/path/PathsKt")
/* renamed from: kotlin.io.path.PathsKt__PathReadWriteKt */
/* compiled from: PathReadWrite.kt */
class PathsKt__PathReadWriteKt {
    static /* synthetic */ InputStreamReader reader$default(Path $this$reader, Charset charset, OpenOption[] options, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return new InputStreamReader(Files.newInputStream($this$reader, (OpenOption[]) Arrays.copyOf(options, options.length)), charset);
    }

    private static final InputStreamReader reader(Path $this$reader, Charset charset, OpenOption... options) throws IOException {
        return new InputStreamReader(Files.newInputStream($this$reader, (OpenOption[]) Arrays.copyOf(options, options.length)), charset);
    }

    static /* synthetic */ BufferedReader bufferedReader$default(Path $this$bufferedReader, Charset charset, int bufferSize, OpenOption[] options, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((i & 2) != 0) {
            bufferSize = 8192;
        }
        return new BufferedReader(new InputStreamReader(Files.newInputStream($this$bufferedReader, (OpenOption[]) Arrays.copyOf(options, options.length)), charset), bufferSize);
    }

    private static final BufferedReader bufferedReader(Path $this$bufferedReader, Charset charset, int bufferSize, OpenOption... options) throws IOException {
        return new BufferedReader(new InputStreamReader(Files.newInputStream($this$bufferedReader, (OpenOption[]) Arrays.copyOf(options, options.length)), charset), bufferSize);
    }

    static /* synthetic */ OutputStreamWriter writer$default(Path $this$writer, Charset charset, OpenOption[] options, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return new OutputStreamWriter(Files.newOutputStream($this$writer, (OpenOption[]) Arrays.copyOf(options, options.length)), charset);
    }

    private static final OutputStreamWriter writer(Path $this$writer, Charset charset, OpenOption... options) throws IOException {
        return new OutputStreamWriter(Files.newOutputStream($this$writer, (OpenOption[]) Arrays.copyOf(options, options.length)), charset);
    }

    static /* synthetic */ BufferedWriter bufferedWriter$default(Path $this$bufferedWriter, Charset charset, int bufferSize, OpenOption[] options, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((i & 2) != 0) {
            bufferSize = 8192;
        }
        return new BufferedWriter(new OutputStreamWriter(Files.newOutputStream($this$bufferedWriter, (OpenOption[]) Arrays.copyOf(options, options.length)), charset), bufferSize);
    }

    private static final BufferedWriter bufferedWriter(Path $this$bufferedWriter, Charset charset, int bufferSize, OpenOption... options) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(Files.newOutputStream($this$bufferedWriter, (OpenOption[]) Arrays.copyOf(options, options.length)), charset), bufferSize);
    }

    private static final byte[] readBytes(Path $this$readBytes) throws IOException {
        byte[] readAllBytes = Files.readAllBytes($this$readBytes);
        Intrinsics.checkNotNullExpressionValue(readAllBytes, "Files.readAllBytes(this)");
        return readAllBytes;
    }

    private static final void writeBytes(Path $this$writeBytes, byte[] array, OpenOption... options) throws IOException {
        Files.write($this$writeBytes, array, (OpenOption[]) Arrays.copyOf(options, options.length));
    }

    private static final void appendBytes(Path $this$appendBytes, byte[] array) throws IOException {
        Files.write($this$appendBytes, array, new OpenOption[]{StandardOpenOption.APPEND});
    }

    public static /* synthetic */ String readText$default(Path path, Charset charset, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return PathsKt.readText(path, charset);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0036, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0032, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0033, code lost:
        kotlin.p008io.CloseableKt.closeFinally(r2, r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String readText(java.nio.file.Path r5, java.nio.charset.Charset r6) throws java.io.IOException {
        /*
            java.lang.String r0 = "$this$readText"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.lang.String r0 = "charset"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            r0 = 0
            java.nio.file.OpenOption[] r1 = new java.nio.file.OpenOption[r0]
            java.io.InputStreamReader r2 = new java.io.InputStreamReader
            java.lang.Object[] r0 = java.util.Arrays.copyOf(r1, r0)
            java.nio.file.OpenOption[] r0 = (java.nio.file.OpenOption[]) r0
            java.io.InputStream r0 = java.nio.file.Files.newInputStream(r5, r0)
            r2.<init>(r0, r6)
            java.io.Closeable r2 = (java.io.Closeable) r2
            r0 = 0
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            r1 = r2
            java.io.InputStreamReader r1 = (java.io.InputStreamReader) r1     // Catch:{ all -> 0x0030 }
            r3 = 0
            r4 = r1
            java.io.Reader r4 = (java.io.Reader) r4     // Catch:{ all -> 0x0030 }
            java.lang.String r4 = kotlin.p008io.TextStreamsKt.readText(r4)     // Catch:{ all -> 0x0030 }
            kotlin.p008io.CloseableKt.closeFinally(r2, r0)
            return r4
        L_0x0030:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x0032 }
        L_0x0032:
            r1 = move-exception
            kotlin.p008io.CloseableKt.closeFinally(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.path.PathsKt__PathReadWriteKt.readText(java.nio.file.Path, java.nio.charset.Charset):java.lang.String");
    }

    public static /* synthetic */ void writeText$default(Path path, CharSequence charSequence, Charset charset, OpenOption[] openOptionArr, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        PathsKt.writeText(path, charSequence, charset, openOptionArr);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0040, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x003c, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003d, code lost:
        kotlin.p008io.CloseableKt.closeFinally(r1, r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void writeText(java.nio.file.Path r4, java.lang.CharSequence r5, java.nio.charset.Charset r6, java.nio.file.OpenOption... r7) throws java.io.IOException {
        /*
            java.lang.String r0 = "$this$writeText"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.lang.String r0 = "text"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.lang.String r0 = "charset"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            java.lang.String r0 = "options"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            int r0 = r7.length
            java.lang.Object[] r0 = java.util.Arrays.copyOf(r7, r0)
            java.nio.file.OpenOption[] r0 = (java.nio.file.OpenOption[]) r0
            java.io.OutputStream r0 = java.nio.file.Files.newOutputStream(r4, r0)
            java.lang.String r1 = "Files.newOutputStream(this, *options)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.io.OutputStreamWriter r1 = new java.io.OutputStreamWriter
            r1.<init>(r0, r6)
            java.io.Closeable r1 = (java.io.Closeable) r1
            r0 = 0
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            r2 = r1
            java.io.OutputStreamWriter r2 = (java.io.OutputStreamWriter) r2     // Catch:{ all -> 0x003a }
            r3 = 0
            r2.append(r5)     // Catch:{ all -> 0x003a }
            kotlin.p008io.CloseableKt.closeFinally(r1, r0)
            return
        L_0x003a:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x003c }
        L_0x003c:
            r2 = move-exception
            kotlin.p008io.CloseableKt.closeFinally(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.path.PathsKt__PathReadWriteKt.writeText(java.nio.file.Path, java.lang.CharSequence, java.nio.charset.Charset, java.nio.file.OpenOption[]):void");
    }

    public static /* synthetic */ void appendText$default(Path path, CharSequence charSequence, Charset charset, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        PathsKt.appendText(path, charSequence, charset);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003e, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x003a, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003b, code lost:
        kotlin.p008io.CloseableKt.closeFinally(r1, r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void appendText(java.nio.file.Path r4, java.lang.CharSequence r5, java.nio.charset.Charset r6) throws java.io.IOException {
        /*
            java.lang.String r0 = "$this$appendText"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.lang.String r0 = "text"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.lang.String r0 = "charset"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            r0 = 1
            java.nio.file.OpenOption[] r0 = new java.nio.file.OpenOption[r0]
            java.nio.file.StandardOpenOption r1 = java.nio.file.StandardOpenOption.APPEND
            java.nio.file.OpenOption r1 = (java.nio.file.OpenOption) r1
            r2 = 0
            r0[r2] = r1
            java.io.OutputStream r0 = java.nio.file.Files.newOutputStream(r4, r0)
            java.lang.String r1 = "Files.newOutputStream(th…tandardOpenOption.APPEND)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            java.io.OutputStreamWriter r1 = new java.io.OutputStreamWriter
            r1.<init>(r0, r6)
            java.io.Closeable r1 = (java.io.Closeable) r1
            r0 = 0
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            r2 = r1
            java.io.OutputStreamWriter r2 = (java.io.OutputStreamWriter) r2     // Catch:{ all -> 0x0038 }
            r3 = 0
            r2.append(r5)     // Catch:{ all -> 0x0038 }
            kotlin.p008io.CloseableKt.closeFinally(r1, r0)
            return
        L_0x0038:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x003a }
        L_0x003a:
            r2 = move-exception
            kotlin.p008io.CloseableKt.closeFinally(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.path.PathsKt__PathReadWriteKt.appendText(java.nio.file.Path, java.lang.CharSequence, java.nio.charset.Charset):void");
    }

    static /* synthetic */ void forEachLine$default(Path $this$forEachLine, Charset charset, Function1 action, int i, Object obj) throws IOException {
        Throwable th;
        Throwable th2;
        BufferedReader newBufferedReader = Files.newBufferedReader($this$forEachLine, (i & 1) != 0 ? Charsets.UTF_8 : charset);
        Intrinsics.checkNotNullExpressionValue(newBufferedReader, "Files.newBufferedReader(this, charset)");
        Reader $this$useLines$iv = newBufferedReader;
        Closeable bufferedReader = $this$useLines$iv instanceof BufferedReader ? (BufferedReader) $this$useLines$iv : new BufferedReader($this$useLines$iv, 8192);
        Throwable th3 = null;
        try {
            for (Object element$iv : TextStreamsKt.lineSequence((BufferedReader) bufferedReader)) {
                try {
                    action.invoke(element$iv);
                } catch (Throwable th4) {
                    th = th4;
                    th = th;
                    try {
                        throw th;
                    } catch (Throwable th5) {
                    }
                }
            }
            Function1 function1 = action;
            Unit unit = Unit.INSTANCE;
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                CloseableKt.closeFinally(bufferedReader, th3);
            } else {
                bufferedReader.close();
            }
            InlineMarker.finallyEnd(1);
            return;
        } catch (Throwable th6) {
            th = th6;
            Function1 function12 = action;
            th = th;
            throw th;
        }
        InlineMarker.finallyEnd(1);
        throw th2;
    }

    private static final void forEachLine(Path $this$forEachLine, Charset charset, Function1<? super String, Unit> action) throws IOException {
        Throwable th;
        Throwable th2;
        BufferedReader newBufferedReader = Files.newBufferedReader($this$forEachLine, charset);
        Intrinsics.checkNotNullExpressionValue(newBufferedReader, "Files.newBufferedReader(this, charset)");
        Reader $this$useLines$iv = newBufferedReader;
        Closeable bufferedReader = $this$useLines$iv instanceof BufferedReader ? (BufferedReader) $this$useLines$iv : new BufferedReader($this$useLines$iv, 8192);
        Throwable th3 = null;
        try {
            for (Object element$iv : TextStreamsKt.lineSequence((BufferedReader) bufferedReader)) {
                try {
                    action.invoke(element$iv);
                } catch (Throwable th4) {
                    th = th4;
                    th = th;
                    try {
                        throw th;
                    } catch (Throwable th5) {
                    }
                }
            }
            Function1<? super String, Unit> function1 = action;
            Unit unit = Unit.INSTANCE;
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                CloseableKt.closeFinally(bufferedReader, th3);
            } else {
                bufferedReader.close();
            }
            InlineMarker.finallyEnd(1);
            return;
        } catch (Throwable th6) {
            th = th6;
            Function1<? super String, Unit> function12 = action;
            th = th;
            throw th;
        }
        InlineMarker.finallyEnd(1);
        throw th2;
    }

    private static final InputStream inputStream(Path $this$inputStream, OpenOption... options) throws IOException {
        InputStream newInputStream = Files.newInputStream($this$inputStream, (OpenOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(newInputStream, "Files.newInputStream(this, *options)");
        return newInputStream;
    }

    private static final OutputStream outputStream(Path $this$outputStream, OpenOption... options) throws IOException {
        OutputStream newOutputStream = Files.newOutputStream($this$outputStream, (OpenOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(newOutputStream, "Files.newOutputStream(this, *options)");
        return newOutputStream;
    }

    static /* synthetic */ List readLines$default(Path $this$readLines, Charset charset, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        List<String> readAllLines = Files.readAllLines($this$readLines, charset);
        Intrinsics.checkNotNullExpressionValue(readAllLines, "Files.readAllLines(this, charset)");
        return readAllLines;
    }

    private static final List<String> readLines(Path $this$readLines, Charset charset) throws IOException {
        List<String> readAllLines = Files.readAllLines($this$readLines, charset);
        Intrinsics.checkNotNullExpressionValue(readAllLines, "Files.readAllLines(this, charset)");
        return readAllLines;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003b, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003c, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0043, code lost:
        if (kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0) == false) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0045, code lost:
        if (r0 != null) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004d, code lost:
        kotlin.p008io.CloseableKt.closeFinally(r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0050, code lost:
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0053, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object useLines$default(java.nio.file.Path r6, java.nio.charset.Charset r7, kotlin.jvm.functions.Function1 r8, int r9, java.lang.Object r10) throws java.io.IOException {
        /*
            r10 = 1
            r9 = r9 & r10
            if (r9 == 0) goto L_0x0006
            java.nio.charset.Charset r7 = kotlin.text.Charsets.UTF_8
        L_0x0006:
            r9 = 0
            java.io.BufferedReader r0 = java.nio.file.Files.newBufferedReader(r6, r7)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = 0
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            r2 = 0
            r3 = r0
            java.io.BufferedReader r3 = (java.io.BufferedReader) r3     // Catch:{ all -> 0x0039 }
            r4 = 0
            java.lang.String r5 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)     // Catch:{ all -> 0x0039 }
            kotlin.sequences.Sequence r5 = kotlin.p008io.TextStreamsKt.lineSequence(r3)     // Catch:{ all -> 0x0039 }
            java.lang.Object r5 = r8.invoke(r5)     // Catch:{ all -> 0x0039 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            boolean r2 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r10, r10, r2)
            if (r2 == 0) goto L_0x002f
            kotlin.p008io.CloseableKt.closeFinally(r0, r1)
            goto L_0x0035
        L_0x002f:
            if (r0 != 0) goto L_0x0032
            goto L_0x0035
        L_0x0032:
            r0.close()
        L_0x0035:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            return r5
        L_0x0039:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x003b }
        L_0x003b:
            r3 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            boolean r2 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r10, r10, r2)
            if (r2 != 0) goto L_0x004d
            if (r0 == 0) goto L_0x0050
            r0.close()     // Catch:{ all -> 0x004b }
            goto L_0x0050
        L_0x004b:
            r0 = move-exception
            goto L_0x0050
        L_0x004d:
            kotlin.p008io.CloseableKt.closeFinally(r0, r1)
        L_0x0050:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.path.PathsKt__PathReadWriteKt.useLines$default(java.nio.file.Path, java.nio.charset.Charset, kotlin.jvm.functions.Function1, int, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0037, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003e, code lost:
        if (kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0) == false) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0040, code lost:
        if (r1 != null) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0048, code lost:
        kotlin.p008io.CloseableKt.closeFinally(r1, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004b, code lost:
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004e, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final <T> T useLines(java.nio.file.Path r8, java.nio.charset.Charset r9, kotlin.jvm.functions.Function1<? super kotlin.sequences.Sequence<java.lang.String>, ? extends T> r10) throws java.io.IOException {
        /*
            r0 = 0
            java.io.BufferedReader r1 = java.nio.file.Files.newBufferedReader(r8, r9)
            java.io.Closeable r1 = (java.io.Closeable) r1
            r2 = 0
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r3 = 0
            r4 = 1
            r5 = r1
            java.io.BufferedReader r5 = (java.io.BufferedReader) r5     // Catch:{ all -> 0x0034 }
            r6 = 0
            java.lang.String r7 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r7)     // Catch:{ all -> 0x0034 }
            kotlin.sequences.Sequence r7 = kotlin.p008io.TextStreamsKt.lineSequence(r5)     // Catch:{ all -> 0x0034 }
            java.lang.Object r7 = r10.invoke(r7)     // Catch:{ all -> 0x0034 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            boolean r3 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r4, r4, r3)
            if (r3 == 0) goto L_0x002a
            kotlin.p008io.CloseableKt.closeFinally(r1, r2)
            goto L_0x0030
        L_0x002a:
            if (r1 != 0) goto L_0x002d
            goto L_0x0030
        L_0x002d:
            r1.close()
        L_0x0030:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            return r7
        L_0x0034:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0036 }
        L_0x0036:
            r5 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            boolean r3 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r4, r4, r3)
            if (r3 != 0) goto L_0x0048
            if (r1 == 0) goto L_0x004b
            r1.close()     // Catch:{ all -> 0x0046 }
            goto L_0x004b
        L_0x0046:
            r1 = move-exception
            goto L_0x004b
        L_0x0048:
            kotlin.p008io.CloseableKt.closeFinally(r1, r2)
        L_0x004b:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.path.PathsKt__PathReadWriteKt.useLines(java.nio.file.Path, java.nio.charset.Charset, kotlin.jvm.functions.Function1):java.lang.Object");
    }

    static /* synthetic */ Path writeLines$default(Path $this$writeLines, Iterable lines, Charset charset, OpenOption[] options, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        Path write = Files.write($this$writeLines, lines, charset, (OpenOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(write, "Files.write(this, lines, charset, *options)");
        return write;
    }

    private static final Path writeLines(Path $this$writeLines, Iterable<? extends CharSequence> lines, Charset charset, OpenOption... options) throws IOException {
        Path write = Files.write($this$writeLines, lines, charset, (OpenOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(write, "Files.write(this, lines, charset, *options)");
        return write;
    }

    static /* synthetic */ Path writeLines$default(Path $this$writeLines, Sequence lines, Charset charset, OpenOption[] options, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        Path write = Files.write($this$writeLines, SequencesKt.asIterable(lines), charset, (OpenOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(write, "Files.write(this, lines.…ble(), charset, *options)");
        return write;
    }

    private static final Path writeLines(Path $this$writeLines, Sequence<? extends CharSequence> lines, Charset charset, OpenOption... options) throws IOException {
        Path write = Files.write($this$writeLines, SequencesKt.asIterable(lines), charset, (OpenOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(write, "Files.write(this, lines.…ble(), charset, *options)");
        return write;
    }

    static /* synthetic */ Path appendLines$default(Path $this$appendLines, Iterable lines, Charset charset, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        Path write = Files.write($this$appendLines, lines, charset, new OpenOption[]{StandardOpenOption.APPEND});
        Intrinsics.checkNotNullExpressionValue(write, "Files.write(this, lines,…tandardOpenOption.APPEND)");
        return write;
    }

    private static final Path appendLines(Path $this$appendLines, Iterable<? extends CharSequence> lines, Charset charset) throws IOException {
        Path write = Files.write($this$appendLines, lines, charset, new OpenOption[]{StandardOpenOption.APPEND});
        Intrinsics.checkNotNullExpressionValue(write, "Files.write(this, lines,…tandardOpenOption.APPEND)");
        return write;
    }

    static /* synthetic */ Path appendLines$default(Path $this$appendLines, Sequence lines, Charset charset, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        Path write = Files.write($this$appendLines, SequencesKt.asIterable(lines), charset, new OpenOption[]{StandardOpenOption.APPEND});
        Intrinsics.checkNotNullExpressionValue(write, "Files.write(this, lines.…tandardOpenOption.APPEND)");
        return write;
    }

    private static final Path appendLines(Path $this$appendLines, Sequence<? extends CharSequence> lines, Charset charset) throws IOException {
        Path write = Files.write($this$appendLines, SequencesKt.asIterable(lines), charset, new OpenOption[]{StandardOpenOption.APPEND});
        Intrinsics.checkNotNullExpressionValue(write, "Files.write(this, lines.…tandardOpenOption.APPEND)");
        return write;
    }
}
