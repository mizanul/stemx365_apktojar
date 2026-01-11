package org.opencv.video;

public class TrackerMIL extends Tracker {
    private static native long create_0(long j);

    private static native long create_1();

    private static native void delete(long j);

    protected TrackerMIL(long addr) {
        super(addr);
    }

    public static TrackerMIL __fromPtr__(long addr) {
        return new TrackerMIL(addr);
    }

    public static TrackerMIL create(TrackerMIL_Params parameters) {
        return __fromPtr__(create_0(parameters.nativeObj));
    }

    public static TrackerMIL create() {
        return __fromPtr__(create_1());
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
