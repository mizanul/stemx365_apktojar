package org.apache.xmlrpc.parser;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.p010ws.commons.util.NamespaceContextImpl;
import org.apache.xmlrpc.common.TypeFactory;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.serializer.ObjectArraySerializer;
import org.apache.xmlrpc.serializer.TypeSerializerImpl;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ObjectArrayParser extends RecursiveTypeParserImpl {
    private int level = 0;
    private List list;

    public ObjectArrayParser(XmlRpcStreamConfig pConfig, NamespaceContextImpl pContext, TypeFactory pFactory) {
        super(pConfig, pContext, pFactory);
    }

    public void startDocument() throws SAXException {
        this.level = 0;
        this.list = new ArrayList();
        super.startDocument();
    }

    /* access modifiers changed from: protected */
    public void addResult(Object pValue) {
        this.list.add(pValue);
    }

    public void endElement(String pURI, String pLocalName, String pQName) throws SAXException {
        int i = this.level - 1;
        this.level = i;
        if (i == 0) {
            setResult(this.list.toArray());
        } else if (i == 1) {
        } else {
            if (i != 2) {
                super.endElement(pURI, pLocalName, pQName);
            } else {
                endValueTag();
            }
        }
    }

    public void startElement(String pURI, String pLocalName, String pQName, Attributes pAttrs) throws SAXException {
        int i = this.level;
        this.level = i + 1;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    super.startElement(pURI, pLocalName, pQName, pAttrs);
                } else if (!"".equals(pURI) || !TypeSerializerImpl.VALUE_TAG.equals(pLocalName)) {
                    throw new SAXParseException("Expected data element, got " + new QName(pURI, pLocalName), getDocumentLocator());
                } else {
                    startValueTag();
                }
            } else if (!"".equals(pURI) || !ObjectArraySerializer.DATA_TAG.equals(pLocalName)) {
                throw new SAXParseException("Expected data element, got " + new QName(pURI, pLocalName), getDocumentLocator());
            }
        } else if (!"".equals(pURI) || !ObjectArraySerializer.ARRAY_TAG.equals(pLocalName)) {
            throw new SAXParseException("Expected array element, got " + new QName(pURI, pLocalName), getDocumentLocator());
        }
    }
}
