package org.tensorflow.lite.task.core;

import org.tensorflow.lite.task.core.AutoValue_ComputeSettings;

public abstract class ComputeSettings {
    private static final Delegate DEFAULT_DELEGATE = Delegate.NONE;

    public static abstract class Builder {
        public abstract ComputeSettings build();

        public abstract Builder setDelegate(Delegate delegate);
    }

    public abstract Delegate getDelegate();

    public enum Delegate {
        NONE(0),
        NNAPI(1),
        GPU(2);
        
        private final int value;

        private Delegate(int value2) {
            this.value = value2;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static Builder builder() {
        return new AutoValue_ComputeSettings.Builder().setDelegate(DEFAULT_DELEGATE);
    }
}
