package org.apache.p010ws.commons.serialize;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.xml.sax.SAXException;

/* renamed from: org.apache.ws.commons.serialize.CharSetXMLWriter */
public class CharSetXMLWriter extends XMLWriterImpl {
    private CharsetEncoder charsetEncoder;

    public void startDocument() throws SAXException {
        Charset charSet = Charset.forName(getEncoding());
        if (charSet.canEncode()) {
            this.charsetEncoder = charSet.newEncoder();
        }
    }

    public boolean canEncode(char c) {
        CharsetEncoder charsetEncoder2 = this.charsetEncoder;
        if (charsetEncoder2 == null) {
            return false;
        }
        return charsetEncoder2.canEncode(c);
    }
}
