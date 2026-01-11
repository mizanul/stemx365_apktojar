package org.tensorflow.lite.task.core;

import org.tensorflow.lite.task.core.AutoValue_BaseOptions;
import org.tensorflow.lite.task.core.ComputeSettings;

public abstract class BaseOptions {
    private static final int DEFAULT_NUM_THREADS = -1;

    /* access modifiers changed from: package-private */
    public abstract ComputeSettings getComputeSettings();

    /* access modifiers changed from: package-private */
    public abstract int getNumThreads();

    public static abstract class Builder {
        public abstract BaseOptions build();

        public abstract Builder setComputeSettings(ComputeSettings computeSettings);

        public abstract Builder setNumThreads(int i);

        public Builder useGpu() {
            return setComputeSettings(ComputeSettings.builder().setDelegate(ComputeSettings.Delegate.GPU).build());
        }

        public Builder useNnapi() {
            return setComputeSettings(ComputeSettings.builder().setDelegate(ComputeSettings.Delegate.NNAPI).build());
        }
    }

    public static Builder builder() {
        return new AutoValue_BaseOptions.Builder().setComputeSettings(ComputeSettings.builder().build()).setNumThreads(-1);
    }
}
