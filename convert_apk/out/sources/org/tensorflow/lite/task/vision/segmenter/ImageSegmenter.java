package org.tensorflow.lite.task.vision.segmenter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.os.ParcelFileDescriptor;
import com.google.android.odml.image.MlImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.opencv.videoio.Videoio;
import org.tensorflow.lite.support.image.MlImageAdapter;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.task.core.BaseOptions;
import org.tensorflow.lite.task.core.TaskJniUtils;
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions;
import org.tensorflow.lite.task.vision.core.BaseVisionTaskApi;
import org.tensorflow.lite.task.vision.segmenter.AutoValue_ImageSegmenter_ImageSegmenterOptions;

public final class ImageSegmenter extends BaseVisionTaskApi {
    private static final String IMAGE_SEGMENTER_NATIVE_LIB = "task_vision_jni";
    private static final int OPTIONAL_FD_LENGTH = -1;
    private static final int OPTIONAL_FD_OFFSET = -1;
    private final OutputType outputType;

    private native void deinitJni(long j);

    /* access modifiers changed from: private */
    public static native long initJniWithByteBuffer(ByteBuffer byteBuffer, String str, int i, long j);

    /* access modifiers changed from: private */
    public static native long initJniWithModelFdAndOptions(int i, long j, long j2, String str, int i2, long j3);

    private static native void segmentNative(long j, long j2, List<byte[]> list, int[] iArr, List<ColoredLabel> list2);

    public static ImageSegmenter createFromFile(Context context, String modelPath) throws IOException {
        return createFromFileAndOptions(context, modelPath, ImageSegmenterOptions.builder().build());
    }

    public static ImageSegmenter createFromFile(File modelFile) throws IOException {
        return createFromFileAndOptions(modelFile, ImageSegmenterOptions.builder().build());
    }

    public static ImageSegmenter createFromBuffer(ByteBuffer modelBuffer) {
        return createFromBufferAndOptions(modelBuffer, ImageSegmenterOptions.builder().build());
    }

