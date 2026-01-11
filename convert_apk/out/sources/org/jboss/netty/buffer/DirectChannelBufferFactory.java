package org.jboss.netty.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.apache.xmlrpc.serializer.ObjectArraySerializer;

public class DirectChannelBufferFactory extends AbstractChannelBufferFactory {
    private static final DirectChannelBufferFactory INSTANCE_BE = new DirectChannelBufferFactory(ByteOrder.BIG_ENDIAN);
    private static final DirectChannelBufferFactory INSTANCE_LE = new DirectChannelBufferFactory(ByteOrder.LITTLE_ENDIAN);
    private final Object bigEndianLock;
    private final Object littleEndianLock;
    private ChannelBuffer preallocatedBEBuf;
    private int preallocatedBEBufPos;
    private final int preallocatedBufCapacity;
    private ChannelBuffer preallocatedLEBuf;
    private int preallocatedLEBufPos;

    public static ChannelBufferFactory getInstance() {
        return INSTANCE_BE;
    }

    public static ChannelBufferFactory getInstance(ByteOrder defaultEndianness) {
        if (defaultEndianness == ByteOrder.BIG_ENDIAN) {
            return INSTANCE_BE;
        }
        if (defaultEndianness == ByteOrder.LITTLE_ENDIAN) {
            return INSTANCE_LE;
        }
        if (defaultEndianness == null) {
            throw new NullPointerException("defaultEndianness");
        }
        throw new IllegalStateException("Should not reach here");
    }

    public DirectChannelBufferFactory() {
        this(ByteOrder.BIG_ENDIAN);
    }

    public DirectChannelBufferFactory(int preallocatedBufferCapacity) {
        this(ByteOrder.BIG_ENDIAN, preallocatedBufferCapacity);
    }

    public DirectChannelBufferFactory(ByteOrder defaultOrder) {
        this(defaultOrder, 1048576);
    }

    public DirectChannelBufferFactory(ByteOrder defaultOrder, int preallocatedBufferCapacity) {
        super(defaultOrder);
        this.bigEndianLock = new Object();
        this.littleEndianLock = new Object();
        if (preallocatedBufferCapacity > 0) {
            this.preallocatedBufCapacity = preallocatedBufferCapacity;
            return;
        }
        throw new IllegalArgumentException("preallocatedBufCapacity must be greater than 0: " + preallocatedBufferCapacity);
    }

    public ChannelBuffer getBuffer(ByteOrder order, int capacity) {
        ChannelBuffer slice;
        if (order == null) {
            throw new NullPointerException("order");
        } else if (capacity < 0) {
            throw new IllegalArgumentException("capacity: " + capacity);
        } else if (capacity == 0) {
            return ChannelBuffers.EMPTY_BUFFER;
        } else {
            if (capacity >= this.preallocatedBufCapacity) {
                return ChannelBuffers.directBuffer(order, capacity);
            }
            if (order == ByteOrder.BIG_ENDIAN) {
                slice = allocateBigEndianBuffer(capacity);
            } else {
                slice = allocateLittleEndianBuffer(capacity);
            }
            slice.clear();
            return slice;
        }
    }

    public ChannelBuffer getBuffer(ByteOrder order, byte[] array, int offset, int length) {
        if (array == null) {
            throw new NullPointerException(ObjectArraySerializer.ARRAY_TAG);
        } else if (offset < 0) {
            throw new IndexOutOfBoundsException("offset: " + offset);
        } else if (length == 0) {
            return ChannelBuffers.EMPTY_BUFFER;
        } else {
            if (offset + length <= array.length) {
                ChannelBuffer buf = getBuffer(order, length);
                buf.writeBytes(array, offset, length);
                return buf;
            }
            throw new IndexOutOfBoundsException("length: " + length);
        }
    }

