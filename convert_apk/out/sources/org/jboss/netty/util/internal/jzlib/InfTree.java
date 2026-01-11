package org.jboss.netty.util.internal.jzlib;

import android.support.p002v4.app.FragmentTransaction;
import android.support.p002v4.view.InputDeviceCompat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.xbill.DNS.Type;
import org.xbill.DNS.WKSRecord;

final class InfTree {
    static final int BMAX = 15;
    static final int[] cpdext = {0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13};
    static final int[] cpdist = {1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193, 257, 385, 513, 769, InputDeviceCompat.SOURCE_GAMEPAD, 1537, 2049, 3073, FragmentTransaction.TRANSIT_FRAGMENT_OPEN, 6145, 8193, 12289, 16385, 24577};
    static final int[] cplens = {3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31, 35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, Imgcodecs.IMWRITE_TIFF_YDPI, 0, 0};
    static final int[] cplext = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0, 112, 112};
    static final int fixed_bd = 5;
    static final int fixed_bl = 9;
    static final int[] fixed_td = {80, 5, 1, 87, 5, 257, 83, 5, 17, 91, 5, FragmentTransaction.TRANSIT_FRAGMENT_OPEN, 81, 5, 5, 89, 5, InputDeviceCompat.SOURCE_GAMEPAD, 85, 5, 65, 93, 5, 16385, 80, 5, 3, 88, 5, 513, 84, 5, 33, 92, 5, 8193, 82, 5, 9, 90, 5, 2049, 86, 5, 129, 192, 5, 24577, 80, 5, 2, 87, 5, 385, 83, 5, 25, 91, 5, 6145, 81, 5, 7, 89, 5, 1537, 85, 5, 97, 93, 5, 24577, 80, 5, 4, 88, 5, 769, 84, 5, 49, 92, 5, 12289, 82, 5, 13, 90, 5, 3073, 86, 5, 193, 192, 5, 24577};
    static final int[] fixed_tl = {96, 7, 256, 0, 8, 80, 0, 8, 16, 84, 8, 115, 82, 7, 31, 0, 8, 112, 0, 8, 48, 0, 9, 192, 80, 7, 10, 0, 8, 96, 0, 8, 32, 0, 9, 160, 0, 8, 0, 0, 8, 128, 0, 8, 64, 0, 9, 224, 80, 7, 6, 0, 8, 88, 0, 8, 24, 0, 9, 144, 83, 7, 59, 0, 8, 120, 0, 8, 56, 0, 9, 208, 81, 7, 17, 0, 8, 104, 0, 8, 40, 0, 9, 176, 0, 8, 8, 0, 8, 136, 0, 8, 72, 0, 9, 240, 80, 7, 4, 0, 8, 84, 0, 8, 20, 85, 8, 227, 83, 7, 43, 0, 8, 116, 0, 8, 52, 0, 9, 200, 81, 7, 13, 0, 8, 100, 0, 8, 36, 0, 9, 168, 0, 8, 4, 0, 8, 132, 0, 8, 68, 0, 9, 232, 80, 7, 8, 0, 8, 92, 0, 8, 28, 0, 9, 152, 84, 7, 83, 0, 8, 124, 0, 8, 60, 0, 9, 216, 82, 7, 23, 0, 8, 108, 0, 8, 44, 0, 9, 184, 0, 8, 12, 0, 8, 140, 0, 8, 76, 0, 9, 248, 80, 7, 3, 0, 8, 82, 0, 8, 18, 85, 8, 163, 83, 7, 35, 0, 8, 114, 0, 8, 50, 0, 9, 196, 81, 7, 11, 0, 8, 98, 0, 8, 34, 0, 9, 164, 0, 8, 2, 0, 8, 130, 0, 8, 66, 0, 9, 228, 80, 7, 7, 0, 8, 90, 0, 8, 26, 0, 9, 148, 84, 7, 67, 0, 8, Imgproc.COLOR_YUV2BGRA_YVYU, 0, 8, 58, 0, 9, 212, 82, 7, 19, 0, 8, 106, 0, 8, 42, 0, 9, 180, 0, 8, 10, 0, 8, 138, 0, 8, 74, 0, 9, 244, 80, 7, 5, 0, 8, 86, 0, 8, 22, 192, 8, 0, 83, 7, 51, 0, 8, 118, 0, 8, 54, 0, 9, 204, 81, 7, 15, 0, 8, 102, 0, 8, 38, 0, 9, 172, 0, 8, 6, 0, 8, 134, 0, 8, 70, 0, 9, 236, 80, 7, 9, 0, 8, 94, 0, 8, 30, 0, 9, 156, 84, 7, 99, 0, 8, 126, 0, 8, 62, 0, 9, 220, 82, 7, 27, 0, 8, 110, 0, 8, 46, 0, 9, 188, 0, 8, 14, 0, 8, 142, 0, 8, 78, 0, 9, 252, 96, 7, 256, 0, 8, 81, 0, 8, 17, 85, 8, 131, 82, 7, 31, 0, 8, 113, 0, 8, 49, 0, 9, 194, 80, 7, 10, 0, 8, 97, 0, 8, 33, 0, 9, 162, 0, 8, 1, 0, 8, 129, 0, 8, 65, 0, 9, 226, 80, 7, 6, 0, 8, 89, 0, 8, 25, 0, 9, 146, 83, 7, 59, 0, 8, 121, 0, 8, 57, 0, 9, 210, 81, 7, 17, 0, 8, 105, 0, 8, 41, 0, 9, 178, 0, 8, 9, 0, 8, 137, 0, 8, 73, 0, 9, 242, 80, 7, 4, 0, 8, 85, 0, 8, 21, 80, 8, Imgcodecs.IMWRITE_TIFF_YDPI, 83, 7, 43, 0, 8, 117, 0, 8, 53, 0, 9, 202, 81, 7, 13, 0, 8, 101, 0, 8, 37, 0, 9, 170, 0, 8, 5, 0, 8, 133, 0, 8, 69, 0, 9, 234, 80, 7, 8, 0, 8, 93, 0, 8, 29, 0, 9, 154, 84, 7, 83, 0, 8, 125, 0, 8, 61, 0, 9, 218, 82, 7, 23, 0, 8, 109, 0, 8, 45, 0, 9, 186, 0, 8, 13, 0, 8, 141, 0, 8, 77, 0, 9, Type.TSIG, 80, 7, 3, 0, 8, 83, 0, 8, 19, 85, 8, 195, 83, 7, 35, 0, 8, 115, 0, 8, 51, 0, 9, 198, 81, 7, 11, 0, 8, 99, 0, 8, 35, 0, 9, 166, 0, 8, 3, 0, 8, 131, 0, 8, 67, 0, 9, 230, 80, 7, 7, 0, 8, 91, 0, 8, 27, 0, 9, 150, 84, 7, 67, 0, 8, 123, 0, 8, 59, 0, 9, 214, 82, 7, 19, 0, 8, 107, 0, 8, 43, 0, 9, 182, 0, 8, 11, 0, 8, 139, 0, 8, 75, 0, 9, 246, 80, 7, 5, 0, 8, 87, 0, 8, 23, 192, 8, 0, 83, 7, 51, 0, 8, 119, 0, 8, 55, 0, 9, 206, 81, 7, 15, 0, 8, 103, 0, 8, 39, 0, 9, 174, 0, 8, 7, 0, 8, 135, 0, 8, 71, 0, 9, 238, 80, 7, 9, 0, 8, 95, 0, 8, 31, 0, 9, 158, 84, 7, 99, 0, 8, 127, 0, 8, 63, 0, 9, 222, 82, 7, 27, 0, 8, 111, 0, 8, 47, 0, 9, 190, 0, 8, 15, 0, 8, Imgproc.COLOR_COLORCVT_MAX, 0, 8, 79, 0, 9, 254, 96, 7, 256, 0, 8, 80, 0, 8, 16, 84, 8, 115, 82, 7, 31, 0, 8, 112, 0, 8, 48, 0, 9, 193, 80, 7, 10, 0, 8, 96, 0, 8, 32, 0, 9, 161, 0, 8, 0, 0, 8, 128, 0, 8, 64, 0, 9, 225, 80, 7, 6, 0, 8, 88, 0, 8, 24, 0, 9, 145, 83, 7, 59, 0, 8, 120, 0, 8, 56, 0, 9, 209, 81, 7, 17, 0, 8, 104, 0, 8, 40, 0, 9, 177, 0, 8, 8, 0, 8, 136, 0, 8, 72, 0, 9, 241, 80, 7, 4, 0, 8, 84, 0, 8, 20, 85, 8, 227, 83, 7, 43, 0, 8, 116, 0, 8, 52, 0, 9, 201, 81, 7, 13, 0, 8, 100, 0, 8, 36, 0, 9, 169, 0, 8, 4, 0, 8, 132, 0, 8, 68, 0, 9, 233, 80, 7, 8, 0, 8, 92, 0, 8, 28, 0, 9, 153, 84, 7, 83, 0, 8, 124, 0, 8, 60, 0, 9, 217, 82, 7, 23, 0, 8, 108, 0, 8, 44, 0, 9, 185, 0, 8, 12, 0, 8, 140, 0, 8, 76, 0, 9, Type.TKEY, 80, 7, 3, 0, 8, 82, 0, 8, 18, 85, 8, 163, 83, 7, 35, 0, 8, 114, 0, 8, 50, 0, 9, 197, 81, 7, 11, 0, 8, 98, 0, 8, 34, 0, 9, 165, 0, 8, 2, 0, 8, 130, 0, 8, 66, 0, 9, 229, 80, 7, 7, 0, 8, 90, 0, 8, 26, 0, 9, 149, 84, 7, 67, 0, 8, Imgproc.COLOR_YUV2BGRA_YVYU, 0, 8, 58, 0, 9, 213, 82, 7, 19, 0, 8, 106, 0, 8, 42, 0, 9, 181, 0, 8, 10, 0, 8, 138, 0, 8, 74, 0, 9, WKSRecord.Service.LINK, 80, 7, 5, 0, 8, 86, 0, 8, 22, 192, 8, 0, 83, 7, 51, 0, 8, 118, 0, 8, 54, 0, 9, 205, 81, 7, 15, 0, 8, 102, 0, 8, 38, 0, 9, 173, 0, 8, 6, 0, 8, 134, 0, 8, 70, 0, 9, 237, 80, 7, 9, 0, 8, 94, 0, 8, 30, 0, 9, 157, 84, 7, 99, 0, 8, 126, 0, 8, 62, 0, 9, 221, 82, 7, 27, 0, 8, 110, 0, 8, 46, 0, 9, 189, 0, 8, 14, 0, 8, 142, 0, 8, 78, 0, 9, 253, 96, 7, 256, 0, 8, 81, 0, 8, 17, 85, 8, 131, 82, 7, 31, 0, 8, 113, 0, 8, 49, 0, 9, 195, 80, 7, 10, 0, 8, 97, 0, 8, 33, 0, 9, 163, 0, 8, 1, 0, 8, 129, 0, 8, 65, 0, 9, 227, 80, 7, 6, 0, 8, 89, 0, 8, 25, 0, 9, 147, 83, 7, 59, 0, 8, 121, 0, 8, 57, 0, 9, 211, 81, 7, 17, 0, 8, 105, 0, 8, 41, 0, 9, 179, 0, 8, 9, 0, 8, 137, 0, 8, 73, 0, 9, WKSRecord.Service.SUR_MEAS, 80, 7, 4, 0, 8, 85, 0, 8, 21, 80, 8, Imgcodecs.IMWRITE_TIFF_YDPI, 83, 7, 43, 0, 8, 117, 0, 8, 53, 0, 9, 203, 81, 7, 13, 0, 8, 101, 0, 8, 37, 0, 9, 171, 0, 8, 5, 0, 8, 133, 0, 8, 69, 0, 9, 235, 80, 7, 8, 0, 8, 93, 0, 8, 29, 0, 9, 155, 84, 7, 83, 0, 8, 125, 0, 8, 61, 0, 9, 219, 82, 7, 23, 0, 8, 109, 0, 8, 45, 0, 9, 187, 0, 8, 13, 0, 8, 141, 0, 8, 77, 0, 9, Type.IXFR, 80, 7, 3, 0, 8, 83, 0, 8, 19, 85, 8, 195, 83, 7, 35, 0, 8, 115, 0, 8, 51, 0, 9, 199, 81, 7, 11, 0, 8, 99, 0, 8, 35, 0, 9, 167, 0, 8, 3, 0, 8, 131, 0, 8, 67, 0, 9, 231, 80, 7, 7, 0, 8, 91, 0, 8, 27, 0, 9, 151, 84, 7, 67, 0, 8, 123, 0, 8, 59, 0, 9, 215, 82, 7, 19, 0, 8, 107, 0, 8, 43, 0, 9, 183, 0, 8, 11, 0, 8, 139, 0, 8, 75, 0, 9, 247, 80, 7, 5, 0, 8, 87, 0, 8, 23, 192, 8, 0, 83, 7, 51, 0, 8, 119, 0, 8, 55, 0, 9, 207, 81, 7, 15, 0, 8, 103, 0, 8, 39, 0, 9, 175, 0, 8, 7, 0, 8, 135, 0, 8, 71, 0, 9, 239, 80, 7, 9, 0, 8, 95, 0, 8, 31, 0, 9, 159, 84, 7, 99, 0, 8, 127, 0, 8, 63, 0, 9, 223, 82, 7, 27, 0, 8, 111, 0, 8, 47, 0, 9, 191, 0, 8, 15, 0, 8, Imgproc.COLOR_COLORCVT_MAX, 0, 8, 79, 0, 9, 255};

    /* renamed from: c */
    private int[] f159c;

    /* renamed from: hn */
    private int[] f160hn;

    /* renamed from: r */
    private int[] f161r;

    /* renamed from: u */
    private int[] f162u;

    /* renamed from: v */
    private int[] f163v;

    /* renamed from: x */
    private int[] f164x;

    InfTree() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01a0, code lost:
        r10 = r7;
        r25 = r12;
        r12 = r0.f161r;
        r12[1] = (byte) (r11 - r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01af, code lost:
        if (r4 < r1) goto L_0x01b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01b1, code lost:
        r12[0] = 192;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01b8, code lost:
        if (r38[r4] >= r2) goto L_0x01d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01be, code lost:
        if (r38[r4] >= 256) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01c0, code lost:
        r7 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01c2, code lost:
        r7 = 96;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01c4, code lost:
        r12[0] = (byte) r7;
        r0.f161r[2] = r38[r4];
        r4 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01d2, code lost:
        r12[0] = (byte) ((r33[r38[r4] - r2] + 16) + 64);
        r12[2] = r32[r38[r4] - r2];
        r4 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01e9, code lost:
        r7 = 1 << (r11 - r14);
        r12 = r5 >>> r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01f0, code lost:
        if (r12 >= r8) goto L_0x0209;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01f2, code lost:
        java.lang.System.arraycopy(r0.f161r, 0, r3, (r9 + r12) * 3, 3);
        r12 = r12 + r7;
        r1 = r1;
        r2 = r31;
        r4 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0209, code lost:
        r30 = r1;
        r18 = r4;
        r1 = 1 << (r11 - 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0215, code lost:
        if ((r5 & r1) == 0) goto L_0x021b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0217, code lost:
        r5 = r5 ^ r1;
        r1 = r1 >>> 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x021b, code lost:
        r5 = r5 ^ r1;
        r12 = (1 << r14) - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0226, code lost:
        if ((r5 & r12) == r0.f164x[r10]) goto L_0x0231;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0228, code lost:
        r10 = r10 - 1;
        r14 = r14 - r6;
        r12 = (1 << r14) - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0244, code lost:
        r25 = r12;
        r11 = r11 + 1;
        r2 = r31;
        r8 = 0;
        r4 = r4;
        r1 = r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int huft_build(int[] r28, int r29, int r30, int r31, int[] r32, int[] r33, int[] r34, int[] r35, int[] r36, int[] r37, int[] r38) {
        /*
            r27 = this;
            r0 = r27
            r1 = r30
            r2 = r31
            r3 = r36
            r4 = 0
            r5 = r30
        L_0x000b:
            int[] r6 = r0.f159c
            int r7 = r29 + r4
            r7 = r28[r7]
            r8 = r6[r7]
            r9 = 1
            int r8 = r8 + r9
            r6[r7] = r8
            int r4 = r4 + r9
            r7 = -1
            int r5 = r5 + r7
            if (r5 != 0) goto L_0x0276
            r8 = 0
            r6 = r6[r8]
            if (r6 != r1) goto L_0x0026
            r34[r8] = r7
            r35[r8] = r8
            return r8
        L_0x0026:
            r6 = r35[r8]
            r10 = 1
        L_0x0029:
            r11 = 15
            if (r10 > r11) goto L_0x0037
            int[] r11 = r0.f159c
            r11 = r11[r10]
            if (r11 == 0) goto L_0x0034
            goto L_0x0037
        L_0x0034:
            int r10 = r10 + 1
            goto L_0x0029
        L_0x0037:
            r11 = r10
            if (r6 >= r10) goto L_0x003b
            r6 = r10
        L_0x003b:
            r5 = 15
        L_0x003d:
            if (r5 == 0) goto L_0x0049
            int[] r12 = r0.f159c
            r12 = r12[r5]
            if (r12 == 0) goto L_0x0046
            goto L_0x0049
        L_0x0046:
            int r5 = r5 + -1
            goto L_0x003d
        L_0x0049:
            r12 = r5
            if (r6 <= r5) goto L_0x004d
            r6 = r5
        L_0x004d:
            r35[r8] = r6
            int r13 = r9 << r10
        L_0x0051:
            r14 = -3
            if (r10 >= r5) goto L_0x0063
            int[] r15 = r0.f159c
            r15 = r15[r10]
            int r15 = r13 - r15
            r13 = r15
            if (r15 >= 0) goto L_0x005e
            return r14
        L_0x005e:
            int r10 = r10 + 1
            int r13 = r13 << 1
            goto L_0x0051
        L_0x0063:
            int[] r15 = r0.f159c
            r16 = r15[r5]
            int r16 = r13 - r16
            r13 = r16
            if (r16 >= 0) goto L_0x006e
            return r14
        L_0x006e:
            r16 = r15[r5]
            int r16 = r16 + r13
            r15[r5] = r16
            int[] r15 = r0.f164x
            r10 = r8
            r15[r9] = r8
            r4 = 1
            r15 = 2
        L_0x007b:
            int r5 = r5 + r7
            if (r5 == 0) goto L_0x008f
            int[] r7 = r0.f164x
            int[] r14 = r0.f159c
            r14 = r14[r4]
            int r14 = r14 + r10
            r10 = r14
            r7[r15] = r14
            int r15 = r15 + 1
            int r4 = r4 + 1
            r7 = -1
            r14 = -3
            goto L_0x007b
        L_0x008f:
            r5 = 0
            r4 = 0
        L_0x0091:
            int r7 = r29 + r4
            r7 = r28[r7]
            r10 = r7
            if (r7 == 0) goto L_0x00a2
            int[] r7 = r0.f164x
            r14 = r7[r10]
            int r16 = r14 + 1
            r7[r10] = r16
            r38[r14] = r5
        L_0x00a2:
            int r4 = r4 + 1
            int r5 = r5 + 1
            if (r5 < r1) goto L_0x026a
            int[] r7 = r0.f164x
            r1 = r7[r12]
            r5 = r8
            r7[r8] = r8
            r4 = 0
            r7 = -1
            int r14 = -r6
            int[] r9 = r0.f162u
            r9[r8] = r8
            r9 = 0
            r18 = 0
        L_0x00b9:
            if (r11 > r12) goto L_0x0257
            int[] r8 = r0.f159c
            r8 = r8[r11]
        L_0x00bf:
            int r20 = r8 + -1
            if (r8 == 0) goto L_0x0244
            r8 = r18
        L_0x00c5:
            r30 = r10
            int r10 = r14 + r6
            r18 = 2
            r21 = r15
            if (r11 <= r10) goto L_0x01a0
            int r7 = r7 + 1
            int r14 = r14 + r6
            int r8 = r12 - r14
            if (r8 <= r6) goto L_0x00d8
            r10 = r6
            goto L_0x00d9
        L_0x00d8:
            r10 = r8
        L_0x00d9:
            r8 = r10
            int r10 = r11 - r14
            r30 = r10
            r16 = 1
            int r10 = r16 << r10
            r22 = r10
            int r15 = r20 + 1
            if (r10 <= r15) goto L_0x012c
            int r10 = r20 + 1
            int r22 = r22 - r10
            r10 = r11
            r15 = r30
            if (r15 >= r8) goto L_0x0122
        L_0x00f1:
            r16 = 1
            int r15 = r15 + 1
            if (r15 >= r8) goto L_0x0118
            r24 = r8
            int r8 = r22 << 1
            r22 = r8
            r30 = r15
            int[] r15 = r0.f159c
            int r10 = r10 + 1
            r25 = r12
            r12 = r15[r10]
            if (r8 > r12) goto L_0x010d
            r15 = r10
            r10 = r30
            goto L_0x0135
        L_0x010d:
            r8 = r15[r10]
            int r22 = r22 - r8
            r15 = r30
            r8 = r24
            r12 = r25
            goto L_0x00f1
        L_0x0118:
            r24 = r8
            r25 = r12
            r30 = r15
            r15 = r10
            r10 = r30
            goto L_0x0135
        L_0x0122:
            r24 = r8
            r25 = r12
            r26 = r15
            r15 = r10
            r10 = r26
            goto L_0x0135
        L_0x012c:
            r15 = r30
            r24 = r8
            r25 = r12
            r10 = r15
            r15 = r21
        L_0x0135:
            r8 = 1
            int r12 = r8 << r10
            r8 = 0
            r19 = r37[r8]
            int r8 = r19 + r12
            r21 = r15
            r15 = 1440(0x5a0, float:2.018E-42)
            if (r8 <= r15) goto L_0x0146
            r17 = -3
            return r17
        L_0x0146:
            r17 = -3
            int[] r8 = r0.f162u
            r15 = 0
            r19 = r37[r15]
            r9 = r19
            r8[r7] = r19
            r19 = r37[r15]
            int r19 = r19 + r12
            r37[r15] = r19
            if (r7 == 0) goto L_0x018f
            int[] r15 = r0.f164x
            r15[r7] = r5
            int[] r15 = r0.f161r
            r30 = r12
            byte r12 = (byte) r10
            r19 = 0
            r15[r19] = r12
            byte r12 = (byte) r6
            r16 = 1
            r15[r16] = r12
            int r12 = r14 - r6
            int r10 = r5 >>> r12
            int r12 = r7 + -1
            r12 = r8[r12]
            int r12 = r9 - r12
            int r12 = r12 - r10
            r15[r18] = r12
            int r12 = r7 + -1
            r8 = r8[r12]
            int r8 = r8 + r10
            r12 = 3
            int r8 = r8 * r12
            r23 = r7
            r7 = 0
            java.lang.System.arraycopy(r15, r7, r3, r8, r12)
            r8 = r30
            r15 = r21
            r7 = r23
            r12 = r25
            goto L_0x00c5
        L_0x018f:
            r23 = r7
            r30 = r12
            r7 = r15
            r34[r7] = r9
            r8 = r30
            r15 = r21
            r7 = r23
            r12 = r25
            goto L_0x00c5
        L_0x01a0:
            r10 = r7
            r25 = r12
            r7 = 0
            r17 = -3
            int[] r12 = r0.f161r
            int r15 = r11 - r14
            byte r15 = (byte) r15
            r16 = 1
            r12[r16] = r15
            if (r4 < r1) goto L_0x01b6
            r15 = 192(0xc0, float:2.69E-43)
            r12[r7] = r15
            goto L_0x01e9
        L_0x01b6:
            r7 = r38[r4]
            if (r7 >= r2) goto L_0x01d2
            r7 = r38[r4]
            r15 = 256(0x100, float:3.59E-43)
            if (r7 >= r15) goto L_0x01c2
            r7 = 0
            goto L_0x01c4
        L_0x01c2:
            r7 = 96
        L_0x01c4:
            byte r7 = (byte) r7
            r15 = 0
            r12[r15] = r7
            int[] r7 = r0.f161r
            int r12 = r4 + 1
            r4 = r38[r4]
            r7[r18] = r4
            r4 = r12
            goto L_0x01e9
        L_0x01d2:
            r7 = r38[r4]
            int r7 = r7 - r2
            r7 = r33[r7]
            int r7 = r7 + 16
            int r7 = r7 + 64
            byte r7 = (byte) r7
            r15 = 0
            r12[r15] = r7
            int r7 = r4 + 1
            r4 = r38[r4]
            int r4 = r4 - r2
            r4 = r32[r4]
            r12[r18] = r4
            r4 = r7
        L_0x01e9:
            int r7 = r11 - r14
            r12 = 1
            int r7 = r12 << r7
            int r12 = r5 >>> r14
        L_0x01f0:
            if (r12 >= r8) goto L_0x0209
            int[] r15 = r0.f161r
            int r18 = r9 + r12
            r30 = r1
            r1 = 3
            int r2 = r18 * 3
            r18 = r4
            r4 = 0
            java.lang.System.arraycopy(r15, r4, r3, r2, r1)
            int r12 = r12 + r7
            r1 = r30
            r2 = r31
            r4 = r18
            goto L_0x01f0
        L_0x0209:
            r30 = r1
            r18 = r4
            r4 = 0
            int r1 = r11 + -1
            r2 = 1
            int r1 = r2 << r1
        L_0x0213:
            r2 = r5 & r1
            if (r2 == 0) goto L_0x021b
            r5 = r5 ^ r1
            int r1 = r1 >>> 1
            goto L_0x0213
        L_0x021b:
            r5 = r5 ^ r1
            r2 = 1
            int r12 = r2 << r14
            int r12 = r12 - r2
        L_0x0220:
            r2 = r5 & r12
            int[] r15 = r0.f164x
            r15 = r15[r10]
            if (r2 == r15) goto L_0x0231
            int r10 = r10 + -1
            int r14 = r14 - r6
            r2 = 1
            int r15 = r2 << r14
            int r12 = r15 + -1
            goto L_0x0220
        L_0x0231:
            r2 = 1
            r2 = r31
            r7 = r10
            r4 = r18
            r15 = r21
            r12 = r25
            r10 = r1
            r18 = r8
            r8 = r20
            r1 = r30
            goto L_0x00bf
        L_0x0244:
            r30 = r1
            r1 = r4
            r25 = r12
            r2 = 1
            r4 = 0
            r17 = -3
            int r11 = r11 + 1
            r2 = r31
            r8 = r4
            r4 = r1
            r1 = r30
            goto L_0x00b9
        L_0x0257:
            r30 = r1
            r1 = r4
            r4 = r8
            r25 = r12
            r2 = 1
            if (r13 == 0) goto L_0x0267
            r8 = r25
            if (r8 == r2) goto L_0x0269
            r2 = -5
            r4 = r2
            goto L_0x0269
        L_0x0267:
            r8 = r25
        L_0x0269:
            return r4
        L_0x026a:
            r7 = r4
            r4 = r8
            r2 = r9
            r8 = r12
            r17 = -3
            r2 = r31
            r8 = r4
            r4 = r7
            goto L_0x0091
        L_0x0276:
            r7 = r4
            r2 = r31
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.util.internal.jzlib.InfTree.huft_build(int[], int, int, int, int[], int[], int[], int[], int[], int[], int[]):int");
    }

    /* access modifiers changed from: package-private */
    public int inflate_trees_bits(int[] c, int[] bb, int[] tb, int[] hp, ZStream z) {
        ZStream zStream = z;
        initWorkArea(19);
        int[] iArr = this.f160hn;
        iArr[0] = 0;
        int result = huft_build(c, 0, 19, 19, (int[]) null, (int[]) null, tb, bb, hp, iArr, this.f163v);
        if (result == -3) {
            zStream.msg = "oversubscribed dynamic bit lengths tree";
            return result;
        } else if (result != -5 && bb[0] != 0) {
            return result;
        } else {
            zStream.msg = "incomplete dynamic bit lengths tree";
            return -3;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0063, code lost:
        if (r18 > 257) goto L_0x006b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int inflate_trees_dynamic(int r18, int r19, int[] r20, int[] r21, int[] r22, int[] r23, int[] r24, int[] r25, org.jboss.netty.util.internal.jzlib.ZStream r26) {
        /*
            r17 = this;
            r12 = r17
            r13 = r26
            r14 = 288(0x120, float:4.04E-43)
            r12.initWorkArea(r14)
            int[] r10 = r12.f160hn
            r15 = 0
            r10[r15] = r15
            int[] r5 = cplens
            int[] r6 = cplext
            int[] r11 = r12.f163v
            r2 = 0
            r4 = 257(0x101, float:3.6E-43)
            r0 = r17
            r1 = r20
            r3 = r18
            r7 = r23
            r8 = r21
            r9 = r25
            int r11 = r0.huft_build(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r10 = -4
            r9 = -3
            if (r11 != 0) goto L_0x0085
            r0 = r21[r15]
            if (r0 != 0) goto L_0x0035
            r2 = r18
            r1 = r9
            r3 = r10
            r14 = r11
            goto L_0x008a
        L_0x0035:
            r12.initWorkArea(r14)
            r4 = 0
            int[] r5 = cpdist
            int[] r6 = cpdext
            int[] r14 = r12.f160hn
            int[] r8 = r12.f163v
            r0 = r17
            r1 = r20
            r2 = r18
            r3 = r19
            r7 = r24
            r16 = r8
            r8 = r22
            r9 = r25
            r10 = r14
            r14 = r11
            r11 = r16
            int r0 = r0.huft_build(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            if (r0 != 0) goto L_0x0069
            r1 = r22[r15]
            if (r1 != 0) goto L_0x0066
            r1 = 257(0x101, float:3.6E-43)
            r2 = r18
            if (r2 <= r1) goto L_0x0068
            goto L_0x006b
        L_0x0066:
            r2 = r18
        L_0x0068:
            return r15
        L_0x0069:
            r2 = r18
        L_0x006b:
            r1 = -3
            if (r0 != r1) goto L_0x0073
            java.lang.String r1 = "oversubscribed distance tree"
            r13.msg = r1
            goto L_0x0084
        L_0x0073:
            r1 = -5
            if (r0 != r1) goto L_0x007c
            java.lang.String r1 = "incomplete distance tree"
            r13.msg = r1
            r0 = -3
            goto L_0x0084
        L_0x007c:
            r3 = -4
            if (r0 == r3) goto L_0x0084
            java.lang.String r1 = "empty distance tree with lengths"
            r13.msg = r1
            r0 = -3
        L_0x0084:
            return r0
        L_0x0085:
            r2 = r18
            r1 = r9
            r3 = r10
            r14 = r11
        L_0x008a:
            if (r14 != r1) goto L_0x0091
            java.lang.String r0 = "oversubscribed literal/length tree"
            r13.msg = r0
            goto L_0x0099
        L_0x0091:
            if (r14 == r3) goto L_0x0099
            java.lang.String r0 = "incomplete literal/length tree"
            r13.msg = r0
            r11 = -3
            goto L_0x009a
        L_0x0099:
            r11 = r14
        L_0x009a:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.util.internal.jzlib.InfTree.inflate_trees_dynamic(int, int, int[], int[], int[], int[], int[], int[], org.jboss.netty.util.internal.jzlib.ZStream):int");
    }

    static int inflate_trees_fixed(int[] bl, int[] bd, int[][] tl, int[][] td) {
        bl[0] = 9;
        bd[0] = 5;
        tl[0] = fixed_tl;
        td[0] = fixed_td;
        return 0;
    }

    private void initWorkArea(int vsize) {
        if (this.f160hn == null) {
            this.f160hn = new int[1];
            this.f163v = new int[vsize];
            this.f159c = new int[16];
            this.f161r = new int[3];
            this.f162u = new int[15];
            this.f164x = new int[16];
            return;
        }
        if (this.f163v.length < vsize) {
            this.f163v = new int[vsize];
        } else {
            for (int i = 0; i < vsize; i++) {
                this.f163v[i] = 0;
            }
        }
        for (int i2 = 0; i2 < 16; i2++) {
            this.f159c[i2] = 0;
        }
        for (int i3 = 0; i3 < 3; i3++) {
            this.f161r[i3] = 0;
        }
        System.arraycopy(this.f159c, 0, this.f162u, 0, 15);
        System.arraycopy(this.f159c, 0, this.f164x, 0, 16);
    }
}
