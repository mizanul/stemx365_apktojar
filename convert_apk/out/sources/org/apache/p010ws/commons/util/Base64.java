package org.apache.p010ws.commons.util;

import com.google.common.base.Ascii;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.UndeclaredThrowableException;
import org.jboss.netty.handler.codec.http.HttpConstants;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/* renamed from: org.apache.ws.commons.util.Base64 */
public class Base64 {
    public static final String LINE_SEPARATOR = "\n";
    public static final int LINE_SIZE = 76;
    /* access modifiers changed from: private */
    public static final byte[] base64ToInt = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, HttpConstants.COLON, HttpConstants.SEMICOLON, 60, HttpConstants.EQUALS, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.f47EM, -1, -1, -1, -1, -1, -1, Ascii.SUB, Ascii.ESC, Ascii.f49FS, Ascii.f50GS, Ascii.f54RS, Ascii.f58US, 32, 33, HttpConstants.DOUBLE_QUOTE, 35, 36, 37, 38, 39, 40, 41, 42, 43, HttpConstants.COMMA, 45, 46, 47, 48, 49, 50, 51};
    /* access modifiers changed from: private */
    public static final char[] intToBase64 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    /* renamed from: org.apache.ws.commons.util.Base64$DecodingException */
    public static class DecodingException extends IOException {
        private static final long serialVersionUID = 3257006574836135478L;

        DecodingException(String pMessage) {
            super(pMessage);
        }
    }

    /* renamed from: org.apache.ws.commons.util.Base64$SAXIOException */
    public static class SAXIOException extends IOException {
        private static final long serialVersionUID = 3258131345216451895L;
        final SAXException saxException;

        SAXIOException(SAXException e) {
            this.saxException = e;
        }

        public SAXException getSAXException() {
            return this.saxException;
        }
    }

    /* renamed from: org.apache.ws.commons.util.Base64$Encoder */
    public static abstract class Encoder {
        private final char[] charBuffer;
        private int charOffset;
        private int lineChars = 0;
        private int num;
        private int numBytes;
        private final String sep;
        private final int skipChars;
        private final int wrapSize;

        /* access modifiers changed from: protected */
        public abstract void writeBuffer(char[] cArr, int i, int i2) throws IOException;

        protected Encoder(char[] pBuffer, int pWrapSize, String pSep) {
            int i = 0;
            this.charBuffer = pBuffer;
            String str = pSep == null ? null : Base64.LINE_SEPARATOR;
            this.sep = str;
            int length = pWrapSize == 0 ? 4 : str.length() + 4;
            this.skipChars = length;
            i = length != 4 ? pWrapSize : i;
            this.wrapSize = i;
            if (i < 0 || i % 4 > 0) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Illegal argument for wrap size: ");
                stringBuffer.append(pWrapSize);
                stringBuffer.append("(Expected nonnegative multiple of 4)");
                throw new IllegalArgumentException(stringBuffer.toString());
            } else if (pBuffer.length < this.skipChars) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("The buffer must contain at least ");
                stringBuffer2.append(this.skipChars);
                stringBuffer2.append(" characters, but has ");
                stringBuffer2.append(pBuffer.length);
                throw new IllegalArgumentException(stringBuffer2.toString());
            }
        }

        private void wrap() {
            for (int j = 0; j < this.sep.length(); j++) {
                char[] cArr = this.charBuffer;
                int i = this.charOffset;
                this.charOffset = i + 1;
                cArr[i] = this.sep.charAt(j);
            }
            this.lineChars = 0;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v3, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v6, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: byte} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void write(byte[] r8, int r9, int r10) throws java.io.IOException {
            /*
                r7 = this;
                r0 = 0
            L_0x0001:
                if (r0 >= r10) goto L_0x0097
                int r1 = r9 + 1
                byte r9 = r8[r9]
                if (r9 >= 0) goto L_0x000b
                int r9 = r9 + 256
            L_0x000b:
                int r2 = r7.num
                int r2 = r2 << 8
                int r2 = r2 + r9
                r7.num = r2
                int r2 = r7.numBytes
                int r2 = r2 + 1
                r7.numBytes = r2
                r3 = 3
                if (r2 != r3) goto L_0x0092
                char[] r2 = r7.charBuffer
                int r3 = r7.charOffset
                int r4 = r3 + 1
                r7.charOffset = r4
                char[] r4 = org.apache.p010ws.commons.util.Base64.intToBase64
                int r5 = r7.num
                int r5 = r5 >> 18
                char r4 = r4[r5]
                r2[r3] = r4
                char[] r2 = r7.charBuffer
                int r3 = r7.charOffset
                int r4 = r3 + 1
                r7.charOffset = r4
                char[] r4 = org.apache.p010ws.commons.util.Base64.intToBase64
                int r5 = r7.num
                int r5 = r5 >> 12
                r5 = r5 & 63
                char r4 = r4[r5]
                r2[r3] = r4
                char[] r2 = r7.charBuffer
                int r3 = r7.charOffset
                int r4 = r3 + 1
                r7.charOffset = r4
                char[] r4 = org.apache.p010ws.commons.util.Base64.intToBase64
                int r5 = r7.num
                int r5 = r5 >> 6
                r5 = r5 & 63
                char r4 = r4[r5]
                r2[r3] = r4
                char[] r2 = r7.charBuffer
                int r3 = r7.charOffset
                int r4 = r3 + 1
                r7.charOffset = r4
                char[] r4 = org.apache.p010ws.commons.util.Base64.intToBase64
                int r5 = r7.num
                r5 = r5 & 63
                char r4 = r4[r5]
                r2[r3] = r4
                int r2 = r7.wrapSize
                if (r2 <= 0) goto L_0x007e
                int r3 = r7.lineChars
                int r3 = r3 + 4
                r7.lineChars = r3
                if (r3 < r2) goto L_0x007e
                r7.wrap()
            L_0x007e:
                r2 = 0
                r7.num = r2
                r7.numBytes = r2
                int r3 = r7.charOffset
                int r4 = r7.skipChars
                int r4 = r4 + r3
                char[] r5 = r7.charBuffer
                int r6 = r5.length
                if (r4 <= r6) goto L_0x0092
                r7.writeBuffer(r5, r2, r3)
                r7.charOffset = r2
            L_0x0092:
                int r0 = r0 + 1
                r9 = r1
                goto L_0x0001
            L_0x0097:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.p010ws.commons.util.Base64.Encoder.write(byte[], int, int):void");
        }

        public void flush() throws IOException {
            int i = this.numBytes;
            if (i > 0) {
                if (i == 1) {
                    char[] cArr = this.charBuffer;
                    int i2 = this.charOffset;
                    this.charOffset = i2 + 1;
                    cArr[i2] = Base64.intToBase64[this.num >> 2];
                    char[] cArr2 = this.charBuffer;
                    int i3 = this.charOffset;
                    this.charOffset = i3 + 1;
                    cArr2[i3] = Base64.intToBase64[(this.num << 4) & 63];
                    char[] cArr3 = this.charBuffer;
                    int i4 = this.charOffset;
                    int i5 = i4 + 1;
                    this.charOffset = i5;
                    cArr3[i4] = '=';
                    this.charOffset = i5 + 1;
                    cArr3[i5] = '=';
                } else {
                    char[] cArr4 = this.charBuffer;
                    int i6 = this.charOffset;
                    this.charOffset = i6 + 1;
                    cArr4[i6] = Base64.intToBase64[this.num >> 10];
                    char[] cArr5 = this.charBuffer;
                    int i7 = this.charOffset;
                    this.charOffset = i7 + 1;
                    cArr5[i7] = Base64.intToBase64[(this.num >> 4) & 63];
                    char[] cArr6 = this.charBuffer;
                    int i8 = this.charOffset;
                    this.charOffset = i8 + 1;
                    cArr6[i8] = Base64.intToBase64[(this.num << 2) & 63];
                    char[] cArr7 = this.charBuffer;
                    int i9 = this.charOffset;
                    this.charOffset = i9 + 1;
                    cArr7[i9] = '=';
                }
                this.lineChars += 4;
                this.num = 0;
                this.numBytes = 0;
            }
            if (this.wrapSize > 0 && this.lineChars > 0) {
                wrap();
            }
            int i10 = this.charOffset;
            if (i10 > 0) {
                writeBuffer(this.charBuffer, 0, i10);
                this.charOffset = 0;
            }
        }
    }

    /* renamed from: org.apache.ws.commons.util.Base64$EncoderOutputStream */
    public static class EncoderOutputStream extends OutputStream {
        private final Encoder encoder;
        private final byte[] oneByte = new byte[1];

        public EncoderOutputStream(Encoder pEncoder) {
            this.encoder = pEncoder;
        }

        public void write(int b) throws IOException {
            byte[] bArr = this.oneByte;
            bArr[0] = (byte) b;
            this.encoder.write(bArr, 0, 1);
        }

        public void write(byte[] pBuffer, int pOffset, int pLen) throws IOException {
            this.encoder.write(pBuffer, pOffset, pLen);
        }

        public void close() throws IOException {
            this.encoder.flush();
        }
    }

    public static OutputStream newEncoder(Writer pWriter) {
        return newEncoder(pWriter, 76, LINE_SEPARATOR);
    }

    public static OutputStream newEncoder(final Writer pWriter, int pLineSize, String pSeparator) {
        return new EncoderOutputStream(new Encoder(new char[4096], pLineSize, pSeparator) {
            /* access modifiers changed from: protected */
            public void writeBuffer(char[] pBuffer, int pOffset, int pLen) throws IOException {
                pWriter.write(pBuffer, pOffset, pLen);
            }
        });
    }

    /* renamed from: org.apache.ws.commons.util.Base64$SAXEncoder */
    public static class SAXEncoder extends Encoder {
        private final ContentHandler handler;

        public SAXEncoder(char[] pBuffer, int pWrapSize, String pSep, ContentHandler pHandler) {
            super(pBuffer, pWrapSize, pSep);
            this.handler = pHandler;
        }

        /* access modifiers changed from: protected */
        public void writeBuffer(char[] pChars, int pOffset, int pLen) throws IOException {
            try {
                this.handler.characters(pChars, pOffset, pLen);
            } catch (SAXException e) {
                throw new SAXIOException(e);
            }
        }
    }

    public static String encode(byte[] pBuffer, int pOffset, int pLength) {
        return encode(pBuffer, pOffset, pLength, 76, LINE_SEPARATOR);
    }

    public static String encode(byte[] pBuffer, int pOffset, int pLength, int pLineSize, String pSeparator) {
        StringWriter sw = new StringWriter();
        OutputStream ostream = newEncoder(sw, pLineSize, pSeparator);
        try {
            ostream.write(pBuffer, pOffset, pLength);
            ostream.close();
            return sw.toString();
        } catch (IOException e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    public static String encode(byte[] pBuffer) {
        return encode(pBuffer, 0, pBuffer.length);
    }

    /* renamed from: org.apache.ws.commons.util.Base64$Decoder */
    public static abstract class Decoder {
        private final byte[] byteBuffer;
        private int byteBufferOffset;
        private int eofBytes;
        private int num;
        private int numBytes;

        /* access modifiers changed from: protected */
        public abstract void writeBuffer(byte[] bArr, int i, int i2) throws IOException;

        protected Decoder(int pBufLen) {
            this.byteBuffer = new byte[pBufLen];
        }

        public void write(char[] pData, int pOffset, int pLen) throws IOException {
            byte result;
            int i = 0;
            while (i < pLen) {
                int pOffset2 = pOffset + 1;
                char c = pData[pOffset];
                if (!Character.isWhitespace(c)) {
                    if (c == '=') {
                        int i2 = this.eofBytes + 1;
                        this.eofBytes = i2;
                        int i3 = this.num << 6;
                        this.num = i3;
                        int i4 = this.numBytes + 1;
                        this.numBytes = i4;
                        if (i4 == 1 || i4 == 2) {
                            throw new DecodingException("Unexpected end of stream character (=)");
                        } else if (i4 == 3) {
                            continue;
                        } else if (i4 == 4) {
                            byte[] bArr = this.byteBuffer;
                            int i5 = this.byteBufferOffset;
                            int i6 = i5 + 1;
                            this.byteBufferOffset = i6;
                            bArr[i5] = (byte) (i3 >> 16);
                            if (i2 == 1) {
                                this.byteBufferOffset = i6 + 1;
                                bArr[i6] = (byte) (i3 >> 8);
                            }
                            writeBuffer(this.byteBuffer, 0, this.byteBufferOffset);
                            this.byteBufferOffset = 0;
                        } else if (i4 != 5) {
                            throw new IllegalStateException("Invalid value for numBytes");
                        } else {
                            throw new DecodingException("Trailing garbage detected");
                        }
                    } else if (this.eofBytes > 0) {
                        throw new DecodingException("Base64 characters after end of stream character (=) detected.");
                    } else if (c >= 0 && c < Base64.base64ToInt.length && (result = Base64.base64ToInt[c]) >= 0) {
                        int i7 = (this.num << 6) + result;
                        this.num = i7;
                        int i8 = this.numBytes + 1;
                        this.numBytes = i8;
                        if (i8 == 4) {
                            byte[] bArr2 = this.byteBuffer;
                            int i9 = this.byteBufferOffset;
                            int i10 = i9 + 1;
                            this.byteBufferOffset = i10;
                            bArr2[i9] = (byte) (i7 >> 16);
                            int i11 = i10 + 1;
                            this.byteBufferOffset = i11;
                            bArr2[i10] = (byte) ((i7 >> 8) & 255);
                            int i12 = i11 + 1;
                            this.byteBufferOffset = i12;
                            bArr2[i11] = (byte) (i7 & 255);
                            if (i12 + 3 > bArr2.length) {
                                writeBuffer(bArr2, 0, i12);
                                this.byteBufferOffset = 0;
                            }
                            this.num = 0;
                            this.numBytes = 0;
                        }
                    } else if (Character.isWhitespace(c) == 0) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Invalid Base64 character: ");
                        stringBuffer.append(c);
                        throw new DecodingException(stringBuffer.toString());
                    }
                }
                i++;
                pOffset = pOffset2;
            }
        }

        public void flush() throws IOException {
            int i = this.numBytes;
            if (i == 0 || i == 4) {
                int i2 = this.byteBufferOffset;
                if (i2 > 0) {
                    writeBuffer(this.byteBuffer, 0, i2);
                    this.byteBufferOffset = 0;
                    return;
                }
                return;
            }
            throw new DecodingException("Unexpected end of file");
        }
    }

    public Writer newDecoder(final OutputStream pStream) {
        return new Writer() {
            private final Decoder decoder = new Decoder(this, 1024) {
                private final /* synthetic */ C07802 this$1;

                {
                    this.this$1 = r1;
                }

                /* access modifiers changed from: protected */
                public void writeBuffer(byte[] pBytes, int pOffset, int pLen) throws IOException {
                    pStream.write(pBytes, pOffset, pLen);
                }
            };

            public void close() throws IOException {
                flush();
            }

            public void flush() throws IOException {
                this.decoder.flush();
                pStream.flush();
            }

            public void write(char[] cbuf, int off, int len) throws IOException {
                this.decoder.write(cbuf, off, len);
            }
        };
    }

    public static byte[] decode(char[] pBuffer, int pOffset, int pLength) throws DecodingException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Decoder d = new Decoder(1024) {
            /* access modifiers changed from: protected */
            public void writeBuffer(byte[] pBuf, int pOff, int pLen) throws IOException {
                baos.write(pBuf, pOff, pLen);
            }
        };
        try {
            d.write(pBuffer, pOffset, pLength);
            d.flush();
            return baos.toByteArray();
        } catch (DecodingException e) {
            throw e;
        } catch (IOException e2) {
            throw new UndeclaredThrowableException(e2);
        }
    }

    public static byte[] decode(char[] pBuffer) throws DecodingException {
        return decode(pBuffer, 0, pBuffer.length);
    }

    public static byte[] decode(String pBuffer) throws DecodingException {
        return decode(pBuffer.toCharArray());
    }
}
