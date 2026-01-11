package com.google.common.p007io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/* renamed from: com.google.common.io.MultiInputStream */
final class MultiInputStream extends InputStream {

    /* renamed from: in */
    private InputStream f80in;

    /* renamed from: it */
    private Iterator<? extends InputSupplier<? extends InputStream>> f81it;

    public MultiInputStream(Iterator<? extends InputSupplier<? extends InputStream>> it) throws IOException {
        this.f81it = it;
        advance();
    }

    public void close() throws IOException {
        InputStream inputStream = this.f80in;
        if (inputStream != null) {
            try {
                inputStream.close();
            } finally {
                this.f80in = null;
            }
        }
    }

    private void advance() throws IOException {
        close();
        if (this.f81it.hasNext()) {
            this.f80in = (InputStream) ((InputSupplier) this.f81it.next()).getInput();
        }
    }

    public int available() throws IOException {
        InputStream inputStream = this.f80in;
        if (inputStream == null) {
            return 0;
        }
        return inputStream.available();
    }

    public boolean markSupported() {
        return false;
    }

    public int read() throws IOException {
        InputStream inputStream = this.f80in;
        if (inputStream == null) {
            return -1;
        }
        int result = inputStream.read();
        if (result != -1) {
            return result;
        }
        advance();
        return read();
    }

    public int read(byte[] b, int off, int len) throws IOException {
        InputStream inputStream = this.f80in;
        if (inputStream == null) {
            return -1;
        }
        int result = inputStream.read(b, off, len);
        if (result != -1) {
            return result;
        }
        advance();
        return read(b, off, len);
    }

    public long skip(long n) throws IOException {
        InputStream inputStream = this.f80in;
        if (inputStream == null || n <= 0) {
            return 0;
        }
        long result = inputStream.skip(n);
        if (result != 0) {
            return result;
        }
        if (read() == -1) {
            return 0;
        }
        return this.f80in.skip(n - 1) + 1;
    }
}
