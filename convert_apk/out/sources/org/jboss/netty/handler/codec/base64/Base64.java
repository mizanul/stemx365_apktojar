package org.jboss.netty.handler.codec.base64;

import com.google.common.base.Ascii;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferFactory;
import org.jboss.netty.buffer.HeapChannelBufferFactory;

public final class Base64 {
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    private static final byte WHITE_SPACE_ENC = -5;

    private static byte[] alphabet(Base64Dialect dialect) {
        if (dialect != null) {
            return dialect.alphabet;
        }
        throw new NullPointerException("dialect");
    }

    private static byte[] decodabet(Base64Dialect dialect) {
        if (dialect != null) {
            return dialect.decodabet;
        }
        throw new NullPointerException("dialect");
    }

    private static boolean breakLines(Base64Dialect dialect) {
        if (dialect != null) {
            return dialect.breakLinesByDefault;
        }
        throw new NullPointerException("dialect");
    }

    public static ChannelBuffer encode(ChannelBuffer src2) {
        return encode(src2, Base64Dialect.STANDARD);
    }

    public static ChannelBuffer encode(ChannelBuffer src2, Base64Dialect dialect) {
        return encode(src2, breakLines(dialect), dialect);
    }

    public static ChannelBuffer encode(ChannelBuffer src2, ChannelBufferFactory bufferFactory) {
        return encode(src2, Base64Dialect.STANDARD, bufferFactory);
    }

    public static ChannelBuffer encode(ChannelBuffer src2, Base64Dialect dialect, ChannelBufferFactory bufferFactory) {
        return encode(src2, breakLines(dialect), dialect, bufferFactory);
    }

    public static ChannelBuffer encode(ChannelBuffer src2, boolean breakLines) {
        return encode(src2, breakLines, Base64Dialect.STANDARD);
    }

    public static ChannelBuffer encode(ChannelBuffer src2, boolean breakLines, Base64Dialect dialect) {
        return encode(src2, breakLines, dialect, HeapChannelBufferFactory.getInstance());
    }

    public static ChannelBuffer encode(ChannelBuffer src2, boolean breakLines, ChannelBufferFactory bufferFactory) {
        return encode(src2, breakLines, Base64Dialect.STANDARD, bufferFactory);
    }

    public static ChannelBuffer encode(ChannelBuffer src2, boolean breakLines, Base64Dialect dialect, ChannelBufferFactory bufferFactory) {
        if (src2 != null) {
            ChannelBuffer dest = encode(src2, src2.readerIndex(), src2.readableBytes(), breakLines, dialect, bufferFactory);
            src2.readerIndex(src2.writerIndex());
            return dest;
        }
        throw new NullPointerException("src");
    }

    public static ChannelBuffer encode(ChannelBuffer src2, int off, int len) {
        return encode(src2, off, len, Base64Dialect.STANDARD);
    }

    public static ChannelBuffer encode(ChannelBuffer src2, int off, int len, Base64Dialect dialect) {
        return encode(src2, off, len, breakLines(dialect), dialect);
    }

    public static ChannelBuffer encode(ChannelBuffer src2, int off, int len, ChannelBufferFactory bufferFactory) {
        return encode(src2, off, len, Base64Dialect.STANDARD, bufferFactory);
    }

    public static ChannelBuffer encode(ChannelBuffer src2, int off, int len, Base64Dialect dialect, ChannelBufferFactory bufferFactory) {
        return encode(src2, off, len, breakLines(dialect), dialect, bufferFactory);
    }

    public static ChannelBuffer encode(ChannelBuffer src2, int off, int len, boolean breakLines) {
        return encode(src2, off, len, breakLines, Base64Dialect.STANDARD);
    }

    public static ChannelBuffer encode(ChannelBuffer src2, int off, int len, boolean breakLines, Base64Dialect dialect) {
        return encode(src2, off, len, breakLines, dialect, HeapChannelBufferFactory.getInstance());
    }

