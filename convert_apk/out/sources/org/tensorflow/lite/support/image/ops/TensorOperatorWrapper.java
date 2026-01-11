package org.tensorflow.lite.support.image.ops;

import android.graphics.PointF;
import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.image.ColorSpaceType;
import org.tensorflow.lite.support.image.ImageOperator;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public class TensorOperatorWrapper implements ImageOperator {
    private final TensorOperator tensorOp;

    public TensorOperatorWrapper(TensorOperator op) {
        this.tensorOp = op;
    }

    public TensorImage apply(TensorImage image) {
        SupportPreconditions.checkNotNull(image, "Op cannot apply on null image.");
        TensorBuffer resBuffer = this.tensorOp.apply(image.getTensorBuffer());
        ColorSpaceType colorSpaceType = image.getColorSpaceType();
        TensorImage resImage = new TensorImage(resBuffer.getDataType());
        resImage.load(resBuffer, colorSpaceType);
        return resImage;
    }

    public int getOutputImageHeight(int inputImageHeight, int inputImageWidth) {
        return inputImageHeight;
    }

    public int getOutputImageWidth(int inputImageHeight, int inputImageWidth) {
        return inputImageWidth;
    }

    public PointF inverseTransform(PointF point, int inputImageHeight, int inputImageWidth) {
        return point;
    }
}
