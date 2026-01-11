package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface FaultState extends Message {
    public static final byte BLOCKED = 3;
    public static final byte FAULT = 2;
    public static final byte FUNCTIONAL = 1;
    public static final byte RELOADING_NODELETS = 4;
    public static final byte STARTING_UP = 0;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Fault state message used to alert the ground of the current faults. It is also\n# used to express to the executive that a fault has occurred that indirectly\n# affects the motion of the robot.\n\nstd_msgs/Header header\n\n# Not sent to the ground, only used by the executive to determine what commands\n# to accept.\nuint8 state\n# System starting up\nuint8 STARTING_UP           = 0\n# No faults are occurring in system\nuint8 FUNCTIONAL            = 1\n# Faults are occurring in the system which may or may not leave the robot\n# functional\nuint8 FAULT                 = 2\n# A fault has occurred that indirectly affects the motion of the robot\nuint8 BLOCKED               = 3\n# Recovering from nodes dying on startup\nuint8 RELOADING_NODELETS    = 4\n\n# A human readable version of the state - only really used for when nodes die on\n# startup and need to be restarted.\nstring hr_state\n\n# Faults occurring in the astrobee system, can only send 32 faults down\nff_msgs/Fault[] faults\n";
    public static final String _TYPE = "ff_msgs/FaultState";

    List<Fault> getFaults();

    Header getHeader();

    String getHrState();

    byte getState();

    void setFaults(List<Fault> list);

    void setHeader(Header header);

    void setHrState(String str);

    void setState(byte b);
}
