package org.opencv.video;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class Tracker {
    protected final long nativeObj;

    private static native void delete(long j);

    private static native void init_0(long j, long j2, int i, int i2, int i3, int i4);

    private static native boolean update_0(long j, long j2, double[] dArr);

    protected Tracker(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static Tracker __fromPtr__(long addr) {
        return new Tracker(addr);
    }

    public void init(Mat image, Rect boundingBox) {
        init_0(this.nativeObj, image.nativeObj, boundingBox.f180x, boundingBox.f181y, boundingBox.width, boundingBox.height);
    }

    public boolean update(Mat image, Rect boundingBox) {
        double[] boundingBox_out = new double[4];
        boolean retVal = update_0(this.nativeObj, image.nativeObj, boundingBox_out);
        if (boundingBox != null) {
            boundingBox.f180x = (int) boundingBox_out[0];
            boundingBox.f181y = (int) boundingBox_out[1];
            boundingBox.width = (int) boundingBox_out[2];
            boundingBox.height = (int) boundingBox_out[3];
        }
        return retVal;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
