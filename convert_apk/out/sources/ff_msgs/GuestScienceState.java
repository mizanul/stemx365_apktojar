package ff_msgs;

import org.ros.internal.message.Message;
import std_msgs.Header;

public interface GuestScienceState extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Message used to tell which guest science apks are running\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Used for guest science config and state message synchronization on the ground\nint64 serial\n\n# Tells which apks are running. Used in conjection with the guest science config\nbool[] runningApks\n";
    public static final String _TYPE = "ff_msgs/GuestScienceState";

    Header getHeader();

    boolean[] getRunningApks();

    long getSerial();

    void setHeader(Header header);

    void setRunningApks(boolean[] zArr);

    void setSerial(long j);
}
