package org.tensorflow.lite.task.core.vision;

import android.graphics.Rect;
import org.tensorflow.lite.task.core.vision.AutoValue_ImageProcessingOptions;

public abstract class ImageProcessingOptions {
    private static final Orientation DEFAULT_ORIENTATION = Orientation.TOP_LEFT;
    private static final Rect defaultRoi = new Rect();

    public abstract Orientation getOrientation();

    public abstract Rect getRoi();

    public enum Orientation {
        TOP_LEFT(0),
        TOP_RIGHT(1),
        BOTTOM_RIGHT(2),
        BOTTOM_LEFT(3),
        LEFT_TOP(4),
        RIGHT_TOP(5),
        RIGHT_BOTTOM(6),
        LEFT_BOTTOM(7);
        
        private final int value;

        private Orientation(int value2) {
            this.value = value2;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static Builder builder() {
        return new AutoValue_ImageProcessingOptions.Builder().setRoi(defaultRoi).setOrientation(DEFAULT_ORIENTATION);
    }

    public static abstract class Builder {
        /* access modifiers changed from: package-private */
        public abstract ImageProcessingOptions autoBuild();

        /* access modifiers changed from: package-private */
        public abstract Rect getRoi();

        public abstract Builder setOrientation(Orientation orientation);

        public abstract Builder setRoi(Rect rect);

        public ImageProcessingOptions build() {
            setRoi(new Rect(getRoi()));
            return autoBuild();
        }
    }
}
