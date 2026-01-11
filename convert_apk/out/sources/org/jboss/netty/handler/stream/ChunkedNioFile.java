package org.jboss.netty.handler.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import org.jboss.netty.buffer.ChannelBuffers;

public class ChunkedNioFile implements ChunkedInput {
    private final int chunkSize;
    private final long endOffset;

    /* renamed from: in */
    private final FileChannel f148in;
    private long offset;
    private long startOffset;

    public ChunkedNioFile(File in) throws IOException {
        this(new FileInputStream(in).getChannel());
    }

    public ChunkedNioFile(File in, int chunkSize2) throws IOException {
        this(new FileInputStream(in).getChannel(), chunkSize2);
    }

    public ChunkedNioFile(FileChannel in) throws IOException {
        this(in, 8192);
    }

    public ChunkedNioFile(FileChannel in, int chunkSize2) throws IOException {
        this(in, 0, in.size(), chunkSize2);
    }

    public ChunkedNioFile(FileChannel in, long offset2, long length, int chunkSize2) throws IOException {
        if (in == null) {
            throw new NullPointerException("in");
        } else if (offset2 < 0) {
            throw new IllegalArgumentException("offset: " + offset2 + " (expected: 0 or greater)");
        } else if (length < 0) {
            throw new IllegalArgumentException("length: " + length + " (expected: 0 or greater)");
        } else if (chunkSize2 > 0) {
            if (offset2 != 0) {
                in.position(offset2);
            }
            this.f148in = in;
            this.chunkSize = chunkSize2;
            this.startOffset = offset2;
            this.offset = offset2;
            this.endOffset = offset2 + length;
        } else {
            throw new IllegalArgumentException("chunkSize: " + chunkSize2 + " (expected: a positive integer)");
        }
    }

    public long getStartOffset() {
        return this.startOffset;
    }

    public long getEndOffset() {
        return this.endOffset;
    }

    public long getCurrentOffset() {
        return this.offset;
    }

    public boolean hasNextChunk() throws Exception {
        return this.offset < this.endOffset && this.f148in.isOpen();
    }

    public boolean isEndOfInput() throws Exception {
        return !hasNextChunk();
    }

    public void close() throws Exception {
        this.f148in.close();
    }

    public Object nextChunk() throws Exception {
        int localReadBytes;
        long offset2 = this.offset;
        long j = this.endOffset;
        if (offset2 >= j) {
            return null;
        }
        int chunkSize2 = (int) Math.min((long) this.chunkSize, j - offset2);
        byte[] chunkArray = new byte[chunkSize2];
        ByteBuffer chunk = ByteBuffer.wrap(chunkArray);
        int readBytes = 0;
        do {
            localReadBytes = this.f148in.read(chunk);
            if (localReadBytes < 0 || (readBytes = readBytes + localReadBytes) == chunkSize2) {
                this.offset += (long) readBytes;
            }
            localReadBytes = this.f148in.read(chunk);
            break;
        } while ((readBytes = readBytes + localReadBytes) == chunkSize2);
        this.offset += (long) readBytes;
        return ChannelBuffers.wrappedBuffer(chunkArray);
    }
}
