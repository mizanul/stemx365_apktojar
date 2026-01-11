package ff_msgs;

import org.ros.internal.message.Message;

public interface ExecState extends Message {
    public static final byte ERROR = 3;
    public static final byte EXECUTING = 1;
    public static final byte IDLE = 0;
    public static final byte PAUSED = 2;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Execution States, based off of the enumeration constants in\n# rapid::ext::astrobee::AgentState\n#\n# *MUST* be kept in sync with the DDS IDL file in astrobee_common\n\nuint8 IDLE      = 0   # Process is idle\nuint8 EXECUTING = 1   # Process is executing\nuint8 PAUSED    = 2   # Process is paused\nuint8 ERROR     = 3   # Process encountered an error\n\n# Execution state\nuint8 state\n";
    public static final String _TYPE = "ff_msgs/ExecState";

    byte getState();

    void setState(byte b);
}
