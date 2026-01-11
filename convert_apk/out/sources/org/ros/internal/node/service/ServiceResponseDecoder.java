package org.ros.internal.node.service;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

class ServiceResponseDecoder<ResponseType> extends ReplayingDecoder<ServiceResponseDecoderState> {
    private ServiceServerResponse response;

    public ServiceResponseDecoder() {
        reset();
    }

    /* renamed from: org.ros.internal.node.service.ServiceResponseDecoder$1 */
    static /* synthetic */ class C10331 {

        /* renamed from: $SwitchMap$org$ros$internal$node$service$ServiceResponseDecoderState */
        static final /* synthetic */ int[] f191x982ebacb;

        static {
            int[] iArr = new int[ServiceResponseDecoderState.values().length];
            f191x982ebacb = iArr;
            try {
                iArr[ServiceResponseDecoderState.ERROR_CODE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f191x982ebacb[ServiceResponseDecoderState.MESSAGE_LENGTH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f191x982ebacb[ServiceResponseDecoderState.MESSAGE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, ServiceResponseDecoderState state) throws Exception {
        int i = C10331.f191x982ebacb[state.ordinal()];
        if (i == 1) {
            this.response.setErrorCode(buffer.readByte());
            checkpoint(ServiceResponseDecoderState.MESSAGE_LENGTH);
        } else if (i != 2) {
            if (i != 3) {
                throw new IllegalStateException();
            }
            ServiceServerResponse serviceServerResponse = this.response;
            serviceServerResponse.setMessage(buffer.readBytes(serviceServerResponse.getMessageLength()));
            return this.response;
        }
        this.response.setMessageLength(buffer.readInt());
        checkpoint(ServiceResponseDecoderState.MESSAGE);
        ServiceServerResponse serviceServerResponse2 = this.response;
        serviceServerResponse2.setMessage(buffer.readBytes(serviceServerResponse2.getMessageLength()));
        try {
            return this.response;
        } finally {
            reset();
        }
    }

    private void reset() {
        checkpoint(ServiceResponseDecoderState.ERROR_CODE);
        this.response = new ServiceServerResponse();
    }
}
