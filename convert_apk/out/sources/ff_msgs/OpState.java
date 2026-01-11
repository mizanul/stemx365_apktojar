package ff_msgs;

import org.ros.internal.message.Message;

public interface OpState extends Message {
    public static final byte AUTO_RETURN = 3;
    public static final byte FAULT = 4;
    public static final byte PLAN_EXECUTION = 1;
    public static final byte READY = 0;
    public static final byte TELEOPERATION = 2;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Operating States, based off of the enumeration constants\n# in rapid::ext::astrobee::AgentState.\n#\n# *MUST* be kept in sync with the DDS IDL file in astrobee_common\n\nuint8 READY            = 0  # Freeflyer is ready to accept commands\nuint8 PLAN_EXECUTION   = 1  # Freeflyer is executing a plan\nuint8 TELEOPERATION    = 2  # Freeflyer is executing a teleop command\nuint8 AUTO_RETURN      = 3  # Freeflyer is autonomously returning to the dock\n# The freeflyer is either executing a fault response or there is a fault\n# occurring in the system that prevents the freeflyer from moving\nuint8 FAULT            = 4\n\n# Operating state\nuint8 state\n";
    public static final String _TYPE = "ff_msgs/OpState";

    byte getState();

    void setState(byte b);
}
