package org.jboss.netty.handler.codec.http;

import java.util.List;

public class CookieDecoder {
    private static final String COMMA = ",";

    public CookieDecoder() {
    }

    @Deprecated
    public CookieDecoder(boolean lenient) {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v4, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Set<org.jboss.netty.handler.codec.http.Cookie> decode(java.lang.String r36) {
        /*
            r35 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 8
            r0.<init>(r1)
            r2 = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>(r1)
            r1 = r0
            r3 = r36
            extractKeyValuePairs(r3, r2, r1)
            boolean r0 = r2.isEmpty()
            if (r0 == 0) goto L_0x001e
            java.util.Set r0 = java.util.Collections.emptySet()
            return r0
        L_0x001e:
            r4 = 0
            r5 = 0
            java.lang.Object r0 = r2.get(r5)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r6 = "Version"
            boolean r0 = r0.equalsIgnoreCase(r6)
            if (r0 == 0) goto L_0x003d
            java.lang.Object r0 = r1.get(r5)     // Catch:{ NumberFormatException -> 0x003a }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ NumberFormatException -> 0x003a }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x003a }
            r4 = r0
            goto L_0x003b
        L_0x003a:
            r0 = move-exception
        L_0x003b:
            r0 = 1
            goto L_0x003e
        L_0x003d:
            r0 = 0
        L_0x003e:
            int r7 = r2.size()
            if (r7 > r0) goto L_0x0049
            java.util.Set r5 = java.util.Collections.emptySet()
            return r5
        L_0x0049:
            java.util.TreeSet r7 = new java.util.TreeSet
            r7.<init>()
        L_0x004e:
            int r8 = r2.size()
            if (r0 >= r8) goto L_0x022f
            java.lang.Object r8 = r2.get(r0)
            java.lang.String r8 = (java.lang.String) r8
            java.lang.Object r9 = r1.get(r0)
            java.lang.String r9 = (java.lang.String) r9
            if (r9 != 0) goto L_0x0064
            java.lang.String r9 = ""
        L_0x0064:
            org.jboss.netty.handler.codec.http.DefaultCookie r10 = new org.jboss.netty.handler.codec.http.DefaultCookie
            r10.<init>(r8, r9)
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r18 = -1
            java.util.ArrayList r5 = new java.util.ArrayList
            r3 = 2
            r5.<init>(r3)
            r3 = r5
            int r5 = r0 + 1
            r31 = r4
            r4 = r0
            r0 = r8
            r8 = r5
            r5 = r31
            r32 = r17
            r17 = r7
            r7 = r32
            r33 = r16
            r16 = r9
            r9 = r33
            r34 = r18
            r18 = r11
            r11 = r34
        L_0x0096:
            r19 = r0
            int r0 = r2.size()
            r20 = r15
            if (r8 >= r0) goto L_0x01e0
            java.lang.Object r0 = r2.get(r8)
            r15 = r0
            java.lang.String r15 = (java.lang.String) r15
            java.lang.Object r0 = r1.get(r8)
            r22 = r1
            r1 = r0
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r0 = "Discard"
            boolean r0 = r0.equalsIgnoreCase(r15)
            if (r0 == 0) goto L_0x00c1
            r0 = 1
            r18 = r0
            r16 = r1
            r23 = r2
            goto L_0x01cd
        L_0x00c1:
            java.lang.String r0 = "Secure"
            boolean r0 = r0.equalsIgnoreCase(r15)
            if (r0 == 0) goto L_0x00d1
            r0 = 1
            r12 = r0
            r16 = r1
            r23 = r2
            goto L_0x01cd
        L_0x00d1:
            java.lang.String r0 = "HTTPOnly"
            boolean r0 = r0.equalsIgnoreCase(r15)
            if (r0 == 0) goto L_0x00e1
            r0 = 1
            r13 = r0
            r16 = r1
            r23 = r2
            goto L_0x01cd
        L_0x00e1:
            java.lang.String r0 = "Comment"
            boolean r0 = r0.equalsIgnoreCase(r15)
            if (r0 == 0) goto L_0x00f1
            r0 = r1
            r14 = r0
            r16 = r1
            r23 = r2
            goto L_0x01cd
        L_0x00f1:
            java.lang.String r0 = "CommentURL"
            boolean r0 = r0.equalsIgnoreCase(r15)
            if (r0 == 0) goto L_0x0102
            r0 = r1
            r20 = r0
            r16 = r1
            r23 = r2
            goto L_0x01cd
        L_0x0102:
            java.lang.String r0 = "Domain"
            boolean r0 = r0.equalsIgnoreCase(r15)
            if (r0 == 0) goto L_0x0112
            r0 = r1
            r9 = r0
            r16 = r1
            r23 = r2
            goto L_0x01cd
        L_0x0112:
            java.lang.String r0 = "Path"
            boolean r0 = r0.equalsIgnoreCase(r15)
            if (r0 == 0) goto L_0x0122
            r0 = r1
            r7 = r0
            r16 = r1
            r23 = r2
            goto L_0x01cd
        L_0x0122:
            java.lang.String r0 = "Expires"
            boolean r0 = r0.equalsIgnoreCase(r15)
            if (r0 == 0) goto L_0x0176
            org.jboss.netty.handler.codec.http.CookieDateFormat r0 = new org.jboss.netty.handler.codec.http.CookieDateFormat     // Catch:{ ParseException -> 0x016c }
            r0.<init>()     // Catch:{ ParseException -> 0x016c }
            java.util.Date r0 = r0.parse(r1)     // Catch:{ ParseException -> 0x016c }
            long r23 = r0.getTime()     // Catch:{ ParseException -> 0x016c }
            long r25 = java.lang.System.currentTimeMillis()     // Catch:{ ParseException -> 0x016c }
            long r23 = r23 - r25
            r25 = 0
            int r0 = (r23 > r25 ? 1 : (r23 == r25 ? 0 : -1))
            if (r0 > 0) goto L_0x0149
            r0 = 0
            r29 = r13
            r30 = r14
            goto L_0x015f
        L_0x0149:
            r27 = 1000(0x3e8, double:4.94E-321)
            r29 = r13
            r30 = r14
            long r13 = r23 / r27
            int r0 = (int) r13     // Catch:{ ParseException -> 0x016a }
            long r13 = r23 % r27
            int r13 = (r13 > r25 ? 1 : (r13 == r25 ? 0 : -1))
            if (r13 == 0) goto L_0x015b
            r21 = 1
            goto L_0x015d
        L_0x015b:
            r21 = 0
        L_0x015d:
            int r0 = r0 + r21
        L_0x015f:
            r11 = r0
            r16 = r1
            r23 = r2
            r13 = r29
            r14 = r30
            goto L_0x01cd
        L_0x016a:
            r0 = move-exception
            goto L_0x0171
        L_0x016c:
            r0 = move-exception
            r29 = r13
            r30 = r14
        L_0x0171:
            r16 = r1
            r23 = r2
            goto L_0x01c9
        L_0x0176:
            r29 = r13
            r30 = r14
            java.lang.String r0 = "Max-Age"
            boolean r0 = r0.equalsIgnoreCase(r15)
            if (r0 == 0) goto L_0x0190
            int r0 = java.lang.Integer.parseInt(r1)
            r11 = r0
            r16 = r1
            r23 = r2
            r13 = r29
            r14 = r30
            goto L_0x01cd
        L_0x0190:
            boolean r0 = r6.equalsIgnoreCase(r15)
            if (r0 == 0) goto L_0x01a3
            int r5 = java.lang.Integer.parseInt(r1)
            r16 = r1
            r23 = r2
            r13 = r29
            r14 = r30
            goto L_0x01cd
        L_0x01a3:
            java.lang.String r0 = "Port"
            boolean r0 = r0.equalsIgnoreCase(r15)
            if (r0 == 0) goto L_0x01da
            java.lang.String r0 = ","
            java.lang.String[] r13 = r1.split(r0)
            r14 = r13
            r16 = r1
            int r1 = r14.length
            r0 = 0
            r23 = r2
            r2 = r0
        L_0x01b9:
            if (r2 >= r1) goto L_0x01c9
            r19 = r14[r2]
            java.lang.Integer r0 = java.lang.Integer.valueOf(r19)     // Catch:{ NumberFormatException -> 0x01c5 }
            r3.add(r0)     // Catch:{ NumberFormatException -> 0x01c5 }
            goto L_0x01c6
        L_0x01c5:
            r0 = move-exception
        L_0x01c6:
            int r2 = r2 + 1
            goto L_0x01b9
        L_0x01c9:
            r13 = r29
            r14 = r30
        L_0x01cd:
            int r8 = r8 + 1
            int r4 = r4 + 1
            r0 = r15
            r15 = r20
            r1 = r22
            r2 = r23
            goto L_0x0096
        L_0x01da:
            r16 = r1
            r23 = r2
            r0 = r15
            goto L_0x01ea
        L_0x01e0:
            r22 = r1
            r23 = r2
            r29 = r13
            r30 = r14
            r0 = r19
        L_0x01ea:
            r10.setVersion(r5)
            r10.setMaxAge(r11)
            r10.setPath(r7)
            r10.setDomain(r9)
            r10.setSecure(r12)
            r13 = r29
            r10.setHttpOnly(r13)
            if (r5 <= 0) goto L_0x0206
            r14 = r30
            r10.setComment(r14)
            goto L_0x0208
        L_0x0206:
            r14 = r30
        L_0x0208:
            r1 = 1
            if (r5 <= r1) goto L_0x0219
            r15 = r20
            r10.setCommentUrl(r15)
            r10.setPorts((java.lang.Iterable<java.lang.Integer>) r3)
            r1 = r18
            r10.setDiscard(r1)
            goto L_0x021d
        L_0x0219:
            r1 = r18
            r15 = r20
        L_0x021d:
            r2 = r17
            r2.add(r10)
            int r0 = r4 + 1
            r3 = r36
            r7 = r2
            r4 = r5
            r1 = r22
            r2 = r23
            r5 = 0
            goto L_0x004e
        L_0x022f:
            r23 = r2
            r2 = r7
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.handler.codec.http.CookieDecoder.decode(java.lang.String):java.util.Set");
    }

    private static void extractKeyValuePairs(String header, List<String> names, List<String> values) {
        String value;
        String name;
        String value2;
        int headerLen = header.length();
        int i = 0;
        while (i != headerLen) {
            char charAt = header.charAt(i);
            if (!(charAt == ' ' || charAt == ',' || charAt == ';')) {
                switch (charAt) {
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                        break;
                    default:
                        while (i != headerLen) {
                            if (header.charAt(i) == '$') {
                                i++;
                            } else {
                                if (i == headerLen) {
                                    name = null;
                                    value = null;
                                } else {
                                    int newNameStart = i;
                                    while (true) {
                                        char charAt2 = header.charAt(i);
                                        if (charAt2 == ';') {
                                            name = header.substring(newNameStart, i);
                                            value = null;
                                        } else if (charAt2 != '=') {
                                            i++;
                                            if (i == headerLen) {
                                                name = header.substring(newNameStart);
                                                value = null;
                                            }
                                        } else {
                                            name = header.substring(newNameStart, i);
                                            i++;
                                            if (i == headerLen) {
                                                value = "";
                                            } else {
                                                int newValueStart = i;
                                                int c = header.charAt(i);
                                                if (c == 34 || c == 39) {
                                                    StringBuilder newValueBuf = new StringBuilder(header.length() - i);
                                                    int q = c;
                                                    boolean hadBackslash = false;
                                                    int i2 = i + 1;
                                                    while (true) {
                                                        if (i == headerLen) {
                                                            char c2 = c;
                                                            int i3 = newValueStart;
                                                            value = newValueBuf.toString();
                                                        } else if (hadBackslash) {
                                                            hadBackslash = false;
                                                            int i4 = i + 1;
                                                            c = header.charAt(i);
                                                            if (c == '\"' || c == '\'' || c == '\\') {
                                                                newValueBuf.setCharAt(newValueBuf.length() - 1, c);
                                                            } else {
                                                                newValueBuf.append(c);
                                                            }
                                                            i2 = i4;
                                                        } else {
                                                            int i5 = i + 1;
                                                            c = header.charAt(i);
                                                            if (c == q) {
                                                                char c3 = c;
                                                                int i6 = newValueStart;
                                                                value = newValueBuf.toString();
                                                                i = i5;
                                                            } else {
                                                                newValueBuf.append(c);
                                                                if (c == '\\') {
                                                                    hadBackslash = true;
                                                                    i2 = i5;
                                                                } else {
                                                                    i2 = i5;
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    int semiPos = header.indexOf(59, i);
                                                    if (semiPos > 0) {
                                                        value2 = header.substring(newValueStart, semiPos);
                                                        i = semiPos;
                                                    } else {
                                                        value2 = header.substring(newValueStart);
                                                        i = headerLen;
                                                    }
                                                    int semiPos2 = c;
                                                    int i7 = newValueStart;
                                                    value = value2;
                                                }
                                            }
                                        }
                                    }
                                }
                                names.add(name);
                                values.add(value);
                                continue;
                            }
                        }
                        return;
                }
            }
            i++;
        }
    }
}
