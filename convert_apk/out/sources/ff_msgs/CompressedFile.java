package ff_msgs;

import org.jboss.netty.buffer.ChannelBuffer;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface CompressedFile extends Message {
    public static final byte TYPE_BZ2 = 2;
    public static final byte TYPE_DEFLATE = 1;
    public static final byte TYPE_GZ = 3;
    public static final byte TYPE_NONE = 0;
    public static final byte TYPE_ZIP = 4;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# A compressed file that represents a CompressedFile from\n# DDS. Used to send a compressed files to the executive.\n# DDS is constrained to a 128k chunk of data, we are only\n# limited by our imaginations (and the size of an unsigned\n# 32-bit integer)\n\n# Header \nstd_msgs/Header header\n\n# Unique file identification\nint32 id\n\nuint8 TYPE_NONE = 0\nuint8 TYPE_DEFLATE = 1\nuint8 TYPE_BZ2 = 2\nuint8 TYPE_GZ = 3\nuint8 TYPE_ZIP = 4\n\n# Type of compression\nuint8 type\n\n# File contents\nuint8[] file\n";
    public static final String _TYPE = "ff_msgs/CompressedFile";

    ChannelBuffer getFile();

    Header getHeader();

    int getId();

    byte getType();

    void setFile(ChannelBuffer channelBuffer);

    void setHeader(Header header);

    void setId(int i);

    void setType(byte b);
}
