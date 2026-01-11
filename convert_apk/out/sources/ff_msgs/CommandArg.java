package ff_msgs;

import org.ros.internal.message.Message;

public interface CommandArg extends Message {
    public static final byte DATA_TYPE_BOOL = 0;
    public static final byte DATA_TYPE_DOUBLE = 1;
    public static final byte DATA_TYPE_FLOAT = 2;
    public static final byte DATA_TYPE_INT = 3;
    public static final byte DATA_TYPE_LONGLONG = 4;
    public static final byte DATA_TYPE_MAT33f = 7;
    public static final byte DATA_TYPE_STRING = 5;
    public static final byte DATA_TYPE_VEC3d = 6;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# An argument to a command sent through RAPID\n#\n# Note that this is approximating a union in DDS. However, this is an\n# inefficient union, and thus each instance will take up at least 89 bytes.\n# However, even with the maximum of 16 arguments to a command, we only have\n# about 1k extra data. I, tfmorse, am ok with that. Commands are rarely sent.\n\nuint8 DATA_TYPE_BOOL     = 0\nuint8 DATA_TYPE_DOUBLE   = 1\nuint8 DATA_TYPE_FLOAT    = 2\nuint8 DATA_TYPE_INT      = 3\nuint8 DATA_TYPE_LONGLONG = 4\nuint8 DATA_TYPE_STRING   = 5\nuint8 DATA_TYPE_VEC3d    = 6\nuint8 DATA_TYPE_MAT33f   = 7\n\nuint8 data_type\n\nbool b\nfloat64 d\nfloat32 f\nint32 i\nint64 ll\nstring s\nfloat64[3] vec3d\nfloat32[9] mat33f\n\n";
    public static final String _TYPE = "ff_msgs/CommandArg";

    boolean getB();

    double getD();

    byte getDataType();

    float getF();

    int getI();

    long getLl();

    float[] getMat33f();

    String getS();

    double[] getVec3d();

    void setB(boolean z);

    void setD(double d);

    void setDataType(byte b);

    void setF(float f);

    void setI(int i);

    void setLl(long j);

    void setMat33f(float[] fArr);

    void setS(String str);

    void setVec3d(double[] dArr);
}
