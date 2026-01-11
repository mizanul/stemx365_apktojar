package org.apache.xmlrpc.util;

import java.io.IOException;
import java.io.InputStream;

public class LimitedInputStream extends InputStream {
    private long available;

    /* renamed from: in */
    private InputStream f101in;
    private long markedAvailable;

    public LimitedInputStream(InputStream pIn, int pAvailable) {
        this.f101in = pIn;
        this.available = (long) pAvailable;
    }

    public int read() throws IOException {
        long j = this.available;
        if (j <= 0) {
            return -1;
        }
        this.available = j - 1;
        return this.f101in.read();
    }

    public int read(byte[] b, int off, int len) throws IOException {
        long j = this.available;
        if (j <= 0) {
            return -1;
        }
        if (((long) len) > j) {
            len = (int) j;
        }
        int read = this.f101in.read(b, off, len);
        if (read == -1) {
            this.available = 0;
        } else {
            this.available -= (long) read;
        }
        return read;
    }

    public long skip(long n) throws IOException {
        long skip = this.f101in.skip(n);
        long j = this.available;
        if (j > 0) {
            this.available = j - skip;
        }
        return skip;
    }

    public void mark(int readlimit) {
        this.f101in.mark(readlimit);
        this.markedAvailable = this.available;
    }

    public void reset() throws IOException {
        this.f101in.reset();
        this.available = this.markedAvailable;
    }

    public boolean markSupported() {
        return true;
    }
}