    public ChannelBuffer getBuffer(ByteBuffer nioBuffer) {
        if (!nioBuffer.isReadOnly() && nioBuffer.isDirect()) {
            return ChannelBuffers.wrappedBuffer(nioBuffer);
        }
        ChannelBuffer buf = getBuffer(nioBuffer.order(), nioBuffer.remaining());
        int pos = nioBuffer.position();
        buf.writeBytes(nioBuffer);
        nioBuffer.position(pos);
        return buf;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0044, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.jboss.netty.buffer.ChannelBuffer allocateBigEndianBuffer(int r6) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.bigEndianLock
            monitor-enter(r0)
            r1 = 0
            org.jboss.netty.buffer.ChannelBuffer r2 = r5.preallocatedBEBuf     // Catch:{ all -> 0x0045 }
            r3 = 0
            if (r2 != 0) goto L_0x001a
            java.nio.ByteOrder r2 = java.nio.ByteOrder.BIG_ENDIAN     // Catch:{ all -> 0x0045 }
            int r4 = r5.preallocatedBufCapacity     // Catch:{ all -> 0x0045 }
            org.jboss.netty.buffer.ChannelBuffer r2 = org.jboss.netty.buffer.ChannelBuffers.directBuffer(r2, r4)     // Catch:{ all -> 0x0045 }
            r5.preallocatedBEBuf = r2     // Catch:{ all -> 0x0045 }
            org.jboss.netty.buffer.ChannelBuffer r1 = r2.slice(r3, r6)     // Catch:{ all -> 0x0045 }
            r5.preallocatedBEBufPos = r6     // Catch:{ all -> 0x0048 }
            goto L_0x0043
        L_0x001a:
            org.jboss.netty.buffer.ChannelBuffer r2 = r5.preallocatedBEBuf     // Catch:{ all -> 0x0045 }
            int r2 = r2.capacity()     // Catch:{ all -> 0x0045 }
            int r4 = r5.preallocatedBEBufPos     // Catch:{ all -> 0x0045 }
            int r2 = r2 - r4
            if (r2 < r6) goto L_0x0033
            org.jboss.netty.buffer.ChannelBuffer r2 = r5.preallocatedBEBuf     // Catch:{ all -> 0x0045 }
            int r3 = r5.preallocatedBEBufPos     // Catch:{ all -> 0x0045 }
            org.jboss.netty.buffer.ChannelBuffer r1 = r2.slice(r3, r6)     // Catch:{ all -> 0x0045 }
            int r2 = r5.preallocatedBEBufPos     // Catch:{ all -> 0x0048 }
            int r2 = r2 + r6
            r5.preallocatedBEBufPos = r2     // Catch:{ all -> 0x0048 }
            goto L_0x0043
        L_0x0033:
            java.nio.ByteOrder r2 = java.nio.ByteOrder.BIG_ENDIAN     // Catch:{ all -> 0x0045 }
            int r4 = r5.preallocatedBufCapacity     // Catch:{ all -> 0x0045 }
            org.jboss.netty.buffer.ChannelBuffer r2 = org.jboss.netty.buffer.ChannelBuffers.directBuffer(r2, r4)     // Catch:{ all -> 0x0045 }
            r5.preallocatedBEBuf = r2     // Catch:{ all -> 0x0045 }
            org.jboss.netty.buffer.ChannelBuffer r1 = r2.slice(r3, r6)     // Catch:{ all -> 0x0045 }
            r5.preallocatedBEBufPos = r6     // Catch:{ all -> 0x0048 }
        L_0x0043:
            monitor-exit(r0)     // Catch:{ all -> 0x0048 }
            return r1
        L_0x0045:
            r2 = move-exception
        L_0x0046:
            monitor-exit(r0)     // Catch:{ all -> 0x0048 }
            throw r2
        L_0x0048:
            r2 = move-exception
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.buffer.DirectChannelBufferFactory.allocateBigEndianBuffer(int):org.jboss.netty.buffer.ChannelBuffer");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0044, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.jboss.netty.buffer.ChannelBuffer allocateLittleEndianBuffer(int r6) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.littleEndianLock
            monitor-enter(r0)
            r1 = 0
            org.jboss.netty.buffer.ChannelBuffer r2 = r5.preallocatedLEBuf     // Catch:{ all -> 0x0045 }
            r3 = 0
            if (r2 != 0) goto L_0x001a
            java.nio.ByteOrder r2 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ all -> 0x0045 }
            int r4 = r5.preallocatedBufCapacity     // Catch:{ all -> 0x0045 }
            org.jboss.netty.buffer.ChannelBuffer r2 = org.jboss.netty.buffer.ChannelBuffers.directBuffer(r2, r4)     // Catch:{ all -> 0x0045 }
            r5.preallocatedLEBuf = r2     // Catch:{ all -> 0x0045 }
            org.jboss.netty.buffer.ChannelBuffer r1 = r2.slice(r3, r6)     // Catch:{ all -> 0x0045 }
            r5.preallocatedLEBufPos = r6     // Catch:{ all -> 0x0048 }
            goto L_0x0043
        L_0x001a:
            org.jboss.netty.buffer.ChannelBuffer r2 = r5.preallocatedLEBuf     // Catch:{ all -> 0x0045 }
            int r2 = r2.capacity()     // Catch:{ all -> 0x0045 }
            int r4 = r5.preallocatedLEBufPos     // Catch:{ all -> 0x0045 }
            int r2 = r2 - r4
            if (r2 < r6) goto L_0x0033
            org.jboss.netty.buffer.ChannelBuffer r2 = r5.preallocatedLEBuf     // Catch:{ all -> 0x0045 }
            int r3 = r5.preallocatedLEBufPos     // Catch:{ all -> 0x0045 }
            org.jboss.netty.buffer.ChannelBuffer r1 = r2.slice(r3, r6)     // Catch:{ all -> 0x0045 }
            int r2 = r5.preallocatedLEBufPos     // Catch:{ all -> 0x0048 }
            int r2 = r2 + r6
            r5.preallocatedLEBufPos = r2     // Catch:{ all -> 0x0048 }
            goto L_0x0043
        L_0x0033:
            java.nio.ByteOrder r2 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ all -> 0x0045 }
            int r4 = r5.preallocatedBufCapacity     // Catch:{ all -> 0x0045 }
            org.jboss.netty.buffer.ChannelBuffer r2 = org.jboss.netty.buffer.ChannelBuffers.directBuffer(r2, r4)     // Catch:{ all -> 0x0045 }
            r5.preallocatedLEBuf = r2     // Catch:{ all -> 0x0045 }
            org.jboss.netty.buffer.ChannelBuffer r1 = r2.slice(r3, r6)     // Catch:{ all -> 0x0045 }
            r5.preallocatedLEBufPos = r6     // Catch:{ all -> 0x0048 }
        L_0x0043:
            monitor-exit(r0)     // Catch:{ all -> 0x0048 }
            return r1
        L_0x0045:
            r2 = move-exception
        L_0x0046:
            monitor-exit(r0)     // Catch:{ all -> 0x0048 }
            throw r2
        L_0x0048:
            r2 = move-exception
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jboss.netty.buffer.DirectChannelBufferFactory.allocateLittleEndianBuffer(int):org.jboss.netty.buffer.ChannelBuffer");
    }
}
