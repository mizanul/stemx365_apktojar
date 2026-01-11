package org.opencv.tracking;

public class legacy_TrackerBoosting extends legacy_Tracker {
    private static native long create_0();

    private static native void delete(long j);

    protected legacy_TrackerBoosting(long addr) {
        super(addr);
    }

    public static legacy_TrackerBoosting __fromPtr__(long addr) {
        return new legacy_TrackerBoosting(addr);
    }

    public static legacy_TrackerBoosting create() {
        return __fromPtr__(create_0());
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
