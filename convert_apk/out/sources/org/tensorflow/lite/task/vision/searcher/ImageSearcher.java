package org.tensorflow.lite.task.vision.searcher;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Rect;
import android.os.ParcelFileDescriptor;
import com.google.android.odml.image.MlImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.util.List;
import org.opencv.videoio.Videoio;
import org.tensorflow.lite.support.image.MlImageAdapter;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.task.core.BaseOptions;
import org.tensorflow.lite.task.core.TaskJniUtils;
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions;
import org.tensorflow.lite.task.processor.NearestNeighbor;
import org.tensorflow.lite.task.processor.SearcherOptions;
import org.tensorflow.lite.task.vision.core.BaseVisionTaskApi;
import org.tensorflow.lite.task.vision.searcher.AutoValue_ImageSearcher_ImageSearcherOptions;

public final class ImageSearcher extends BaseVisionTaskApi {
    private static final String IMAGE_SEARCHER_NATIVE_LIB = "task_vision_jni";
    private static final int OPTIONAL_FD_LENGTH = -1;
    private static final int OPTIONAL_FD_OFFSET = -1;

    private native void deinitJni(long j);

    /* access modifiers changed from: private */
    public static native long initJniWithByteBuffer(ByteBuffer byteBuffer, long j, boolean z, boolean z2, int i, int i2);

    /* access modifiers changed from: private */
    public static native long initJniWithModelFdAndOptions(int i, long j, long j2, long j3, boolean z, boolean z2, int i2, int i3);

    private static native List<NearestNeighbor> searchNative(long j, long j2, int[] iArr);

