package ff_msgs;

import org.ros.internal.message.Message;

public interface VisualeyezConfigResponse extends Message {
    public static final String _DEFINITION = "\nbool success";
    public static final String _TYPE = "ff_msgs/VisualeyezConfigResponse";

    boolean getSuccess();

    void setSuccess(boolean z);
}
