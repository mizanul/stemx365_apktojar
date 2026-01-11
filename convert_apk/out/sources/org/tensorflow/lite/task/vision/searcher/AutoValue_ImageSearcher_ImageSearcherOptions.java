package org.tensorflow.lite.task.vision.searcher;

import org.tensorflow.lite.task.core.BaseOptions;
import org.tensorflow.lite.task.processor.SearcherOptions;
import org.tensorflow.lite.task.vision.searcher.ImageSearcher;

final class AutoValue_ImageSearcher_ImageSearcherOptions extends ImageSearcher.ImageSearcherOptions {
    private final BaseOptions baseOptions;
    private final SearcherOptions searcherOptions;

    private AutoValue_ImageSearcher_ImageSearcherOptions(BaseOptions baseOptions2, SearcherOptions searcherOptions2) {
        this.baseOptions = baseOptions2;
        this.searcherOptions = searcherOptions2;
    }

    /* access modifiers changed from: package-private */
    public BaseOptions getBaseOptions() {
        return this.baseOptions;
    }

    /* access modifiers changed from: package-private */
    public SearcherOptions getSearcherOptions() {
        return this.searcherOptions;
    }

    public String toString() {
        return "ImageSearcherOptions{baseOptions=" + this.baseOptions + ", searcherOptions=" + this.searcherOptions + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ImageSearcher.ImageSearcherOptions)) {
            return false;
        }
        ImageSearcher.ImageSearcherOptions that = (ImageSearcher.ImageSearcherOptions) o;
        if (!this.baseOptions.equals(that.getBaseOptions()) || !this.searcherOptions.equals(that.getSearcherOptions())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.baseOptions.hashCode()) * 1000003) ^ this.searcherOptions.hashCode();
    }

    static final class Builder extends ImageSearcher.ImageSearcherOptions.Builder {
        private BaseOptions baseOptions;
        private SearcherOptions searcherOptions;

        Builder() {
        }

        public ImageSearcher.ImageSearcherOptions.Builder setBaseOptions(BaseOptions baseOptions2) {
            if (baseOptions2 != null) {
                this.baseOptions = baseOptions2;
                return this;
            }
            throw new NullPointerException("Null baseOptions");
        }

        public ImageSearcher.ImageSearcherOptions.Builder setSearcherOptions(SearcherOptions searcherOptions2) {
            if (searcherOptions2 != null) {
                this.searcherOptions = searcherOptions2;
                return this;
            }
            throw new NullPointerException("Null searcherOptions");
        }

        public ImageSearcher.ImageSearcherOptions build() {
            String missing = "";
            if (this.baseOptions == null) {
                missing = missing + " baseOptions";
            }
            if (this.searcherOptions == null) {
                missing = missing + " searcherOptions";
            }
            if (missing.isEmpty()) {
                return new AutoValue_ImageSearcher_ImageSearcherOptions(this.baseOptions, this.searcherOptions);
            }
            throw new IllegalStateException("Missing required properties:" + missing);
        }
    }
}
