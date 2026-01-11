package org.tensorflow.lite.task.vision.classifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.tensorflow.lite.support.label.Category;

public abstract class Classifications {
    public abstract List<Category> getCategories();

    public abstract int getHeadIndex();

    static Classifications create(List<Category> categories, int headIndex) {
        return new AutoValue_Classifications(Collections.unmodifiableList(new ArrayList(categories)), headIndex);
    }
}
