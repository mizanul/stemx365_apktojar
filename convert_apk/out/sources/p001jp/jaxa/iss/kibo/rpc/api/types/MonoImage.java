package p001jp.jaxa.iss.kibo.rpc.api.types;

import android.graphics.Bitmap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import sensor_msgs.Image;

/* renamed from: jp.jaxa.iss.kibo.rpc.api.types.MonoImage */
public class MonoImage {
    private byte[] data = new byte[(this.width * this.height)];
    private ChannelBuffer dataBuffer;
    private int height;
    private int width;

    public MonoImage(Image image) {
        this.height = image.getHeight();
        this.width = image.getWidth();
        ChannelBuffer data2 = image.getData();
        this.dataBuffer = data2;
        data2.getBytes(data2.readerIndex(), this.data);
    }

    public Mat getMat() {
        Mat mat = new Mat(this.height, this.width, CvType.CV_8UC1);
        mat.put(0, 0, this.data);
        return mat;
    }

    public Bitmap getBitmap() {
        Mat mat = getMat();
        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }
}
