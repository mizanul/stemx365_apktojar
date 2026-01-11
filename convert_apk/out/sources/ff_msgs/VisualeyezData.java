package ff_msgs;

import geometry_msgs.Vector3;
import org.ros.internal.message.Message;

public interface VisualeyezData extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Raw Visualeyez data.\n\nuint8 tcmid                         # Transmission control module ID\nuint8 ledid                         # Light emitting diode ID\ngeometry_msgs/Vector3 position      # Coordinate ";
    public static final String _TYPE = "ff_msgs/VisualeyezData";

    byte getLedid();

    Vector3 getPosition();

    byte getTcmid();

    void setLedid(byte b);

    void setPosition(Vector3 vector3);

    void setTcmid(byte b);
}
