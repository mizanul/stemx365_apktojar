package org.tensorflow.lite.support.image;

import android.graphics.Bitmap;
import android.graphics.Color;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

class ImageConversions {
    static Bitmap convertRgbTensorBufferToBitmap(TensorBuffer buffer) {
        int[] shape = buffer.getShape();
        ColorSpaceType rgb = ColorSpaceType.RGB;
        rgb.assertShape(shape);
        int h = rgb.getHeight(shape);
        int w = rgb.getWidth(shape);
        Bitmap bitmap = Bitmap.createBitmap(w, h, rgb.toBitmapConfig());
        int[] intValues = new int[(w * h)];
        int[] rgbValues = buffer.getIntArray();
        int i = 0;
        int j = 0;
        while (i < intValues.length) {
            int j2 = j + 1;
            int j3 = j2 + 1;
            intValues[i] = Color.rgb(rgbValues[j], rgbValues[j2], rgbValues[j3]);
            i++;
            j = j3 + 1;
        }
        bitmap.setPixels(intValues, 0, w, 0, 0, w, h);
        return bitmap;
    }

    static Bitmap convertGrayscaleTensorBufferToBitmap(TensorBuffer buffer) {
        TensorBuffer uint8Buffer;
        if (buffer.getDataType() == DataType.UINT8) {
            uint8Buffer = buffer;
        } else {
            uint8Buffer = TensorBuffer.createFrom(buffer, DataType.UINT8);
        }
        int[] shape = uint8Buffer.getShape();
        ColorSpaceType grayscale = ColorSpaceType.GRAYSCALE;
        grayscale.assertShape(shape);
        Bitmap bitmap = Bitmap.createBitmap(grayscale.getWidth(shape), grayscale.getHeight(shape), grayscale.toBitmapConfig());
        uint8Buffer.getBuffer().rewind();
        bitmap.copyPixelsFromBuffer(uint8Buffer.getBuffer());
        return bitmap;
    }

    static void convertBitmapToTensorBuffer(Bitmap bitmap, TensorBuffer buffer) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] intValues = new int[(w * h)];
        bitmap.getPixels(intValues, 0, w, 0, 0, w, h);
        int[] shape = {h, w, 3};
        int i = C10791.$SwitchMap$org$tensorflow$lite$DataType[buffer.getDataType().ordinal()];
        if (i == 1) {
            byte[] byteArr = new byte[(w * h * 3)];
            int i2 = 0;
            int j = 0;
            while (i2 < intValues.length) {
                int j2 = j + 1;
                byteArr[j] = (byte) ((intValues[i2] >> 16) & 255);
                int j3 = j2 + 1;
                byteArr[j2] = (byte) ((intValues[i2] >> 8) & 255);
                byteArr[j3] = (byte) (intValues[i2] & 255);
                i2++;
                j = j3 + 1;
            }
            ByteBuffer byteBuffer = ByteBuffer.wrap(byteArr);
            byteBuffer.order(ByteOrder.nativeOrder());
            buffer.loadBuffer(byteBuffer, shape);
        } else if (i == 2) {
            float[] floatArr = new float[(w * h * 3)];
            int i3 = 0;
            int j4 = 0;
            while (i3 < intValues.length) {
                int j5 = j4 + 1;
                floatArr[j4] = (float) ((intValues[i3] >> 16) & 255);
                int j6 = j5 + 1;
                floatArr[j5] = (float) ((intValues[i3] >> 8) & 255);
                floatArr[j6] = (float) (intValues[i3] & 255);
                i3++;
                j4 = j6 + 1;
            }
            buffer.loadArray(floatArr, shape);
        } else {
            throw new IllegalStateException("The type of TensorBuffer, " + buffer.getBuffer() + ", is unsupported.");
        }
    }

    /* renamed from: org.tensorflow.lite.support.image.ImageConversions$1 */
    static /* synthetic */ class C10791 {
        static final /* synthetic */ int[] $SwitchMap$org$tensorflow$lite$DataType;

        static {
            int[] iArr = new int[DataType.values().length];
            $SwitchMap$org$tensorflow$lite$DataType = iArr;
            try {
                iArr[DataType.UINT8.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$tensorflow$lite$DataType[DataType.FLOAT32.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private ImageConversions() {
    }
}
