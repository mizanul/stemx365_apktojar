package ff_msgs;

import org.ros.internal.message.Message;
import std_msgs.Header;

public interface PerchState extends Message {
    public static final byte INITIALIZING = 13;
    public static final byte PERCHED = 0;
    public static final byte PERCHING_CHECKING_ATTACHED = 4;
    public static final byte PERCHING_CLOSING_GRIPPER = 5;
    public static final byte PERCHING_DEPLOYING_ARM = 8;
    public static final byte PERCHING_ENSURING_APPROACH_POSE = 9;
    public static final byte PERCHING_MAX_STATE = 11;
    public static final byte PERCHING_MOVING_TO_APPROACH_POSE = 10;
    public static final byte PERCHING_MOVING_TO_COMPLETE_POSE = 6;
    public static final byte PERCHING_OPENING_GRIPPER = 7;
    public static final byte PERCHING_STOPPING = 1;
    public static final byte PERCHING_SWITCHING_TO_HR_LOC = 11;
    public static final byte PERCHING_SWITCHING_TO_PL_LOC = 2;
    public static final byte PERCHING_WAITING_FOR_SPIN_DOWN = 3;
    public static final byte RECOVERY_MOVING_TO_APPROACH_POSE = 15;
    public static final byte RECOVERY_MOVING_TO_RECOVERY_POSE = 18;
    public static final byte RECOVERY_OPENING_GRIPPER = 14;
    public static final byte RECOVERY_STOWING_ARM = 16;
    public static final byte RECOVERY_SWITCHING_TO_ML_LOC = 17;
    public static final byte UNKNOWN = 12;
    public static final byte UNPERCHED = -7;
    public static final byte UNPERCHING_MAX_STATE = -7;
    public static final byte UNPERCHING_MOVING_TO_APPROACH_POSE = -4;
    public static final byte UNPERCHING_OPENING_GRIPPER = -3;
    public static final byte UNPERCHING_STOWING_ARM = -5;
    public static final byte UNPERCHING_SWITCHING_TO_HR_LOC = -1;
    public static final byte UNPERCHING_SWITCHING_TO_ML_LOC = -6;
    public static final byte UNPERCHING_WAITING_FOR_SPIN_UP = -2;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n#\n# All rights reserved.\n#\n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n#\n#     http://www.apache.org/licenses/LICENSE-2.0\n#\n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# The state of the perching system\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Feedback\nint8 state\n\nint8 RECOVERY_MOVING_TO_RECOVERY_POSE   = 18\nint8 RECOVERY_SWITCHING_TO_ML_LOC       = 17\nint8 RECOVERY_STOWING_ARM               = 16\nint8 RECOVERY_MOVING_TO_APPROACH_POSE   = 15\nint8 RECOVERY_OPENING_GRIPPER           = 14\nint8 INITIALIZING                       = 13\nint8 UNKNOWN                            = 12\n# Used to check the perching/unperching ranges\nint8 PERCHING_MAX_STATE                 = 11\nint8 PERCHING_SWITCHING_TO_HR_LOC       = 11\nint8 PERCHING_MOVING_TO_APPROACH_POSE   = 10\nint8 PERCHING_ENSURING_APPROACH_POSE    = 9\nint8 PERCHING_DEPLOYING_ARM             = 8\nint8 PERCHING_OPENING_GRIPPER           = 7\nint8 PERCHING_MOVING_TO_COMPLETE_POSE   = 6\nint8 PERCHING_CLOSING_GRIPPER           = 5\nint8 PERCHING_CHECKING_ATTACHED         = 4\nint8 PERCHING_WAITING_FOR_SPIN_DOWN     = 3\nint8 PERCHING_SWITCHING_TO_PL_LOC       = 2\nint8 PERCHING_STOPPING                  = 1\nint8 PERCHED                            = 0\nint8 UNPERCHING_SWITCHING_TO_HR_LOC     = -1\nint8 UNPERCHING_WAITING_FOR_SPIN_UP     = -2\nint8 UNPERCHING_OPENING_GRIPPER         = -3\nint8 UNPERCHING_MOVING_TO_APPROACH_POSE = -4\nint8 UNPERCHING_STOWING_ARM             = -5\nint8 UNPERCHING_SWITCHING_TO_ML_LOC     = -6\nint8 UNPERCHED                          = -7\n# Used to check the perching/unperching ranges\nint8 UNPERCHING_MAX_STATE               = -7\n\n# A human readable version of the (event) -> [state] transition\nstring fsm_event\nstring fsm_state\n";
    public static final String _TYPE = "ff_msgs/PerchState";

    String getFsmEvent();

    String getFsmState();

    Header getHeader();

    byte getState();

    void setFsmEvent(String str);

    void setFsmState(String str);

    void setHeader(Header header);

    void setState(byte b);
}
