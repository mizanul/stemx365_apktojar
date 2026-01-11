package org.apache.xmlrpc.serializer;

import org.apache.p010ws.commons.serialize.CharSetXMLWriter;
import org.apache.p010ws.commons.serialize.XMLWriter;

public class CharSetXmlWriterFactory extends BaseXmlWriterFactory {
    /* access modifiers changed from: protected */
    public XMLWriter newXmlWriter() {
        return new CharSetXMLWriter();
    }
}
