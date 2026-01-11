package org.tensorflow.lite;

import java.nio.ByteBuffer;

public interface Tensor {
    ByteBuffer asReadOnlyBuffer();

    DataType dataType();

    int index();

    String name();

    int numBytes();

    int numDimensions();

    int numElements();

    QuantizationParams quantizationParams();

    int[] shape();

    int[] shapeSignature();

    public static class QuantizationParams {
        private final float scale;
        private final int zeroPoint;

        public QuantizationParams(float scale2, int zeroPoint2) {
            this.scale = scale2;
            this.zeroPoint = zeroPoint2;
        }

        public float getScale() {
            return this.scale;
        }

        public int getZeroPoint() {
            return this.zeroPoint;
        }
    }
}
