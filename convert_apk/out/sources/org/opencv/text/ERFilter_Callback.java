package org.opencv.text;

public class ERFilter_Callback {
    protected final long nativeObj;

    private static native void delete(long j);

    protected ERFilter_Callback(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static ERFilter_Callback __fromPtr__(long addr) {
        return new ERFilter_Callback(addr);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
