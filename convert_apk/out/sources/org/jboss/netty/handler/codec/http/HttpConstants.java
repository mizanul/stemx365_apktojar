package org.jboss.netty.handler.codec.http;

import java.nio.charset.Charset;
import org.jboss.netty.util.CharsetUtil;

public final class HttpConstants {
    public static final byte COLON = 58;
    public static final byte COMMA = 44;

    /* renamed from: CR */
    public static final byte f128CR = 13;
    public static final Charset DEFAULT_CHARSET = CharsetUtil.UTF_8;
    public static final byte DOUBLE_QUOTE = 34;
    public static final byte EQUALS = 61;

    /* renamed from: HT */
    public static final byte f129HT = 9;

    /* renamed from: LF */
    public static final byte f130LF = 10;
    public static final byte SEMICOLON = 59;

    /* renamed from: SP */
    public static final byte f131SP = 32;

    private HttpConstants() {
    }
}
