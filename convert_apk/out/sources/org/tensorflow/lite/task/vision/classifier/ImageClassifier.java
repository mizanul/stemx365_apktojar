package org.tensorflow.lite.task.vision.classifier;

import android.content.Context;
import android.graphics.Rect;
import android.os.ParcelFileDescriptor;
import com.google.android.odml.image.MlImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opencv.videoio.Videoio;
import org.tensorflow.lite.support.image.MlImageAdapter;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.task.core.BaseOptions;
import org.tensorflow.lite.task.core.TaskJniUtils;
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions;
import org.tensorflow.lite.task.vision.core.BaseVisionTaskApi;

public final class ImageClassifier extends BaseVisionTaskApi {
    private static final String IMAGE_CLASSIFIER_NATIVE_LIB = "task_vision_jni";
    private static final int OPTIONAL_FD_LENGTH = -1;
    private static final int OPTIONAL_FD_OFFSET = -1;

    private static native List<Classifications> classifyNative(long j, long j2, int[] iArr);

    private native void deinitJni(long j);

    /* access modifiers changed from: private */
    public static native long initJniWithByteBuffer(ByteBuffer byteBuffer, ImageClassifierOptions imageClassifierOptions, long j);

    /* access modifiers changed from: private */
    public static native long initJniWithModelFdAndOptions(int i, long j, long j2, ImageClassifierOptions imageClassifierOptions, long j3);

    public static ImageClassifier createFromFile(Context context, String modelPath) throws IOException {
        return createFromFileAndOptions(context, modelPath, ImageClassifierOptions.builder().build());
    }

    public static ImageClassifier createFromFile(File modelFile) throws IOException {
        return createFromFileAndOptions(modelFile, ImageClassifierOptions.builder().build());
    }

    public static ImageClassifier createFromBuffer(ByteBuffer modelBuffer) {
        return createFromBufferAndOptions(modelBuffer, ImageClassifierOptions.builder().build());
    }

    public static ImageClassifier createFromFileAndOptions(Context context, String modelPath, ImageClassifierOptions options) throws IOException {
        return new ImageClassifier(TaskJniUtils.createHandleFromFdAndOptions(context, new TaskJniUtils.FdAndOptionsHandleProvider<ImageClassifierOptions>() {
            public long createHandle(int fileDescriptor, long fileDescriptorLength, long fileDescriptorOffset, ImageClassifierOptions options) {
                return ImageClassifier.initJniWithModelFdAndOptions(fileDescriptor, fileDescriptorLength, fileDescriptorOffset, options, TaskJniUtils.createProtoBaseOptionsHandleWithLegacyNumThreads(options.getBaseOptions(), options.getNumThreads()));
            }
        }, IMAGE_CLASSIFIER_NATIVE_LIB, modelPath, options));
    }

