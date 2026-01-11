package ff_msgs;

import org.ros.internal.message.Message;
import std_msgs.Header;

public interface MotionState extends Message {
    public static final byte BOOTSTRAPPING = 6;
    public static final byte CONTROLLING = 9;
    public static final byte HALTING = 11;
    public static final byte IDLE = 1;
    public static final byte IDLING = 3;
    public static final byte INITIALIZING = 0;
    public static final byte PLANNING = 7;
    public static final byte PREPARING = 8;
    public static final byte PREPPING = 5;
    public static final byte REPLANNING = 10;
    public static final byte REPLAN_WAIT = 12;
    public static final byte STOPPED = 2;
    public static final byte STOPPING = 4;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n#\n# All rights reserved.\n#\n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n#\n#     http://www.apache.org/licenses/LICENSE-2.0\n#\n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Locked topic that registers updates to the internal dock state\n\n# Header with timestamp\nstd_msgs/Header header\n\n# The state of the mobility subsystem\nint8 state\nint8 INITIALIZING        = 0\nint8 IDLE                = 1\nint8 STOPPED             = 2\nint8 IDLING              = 3\nint8 STOPPING            = 4\nint8 PREPPING            = 5\nint8 BOOTSTRAPPING       = 6\nint8 PLANNING            = 7\nint8 PREPARING           = 8\nint8 CONTROLLING         = 9\nint8 REPLANNING          = 10\nint8 HALTING             = 11\nint8 REPLAN_WAIT         = 12\n\n# A human readble version of the (event) -> [state] transition\nstring fsm_event\nstring fsm_state\n";
    public static final String _TYPE = "ff_msgs/MotionState";

    String getFsmEvent();

    String getFsmState();

    Header getHeader();

    byte getState();

    void setFsmEvent(String str);

    void setFsmState(String str);

    void setHeader(Header header);

    void setState(byte b);
}
