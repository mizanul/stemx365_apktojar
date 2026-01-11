package org.tensorflow.lite.support.image;

import org.tensorflow.lite.support.image.ImageProperties;

final class AutoValue_ImageProperties extends ImageProperties {
    private final ColorSpaceType colorSpaceType;
    private final int height;
    private final int width;

    private AutoValue_ImageProperties(int height2, int width2, ColorSpaceType colorSpaceType2) {
        this.height = height2;
        this.width = width2;
        this.colorSpaceType = colorSpaceType2;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public ColorSpaceType getColorSpaceType() {
        return this.colorSpaceType;
    }

    public String toString() {
        return "ImageProperties{height=" + this.height + ", width=" + this.width + ", colorSpaceType=" + this.colorSpaceType + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ImageProperties)) {
            return false;
        }
        ImageProperties that = (ImageProperties) o;
        if (this.height == that.getHeight() && this.width == that.getWidth() && this.colorSpaceType.equals(that.getColorSpaceType())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((1 * 1000003) ^ this.height) * 1000003) ^ this.width) * 1000003) ^ this.colorSpaceType.hashCode();
    }

    static final class Builder extends ImageProperties.Builder {
        private ColorSpaceType colorSpaceType;
        private Integer height;
        private Integer width;

        Builder() {
        }

        public ImageProperties.Builder setHeight(int height2) {
            this.height = Integer.valueOf(height2);
            return this;
        }

        public ImageProperties.Builder setWidth(int width2) {
            this.width = Integer.valueOf(width2);
            return this;
        }

        public ImageProperties.Builder setColorSpaceType(ColorSpaceType colorSpaceType2) {
            if (colorSpaceType2 != null) {
                this.colorSpaceType = colorSpaceType2;
                return this;
            }
            throw new NullPointerException("Null colorSpaceType");
        }

        /* access modifiers changed from: package-private */
        public ImageProperties autoBuild() {
            String missing = "";
            if (this.height == null) {
                missing = missing + " height";
            }
            if (this.width == null) {
                missing = missing + " width";
            }
            if (this.colorSpaceType == null) {
                missing = missing + " colorSpaceType";
            }
            if (missing.isEmpty()) {
                return new AutoValue_ImageProperties(this.height.intValue(), this.width.intValue(), this.colorSpaceType);
            }
            throw new IllegalStateException("Missing required properties:" + missing);
        }
    }
}
