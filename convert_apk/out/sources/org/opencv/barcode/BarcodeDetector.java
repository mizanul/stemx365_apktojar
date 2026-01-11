package org.opencv.barcode;

import java.util.List;
import org.opencv.core.Mat;

public class BarcodeDetector {
    protected final long nativeObj;

    private static native long BarcodeDetector_0(String str, String str2);

    private static native long BarcodeDetector_1(String str);

    private static native long BarcodeDetector_2();

    private static native boolean decode_0(long j, long j2, long j3, List<String> list, List<Integer> list2);

    private static native void delete(long j);

    private static native boolean detectAndDecode_0(long j, long j2, List<String> list, List<Integer> list2, long j3);

    private static native boolean detectAndDecode_1(long j, long j2, List<String> list, List<Integer> list2);

    private static native boolean detect_0(long j, long j2, long j3);

    protected BarcodeDetector(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static BarcodeDetector __fromPtr__(long addr) {
        return new BarcodeDetector(addr);
    }

    public BarcodeDetector(String prototxt_path, String model_path) {
        this.nativeObj = BarcodeDetector_0(prototxt_path, model_path);
    }

    public BarcodeDetector(String prototxt_path) {
        this.nativeObj = BarcodeDetector_1(prototxt_path);
    }

    public BarcodeDetector() {
        this.nativeObj = BarcodeDetector_2();
    }

    public boolean detect(Mat img, Mat points) {
        return detect_0(this.nativeObj, img.nativeObj, points.nativeObj);
    }

    public boolean decode(Mat img, Mat points, List<String> decoded_info, List<Integer> decoded_type) {
        return decode_0(this.nativeObj, img.nativeObj, points.nativeObj, decoded_info, decoded_type);
    }

    public boolean detectAndDecode(Mat img, List<String> decoded_info, List<Integer> decoded_type, Mat points) {
        return detectAndDecode_0(this.nativeObj, img.nativeObj, decoded_info, decoded_type, points.nativeObj);
    }

    public boolean detectAndDecode(Mat img, List<String> decoded_info, List<Integer> decoded_type) {
        return detectAndDecode_1(this.nativeObj, img.nativeObj, decoded_info, decoded_type);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
