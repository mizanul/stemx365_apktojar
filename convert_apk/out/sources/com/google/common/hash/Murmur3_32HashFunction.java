package com.google.common.hash;

import com.google.common.hash.AbstractStreamingHashFunction;
import com.google.common.primitives.UnsignedBytes;
import java.io.Serializable;
import java.nio.ByteBuffer;

final class Murmur3_32HashFunction extends AbstractStreamingHashFunction implements Serializable {
    private static final long serialVersionUID = 0;
    private final int seed;

    Murmur3_32HashFunction(int seed2) {
        this.seed = seed2;
    }

    public int bits() {
        return 32;
    }

    public Hasher newHasher() {
        return new Murmur3_32Hasher(this.seed);
    }

    private static final class Murmur3_32Hasher extends AbstractStreamingHashFunction.AbstractStreamingHasher {

        /* renamed from: c1 */
        int f77c1 = -862048943;

        /* renamed from: c2 */
        int f78c2 = 461845907;

        /* renamed from: h1 */
        int f79h1;
        int len;

        Murmur3_32Hasher(int seed) {
            super(4);
            this.f79h1 = seed;
        }

        /* access modifiers changed from: protected */
        public void process(ByteBuffer bb) {
            int k1 = bb.getInt();
            this.len += 4;
            int rotateLeft = this.f79h1 ^ (Integer.rotateLeft(k1 * this.f77c1, 15) * this.f78c2);
            this.f79h1 = rotateLeft;
            int rotateLeft2 = Integer.rotateLeft(rotateLeft, 13);
            this.f79h1 = rotateLeft2;
            this.f79h1 = (rotateLeft2 * 5) - 430675100;
        }

        /* access modifiers changed from: protected */
        public void processRemaining(ByteBuffer bb) {
            this.len += bb.remaining();
            int k1 = 0;
            int remaining = bb.remaining();
            if (remaining != 1) {
                if (remaining != 2) {
                    if (remaining == 3) {
                        k1 = 0 ^ (UnsignedBytes.toInt(bb.get(2)) << 16);
                    }
                    this.f79h1 ^= Integer.rotateLeft(k1 * this.f77c1, 15) * this.f78c2;
                }
                k1 ^= UnsignedBytes.toInt(bb.get(1)) << 8;
            }
            k1 ^= UnsignedBytes.toInt(bb.get(0));
            this.f79h1 ^= Integer.rotateLeft(k1 * this.f77c1, 15) * this.f78c2;
        }

        public HashCode makeHash() {
            int i = this.f79h1 ^ this.len;
            this.f79h1 = i;
            int i2 = i ^ (i >>> 16);
            this.f79h1 = i2;
            int i3 = i2 * -2048144789;
            this.f79h1 = i3;
            int i4 = i3 ^ (i3 >>> 13);
            this.f79h1 = i4;
            int i5 = i4 * -1028477387;
            this.f79h1 = i5;
            int i6 = i5 ^ (i5 >>> 16);
            this.f79h1 = i6;
            return HashCodes.fromInt(i6);
        }
    }
}
