package org.opencv.core;

import java.nio.ByteBuffer;
import org.ros.node.topic.Subscriber;

public class Mat {
    public final long nativeObj;

    public interface Atable<T> {
        T getV();

        Tuple2<T> getV2c();

        Tuple3<T> getV3c();

        Tuple4<T> getV4c();

        void setV(T t);

        void setV2c(Tuple2<T> tuple2);

        void setV3c(Tuple3<T> tuple3);

        void setV4c(Tuple4<T> tuple4);
    }

    private static native void locateROI_0(long j, double[] dArr, double[] dArr2);

    private static native String nDump(long j);

    private static native double[] nGet(long j, int i, int i2);

    private static native int nGetB(long j, int i, int i2, int i3, byte[] bArr);

    private static native int nGetBIdx(long j, int[] iArr, int i, byte[] bArr);

    private static native int nGetD(long j, int i, int i2, int i3, double[] dArr);

    private static native int nGetDIdx(long j, int[] iArr, int i, double[] dArr);

    private static native int nGetF(long j, int i, int i2, int i3, float[] fArr);

    private static native int nGetFIdx(long j, int[] iArr, int i, float[] fArr);

    private static native int nGetI(long j, int i, int i2, int i3, int[] iArr);

    private static native int nGetIIdx(long j, int[] iArr, int i, int[] iArr2);

    private static native double[] nGetIdx(long j, int[] iArr);

    private static native int nGetS(long j, int i, int i2, int i3, short[] sArr);

    private static native int nGetSIdx(long j, int[] iArr, int i, short[] sArr);

    private static native int nPutB(long j, int i, int i2, int i3, byte[] bArr);

    private static native int nPutBIdx(long j, int[] iArr, int i, byte[] bArr);

    private static native int nPutBwIdxOffset(long j, int[] iArr, int i, int i2, byte[] bArr);

    private static native int nPutBwOffset(long j, int i, int i2, int i3, int i4, byte[] bArr);

    private static native int nPutD(long j, int i, int i2, int i3, double[] dArr);

    private static native int nPutDIdx(long j, int[] iArr, int i, double[] dArr);

    private static native int nPutF(long j, int i, int i2, int i3, float[] fArr);

    private static native int nPutFIdx(long j, int[] iArr, int i, float[] fArr);

    private static native int nPutI(long j, int i, int i2, int i3, int[] iArr);

    private static native int nPutIIdx(long j, int[] iArr, int i, int[] iArr2);

    private static native int nPutS(long j, int i, int i2, int i3, short[] sArr);

    private static native int nPutSIdx(long j, int[] iArr, int i, short[] sArr);

    private static native long n_Mat();

    private static native long n_Mat(double d, double d2, int i);

    private static native long n_Mat(double d, double d2, int i, double d3, double d4, double d5, double d6);

    private static native long n_Mat(int i, int i2, int i3);

    private static native long n_Mat(int i, int i2, int i3, double d, double d2, double d3, double d4);

    private static native long n_Mat(int i, int i2, int i3, ByteBuffer byteBuffer);

    private static native long n_Mat(int i, int i2, int i3, ByteBuffer byteBuffer, long j);

    private static native long n_Mat(int i, int[] iArr, int i2);

    private static native long n_Mat(int i, int[] iArr, int i2, double d, double d2, double d3, double d4);

    private static native long n_Mat(long j, int i, int i2);

    private static native long n_Mat(long j, int i, int i2, int i3, int i4);

    private static native long n_Mat(long j, Range[] rangeArr);

    private static native long n_adjustROI(long j, int i, int i2, int i3, int i4);

    private static native void n_assignTo(long j, long j2);

    private static native void n_assignTo(long j, long j2, int i);

    private static native int n_channels(long j);

    private static native int n_checkVector(long j, int i);

    private static native int n_checkVector(long j, int i, int i2);

    private static native int n_checkVector(long j, int i, int i2, boolean z);

    private static native long n_clone(long j);

    private static native long n_col(long j, int i);

    private static native long n_colRange(long j, int i, int i2);

    private static native int n_cols(long j);

    private static native void n_convertTo(long j, long j2, int i);

    private static native void n_convertTo(long j, long j2, int i, double d);

    private static native void n_convertTo(long j, long j2, int i, double d, double d2);

    private static native void n_copySize(long j, long j2);

    private static native void n_copyTo(long j, long j2);

    private static native void n_copyTo(long j, long j2, long j3);

    private static native void n_create(long j, double d, double d2, int i);

    private static native void n_create(long j, int i, int i2, int i3);

    private static native void n_create(long j, int i, int[] iArr, int i2);

