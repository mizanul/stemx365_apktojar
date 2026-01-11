package org.opencv.dnn;

import org.opencv.core.Mat;

public class SegmentationModel extends Model {
    private static native long SegmentationModel_0(String str, String str2);

    private static native long SegmentationModel_1(String str);

    private static native long SegmentationModel_2(long j);

    private static native void delete(long j);

    private static native void segment_0(long j, long j2, long j3);

    protected SegmentationModel(long addr) {
        super(addr);
    }

    public static SegmentationModel __fromPtr__(long addr) {
        return new SegmentationModel(addr);
    }

    public SegmentationModel(String model, String config) {
        super(SegmentationModel_0(model, config));
    }

    public SegmentationModel(String model) {
        super(SegmentationModel_1(model));
    }

    public SegmentationModel(Net network) {
        super(SegmentationModel_2(network.nativeObj));
    }

    public void segment(Mat frame, Mat mask) {
        segment_0(this.nativeObj, frame.nativeObj, mask.nativeObj);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
