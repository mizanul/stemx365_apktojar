package ff_msgs;

import org.ros.internal.message.Message;
import std_msgs.Header;

public interface SignalState extends Message {
    public static final byte CHARGING = 18;
    public static final byte CLEAR = 14;
    public static final byte ENTER_HATCHWAY = 4;
    public static final byte MOTION_IMPAIRED = 7;
    public static final byte SLEEP = 15;
    public static final byte STOP_ALL_LIGHTS = 17;
    public static final byte SUCCESS = 3;
    public static final byte THRUST_AFT = 9;
    public static final byte THRUST_FORWARD = 8;
    public static final byte TURN_DOWN = 13;
    public static final byte TURN_LEFT = 11;
    public static final byte TURN_RIGHT = 10;
    public static final byte TURN_UP = 12;
    public static final byte UNDOCK = 5;
    public static final byte UNPERCH = 6;
    public static final byte VIDEO_OFF = 1;
    public static final byte VIDEO_ON = 0;
    public static final byte WAKE = 16;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n#\n# All rights reserved.\n#\n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n#\n#     http://www.apache.org/licenses/LICENSE-2.0\n#\n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Signal state which is based on what the Astrobee is doing. Should be used to\n# figure out what should be displayed on the signal lights and touch screen.\n\n# Header with timestamp\nstd_msgs/Header header\n\nuint8 VIDEO_ON              = 0\nuint8 VIDEO_OFF             = 1\nuint8 SUCCESS               = 3\nuint8 ENTER_HATCHWAY        = 4\nuint8 UNDOCK                = 5\nuint8 UNPERCH               = 6\nuint8 MOTION_IMPAIRED       = 7\nuint8 THRUST_FORWARD        = 8\nuint8 THRUST_AFT            = 9\nuint8 TURN_RIGHT            = 10\nuint8 TURN_LEFT             = 11\nuint8 TURN_UP               = 12\nuint8 TURN_DOWN             = 13\nuint8 CLEAR                 = 14\nuint8 SLEEP                 = 15\nuint8 WAKE                  = 16\nuint8 STOP_ALL_LIGHTS       = 17\nuint8 CHARGING              = 18\n\n# Signal state\nuint8 state\n";
    public static final String _TYPE = "ff_msgs/SignalState";

    Header getHeader();

    byte getState();

    void setHeader(Header header);

    void setState(byte b);
}