    private static native long n_cross(long j, long j2);

    private static native long n_dataAddr(long j);

    private static native void n_delete(long j);

    private static native int n_depth(long j);

    private static native long n_diag(long j);

    private static native long n_diag(long j, int i);

    private static native int n_dims(long j);

    private static native double n_dot(long j, long j2);

    private static native long n_elemSize(long j);

    private static native long n_elemSize1(long j);

    private static native boolean n_empty(long j);

    private static native long n_eye(double d, double d2, int i);

    private static native long n_eye(int i, int i2, int i3);

    private static native long n_inv(long j);

    private static native long n_inv(long j, int i);

    private static native boolean n_isContinuous(long j);

    private static native boolean n_isSubmatrix(long j);

    private static native long n_mul(long j, long j2);

    private static native long n_mul(long j, long j2, double d);

    private static native long n_ones(double d, double d2, int i);

    private static native long n_ones(int i, int i2, int i3);

    private static native long n_ones(int i, int[] iArr, int i2);

    private static native void n_push_back(long j, long j2);

    private static native void n_release(long j);

    private static native long n_reshape(long j, int i);

    private static native long n_reshape(long j, int i, int i2);

    private static native long n_reshape_1(long j, int i, int i2, int[] iArr);

    private static native long n_row(long j, int i);

    private static native long n_rowRange(long j, int i, int i2);

    private static native int n_rows(long j);

    private static native long n_setTo(long j, double d, double d2, double d3, double d4);

    private static native long n_setTo(long j, double d, double d2, double d3, double d4, long j2);

    private static native long n_setTo(long j, long j2);

    private static native long n_setTo(long j, long j2, long j3);

    private static native double[] n_size(long j);

    private static native int n_size_i(long j, int i);

    private static native long n_step1(long j);

    private static native long n_step1(long j, int i);

    private static native long n_submat(long j, int i, int i2, int i3, int i4);

    private static native long n_submat_ranges(long j, Range[] rangeArr);

    private static native long n_submat_rr(long j, int i, int i2, int i3, int i4);

    private static native long n_t(long j);

    private static native long n_total(long j);

    private static native int n_type(long j);

    private static native long n_zeros(double d, double d2, int i);

    private static native long n_zeros(int i, int i2, int i3);

    private static native long n_zeros(int i, int[] iArr, int i2);

    public Mat(long addr) {
        if (addr != 0) {
            this.nativeObj = addr;
            return;
        }
        throw new UnsupportedOperationException("Native object address is NULL");
    }

    public Mat() {
        this.nativeObj = n_Mat();
    }

    public Mat(int rows, int cols, int type) {
        this.nativeObj = n_Mat(rows, cols, type);
    }

    public Mat(int rows, int cols, int type, ByteBuffer data) {
        this.nativeObj = n_Mat(rows, cols, type, data);
    }

    public Mat(int rows, int cols, int type, ByteBuffer data, long step) {
        this.nativeObj = n_Mat(rows, cols, type, data, step);
    }

    public Mat(Size size, int type) {
        this.nativeObj = n_Mat(size.width, size.height, type);
    }

    public Mat(int[] sizes, int type) {
        this.nativeObj = n_Mat(sizes.length, sizes, type);
    }

    public Mat(int rows, int cols, int type, Scalar s) {
        Scalar scalar = s;
        this.nativeObj = n_Mat(rows, cols, type, scalar.val[0], scalar.val[1], scalar.val[2], scalar.val[3]);
    }

    public Mat(Size size, int type, Scalar s) {
        Size size2 = size;
        Scalar scalar = s;
        this.nativeObj = n_Mat(size2.width, size2.height, type, scalar.val[0], scalar.val[1], scalar.val[2], scalar.val[3]);
    }

    public Mat(int[] sizes, int type, Scalar s) {
        this.nativeObj = n_Mat(sizes.length, sizes, type, s.val[0], s.val[1], s.val[2], s.val[3]);
    }

    public Mat(Mat m, Range rowRange, Range colRange) {
        this.nativeObj = n_Mat(m.nativeObj, rowRange.start, rowRange.end, colRange.start, colRange.end);
    }

    public Mat(Mat m, Range rowRange) {
        this.nativeObj = n_Mat(m.nativeObj, rowRange.start, rowRange.end);
    }

    public Mat(Mat m, Range[] ranges) {
        this.nativeObj = n_Mat(m.nativeObj, ranges);
    }

    public Mat(Mat m, Rect roi) {
        this.nativeObj = n_Mat(m.nativeObj, roi.f181y, roi.f181y + roi.height, roi.f180x, roi.f180x + roi.width);
    }

