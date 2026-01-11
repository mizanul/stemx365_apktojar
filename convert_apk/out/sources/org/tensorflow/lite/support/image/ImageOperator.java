package org.tensorflow.lite.support.image;

import android.graphics.PointF;
import org.tensorflow.lite.support.common.Operator;

public interface ImageOperator extends Operator<TensorImage> {
    TensorImage apply(TensorImage tensorImage);

    int getOutputImageHeight(int i, int i2);

    int getOutputImageWidth(int i, int i2);

    PointF inverseTransform(PointF pointF, int i, int i2);
}
