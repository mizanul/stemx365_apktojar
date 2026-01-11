package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface DiskStateStamped extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# This message describes the state of a filesystem within astrobee\n# Based off of DiskState from rapid::ext::astrobee\n\n# Header with timestamp\nstd_msgs/Header header\n\nstring processor_name       # Processor name, either llp, mlp, or hlp\n\n# Information on the mounted filesystem on the processor\nff_msgs/DiskState[] disks\n";
    public static final String _TYPE = "ff_msgs/DiskStateStamped";

    List<DiskState> getDisks();

    Header getHeader();

    String getProcessorName();

    void setDisks(List<DiskState> list);

    void setHeader(Header header);

    void setProcessorName(String str);
}
