package org.tensorflow.lite.task.processor;

import java.io.File;
import org.tensorflow.lite.task.processor.SearcherOptions;

final class AutoValue_SearcherOptions extends SearcherOptions {
    private final File indexFile;
    private final boolean l2Normalize;
    private final int maxResults;
    private final boolean quantize;

    private AutoValue_SearcherOptions(boolean l2Normalize2, boolean quantize2, File indexFile2, int maxResults2) {
        this.l2Normalize = l2Normalize2;
        this.quantize = quantize2;
        this.indexFile = indexFile2;
        this.maxResults = maxResults2;
    }

    public boolean getL2Normalize() {
        return this.l2Normalize;
    }

    public boolean getQuantize() {
        return this.quantize;
    }

    public File getIndexFile() {
        return this.indexFile;
    }

    public int getMaxResults() {
        return this.maxResults;
    }

    public String toString() {
        return "SearcherOptions{l2Normalize=" + this.l2Normalize + ", quantize=" + this.quantize + ", indexFile=" + this.indexFile + ", maxResults=" + this.maxResults + "}";
    }

    public boolean equals(Object o) {
        File file;
        if (o == this) {
            return true;
        }
        if (!(o instanceof SearcherOptions)) {
            return false;
        }
        SearcherOptions that = (SearcherOptions) o;
        if (this.l2Normalize == that.getL2Normalize() && this.quantize == that.getQuantize() && ((file = this.indexFile) != null ? file.equals(that.getIndexFile()) : that.getIndexFile() == null) && this.maxResults == that.getMaxResults()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 1231;
        int h$ = ((1 * 1000003) ^ (this.l2Normalize ? 1231 : 1237)) * 1000003;
        if (!this.quantize) {
            i = 1237;
        }
        int h$2 = (h$ ^ i) * 1000003;
        File file = this.indexFile;
        return ((h$2 ^ (file == null ? 0 : file.hashCode())) * 1000003) ^ this.maxResults;
    }

    static final class Builder extends SearcherOptions.Builder {
        private File indexFile;
        private Boolean l2Normalize;
        private Integer maxResults;
        private Boolean quantize;

        Builder() {
        }

        public SearcherOptions.Builder setL2Normalize(boolean l2Normalize2) {
            this.l2Normalize = Boolean.valueOf(l2Normalize2);
            return this;
        }

        public SearcherOptions.Builder setQuantize(boolean quantize2) {
            this.quantize = Boolean.valueOf(quantize2);
            return this;
        }

        public SearcherOptions.Builder setIndexFile(File indexFile2) {
            this.indexFile = indexFile2;
            return this;
        }

        public SearcherOptions.Builder setMaxResults(int maxResults2) {
            this.maxResults = Integer.valueOf(maxResults2);
            return this;
        }

        public SearcherOptions build() {
            String missing = "";
            if (this.l2Normalize == null) {
                missing = missing + " l2Normalize";
            }
            if (this.quantize == null) {
                missing = missing + " quantize";
            }
            if (this.maxResults == null) {
                missing = missing + " maxResults";
            }
            if (missing.isEmpty()) {
                return new AutoValue_SearcherOptions(this.l2Normalize.booleanValue(), this.quantize.booleanValue(), this.indexFile, this.maxResults.intValue());
            }
            throw new IllegalStateException("Missing required properties:" + missing);
        }
    }
}
