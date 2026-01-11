package org.jboss.netty.util.internal.jzlib;

import org.jboss.netty.util.internal.jzlib.JZlib;

final class Inflate {
    private static final int BAD = 13;
    private static final int BLOCKS = 7;
    private static final int CHECK1 = 11;
    private static final int CHECK2 = 10;
    private static final int CHECK3 = 9;
    private static final int CHECK4 = 8;
    private static final int DICT0 = 6;
    private static final int DICT1 = 5;
    private static final int DICT2 = 4;
    private static final int DICT3 = 3;
    private static final int DICT4 = 2;
    private static final int DONE = 12;
    private static final int FLAG = 1;
    private static final int GZIP_CM = 16;
    private static final int GZIP_CRC32 = 24;
    private static final int GZIP_FCOMMENT = 22;
    private static final int GZIP_FEXTRA = 20;
    private static final int GZIP_FHCRC = 23;
    private static final int GZIP_FLG = 17;
    private static final int GZIP_FNAME = 21;
    private static final int GZIP_ID1 = 14;
    private static final int GZIP_ID2 = 15;
    private static final int GZIP_ISIZE = 25;
    private static final int GZIP_MTIME_XFL_OS = 18;
    private static final int GZIP_XLEN = 19;
    private static final int METHOD = 0;
    private static final byte[] mark = {0, 0, -1, -1};
    private InfBlocks blocks;
    private int gzipBytesToRead;
    private int gzipCRC32;
    private int gzipFlag;
    private int gzipISize;
    private int gzipUncompressedBytes;
    private int gzipXLen;
    private int marker;
    private int method;
    private int mode;
    private long need;
    private final long[] was = new long[1];
    private int wbits;
    private JZlib.WrapperType wrapperType;

    Inflate() {
    }

    private int inflateReset(ZStream z) {
        if (z == null || z.istate == null) {
            return -2;
        }
        z.total_out = 0;
        z.total_in = 0;
        z.msg = null;
        int i = C09131.$SwitchMap$org$jboss$netty$util$internal$jzlib$JZlib$WrapperType[this.wrapperType.ordinal()];
        if (i == 1) {
            z.istate.mode = 7;
        } else if (i == 2 || i == 3) {
            z.istate.mode = 0;
        } else if (i == 4) {
            z.istate.mode = 14;
        }
        z.istate.blocks.reset(z, (long[]) null);
        this.gzipUncompressedBytes = 0;
        return 0;
    }

