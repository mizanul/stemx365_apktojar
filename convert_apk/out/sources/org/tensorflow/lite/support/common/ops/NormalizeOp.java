package org.tensorflow.lite.support.common.ops;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import org.tensorflow.lite.support.tensorbuffer.TensorBufferFloat;

public class NormalizeOp implements TensorOperator {
    private final boolean isIdentityOp;
    private final float[] mean;
    private final int numChannels;
    private final float[] stddev;

    public NormalizeOp(float mean2, float stddev2) {
        if (mean2 == 0.0f && (stddev2 == 0.0f || Float.isInfinite(stddev2))) {
            stddev2 = 1.0f;
        }
        SupportPreconditions.checkArgument(stddev2 != 0.0f, "Stddev cannot be zero.");
        boolean meansIsZeroAndDevsIs1 = false;
        if (mean2 == 0.0f && stddev2 == 1.0f) {
            meansIsZeroAndDevsIs1 = true;
        }
        this.isIdentityOp = meansIsZeroAndDevsIs1;
        this.mean = new float[]{mean2};
        this.stddev = new float[]{stddev2};
        this.numChannels = 1;
    }

    public NormalizeOp(float[] mean2, float[] stddev2) {
        SupportPreconditions.checkNotNull(mean2, "Mean cannot be null");
        SupportPreconditions.checkNotNull(stddev2, "Stddev cannot be null");
        SupportPreconditions.checkArgument(mean2.length == stddev2.length, "Per channel normalization requires same number of means and stddevs");
        SupportPreconditions.checkArgument(mean2.length > 0, "Means and stddevs are empty.");
        this.mean = (float[]) mean2.clone();
        this.stddev = (float[]) stddev2.clone();
        boolean allMeansAreZeroAndAllDevsAre1 = true;
        this.numChannels = mean2.length;
        for (int i = 0; i < this.numChannels; i++) {
            SupportPreconditions.checkArgument(this.stddev[i] != 0.0f, "Stddev cannot be zero.");
            if (this.stddev[i] != 1.0f || this.mean[i] != 0.0f) {
                allMeansAreZeroAndAllDevsAre1 = false;
            }
        }
        this.isIdentityOp = allMeansAreZeroAndAllDevsAre1;
    }

    public TensorBuffer apply(TensorBuffer input) {
        TensorBuffer output;
        if (this.isIdentityOp) {
            return input;
        }
        int[] shape = input.getShape();
        int i = this.numChannels;
        boolean z = true;
        if (i != 1 && (shape.length == 0 || shape[shape.length - 1] != i)) {
            z = false;
        }
        SupportPreconditions.checkArgument(z, "Number of means (stddevs) is not same with number of channels (size of last axis).");
        float[] values = input.getFloatArray();
        int j = 0;
        for (int i2 = 0; i2 < values.length; i2++) {
            values[i2] = (values[i2] - this.mean[j]) / this.stddev[j];
            j = (j + 1) % this.numChannels;
        }
        if (input.isDynamic() != 0) {
            output = TensorBufferFloat.createDynamic(DataType.FLOAT32);
        } else {
            output = TensorBufferFloat.createFixedSize(shape, DataType.FLOAT32);
        }
        output.loadArray(values, shape);
        return output;
    }
}
