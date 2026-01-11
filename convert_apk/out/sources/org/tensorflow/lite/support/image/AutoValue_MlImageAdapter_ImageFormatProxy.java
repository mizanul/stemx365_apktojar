package org.tensorflow.lite.support.image;

import org.tensorflow.lite.support.image.MlImageAdapter;

final class AutoValue_MlImageAdapter_ImageFormatProxy extends MlImageAdapter.ImageFormatProxy {
    private final ColorSpaceType colorSpaceType;
    private final int imageFormat;

    AutoValue_MlImageAdapter_ImageFormatProxy(ColorSpaceType colorSpaceType2, int imageFormat2) {
        if (colorSpaceType2 != null) {
            this.colorSpaceType = colorSpaceType2;
            this.imageFormat = imageFormat2;
            return;
        }
        throw new NullPointerException("Null colorSpaceType");
    }

    /* access modifiers changed from: package-private */
    public ColorSpaceType getColorSpaceType() {
        return this.colorSpaceType;
    }

    /* access modifiers changed from: package-private */
    public int getImageFormat() {
        return this.imageFormat;
    }

    public String toString() {
        return "ImageFormatProxy{colorSpaceType=" + this.colorSpaceType + ", imageFormat=" + this.imageFormat + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MlImageAdapter.ImageFormatProxy)) {
            return false;
        }
        MlImageAdapter.ImageFormatProxy that = (MlImageAdapter.ImageFormatProxy) o;
        if (!this.colorSpaceType.equals(that.getColorSpaceType()) || this.imageFormat != that.getImageFormat()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.colorSpaceType.hashCode()) * 1000003) ^ this.imageFormat;
    }
}
