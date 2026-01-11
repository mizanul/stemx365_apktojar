package org.tensorflow.lite.support.image;

import android.graphics.RectF;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.common.internal.SupportPreconditions;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

public final class BoundingBoxUtil {

    public enum CoordinateType {
        RATIO,
        PIXEL
    }

    public enum Type {
        BOUNDARIES,
        UPPER_LEFT,
        CENTER
    }

    public static List<RectF> convert(TensorBuffer tensor, int[] valueIndex, int boundingBoxAxis, Type type, CoordinateType coordinateType, int height, int width) {
        int boundingBoxAxis2;
        int[] iArr = valueIndex;
        int boundingBoxAxis3 = boundingBoxAxis;
        int[] shape = tensor.getShape();
        boolean z = false;
        SupportPreconditions.checkArgument(boundingBoxAxis3 >= (-shape.length) && boundingBoxAxis3 < shape.length, String.format("Axis %d is not in range (-(D+1), D), where D is the number of dimensions of input tensor (shape=%s)", new Object[]{Integer.valueOf(boundingBoxAxis), Arrays.toString(shape)}));
        if (boundingBoxAxis3 < 0) {
            boundingBoxAxis2 = boundingBoxAxis3 + shape.length;
        } else {
            boundingBoxAxis2 = boundingBoxAxis3;
        }
        SupportPreconditions.checkArgument(shape[boundingBoxAxis2] == 4, String.format("Size of bounding box dimension %d is not 4. Got %d in shape %s", new Object[]{Integer.valueOf(boundingBoxAxis2), Integer.valueOf(shape[boundingBoxAxis2]), Arrays.toString(shape)}));
        SupportPreconditions.checkArgument(iArr.length == 4, String.format("Bounding box index array length %d is not 4. Got index array %s", new Object[]{Integer.valueOf(iArr.length), Arrays.toString(valueIndex)}));
        if (tensor.getDataType() == DataType.FLOAT32) {
            z = true;
        }
        SupportPreconditions.checkArgument(z, "Bounding Boxes only create from FLOAT32 buffers. Got: " + tensor.getDataType().name());
        List<RectF> boundingBoxList = new ArrayList<>();
        int a = 1;
        for (int i = 0; i < boundingBoxAxis2; i++) {
            a *= shape[i];
        }
        int b = 1;
        for (int i2 = boundingBoxAxis2 + 1; i2 < shape.length; i2++) {
            b *= shape[i2];
        }
        float[] values = new float[4];
        ByteBuffer byteBuffer = tensor.getBuffer();
        byteBuffer.rewind();
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        int i3 = 0;
        while (i3 < a) {
            int j = 0;
            while (j < b) {
                for (int k = 0; k < 4; k++) {
                    values[k] = floatBuffer.get((((i3 * 4) + k) * b) + j);
                }
                boundingBoxList.add(convertOneBoundingBox(values, valueIndex, type, coordinateType, height, width));
                j++;
                i3 = i3;
            }
            int i4 = j;
            i3++;
        }
        byteBuffer.rewind();
        return boundingBoxList;
    }

    private static RectF convertOneBoundingBox(float[] values, int[] valueIndex, Type type, CoordinateType coordinateType, int height, int width) {
        float[] orderedValues = new float[4];
        for (int i = 0; i < 4; i++) {
            orderedValues[i] = values[valueIndex[i]];
        }
        return convertOneBoundingBox(orderedValues, type, coordinateType, height, width);
    }

    /* renamed from: org.tensorflow.lite.support.image.BoundingBoxUtil$1 */
    static /* synthetic */ class C10701 {

        /* renamed from: $SwitchMap$org$tensorflow$lite$support$image$BoundingBoxUtil$Type */
        static final /* synthetic */ int[] f192xf6ea506c;

        static {
            int[] iArr = new int[Type.values().length];
            f192xf6ea506c = iArr;
            try {
                iArr[Type.BOUNDARIES.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f192xf6ea506c[Type.UPPER_LEFT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f192xf6ea506c[Type.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private static RectF convertOneBoundingBox(float[] values, Type type, CoordinateType coordinateType, int height, int width) {
        int i = C10701.f192xf6ea506c[type.ordinal()];
        if (i == 1) {
            return convertFromBoundaries(values, coordinateType, height, width);
        }
        if (i == 2) {
            return convertFromUpperLeft(values, coordinateType, height, width);
        }
        if (i == 3) {
            return convertFromCenter(values, coordinateType, height, width);
        }
        throw new IllegalArgumentException("Cannot recognize BoundingBox.Type " + type);
    }

    private static RectF convertFromBoundaries(float[] values, CoordinateType coordinateType, int imageHeight, int imageWidth) {
        return getRectF(values[0], values[1], values[2], values[3], imageHeight, imageWidth, coordinateType);
    }

    private static RectF convertFromUpperLeft(float[] values, CoordinateType coordinateType, int imageHeight, int imageWidth) {
        return getRectF(values[0], values[1], values[0] + values[2], values[1] + values[3], imageHeight, imageWidth, coordinateType);
    }

    private static RectF convertFromCenter(float[] values, CoordinateType coordinateType, int imageHeight, int imageWidth) {
        float centerX = values[0];
        float centerY = values[1];
        float w = values[2];
        float h = values[3];
        return getRectF(centerX - (w / 2.0f), centerY - (h / 2.0f), centerX + (w / 2.0f), (h / 2.0f) + centerY, imageHeight, imageWidth, coordinateType);
    }

    private static RectF getRectF(float left, float top, float right, float bottom, int imageHeight, int imageWidth, CoordinateType coordinateType) {
        if (coordinateType == CoordinateType.PIXEL) {
            return new RectF(left, top, right, bottom);
        }
        if (coordinateType == CoordinateType.RATIO) {
            return new RectF(((float) imageWidth) * left, ((float) imageHeight) * top, ((float) imageWidth) * right, ((float) imageHeight) * bottom);
        }
        throw new IllegalArgumentException("Cannot convert coordinate type " + coordinateType);
    }

    private BoundingBoxUtil() {
    }
}