    public static ImageClassifier createFromFileAndOptions(File modelFile, final ImageClassifierOptions options) throws IOException {
        final ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(modelFile, Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        try {
            ImageClassifier imageClassifier = new ImageClassifier(TaskJniUtils.createHandleFromLibrary(new TaskJniUtils.EmptyHandleProvider() {
                public long createHandle() {
                    int fd = descriptor.getFd();
                    ImageClassifierOptions imageClassifierOptions = options;
                    return ImageClassifier.initJniWithModelFdAndOptions(fd, -1, -1, imageClassifierOptions, TaskJniUtils.createProtoBaseOptionsHandleWithLegacyNumThreads(imageClassifierOptions.getBaseOptions(), options.getNumThreads()));
                }
            }, IMAGE_CLASSIFIER_NATIVE_LIB));
            if (descriptor != null) {
                descriptor.close();
            }
            return imageClassifier;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public static ImageClassifier createFromBufferAndOptions(final ByteBuffer modelBuffer, final ImageClassifierOptions options) {
        if (modelBuffer.isDirect() || (modelBuffer instanceof MappedByteBuffer)) {
            return new ImageClassifier(TaskJniUtils.createHandleFromLibrary(new TaskJniUtils.EmptyHandleProvider() {
                public long createHandle() {
                    ByteBuffer byteBuffer = modelBuffer;
                    ImageClassifierOptions imageClassifierOptions = options;
                    return ImageClassifier.initJniWithByteBuffer(byteBuffer, imageClassifierOptions, TaskJniUtils.createProtoBaseOptionsHandleWithLegacyNumThreads(imageClassifierOptions.getBaseOptions(), options.getNumThreads()));
                }
            }, IMAGE_CLASSIFIER_NATIVE_LIB));
        }
        throw new IllegalArgumentException("The model buffer should be either a direct ByteBuffer or a MappedByteBuffer.");
    }

    ImageClassifier(long nativeHandle) {
        super(nativeHandle);
    }

    public static class ImageClassifierOptions {
        private final BaseOptions baseOptions;
        private final String displayNamesLocale;
        private final boolean isScoreThresholdSet;
        private final List<String> labelAllowList;
        private final List<String> labelDenyList;
        private final int maxResults;
        private final int numThreads;
        private final float scoreThreshold;

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            /* access modifiers changed from: private */
            public BaseOptions baseOptions = BaseOptions.builder().build();
            /* access modifiers changed from: private */
            public String displayNamesLocale = "en";
            /* access modifiers changed from: private */
            public boolean isScoreThresholdSet = false;
            /* access modifiers changed from: private */
            public List<String> labelAllowList = new ArrayList();
            /* access modifiers changed from: private */
            public List<String> labelDenyList = new ArrayList();
            /* access modifiers changed from: private */
            public int maxResults = -1;
            /* access modifiers changed from: private */
            public int numThreads = -1;
            /* access modifiers changed from: private */
            public float scoreThreshold;

            Builder() {
            }

            public Builder setBaseOptions(BaseOptions baseOptions2) {
                this.baseOptions = baseOptions2;
                return this;
            }

            public Builder setDisplayNamesLocale(String displayNamesLocale2) {
                this.displayNamesLocale = displayNamesLocale2;
                return this;
            }

            public Builder setMaxResults(int maxResults2) {
                if (maxResults2 != 0) {
                    this.maxResults = maxResults2;
                    return this;
                }
                throw new IllegalArgumentException("maxResults cannot be 0.");
            }

            public Builder setScoreThreshold(float scoreThreshold2) {
                this.scoreThreshold = scoreThreshold2;
                this.isScoreThresholdSet = true;
                return this;
            }

            public Builder setLabelAllowList(List<String> labelAllowList2) {
                this.labelAllowList = Collections.unmodifiableList(new ArrayList(labelAllowList2));
                return this;
            }

            public Builder setLabelDenyList(List<String> labelDenyList2) {
                this.labelDenyList = Collections.unmodifiableList(new ArrayList(labelDenyList2));
                return this;
            }

            @Deprecated
            public Builder setNumThreads(int numThreads2) {
                this.numThreads = numThreads2;
                return this;
            }

            public ImageClassifierOptions build() {
                return new ImageClassifierOptions(this);
            }
        }

        public String getDisplayNamesLocale() {
            return this.displayNamesLocale;
        }

        public int getMaxResults() {
            return this.maxResults;
        }

        public float getScoreThreshold() {
            return this.scoreThreshold;
        }

        public boolean getIsScoreThresholdSet() {
            return this.isScoreThresholdSet;
        }

        public List<String> getLabelAllowList() {
            return new ArrayList(this.labelAllowList);
        }

        public List<String> getLabelDenyList() {
            return new ArrayList(this.labelDenyList);
        }

        public int getNumThreads() {
            return this.numThreads;
        }

        public BaseOptions getBaseOptions() {
            return this.baseOptions;
        }

        ImageClassifierOptions(Builder builder) {
            this.displayNamesLocale = builder.displayNamesLocale;
            this.maxResults = builder.maxResults;
            this.scoreThreshold = builder.scoreThreshold;
            this.isScoreThresholdSet = builder.isScoreThresholdSet;
            this.labelAllowList = builder.labelAllowList;
            this.labelDenyList = builder.labelDenyList;
            this.numThreads = builder.numThreads;
            this.baseOptions = builder.baseOptions;
        }
    }

    public List<Classifications> classify(TensorImage image) {
        return classify(image, ImageProcessingOptions.builder().build());
    }

    public List<Classifications> classify(TensorImage image, ImageProcessingOptions options) {
        return (List) run(new BaseVisionTaskApi.InferenceProvider<List<Classifications>>() {
            public List<Classifications> run(long frameBufferHandle, int width, int height, ImageProcessingOptions options) {
                return ImageClassifier.this.classify(frameBufferHandle, width, height, options);
            }
        }, image, options);
    }

    public List<Classifications> classify(MlImage image) {
        return classify(image, ImageProcessingOptions.builder().build());
    }

    public List<Classifications> classify(MlImage image, ImageProcessingOptions options) {
        image.getInternal().acquire();
        List<Classifications> result = classify(MlImageAdapter.createTensorImageFrom(image), options);
        image.close();
        return result;
    }

    /* access modifiers changed from: private */
    public List<Classifications> classify(long frameBufferHandle, int width, int height, ImageProcessingOptions options) {
        checkNotClosed();
        Rect roi = options.getRoi().isEmpty() ? new Rect(0, 0, width, height) : options.getRoi();
        return classifyNative(getNativeHandle(), frameBufferHandle, new int[]{roi.left, roi.top, roi.width(), roi.height()});
    }

    /* access modifiers changed from: protected */
    public void deinit(long nativeHandle) {
        deinitJni(nativeHandle);
    }
}
