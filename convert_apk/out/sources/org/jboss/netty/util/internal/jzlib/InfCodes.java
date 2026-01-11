package org.jboss.netty.util.internal.jzlib;

final class InfCodes {
    private static final int BADCODE = 9;
    private static final int COPY = 5;
    private static final int DIST = 3;
    private static final int DISTEXT = 4;
    private static final int END = 8;
    private static final int LEN = 1;
    private static final int LENEXT = 2;
    private static final int LIT = 6;
    private static final int START = 0;
    private static final int WASH = 7;
    private static final int[] inflate_mask = {0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535};
    private byte dbits;
    private int dist;
    private int[] dtree;
    private int dtree_index;
    private int get;
    private byte lbits;
    private int len;
    private int lit;
    private int[] ltree;
    private int ltree_index;
    private int mode;
    private int need;
    private int[] tree;
    private int tree_index;

    InfCodes() {
    }

    /* access modifiers changed from: package-private */
    public void init(int bl, int bd, int[] tl, int tl_index, int[] td, int td_index) {
        this.mode = 0;
        this.lbits = (byte) bl;
        this.dbits = (byte) bd;
        this.ltree = tl;
        this.ltree_index = tl_index;
        this.dtree = td;
        this.dtree_index = td_index;
        this.tree = null;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x02c5, code lost:
        r3 = r0.need;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x02c7, code lost:
        if (r15 >= r3) goto L_0x02f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x02c9, code lost:
        if (r7 == 0) goto L_0x02dd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x02cb, code lost:
        r13 = 0;
        r7 = r7 - 1;
        r14 = r14 | ((r10.next_in[r8] & 255) << r15);
        r15 = r15 + 8;
        r8 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x02dd, code lost:
        r9.bitb = r14;
        r9.bitk = r15;
        r10.avail_in = r7;
        r28 = r1;
        r10.total_in += (long) (r8 - r10.next_in_index);
        r10.next_in_index = r8;
        r9.write = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x02f7, code lost:
        return r9.inflate_flush(r10, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x02f8, code lost:
        r28 = r1;
        r18 = (r0.tree_index + (inflate_mask[r3] & r14)) * 3;
        r1 = r0.tree;
        r14 = r14 >> r1[r18 + 1];
        r15 = r15 - r1[r18 + 1];
        r2 = r1[r18];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0314, code lost:
        if ((r2 & 16) == 0) goto L_0x0324;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x0316, code lost:
        r0.get = r2 & 15;
        r0.dist = r1[r18 + 2];
        r0.mode = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0326, code lost:
        if ((r2 & 64) != 0) goto L_0x033a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0328, code lost:
        r0.need = r2;
        r0.tree_index = (r18 / 3) + r1[r18 + 2];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0334, code lost:
        r16 = r2;
        r17 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x033a, code lost:
        r0.mode = 9;
        r10.msg = "invalid distance code";
        r9.bitb = r14;
        r9.bitk = r15;
        r10.avail_in = r7;
        r28 = r2;
        r13 = r3;
        r10.total_in += (long) (r8 - r10.next_in_index);
        r10.next_in_index = r8;
        r9.write = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x035c, code lost:
        return r9.inflate_flush(r10, -3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x03dd, code lost:
        r1 = r0.need;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x03df, code lost:
        if (r15 >= r1) goto L_0x040d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x03e1, code lost:
        if (r7 == 0) goto L_0x03f4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x03e3, code lost:
        r13 = 0;
        r7 = r7 - 1;
        r14 = r14 | ((r10.next_in[r8] & 255) << r15);
        r15 = r15 + 8;
        r8 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x03f4, code lost:
        r9.bitb = r14;
        r9.bitk = r15;
        r10.avail_in = r7;
        r10.total_in += (long) (r8 - r10.next_in_index);
        r10.next_in_index = r8;
        r9.write = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x040c, code lost:
        return r9.inflate_flush(r10, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x040d, code lost:
        r18 = (r0.tree_index + (inflate_mask[r1] & r14)) * 3;
        r2 = r0.tree;
        r14 = r14 >>> r2[r18 + 1];
        r15 = r15 - r2[r18 + 1];
        r3 = r2[r18];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0425, code lost:
        if (r3 != 0) goto L_0x0431;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0427, code lost:
        r0.lit = r2[r18 + 2];
        r0.mode = 6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0433, code lost:
        if ((r3 & 16) == 0) goto L_0x0443;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0435, code lost:
        r0.get = r3 & 15;
        r0.len = r2[r18 + 2];
        r0.mode = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x0445, code lost:
        if ((r3 & 64) != 0) goto L_0x0453;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0447, code lost:
        r0.need = r3;
        r0.tree_index = (r18 / 3) + r2[r18 + 2];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0455, code lost:
        if ((r3 & 32) == 0) goto L_0x0462;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x0457, code lost:
        r0.mode = 7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x045b, code lost:
        r17 = r1;
        r16 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x045f, code lost:
        r12 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x0462, code lost:
        r0.mode = 9;
        r10.msg = "invalid literal/length code";
        r9.bitb = r14;
        r9.bitk = r15;
        r10.avail_in = r7;
        r13 = r1;
        r10.total_in += (long) (r8 - r10.next_in_index);
        r10.next_in_index = r8;
        r9.write = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x0484, code lost:
        return r9.inflate_flush(r10, -3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x01ce, code lost:
        r1 = r6 - r0.dist;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x01d2, code lost:
        if (r1 >= 0) goto L_0x01d8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x01d4, code lost:
        r1 = r1 + r9.end;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x01d8, code lost:
        r19 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01dc, code lost:
        if (r0.len == 0) goto L_0x0262;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01de, code lost:
        if (r5 != 0) goto L_0x0242;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x01e2, code lost:
        if (r6 != r9.end) goto L_0x01f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01e6, code lost:
        if (r9.read == 0) goto L_0x01f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x01e8, code lost:
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01eb, code lost:
        if (0 >= r9.read) goto L_0x01f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01ed, code lost:
        r1 = (r9.read - 0) - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01f2, code lost:
        r1 = r9.end - 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01f5, code lost:
        r5 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01f6, code lost:
        if (r5 != 0) goto L_0x0242;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01f8, code lost:
        r9.write = r6;
        r13 = r9.inflate_flush(r10, r13);
        r1 = r9.write;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0202, code lost:
        if (r1 >= r9.read) goto L_0x0209;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0204, code lost:
        r2 = (r9.read - r1) - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0209, code lost:
        r2 = r9.end - r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x020e, code lost:
        if (r1 != r9.end) goto L_0x0225;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0212, code lost:
        if (r9.read == 0) goto L_0x0225;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0217, code lost:
        if (0 >= r9.read) goto L_0x021e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0219, code lost:
        r3 = (r9.read - 0) - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x021e, code lost:
        r3 = r9.end - 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0221, code lost:
        r6 = 0;
        r5 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0225, code lost:
        r6 = r1;
        r5 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0227, code lost:
        if (r5 != 0) goto L_0x0242;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0229, code lost:
        r9.bitb = r14;
        r9.bitk = r15;
        r10.avail_in = r7;
        r10.total_in += (long) (r8 - r10.next_in_index);
        r10.next_in_index = r8;
        r9.write = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0241, code lost:
        return r9.inflate_flush(r10, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0242, code lost:
        r2 = r6 + 1;
        r4 = r19 + 1;
        r9.window[r6] = r9.window[r19];
        r5 = r5 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0252, code lost:
        if (r4 != r9.end) goto L_0x0258;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0254, code lost:
        r19 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0258, code lost:
        r19 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x025a, code lost:
        r0.len--;
        r6 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0262, code lost:
        r0.mode = r12;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int proc(org.jboss.netty.util.internal.jzlib.InfBlocks r26, org.jboss.netty.util.internal.jzlib.ZStream r27, int r28) {
        /*
            r25 = this;
            r0 = r25
            r9 = r26
            r10 = r27
            r1 = 0
            r2 = 0
            r3 = 0
            int r3 = r10.next_in_index
            int r4 = r10.avail_in
            int r1 = r9.bitb
            int r2 = r9.bitk
            int r5 = r9.write
            int r6 = r9.read
            r11 = 1
            if (r5 >= r6) goto L_0x001d
            int r6 = r9.read
            int r6 = r6 - r5
            int r6 = r6 - r11
            goto L_0x0020
        L_0x001d:
            int r6 = r9.end
            int r6 = r6 - r5
        L_0x0020:
            r12 = 0
            r13 = r28
            r14 = r1
            r15 = r2
            r8 = r3
            r7 = r4
            r16 = r12
            r17 = r16
            r18 = r17
            r19 = r18
            r24 = r6
            r6 = r5
            r5 = r24
        L_0x0034:
            int r1 = r0.mode
            r4 = 9
            r3 = 7
            r2 = 3
            switch(r1) {
                case 0: goto L_0x0361;
                case 1: goto L_0x035d;
                case 2: goto L_0x026d;
                case 3: goto L_0x0266;
                case 4: goto L_0x017c;
                case 5: goto L_0x0175;
                case 6: goto L_0x0100;
                case 7: goto L_0x0098;
                case 8: goto L_0x008f;
                case 9: goto L_0x0069;
                default: goto L_0x003d;
            }
        L_0x003d:
            r20 = r5
            r22 = r6
            r12 = r7
            r0 = r16
            r1 = r17
            r2 = r19
            r3 = r18
            r4 = -2
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r12
            long r5 = r10.total_in
            int r7 = r10.next_in_index
            int r7 = r8 - r7
            r28 = r0
            r11 = r1
            long r0 = (long) r7
            long r5 = r5 + r0
            r10.total_in = r5
            r10.next_in_index = r8
            r6 = r22
            r9.write = r6
            int r0 = r9.inflate_flush(r10, r4)
            return r0
        L_0x0069:
            r1 = r16
            r2 = r17
            r3 = r19
            r4 = r18
            r11 = -3
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r7
            long r12 = r10.total_in
            r28 = r1
            int r1 = r10.next_in_index
            int r1 = r8 - r1
            r16 = r2
            long r1 = (long) r1
            long r12 = r12 + r1
            r10.total_in = r12
            r10.next_in_index = r8
            r9.write = r6
            int r1 = r9.inflate_flush(r10, r11)
            return r1
        L_0x008f:
            r1 = r16
            r2 = r17
            r3 = r19
            r4 = r18
            goto L_0x00e2
        L_0x0098:
            r1 = r16
            r2 = r17
            r4 = r19
            r11 = r18
            if (r15 <= r3) goto L_0x00a8
            int r15 = r15 + -8
            int r7 = r7 + 1
            int r8 = r8 + -1
        L_0x00a8:
            r9.write = r6
            int r13 = r9.inflate_flush(r10, r13)
            int r6 = r9.write
            int r3 = r9.read
            int r12 = r9.write
            if (r3 == r12) goto L_0x00d4
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r7
            r28 = r1
            r12 = r2
            long r1 = r10.total_in
            int r3 = r10.next_in_index
            int r3 = r8 - r3
            r16 = r4
            long r3 = (long) r3
            long r1 = r1 + r3
            r10.total_in = r1
            r10.next_in_index = r8
            r9.write = r6
            int r1 = r9.inflate_flush(r10, r13)
            return r1
        L_0x00d4:
            r28 = r1
            r12 = r2
            r16 = r4
            r1 = 8
            r0.mode = r1
            r1 = r28
            r4 = r11
            r3 = r16
        L_0x00e2:
            r11 = 1
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r7
            long r12 = r10.total_in
            r28 = r1
            int r1 = r10.next_in_index
            int r1 = r8 - r1
            r16 = r2
            long r1 = (long) r1
            long r12 = r12 + r1
            r10.total_in = r12
            r10.next_in_index = r8
            r9.write = r6
            int r1 = r9.inflate_flush(r10, r11)
            return r1
        L_0x0100:
            if (r5 != 0) goto L_0x0164
            int r1 = r9.end
            if (r6 != r1) goto L_0x0118
            int r1 = r9.read
            if (r1 == 0) goto L_0x0118
            r6 = 0
            int r1 = r9.read
            if (r6 >= r1) goto L_0x0114
            int r1 = r9.read
            int r1 = r1 - r6
            int r1 = r1 - r11
            goto L_0x0117
        L_0x0114:
            int r1 = r9.end
            int r1 = r1 - r6
        L_0x0117:
            r5 = r1
        L_0x0118:
            if (r5 != 0) goto L_0x0164
            r9.write = r6
            int r13 = r9.inflate_flush(r10, r13)
            int r1 = r9.write
            int r2 = r9.read
            if (r1 >= r2) goto L_0x012b
            int r2 = r9.read
            int r2 = r2 - r1
            int r2 = r2 - r11
            goto L_0x012e
        L_0x012b:
            int r2 = r9.end
            int r2 = r2 - r1
        L_0x012e:
            int r3 = r9.end
            if (r1 != r3) goto L_0x0147
            int r3 = r9.read
            if (r3 == 0) goto L_0x0147
            r1 = 0
            int r3 = r9.read
            if (r1 >= r3) goto L_0x0140
            int r3 = r9.read
            int r3 = r3 - r1
            int r3 = r3 - r11
            goto L_0x0143
        L_0x0140:
            int r3 = r9.end
            int r3 = r3 - r1
        L_0x0143:
            r2 = r3
            r6 = r1
            r5 = r2
            goto L_0x0149
        L_0x0147:
            r6 = r1
            r5 = r2
        L_0x0149:
            if (r5 != 0) goto L_0x0164
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r7
            long r1 = r10.total_in
            int r3 = r10.next_in_index
            int r3 = r8 - r3
            long r3 = (long) r3
            long r1 = r1 + r3
            r10.total_in = r1
            r10.next_in_index = r8
            r9.write = r6
            int r1 = r9.inflate_flush(r10, r13)
            return r1
        L_0x0164:
            r13 = 0
            byte[] r1 = r9.window
            int r2 = r6 + 1
            int r3 = r0.lit
            byte r3 = (byte) r3
            r1[r6] = r3
            int r5 = r5 + -1
            r0.mode = r12
            r6 = r2
            goto L_0x0034
        L_0x0175:
            r1 = r16
            r2 = r17
            r3 = r18
            goto L_0x01ce
        L_0x017c:
            r1 = r16
            r2 = r17
            r3 = r18
            int r2 = r0.get
        L_0x0184:
            if (r15 >= r2) goto L_0x01b5
            if (r7 == 0) goto L_0x019a
            r13 = 0
            int r7 = r7 + -1
            byte[] r4 = r10.next_in
            int r16 = r8 + 1
            byte r4 = r4[r8]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r4 = r4 << r15
            r14 = r14 | r4
            int r15 = r15 + 8
            r8 = r16
            goto L_0x0184
        L_0x019a:
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r7
            long r11 = r10.total_in
            int r4 = r10.next_in_index
            int r4 = r8 - r4
            r28 = r3
            long r3 = (long) r4
            long r11 = r11 + r3
            r10.total_in = r11
            r10.next_in_index = r8
            r9.write = r6
            int r3 = r9.inflate_flush(r10, r13)
            return r3
        L_0x01b5:
            r28 = r3
            int r3 = r0.dist
            int[] r4 = inflate_mask
            r4 = r4[r2]
            r4 = r4 & r14
            int r3 = r3 + r4
            r0.dist = r3
            int r3 = r14 >> r2
            int r15 = r15 - r2
            r4 = 5
            r0.mode = r4
            r18 = r28
            r16 = r1
            r17 = r2
            r14 = r3
        L_0x01ce:
            int r1 = r0.dist
            int r1 = r6 - r1
        L_0x01d2:
            if (r1 >= 0) goto L_0x01d8
            int r2 = r9.end
            int r1 = r1 + r2
            goto L_0x01d2
        L_0x01d8:
            r19 = r1
        L_0x01da:
            int r1 = r0.len
            if (r1 == 0) goto L_0x0262
            if (r5 != 0) goto L_0x0242
            int r1 = r9.end
            if (r6 != r1) goto L_0x01f6
            int r1 = r9.read
            if (r1 == 0) goto L_0x01f6
            r6 = 0
            int r1 = r9.read
            if (r6 >= r1) goto L_0x01f2
            int r1 = r9.read
            int r1 = r1 - r6
            int r1 = r1 - r11
            goto L_0x01f5
        L_0x01f2:
            int r1 = r9.end
            int r1 = r1 - r6
        L_0x01f5:
            r5 = r1
        L_0x01f6:
            if (r5 != 0) goto L_0x0242
            r9.write = r6
            int r13 = r9.inflate_flush(r10, r13)
            int r1 = r9.write
            int r2 = r9.read
            if (r1 >= r2) goto L_0x0209
            int r2 = r9.read
            int r2 = r2 - r1
            int r2 = r2 - r11
            goto L_0x020c
        L_0x0209:
            int r2 = r9.end
            int r2 = r2 - r1
        L_0x020c:
            int r3 = r9.end
            if (r1 != r3) goto L_0x0225
            int r3 = r9.read
            if (r3 == 0) goto L_0x0225
            r1 = 0
            int r3 = r9.read
            if (r1 >= r3) goto L_0x021e
            int r3 = r9.read
            int r3 = r3 - r1
            int r3 = r3 - r11
            goto L_0x0221
        L_0x021e:
            int r3 = r9.end
            int r3 = r3 - r1
        L_0x0221:
            r2 = r3
            r6 = r1
            r5 = r2
            goto L_0x0227
        L_0x0225:
            r6 = r1
            r5 = r2
        L_0x0227:
            if (r5 != 0) goto L_0x0242
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r7
            long r1 = r10.total_in
            int r3 = r10.next_in_index
            int r3 = r8 - r3
            long r3 = (long) r3
            long r1 = r1 + r3
            r10.total_in = r1
            r10.next_in_index = r8
            r9.write = r6
            int r1 = r9.inflate_flush(r10, r13)
            return r1
        L_0x0242:
            byte[] r1 = r9.window
            int r2 = r6 + 1
            byte[] r3 = r9.window
            int r4 = r19 + 1
            byte r3 = r3[r19]
            r1[r6] = r3
            int r5 = r5 + -1
            int r1 = r9.end
            if (r4 != r1) goto L_0x0258
            r1 = 0
            r19 = r1
            goto L_0x025a
        L_0x0258:
            r19 = r4
        L_0x025a:
            int r1 = r0.len
            int r1 = r1 - r11
            r0.len = r1
            r6 = r2
            goto L_0x01da
        L_0x0262:
            r0.mode = r12
            goto L_0x0034
        L_0x0266:
            r1 = r16
            r3 = r17
            r16 = r18
            goto L_0x02c5
        L_0x026d:
            r1 = r16
            r3 = r17
            r16 = r18
            int r3 = r0.get
        L_0x0275:
            if (r15 >= r3) goto L_0x02a7
            if (r7 == 0) goto L_0x028c
            r13 = 0
            int r7 = r7 + -1
            byte[] r12 = r10.next_in
            int r17 = r8 + 1
            byte r8 = r12[r8]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r8 = r8 << r15
            r14 = r14 | r8
            int r15 = r15 + 8
            r8 = r17
            r12 = 0
            goto L_0x0275
        L_0x028c:
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r7
            long r11 = r10.total_in
            int r2 = r10.next_in_index
            int r2 = r8 - r2
            r28 = r1
            long r1 = (long) r2
            long r11 = r11 + r1
            r10.total_in = r11
            r10.next_in_index = r8
            r9.write = r6
            int r1 = r9.inflate_flush(r10, r13)
            return r1
        L_0x02a7:
            r28 = r1
            int r1 = r0.len
            int[] r12 = inflate_mask
            r12 = r12[r3]
            r12 = r12 & r14
            int r1 = r1 + r12
            r0.len = r1
            int r14 = r14 >> r3
            int r15 = r15 - r3
            byte r1 = r0.dbits
            r0.need = r1
            int[] r1 = r0.dtree
            r0.tree = r1
            int r1 = r0.dtree_index
            r0.tree_index = r1
            r0.mode = r2
            r1 = r28
        L_0x02c5:
            int r3 = r0.need
        L_0x02c7:
            if (r15 >= r3) goto L_0x02f8
            if (r7 == 0) goto L_0x02dd
            r13 = 0
            int r7 = r7 + -1
            byte[] r12 = r10.next_in
            int r17 = r8 + 1
            byte r8 = r12[r8]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r8 = r8 << r15
            r14 = r14 | r8
            int r15 = r15 + 8
            r8 = r17
            goto L_0x02c7
        L_0x02dd:
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r7
            long r11 = r10.total_in
            int r2 = r10.next_in_index
            int r2 = r8 - r2
            r28 = r1
            long r1 = (long) r2
            long r11 = r11 + r1
            r10.total_in = r11
            r10.next_in_index = r8
            r9.write = r6
            int r1 = r9.inflate_flush(r10, r13)
            return r1
        L_0x02f8:
            r28 = r1
            int r1 = r0.tree_index
            int[] r12 = inflate_mask
            r12 = r12[r3]
            r12 = r12 & r14
            int r1 = r1 + r12
            int r18 = r1 * 3
            int[] r1 = r0.tree
            int r2 = r18 + 1
            r2 = r1[r2]
            int r14 = r14 >> r2
            int r2 = r18 + 1
            r2 = r1[r2]
            int r15 = r15 - r2
            r2 = r1[r18]
            r12 = r2 & 16
            if (r12 == 0) goto L_0x0324
            r4 = r2 & 15
            r0.get = r4
            int r4 = r18 + 2
            r1 = r1[r4]
            r0.dist = r1
            r1 = 4
            r0.mode = r1
            goto L_0x0334
        L_0x0324:
            r12 = r2 & 64
            if (r12 != 0) goto L_0x033a
            r0.need = r2
            int r4 = r18 / 3
            int r12 = r18 + 2
            r1 = r1[r12]
            int r4 = r4 + r1
            r0.tree_index = r4
        L_0x0334:
            r16 = r2
            r17 = r3
            goto L_0x045f
        L_0x033a:
            r0.mode = r4
            java.lang.String r1 = "invalid distance code"
            r10.msg = r1
            r1 = -3
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r7
            long r11 = r10.total_in
            int r4 = r10.next_in_index
            int r4 = r8 - r4
            r28 = r2
            r13 = r3
            long r2 = (long) r4
            long r11 = r11 + r2
            r10.total_in = r11
            r10.next_in_index = r8
            r9.write = r6
            int r2 = r9.inflate_flush(r10, r1)
            return r2
        L_0x035d:
            r21 = r2
            goto L_0x03dd
        L_0x0361:
            r1 = 258(0x102, float:3.62E-43)
            if (r5 < r1) goto L_0x03bf
            r1 = 10
            if (r7 < r1) goto L_0x03bf
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r7
            long r2 = r10.total_in
            int r1 = r10.next_in_index
            int r1 = r8 - r1
            r20 = r5
            long r4 = (long) r1
            long r2 = r2 + r4
            r10.total_in = r2
            r10.next_in_index = r8
            r9.write = r6
            byte r1 = r0.lbits
            byte r2 = r0.dbits
            int[] r3 = r0.ltree
            int r4 = r0.ltree_index
            int[] r5 = r0.dtree
            int r12 = r0.dtree_index
            r21 = 3
            r22 = r6
            r6 = r12
            r12 = r7
            r7 = r26
            r23 = r8
            r8 = r27
            int r13 = inflate_fast(r1, r2, r3, r4, r5, r6, r7, r8)
            int r8 = r10.next_in_index
            int r7 = r10.avail_in
            int r14 = r9.bitb
            int r15 = r9.bitk
            int r6 = r9.write
            int r1 = r9.read
            if (r6 >= r1) goto L_0x03ae
            int r1 = r9.read
            int r1 = r1 - r6
            int r1 = r1 - r11
            goto L_0x03b1
        L_0x03ae:
            int r1 = r9.end
            int r1 = r1 - r6
        L_0x03b1:
            r5 = r1
            if (r13 == 0) goto L_0x03cf
            if (r13 != r11) goto L_0x03b8
            r4 = 7
            goto L_0x03ba
        L_0x03b8:
            r4 = 9
        L_0x03ba:
            r0.mode = r4
            r12 = 0
            goto L_0x0034
        L_0x03bf:
            r21 = r2
            r20 = r5
            r22 = r6
            r12 = r7
            r23 = r8
            r7 = r12
            r5 = r20
            r6 = r22
            r8 = r23
        L_0x03cf:
            byte r1 = r0.lbits
            r0.need = r1
            int[] r1 = r0.ltree
            r0.tree = r1
            int r1 = r0.ltree_index
            r0.tree_index = r1
            r0.mode = r11
        L_0x03dd:
            int r1 = r0.need
        L_0x03df:
            if (r15 >= r1) goto L_0x040d
            if (r7 == 0) goto L_0x03f4
            r13 = 0
            int r7 = r7 + -1
            byte[] r2 = r10.next_in
            int r3 = r8 + 1
            byte r2 = r2[r8]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << r15
            r14 = r14 | r2
            int r15 = r15 + 8
            r8 = r3
            goto L_0x03df
        L_0x03f4:
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r7
            long r2 = r10.total_in
            int r4 = r10.next_in_index
            int r4 = r8 - r4
            long r11 = (long) r4
            long r2 = r2 + r11
            r10.total_in = r2
            r10.next_in_index = r8
            r9.write = r6
            int r2 = r9.inflate_flush(r10, r13)
            return r2
        L_0x040d:
            int r2 = r0.tree_index
            int[] r3 = inflate_mask
            r3 = r3[r1]
            r3 = r3 & r14
            int r2 = r2 + r3
            int r18 = r2 * 3
            int[] r2 = r0.tree
            int r3 = r18 + 1
            r3 = r2[r3]
            int r14 = r14 >>> r3
            int r3 = r18 + 1
            r3 = r2[r3]
            int r15 = r15 - r3
            r3 = r2[r18]
            if (r3 != 0) goto L_0x0431
            int r4 = r18 + 2
            r2 = r2[r4]
            r0.lit = r2
            r2 = 6
            r0.mode = r2
            goto L_0x045b
        L_0x0431:
            r4 = r3 & 16
            if (r4 == 0) goto L_0x0443
            r4 = r3 & 15
            r0.get = r4
            int r4 = r18 + 2
            r2 = r2[r4]
            r0.len = r2
            r2 = 2
            r0.mode = r2
            goto L_0x045b
        L_0x0443:
            r4 = r3 & 64
            if (r4 != 0) goto L_0x0453
            r0.need = r3
            int r4 = r18 / 3
            int r12 = r18 + 2
            r2 = r2[r12]
            int r4 = r4 + r2
            r0.tree_index = r4
            goto L_0x045b
        L_0x0453:
            r2 = r3 & 32
            if (r2 == 0) goto L_0x0462
            r2 = 7
            r0.mode = r2
        L_0x045b:
            r17 = r1
            r16 = r3
        L_0x045f:
            r12 = 0
            goto L_0x0034
        L_0x0462:
            r2 = 9
            r0.mode = r2
            java.lang.String r2 = "invalid literal/length code"
            r10.msg = r2
            r2 = -3
            r9.bitb = r14
            r9.bitk = r15
            r10.avail_in = r7
            long r11 = r10.total_in
            int r4 = r10.next_in_index
            int r4 = r8 - r4
            r13 = r1
            long r0 = (long) r4
            long r11 = r11 + r0
            r10.total_in = r11
            r10.next_in_index = r8
            r9.write = r6
            int r0 = r9.inflate_flush(r10, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.util.internal.jzlib.InfCodes.proc(org.jboss.netty.util.internal.jzlib.InfBlocks, org.jboss.netty.util.internal.jzlib.ZStream, int):int");
    }

    static int inflate_fast(int bl, int bd, int[] tl, int tl_index, int[] td, int td_index, InfBlocks s, ZStream z) {
        int md;
        int r;
        int m;
        InfBlocks infBlocks = s;
        ZStream zStream = z;
        int p = zStream.next_in_index;
        int n = zStream.avail_in;
        int b = infBlocks.bitb;
        int k = infBlocks.bitk;
        int q = infBlocks.write;
        int m2 = q < infBlocks.read ? (infBlocks.read - q) - 1 : infBlocks.end - q;
        int[] iArr = inflate_mask;
        int ml = iArr[bl];
        int b2 = iArr[bd];
        while (true) {
            if (k < 20) {
                n--;
                b |= (zStream.next_in[p] & 255) << k;
                k += 8;
                p++;
            } else {
                int t = b & ml;
                int[] tp = tl;
                int tp_index = tl_index;
                int tp_index_t_3 = (tp_index + t) * 3;
                int i = tp[tp_index_t_3];
                int e = i;
                if (i == 0) {
                    b >>= tp[tp_index_t_3 + 1];
                    k -= tp[tp_index_t_3 + 1];
                    infBlocks.window[q] = (byte) tp[tp_index_t_3 + 2];
                    m = m2 - 1;
                    md = b2;
                    q++;
                } else {
                    while (true) {
                        b >>= tp[tp_index_t_3 + 1];
                        k -= tp[tp_index_t_3 + 1];
                        if ((e & 16) != 0) {
                            int e2 = e & 15;
                            int c = tp[tp_index_t_3 + 2] + (b & inflate_mask[e2]);
                            int b3 = b >> e2;
                            int k2 = k - e2;
                            while (true) {
                                int e3 = e2;
                                if (k2 >= 15) {
                                    break;
                                }
                                n--;
                                b3 |= (zStream.next_in[p] & 255) << k2;
                                k2 += 8;
                                e2 = e3;
                                p++;
                            }
                            int t2 = b3 & b2;
                            int[] tp2 = td;
                            int tp_index2 = td_index;
                            int tp_index_t_32 = (tp_index2 + t2) * 3;
                            int tp_index_t_33 = tp2[tp_index_t_32];
                            int i2 = tp_index_t_32;
                            int t3 = t2;
                            int e4 = tp_index_t_33;
                            int tp_index_t_34 = i2;
                            while (true) {
                                b3 >>= tp2[tp_index_t_34 + 1];
                                k2 -= tp2[tp_index_t_34 + 1];
                                if ((e4 & 16) != 0) {
                                    int e5 = e4 & 15;
                                    int p2 = p;
                                    int n2 = n;
                                    while (k2 < e5) {
                                        n2--;
                                        b3 |= (zStream.next_in[p2] & 255) << k2;
                                        k2 += 8;
                                        p2++;
                                    }
                                    int d = tp2[tp_index_t_34 + 2] + (inflate_mask[e5] & b3);
                                    int b4 = b3 >> e5;
                                    int k3 = k2 - e5;
                                    int m3 = m2 - c;
                                    if (q >= d) {
                                        int r2 = q - d;
                                        if (q - r2 <= 0 || 2 <= q - r2) {
                                            System.arraycopy(infBlocks.window, r2, infBlocks.window, q, 2);
                                            q += 2;
                                            r = r2 + 2;
                                            c -= 2;
                                        } else {
                                            int q2 = q + 1;
                                            int r3 = r2 + 1;
                                            infBlocks.window[q] = infBlocks.window[r2];
                                            q = q2 + 1;
                                            r = r3 + 1;
                                            infBlocks.window[q2] = infBlocks.window[r3];
                                            c -= 2;
                                        }
                                    } else {
                                        int r4 = q - d;
                                        do {
                                            r4 += infBlocks.end;
                                        } while (r4 < 0);
                                        int e6 = infBlocks.end - r4;
                                        if (c > e6) {
                                            c -= e6;
                                            if (q - r4 <= 0 || e6 <= q - r4) {
                                                System.arraycopy(infBlocks.window, r4, infBlocks.window, q, e6);
                                                q += e6;
                                                int i3 = r4 + e6;
                                                e6 = 0;
                                            } else {
                                                while (true) {
                                                    int r5 = r4 + 1;
                                                    infBlocks.window[q] = infBlocks.window[r4];
                                                    e6--;
                                                    q++;
                                                    if (e6 == 0) {
                                                        break;
                                                    }
                                                    r4 = r5;
                                                }
                                            }
                                            r = 0;
                                            e5 = e6;
                                        } else {
                                            r = r4;
                                            e5 = e6;
                                        }
                                    }
                                    if (q - r <= 0 || c <= q - r) {
                                        System.arraycopy(infBlocks.window, r, infBlocks.window, q, c);
                                        q += c;
                                        int r6 = r + c;
                                        n = n2;
                                        p = p2;
                                        b = b4;
                                        k = k3;
                                        m = m3;
                                        int n3 = e5;
                                        md = b2;
                                    } else {
                                        while (true) {
                                            int r7 = r + 1;
                                            infBlocks.window[q] = infBlocks.window[r];
                                            c--;
                                            q++;
                                            if (c == 0) {
                                                break;
                                            }
                                            r = r7;
                                        }
                                        n = n2;
                                        p = p2;
                                        b = b4;
                                        k = k3;
                                        m = m3;
                                        int n4 = e5;
                                        md = b2;
                                    }
                                } else if ((e4 & 64) == 0) {
                                    t3 = t3 + tp2[tp_index_t_34 + 2] + (b3 & inflate_mask[e4]);
                                    tp_index_t_34 = (tp_index2 + t3) * 3;
                                    e4 = tp2[tp_index_t_34];
                                } else {
                                    int i4 = e4;
                                    zStream.msg = "invalid distance code";
                                    int c2 = zStream.avail_in - n;
                                    int c3 = (k2 >> 3) < c2 ? k2 >> 3 : c2;
                                    int n5 = n + c3;
                                    int p3 = p - c3;
                                    int k4 = k2 - (c3 << 3);
                                    infBlocks.bitb = b3;
                                    infBlocks.bitk = k4;
                                    zStream.avail_in = n5;
                                    int i5 = n5;
                                    int i6 = b3;
                                    int i7 = k4;
                                    int i8 = c3;
                                    int i9 = b2;
                                    zStream.total_in += (long) (p3 - zStream.next_in_index);
                                    zStream.next_in_index = p3;
                                    infBlocks.write = q;
                                    return -3;
                                }
                            }
                        } else {
                            md = b2;
                            if ((e & 64) == 0) {
                                t = t + tp[tp_index_t_3 + 2] + (inflate_mask[e] & b);
                                tp_index_t_3 = (tp_index + t) * 3;
                                int i10 = tp[tp_index_t_3];
                                e = i10;
                                if (i10 == 0) {
                                    b >>= tp[tp_index_t_3 + 1];
                                    k -= tp[tp_index_t_3 + 1];
                                    infBlocks.window[q] = (byte) tp[tp_index_t_3 + 2];
                                    m = m2 - 1;
                                    q++;
                                    break;
                                }
                                b2 = md;
                            } else if ((e & 32) != 0) {
                                int c4 = zStream.avail_in - n;
                                int c5 = (k >> 3) < c4 ? k >> 3 : c4;
                                int n6 = n + c5;
                                int p4 = p - c5;
                                infBlocks.bitb = b;
                                infBlocks.bitk = k - (c5 << 3);
                                zStream.avail_in = n6;
                                int i11 = c5;
                                int i12 = n6;
                                int i13 = m2;
                                int i14 = ml;
                                zStream.total_in += (long) (p4 - zStream.next_in_index);
                                zStream.next_in_index = p4;
                                infBlocks.write = q;
                                return 1;
                            } else {
                                int i15 = ml;
                                zStream.msg = "invalid literal/length code";
                                int c6 = zStream.avail_in - n;
                                int c7 = (k >> 3) < c6 ? k >> 3 : c6;
                                int n7 = n + c7;
                                int p5 = p - c7;
                                infBlocks.bitb = b;
                                infBlocks.bitk = k - (c7 << 3);
                                zStream.avail_in = n7;
                                int i16 = n7;
                                int i17 = b;
                                zStream.total_in += (long) (p5 - zStream.next_in_index);
                                zStream.next_in_index = p5;
                                infBlocks.write = q;
                                return -3;
                            }
                        }
                    }
                }
                if (m2 < 258 || n < 10) {
                    int c8 = zStream.avail_in - n;
                } else {
                    b2 = md;
                }
            }
        }
        int c82 = zStream.avail_in - n;
        int c9 = (k >> 3) < c82 ? k >> 3 : c82;
        int n8 = n + c9;
        int p6 = p - c9;
        int k5 = k - (c9 << 3);
        infBlocks.bitb = b;
        infBlocks.bitk = k5;
        zStream.avail_in = n8;
        int i18 = n8;
        int i19 = b;
        int i20 = k5;
        int i21 = m2;
        int i22 = c9;
        zStream.total_in += (long) (p6 - zStream.next_in_index);
        zStream.next_in_index = p6;
        infBlocks.write = q;
        return 0;
    }
}
