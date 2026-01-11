package org.jboss.netty.handler.codec.spdy;

import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;

public class SpdyFrameDecoder extends FrameDecoder {
    private ChannelBuffer decompressed;
    private byte flags;
    private final SpdyHeaderBlockDecompressor headerBlockDecompressor;
    private int headerSize;
    private int length;
    private final int maxChunkSize;
    private final int maxHeaderSize;
    private int numHeaders;
    private SpdyHeaderBlock spdyHeaderBlock;
    private SpdySettingsFrame spdySettingsFrame;
    private final int spdyVersion;
    private State state;
    private int streamID;
    private int type;
    private int version;

    private enum State {
        READ_COMMON_HEADER,
        READ_CONTROL_FRAME,
        READ_SETTINGS_FRAME,
        READ_HEADER_BLOCK_FRAME,
        READ_HEADER_BLOCK,
        READ_DATA_FRAME,
        DISCARD_FRAME,
        FRAME_ERROR
    }

    @Deprecated
    public SpdyFrameDecoder() {
        this(2);
    }

    public SpdyFrameDecoder(int version2) {
        this(version2, 8192, 16384);
    }

    public SpdyFrameDecoder(int version2, int maxChunkSize2, int maxHeaderSize2) {
        super(false);
        if (version2 < 2 || version2 > 3) {
            throw new IllegalArgumentException("unsupported version: " + version2);
        } else if (maxChunkSize2 <= 0) {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + maxChunkSize2);
        } else if (maxHeaderSize2 > 0) {
            this.spdyVersion = version2;
            this.maxChunkSize = maxChunkSize2;
            this.maxHeaderSize = maxHeaderSize2;
            this.headerBlockDecompressor = SpdyHeaderBlockDecompressor.newInstance(version2);
            this.state = State.READ_COMMON_HEADER;
        } else {
            throw new IllegalArgumentException("maxHeaderSize must be a positive integer: " + maxHeaderSize2);
        }
    }

    /* access modifiers changed from: protected */
    public Object decodeLast(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        try {
            return decode(ctx, channel, buffer);
        } finally {
            this.headerBlockDecompressor.end();
        }
    }

    /* renamed from: org.jboss.netty.handler.codec.spdy.SpdyFrameDecoder$1 */
    static /* synthetic */ class C08831 {

        /* renamed from: $SwitchMap$org$jboss$netty$handler$codec$spdy$SpdyFrameDecoder$State */
        static final /* synthetic */ int[] f141xc5333031;

        static {
            int[] iArr = new int[State.values().length];
            f141xc5333031 = iArr;
            try {
                iArr[State.READ_COMMON_HEADER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f141xc5333031[State.READ_CONTROL_FRAME.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f141xc5333031[State.READ_SETTINGS_FRAME.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f141xc5333031[State.READ_HEADER_BLOCK_FRAME.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f141xc5333031[State.READ_HEADER_BLOCK.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f141xc5333031[State.READ_DATA_FRAME.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f141xc5333031[State.DISCARD_FRAME.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f141xc5333031[State.FRAME_ERROR.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        byte ID_flags;
        int ID;
        ChannelHandlerContext channelHandlerContext = ctx;
        ChannelBuffer channelBuffer = buffer;
        boolean z = false;
        switch (C08831.f141xc5333031[this.state.ordinal()]) {
            case 1:
                State readCommonHeader = readCommonHeader(channelBuffer);
                this.state = readCommonHeader;
                if (readCommonHeader == State.FRAME_ERROR) {
                    if (this.version != this.spdyVersion) {
                        fireProtocolException(channelHandlerContext, "Unsupported version: " + this.version);
                    } else {
                        fireInvalidControlFrameException(ctx);
                    }
                }
                if (this.length == 0) {
                    if (this.state != State.READ_DATA_FRAME) {
                        this.state = State.READ_COMMON_HEADER;
                    } else if (this.streamID == 0) {
                        this.state = State.FRAME_ERROR;
                        fireProtocolException(channelHandlerContext, "Received invalid data frame");
                        return null;
                    } else {
                        SpdyDataFrame spdyDataFrame = new DefaultSpdyDataFrame(this.streamID);
                        if ((this.flags & 1) != 0) {
                            z = true;
                        }
                        spdyDataFrame.setLast(z);
                        this.state = State.READ_COMMON_HEADER;
                        return spdyDataFrame;
                    }
                }
                return null;
            case 2:
                try {
                    Object frame = readControlFrame(channelBuffer);
                    if (frame != null) {
                        this.state = State.READ_COMMON_HEADER;
                    }
                    return frame;
                } catch (IllegalArgumentException e) {
                    this.state = State.FRAME_ERROR;
                    fireInvalidControlFrameException(ctx);
                    return null;
                }
            case 3:
                if (this.spdySettingsFrame == null) {
                    if (buffer.readableBytes() < 4) {
                        return null;
                    }
                    int numEntries = SpdyCodecUtil.getUnsignedInt(channelBuffer, buffer.readerIndex());
                    channelBuffer.skipBytes(4);
                    int i = this.length - 4;
                    this.length = i;
                    if ((i & 7) == 0 && (i >> 3) == numEntries) {
                        this.spdySettingsFrame = new DefaultSpdySettingsFrame();
                        this.spdySettingsFrame.setClearPreviouslyPersistedSettings((this.flags & 1) != 0);
                    } else {
                        this.state = State.FRAME_ERROR;
                        fireInvalidControlFrameException(ctx);
                        return null;
                    }
                }
                int readableEntries = Math.min(buffer.readableBytes() >> 3, this.length >> 3);
                for (int i2 = 0; i2 < readableEntries; i2++) {
                    if (this.version < 3) {
                        ID = (buffer.readByte() & 255) | ((buffer.readByte() & 255) << 8) | ((buffer.readByte() & 255) << 16);
                        ID_flags = buffer.readByte();
                    } else {
                        ID_flags = buffer.readByte();
                        ID = SpdyCodecUtil.getUnsignedMedium(channelBuffer, buffer.readerIndex());
                        channelBuffer.skipBytes(3);
                    }
                    int value = SpdyCodecUtil.getSignedInt(channelBuffer, buffer.readerIndex());
                    channelBuffer.skipBytes(4);
                    if (ID == 0) {
                        this.state = State.FRAME_ERROR;
                        this.spdySettingsFrame = null;
                        fireInvalidControlFrameException(ctx);
                        return null;
                    }
                    if (!this.spdySettingsFrame.isSet(ID)) {
                        this.spdySettingsFrame.setValue(ID, value, (ID_flags & 1) != 0, (ID_flags & 2) != 0);
                    }
                }
                int i3 = this.length - (readableEntries * 8);
                this.length = i3;
                if (i3 != 0) {
                    return null;
                }
                this.state = State.READ_COMMON_HEADER;
                Object frame2 = this.spdySettingsFrame;
                this.spdySettingsFrame = null;
                return frame2;
            case 4:
                try {
                    SpdyHeaderBlock readHeaderBlockFrame = readHeaderBlockFrame(channelBuffer);
                    this.spdyHeaderBlock = readHeaderBlockFrame;
                    if (readHeaderBlockFrame != null) {
                        if (this.length == 0) {
                            this.state = State.READ_COMMON_HEADER;
                            Object frame3 = this.spdyHeaderBlock;
                            this.spdyHeaderBlock = null;
                            return frame3;
                        }
                        this.state = State.READ_HEADER_BLOCK;
                    }
                    return null;
                } catch (IllegalArgumentException e2) {
                    this.state = State.FRAME_ERROR;
                    fireInvalidControlFrameException(ctx);
                    return null;
                }
            case 5:
                int compressedBytes = Math.min(buffer.readableBytes(), this.length);
                this.length -= compressedBytes;
                try {
                    decodeHeaderBlock(channelBuffer.readSlice(compressedBytes));
                    SpdyHeaderBlock spdyHeaderBlock2 = this.spdyHeaderBlock;
                    if (spdyHeaderBlock2 != null && spdyHeaderBlock2.isInvalid()) {
                        Object frame4 = this.spdyHeaderBlock;
                        this.spdyHeaderBlock = null;
                        this.decompressed = null;
                        if (this.length == 0) {
                            this.state = State.READ_COMMON_HEADER;
                        }
                        return frame4;
                    } else if (this.length != 0) {
                        return null;
                    } else {
                        Object frame5 = this.spdyHeaderBlock;
                        this.spdyHeaderBlock = null;
                        this.state = State.READ_COMMON_HEADER;
                        return frame5;
                    }
                } catch (Exception e3) {
                    this.state = State.FRAME_ERROR;
                    this.spdyHeaderBlock = null;
                    this.decompressed = null;
                    Channels.fireExceptionCaught(channelHandlerContext, (Throwable) e3);
                    return null;
                }
            case 6:
                if (this.streamID == 0) {
                    this.state = State.FRAME_ERROR;
                    fireProtocolException(channelHandlerContext, "Received invalid data frame");
                    return null;
                }
                int dataLength = Math.min(this.maxChunkSize, this.length);
                if (buffer.readableBytes() < dataLength) {
                    return null;
                }
                SpdyDataFrame spdyDataFrame2 = new DefaultSpdyDataFrame(this.streamID);
                spdyDataFrame2.setData(channelBuffer.readBytes(dataLength));
                int i4 = this.length - dataLength;
                this.length = i4;
                if (i4 == 0) {
                    if ((this.flags & 1) != 0) {
                        z = true;
                    }
                    spdyDataFrame2.setLast(z);
                    this.state = State.READ_COMMON_HEADER;
                }
                return spdyDataFrame2;
            case 7:
                int numBytes = Math.min(buffer.readableBytes(), this.length);
                channelBuffer.skipBytes(numBytes);
                int i5 = this.length - numBytes;
                this.length = i5;
                if (i5 == 0) {
                    this.state = State.READ_COMMON_HEADER;
                }
                return null;
            case 8:
                channelBuffer.skipBytes(buffer.readableBytes());
                return null;
            default:
                throw new Error("Shouldn't reach here.");
        }
    }

    private State readCommonHeader(ChannelBuffer buffer) {
        if (buffer.readableBytes() < 8) {
            return State.READ_COMMON_HEADER;
        }
        int frameOffset = buffer.readerIndex();
        int flagsOffset = frameOffset + 4;
        int lengthOffset = frameOffset + 5;
        buffer.skipBytes(8);
        boolean control = (buffer.getByte(frameOffset) & 128) != 0;
        this.flags = buffer.getByte(flagsOffset);
        this.length = SpdyCodecUtil.getUnsignedMedium(buffer, lengthOffset);
        if (control) {
            this.version = SpdyCodecUtil.getUnsignedShort(buffer, frameOffset) & 32767;
            this.type = SpdyCodecUtil.getUnsignedShort(buffer, frameOffset + 2);
            if (this.version != this.spdyVersion || !isValidControlFrameHeader()) {
                return State.FRAME_ERROR;
            }
            if (willGenerateControlFrame()) {
                int i = this.type;
                if (!(i == 1 || i == 2)) {
                    if (i == 4) {
                        return State.READ_SETTINGS_FRAME;
                    }
                    if (i != 8) {
                        return State.READ_CONTROL_FRAME;
                    }
                }
                return State.READ_HEADER_BLOCK_FRAME;
            } else if (this.length != 0) {
                return State.DISCARD_FRAME;
            } else {
                return State.READ_COMMON_HEADER;
            }
        } else {
            this.streamID = SpdyCodecUtil.getUnsignedInt(buffer, frameOffset);
            return State.READ_DATA_FRAME;
        }
    }

    private Object readControlFrame(ChannelBuffer buffer) {
        int i = this.type;
        int minLength = 8;
        if (i != 3) {
            if (i != 9) {
                if (i != 6) {
                    if (i == 7) {
                        if (this.version < 3) {
                            minLength = 4;
                        }
                        if (buffer.readableBytes() < minLength) {
                            return null;
                        }
                        int lastGoodStreamID = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                        buffer.skipBytes(4);
                        if (this.version < 3) {
                            return new DefaultSpdyGoAwayFrame(lastGoodStreamID);
                        }
                        int statusCode = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
                        buffer.skipBytes(4);
                        return new DefaultSpdyGoAwayFrame(lastGoodStreamID, statusCode);
                    }
                    throw new Error("Shouldn't reach here.");
                } else if (buffer.readableBytes() < 4) {
                    return null;
                } else {
                    int ID = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
                    buffer.skipBytes(4);
                    return new DefaultSpdyPingFrame(ID);
                }
            } else if (buffer.readableBytes() < 8) {
                return null;
            } else {
                int streamID2 = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                int deltaWindowSize = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex() + 4);
                buffer.skipBytes(8);
                return new DefaultSpdyWindowUpdateFrame(streamID2, deltaWindowSize);
            }
        } else if (buffer.readableBytes() < 8) {
            return null;
        } else {
            int streamID3 = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
            int statusCode2 = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex() + 4);
            buffer.skipBytes(8);
            return new DefaultSpdyRstStreamFrame(streamID3, statusCode2);
        }
    }

    private SpdyHeaderBlock readHeaderBlockFrame(ChannelBuffer buffer) {
        ChannelBuffer channelBuffer = buffer;
        int i = this.type;
        boolean z = false;
        if (i != 1) {
            int minLength = 8;
            if (i == 2) {
                if (this.version >= 3) {
                    minLength = 4;
                }
                if (buffer.readableBytes() < minLength) {
                    return null;
                }
                int streamID2 = SpdyCodecUtil.getUnsignedInt(channelBuffer, buffer.readerIndex());
                channelBuffer.skipBytes(4);
                this.length -= 4;
                if (this.version < 3) {
                    channelBuffer.skipBytes(2);
                    this.length -= 2;
                }
                if (this.version < 3 && this.length == 2 && channelBuffer.getShort(buffer.readerIndex()) == 0) {
                    channelBuffer.skipBytes(2);
                    this.length = 0;
                }
                SpdySynReplyFrame spdySynReplyFrame = new DefaultSpdySynReplyFrame(streamID2);
                if ((this.flags & 1) != 0) {
                    z = true;
                }
                spdySynReplyFrame.setLast(z);
                return spdySynReplyFrame;
            } else if (i != 8) {
                throw new Error("Shouldn't reach here.");
            } else if (buffer.readableBytes() < 4) {
                return null;
            } else {
                if (this.version < 3 && this.length > 4 && buffer.readableBytes() < 8) {
                    return null;
                }
                int streamID3 = SpdyCodecUtil.getUnsignedInt(channelBuffer, buffer.readerIndex());
                channelBuffer.skipBytes(4);
                int i2 = this.length - 4;
                this.length = i2;
                if (this.version < 3 && i2 != 0) {
                    channelBuffer.skipBytes(2);
                    this.length -= 2;
                }
                if (this.version < 3 && this.length == 2 && channelBuffer.getShort(buffer.readerIndex()) == 0) {
                    channelBuffer.skipBytes(2);
                    this.length = 0;
                }
                SpdyHeadersFrame spdyHeadersFrame = new DefaultSpdyHeadersFrame(streamID3);
                spdyHeadersFrame.setLast((this.flags & 1) != 0);
                return spdyHeadersFrame;
            }
        } else {
            if (buffer.readableBytes() < (this.version < 3 ? 12 : 10)) {
                return null;
            }
            int offset = buffer.readerIndex();
            int streamID4 = SpdyCodecUtil.getUnsignedInt(channelBuffer, offset);
            int associatedToStreamID = SpdyCodecUtil.getUnsignedInt(channelBuffer, offset + 4);
            byte priority = (byte) ((channelBuffer.getByte(offset + 8) >> 5) & 7);
            if (this.version < 3) {
                priority = (byte) (priority >> 1);
            }
            channelBuffer.skipBytes(10);
            int i3 = this.length - 10;
            this.length = i3;
            if (this.version < 3 && i3 == 2 && channelBuffer.getShort(buffer.readerIndex()) == 0) {
                channelBuffer.skipBytes(2);
                this.length = 0;
            }
            SpdySynStreamFrame spdySynStreamFrame = new DefaultSpdySynStreamFrame(streamID4, associatedToStreamID, priority);
            spdySynStreamFrame.setLast((this.flags & 1) != 0);
            spdySynStreamFrame.setUnidirectional((this.flags & 2) != 0);
            return spdySynStreamFrame;
        }
    }

    private boolean ensureBytes(int bytes) throws Exception {
        if (this.decompressed.readableBytes() >= bytes) {
            return true;
        }
        this.headerBlockDecompressor.decode(this.decompressed);
        if (this.decompressed.readableBytes() >= bytes) {
            return true;
        }
        return false;
    }

    private int readLengthField() {
        if (this.version < 3) {
            return this.decompressed.readUnsignedShort();
        }
        return this.decompressed.readInt();
    }

    private void decodeHeaderBlock(ChannelBuffer buffer) throws Exception {
        if (this.decompressed == null) {
            this.headerSize = 0;
            this.numHeaders = -1;
            this.decompressed = ChannelBuffers.dynamicBuffer(8192);
        }
        this.headerBlockDecompressor.setInput(buffer);
        this.headerBlockDecompressor.decode(this.decompressed);
        if (this.spdyHeaderBlock == null) {
            this.decompressed = null;
            return;
        }
        int lengthFieldSize = this.version < 3 ? 2 : 4;
        if (this.numHeaders == -1) {
            if (this.decompressed.readableBytes() >= lengthFieldSize) {
                int readLengthField = readLengthField();
                this.numHeaders = readLengthField;
                if (readLengthField < 0) {
                    this.spdyHeaderBlock.setInvalid();
                    return;
                }
            } else {
                return;
            }
        }
        while (this.numHeaders > 0) {
            int headerSize2 = this.headerSize;
            this.decompressed.markReaderIndex();
            if (!ensureBytes(lengthFieldSize)) {
                this.decompressed.resetReaderIndex();
                this.decompressed.discardReadBytes();
                return;
            }
            int nameLength = readLengthField();
            if (nameLength <= 0) {
                this.spdyHeaderBlock.setInvalid();
                return;
            }
            int headerSize3 = headerSize2 + nameLength;
            if (headerSize3 > this.maxHeaderSize) {
                throw new TooLongFrameException("Header block exceeds " + this.maxHeaderSize);
            } else if (!ensureBytes(nameLength)) {
                this.decompressed.resetReaderIndex();
                this.decompressed.discardReadBytes();
                return;
            } else {
                byte[] nameBytes = new byte[nameLength];
                this.decompressed.readBytes(nameBytes);
                String name = new String(nameBytes, XmlRpcStreamConfig.UTF8_ENCODING);
                if (this.spdyHeaderBlock.containsHeader(name)) {
                    this.spdyHeaderBlock.setInvalid();
                    return;
                } else if (!ensureBytes(lengthFieldSize)) {
                    this.decompressed.resetReaderIndex();
                    this.decompressed.discardReadBytes();
                    return;
                } else {
                    int valueLength = readLengthField();
                    if (valueLength <= 0) {
                        this.spdyHeaderBlock.setInvalid();
                        return;
                    }
                    int headerSize4 = headerSize3 + valueLength;
                    if (headerSize4 > this.maxHeaderSize) {
                        throw new TooLongFrameException("Header block exceeds " + this.maxHeaderSize);
                    } else if (!ensureBytes(valueLength)) {
                        this.decompressed.resetReaderIndex();
                        this.decompressed.discardReadBytes();
                        return;
                    } else {
                        byte[] valueBytes = new byte[valueLength];
                        this.decompressed.readBytes(valueBytes);
                        int index = 0;
                        int offset = 0;
                        while (index < valueLength) {
                            while (index < valueBytes.length && valueBytes[index] != 0) {
                                index++;
                            }
                            if (index >= valueBytes.length || valueBytes[index + 1] != 0) {
                                try {
                                    this.spdyHeaderBlock.addHeader(name, new String(valueBytes, offset, index - offset, XmlRpcStreamConfig.UTF8_ENCODING));
                                    index++;
                                    offset = index;
                                } catch (IllegalArgumentException e) {
                                    this.spdyHeaderBlock.setInvalid();
                                    return;
                                }
                            } else {
                                this.spdyHeaderBlock.setInvalid();
                                return;
                            }
                        }
                        this.numHeaders--;
                        this.headerSize = headerSize4;
                    }
                }
            }
        }
        this.decompressed = null;
    }

    private boolean isValidControlFrameHeader() {
        switch (this.type) {
            case 1:
                if (this.version < 3) {
                    if (this.length < 12) {
                        return false;
                    }
                } else if (this.length < 10) {
                    return false;
                }
                return true;
            case 2:
                if (this.version < 3) {
                    if (this.length < 8) {
                        return false;
                    }
                } else if (this.length < 4) {
                    return false;
                }
                return true;
            case 3:
                if (this.flags == 0 && this.length == 8) {
                    return true;
                }
                return false;
            case 4:
                if (this.length >= 4) {
                    return true;
                }
                return false;
            case 5:
                if (this.length == 0) {
                    return true;
                }
                return false;
            case 6:
                if (this.length == 4) {
                    return true;
                }
                return false;
            case 7:
                if (this.version < 3) {
                    if (this.length != 4) {
                        return false;
                    }
                } else if (this.length != 8) {
                    return false;
                }
                return true;
            case 8:
                if (this.version < 3) {
                    int i = this.length;
                    if (i == 4 || i >= 8) {
                        return true;
                    }
                    return false;
                } else if (this.length >= 4) {
                    return true;
                } else {
                    return false;
                }
            case 9:
                if (this.length == 8) {
                    return true;
                }
                return false;
            default:
                return true;
        }
    }

    private boolean willGenerateControlFrame() {
        switch (this.type) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
                return true;
            default:
                return false;
        }
    }

    private void fireInvalidControlFrameException(ChannelHandlerContext ctx) {
        String message = "Received invalid control frame";
        switch (this.type) {
            case 1:
                message = "Received invalid SYN_STREAM control frame";
                break;
            case 2:
                message = "Received invalid SYN_REPLY control frame";
                break;
            case 3:
                message = "Received invalid RST_STREAM control frame";
                break;
            case 4:
                message = "Received invalid SETTINGS control frame";
                break;
            case 5:
                message = "Received invalid NOOP control frame";
                break;
            case 6:
                message = "Received invalid PING control frame";
                break;
            case 7:
                message = "Received invalid GOAWAY control frame";
                break;
            case 8:
                message = "Received invalid HEADERS control frame";
                break;
            case 9:
                message = "Received invalid WINDOW_UPDATE control frame";
                break;
            case 10:
                message = "Received invalid CREDENTIAL control frame";
                break;
        }
        fireProtocolException(ctx, message);
    }

    private static void fireProtocolException(ChannelHandlerContext ctx, String message) {
        Channels.fireExceptionCaught(ctx, (Throwable) new SpdyProtocolException(message));
    }
}
