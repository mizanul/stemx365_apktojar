package org.jboss.netty.channel.socket.nio;

import java.net.ProtocolFamily;
import java.net.StandardProtocolFamily;
import org.jboss.netty.channel.socket.InternetProtocolFamily;

final class ProtocolFamilyConverter {
    private ProtocolFamilyConverter() {
    }

    /* renamed from: org.jboss.netty.channel.socket.nio.ProtocolFamilyConverter$1 */
    static /* synthetic */ class C08661 {
        static final /* synthetic */ int[] $SwitchMap$org$jboss$netty$channel$socket$InternetProtocolFamily;

        static {
            int[] iArr = new int[InternetProtocolFamily.values().length];
            $SwitchMap$org$jboss$netty$channel$socket$InternetProtocolFamily = iArr;
            try {
                iArr[InternetProtocolFamily.IPv4.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$jboss$netty$channel$socket$InternetProtocolFamily[InternetProtocolFamily.IPv6.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static ProtocolFamily convert(InternetProtocolFamily family) {
        int i = C08661.$SwitchMap$org$jboss$netty$channel$socket$InternetProtocolFamily[family.ordinal()];
        if (i == 1) {
            return StandardProtocolFamily.INET;
        }
        if (i == 2) {
            return StandardProtocolFamily.INET6;
        }
        throw new IllegalArgumentException();
    }
}