    public static ChannelBuffer encode(ChannelBuffer src2, int off, int len, boolean breakLines, ChannelBufferFactory bufferFactory) {
        return encode(src2, off, len, breakLines, Base64Dialect.STANDARD, bufferFactory);
    }

    public static ChannelBuffer encode(ChannelBuffer src2, int off, int len, boolean breakLines, Base64Dialect dialect, ChannelBufferFactory bufferFactory) {
        int i = len;
        ChannelBufferFactory channelBufferFactory = bufferFactory;
        if (src2 == null) {
            throw new NullPointerException("src");
        } else if (dialect == null) {
            throw new NullPointerException("dialect");
        } else if (channelBufferFactory != null) {
            int len43 = (i * 4) / 3;
            ChannelBuffer dest = channelBufferFactory.getBuffer(src2.order(), (i % 3 > 0 ? 4 : 0) + len43 + (breakLines ? len43 / 76 : 0));
            int len2 = i - 2;
            int d = 0;
            int e = 0;
            int lineLength = 0;
            while (d < len2) {
                encode3to4(src2, d + off, 3, dest, e, dialect);
                lineLength += 4;
                if (breakLines && lineLength == 76) {
                    dest.setByte(e + 4, 10);
                    e++;
                    lineLength = 0;
                }
                d += 3;
                e += 4;
            }
            if (d < i) {
                encode3to4(src2, d + off, i - d, dest, e, dialect);
                e += 4;
            }
            return dest.slice(0, e);
        } else {
            throw new NullPointerException("bufferFactory");
        }
    }

    private static void encode3to4(ChannelBuffer src2, int srcOffset, int numSigBytes, ChannelBuffer dest, int destOffset, Base64Dialect dialect) {
        byte[] ALPHABET = alphabet(dialect);
        int i = 0;
        int i2 = (numSigBytes > 0 ? (src2.getByte(srcOffset) << Ascii.CAN) >>> 8 : 0) | (numSigBytes > 1 ? (src2.getByte(srcOffset + 1) << Ascii.CAN) >>> 16 : 0);
        if (numSigBytes > 2) {
            i = (src2.getByte(srcOffset + 2) << Ascii.CAN) >>> 24;
        }
        int inBuff = i | i2;
        if (numSigBytes == 1) {
            dest.setByte(destOffset, ALPHABET[inBuff >>> 18]);
            dest.setByte(destOffset + 1, ALPHABET[(inBuff >>> 12) & 63]);
            dest.setByte(destOffset + 2, 61);
            dest.setByte(destOffset + 3, 61);
        } else if (numSigBytes == 2) {
            dest.setByte(destOffset, ALPHABET[inBuff >>> 18]);
            dest.setByte(destOffset + 1, ALPHABET[(inBuff >>> 12) & 63]);
            dest.setByte(destOffset + 2, ALPHABET[(inBuff >>> 6) & 63]);
            dest.setByte(destOffset + 3, 61);
        } else if (numSigBytes == 3) {
            dest.setByte(destOffset, ALPHABET[inBuff >>> 18]);
            dest.setByte(destOffset + 1, ALPHABET[(inBuff >>> 12) & 63]);
            dest.setByte(destOffset + 2, ALPHABET[(inBuff >>> 6) & 63]);
            dest.setByte(destOffset + 3, ALPHABET[inBuff & 63]);
        }
    }

    public static ChannelBuffer decode(ChannelBuffer src2) {
        return decode(src2, Base64Dialect.STANDARD);
    }

    public static ChannelBuffer decode(ChannelBuffer src2, Base64Dialect dialect) {
        return decode(src2, dialect, HeapChannelBufferFactory.getInstance());
    }

    public static ChannelBuffer decode(ChannelBuffer src2, ChannelBufferFactory bufferFactory) {
        return decode(src2, Base64Dialect.STANDARD, bufferFactory);
    }

    public static ChannelBuffer decode(ChannelBuffer src2, Base64Dialect dialect, ChannelBufferFactory bufferFactory) {
        if (src2 != null) {
            ChannelBuffer dest = decode(src2, src2.readerIndex(), src2.readableBytes(), dialect, bufferFactory);
            src2.readerIndex(src2.writerIndex());
            return dest;
        }
        throw new NullPointerException("src");
    }

