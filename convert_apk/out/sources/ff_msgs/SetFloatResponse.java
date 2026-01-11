package ff_msgs;

import org.ros.internal.message.Message;

public interface SetFloatResponse extends Message {
    public static final String _DEFINITION = "bool success # whether succeeded";
    public static final String _TYPE = "ff_msgs/SetFloatResponse";

    boolean getSuccess();

    void setSuccess(boolean z);
}
