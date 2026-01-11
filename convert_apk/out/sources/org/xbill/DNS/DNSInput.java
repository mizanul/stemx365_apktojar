package org.xbill.DNS;

public class DNSInput {
    private byte[] array;
    private int end;
    private int pos = 0;
    private int saved_end;
    private int saved_pos;

    public DNSInput(byte[] input) {
        this.array = input;
        this.end = input.length;
        this.saved_pos = -1;
        this.saved_end = -1;
    }

    public int current() {
        return this.pos;
    }

    public int remaining() {
        return this.end - this.pos;
    }

    private void require(int n) throws WireParseException {
        if (n > remaining()) {
            throw new WireParseException("end of input");
        }
    }

    public void setActive(int len) {
        int length = this.array.length;
        int i = this.pos;
        if (len <= length - i) {
            this.end = i + len;
            return;
        }
        throw new IllegalArgumentException("cannot set active region past end of input");
    }

    public void clearActive() {
        this.end = this.array.length;
    }

    public void jump(int index) {
        byte[] bArr = this.array;
        if (index < bArr.length) {
            this.pos = index;
            this.end = bArr.length;
            return;
        }
        throw new IllegalArgumentException("cannot jump past end of input");
    }

    public void save() {
        this.saved_pos = this.pos;
        this.saved_end = this.end;
    }

    public void restore() {
        int i = this.saved_pos;
        if (i >= 0) {
            this.pos = i;
            this.end = this.saved_end;
            this.saved_pos = -1;
            this.saved_end = -1;
            return;
        }
        throw new IllegalStateException("no previous state");
    }

    public int readU8() throws WireParseException {
        require(1);
        byte[] bArr = this.array;
        int i = this.pos;
        this.pos = i + 1;
        return bArr[i] & 255;
    }

    public int readU16() throws WireParseException {
        require(2);
        byte[] bArr = this.array;
        int i = this.pos;
        int i2 = i + 1;
        this.pos = i2;
        this.pos = i2 + 1;
        return ((bArr[i] & 255) << 8) + (bArr[i2] & 255);
    }

    public long readU32() throws WireParseException {
        require(4);
        byte[] bArr = this.array;
        int i = this.pos;
        int i2 = i + 1;
        this.pos = i2;
        int i3 = i2 + 1;
        this.pos = i3;
        int i4 = i3 + 1;
        this.pos = i4;
        this.pos = i4 + 1;
        return (((long) (bArr[i] & 255)) << 24) + ((long) ((bArr[i2] & 255) << 16)) + ((long) ((bArr[i3] & 255) << 8)) + ((long) (bArr[i4] & 255));
    }

    public void readByteArray(byte[] b, int off, int len) throws WireParseException {
        require(len);
        System.arraycopy(this.array, this.pos, b, off, len);
        this.pos += len;
    }

    public byte[] readByteArray(int len) throws WireParseException {
        require(len);
        byte[] out = new byte[len];
        System.arraycopy(this.array, this.pos, out, 0, len);
        this.pos += len;
        return out;
    }

    public byte[] readByteArray() {
        int len = remaining();
        byte[] out = new byte[len];
        System.arraycopy(this.array, this.pos, out, 0, len);
        this.pos += len;
        return out;
    }

    public byte[] readCountedString() throws WireParseException {
        require(1);
        byte[] bArr = this.array;
        int i = this.pos;
        this.pos = i + 1;
        return readByteArray(bArr[i] & 255);
    }
}
