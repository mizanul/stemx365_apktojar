package org.tensorflow.lite.support.image;

import com.google.android.odml.image.BitmapExtractor;
import com.google.android.odml.image.ByteBufferExtractor;
import com.google.android.odml.image.ImageProperties;
import com.google.android.odml.image.MediaImageExtractor;
import com.google.android.odml.image.MlImage;
import java.nio.ByteBuffer;

public class MlImageAdapter {

    static abstract class ImageFormatProxy {
        /* access modifiers changed from: package-private */
        public abstract ColorSpaceType getColorSpaceType();

        /* access modifiers changed from: package-private */
        public abstract int getImageFormat();

        ImageFormatProxy() {
        }

        static ImageFormatProxy createFromImageFormat(int format) {
            switch (format) {
                case 0:
                case 1:
                case 9:
                    throw new IllegalArgumentException("Cannot create ColorSpaceType from MlImage format: " + format);
                case 2:
                    return new AutoValue_MlImageAdapter_ImageFormatProxy(ColorSpaceType.RGB, format);
                case 3:
                    return new AutoValue_MlImageAdapter_ImageFormatProxy(ColorSpaceType.NV12, format);
                case 4:
                    return new AutoValue_MlImageAdapter_ImageFormatProxy(ColorSpaceType.NV21, format);
                case 5:
                    return new AutoValue_MlImageAdapter_ImageFormatProxy(ColorSpaceType.YV12, format);
                case 6:
                    return new AutoValue_MlImageAdapter_ImageFormatProxy(ColorSpaceType.YV21, format);
                case 7:
                    return new AutoValue_MlImageAdapter_ImageFormatProxy(ColorSpaceType.YUV_420_888, format);
                case 8:
                    return new AutoValue_MlImageAdapter_ImageFormatProxy(ColorSpaceType.GRAYSCALE, format);
                default:
                    throw new AssertionError("Illegal @ImageFormat: " + format);
            }
        }
    }

    public static TensorImage createTensorImageFrom(MlImage mlImage) {
        ImageProperties mlImageProperties = mlImage.getContainedImageProperties().get(0);
        int storageType = mlImageProperties.getStorageType();
        if (storageType == 1) {
            return TensorImage.fromBitmap(BitmapExtractor.extract(mlImage));
        }
        if (storageType == 2) {
            ByteBuffer buffer = ByteBufferExtractor.extract(mlImage);
            ImageFormatProxy formatProxy = ImageFormatProxy.createFromImageFormat(mlImageProperties.getImageFormat());
            TensorImage byteBufferTensorImage = new TensorImage();
            byteBufferTensorImage.load(buffer, ImageProperties.builder().setColorSpaceType(formatProxy.getColorSpaceType()).setHeight(mlImage.getHeight()).setWidth(mlImage.getWidth()).build());
            return byteBufferTensorImage;
        } else if (storageType == 3) {
            TensorImage mediaTensorImage = new TensorImage();
            mediaTensorImage.load(MediaImageExtractor.extract(mlImage));
            return mediaTensorImage;
        } else {
            throw new IllegalArgumentException("Illegal storage type: " + mlImageProperties.getStorageType());
        }
    }

    public static ColorSpaceType createColorSpaceTypeFrom(int imageFormat) {
        return ImageFormatProxy.createFromImageFormat(imageFormat).getColorSpaceType();
    }

    private MlImageAdapter() {
    }
}
