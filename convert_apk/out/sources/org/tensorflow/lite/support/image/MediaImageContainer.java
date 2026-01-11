package org.tensorflow.lite.support.image;

import android.graphics.Bitmap;
import android.media.Image;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

final class MediaImageContainer implements ImageContainer {
    private final Image image;

    static MediaImageContainer create(Image image2) {
        return new MediaImageContainer(image2);
    }

    private MediaImageContainer(Image image2) {
        SupportPreconditions.checkNotNull(image2, "Cannot load null Image.");
        SupportPreconditions.checkArgument(image2.getFormat() == 35, "Only supports loading YUV_420_888 Image.");
        this.image = image2;
    }

    public MediaImageContainer clone() {
        throw new UnsupportedOperationException("android.media.Image is an abstract class and cannot be cloned.");
    }

    public Bitmap getBitmap() {
        throw new UnsupportedOperationException("Converting an android.media.Image to Bitmap is not supported.");
    }

    public TensorBuffer getTensorBuffer(DataType dataType) {
        throw new UnsupportedOperationException("Converting an android.media.Image to TesorBuffer is not supported.");
    }

    public Image getMediaImage() {
        return this.image;
    }

    public int getWidth() {
        return this.image.getWidth();
    }

    public int getHeight() {
        return this.image.getHeight();
    }

    public ColorSpaceType getColorSpaceType() {
        return ColorSpaceType.fromImageFormat(this.image.getFormat());
    }
}
