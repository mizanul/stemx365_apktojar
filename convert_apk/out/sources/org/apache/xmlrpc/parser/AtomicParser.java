package org.apache.xmlrpc.parser;

import javax.xml.namespace.QName;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public abstract class AtomicParser extends TypeParserImpl {
    private int level;

    /* renamed from: sb */
    protected StringBuffer f99sb;

    /* access modifiers changed from: protected */
    public abstract void setResult(String str) throws SAXException;

    protected AtomicParser() {
    }

    public void startDocument() throws SAXException {
        this.level = 0;
    }

    public void characters(char[] pChars, int pStart, int pLength) throws SAXException {
        StringBuffer stringBuffer = this.f99sb;
        if (stringBuffer != null) {
            stringBuffer.append(pChars, pStart, pLength);
        } else if (!isEmpty(pChars, pStart, pLength)) {
            throw new SAXParseException("Unexpected non-whitespace characters", getDocumentLocator());
        }
    }

    public void endElement(String pURI, String pLocalName, String pQName) throws SAXException {
        int i = this.level - 1;
        this.level = i;
        if (i == 0) {
            setResult(this.f99sb.toString());
            return;
        }
        throw new SAXParseException("Unexpected end tag in atomic element: " + new QName(pURI, pLocalName), getDocumentLocator());
    }

    public void startElement(String pURI, String pLocalName, String pQName, Attributes pAttrs) throws SAXException {
        int i = this.level;
        this.level = i + 1;
        if (i == 0) {
            this.f99sb = new StringBuffer();
            return;
        }
        throw new SAXParseException("Unexpected start tag in atomic element: " + new QName(pURI, pLocalName), getDocumentLocator());
    }
}
