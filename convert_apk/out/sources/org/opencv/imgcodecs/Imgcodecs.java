package org.opencv.imgcodecs;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.utils.Converters;

public class Imgcodecs {
    public static final int IMREAD_ANYCOLOR = 4;
    public static final int IMREAD_ANYDEPTH = 2;
    public static final int IMREAD_COLOR = 1;
    public static final int IMREAD_GRAYSCALE = 0;
    public static final int IMREAD_IGNORE_ORIENTATION = 128;
    public static final int IMREAD_LOAD_GDAL = 8;
    public static final int IMREAD_REDUCED_COLOR_2 = 17;
    public static final int IMREAD_REDUCED_COLOR_4 = 33;
    public static final int IMREAD_REDUCED_COLOR_8 = 65;
    public static final int IMREAD_REDUCED_GRAYSCALE_2 = 16;
    public static final int IMREAD_REDUCED_GRAYSCALE_4 = 32;
    public static final int IMREAD_REDUCED_GRAYSCALE_8 = 64;
    public static final int IMREAD_UNCHANGED = -1;
    public static final int IMWRITE_EXR_COMPRESSION = 49;
    public static final int IMWRITE_EXR_COMPRESSION_B44 = 6;
    public static final int IMWRITE_EXR_COMPRESSION_B44A = 7;
    public static final int IMWRITE_EXR_COMPRESSION_DWAA = 8;
    public static final int IMWRITE_EXR_COMPRESSION_DWAB = 9;
    public static final int IMWRITE_EXR_COMPRESSION_NO = 0;
    public static final int IMWRITE_EXR_COMPRESSION_PIZ = 4;
    public static final int IMWRITE_EXR_COMPRESSION_PXR24 = 5;
    public static final int IMWRITE_EXR_COMPRESSION_RLE = 1;
    public static final int IMWRITE_EXR_COMPRESSION_ZIP = 3;
    public static final int IMWRITE_EXR_COMPRESSION_ZIPS = 2;
    public static final int IMWRITE_EXR_TYPE = 48;
    public static final int IMWRITE_EXR_TYPE_FLOAT = 2;
    public static final int IMWRITE_EXR_TYPE_HALF = 1;
    public static final int IMWRITE_JPEG2000_COMPRESSION_X1000 = 272;
    public static final int IMWRITE_JPEG_CHROMA_QUALITY = 6;
    public static final int IMWRITE_JPEG_LUMA_QUALITY = 5;
    public static final int IMWRITE_JPEG_OPTIMIZE = 3;
    public static final int IMWRITE_JPEG_PROGRESSIVE = 2;
    public static final int IMWRITE_JPEG_QUALITY = 1;
    public static final int IMWRITE_JPEG_RST_INTERVAL = 4;
    public static final int IMWRITE_PAM_FORMAT_BLACKANDWHITE = 1;
    public static final int IMWRITE_PAM_FORMAT_GRAYSCALE = 2;
    public static final int IMWRITE_PAM_FORMAT_GRAYSCALE_ALPHA = 3;
    public static final int IMWRITE_PAM_FORMAT_NULL = 0;
    public static final int IMWRITE_PAM_FORMAT_RGB = 4;
    public static final int IMWRITE_PAM_FORMAT_RGB_ALPHA = 5;
    public static final int IMWRITE_PAM_TUPLETYPE = 128;
    public static final int IMWRITE_PNG_BILEVEL = 18;
    public static final int IMWRITE_PNG_COMPRESSION = 16;
    public static final int IMWRITE_PNG_STRATEGY = 17;
    public static final int IMWRITE_PNG_STRATEGY_DEFAULT = 0;
    public static final int IMWRITE_PNG_STRATEGY_FILTERED = 1;
    public static final int IMWRITE_PNG_STRATEGY_FIXED = 4;
    public static final int IMWRITE_PNG_STRATEGY_HUFFMAN_ONLY = 2;
    public static final int IMWRITE_PNG_STRATEGY_RLE = 3;
    public static final int IMWRITE_PXM_BINARY = 32;
    public static final int IMWRITE_TIFF_COMPRESSION = 259;
    public static final int IMWRITE_TIFF_RESUNIT = 256;
    public static final int IMWRITE_TIFF_XDPI = 257;
    public static final int IMWRITE_TIFF_YDPI = 258;
    public static final int IMWRITE_WEBP_QUALITY = 64;

