package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface MemStateStamped extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Memory state message with timestamp.\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Machine name (llp, hlp, mlp, etc)\nstring name\n\n# Physical Memory (RAM)\nuint32 ram_total   # total physical memeory in system Mb\nuint32 ram_used    # totalphysical memeory used in Mb\n\n# Virtual Memory\nuint32 virt_total  # total virtual memeory in system in Mb\nuint32 virt_used   # total virtual memeory used in Mb\n\n# Individual nodes\nff_msgs/MemState[] nodes\n\n\n";
    public static final String _TYPE = "ff_msgs/MemStateStamped";

    Header getHeader();

    String getName();

    List<MemState> getNodes();

    int getRamTotal();

    int getRamUsed();

    int getVirtTotal();

    int getVirtUsed();

    void setHeader(Header header);

    void setName(String str);

    void setNodes(List<MemState> list);

    void setRamTotal(int i);

    void setRamUsed(int i);

    void setVirtTotal(int i);

    void setVirtUsed(int i);
}
