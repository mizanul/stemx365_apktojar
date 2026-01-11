package org.tensorflow.lite.task.vision.segmenter;

final class AutoValue_ColoredLabel extends ColoredLabel {
    private final int argb;
    private final String displayName;
    private final String label;

    AutoValue_ColoredLabel(String label2, String displayName2, int argb2) {
        if (label2 != null) {
            this.label = label2;
            if (displayName2 != null) {
                this.displayName = displayName2;
                this.argb = argb2;
                return;
            }
            throw new NullPointerException("Null displayName");
        }
        throw new NullPointerException("Null label");
    }

    public String getlabel() {
        return this.label;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public int getArgb() {
        return this.argb;
    }

    public String toString() {
        return "ColoredLabel{label=" + this.label + ", displayName=" + this.displayName + ", argb=" + this.argb + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ColoredLabel)) {
            return false;
        }
        ColoredLabel that = (ColoredLabel) o;
        if (!this.label.equals(that.getlabel()) || !this.displayName.equals(that.getDisplayName()) || this.argb != that.getArgb()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((1 * 1000003) ^ this.label.hashCode()) * 1000003) ^ this.displayName.hashCode()) * 1000003) ^ this.argb;
    }
}