    public Mat adjustROI(int dtop, int dbottom, int dleft, int dright) {
        return new Mat(n_adjustROI(this.nativeObj, dtop, dbottom, dleft, dright));
    }

    public void assignTo(Mat m, int type) {
        n_assignTo(this.nativeObj, m.nativeObj, type);
    }

    public void assignTo(Mat m) {
        n_assignTo(this.nativeObj, m.nativeObj);
    }

    public int channels() {
        return n_channels(this.nativeObj);
    }

    public int checkVector(int elemChannels, int depth, boolean requireContinuous) {
        return n_checkVector(this.nativeObj, elemChannels, depth, requireContinuous);
    }

    public int checkVector(int elemChannels, int depth) {
        return n_checkVector(this.nativeObj, elemChannels, depth);
    }

    public int checkVector(int elemChannels) {
        return n_checkVector(this.nativeObj, elemChannels);
    }

    public Mat clone() {
        return new Mat(n_clone(this.nativeObj));
    }

    public Mat col(int x) {
        return new Mat(n_col(this.nativeObj, x));
    }

    public Mat colRange(int startcol, int endcol) {
        return new Mat(n_colRange(this.nativeObj, startcol, endcol));
    }

    public Mat colRange(Range r) {
        return new Mat(n_colRange(this.nativeObj, r.start, r.end));
    }

    public int dims() {
        return n_dims(this.nativeObj);
    }

    public int cols() {
        return n_cols(this.nativeObj);
    }

    public void convertTo(Mat m, int rtype, double alpha, double beta) {
        n_convertTo(this.nativeObj, m.nativeObj, rtype, alpha, beta);
    }

    public void convertTo(Mat m, int rtype, double alpha) {
        n_convertTo(this.nativeObj, m.nativeObj, rtype, alpha);
    }

    public void convertTo(Mat m, int rtype) {
        n_convertTo(this.nativeObj, m.nativeObj, rtype);
    }

    public void copyTo(Mat m) {
        n_copyTo(this.nativeObj, m.nativeObj);
    }

    public void copyTo(Mat m, Mat mask) {
        n_copyTo(this.nativeObj, m.nativeObj, mask.nativeObj);
    }

    public void create(int rows, int cols, int type) {
        n_create(this.nativeObj, rows, cols, type);
    }

    public void create(Size size, int type) {
        n_create(this.nativeObj, size.width, size.height, type);
    }

    public void create(int[] sizes, int type) {
        n_create(this.nativeObj, sizes.length, sizes, type);
    }

    public void copySize(Mat m) {
        n_copySize(this.nativeObj, m.nativeObj);
    }

    public Mat cross(Mat m) {
        return new Mat(n_cross(this.nativeObj, m.nativeObj));
    }

    public long dataAddr() {
        return n_dataAddr(this.nativeObj);
    }

    public int depth() {
        return n_depth(this.nativeObj);
    }

    public Mat diag(int d) {
        return new Mat(n_diag(this.nativeObj, d));
    }

    public Mat diag() {
        return new Mat(n_diag(this.nativeObj, 0));
    }

    public static Mat diag(Mat d) {
        return new Mat(n_diag(d.nativeObj));
    }

    public double dot(Mat m) {
        return n_dot(this.nativeObj, m.nativeObj);
    }

    public long elemSize() {
        return n_elemSize(this.nativeObj);
    }

    public long elemSize1() {
        return n_elemSize1(this.nativeObj);
    }

    public boolean empty() {
        return n_empty(this.nativeObj);
    }

    public static Mat eye(int rows, int cols, int type) {
        return new Mat(n_eye(rows, cols, type));
    }

    public static Mat eye(Size size, int type) {
        return new Mat(n_eye(size.width, size.height, type));
    }

    public Mat inv(int method) {
        return new Mat(n_inv(this.nativeObj, method));
    }

    public Mat inv() {
        return new Mat(n_inv(this.nativeObj));
    }

    public boolean isContinuous() {
        return n_isContinuous(this.nativeObj);
    }

    public boolean isSubmatrix() {
        return n_isSubmatrix(this.nativeObj);
    }

    public void locateROI(Size wholeSize, Point ofs) {
        double[] wholeSize_out = new double[2];
        double[] ofs_out = new double[2];
        locateROI_0(this.nativeObj, wholeSize_out, ofs_out);
        if (wholeSize != null) {
            wholeSize.width = wholeSize_out[0];
            wholeSize.height = wholeSize_out[1];
        }
        if (ofs != null) {
            ofs.f175x = ofs_out[0];
            ofs.f176y = ofs_out[1];
        }
    }

