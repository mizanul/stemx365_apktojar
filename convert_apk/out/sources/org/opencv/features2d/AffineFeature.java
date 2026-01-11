package org.opencv.features2d;

import org.opencv.core.MatOfFloat;

public class AffineFeature extends Feature2D {
    private static native long create_0(long j, int i, int i2, float f, float f2);

    private static native long create_1(long j, int i, int i2, float f);

    private static native long create_2(long j, int i, int i2);

    private static native long create_3(long j, int i);

    private static native long create_4(long j);

    private static native void delete(long j);

    private static native String getDefaultName_0(long j);

    private static native void getViewParams_0(long j, long j2, long j3);

    private static native void setViewParams_0(long j, long j2, long j3);

    protected AffineFeature(long addr) {
        super(addr);
    }

    public static AffineFeature __fromPtr__(long addr) {
        return new AffineFeature(addr);
    }

    public static AffineFeature create(Feature2D backend, int maxTilt, int minTilt, float tiltStep, float rotateStepBase) {
        return __fromPtr__(create_0(backend.getNativeObjAddr(), maxTilt, minTilt, tiltStep, rotateStepBase));
    }

    public static AffineFeature create(Feature2D backend, int maxTilt, int minTilt, float tiltStep) {
        return __fromPtr__(create_1(backend.getNativeObjAddr(), maxTilt, minTilt, tiltStep));
    }

    public static AffineFeature create(Feature2D backend, int maxTilt, int minTilt) {
        return __fromPtr__(create_2(backend.getNativeObjAddr(), maxTilt, minTilt));
    }

    public static AffineFeature create(Feature2D backend, int maxTilt) {
        return __fromPtr__(create_3(backend.getNativeObjAddr(), maxTilt));
    }

    public static AffineFeature create(Feature2D backend) {
        return __fromPtr__(create_4(backend.getNativeObjAddr()));
    }

    public void setViewParams(MatOfFloat tilts, MatOfFloat rolls) {
        setViewParams_0(this.nativeObj, tilts.nativeObj, rolls.nativeObj);
    }

    public void getViewParams(MatOfFloat tilts, MatOfFloat rolls) {
        getViewParams_0(this.nativeObj, tilts.nativeObj, rolls.nativeObj);
    }

    public String getDefaultName() {
        return getDefaultName_0(this.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
