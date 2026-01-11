package ff_msgs;

import org.ros.internal.message.Message;
import std_msgs.Header;

public interface ControlCommand extends Message {
    public static final byte MODE_IDLE = 0;
    public static final byte MODE_NOMINAL = 2;
    public static final byte MODE_STOP = 1;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# two copies of a ControlState message, plus a header\n# mode: the current mode we are in. only two states defined so far\n# current: the current ControlState trajectory we should aim for\n# next: the next ControlState trajectory, just in case\n\nstd_msgs/Header header\nuint8 mode\nuint8 MODE_IDLE = 0\nuint8 MODE_STOP = 1\nuint8 MODE_NOMINAL = 2\nff_msgs/ControlState current\nff_msgs/ControlState next\n";
    public static final String _TYPE = "ff_msgs/ControlCommand";

    ControlState getCurrent();

    Header getHeader();

    byte getMode();

    ControlState getNext();

    void setCurrent(ControlState controlState);

    void setHeader(Header header);

    void setMode(byte b);

    void setNext(ControlState controlState);
}
