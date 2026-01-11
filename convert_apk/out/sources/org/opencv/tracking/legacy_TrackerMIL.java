package org.opencv.tracking;

public class legacy_TrackerMIL extends legacy_Tracker {
    private static native long create_0();

    private static native void delete(long j);

    protected legacy_TrackerMIL(long addr) {
        super(addr);
    }

    public static legacy_TrackerMIL __fromPtr__(long addr) {
        return new legacy_TrackerMIL(addr);
    }

    public static legacy_TrackerMIL create() {
        return __fromPtr__(create_0());
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
