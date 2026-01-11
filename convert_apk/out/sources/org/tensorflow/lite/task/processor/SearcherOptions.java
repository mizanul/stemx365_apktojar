package org.tensorflow.lite.task.processor;

import java.io.File;
import org.tensorflow.lite.task.processor.AutoValue_SearcherOptions;

public abstract class SearcherOptions {
    private static final boolean DEFAULT_L2_NORMALIZE = false;
    private static final int DEFAULT_MAX_RESULTS = 5;
    private static final boolean DEFAULT_QUANTIZE = false;

    public static abstract class Builder {
        public abstract SearcherOptions build();

        public abstract Builder setIndexFile(File file);

        public abstract Builder setL2Normalize(boolean z);

        public abstract Builder setMaxResults(int i);

        public abstract Builder setQuantize(boolean z);
    }

    public abstract File getIndexFile();

    public abstract boolean getL2Normalize();

    public abstract int getMaxResults();

    public abstract boolean getQuantize();

    public static Builder builder() {
        return new AutoValue_SearcherOptions.Builder().setL2Normalize(false).setQuantize(false).setIndexFile((File) null).setMaxResults(5);
    }
}
