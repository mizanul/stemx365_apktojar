package org.tensorflow.lite.task.vision.segmenter;

import android.graphics.Color;

public abstract class ColoredLabel {
    public abstract int getArgb();

    public abstract String getDisplayName();

    public abstract String getlabel();

    public static ColoredLabel create(String label, String displayName, int argb) {
        return new AutoValue_ColoredLabel(label, displayName, argb);
    }

    public static ColoredLabel create(String label, String displayName, Color color) {
        return new AutoValue_ColoredLabel(label, displayName, color.toArgb());
    }

    public Color getColor() {
        return Color.valueOf(getArgb());
    }
}
