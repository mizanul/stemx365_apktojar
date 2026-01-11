package org.apache.xmlrpc.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import org.apache.xmlrpc.common.ServerStreamConnection;
import org.apache.xmlrpc.common.XmlRpcNotAuthorizedException;
import org.apache.xmlrpc.server.XmlRpcStreamServer;
import org.apache.xmlrpc.util.LimitedInputStream;
import org.apache.xmlrpc.util.ThreadPool;

public class Connection implements ThreadPool.InterruptableTask, ServerStreamConnection {
    private static final String US_ASCII = "US-ASCII";
    private static final byte[] clength = toHTTPBytes("Content-Length: ");
    private static final byte[] conclose = toHTTPBytes("Connection: close\r\n");
    private static final byte[] conkeep = toHTTPBytes("Connection: Keep-Alive\r\n");
    private static final byte[] ctype = toHTTPBytes("Content-Type: text/xml\r\n");
    private static final byte[] doubleNewline = toHTTPBytes("\r\n\r\n");
    private static final byte[] newline = toHTTPBytes("\r\n");

    /* renamed from: ok */
    private static final byte[] f102ok = toHTTPBytes(" 200 OK\r\n");
    private static final byte[] serverName = toHTTPBytes("Server: Apache XML-RPC 1.0\r\n");
    private static final byte[] wwwAuthenticate = toHTTPBytes("WWW-Authenticate: Basic realm=XML-RPC\r\n");
    private byte[] buffer;
    private boolean firstByte;
    private Map headers;
    private final InputStream input = new BufferedInputStream(this.socket.getInputStream()) {
        public void close() throws IOException {
        }
    };
    private final OutputStream output = new BufferedOutputStream(this.socket.getOutputStream());
    private RequestData requestData;
    private final XmlRpcStreamServer server;
    private boolean shuttingDown;
    private final Socket socket;
    private final WebServer webServer;

    private static abstract class RequestException extends IOException {
        private static final long serialVersionUID = 2113732921468653309L;
        /* access modifiers changed from: private */
        public final RequestData requestData;

        RequestException(RequestData pData, String pMessage) {
            super(pMessage);
            this.requestData = pData;
        }

        /* access modifiers changed from: package-private */
        public RequestData getRequestData() {
            return this.requestData;
        }
    }

    private static class BadEncodingException extends RequestException {
        private static final long serialVersionUID = -2674424938251521248L;

        BadEncodingException(RequestData pData, String pTransferEncoding) {
            super(pData, pTransferEncoding);
        }
    }

    private static class BadRequestException extends RequestException {
        private static final long serialVersionUID = 3257848779234554934L;

        BadRequestException(RequestData pData, String pTransferEncoding) {
            super(pData, pTransferEncoding);
        }
    }

    private static final byte[] toHTTPBytes(String text) {
        try {
            return text.getBytes(US_ASCII);
        } catch (UnsupportedEncodingException e) {
            throw new Error(e.getMessage() + ": HTTP requires US-ASCII encoding");
        }
    }

