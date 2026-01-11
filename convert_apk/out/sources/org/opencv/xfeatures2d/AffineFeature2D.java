package org.opencv.xfeatures2d;

import org.opencv.features2d.Feature2D;

public class AffineFeature2D extends Feature2D {
    private static native void delete(long j);

    protected AffineFeature2D(long addr) {
        super(addr);
    }

    public static AffineFeature2D __fromPtr__(long addr) {
        return new AffineFeature2D(addr);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
