package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface Heartbeat extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n# \n# Fault State Message which doubles as a heartbeat\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Name of the nodelet manager the node is running in\nstring nodelet_manager\n\n# Name of node publishing the heartbeat\nstring node\n\n# Faults that are currently occurring in the node\nff_msgs/Fault[] faults\n";
    public static final String _TYPE = "ff_msgs/Heartbeat";

    List<Fault> getFaults();

    Header getHeader();

    String getNode();

    String getNodeletManager();

    void setFaults(List<Fault> list);

    void setHeader(Header header);

    void setNode(String str);

    void setNodeletManager(String str);
}
