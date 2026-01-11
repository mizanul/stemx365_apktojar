package org.tensorflow.lite.task.vision.segmenter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.image.ColorSpaceType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public enum OutputType {
    CATEGORY_MASK(0) {
        /* access modifiers changed from: package-private */
        public void assertMasksMatchColoredLabels(List<TensorImage> masks, List<ColoredLabel> list) {
            boolean z = false;
            SupportPreconditions.checkArgument(masks.size() == 1, "CATRGORY_MASK only allows one TensorImage in the list, providing " + masks.size());
            TensorImage mask = masks.get(0);
            if (mask.getColorSpaceType() == ColorSpaceType.GRAYSCALE) {
                z = true;
            }
            SupportPreconditions.checkArgument(z, "CATRGORY_MASK only supports masks of ColorSpaceType, GRAYSCALE, providing " + mask.getColorSpaceType());
        }

        /* access modifiers changed from: package-private */
        public List<TensorImage> createMasksFromBuffer(List<ByteBuffer> buffers, int[] maskShape) {
            boolean z = true;
            if (buffers.size() != 1) {
                z = false;
            }
            SupportPreconditions.checkArgument(z, "CATRGORY_MASK only allows one mask in the buffer list, providing " + buffers.size());
            List<TensorImage> masks = new ArrayList<>();
            TensorBuffer tensorBuffer = TensorBuffer.createDynamic(DataType.UINT8);
            tensorBuffer.loadBuffer(buffers.get(0), maskShape);
            TensorImage tensorImage = new TensorImage(DataType.UINT8);
            tensorImage.load(tensorBuffer, ColorSpaceType.GRAYSCALE);
            masks.add(tensorImage);
            return masks;
        }
    },
    CONFIDENCE_MASK(1) {
        /* access modifiers changed from: package-private */
        public void assertMasksMatchColoredLabels(List<TensorImage> masks, List<ColoredLabel> coloredLabels) {
            SupportPreconditions.checkArgument(masks.size() == coloredLabels.size(), String.format("When using CONFIDENCE_MASK, the number of masks (%d) should match the number of coloredLabels (%d).", new Object[]{Integer.valueOf(masks.size()), Integer.valueOf(coloredLabels.size())}));
            for (TensorImage mask : masks) {
                boolean z = mask.getColorSpaceType() == ColorSpaceType.GRAYSCALE;
                SupportPreconditions.checkArgument(z, "CONFIDENCE_MASK only supports masks of ColorSpaceType, GRAYSCALE, providing " + mask.getColorSpaceType());
            }
        }

        /* access modifiers changed from: package-private */
        public List<TensorImage> createMasksFromBuffer(List<ByteBuffer> buffers, int[] maskShape) {
            List<TensorImage> masks = new ArrayList<>();
            for (ByteBuffer buffer : buffers) {
                TensorBuffer tensorBuffer = TensorBuffer.createDynamic(DataType.FLOAT32);
                tensorBuffer.loadBuffer(buffer, maskShape);
                TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                tensorImage.load(tensorBuffer, ColorSpaceType.GRAYSCALE);
                masks.add(tensorImage);
            }
            return masks;
        }
    };
    
    private final int value;

    /* access modifiers changed from: package-private */
    public abstract void assertMasksMatchColoredLabels(List<TensorImage> list, List<ColoredLabel> list2);

    /* access modifiers changed from: package-private */
    public abstract List<TensorImage> createMasksFromBuffer(List<ByteBuffer> list, int[] iArr);

    public int getValue() {
        return this.value;
    }

    private OutputType(int value2) {
        this.value = value2;
    }
}
