package org.apache.xmlrpc.parser;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.p010ws.commons.util.NamespaceContextImpl;
import org.apache.xmlrpc.common.TypeFactory;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.serializer.TypeSerializerImpl;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlRpcRequestParser extends RecursiveTypeParserImpl {
    private boolean inMethodName;
    private int level;
    private String methodName;
    private List params;

    public XmlRpcRequestParser(XmlRpcStreamConfig pConfig, TypeFactory pTypeFactory) {
        super(pConfig, new NamespaceContextImpl(), pTypeFactory);
    }

    /* access modifiers changed from: protected */
    public void addResult(Object pResult) {
        this.params.add(pResult);
    }

    public void startDocument() throws SAXException {
        super.startDocument();
        this.level = 0;
        this.inMethodName = false;
        this.methodName = null;
        this.params = null;
    }

    public void characters(char[] pChars, int pOffset, int pLength) throws SAXException {
        String str;
        if (this.inMethodName) {
            String s = new String(pChars, pOffset, pLength);
            if (this.methodName == null) {
                str = s;
            } else {
                str = this.methodName + s;
            }
            this.methodName = str;
            return;
        }
        super.characters(pChars, pOffset, pLength);
    }

    public void startElement(String pURI, String pLocalName, String pQName, Attributes pAttrs) throws SAXException {
        int i = this.level;
        this.level = i + 1;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        super.startElement(pURI, pLocalName, pQName, pAttrs);
                    } else if (!"".equals(pURI) || !TypeSerializerImpl.VALUE_TAG.equals(pLocalName)) {
                        throw new SAXParseException("Expected value element, got " + new QName(pURI, pLocalName), getDocumentLocator());
                    } else {
                        startValueTag();
                    }
                } else if (!"".equals(pURI) || !"param".equals(pLocalName)) {
                    throw new SAXParseException("Expected param element, got " + new QName(pURI, pLocalName), getDocumentLocator());
                }
            } else if (this.methodName == null) {
                if (!"".equals(pURI) || !"methodName".equals(pLocalName)) {
                    throw new SAXParseException("Expected methodName element, got " + new QName(pURI, pLocalName), getDocumentLocator());
                }
                this.inMethodName = true;
            } else if (this.params != null) {
                throw new SAXParseException("Expected /methodCall, got " + new QName(pURI, pLocalName), getDocumentLocator());
            } else if (!"".equals(pURI) || !"params".equals(pLocalName)) {
                throw new SAXParseException("Expected params element, got " + new QName(pURI, pLocalName), getDocumentLocator());
            } else {
                this.params = new ArrayList();
            }
        } else if (!"".equals(pURI) || !"methodCall".equals(pLocalName)) {
            throw new SAXParseException("Expected root element 'methodCall', got " + new QName(pURI, pLocalName), getDocumentLocator());
        }
    }

    public void endElement(String pURI, String pLocalName, String pQName) throws SAXException {
        int i = this.level - 1;
        this.level = i;
        if (i == 0) {
            return;
        }
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    super.endElement(pURI, pLocalName, pQName);
                } else if (!"".equals(pURI) || !TypeSerializerImpl.VALUE_TAG.equals(pLocalName)) {
                    throw new SAXParseException("Expected /value, got " + new QName(pURI, pLocalName), getDocumentLocator());
                } else {
                    endValueTag();
                }
            } else if (!"".equals(pURI) || !"param".equals(pLocalName)) {
                throw new SAXParseException("Expected /param, got " + new QName(pURI, pLocalName), getDocumentLocator());
            }
        } else if (this.inMethodName) {
            if (!"".equals(pURI) || !"methodName".equals(pLocalName)) {
                throw new SAXParseException("Expected /methodName, got " + new QName(pURI, pLocalName), getDocumentLocator());
            }
            if (this.methodName == null) {
                this.methodName = "";
            }
            this.inMethodName = false;
        } else if (!"".equals(pURI) || !"params".equals(pLocalName)) {
            throw new SAXParseException("Expected /params, got " + new QName(pURI, pLocalName), getDocumentLocator());
        }
    }

    public String getMethodName() {
        return this.methodName;
    }

    public List getParams() {
        return this.params;
    }
}
