package org.apache.xmlrpc.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.XmlRpcRequestConfig;
import org.apache.xmlrpc.common.ServerStreamConnection;
import org.apache.xmlrpc.common.XmlRpcStreamRequestConfig;
import org.apache.xmlrpc.common.XmlRpcStreamRequestProcessor;
import org.apache.xmlrpc.parser.XmlRpcRequestParser;
import org.apache.xmlrpc.serializer.DefaultXMLWriterFactory;
import org.apache.xmlrpc.serializer.XmlRpcWriter;
import org.apache.xmlrpc.serializer.XmlWriterFactory;
import org.apache.xmlrpc.util.SAXParsers;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public abstract class XmlRpcStreamServer extends XmlRpcServer implements XmlRpcStreamRequestProcessor {
    private static final Log log = LogFactory.getLog(XmlRpcStreamServer.class);
    private static final XmlRpcErrorLogger theErrorLogger = new XmlRpcErrorLogger();
    private XmlRpcErrorLogger errorLogger = theErrorLogger;
    private XmlWriterFactory writerFactory = new DefaultXMLWriterFactory();

    /* access modifiers changed from: protected */
    public XmlRpcRequest getRequest(final XmlRpcStreamRequestConfig pConfig, InputStream pStream) throws XmlRpcException {
        final XmlRpcRequestParser parser = new XmlRpcRequestParser(pConfig, getTypeFactory());
        XMLReader xr = SAXParsers.newXMLReader();
        xr.setContentHandler(parser);
        try {
            xr.parse(new InputSource(pStream));
            final List params = parser.getParams();
            return new XmlRpcRequest() {
                public XmlRpcRequestConfig getConfig() {
                    return pConfig;
                }

                public String getMethodName() {
                    return parser.getMethodName();
                }

                public int getParameterCount() {
                    List list = params;
                    if (list == null) {
                        return 0;
                    }
                    return list.size();
                }

                public Object getParameter(int pIndex) {
                    return params.get(pIndex);
                }
            };
        } catch (SAXException e) {
            Exception ex = e.getException();
            if (ex == null || !(ex instanceof XmlRpcException)) {
                throw new XmlRpcException("Failed to parse XML-RPC request: " + e.getMessage(), (Throwable) e);
            }
            throw ((XmlRpcException) ex);
        } catch (IOException e2) {
            throw new XmlRpcException("Failed to read XML-RPC request: " + e2.getMessage(), (Throwable) e2);
        }
    }

    /* access modifiers changed from: protected */
    public XmlRpcWriter getXmlRpcWriter(XmlRpcStreamRequestConfig pConfig, OutputStream pStream) throws XmlRpcException {
        return new XmlRpcWriter(pConfig, getXMLWriterFactory().getXmlWriter(pConfig, pStream), getTypeFactory());
    }

    /* access modifiers changed from: protected */
    public void writeResponse(XmlRpcStreamRequestConfig pConfig, OutputStream pStream, Object pResult) throws XmlRpcException {
        try {
            getXmlRpcWriter(pConfig, pStream).write(pConfig, pResult);
        } catch (SAXException e) {
            throw new XmlRpcException("Failed to write XML-RPC response: " + e.getMessage(), (Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public Throwable convertThrowable(Throwable pError) {
        return pError;
    }

    /* access modifiers changed from: protected */
    public void writeError(XmlRpcStreamRequestConfig pConfig, OutputStream pStream, Throwable pError) throws XmlRpcException {
        int code;
        Throwable error = convertThrowable(pError);
        if (error instanceof XmlRpcException) {
            code = ((XmlRpcException) error).code;
        } else {
            code = 0;
        }
        try {
            getXmlRpcWriter(pConfig, pStream).write(pConfig, code, error.getMessage(), error);
        } catch (SAXException e) {
            throw new XmlRpcException("Failed to write XML-RPC response: " + e.getMessage(), (Throwable) e);
        }
    }

    public void setXMLWriterFactory(XmlWriterFactory pFactory) {
        this.writerFactory = pFactory;
    }

    public XmlWriterFactory getXMLWriterFactory() {
        return this.writerFactory;
    }

    /* access modifiers changed from: protected */
    public InputStream getInputStream(XmlRpcStreamRequestConfig pConfig, ServerStreamConnection pConnection) throws IOException {
        InputStream istream = pConnection.newInputStream();
        if (!pConfig.isEnabledForExtensions() || !pConfig.isGzipCompressing()) {
            return istream;
        }
        return new GZIPInputStream(istream);
    }

    /* access modifiers changed from: protected */
    public OutputStream getOutputStream(ServerStreamConnection pConnection, XmlRpcStreamRequestConfig pConfig, OutputStream pStream) throws IOException {
        if (!pConfig.isEnabledForExtensions() || !pConfig.isGzipRequesting()) {
            return pStream;
        }
        return new GZIPOutputStream(pStream);
    }

    /* access modifiers changed from: protected */
    public OutputStream getOutputStream(XmlRpcStreamRequestConfig pConfig, ServerStreamConnection pConnection, int pSize) throws IOException {
        return pConnection.newOutputStream();
    }

    /* access modifiers changed from: protected */
    public boolean isContentLengthRequired(XmlRpcStreamRequestConfig pConfig) {
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0078, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0079, code lost:
        if (r6 != null) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0094, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0095, code lost:
        if (r5 != null) goto L_0x0097;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x009d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x009f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00c4, code lost:
        throw new org.apache.xmlrpc.XmlRpcException("I/O error while processing request: " + r0.getMessage(), (java.lang.Throwable) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00c5, code lost:
        if (r11 != null) goto L_0x00c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        r11.close();
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:14:0x0034, B:22:0x004d, B:34:0x0069, B:43:0x007b, B:58:0x0097, B:67:0x00a4] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:46:0x0080=Splitter:B:46:0x0080, B:14:0x0034=Splitter:B:14:0x0034, B:70:0x00a9=Splitter:B:70:0x00a9, B:61:0x009c=Splitter:B:61:0x009c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void execute(org.apache.xmlrpc.common.XmlRpcStreamRequestConfig r10, org.apache.xmlrpc.common.ServerStreamConnection r11) throws org.apache.xmlrpc.XmlRpcException {
        /*
            r9 = this;
            org.apache.commons.logging.Log r0 = log
            java.lang.String r1 = "execute: ->"
            r0.debug(r1)
            r0 = 0
            java.io.InputStream r1 = r9.getInputStream(r10, r11)     // Catch:{ all -> 0x0029 }
            r0 = r1
            org.apache.xmlrpc.XmlRpcRequest r1 = r9.getRequest(r10, r0)     // Catch:{ all -> 0x0029 }
            java.lang.Object r2 = r9.execute(r1)     // Catch:{ all -> 0x0029 }
            r0.close()     // Catch:{ all -> 0x0029 }
            r0 = 0
            r3 = 0
            org.apache.commons.logging.Log r4 = log     // Catch:{ all -> 0x0029 }
            java.lang.String r5 = "execute: Request performed successfully"
            r4.debug(r5)     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x0034
            r0.close()     // Catch:{ all -> 0x0027 }
            goto L_0x0034
        L_0x0027:
            r1 = move-exception
            goto L_0x0034
        L_0x0029:
            r1 = move-exception
            r9.logError(r1)     // Catch:{ all -> 0x00a1 }
            r2 = 0
            r3 = r1
            if (r0 == 0) goto L_0x0034
            r0.close()     // Catch:{ all -> 0x0027 }
        L_0x0034:
            boolean r1 = r9.isContentLengthRequired(r10)     // Catch:{ IOException -> 0x009f }
            if (r1 == 0) goto L_0x0041
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x009f }
            r4.<init>()     // Catch:{ IOException -> 0x009f }
            r5 = r4
            goto L_0x0046
        L_0x0041:
            r4 = 0
            java.io.OutputStream r5 = r11.newOutputStream()     // Catch:{ IOException -> 0x009f }
        L_0x0046:
            java.io.OutputStream r6 = r9.getOutputStream((org.apache.xmlrpc.common.ServerStreamConnection) r11, (org.apache.xmlrpc.common.XmlRpcStreamRequestConfig) r10, (java.io.OutputStream) r5)     // Catch:{ IOException -> 0x009f }
            r5 = r6
            if (r3 != 0) goto L_0x0051
            r9.writeResponse(r10, r5, r2)     // Catch:{ all -> 0x0094 }
            goto L_0x0054
        L_0x0051:
            r9.writeError(r10, r5, r3)     // Catch:{ all -> 0x0094 }
        L_0x0054:
            r5.close()     // Catch:{ all -> 0x0094 }
            r5 = 0
            if (r5 == 0) goto L_0x005f
            r5.close()     // Catch:{ all -> 0x005e }
            goto L_0x005f
        L_0x005e:
            r6 = move-exception
        L_0x005f:
            if (r4 == 0) goto L_0x0081
            int r6 = r4.size()     // Catch:{ IOException -> 0x009f }
            java.io.OutputStream r6 = r9.getOutputStream((org.apache.xmlrpc.common.XmlRpcStreamRequestConfig) r10, (org.apache.xmlrpc.common.ServerStreamConnection) r11, (int) r6)     // Catch:{ IOException -> 0x009f }
            r4.writeTo(r6)     // Catch:{ all -> 0x0078 }
            r6.close()     // Catch:{ all -> 0x0078 }
            r6 = 0
            if (r6 == 0) goto L_0x0081
            r6.close()     // Catch:{ all -> 0x0076 }
            goto L_0x0081
        L_0x0076:
            r7 = move-exception
            goto L_0x0081
        L_0x0078:
            r7 = move-exception
            if (r6 == 0) goto L_0x0080
            r6.close()     // Catch:{ all -> 0x007f }
            goto L_0x0080
        L_0x007f:
            r8 = move-exception
        L_0x0080:
            throw r7     // Catch:{ IOException -> 0x009f }
        L_0x0081:
            r11.close()     // Catch:{ IOException -> 0x009f }
            r11 = 0
            if (r11 == 0) goto L_0x008c
            r11.close()     // Catch:{ all -> 0x008b }
            goto L_0x008c
        L_0x008b:
            r0 = move-exception
        L_0x008c:
            org.apache.commons.logging.Log r0 = log
            java.lang.String r1 = "execute: <-"
            r0.debug(r1)
            return
        L_0x0094:
            r6 = move-exception
            if (r5 == 0) goto L_0x009c
            r5.close()     // Catch:{ all -> 0x009b }
            goto L_0x009c
        L_0x009b:
            r7 = move-exception
        L_0x009c:
            throw r6     // Catch:{ IOException -> 0x009f }
        L_0x009d:
            r0 = move-exception
            goto L_0x00c5
        L_0x009f:
            r0 = move-exception
            goto L_0x00aa
        L_0x00a1:
            r1 = move-exception
            if (r0 == 0) goto L_0x00a9
            r0.close()     // Catch:{ all -> 0x00a8 }
            goto L_0x00a9
        L_0x00a8:
            r2 = move-exception
        L_0x00a9:
            throw r1     // Catch:{ IOException -> 0x009f }
        L_0x00aa:
            org.apache.xmlrpc.XmlRpcException r1 = new org.apache.xmlrpc.XmlRpcException     // Catch:{ all -> 0x009d }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x009d }
            r2.<init>()     // Catch:{ all -> 0x009d }
            java.lang.String r3 = "I/O error while processing request: "
            r2.append(r3)     // Catch:{ all -> 0x009d }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x009d }
            r2.append(r3)     // Catch:{ all -> 0x009d }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x009d }
            r1.<init>((java.lang.String) r2, (java.lang.Throwable) r0)     // Catch:{ all -> 0x009d }
            throw r1     // Catch:{ all -> 0x009d }
        L_0x00c5:
            if (r11 == 0) goto L_0x00cc
            r11.close()     // Catch:{ all -> 0x00cb }
            goto L_0x00cc
        L_0x00cb:
            r1 = move-exception
        L_0x00cc:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xmlrpc.server.XmlRpcStreamServer.execute(org.apache.xmlrpc.common.XmlRpcStreamRequestConfig, org.apache.xmlrpc.common.ServerStreamConnection):void");
    }

    /* access modifiers changed from: protected */
    public void logError(Throwable t) {
        this.errorLogger.log(t.getMessage() == null ? t.getClass().getName() : t.getMessage(), t);
    }

    public XmlRpcErrorLogger getErrorLogger() {
        return this.errorLogger;
    }

    public void setErrorLogger(XmlRpcErrorLogger pErrorLogger) {
        this.errorLogger = pErrorLogger;
    }
}
