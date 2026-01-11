package org.apache.xmlrpc.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.client.XmlRpcStreamTransport;
import org.apache.xmlrpc.common.XmlRpcStreamRequestConfig;
import org.xml.sax.SAXException;

public class XmlRpcLiteHttpTransport extends XmlRpcHttpTransport {
    private static final String userAgent = (USER_AGENT + " (Lite HTTP Transport)");
    private XmlRpcHttpClientConfig config;
    private final Map headers = new HashMap();
    private String host;
    private String hostname;
    private InputStream input;
    private OutputStream output;
    private int port;
    private boolean responseGzipCompressed = false;
    /* access modifiers changed from: private */
    public Socket socket;
    private boolean ssl;
    private String uri;

    public XmlRpcLiteHttpTransport(XmlRpcClient pClient) {
        super(pClient, userAgent);
    }

    public Object sendRequest(XmlRpcRequest pRequest) throws XmlRpcException {
        String str;
        XmlRpcHttpClientConfig xmlRpcHttpClientConfig = (XmlRpcHttpClientConfig) pRequest.getConfig();
        this.config = xmlRpcHttpClientConfig;
        URL url = xmlRpcHttpClientConfig.getServerURL();
        this.ssl = "https".equals(url.getProtocol());
        this.hostname = url.getHost();
        int p = url.getPort();
        this.port = p < 1 ? 80 : p;
        String u = url.getFile();
        this.uri = (u == null || "".equals(u)) ? "/" : u;
        if (this.port == 80) {
            str = this.hostname;
        } else {
            str = this.hostname + ":" + this.port;
        }
        this.host = str;
        this.headers.put("Host", str);
        return super.sendRequest(pRequest);
    }

    /* access modifiers changed from: protected */
    public void setRequestHeader(String pHeader, String pValue) {
        List list;
        Object value = this.headers.get(pHeader);
        if (value == null) {
            this.headers.put(pHeader, pValue);
            return;
        }
        if (value instanceof String) {
            list = new ArrayList();
            list.add(value);
            this.headers.put(pHeader, list);
        } else {
            list = (List) value;
        }
        list.add(pValue);
    }

