package org.tensorflow.lite.task.core;

import org.tensorflow.lite.task.core.BaseOptions;

final class AutoValue_BaseOptions extends BaseOptions {
    private final ComputeSettings computeSettings;
    private final int numThreads;

    private AutoValue_BaseOptions(ComputeSettings computeSettings2, int numThreads2) {
        this.computeSettings = computeSettings2;
        this.numThreads = numThreads2;
    }

    /* access modifiers changed from: package-private */
    public ComputeSettings getComputeSettings() {
        return this.computeSettings;
    }

    /* access modifiers changed from: package-private */
    public int getNumThreads() {
        return this.numThreads;
    }

    public String toString() {
        return "BaseOptions{computeSettings=" + this.computeSettings + ", numThreads=" + this.numThreads + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseOptions)) {
            return false;
        }
        BaseOptions that = (BaseOptions) o;
        if (!this.computeSettings.equals(that.getComputeSettings()) || this.numThreads != that.getNumThreads()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.computeSettings.hashCode()) * 1000003) ^ this.numThreads;
    }

    static final class Builder extends BaseOptions.Builder {
        private ComputeSettings computeSettings;
        private Integer numThreads;

        Builder() {
        }

        public BaseOptions.Builder setComputeSettings(ComputeSettings computeSettings2) {
            if (computeSettings2 != null) {
                this.computeSettings = computeSettings2;
                return this;
            }
            throw new NullPointerException("Null computeSettings");
        }

        public BaseOptions.Builder setNumThreads(int numThreads2) {
            this.numThreads = Integer.valueOf(numThreads2);
            return this;
        }

        public BaseOptions build() {
            String missing = "";
            if (this.computeSettings == null) {
                missing = missing + " computeSettings";
            }
            if (this.numThreads == null) {
                missing = missing + " numThreads";
            }
            if (missing.isEmpty()) {
                return new AutoValue_BaseOptions(this.computeSettings, this.numThreads.intValue());
            }
            throw new IllegalStateException("Missing required properties:" + missing);
        }
    }
}
