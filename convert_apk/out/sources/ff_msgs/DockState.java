package ff_msgs;

import org.ros.internal.message.Message;
import std_msgs.Header;

public interface DockState extends Message {
    public static final byte DOCKED = 0;
    public static final byte DOCKING_CHECKING_ATTACHED = 3;
    public static final byte DOCKING_MAX_STATE = 7;
    public static final byte DOCKING_MOVING_TO_APPROACH_POSE = 6;
    public static final byte DOCKING_MOVING_TO_COMPLETE_POSE = 4;
    public static final byte DOCKING_SWITCHING_TO_AR_LOC = 5;
    public static final byte DOCKING_SWITCHING_TO_ML_LOC = 7;
    public static final byte DOCKING_SWITCHING_TO_NO_LOC = 1;
    public static final byte DOCKING_WAITING_FOR_SPIN_DOWN = 2;
    public static final byte INITIALIZING = 11;
    public static final byte RECOVERY_MOVING_TO_APPROACH_POSE = 14;
    public static final byte RECOVERY_SWITCHING_TO_ML_LOC = 15;
    public static final byte RECOVERY_SWITCHING_TO_NO_LOC = 12;
    public static final byte RECOVERY_WAITING_FOR_SPIN_DOWN = 13;
    public static final byte UNDOCKED = -4;
    public static final byte UNDOCKING_MAX_STATE = -4;
    public static final byte UNDOCKING_MOVING_TO_APPROACH_POSE = -3;
    public static final byte UNDOCKING_SWITCHING_TO_ML_LOC = -1;
    public static final byte UNDOCKING_WAITING_FOR_SPIN_UP = -2;
    public static final byte UNKNOWN = 10;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Response for Dock/Undock goals\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Feedback\nint8 state\nint8 RECOVERY_SWITCHING_TO_ML_LOC       = 15\nint8 RECOVERY_MOVING_TO_APPROACH_POSE   = 14\nint8 RECOVERY_WAITING_FOR_SPIN_DOWN     = 13\nint8 RECOVERY_SWITCHING_TO_NO_LOC       = 12\nint8 INITIALIZING                       = 11\nint8 UNKNOWN                            = 10\nint8 DOCKING_MAX_STATE                  = 7\nint8 DOCKING_SWITCHING_TO_ML_LOC        = 7\nint8 DOCKING_MOVING_TO_APPROACH_POSE    = 6\nint8 DOCKING_SWITCHING_TO_AR_LOC        = 5\nint8 DOCKING_MOVING_TO_COMPLETE_POSE    = 4\nint8 DOCKING_CHECKING_ATTACHED          = 3\nint8 DOCKING_WAITING_FOR_SPIN_DOWN      = 2\nint8 DOCKING_SWITCHING_TO_NO_LOC        = 1\nint8 DOCKED                             = 0\nint8 UNDOCKING_SWITCHING_TO_ML_LOC      = -1\nint8 UNDOCKING_WAITING_FOR_SPIN_UP      = -2\nint8 UNDOCKING_MOVING_TO_APPROACH_POSE  = -3\nint8 UNDOCKED                           = -4\nint8 UNDOCKING_MAX_STATE                = -4\n\n# A human readble version of the (event) -> [state] transition\nstring fsm_event\nstring fsm_state";
    public static final String _TYPE = "ff_msgs/DockState";

    String getFsmEvent();

    String getFsmState();

    Header getHeader();

    byte getState();

    void setFsmEvent(String str);

    void setFsmState(String str);

    void setHeader(Header header);

    void setState(byte b);
}
