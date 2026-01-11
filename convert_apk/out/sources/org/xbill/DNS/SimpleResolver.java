package org.xbill.DNS;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.ros.address.Address;

public class SimpleResolver implements Resolver {
    public static final int DEFAULT_EDNS_PAYLOADSIZE = 1280;
    public static final int DEFAULT_PORT = 53;
    private static final short DEFAULT_UDPSIZE = 512;
    private static String defaultResolver = Address.LOCALHOST;
    private static int uniqueID = 0;
    private InetSocketAddress address;
    private boolean ignoreTruncation;
    private InetSocketAddress localAddress;
    private OPTRecord queryOPT;
    private long timeoutValue;
    private TSIG tsig;
    private boolean useTCP;

    public SimpleResolver(String hostname) throws UnknownHostException {
        InetAddress addr;
        this.timeoutValue = 10000;
        if (hostname == null && (hostname = ResolverConfig.getCurrentConfig().server()) == null) {
            hostname = defaultResolver;
        }
        if (hostname.equals("0")) {
            addr = InetAddress.getLocalHost();
        } else {
            addr = InetAddress.getByName(hostname);
        }
        this.address = new InetSocketAddress(addr, 53);
    }

    public SimpleResolver() throws UnknownHostException {
        this((String) null);
    }

    /* access modifiers changed from: package-private */
    public InetSocketAddress getAddress() {
        return this.address;
    }

    public static void setDefaultResolver(String hostname) {
        defaultResolver = hostname;
    }

    public void setPort(int port) {
        this.address = new InetSocketAddress(this.address.getAddress(), port);
    }

    public void setAddress(InetSocketAddress addr) {
        this.address = addr;
    }

    public void setAddress(InetAddress addr) {
        this.address = new InetSocketAddress(addr, this.address.getPort());
    }

    public void setLocalAddress(InetSocketAddress addr) {
        this.localAddress = addr;
    }

    public void setLocalAddress(InetAddress addr) {
        this.localAddress = new InetSocketAddress(addr, 0);
    }

    public void setTCP(boolean flag) {
        this.useTCP = flag;
    }

    public void setIgnoreTruncation(boolean flag) {
        this.ignoreTruncation = flag;
    }

    public void setEDNS(int level, int payloadSize, int flags, List options) {
        if (level == 0 || level == -1) {
            if (payloadSize == 0) {
                payloadSize = DEFAULT_EDNS_PAYLOADSIZE;
            }
            this.queryOPT = new OPTRecord(payloadSize, 0, level, flags, options);
            return;
        }
        throw new IllegalArgumentException("invalid EDNS level - must be 0 or -1");
    }

    public void setEDNS(int level) {
        setEDNS(level, 0, 0, (List) null);
    }

    public void setTSIGKey(TSIG key) {
        this.tsig = key;
    }

    /* access modifiers changed from: package-private */
    public TSIG getTSIGKey() {
        return this.tsig;
    }

    public void setTimeout(int secs, int msecs) {
        this.timeoutValue = (((long) secs) * 1000) + ((long) msecs);
    }

    public void setTimeout(int secs) {
        setTimeout(secs, 0);
    }

    /* access modifiers changed from: package-private */
    public long getTimeout() {
        return this.timeoutValue;
    }

    private Message parseMessage(byte[] b) throws WireParseException {
        try {
            return new Message(b);
        } catch (IOException e) {
            e = e;
            if (Options.check("verbose")) {
                e.printStackTrace();
            }
            if (!(e instanceof WireParseException)) {
                e = new WireParseException("Error parsing message");
            }
            throw ((WireParseException) e);
        }
    }

    private void verifyTSIG(Message query, Message response, byte[] b, TSIG tsig2) {
        if (tsig2 != null) {
            int error = tsig2.verify(response, b, query.getTSIG());
            if (Options.check("verbose")) {
                PrintStream printStream = System.err;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("TSIG verify: ");
                stringBuffer.append(Rcode.string(error));
                printStream.println(stringBuffer.toString());
            }
        }
    }

    private void applyEDNS(Message query) {
        if (this.queryOPT != null && query.getOPT() == null) {
            query.addRecord(this.queryOPT, 3);
        }
    }

    private int maxUDPSize(Message query) {
        OPTRecord opt = query.getOPT();
        if (opt == null) {
            return 512;
        }
        return opt.getPayloadSize();
    }

