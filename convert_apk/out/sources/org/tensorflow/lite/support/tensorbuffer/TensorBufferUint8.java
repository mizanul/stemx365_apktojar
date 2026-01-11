package org.tensorflow.lite.support.tensorbuffer;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;

public final class TensorBufferUint8 extends TensorBuffer {
    private static final DataType DATA_TYPE = DataType.UINT8;

    TensorBufferUint8(int[] shape) {
        super(shape);
    }

    TensorBufferUint8() {
    }

    public DataType getDataType() {
        return DATA_TYPE;
    }

    public float[] getFloatArray() {
        this.buffer.rewind();
        byte[] byteArr = new byte[this.flatSize];
        this.buffer.get(byteArr);
        float[] floatArr = new float[this.flatSize];
        for (int i = 0; i < this.flatSize; i++) {
            floatArr[i] = (float) (byteArr[i] & 255);
        }
        return floatArr;
    }

    public float getFloatValue(int index) {
        return (float) (this.buffer.get(index) & 255);
    }

    public int[] getIntArray() {
        this.buffer.rewind();
        byte[] byteArr = new byte[this.flatSize];
        this.buffer.get(byteArr);
        int[] intArr = new int[this.flatSize];
        for (int i = 0; i < this.flatSize; i++) {
            intArr[i] = byteArr[i] & 255;
        }
        return intArr;
    }

    public int getIntValue(int index) {
        return this.buffer.get(index) & 255;
    }

    public int getTypeSize() {
        return DATA_TYPE.byteSize();
    }

    public void loadArray(float[] src2, int[] shape) {
        SupportPreconditions.checkNotNull(src2, "The array to be loaded cannot be null.");
        int i = 0;
        SupportPreconditions.checkArgument(src2.length == computeFlatSize(shape), "The size of the array to be loaded does not match the specified shape.");
        copyByteBufferIfReadOnly();
        resize(shape);
        this.buffer.rewind();
        byte[] byteArr = new byte[src2.length];
        int cnt = 0;
        int length = src2.length;
        while (i < length) {
            byteArr[cnt] = (byte) ((int) Math.max(Math.min((double) src2[i], 255.0d), 0.0d));
            i++;
            cnt++;
        }
        this.buffer.put(byteArr);
    }

    public void loadArray(int[] src2, int[] shape) {
        SupportPreconditions.checkNotNull(src2, "The array to be loaded cannot be null.");
        int i = 0;
        SupportPreconditions.checkArgument(src2.length == computeFlatSize(shape), "The size of the array to be loaded does not match the specified shape.");
        copyByteBufferIfReadOnly();
        resize(shape);
        this.buffer.rewind();
        byte[] byteArr = new byte[src2.length];
        int cnt = 0;
        int length = src2.length;
        while (i < length) {
            byteArr[cnt] = (byte) ((int) Math.max(Math.min((float) src2[i], 255.0f), 0.0f));
            i++;
            cnt++;
        }
        this.buffer.put(byteArr);
    }
}