    public Connection(WebServer pWebServer, XmlRpcStreamServer pServer, Socket pSocket) throws IOException {
        this.webServer = pWebServer;
        this.server = pServer;
        this.socket = pSocket;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x009d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.apache.xmlrpc.webserver.RequestData getRequestConfig() throws java.io.IOException {
        /*
            r11 = this;
            org.apache.xmlrpc.webserver.RequestData r0 = new org.apache.xmlrpc.webserver.RequestData
            r0.<init>(r11)
            r11.requestData = r0
            java.util.Map r0 = r11.headers
            if (r0 == 0) goto L_0x000e
            r0.clear()
        L_0x000e:
            r0 = 1
            r11.firstByte = r0
            org.apache.xmlrpc.server.XmlRpcStreamServer r1 = r11.server
            org.apache.xmlrpc.XmlRpcConfig r1 = r1.getConfig()
            org.apache.xmlrpc.server.XmlRpcHttpServerConfig r1 = (org.apache.xmlrpc.server.XmlRpcHttpServerConfig) r1
            org.apache.xmlrpc.webserver.RequestData r2 = r11.requestData
            java.lang.String r3 = r1.getBasicEncoding()
            r2.setBasicEncoding(r3)
            org.apache.xmlrpc.webserver.RequestData r2 = r11.requestData
            boolean r3 = r1.isContentLengthOptional()
            r2.setContentLengthOptional(r3)
            org.apache.xmlrpc.webserver.RequestData r2 = r11.requestData
            boolean r3 = r1.isEnabledForExtensions()
            r2.setEnabledForExtensions(r3)
            org.apache.xmlrpc.webserver.RequestData r2 = r11.requestData
            boolean r3 = r1.isEnabledForExceptions()
            r2.setEnabledForExceptions(r3)
            java.lang.String r2 = r11.readLine()
            r3 = 0
            if (r2 != 0) goto L_0x0049
            boolean r4 = r11.firstByte
            if (r4 == 0) goto L_0x0049
            return r3
        L_0x0049:
            if (r2 == 0) goto L_0x005e
            int r4 = r2.length()
            if (r4 != 0) goto L_0x005e
            java.lang.String r2 = r11.readLine()
            if (r2 == 0) goto L_0x005d
            int r4 = r2.length()
            if (r4 != 0) goto L_0x005e
        L_0x005d:
            return r3
        L_0x005e:
            java.util.StringTokenizer r3 = new java.util.StringTokenizer
            r3.<init>(r2)
            java.lang.String r4 = r3.nextToken()
            java.lang.String r5 = "POST"
            boolean r5 = r5.equalsIgnoreCase(r4)
            if (r5 == 0) goto L_0x0120
            org.apache.xmlrpc.webserver.RequestData r5 = r11.requestData
            r5.setMethod(r4)
            r3.nextToken()
            java.lang.String r5 = r3.nextToken()
            org.apache.xmlrpc.webserver.RequestData r6 = r11.requestData
            r6.setHttpVersion(r5)
            org.apache.xmlrpc.webserver.RequestData r6 = r11.requestData
            boolean r7 = r1.isKeepAliveEnabled()
            r8 = 0
            if (r7 == 0) goto L_0x0093
            java.lang.String r7 = "HTTP/1.1"
            boolean r7 = r7.equals(r5)
            if (r7 == 0) goto L_0x0093
            r7 = r0
            goto L_0x0094
        L_0x0093:
            r7 = r8
        L_0x0094:
            r6.setKeepAlive(r7)
        L_0x0097:
            java.lang.String r2 = r11.readLine()
            if (r2 == 0) goto L_0x0115
            java.lang.String r6 = r2.toLowerCase()
            java.lang.String r7 = "content-length:"
            boolean r9 = r6.startsWith(r7)
            if (r9 == 0) goto L_0x00bf
            int r7 = r7.length()
            java.lang.String r7 = r2.substring(r7)
            org.apache.xmlrpc.webserver.RequestData r9 = r11.requestData
            java.lang.String r10 = r7.trim()
            int r10 = java.lang.Integer.parseInt(r10)
            r9.setContentLength(r10)
            goto L_0x0115
        L_0x00bf:
            java.lang.String r7 = "connection:"
            boolean r7 = r6.startsWith(r7)
            if (r7 == 0) goto L_0x00df
            org.apache.xmlrpc.webserver.RequestData r7 = r11.requestData
            boolean r9 = r1.isKeepAliveEnabled()
            if (r9 == 0) goto L_0x00da
            java.lang.String r9 = "keep-alive"
            int r9 = r6.indexOf(r9)
            r10 = -1
            if (r9 <= r10) goto L_0x00da
            r9 = r0
            goto L_0x00db
        L_0x00da:
            r9 = r8
        L_0x00db:
            r7.setKeepAlive(r9)
            goto L_0x0115
        L_0x00df:
            java.lang.String r7 = "authorization:"
            boolean r9 = r6.startsWith(r7)
            if (r9 == 0) goto L_0x00f5
            int r7 = r7.length()
            java.lang.String r7 = r2.substring(r7)
            org.apache.xmlrpc.webserver.RequestData r9 = r11.requestData
            org.apache.xmlrpc.util.HttpUtil.parseAuthorization(r9, r7)
            goto L_0x0115
        L_0x00f5:
            java.lang.String r7 = "transfer-encoding:"
            boolean r9 = r6.startsWith(r7)
            if (r9 == 0) goto L_0x0115
            int r7 = r7.length()
            java.lang.String r7 = r2.substring(r7)
            java.lang.String r9 = org.apache.xmlrpc.util.HttpUtil.getNonIdentityTransferEncoding(r7)
            if (r9 != 0) goto L_0x010d
            goto L_0x0115
        L_0x010d:
            org.apache.xmlrpc.webserver.Connection$BadEncodingException r0 = new org.apache.xmlrpc.webserver.Connection$BadEncodingException
            org.apache.xmlrpc.webserver.RequestData r8 = r11.requestData
            r0.<init>(r8, r9)
            throw r0
        L_0x0115:
            if (r2 == 0) goto L_0x011d
            int r6 = r2.length()
            if (r6 != 0) goto L_0x0097
        L_0x011d:
            org.apache.xmlrpc.webserver.RequestData r0 = r11.requestData
            return r0
        L_0x0120:
            org.apache.xmlrpc.webserver.Connection$BadRequestException r0 = new org.apache.xmlrpc.webserver.Connection$BadRequestException
            org.apache.xmlrpc.webserver.RequestData r5 = r11.requestData
            r0.<init>(r5, r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xmlrpc.webserver.Connection.getRequestConfig():org.apache.xmlrpc.webserver.RequestData");
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:16:0x0030=Splitter:B:16:0x0030, B:30:0x0050=Splitter:B:30:0x0050, B:44:0x0099=Splitter:B:44:0x0099} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r4 = this;
            r0 = 0
        L_0x0001:
            org.apache.xmlrpc.webserver.RequestData r1 = r4.getRequestConfig()     // Catch:{ RequestException -> 0x0056, all -> 0x0038 }
            if (r1 != 0) goto L_0x0008
            goto L_0x0022
        L_0x0008:
            org.apache.xmlrpc.server.XmlRpcStreamServer r2 = r4.server     // Catch:{ RequestException -> 0x0056, all -> 0x0038 }
            r2.execute(r1, r4)     // Catch:{ RequestException -> 0x0056, all -> 0x0038 }
            java.io.OutputStream r2 = r4.output     // Catch:{ RequestException -> 0x0056, all -> 0x0038 }
            r2.flush()     // Catch:{ RequestException -> 0x0056, all -> 0x0038 }
            boolean r2 = r1.isKeepAlive()     // Catch:{ RequestException -> 0x0056, all -> 0x0038 }
            if (r2 == 0) goto L_0x0022
            boolean r2 = r1.isSuccess()     // Catch:{ RequestException -> 0x0056, all -> 0x0038 }
            if (r2 != 0) goto L_0x001f
            goto L_0x0022
        L_0x001f:
            int r0 = r0 + 1
            goto L_0x0001
        L_0x0022:
            java.io.OutputStream r0 = r4.output     // Catch:{ all -> 0x0028 }
            r0.close()     // Catch:{ all -> 0x0028 }
            goto L_0x0029
        L_0x0028:
            r0 = move-exception
        L_0x0029:
            java.io.InputStream r0 = r4.input     // Catch:{ all -> 0x002f }
            r0.close()     // Catch:{ all -> 0x002f }
            goto L_0x0030
        L_0x002f:
            r0 = move-exception
        L_0x0030:
            java.net.Socket r0 = r4.socket     // Catch:{ all -> 0x0036 }
            r0.close()     // Catch:{ all -> 0x0036 }
            goto L_0x009e
        L_0x0036:
            r0 = move-exception
            goto L_0x009e
        L_0x0038:
            r0 = move-exception
            boolean r1 = r4.shuttingDown     // Catch:{ all -> 0x009f }
            if (r1 != 0) goto L_0x0042
            org.apache.xmlrpc.webserver.WebServer r1 = r4.webServer     // Catch:{ all -> 0x009f }
            r1.log((java.lang.Throwable) r0)     // Catch:{ all -> 0x009f }
        L_0x0042:
            java.io.OutputStream r0 = r4.output     // Catch:{ all -> 0x0048 }
            r0.close()     // Catch:{ all -> 0x0048 }
            goto L_0x0049
        L_0x0048:
            r0 = move-exception
        L_0x0049:
            java.io.InputStream r0 = r4.input     // Catch:{ all -> 0x004f }
            r0.close()     // Catch:{ all -> 0x004f }
            goto L_0x0050
        L_0x004f:
            r0 = move-exception
        L_0x0050:
            java.net.Socket r0 = r4.socket     // Catch:{ all -> 0x0036 }
            r0.close()     // Catch:{ all -> 0x0036 }
            goto L_0x009e
        L_0x0056:
            r0 = move-exception
            org.apache.xmlrpc.webserver.WebServer r1 = r4.webServer     // Catch:{ all -> 0x009f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x009f }
            r2.<init>()     // Catch:{ all -> 0x009f }
            java.lang.Class r3 = r0.getClass()     // Catch:{ all -> 0x009f }
            java.lang.String r3 = r3.getName()     // Catch:{ all -> 0x009f }
            r2.append(r3)     // Catch:{ all -> 0x009f }
            java.lang.String r3 = ": "
            r2.append(r3)     // Catch:{ all -> 0x009f }
            java.lang.String r3 = r0.getMessage()     // Catch:{ all -> 0x009f }
            r2.append(r3)     // Catch:{ all -> 0x009f }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x009f }
            r1.log((java.lang.String) r2)     // Catch:{ all -> 0x009f }
            org.apache.xmlrpc.webserver.RequestData r1 = r0.requestData     // Catch:{ IOException -> 0x008a }
            r2 = -1
            r4.writeErrorHeader(r1, r0, r2)     // Catch:{ IOException -> 0x008a }
            java.io.OutputStream r1 = r4.output     // Catch:{ IOException -> 0x008a }
            r1.flush()     // Catch:{ IOException -> 0x008a }
            goto L_0x008b
        L_0x008a:
            r1 = move-exception
        L_0x008b:
            java.io.OutputStream r0 = r4.output     // Catch:{ all -> 0x0091 }
            r0.close()     // Catch:{ all -> 0x0091 }
            goto L_0x0092
        L_0x0091:
            r0 = move-exception
        L_0x0092:
            java.io.InputStream r0 = r4.input     // Catch:{ all -> 0x0098 }
            r0.close()     // Catch:{ all -> 0x0098 }
            goto L_0x0099
        L_0x0098:
            r0 = move-exception
        L_0x0099:
            java.net.Socket r0 = r4.socket     // Catch:{ all -> 0x0036 }
            r0.close()     // Catch:{ all -> 0x0036 }
        L_0x009e:
            return
        L_0x009f:
            r0 = move-exception
            java.io.OutputStream r1 = r4.output     // Catch:{ all -> 0x00a6 }
            r1.close()     // Catch:{ all -> 0x00a6 }
            goto L_0x00a7
        L_0x00a6:
            r1 = move-exception
        L_0x00a7:
            java.io.InputStream r1 = r4.input     // Catch:{ all -> 0x00ad }
            r1.close()     // Catch:{ all -> 0x00ad }
            goto L_0x00ae
        L_0x00ad:
            r1 = move-exception
        L_0x00ae:
            java.net.Socket r1 = r4.socket     // Catch:{ all -> 0x00b4 }
            r1.close()     // Catch:{ all -> 0x00b4 }
            goto L_0x00b5
        L_0x00b4:
            r1 = move-exception
        L_0x00b5:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xmlrpc.webserver.Connection.run():void");
    }

    private String readLine() throws IOException {
        if (this.buffer == null) {
            this.buffer = new byte[2048];
        }
        int count = 0;
        do {
            try {
                int next = this.input.read();
                this.firstByte = false;
                if (next < 0 || next == 10) {
                    return new String(this.buffer, 0, count, US_ASCII);
                }
                if (next != 13) {
                    this.buffer[count] = (byte) next;
                    count++;
                }
            } catch (SocketException e) {
                if (this.firstByte) {
                    return null;
                }
                throw e;
            }
        } while (count < this.buffer.length);
        throw new IOException("HTTP Header too long");
    }

    public void writeResponse(RequestData pData, OutputStream pBuffer) throws IOException {
        ByteArrayOutputStream response = (ByteArrayOutputStream) pBuffer;
        writeResponseHeader(pData, response.size());
        response.writeTo(this.output);
    }

    public void writeResponseHeader(RequestData pData, int pContentLength) throws IOException {
        this.output.write(toHTTPBytes(pData.getHttpVersion()));
        this.output.write(f102ok);
        this.output.write(serverName);
        this.output.write(pData.isKeepAlive() ? conkeep : conclose);
        this.output.write(ctype);
        Map map = this.headers;
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                OutputStream outputStream = this.output;
                outputStream.write(toHTTPBytes(((String) entry.getKey()) + ": " + ((String) entry.getValue()) + "\r\n"));
            }
        }
        if (pContentLength != -1) {
            this.output.write(clength);
            this.output.write(toHTTPBytes(Integer.toString(pContentLength)));
            this.output.write(doubleNewline);
        } else {
            this.output.write(newline);
        }
        pData.setSuccess(true);
    }

    public void writeError(RequestData pData, Throwable pError, ByteArrayOutputStream pStream) throws IOException {
        writeErrorHeader(pData, pError, pStream.size());
        pStream.writeTo(this.output);
        this.output.flush();
    }

    public void writeErrorHeader(RequestData pData, Throwable pError, int pContentLength) throws IOException {
        if (pError instanceof BadRequestException) {
            byte[] content = toHTTPBytes("Method " + pData.getMethod() + " not implemented (try POST)\r\n");
            this.output.write(toHTTPBytes(pData.getHttpVersion()));
            this.output.write(toHTTPBytes(" 400 Bad Request"));
            this.output.write(newline);
            this.output.write(serverName);
            writeContentLengthHeader(content.length);
            this.output.write(newline);
            this.output.write(content);
        } else if (pError instanceof BadEncodingException) {
            byte[] content2 = toHTTPBytes("The Transfer-Encoding " + pError.getMessage() + " is not implemented.\r\n");
            this.output.write(toHTTPBytes(pData.getHttpVersion()));
            this.output.write(toHTTPBytes(" 501 Not Implemented"));
            this.output.write(newline);
            this.output.write(serverName);
            writeContentLengthHeader(content2.length);
            this.output.write(newline);
            this.output.write(content2);
        } else if (pError instanceof XmlRpcNotAuthorizedException) {
            byte[] content3 = toHTTPBytes("Method " + pData.getMethod() + " requires a valid user name and password.\r\n");
            this.output.write(toHTTPBytes(pData.getHttpVersion()));
            this.output.write(toHTTPBytes(" 401 Unauthorized"));
            this.output.write(newline);
            this.output.write(serverName);
            writeContentLengthHeader(content3.length);
            this.output.write(wwwAuthenticate);
            this.output.write(newline);
            this.output.write(content3);
        } else {
            this.output.write(toHTTPBytes(pData.getHttpVersion()));
            this.output.write(f102ok);
            this.output.write(serverName);
            this.output.write(conclose);
            this.output.write(ctype);
            writeContentLengthHeader(pContentLength);
            this.output.write(newline);
        }
    }

    private void writeContentLengthHeader(int pContentLength) throws IOException {
        if (pContentLength != -1) {
            this.output.write(clength);
            this.output.write(toHTTPBytes(Integer.toString(pContentLength)));
            this.output.write(newline);
        }
    }

    public void setResponseHeader(String pHeader, String pValue) {
        this.headers.put(pHeader, pValue);
    }

    public OutputStream newOutputStream() throws IOException {
        if (!this.requestData.isEnabledForExtensions() || !this.requestData.isContentLengthOptional()) {
            return new ByteArrayOutputStream();
        }
        return this.output;
    }

    public InputStream newInputStream() throws IOException {
        int contentLength = this.requestData.getContentLength();
        if (contentLength == -1) {
            return this.input;
        }
        return new LimitedInputStream(this.input, contentLength);
    }

    public void close() throws IOException {
    }

    public void shutdown() throws Throwable {
        this.shuttingDown = true;
        this.socket.close();
    }
}
