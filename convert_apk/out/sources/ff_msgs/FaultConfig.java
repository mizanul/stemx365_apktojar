package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface FaultConfig extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Fault config message used to inform DDS of all the faults in the system\n\n# Header with timestamp\nstd_msgs/Header header\n\nstring[] subsystems         # A list of all the subsystem names in the system\n\nstring[] nodes              # A list of all the node names in the system\nff_msgs/FaultInfo[] faults  # A list of all of the faults in the system\n";
    public static final String _TYPE = "ff_msgs/FaultConfig";

    List<FaultInfo> getFaults();

    Header getHeader();

    List<String> getNodes();

    List<String> getSubsystems();

    void setFaults(List<FaultInfo> list);

    void setHeader(Header header);

    void setNodes(List<String> list);

    void setSubsystems(List<String> list);
}