    public static ChannelBuffer decode(ChannelBuffer src2, int off, int len) {
        return decode(src2, off, len, Base64Dialect.STANDARD);
    }

    public static ChannelBuffer decode(ChannelBuffer src2, int off, int len, Base64Dialect dialect) {
        return decode(src2, off, len, dialect, HeapChannelBufferFactory.getInstance());
    }

    public static ChannelBuffer decode(ChannelBuffer src2, int off, int len, ChannelBufferFactory bufferFactory) {
        return decode(src2, off, len, Base64Dialect.STANDARD, bufferFactory);
    }

    public static ChannelBuffer decode(ChannelBuffer src2, int off, int len, Base64Dialect dialect, ChannelBufferFactory bufferFactory) {
        Base64Dialect base64Dialect = dialect;
        ChannelBufferFactory channelBufferFactory = bufferFactory;
        if (src2 == null) {
            throw new NullPointerException("src");
        } else if (base64Dialect == null) {
            throw new NullPointerException("dialect");
        } else if (channelBufferFactory != null) {
            byte[] DECODABET = decodabet(dialect);
            ChannelBuffer dest = channelBufferFactory.getBuffer(src2.order(), (len * 3) / 4);
            int outBuffPosn = 0;
            byte[] b4 = new byte[4];
            int b4Posn = 0;
            int i = off;
            while (i < off + len) {
                byte sbiCrop = (byte) (src2.getByte(i) & Byte.MAX_VALUE);
                byte sbiDecode = DECODABET[sbiCrop];
                if (sbiDecode >= -5) {
                    if (sbiDecode >= -1) {
                        int b4Posn2 = b4Posn + 1;
                        b4[b4Posn] = sbiCrop;
                        if (b4Posn2 > 3) {
                            outBuffPosn += decode4to3(b4, 0, dest, outBuffPosn, base64Dialect);
                            b4Posn = 0;
                            if (sbiCrop == 61) {
                                break;
                            }
                        } else {
                            b4Posn = b4Posn2;
                        }
                    }
                    i++;
                } else {
                    throw new IllegalArgumentException("bad Base64 input character at " + i + ": " + src2.getUnsignedByte(i) + " (decimal)");
                }
            }
            return dest.slice(0, outBuffPosn);
        } else {
            throw new NullPointerException("bufferFactory");
        }
    }

    private static int decode4to3(byte[] src2, int srcOffset, ChannelBuffer dest, int destOffset, Base64Dialect dialect) {
        byte[] DECODABET = decodabet(dialect);
        if (src2[srcOffset + 2] == 61) {
            dest.setByte(destOffset, (byte) ((((DECODABET[src2[srcOffset]] & 255) << 18) | ((DECODABET[src2[srcOffset + 1]] & 255) << 12)) >>> 16));
            return 1;
        } else if (src2[srcOffset + 3] == 61) {
            int outBuff = ((DECODABET[src2[srcOffset]] & 255) << 18) | ((DECODABET[src2[srcOffset + 1]] & 255) << 12) | ((DECODABET[src2[srcOffset + 2]] & 255) << 6);
            dest.setByte(destOffset, (byte) (outBuff >>> 16));
            dest.setByte(destOffset + 1, (byte) (outBuff >>> 8));
            return 2;
        } else {
            try {
                int outBuff2 = (DECODABET[src2[srcOffset + 3]] & 255) | ((DECODABET[src2[srcOffset]] & 255) << 18) | ((DECODABET[src2[srcOffset + 1]] & 255) << 12) | ((DECODABET[src2[srcOffset + 2]] & 255) << 6);
                dest.setByte(destOffset, (byte) (outBuff2 >> 16));
                dest.setByte(destOffset + 1, (byte) (outBuff2 >> 8));
                dest.setByte(destOffset + 2, (byte) outBuff2);
                return 3;
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException("not encoded in Base64");
            }
        }
    }

    private Base64() {
    }
}