    private static native boolean haveImageReader_0(String str);

    private static native boolean haveImageWriter_0(String str);

    private static native long imcount_0(String str, int i);

    private static native long imcount_1(String str);

    private static native long imdecode_0(long j, int i);

    private static native boolean imencode_0(String str, long j, long j2, long j3);

    private static native boolean imencode_1(String str, long j, long j2);

    private static native long imread_0(String str, int i);

    private static native long imread_1(String str);

    private static native boolean imreadmulti_0(String str, long j, int i);

    private static native boolean imreadmulti_1(String str, long j);

    private static native boolean imreadmulti_2(String str, long j, int i, int i2, int i3);

    private static native boolean imreadmulti_3(String str, long j, int i, int i2);

    private static native boolean imwrite_0(String str, long j, long j2);

    private static native boolean imwrite_1(String str, long j);

    private static native boolean imwritemulti_0(String str, long j, long j2);

    private static native boolean imwritemulti_1(String str, long j);

    public static Mat imread(String filename, int flags) {
        return new Mat(imread_0(filename, flags));
    }

    public static Mat imread(String filename) {
        return new Mat(imread_1(filename));
    }

    public static boolean imreadmulti(String filename, List<Mat> mats, int flags) {
        Mat mats_mat = new Mat();
        boolean retVal = imreadmulti_0(filename, mats_mat.nativeObj, flags);
        Converters.Mat_to_vector_Mat(mats_mat, mats);
        mats_mat.release();
        return retVal;
    }

    public static boolean imreadmulti(String filename, List<Mat> mats) {
        Mat mats_mat = new Mat();
        boolean retVal = imreadmulti_1(filename, mats_mat.nativeObj);
        Converters.Mat_to_vector_Mat(mats_mat, mats);
        mats_mat.release();
        return retVal;
    }

    public static boolean imreadmulti(String filename, List<Mat> mats, int start, int count, int flags) {
        Mat mats_mat = new Mat();
        boolean retVal = imreadmulti_2(filename, mats_mat.nativeObj, start, count, flags);
        Converters.Mat_to_vector_Mat(mats_mat, mats);
        mats_mat.release();
        return retVal;
    }

    public static boolean imreadmulti(String filename, List<Mat> mats, int start, int count) {
        Mat mats_mat = new Mat();
        boolean retVal = imreadmulti_3(filename, mats_mat.nativeObj, start, count);
        Converters.Mat_to_vector_Mat(mats_mat, mats);
        mats_mat.release();
        return retVal;
    }

    public static long imcount(String filename, int flags) {
        return imcount_0(filename, flags);
    }

    public static long imcount(String filename) {
        return imcount_1(filename);
    }

    public static boolean imwrite(String filename, Mat img, MatOfInt params) {
        return imwrite_0(filename, img.nativeObj, params.nativeObj);
    }

    public static boolean imwrite(String filename, Mat img) {
        return imwrite_1(filename, img.nativeObj);
    }

    public static boolean imwritemulti(String filename, List<Mat> img, MatOfInt params) {
        return imwritemulti_0(filename, Converters.vector_Mat_to_Mat(img).nativeObj, params.nativeObj);
    }

    public static boolean imwritemulti(String filename, List<Mat> img) {
        return imwritemulti_1(filename, Converters.vector_Mat_to_Mat(img).nativeObj);
    }

    public static Mat imdecode(Mat buf, int flags) {
        return new Mat(imdecode_0(buf.nativeObj, flags));
    }

    public static boolean imencode(String ext, Mat img, MatOfByte buf, MatOfInt params) {
        return imencode_0(ext, img.nativeObj, buf.nativeObj, params.nativeObj);
    }

    public static boolean imencode(String ext, Mat img, MatOfByte buf) {
        return imencode_1(ext, img.nativeObj, buf.nativeObj);
    }

    public static boolean haveImageReader(String filename) {
        return haveImageReader_0(filename);
    }

    public static boolean haveImageWriter(String filename) {
        return haveImageWriter_0(filename);
    }
}
