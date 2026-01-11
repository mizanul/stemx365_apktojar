package org.tensorflow.lite.task.core;

import org.tensorflow.lite.task.core.ComputeSettings;

final class AutoValue_ComputeSettings extends ComputeSettings {
    private final ComputeSettings.Delegate delegate;

    private AutoValue_ComputeSettings(ComputeSettings.Delegate delegate2) {
        this.delegate = delegate2;
    }

    public ComputeSettings.Delegate getDelegate() {
        return this.delegate;
    }

    public String toString() {
        return "ComputeSettings{delegate=" + this.delegate + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ComputeSettings) {
            return this.delegate.equals(((ComputeSettings) o).getDelegate());
        }
        return false;
    }

    public int hashCode() {
        return (1 * 1000003) ^ this.delegate.hashCode();
    }

    static final class Builder extends ComputeSettings.Builder {
        private ComputeSettings.Delegate delegate;

        Builder() {
        }

        public ComputeSettings.Builder setDelegate(ComputeSettings.Delegate delegate2) {
            if (delegate2 != null) {
                this.delegate = delegate2;
                return this;
            }
            throw new NullPointerException("Null delegate");
        }

        public ComputeSettings build() {
            String missing = "";
            if (this.delegate == null) {
                missing = missing + " delegate";
            }
            if (missing.isEmpty()) {
                return new AutoValue_ComputeSettings(this.delegate);
            }
            throw new IllegalStateException("Missing required properties:" + missing);
        }
    }
}
