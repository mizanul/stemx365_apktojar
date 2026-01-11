package org.jboss.netty.util.internal.jzlib;

import java.io.PrintStream;
import org.jboss.netty.util.internal.jzlib.JZlib;

public final class ZStream {
    long adler;
    public int avail_in;
    public int avail_out;
    int crc32;
    Deflate dstate;
    Inflate istate;
    public String msg;
    public byte[] next_in;
    public int next_in_index;
    public byte[] next_out;
    public int next_out_index;
    public long total_in;
    public long total_out;

    public int inflateInit() {
        return inflateInit(15);
    }

    public int inflateInit(Enum<?> wrapperType) {
        return inflateInit(15, wrapperType);
    }

    public int inflateInit(int w) {
        return inflateInit(w, JZlib.WrapperType.ZLIB);
    }

    public int inflateInit(int w, Enum wrapperType) {
        Inflate inflate = new Inflate();
        this.istate = inflate;
        return inflate.inflateInit(this, w, (JZlib.WrapperType) wrapperType);
    }

    public int inflate(int f) {
        Inflate inflate = this.istate;
        if (inflate == null) {
            return -2;
        }
        return inflate.inflate(this, f);
    }

    public int inflateEnd() {
        Inflate inflate = this.istate;
        if (inflate == null) {
            return -2;
        }
        int ret = inflate.inflateEnd(this);
        this.istate = null;
        return ret;
    }

    public int inflateSync() {
        Inflate inflate = this.istate;
        if (inflate == null) {
            return -2;
        }
        return inflate.inflateSync(this);
    }

    public int inflateSetDictionary(byte[] dictionary, int dictLength) {
        if (this.istate == null) {
            return -2;
        }
        return Inflate.inflateSetDictionary(this, dictionary, dictLength);
    }

    public int deflateInit(int level) {
        return deflateInit(level, 15);
    }

    public int deflateInit(int level, Enum<?> wrapperType) {
        return deflateInit(level, 15, wrapperType);
    }

    public int deflateInit(int level, int bits) {
        return deflateInit(level, bits, JZlib.WrapperType.ZLIB);
    }

    public int deflateInit(int level, int bits, Enum<?> wrapperType) {
        return deflateInit(level, bits, 8, wrapperType);
    }

    public int deflateInit(int level, int bits, int memLevel, Enum wrapperType) {
        Deflate deflate = new Deflate();
        this.dstate = deflate;
        return deflate.deflateInit(this, level, bits, memLevel, (JZlib.WrapperType) wrapperType);
    }

    public int deflate(int flush) {
        Deflate deflate = this.dstate;
        if (deflate == null) {
            return -2;
        }
        return deflate.deflate(this, flush);
    }

    public int deflateEnd() {
        Deflate deflate = this.dstate;
        if (deflate == null) {
            return -2;
        }
        int ret = deflate.deflateEnd();
        this.dstate = null;
        return ret;
    }

    public int deflateParams(int level, int strategy) {
        Deflate deflate = this.dstate;
        if (deflate == null) {
            return -2;
        }
        return deflate.deflateParams(this, level, strategy);
    }

    public int deflateSetDictionary(byte[] dictionary, int dictLength) {
        Deflate deflate = this.dstate;
        if (deflate == null) {
            return -2;
        }
        return deflate.deflateSetDictionary(this, dictionary, dictLength);
    }

    /* access modifiers changed from: package-private */
    public void flush_pending() {
        int len = this.dstate.pending;
        if (len > this.avail_out) {
            len = this.avail_out;
        }
        if (len != 0) {
            if (this.dstate.pending_buf.length <= this.dstate.pending_out || this.next_out.length <= this.next_out_index || this.dstate.pending_buf.length < this.dstate.pending_out + len || this.next_out.length < this.next_out_index + len) {
                System.out.println(this.dstate.pending_buf.length + ", " + this.dstate.pending_out + ", " + this.next_out.length + ", " + this.next_out_index + ", " + len);
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("avail_out=");
                sb.append(this.avail_out);
                printStream.println(sb.toString());
            }
            System.arraycopy(this.dstate.pending_buf, this.dstate.pending_out, this.next_out, this.next_out_index, len);
            this.next_out_index += len;
            this.dstate.pending_out += len;
            this.total_out += (long) len;
            this.avail_out -= len;
            this.dstate.pending -= len;
            if (this.dstate.pending == 0) {
                this.dstate.pending_out = 0;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int read_buf(byte[] buf, int start, int size) {
        int len = this.avail_in;
        if (len > size) {
            len = size;
        }
        if (len == 0) {
            return 0;
        }
        this.avail_in -= len;
        int i = C09141.$SwitchMap$org$jboss$netty$util$internal$jzlib$JZlib$WrapperType[this.dstate.wrapperType.ordinal()];
        if (i == 1) {
            this.adler = Adler32.adler32(this.adler, this.next_in, this.next_in_index, len);
        } else if (i == 2) {
            this.crc32 = CRC32.crc32(this.crc32, this.next_in, this.next_in_index, len);
        }
        System.arraycopy(this.next_in, this.next_in_index, buf, start, len);
        this.next_in_index += len;
        this.total_in += (long) len;
        return len;
    }

    /* renamed from: org.jboss.netty.util.internal.jzlib.ZStream$1 */
    static /* synthetic */ class C09141 {
        static final /* synthetic */ int[] $SwitchMap$org$jboss$netty$util$internal$jzlib$JZlib$WrapperType;

        static {
            int[] iArr = new int[JZlib.WrapperType.values().length];
            $SwitchMap$org$jboss$netty$util$internal$jzlib$JZlib$WrapperType = iArr;
            try {
                iArr[JZlib.WrapperType.ZLIB.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$jboss$netty$util$internal$jzlib$JZlib$WrapperType[JZlib.WrapperType.GZIP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public void free() {
        this.next_in = null;
        this.next_out = null;
        this.msg = null;
    }
}
