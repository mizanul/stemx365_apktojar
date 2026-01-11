package org.tensorflow.lite.support.image;

import android.graphics.Bitmap;
import android.media.Image;
import java.nio.ByteBuffer;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public class TensorImage {
    private ImageContainer container;
    private final DataType dataType;

    public TensorImage() {
        this(DataType.UINT8);
    }

    public TensorImage(DataType dataType2) {
        this.container = null;
        SupportPreconditions.checkArgument(dataType2 == DataType.UINT8 || dataType2 == DataType.FLOAT32, "Illegal data type for TensorImage: Only FLOAT32 and UINT8 are accepted");
        this.dataType = dataType2;
    }

    public static TensorImage fromBitmap(Bitmap bitmap) {
        TensorImage image = new TensorImage();
        image.load(bitmap);
        return image;
    }

    public static TensorImage createFrom(TensorImage src2, DataType dataType2) {
        TensorImage dst = new TensorImage(dataType2);
        dst.container = src2.container.clone();
        return dst;
    }

    public void load(Bitmap bitmap) {
        this.container = BitmapContainer.create(bitmap);
    }

    public void load(float[] pixels, int[] shape) {
        TensorBuffer buffer = TensorBuffer.createDynamic(getDataType());
        buffer.loadArray(pixels, shape);
        load(buffer);
    }

    public void load(int[] pixels, int[] shape) {
        TensorBuffer buffer = TensorBuffer.createDynamic(getDataType());
        buffer.loadArray(pixels, shape);
        load(buffer);
    }

    public void load(TensorBuffer buffer) {
        load(buffer, ColorSpaceType.RGB);
    }

    public void load(TensorBuffer buffer, ColorSpaceType colorSpaceType) {
        SupportPreconditions.checkArgument(colorSpaceType == ColorSpaceType.RGB || colorSpaceType == ColorSpaceType.GRAYSCALE, "Only ColorSpaceType.RGB and ColorSpaceType.GRAYSCALE are supported. Use `load(TensorBuffer, ImageProperties)` for other color space types.");
        this.container = TensorBufferContainer.create(buffer, colorSpaceType);
    }

    public void load(TensorBuffer buffer, ImageProperties imageProperties) {
        this.container = TensorBufferContainer.create(buffer, imageProperties);
    }

    public void load(ByteBuffer buffer, ImageProperties imageProperties) {
        TensorBuffer tensorBuffer = TensorBuffer.createDynamic(DataType.UINT8);
        tensorBuffer.loadBuffer(buffer, new int[]{buffer.limit()});
        this.container = TensorBufferContainer.create(tensorBuffer, imageProperties);
    }

    public void load(Image image) {
        this.container = MediaImageContainer.create(image);
    }

    public Bitmap getBitmap() {
        ImageContainer imageContainer = this.container;
        if (imageContainer != null) {
            return imageContainer.getBitmap();
        }
        throw new IllegalStateException("No image has been loaded yet.");
    }

    public ByteBuffer getBuffer() {
        return getTensorBuffer().getBuffer();
    }

    public TensorBuffer getTensorBuffer() {
        ImageContainer imageContainer = this.container;
        if (imageContainer != null) {
            return imageContainer.getTensorBuffer(this.dataType);
        }
        throw new IllegalStateException("No image has been loaded yet.");
    }

    public Image getMediaImage() {
        ImageContainer imageContainer = this.container;
        if (imageContainer != null) {
            return imageContainer.getMediaImage();
        }
        throw new IllegalStateException("No image has been loaded yet.");
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public ColorSpaceType getColorSpaceType() {
        ImageContainer imageContainer = this.container;
        if (imageContainer != null) {
            return imageContainer.getColorSpaceType();
        }
        throw new IllegalStateException("No image has been loaded yet.");
    }

    public int getWidth() {
        ImageContainer imageContainer = this.container;
        if (imageContainer != null) {
            return imageContainer.getWidth();
        }
        throw new IllegalStateException("No image has been loaded yet.");
    }

    public int getHeight() {
        ImageContainer imageContainer = this.container;
        if (imageContainer != null) {
            return imageContainer.getHeight();
        }
        throw new IllegalStateException("No image has been loaded yet.");
    }
}
