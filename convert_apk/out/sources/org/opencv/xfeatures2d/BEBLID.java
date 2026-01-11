package org.opencv.xfeatures2d;

import org.opencv.features2d.Feature2D;

public class BEBLID extends Feature2D {
    public static final int SIZE_256_BITS = 101;
    public static final int SIZE_512_BITS = 100;

    private static native long create_0(float f, int i);

    private static native long create_1(float f);

    private static native void delete(long j);

    protected BEBLID(long addr) {
        super(addr);
    }

    public static BEBLID __fromPtr__(long addr) {
        return new BEBLID(addr);
    }

    public static BEBLID create(float scale_factor, int n_bits) {
        return __fromPtr__(create_0(scale_factor, n_bits));
    }

    public static BEBLID create(float scale_factor) {
        return __fromPtr__(create_1(scale_factor));
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