    public static ImageSearcher createFromFileAndOptions(Context context, String modelPath, ImageSearcherOptions options) throws IOException {
        AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd(modelPath);
        try {
            ImageSearcher createFromModelFdAndOptions = createFromModelFdAndOptions(assetFileDescriptor.getParcelFileDescriptor().getFd(), assetFileDescriptor.getLength(), assetFileDescriptor.getStartOffset(), options);
            if (assetFileDescriptor != null) {
                assetFileDescriptor.close();
            }
            return createFromModelFdAndOptions;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public static ImageSearcher createFromFileAndOptions(File modelFile, ImageSearcherOptions options) throws IOException {
        ParcelFileDescriptor descriptor = ParcelFileDescriptor.open(modelFile, Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        try {
            ImageSearcher createFromModelFdAndOptions = createFromModelFdAndOptions(descriptor.getFd(), -1, -1, options);
            if (descriptor != null) {
                descriptor.close();
            }
            return createFromModelFdAndOptions;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public static ImageSearcher createFromBufferAndOptions(ByteBuffer modelBuffer, ImageSearcherOptions options) throws IOException {
        if (!modelBuffer.isDirect() && !(modelBuffer instanceof MappedByteBuffer)) {
            throw new IllegalArgumentException("The model buffer should be either a direct ByteBuffer or a MappedByteBuffer.");
        } else if (options.getSearcherOptions().getIndexFile() == null) {
            return createFromBufferAndOptionsImpl(modelBuffer, options, 0);
        } else {
            ParcelFileDescriptor indexDescriptor = ParcelFileDescriptor.open(options.getSearcherOptions().getIndexFile(), Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
            try {
                ImageSearcher createFromBufferAndOptionsImpl = createFromBufferAndOptionsImpl(modelBuffer, options, indexDescriptor.getFd());
                if (indexDescriptor != null) {
                    indexDescriptor.close();
                }
                return createFromBufferAndOptionsImpl;
            } catch (Throwable th) {
                th.addSuppressed(th);
            }
        }
        throw th;
    }

    public static ImageSearcher createFromBufferAndOptionsImpl(final ByteBuffer modelBuffer, final ImageSearcherOptions options, final int indexFd) {
        return new ImageSearcher(TaskJniUtils.createHandleFromLibrary(new TaskJniUtils.EmptyHandleProvider() {
            public long createHandle() {
                return ImageSearcher.initJniWithByteBuffer(modelBuffer, TaskJniUtils.createProtoBaseOptionsHandle(options.getBaseOptions()), options.getSearcherOptions().getL2Normalize(), options.getSearcherOptions().getQuantize(), indexFd, options.getSearcherOptions().getMaxResults());
            }
        }, IMAGE_SEARCHER_NATIVE_LIB));
    }

    ImageSearcher(long nativeHandle) {
        super(nativeHandle);
    }

    public static abstract class ImageSearcherOptions {

        public static abstract class Builder {
            public abstract ImageSearcherOptions build();

            public abstract Builder setBaseOptions(BaseOptions baseOptions);

            public abstract Builder setSearcherOptions(SearcherOptions searcherOptions);
        }

        /* access modifiers changed from: package-private */
        public abstract BaseOptions getBaseOptions();

        /* access modifiers changed from: package-private */
        public abstract SearcherOptions getSearcherOptions();

        public static Builder builder() {
            return new AutoValue_ImageSearcher_ImageSearcherOptions.Builder().setBaseOptions(BaseOptions.builder().build()).setSearcherOptions(SearcherOptions.builder().build());
        }
    }

    public List<NearestNeighbor> search(TensorImage image) {
        return search(image, ImageProcessingOptions.builder().build());
    }

    public List<NearestNeighbor> search(TensorImage image, ImageProcessingOptions options) {
        return (List) run(new BaseVisionTaskApi.InferenceProvider<List<NearestNeighbor>>() {
            public List<NearestNeighbor> run(long frameBufferHandle, int width, int height, ImageProcessingOptions options) {
                return ImageSearcher.this.search(frameBufferHandle, width, height, options);
            }
        }, image, options);
    }

    public List<NearestNeighbor> search(MlImage image) {
        return search(image, ImageProcessingOptions.builder().build());
    }

    public List<NearestNeighbor> search(MlImage image, ImageProcessingOptions options) {
        image.getInternal().acquire();
        List<NearestNeighbor> result = search(MlImageAdapter.createTensorImageFrom(image), options);
        image.close();
        return result;
    }

    /* access modifiers changed from: private */
    public List<NearestNeighbor> search(long frameBufferHandle, int width, int height, ImageProcessingOptions options) {
        checkNotClosed();
        Rect roi = options.getRoi().isEmpty() ? new Rect(0, 0, width, height) : options.getRoi();
        return searchNative(getNativeHandle(), frameBufferHandle, new int[]{roi.left, roi.top, roi.width(), roi.height()});
    }

    private static ImageSearcher createFromModelFdAndOptions(int modelDescriptor, long modelDescriptorLength, long modelDescriptorOffset, ImageSearcherOptions options) throws IOException {
        if (options.getSearcherOptions().getIndexFile() == null) {
            return createFromModelFdAndOptionsImpl(modelDescriptor, modelDescriptorLength, modelDescriptorOffset, options, 0);
        }
        ParcelFileDescriptor indexDescriptor = ParcelFileDescriptor.open(options.getSearcherOptions().getIndexFile(), Videoio.CAP_INTELPERC_IMAGE_GENERATOR);
        try {
            ImageSearcher createFromModelFdAndOptionsImpl = createFromModelFdAndOptionsImpl(modelDescriptor, modelDescriptorLength, modelDescriptorOffset, options, indexDescriptor.getFd());
            if (indexDescriptor != null) {
                indexDescriptor.close();
            }
            return createFromModelFdAndOptionsImpl;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    private static ImageSearcher createFromModelFdAndOptionsImpl(int modelDescriptor, long modelDescriptorLength, long modelDescriptorOffset, ImageSearcherOptions options, int indexFd) {
        final int i = modelDescriptor;
        final long j = modelDescriptorLength;
        final long j2 = modelDescriptorOffset;
        final ImageSearcherOptions imageSearcherOptions = options;
        final int i2 = indexFd;
        return new ImageSearcher(TaskJniUtils.createHandleFromLibrary(new TaskJniUtils.EmptyHandleProvider() {
            public long createHandle() {
                return ImageSearcher.initJniWithModelFdAndOptions(i, j, j2, TaskJniUtils.createProtoBaseOptionsHandle(imageSearcherOptions.getBaseOptions()), imageSearcherOptions.getSearcherOptions().getL2Normalize(), imageSearcherOptions.getSearcherOptions().getQuantize(), i2, imageSearcherOptions.getSearcherOptions().getMaxResults());
            }
        }, IMAGE_SEARCHER_NATIVE_LIB));
    }

    /* access modifiers changed from: protected */
    public void deinit(long nativeHandle) {
        deinitJni(nativeHandle);
    }
}
