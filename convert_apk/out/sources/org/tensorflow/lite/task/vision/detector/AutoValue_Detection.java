package org.tensorflow.lite.task.vision.detector;

import android.graphics.RectF;
import java.util.List;
import org.tensorflow.lite.support.label.Category;

final class AutoValue_Detection extends Detection {
    private final RectF boundingBox;
    private final List<Category> categories;

    AutoValue_Detection(RectF boundingBox2, List<Category> categories2) {
        if (boundingBox2 != null) {
            this.boundingBox = boundingBox2;
            if (categories2 != null) {
                this.categories = categories2;
                return;
            }
            throw new NullPointerException("Null categories");
        }
        throw new NullPointerException("Null boundingBox");
    }

    public RectF getBoundingBox() {
        return this.boundingBox;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public String toString() {
        return "Detection{boundingBox=" + this.boundingBox + ", categories=" + this.categories + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Detection)) {
            return false;
        }
        Detection that = (Detection) o;
        if (!this.boundingBox.equals(that.getBoundingBox()) || !this.categories.equals(that.getCategories())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.boundingBox.hashCode()) * 1000003) ^ this.categories.hashCode();
    }
}
