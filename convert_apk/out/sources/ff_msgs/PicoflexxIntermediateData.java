package ff_msgs;

import org.ros.internal.message.Message;
import sensor_msgs.Image;
import std_msgs.Header;

public interface PicoflexxIntermediateData extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.      #\n#\n# Message for encoding Picoflexx imtermediate data \n\nstd_msgs/Header header\nuint32[] frequency\nuint32[] exposure\nsensor_msgs/Image raw";
    public static final String _TYPE = "ff_msgs/PicoflexxIntermediateData";

    int[] getExposure();

    int[] getFrequency();

    Header getHeader();

    Image getRaw();

    void setExposure(int[] iArr);

    void setFrequency(int[] iArr);

    void setHeader(Header header);

    void setRaw(Image image);
}
