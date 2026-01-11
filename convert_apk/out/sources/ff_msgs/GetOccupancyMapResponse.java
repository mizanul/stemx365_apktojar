package ff_msgs;

import geometry_msgs.Vector3;
import org.jboss.netty.buffer.ChannelBuffer;
import org.ros.internal.message.Message;
import org.ros.message.Time;

public interface GetOccupancyMapResponse extends Message {
    public static final String _DEFINITION = "time timestamp                          # When these zones were updated\n\nint8[] map                              # Occupancy map\ngeometry_msgs/Vector3 origin            # Map origin\ngeometry_msgs/Vector3 dim\t              # Map dimentions\nfloat32 resolution                      # Map resolution";
    public static final String _TYPE = "ff_msgs/GetOccupancyMapResponse";

    Vector3 getDim();

    ChannelBuffer getMap();

    Vector3 getOrigin();

    float getResolution();

    Time getTimestamp();

    void setDim(Vector3 vector3);

    void setMap(ChannelBuffer channelBuffer);

    void setOrigin(Vector3 vector3);

    void setResolution(float f);

    void setTimestamp(Time time);
}
