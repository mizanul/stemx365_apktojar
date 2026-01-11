package org.opencv.video;

public class TrackerGOTURN extends Tracker {
    private static native long create_0(long j);

    private static native long create_1();

    private static native void delete(long j);

    protected TrackerGOTURN(long addr) {
        super(addr);
    }

    public static TrackerGOTURN __fromPtr__(long addr) {
        return new TrackerGOTURN(addr);
    }

    public static TrackerGOTURN create(TrackerGOTURN_Params parameters) {
        return __fromPtr__(create_0(parameters.nativeObj));
    }

    public static TrackerGOTURN create() {
        return __fromPtr__(create_1());
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
