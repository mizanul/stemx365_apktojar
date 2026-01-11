package org.tensorflow.lite.task.vision.segmenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.tensorflow.lite.support.image.TensorImage;

public abstract class Segmentation {
    public abstract List<ColoredLabel> getColoredLabels();

    public abstract List<TensorImage> getMasks();

    public abstract OutputType getOutputType();

    static Segmentation create(OutputType outputType, List<TensorImage> masks, List<ColoredLabel> coloredLabels) {
        outputType.assertMasksMatchColoredLabels(masks, coloredLabels);
        return new AutoValue_Segmentation(outputType, Collections.unmodifiableList(new ArrayList(masks)), Collections.unmodifiableList(new ArrayList(coloredLabels)));
    }
}
