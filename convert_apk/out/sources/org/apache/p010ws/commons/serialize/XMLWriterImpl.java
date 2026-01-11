package org.apache.p010ws.commons.serialize;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/* renamed from: org.apache.ws.commons.serialize.XMLWriterImpl */
public class XMLWriterImpl implements XMLWriter {
    private static final int STATE_IN_ELEMENT = 2;
    private static final int STATE_IN_START_ELEMENT = 1;
    private static final int STATE_OUTSIDE = 0;
    int curIndent = 0;
    private boolean declarating;
    private Map delayedPrefixes;
    private String encoding;
    private boolean flushing;
    private String indentString;
    private boolean indenting;

    /* renamed from: l */
    private Locator f97l;
    private String lineFeed;
    private int state;

    /* renamed from: w */
    private Writer f98w;

    public void setEncoding(String pEncoding) {
        this.encoding = pEncoding;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setDeclarating(boolean pDeclarating) {
        this.declarating = pDeclarating;
    }

    public boolean isDeclarating() {
        return this.declarating;
    }

    public void setIndenting(boolean pIndenting) {
        this.indenting = pIndenting;
    }

    public boolean isIndenting() {
        return this.indenting;
    }

    public void setIndentString(String pIndentString) {
        this.indentString = pIndentString;
    }

    public String getIndentString() {
        return this.indentString;
    }

    public void setLineFeed(String pLineFeed) {
        this.lineFeed = pLineFeed;
    }

    public String getLineFeed() {
        return this.lineFeed;
    }

    public void setFlushing(boolean pFlushing) {
        this.flushing = pFlushing;
    }

    public boolean isFlushing() {
        return this.flushing;
    }

    public void setWriter(Writer pWriter) {
        this.f98w = pWriter;
    }

    public Writer getWriter() {
        return this.f98w;
    }

    public void setDocumentLocator(Locator pLocator) {
        this.f97l = pLocator;
    }

    public Locator getDocumentLocator() {
        return this.f97l;
    }

    public void startPrefixMapping(String prefix, String namespaceURI) throws SAXException {
        String prefix2;
        if (this.delayedPrefixes == null) {
            this.delayedPrefixes = new HashMap();
        }
        if (!"".equals(prefix)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("xmlns:");
            stringBuffer.append(prefix);
            prefix2 = stringBuffer.toString();
        } else if (!namespaceURI.equals(prefix)) {
            prefix2 = "xmlns";
        } else {
            return;
        }
        this.delayedPrefixes.put(prefix2, namespaceURI);
    }

    public void endPrefixMapping(String prefix) throws SAXException {
        String prefix2;
        if (this.delayedPrefixes != null) {
            if ("".equals(prefix)) {
                prefix2 = "xmlns";
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("xmlns:");
                stringBuffer.append(prefix);
                prefix2 = stringBuffer.toString();
            }
            this.delayedPrefixes.remove(prefix2);
        }
    }

    public void startDocument() throws SAXException {
        Writer writer;
        String lf;
        Map map = this.delayedPrefixes;
        if (map != null) {
            map.clear();
        }
        this.state = 0;
        this.curIndent = 0;
        if (isDeclarating() && (writer = this.f98w) != null) {
            try {
                writer.write("<?xml version=\"1.0\"");
                String enc = getEncoding();
                if (enc != null) {
                    this.f98w.write(" encoding=\"");
                    this.f98w.write(enc);
                    this.f98w.write("\"");
                }
                this.f98w.write("?>");
                if (isIndenting() && (lf = getLineFeed()) != null) {
                    this.f98w.write(lf);
                }
            } catch (IOException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Failed to write XML declaration: ");
                stringBuffer.append(e.getMessage());
                throw new SAXException(stringBuffer.toString(), e);
            }
        }
    }

    public void endDocument() throws SAXException {
        Writer writer;
        if (isFlushing() && (writer = this.f98w) != null) {
            try {
                writer.flush();
            } catch (IOException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Failed to flush target writer: ");
                stringBuffer.append(e.getMessage());
                throw new SAXException(stringBuffer.toString(), e);
            }
        }
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        characters(ch, start, length);
    }

    private void stopTerminator() throws IOException {
        if (this.state == 1) {
            Writer writer = this.f98w;
            if (writer != null) {
                writer.write(62);
            }
            this.state = 2;
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        try {
            stopTerminator();
            if (this.f98w != null) {
                int end = start + length;
                for (int i = start; i < end; i++) {
                    char c = ch[i];
                    if (c == 9 || c == 10 || c == 13) {
                        this.f98w.write(c);
                    } else if (c == '&') {
                        this.f98w.write("&amp;");
                    } else if (c == '<') {
                        this.f98w.write("&lt;");
                    } else if (c == '>') {
                        this.f98w.write("&gt;");
                    } else if (canEncode(c)) {
                        this.f98w.write(c);
                    } else {
                        this.f98w.write("&#");
                        this.f98w.write(Integer.toString(c));
                        this.f98w.write(";");
                    }
                }
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    public boolean canEncode(char c) {
        return c == 10 || (c >= ' ' && c < 127);
    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (isIndenting()) {
            this.curIndent--;
        }
        Writer writer = this.f98w;
        if (writer != null) {
            try {
                if (this.state == 1) {
                    writer.write("/>");
                    this.state = 0;
                } else {
                    if (this.state == 0) {
                        indentMe();
                    }
                    this.f98w.write("</");
                    this.f98w.write(qName);
                    this.f98w.write(62);
                }
                this.state = 0;
            } catch (IOException e) {
                throw new SAXException(e);
            }
        }
    }

    private void indentMe() throws IOException {
        if (this.f98w != null && isIndenting()) {
            String s = getLineFeed();
            if (s != null) {
                this.f98w.write(s);
            }
            String s2 = getIndentString();
            if (s2 != null) {
                for (int i = 0; i < this.curIndent; i++) {
                    this.f98w.write(s2);
                }
            }
        }
    }

    private void writeCData(String v) throws IOException {
        int len = v.length();
        for (int j = 0; j < len; j++) {
            char c = v.charAt(j);
            if (c == '\"') {
                this.f98w.write("&quot;");
            } else if (c == '<') {
                this.f98w.write("&lt;");
            } else if (c == '>') {
                this.f98w.write("&gt;");
            } else if (c == '&') {
                this.f98w.write("&amp;");
            } else if (c == '\'') {
                this.f98w.write("&apos;");
            } else if (canEncode(c)) {
                this.f98w.write(c);
            } else {
                this.f98w.write("&#");
                this.f98w.write(Integer.toString(c));
                this.f98w.write(59);
            }
        }
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes attr) throws SAXException {
        try {
            stopTerminator();
            if (isIndenting()) {
                if (this.curIndent > 0) {
                    indentMe();
                }
                this.curIndent++;
            }
            if (this.f98w != null) {
                this.f98w.write(60);
                this.f98w.write(qName);
                if (attr != null) {
                    int i = attr.getLength();
                    while (i > 0) {
                        this.f98w.write(32);
                        i--;
                        String name = attr.getQName(i);
                        this.f98w.write(name);
                        if (this.delayedPrefixes != null) {
                            this.delayedPrefixes.remove(name);
                        }
                        this.f98w.write("=\"");
                        writeCData(attr.getValue(i));
                        this.f98w.write(34);
                    }
                }
                if (this.delayedPrefixes != null && this.delayedPrefixes.size() > 0) {
                    for (Map.Entry entry : this.delayedPrefixes.entrySet()) {
                        this.f98w.write(32);
                        this.f98w.write((String) entry.getKey());
                        this.f98w.write("=\"");
                        this.f98w.write((String) entry.getValue());
                        this.f98w.write(34);
                    }
                    this.delayedPrefixes.clear();
                }
            }
            this.state = 1;
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    public void skippedEntity(String ent) throws SAXException {
        throw new SAXException("Don't know how to skip entities");
    }

    public void processingInstruction(String target, String data) throws SAXException {
        try {
            stopTerminator();
            if (this.f98w != null) {
                this.f98w.write("<?");
                this.f98w.write(target);
                this.f98w.write(32);
                this.f98w.write(data);
                this.f98w.write("?>");
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }
}