    public Message send(Message query) throws IOException {
        boolean tcp;
        byte[] in;
        Message response;
        Record question;
        if (Options.check("verbose")) {
            PrintStream printStream = System.err;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Sending to ");
            stringBuffer.append(this.address.getAddress().getHostAddress());
            stringBuffer.append(":");
            stringBuffer.append(this.address.getPort());
            printStream.println(stringBuffer.toString());
        }
        if (query.getHeader().getOpcode() == 0 && (question = query.getQuestion()) != null && question.getType() == 252) {
            return sendAXFR(query);
        }
        Message query2 = (Message) query.clone();
        applyEDNS(query2);
        TSIG tsig2 = this.tsig;
        if (tsig2 != null) {
            tsig2.apply(query2, (TSIGRecord) null);
        }
        byte[] out = query2.toWire(65535);
        int udpSize = maxUDPSize(query2);
        boolean in2 = false;
        long endTime = System.currentTimeMillis() + this.timeoutValue;
        while (true) {
            if (this.useTCP || out.length > udpSize) {
                tcp = true;
            } else {
                tcp = in2;
            }
            if (tcp) {
                in = TCPClient.sendrecv(this.localAddress, this.address, out, endTime);
            } else {
                in = UDPClient.sendrecv(this.localAddress, this.address, out, udpSize, endTime);
            }
            if (in.length >= 12) {
                int id = ((in[0] & 255) << 8) + (in[1] & 255);
                int qid = query2.getHeader().getID();
                if (id != qid) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("invalid message id: expected ");
                    stringBuffer2.append(qid);
                    stringBuffer2.append("; got id ");
                    stringBuffer2.append(id);
                    String error = stringBuffer2.toString();
                    if (!tcp) {
                        if (Options.check("verbose")) {
                            System.err.println(error);
                        }
                        in2 = tcp;
                    } else {
                        throw new WireParseException(error);
                    }
                } else {
                    response = parseMessage(in);
                    verifyTSIG(query2, response, in, this.tsig);
                    if (tcp || this.ignoreTruncation || !response.getHeader().getFlag(6)) {
                        return response;
                    }
                    in2 = true;
                }
            } else {
                throw new WireParseException("invalid DNS header - too short");
            }
        }
        return response;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
        r2 = "(none)";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0020, code lost:
        r3 = new java.lang.StringBuffer();
        r3.append(getClass());
        r3.append(": ");
        r3.append(r2);
        r3 = r3.toString();
        r4 = new org.xbill.DNS.ResolveThread(r6, r7, r0, r8);
        r4.setName(r3);
        r4.setDaemon(true);
        r4.start();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0047, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000f, code lost:
        r1 = r7.getQuestion();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0013, code lost:
        if (r1 == null) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0015, code lost:
        r2 = r1.getName().toString();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object sendAsync(org.xbill.DNS.Message r7, org.xbill.DNS.ResolverListener r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            r0 = 0
            java.lang.Integer r1 = new java.lang.Integer     // Catch:{ all -> 0x0048 }
            int r2 = uniqueID     // Catch:{ all -> 0x0048 }
            int r3 = r2 + 1
            uniqueID = r3     // Catch:{ all -> 0x0048 }
            r1.<init>(r2)     // Catch:{ all -> 0x0048 }
            r0 = r1
            monitor-exit(r6)     // Catch:{ all -> 0x004b }
            org.xbill.DNS.Record r1 = r7.getQuestion()
            if (r1 == 0) goto L_0x001e
            org.xbill.DNS.Name r2 = r1.getName()
            java.lang.String r2 = r2.toString()
            goto L_0x0020
        L_0x001e:
            java.lang.String r2 = "(none)"
        L_0x0020:
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
            java.lang.Class r4 = r6.getClass()
            r3.append(r4)
            java.lang.String r4 = ": "
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            org.xbill.DNS.ResolveThread r4 = new org.xbill.DNS.ResolveThread
            r4.<init>(r6, r7, r0, r8)
            r4.setName(r3)
            r5 = 1
            r4.setDaemon(r5)
            r4.start()
            return r0
        L_0x0048:
            r1 = move-exception
        L_0x0049:
            monitor-exit(r6)     // Catch:{ all -> 0x004b }
            throw r1
        L_0x004b:
            r1 = move-exception
            goto L_0x0049
        */
        throw new UnsupportedOperationException("Method not decompiled: org.xbill.DNS.SimpleResolver.sendAsync(org.xbill.DNS.Message, org.xbill.DNS.ResolverListener):java.lang.Object");
    }

    private Message sendAXFR(Message query) throws IOException {
        ZoneTransferIn xfrin = ZoneTransferIn.newAXFR(query.getQuestion().getName(), (SocketAddress) this.address, this.tsig);
        xfrin.setTimeout((int) (getTimeout() / 1000));
        xfrin.setLocalAddress(this.localAddress);
        try {
            xfrin.run();
            List<Record> records = xfrin.getAXFR();
            Message response = new Message(query.getHeader().getID());
            response.getHeader().setFlag(5);
            response.getHeader().setFlag(0);
            response.addRecord(query.getQuestion(), 0);
            for (Record addRecord : records) {
                response.addRecord(addRecord, 1);
            }
            return response;
        } catch (ZoneTransferException e) {
            throw new WireParseException(e.getMessage());
        }
    }
}
