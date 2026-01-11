package ff_msgs;

import org.ros.internal.message.Message;

public interface ArmJointState extends Message {
    public static final byte DEPLOYING = 2;
    public static final byte MOVING = 4;
    public static final byte STOPPED = 3;
    public static final byte STOWED = 1;
    public static final byte STOWING = 5;
    public static final byte UNKNOWN = 0;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Arm Joint State enum.\n#\n# *MUST* be kept in sync with rapid::ext::astrobee::ArmState\n\nuint8 UNKNOWN   = 0\nuint8 STOWED    = 1\nuint8 DEPLOYING = 2\nuint8 STOPPED   = 3\nuint8 MOVING    = 4\nuint8 STOWING   = 5\n\nuint8 state\n";
    public static final String _TYPE = "ff_msgs/ArmJointState";

    byte getState();

    void setState(byte b);
}
