package com.google.common.base;

public final class Ascii {
    public static final byte ACK = 6;
    public static final byte BEL = 7;

    /* renamed from: BS */
    public static final byte f45BS = 8;
    public static final byte CAN = 24;

    /* renamed from: CR */
    public static final byte f46CR = 13;
    public static final byte DC1 = 17;
    public static final byte DC2 = 18;
    public static final byte DC3 = 19;
    public static final byte DC4 = 20;
    public static final byte DEL = Byte.MAX_VALUE;
    public static final byte DLE = 16;

    /* renamed from: EM */
    public static final byte f47EM = 25;
    public static final byte ENQ = 5;
    public static final byte EOT = 4;
    public static final byte ESC = 27;
    public static final byte ETB = 23;
    public static final byte ETX = 3;

    /* renamed from: FF */
    public static final byte f48FF = 12;

    /* renamed from: FS */
    public static final byte f49FS = 28;

    /* renamed from: GS */
    public static final byte f50GS = 29;

    /* renamed from: HT */
    public static final byte f51HT = 9;

    /* renamed from: LF */
    public static final byte f52LF = 10;
    public static final char MAX = '';
    public static final char MIN = '\u0000';
    public static final byte NAK = 21;

    /* renamed from: NL */
    public static final byte f53NL = 10;
    public static final byte NUL = 0;

    /* renamed from: RS */
    public static final byte f54RS = 30;

    /* renamed from: SI */
    public static final byte f55SI = 15;

    /* renamed from: SO */
    public static final byte f56SO = 14;
    public static final byte SOH = 1;

    /* renamed from: SP */
    public static final byte f57SP = 32;
    public static final byte SPACE = 32;
    public static final byte STX = 2;
    public static final byte SUB = 26;
    public static final byte SYN = 22;

    /* renamed from: US */
    public static final byte f58US = 31;

    /* renamed from: VT */
    public static final byte f59VT = 11;
    public static final byte XOFF = 19;
    public static final byte XON = 17;

    private Ascii() {
    }

    public static String toLowerCase(String string) {
        int length = string.length();
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(toLowerCase(string.charAt(i)));
        }
        return builder.toString();
    }

    public static char toLowerCase(char c) {
        return isUpperCase(c) ? (char) (c ^ ' ') : c;
    }

    public static String toUpperCase(String string) {
        int length = string.length();
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(toUpperCase(string.charAt(i)));
        }
        return builder.toString();
    }

    public static char toUpperCase(char c) {
        return isLowerCase(c) ? (char) (c & '_') : c;
    }

    public static boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }
}
