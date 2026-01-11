package ff_msgs;

import org.ros.internal.message.Message;

public interface VisualeyezConfig extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# This message tells the Visualeyez system to calibrate a given target\n\nuint8 action\nuint8 TRACK       = 0     # Go back to tracking mode\nuint8 RECORD      = 1     # Record data for some target called \"name\"\nuint8 CALIBRATE   = 2     # Run calibration for the recorded data\nuint8 LOAD        = 3     # Load calibration file called \"name\"\nuint8 SAVE        = 4     # Save calibration file called \"name\"\n\n# Only used for RECORD, LOAD, SAVE\nstring name\n\n# Whether to publish a TF2 messsage\nbool pub_tf\n\n---\n\nbool success\n";
    public static final String _TYPE = "ff_msgs/VisualeyezConfig";
}
