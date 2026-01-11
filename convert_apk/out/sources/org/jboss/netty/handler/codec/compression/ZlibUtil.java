package org.jboss.netty.handler.codec.compression;

import org.jboss.netty.util.internal.jzlib.JZlib;
import org.jboss.netty.util.internal.jzlib.ZStream;

final class ZlibUtil {
    static void fail(ZStream z, String message, int resultCode) {
        throw exception(z, message, resultCode);
    }

    static CompressionException exception(ZStream z, String message, int resultCode) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append(" (");
        sb.append(resultCode);
        sb.append(")");
        if (z.msg != null) {
            str = ": " + z.msg;
        } else {
            str = "";
        }
        sb.append(str);
        return new CompressionException(sb.toString());
    }

    /* renamed from: org.jboss.netty.handler.codec.compression.ZlibUtil$1 */
    static /* synthetic */ class C08741 {
        static final /* synthetic */ int[] $SwitchMap$org$jboss$netty$handler$codec$compression$ZlibWrapper;

        static {
            int[] iArr = new int[ZlibWrapper.values().length];
            $SwitchMap$org$jboss$netty$handler$codec$compression$ZlibWrapper = iArr;
            try {
                iArr[ZlibWrapper.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$jboss$netty$handler$codec$compression$ZlibWrapper[ZlibWrapper.ZLIB.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$jboss$netty$handler$codec$compression$ZlibWrapper[ZlibWrapper.GZIP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$jboss$netty$handler$codec$compression$ZlibWrapper[ZlibWrapper.ZLIB_OR_NONE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static Enum<?> convertWrapperType(ZlibWrapper wrapper) {
        int i = C08741.$SwitchMap$org$jboss$netty$handler$codec$compression$ZlibWrapper[wrapper.ordinal()];
        if (i == 1) {
            return JZlib.W_NONE;
        }
        if (i == 2) {
            return JZlib.W_ZLIB;
        }
        if (i == 3) {
            return JZlib.W_GZIP;
        }
        if (i == 4) {
            return JZlib.W_ZLIB_OR_NONE;
        }
        throw new Error();
    }

    private ZlibUtil() {
    }
}
