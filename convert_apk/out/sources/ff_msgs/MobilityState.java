package ff_msgs;

import org.ros.internal.message.Message;

public interface MobilityState extends Message {
    public static final byte DOCKING = 3;
    public static final byte DRIFTING = 0;
    public static final byte FLYING = 2;
    public static final byte PERCHING = 4;
    public static final byte STOPPING = 1;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Mobility states, based off the enumeration constants in\n# rapid::ext::astrobee::AgentState\n#\n# *MUST* be kept in sync with the DDS IDL file in astrobee_common\n\nuint8 DRIFTING        = 0   # Astrobee's propulsion is off\nuint8 STOPPING        = 1   # Astrobee is either stopping or stopped\nuint8 FLYING          = 2   # Astrobee is flying\nuint8 DOCKING         = 3   # Astrobee is either docking or undocking\nuint8 PERCHING        = 4   # Astrobee is either perching or unperching\n\n# Mobility state\nuint8 state\n\n# Specifies the progress of the action. For docking, this value can be N to -N\n# where N through 1 specifies the progress of a docking action, 0 is docked, and\n# -1 through -N specifies the process of an undocking action. For stopping, this\n# value is either 1 or 0 where 1 means the robot is coming to a stop and 0 means\n# the robot is stopped. For perching, this value can be N to -N where N through\n# 1 specifies the progress of a perching action, 0 is perched, and -1 through\n# -N specifies the process of an unperching action.\nint32 sub_state\n";
    public static final String _TYPE = "ff_msgs/MobilityState";

    byte getState();

    int getSubState();

    void setState(byte b);

    void setSubState(int i);
}
