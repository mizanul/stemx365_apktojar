package org.tensorflow.lite.support.label.ops;

import android.content.Context;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public class LabelAxisOp {
    private final Map<Integer, List<String>> axisLabels;

    protected LabelAxisOp(Builder builder) {
        this.axisLabels = builder.axisLabels;
    }

    public TensorLabel apply(TensorBuffer buffer) {
        SupportPreconditions.checkNotNull(buffer, "Tensor buffer cannot be null.");
        return new TensorLabel(this.axisLabels, buffer);
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public final Map<Integer, List<String>> axisLabels = new HashMap();

        protected Builder() {
        }

        public Builder addAxisLabel(Context context, int axis, String filePath) throws IOException {
            SupportPreconditions.checkNotNull(context, "Context cannot be null.");
            SupportPreconditions.checkNotNull(filePath, "File path cannot be null.");
            this.axisLabels.put(Integer.valueOf(axis), FileUtil.loadLabels(context, filePath));
            return this;
        }

        public Builder addAxisLabel(int axis, List<String> labels) {
            this.axisLabels.put(Integer.valueOf(axis), labels);
            return this;
        }

        public LabelAxisOp build() {
            return new LabelAxisOp(this);
        }
    }
}
