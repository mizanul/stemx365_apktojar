package org.tensorflow.lite.support.tensorbuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;

public abstract class TensorBuffer {
    protected ByteBuffer buffer;
    protected int flatSize = -1;
    protected final boolean isDynamic = true;
    protected int[] shape;

    public abstract DataType getDataType();

    public abstract float[] getFloatArray();

    public abstract float getFloatValue(int i);

    public abstract int[] getIntArray();

    public abstract int getIntValue(int i);

    public abstract int getTypeSize();

    public abstract void loadArray(float[] fArr, int[] iArr);

    public abstract void loadArray(int[] iArr, int[] iArr2);

    /* renamed from: org.tensorflow.lite.support.tensorbuffer.TensorBuffer$1 */
    static /* synthetic */ class C10821 {
        static final /* synthetic */ int[] $SwitchMap$org$tensorflow$lite$DataType;

        static {
            int[] iArr = new int[DataType.values().length];
            $SwitchMap$org$tensorflow$lite$DataType = iArr;
            try {
                iArr[DataType.FLOAT32.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$DataType[DataType.UINT8.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static TensorBuffer createFixedSize(int[] shape2, DataType dataType) {
        int i = C10821.$SwitchMap$org$tensorflow$lite$DataType[dataType.ordinal()];
        if (i == 1) {
            return new TensorBufferFloat(shape2);
        }
        if (i == 2) {
            return new TensorBufferUint8(shape2);
        }
        throw new AssertionError("TensorBuffer does not support data type: " + dataType);
    }

    public static TensorBuffer createDynamic(DataType dataType) {
        int i = C10821.$SwitchMap$org$tensorflow$lite$DataType[dataType.ordinal()];
        if (i == 1) {
            return new TensorBufferFloat();
        }
        if (i == 2) {
            return new TensorBufferUint8();
        }
        throw new AssertionError("TensorBuffer does not support data type: " + dataType);
    }

    public static TensorBuffer createFrom(TensorBuffer buffer2, DataType dataType) {
        TensorBuffer result;
        SupportPreconditions.checkNotNull(buffer2, "Cannot create a buffer from null");
        if (buffer2.isDynamic()) {
            result = createDynamic(dataType);
        } else {
            result = createFixedSize(buffer2.shape, dataType);
        }
        if (buffer2.getDataType() == DataType.FLOAT32 && dataType == DataType.FLOAT32) {
            result.loadArray(buffer2.getFloatArray(), buffer2.shape);
        } else {
            result.loadArray(buffer2.getIntArray(), buffer2.shape);
        }
        return result;
    }

    public ByteBuffer getBuffer() {
        return this.buffer;
    }

    public int getFlatSize() {
        assertShapeIsCorrect();
        return this.flatSize;
    }

    public int[] getShape() {
        assertShapeIsCorrect();
        int[] iArr = this.shape;
        return Arrays.copyOf(iArr, iArr.length);
    }

    public boolean isDynamic() {
        return this.isDynamic;
    }

    public void loadArray(int[] src2) {
        loadArray(src2, this.shape);
    }

    public void loadArray(float[] src2) {
        loadArray(src2, this.shape);
    }

    public void loadBuffer(ByteBuffer buffer2, int[] shape2) {
        SupportPreconditions.checkNotNull(buffer2, "Byte buffer cannot be null.");
        SupportPreconditions.checkArgument(isShapeValid(shape2), "Values in TensorBuffer shape should be non-negative.");
        int flatSize2 = computeFlatSize(shape2);
        boolean z = buffer2.limit() == getTypeSize() * flatSize2;
        SupportPreconditions.checkArgument(z, "The size of byte buffer and the shape do not match. Expected: " + (getTypeSize() * flatSize2) + " Actual: " + buffer2.limit());
        if (!this.isDynamic) {
            SupportPreconditions.checkArgument(Arrays.equals(shape2, this.shape));
        }
        this.shape = (int[]) shape2.clone();
        this.flatSize = flatSize2;
        buffer2.rewind();
        this.buffer = buffer2;
    }

    public void loadBuffer(ByteBuffer buffer2) {
        loadBuffer(buffer2, this.shape);
    }

    protected TensorBuffer(int[] shape2) {
        allocateMemory(shape2);
    }

    protected TensorBuffer() {
        allocateMemory(new int[]{0});
    }

    protected static int computeFlatSize(int[] shape2) {
        SupportPreconditions.checkNotNull(shape2, "Shape cannot be null.");
        int prod = 1;
        for (int s : shape2) {
            prod *= s;
        }
        return prod;
    }

    /* access modifiers changed from: protected */
    public void resize(int[] shape2) {
        if (this.isDynamic) {
            allocateMemory(shape2);
            return;
        }
        SupportPreconditions.checkArgument(Arrays.equals(shape2, this.shape));
        this.shape = (int[]) shape2.clone();
    }

    /* access modifiers changed from: protected */
    public synchronized void copyByteBufferIfReadOnly() {
        if (this.buffer.isReadOnly()) {
            ByteBuffer newByteBuffer = ByteBuffer.allocateDirect(this.buffer.capacity());
            newByteBuffer.order(this.buffer.order());
            newByteBuffer.put(this.buffer);
            newByteBuffer.rewind();
            this.buffer = newByteBuffer;
        }
    }

    private void allocateMemory(int[] shape2) {
        SupportPreconditions.checkNotNull(shape2, "TensorBuffer shape cannot be null.");
        SupportPreconditions.checkArgument(isShapeValid(shape2), "Values in TensorBuffer shape should be non-negative.");
        int newFlatSize = computeFlatSize(shape2);
        this.shape = (int[]) shape2.clone();
        if (this.flatSize != newFlatSize) {
            this.flatSize = newFlatSize;
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(getTypeSize() * newFlatSize);
            this.buffer = allocateDirect;
            allocateDirect.order(ByteOrder.nativeOrder());
        }
    }

    private void assertShapeIsCorrect() {
        SupportPreconditions.checkState(this.buffer.limit() == getTypeSize() * computeFlatSize(this.shape), String.format("The size of underlying ByteBuffer (%d) and the shape (%s) do not match. The ByteBuffer may have been changed.", new Object[]{Integer.valueOf(this.buffer.limit()), Arrays.toString(this.shape)}));
    }

    private static boolean isShapeValid(int[] shape2) {
        if (shape2.length == 0) {
            return true;
        }
        for (int s : shape2) {
            if (s < 0) {
                return false;
            }
        }
        return true;
    }
}
