package org.tensorflow.lite.support.image;

import android.graphics.Bitmap;
import java.util.Arrays;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public enum ColorSpaceType {
    RGB(0) {
        private static final int CHANNEL_VALUE = 3;

        /* access modifiers changed from: package-private */
        public Bitmap convertTensorBufferToBitmap(TensorBuffer buffer) {
            return ImageConversions.convertRgbTensorBufferToBitmap(buffer);
        }

        /* access modifiers changed from: package-private */
        public int getChannelValue() {
            return 3;
        }

        /* access modifiers changed from: package-private */
        public int[] getNormalizedShape(int[] shape) {
            int length = shape.length;
            if (length == 3) {
                return ColorSpaceType.insertValue(shape, 0, 1);
            }
            if (length == 4) {
                return shape;
            }
            throw new IllegalArgumentException(getShapeInfoMessage() + "The provided image shape is " + Arrays.toString(shape));
        }

        /* access modifiers changed from: package-private */
        public int getNumElements(int height, int width) {
            return height * width * 3;
        }

        /* access modifiers changed from: package-private */
        public String getShapeInfoMessage() {
            return "The shape of a RGB image should be (h, w, c) or (1, h, w, c), and channels representing R, G, B in order. ";
        }

        /* access modifiers changed from: package-private */
        public Bitmap.Config toBitmapConfig() {
            return Bitmap.Config.ARGB_8888;
        }
    },
    GRAYSCALE(1) {
        private static final int CHANNEL_VALUE = 1;

        /* access modifiers changed from: package-private */
        public Bitmap convertTensorBufferToBitmap(TensorBuffer buffer) {
            return ImageConversions.convertGrayscaleTensorBufferToBitmap(buffer);
        }

        /* access modifiers changed from: package-private */
        public int getChannelValue() {
            return 1;
        }

        /* access modifiers changed from: package-private */
        public int[] getNormalizedShape(int[] shape) {
            int length = shape.length;
            if (length == 2) {
                return ColorSpaceType.insertValue(ColorSpaceType.insertValue(shape, 0, 1), 3, 1);
            }
            if (length == 4) {
                return shape;
            }
            throw new IllegalArgumentException(getShapeInfoMessage() + "The provided image shape is " + Arrays.toString(shape));
        }

        /* access modifiers changed from: package-private */
        public int getNumElements(int height, int width) {
            return height * width;
        }

        /* access modifiers changed from: package-private */
        public String getShapeInfoMessage() {
            return "The shape of a grayscale image should be (h, w) or (1, h, w, 1). ";
        }

        /* access modifiers changed from: package-private */
        public Bitmap.Config toBitmapConfig() {
            return Bitmap.Config.ALPHA_8;
        }
    },
    NV12(2) {
        /* access modifiers changed from: package-private */
        public int getNumElements(int height, int width) {
            return ColorSpaceType.getYuv420NumElements(height, width);
        }
    },
    NV21(3) {
        /* access modifiers changed from: package-private */
        public int getNumElements(int height, int width) {
            return ColorSpaceType.getYuv420NumElements(height, width);
        }
    },
    YV12(4) {
        /* access modifiers changed from: package-private */
        public int getNumElements(int height, int width) {
            return ColorSpaceType.getYuv420NumElements(height, width);
        }
    },
    YV21(5) {
        /* access modifiers changed from: package-private */
        public int getNumElements(int height, int width) {
            return ColorSpaceType.getYuv420NumElements(height, width);
        }
    },
    YUV_420_888(6) {
        /* access modifiers changed from: package-private */
        public int getNumElements(int height, int width) {
            return ColorSpaceType.getYuv420NumElements(height, width);
        }
    };
    
    private static final int BATCH_DIM = 0;
    private static final int BATCH_VALUE = 1;
    private static final int CHANNEL_DIM = 3;
    private static final int HEIGHT_DIM = 1;
    private static final int WIDTH_DIM = 2;
    private final int value;

    /* access modifiers changed from: package-private */
    public abstract int getNumElements(int i, int i2);

    private ColorSpaceType(int value2) {
        this.value = value2;
    }

    /* renamed from: org.tensorflow.lite.support.image.ColorSpaceType$8 */
    static /* synthetic */ class C10788 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$Config = null;

        static {
            int[] iArr = new int[Bitmap.Config.values().length];
            $SwitchMap$android$graphics$Bitmap$Config = iArr;
            try {
                iArr[Bitmap.Config.ARGB_8888.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ALPHA_8.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static ColorSpaceType fromBitmapConfig(Bitmap.Config config) {
        int i = C10788.$SwitchMap$android$graphics$Bitmap$Config[config.ordinal()];
        if (i == 1) {
            return RGB;
        }
        if (i == 2) {
            return GRAYSCALE;
        }
        throw new IllegalArgumentException("Bitmap configuration: " + config + ", is not supported yet.");
    }

    static ColorSpaceType fromImageFormat(int imageFormat) {
        if (imageFormat == 17) {
            return NV21;
        }
        if (imageFormat == 35) {
            return YUV_420_888;
        }
        if (imageFormat == 842094169) {
            return YV12;
        }
        throw new IllegalArgumentException("ImageFormat: " + imageFormat + ", is not supported yet.");
    }

    public int getValue() {
        return this.value;
    }

    /* access modifiers changed from: package-private */
    public void assertShape(int[] shape) {
        assertRgbOrGrayScale("assertShape()");
        boolean isValidNormalizedShape = isValidNormalizedShape(getNormalizedShape(shape));
        SupportPreconditions.checkArgument(isValidNormalizedShape, getShapeInfoMessage() + "The provided image shape is " + Arrays.toString(shape));
    }

    /* access modifiers changed from: package-private */
    public void assertNumElements(int numElements, int height, int width) {
        SupportPreconditions.checkArgument(numElements >= getNumElements(height, width), String.format("The given number of elements (%d) does not match the image (%s) in %d x %d. The expected number of elements should be at least %d.", new Object[]{Integer.valueOf(numElements), name(), Integer.valueOf(height), Integer.valueOf(width), Integer.valueOf(getNumElements(height, width))}));
    }

    /* access modifiers changed from: package-private */
    public Bitmap convertTensorBufferToBitmap(TensorBuffer buffer) {
        throw new UnsupportedOperationException("convertTensorBufferToBitmap() is unsupported for the color space type " + name());
    }

    /* access modifiers changed from: package-private */
    public int getWidth(int[] shape) {
        assertRgbOrGrayScale("getWidth()");
        assertShape(shape);
        return getNormalizedShape(shape)[2];
    }

    /* access modifiers changed from: package-private */
    public int getHeight(int[] shape) {
        assertRgbOrGrayScale("getHeight()");
        assertShape(shape);
        return getNormalizedShape(shape)[1];
    }

    /* access modifiers changed from: package-private */
    public int getChannelValue() {
        throw new UnsupportedOperationException("getChannelValue() is unsupported for the color space type " + name());
    }

    /* access modifiers changed from: package-private */
    public int[] getNormalizedShape(int[] shape) {
        throw new UnsupportedOperationException("getNormalizedShape() is unsupported for the color space type " + name());
    }

    /* access modifiers changed from: package-private */
    public String getShapeInfoMessage() {
        throw new UnsupportedOperationException("getShapeInfoMessage() is unsupported for the color space type " + name());
    }

    /* access modifiers changed from: package-private */
    public Bitmap.Config toBitmapConfig() {
        throw new UnsupportedOperationException("toBitmapConfig() is unsupported for the color space type " + name());
    }

    /* access modifiers changed from: private */
    public static int getYuv420NumElements(int height, int width) {
        return (height * width) + (((height + 1) / 2) * ((width + 1) / 2) * 2);
    }

    /* access modifiers changed from: private */
    public static int[] insertValue(int[] array, int pos, int value2) {
        int[] newArray = new int[(array.length + 1)];
        for (int i = 0; i < pos; i++) {
            newArray[i] = array[i];
        }
        newArray[pos] = value2;
        for (int i2 = pos + 1; i2 < newArray.length; i2++) {
            newArray[i2] = array[i2 - 1];
        }
        return newArray;
    }

    /* access modifiers changed from: protected */
    public boolean isValidNormalizedShape(int[] shape) {
        if (shape[0] != 1 || shape[1] <= 0 || shape[2] <= 0 || shape[3] != getChannelValue()) {
            return false;
        }
        return true;
    }

    private void assertRgbOrGrayScale(String unsupportedMethodName) {
        if (this != RGB && this != GRAYSCALE) {
            throw new UnsupportedOperationException(unsupportedMethodName + " only supports RGB and GRAYSCALE formats, but not " + name());
        }
    }
}
