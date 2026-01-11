package org.opencv.text;

import org.opencv.core.Mat;

public class OCRBeamSearchDecoder extends BaseOCR {
    private static native long create_0(long j, String str, long j2, long j3, int i, int i2);

    private static native long create_1(long j, String str, long j2, long j3, int i);

    private static native long create_2(long j, String str, long j2, long j3);

    private static native void delete(long j);

    private static native String run_0(long j, long j2, int i, int i2);

    private static native String run_1(long j, long j2, int i);

    private static native String run_2(long j, long j2, long j3, int i, int i2);

    private static native String run_3(long j, long j2, long j3, int i);

    protected OCRBeamSearchDecoder(long addr) {
        super(addr);
    }

    public static OCRBeamSearchDecoder __fromPtr__(long addr) {
        return new OCRBeamSearchDecoder(addr);
    }

    public String run(Mat image, int min_confidence, int component_level) {
        return run_0(this.nativeObj, image.nativeObj, min_confidence, component_level);
    }

    public String run(Mat image, int min_confidence) {
        return run_1(this.nativeObj, image.nativeObj, min_confidence);
    }

    public String run(Mat image, Mat mask, int min_confidence, int component_level) {
        return run_2(this.nativeObj, image.nativeObj, mask.nativeObj, min_confidence, component_level);
    }

    public String run(Mat image, Mat mask, int min_confidence) {
        return run_3(this.nativeObj, image.nativeObj, mask.nativeObj, min_confidence);
    }

    public static OCRBeamSearchDecoder create(OCRBeamSearchDecoder_ClassifierCallback classifier, String vocabulary, Mat transition_probabilities_table, Mat emission_probabilities_table, int mode, int beam_size) {
        return __fromPtr__(create_0(classifier.getNativeObjAddr(), vocabulary, transition_probabilities_table.nativeObj, emission_probabilities_table.nativeObj, mode, beam_size));
    }

    public static OCRBeamSearchDecoder create(OCRBeamSearchDecoder_ClassifierCallback classifier, String vocabulary, Mat transition_probabilities_table, Mat emission_probabilities_table, int mode) {
        return __fromPtr__(create_1(classifier.getNativeObjAddr(), vocabulary, transition_probabilities_table.nativeObj, emission_probabilities_table.nativeObj, mode));
    }

    public static OCRBeamSearchDecoder create(OCRBeamSearchDecoder_ClassifierCallback classifier, String vocabulary, Mat transition_probabilities_table, Mat emission_probabilities_table) {
        return __fromPtr__(create_2(classifier.getNativeObjAddr(), vocabulary, transition_probabilities_table.nativeObj, emission_probabilities_table.nativeObj));
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
