package org.tensorflow.lite.support.image.ops;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.image.ColorSpaceType;
import org.tensorflow.lite.support.image.ImageOperator;
import org.tensorflow.lite.support.image.TensorImage;

public class ResizeWithCropOrPadOp implements ImageOperator {
    private final Bitmap output;
    private final int targetHeight;
    private final int targetWidth;

    public ResizeWithCropOrPadOp(int targetHeight2, int targetWidth2) {
        this.targetHeight = targetHeight2;
        this.targetWidth = targetWidth2;
        this.output = Bitmap.createBitmap(targetWidth2, targetHeight2, Bitmap.Config.ARGB_8888);
    }

    public TensorImage apply(TensorImage image) {
        int dstR;
        int srcL;
        int dstL;
        int dstL2;
        int dstB;
        int srcT;
        int dstT;
        int dstT2;
        TensorImage tensorImage = image;
        SupportPreconditions.checkArgument(image.getColorSpaceType() == ColorSpaceType.RGB, "Only RGB images are supported in ResizeWithCropOrPadOp, but not " + image.getColorSpaceType().name());
        Bitmap input = image.getBitmap();
        int w = input.getWidth();
        int h = input.getHeight();
        int i = this.targetWidth;
        if (i > w) {
            dstL = 0;
            srcL = w;
            dstL2 = (i - w) / 2;
            dstR = dstL2 + w;
        } else {
            dstR = this.targetWidth;
            int srcL2 = (w - i) / 2;
            int i2 = srcL2;
            srcL = i + srcL2;
            dstL2 = 0;
            dstL = i2;
        }
        int i3 = this.targetHeight;
        if (i3 > h) {
            dstT = 0;
            srcT = h;
            dstT2 = (i3 - h) / 2;
            dstB = dstT2 + h;
        } else {
            dstB = this.targetHeight;
            int srcT2 = (h - i3) / 2;
            int i4 = srcT2;
            srcT = i3 + srcT2;
            dstT2 = 0;
            dstT = i4;
        }
        int i5 = w;
        new Canvas(this.output).drawBitmap(input, new Rect(dstL, dstT, srcL, srcT), new Rect(dstL2, dstT2, dstR, dstB), (Paint) null);
        tensorImage.load(this.output);
        return tensorImage;
    }

    public int getOutputImageHeight(int inputImageHeight, int inputImageWidth) {
        return this.targetHeight;
    }

    public int getOutputImageWidth(int inputImageHeight, int inputImageWidth) {
        return this.targetWidth;
    }

    public PointF inverseTransform(PointF point, int inputImageHeight, int inputImageWidth) {
        return transformImpl(point, this.targetHeight, this.targetWidth, inputImageHeight, inputImageWidth);
    }

    private static PointF transformImpl(PointF point, int srcH, int srcW, int dstH, int dstW) {
        return new PointF(point.x + ((float) ((dstW - srcW) / 2)), point.y + ((float) ((dstH - srcH) / 2)));
    }
}
