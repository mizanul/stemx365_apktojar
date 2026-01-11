package org.opencv.tracking;

public class legacy_TrackerMedianFlow extends legacy_Tracker {
    private static native long create_0();

    private static native void delete(long j);

    protected legacy_TrackerMedianFlow(long addr) {
        super(addr);
    }

    public static legacy_TrackerMedianFlow __fromPtr__(long addr) {
        return new legacy_TrackerMedianFlow(addr);
    }

    public static legacy_TrackerMedianFlow create() {
        return __fromPtr__(create_0());
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
