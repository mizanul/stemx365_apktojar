package com.google.common.p007io;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

/* renamed from: com.google.common.io.MultiReader */
class MultiReader extends Reader {
    private Reader current;

    /* renamed from: it */
    private final Iterator<? extends InputSupplier<? extends Reader>> f82it;

    MultiReader(Iterator<? extends InputSupplier<? extends Reader>> readers) throws IOException {
        this.f82it = readers;
        advance();
    }

    private void advance() throws IOException {
        close();
        if (this.f82it.hasNext()) {
            this.current = (Reader) ((InputSupplier) this.f82it.next()).getInput();
        }
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
        Reader reader = this.current;
        if (reader == null) {
            return -1;
        }
        int result = reader.read(cbuf, off, len);
        if (result != -1) {
            return result;
        }
        advance();
        return read(cbuf, off, len);
    }

    public long skip(long n) throws IOException {
        Preconditions.checkArgument(n >= 0, "n is negative");
        if (n > 0) {
            while (true) {
                Reader reader = this.current;
                if (reader == null) {
                    break;
                }
                long result = reader.skip(n);
                if (result > 0) {
                    return result;
                }
                advance();
            }
        }
        return 0;
    }

    public boolean ready() throws IOException {
        Reader reader = this.current;
        return reader != null && reader.ready();
    }

    public void close() throws IOException {
        Reader reader = this.current;
        if (reader != null) {
            try {
                reader.close();
            } finally {
                this.current = null;
            }
        }
    }
}
