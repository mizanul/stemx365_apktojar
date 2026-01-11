package ff_msgs;

import org.jboss.netty.buffer.ChannelBuffer;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface GuestScienceData extends Message {
    public static final byte BINARY = 2;
    public static final byte JSON = 1;
    public static final byte STRING = 0;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Message used to send guest science data to the ground\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Full name of apk\nstring apk_name\n\n# Types of data stored in the data array\nuint8 STRING  = 0\nuint8 JSON    = 1\nuint8 BINARY  = 2\n\n# Type of data being sent, see above\nuint8 data_type\n\n# String to classify the kind of data\nstring topic\n\n# Data from the apk, rapid type is an octet sequence where an octet is an 8-bit\n# quantity\nuint8[] data\n";
    public static final String _TYPE = "ff_msgs/GuestScienceData";

    String getApkName();

    ChannelBuffer getData();

    byte getDataType();

    Header getHeader();

    String getTopic();

    void setApkName(String str);

    void setData(ChannelBuffer channelBuffer);

    void setDataType(byte b);

    void setHeader(Header header);

    void setTopic(String str);
}
