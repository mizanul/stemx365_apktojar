package org.tensorflow.lite.support.image;

import android.graphics.Bitmap;
import android.media.Image;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

interface ImageContainer {
    ImageContainer clone();

    Bitmap getBitmap();

    ColorSpaceType getColorSpaceType();

    int getHeight();

    Image getMediaImage();

    TensorBuffer getTensorBuffer(DataType dataType);

    int getWidth();
}
