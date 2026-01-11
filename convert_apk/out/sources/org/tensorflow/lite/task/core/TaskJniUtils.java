package org.tensorflow.lite.task.core;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TaskJniUtils {
    public static final long INVALID_POINTER = 0;
    private static final String TAG = TaskJniUtils.class.getSimpleName();

    public interface EmptyHandleProvider {
        long createHandle();
    }

    public interface FdAndOptionsHandleProvider<T> {
        long createHandle(int i, long j, long j2, T t);
    }

    public interface MultipleBuffersHandleProvider {
        long createHandle(ByteBuffer... byteBufferArr);
    }

    private static native long createProtoBaseOptions(int i, int i2);

    public static <T> long createHandleFromFdAndOptions(Context context, final FdAndOptionsHandleProvider<T> provider, String libName, String filePath, final T options) throws IOException {
        final AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd(filePath);
        try {
            long createHandleFromLibrary = createHandleFromLibrary(new EmptyHandleProvider() {
                public long createHandle() {
                    return FdAndOptionsHandleProvider.this.createHandle(assetFileDescriptor.getParcelFileDescriptor().getFd(), assetFileDescriptor.getLength(), assetFileDescriptor.getStartOffset(), options);
                }
            }, libName);
            if (assetFileDescriptor != null) {
                assetFileDescriptor.close();
            }
            return createHandleFromLibrary;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public static long createHandleFromLibrary(EmptyHandleProvider provider, String libName) {
        tryLoadLibrary(libName);
        try {
            return provider.createHandle();
        } catch (RuntimeException e) {
            String errorMessage = "Error getting native address of native library: " + libName;
            Log.e(TAG, errorMessage, e);
            throw new IllegalStateException(errorMessage, e);
        }
    }

    public static long createHandleWithMultipleAssetFilesFromLibrary(Context context, final MultipleBuffersHandleProvider provider, String libName, String... filePaths) throws IOException {
        final MappedByteBuffer[] buffers = new MappedByteBuffer[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            buffers[i] = loadMappedFile(context, filePaths[i]);
        }
        return createHandleFromLibrary(new EmptyHandleProvider() {
            public long createHandle() {
                return MultipleBuffersHandleProvider.this.createHandle(buffers);
            }
        }, libName);
    }

    public static MappedByteBuffer loadMappedFile(Context context, String filePath) throws IOException {
        FileInputStream inputStream;
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(filePath);
        try {
            inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
            MappedByteBuffer map = inputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, fileDescriptor.getStartOffset(), fileDescriptor.getDeclaredLength());
            inputStream.close();
            if (fileDescriptor != null) {
                fileDescriptor.close();
            }
            return map;
        } catch (Throwable th) {
            if (fileDescriptor != null) {
                try {
                    fileDescriptor.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
        throw th;
    }

    public static void tryLoadLibrary(String libName) {
        try {
            System.loadLibrary(libName);
        } catch (UnsatisfiedLinkError e) {
            String errorMessage = "Error loading native library: " + libName;
            Log.e(TAG, errorMessage, e);
            throw new UnsatisfiedLinkError(errorMessage);
        }
    }

    public static long createProtoBaseOptionsHandle(BaseOptions baseOptions) {
        return createProtoBaseOptionsHandleWithLegacyNumThreads(baseOptions, -1);
    }

    public static long createProtoBaseOptionsHandleWithLegacyNumThreads(BaseOptions baseOptions, int legacyNumThreads) {
        return createProtoBaseOptions(baseOptions.getComputeSettings().getDelegate().getValue(), legacyNumThreads == -1 ? baseOptions.getNumThreads() : legacyNumThreads);
    }

    private TaskJniUtils() {
    }
}
