package org.jboss.netty.handler.stream;

import java.io.InputStream;
import java.io.PushbackInputStream;
import org.jboss.netty.buffer.ChannelBuffers;

public class ChunkedStream implements ChunkedInput {
    static final int DEFAULT_CHUNK_SIZE = 8192;
    private final int chunkSize;

    /* renamed from: in */
    private final PushbackInputStream f150in;
    private long offset;

    public ChunkedStream(InputStream in) {
        this(in, 8192);
    }

    public ChunkedStream(InputStream in, int chunkSize2) {
        if (in == null) {
            throw new NullPointerException("in");
        } else if (chunkSize2 > 0) {
            if (in instanceof PushbackInputStream) {
                this.f150in = (PushbackInputStream) in;
            } else {
                this.f150in = new PushbackInputStream(in);
            }
            this.chunkSize = chunkSize2;
        } else {
            throw new IllegalArgumentException("chunkSize: " + chunkSize2 + " (expected: a positive integer)");
        }
    }

    public long getTransferredBytes() {
        return this.offset;
    }

    public boolean hasNextChunk() throws Exception {
        int b = this.f150in.read();
        if (b < 0) {
            return false;
        }
        this.f150in.unread(b);
        return true;
    }

    public boolean isEndOfInput() throws Exception {
        return !hasNextChunk();
    }

    public void close() throws Exception {
        this.f150in.close();
    }

    public Object nextChunk() throws Exception {
        int chunkSize2;
        if (!hasNextChunk()) {
            return null;
        }
        if (this.f150in.available() <= 0) {
            chunkSize2 = this.chunkSize;
        } else {
            chunkSize2 = Math.min(this.chunkSize, this.f150in.available());
        }
        byte[] chunk = new byte[chunkSize2];
        int readBytes = 0;
        do {
            int localReadBytes = this.f150in.read(chunk, readBytes, chunkSize2 - readBytes);
            if (localReadBytes < 0) {
                break;
            }
            readBytes += localReadBytes;
            this.offset += (long) localReadBytes;
        } while (readBytes != chunkSize2);
        return ChannelBuffers.wrappedBuffer(chunk, 0, readBytes);
    }
}
