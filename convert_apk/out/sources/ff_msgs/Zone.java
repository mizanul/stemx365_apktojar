package ff_msgs;

import geometry_msgs.Vector3;
import org.ros.internal.message.Message;

public interface Zone extends Message {
    public static final byte CLUTTER = 2;
    public static final byte KEEPIN = 1;
    public static final byte KEEPOUT = 0;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# This message defines a zone, such as a Keepin or Keeoput.\n\nstring name                   # Name of zone\n\n# A name can refer to multiple zones. This is the index of the zone with respect\n# to the zone name\nint32 index\n\n# Zone type\nuint8 KEEPOUT = 0       # An area the freeflyer should stay out of\nuint8 KEEPIN  = 1       # An area the freeflyer can fly freely in \nuint8 CLUTTER = 2       # An area that the freeflyer should avoid due to clutter\n\nuint8 type              # Whether the zone is a keepin, keepout, or clutter\n\ngeometry_msgs/Vector3 min   # One corner of the zone\ngeometry_msgs/Vector3 max   # The opposite corner of the zone\n";
    public static final String _TYPE = "ff_msgs/Zone";

    int getIndex();

    Vector3 getMax();

    Vector3 getMin();

    String getName();

    byte getType();

    void setIndex(int i);

    void setMax(Vector3 vector3);

    void setMin(Vector3 vector3);

    void setName(String str);

    void setType(byte b);
}
