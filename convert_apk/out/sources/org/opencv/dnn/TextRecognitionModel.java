package org.opencv.dnn;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.utils.Converters;

public class TextRecognitionModel extends Model {
    private static native long TextRecognitionModel_0(long j);

    private static native long TextRecognitionModel_1(String str, String str2);

    private static native long TextRecognitionModel_2(String str);

    private static native void delete(long j);

    private static native String getDecodeType_0(long j);

    private static native List<String> getVocabulary_0(long j);

    private static native String recognize_0(long j, long j2);

    private static native void recognize_1(long j, long j2, long j3, List<String> list);

    private static native long setDecodeType_0(long j, String str);

    private static native long setVocabulary_0(long j, List<String> list);

    protected TextRecognitionModel(long addr) {
        super(addr);
    }

    public static TextRecognitionModel __fromPtr__(long addr) {
        return new TextRecognitionModel(addr);
    }

    public TextRecognitionModel(Net network) {
        super(TextRecognitionModel_0(network.nativeObj));
    }

    public TextRecognitionModel(String model, String config) {
        super(TextRecognitionModel_1(model, config));
    }

    public TextRecognitionModel(String model) {
        super(TextRecognitionModel_2(model));
    }

    public TextRecognitionModel setDecodeType(String decodeType) {
        return new TextRecognitionModel(setDecodeType_0(this.nativeObj, decodeType));
    }

    public String getDecodeType() {
        return getDecodeType_0(this.nativeObj);
    }

    public TextRecognitionModel setVocabulary(List<String> vocabulary) {
        return new TextRecognitionModel(setVocabulary_0(this.nativeObj, vocabulary));
    }

    public List<String> getVocabulary() {
        return getVocabulary_0(this.nativeObj);
    }

    public String recognize(Mat frame) {
        return recognize_0(this.nativeObj, frame.nativeObj);
    }

    public void recognize(Mat frame, List<Mat> roiRects, List<String> results) {
        recognize_1(this.nativeObj, frame.nativeObj, Converters.vector_Mat_to_Mat(roiRects).nativeObj, results);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
