package ff_msgs;

import org.ros.internal.message.Message;

public interface JointSample extends Message {
    public static final byte JOINT_DISABLED = 1;
    public static final byte JOINT_ENABLED = 0;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# JointSample message, based off of rapid::JointSample\n\n# Flag values for joint status. \n# Joint is enabled\nuint8 JOINT_ENABLED    = 0      # Joint enabled\nuint8 JOINT_DISABLED   = 1      # Joint disabled\n\n\n# Angle position (in radians) of the joint\nfloat32 angle_pos\n\n# Angle velocity (in radians/sec) of the joint\nfloat32 angle_vel\n\n# Angle acceleration (in radians/sec^2) of the joint (not being used)\nfloat32 angle_acc\n\n# Current draw of joint motor\nfloat32 current\n\n# Torque sensed at the joint (not being used)\nfloat32 torque\n\n# Temperature of the joint (in Celsius)\nfloat32 temperature\n\n# Bit field representing the state of the joint\nuint16 status\n\n# Human-readable name\nstring name\n";
    public static final String _TYPE = "ff_msgs/JointSample";

    float getAngleAcc();

    float getAnglePos();

    float getAngleVel();

    float getCurrent();

    String getName();

    short getStatus();

    float getTemperature();

    float getTorque();

    void setAngleAcc(float f);

    void setAnglePos(float f);

    void setAngleVel(float f);

    void setCurrent(float f);

    void setName(String str);

    void setStatus(short s);

    void setTemperature(float f);

    void setTorque(float f);
}
