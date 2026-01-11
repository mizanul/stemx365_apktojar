package org.tensorflow.lite.task.vision.segmenter;

import java.util.List;
import org.tensorflow.lite.support.image.TensorImage;

final class AutoValue_Segmentation extends Segmentation {
    private final List<ColoredLabel> coloredLabels;
    private final List<TensorImage> masks;
    private final OutputType outputType;

    AutoValue_Segmentation(OutputType outputType2, List<TensorImage> masks2, List<ColoredLabel> coloredLabels2) {
        if (outputType2 != null) {
            this.outputType = outputType2;
            if (masks2 != null) {
                this.masks = masks2;
                if (coloredLabels2 != null) {
                    this.coloredLabels = coloredLabels2;
                    return;
                }
                throw new NullPointerException("Null coloredLabels");
            }
            throw new NullPointerException("Null masks");
        }
        throw new NullPointerException("Null outputType");
    }

    public OutputType getOutputType() {
        return this.outputType;
    }

    public List<TensorImage> getMasks() {
        return this.masks;
    }

    public List<ColoredLabel> getColoredLabels() {
        return this.coloredLabels;
    }

    public String toString() {
        return "Segmentation{outputType=" + this.outputType + ", masks=" + this.masks + ", coloredLabels=" + this.coloredLabels + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Segmentation)) {
            return false;
        }
        Segmentation that = (Segmentation) o;
        if (!this.outputType.equals(that.getOutputType()) || !this.masks.equals(that.getMasks()) || !this.coloredLabels.equals(that.getColoredLabels())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((1 * 1000003) ^ this.outputType.hashCode()) * 1000003) ^ this.masks.hashCode()) * 1000003) ^ this.coloredLabels.hashCode();
    }
}
