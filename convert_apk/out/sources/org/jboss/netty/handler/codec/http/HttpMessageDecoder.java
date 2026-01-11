package org.jboss.netty.handler.codec.http;

import java.util.List;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

public abstract class HttpMessageDecoder extends ReplayingDecoder<State> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long chunkSize;
    private ChannelBuffer content;
    private int contentRead;
    private int headerSize;
    private final int maxChunkSize;
    private final int maxHeaderSize;
    private final int maxInitialLineLength;
    private HttpMessage message;

    protected enum State {
        SKIP_CONTROL_CHARS,
        READ_INITIAL,
        READ_HEADER,
        READ_VARIABLE_LENGTH_CONTENT,
        READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS,
        READ_FIXED_LENGTH_CONTENT,
        READ_FIXED_LENGTH_CONTENT_AS_CHUNKS,
        READ_CHUNK_SIZE,
        READ_CHUNKED_CONTENT,
        READ_CHUNKED_CONTENT_AS_CHUNKS,
        READ_CHUNK_DELIMITER,
        READ_CHUNK_FOOTER
    }

    /* access modifiers changed from: protected */
    public abstract HttpMessage createMessage(String[] strArr) throws Exception;

    /* access modifiers changed from: protected */
    public abstract boolean isDecodingRequest();

    protected HttpMessageDecoder() {
        this(4096, 8192, 8192);
    }

    protected HttpMessageDecoder(int maxInitialLineLength2, int maxHeaderSize2, int maxChunkSize2) {
        super(State.SKIP_CONTROL_CHARS, true);
        if (maxInitialLineLength2 <= 0) {
            throw new IllegalArgumentException("maxInitialLineLength must be a positive integer: " + maxInitialLineLength2);
        } else if (maxHeaderSize2 <= 0) {
            throw new IllegalArgumentException("maxHeaderSize must be a positive integer: " + maxHeaderSize2);
        } else if (maxChunkSize2 >= 0) {
            this.maxInitialLineLength = maxInitialLineLength2;
            this.maxHeaderSize = maxHeaderSize2;
            this.maxChunkSize = maxChunkSize2;
        } else {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + maxChunkSize2);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0189, code lost:
        if (r1 != 2) goto L_0x01a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0191, code lost:
        if (r14.readableBytes() > r11.maxChunkSize) goto L_0x019b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0199, code lost:
        if (org.jboss.netty.handler.codec.http.HttpHeaders.is100ContinueExpected(r11.message) == false) goto L_0x01cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x019b, code lost:
        checkpoint(org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS);
        r11.message.setChunked(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x01a7, code lost:
        return r11.message;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x01be, code lost:
        throw new java.lang.IllegalStateException("Unexpected state: " + r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01c4, code lost:
        if (r7 > ((long) r11.maxChunkSize)) goto L_0x01d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x01cc, code lost:
        if (org.jboss.netty.handler.codec.http.HttpHeaders.is100ContinueExpected(r11.message) == false) goto L_0x01cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x01cf, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x01d0, code lost:
        checkpoint(org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_FIXED_LENGTH_CONTENT_AS_CHUNKS);
        r11.message.setChunked(true);
        r11.chunkSize = org.jboss.netty.handler.codec.http.HttpHeaders.getContentLength(r11.message, -1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x01e4, code lost:
        return r11.message;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x01e5, code lost:
        r11.content = org.jboss.netty.buffer.ChannelBuffers.EMPTY_BUFFER;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x01ed, code lost:
        return reset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005c, code lost:
        r0 = r14.readByte();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0064, code lost:
        if (r0 != 13) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006a, code lost:
        if (r14.readByte() != 10) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006c, code lost:
        checkpoint(org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_CHUNK_SIZE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0071, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0072, code lost:
        if (r0 != 10) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0074, code lost:
        checkpoint(org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_CHUNK_SIZE);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0079, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x009f, code lost:
        r0 = new org.jboss.netty.handler.codec.http.DefaultHttpChunk(r14.readBytes((int) r11.chunkSize));
        checkpoint(org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_CHUNK_DELIMITER);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00b1, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0126, code lost:
        r0 = splitInitialLine(readLine(r14, r11.maxInitialLineLength));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0132, code lost:
        if (r0.length >= 3) goto L_0x013a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0134, code lost:
        checkpoint(org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.SKIP_CONTROL_CHARS);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0139, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x013a, code lost:
        r11.message = createMessage(r0);
        checkpoint(org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_HEADER);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0145, code lost:
        r0 = readHeaders(r14);
        checkpoint(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x014e, code lost:
        if (r0 != org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_CHUNK_SIZE) goto L_0x0158;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0150, code lost:
        r11.message.setChunked(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0157, code lost:
        return r11.message;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x015a, code lost:
        if (r0 != org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.SKIP_CONTROL_CHARS) goto L_0x0166;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x015c, code lost:
        r11.message.removeHeader("Transfer-Encoding");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0165, code lost:
        return r11.message;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0166, code lost:
        r7 = org.jboss.netty.handler.codec.http.HttpHeaders.getContentLength(r11.message, -1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0172, code lost:
        if (r7 == 0) goto L_0x01e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0176, code lost:
        if (r7 != -1) goto L_0x017f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x017c, code lost:
        if (isDecodingRequest() == false) goto L_0x017f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x017f, code lost:
        r1 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.C08781.f133x5907aa57[r0.ordinal()];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0187, code lost:
        if (r1 == 1) goto L_0x01bf;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object decode(org.jboss.netty.channel.ChannelHandlerContext r12, org.jboss.netty.channel.Channel r13, org.jboss.netty.buffer.ChannelBuffer r14, org.jboss.netty.handler.codec.http.HttpMessageDecoder.State r15) throws java.lang.Exception {
        /*
            r11 = this;
            int[] r0 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.C08781.f133x5907aa57
            int r1 = r15.ordinal()
            r0 = r0[r1]
            r1 = 0
            r2 = 2
            r3 = 0
            r4 = 1
            switch(r0) {
                case 1: goto L_0x0226;
                case 2: goto L_0x01f3;
                case 3: goto L_0x0119;
                case 4: goto L_0x0126;
                case 5: goto L_0x0145;
                case 6: goto L_0x00ed;
                case 7: goto L_0x00b2;
                case 8: goto L_0x007b;
                case 9: goto L_0x009f;
                case 10: goto L_0x0028;
                case 11: goto L_0x005c;
                case 12: goto L_0x0017;
                default: goto L_0x000f;
            }
        L_0x000f:
            java.lang.Error r0 = new java.lang.Error
            java.lang.String r1 = "Shouldn't reach here."
            r0.<init>(r1)
            throw r0
        L_0x0017:
            org.jboss.netty.handler.codec.http.HttpChunkTrailer r0 = r11.readTrailingHeaders(r14)
            int r1 = r11.maxChunkSize
            if (r1 != 0) goto L_0x0024
            java.lang.Object r1 = r11.reset()
            return r1
        L_0x0024:
            r11.reset()
            return r0
        L_0x0028:
            long r0 = r11.chunkSize
            int r0 = (int) r0
            int r1 = r11.actualReadableBytes()
            if (r1 != 0) goto L_0x0033
            return r3
        L_0x0033:
            r2 = r0
            int r4 = r11.maxChunkSize
            if (r2 <= r4) goto L_0x003a
            int r2 = r11.maxChunkSize
        L_0x003a:
            if (r2 <= r1) goto L_0x003d
            r2 = r1
        L_0x003d:
            org.jboss.netty.handler.codec.http.DefaultHttpChunk r4 = new org.jboss.netty.handler.codec.http.DefaultHttpChunk
            org.jboss.netty.buffer.ChannelBuffer r5 = r14.readBytes((int) r2)
            r4.<init>(r5)
            if (r0 <= r2) goto L_0x004a
            int r0 = r0 - r2
            goto L_0x004b
        L_0x004a:
            r0 = 0
        L_0x004b:
            long r5 = (long) r0
            r11.chunkSize = r5
            if (r0 != 0) goto L_0x0055
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r5 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_CHUNK_DELIMITER
            r11.checkpoint(r5)
        L_0x0055:
            boolean r5 = r4.isLast()
            if (r5 != 0) goto L_0x005c
            return r4
        L_0x005c:
            byte r0 = r14.readByte()
            r1 = 13
            r2 = 10
            if (r0 != r1) goto L_0x0072
            byte r1 = r14.readByte()
            if (r1 != r2) goto L_0x007a
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r1 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_CHUNK_SIZE
            r11.checkpoint(r1)
            return r3
        L_0x0072:
            if (r0 != r2) goto L_0x007a
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r1 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_CHUNK_SIZE
            r11.checkpoint(r1)
            return r3
        L_0x007a:
            goto L_0x005c
        L_0x007b:
            int r0 = r11.maxInitialLineLength
            java.lang.String r0 = readLine(r14, r0)
            int r1 = getChunkSize(r0)
            long r4 = (long) r1
            r11.chunkSize = r4
            if (r1 != 0) goto L_0x0090
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r2 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_CHUNK_FOOTER
            r11.checkpoint(r2)
            return r3
        L_0x0090:
            int r2 = r11.maxChunkSize
            if (r1 <= r2) goto L_0x009a
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r2 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_CHUNKED_CONTENT_AS_CHUNKS
            r11.checkpoint(r2)
            goto L_0x009f
        L_0x009a:
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r2 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_CHUNKED_CONTENT
            r11.checkpoint(r2)
        L_0x009f:
            org.jboss.netty.handler.codec.http.DefaultHttpChunk r0 = new org.jboss.netty.handler.codec.http.DefaultHttpChunk
            long r1 = r11.chunkSize
            int r1 = (int) r1
            org.jboss.netty.buffer.ChannelBuffer r1 = r14.readBytes((int) r1)
            r0.<init>(r1)
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r1 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_CHUNK_DELIMITER
            r11.checkpoint(r1)
            return r0
        L_0x00b2:
            long r5 = r11.chunkSize
            int r0 = (int) r5
            int r5 = r11.actualReadableBytes()
            if (r5 != 0) goto L_0x00bd
            return r3
        L_0x00bd:
            r3 = r0
            int r6 = r11.maxChunkSize
            if (r3 <= r6) goto L_0x00c4
            int r3 = r11.maxChunkSize
        L_0x00c4:
            if (r3 <= r5) goto L_0x00c7
            r3 = r5
        L_0x00c7:
            org.jboss.netty.handler.codec.http.DefaultHttpChunk r6 = new org.jboss.netty.handler.codec.http.DefaultHttpChunk
            org.jboss.netty.buffer.ChannelBuffer r7 = r14.readBytes((int) r3)
            r6.<init>(r7)
            if (r0 <= r3) goto L_0x00d4
            int r0 = r0 - r3
            goto L_0x00d5
        L_0x00d4:
            r0 = 0
        L_0x00d5:
            long r7 = (long) r0
            r11.chunkSize = r7
            if (r0 != 0) goto L_0x00ec
            r11.reset()
            boolean r7 = r6.isLast()
            if (r7 != 0) goto L_0x00ec
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r1] = r6
            org.jboss.netty.handler.codec.http.HttpChunkTrailer r1 = org.jboss.netty.handler.codec.http.HttpChunk.LAST_CHUNK
            r2[r4] = r1
            return r2
        L_0x00ec:
            return r6
        L_0x00ed:
            int r0 = r11.actualReadableBytes()
            int r3 = r11.maxChunkSize
            if (r0 <= r3) goto L_0x00f7
            int r0 = r11.maxChunkSize
        L_0x00f7:
            org.jboss.netty.handler.codec.http.DefaultHttpChunk r3 = new org.jboss.netty.handler.codec.http.DefaultHttpChunk
            org.jboss.netty.buffer.ChannelBuffer r5 = r14.readBytes((int) r0)
            r3.<init>(r5)
            boolean r5 = r14.readable()
            if (r5 != 0) goto L_0x0118
            r11.reset()
            boolean r5 = r3.isLast()
            if (r5 != 0) goto L_0x0118
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r1] = r3
            org.jboss.netty.handler.codec.http.HttpChunkTrailer r1 = org.jboss.netty.handler.codec.http.HttpChunk.LAST_CHUNK
            r2[r4] = r1
            return r2
        L_0x0118:
            return r3
        L_0x0119:
            skipControlCharacters(r14)     // Catch:{ all -> 0x01ee }
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r0 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_INITIAL     // Catch:{ all -> 0x01ee }
            r11.checkpoint(r0)     // Catch:{ all -> 0x01ee }
            r11.checkpoint()
        L_0x0126:
            int r0 = r11.maxInitialLineLength
            java.lang.String r0 = readLine(r14, r0)
            java.lang.String[] r0 = splitInitialLine(r0)
            int r1 = r0.length
            r5 = 3
            if (r1 >= r5) goto L_0x013a
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r1 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.SKIP_CONTROL_CHARS
            r11.checkpoint(r1)
            return r3
        L_0x013a:
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.createMessage(r0)
            r11.message = r1
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r1 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_HEADER
            r11.checkpoint(r1)
        L_0x0145:
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r0 = r11.readHeaders(r14)
            r11.checkpoint(r0)
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r1 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_CHUNK_SIZE
            if (r0 != r1) goto L_0x0158
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.message
            r1.setChunked(r4)
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.message
            return r1
        L_0x0158:
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r1 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.SKIP_CONTROL_CHARS
            if (r0 != r1) goto L_0x0166
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.message
            java.lang.String r2 = "Transfer-Encoding"
            r1.removeHeader(r2)
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.message
            return r1
        L_0x0166:
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.message
            r5 = -1
            long r7 = org.jboss.netty.handler.codec.http.HttpHeaders.getContentLength(r1, r5)
            r9 = 0
            int r1 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r1 == 0) goto L_0x01e5
            int r1 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x017f
            boolean r1 = r11.isDecodingRequest()
            if (r1 == 0) goto L_0x017f
            goto L_0x01e5
        L_0x017f:
            int[] r1 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.C08781.f133x5907aa57
            int r9 = r0.ordinal()
            r1 = r1[r9]
            if (r1 == r4) goto L_0x01bf
            if (r1 != r2) goto L_0x01a8
            int r1 = r14.readableBytes()
            int r2 = r11.maxChunkSize
            if (r1 > r2) goto L_0x019b
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.message
            boolean r1 = org.jboss.netty.handler.codec.http.HttpHeaders.is100ContinueExpected(r1)
            if (r1 == 0) goto L_0x01cf
        L_0x019b:
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r1 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS
            r11.checkpoint(r1)
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.message
            r1.setChunked(r4)
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.message
            return r1
        L_0x01a8:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unexpected state: "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x01bf:
            int r1 = r11.maxChunkSize
            long r1 = (long) r1
            int r1 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r1 > 0) goto L_0x01d0
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.message
            boolean r1 = org.jboss.netty.handler.codec.http.HttpHeaders.is100ContinueExpected(r1)
            if (r1 == 0) goto L_0x01cf
            goto L_0x01d0
        L_0x01cf:
            return r3
        L_0x01d0:
            org.jboss.netty.handler.codec.http.HttpMessageDecoder$State r1 = org.jboss.netty.handler.codec.http.HttpMessageDecoder.State.READ_FIXED_LENGTH_CONTENT_AS_CHUNKS
            r11.checkpoint(r1)
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.message
            r1.setChunked(r4)
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.message
            long r1 = org.jboss.netty.handler.codec.http.HttpHeaders.getContentLength(r1, r5)
            r11.chunkSize = r1
            org.jboss.netty.handler.codec.http.HttpMessage r1 = r11.message
            return r1
        L_0x01e5:
            org.jboss.netty.buffer.ChannelBuffer r1 = org.jboss.netty.buffer.ChannelBuffers.EMPTY_BUFFER
            r11.content = r1
            java.lang.Object r1 = r11.reset()
            return r1
        L_0x01ee:
            r0 = move-exception
            r11.checkpoint()
            throw r0
        L_0x01f3:
            int r0 = r11.actualReadableBytes()
            int r3 = r11.maxChunkSize
            if (r0 <= r3) goto L_0x01fd
            int r0 = r11.maxChunkSize
        L_0x01fd:
            org.jboss.netty.handler.codec.http.HttpMessage r3 = r11.message
            boolean r3 = r3.isChunked()
            if (r3 != 0) goto L_0x021c
            org.jboss.netty.handler.codec.http.HttpMessage r3 = r11.message
            r3.setChunked(r4)
            java.lang.Object[] r2 = new java.lang.Object[r2]
            org.jboss.netty.handler.codec.http.HttpMessage r3 = r11.message
            r2[r1] = r3
            org.jboss.netty.handler.codec.http.DefaultHttpChunk r1 = new org.jboss.netty.handler.codec.http.DefaultHttpChunk
            org.jboss.netty.buffer.ChannelBuffer r3 = r14.readBytes((int) r0)
            r1.<init>(r3)
            r2[r4] = r1
            return r2
        L_0x021c:
            org.jboss.netty.handler.codec.http.DefaultHttpChunk r1 = new org.jboss.netty.handler.codec.http.DefaultHttpChunk
            org.jboss.netty.buffer.ChannelBuffer r2 = r14.readBytes((int) r0)
            r1.<init>(r2)
            return r1
        L_0x0226:
            java.lang.Object r0 = r11.readFixedLengthContent(r14)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.handler.codec.http.HttpMessageDecoder.decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer, org.jboss.netty.handler.codec.http.HttpMessageDecoder$State):java.lang.Object");
    }

    /* renamed from: org.jboss.netty.handler.codec.http.HttpMessageDecoder$1 */
    static /* synthetic */ class C08781 {

        /* renamed from: $SwitchMap$org$jboss$netty$handler$codec$http$HttpMessageDecoder$State */
        static final /* synthetic */ int[] f133x5907aa57;

        static {
            int[] iArr = new int[State.values().length];
            f133x5907aa57 = iArr;
            try {
                iArr[State.READ_FIXED_LENGTH_CONTENT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f133x5907aa57[State.READ_VARIABLE_LENGTH_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f133x5907aa57[State.SKIP_CONTROL_CHARS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f133x5907aa57[State.READ_INITIAL.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f133x5907aa57[State.READ_HEADER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f133x5907aa57[State.READ_VARIABLE_LENGTH_CONTENT_AS_CHUNKS.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f133x5907aa57[State.READ_FIXED_LENGTH_CONTENT_AS_CHUNKS.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f133x5907aa57[State.READ_CHUNK_SIZE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f133x5907aa57[State.READ_CHUNKED_CONTENT.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                f133x5907aa57[State.READ_CHUNKED_CONTENT_AS_CHUNKS.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                f133x5907aa57[State.READ_CHUNK_DELIMITER.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                f133x5907aa57[State.READ_CHUNK_FOOTER.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isContentAlwaysEmpty(HttpMessage msg) {
        if (msg instanceof HttpResponse) {
            HttpResponse res = (HttpResponse) msg;
            int code = res.getStatus().getCode();
            if (code < 100 || code >= 200) {
                if (code == 204 || code == 205 || code == 304) {
                    return true;
                }
            } else if (code != 101 || res.containsHeader(HttpHeaders.Names.SEC_WEBSOCKET_ACCEPT)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private Object reset() {
        HttpMessage message2 = this.message;
        ChannelBuffer content2 = this.content;
        if (content2 != null) {
            message2.setContent(content2);
            this.content = null;
        }
        this.message = null;
        checkpoint(State.SKIP_CONTROL_CHARS);
        return message2;
    }

    private static void skipControlCharacters(ChannelBuffer buffer) {
        while (true) {
            char c = (char) buffer.readUnsignedByte();
            if (!Character.isISOControl(c) && !Character.isWhitespace(c)) {
                buffer.readerIndex(buffer.readerIndex() - 1);
                return;
            }
        }
    }

    private Object readFixedLengthContent(ChannelBuffer buffer) {
        long length = HttpHeaders.getContentLength(this.message, -1);
        int toRead = ((int) length) - this.contentRead;
        if (toRead > actualReadableBytes()) {
            toRead = actualReadableBytes();
        }
        int i = this.contentRead + toRead;
        this.contentRead = i;
        if (length >= ((long) i)) {
            ChannelBuffer channelBuffer = this.content;
            if (channelBuffer == null) {
                this.content = read(buffer, (int) length);
            } else {
                channelBuffer.writeBytes(buffer.readBytes((int) length));
            }
            return reset();
        } else if (this.message.isChunked()) {
            return new DefaultHttpChunk(read(buffer, toRead));
        } else {
            this.message.setChunked(true);
            return new Object[]{this.message, new DefaultHttpChunk(read(buffer, toRead))};
        }
    }

    private ChannelBuffer read(ChannelBuffer buffer, int len) {
        ChannelBuffer internal = internalBuffer();
        if (internal.readableBytes() < len) {
            return buffer.readBytes(len);
        }
        int index = internal.readerIndex();
        ChannelBuffer buf = internal.slice(index, len);
        buffer.readerIndex(index + len);
        return buf;
    }

    private State readHeaders(ChannelBuffer buffer) throws TooLongFrameException {
        this.headerSize = 0;
        HttpMessage message2 = this.message;
        String line = readHeader(buffer);
        String name = null;
        String value = null;
        if (line.length() != 0) {
            message2.clearHeaders();
            do {
                char firstChar = line.charAt(0);
                if (name == null || !(firstChar == ' ' || firstChar == 9)) {
                    if (name != null) {
                        message2.addHeader(name, value);
                    }
                    String[] header = splitHeader(line);
                    name = header[0];
                    value = header[1];
                } else {
                    value = value + ' ' + line.trim();
                }
                line = readHeader(buffer);
            } while (line.length() != 0);
            if (name != null) {
                message2.addHeader(name, value);
            }
        }
        if (isContentAlwaysEmpty(message2)) {
            return State.SKIP_CONTROL_CHARS;
        }
        if (message2.isChunked()) {
            return State.READ_CHUNK_SIZE;
        }
        if (HttpHeaders.getContentLength(message2, -1) >= 0) {
            return State.READ_FIXED_LENGTH_CONTENT;
        }
        return State.READ_VARIABLE_LENGTH_CONTENT;
    }

    private HttpChunkTrailer readTrailingHeaders(ChannelBuffer buffer) throws TooLongFrameException {
        this.headerSize = 0;
        String line = readHeader(buffer);
        String lastHeader = null;
        if (line.length() == 0) {
            return HttpChunk.LAST_CHUNK;
        }
        HttpChunkTrailer trailer = new DefaultHttpChunkTrailer();
        do {
            char firstChar = line.charAt(0);
            if (lastHeader == null || !(firstChar == ' ' || firstChar == 9)) {
                String[] header = splitHeader(line);
                String name = header[0];
                if (!name.equalsIgnoreCase("Content-Length") && !name.equalsIgnoreCase("Transfer-Encoding") && !name.equalsIgnoreCase("Trailer")) {
                    trailer.addHeader(name, header[1]);
                }
                lastHeader = name;
            } else {
                List<String> current = trailer.getHeaders(lastHeader);
                if (current.size() != 0) {
                    int lastPos = current.size() - 1;
                    current.set(lastPos, current.get(lastPos) + line.trim());
                }
            }
            line = readHeader(buffer);
        } while (line.length() != 0);
        return trailer;
    }

    private String readHeader(ChannelBuffer buffer) throws TooLongFrameException {
        StringBuilder sb = new StringBuilder(64);
        int headerSize2 = this.headerSize;
        while (true) {
            char nextByte = (char) buffer.readByte();
            headerSize2++;
            if (nextByte == 10) {
                break;
            }
            if (nextByte == 13) {
                nextByte = (char) buffer.readByte();
                headerSize2++;
                if (nextByte == 10) {
                    break;
                }
            }
            if (headerSize2 < this.maxHeaderSize) {
                sb.append(nextByte);
            } else {
                throw new TooLongFrameException("HTTP header is larger than " + this.maxHeaderSize + " bytes.");
            }
        }
        this.headerSize = headerSize2;
        return sb.toString();
    }

    private static int getChunkSize(String hex) {
        String hex2 = hex.trim();
        int i = 0;
        while (true) {
            if (i >= hex2.length()) {
                break;
            }
            char c = hex2.charAt(i);
            if (c == ';' || Character.isWhitespace(c) || Character.isISOControl(c)) {
                hex2 = hex2.substring(0, i);
            } else {
                i++;
            }
        }
        hex2 = hex2.substring(0, i);
        return Integer.parseInt(hex2, 16);
    }

    private static String readLine(ChannelBuffer buffer, int maxLineLength) throws TooLongFrameException {
        StringBuilder sb = new StringBuilder(64);
        int lineLength = 0;
        while (true) {
            byte nextByte = buffer.readByte();
            if (nextByte == 13) {
                if (buffer.readByte() == 10) {
                    return sb.toString();
                }
            } else if (nextByte == 10) {
                return sb.toString();
            } else {
                if (lineLength < maxLineLength) {
                    lineLength++;
                    sb.append((char) nextByte);
                } else {
                    throw new TooLongFrameException("An HTTP line is larger than " + maxLineLength + " bytes.");
                }
            }
        }
    }

    private static String[] splitInitialLine(String sb) {
        int aStart = findNonWhitespace(sb, 0);
        int aEnd = findWhitespace(sb, aStart);
        int bStart = findNonWhitespace(sb, aEnd);
        int bEnd = findWhitespace(sb, bStart);
        int cStart = findNonWhitespace(sb, bEnd);
        int cEnd = findEndOfString(sb);
        String[] strArr = new String[3];
        strArr[0] = sb.substring(aStart, aEnd);
        strArr[1] = sb.substring(bStart, bEnd);
        strArr[2] = cStart < cEnd ? sb.substring(cStart, cEnd) : "";
        return strArr;
    }

    private static String[] splitHeader(String sb) {
        int length = sb.length();
        int nameStart = findNonWhitespace(sb, 0);
        int nameEnd = nameStart;
        while (nameEnd < length && (ch = sb.charAt(nameEnd)) != ':' && !Character.isWhitespace(ch)) {
            nameEnd++;
        }
        int colonEnd = nameEnd;
        while (true) {
            if (colonEnd >= length) {
                break;
            } else if (sb.charAt(colonEnd) == ':') {
                colonEnd++;
                break;
            } else {
                colonEnd++;
            }
        }
        int valueStart = findNonWhitespace(sb, colonEnd);
        if (valueStart == length) {
            return new String[]{sb.substring(nameStart, nameEnd), ""};
        }
        return new String[]{sb.substring(nameStart, nameEnd), sb.substring(valueStart, findEndOfString(sb))};
    }

    private static int findNonWhitespace(String sb, int offset) {
        int result = offset;
        while (result < sb.length() && Character.isWhitespace(sb.charAt(result))) {
            result++;
        }
        return result;
    }

    private static int findWhitespace(String sb, int offset) {
        int result = offset;
        while (result < sb.length() && !Character.isWhitespace(sb.charAt(result))) {
            result++;
        }
        return result;
    }

    private static int findEndOfString(String sb) {
        int result = sb.length();
        while (result > 0 && Character.isWhitespace(sb.charAt(result - 1))) {
            result--;
        }
        return result;
    }
}
