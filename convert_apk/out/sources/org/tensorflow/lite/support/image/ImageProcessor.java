package org.tensorflow.lite.support.image;

import android.graphics.PointF;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.tensorflow.lite.support.common.Operator;
import org.tensorflow.lite.support.common.SequentialProcessor;
import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.image.ops.Rot90Op;
import org.tensorflow.lite.support.image.ops.TensorOperatorWrapper;

public class ImageProcessor extends SequentialProcessor<TensorImage> {
    private ImageProcessor(Builder builder) {
        super(builder);
    }

    public PointF inverseTransform(PointF point, int inputImageHeight, int inputImageWidth) {
        List<Integer> widths = new ArrayList<>();
        List<Integer> heights = new ArrayList<>();
        int currentWidth = inputImageWidth;
        int currentHeight = inputImageHeight;
        for (Operator<TensorImage> op : this.operatorList) {
            widths.add(Integer.valueOf(currentWidth));
            heights.add(Integer.valueOf(currentHeight));
            ImageOperator imageOperator = (ImageOperator) op;
            int newHeight = imageOperator.getOutputImageHeight(currentHeight, currentWidth);
            int newWidth = imageOperator.getOutputImageWidth(currentHeight, currentWidth);
            currentHeight = newHeight;
            currentWidth = newWidth;
        }
        ListIterator<Operator<TensorImage>> opIterator = this.operatorList.listIterator(this.operatorList.size());
        ListIterator<Integer> widthIterator = widths.listIterator(widths.size());
        ListIterator<Integer> heightIterator = heights.listIterator(heights.size());
        while (opIterator.hasPrevious()) {
            point = ((ImageOperator) opIterator.previous()).inverseTransform(point, heightIterator.previous().intValue(), widthIterator.previous().intValue());
        }
        return point;
    }

    public RectF inverseTransform(RectF rect, int inputImageHeight, int inputImageWidth) {
        PointF p1 = inverseTransform(new PointF(rect.left, rect.top), inputImageHeight, inputImageWidth);
        PointF p2 = inverseTransform(new PointF(rect.right, rect.bottom), inputImageHeight, inputImageWidth);
        return new RectF(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y), Math.max(p1.x, p2.x), Math.max(p1.y, p2.y));
    }

    public TensorImage process(TensorImage image) {
        return (TensorImage) super.process(image);
    }

    public static class Builder extends SequentialProcessor.Builder<TensorImage> {
        public /* bridge */ /* synthetic */ SequentialProcessor.Builder add(Operator operator) {
            return super.add(operator);
        }

        public Builder add(ImageOperator op) {
            super.add(op);
            return this;
        }

        public Builder add(TensorOperator op) {
            return add((ImageOperator) new TensorOperatorWrapper(op));
        }

        public ImageProcessor build() {
            return new ImageProcessor(this);
        }
    }

    public void updateNumberOfRotations(int k) {
        updateNumberOfRotations(k, 0);
    }

    public synchronized void updateNumberOfRotations(int k, int occurrence) {
        SupportPreconditions.checkState(this.operatorIndex.containsKey(Rot90Op.class.getName()), "The Rot90Op has not been added to the ImageProcessor.");
        List<Integer> indexes = (List) this.operatorIndex.get(Rot90Op.class.getName());
        SupportPreconditions.checkElementIndex(occurrence, indexes.size(), "occurrence");
        this.operatorList.set(indexes.get(occurrence).intValue(), new Rot90Op(k));
    }
}
