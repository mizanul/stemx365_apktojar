package org.opencv.video;

public class TrackerDaSiamRPN extends Tracker {
    private static native long create_0(long j);

    private static native long create_1();

    private static native void delete(long j);

    private static native float getTrackingScore_0(long j);

    protected TrackerDaSiamRPN(long addr) {
        super(addr);
    }

    public static TrackerDaSiamRPN __fromPtr__(long addr) {
        return new TrackerDaSiamRPN(addr);
    }

    public static TrackerDaSiamRPN create(TrackerDaSiamRPN_Params parameters) {
        return __fromPtr__(create_0(parameters.nativeObj));
    }

    public static TrackerDaSiamRPN create() {
        return __fromPtr__(create_1());
    }

    public float getTrackingScore() {
        return getTrackingScore_0(this.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
