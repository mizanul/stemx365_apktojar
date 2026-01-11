package ff_msgs;

import org.ros.internal.message.Message;

public interface EnableRecordingResponse extends Message {
    public static final String _DEFINITION = "bool success\nstring status";
    public static final String _TYPE = "ff_msgs/EnableRecordingResponse";

    String getStatus();

    boolean getSuccess();

    void setStatus(String str);

    void setSuccess(boolean z);
}
