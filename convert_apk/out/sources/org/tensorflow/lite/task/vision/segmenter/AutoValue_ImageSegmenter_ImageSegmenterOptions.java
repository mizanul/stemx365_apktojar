package org.tensorflow.lite.task.vision.segmenter;

import org.tensorflow.lite.task.core.BaseOptions;
import org.tensorflow.lite.task.vision.segmenter.ImageSegmenter;

final class AutoValue_ImageSegmenter_ImageSegmenterOptions extends ImageSegmenter.ImageSegmenterOptions {
    private final BaseOptions baseOptions;
    private final String displayNamesLocale;
    private final int numThreads;
    private final OutputType outputType;

    private AutoValue_ImageSegmenter_ImageSegmenterOptions(BaseOptions baseOptions2, String displayNamesLocale2, OutputType outputType2, int numThreads2) {
        this.baseOptions = baseOptions2;
        this.displayNamesLocale = displayNamesLocale2;
        this.outputType = outputType2;
        this.numThreads = numThreads2;
    }

    public BaseOptions getBaseOptions() {
        return this.baseOptions;
    }

    public String getDisplayNamesLocale() {
        return this.displayNamesLocale;
    }

    public OutputType getOutputType() {
        return this.outputType;
    }

    public int getNumThreads() {
        return this.numThreads;
    }

    public String toString() {
        return "ImageSegmenterOptions{baseOptions=" + this.baseOptions + ", displayNamesLocale=" + this.displayNamesLocale + ", outputType=" + this.outputType + ", numThreads=" + this.numThreads + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ImageSegmenter.ImageSegmenterOptions)) {
            return false;
        }
        ImageSegmenter.ImageSegmenterOptions that = (ImageSegmenter.ImageSegmenterOptions) o;
        if (!this.baseOptions.equals(that.getBaseOptions()) || !this.displayNamesLocale.equals(that.getDisplayNamesLocale()) || !this.outputType.equals(that.getOutputType()) || this.numThreads != that.getNumThreads()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((((1 * 1000003) ^ this.baseOptions.hashCode()) * 1000003) ^ this.displayNamesLocale.hashCode()) * 1000003) ^ this.outputType.hashCode()) * 1000003) ^ this.numThreads;
    }

    static final class Builder extends ImageSegmenter.ImageSegmenterOptions.Builder {
        private BaseOptions baseOptions;
        private String displayNamesLocale;
        private Integer numThreads;
        private OutputType outputType;

        Builder() {
        }

        public ImageSegmenter.ImageSegmenterOptions.Builder setBaseOptions(BaseOptions baseOptions2) {
            if (baseOptions2 != null) {
                this.baseOptions = baseOptions2;
                return this;
            }
            throw new NullPointerException("Null baseOptions");
        }

        public ImageSegmenter.ImageSegmenterOptions.Builder setDisplayNamesLocale(String displayNamesLocale2) {
            if (displayNamesLocale2 != null) {
                this.displayNamesLocale = displayNamesLocale2;
                return this;
            }
            throw new NullPointerException("Null displayNamesLocale");
        }

        public ImageSegmenter.ImageSegmenterOptions.Builder setOutputType(OutputType outputType2) {
            if (outputType2 != null) {
                this.outputType = outputType2;
                return this;
            }
            throw new NullPointerException("Null outputType");
        }

        public ImageSegmenter.ImageSegmenterOptions.Builder setNumThreads(int numThreads2) {
            this.numThreads = Integer.valueOf(numThreads2);
            return this;
        }

        public ImageSegmenter.ImageSegmenterOptions build() {
            String missing = "";
            if (this.baseOptions == null) {
                missing = missing + " baseOptions";
            }
            if (this.displayNamesLocale == null) {
                missing = missing + " displayNamesLocale";
            }
            if (this.outputType == null) {
                missing = missing + " outputType";
            }
            if (this.numThreads == null) {
                missing = missing + " numThreads";
            }
            if (missing.isEmpty()) {
                return new AutoValue_ImageSegmenter_ImageSegmenterOptions(this.baseOptions, this.displayNamesLocale, this.outputType, this.numThreads.intValue());
            }
            throw new IllegalStateException("Missing required properties:" + missing);
        }
    }
}
