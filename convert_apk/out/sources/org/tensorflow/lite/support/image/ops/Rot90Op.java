package org.tensorflow.lite.support.image.ops;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.image.ColorSpaceType;
import org.tensorflow.lite.support.image.ImageOperator;
import org.tensorflow.lite.support.image.TensorImage;

public class Rot90Op implements ImageOperator {
    private final int numRotation;

    public Rot90Op() {
        this(1);
    }

    public Rot90Op(int k) {
        this.numRotation = k % 4;
    }

    public TensorImage apply(TensorImage image) {
        boolean z = image.getColorSpaceType() == ColorSpaceType.RGB;
        SupportPreconditions.checkArgument(z, "Only RGB images are supported in Rot90Op, but not " + image.getColorSpaceType().name());
        Bitmap input = image.getBitmap();
        if (this.numRotation == 0) {
            return image;
        }
        int w = input.getWidth();
        int h = input.getHeight();
        Matrix matrix = new Matrix();
        matrix.postTranslate(((float) w) * 0.5f, ((float) h) * 0.5f);
        matrix.postRotate((float) (this.numRotation * -90));
        matrix.postTranslate(((float) (this.numRotation % 2 == 0 ? w : h)) * 0.5f, ((float) (this.numRotation % 2 == 0 ? h : w)) * 0.5f);
        image.load(Bitmap.createBitmap(input, 0, 0, w, h, matrix, false));
        return image;
    }

    public int getOutputImageHeight(int inputImageHeight, int inputImageWidth) {
        return this.numRotation % 2 == 0 ? inputImageHeight : inputImageWidth;
    }

    public int getOutputImageWidth(int inputImageHeight, int inputImageWidth) {
        return this.numRotation % 2 == 0 ? inputImageWidth : inputImageHeight;
    }

    public PointF inverseTransform(PointF point, int inputImageHeight, int inputImageWidth) {
        return transformImpl(point, getOutputImageHeight(inputImageHeight, inputImageWidth), getOutputImageWidth(inputImageHeight, inputImageWidth), (4 - this.numRotation) % 4);
    }

    private static PointF transformImpl(PointF point, int height, int width, int numRotation2) {
        if (numRotation2 == 0) {
            return point;
        }
        if (numRotation2 == 1) {
            return new PointF(point.y, ((float) width) - point.x);
        }
        if (numRotation2 == 2) {
            return new PointF(((float) width) - point.x, ((float) height) - point.y);
        }
        return new PointF(((float) height) - point.y, point.x);
    }
}
