package org.opencv.tracking;

public class legacy_TrackerTLD extends legacy_Tracker {
    private static native long create_0();

    private static native void delete(long j);

    protected legacy_TrackerTLD(long addr) {
        super(addr);
    }

    public static legacy_TrackerTLD __fromPtr__(long addr) {
        return new legacy_TrackerTLD(addr);
    }

    public static legacy_TrackerTLD create() {
        return __fromPtr__(create_0());
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
