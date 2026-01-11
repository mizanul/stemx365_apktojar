package ff_msgs;

import org.ros.internal.message.Message;

public interface UnloadLoadNodeletResponse extends Message {
    public static final int MANAGER_NAME_MISSING = 4;
    public static final int NODE_NOT_IN_MAP = 3;
    public static final int ROS_SERVICE_FAILED = 2;
    public static final int SUCCESSFUL = 1;
    public static final int TYPE_MISSING = 5;
    public static final String _DEFINITION = "int32 result\n\nint32 SUCCESSFUL            = 1   # Success :)\n# Un/load nodelet service offerred by the nodelet manager failed\nint32 ROS_SERVICE_FAILED    = 2\n# Node is not in the watchdog map i.e. the node doesn't have a heartbeat fault\n# listed in the fault table. Thus the system monitor wasn't able to retrive the\n# manager name.\nint32 NODE_NOT_IN_MAP       = 3\n# The operator didn't list the manager name in the command and the system\n# monitor was unable to figure out what it should be.\nint32 MANAGER_NAME_MISSING  = 4\n# The operator didn't list the nodelet type and it wasn't specified in the\n# system monitor config file\nint32 TYPE_MISSING          = 5";
    public static final String _TYPE = "ff_msgs/UnloadLoadNodeletResponse";

    int getResult();

    void setResult(int i);
}
