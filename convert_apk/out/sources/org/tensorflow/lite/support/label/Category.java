package org.tensorflow.lite.support.label;

import java.util.Objects;

public final class Category {
    private static final int DEFAULT_INDEX = -1;
    private static final float TOLERANCE = 1.0E-6f;
    private final String displayName;
    private final int index;
    private final String label;
    private final float score;

    public static Category create(String label2, String displayName2, float score2, int index2) {
        return new Category(label2, displayName2, score2, index2);
    }

    public static Category create(String label2, String displayName2, float score2) {
        return new Category(label2, displayName2, score2, -1);
    }

    public Category(String label2, float score2) {
        this(label2, "", score2, -1);
    }

    private Category(String label2, String displayName2, float score2, int index2) {
        this.label = label2;
        this.displayName = displayName2;
        this.score = score2;
        this.index = index2;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public float getScore() {
        return this.score;
    }

    public int getIndex() {
        return this.index;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Category)) {
            return false;
        }
        Category other = (Category) o;
        if (!other.getLabel().equals(this.label) || !other.getDisplayName().equals(this.displayName) || Math.abs(other.getScore() - this.score) >= TOLERANCE || other.getIndex() != this.index) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.label, this.displayName, Float.valueOf(this.score), Integer.valueOf(this.index)});
    }

    public String toString() {
        return "<Category \"" + this.label + "\" (displayName=" + this.displayName + " score=" + this.score + " index=" + this.index + ")>";
    }
}
