package org.xbill.DNS;

import java.util.Date;
import org.xbill.DNS.utils.C1114base64;
import org.xbill.DNS.utils.HMAC;

public class TSIG {
    public static final short FUDGE = 300;
    public static final Name HMAC;
    public static final Name HMAC_MD5;
    private static final String HMAC_MD5_STR = "HMAC-MD5.SIG-ALG.REG.INT.";
    public static final Name HMAC_SHA1 = Name.fromConstantString(HMAC_SHA1_STR);
    private static final String HMAC_SHA1_STR = "hmac-sha1.";
    public static final Name HMAC_SHA256 = Name.fromConstantString(HMAC_SHA256_STR);
    private static final String HMAC_SHA256_STR = "hmac-sha256.";
    /* access modifiers changed from: private */
    public Name alg;
    /* access modifiers changed from: private */
    public String digest;
    /* access modifiers changed from: private */
    public byte[] key;
    /* access modifiers changed from: private */
    public Name name;

    static {
        Name fromConstantString = Name.fromConstantString(HMAC_MD5_STR);
        HMAC_MD5 = fromConstantString;
        HMAC = fromConstantString;
    }

    private void getDigest() {
        if (this.alg.equals(HMAC_MD5)) {
            this.digest = "md5";
        } else if (this.alg.equals(HMAC_SHA1)) {
            this.digest = "sha-1";
        } else if (this.alg.equals(HMAC_SHA256)) {
            this.digest = "sha-256";
        } else {
            throw new IllegalArgumentException("Invalid algorithm");
        }
    }

    public TSIG(Name algorithm, Name name2, byte[] key2) {
        this.name = name2;
        this.alg = algorithm;
        this.key = key2;
        getDigest();
    }

    public TSIG(Name name2, byte[] key2) {
        this(HMAC_MD5, name2, key2);
    }

    public TSIG(Name algorithm, String name2, String key2) {
        byte[] fromString = C1114base64.fromString(key2);
        this.key = fromString;
        if (fromString != null) {
            try {
                this.name = Name.fromString(name2, Name.root);
                this.alg = algorithm;
                getDigest();
            } catch (TextParseException e) {
                throw new IllegalArgumentException("Invalid TSIG key name");
            }
        } else {
            throw new IllegalArgumentException("Invalid TSIG key string");
        }
    }

    public TSIG(String algorithm, String name2, String key2) {
        this(HMAC_MD5, name2, key2);
        if (algorithm.equalsIgnoreCase("hmac-md5")) {
            this.alg = HMAC_MD5;
        } else if (algorithm.equalsIgnoreCase("hmac-sha1")) {
            this.alg = HMAC_SHA1;
        } else if (algorithm.equalsIgnoreCase("hmac-sha256")) {
            this.alg = HMAC_SHA256;
        } else {
            throw new IllegalArgumentException("Invalid TSIG algorithm");
        }
        getDigest();
    }

    public TSIG(String name2, String key2) {
        this(HMAC_MD5, name2, key2);
    }

    public static TSIG fromString(String str) {
        String[] parts = str.split("[:/]");
        if (parts.length < 2 || parts.length > 3) {
            throw new IllegalArgumentException("Invalid TSIG key specification");
        } else if (parts.length == 3) {
            return new TSIG(parts[0], parts[1], parts[2]);
        } else {
            return new TSIG(HMAC_MD5, parts[0], parts[1]);
        }
    }

