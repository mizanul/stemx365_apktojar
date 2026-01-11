package org.apache.xmlrpc.parser;

import ff_msgs.CommandConstants;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.p010ws.commons.util.NamespaceContextImpl;
import org.apache.xmlrpc.common.TypeFactory;
import org.apache.xmlrpc.common.XmlRpcStreamRequestConfig;
import org.apache.xmlrpc.serializer.TypeSerializerImpl;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlRpcResponseParser extends RecursiveTypeParserImpl {
    private Throwable errorCause;
    private int errorCode;
    private String errorMessage;
    private boolean isSuccess;
    private int level;

    public XmlRpcResponseParser(XmlRpcStreamRequestConfig pConfig, TypeFactory pTypeFactory) {
        super(pConfig, new NamespaceContextImpl(), pTypeFactory);
    }

    /* access modifiers changed from: protected */
    public void addResult(Object pResult) throws SAXException {
        if (this.isSuccess) {
            super.setResult(pResult);
            return;
        }
        Map map = (Map) pResult;
        Integer faultCode = (Integer) map.get("faultCode");
        if (faultCode != null) {
            try {
                this.errorCode = faultCode.intValue();
                this.errorMessage = (String) map.get("faultString");
                Object exception = map.get("faultCause");
                if (exception != null) {
                    try {
                        ByteArrayInputStream bais = new ByteArrayInputStream((byte[]) exception);
                        ObjectInputStream ois = new ObjectInputStream(bais);
                        this.errorCause = (Throwable) ois.readObject();
                        ois.close();
                        bais.close();
                    } catch (Throwable th) {
                    }
                }
            } catch (NumberFormatException e) {
                throw new SAXParseException("Invalid faultCode: " + faultCode, getDocumentLocator());
            }
        } else {
            throw new SAXParseException("Missing faultCode", getDocumentLocator());
        }
    }

    public void startDocument() throws SAXException {
        super.startDocument();
        this.level = 0;
        this.isSuccess = false;
        this.errorCode = 0;
        this.errorMessage = null;
    }

    public void startElement(String pURI, String pLocalName, String pQName, Attributes pAttrs) throws SAXException {
        int i = this.level;
        this.level = i + 1;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        super.startElement(pURI, pLocalName, pQName, pAttrs);
                    } else if (!this.isSuccess) {
                        super.startElement(pURI, pLocalName, pQName, pAttrs);
                    } else if (!"".equals(pURI) || !TypeSerializerImpl.VALUE_TAG.equals(pLocalName)) {
                        throw new SAXParseException("Expected value element, got " + new QName(pURI, pLocalName), getDocumentLocator());
                    } else {
                        startValueTag();
                    }
                } else if (this.isSuccess) {
                    if (!"".equals(pURI) || !"param".equals(pLocalName)) {
                        throw new SAXParseException("Expected param element, got " + new QName(pURI, pLocalName), getDocumentLocator());
                    }
                } else if (!"".equals(pURI) || !TypeSerializerImpl.VALUE_TAG.equals(pLocalName)) {
                    throw new SAXParseException("Expected value element, got " + new QName(pURI, pLocalName), getDocumentLocator());
                } else {
                    startValueTag();
                }
            } else if ("".equals(pURI) && "params".equals(pLocalName)) {
                this.isSuccess = true;
            } else if (!"".equals(pURI) || !CommandConstants.CMD_NAME_FAULT.equals(pLocalName)) {
                throw new SAXParseException("Expected params or fault element, got " + new QName(pURI, pLocalName), getDocumentLocator());
            } else {
                this.isSuccess = false;
            }
        } else if (!"".equals(pURI) || !"methodResponse".equals(pLocalName)) {
            throw new SAXParseException("Expected methodResponse element, got " + new QName(pURI, pLocalName), getDocumentLocator());
        }
    }

    public void endElement(String pURI, String pLocalName, String pQName) throws SAXException {
        String tag;
        int i = this.level - 1;
        this.level = i;
        if (i != 0) {
            if (i == 1) {
                if (this.isSuccess) {
                    tag = "params";
                } else {
                    tag = CommandConstants.CMD_NAME_FAULT;
                }
                if (!"".equals(pURI) || !tag.equals(pLocalName)) {
                    throw new SAXParseException("Expected /" + tag + " element, got " + new QName(pURI, pLocalName), getDocumentLocator());
                }
            } else if (i != 2) {
                if (i != 3) {
                    super.endElement(pURI, pLocalName, pQName);
                } else if (!this.isSuccess) {
                    super.endElement(pURI, pLocalName, pQName);
                } else if (!"".equals(pURI) || !TypeSerializerImpl.VALUE_TAG.equals(pLocalName)) {
                    throw new SAXParseException("Expected /value, got " + new QName(pURI, pLocalName), getDocumentLocator());
                } else {
                    endValueTag();
                }
            } else if (this.isSuccess) {
                if (!"".equals(pURI) || !"param".equals(pLocalName)) {
                    throw new SAXParseException("Expected /param, got " + new QName(pURI, pLocalName), getDocumentLocator());
                }
            } else if (!"".equals(pURI) || !TypeSerializerImpl.VALUE_TAG.equals(pLocalName)) {
                throw new SAXParseException("Expected /value, got " + new QName(pURI, pLocalName), getDocumentLocator());
            } else {
                endValueTag();
            }
        } else if (!"".equals(pURI) || !"methodResponse".equals(pLocalName)) {
            throw new SAXParseException("Expected /methodResponse element, got " + new QName(pURI, pLocalName), getDocumentLocator());
        }
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public Throwable getErrorCause() {
        return this.errorCause;
    }
}
