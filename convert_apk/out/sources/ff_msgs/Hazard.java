package ff_msgs;

import geometry_msgs.PointStamped;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface Hazard extends Message {
    public static final byte TYPE_KEEP_IN = 1;
    public static final byte TYPE_KEEP_OUT = 2;
    public static final byte TYPE_OBSTACLE = 3;
    public static final byte TYPE_UNKNOWN = 0;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Message used to notify choreographer of hazards\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Type of hazard\nuint8 type\nuint8 TYPE_UNKNOWN  = 0\nuint8 TYPE_KEEP_IN  = 1\nuint8 TYPE_KEEP_OUT = 2\nuint8 TYPE_OBSTACLE = 3\n\n# Spatio-tempral information about the hazard\ngeometry_msgs/PointStamped hazard";
    public static final String _TYPE = "ff_msgs/Hazard";

    PointStamped getHazard();

    Header getHeader();

    byte getType();

    void setHazard(PointStamped pointStamped);

    void setHeader(Header header);

    void setType(byte b);
}
