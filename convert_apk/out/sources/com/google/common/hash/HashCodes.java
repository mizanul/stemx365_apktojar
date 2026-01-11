package com.google.common.hash;

import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import java.io.Serializable;

public final class HashCodes {
    private HashCodes() {
    }

    public static HashCode fromInt(int hash) {
        return new IntHashCode(hash);
    }

    private static final class IntHashCode extends HashCode implements Serializable {
        private static final long serialVersionUID = 0;
        final int hash;

        IntHashCode(int hash2) {
            this.hash = hash2;
        }

        public int bits() {
            return 32;
        }

        public byte[] asBytes() {
            int i = this.hash;
            return new byte[]{(byte) i, (byte) (i >> 8), (byte) (i >> 16), (byte) (i >> 24)};
        }

        public int asInt() {
            return this.hash;
        }

        public long asLong() {
            throw new IllegalStateException("this HashCode only has 32 bits; cannot create a long");
        }
    }

    public static HashCode fromLong(long hash) {
        return new LongHashCode(hash);
    }

    private static final class LongHashCode extends HashCode implements Serializable {
        private static final long serialVersionUID = 0;
        final long hash;

        LongHashCode(long hash2) {
            this.hash = hash2;
        }

        public int bits() {
            return 64;
        }

        public byte[] asBytes() {
            long j = this.hash;
            return new byte[]{(byte) ((int) j), (byte) ((int) (j >> 8)), (byte) ((int) (j >> 16)), (byte) ((int) (j >> 24)), (byte) ((int) (j >> 32)), (byte) ((int) (j >> 40)), (byte) ((int) (j >> 48)), (byte) ((int) (j >> 56))};
        }

        public int asInt() {
            return (int) this.hash;
        }

        public long asLong() {
            return this.hash;
        }
    }

    public static HashCode fromBytes(byte[] bytes) {
        Preconditions.checkArgument(bytes.length >= 4, "A HashCode must contain at least 4 bytes.");
        return fromBytesNoCopy((byte[]) bytes.clone());
    }

    static HashCode fromBytesNoCopy(byte[] bytes) {
        return new BytesHashCode(bytes);
    }

    private static final class BytesHashCode extends HashCode implements Serializable {
        private static final long serialVersionUID = 0;
        final byte[] bytes;

        BytesHashCode(byte[] bytes2) {
            this.bytes = bytes2;
        }

        public int bits() {
            return this.bytes.length * 8;
        }

        public byte[] asBytes() {
            return (byte[]) this.bytes.clone();
        }

        public int asInt() {
            byte[] bArr = this.bytes;
            return ((bArr[3] & 255) << Ascii.CAN) | (bArr[0] & 255) | ((bArr[1] & 255) << 8) | ((bArr[2] & 255) << 16);
        }

        public long asLong() {
            byte[] bArr = this.bytes;
            if (bArr.length >= 8) {
                return ((((long) bArr[1]) & 255) << 8) | (((long) bArr[0]) & 255) | ((((long) bArr[2]) & 255) << 16) | ((((long) bArr[3]) & 255) << 24) | ((((long) bArr[4]) & 255) << 32) | ((((long) bArr[5]) & 255) << 40) | ((((long) bArr[6]) & 255) << 48) | ((((long) bArr[7]) & 255) << 56);
            }
            throw new IllegalStateException("Not enough bytes");
        }
    }
}
