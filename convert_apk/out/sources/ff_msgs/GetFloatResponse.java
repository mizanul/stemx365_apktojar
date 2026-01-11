package ff_msgs;

import org.ros.internal.message.Message;

public interface GetFloatResponse extends Message {
    public static final String _DEFINITION = "float64 data # float data\nbool success # whether succeeded";
    public static final String _TYPE = "ff_msgs/GetFloatResponse";

    double getData();

    boolean getSuccess();

    void setData(double d);

    void setSuccess(boolean z);
}
