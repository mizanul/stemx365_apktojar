package org.tensorflow.lite.support.image;

import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.image.AutoValue_ImageProperties;

public abstract class ImageProperties {
    private static final int DEFAULT_HEIGHT = -1;
    private static final int DEFAULT_WIDTH = -1;

    public abstract ColorSpaceType getColorSpaceType();

    public abstract int getHeight();

    public abstract int getWidth();

    public static Builder builder() {
        return new AutoValue_ImageProperties.Builder().setHeight(-1).setWidth(-1);
    }

    public static abstract class Builder {
        /* access modifiers changed from: package-private */
        public abstract ImageProperties autoBuild();

        public abstract Builder setColorSpaceType(ColorSpaceType colorSpaceType);

        public abstract Builder setHeight(int i);

        public abstract Builder setWidth(int i);

        public ImageProperties build() {
            ImageProperties properties = autoBuild();
            boolean z = true;
            SupportPreconditions.checkState(properties.getHeight() >= 0, "Negative image height is not allowed.");
            if (properties.getWidth() < 0) {
                z = false;
            }
            SupportPreconditions.checkState(z, "Negative image width is not allowed.");
            return properties;
        }
    }
}
