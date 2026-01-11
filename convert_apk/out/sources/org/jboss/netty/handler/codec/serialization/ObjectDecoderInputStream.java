package org.jboss.netty.handler.codec.serialization;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.StreamCorruptedException;

public class ObjectDecoderInputStream extends InputStream implements ObjectInput {
    private final ClassResolver classResolver;

    /* renamed from: in */
    private final DataInputStream f139in;
    private final int maxObjectSize;

    public ObjectDecoderInputStream(InputStream in) {
        this(in, (ClassLoader) null);
    }

    public ObjectDecoderInputStream(InputStream in, ClassLoader classLoader) {
        this(in, classLoader, 1048576);
    }

    public ObjectDecoderInputStream(InputStream in, int maxObjectSize2) {
        this(in, (ClassLoader) null, maxObjectSize2);
    }

    public ObjectDecoderInputStream(InputStream in, ClassLoader classLoader, int maxObjectSize2) {
        if (in == null) {
            throw new NullPointerException("in");
        } else if (maxObjectSize2 > 0) {
            if (in instanceof DataInputStream) {
                this.f139in = (DataInputStream) in;
            } else {
                this.f139in = new DataInputStream(in);
            }
            this.classResolver = ClassResolvers.weakCachingResolver(classLoader);
            this.maxObjectSize = maxObjectSize2;
        } else {
            throw new IllegalArgumentException("maxObjectSize: " + maxObjectSize2);
        }
    }

    public Object readObject() throws ClassNotFoundException, IOException {
        int dataLen = readInt();
        if (dataLen <= 0) {
            throw new StreamCorruptedException("invalid data length: " + dataLen);
        } else if (dataLen <= this.maxObjectSize) {
            return new CompactObjectInputStream(this.f139in, this.classResolver).readObject();
        } else {
            throw new StreamCorruptedException("data length too big: " + dataLen + " (max: " + this.maxObjectSize + ')');
        }
    }

    public int available() throws IOException {
        return this.f139in.available();
    }

    public void close() throws IOException {
        this.f139in.close();
    }

    public void mark(int readlimit) {
        this.f139in.mark(readlimit);
    }

    public boolean markSupported() {
        return this.f139in.markSupported();
    }

    public int read() throws IOException {
        return this.f139in.read();
    }

    public final int read(byte[] b, int off, int len) throws IOException {
        return this.f139in.read(b, off, len);
    }

    public final int read(byte[] b) throws IOException {
        return this.f139in.read(b);
    }

    public final boolean readBoolean() throws IOException {
        return this.f139in.readBoolean();
    }

    public final byte readByte() throws IOException {
        return this.f139in.readByte();
    }

    public final char readChar() throws IOException {
        return this.f139in.readChar();
    }

    public final double readDouble() throws IOException {
        return this.f139in.readDouble();
    }

    public final float readFloat() throws IOException {
        return this.f139in.readFloat();
    }

    public final void readFully(byte[] b, int off, int len) throws IOException {
        this.f139in.readFully(b, off, len);
    }

    public final void readFully(byte[] b) throws IOException {
        this.f139in.readFully(b);
    }

    public final int readInt() throws IOException {
        return this.f139in.readInt();
    }

    @Deprecated
    public final String readLine() throws IOException {
        return this.f139in.readLine();
    }

    public final long readLong() throws IOException {
        return this.f139in.readLong();
    }

    public final short readShort() throws IOException {
        return this.f139in.readShort();
    }

    public final int readUnsignedByte() throws IOException {
        return this.f139in.readUnsignedByte();
    }

    public final int readUnsignedShort() throws IOException {
        return this.f139in.readUnsignedShort();
    }

    public final String readUTF() throws IOException {
        return this.f139in.readUTF();
    }

    public void reset() throws IOException {
        this.f139in.reset();
    }

    public long skip(long n) throws IOException {
        return this.f139in.skip(n);
    }

    public final int skipBytes(int n) throws IOException {
        return this.f139in.skipBytes(n);
    }
}