    /* access modifiers changed from: protected */
    public void close() throws XmlRpcClientException {
        IOException e = null;
        InputStream inputStream = this.input;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ex) {
                e = ex;
            }
        }
        OutputStream outputStream = this.output;
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException ex2) {
                if (e != null) {
                    e = ex2;
                }
            }
        }
        Socket socket2 = this.socket;
        if (socket2 != null) {
            try {
                socket2.close();
            } catch (IOException ex3) {
                if (e != null) {
                    e = ex3;
                }
            }
        }
        if (e != null) {
            throw new XmlRpcClientException("Failed to close connection: " + e.getMessage(), e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:?, code lost:
        sendRequestHeaders(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0027, code lost:
        return r9.output;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.io.OutputStream getOutputStream() throws org.apache.xmlrpc.XmlRpcException {
        /*
            r9 = this;
            java.lang.String r0 = ": "
            java.lang.String r1 = ":"
            r2 = 3
            r3 = 100
            r4 = 0
        L_0x0008:
            boolean r5 = r9.ssl     // Catch:{ ConnectException -> 0x002a }
            java.lang.String r6 = r9.hostname     // Catch:{ ConnectException -> 0x002a }
            int r7 = r9.port     // Catch:{ ConnectException -> 0x002a }
            java.net.Socket r5 = r9.newSocket(r5, r6, r7)     // Catch:{ ConnectException -> 0x002a }
            r9.socket = r5     // Catch:{ ConnectException -> 0x002a }
            org.apache.xmlrpc.client.XmlRpcLiteHttpTransport$1 r5 = new org.apache.xmlrpc.client.XmlRpcLiteHttpTransport$1     // Catch:{ ConnectException -> 0x002a }
            java.net.Socket r6 = r9.socket     // Catch:{ ConnectException -> 0x002a }
            java.io.OutputStream r6 = r6.getOutputStream()     // Catch:{ ConnectException -> 0x002a }
            r5.<init>(r6)     // Catch:{ ConnectException -> 0x002a }
            r9.output = r5     // Catch:{ ConnectException -> 0x002a }
            r9.sendRequestHeaders(r5)     // Catch:{ IOException -> 0x0028 }
            java.io.OutputStream r0 = r9.output     // Catch:{ IOException -> 0x0028 }
            return r0
        L_0x0028:
            r2 = move-exception
            goto L_0x0063
        L_0x002a:
            r5 = move-exception
            r6 = 3
            if (r4 >= r6) goto L_0x0038
            r6 = 100
            java.lang.Thread.sleep(r6)     // Catch:{ InterruptedException -> 0x0034 }
            goto L_0x0035
        L_0x0034:
            r6 = move-exception
        L_0x0035:
            int r4 = r4 + 1
            goto L_0x0008
        L_0x0038:
            org.apache.xmlrpc.XmlRpcException r6 = new org.apache.xmlrpc.XmlRpcException     // Catch:{ IOException -> 0x0028 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0028 }
            r7.<init>()     // Catch:{ IOException -> 0x0028 }
            java.lang.String r8 = "Failed to connect to "
            r7.append(r8)     // Catch:{ IOException -> 0x0028 }
            java.lang.String r8 = r9.hostname     // Catch:{ IOException -> 0x0028 }
            r7.append(r8)     // Catch:{ IOException -> 0x0028 }
            r7.append(r1)     // Catch:{ IOException -> 0x0028 }
            int r8 = r9.port     // Catch:{ IOException -> 0x0028 }
            r7.append(r8)     // Catch:{ IOException -> 0x0028 }
            r7.append(r0)     // Catch:{ IOException -> 0x0028 }
            java.lang.String r8 = r5.getMessage()     // Catch:{ IOException -> 0x0028 }
            r7.append(r8)     // Catch:{ IOException -> 0x0028 }
            java.lang.String r7 = r7.toString()     // Catch:{ IOException -> 0x0028 }
            r6.<init>((java.lang.String) r7, (java.lang.Throwable) r5)     // Catch:{ IOException -> 0x0028 }
            throw r6     // Catch:{ IOException -> 0x0028 }
        L_0x0063:
            org.apache.xmlrpc.XmlRpcException r3 = new org.apache.xmlrpc.XmlRpcException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Failed to open connection to "
            r4.append(r5)
            java.lang.String r5 = r9.hostname
            r4.append(r5)
            r4.append(r1)
            int r1 = r9.port
            r4.append(r1)
            r4.append(r0)
            java.lang.String r0 = r2.getMessage()
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            r3.<init>((java.lang.String) r0, (java.lang.Throwable) r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xmlrpc.client.XmlRpcLiteHttpTransport.getOutputStream():java.io.OutputStream");
    }

    /* access modifiers changed from: protected */
    public Socket newSocket(boolean pSSL, String pHostName, int pPort) throws UnknownHostException, IOException {
        if (!pSSL) {
            return new Socket(pHostName, pPort);
        }
        throw new IOException("Unable to create SSL connections, use the XmlRpcLite14HttpTransportFactory.");
    }

    private byte[] toHTTPBytes(String pValue) throws UnsupportedEncodingException {
        return pValue.getBytes("US-ASCII");
    }

    private void sendHeader(OutputStream pOut, String pKey, String pValue) throws IOException {
        pOut.write(toHTTPBytes(pKey + ": " + pValue + "\r\n"));
    }

    private void sendRequestHeaders(OutputStream pOut) throws IOException {
        pOut.write(("POST " + this.uri + " HTTP/1.0\r\n").getBytes("US-ASCII"));
        for (Map.Entry entry : this.headers.entrySet()) {
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                sendHeader(pOut, key, (String) value);
            } else {
                List list = (List) value;
                for (int i = 0; i < list.size(); i++) {
                    sendHeader(pOut, key, (String) list.get(i));
                }
            }
        }
        pOut.write(toHTTPBytes("\r\n"));
    }

    /* access modifiers changed from: protected */
    public boolean isResponseGzipCompressed(XmlRpcStreamRequestConfig pConfig) {
        return this.responseGzipCompressed;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.io.InputStream getInputStream() throws org.apache.xmlrpc.XmlRpcException {
        /*
            r10 = this;
            java.lang.String r0 = "content-encoding:"
            java.lang.String r1 = "content-length:"
            r2 = 2048(0x800, float:2.87E-42)
            byte[] r2 = new byte[r2]
            org.apache.xmlrpc.client.XmlRpcHttpClientConfig r3 = r10.config     // Catch:{ IOException -> 0x00c5 }
            int r3 = r3.getReplyTimeout()     // Catch:{ IOException -> 0x00c5 }
            if (r3 == 0) goto L_0x001b
            java.net.Socket r3 = r10.socket     // Catch:{ IOException -> 0x00c5 }
            org.apache.xmlrpc.client.XmlRpcHttpClientConfig r4 = r10.config     // Catch:{ IOException -> 0x00c5 }
            int r4 = r4.getReplyTimeout()     // Catch:{ IOException -> 0x00c5 }
            r3.setSoTimeout(r4)     // Catch:{ IOException -> 0x00c5 }
        L_0x001b:
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x00c5 }
            java.net.Socket r4 = r10.socket     // Catch:{ IOException -> 0x00c5 }
            java.io.InputStream r4 = r4.getInputStream()     // Catch:{ IOException -> 0x00c5 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x00c5 }
            r10.input = r3     // Catch:{ IOException -> 0x00c5 }
            java.lang.String r3 = org.apache.xmlrpc.util.HttpUtil.readLine(r3, r2)     // Catch:{ IOException -> 0x00c5 }
            java.util.StringTokenizer r4 = new java.util.StringTokenizer     // Catch:{ IOException -> 0x00c5 }
            r4.<init>(r3)     // Catch:{ IOException -> 0x00c5 }
            r4.nextToken()     // Catch:{ IOException -> 0x00c5 }
            java.lang.String r5 = r4.nextToken()     // Catch:{ IOException -> 0x00c5 }
            java.lang.String r6 = "\n\r"
            java.lang.String r6 = r4.nextToken(r6)     // Catch:{ IOException -> 0x00c5 }
            int r7 = java.lang.Integer.parseInt(r5)     // Catch:{ NumberFormatException -> 0x00a4 }
            r8 = 200(0xc8, float:2.8E-43)
            if (r7 < r8) goto L_0x009e
            r8 = 299(0x12b, float:4.19E-43)
            if (r7 > r8) goto L_0x009e
            r8 = -1
        L_0x004c:
            java.io.InputStream r9 = r10.input     // Catch:{ IOException -> 0x00c5 }
            java.lang.String r9 = org.apache.xmlrpc.util.HttpUtil.readLine(r9, r2)     // Catch:{ IOException -> 0x00c5 }
            r3 = r9
            if (r3 == 0) goto L_0x0090
            java.lang.String r9 = ""
            boolean r9 = r9.equals(r3)     // Catch:{ IOException -> 0x00c5 }
            if (r9 == 0) goto L_0x005e
            goto L_0x0090
        L_0x005e:
            java.lang.String r9 = r3.toLowerCase()     // Catch:{ IOException -> 0x00c5 }
            r3 = r9
            boolean r9 = r3.startsWith(r1)     // Catch:{ IOException -> 0x00c5 }
            if (r9 == 0) goto L_0x007b
            int r9 = r1.length()     // Catch:{ IOException -> 0x00c5 }
            java.lang.String r9 = r3.substring(r9)     // Catch:{ IOException -> 0x00c5 }
            java.lang.String r9 = r9.trim()     // Catch:{ IOException -> 0x00c5 }
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ IOException -> 0x00c5 }
            r8 = r9
            goto L_0x004c
        L_0x007b:
            boolean r9 = r3.startsWith(r0)     // Catch:{ IOException -> 0x00c5 }
            if (r9 == 0) goto L_0x004c
            int r9 = r0.length()     // Catch:{ IOException -> 0x00c5 }
            java.lang.String r9 = r3.substring(r9)     // Catch:{ IOException -> 0x00c5 }
            boolean r9 = org.apache.xmlrpc.util.HttpUtil.isUsingGzipEncoding((java.lang.String) r9)     // Catch:{ IOException -> 0x00c5 }
            r10.responseGzipCompressed = r9     // Catch:{ IOException -> 0x00c5 }
            goto L_0x004c
        L_0x0090:
            r0 = -1
            if (r8 != r0) goto L_0x0096
            java.io.InputStream r0 = r10.input     // Catch:{ IOException -> 0x00c5 }
            goto L_0x009d
        L_0x0096:
            org.apache.xmlrpc.util.LimitedInputStream r0 = new org.apache.xmlrpc.util.LimitedInputStream     // Catch:{ IOException -> 0x00c5 }
            java.io.InputStream r1 = r10.input     // Catch:{ IOException -> 0x00c5 }
            r0.<init>(r1, r8)     // Catch:{ IOException -> 0x00c5 }
        L_0x009d:
            return r0
        L_0x009e:
            org.apache.xmlrpc.client.XmlRpcHttpTransportException r0 = new org.apache.xmlrpc.client.XmlRpcHttpTransportException     // Catch:{ IOException -> 0x00c5 }
            r0.<init>(r7, r6)     // Catch:{ IOException -> 0x00c5 }
            throw r0     // Catch:{ IOException -> 0x00c5 }
        L_0x00a4:
            r0 = move-exception
            org.apache.xmlrpc.client.XmlRpcClientException r1 = new org.apache.xmlrpc.client.XmlRpcClientException     // Catch:{ IOException -> 0x00c5 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c5 }
            r7.<init>()     // Catch:{ IOException -> 0x00c5 }
            java.lang.String r8 = "Server returned invalid status code: "
            r7.append(r8)     // Catch:{ IOException -> 0x00c5 }
            r7.append(r5)     // Catch:{ IOException -> 0x00c5 }
            java.lang.String r8 = " "
            r7.append(r8)     // Catch:{ IOException -> 0x00c5 }
            r7.append(r6)     // Catch:{ IOException -> 0x00c5 }
            java.lang.String r7 = r7.toString()     // Catch:{ IOException -> 0x00c5 }
            r8 = 0
            r1.<init>(r7, r8)     // Catch:{ IOException -> 0x00c5 }
            throw r1     // Catch:{ IOException -> 0x00c5 }
        L_0x00c5:
            r0 = move-exception
            org.apache.xmlrpc.client.XmlRpcClientException r1 = new org.apache.xmlrpc.client.XmlRpcClientException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Failed to read server response: "
            r3.append(r4)
            java.lang.String r4 = r0.getMessage()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.<init>(r3, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xmlrpc.client.XmlRpcLiteHttpTransport.getInputStream():java.io.InputStream");
    }

    /* access modifiers changed from: protected */
    public boolean isUsingByteArrayOutput(XmlRpcHttpClientConfig pConfig) {
        boolean result = super.isUsingByteArrayOutput(pConfig);
        if (result) {
            return result;
        }
        throw new IllegalStateException("The Content-Length header is required with HTTP/1.0, and HTTP/1.1 is unsupported by the Lite HTTP Transport.");
    }

    /* access modifiers changed from: protected */
    public void writeRequest(XmlRpcStreamTransport.ReqWriter pWriter) throws XmlRpcException, IOException, SAXException {
        pWriter.write(getOutputStream());
    }
}
