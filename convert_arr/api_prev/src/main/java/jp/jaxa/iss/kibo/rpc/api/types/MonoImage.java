 

package jp.jaxa.iss.kibo.rpc.api.types;

import org.opencv.android.Utils;
import android.graphics.Bitmap;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import sensor_msgs.Image;
import org.jboss.netty.buffer.ChannelBuffer;

public class MonoImage
{
    private byte[] data;
    private int height;
    private int width;
    private ChannelBuffer dataBuffer;
    
    public MonoImage(final Image image) {
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.dataBuffer = image.getData();
        this.data = new byte[this.width * this.height];
        this.dataBuffer.getBytes(this.dataBuffer.readerIndex(), this.data);
    }
    
    public Mat getMat() {
        final Mat mat = new Mat(this.height, this.width, CvType.CV_8UC1);
        mat.put(0, 0, this.data);
        return mat;
    }
    
    public Bitmap getBitmap() {
        final Mat mat = this.getMat();
        final Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }
}
