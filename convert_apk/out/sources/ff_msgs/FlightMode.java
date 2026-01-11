package ff_msgs;

import geometry_msgs.Vector3;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface FlightMode extends Message {
    public static final byte SPEED_AGGRESSIVE = 3;
    public static final byte SPEED_MAX = 3;
    public static final byte SPEED_MIN = 0;
    public static final byte SPEED_NOMINAL = 2;
    public static final byte SPEED_OFF = 0;
    public static final byte SPEED_QUIET = 1;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n#\n# All rights reserved.\n#\n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n#\n#     http://www.apache.org/licenses/LICENSE-2.0\n#\n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# This message captures all information in a flight mode\n\nHeader header                     # Metadata\n\nstring name                       # Name of the flight mode\n\nbool control_enabled              # Is control enabled?\n\nfloat32 collision_radius          # Collision radius in meters\n\n# Tolerances (all in SI units)\nfloat32 tolerance_pos_endpoint    # Endpoint position tolerance in m\nfloat32 tolerance_pos             # Position tolerance in m\nfloat32 tolerance_vel             # Velocity tolerance in m/s\nfloat32 tolerance_att             # Attitude tolerance in rads\nfloat32 tolerance_omega           # Angular acceleration tolerance in rad/s\nfloat32 tolerance_time            # Acceptable lag betwee TX and RX of control\n\n# Controller gains\ngeometry_msgs/Vector3 att_kp      # Positional proportional constant\ngeometry_msgs/Vector3 att_ki      # Positional integrative constant\ngeometry_msgs/Vector3 omega_kd    # Attidue derivative constant\ngeometry_msgs/Vector3 pos_kp      # Positional proportional contant\ngeometry_msgs/Vector3 pos_ki      # Positional integrative constant\ngeometry_msgs/Vector3 vel_kd      # Positional derivative constant\n\n# Hard limit on planning\nfloat32 hard_limit_vel            # Position tolerance in m/s\nfloat32 hard_limit_accel          # Position tolerance in m/s^2\nfloat32 hard_limit_omega          # Position tolerance in rads/s\nfloat32 hard_limit_alpha          # Position tolerance in rads/s^2\n\n# Impeller speed\nuint8 speed                       # Current speed gain\nuint8 SPEED_MIN        = 0        # Min acceptable gain\nuint8 SPEED_OFF        = 0        # Blowers off\nuint8 SPEED_QUIET      = 1        # Quiet mode\nuint8 SPEED_NOMINAL    = 2        # Nomainal mode\nuint8 SPEED_AGGRESSIVE = 3        # Aggressive mode\nuint8 SPEED_MAX        = 3        # Max acceptable gain\n";
    public static final String _TYPE = "ff_msgs/FlightMode";

    Vector3 getAttKi();

    Vector3 getAttKp();

    float getCollisionRadius();

    boolean getControlEnabled();

    float getHardLimitAccel();

    float getHardLimitAlpha();

    float getHardLimitOmega();

    float getHardLimitVel();

    Header getHeader();

    String getName();

    Vector3 getOmegaKd();

    Vector3 getPosKi();

    Vector3 getPosKp();

    byte getSpeed();

    float getToleranceAtt();

    float getToleranceOmega();

    float getTolerancePos();

    float getTolerancePosEndpoint();

    float getToleranceTime();

    float getToleranceVel();

    Vector3 getVelKd();

    void setAttKi(Vector3 vector3);

    void setAttKp(Vector3 vector3);

    void setCollisionRadius(float f);

    void setControlEnabled(boolean z);

    void setHardLimitAccel(float f);

    void setHardLimitAlpha(float f);

    void setHardLimitOmega(float f);

    void setHardLimitVel(float f);

    void setHeader(Header header);

    void setName(String str);

    void setOmegaKd(Vector3 vector3);

    void setPosKi(Vector3 vector3);

    void setPosKp(Vector3 vector3);

    void setSpeed(byte b);

    void setToleranceAtt(float f);

    void setToleranceOmega(float f);

    void setTolerancePos(float f);

    void setTolerancePosEndpoint(float f);

    void setToleranceTime(float f);

    void setToleranceVel(float f);

    void setVelKd(Vector3 vector3);
}
