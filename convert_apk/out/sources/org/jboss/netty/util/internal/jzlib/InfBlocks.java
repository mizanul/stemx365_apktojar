package org.jboss.netty.util.internal.jzlib;

final class InfBlocks {
    private static final int BAD = 9;
    private static final int BTREE = 4;
    private static final int CODES = 6;
    private static final int DONE = 8;
    private static final int DRY = 7;
    private static final int DTREE = 5;
    private static final int LENS = 1;
    private static final int STORED = 2;
    private static final int TABLE = 3;
    private static final int TYPE = 0;
    private static final int[] border = {16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15};
    private static final int[] inflate_mask = {0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535};

    /* renamed from: bb */
    private final int[] f157bb = new int[1];
    int bitb;
    int bitk;
    private int[] blens;
    private long check;
    private final Object checkfn;
    private final InfCodes codes = new InfCodes();
    final int end;
    private int[] hufts = new int[4320];
    private int index;
    private final InfTree inftree = new InfTree();
    private int last;
    private int left;
    private int mode;
    int read;
    private int table;

    /* renamed from: tb */
    private final int[] f158tb = new int[1];
    byte[] window;
    int write;

    InfBlocks(ZStream z, Object checkfn2, int w) {
        this.window = new byte[w];
        this.end = w;
        this.checkfn = checkfn2;
        this.mode = 0;
        reset(z, (long[]) null);
    }

