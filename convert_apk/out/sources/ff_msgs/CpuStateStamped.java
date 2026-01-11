package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface CpuStateStamped extends Message {
    public static final String NICE = "nice";
    public static final String SYS = "sys";
    public static final String TOTAL = "total";
    public static final String USER = "user";
    public static final String VIRT = "virt";
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Cpu state message with timestamp.\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Machine name (llp, hlp, mlp, etc)\nstring name\n\n# Load constants\nstring NICE=nice\nstring USER=user\nstring SYS=sys\nstring VIRT=virt\nstring TOTAL=total\n\n# The available fields within the load values, mostly uses the constants\n# defined above.\nstring[] load_fields\n\n# Average loads for all processors combined\nfloat32[] avg_loads\n\n# Temperature for a cpu (average of all thermal zones)\nfloat32 temp\n\n# Information for each processor\n# Size of the array specifies how many processors are on the board, whether\n# or not all of them are enabled.\nff_msgs/CpuState[] cpus\n\n# Load usage of individual ROS nodes\nff_msgs/CpuNodeState[] load_nodes";
    public static final String _TYPE = "ff_msgs/CpuStateStamped";

    float[] getAvgLoads();

    List<CpuState> getCpus();

    Header getHeader();

    List<String> getLoadFields();

    List<CpuNodeState> getLoadNodes();

    String getName();

    float getTemp();

    void setAvgLoads(float[] fArr);

    void setCpus(List<CpuState> list);

    void setHeader(Header header);

    void setLoadFields(List<String> list);

    void setLoadNodes(List<CpuNodeState> list);

    void setName(String str);

    void setTemp(float f);
}
