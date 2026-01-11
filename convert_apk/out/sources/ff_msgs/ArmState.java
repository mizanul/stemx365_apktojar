package ff_msgs;

import org.ros.internal.message.Message;
import std_msgs.Header;

public interface ArmState extends Message {
    public static final byte CALIBRATING = 12;
    public static final byte DEPLOYED = 3;
    public static final byte DEPLOYING_PANNING = 10;
    public static final byte DEPLOYING_TILTING = 11;
    public static final byte INITIALIZING = 0;
    public static final byte PANNING = 5;
    public static final byte SETTING = 4;
    public static final byte STOWED = 2;
    public static final byte STOWING_PANNING = 8;
    public static final byte STOWING_SETTING = 7;
    public static final byte STOWING_TILTING = 9;
    public static final byte TILTING = 6;
    public static final byte UNKNOWN = 1;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n#\n# All rights reserved.\n#\n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n#\n#     http://www.apache.org/licenses/LICENSE-2.0\n#\n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# The state of the arm behavior\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Tee current state\nint8 state                         # Current state\nint8 INITIALIZING        = 0       # Waiting on child services, actions, etc.\nint8 UNKNOWN             = 1       # Waiting on feedback from driver\nint8 STOWED              = 2       # The arm is stowed\nint8 DEPLOYED            = 3       # The arm is deployed\nint8 SETTING             = 4       # The gripper is being set to a value\nint8 PANNING             = 5       # We are panning as part of a move\nint8 TILTING             = 6       # We are tilting as part of a move\nint8 STOWING_SETTING     = 7       # We are closing the gripper for stowing\nint8 STOWING_PANNING     = 8       # We are panning to zero for stowing\nint8 STOWING_TILTING     = 9       # We are tilting to zero for stowing\nint8 DEPLOYING_PANNING   = 10      # We are panning to zero for stowing\nint8 DEPLOYING_TILTING   = 11      # We are tilting to zero for stowing\nint8 CALIBRATING         = 12      # We are calibrating the gripper\n\n# A human readble version of the (event) -> [state] transition\nstring fsm_event\nstring fsm_state\n";
    public static final String _TYPE = "ff_msgs/ArmState";

    String getFsmEvent();

    String getFsmState();

    Header getHeader();

    byte getState();

    void setFsmEvent(String str);

    void setFsmState(String str);

    void setHeader(Header header);

    void setState(byte b);
}
