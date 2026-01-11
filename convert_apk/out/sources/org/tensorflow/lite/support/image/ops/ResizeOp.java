package org.tensorflow.lite.support.image.ops;

import android.graphics.Bitmap;
import android.graphics.PointF;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.image.ColorSpaceType;
import org.tensorflow.lite.support.image.ImageOperator;
import org.tensorflow.lite.support.image.TensorImage;

public class ResizeOp implements ImageOperator {
    private final int targetHeight;
    private final int targetWidth;
    private final boolean useBilinear;

    public enum ResizeMethod {
        BILINEAR,
        NEAREST_NEIGHBOR
    }

    public ResizeOp(int targetHeight2, int targetWidth2, ResizeMethod resizeMethod) {
        this.targetHeight = targetHeight2;
        this.targetWidth = targetWidth2;
        this.useBilinear = resizeMethod == ResizeMethod.BILINEAR;
    }

    public TensorImage apply(TensorImage image) {
        boolean z = image.getColorSpaceType() == ColorSpaceType.RGB;
        SupportPreconditions.checkArgument(z, "Only RGB images are supported in ResizeOp, but not " + image.getColorSpaceType().name());
        image.load(Bitmap.createScaledBitmap(image.getBitmap(), this.targetWidth, this.targetHeight, this.useBilinear));
        return image;
    }

    public int getOutputImageHeight(int inputImageHeight, int inputImageWidth) {
        return this.targetHeight;
    }

    public int getOutputImageWidth(int inputImageHeight, int inputImageWidth) {
        return this.targetWidth;
    }

    public PointF inverseTransform(PointF point, int inputImageHeight, int inputImageWidth) {
        return new PointF((point.x * ((float) inputImageWidth)) / ((float) this.targetWidth), (point.y * ((float) inputImageHeight)) / ((float) this.targetHeight));
    }
}
