package org.tensorflow.lite.task.vision.detector;

import android.content.Context;
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

public final class ObjectDetector extends BaseVisionTaskApi {
    private static final String OBJECT_DETECTOR_NATIVE_LIB = "task_vision_jni";
    private static final int OPTIONAL_FD_LENGTH = -1;
    private static final int OPTIONAL_FD_OFFSET = -1;

    private native void deinitJni(long j);

    private static native List<Detection> detectNative(long j, long j2);

    /* access modifiers changed from: private */
    public static native long initJniWithByteBuffer(ByteBuffer byteBuffer, ObjectDetectorOptions objectDetectorOptions, long j);

    /* access modifiers changed from: private */
    public static native long initJniWithModelFdAndOptions(int i, long j, long j2, ObjectDetectorOptions objectDetectorOptions, long j3);

    public static ObjectDetector createFromFile(Context context, String modelPath) throws IOException {
        return createFromFileAndOptions(context, modelPath, ObjectDetectorOptions.builder().build());
    }

    public static ObjectDetector createFromFile(File modelFile) throws IOException {
        return createFromFileAndOptions(modelFile, ObjectDetectorOptions.builder().build());
    }

    public static ObjectDetector createFromBuffer(ByteBuffer modelBuffer) {
        return createFromBufferAndOptions(modelBuffer, ObjectDetectorOptions.builder().build());
    }

    public static ObjectDetector createFromFileAndOptions(Context context, String modelPath, ObjectDetectorOptions options) throws IOException {
        return new ObjectDetector(TaskJniUtils.createHandleFromFdAndOptions(context, new TaskJniUtils.FdAndOptionsHandleProvider<ObjectDetectorOptions>() {
            public long createHandle(int fileDescriptor, long fileDescriptorLength, long fileDescriptorOffset, ObjectDetectorOptions options) {
                return ObjectDetector.initJniWithModelFdAndOptions(fileDescriptor, fileDescriptorLength, fileDescriptorOffset, options, TaskJniUtils.createProtoBaseOptionsHandleWithLegacyNumThreads(options.getBaseOptions(), options.getNumThreads()));
            }
        }, OBJECT_DETECTOR_NATIVE_LIB, modelPath, options));
    }

    public static ObjectDetector createFromFileAndOptions(File modelFile, final ObjectDetectorOptions options) throws IOException {
        final ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(modelFile, Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        try {
            ObjectDetector objectDetector = new ObjectDetector(TaskJniUtils.createHandleFromLibrary(new TaskJniUtils.EmptyHandleProvider() {
                public long createHandle() {
                    int fd = descriptor.getFd();
                    ObjectDetectorOptions objectDetectorOptions = options;
                    return ObjectDetector.initJniWithModelFdAndOptions(fd, -1, -1, objectDetectorOptions, TaskJniUtils.createProtoBaseOptionsHandleWithLegacyNumThreads(objectDetectorOptions.getBaseOptions(), options.getNumThreads()));
                }
            }, OBJECT_DETECTOR_NATIVE_LIB));
            if (descriptor != null) {
                descriptor.close();
            }
            return objectDetector;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public static ObjectDetector createFromBufferAndOptions(final ByteBuffer modelBuffer, final ObjectDetectorOptions options) {
        if (modelBuffer.isDirect() || (modelBuffer instanceof MappedByteBuffer)) {
            return new ObjectDetector(TaskJniUtils.createHandleFromLibrary(new TaskJniUtils.EmptyHandleProvider() {
                public long createHandle() {
                    ByteBuffer byteBuffer = modelBuffer;
                    ObjectDetectorOptions objectDetectorOptions = options;
                    return ObjectDetector.initJniWithByteBuffer(byteBuffer, objectDetectorOptions, TaskJniUtils.createProtoBaseOptionsHandleWithLegacyNumThreads(objectDetectorOptions.getBaseOptions(), options.getNumThreads()));
                }
            }, OBJECT_DETECTOR_NATIVE_LIB));
        }
        throw new IllegalArgumentException("The model buffer should be either a direct ByteBuffer or a MappedByteBuffer.");
    }

    private ObjectDetector(long nativeHandle) {
        super(nativeHandle);
    }

    public static class ObjectDetectorOptions {
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
            public BaseOptions baseOptions;
            /* access modifiers changed from: private */
            public String displayNamesLocale;
            /* access modifiers changed from: private */
            public boolean isScoreThresholdSet;
            /* access modifiers changed from: private */
            public List<String> labelAllowList;
            /* access modifiers changed from: private */
            public List<String> labelDenyList;
            /* access modifiers changed from: private */
            public int maxResults;
            /* access modifiers changed from: private */
            public int numThreads;
            /* access modifiers changed from: private */
            public float scoreThreshold;

            private Builder() {
                this.baseOptions = BaseOptions.builder().build();
                this.displayNamesLocale = "en";
                this.maxResults = -1;
                this.isScoreThresholdSet = false;
                this.labelAllowList = new ArrayList();
                this.labelDenyList = new ArrayList();
                this.numThreads = -1;
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

            public ObjectDetectorOptions build() {
                return new ObjectDetectorOptions(this);
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

        private ObjectDetectorOptions(Builder builder) {
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

    public List<Detection> detect(TensorImage image) {
        return detect(image, ImageProcessingOptions.builder().build());
    }

    public List<Detection> detect(TensorImage image, ImageProcessingOptions options) {
        return (List) run(new BaseVisionTaskApi.InferenceProvider<List<Detection>>() {
            public List<Detection> run(long frameBufferHandle, int width, int height, ImageProcessingOptions options) {
                return ObjectDetector.this.detect(frameBufferHandle, options);
            }
        }, image, options);
    }

    public List<Detection> detect(MlImage image) {
        return detect(image, ImageProcessingOptions.builder().build());
    }

    public List<Detection> detect(MlImage image, ImageProcessingOptions options) {
        image.getInternal().acquire();
        List<Detection> result = detect(MlImageAdapter.createTensorImageFrom(image), options);
        image.close();
        return result;
    }

    /* access modifiers changed from: private */
    public List<Detection> detect(long frameBufferHandle, ImageProcessingOptions options) {
        checkNotClosed();
        return detectNative(getNativeHandle(), frameBufferHandle);
    }

    /* access modifiers changed from: protected */
    public void deinit(long nativeHandle) {
        deinitJni(nativeHandle);
    }
}
