package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;
import org.ros.message.Time;

public interface GetZonesResponse extends Message {
    public static final String _DEFINITION = "time timestamp                           # When these zones were updated\nff_msgs/Zone[] zones                     # A vector of zones";
    public static final String _TYPE = "ff_msgs/GetZonesResponse";

    Time getTimestamp();

    List<Zone> getZones();

    void setTimestamp(Time time);

    void setZones(List<Zone> list);
}
