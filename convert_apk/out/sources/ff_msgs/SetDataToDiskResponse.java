package ff_msgs;

import org.ros.internal.message.Message;

public interface SetDataToDiskResponse extends Message {
    public static final String _DEFINITION = "bool success                             # Did the update work?\nstring status";
    public static final String _TYPE = "ff_msgs/SetDataToDiskResponse";

    String getStatus();

    boolean getSuccess();

    void setStatus(String str);

    void setSuccess(boolean z);
}
