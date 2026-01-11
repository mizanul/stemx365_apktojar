package ff_msgs;

import org.ros.internal.message.Message;

public interface SetEkfInputRequest extends Message {
    public static final byte MODE_AR_TAGS = 1;
    public static final byte MODE_HANDRAIL = 2;
    public static final byte MODE_MAP_LANDMARKS = 0;
    public static final byte MODE_NONE = 3;
    public static final byte MODE_PERCH = 4;
    public static final byte MODE_TRUTH = 5;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# This message sets the inputs to be used by the EKF.\n\nuint8 MODE_MAP_LANDMARKS = 0 # use mapped landmarks\nuint8 MODE_AR_TAGS       = 1 # use AR tags\nuint8 MODE_HANDRAIL      = 2 # use the handrail\nuint8 MODE_NONE          = 3 # no localization pipeline\nuint8 MODE_PERCH         = 4 # perch localization\nuint8 MODE_TRUTH         = 5 # ground truth localization\n\nuint8 mode # the mode to use in the EKF\n";
    public static final String _TYPE = "ff_msgs/SetEkfInputRequest";

    byte getMode();

    void setMode(byte b);
}
