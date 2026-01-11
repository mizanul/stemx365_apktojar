package ff_msgs;

import org.ros.internal.message.Message;
import sensor_msgs.PointCloud2;

public interface GetMapResponse extends Message {
    public static final String _DEFINITION = "sensor_msgs/PointCloud2 points           # Points of the map\nfloat32 resolution                       # Resolution of the map\nbool free                                # Whether theses points represent free voxels";
    public static final String _TYPE = "ff_msgs/GetMapResponse";

    boolean getFree();

    PointCloud2 getPoints();

    float getResolution();

    void setFree(boolean z);

    void setPoints(PointCloud2 pointCloud2);

    void setResolution(float f);
}
