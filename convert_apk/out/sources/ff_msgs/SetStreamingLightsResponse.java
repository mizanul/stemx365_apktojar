package ff_msgs;

import org.ros.internal.message.Message;

public interface SetStreamingLightsResponse extends Message {
    public static final String _DEFINITION = "bool success";
    public static final String _TYPE = "ff_msgs/SetStreamingLightsResponse";

    boolean getSuccess();

    void setSuccess(boolean z);
}
