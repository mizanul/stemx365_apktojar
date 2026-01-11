package org.tensorflow.lite.support.image.ops;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.image.ColorSpaceType;
import org.tensorflow.lite.support.image.ImageOperator;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public class TransformToGrayscaleOp implements ImageOperator {
    private static final float[] BITMAP_RGBA_GRAYSCALE_TRANSFORMATION = {0.299f, 0.587f, 0.114f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    public TensorImage apply(TensorImage image) {
        TensorImage tensorImage = image;
        if (image.getColorSpaceType() == ColorSpaceType.GRAYSCALE) {
            return tensorImage;
        }
        boolean z = image.getColorSpaceType() == ColorSpaceType.RGB;
        SupportPreconditions.checkArgument(z, "Only RGB images are supported in TransformToGrayscaleOp, but not " + image.getColorSpaceType().name());
        int h = image.getHeight();
        int w = image.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(BITMAP_RGBA_GRAYSCALE_TRANSFORMATION);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(image.getBitmap(), 0.0f, 0.0f, paint);
        int[] intValues = new int[(w * h)];
        int[] intValues2 = intValues;
        ColorMatrixColorFilter colorMatrixColorFilter = colorMatrixFilter;
        bmpGrayscale.getPixels(intValues, 0, w, 0, 0, w, h);
        int[] shape = {1, h, w, 1};
        int i = 0;
        while (true) {
            int[] intValues3 = intValues2;
            if (i < intValues3.length) {
                intValues3[i] = (intValues3[i] >> 16) & 255;
                i++;
                intValues2 = intValues3;
            } else {
                TensorBuffer buffer = TensorBuffer.createFixedSize(shape, image.getDataType());
                buffer.loadArray(intValues3, shape);
                tensorImage.load(buffer, ColorSpaceType.GRAYSCALE);
                return tensorImage;
            }
        }
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
