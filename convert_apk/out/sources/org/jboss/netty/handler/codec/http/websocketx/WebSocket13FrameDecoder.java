package org.jboss.netty.handler.codec.http.websocketx;

import kotlin.jvm.internal.LongCompanionObject;

public class WebSocket13FrameDecoder extends WebSocket08FrameDecoder {
    public WebSocket13FrameDecoder(boolean maskedPayload, boolean allowExtensions) {
        this(maskedPayload, allowExtensions, LongCompanionObject.MAX_VALUE);
    }

    public WebSocket13FrameDecoder(boolean maskedPayload, boolean allowExtensions, long maxFramePayloadLength) {
        super(maskedPayload, allowExtensions, maxFramePayloadLength);
    }
}
