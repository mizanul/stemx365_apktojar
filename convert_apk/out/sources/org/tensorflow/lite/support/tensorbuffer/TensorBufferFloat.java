package org.tensorflow.lite.support.tensorbuffer;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;

public final class TensorBufferFloat extends TensorBuffer {
    private static final DataType DATA_TYPE = DataType.FLOAT32;

    TensorBufferFloat(int[] shape) {
        super(shape);
    }

    TensorBufferFloat() {
    }

    public DataType getDataType() {
        return DATA_TYPE;
    }

    public float[] getFloatArray() {
        this.buffer.rewind();
        float[] arr = new float[this.flatSize];
        this.buffer.asFloatBuffer().get(arr);
        return arr;
    }

    public float getFloatValue(int absIndex) {
        return this.buffer.getFloat(absIndex << 2);
    }

    public int[] getIntArray() {
        this.buffer.rewind();
        float[] floatArr = new float[this.flatSize];
        this.buffer.asFloatBuffer().get(floatArr);
        int[] intArr = new int[this.flatSize];
        for (int i = 0; i < this.flatSize; i++) {
            intArr[i] = (int) floatArr[i];
        }
        return intArr;
    }

    public int getIntValue(int absIndex) {
        return (int) this.buffer.getFloat(absIndex << 2);
    }

    public int getTypeSize() {
        return DATA_TYPE.byteSize();
    }

    public void loadArray(float[] src2, int[] shape) {
        SupportPreconditions.checkNotNull(src2, "The array to be loaded cannot be null.");
        SupportPreconditions.checkArgument(src2.length == computeFlatSize(shape), "The size of the array to be loaded does not match the specified shape.");
        copyByteBufferIfReadOnly();
        resize(shape);
        this.buffer.rewind();
        this.buffer.asFloatBuffer().put(src2);
    }

    public void loadArray(int[] src2, int[] shape) {
        SupportPreconditions.checkNotNull(src2, "The array to be loaded cannot be null.");
        int i = 0;
        SupportPreconditions.checkArgument(src2.length == computeFlatSize(shape), "The size of the array to be loaded does not match the specified shape.");
        copyByteBufferIfReadOnly();
        resize(shape);
        this.buffer.rewind();
        float[] floatArray = new float[src2.length];
        int cnt = 0;
        int length = src2.length;
        while (i < length) {
            floatArray[cnt] = (float) src2[i];
            i++;
            cnt++;
        }
        this.buffer.asFloatBuffer().put(floatArray);
    }
}
