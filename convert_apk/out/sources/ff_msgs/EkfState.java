package ff_msgs;

import geometry_msgs.Pose;
import geometry_msgs.Vector3;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface EkfState extends Message {
    public static final byte CONFIDENCE_GOOD = 0;
    public static final byte CONFIDENCE_LOST = 2;
    public static final byte CONFIDENCE_POOR = 1;
    public static final byte STATUS_INVALID = -1;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n#\n# All rights reserved.\n#\n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n#\n#     http://www.apache.org/licenses/LICENSE-2.0\n#\n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# An observation of a handrail from a depth image.\n\nstd_msgs/Header header # header with timestamp\nstring child_frame_id # frame ID\n\ngeometry_msgs/Pose pose # robot body pose\n\n# m/s\ngeometry_msgs/Vector3 velocity # the body velocity\n\n# rad/s\ngeometry_msgs/Vector3 omega # body rotational velocity\ngeometry_msgs/Vector3 gyro_bias # estimated gyro bias\n\n# m/s/s\ngeometry_msgs/Vector3 accel # acceleration in body frame\ngeometry_msgs/Vector3 accel_bias # estimated accel bias\n\n# Filter Health\n\n# covariance diagonal. 1-3 orientation, 4-6 gyro bias, 7-9 velocity, 10-12 accel bias, 13-15 position\nfloat32[15] cov_diag\n# confidence in EKF. 0 is good, 1 is a bit confused, 2 is lost\nuint8 confidence\nuint8 CONFIDENCE_GOOD = 0\t# Tracking well\nuint8 CONFIDENCE_POOR = 1\t# Tracking poorly\nuint8 CONFIDENCE_LOST = 2\t# We are lost\n\nuint8 aug_state_enum # bitmask of augmented states intialized\n\n# status byte sent by GNC\nuint8 status\nuint8 STATUS_INVALID = 255\t# invalid\n\n# optical flow features this frame (0 if no update)\nuint8 of_count\n# ml features this frame (0 if no update)\nuint8 ml_count\n\n# Global Handrail Pose\ngeometry_msgs/Pose hr_global_pose\n\n# mahalanobis distances for features\nfloat32[50] ml_mahal_dists\n\n# Are we busy estimating the bias?\nbool estimating_bias\n";
    public static final String _TYPE = "ff_msgs/EkfState";

    Vector3 getAccel();

    Vector3 getAccelBias();

    byte getAugStateEnum();

    String getChildFrameId();

    byte getConfidence();

    float[] getCovDiag();

    boolean getEstimatingBias();

    Vector3 getGyroBias();

    Header getHeader();

    Pose getHrGlobalPose();

    byte getMlCount();

    float[] getMlMahalDists();

    byte getOfCount();

    Vector3 getOmega();

    Pose getPose();

    byte getStatus();

    Vector3 getVelocity();

    void setAccel(Vector3 vector3);

    void setAccelBias(Vector3 vector3);

    void setAugStateEnum(byte b);

    void setChildFrameId(String str);

    void setConfidence(byte b);

    void setCovDiag(float[] fArr);

    void setEstimatingBias(boolean z);

    void setGyroBias(Vector3 vector3);

    void setHeader(Header header);

    void setHrGlobalPose(Pose pose);

    void setMlCount(byte b);

    void setMlMahalDists(float[] fArr);

    void setOfCount(byte b);

    void setOmega(Vector3 vector3);

    void setPose(Pose pose);

    void setStatus(byte b);

    void setVelocity(Vector3 vector3);
}
