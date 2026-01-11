package org.opencv.wechat_qrcode;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.utils.Converters;

public class WeChatQRCode {
    protected final long nativeObj;

    private static native long WeChatQRCode_0(String str, String str2, String str3, String str4);

    private static native long WeChatQRCode_1(String str, String str2, String str3);

    private static native long WeChatQRCode_2(String str, String str2);

    private static native long WeChatQRCode_3(String str);

    private static native long WeChatQRCode_4();

    private static native void delete(long j);

    private static native List<String> detectAndDecode_0(long j, long j2, long j3);

    private static native List<String> detectAndDecode_1(long j, long j2);

    protected WeChatQRCode(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static WeChatQRCode __fromPtr__(long addr) {
        return new WeChatQRCode(addr);
    }

    public WeChatQRCode(String detector_prototxt_path, String detector_caffe_model_path, String super_resolution_prototxt_path, String super_resolution_caffe_model_path) {
        this.nativeObj = WeChatQRCode_0(detector_prototxt_path, detector_caffe_model_path, super_resolution_prototxt_path, super_resolution_caffe_model_path);
    }

    public WeChatQRCode(String detector_prototxt_path, String detector_caffe_model_path, String super_resolution_prototxt_path) {
        this.nativeObj = WeChatQRCode_1(detector_prototxt_path, detector_caffe_model_path, super_resolution_prototxt_path);
    }

    public WeChatQRCode(String detector_prototxt_path, String detector_caffe_model_path) {
        this.nativeObj = WeChatQRCode_2(detector_prototxt_path, detector_caffe_model_path);
    }

    public WeChatQRCode(String detector_prototxt_path) {
        this.nativeObj = WeChatQRCode_3(detector_prototxt_path);
    }

    public WeChatQRCode() {
        this.nativeObj = WeChatQRCode_4();
    }

    public List<String> detectAndDecode(Mat img, List<Mat> points) {
        Mat points_mat = new Mat();
        List<String> retVal = detectAndDecode_0(this.nativeObj, img.nativeObj, points_mat.nativeObj);
        Converters.Mat_to_vector_Mat(points_mat, points);
        points_mat.release();
        return retVal;
    }

    public List<String> detectAndDecode(Mat img) {
        return detectAndDecode_1(this.nativeObj, img.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
