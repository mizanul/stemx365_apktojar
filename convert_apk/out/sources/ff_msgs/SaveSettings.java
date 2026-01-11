package ff_msgs;

import org.ros.internal.message.Message;

public interface SaveSettings extends Message {
    public static final byte DELAYED = 1;
    public static final byte IMMEDIATE = 0;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n\n# The save settings message contains information about the topics currently\n# being recorded.\n\n# Name of topic\nstring topic_name\n\n# Topic saved to disk; upon docking it is downlinked\nuint8 IMMEDIATE   = 0\n\n# Topic saved to disk; upon docking it is transferred to ISS server for later\n# downlink\nuint8 DELAYED     = 1\n\n# Downlink option indicates if and when the data in the rostopic is downlinked\nuint8 downlinkOption\n\n# Times per second to save the data (Hz)\nfloat32 frequency\n";
    public static final String _TYPE = "ff_msgs/SaveSettings";

    byte getDownlinkOption();

    float getFrequency();

    String getTopicName();

    void setDownlinkOption(byte b);

    void setFrequency(float f);

    void setTopicName(String str);
}
