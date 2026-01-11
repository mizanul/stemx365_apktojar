package org.tensorflow.lite.task.vision.classifier;

import java.util.List;
import org.tensorflow.lite.support.label.Category;

final class AutoValue_Classifications extends Classifications {
    private final List<Category> categories;
    private final int headIndex;

    AutoValue_Classifications(List<Category> categories2, int headIndex2) {
        if (categories2 != null) {
            this.categories = categories2;
            this.headIndex = headIndex2;
            return;
        }
        throw new NullPointerException("Null categories");
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public int getHeadIndex() {
        return this.headIndex;
    }

    public String toString() {
        return "Classifications{categories=" + this.categories + ", headIndex=" + this.headIndex + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Classifications)) {
            return false;
        }
        Classifications that = (Classifications) o;
        if (!this.categories.equals(that.getCategories()) || this.headIndex != that.getHeadIndex()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.categories.hashCode()) * 1000003) ^ this.headIndex;
    }
}
