package p004;

import java.io.IOException;
import java.io.PrintStream;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;

/* renamed from: dig */
public class dig {
    static int dclass = 1;
    static Name name = null;
    static int type = 1;

    static void usage() {
        System.out.println("Usage: dig [@server] name [<type>] [<class>] [options]");
        System.exit(0);
    }

    static void doQuery(Message response, long ms) throws IOException {
        System.out.println("; java dig 0.0");
        System.out.println(response);
        PrintStream printStream = System.out;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(";; Query time: ");
        stringBuffer.append(ms);
        stringBuffer.append(" ms");
        printStream.println(stringBuffer.toString());
    }

    static void doAXFR(Message response) throws IOException {
        PrintStream printStream = System.out;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("; java dig 0.0 <> ");
        stringBuffer.append(name);
        stringBuffer.append(" axfr");
        printStream.println(stringBuffer.toString());
        if (response.isSigned()) {
            System.out.print(";; TSIG ");
            if (response.isVerified()) {
                System.out.println("ok");
            } else {
                System.out.println("failed");
            }
        }
        if (response.getRcode() != 0) {
            System.out.println(response);
            return;
        }
        Record[] records = response.getSectionArray(1);
        for (Record println : records) {
            System.out.println(println);
        }
        System.out.print(";; done (");
        System.out.print(response.getHeader().getCount(1));
        System.out.print(" records, ");
        System.out.print(response.getHeader().getCount(3));
        System.out.println(" additional)");
    }

