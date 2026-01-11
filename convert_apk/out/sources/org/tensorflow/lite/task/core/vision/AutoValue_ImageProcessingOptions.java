package org.tensorflow.lite.task.core.vision;

import android.graphics.Rect;
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions;

final class AutoValue_ImageProcessingOptions extends ImageProcessingOptions {
    private final ImageProcessingOptions.Orientation orientation;
    private final Rect roi;

    private AutoValue_ImageProcessingOptions(Rect roi2, ImageProcessingOptions.Orientation orientation2) {
        this.roi = roi2;
        this.orientation = orientation2;
    }

    public Rect getRoi() {
        return this.roi;
    }

    public ImageProcessingOptions.Orientation getOrientation() {
        return this.orientation;
    }

    public String toString() {
        return "ImageProcessingOptions{roi=" + this.roi + ", orientation=" + this.orientation + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ImageProcessingOptions)) {
            return false;
        }
        ImageProcessingOptions that = (ImageProcessingOptions) o;
        if (!this.roi.equals(that.getRoi()) || !this.orientation.equals(that.getOrientation())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.roi.hashCode()) * 1000003) ^ this.orientation.hashCode();
    }

    static final class Builder extends ImageProcessingOptions.Builder {
        private ImageProcessingOptions.Orientation orientation;
        private Rect roi;

        Builder() {
        }

        public ImageProcessingOptions.Builder setRoi(Rect roi2) {
            if (roi2 != null) {
                this.roi = roi2;
                return this;
            }
            throw new NullPointerException("Null roi");
        }

        /* access modifiers changed from: package-private */
        public Rect getRoi() {
            Rect rect = this.roi;
            if (rect != null) {
                return rect;
            }
            throw new IllegalStateException("Property \"roi\" has not been set");
        }

        public ImageProcessingOptions.Builder setOrientation(ImageProcessingOptions.Orientation orientation2) {
            if (orientation2 != null) {
                this.orientation = orientation2;
                return this;
            }
            throw new NullPointerException("Null orientation");
        }

        /* access modifiers changed from: package-private */
        public ImageProcessingOptions autoBuild() {
            String missing = "";
            if (this.roi == null) {
                missing = missing + " roi";
            }
            if (this.orientation == null) {
                missing = missing + " orientation";
            }
            if (missing.isEmpty()) {
                return new AutoValue_ImageProcessingOptions(this.roi, this.orientation);
            }
            throw new IllegalStateException("Missing required properties:" + missing);
        }
    }
}
