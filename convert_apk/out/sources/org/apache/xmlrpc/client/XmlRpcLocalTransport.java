package org.apache.xmlrpc.client;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.XmlRpcRequestConfig;
import org.apache.xmlrpc.common.XmlRpcExtensionException;
import org.apache.xmlrpc.common.XmlRpcRequestProcessor;

public class XmlRpcLocalTransport extends XmlRpcTransportImpl {
    public XmlRpcLocalTransport(XmlRpcClient pClient) {
        super(pClient);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isExtensionType(java.lang.Object r7) {
        /*
            r6 = this;
            r0 = 1
            if (r7 != 0) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r7 instanceof java.lang.Object[]
            r2 = 0
            if (r1 == 0) goto L_0x001f
            r1 = r7
            java.lang.Object[] r1 = (java.lang.Object[]) r1
            java.lang.Object[] r1 = (java.lang.Object[]) r1
            r3 = 0
        L_0x000f:
            int r4 = r1.length
            if (r3 >= r4) goto L_0x001e
            r4 = r1[r3]
            boolean r4 = r6.isExtensionType(r4)
            if (r4 == 0) goto L_0x001b
            return r0
        L_0x001b:
            int r3 = r3 + 1
            goto L_0x000f
        L_0x001e:
            return r2
        L_0x001f:
            boolean r1 = r7 instanceof java.util.Collection
            if (r1 == 0) goto L_0x003c
            r1 = r7
            java.util.Collection r1 = (java.util.Collection) r1
            java.util.Iterator r1 = r1.iterator()
        L_0x002a:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x003b
            java.lang.Object r3 = r1.next()
            boolean r3 = r6.isExtensionType(r3)
            if (r3 == 0) goto L_0x002a
            return r0
        L_0x003b:
            return r2
        L_0x003c:
            boolean r1 = r7 instanceof java.util.Map
            if (r1 == 0) goto L_0x006f
            r1 = r7
            java.util.Map r1 = (java.util.Map) r1
            java.util.Set r3 = r1.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x004b:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x006e
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r5 = r4.getKey()
            boolean r5 = r6.isExtensionType(r5)
            if (r5 != 0) goto L_0x006d
            java.lang.Object r5 = r4.getValue()
            boolean r5 = r6.isExtensionType(r5)
            if (r5 == 0) goto L_0x006c
            goto L_0x006d
        L_0x006c:
            goto L_0x004b
        L_0x006d:
            return r0
        L_0x006e:
            return r2
        L_0x006f:
            boolean r1 = r7 instanceof java.lang.Integer
            if (r1 != 0) goto L_0x0084
            boolean r1 = r7 instanceof java.util.Date
            if (r1 != 0) goto L_0x0084
            boolean r1 = r7 instanceof java.lang.String
            if (r1 != 0) goto L_0x0084
            boolean r1 = r7 instanceof byte[]
            if (r1 != 0) goto L_0x0084
            boolean r1 = r7 instanceof java.lang.Double
            if (r1 != 0) goto L_0x0084
            goto L_0x0085
        L_0x0084:
            r0 = r2
        L_0x0085:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xmlrpc.client.XmlRpcLocalTransport.isExtensionType(java.lang.Object):boolean");
    }

    public Object sendRequest(XmlRpcRequest pRequest) throws XmlRpcException {
        XmlRpcRequestConfig config = pRequest.getConfig();
        if (!config.isEnabledForExtensions()) {
            int i = 0;
            while (i < pRequest.getParameterCount()) {
                if (!isExtensionType(pRequest.getParameter(i))) {
                    i++;
                } else {
                    throw new XmlRpcExtensionException("Parameter " + i + " has invalid type, if isEnabledForExtensions() == false");
                }
            }
        }
        XmlRpcRequestProcessor server = ((XmlRpcLocalClientConfig) config).getXmlRpcServer();
        try {
            Object result = server.execute(pRequest);
            if (!config.isEnabledForExtensions() && isExtensionType(result)) {
                throw new XmlRpcExtensionException("Result has invalid type, if isEnabledForExtensions() == false");
            } else if (result == null) {
                return null;
            } else {
                return server.getTypeConverterFactory().getTypeConverter(result.getClass()).backConvert(result);
            }
        } catch (XmlRpcException t) {
            throw t;
        } catch (Throwable t2) {
            throw new XmlRpcClientException("Failed to invoke method " + pRequest.getMethodName() + ": " + t2.getMessage(), t2);
        }
    }
}
