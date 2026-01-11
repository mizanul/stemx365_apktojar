package org.tensorflow.lite.task.vision.detector;

import android.graphics.RectF;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.tensorflow.lite.support.label.Category;

public abstract class Detection {
    public abstract RectF getBoundingBox();

    public abstract List<Category> getCategories();

    public static Detection create(RectF boundingBox, List<Category> categories) {
        return new AutoValue_Detection(new RectF(boundingBox), Collections.unmodifiableList(new ArrayList(categories)));
    }
}
