package ff_msgs;

import org.ros.internal.message.Message;

public interface SetInertiaResponse extends Message {
    public static final String _DEFINITION = "bool success                                # Did the update work?";
    public static final String _TYPE = "ff_msgs/SetInertiaResponse";

    boolean getSuccess();

    void setSuccess(boolean z);
}