    public Mat mul(Mat m, double scale) {
        return new Mat(n_mul(this.nativeObj, m.nativeObj, scale));
    }

    public Mat mul(Mat m) {
        return new Mat(n_mul(this.nativeObj, m.nativeObj));
    }

    public static Mat ones(int rows, int cols, int type) {
        return new Mat(n_ones(rows, cols, type));
    }

    public static Mat ones(Size size, int type) {
        return new Mat(n_ones(size.width, size.height, type));
    }

    public static Mat ones(int[] sizes, int type) {
        return new Mat(n_ones(sizes.length, sizes, type));
    }

    public void push_back(Mat m) {
        n_push_back(this.nativeObj, m.nativeObj);
    }

    public void release() {
        n_release(this.nativeObj);
    }

    public Mat reshape(int cn, int rows) {
        return new Mat(n_reshape(this.nativeObj, cn, rows));
    }

    public Mat reshape(int cn) {
        return new Mat(n_reshape(this.nativeObj, cn));
    }

    public Mat reshape(int cn, int[] newshape) {
        return new Mat(n_reshape_1(this.nativeObj, cn, newshape.length, newshape));
    }

    public Mat row(int y) {
        return new Mat(n_row(this.nativeObj, y));
    }

    public Mat rowRange(int startrow, int endrow) {
        return new Mat(n_rowRange(this.nativeObj, startrow, endrow));
    }

    public Mat rowRange(Range r) {
        return new Mat(n_rowRange(this.nativeObj, r.start, r.end));
    }

    public int rows() {
        return n_rows(this.nativeObj);
    }

    public Mat setTo(Scalar s) {
        return new Mat(n_setTo(this.nativeObj, s.val[0], s.val[1], s.val[2], s.val[3]));
    }

    public Mat setTo(Scalar value, Mat mask) {
        Scalar scalar = value;
        return new Mat(n_setTo(this.nativeObj, scalar.val[0], scalar.val[1], scalar.val[2], scalar.val[3], mask.nativeObj));
    }

    public Mat setTo(Mat value, Mat mask) {
        return new Mat(n_setTo(this.nativeObj, value.nativeObj, mask.nativeObj));
    }

    public Mat setTo(Mat value) {
        return new Mat(n_setTo(this.nativeObj, value.nativeObj));
    }

    public Size size() {
        return new Size(n_size(this.nativeObj));
    }

    public int size(int i) {
        return n_size_i(this.nativeObj, i);
    }

    public long step1(int i) {
        return n_step1(this.nativeObj, i);
    }

    public long step1() {
        return n_step1(this.nativeObj);
    }

    public Mat submat(int rowStart, int rowEnd, int colStart, int colEnd) {
        return new Mat(n_submat_rr(this.nativeObj, rowStart, rowEnd, colStart, colEnd));
    }

    public Mat submat(Range rowRange, Range colRange) {
        return new Mat(n_submat_rr(this.nativeObj, rowRange.start, rowRange.end, colRange.start, colRange.end));
    }

    public Mat submat(Range[] ranges) {
        return new Mat(n_submat_ranges(this.nativeObj, ranges));
    }

    public Mat submat(Rect roi) {
        return new Mat(n_submat(this.nativeObj, roi.f180x, roi.f181y, roi.width, roi.height));
    }

    /* renamed from: t */
    public Mat mo16295t() {
        return new Mat(n_t(this.nativeObj));
    }

    public long total() {
        return n_total(this.nativeObj);
    }

    public int type() {
        return n_type(this.nativeObj);
    }

    public static Mat zeros(int rows, int cols, int type) {
        return new Mat(n_zeros(rows, cols, type));
    }

    public static Mat zeros(Size size, int type) {
        return new Mat(n_zeros(size.width, size.height, type));
    }

    public static Mat zeros(int[] sizes, int type) {
        return new Mat(n_zeros(sizes.length, sizes, type));
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        n_delete(this.nativeObj);
        super.finalize();
    }

    public String toString() {
        String _dims = dims() > 0 ? "" : "-1*-1*";
        for (int i = 0; i < dims(); i++) {
            _dims = _dims + size(i) + Subscriber.TOPIC_MESSAGE_TYPE_WILDCARD;
        }
        return "Mat [ " + _dims + CvType.typeToString(type()) + ", isCont=" + isContinuous() + ", isSubmat=" + isSubmatrix() + ", nativeObj=0x" + Long.toHexString(this.nativeObj) + ", dataAddr=0x" + Long.toHexString(dataAddr()) + " ]";
    }

    public String dump() {
        return nDump(this.nativeObj);
    }

