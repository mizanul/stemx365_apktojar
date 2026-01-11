package org.tensorflow.lite.support.label;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public class TensorLabel {
    private final Map<Integer, List<String>> axisLabels;
    private final int[] shape;
    private final TensorBuffer tensorBuffer;

    public TensorLabel(Map<Integer, List<String>> axisLabels2, TensorBuffer tensorBuffer2) {
        SupportPreconditions.checkNotNull(axisLabels2, "Axis labels cannot be null.");
        SupportPreconditions.checkNotNull(tensorBuffer2, "Tensor Buffer cannot be null.");
        this.axisLabels = axisLabels2;
        this.tensorBuffer = tensorBuffer2;
        this.shape = tensorBuffer2.getShape();
        for (Map.Entry<Integer, List<String>> entry : axisLabels2.entrySet()) {
            int axis = entry.getKey().intValue();
            boolean z = true;
            SupportPreconditions.checkArgument(axis >= 0 && axis < this.shape.length, "Invalid axis id: " + axis);
            SupportPreconditions.checkNotNull(entry.getValue(), "Label list is null on axis " + axis);
            if (this.shape[axis] != entry.getValue().size()) {
                z = false;
            }
            SupportPreconditions.checkArgument(z, "Label number " + entry.getValue().size() + " mismatch the shape on axis " + axis);
        }
    }

    public TensorLabel(List<String> axisLabels2, TensorBuffer tensorBuffer2) {
        this(makeMap(getFirstAxisWithSizeGreaterThanOne(tensorBuffer2), axisLabels2), tensorBuffer2);
    }

    public Map<String, TensorBuffer> getMapWithTensorBuffer() {
        TensorLabel tensorLabel = this;
        int labeledAxis = getFirstAxisWithSizeGreaterThanOne(tensorLabel.tensorBuffer);
        Map<String, TensorBuffer> labelToTensorMap = new LinkedHashMap<>();
        SupportPreconditions.checkArgument(tensorLabel.axisLabels.containsKey(Integer.valueOf(labeledAxis)), "get a <String, TensorBuffer> map requires the labels are set on the first non-1 axis.");
        List<String> labels = tensorLabel.axisLabels.get(Integer.valueOf(labeledAxis));
        DataType dataType = tensorLabel.tensorBuffer.getDataType();
        int typeSize = tensorLabel.tensorBuffer.getTypeSize();
        int flatSize = tensorLabel.tensorBuffer.getFlatSize();
        ByteBuffer byteBuffer = tensorLabel.tensorBuffer.getBuffer();
        byteBuffer.rewind();
        int subArrayLength = (flatSize / tensorLabel.shape[labeledAxis]) * typeSize;
        int i = 0;
        SupportPreconditions.checkNotNull(labels, "Label list should never be null");
        for (String label : labels) {
            byteBuffer.position(i * subArrayLength);
            ByteBuffer subBuffer = byteBuffer.slice();
            subBuffer.order(byteBuffer.order()).limit(subArrayLength);
            TensorBuffer labelBuffer = TensorBuffer.createDynamic(dataType);
            int[] iArr = tensorLabel.shape;
            labelBuffer.loadBuffer(subBuffer, Arrays.copyOfRange(iArr, labeledAxis + 1, iArr.length));
            labelToTensorMap.put(label, labelBuffer);
            i++;
            tensorLabel = this;
        }
        return labelToTensorMap;
    }

    public Map<String, Float> getMapWithFloatValue() {
        int labeledAxis = getFirstAxisWithSizeGreaterThanOne(this.tensorBuffer);
        boolean z = true;
        SupportPreconditions.checkState(labeledAxis == this.shape.length - 1, "get a <String, Scalar> map is only valid when the only labeled axis is the last one.");
        List<String> labels = this.axisLabels.get(Integer.valueOf(labeledAxis));
        float[] data = this.tensorBuffer.getFloatArray();
        if (labels.size() != data.length) {
            z = false;
        }
        SupportPreconditions.checkState(z);
        Map<String, Float> result = new LinkedHashMap<>();
        int i = 0;
        for (String label : labels) {
            result.put(label, Float.valueOf(data[i]));
            i++;
        }
        return result;
    }

    public List<Category> getCategoryList() {
        int labeledAxis = getFirstAxisWithSizeGreaterThanOne(this.tensorBuffer);
        boolean z = true;
        SupportPreconditions.checkState(labeledAxis == this.shape.length - 1, "get a Category list is only valid when the only labeled axis is the last one.");
        List<String> labels = this.axisLabels.get(Integer.valueOf(labeledAxis));
        float[] data = this.tensorBuffer.getFloatArray();
        if (labels.size() != data.length) {
            z = false;
        }
        SupportPreconditions.checkState(z);
        List<Category> result = new ArrayList<>();
        int i = 0;
        for (String label : labels) {
            result.add(new Category(label, data[i]));
            i++;
        }
        return result;
    }

    private static int getFirstAxisWithSizeGreaterThanOne(TensorBuffer tensorBuffer2) {
        int[] shape2 = tensorBuffer2.getShape();
        for (int i = 0; i < shape2.length; i++) {
            if (shape2[i] > 1) {
                return i;
            }
        }
        throw new IllegalArgumentException("Cannot find an axis to label. A valid axis to label should have size larger than 1.");
    }

    private static Map<Integer, List<String>> makeMap(int axis, List<String> labels) {
        Map<Integer, List<String>> map = new LinkedHashMap<>();
        map.put(Integer.valueOf(axis), labels);
        return map;
    }
}
