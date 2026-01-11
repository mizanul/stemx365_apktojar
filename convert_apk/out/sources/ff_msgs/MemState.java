package ff_msgs;

import org.ros.internal.message.Message;

public interface MemState extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# State of the Memory.\n\n# The memory load of the node, for the fields given in\nstring name\n# Virtual Memory\nuint32 virt        # virtual memeory used in Mb\nuint32 virt_peak   # peak virtual memory used in Mb\n\n# Physical Memory\nuint32 ram        # physical memory used in Mb\nuint32 ram_peak   # peak physical memory used in Mb\nfloat32 ram_perc  # percentage of physical memory in %\n\n";
    public static final String _TYPE = "ff_msgs/MemState";

    String getName();

    int getRam();

    int getRamPeak();

    float getRamPerc();

    int getVirt();

    int getVirtPeak();

    void setName(String str);

    void setRam(int i);

    void setRamPeak(int i);

    void setRamPerc(float f);

    void setVirt(int i);

    void setVirtPeak(int i);
}
