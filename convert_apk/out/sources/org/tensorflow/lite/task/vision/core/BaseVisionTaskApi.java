package org.tensorflow.lite.task.vision.core;

import android.media.Image;
import java.nio.ByteBuffer;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.image.ColorSpaceType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.task.core.BaseTaskApi;
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions;

public abstract class BaseVisionTaskApi extends BaseTaskApi {

    public interface InferenceProvider<T> {
        T run(long j, int i, int i2, ImageProcessingOptions imageProcessingOptions);
    }

    private static native long createFrameBufferFromByteBuffer(ByteBuffer byteBuffer, int i, int i2, int i3, int i4);

    private static native long createFrameBufferFromBytes(byte[] bArr, int i, int i2, int i3, int i4, long[] jArr);

    private static native long createFrameBufferFromPlanes(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3, int i, int i2, int i3, int i4, int i5, int i6);

    private static native void deleteFrameBuffer(long j, long j2, byte[] bArr);

    protected BaseVisionTaskApi(long nativeHandle) {
        super(nativeHandle);
    }

    /* access modifiers changed from: protected */
    public <T> T run(InferenceProvider<T> provider, TensorImage image, ImageProcessingOptions options) {
        FrameBufferData frameBufferData = createFrameBuffer(image, options.getOrientation().getValue());
        T results = provider.run(frameBufferData.getFrameBufferHandle(), image.getWidth(), image.getHeight(), options);
        deleteFrameBuffer(frameBufferData.getFrameBufferHandle(), frameBufferData.getByteArrayHandle(), frameBufferData.getByteArray());
        return results;
    }

    /* renamed from: org.tensorflow.lite.task.vision.core.BaseVisionTaskApi$1 */
    static /* synthetic */ class C10931 {
        static final /* synthetic */ int[] $SwitchMap$org$tensorflow$lite$support$image$ColorSpaceType;

        static {
            int[] iArr = new int[ColorSpaceType.values().length];
            $SwitchMap$org$tensorflow$lite$support$image$ColorSpaceType = iArr;
            try {
                iArr[ColorSpaceType.RGB.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$support$image$ColorSpaceType[ColorSpaceType.NV12.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$support$image$ColorSpaceType[ColorSpaceType.NV21.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$support$image$ColorSpaceType[ColorSpaceType.YV12.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$support$image$ColorSpaceType[ColorSpaceType.YV21.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$support$image$ColorSpaceType[ColorSpaceType.YUV_420_888.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    private static FrameBufferData createFrameBuffer(TensorImage image, int orientation) {
        ColorSpaceType colorSpaceType = image.getColorSpaceType();
        switch (C10931.$SwitchMap$org$tensorflow$lite$support$image$ColorSpaceType[colorSpaceType.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return createFrameBufferFromByteBuffer(image, orientation);
            case 6:
                return createFrameBufferFromMediaImage(image, orientation);
            default:
                throw new IllegalArgumentException("Color space type, " + colorSpaceType.name() + ", is unsupported.");
        }
    }

    private static FrameBufferData createFrameBufferFromMediaImage(TensorImage image, int orientation) {
        Image mediaImage = image.getMediaImage();
        SupportPreconditions.checkArgument(mediaImage.getFormat() == 35, "Only supports loading YUV_420_888 Image.");
        Image.Plane[] planes = mediaImage.getPlanes();
        SupportPreconditions.checkArgument(planes.length == 3, String.format("The input image should have 3 planes, but got %d plane(s).", new Object[]{Integer.valueOf(planes.length)}));
        for (Image.Plane plane : planes) {
            ByteBuffer buffer = plane.getBuffer();
            SupportPreconditions.checkNotNull(buffer, "The image buffer is corrupted and the plane is null.");
            SupportPreconditions.checkArgument(buffer.isDirect(), "The image plane buffer is not a direct ByteBuffer, and is not supported.");
            buffer.rewind();
        }
        return FrameBufferData.create(createFrameBufferFromPlanes(planes[0].getBuffer(), planes[1].getBuffer(), planes[2].getBuffer(), mediaImage.getWidth(), mediaImage.getHeight(), planes[0].getRowStride(), planes[1].getRowStride(), planes[1].getPixelStride(), orientation), 0, new byte[0]);
    }

    private static FrameBufferData createFrameBufferFromByteBuffer(TensorImage image, int orientation) {
        TensorImage imageUint8;
        if (image.getDataType() == DataType.UINT8) {
            imageUint8 = image;
        } else {
            imageUint8 = TensorImage.createFrom(image, DataType.UINT8);
        }
        ByteBuffer byteBuffer = imageUint8.getBuffer();
        byteBuffer.rewind();
        ColorSpaceType colorSpaceType = image.getColorSpaceType();
        if (byteBuffer.isDirect()) {
            return FrameBufferData.create(createFrameBufferFromByteBuffer(byteBuffer, imageUint8.getWidth(), imageUint8.getHeight(), orientation, colorSpaceType.getValue()), 0, new byte[0]);
        }
        long[] byteArrayHandle = new long[1];
        byte[] byteArray = getBytesFromByteBuffer(byteBuffer);
        return FrameBufferData.create(createFrameBufferFromBytes(byteArray, imageUint8.getWidth(), imageUint8.getHeight(), orientation, colorSpaceType.getValue(), byteArrayHandle), byteArrayHandle[0], byteArray);
    }

    static abstract class FrameBufferData {
        /* access modifiers changed from: package-private */
        public abstract byte[] getByteArray();

        /* access modifiers changed from: package-private */
        public abstract long getByteArrayHandle();

        /* access modifiers changed from: package-private */
        public abstract long getFrameBufferHandle();

        FrameBufferData() {
        }

        public static FrameBufferData create(long frameBufferHandle, long byteArrayHandle, byte[] byteArray) {
            return new AutoValue_BaseVisionTaskApi_FrameBufferData(frameBufferHandle, byteArrayHandle, byteArray);
        }
    }

    private static byte[] getBytesFromByteBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray() && byteBuffer.arrayOffset() == 0) {
            return byteBuffer.array();
        }
        byteBuffer.rewind();
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes, 0, bytes.length);
        return bytes;
    }
}
