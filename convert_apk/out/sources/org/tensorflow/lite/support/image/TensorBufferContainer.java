package org.tensorflow.lite.support.image;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

final class TensorBufferContainer implements ImageContainer {
    private static final String TAG = TensorBufferContainer.class.getSimpleName();
    private final TensorBuffer buffer;
    private final ColorSpaceType colorSpaceType;
    private final int height;
    private final int width;

    static TensorBufferContainer create(TensorBuffer buffer2, ColorSpaceType colorSpaceType2) {
        SupportPreconditions.checkArgument(colorSpaceType2 == ColorSpaceType.RGB || colorSpaceType2 == ColorSpaceType.GRAYSCALE, "Only ColorSpaceType.RGB and ColorSpaceType.GRAYSCALE are supported. Use `create(TensorBuffer, ImageProperties)` for other color space types.");
        return new TensorBufferContainer(buffer2, colorSpaceType2, colorSpaceType2.getHeight(buffer2.getShape()), colorSpaceType2.getWidth(buffer2.getShape()));
    }

    static TensorBufferContainer create(TensorBuffer buffer2, ImageProperties imageProperties) {
        return new TensorBufferContainer(buffer2, imageProperties.getColorSpaceType(), imageProperties.getHeight(), imageProperties.getWidth());
    }

    private TensorBufferContainer(TensorBuffer buffer2, ColorSpaceType colorSpaceType2, int height2, int width2) {
        SupportPreconditions.checkArgument(colorSpaceType2 != ColorSpaceType.YUV_420_888, "The actual encoding format of YUV420 is required. Choose a ColorSpaceType from: NV12, NV21, YV12, YV21. Use YUV_420_888 only when loading an android.media.Image.");
        colorSpaceType2.assertNumElements(buffer2.getFlatSize(), height2, width2);
        this.buffer = buffer2;
        this.colorSpaceType = colorSpaceType2;
        this.height = height2;
        this.width = width2;
    }

    public TensorBufferContainer clone() {
        TensorBuffer tensorBuffer = this.buffer;
        return new TensorBufferContainer(TensorBuffer.createFrom(tensorBuffer, tensorBuffer.getDataType()), this.colorSpaceType, getHeight(), getWidth());
    }

    public Bitmap getBitmap() {
        if (this.buffer.getDataType() != DataType.UINT8) {
            Log.w(TAG, "<Warning> TensorBufferContainer is holding a non-uint8 image. The conversion to Bitmap will cause numeric casting and clamping on the data value.");
        }
        return this.colorSpaceType.convertTensorBufferToBitmap(this.buffer);
    }

    public TensorBuffer getTensorBuffer(DataType dataType) {
        return this.buffer.getDataType() == dataType ? this.buffer : TensorBuffer.createFrom(this.buffer, dataType);
    }

    public Image getMediaImage() {
        throw new UnsupportedOperationException("Converting from TensorBuffer to android.media.Image is unsupported.");
    }

    public int getWidth() {
        this.colorSpaceType.assertNumElements(this.buffer.getFlatSize(), this.height, this.width);
        return this.width;
    }

    public int getHeight() {
        this.colorSpaceType.assertNumElements(this.buffer.getFlatSize(), this.height, this.width);
        return this.height;
    }

    public ColorSpaceType getColorSpaceType() {
        return this.colorSpaceType;
    }
}
