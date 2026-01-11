package org.jboss.netty.handler.codec.http;

import org.jboss.netty.buffer.ChannelBuffer;

public class HttpRequestEncoder extends HttpMessageEncoder {
    /* access modifiers changed from: protected */
    public void encodeInitialLine(ChannelBuffer buf, HttpMessage message) throws Exception {
        HttpRequest request = (HttpRequest) message;
        buf.writeBytes(request.getMethod().toString().getBytes("ASCII"));
        buf.writeByte(32);
        buf.writeBytes(request.getUri().getBytes("ASCII"));
        buf.writeByte(32);
        buf.writeBytes(request.getProtocolVersion().toString().getBytes("ASCII"));
        buf.writeByte(13);
        buf.writeByte(10);
    }
}
