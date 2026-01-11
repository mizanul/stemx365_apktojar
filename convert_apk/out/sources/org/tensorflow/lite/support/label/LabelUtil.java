package org.tensorflow.lite.support.label;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public class LabelUtil {
    public static List<String> mapValueToLabels(TensorBuffer tensorBuffer, List<String> labels, int offset) {
        SupportPreconditions.checkNotNull(tensorBuffer, "Given tensor should not be null");
        SupportPreconditions.checkNotNull(labels, "Given labels should not be null");
        int[] values = tensorBuffer.getIntArray();
        Log.d("values", Arrays.toString(values));
        List<String> result = new ArrayList<>();
        for (int v : values) {
            int index = v + offset;
            if (index < 0 || index >= labels.size()) {
                result.add("");
            } else {
                result.add(labels.get(index));
            }
        }
        return result;
    }

    private LabelUtil() {
    }
}
