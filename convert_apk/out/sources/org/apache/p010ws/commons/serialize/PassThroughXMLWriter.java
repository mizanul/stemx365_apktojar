package org.apache.p010ws.commons.serialize;

/* renamed from: org.apache.ws.commons.serialize.PassThroughXMLWriter */
public class PassThroughXMLWriter extends XMLWriterImpl {
    public boolean canEncode(char c) {
        return true;
    }
}