    /* renamed from: org.jboss.netty.util.internal.jzlib.Inflate$1 */
    static /* synthetic */ class C09131 {
        static final /* synthetic */ int[] $SwitchMap$org$jboss$netty$util$internal$jzlib$JZlib$WrapperType;

        static {
            int[] iArr = new int[JZlib.WrapperType.values().length];
            $SwitchMap$org$jboss$netty$util$internal$jzlib$JZlib$WrapperType = iArr;
            try {
                iArr[JZlib.WrapperType.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$jboss$netty$util$internal$jzlib$JZlib$WrapperType[JZlib.WrapperType.ZLIB.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$jboss$netty$util$internal$jzlib$JZlib$WrapperType[JZlib.WrapperType.ZLIB_OR_NONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$jboss$netty$util$internal$jzlib$JZlib$WrapperType[JZlib.WrapperType.GZIP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int inflateEnd(ZStream z) {
        InfBlocks infBlocks = this.blocks;
        if (infBlocks != null) {
            infBlocks.free(z);
        }
        this.blocks = null;
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int inflateInit(ZStream z, int w, JZlib.WrapperType wrapperType2) {
        Inflate inflate = null;
        z.msg = null;
        this.blocks = null;
        this.wrapperType = wrapperType2;
        if (w < 0) {
            throw new IllegalArgumentException("w: " + w);
        } else if (w < 8 || w > 15) {
            inflateEnd(z);
            return -2;
        } else {
            this.wbits = w;
            Inflate inflate2 = z.istate;
            if (z.istate.wrapperType != JZlib.WrapperType.NONE) {
                inflate = this;
            }
            inflate2.blocks = new InfBlocks(z, inflate, 1 << w);
            inflateReset(z);
            return 0;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x027f, code lost:
        if ((8 & r1.gzipFlag) == 0) goto L_0x029e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0283, code lost:
        if (r2.avail_in != 0) goto L_0x0286;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0285, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0286, code lost:
        r6 = r5;
        r2.avail_in--;
        r2.total_in++;
        r4 = r2.next_in;
        r8 = r2.next_in_index;
        r2.next_in_index = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x029c, code lost:
        if (r4[r8] != 0) goto L_0x0281;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x029e, code lost:
        r2.istate.mode = 22;
        r4 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x02aa, code lost:
        if ((r1.gzipFlag & 16) == 0) goto L_0x02c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x02ae, code lost:
        if (r2.avail_in != 0) goto L_0x02b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x02b0, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x02b1, code lost:
        r6 = r5;
        r2.avail_in--;
        r2.total_in++;
        r7 = r2.next_in;
        r8 = r2.next_in_index;
        r2.next_in_index = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x02c7, code lost:
        if (r7[r8] != 0) goto L_0x02ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x02c9, code lost:
        r7 = 2;
        r1.gzipBytesToRead = 2;
        r2.istate.mode = 23;
        r8 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x02d6, code lost:
        if ((r1.gzipFlag & r7) == 0) goto L_0x02f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x02da, code lost:
        if (r1.gzipBytesToRead <= 0) goto L_0x02f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x02de, code lost:
        if (r2.avail_in != 0) goto L_0x02e1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x02e0, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x02e1, code lost:
        r6 = r5;
        r2.avail_in--;
        r2.total_in++;
        r2.next_in_index++;
        r1.gzipBytesToRead--;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x02f8, code lost:
        r2.istate.mode = 7;
        r7 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x0300, code lost:
        r2.istate.mode = 21;
        r7 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x03a7, code lost:
        if (r2.avail_in != 0) goto L_0x03aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x03a9, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x03aa, code lost:
        r6 = r5;
        r2.avail_in--;
        r2.total_in++;
        r8 = r2.istate;
        r11 = r2.next_in;
        r12 = r2.next_in_index;
        r2.next_in_index = r12 + 1;
        r8.need = ((long) ((r11[r12] & 255) << com.google.common.base.Ascii.CAN)) & 4278190080L;
        r2.istate.mode = 9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x03d4, code lost:
        if (r2.avail_in != 0) goto L_0x03d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x03d6, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x03d7, code lost:
        r6 = r5;
        r2.avail_in--;
        r2.total_in++;
        r8 = r2.istate;
        r11 = r8.need;
        r13 = r2.next_in;
        r14 = r2.next_in_index;
        r2.next_in_index = r14 + 1;
        r8.need = r11 + (((long) ((r13[r14] & 255) << 16)) & 16711680);
        r2.istate.mode = 10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x0407, code lost:
        if (r2.avail_in != 0) goto L_0x040a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x0409, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x040a, code lost:
        r6 = r5;
        r2.avail_in--;
        r2.total_in++;
        r8 = r2.istate;
        r11 = r8.need;
        r9 = r2.next_in;
        r13 = r2.next_in_index;
        r2.next_in_index = r13 + 1;
        r8.need = r11 + (((long) ((r9[r13] & 255) << 8)) & 65280);
        r2.istate.mode = 11;
        r8 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x043a, code lost:
        if (r2.avail_in != 0) goto L_0x043d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x043c, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x043d, code lost:
        r6 = r5;
        r2.avail_in--;
        r2.total_in++;
        r4 = r2.istate;
        r11 = r4.need;
        r7 = r2.next_in;
        r9 = r2.next_in_index;
        r2.next_in_index = r9 + 1;
        r4.need = r11 + (((long) r7[r9]) & 255);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x046c, code lost:
        if (((int) r2.istate.was[0]) == ((int) r2.istate.need)) goto L_0x047e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x046e, code lost:
        r2.istate.mode = 13;
        r2.msg = "incorrect data check";
        r2.istate.marker = 5;
        r7 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x047e, code lost:
        r2.istate.mode = 12;
        r3 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x0485, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0177, code lost:
        if (r2.avail_in != 0) goto L_0x017a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0179, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x017a, code lost:
        r6 = r5;
        r2.avail_in--;
        r2.total_in++;
        r7 = r2.next_in;
        r9 = r2.next_in_index;
        r2.next_in_index = r9 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0192, code lost:
        if ((r7[r9] & 255) == 8) goto L_0x01a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0194, code lost:
        r2.istate.mode = 13;
        r2.msg = "unknown compression method";
        r2.istate.marker = 5;
        r7 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01a5, code lost:
        r2.istate.mode = 17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x01ad, code lost:
        if (r2.avail_in != 0) goto L_0x01b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01af, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01b0, code lost:
        r6 = r5;
        r2.avail_in--;
        r2.total_in++;
        r7 = r2.next_in;
        r9 = r2.next_in_index;
        r2.next_in_index = r9 + 1;
        r7 = r7[r9] & 255;
        r1.gzipFlag = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01cc, code lost:
        if ((r7 & 226) == 0) goto L_0x01df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01ce, code lost:
        r2.istate.mode = 13;
        r2.msg = "unsupported flag";
        r2.istate.marker = 5;
        r7 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01df, code lost:
        r1.gzipBytesToRead = 6;
        r2.istate.mode = 18;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01eb, code lost:
        if (r1.gzipBytesToRead <= 0) goto L_0x0209;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01ef, code lost:
        if (r2.avail_in != 0) goto L_0x01f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01f1, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01f2, code lost:
        r6 = r5;
        r2.avail_in--;
        r2.total_in++;
        r2.next_in_index++;
        r1.gzipBytesToRead--;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0209, code lost:
        r2.istate.mode = 19;
        r1.gzipXLen = 0;
        r1.gzipBytesToRead = 2;
        r8 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0219, code lost:
        if ((r1.gzipFlag & 4) == 0) goto L_0x0300;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x021d, code lost:
        if (r1.gzipBytesToRead <= 0) goto L_0x024b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0221, code lost:
        if (r2.avail_in != 0) goto L_0x0224;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0223, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0224, code lost:
        r6 = r5;
        r2.avail_in--;
        r2.total_in++;
        r7 = r1.gzipXLen;
        r9 = r2.next_in;
        r10 = r2.next_in_index;
        r2.next_in_index = r10 + 1;
        r10 = r1.gzipBytesToRead;
        r1.gzipXLen = r7 | ((r9[r10] & 255) << ((1 - r10) * 8));
        r1.gzipBytesToRead = r10 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x024b, code lost:
        r1.gzipBytesToRead = r1.gzipXLen;
        r2.istate.mode = 20;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0258, code lost:
        if (r1.gzipBytesToRead <= 0) goto L_0x0276;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x025c, code lost:
        if (r2.avail_in != 0) goto L_0x025f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x025e, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x025f, code lost:
        r6 = r5;
        r2.avail_in--;
        r2.total_in++;
        r2.next_in_index++;
        r1.gzipBytesToRead--;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0276, code lost:
        r2.istate.mode = 21;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int inflate(org.jboss.netty.util.internal.jzlib.ZStream r21, int r22) {
        /*
            r20 = this;
            r1 = r20
            r2 = r21
            if (r2 == 0) goto L_0x066b
            org.jboss.netty.util.internal.jzlib.Inflate r3 = r2.istate
            if (r3 == 0) goto L_0x066b
            byte[] r3 = r2.next_in
            if (r3 != 0) goto L_0x0013
            r5 = r22
            r4 = -2
            goto L_0x066e
        L_0x0013:
            r3 = 4
            r5 = r22
            if (r5 != r3) goto L_0x001a
            r6 = -5
            goto L_0x001b
        L_0x001a:
            r6 = 0
        L_0x001b:
            r5 = r6
            r6 = -5
            r7 = 0
            r8 = 0
        L_0x001f:
            org.jboss.netty.util.internal.jzlib.Inflate r9 = r2.istate
            int r9 = r9.mode
            r16 = 4278190080(0xff000000, double:2.113706745E-314)
            r0 = 31
            r11 = 15
            r14 = 3
            r10 = 13
            r4 = 8
            r18 = 1
            r15 = 1
            switch(r9) {
                case 0: goto L_0x04e0;
                case 1: goto L_0x04dd;
                case 2: goto L_0x04da;
                case 3: goto L_0x04d7;
                case 4: goto L_0x04d4;
                case 5: goto L_0x04d1;
                case 6: goto L_0x04c1;
                case 7: goto L_0x0321;
                case 8: goto L_0x031d;
                case 9: goto L_0x0319;
                case 10: goto L_0x0315;
                case 11: goto L_0x0311;
                case 12: goto L_0x030d;
                case 13: goto L_0x0309;
                case 14: goto L_0x010b;
                case 15: goto L_0x0108;
                case 16: goto L_0x0104;
                case 17: goto L_0x0100;
                case 18: goto L_0x00fc;
                case 19: goto L_0x00f8;
                case 20: goto L_0x00f4;
                case 21: goto L_0x00f0;
                case 22: goto L_0x00ec;
                case 23: goto L_0x00e7;
                case 24: goto L_0x003e;
                case 25: goto L_0x003b;
                default: goto L_0x0037;
            }
        L_0x0037:
            r0 = r7
            r3 = r8
            r4 = -2
            return r4
        L_0x003b:
            r0 = r7
            r7 = r8
            goto L_0x0093
        L_0x003e:
            r0 = r7
        L_0x003f:
            int r7 = r1.gzipBytesToRead
            if (r7 <= 0) goto L_0x0073
            int r7 = r2.avail_in
            if (r7 != 0) goto L_0x0048
            return r6
        L_0x0048:
            r6 = r5
            int r7 = r2.avail_in
            int r7 = r7 - r15
            r2.avail_in = r7
            long r12 = r2.total_in
            long r12 = r12 + r18
            r2.total_in = r12
            int r7 = r1.gzipBytesToRead
            int r7 = r7 - r15
            r1.gzipBytesToRead = r7
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            int r11 = r7.gzipCRC32
            byte[] r12 = r2.next_in
            int r13 = r2.next_in_index
            int r9 = r13 + 1
            r2.next_in_index = r9
            byte r9 = r12[r13]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r12 = r1.gzipBytesToRead
            int r12 = 3 - r12
            int r12 = r12 * r4
            int r9 = r9 << r12
            r9 = r9 | r11
            r7.gzipCRC32 = r9
            goto L_0x003f
        L_0x0073:
            int r7 = r2.crc32
            org.jboss.netty.util.internal.jzlib.Inflate r9 = r2.istate
            int r9 = r9.gzipCRC32
            if (r7 == r9) goto L_0x008b
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r4.mode = r10
            java.lang.String r4 = "incorrect CRC32 checksum"
            r2.msg = r4
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r7 = 5
            r4.marker = r7
            r7 = r0
            goto L_0x05a5
        L_0x008b:
            r1.gzipBytesToRead = r3
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            r9 = 25
            r7.mode = r9
        L_0x0093:
            int r7 = r1.gzipBytesToRead
            if (r7 <= 0) goto L_0x00c7
            int r7 = r2.avail_in
            if (r7 != 0) goto L_0x009c
            return r6
        L_0x009c:
            r6 = r5
            int r7 = r2.avail_in
            int r7 = r7 - r15
            r2.avail_in = r7
            long r11 = r2.total_in
            long r11 = r11 + r18
            r2.total_in = r11
            int r7 = r1.gzipBytesToRead
            int r7 = r7 - r15
            r1.gzipBytesToRead = r7
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            int r9 = r7.gzipISize
            byte[] r11 = r2.next_in
            int r12 = r2.next_in_index
            int r13 = r12 + 1
            r2.next_in_index = r13
            byte r11 = r11[r12]
            r11 = r11 & 255(0xff, float:3.57E-43)
            int r12 = r1.gzipBytesToRead
            int r12 = 3 - r12
            int r12 = r12 * r4
            int r11 = r11 << r12
            r9 = r9 | r11
            r7.gzipISize = r9
            goto L_0x0093
        L_0x00c7:
            int r4 = r1.gzipUncompressedBytes
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            int r7 = r7.gzipISize
            if (r4 == r7) goto L_0x00dd
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r4.mode = r10
            java.lang.String r4 = "incorrect ISIZE checksum"
            r2.msg = r4
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r7 = 5
            r4.marker = r7
            goto L_0x00e4
        L_0x00dd:
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r7 = 12
            r4.mode = r7
        L_0x00e4:
            r7 = r0
            goto L_0x05a5
        L_0x00e7:
            r0 = r7
            r4 = r8
            r7 = 2
            goto L_0x02d3
        L_0x00ec:
            r0 = r7
            r4 = r8
            goto L_0x02a5
        L_0x00f0:
            r0 = r7
            r7 = r8
            goto L_0x027c
        L_0x00f4:
            r0 = r7
            r7 = r8
            goto L_0x0256
        L_0x00f8:
            r0 = r7
            r7 = r8
            goto L_0x0216
        L_0x00fc:
            r0 = r7
            r7 = r8
            goto L_0x01e9
        L_0x0100:
            r0 = r7
            r7 = r8
            goto L_0x01ab
        L_0x0104:
            r0 = r7
            r7 = r8
            goto L_0x0175
        L_0x0108:
            r0 = r7
            r7 = r8
            goto L_0x013e
        L_0x010b:
            int r9 = r2.avail_in
            if (r9 != 0) goto L_0x0110
            return r6
        L_0x0110:
            r6 = r5
            int r9 = r2.avail_in
            int r9 = r9 - r15
            r2.avail_in = r9
            long r12 = r2.total_in
            long r12 = r12 + r18
            r2.total_in = r12
            byte[] r9 = r2.next_in
            int r12 = r2.next_in_index
            int r13 = r12 + 1
            r2.next_in_index = r13
            byte r9 = r9[r12]
            r9 = r9 & 255(0xff, float:3.57E-43)
            if (r9 == r0) goto L_0x0139
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r0.mode = r10
            java.lang.String r0 = "not a gzip stream"
            r2.msg = r0
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r4 = 5
            r0.marker = r4
            goto L_0x05a5
        L_0x0139:
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r0.mode = r11
            r0 = r7
        L_0x013e:
            int r7 = r2.avail_in
            if (r7 != 0) goto L_0x0143
            return r6
        L_0x0143:
            r6 = r5
            int r7 = r2.avail_in
            int r7 = r7 - r15
            r2.avail_in = r7
            long r11 = r2.total_in
            long r11 = r11 + r18
            r2.total_in = r11
            byte[] r7 = r2.next_in
            int r9 = r2.next_in_index
            int r11 = r9 + 1
            r2.next_in_index = r11
            byte r7 = r7[r9]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r9 = 139(0x8b, float:1.95E-43)
            if (r7 == r9) goto L_0x016f
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r4.mode = r10
            java.lang.String r4 = "not a gzip stream"
            r2.msg = r4
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r7 = 5
            r4.marker = r7
            r7 = r0
            goto L_0x05a5
        L_0x016f:
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            r9 = 16
            r7.mode = r9
        L_0x0175:
            int r7 = r2.avail_in
            if (r7 != 0) goto L_0x017a
            return r6
        L_0x017a:
            r6 = r5
            int r7 = r2.avail_in
            int r7 = r7 - r15
            r2.avail_in = r7
            long r11 = r2.total_in
            long r11 = r11 + r18
            r2.total_in = r11
            byte[] r7 = r2.next_in
            int r9 = r2.next_in_index
            int r11 = r9 + 1
            r2.next_in_index = r11
            byte r7 = r7[r9]
            r7 = r7 & 255(0xff, float:3.57E-43)
            if (r7 == r4) goto L_0x01a5
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r4.mode = r10
            java.lang.String r4 = "unknown compression method"
            r2.msg = r4
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r7 = 5
            r4.marker = r7
            r7 = r0
            goto L_0x05a5
        L_0x01a5:
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            r9 = 17
            r7.mode = r9
        L_0x01ab:
            int r7 = r2.avail_in
            if (r7 != 0) goto L_0x01b0
            return r6
        L_0x01b0:
            r6 = r5
            int r7 = r2.avail_in
            int r7 = r7 - r15
            r2.avail_in = r7
            long r11 = r2.total_in
            long r11 = r11 + r18
            r2.total_in = r11
            byte[] r7 = r2.next_in
            int r9 = r2.next_in_index
            int r11 = r9 + 1
            r2.next_in_index = r11
            byte r7 = r7[r9]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r1.gzipFlag = r7
            r7 = r7 & 226(0xe2, float:3.17E-43)
            if (r7 == 0) goto L_0x01df
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r4.mode = r10
            java.lang.String r4 = "unsupported flag"
            r2.msg = r4
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r7 = 5
            r4.marker = r7
            r7 = r0
            goto L_0x05a5
        L_0x01df:
            r7 = 6
            r1.gzipBytesToRead = r7
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            r9 = 18
            r7.mode = r9
            r7 = r8
        L_0x01e9:
            int r8 = r1.gzipBytesToRead
            if (r8 <= 0) goto L_0x0209
            int r8 = r2.avail_in
            if (r8 != 0) goto L_0x01f2
            return r6
        L_0x01f2:
            r6 = r5
            int r8 = r2.avail_in
            int r8 = r8 - r15
            r2.avail_in = r8
            long r8 = r2.total_in
            long r8 = r8 + r18
            r2.total_in = r8
            int r8 = r2.next_in_index
            int r8 = r8 + r15
            r2.next_in_index = r8
            int r8 = r1.gzipBytesToRead
            int r8 = r8 - r15
            r1.gzipBytesToRead = r8
            goto L_0x01e9
        L_0x0209:
            org.jboss.netty.util.internal.jzlib.Inflate r8 = r2.istate
            r9 = 19
            r8.mode = r9
            r8 = 0
            r1.gzipXLen = r8
            r8 = 2
            r1.gzipBytesToRead = r8
            r8 = r7
        L_0x0216:
            int r7 = r1.gzipFlag
            r7 = r7 & r3
            if (r7 == 0) goto L_0x0300
        L_0x021b:
            int r7 = r1.gzipBytesToRead
            if (r7 <= 0) goto L_0x024b
            int r7 = r2.avail_in
            if (r7 != 0) goto L_0x0224
            return r6
        L_0x0224:
            r6 = r5
            int r7 = r2.avail_in
            int r7 = r7 - r15
            r2.avail_in = r7
            long r9 = r2.total_in
            long r9 = r9 + r18
            r2.total_in = r9
            int r7 = r1.gzipXLen
            byte[] r9 = r2.next_in
            int r10 = r2.next_in_index
            int r11 = r10 + 1
            r2.next_in_index = r11
            byte r9 = r9[r10]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r10 = r1.gzipBytesToRead
            int r11 = 1 - r10
            int r11 = r11 * r4
            int r9 = r9 << r11
            r7 = r7 | r9
            r1.gzipXLen = r7
            int r10 = r10 - r15
            r1.gzipBytesToRead = r10
            goto L_0x021b
        L_0x024b:
            int r7 = r1.gzipXLen
            r1.gzipBytesToRead = r7
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            r9 = 20
            r7.mode = r9
            r7 = r8
        L_0x0256:
            int r8 = r1.gzipBytesToRead
            if (r8 <= 0) goto L_0x0276
            int r8 = r2.avail_in
            if (r8 != 0) goto L_0x025f
            return r6
        L_0x025f:
            r6 = r5
            int r8 = r2.avail_in
            int r8 = r8 - r15
            r2.avail_in = r8
            long r8 = r2.total_in
            long r8 = r8 + r18
            r2.total_in = r8
            int r8 = r2.next_in_index
            int r8 = r8 + r15
            r2.next_in_index = r8
            int r8 = r1.gzipBytesToRead
            int r8 = r8 - r15
            r1.gzipBytesToRead = r8
            goto L_0x0256
        L_0x0276:
            org.jboss.netty.util.internal.jzlib.Inflate r8 = r2.istate
            r9 = 21
            r8.mode = r9
        L_0x027c:
            int r8 = r1.gzipFlag
            r4 = r4 & r8
            if (r4 == 0) goto L_0x029e
        L_0x0281:
            int r4 = r2.avail_in
            if (r4 != 0) goto L_0x0286
            return r6
        L_0x0286:
            r6 = r5
            int r4 = r2.avail_in
            int r4 = r4 - r15
            r2.avail_in = r4
            long r8 = r2.total_in
            long r8 = r8 + r18
            r2.total_in = r8
            byte[] r4 = r2.next_in
            int r8 = r2.next_in_index
            int r9 = r8 + 1
            r2.next_in_index = r9
            byte r4 = r4[r8]
            if (r4 != 0) goto L_0x0281
        L_0x029e:
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r8 = 22
            r4.mode = r8
            r4 = r7
        L_0x02a5:
            int r7 = r1.gzipFlag
            r8 = 16
            r7 = r7 & r8
            if (r7 == 0) goto L_0x02c9
        L_0x02ac:
            int r7 = r2.avail_in
            if (r7 != 0) goto L_0x02b1
            return r6
        L_0x02b1:
            r6 = r5
            int r7 = r2.avail_in
            int r7 = r7 - r15
            r2.avail_in = r7
            long r7 = r2.total_in
            long r7 = r7 + r18
            r2.total_in = r7
            byte[] r7 = r2.next_in
            int r8 = r2.next_in_index
            int r9 = r8 + 1
            r2.next_in_index = r9
            byte r7 = r7[r8]
            if (r7 != 0) goto L_0x02ac
        L_0x02c9:
            r7 = 2
            r1.gzipBytesToRead = r7
            org.jboss.netty.util.internal.jzlib.Inflate r8 = r2.istate
            r9 = 23
            r8.mode = r9
            r8 = r4
        L_0x02d3:
            int r4 = r1.gzipFlag
            r4 = r4 & r7
            if (r4 == 0) goto L_0x02f8
        L_0x02d8:
            int r4 = r1.gzipBytesToRead
            if (r4 <= 0) goto L_0x02f8
            int r4 = r2.avail_in
            if (r4 != 0) goto L_0x02e1
            return r6
        L_0x02e1:
            r6 = r5
            int r4 = r2.avail_in
            int r4 = r4 - r15
            r2.avail_in = r4
            long r9 = r2.total_in
            long r9 = r9 + r18
            r2.total_in = r9
            int r4 = r2.next_in_index
            int r4 = r4 + r15
            r2.next_in_index = r4
            int r4 = r1.gzipBytesToRead
            int r4 = r4 - r15
            r1.gzipBytesToRead = r4
            goto L_0x02d8
        L_0x02f8:
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r7 = 7
            r4.mode = r7
            r7 = r0
            goto L_0x05a5
        L_0x0300:
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r7 = 21
            r4.mode = r7
            r7 = r0
            goto L_0x05a5
        L_0x0309:
            r0 = r7
            r3 = r8
            r4 = -3
            return r4
        L_0x030d:
            r0 = r7
            r3 = r8
            goto L_0x0485
        L_0x0311:
            r0 = r7
            r4 = r8
            goto L_0x0438
        L_0x0315:
            r0 = r7
            r7 = r8
            goto L_0x0405
        L_0x0319:
            r0 = r7
            r7 = r8
            goto L_0x03d2
        L_0x031d:
            r0 = r7
            r7 = r8
            goto L_0x03a5
        L_0x0321:
            int r8 = r2.next_out_index
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate     // Catch:{ all -> 0x04ad }
            org.jboss.netty.util.internal.jzlib.InfBlocks r0 = r0.blocks     // Catch:{ all -> 0x04ad }
            int r0 = r0.proc(r2, r6)     // Catch:{ all -> 0x04ad }
            r6 = r0
            r0 = -3
            if (r6 != r0) goto L_0x034f
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate     // Catch:{ all -> 0x034c }
            r0.mode = r10     // Catch:{ all -> 0x034c }
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate     // Catch:{ all -> 0x034c }
            r4 = 0
            r0.marker = r4     // Catch:{ all -> 0x034c }
            int r0 = r2.next_out_index
            int r0 = r0 - r8
            int r4 = r1.gzipUncompressedBytes
            int r4 = r4 + r0
            r1.gzipUncompressedBytes = r4
            int r4 = r2.crc32
            byte[] r9 = r2.next_out
            int r4 = org.jboss.netty.util.internal.jzlib.CRC32.crc32(r4, r9, r8, r0)
            r2.crc32 = r4
            goto L_0x05a5
        L_0x034c:
            r0 = move-exception
            goto L_0x04ae
        L_0x034f:
            if (r6 != 0) goto L_0x0352
            r6 = r5
        L_0x0352:
            if (r6 == r15) goto L_0x0368
            int r0 = r2.next_out_index
            int r0 = r0 - r8
            int r3 = r1.gzipUncompressedBytes
            int r3 = r3 + r0
            r1.gzipUncompressedBytes = r3
            int r3 = r2.crc32
            byte[] r4 = r2.next_out
            int r3 = org.jboss.netty.util.internal.jzlib.CRC32.crc32(r3, r4, r8, r0)
            r2.crc32 = r3
            return r6
        L_0x0368:
            r6 = r5
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate     // Catch:{ all -> 0x04ad }
            org.jboss.netty.util.internal.jzlib.InfBlocks r0 = r0.blocks     // Catch:{ all -> 0x04ad }
            org.jboss.netty.util.internal.jzlib.Inflate r11 = r2.istate     // Catch:{ all -> 0x04ad }
            long[] r11 = r11.was     // Catch:{ all -> 0x04ad }
            r0.reset(r2, r11)     // Catch:{ all -> 0x04ad }
            int r0 = r2.next_out_index
            int r0 = r0 - r8
            int r11 = r1.gzipUncompressedBytes
            int r11 = r11 + r0
            r1.gzipUncompressedBytes = r11
            int r11 = r2.crc32
            byte[] r12 = r2.next_out
            int r11 = org.jboss.netty.util.internal.jzlib.CRC32.crc32(r11, r12, r8, r0)
            r2.crc32 = r11
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            org.jboss.netty.util.internal.jzlib.JZlib$WrapperType r0 = r0.wrapperType
            org.jboss.netty.util.internal.jzlib.JZlib$WrapperType r11 = org.jboss.netty.util.internal.jzlib.JZlib.WrapperType.NONE
            if (r0 != r11) goto L_0x0397
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r4 = 12
            r0.mode = r4
            goto L_0x05a5
        L_0x0397:
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            org.jboss.netty.util.internal.jzlib.JZlib$WrapperType r0 = r0.wrapperType
            org.jboss.netty.util.internal.jzlib.JZlib$WrapperType r11 = org.jboss.netty.util.internal.jzlib.JZlib.WrapperType.ZLIB
            if (r0 != r11) goto L_0x0486
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r0.mode = r4
            r0 = r7
            r7 = r8
        L_0x03a5:
            int r8 = r2.avail_in
            if (r8 != 0) goto L_0x03aa
            return r6
        L_0x03aa:
            r6 = r5
            int r8 = r2.avail_in
            int r8 = r8 - r15
            r2.avail_in = r8
            long r11 = r2.total_in
            long r11 = r11 + r18
            r2.total_in = r11
            org.jboss.netty.util.internal.jzlib.Inflate r8 = r2.istate
            byte[] r11 = r2.next_in
            int r12 = r2.next_in_index
            int r13 = r12 + 1
            r2.next_in_index = r13
            byte r11 = r11[r12]
            r11 = r11 & 255(0xff, float:3.57E-43)
            r12 = 24
            int r11 = r11 << r12
            long r11 = (long) r11
            long r11 = r11 & r16
            r8.need = r11
            org.jboss.netty.util.internal.jzlib.Inflate r8 = r2.istate
            r11 = 9
            r8.mode = r11
        L_0x03d2:
            int r8 = r2.avail_in
            if (r8 != 0) goto L_0x03d7
            return r6
        L_0x03d7:
            r6 = r5
            int r8 = r2.avail_in
            int r8 = r8 - r15
            r2.avail_in = r8
            long r11 = r2.total_in
            long r11 = r11 + r18
            r2.total_in = r11
            org.jboss.netty.util.internal.jzlib.Inflate r8 = r2.istate
            long r11 = r8.need
            byte[] r13 = r2.next_in
            int r14 = r2.next_in_index
            int r9 = r14 + 1
            r2.next_in_index = r9
            byte r9 = r13[r14]
            r9 = r9 & 255(0xff, float:3.57E-43)
            r13 = 16
            int r9 = r9 << r13
            long r13 = (long) r9
            r16 = 16711680(0xff0000, double:8.256667E-317)
            long r13 = r13 & r16
            long r11 = r11 + r13
            r8.need = r11
            org.jboss.netty.util.internal.jzlib.Inflate r8 = r2.istate
            r9 = 10
            r8.mode = r9
        L_0x0405:
            int r8 = r2.avail_in
            if (r8 != 0) goto L_0x040a
            return r6
        L_0x040a:
            r6 = r5
            int r8 = r2.avail_in
            int r8 = r8 - r15
            r2.avail_in = r8
            long r8 = r2.total_in
            long r8 = r8 + r18
            r2.total_in = r8
            org.jboss.netty.util.internal.jzlib.Inflate r8 = r2.istate
            long r11 = r8.need
            byte[] r9 = r2.next_in
            int r13 = r2.next_in_index
            int r14 = r13 + 1
            r2.next_in_index = r14
            byte r9 = r9[r13]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r4 = r9 << 8
            long r13 = (long) r4
            r16 = 65280(0xff00, double:3.22526E-319)
            long r13 = r13 & r16
            long r11 = r11 + r13
            r8.need = r11
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r8 = 11
            r4.mode = r8
            r8 = r7
        L_0x0438:
            int r4 = r2.avail_in
            if (r4 != 0) goto L_0x043d
            return r6
        L_0x043d:
            r6 = r5
            int r4 = r2.avail_in
            int r4 = r4 - r15
            r2.avail_in = r4
            long r11 = r2.total_in
            long r11 = r11 + r18
            r2.total_in = r11
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            long r11 = r4.need
            byte[] r7 = r2.next_in
            int r9 = r2.next_in_index
            int r13 = r9 + 1
            r2.next_in_index = r13
            byte r7 = r7[r9]
            long r13 = (long) r7
            r16 = 255(0xff, double:1.26E-321)
            long r13 = r13 & r16
            long r11 = r11 + r13
            r4.need = r11
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            long[] r4 = r4.was
            r7 = 0
            r11 = r4[r7]
            int r4 = (int) r11
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            long r11 = r7.need
            int r7 = (int) r11
            if (r4 == r7) goto L_0x047e
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r4.mode = r10
            java.lang.String r4 = "incorrect data check"
            r2.msg = r4
            org.jboss.netty.util.internal.jzlib.Inflate r4 = r2.istate
            r7 = 5
            r4.marker = r7
            r7 = r0
            goto L_0x05a5
        L_0x047e:
            org.jboss.netty.util.internal.jzlib.Inflate r3 = r2.istate
            r4 = 12
            r3.mode = r4
            r3 = r8
        L_0x0485:
            return r15
        L_0x0486:
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            org.jboss.netty.util.internal.jzlib.JZlib$WrapperType r0 = r0.wrapperType
            org.jboss.netty.util.internal.jzlib.JZlib$WrapperType r4 = org.jboss.netty.util.internal.jzlib.JZlib.WrapperType.GZIP
            if (r0 != r4) goto L_0x049d
            r0 = 0
            r1.gzipCRC32 = r0
            r1.gzipISize = r0
            r1.gzipBytesToRead = r3
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r4 = 24
            r0.mode = r4
            goto L_0x05a5
        L_0x049d:
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r0.mode = r10
            java.lang.String r0 = "unexpected state"
            r2.msg = r0
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r4 = 0
            r0.marker = r4
            goto L_0x05a5
        L_0x04ad:
            r0 = move-exception
        L_0x04ae:
            int r3 = r2.next_out_index
            int r3 = r3 - r8
            int r4 = r1.gzipUncompressedBytes
            int r4 = r4 + r3
            r1.gzipUncompressedBytes = r4
            int r4 = r2.crc32
            byte[] r9 = r2.next_out
            int r4 = org.jboss.netty.util.internal.jzlib.CRC32.crc32(r4, r9, r8, r3)
            r2.crc32 = r4
            throw r0
        L_0x04c1:
            r0 = r7
            org.jboss.netty.util.internal.jzlib.Inflate r3 = r2.istate
            r3.mode = r10
            java.lang.String r3 = "need dictionary"
            r2.msg = r3
            org.jboss.netty.util.internal.jzlib.Inflate r3 = r2.istate
            r9 = 0
            r3.marker = r9
            r3 = -2
            return r3
        L_0x04d1:
            r0 = r7
            goto L_0x0639
        L_0x04d4:
            r0 = r7
            goto L_0x0609
        L_0x04d7:
            r0 = r7
            goto L_0x05d9
        L_0x04da:
            r0 = r7
            goto L_0x05ad
        L_0x04dd:
            r9 = 0
            goto L_0x0567
        L_0x04e0:
            r9 = 0
            int r12 = r2.avail_in
            if (r12 != 0) goto L_0x04e6
            return r6
        L_0x04e6:
            org.jboss.netty.util.internal.jzlib.Inflate r12 = r2.istate
            org.jboss.netty.util.internal.jzlib.JZlib$WrapperType r12 = r12.wrapperType
            org.jboss.netty.util.internal.jzlib.JZlib$WrapperType r13 = org.jboss.netty.util.internal.jzlib.JZlib.WrapperType.ZLIB_OR_NONE
            if (r12 != r13) goto L_0x051b
            byte[] r12 = r2.next_in
            int r13 = r2.next_in_index
            byte r12 = r12[r13]
            r12 = r12 & r11
            if (r12 != r4) goto L_0x050d
            byte[] r12 = r2.next_in
            int r13 = r2.next_in_index
            byte r12 = r12[r13]
            int r12 = r12 >> r3
            int r12 = r12 + r4
            org.jboss.netty.util.internal.jzlib.Inflate r13 = r2.istate
            int r13 = r13.wbits
            if (r12 <= r13) goto L_0x0506
            goto L_0x050d
        L_0x0506:
            org.jboss.netty.util.internal.jzlib.Inflate r12 = r2.istate
            org.jboss.netty.util.internal.jzlib.JZlib$WrapperType r13 = org.jboss.netty.util.internal.jzlib.JZlib.WrapperType.ZLIB
            r12.wrapperType = r13
            goto L_0x051b
        L_0x050d:
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            org.jboss.netty.util.internal.jzlib.JZlib$WrapperType r4 = org.jboss.netty.util.internal.jzlib.JZlib.WrapperType.NONE
            r0.wrapperType = r4
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r4 = 7
            r0.mode = r4
            r0 = r7
            goto L_0x05a5
        L_0x051b:
            r6 = r5
            int r12 = r2.avail_in
            int r12 = r12 - r15
            r2.avail_in = r12
            long r12 = r2.total_in
            long r12 = r12 + r18
            r2.total_in = r12
            org.jboss.netty.util.internal.jzlib.Inflate r12 = r2.istate
            byte[] r13 = r2.next_in
            int r9 = r2.next_in_index
            int r14 = r9 + 1
            r2.next_in_index = r14
            byte r9 = r13[r9]
            r12.method = r9
            r9 = r9 & r11
            if (r9 == r4) goto L_0x0548
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r0.mode = r10
            java.lang.String r0 = "unknown compression method"
            r2.msg = r0
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r4 = 5
            r0.marker = r4
            r0 = r7
            goto L_0x05a5
        L_0x0548:
            org.jboss.netty.util.internal.jzlib.Inflate r9 = r2.istate
            int r9 = r9.method
            int r9 = r9 >> r3
            int r9 = r9 + r4
            org.jboss.netty.util.internal.jzlib.Inflate r11 = r2.istate
            int r11 = r11.wbits
            if (r9 <= r11) goto L_0x0563
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r0.mode = r10
            java.lang.String r0 = "invalid window size"
            r2.msg = r0
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r4 = 5
            r0.marker = r4
            r0 = r7
            goto L_0x05a5
        L_0x0563:
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            r7.mode = r15
        L_0x0567:
            int r7 = r2.avail_in
            if (r7 != 0) goto L_0x056c
            return r6
        L_0x056c:
            r6 = r5
            int r7 = r2.avail_in
            int r7 = r7 - r15
            r2.avail_in = r7
            long r11 = r2.total_in
            long r11 = r11 + r18
            r2.total_in = r11
            byte[] r7 = r2.next_in
            int r9 = r2.next_in_index
            int r11 = r9 + 1
            r2.next_in_index = r11
            byte r7 = r7[r9]
            r7 = r7 & 255(0xff, float:3.57E-43)
            org.jboss.netty.util.internal.jzlib.Inflate r9 = r2.istate
            int r9 = r9.method
            int r9 = r9 << r4
            int r9 = r9 + r7
            int r9 = r9 % r0
            if (r9 == 0) goto L_0x059b
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r0.mode = r10
            java.lang.String r0 = "incorrect header check"
            r2.msg = r0
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r4 = 5
            r0.marker = r4
            goto L_0x05a5
        L_0x059b:
            r0 = r7 & 32
            if (r0 != 0) goto L_0x05a7
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r4 = 7
            r0.mode = r4
        L_0x05a5:
            goto L_0x001f
        L_0x05a7:
            org.jboss.netty.util.internal.jzlib.Inflate r0 = r2.istate
            r8 = 2
            r0.mode = r8
            r0 = r7
        L_0x05ad:
            int r7 = r2.avail_in
            if (r7 != 0) goto L_0x05b2
            return r6
        L_0x05b2:
            r6 = r5
            int r7 = r2.avail_in
            int r7 = r7 - r15
            r2.avail_in = r7
            long r7 = r2.total_in
            long r7 = r7 + r18
            r2.total_in = r7
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            byte[] r8 = r2.next_in
            int r9 = r2.next_in_index
            int r10 = r9 + 1
            r2.next_in_index = r10
            byte r8 = r8[r9]
            r8 = r8 & 255(0xff, float:3.57E-43)
            r9 = 24
            int r8 = r8 << r9
            long r8 = (long) r8
            long r8 = r8 & r16
            r7.need = r8
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            r8 = 3
            r7.mode = r8
        L_0x05d9:
            int r7 = r2.avail_in
            if (r7 != 0) goto L_0x05de
            return r6
        L_0x05de:
            r6 = r5
            int r7 = r2.avail_in
            int r7 = r7 - r15
            r2.avail_in = r7
            long r7 = r2.total_in
            long r7 = r7 + r18
            r2.total_in = r7
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            long r8 = r7.need
            byte[] r10 = r2.next_in
            int r11 = r2.next_in_index
            int r12 = r11 + 1
            r2.next_in_index = r12
            byte r10 = r10[r11]
            r10 = r10 & 255(0xff, float:3.57E-43)
            r11 = 16
            int r10 = r10 << r11
            long r10 = (long) r10
            r12 = 16711680(0xff0000, double:8.256667E-317)
            long r10 = r10 & r12
            long r8 = r8 + r10
            r7.need = r8
            org.jboss.netty.util.internal.jzlib.Inflate r7 = r2.istate
            r7.mode = r3
        L_0x0609:
            int r3 = r2.avail_in
            if (r3 != 0) goto L_0x060e
            return r6
        L_0x060e:
            r6 = r5
            int r3 = r2.avail_in
            int r3 = r3 - r15
            r2.avail_in = r3
            long r7 = r2.total_in
            long r7 = r7 + r18
            r2.total_in = r7
            org.jboss.netty.util.internal.jzlib.Inflate r3 = r2.istate
            long r7 = r3.need
            byte[] r9 = r2.next_in
            int r10 = r2.next_in_index
            int r11 = r10 + 1
            r2.next_in_index = r11
            byte r9 = r9[r10]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r4 = r9 << 8
            long r9 = (long) r4
            r11 = 65280(0xff00, double:3.22526E-319)
            long r9 = r9 & r11
            long r7 = r7 + r9
            r3.need = r7
            org.jboss.netty.util.internal.jzlib.Inflate r3 = r2.istate
            r4 = 5
            r3.mode = r4
        L_0x0639:
            int r3 = r2.avail_in
            if (r3 != 0) goto L_0x063e
            return r6
        L_0x063e:
            int r3 = r2.avail_in
            int r3 = r3 - r15
            r2.avail_in = r3
            long r3 = r2.total_in
            long r3 = r3 + r18
            r2.total_in = r3
            org.jboss.netty.util.internal.jzlib.Inflate r3 = r2.istate
            long r7 = r3.need
            byte[] r4 = r2.next_in
            int r9 = r2.next_in_index
            int r10 = r9 + 1
            r2.next_in_index = r10
            byte r4 = r4[r9]
            long r9 = (long) r4
            r11 = 255(0xff, double:1.26E-321)
            long r9 = r9 & r11
            long r7 = r7 + r9
            r3.need = r7
            org.jboss.netty.util.internal.jzlib.Inflate r3 = r2.istate
            long r3 = r3.need
            r2.adler = r3
            org.jboss.netty.util.internal.jzlib.Inflate r3 = r2.istate
            r4 = 6
            r3.mode = r4
            r3 = 2
            return r3
        L_0x066b:
            r5 = r22
            r4 = -2
        L_0x066e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.util.internal.jzlib.Inflate.inflate(org.jboss.netty.util.internal.jzlib.ZStream, int):int");
    }

    static int inflateSetDictionary(ZStream z, byte[] dictionary, int dictLength) {
        int index = 0;
        int length = dictLength;
        if (z == null || z.istate == null || z.istate.mode != 6) {
            return -2;
        }
        if (Adler32.adler32(1, dictionary, 0, dictLength) != z.adler) {
            return -3;
        }
        z.adler = Adler32.adler32(0, (byte[]) null, 0, 0);
        if (length >= (1 << z.istate.wbits)) {
            length = (1 << z.istate.wbits) - 1;
            index = dictLength - length;
        }
        z.istate.blocks.set_dictionary(dictionary, index, length);
        z.istate.mode = 7;
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int inflateSync(ZStream z) {
        if (z == null || z.istate == null) {
            return -2;
        }
        if (z.istate.mode != 13) {
            z.istate.mode = 13;
            z.istate.marker = 0;
        }
        int i = z.avail_in;
        int n = i;
        if (i == 0) {
            return -5;
        }
        int p = z.next_in_index;
        int m = z.istate.marker;
        while (n != 0 && m < 4) {
            if (z.next_in[p] == mark[m]) {
                m++;
            } else if (z.next_in[p] != 0) {
                m = 0;
            } else {
                m = 4 - m;
            }
            p++;
            n--;
        }
        z.total_in += (long) (p - z.next_in_index);
        z.next_in_index = p;
        z.avail_in = n;
        z.istate.marker = m;
        if (m != 4) {
            return -3;
        }
        long r = z.total_in;
        long w = z.total_out;
        inflateReset(z);
        z.total_in = r;
        z.total_out = w;
        z.istate.mode = 7;
        return 0;
    }
}