    /* JADX WARNING: Removed duplicated region for block: B:129:0x01da  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x01e0  */
    /* JADX WARNING: Removed duplicated region for block: B:135:0x01f6  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x020d  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x0211  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void main(java.lang.String[] r17) throws java.io.IOException {
        /*
            r1 = r17
            r2 = 0
            r3 = 0
            r4 = 0
            int r0 = r1.length
            r5 = 1
            if (r0 >= r5) goto L_0x000c
            usage()
        L_0x000c:
            r6 = 0
            r0 = r1[r6]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d5 }
            java.lang.String r7 = "@"
            boolean r0 = r0.startsWith(r7)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d5 }
            if (r0 == 0) goto L_0x0026
            int r7 = r6 + 1
            r0 = r1[r6]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            java.lang.String r0 = r0.substring(r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r2 = r0
            r6 = r7
            goto L_0x0026
        L_0x0022:
            r0 = move-exception
            r6 = r7
            goto L_0x01d6
        L_0x0026:
            if (r2 == 0) goto L_0x002f
            org.xbill.DNS.SimpleResolver r0 = new org.xbill.DNS.SimpleResolver     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d5 }
            r0.<init>(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d5 }
            r3 = r0
            goto L_0x0035
        L_0x002f:
            org.xbill.DNS.SimpleResolver r0 = new org.xbill.DNS.SimpleResolver     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d5 }
            r0.<init>()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d5 }
            r3 = r0
        L_0x0035:
            int r7 = r6 + 1
            r0 = r1[r6]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r6 = r0
            java.lang.String r0 = "-x"
            boolean r0 = r6.equals(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            if (r0 == 0) goto L_0x005f
            int r10 = r7 + 1
            r0 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x005b }
            org.xbill.DNS.Name r0 = org.xbill.DNS.ReverseMap.fromAddress((java.lang.String) r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x005b }
            name = r0     // Catch:{ ArrayIndexOutOfBoundsException -> 0x005b }
            r0 = 12
            type = r0     // Catch:{ ArrayIndexOutOfBoundsException -> 0x005b }
            dclass = r5     // Catch:{ ArrayIndexOutOfBoundsException -> 0x005b }
            r7 = r10
            r0 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            goto L_0x0093
        L_0x005b:
            r0 = move-exception
            r6 = r10
            goto L_0x01d6
        L_0x005f:
            org.xbill.DNS.Name r0 = org.xbill.DNS.Name.root     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            org.xbill.DNS.Name r0 = org.xbill.DNS.Name.fromString(r6, r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            name = r0     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r0 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            int r0 = org.xbill.DNS.Type.value(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            type = r0     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            if (r0 >= 0) goto L_0x0074
            type = r5     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            goto L_0x0076
        L_0x0074:
            int r7 = r7 + 1
        L_0x0076:
            r0 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            int r0 = org.xbill.DNS.DClass.value(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            dclass = r0     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            if (r0 >= 0) goto L_0x008a
            dclass = r5     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r0 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            goto L_0x0093
        L_0x008a:
            int r7 = r7 + 1
            r0 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
        L_0x0093:
            r8 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            java.lang.String r9 = "-"
            boolean r8 = r8.startsWith(r9)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            if (r8 == 0) goto L_0x01d4
            r8 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            int r8 = r8.length()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            if (r8 <= r5) goto L_0x01d4
            r8 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            char r8 = r8.charAt(r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r9 = 98
            r5 = 2
            if (r8 == r9) goto L_0x0199
            r9 = 105(0x69, float:1.47E-43)
            if (r8 == r9) goto L_0x0190
            r9 = 107(0x6b, float:1.5E-43)
            if (r8 == r9) goto L_0x016f
            r9 = 116(0x74, float:1.63E-43)
            if (r8 == r9) goto L_0x0166
            r9 = 100
            if (r8 == r9) goto L_0x0159
            r9 = 101(0x65, float:1.42E-43)
            if (r8 == r9) goto L_0x0117
            r9 = 112(0x70, float:1.57E-43)
            if (r8 == r9) goto L_0x00e6
            r5 = 113(0x71, float:1.58E-43)
            if (r8 == r5) goto L_0x00e0
            r5 = r10
            java.io.PrintStream r8 = java.lang.System.out     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            java.lang.String r9 = "Invalid option: "
            r8.print(r9)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            java.io.PrintStream r8 = java.lang.System.out     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r9 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r8.println(r9)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r10 = r5
            r8 = 0
            r9 = 0
            goto L_0x01be
        L_0x00e0:
            r5 = r10
            r4 = 1
            r8 = 0
            r9 = 0
            goto L_0x01be
        L_0x00e6:
            r0 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            int r0 = r0.length()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            if (r0 <= r5) goto L_0x00f6
            r0 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            java.lang.String r0 = r0.substring(r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r5 = r7
            goto L_0x00fa
        L_0x00f6:
            int r5 = r7 + 1
            r0 = r1[r5]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
        L_0x00fa:
            int r7 = java.lang.Integer.parseInt(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            if (r7 < 0) goto L_0x010f
            r8 = 65536(0x10000, float:9.18355E-41)
            if (r7 <= r8) goto L_0x0105
            goto L_0x010f
        L_0x0105:
            r3.setPort(r7)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            r10 = r0
            r0 = r7
            r8 = 0
            r9 = 0
            r7 = r5
            goto L_0x01be
        L_0x010f:
            java.io.PrintStream r8 = java.lang.System.out     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            java.lang.String r9 = "Invalid port"
            r8.println(r9)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            return
        L_0x0117:
            r8 = r10
            r9 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            int r9 = r9.length()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            if (r9 <= r5) goto L_0x0129
            r9 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            java.lang.String r5 = r9.substring(r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r15 = r5
            r5 = r7
            goto L_0x012e
        L_0x0129:
            int r5 = r7 + 1
            r7 = r1[r5]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            r15 = r7
        L_0x012e:
            int r7 = java.lang.Integer.parseInt(r15)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            r14 = r7
            if (r14 < 0) goto L_0x0142
            r7 = 1
            if (r14 <= r7) goto L_0x0139
            goto L_0x0142
        L_0x0139:
            r3.setEDNS(r14)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            r7 = r5
            r10 = r8
            r8 = 0
            r9 = 0
            goto L_0x01be
        L_0x0142:
            java.io.PrintStream r7 = java.lang.System.out     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            java.lang.StringBuffer r9 = new java.lang.StringBuffer     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            r9.<init>()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            java.lang.String r10 = "Unsupported EDNS level: "
            r9.append(r10)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            r9.append(r14)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            java.lang.String r9 = r9.toString()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            r7.println(r9)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            return
        L_0x0159:
            r5 = r10
            r8 = 32768(0x8000, float:4.5918E-41)
            r9 = 0
            r10 = 0
            r3.setEDNS(r9, r9, r8, r10)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r8 = r10
            r10 = r5
            goto L_0x01be
        L_0x0166:
            r8 = 0
            r9 = 0
            r5 = r10
            r10 = 1
            r3.setTCP(r10)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r10 = r5
            goto L_0x01be
        L_0x016f:
            r8 = 0
            r9 = 0
            r13 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            int r13 = r13.length()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            if (r13 <= r5) goto L_0x0182
            r13 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            java.lang.String r5 = r13.substring(r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r13 = r5
            r5 = r7
            goto L_0x0187
        L_0x0182:
            int r5 = r7 + 1
            r7 = r1[r5]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            r13 = r7
        L_0x0187:
            org.xbill.DNS.TSIG r7 = org.xbill.DNS.TSIG.fromString(r13)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            r3.setTSIGKey(r7)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            r7 = r5
            goto L_0x01be
        L_0x0190:
            r8 = 0
            r9 = 0
            r5 = r10
            r10 = 1
            r3.setIgnoreTruncation(r10)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r10 = r5
            goto L_0x01be
        L_0x0199:
            r8 = 0
            r9 = 0
            r11 = r0
            r0 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            int r0 = r0.length()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            if (r0 <= r5) goto L_0x01ad
            r0 = r1[r7]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            java.lang.String r0 = r0.substring(r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0022 }
            r5 = r7
            r7 = r0
            goto L_0x01b2
        L_0x01ad:
            int r5 = r7 + 1
            r0 = r1[r5]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            r7 = r0
        L_0x01b2:
            java.net.InetAddress r0 = java.net.InetAddress.getByName(r7)     // Catch:{ Exception -> 0x01c6 }
            r12 = r0
            r3.setLocalAddress((java.net.InetAddress) r12)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            r0 = r11
            r11 = r7
            r7 = r5
        L_0x01be:
            r16 = 1
            int r7 = r7 + 1
            r5 = r16
            goto L_0x0093
        L_0x01c6:
            r0 = move-exception
            r8 = r0
            r0 = r12
            java.io.PrintStream r9 = java.lang.System.out     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            java.lang.String r12 = "Invalid address"
            r9.println(r12)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x01d1 }
            return
        L_0x01d1:
            r0 = move-exception
            r6 = r5
            goto L_0x01d6
        L_0x01d4:
            goto L_0x01de
        L_0x01d5:
            r0 = move-exception
        L_0x01d6:
            org.xbill.DNS.Name r5 = name
            if (r5 != 0) goto L_0x01dd
            usage()
        L_0x01dd:
            r7 = r6
        L_0x01de:
            if (r3 != 0) goto L_0x01e6
            org.xbill.DNS.SimpleResolver r0 = new org.xbill.DNS.SimpleResolver
            r0.<init>()
            r3 = r0
        L_0x01e6:
            org.xbill.DNS.Name r0 = name
            int r5 = type
            int r6 = dclass
            org.xbill.DNS.Record r0 = org.xbill.DNS.Record.newRecord(r0, r5, r6)
            org.xbill.DNS.Message r5 = org.xbill.DNS.Message.newQuery(r0)
            if (r4 == 0) goto L_0x01fb
            java.io.PrintStream r6 = java.lang.System.out
            r6.println(r5)
        L_0x01fb:
            long r8 = java.lang.System.currentTimeMillis()
            org.xbill.DNS.Message r6 = r3.send(r5)
            long r10 = java.lang.System.currentTimeMillis()
            int r12 = type
            r13 = 252(0xfc, float:3.53E-43)
            if (r12 != r13) goto L_0x0211
            doAXFR(r6)
            goto L_0x0216
        L_0x0211:
            long r12 = r10 - r8
            doQuery(r6, r12)
        L_0x0216:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p004.dig.main(java.lang.String[]):void");
    }
}
