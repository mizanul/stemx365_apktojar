package org.jboss.netty.handler.stream;

import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class ChunkedNioStream implements ChunkedInput {
    private final ByteBuffer byteBuffer;
    private final int chunkSize;

    /* renamed from: in */
    private final ReadableByteChannel f149in;
    private long offset;

    public ChunkedNioStream(ReadableByteChannel in) {
        this(in, 8192);
    }

    public ChunkedNioStream(ReadableByteChannel in, int chunkSize2) {
        if (in == null) {
            throw new NullPointerException("in");
        } else if (chunkSize2 > 0) {
            this.f149in = in;
            this.offset = 0;
            this.chunkSize = chunkSize2;
            this.byteBuffer = ByteBuffer.allocate(chunkSize2);
        } else {
            throw new IllegalArgumentException("chunkSize: " + chunkSize2 + " (expected: a positive integer)");
        }
    }

    public long getTransferredBytes() {
        return this.offset;
    }

    public boolean hasNextChunk() throws Exception {
        int b;
        if (this.byteBuffer.position() > 0) {
            return true;
        }
        if (!this.f149in.isOpen() || (b = this.f149in.read(this.byteBuffer)) < 0) {
            return false;
        }
        this.offset += (long) b;
        return true;
    }

    public boolean isEndOfInput() throws Exception {
        return !hasNextChunk();
    }

    public void close() throws Exception {
        this.f149in.close();
    }

    public Object nextChunk() throws Exception {
        if (!hasNextChunk()) {
            return null;
        }
        int readBytes = this.byteBuffer.position();
        do {
            int localReadBytes = this.f149in.read(this.byteBuffer);
            if (localReadBytes < 0) {
                break;
            }
            readBytes += localReadBytes;
            this.offset += (long) localReadBytes;
        } while (readBytes != this.chunkSize);
        this.byteBuffer.flip();
        ChannelBuffer buffer = ChannelBuffers.copiedBuffer(this.byteBuffer);
        this.byteBuffer.clear();
        return buffer;
    }
}