    public int put(int row, int col, double... data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        }
        return nPutD(this.nativeObj, row, col, data.length, data);
    }

    public int put(int[] idx, double... data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (idx.length == dims()) {
            return nPutDIdx(this.nativeObj, idx, data.length, data);
        } else {
            throw new IllegalArgumentException("Incorrect number of indices");
        }
    }

    public int put(int row, int col, float[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (CvType.depth(t) == 5) {
            return nPutF(this.nativeObj, row, col, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int put(int[] idx, float[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (idx.length != dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        } else if (CvType.depth(t) == 5) {
            return nPutFIdx(this.nativeObj, idx, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int put(int row, int col, int[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (CvType.depth(t) == 4) {
            return nPutI(this.nativeObj, row, col, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int put(int[] idx, int[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (idx.length != dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        } else if (CvType.depth(t) == 4) {
            return nPutIIdx(this.nativeObj, idx, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int put(int row, int col, short[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (CvType.depth(t) == 2 || CvType.depth(t) == 3) {
            return nPutS(this.nativeObj, row, col, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int put(int[] idx, short[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (idx.length != dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        } else if (CvType.depth(t) == 2 || CvType.depth(t) == 3) {
            return nPutSIdx(this.nativeObj, idx, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int put(int row, int col, byte[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (CvType.depth(t) == 0 || CvType.depth(t) == 1) {
            return nPutB(this.nativeObj, row, col, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int put(int[] idx, byte[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (idx.length != dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        } else if (CvType.depth(t) == 0 || CvType.depth(t) == 1) {
            return nPutBIdx(this.nativeObj, idx, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int put(int row, int col, byte[] data, int offset, int length) {
        int t = type();
        if (data == null || length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (CvType.depth(t) == 0 || CvType.depth(t) == 1) {
            return nPutBwOffset(this.nativeObj, row, col, length, offset, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int put(int[] idx, byte[] data, int offset, int length) {
        int t = type();
        if (data == null || length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (idx.length != dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        } else if (CvType.depth(t) == 0 || CvType.depth(t) == 1) {
            return nPutBwIdxOffset(this.nativeObj, idx, length, offset, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int get(int row, int col, byte[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (CvType.depth(t) == 0 || CvType.depth(t) == 1) {
            return nGetB(this.nativeObj, row, col, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int get(int[] idx, byte[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (idx.length != dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        } else if (CvType.depth(t) == 0 || CvType.depth(t) == 1) {
            return nGetBIdx(this.nativeObj, idx, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int get(int row, int col, short[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (CvType.depth(t) == 2 || CvType.depth(t) == 3) {
            return nGetS(this.nativeObj, row, col, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int get(int[] idx, short[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (idx.length != dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        } else if (CvType.depth(t) == 2 || CvType.depth(t) == 3) {
            return nGetSIdx(this.nativeObj, idx, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int get(int row, int col, int[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (CvType.depth(t) == 4) {
            return nGetI(this.nativeObj, row, col, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int get(int[] idx, int[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (idx.length != dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        } else if (CvType.depth(t) == 4) {
            return nGetIIdx(this.nativeObj, idx, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int get(int row, int col, float[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (CvType.depth(t) == 5) {
            return nGetF(this.nativeObj, row, col, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int get(int[] idx, float[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (idx.length != dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        } else if (CvType.depth(t) == 5) {
            return nGetFIdx(this.nativeObj, idx, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int get(int row, int col, double[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (CvType.depth(t) == 6) {
            return nGetD(this.nativeObj, row, col, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public int get(int[] idx, double[] data) {
        int t = type();
        if (data == null || data.length % CvType.channels(t) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Provided data element number (");
            sb.append(data == null ? 0 : data.length);
            sb.append(") should be multiple of the Mat channels count (");
            sb.append(CvType.channels(t));
            sb.append(")");
            throw new UnsupportedOperationException(sb.toString());
        } else if (idx.length != dims()) {
            throw new IllegalArgumentException("Incorrect number of indices");
        } else if (CvType.depth(t) == 6) {
            return nGetDIdx(this.nativeObj, idx, data.length, data);
        } else {
            throw new UnsupportedOperationException("Mat data type is not compatible: " + t);
        }
    }

    public double[] get(int row, int col) {
        return nGet(this.nativeObj, row, col);
    }

    public double[] get(int[] idx) {
        if (idx.length == dims()) {
            return nGetIdx(this.nativeObj, idx);
        }
        throw new IllegalArgumentException("Incorrect number of indices");
    }

    public int height() {
        return rows();
    }

    public int width() {
        return cols();
    }

    /* renamed from: at */
    public <T> Atable<T> mo16209at(Class<T> clazz, int row, int col) {
        if (clazz == Byte.class || clazz == Byte.TYPE) {
            return new AtableByte(this, row, col);
        }
        if (clazz == Double.class || clazz == Double.TYPE) {
            return new AtableDouble(this, row, col);
        }
        if (clazz == Float.class || clazz == Float.TYPE) {
            return new AtableFloat(this, row, col);
        }
        if (clazz == Integer.class || clazz == Integer.TYPE) {
            return new AtableInteger(this, row, col);
        }
        if (clazz == Short.class || clazz == Short.TYPE) {
            return new AtableShort(this, row, col);
        }
        throw new RuntimeException("Unsupported class type");
    }

    /* renamed from: at */
    public <T> Atable<T> mo16210at(Class<T> clazz, int[] idx) {
        if (clazz == Byte.class || clazz == Byte.TYPE) {
            return new AtableByte(this, idx);
        }
        if (clazz == Double.class || clazz == Double.TYPE) {
            return new AtableDouble(this, idx);
        }
        if (clazz == Float.class || clazz == Float.TYPE) {
            return new AtableFloat(this, idx);
        }
        if (clazz == Integer.class || clazz == Integer.TYPE) {
            return new AtableInteger(this, idx);
        }
        if (clazz == Short.class || clazz == Short.TYPE) {
            return new AtableShort(this, idx);
        }
        throw new RuntimeException("Unsupported class parameter");
    }

    public static class Tuple2<T> {
        /* access modifiers changed from: private */

        /* renamed from: _0 */
        public final T f166_0;
        /* access modifiers changed from: private */

        /* renamed from: _1 */
        public final T f167_1;

        public Tuple2(T _0, T _1) {
            this.f166_0 = _0;
            this.f167_1 = _1;
        }

        public T get_0() {
            return this.f166_0;
        }

        public T get_1() {
            return this.f167_1;
        }
    }

    public static class Tuple3<T> {
        /* access modifiers changed from: private */

        /* renamed from: _0 */
        public final T f168_0;
        /* access modifiers changed from: private */

        /* renamed from: _1 */
        public final T f169_1;
        /* access modifiers changed from: private */

        /* renamed from: _2 */
        public final T f170_2;

        public Tuple3(T _0, T _1, T _2) {
            this.f168_0 = _0;
            this.f169_1 = _1;
            this.f170_2 = _2;
        }

        public T get_0() {
            return this.f168_0;
        }

        public T get_1() {
            return this.f169_1;
        }

        public T get_2() {
            return this.f170_2;
        }
    }

    public static class Tuple4<T> {
        /* access modifiers changed from: private */

        /* renamed from: _0 */
        public final T f171_0;
        /* access modifiers changed from: private */

        /* renamed from: _1 */
        public final T f172_1;
        /* access modifiers changed from: private */

        /* renamed from: _2 */
        public final T f173_2;
        /* access modifiers changed from: private */

        /* renamed from: _3 */
        public final T f174_3;

        public Tuple4(T _0, T _1, T _2, T _3) {
            this.f171_0 = _0;
            this.f172_1 = _1;
            this.f173_2 = _2;
            this.f174_3 = _3;
        }

        public T get_0() {
            return this.f171_0;
        }

        public T get_1() {
            return this.f172_1;
        }

        public T get_2() {
            return this.f173_2;
        }

        public T get_3() {
            return this.f174_3;
        }
    }

    private static class AtableBase {
        protected final int[] indices;
        protected final Mat mat;

        protected AtableBase(Mat mat2, int row, int col) {
            this.mat = mat2;
            int[] iArr = new int[2];
            this.indices = iArr;
            iArr[0] = row;
            iArr[1] = col;
        }

        protected AtableBase(Mat mat2, int[] indices2) {
            this.mat = mat2;
            this.indices = indices2;
        }
    }

    private static class AtableByte extends AtableBase implements Atable<Byte> {
        public AtableByte(Mat mat, int row, int col) {
            super(mat, row, col);
        }

        public AtableByte(Mat mat, int[] indices) {
            super(mat, indices);
        }

        public Byte getV() {
            byte[] data = new byte[1];
            this.mat.get(this.indices, data);
            return Byte.valueOf(data[0]);
        }

        public void setV(Byte v) {
            this.mat.put(this.indices, new byte[]{v.byteValue()});
        }

        public Tuple2<Byte> getV2c() {
            byte[] data = new byte[2];
            this.mat.get(this.indices, data);
            return new Tuple2<>(Byte.valueOf(data[0]), Byte.valueOf(data[1]));
        }

        public void setV2c(Tuple2<Byte> v) {
            this.mat.put(this.indices, new byte[]{((Byte) v.f166_0).byteValue(), ((Byte) v.f167_1).byteValue()});
        }

        public Tuple3<Byte> getV3c() {
            byte[] data = new byte[3];
            this.mat.get(this.indices, data);
            return new Tuple3<>(Byte.valueOf(data[0]), Byte.valueOf(data[1]), Byte.valueOf(data[2]));
        }

        public void setV3c(Tuple3<Byte> v) {
            this.mat.put(this.indices, new byte[]{((Byte) v.f168_0).byteValue(), ((Byte) v.f169_1).byteValue(), ((Byte) v.f170_2).byteValue()});
        }

        public Tuple4<Byte> getV4c() {
            byte[] data = new byte[4];
            this.mat.get(this.indices, data);
            return new Tuple4<>(Byte.valueOf(data[0]), Byte.valueOf(data[1]), Byte.valueOf(data[2]), Byte.valueOf(data[3]));
        }

        public void setV4c(Tuple4<Byte> v) {
            this.mat.put(this.indices, new byte[]{((Byte) v.f171_0).byteValue(), ((Byte) v.f172_1).byteValue(), ((Byte) v.f173_2).byteValue(), ((Byte) v.f174_3).byteValue()});
        }
    }

    private static class AtableDouble extends AtableBase implements Atable<Double> {
        public AtableDouble(Mat mat, int row, int col) {
            super(mat, row, col);
        }

        public AtableDouble(Mat mat, int[] indices) {
            super(mat, indices);
        }

        public Double getV() {
            double[] data = new double[1];
            this.mat.get(this.indices, data);
            return Double.valueOf(data[0]);
        }

        public void setV(Double v) {
            this.mat.put(this.indices, v.doubleValue());
        }

        public Tuple2<Double> getV2c() {
            double[] data = new double[2];
            this.mat.get(this.indices, data);
            return new Tuple2<>(Double.valueOf(data[0]), Double.valueOf(data[1]));
        }

        public void setV2c(Tuple2<Double> v) {
            this.mat.put(this.indices, ((Double) v.f166_0).doubleValue(), ((Double) v.f167_1).doubleValue());
        }

        public Tuple3<Double> getV3c() {
            double[] data = new double[3];
            this.mat.get(this.indices, data);
            return new Tuple3<>(Double.valueOf(data[0]), Double.valueOf(data[1]), Double.valueOf(data[2]));
        }

        public void setV3c(Tuple3<Double> v) {
            this.mat.put(this.indices, ((Double) v.f168_0).doubleValue(), ((Double) v.f169_1).doubleValue(), ((Double) v.f170_2).doubleValue());
        }

        public Tuple4<Double> getV4c() {
            double[] data = new double[4];
            this.mat.get(this.indices, data);
            return new Tuple4<>(Double.valueOf(data[0]), Double.valueOf(data[1]), Double.valueOf(data[2]), Double.valueOf(data[3]));
        }

        public void setV4c(Tuple4<Double> v) {
            this.mat.put(this.indices, ((Double) v.f171_0).doubleValue(), ((Double) v.f172_1).doubleValue(), ((Double) v.f173_2).doubleValue(), ((Double) v.f174_3).doubleValue());
        }
    }

    private static class AtableFloat extends AtableBase implements Atable<Float> {
        public AtableFloat(Mat mat, int row, int col) {
            super(mat, row, col);
        }

        public AtableFloat(Mat mat, int[] indices) {
            super(mat, indices);
        }

        public Float getV() {
            float[] data = new float[1];
            this.mat.get(this.indices, data);
            return Float.valueOf(data[0]);
        }

        public void setV(Float v) {
            this.mat.put(this.indices, new float[]{v.floatValue()});
        }

        public Tuple2<Float> getV2c() {
            float[] data = new float[2];
            this.mat.get(this.indices, data);
            return new Tuple2<>(Float.valueOf(data[0]), Float.valueOf(data[1]));
        }

        public void setV2c(Tuple2<Float> v) {
            this.mat.put(this.indices, new float[]{((Float) v.f166_0).floatValue(), ((Float) v.f167_1).floatValue()});
        }

        public Tuple3<Float> getV3c() {
            float[] data = new float[3];
            this.mat.get(this.indices, data);
            return new Tuple3<>(Float.valueOf(data[0]), Float.valueOf(data[1]), Float.valueOf(data[2]));
        }

        public void setV3c(Tuple3<Float> v) {
            this.mat.put(this.indices, new float[]{((Float) v.f168_0).floatValue(), ((Float) v.f169_1).floatValue(), ((Float) v.f170_2).floatValue()});
        }

        public Tuple4<Float> getV4c() {
            float[] data = new float[4];
            this.mat.get(this.indices, data);
            return new Tuple4<>(Float.valueOf(data[0]), Float.valueOf(data[1]), Float.valueOf(data[2]), Float.valueOf(data[3]));
        }

        public void setV4c(Tuple4<Float> v) {
            this.mat.put(this.indices, (double) ((Float) v.f171_0).floatValue(), (double) ((Float) v.f172_1).floatValue(), (double) ((Float) v.f173_2).floatValue(), (double) ((Float) v.f174_3).floatValue());
        }
    }

    private static class AtableInteger extends AtableBase implements Atable<Integer> {
        public AtableInteger(Mat mat, int row, int col) {
            super(mat, row, col);
        }

        public AtableInteger(Mat mat, int[] indices) {
            super(mat, indices);
        }

        public Integer getV() {
            int[] data = new int[1];
            this.mat.get(this.indices, data);
            return Integer.valueOf(data[0]);
        }

        public void setV(Integer v) {
            this.mat.put(this.indices, new int[]{v.intValue()});
        }

        public Tuple2<Integer> getV2c() {
            int[] data = new int[2];
            this.mat.get(this.indices, data);
            return new Tuple2<>(Integer.valueOf(data[0]), Integer.valueOf(data[1]));
        }

        public void setV2c(Tuple2<Integer> v) {
            this.mat.put(this.indices, new int[]{((Integer) v.f166_0).intValue(), ((Integer) v.f167_1).intValue()});
        }

        public Tuple3<Integer> getV3c() {
            int[] data = new int[3];
            this.mat.get(this.indices, data);
            return new Tuple3<>(Integer.valueOf(data[0]), Integer.valueOf(data[1]), Integer.valueOf(data[2]));
        }

        public void setV3c(Tuple3<Integer> v) {
            this.mat.put(this.indices, new int[]{((Integer) v.f168_0).intValue(), ((Integer) v.f169_1).intValue(), ((Integer) v.f170_2).intValue()});
        }

        public Tuple4<Integer> getV4c() {
            int[] data = new int[4];
            this.mat.get(this.indices, data);
            return new Tuple4<>(Integer.valueOf(data[0]), Integer.valueOf(data[1]), Integer.valueOf(data[2]), Integer.valueOf(data[3]));
        }

        public void setV4c(Tuple4<Integer> v) {
            this.mat.put(this.indices, new int[]{((Integer) v.f171_0).intValue(), ((Integer) v.f172_1).intValue(), ((Integer) v.f173_2).intValue(), ((Integer) v.f174_3).intValue()});
        }
    }

    private static class AtableShort extends AtableBase implements Atable<Short> {
        public AtableShort(Mat mat, int row, int col) {
            super(mat, row, col);
        }

        public AtableShort(Mat mat, int[] indices) {
            super(mat, indices);
        }

        public Short getV() {
            short[] data = new short[1];
            this.mat.get(this.indices, data);
            return Short.valueOf(data[0]);
        }

        public void setV(Short v) {
            this.mat.put(this.indices, new short[]{v.shortValue()});
        }

        public Tuple2<Short> getV2c() {
            short[] data = new short[2];
            this.mat.get(this.indices, data);
            return new Tuple2<>(Short.valueOf(data[0]), Short.valueOf(data[1]));
        }

        public void setV2c(Tuple2<Short> v) {
            this.mat.put(this.indices, new short[]{((Short) v.f166_0).shortValue(), ((Short) v.f167_1).shortValue()});
        }

        public Tuple3<Short> getV3c() {
            short[] data = new short[3];
            this.mat.get(this.indices, data);
            return new Tuple3<>(Short.valueOf(data[0]), Short.valueOf(data[1]), Short.valueOf(data[2]));
        }

        public void setV3c(Tuple3<Short> v) {
            this.mat.put(this.indices, new short[]{((Short) v.f168_0).shortValue(), ((Short) v.f169_1).shortValue(), ((Short) v.f170_2).shortValue()});
        }

        public Tuple4<Short> getV4c() {
            short[] data = new short[4];
            this.mat.get(this.indices, data);
            return new Tuple4<>(Short.valueOf(data[0]), Short.valueOf(data[1]), Short.valueOf(data[2]), Short.valueOf(data[3]));
        }

        public void setV4c(Tuple4<Short> v) {
            this.mat.put(this.indices, new short[]{((Short) v.f171_0).shortValue(), ((Short) v.f172_1).shortValue(), ((Short) v.f173_2).shortValue(), ((Short) v.f174_3).shortValue()});
        }
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }
}
