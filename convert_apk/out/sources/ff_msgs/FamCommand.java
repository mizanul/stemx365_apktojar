package ff_msgs;

import geometry_msgs.Vector3;
import geometry_msgs.Wrench;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface FamCommand extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Command sent from control to the FAM.\n\nstd_msgs/Header header # header with time stamp\n\n# force and torque\ngeometry_msgs/Wrench wrench\n# linear acceleration (wrench w/out estimated mass)\ngeometry_msgs/Vector3 accel\n# angular accceleration (wrench w/out estimated mass)\ngeometry_msgs/Vector3 alpha\n\n# status byte from GNC ICD\nuint8 status\n\n# position error\ngeometry_msgs/Vector3 position_error\n# integrated position error\ngeometry_msgs/Vector3 position_error_integrated\n\n# attitude error\ngeometry_msgs/Vector3 attitude_error\n# integrated attitude error\ngeometry_msgs/Vector3 attitude_error_integrated\n# magnitude of attitude error\nfloat32 attitude_error_mag\n\n# control mode from GNC ICD\nuint8 control_mode\n\n";
    public static final String _TYPE = "ff_msgs/FamCommand";

    Vector3 getAccel();

    Vector3 getAlpha();

    Vector3 getAttitudeError();

    Vector3 getAttitudeErrorIntegrated();

    float getAttitudeErrorMag();

    byte getControlMode();

    Header getHeader();

    Vector3 getPositionError();

    Vector3 getPositionErrorIntegrated();

    byte getStatus();

    Wrench getWrench();

    void setAccel(Vector3 vector3);

    void setAlpha(Vector3 vector3);

    void setAttitudeError(Vector3 vector3);

    void setAttitudeErrorIntegrated(Vector3 vector3);

    void setAttitudeErrorMag(float f);

    void setControlMode(byte b);

    void setHeader(Header header);

    void setPositionError(Vector3 vector3);

    void setPositionErrorIntegrated(Vector3 vector3);

    void setStatus(byte b);

    void setWrench(Wrench wrench);
}
