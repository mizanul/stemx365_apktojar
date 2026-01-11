package ff_msgs;

import org.ros.internal.message.Message;

public interface CpuState extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# State of a CPU.\n\n# Processor is on (enabled) or not\nbool enabled\n\n# The load (in percentages) of the cpu, for the fields given in\n# CpuStateStamped\nfloat32[] loads \n\n# Current operating frequency in Hz\nuint32 frequency\n\n# Max frequency (may be less than theoretical limit of the processor)\nuint32 max_frequency\n";
    public static final String _TYPE = "ff_msgs/CpuState";

    boolean getEnabled();

    int getFrequency();

    float[] getLoads();

    int getMaxFrequency();

    void setEnabled(boolean z);

    void setFrequency(int i);

    void setLoads(float[] fArr);

    void setMaxFrequency(int i);
}
