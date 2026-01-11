package org.opencv.text;

public class OCRHMMDecoder_ClassifierCallback {
    protected final long nativeObj;

    private static native void delete(long j);

    protected OCRHMMDecoder_ClassifierCallback(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static OCRHMMDecoder_ClassifierCallback __fromPtr__(long addr) {
        return new OCRHMMDecoder_ClassifierCallback(addr);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
