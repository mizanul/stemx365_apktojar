package ff_msgs;

import org.jboss.netty.buffer.ChannelBuffer;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface AvailableRobots extends Message {
    public static final byte BUMBLE = 1;
    public static final byte HONEY = 2;
    public static final byte QUEEN = 4;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n#\n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Message containing the Astrobee's connected to the current Astrobee \n\nstd_msgs/Header header # header with time stamp\n\nuint8 BUMBLE = 1\nuint8 HONEY  = 2\nuint8 QUEEN  = 4\n\n# Robots connected to the current robot through DDS\nuint8[] available_robots\n";
    public static final String _TYPE = "ff_msgs/AvailableRobots";

    ChannelBuffer getAvailableRobots();

    Header getHeader();

    void setAvailableRobots(ChannelBuffer channelBuffer);

    void setHeader(Header header);
}