    public TSIGRecord generate(Message m, byte[] b, int error, TSIGRecord old) {
        Date timeSigned;
        HMAC hmac;
        int fudge;
        byte[] signature;
        byte[] other;
        int i = error;
        if (i != 18) {
            timeSigned = new Date();
        } else {
            timeSigned = old.getTimeSigned();
        }
        if (i == 0 || i == 18) {
            hmac = new HMAC(this.digest, this.key);
        } else {
            hmac = null;
        }
        int fudge2 = Options.intValue("tsigfudge");
        if (fudge2 < 0 || fudge2 > 32767) {
            fudge = 300;
        } else {
            fudge = fudge2;
        }
        if (old != null) {
            DNSOutput out = new DNSOutput();
            out.writeU16(old.getSignature().length);
            if (hmac != null) {
                hmac.update(out.toByteArray());
                hmac.update(old.getSignature());
            }
        }
        if (hmac != null) {
            hmac.update(b);
        } else {
            byte[] bArr = b;
        }
        DNSOutput out2 = new DNSOutput();
        this.name.toWireCanonical(out2);
        out2.writeU16(255);
        out2.writeU32(0);
        this.alg.toWireCanonical(out2);
        long time = timeSigned.getTime() / 1000;
        int timeHigh = (int) (time >> 32);
        long timeLow = time & 4294967295L;
        out2.writeU16(timeHigh);
        out2.writeU32(timeLow);
        out2.writeU16(fudge);
        out2.writeU16(i);
        out2.writeU16(0);
        if (hmac != null) {
            hmac.update(out2.toByteArray());
        }
        if (hmac != null) {
            signature = hmac.sign();
        } else {
            signature = new byte[0];
        }
        if (i == 18) {
            DNSOutput out3 = new DNSOutput();
            long time2 = new Date().getTime() / 1000;
            long j = timeLow;
            int timeHigh2 = (int) (time2 >> 32);
            long timeLow2 = 4294967295L & time2;
            out3.writeU16(timeHigh2);
            out3.writeU32(timeLow2);
            DNSOutput dNSOutput = out3;
            long j2 = time2;
            long j3 = timeLow2;
            int i2 = timeHigh2;
            other = out3.toByteArray();
        } else {
            int i3 = timeHigh;
            other = null;
            long j4 = timeLow;
            DNSOutput dNSOutput2 = out2;
            long j5 = time;
        }
        int i4 = fudge;
        return new TSIGRecord(this.name, 255, 0, this.alg, timeSigned, fudge, signature, m.getHeader().getID(), error, other);
    }

    public void apply(Message m, int error, TSIGRecord old) {
        m.addRecord(generate(m, m.toWire(), error, old), 3);
        m.tsigState = 3;
    }

    public void apply(Message m, TSIGRecord old) {
        apply(m, 0, old);
    }

    public void applyStream(Message m, TSIGRecord old, boolean first) {
        int fudge;
        Message message = m;
        if (first) {
            apply(m, old);
            return;
        }
        Date timeSigned = new Date();
        HMAC hmac = new HMAC(this.digest, this.key);
        int fudge2 = Options.intValue("tsigfudge");
        if (fudge2 < 0 || fudge2 > 32767) {
            fudge = 300;
        } else {
            fudge = fudge2;
        }
        DNSOutput out = new DNSOutput();
        out.writeU16(old.getSignature().length);
        hmac.update(out.toByteArray());
        hmac.update(old.getSignature());
        hmac.update(m.toWire());
        DNSOutput out2 = new DNSOutput();
        long time = timeSigned.getTime() / 1000;
        int timeHigh = (int) (time >> 32);
        long timeLow = time & 4294967295L;
        out2.writeU16(timeHigh);
        out2.writeU32(timeLow);
        out2.writeU16(fudge);
        hmac.update(out2.toByteArray());
        long j = timeLow;
        int i = timeHigh;
        DNSOutput dNSOutput = out2;
        message.addRecord(new TSIGRecord(this.name, 255, 0, this.alg, timeSigned, fudge, hmac.sign(), m.getHeader().getID(), 0, (byte[]) null), 3);
        message.tsigState = 3;
    }

