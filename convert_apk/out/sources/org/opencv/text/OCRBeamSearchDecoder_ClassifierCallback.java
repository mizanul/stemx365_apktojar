package org.opencv.text;

public class OCRBeamSearchDecoder_ClassifierCallback {
    protected final long nativeObj;

    private static native void delete(long j);

    protected OCRBeamSearchDecoder_ClassifierCallback(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static OCRBeamSearchDecoder_ClassifierCallback __fromPtr__(long addr) {
        return new OCRBeamSearchDecoder_ClassifierCallback(addr);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
