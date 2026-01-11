package p001jp.jaxa.iss.kibo.rpc.api.types;

import gov.nasa.arc.astrobee.types.Point;
import org.jboss.netty.buffer.ChannelBuffer;
import sensor_msgs.PointCloud2;

/* renamed from: jp.jaxa.iss.kibo.rpc.api.types.PointCloud */
public class PointCloud implements Cloneable {
    private int height;
    private int pointStep;
    private ChannelBuffer rawData;
    private int rowStep;
    private int width;

    public PointCloud(PointCloud2 pointCloud2) {
        this.width = pointCloud2.getWidth();
        this.height = pointCloud2.getHeight();
        this.rowStep = pointCloud2.getRowStep();
        this.pointStep = pointCloud2.getPointStep();
        this.rawData = pointCloud2.getData();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Point[] getPointArray() {
        Point[] pointArray = new Point[(this.height * this.width)];
        for (int ix = 0; ix < this.width; ix++) {
            for (int iy = 0; iy < this.height; iy++) {
                pointArray[(this.width * iy) + ix] = new Point((double) this.rawData.getFloat((this.rowStep * iy) + (this.pointStep * ix)), (double) this.rawData.getFloat((this.rowStep * iy) + (this.pointStep * ix) + 4), (double) this.rawData.getFloat((this.rowStep * iy) + (this.pointStep * ix) + 8));
            }
        }
        return pointArray;
    }

    public PointCloud clone() throws CloneNotSupportedException {
        return (PointCloud) super.clone();
    }
}
