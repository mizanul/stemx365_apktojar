package ff_msgs;

import org.ros.internal.message.Message;

public interface SetBoolResponse extends Message {
    public static final String _DEFINITION = "bool success # whether succeeded";
    public static final String _TYPE = "ff_msgs/SetBoolResponse";

    boolean getSuccess();

    void setSuccess(boolean z);
}
