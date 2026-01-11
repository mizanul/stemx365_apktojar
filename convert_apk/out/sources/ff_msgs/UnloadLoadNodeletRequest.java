package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;

public interface UnloadLoadNodeletRequest extends Message {
    public static final String _DEFINITION = "###########################################################################\n#           Copyright 2017 Intelligent Robotics Group, NASA ARC           #\n###########################################################################\n# This service is used by the system monitor to load and unload nodelets. #\n###########################################################################\n\nbool load             # If true load the nodelet. Else unload the nodelet.\n\nstring name           # Name of nodelet. Needed for both unload and load.\n\n# Type of nodelet (\"namespace/classname\"). Needed for load. May not be needed\n# if the type is specified in the system monitor config.\nstring type\n\n# Name of nodelet manager. Needed for both load and unload. May not be needed if\n# the system monitor received a heartbeat before being unloaded.\nstring manager_name\n\nstring[] remap_source_args  # Leave blank if no source args. Needed for load.\nstring[] remap_target_args  # Leave blank if no target args. Needed for load.\nstring[] my_argv            # Leave blank if none. Needed for load\nstring bond_id  # Not entirely sure what this is, leave blank. Needed for load.\n";
    public static final String _TYPE = "ff_msgs/UnloadLoadNodeletRequest";

    String getBondId();

    boolean getLoad();

    String getManagerName();

    List<String> getMyArgv();

    String getName();

    List<String> getRemapSourceArgs();

    List<String> getRemapTargetArgs();

    String getType();

    void setBondId(String str);

    void setLoad(boolean z);

    void setManagerName(String str);

    void setMyArgv(List<String> list);

    void setName(String str);

    void setRemapSourceArgs(List<String> list);

    void setRemapTargetArgs(List<String> list);

    void setType(String str);
}
