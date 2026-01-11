package org.tensorflow.lite.support.common;

import org.tensorflow.lite.support.common.SequentialProcessor;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public class TensorProcessor extends SequentialProcessor<TensorBuffer> {
    private TensorProcessor(Builder builder) {
        super(builder);
    }

    public static class Builder extends SequentialProcessor.Builder<TensorBuffer> {
        public /* bridge */ /* synthetic */ SequentialProcessor.Builder add(Operator operator) {
            return super.add(operator);
        }

        public Builder add(TensorOperator op) {
            super.add(op);
            return this;
        }

        public TensorProcessor build() {
            return new TensorProcessor(this);
        }
    }
}
