package org.opencv.videoio;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;

public class VideoCapture {
    protected final long nativeObj;

    private static native long VideoCapture_0();

    private static native long VideoCapture_1(String str, int i);

    private static native long VideoCapture_2(String str);

    private static native long VideoCapture_3(String str, int i, long j);

    private static native long VideoCapture_4(int i, int i2);

    private static native long VideoCapture_5(int i);

    private static native long VideoCapture_6(int i, int i2, long j);

    private static native void delete(long j);

    private static native String getBackendName_0(long j);

    private static native boolean getExceptionMode_0(long j);

    private static native double get_0(long j, int i);

    private static native boolean grab_0(long j);

    private static native boolean isOpened_0(long j);

    private static native boolean open_0(long j, String str, int i);

    private static native boolean open_1(long j, String str);

    private static native boolean open_2(long j, String str, int i, long j2);

    private static native boolean open_3(long j, int i, int i2);

    private static native boolean open_4(long j, int i);

    private static native boolean open_5(long j, int i, int i2, long j2);

    private static native boolean read_0(long j, long j2);

    private static native void release_0(long j);

    private static native boolean retrieve_0(long j, long j2, int i);

    private static native boolean retrieve_1(long j, long j2);

    private static native void setExceptionMode_0(long j, boolean z);

    private static native boolean set_0(long j, int i, double d);

    protected VideoCapture(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static VideoCapture __fromPtr__(long addr) {
        return new VideoCapture(addr);
    }

    public VideoCapture() {
        this.nativeObj = VideoCapture_0();
    }

    public VideoCapture(String filename, int apiPreference) {
        this.nativeObj = VideoCapture_1(filename, apiPreference);
    }

    public VideoCapture(String filename) {
        this.nativeObj = VideoCapture_2(filename);
    }

    public VideoCapture(String filename, int apiPreference, MatOfInt params) {
        this.nativeObj = VideoCapture_3(filename, apiPreference, params.nativeObj);
    }

    public VideoCapture(int index, int apiPreference) {
        this.nativeObj = VideoCapture_4(index, apiPreference);
    }

    public VideoCapture(int index) {
        this.nativeObj = VideoCapture_5(index);
    }

    public VideoCapture(int index, int apiPreference, MatOfInt params) {
        this.nativeObj = VideoCapture_6(index, apiPreference, params.nativeObj);
    }

    public boolean open(String filename, int apiPreference) {
        return open_0(this.nativeObj, filename, apiPreference);
    }

    public boolean open(String filename) {
        return open_1(this.nativeObj, filename);
    }

    public boolean open(String filename, int apiPreference, MatOfInt params) {
        return open_2(this.nativeObj, filename, apiPreference, params.nativeObj);
    }

    public boolean open(int index, int apiPreference) {
        return open_3(this.nativeObj, index, apiPreference);
    }

    public boolean open(int index) {
        return open_4(this.nativeObj, index);
    }

    public boolean open(int index, int apiPreference, MatOfInt params) {
        return open_5(this.nativeObj, index, apiPreference, params.nativeObj);
    }

    public boolean isOpened() {
        return isOpened_0(this.nativeObj);
    }

    public void release() {
        release_0(this.nativeObj);
    }

    public boolean grab() {
        return grab_0(this.nativeObj);
    }

    public boolean retrieve(Mat image, int flag) {
        return retrieve_0(this.nativeObj, image.nativeObj, flag);
    }

    public boolean retrieve(Mat image) {
        return retrieve_1(this.nativeObj, image.nativeObj);
    }

    public boolean read(Mat image) {
        return read_0(this.nativeObj, image.nativeObj);
    }

    public boolean set(int propId, double value) {
        return set_0(this.nativeObj, propId, value);
    }

    public double get(int propId) {
        return get_0(this.nativeObj, propId);
    }

    public String getBackendName() {
        return getBackendName_0(this.nativeObj);
    }

    public void setExceptionMode(boolean enable) {
        setExceptionMode_0(this.nativeObj, enable);
    }

    public boolean getExceptionMode() {
        return getExceptionMode_0(this.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