    /* access modifiers changed from: package-private */
    public void reset(ZStream z, long[] c) {
        if (c != null) {
            c[0] = this.check;
        }
        this.mode = 0;
        this.bitk = 0;
        this.bitb = 0;
        this.write = 0;
        this.read = 0;
        if (this.checkfn != null) {
            long adler32 = Adler32.adler32(0, (byte[]) null, 0, 0);
            this.check = adler32;
            z.adler = adler32;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x036c, code lost:
        if (r9 == 0) goto L_0x0381;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x036e, code lost:
        r12 = 0;
        r9 = r9 - 1;
        r15 = r15 | ((r11.next_in[r10] & 255) << r13);
        r13 = r13 + 8;
        r10 = r10 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0381, code lost:
        r0.bitb = r15;
        r0.bitk = r13;
        r11.avail_in = r9;
        r38 = r9;
        r19 = r2;
        r22 = r3;
        r11.total_in += (long) (r10 - r11.next_in_index);
        r11.next_in_index = r10;
        r0.write = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x039f, code lost:
        return inflate_flush(r11, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x03a0, code lost:
        r19 = r2;
        r38 = r9;
        r2 = r15 >>> r1;
        r3 = r3 + (inflate_mask[r5] & r2);
        r2 = r2 >>> r5;
        r13 = (r13 - r1) - r5;
        r5 = r0.index;
        r1 = r0.table;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x03c1, code lost:
        if ((r5 + r3) > (((r1 & 31) + org.opencv.imgcodecs.Imgcodecs.IMWRITE_TIFF_YDPI) + ((r1 >> 5) & 31))) goto L_0x03f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x03c3, code lost:
        r8 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x03c7, code lost:
        if (r8 != 16) goto L_0x03cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x03ca, code lost:
        if (r5 >= 1) goto L_0x03cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x03cd, code lost:
        if (r8 != 16) goto L_0x03d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x03cf, code lost:
        r9 = r0.blens[r5 - 1];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x03d6, code lost:
        r9 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x03d7, code lost:
        r15 = r5 + 1;
        r0.blens[r5] = r9;
        r3 = r3 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x03de, code lost:
        if (r3 != 0) goto L_0x03ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x03e0, code lost:
        r0.index = r15;
        r9 = r38;
        r8 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x03e5, code lost:
        r6 = r1;
        r5 = r14;
        r15 = r26;
        r14 = r7;
        r7 = r13;
        r13 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x03ee, code lost:
        r5 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x03f0, code lost:
        r8 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x041b, code lost:
        r7 = r14;
        r26 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x041e, code lost:
        r0.mode = 9;
        r11.msg = "too many length or distance symbols";
        r0.bitb = r4;
        r0.bitk = r5;
        r11.avail_in = r3;
        r11.total_in += (long) (r2 - r11.next_in_index);
        r11.next_in_index = r2;
        r0.write = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0440, code lost:
        return inflate_flush(r11, -3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00ae, code lost:
        r7 = r4 & 16383;
        r6 = r7;
        r0.table = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00b7, code lost:
        if ((r6 & 31) > 29) goto L_0x041b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00bd, code lost:
        if (((r6 >> 5) & 31) <= 29) goto L_0x00c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00bf, code lost:
        r7 = r14;
        r26 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00c4, code lost:
        r6 = ((r6 & 31) + org.opencv.imgcodecs.Imgcodecs.IMWRITE_TIFF_YDPI) + ((r6 >> 5) & 31);
        r7 = r0.blens;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00d0, code lost:
        if (r7 == null) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00d3, code lost:
        if (r7.length >= r6) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00d6, code lost:
        r7 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00d7, code lost:
        if (r7 >= r6) goto L_0x00e4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00d9, code lost:
        r0.blens[r7] = r13;
        r7 = r7 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00e0, code lost:
        r0.blens = new int[r6];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00e4, code lost:
        r0.index = r13;
        r0.mode = 4;
        r7 = r1;
        r12 = r2;
        r22 = r6;
        r6 = r3;
        r35 = r5 - 14;
        r5 = r4 >>> 14;
        r4 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00fd, code lost:
        if (r0.index >= ((r0.table >>> 10) + r8)) goto L_0x0143;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ff, code lost:
        if (r4 >= 3) goto L_0x012d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0101, code lost:
        if (r6 == 0) goto L_0x0114;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0103, code lost:
        r7 = 0;
        r6 = r6 - 1;
        r5 = r5 | ((r11.next_in[r12] & 255) << r4);
        r4 = r4 + 8;
        r12 = r12 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0114, code lost:
        r0.bitb = r5;
        r0.bitk = r4;
        r11.avail_in = r6;
        r11.total_in += (long) (r12 - r11.next_in_index);
        r11.next_in_index = r12;
        r0.write = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x012c, code lost:
        return inflate_flush(r11, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x012d, code lost:
        r1 = r0.blens;
        r2 = border;
        r3 = r0.index;
        r0.index = r3 + 1;
        r1[r2[r3]] = r5 & 7;
        r5 = r5 >>> 3;
        r4 = r4 - 3;
        r8 = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0143, code lost:
        r1 = r0.index;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0147, code lost:
        if (r1 >= 19) goto L_0x0156;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0149, code lost:
        r2 = r0.blens;
        r3 = border;
        r0.index = r1 + 1;
        r2[r3[r1]] = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0156, code lost:
        r3 = r0.f157bb;
        r3[r13] = 7;
        r9 = r4;
        r8 = r5;
        r10 = r6;
        r6 = r0.inftree.inflate_trees_bits(r0.blens, r3, r0.f158tb, r0.hufts, r37);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x016d, code lost:
        if (r6 == 0) goto L_0x0193;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x016f, code lost:
        r1 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0171, code lost:
        if (r1 != -3) goto L_0x017a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0173, code lost:
        r0.blens = null;
        r0.mode = 9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x017a, code lost:
        r0.bitb = r8;
        r0.bitk = r9;
        r11.avail_in = r10;
        r11.total_in += (long) (r12 - r11.next_in_index);
        r11.next_in_index = r12;
        r0.write = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0192, code lost:
        return inflate_flush(r11, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0193, code lost:
        r5 = null;
        r0.index = r13;
        r0.mode = 5;
        r35 = r12;
        r12 = r7;
        r7 = r9;
        r9 = r10;
        r10 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x01a0, code lost:
        r1 = r0.table;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x01ae, code lost:
        if (r0.index < (((r1 & 31) + org.opencv.imgcodecs.Imgcodecs.IMWRITE_TIFF_YDPI) + ((r1 >> 5) & 31))) goto L_0x02de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01b0, code lost:
        r0.f158tb[r13] = -1;
        r6 = new int[1];
        r4 = new int[1];
        r19 = r12;
        r12 = new int[1];
        r6[r13] = 9;
        r4[r13] = 6;
        r2 = r0.table;
        r24 = r2;
        r25 = new int[1];
        r20 = r4;
        r22 = r6;
        r13 = r7;
        r26 = r15;
        r15 = r8;
        r8 = r12;
        r27 = r12;
        r12 = r9;
        r23 = r10;
        r38 = r14;
        r1 = r0.inftree.inflate_trees_dynamic((r2 & 31) + 257, ((r2 >> 5) & 31) + 1, r0.blens, r6, r20, r25, r8, r0.hufts, r37);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0204, code lost:
        if (r1 == 0) goto L_0x022d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0207, code lost:
        if (r1 != -3) goto L_0x020f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0209, code lost:
        r0.blens = null;
        r0.mode = 9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x020f, code lost:
        r0.bitb = r15;
        r0.bitk = r13;
        r11.avail_in = r12;
        r11.total_in += (long) (r23 - r11.next_in_index);
        r11.next_in_index = r23;
        r0.write = r38;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x022c, code lost:
        return inflate_flush(r11, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x022d, code lost:
        r7 = r38;
        r2 = r0.codes;
        r29 = r22[0];
        r30 = r20[0];
        r5 = r0.hufts;
        r2.init(r29, r30, r5, r25[0], r5, r27[0]);
        r0.mode = 6;
        r6 = r1;
        r2 = r23;
        r3 = r12;
        r5 = r13;
        r4 = r15;
        r1 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0251, code lost:
        r0.bitb = r4;
        r0.bitk = r5;
        r11.avail_in = r3;
        r11.total_in += (long) (r2 - r11.next_in_index);
        r11.next_in_index = r2;
        r0.write = r7;
        r8 = r0.codes.proc(r0, r11, r1);
        r1 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x026d, code lost:
        if (r8 == 1) goto L_0x0274;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0273, code lost:
        return inflate_flush(r11, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0274, code lost:
        r1 = 0;
        r2 = r11.next_in_index;
        r3 = r11.avail_in;
        r4 = r0.bitb;
        r5 = r0.bitk;
        r14 = r0.write;
        r7 = r0.read;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0281, code lost:
        if (r14 >= r7) goto L_0x0287;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0283, code lost:
        r7 = (r7 - r14) - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0287, code lost:
        r7 = r0.end - r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x028a, code lost:
        r15 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x028d, code lost:
        if (r0.last != 0) goto L_0x0296;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x028f, code lost:
        r0.mode = 0;
        r13 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0296, code lost:
        r0.mode = 7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0299, code lost:
        r0.write = r14;
        r1 = inflate_flush(r11, r1);
        r14 = r0.write;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x02a5, code lost:
        if (r0.read == r0.write) goto L_0x02c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x02a7, code lost:
        r0.bitb = r4;
        r0.bitk = r5;
        r11.avail_in = r3;
        r11.total_in += (long) (r2 - r11.next_in_index);
        r11.next_in_index = r2;
        r0.write = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x02bf, code lost:
        return inflate_flush(r11, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x02c0, code lost:
        r0.mode = 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x02c4, code lost:
        r0.bitb = r4;
        r0.bitk = r5;
        r11.avail_in = r3;
        r11.total_in += (long) (r2 - r11.next_in_index);
        r11.next_in_index = r2;
        r0.write = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x02dd, code lost:
        return inflate_flush(r11, 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x02de, code lost:
        r13 = r7;
        r3 = r10;
        r19 = r12;
        r7 = r14;
        r26 = r15;
        r14 = r5;
        r15 = r8;
        r12 = r9;
        r1 = r0.f157bb[0];
        r12 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x02f1, code lost:
        if (r13 >= r1) goto L_0x031f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x02f3, code lost:
        if (r9 == 0) goto L_0x0306;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x02f5, code lost:
        r12 = 0;
        r9 = r9 - 1;
        r15 = r15 | ((r11.next_in[r10] & 255) << r13);
        r13 = r13 + 8;
        r10 = r10 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0306, code lost:
        r0.bitb = r15;
        r0.bitk = r13;
        r11.avail_in = r9;
        r11.total_in += (long) (r10 - r11.next_in_index);
        r11.next_in_index = r10;
        r0.write = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x031e, code lost:
        return inflate_flush(r11, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x031f, code lost:
        r2 = r0.f158tb;
        r5 = r2[0];
        r5 = r0.hufts;
        r19 = r2[0];
        r22 = inflate_mask;
        r1 = r5[((r19 + (r15 & r22[r1])) * 3) + 1];
        r2 = r5[((r2[0] + (r22[r1] & r15)) * 3) + 2];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0348, code lost:
        if (r2 >= 16) goto L_0x035a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x034a, code lost:
        r13 = r13 - r1;
        r4 = r0.blens;
        r5 = r0.index;
        r0.index = r5 + 1;
        r4[r5] = r2;
        r8 = r15 >>> r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x035c, code lost:
        if (r2 != 18) goto L_0x0360;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x035e, code lost:
        r5 = 7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0360, code lost:
        r5 = r2 - 14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0362, code lost:
        if (r2 != 18) goto L_0x0367;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0364, code lost:
        r3 = 11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0367, code lost:
        r3 = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x036a, code lost:
        if (r13 >= (r1 + r5)) goto L_0x03a0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int proc(org.jboss.netty.util.internal.jzlib.ZStream r37, int r38) {
        /*
            r36 = this;
            r0 = r36
            r11 = r37
            int r1 = r11.next_in_index
            int r2 = r11.avail_in
            int r3 = r0.bitb
            int r4 = r0.bitk
            int r5 = r0.write
            int r6 = r0.read
            r12 = 1
            if (r5 >= r6) goto L_0x0016
            int r6 = r6 - r5
            int r6 = r6 - r12
            goto L_0x0019
        L_0x0016:
            int r6 = r0.end
            int r6 = r6 - r5
        L_0x0019:
            r13 = 0
            r14 = r5
            r15 = r6
            r6 = r13
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r38
        L_0x0023:
            int r7 = r0.mode
            r8 = 4
            r10 = 7
            r9 = 3
            switch(r7) {
                case 0: goto L_0x0567;
                case 1: goto L_0x04eb;
                case 2: goto L_0x0441;
                case 3: goto L_0x007d;
                case 4: goto L_0x0071;
                case 5: goto L_0x0069;
                case 6: goto L_0x0064;
                case 7: goto L_0x0062;
                case 8: goto L_0x0060;
                case 9: goto L_0x0046;
                default: goto L_0x002b;
            }
        L_0x002b:
            r7 = r14
            r1 = -2
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r8 = r11.total_in
            int r10 = r11.next_in_index
            int r10 = r2 - r10
            long r12 = (long) r10
            long r8 = r8 + r12
            r11.total_in = r8
            r11.next_in_index = r2
            r0.write = r7
            int r8 = r0.inflate_flush(r11, r1)
            return r8
        L_0x0046:
            r1 = -3
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r7 = r11.total_in
            int r9 = r11.next_in_index
            int r9 = r2 - r9
            long r9 = (long) r9
            long r7 = r7 + r9
            r11.total_in = r7
            r11.next_in_index = r2
            r0.write = r14
            int r7 = r0.inflate_flush(r11, r1)
            return r7
        L_0x0060:
            goto L_0x02c4
        L_0x0062:
            goto L_0x0299
        L_0x0064:
            r7 = r14
            r26 = r15
            goto L_0x0251
        L_0x0069:
            r12 = r1
            r10 = r2
            r9 = r3
            r8 = r4
            r7 = r5
            r5 = 0
            goto L_0x01a0
        L_0x0071:
            r7 = r1
            r12 = r2
            r22 = r6
            r6 = r3
            r35 = r5
            r5 = r4
            r4 = r35
            goto L_0x00f6
        L_0x007d:
            r7 = 14
            if (r5 >= r7) goto L_0x00ae
            if (r3 == 0) goto L_0x0095
            r1 = 0
            int r3 = r3 + -1
            byte[] r7 = r11.next_in
            int r21 = r2 + 1
            byte r2 = r7[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << r5
            r4 = r4 | r2
            int r5 = r5 + 8
            r2 = r21
            goto L_0x007d
        L_0x0095:
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r7 = r11.total_in
            int r9 = r11.next_in_index
            int r9 = r2 - r9
            long r9 = (long) r9
            long r7 = r7 + r9
            r11.total_in = r7
            r11.next_in_index = r2
            r0.write = r14
            int r7 = r0.inflate_flush(r11, r1)
            return r7
        L_0x00ae:
            r7 = r4 & 16383(0x3fff, float:2.2957E-41)
            r6 = r7
            r0.table = r7
            r7 = r6 & 31
            r12 = 29
            if (r7 > r12) goto L_0x041b
            int r7 = r6 >> 5
            r7 = r7 & 31
            if (r7 <= r12) goto L_0x00c4
            r7 = r14
            r26 = r15
            goto L_0x041e
        L_0x00c4:
            r7 = r6 & 31
            int r7 = r7 + 258
            int r12 = r6 >> 5
            r12 = r12 & 31
            int r6 = r7 + r12
            int[] r7 = r0.blens
            if (r7 == 0) goto L_0x00e0
            int r7 = r7.length
            if (r7 >= r6) goto L_0x00d6
            goto L_0x00e0
        L_0x00d6:
            r7 = 0
        L_0x00d7:
            if (r7 >= r6) goto L_0x00e4
            int[] r12 = r0.blens
            r12[r7] = r13
            int r7 = r7 + 1
            goto L_0x00d7
        L_0x00e0:
            int[] r7 = new int[r6]
            r0.blens = r7
        L_0x00e4:
            int r4 = r4 >>> 14
            int r5 = r5 + -14
            r0.index = r13
            r0.mode = r8
            r7 = r1
            r12 = r2
            r22 = r6
            r6 = r3
            r35 = r5
            r5 = r4
            r4 = r35
        L_0x00f6:
            int r1 = r0.index
            int r2 = r0.table
            int r2 = r2 >>> 10
            int r2 = r2 + r8
            if (r1 >= r2) goto L_0x0143
        L_0x00ff:
            if (r4 >= r9) goto L_0x012d
            if (r6 == 0) goto L_0x0114
            r7 = 0
            int r6 = r6 + -1
            byte[] r1 = r11.next_in
            int r2 = r12 + 1
            byte r1 = r1[r12]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 << r4
            r5 = r5 | r1
            int r4 = r4 + 8
            r12 = r2
            goto L_0x00ff
        L_0x0114:
            r0.bitb = r5
            r0.bitk = r4
            r11.avail_in = r6
            long r1 = r11.total_in
            int r3 = r11.next_in_index
            int r3 = r12 - r3
            long r8 = (long) r3
            long r1 = r1 + r8
            r11.total_in = r1
            r11.next_in_index = r12
            r0.write = r14
            int r1 = r0.inflate_flush(r11, r7)
            return r1
        L_0x012d:
            int[] r1 = r0.blens
            int[] r2 = border
            int r3 = r0.index
            int r8 = r3 + 1
            r0.index = r8
            r2 = r2[r3]
            r3 = r5 & 7
            r1[r2] = r3
            int r5 = r5 >>> 3
            int r4 = r4 + -3
            r8 = 4
            goto L_0x00f6
        L_0x0143:
            int r1 = r0.index
            r2 = 19
            if (r1 >= r2) goto L_0x0156
            int[] r2 = r0.blens
            int[] r3 = border
            int r8 = r1 + 1
            r0.index = r8
            r1 = r3[r1]
            r2[r1] = r13
            goto L_0x0143
        L_0x0156:
            int[] r3 = r0.f157bb
            r3[r13] = r10
            org.jboss.netty.util.internal.jzlib.InfTree r1 = r0.inftree
            int[] r2 = r0.blens
            int[] r8 = r0.f158tb
            int[] r10 = r0.hufts
            r9 = r4
            r4 = r8
            r8 = r5
            r5 = r10
            r10 = r6
            r6 = r37
            int r6 = r1.inflate_trees_bits(r2, r3, r4, r5, r6)
            if (r6 == 0) goto L_0x0193
            r1 = r6
            r2 = -3
            if (r1 != r2) goto L_0x017a
            r5 = 0
            r0.blens = r5
            r2 = 9
            r0.mode = r2
        L_0x017a:
            r0.bitb = r8
            r0.bitk = r9
            r11.avail_in = r10
            long r2 = r11.total_in
            int r4 = r11.next_in_index
            int r4 = r12 - r4
            long r4 = (long) r4
            long r2 = r2 + r4
            r11.total_in = r2
            r11.next_in_index = r12
            r0.write = r14
            int r2 = r0.inflate_flush(r11, r1)
            return r2
        L_0x0193:
            r5 = 0
            r0.index = r13
            r1 = 5
            r0.mode = r1
            r35 = r12
            r12 = r7
            r7 = r9
            r9 = r10
            r10 = r35
        L_0x01a0:
            int r1 = r0.table
            int r2 = r0.index
            r3 = r1 & 31
            int r3 = r3 + 258
            int r4 = r1 >> 5
            r4 = r4 & 31
            int r3 = r3 + r4
            r4 = -1
            if (r2 < r3) goto L_0x02de
            int[] r2 = r0.f158tb
            r2[r13] = r4
            r2 = 1
            int[] r6 = new int[r2]
            int[] r4 = new int[r2]
            int[] r3 = new int[r2]
            r19 = r12
            int[] r12 = new int[r2]
            r2 = 9
            r6[r13] = r2
            r16 = 6
            r4[r13] = r16
            int r2 = r0.table
            org.jboss.netty.util.internal.jzlib.InfTree r1 = r0.inftree
            r5 = r2 & 31
            int r5 = r5 + 257
            int r20 = r2 >> 5
            r20 = r20 & 31
            r17 = 1
            int r20 = r20 + 1
            int[] r13 = r0.blens
            r38 = r9
            int[] r9 = r0.hufts
            r24 = r2
            r2 = r5
            r25 = r3
            r3 = r20
            r20 = r4
            r4 = r13
            r13 = 0
            r5 = r6
            r22 = r6
            r6 = r20
            r13 = r7
            r7 = r25
            r26 = r15
            r15 = r8
            r8 = r12
            r27 = r12
            r12 = r38
            r23 = r10
            r38 = r14
            r14 = 0
            r10 = r37
            int r1 = r1.inflate_trees_dynamic(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            if (r1 == 0) goto L_0x022d
            r2 = -3
            if (r1 != r2) goto L_0x020f
            r0.blens = r14
            r2 = 9
            r0.mode = r2
        L_0x020f:
            r2 = r1
            r0.bitb = r15
            r0.bitk = r13
            r11.avail_in = r12
            long r3 = r11.total_in
            int r5 = r11.next_in_index
            int r10 = r23 - r5
            long r5 = (long) r10
            long r3 = r3 + r5
            r11.total_in = r3
            r3 = r23
            r11.next_in_index = r3
            r7 = r38
            r0.write = r7
            int r4 = r0.inflate_flush(r11, r2)
            return r4
        L_0x022d:
            r7 = r38
            r3 = r23
            org.jboss.netty.util.internal.jzlib.InfCodes r2 = r0.codes
            r4 = 0
            r29 = r22[r4]
            r30 = r20[r4]
            int[] r5 = r0.hufts
            r32 = r25[r4]
            r34 = r27[r4]
            r28 = r2
            r31 = r5
            r33 = r5
            r28.init(r29, r30, r31, r32, r33, r34)
            r6 = 6
            r0.mode = r6
            r6 = r1
            r2 = r3
            r3 = r12
            r5 = r13
            r4 = r15
            r1 = r19
        L_0x0251:
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r8 = r11.total_in
            int r10 = r11.next_in_index
            int r10 = r2 - r10
            long r12 = (long) r10
            long r8 = r8 + r12
            r11.total_in = r8
            r11.next_in_index = r2
            r0.write = r7
            org.jboss.netty.util.internal.jzlib.InfCodes r8 = r0.codes
            int r8 = r8.proc(r0, r11, r1)
            r1 = r8
            r9 = 1
            if (r8 == r9) goto L_0x0274
            int r8 = r0.inflate_flush(r11, r1)
            return r8
        L_0x0274:
            r1 = 0
            int r2 = r11.next_in_index
            int r3 = r11.avail_in
            int r4 = r0.bitb
            int r5 = r0.bitk
            int r14 = r0.write
            int r7 = r0.read
            if (r14 >= r7) goto L_0x0287
            int r7 = r7 - r14
            r8 = 1
            int r7 = r7 - r8
            goto L_0x028a
        L_0x0287:
            int r7 = r0.end
            int r7 = r7 - r14
        L_0x028a:
            r15 = r7
            int r7 = r0.last
            if (r7 != 0) goto L_0x0296
            r7 = 0
            r0.mode = r7
            r12 = 1
            r13 = 0
            goto L_0x0023
        L_0x0296:
            r8 = 7
            r0.mode = r8
        L_0x0299:
            r0.write = r14
            int r1 = r0.inflate_flush(r11, r1)
            int r14 = r0.write
            int r7 = r0.read
            int r8 = r0.write
            if (r7 == r8) goto L_0x02c0
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r7 = r11.total_in
            int r9 = r11.next_in_index
            int r9 = r2 - r9
            long r9 = (long) r9
            long r7 = r7 + r9
            r11.total_in = r7
            r11.next_in_index = r2
            r0.write = r14
            int r7 = r0.inflate_flush(r11, r1)
            return r7
        L_0x02c0:
            r7 = 8
            r0.mode = r7
        L_0x02c4:
            r1 = 1
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r7 = r11.total_in
            int r9 = r11.next_in_index
            int r9 = r2 - r9
            long r9 = (long) r9
            long r7 = r7 + r9
            r11.total_in = r7
            r11.next_in_index = r2
            r0.write = r14
            int r7 = r0.inflate_flush(r11, r1)
            return r7
        L_0x02de:
            r13 = r7
            r3 = r10
            r19 = r12
            r7 = r14
            r26 = r15
            r6 = 6
            r14 = r5
            r15 = r8
            r12 = r9
            r8 = 7
            int[] r2 = r0.f157bb
            r5 = 0
            r1 = r2[r5]
            r12 = r19
        L_0x02f1:
            if (r13 >= r1) goto L_0x031f
            if (r9 == 0) goto L_0x0306
            r12 = 0
            int r9 = r9 + -1
            byte[] r2 = r11.next_in
            int r3 = r10 + 1
            byte r2 = r2[r10]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << r13
            r15 = r15 | r2
            int r13 = r13 + 8
            r10 = r3
            goto L_0x02f1
        L_0x0306:
            r0.bitb = r15
            r0.bitk = r13
            r11.avail_in = r9
            long r2 = r11.total_in
            int r4 = r11.next_in_index
            int r4 = r10 - r4
            long r4 = (long) r4
            long r2 = r2 + r4
            r11.total_in = r2
            r11.next_in_index = r10
            r0.write = r7
            int r2 = r0.inflate_flush(r11, r12)
            return r2
        L_0x031f:
            int[] r2 = r0.f158tb
            r3 = 0
            r5 = r2[r3]
            int[] r5 = r0.hufts
            r19 = r2[r3]
            int[] r22 = inflate_mask
            r23 = r22[r1]
            r23 = r15 & r23
            int r19 = r19 + r23
            r23 = 3
            int r19 = r19 * 3
            r17 = 1
            int r19 = r19 + 1
            r1 = r5[r19]
            r2 = r2[r3]
            r3 = r22[r1]
            r3 = r3 & r15
            int r2 = r2 + r3
            int r2 = r2 * 3
            r3 = 2
            int r2 = r2 + r3
            r2 = r5[r2]
            r3 = 16
            if (r2 >= r3) goto L_0x035a
            int r3 = r15 >>> r1
            int r13 = r13 - r1
            int[] r4 = r0.blens
            int r5 = r0.index
            int r15 = r5 + 1
            r0.index = r15
            r4[r5] = r2
            r8 = r3
            goto L_0x03e5
        L_0x035a:
            r3 = 18
            if (r2 != r3) goto L_0x0360
            r5 = r8
            goto L_0x0362
        L_0x0360:
            int r5 = r2 + -14
        L_0x0362:
            if (r2 != r3) goto L_0x0367
            r3 = 11
            goto L_0x0368
        L_0x0367:
            r3 = 3
        L_0x0368:
            int r8 = r1 + r5
            if (r13 >= r8) goto L_0x03a0
            if (r9 == 0) goto L_0x0381
            r12 = 0
            int r9 = r9 + -1
            byte[] r8 = r11.next_in
            int r19 = r10 + 1
            byte r8 = r8[r10]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r8 = r8 << r13
            r15 = r15 | r8
            int r13 = r13 + 8
            r10 = r19
            r8 = 7
            goto L_0x0368
        L_0x0381:
            r0.bitb = r15
            r0.bitk = r13
            r11.avail_in = r9
            r38 = r9
            long r8 = r11.total_in
            int r4 = r11.next_in_index
            int r4 = r10 - r4
            r19 = r2
            r22 = r3
            long r2 = (long) r4
            long r8 = r8 + r2
            r11.total_in = r8
            r11.next_in_index = r10
            r0.write = r7
            int r2 = r0.inflate_flush(r11, r12)
            return r2
        L_0x03a0:
            r19 = r2
            r22 = r3
            r38 = r9
            int r2 = r15 >>> r1
            int r13 = r13 - r1
            int[] r3 = inflate_mask
            r3 = r3[r5]
            r3 = r3 & r2
            int r3 = r22 + r3
            int r2 = r2 >>> r5
            int r13 = r13 - r5
            int r5 = r0.index
            int r1 = r0.table
            int r8 = r5 + r3
            r9 = r1 & 31
            int r9 = r9 + 258
            int r15 = r1 >> 5
            r15 = r15 & 31
            int r9 = r9 + r15
            if (r8 > r9) goto L_0x03f0
            r8 = r19
            r9 = 16
            if (r8 != r9) goto L_0x03cd
            r15 = 1
            if (r5 >= r15) goto L_0x03cd
            goto L_0x03f2
        L_0x03cd:
            if (r8 != r9) goto L_0x03d6
            int[] r9 = r0.blens
            int r15 = r5 + -1
            r9 = r9[r15]
            goto L_0x03d7
        L_0x03d6:
            r9 = 0
        L_0x03d7:
            int[] r8 = r0.blens
            int r15 = r5 + 1
            r8[r5] = r9
            int r3 = r3 + r4
            if (r3 != 0) goto L_0x03ee
            r0.index = r15
            r9 = r38
            r8 = r2
        L_0x03e5:
            r6 = r1
            r5 = r14
            r15 = r26
            r14 = r7
            r7 = r13
            r13 = 0
            goto L_0x01a0
        L_0x03ee:
            r5 = r15
            goto L_0x03d7
        L_0x03f0:
            r8 = r19
        L_0x03f2:
            r0.blens = r14
            r4 = 9
            r0.mode = r4
            java.lang.String r4 = "invalid bit length repeat"
            r11.msg = r4
            r4 = -3
            r0.bitb = r2
            r0.bitk = r13
            r9 = r38
            r11.avail_in = r9
            long r14 = r11.total_in
            int r6 = r11.next_in_index
            int r6 = r10 - r6
            r12 = r1
            r38 = r2
            long r1 = (long) r6
            long r14 = r14 + r1
            r11.total_in = r14
            r11.next_in_index = r10
            r0.write = r7
            int r1 = r0.inflate_flush(r11, r4)
            return r1
        L_0x041b:
            r7 = r14
            r26 = r15
        L_0x041e:
            r8 = 9
            r0.mode = r8
            java.lang.String r8 = "too many length or distance symbols"
            r11.msg = r8
            r1 = -3
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r8 = r11.total_in
            int r10 = r11.next_in_index
            int r10 = r2 - r10
            long r12 = (long) r10
            long r8 = r8 + r12
            r11.total_in = r8
            r11.next_in_index = r2
            r0.write = r7
            int r8 = r0.inflate_flush(r11, r1)
            return r8
        L_0x0441:
            r7 = r14
            r26 = r15
            if (r3 != 0) goto L_0x045f
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r8 = r11.total_in
            int r10 = r11.next_in_index
            int r10 = r2 - r10
            long r12 = (long) r10
            long r8 = r8 + r12
            r11.total_in = r8
            r11.next_in_index = r2
            r0.write = r7
            int r8 = r0.inflate_flush(r11, r1)
            return r8
        L_0x045f:
            if (r26 != 0) goto L_0x04be
            int r8 = r0.end
            if (r7 != r8) goto L_0x0474
            int r9 = r0.read
            if (r9 == 0) goto L_0x0474
            r14 = 0
            if (r14 >= r9) goto L_0x0470
            int r9 = r9 - r14
            r7 = 1
            int r9 = r9 - r7
            goto L_0x0472
        L_0x0470:
            int r9 = r8 - r14
        L_0x0472:
            r15 = r9
            goto L_0x0477
        L_0x0474:
            r14 = r7
            r15 = r26
        L_0x0477:
            if (r15 != 0) goto L_0x04c1
            r0.write = r14
            int r1 = r0.inflate_flush(r11, r1)
            int r7 = r0.write
            int r8 = r0.read
            if (r7 >= r8) goto L_0x0489
            int r8 = r8 - r7
            r9 = 1
            int r8 = r8 - r9
            goto L_0x048c
        L_0x0489:
            int r8 = r0.end
            int r8 = r8 - r7
        L_0x048c:
            int r9 = r0.end
            if (r7 != r9) goto L_0x04a1
            int r10 = r0.read
            if (r10 == 0) goto L_0x04a1
            r7 = 0
            if (r7 >= r10) goto L_0x049b
            int r10 = r10 - r7
            r9 = 1
            int r10 = r10 - r9
            goto L_0x049d
        L_0x049b:
            int r10 = r9 - r7
        L_0x049d:
            r8 = r10
            r14 = r7
            r15 = r8
            goto L_0x04a3
        L_0x04a1:
            r14 = r7
            r15 = r8
        L_0x04a3:
            if (r15 != 0) goto L_0x04c1
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r7 = r11.total_in
            int r9 = r11.next_in_index
            int r9 = r2 - r9
            long r9 = (long) r9
            long r7 = r7 + r9
            r11.total_in = r7
            r11.next_in_index = r2
            r0.write = r14
            int r7 = r0.inflate_flush(r11, r1)
            return r7
        L_0x04be:
            r14 = r7
            r15 = r26
        L_0x04c1:
            r1 = 0
            int r6 = r0.left
            if (r6 <= r3) goto L_0x04c7
            r6 = r3
        L_0x04c7:
            if (r6 <= r15) goto L_0x04ca
            r6 = r15
        L_0x04ca:
            byte[] r7 = r11.next_in
            byte[] r8 = r0.window
            java.lang.System.arraycopy(r7, r2, r8, r14, r6)
            int r2 = r2 + r6
            int r3 = r3 - r6
            int r14 = r14 + r6
            int r15 = r15 - r6
            int r7 = r0.left
            int r7 = r7 - r6
            r0.left = r7
            if (r7 == 0) goto L_0x04dd
            goto L_0x04e7
        L_0x04dd:
            int r7 = r0.last
            if (r7 == 0) goto L_0x04e3
            r10 = 7
            goto L_0x04e4
        L_0x04e3:
            r10 = 0
        L_0x04e4:
            r0.mode = r10
        L_0x04e7:
            r12 = 1
            r13 = 0
            goto L_0x0023
        L_0x04eb:
            r7 = r14
            r26 = r15
        L_0x04ee:
            r8 = 32
            if (r5 >= r8) goto L_0x051e
            if (r3 == 0) goto L_0x0505
            r1 = 0
            int r3 = r3 + -1
            byte[] r8 = r11.next_in
            int r9 = r2 + 1
            byte r2 = r8[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << r5
            r4 = r4 | r2
            int r5 = r5 + 8
            r2 = r9
            goto L_0x04ee
        L_0x0505:
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r8 = r11.total_in
            int r10 = r11.next_in_index
            int r10 = r2 - r10
            long r12 = (long) r10
            long r8 = r8 + r12
            r11.total_in = r8
            r11.next_in_index = r2
            r0.write = r7
            int r8 = r0.inflate_flush(r11, r1)
            return r8
        L_0x051e:
            int r8 = ~r4
            r9 = 16
            int r8 = r8 >>> r9
            r9 = 65535(0xffff, float:9.1834E-41)
            r8 = r8 & r9
            r10 = r4 & r9
            if (r8 == r10) goto L_0x054c
            r8 = 9
            r0.mode = r8
            java.lang.String r8 = "invalid stored block lengths"
            r11.msg = r8
            r1 = -3
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r8 = r11.total_in
            int r10 = r11.next_in_index
            int r10 = r2 - r10
            long r12 = (long) r10
            long r8 = r8 + r12
            r11.total_in = r8
            r11.next_in_index = r2
            r0.write = r7
            int r8 = r0.inflate_flush(r11, r1)
            return r8
        L_0x054c:
            r8 = r4 & r9
            r0.left = r8
            r9 = 0
            r5 = r9
            r4 = r9
            if (r8 == 0) goto L_0x0557
            r9 = 2
            goto L_0x055e
        L_0x0557:
            int r8 = r0.last
            if (r8 == 0) goto L_0x055d
            r9 = 7
            goto L_0x055e
        L_0x055d:
            r9 = 0
        L_0x055e:
            r0.mode = r9
            r14 = r7
            r15 = r26
            r12 = 1
            r13 = 0
            goto L_0x0023
        L_0x0567:
            r7 = r14
            r26 = r15
            r6 = 6
        L_0x056b:
            r8 = 3
            if (r5 >= r8) goto L_0x059a
            if (r3 == 0) goto L_0x0581
            r1 = 0
            int r3 = r3 + -1
            byte[] r8 = r11.next_in
            int r9 = r2 + 1
            byte r2 = r8[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << r5
            r4 = r4 | r2
            int r5 = r5 + 8
            r2 = r9
            goto L_0x056b
        L_0x0581:
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r8 = r11.total_in
            int r6 = r11.next_in_index
            int r6 = r2 - r6
            long r12 = (long) r6
            long r8 = r8 + r12
            r11.total_in = r8
            r11.next_in_index = r2
            r0.write = r7
            int r6 = r0.inflate_flush(r11, r1)
            return r6
        L_0x059a:
            r8 = r4 & 7
            r9 = r8 & 1
            r0.last = r9
            int r9 = r8 >>> 1
            if (r9 == 0) goto L_0x060a
            r10 = 1
            if (r9 == r10) goto L_0x05e1
            r10 = 2
            if (r9 == r10) goto L_0x05d6
            r6 = 3
            if (r9 == r6) goto L_0x05b1
            r6 = r8
            r8 = 1
            r15 = 0
            goto L_0x0617
        L_0x05b1:
            int r4 = r4 >>> r6
            r6 = -3
            int r5 = r5 + r6
            r6 = 9
            r0.mode = r6
            java.lang.String r6 = "invalid block type"
            r11.msg = r6
            r1 = -3
            r0.bitb = r4
            r0.bitk = r5
            r11.avail_in = r3
            long r9 = r11.total_in
            int r6 = r11.next_in_index
            int r6 = r2 - r6
            long r12 = (long) r6
            long r9 = r9 + r12
            r11.total_in = r9
            r11.next_in_index = r2
            r0.write = r7
            int r6 = r0.inflate_flush(r11, r1)
            return r6
        L_0x05d6:
            int r4 = r4 >>> 3
            int r5 = r5 + -3
            r6 = 3
            r0.mode = r6
            r6 = r8
            r8 = 1
            r15 = 0
            goto L_0x0617
        L_0x05e1:
            r9 = 1
            int[] r10 = new int[r9]
            int[] r12 = new int[r9]
            int[][] r13 = new int[r9][]
            int[][] r14 = new int[r9][]
            org.jboss.netty.util.internal.jzlib.InfTree.inflate_trees_fixed(r10, r12, r13, r14)
            org.jboss.netty.util.internal.jzlib.InfCodes r9 = r0.codes
            r15 = 0
            r19 = r10[r15]
            r20 = r12[r15]
            r21 = r13[r15]
            r22 = 0
            r23 = r14[r15]
            r24 = 0
            r18 = r9
            r18.init(r19, r20, r21, r22, r23, r24)
            int r4 = r4 >>> 3
            int r5 = r5 + -3
            r0.mode = r6
            r6 = r8
            r8 = 1
            goto L_0x0617
        L_0x060a:
            r15 = 0
            int r4 = r4 >>> 3
            int r5 = r5 + -3
            r6 = r5 & 7
            int r4 = r4 >>> r6
            int r5 = r5 - r6
            r8 = 1
            r0.mode = r8
        L_0x0617:
            r14 = r7
            r12 = r8
            r13 = r15
            r15 = r26
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.util.internal.jzlib.InfBlocks.proc(org.jboss.netty.util.internal.jzlib.ZStream, int):int");
    }

    /* access modifiers changed from: package-private */
    public void free(ZStream z) {
        reset(z, (long[]) null);
        this.window = null;
        this.hufts = null;
    }

    /* access modifiers changed from: package-private */
    public void set_dictionary(byte[] d, int start, int n) {
        System.arraycopy(d, start, this.window, 0, n);
        this.write = n;
        this.read = n;
    }

    /* access modifiers changed from: package-private */
    public int sync_point() {
        return this.mode == 1 ? 1 : 0;
    }

    /* access modifiers changed from: package-private */
    public int inflate_flush(ZStream z, int r) {
        int n;
        int p = z.next_out_index;
        int q = this.read;
        int i = this.write;
        if (q > i) {
            i = this.end;
        }
        int n2 = i - q;
        if (n2 > z.avail_out) {
            n2 = z.avail_out;
        }
        if (n2 != 0 && r == -5) {
            r = 0;
        }
        z.avail_out -= n2;
        z.total_out += (long) n2;
        if (this.checkfn != null) {
            long adler32 = Adler32.adler32(this.check, this.window, q, n2);
            this.check = adler32;
            z.adler = adler32;
        }
        System.arraycopy(this.window, q, z.next_out, p, n2);
        int p2 = p + n2;
        int q2 = q + n2;
        int i2 = this.end;
        if (q2 == i2) {
            if (this.write == i2) {
                this.write = 0;
            }
            int n3 = this.write - 0;
            if (n3 > z.avail_out) {
                n = z.avail_out;
            } else {
                n = n3;
            }
            if (n != 0 && r == -5) {
                r = 0;
            }
            z.avail_out -= n;
            z.total_out += (long) n;
            if (this.checkfn != null) {
                long adler322 = Adler32.adler32(this.check, this.window, 0, n);
                this.check = adler322;
                z.adler = adler322;
            }
            System.arraycopy(this.window, 0, z.next_out, p2, n);
            p2 += n;
            q2 = 0 + n;
        }
        z.next_out_index = p2;
        this.read = q2;
        return r;
    }
}
