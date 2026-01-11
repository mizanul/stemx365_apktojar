package org.opencv.dnn;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.utils.Converters;

public class Model {
    protected final long nativeObj;

    private static native long Model_0(String str, String str2);

    private static native long Model_1(String str);

    private static native long Model_2(long j);

    private static native void delete(long j);

    private static native void predict_0(long j, long j2, long j3);

    private static native long setInputCrop_0(long j, boolean z);

    private static native long setInputMean_0(long j, double d, double d2, double d3, double d4);

    private static native void setInputParams_0(long j, double d, double d2, double d3, double d4, double d5, double d6, double d7, boolean z, boolean z2);

    private static native void setInputParams_1(long j, double d, double d2, double d3, double d4, double d5, double d6, double d7, boolean z);

    private static native void setInputParams_2(long j, double d, double d2, double d3, double d4, double d5, double d6, double d7);

    private static native void setInputParams_3(long j, double d, double d2, double d3);

    private static native void setInputParams_4(long j, double d);

    private static native void setInputParams_5(long j);

    private static native long setInputScale_0(long j, double d);

    private static native long setInputSize_0(long j, double d, double d2);

    private static native long setInputSize_1(long j, int i, int i2);

    private static native long setInputSwapRB_0(long j, boolean z);

    private static native long setPreferableBackend_0(long j, int i);

    private static native long setPreferableTarget_0(long j, int i);

    protected Model(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static Model __fromPtr__(long addr) {
        return new Model(addr);
    }

    public Model(String model, String config) {
        this.nativeObj = Model_0(model, config);
    }

    public Model(String model) {
        this.nativeObj = Model_1(model);
    }

    public Model(Net network) {
        this.nativeObj = Model_2(network.nativeObj);
    }

    public Model setInputSize(Size size) {
        return new Model(setInputSize_0(this.nativeObj, size.width, size.height));
    }

    public Model setInputSize(int width, int height) {
        return new Model(setInputSize_1(this.nativeObj, width, height));
    }

    public Model setInputMean(Scalar mean) {
        return new Model(setInputMean_0(this.nativeObj, mean.val[0], mean.val[1], mean.val[2], mean.val[3]));
    }

    public Model setInputScale(double scale) {
        return new Model(setInputScale_0(this.nativeObj, scale));
    }

    public Model setInputCrop(boolean crop) {
        return new Model(setInputCrop_0(this.nativeObj, crop));
    }

    public Model setInputSwapRB(boolean swapRB) {
        return new Model(setInputSwapRB_0(this.nativeObj, swapRB));
    }

    public void setInputParams(double scale, Size size, Scalar mean, boolean swapRB, boolean crop) {
        Size size2 = size;
        Scalar scalar = mean;
        setInputParams_0(this.nativeObj, scale, size2.width, size2.height, scalar.val[0], scalar.val[1], scalar.val[2], scalar.val[3], swapRB, crop);
    }

    public void setInputParams(double scale, Size size, Scalar mean, boolean swapRB) {
        Size size2 = size;
        Scalar scalar = mean;
        setInputParams_1(this.nativeObj, scale, size2.width, size2.height, scalar.val[0], scalar.val[1], scalar.val[2], scalar.val[3], swapRB);
    }

    public void setInputParams(double scale, Size size, Scalar mean) {
        Size size2 = size;
        Scalar scalar = mean;
        setInputParams_2(this.nativeObj, scale, size2.width, size2.height, scalar.val[0], scalar.val[1], scalar.val[2], scalar.val[3]);
    }

    public void setInputParams(double scale, Size size) {
        setInputParams_3(this.nativeObj, scale, size.width, size.height);
    }

    public void setInputParams(double scale) {
        setInputParams_4(this.nativeObj, scale);
    }

    public void setInputParams() {
        setInputParams_5(this.nativeObj);
    }

    public void predict(Mat frame, List<Mat> outs) {
        Mat outs_mat = new Mat();
        predict_0(this.nativeObj, frame.nativeObj, outs_mat.nativeObj);
        Converters.Mat_to_vector_Mat(outs_mat, outs);
        outs_mat.release();
    }

    public Model setPreferableBackend(int backendId) {
        return new Model(setPreferableBackend_0(this.nativeObj, backendId));
    }

    public Model setPreferableTarget(int targetId) {
        return new Model(setPreferableTarget_0(this.nativeObj, targetId));
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
