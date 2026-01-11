package org.opencv.tracking;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect2d;
import org.opencv.core.Rect2d;

public class legacy_MultiTracker extends Algorithm {
    private static native boolean add_0(long j, long j2, long j3, double d, double d2, double d3, double d4);

    private static native void delete(long j);

    private static native long getObjects_0(long j);

    private static native long legacy_MultiTracker_0();

    private static native boolean update_0(long j, long j2, long j3);

    protected legacy_MultiTracker(long addr) {
        super(addr);
    }

    public static legacy_MultiTracker __fromPtr__(long addr) {
        return new legacy_MultiTracker(addr);
    }

    public legacy_MultiTracker() {
        super(legacy_MultiTracker_0());
    }

    public boolean add(legacy_Tracker newTracker, Mat image, Rect2d boundingBox) {
        Rect2d rect2d = boundingBox;
        return add_0(this.nativeObj, newTracker.getNativeObjAddr(), image.nativeObj, rect2d.f182x, rect2d.f183y, rect2d.width, rect2d.height);
    }

    public boolean update(Mat image, MatOfRect2d boundingBox) {
        return update_0(this.nativeObj, image.nativeObj, boundingBox.nativeObj);
    }

    public MatOfRect2d getObjects() {
        return MatOfRect2d.fromNativeAddr(getObjects_0(this.nativeObj));
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