    public static ImageSegmenter createFromFileAndOptions(Context context, String modelPath, ImageSegmenterOptions options) throws IOException {
        AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd(modelPath);
        try {
            ImageSegmenter createFromModelFdAndOptions = createFromModelFdAndOptions(assetFileDescriptor.getParcelFileDescriptor().getFd(), assetFileDescriptor.getLength(), assetFileDescriptor.getStartOffset(), options);
            if (assetFileDescriptor != null) {
                assetFileDescriptor.close();
            }
            return createFromModelFdAndOptions;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public static ImageSegmenter createFromFileAndOptions(File modelFile, ImageSegmenterOptions options) throws IOException {
        ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(modelFile, Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        try {
            ImageSegmenter createFromModelFdAndOptions = createFromModelFdAndOptions(descriptor.getFd(), -1, -1, options);
            if (descriptor != null) {
                descriptor.close();
            }
            return createFromModelFdAndOptions;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public static ImageSegmenter createFromBufferAndOptions(final ByteBuffer modelBuffer, final ImageSegmenterOptions options) {
        if (modelBuffer.isDirect() || (modelBuffer instanceof MappedByteBuffer)) {
            return new ImageSegmenter(TaskJniUtils.createHandleFromLibrary(new TaskJniUtils.EmptyHandleProvider() {
                public long createHandle() {
                    return ImageSegmenter.initJniWithByteBuffer(modelBuffer, options.getDisplayNamesLocale(), options.getOutputType().getValue(), TaskJniUtils.createProtoBaseOptionsHandleWithLegacyNumThreads(options.getBaseOptions(), options.getNumThreads()));
                }
            }, IMAGE_SEGMENTER_NATIVE_LIB), options.getOutputType());
        }
        throw new IllegalArgumentException("The model buffer should be either a direct ByteBuffer or a MappedByteBuffer.");
    }

    private ImageSegmenter(long nativeHandle, OutputType outputType2) {
        super(nativeHandle);
        this.outputType = outputType2;
    }

    public static abstract class ImageSegmenterOptions {
        private static final String DEFAULT_DISPLAY_NAME_LOCALE = "en";
        private static final OutputType DEFAULT_OUTPUT_TYPE = OutputType.CATEGORY_MASK;
        private static final int NUM_THREADS = -1;

        public static abstract class Builder {
            public abstract ImageSegmenterOptions build();

            public abstract Builder setBaseOptions(BaseOptions baseOptions);

            public abstract Builder setDisplayNamesLocale(String str);

            @Deprecated
            public abstract Builder setNumThreads(int i);

            public abstract Builder setOutputType(OutputType outputType);
        }

        public abstract BaseOptions getBaseOptions();

        public abstract String getDisplayNamesLocale();

        public abstract int getNumThreads();

        public abstract OutputType getOutputType();

        public static Builder builder() {
            return new AutoValue_ImageSegmenter_ImageSegmenterOptions.Builder().setDisplayNamesLocale(DEFAULT_DISPLAY_NAME_LOCALE).setOutputType(DEFAULT_OUTPUT_TYPE).setNumThreads(-1).setBaseOptions(BaseOptions.builder().build());
        }
    }

    public List<Segmentation> segment(TensorImage image) {
        return segment(image, ImageProcessingOptions.builder().build());
    }

    public List<Segmentation> segment(TensorImage image, ImageProcessingOptions options) {
        return (List) run(new BaseVisionTaskApi.InferenceProvider<List<Segmentation>>() {
            public List<Segmentation> run(long frameBufferHandle, int width, int height, ImageProcessingOptions options) {
                return ImageSegmenter.this.segment(frameBufferHandle, options);
            }
        }, image, options);
    }

    public List<Segmentation> segment(MlImage image) {
        return segment(image, ImageProcessingOptions.builder().build());
    }

    public List<Segmentation> segment(MlImage image, ImageProcessingOptions options) {
        image.getInternal().acquire();
        List<Segmentation> result = segment(MlImageAdapter.createTensorImageFrom(image), options);
        image.close();
        return result;
    }

    public List<Segmentation> segment(long frameBufferHandle, ImageProcessingOptions options) {
        checkNotClosed();
        List<byte[]> maskByteArrays = new ArrayList<>();
        List<ColoredLabel> coloredLabels = new ArrayList<>();
        int[] maskShape = new int[2];
        segmentNative(getNativeHandle(), frameBufferHandle, maskByteArrays, maskShape, coloredLabels);
        List<ByteBuffer> maskByteBuffers = new ArrayList<>();
        for (byte[] bytes : maskByteArrays) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            maskByteBuffers.add(byteBuffer);
        }
        OutputType outputType2 = this.outputType;
        return Arrays.asList(new Segmentation[]{Segmentation.create(outputType2, outputType2.createMasksFromBuffer(maskByteBuffers, maskShape), coloredLabels)});
    }

    private static ImageSegmenter createFromModelFdAndOptions(int fileDescriptor, long fileDescriptorLength, long fileDescriptorOffset, ImageSegmenterOptions options) {
        final int i = fileDescriptor;
        final long j = fileDescriptorLength;
        final long j2 = fileDescriptorOffset;
        final ImageSegmenterOptions imageSegmenterOptions = options;
        return new ImageSegmenter(TaskJniUtils.createHandleFromLibrary(new TaskJniUtils.EmptyHandleProvider() {
            public long createHandle() {
                return ImageSegmenter.initJniWithModelFdAndOptions(i, j, j2, imageSegmenterOptions.getDisplayNamesLocale(), imageSegmenterOptions.getOutputType().getValue(), TaskJniUtils.createProtoBaseOptionsHandleWithLegacyNumThreads(imageSegmenterOptions.getBaseOptions(), imageSegmenterOptions.getNumThreads()));
            }
        }, IMAGE_SEGMENTER_NATIVE_LIB), options.getOutputType());
    }

    /* access modifiers changed from: protected */
    public void deinit(long nativeHandle) {
        deinitJni(nativeHandle);
    }
}
