package org.tensorflow.lite.support.image;

import android.graphics.Bitmap;
import android.media.Image;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

final class BitmapContainer implements ImageContainer {
    private final Bitmap bitmap;

    static BitmapContainer create(Bitmap bitmap2) {
        return new BitmapContainer(bitmap2);
    }

    private BitmapContainer(Bitmap bitmap2) {
        SupportPreconditions.checkNotNull(bitmap2, "Cannot load null bitmap.");
        SupportPreconditions.checkArgument(bitmap2.getConfig().equals(Bitmap.Config.ARGB_8888), "Only supports loading ARGB_8888 bitmaps.");
        this.bitmap = bitmap2;
    }

    public BitmapContainer clone() {
        Bitmap bitmap2 = this.bitmap;
        return create(bitmap2.copy(bitmap2.getConfig(), this.bitmap.isMutable()));
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public TensorBuffer getTensorBuffer(DataType dataType) {
        TensorBuffer buffer = TensorBuffer.createDynamic(dataType);
        ImageConversions.convertBitmapToTensorBuffer(this.bitmap, buffer);
        return buffer;
    }

    public Image getMediaImage() {
        throw new UnsupportedOperationException("Converting from Bitmap to android.media.Image is unsupported.");
    }

    public int getWidth() {
        return this.bitmap.getWidth();
    }

    public int getHeight() {
        return this.bitmap.getHeight();
    }

    public ColorSpaceType getColorSpaceType() {
        return ColorSpaceType.fromBitmapConfig(this.bitmap.getConfig());
    }
}
