package ff_msgs;

import org.ros.internal.message.Message;

public interface SetStateResponse extends Message {
    public static final String _DEFINITION = "bool success                             # Did the update work?";
    public static final String _TYPE = "ff_msgs/SetStateResponse";

    boolean getSuccess();

    void setSuccess(boolean z);
}
