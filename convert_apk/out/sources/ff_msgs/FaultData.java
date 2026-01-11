package ff_msgs;

import org.ros.internal.message.Message;

public interface FaultData extends Message {
    public static final byte DATA_TYPE_FLOAT = 0;
    public static final byte DATA_TYPE_INT = 1;
    public static final byte DATA_TYPE_STRING = 2;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n# \n# Fault data messsage contains information of why the fault occurred\n\nuint8 DATA_TYPE_FLOAT   = 0   # Data in this msg is of type float\nuint8 DATA_TYPE_INT     = 1   # Data in this msg is of type int\nuint8 DATA_TYPE_STRING  = 2   # Data in this msg is of type string\n\nstring key  # Specifies what the data in the msg is, can only be 32 chars long\n\nuint8 data_type   # Specifies the type of data in the message\n\nfloat32 f   # Value used for fault analysis, data_type must be 0\nint32 i     # Value used for fault analysis, data_type must be 1\nstring s    # String used for fault analysis, data_type must be 2\n";
    public static final String _TYPE = "ff_msgs/FaultData";

    byte getDataType();

    float getF();

    int getI();

    String getKey();

    String getS();

    void setDataType(byte b);

    void setF(float f);

    void setI(int i);

    void setKey(String str);

    void setS(String str);
}
