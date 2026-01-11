package org.tensorflow.lite.support.common.ops;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public class CastOp implements TensorOperator {
    private final DataType destinationType;

    public CastOp(DataType destinationType2) {
        boolean z = destinationType2 == DataType.UINT8 || destinationType2 == DataType.FLOAT32;
        SupportPreconditions.checkArgument(z, "Destination type " + destinationType2 + " is not supported.");
        this.destinationType = destinationType2;
    }

    public TensorBuffer apply(TensorBuffer input) {
        DataType dataType = input.getDataType();
        DataType dataType2 = this.destinationType;
        if (dataType == dataType2) {
            return input;
        }
        return TensorBuffer.createFrom(input, dataType2);
    }
}
