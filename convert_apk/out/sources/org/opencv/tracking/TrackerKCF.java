package org.opencv.tracking;

import org.opencv.video.Tracker;

public class TrackerKCF extends Tracker {

    /* renamed from: CN */
    public static final int f187CN = 2;
    public static final int CUSTOM = 4;
    public static final int GRAY = 1;

    private static native long create_0(long j);

    private static native long create_1();

    private static native void delete(long j);

    protected TrackerKCF(long addr) {
        super(addr);
    }

    public static TrackerKCF __fromPtr__(long addr) {
        return new TrackerKCF(addr);
    }

    public static TrackerKCF create(TrackerKCF_Params parameters) {
        return __fromPtr__(create_0(parameters.nativeObj));
    }

    public static TrackerKCF create() {
        return __fromPtr__(create_1());
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