    public byte verify(Message m, byte[] b, int length, TSIGRecord old) {
        byte b2;
        Message message = m;
        message.tsigState = 4;
        TSIGRecord tsig = m.getTSIG();
        HMAC hmac = new HMAC(this.digest, this.key);
        if (tsig == null) {
            return 1;
        }
        if (!tsig.getName().equals(this.name)) {
            b2 = 17;
            byte[] bArr = b;
        } else if (!tsig.getAlgorithm().equals(this.alg)) {
            b2 = 17;
            byte[] bArr2 = b;
        } else {
            long now = System.currentTimeMillis();
            long then = tsig.getTimeSigned().getTime();
            if (Math.abs(now - then) <= ((long) tsig.getFudge()) * 1000) {
                if (!(old == null || tsig.getError() == 17 || tsig.getError() == 16)) {
                    DNSOutput out = new DNSOutput();
                    out.writeU16(old.getSignature().length);
                    hmac.update(out.toByteArray());
                    hmac.update(old.getSignature());
                }
                m.getHeader().decCount(3);
                byte[] header = m.getHeader().toWire();
                m.getHeader().incCount(3);
                hmac.update(header);
                byte[] bArr3 = header;
                hmac.update(b, header.length, message.tsigstart - header.length);
                DNSOutput out2 = new DNSOutput();
                tsig.getName().toWireCanonical(out2);
                out2.writeU16(tsig.dclass);
                out2.writeU32(tsig.ttl);
                tsig.getAlgorithm().toWireCanonical(out2);
                long time = tsig.getTimeSigned().getTime() / 1000;
                long j = now;
                long j2 = then;
                out2.writeU16((int) (time >> 32));
                out2.writeU32(time & 4294967295L);
                out2.writeU16(tsig.getFudge());
                out2.writeU16(tsig.getError());
                if (tsig.getOther() != null) {
                    out2.writeU16(tsig.getOther().length);
                    out2.writeByteArray(tsig.getOther());
                } else {
                    out2.writeU16(0);
                }
                hmac.update(out2.toByteArray());
                if (hmac.verify(tsig.getSignature())) {
                    message.tsigState = 1;
                    return 0;
                } else if (!Options.check("verbose")) {
                    return 16;
                } else {
                    System.err.println("BADSIG failure");
                    return 16;
                }
            } else if (!Options.check("verbose")) {
                return 18;
            } else {
                System.err.println("BADTIME failure");
                return 18;
            }
        }
        if (Options.check("verbose")) {
            System.err.println("BADKEY failure");
        }
        return b2;
    }

    public int verify(Message m, byte[] b, TSIGRecord old) {
        return verify(m, b, b.length, old);
    }

    public int recordLength() {
        return this.name.length() + 10 + this.alg.length() + 8 + 18 + 4 + 8;
    }

    public static class StreamVerifier {
        private TSIG key;
        private TSIGRecord lastTSIG;
        private int lastsigned;
        private int nresponses = 0;
        private HMAC verifier = new HMAC(this.key.digest, this.key.key);

        public StreamVerifier(TSIG tsig, TSIGRecord old) {
            this.key = tsig;
            this.lastTSIG = old;
        }

        public int verify(Message m, byte[] b) {
            int len;
            Message message = m;
            byte[] bArr = b;
            TSIGRecord tsig = m.getTSIG();
            int i = this.nresponses + 1;
            this.nresponses = i;
            if (i == 1) {
                int result = this.key.verify(message, bArr, this.lastTSIG);
                if (result == 0) {
                    byte[] signature = tsig.getSignature();
                    DNSOutput out = new DNSOutput();
                    out.writeU16(signature.length);
                    this.verifier.update(out.toByteArray());
                    this.verifier.update(signature);
                }
                this.lastTSIG = tsig;
                return result;
            }
            if (tsig != null) {
                m.getHeader().decCount(3);
            }
            byte[] header = m.getHeader().toWire();
            if (tsig != null) {
                m.getHeader().incCount(3);
            }
            this.verifier.update(header);
            if (tsig == null) {
                len = bArr.length - header.length;
            } else {
                len = message.tsigstart - header.length;
            }
            this.verifier.update(bArr, header.length, len);
            if (tsig != null) {
                this.lastsigned = this.nresponses;
                this.lastTSIG = tsig;
                if (!tsig.getName().equals(this.key.name) || !tsig.getAlgorithm().equals(this.key.alg)) {
                    if (Options.check("verbose")) {
                        System.err.println("BADKEY failure");
                    }
                    message.tsigState = 4;
                    return 17;
                }
                DNSOutput out2 = new DNSOutput();
                long time = tsig.getTimeSigned().getTime() / 1000;
                out2.writeU16((int) (time >> 32));
                out2.writeU32(4294967295L & time);
                out2.writeU16(tsig.getFudge());
                this.verifier.update(out2.toByteArray());
                if (!this.verifier.verify(tsig.getSignature())) {
                    if (Options.check("verbose")) {
                        System.err.println("BADSIG failure");
                    }
                    message.tsigState = 4;
                    return 16;
                }
                this.verifier.clear();
                DNSOutput out3 = new DNSOutput();
                out3.writeU16(tsig.getSignature().length);
                this.verifier.update(out3.toByteArray());
                this.verifier.update(tsig.getSignature());
                message.tsigState = 1;
                return 0;
            }
            if (this.nresponses - this.lastsigned >= 100) {
                message.tsigState = 4;
                return 1;
            }
            message.tsigState = 2;
            return 0;
        }
    }
}
