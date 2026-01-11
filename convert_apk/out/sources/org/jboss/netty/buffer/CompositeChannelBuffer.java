package org.jboss.netty.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.UShort;
import org.jboss.netty.util.internal.DetectionUtil;

public class CompositeChannelBuffer extends AbstractChannelBuffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private ChannelBuffer[] components;
    private final boolean gathering;
    private int[] indices;
    private int lastAccessedComponentId;
    private final ByteOrder order;

    public CompositeChannelBuffer(ByteOrder endianness, List<ChannelBuffer> buffers, boolean gathering2) {
        this.order = endianness;
        this.gathering = gathering2;
        setComponents(buffers);
    }

    public boolean useGathering() {
        return this.gathering && DetectionUtil.javaVersion() >= 7;
    }

    public List<ChannelBuffer> decompose(int index, int length) {
        if (length == 0) {
            return Collections.emptyList();
        }
        if (index + length <= capacity()) {
            int componentId = componentId(index);
            List<ChannelBuffer> slice = new ArrayList<>(this.components.length);
            ChannelBuffer first = this.components[componentId].duplicate();
            first.readerIndex(index - this.indices[componentId]);
            ChannelBuffer buf = first;
            int bytesToSlice = length;
            while (true) {
                int readableBytes = buf.readableBytes();
                if (bytesToSlice > readableBytes) {
                    slice.add(buf);
                    bytesToSlice -= readableBytes;
                    componentId++;
                    buf = this.components[componentId].duplicate();
                    if (bytesToSlice <= 0) {
                        break;
                    }
                } else {
                    buf.writerIndex(buf.readerIndex() + bytesToSlice);
                    slice.add(buf);
                    break;
                }
            }
            for (int i = 0; i < slice.size(); i++) {
                slice.set(i, slice.get(i).slice());
            }
            return slice;
        }
        throw new IndexOutOfBoundsException("Too many bytes to decompose - Need " + (index + length) + ", capacity is " + capacity());
    }

    private void setComponents(List<ChannelBuffer> newComponents) {
        this.lastAccessedComponentId = 0;
        this.components = new ChannelBuffer[newComponents.size()];
        int i = 0;
        while (true) {
            ChannelBuffer[] channelBufferArr = this.components;
            if (i < channelBufferArr.length) {
                ChannelBuffer c = newComponents.get(i);
                if (c.order() == order()) {
                    this.components[i] = c;
                    i++;
                } else {
                    throw new IllegalArgumentException("All buffers must have the same endianness.");
                }
            } else {
                int[] iArr = new int[(channelBufferArr.length + 1)];
                this.indices = iArr;
                iArr[0] = 0;
                int i2 = 1;
                while (true) {
                    ChannelBuffer[] channelBufferArr2 = this.components;
                    if (i2 <= channelBufferArr2.length) {
                        int[] iArr2 = this.indices;
                        iArr2[i2] = iArr2[i2 - 1] + channelBufferArr2[i2 - 1].capacity();
                        i2++;
                    } else {
                        setIndex(0, capacity());
                        return;
                    }
                }
            }
        }
    }

    private CompositeChannelBuffer(CompositeChannelBuffer buffer) {
        this.order = buffer.order;
        this.gathering = buffer.gathering;
        this.components = (ChannelBuffer[]) buffer.components.clone();
        this.indices = (int[]) buffer.indices.clone();
        setIndex(buffer.readerIndex(), buffer.writerIndex());
    }

    public ChannelBufferFactory factory() {
        return HeapChannelBufferFactory.getInstance(order());
    }

    public ByteOrder order() {
        return this.order;
    }

    public boolean isDirect() {
        return false;
    }

    public boolean hasArray() {
        return false;
    }

    public byte[] array() {
        throw new UnsupportedOperationException();
    }

    public int arrayOffset() {
        throw new UnsupportedOperationException();
    }

    public int capacity() {
        return this.indices[this.components.length];
    }

    public int numComponents() {
        return this.components.length;
    }

    public byte getByte(int index) {
        int componentId = componentId(index);
        return this.components[componentId].getByte(index - this.indices[componentId]);
    }

    public short getShort(int index) {
        int componentId = componentId(index);
        int i = index + 2;
        int[] iArr = this.indices;
        if (i <= iArr[componentId + 1]) {
            return this.components[componentId].getShort(index - iArr[componentId]);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (short) (((getByte(index) & 255) << 8) | (getByte(index + 1) & 255));
        }
        return (short) ((getByte(index) & 255) | ((getByte(index + 1) & 255) << 8));
    }

    public int getUnsignedMedium(int index) {
        int componentId = componentId(index);
        int i = index + 3;
        int[] iArr = this.indices;
        if (i <= iArr[componentId + 1]) {
            return this.components[componentId].getUnsignedMedium(index - iArr[componentId]);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((getShort(index) & UShort.MAX_VALUE) << 8) | (getByte(index + 2) & 255);
        }
        return (getShort(index) & UShort.MAX_VALUE) | ((getByte(index + 2) & 255) << 16);
    }

    public int getInt(int index) {
        int componentId = componentId(index);
        int i = index + 4;
        int[] iArr = this.indices;
        if (i <= iArr[componentId + 1]) {
            return this.components[componentId].getInt(index - iArr[componentId]);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((getShort(index) & UShort.MAX_VALUE) << 16) | (getShort(index + 2) & UShort.MAX_VALUE);
        }
        return (getShort(index) & UShort.MAX_VALUE) | ((getShort(index + 2) & UShort.MAX_VALUE) << 16);
    }

    public long getLong(int index) {
        int componentId = componentId(index);
        int i = index + 8;
        int[] iArr = this.indices;
        if (i <= iArr[componentId + 1]) {
            return this.components[componentId].getLong(index - iArr[componentId]);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((((long) getInt(index)) & 4294967295L) << 32) | (((long) getInt(index + 4)) & 4294967295L);
        }
        return (((long) getInt(index)) & 4294967295L) | ((4294967295L & ((long) getInt(index + 4))) << 32);
    }

    public void getBytes(int index, byte[] dst, int dstIndex, int length) {
        int componentId = componentId(index);
        if (index > capacity() - length || dstIndex > dst.length - length) {
            throw new IndexOutOfBoundsException("Too many bytes to read - Needs " + (index + length) + ", maximum is " + capacity() + " or " + dst.length);
        }
        int i = componentId;
        while (length > 0) {
            ChannelBuffer s = this.components[i];
            int adjustment = this.indices[i];
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
            length -= localLength;
            i++;
        }
    }

    /* JADX INFO: finally extract failed */
    public void getBytes(int index, ByteBuffer dst) {
        int componentId = componentId(index);
        int limit = dst.limit();
        int length = dst.remaining();
        if (index <= capacity() - length) {
            int i = componentId;
            while (length > 0) {
                try {
                    ChannelBuffer s = this.components[i];
                    int adjustment = this.indices[i];
                    int localLength = Math.min(length, s.capacity() - (index - adjustment));
                    dst.limit(dst.position() + localLength);
                    s.getBytes(index - adjustment, dst);
                    index += localLength;
                    length -= localLength;
                    i++;
                } catch (Throwable th) {
                    dst.limit(limit);
                    throw th;
                }
            }
            dst.limit(limit);
            return;
        }
        throw new IndexOutOfBoundsException("Too many bytes to be read - Needs " + (index + length) + ", maximum is " + capacity());
    }

    public void getBytes(int index, ChannelBuffer dst, int dstIndex, int length) {
        int componentId = componentId(index);
        if (index > capacity() - length || dstIndex > dst.capacity() - length) {
            throw new IndexOutOfBoundsException("Too many bytes to be read - Needs " + (index + length) + " or " + (dstIndex + length) + ", maximum is " + capacity() + " or " + dst.capacity());
        }
        int i = componentId;
        while (length > 0) {
            ChannelBuffer s = this.components[i];
            int adjustment = this.indices[i];
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
            length -= localLength;
            i++;
        }
    }

    public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
        if (useGathering()) {
            return (int) out.write(toByteBuffers(index, length));
        }
        return out.write(toByteBuffer(index, length));
    }

    public void getBytes(int index, OutputStream out, int length) throws IOException {
        int componentId = componentId(index);
        if (index <= capacity() - length) {
            int i = componentId;
            while (length > 0) {
                ChannelBuffer s = this.components[i];
                int adjustment = this.indices[i];
                int localLength = Math.min(length, s.capacity() - (index - adjustment));
                s.getBytes(index - adjustment, out, localLength);
                index += localLength;
                length -= localLength;
                i++;
            }
            return;
        }
        throw new IndexOutOfBoundsException("Too many bytes to be read - needs " + (index + length) + ", maximum of " + capacity());
    }

    public void setByte(int index, int value) {
        int componentId = componentId(index);
        this.components[componentId].setByte(index - this.indices[componentId], value);
    }

    public void setShort(int index, int value) {
        int componentId = componentId(index);
        int i = index + 2;
        int[] iArr = this.indices;
        if (i <= iArr[componentId + 1]) {
            this.components[componentId].setShort(index - iArr[componentId], value);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            setByte(index, (byte) (value >>> 8));
            setByte(index + 1, (byte) value);
        } else {
            setByte(index, (byte) value);
            setByte(index + 1, (byte) (value >>> 8));
        }
    }

    public void setMedium(int index, int value) {
        int componentId = componentId(index);
        int i = index + 3;
        int[] iArr = this.indices;
        if (i <= iArr[componentId + 1]) {
            this.components[componentId].setMedium(index - iArr[componentId], value);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            setShort(index, (short) (value >> 8));
            setByte(index + 2, (byte) value);
        } else {
            setShort(index, (short) value);
            setByte(index + 2, (byte) (value >>> 16));
        }
    }

    public void setInt(int index, int value) {
        int componentId = componentId(index);
        int i = index + 4;
        int[] iArr = this.indices;
        if (i <= iArr[componentId + 1]) {
            this.components[componentId].setInt(index - iArr[componentId], value);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            setShort(index, (short) (value >>> 16));
            setShort(index + 2, (short) value);
        } else {
            setShort(index, (short) value);
            setShort(index + 2, (short) (value >>> 16));
        }
    }

    public void setLong(int index, long value) {
        int componentId = componentId(index);
        int i = index + 8;
        int[] iArr = this.indices;
        if (i <= iArr[componentId + 1]) {
            this.components[componentId].setLong(index - iArr[componentId], value);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            setInt(index, (int) (value >>> 32));
            setInt(index + 4, (int) value);
        } else {
            setInt(index, (int) value);
            setInt(index + 4, (int) (value >>> 32));
        }
    }

    public void setBytes(int index, byte[] src2, int srcIndex, int length) {
        int componentId = componentId(index);
        if (index > capacity() - length || srcIndex > src2.length - length) {
            throw new IndexOutOfBoundsException("Too many bytes to read - needs " + (index + length) + " or " + (srcIndex + length) + ", maximum is " + capacity() + " or " + src2.length);
        }
        int i = componentId;
        while (length > 0) {
            ChannelBuffer s = this.components[i];
            int adjustment = this.indices[i];
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.setBytes(index - adjustment, src2, srcIndex, localLength);
            index += localLength;
            srcIndex += localLength;
            length -= localLength;
            i++;
        }
    }

    /* JADX INFO: finally extract failed */
    public void setBytes(int index, ByteBuffer src2) {
        int componentId = componentId(index);
        int limit = src2.limit();
        int length = src2.remaining();
        if (index <= capacity() - length) {
            int i = componentId;
            while (length > 0) {
                try {
                    ChannelBuffer s = this.components[i];
                    int adjustment = this.indices[i];
                    int localLength = Math.min(length, s.capacity() - (index - adjustment));
                    src2.limit(src2.position() + localLength);
                    s.setBytes(index - adjustment, src2);
                    index += localLength;
                    length -= localLength;
                    i++;
                } catch (Throwable th) {
                    src2.limit(limit);
                    throw th;
                }
            }
            src2.limit(limit);
            return;
        }
        throw new IndexOutOfBoundsException("Too many bytes to be written - Needs " + (index + length) + ", maximum is " + capacity());
    }

    public void setBytes(int index, ChannelBuffer src2, int srcIndex, int length) {
        int componentId = componentId(index);
        if (index > capacity() - length || srcIndex > src2.capacity() - length) {
            throw new IndexOutOfBoundsException("Too many bytes to be written - Needs " + (index + length) + " or " + (srcIndex + length) + ", maximum is " + capacity() + " or " + src2.capacity());
        }
        int i = componentId;
        while (length > 0) {
            ChannelBuffer s = this.components[i];
            int adjustment = this.indices[i];
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.setBytes(index - adjustment, src2, srcIndex, localLength);
            index += localLength;
            srcIndex += localLength;
            length -= localLength;
            i++;
        }
    }

    public int setBytes(int index, InputStream in, int length) throws IOException {
        int componentId = componentId(index);
        if (index <= capacity() - length) {
            int i = componentId;
            int readBytes = 0;
            while (true) {
                ChannelBuffer s = this.components[i];
                int adjustment = this.indices[i];
                int localLength = Math.min(length, s.capacity() - (index - adjustment));
                int localReadBytes = s.setBytes(index - adjustment, in, localLength);
                if (localReadBytes >= 0) {
                    if (localReadBytes == localLength) {
                        index += localLength;
                        length -= localLength;
                        readBytes += localLength;
                        i++;
                        continue;
                    } else {
                        index += localReadBytes;
                        length -= localReadBytes;
                        readBytes += localReadBytes;
                        continue;
                    }
                    if (length <= 0) {
                        break;
                    }
                } else if (readBytes == 0) {
                    return -1;
                }
            }
            return readBytes;
        }
        throw new IndexOutOfBoundsException("Too many bytes to write - Needs " + (index + length) + ", maximum is " + capacity());
    }

    public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
        int componentId = componentId(index);
        if (index <= capacity() - length) {
            int i = componentId;
            int readBytes = 0;
            do {
                ChannelBuffer s = this.components[i];
                int adjustment = this.indices[i];
                int localLength = Math.min(length, s.capacity() - (index - adjustment));
                int localReadBytes = s.setBytes(index - adjustment, in, localLength);
                if (localReadBytes == localLength) {
                    index += localLength;
                    length -= localLength;
                    readBytes += localLength;
                    i++;
                    continue;
                } else {
                    index += localReadBytes;
                    length -= localReadBytes;
                    readBytes += localReadBytes;
                    continue;
                }
            } while (length > 0);
            return readBytes;
        }
        throw new IndexOutOfBoundsException("Too many bytes to write - Needs " + (index + length) + ", maximum is " + capacity());
    }

    public ChannelBuffer duplicate() {
        ChannelBuffer duplicate = new CompositeChannelBuffer(this);
        duplicate.setIndex(readerIndex(), writerIndex());
        return duplicate;
    }

    public ChannelBuffer copy(int index, int length) {
        int componentId = componentId(index);
        if (index <= capacity() - length) {
            ChannelBuffer dst = factory().getBuffer(order(), length);
            copyTo(index, length, componentId, dst);
            return dst;
        }
        throw new IndexOutOfBoundsException("Too many bytes to copy - Needs " + (index + length) + ", maximum is " + capacity());
    }

    private void copyTo(int index, int length, int componentId, ChannelBuffer dst) {
        int dstIndex = 0;
        int i = componentId;
        while (length > 0) {
            ChannelBuffer s = this.components[i];
            int adjustment = this.indices[i];
            int localLength = Math.min(length, s.capacity() - (index - adjustment));
            s.getBytes(index - adjustment, dst, dstIndex, localLength);
            index += localLength;
            dstIndex += localLength;
            length -= localLength;
            i++;
        }
        dst.writerIndex(dst.capacity());
    }

    public ChannelBuffer getBuffer(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < capacity()) {
            return this.components[componentId(index)];
        }
        throw new IndexOutOfBoundsException("Invalid index: " + index + " - Bytes needed: " + index + ", maximum is " + capacity());
    }

    public ChannelBuffer slice(int index, int length) {
        if (index == 0) {
            if (length == 0) {
                return ChannelBuffers.EMPTY_BUFFER;
            }
        } else if (index < 0 || index > capacity() - length) {
            throw new IndexOutOfBoundsException("Invalid index: " + index + " - Bytes needed: " + (index + length) + ", maximum is " + capacity());
        } else if (length == 0) {
            return ChannelBuffers.EMPTY_BUFFER;
        }
        List<ChannelBuffer> components2 = decompose(index, length);
        int size = components2.size();
        if (size == 0) {
            return ChannelBuffers.EMPTY_BUFFER;
        }
        if (size != 1) {
            return new CompositeChannelBuffer(order(), components2, this.gathering);
        }
        return components2.get(0);
    }

    public ByteBuffer toByteBuffer(int index, int length) {
        ChannelBuffer[] channelBufferArr = this.components;
        if (channelBufferArr.length == 1) {
            return channelBufferArr[0].toByteBuffer(index, length);
        }
        ByteBuffer[] buffers = toByteBuffers(index, length);
        ByteBuffer merged = ByteBuffer.allocate(length).order(order());
        for (ByteBuffer b : buffers) {
            merged.put(b);
        }
        merged.flip();
        return merged;
    }

    public ByteBuffer[] toByteBuffers(int index, int length) {
        int componentId = componentId(index);
        if (index + length <= capacity()) {
            List<ByteBuffer> buffers = new ArrayList<>(this.components.length);
            int i = componentId;
            while (length > 0) {
                ChannelBuffer s = this.components[i];
                int adjustment = this.indices[i];
                int localLength = Math.min(length, s.capacity() - (index - adjustment));
                buffers.add(s.toByteBuffer(index - adjustment, localLength));
                index += localLength;
                length -= localLength;
                i++;
            }
            return (ByteBuffer[]) buffers.toArray(new ByteBuffer[buffers.size()]);
        }
        throw new IndexOutOfBoundsException("Too many bytes to convert - Needs" + (index + length) + ", maximum is " + capacity());
    }

    private int componentId(int index) {
        int lastComponentId = this.lastAccessedComponentId;
        int[] iArr = this.indices;
        if (index < iArr[lastComponentId]) {
            for (int i = lastComponentId - 1; i >= 0; i--) {
                if (index >= this.indices[i]) {
                    this.lastAccessedComponentId = i;
                    return i;
                }
            }
        } else if (index < iArr[lastComponentId + 1]) {
            return lastComponentId;
        } else {
            for (int i2 = lastComponentId + 1; i2 < this.components.length; i2++) {
                if (index < this.indices[i2 + 1]) {
                    this.lastAccessedComponentId = i2;
                    return i2;
                }
            }
        }
        throw new IndexOutOfBoundsException("Invalid index: " + index + ", maximum: " + this.indices.length);
    }

    public void discardReadBytes() {
        int localReaderIndex = readerIndex();
        if (localReaderIndex != 0) {
            int localWriterIndex = writerIndex();
            List<ChannelBuffer> list = decompose(localReaderIndex, capacity() - localReaderIndex);
            if (list.isEmpty()) {
                list = new ArrayList<>(1);
            }
            ChannelBuffer padding = ChannelBuffers.buffer(order(), localReaderIndex);
            padding.writerIndex(localReaderIndex);
            list.add(padding);
            int localMarkedReaderIndex = localReaderIndex;
            try {
                resetReaderIndex();
                localMarkedReaderIndex = readerIndex();
            } catch (IndexOutOfBoundsException e) {
            }
            int localMarkedWriterIndex = localWriterIndex;
            try {
                resetWriterIndex();
                localMarkedWriterIndex = writerIndex();
            } catch (IndexOutOfBoundsException e2) {
            }
            setComponents(list);
            setIndex(Math.max(localMarkedReaderIndex - localReaderIndex, 0), Math.max(localMarkedWriterIndex - localReaderIndex, 0));
            markReaderIndex();
            markWriterIndex();
            setIndex(0, Math.max(localWriterIndex - localReaderIndex, 0));
        }
    }

    public String toString() {
        String result = super.toString();
        String result2 = result.substring(0, result.length() - 1);
        return result2 + ", components=" + this.components.length + ")";
    }
}
