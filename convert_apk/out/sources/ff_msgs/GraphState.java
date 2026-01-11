package ff_msgs;

import geometry_msgs.Pose;
import geometry_msgs.Vector3;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface GraphState extends Message {
    public static final byte CONFIDENCE_GOOD = 0;
    public static final byte CONFIDENCE_LOST = 2;
    public static final byte CONFIDENCE_POOR = 1;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n#\n# All rights reserved.\n#\n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n#\n#     http://www.apache.org/licenses/LICENSE-2.0\n#\n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n\nstd_msgs/Header header # header with timestamp\nstring child_frame_id # frame ID\n# State Estimates\ngeometry_msgs/Pose pose # world_T_body \ngeometry_msgs/Vector3 velocity # body velocity\ngeometry_msgs/Vector3 gyro_bias # estimated gyro bias\ngeometry_msgs/Vector3 accel_bias # estimated accel bias\n# Covariances/Confidences\n# covariance diagonal. 1-3 orientation, 4-6 gyro bias, 7-9 velocity, 10-12 accel bias, 13-15 position\nfloat32[15] cov_diag\n# confidence in estimate. 0 is good, 1 is a bit confused, 2 is lost\nuint8 confidence\nuint8 CONFIDENCE_GOOD = 0\t# Tracking well\nuint8 CONFIDENCE_POOR = 1\t# Tracking poorly\nuint8 CONFIDENCE_LOST = 2\t# We are lost\n# Stats\nuint32 num_detected_of_features  \nuint32 num_detected_ar_features \nuint32 num_detected_ml_features \nuint32 iterations # Optimization iterations\nfloat32 optimization_time\nfloat32 update_time # Include optimization_time and other operations to add data to graph\nfloat32 callbacks_time # Includes processing msgs and their callbacks\nfloat32 nodelet_runtime # Total runtime of nodelet iteration.  Includes update and callback time\nuint32 num_factors\nuint32 num_of_factors\nuint32 num_ml_projection_factors\nuint32 num_ml_pose_factors\nuint32 num_states\n# Status\nbool standstill\nbool estimating_bias # Are we busy estimating the bias?\nuint8 fan_speed_mode # Used for imu filtering\n";
    public static final String _TYPE = "ff_msgs/GraphState";

    Vector3 getAccelBias();

    float getCallbacksTime();

    String getChildFrameId();

    byte getConfidence();

    float[] getCovDiag();

    boolean getEstimatingBias();

    byte getFanSpeedMode();

    Vector3 getGyroBias();

    Header getHeader();

    int getIterations();

    float getNodeletRuntime();

    int getNumDetectedArFeatures();

    int getNumDetectedMlFeatures();

    int getNumDetectedOfFeatures();

    int getNumFactors();

    int getNumMlPoseFactors();

    int getNumMlProjectionFactors();

    int getNumOfFactors();

    int getNumStates();

    float getOptimizationTime();

    Pose getPose();

    boolean getStandstill();

    float getUpdateTime();

    Vector3 getVelocity();

    void setAccelBias(Vector3 vector3);

    void setCallbacksTime(float f);

    void setChildFrameId(String str);

    void setConfidence(byte b);

    void setCovDiag(float[] fArr);

    void setEstimatingBias(boolean z);

    void setFanSpeedMode(byte b);

    void setGyroBias(Vector3 vector3);

    void setHeader(Header header);

    void setIterations(int i);

    void setNodeletRuntime(float f);

    void setNumDetectedArFeatures(int i);

    void setNumDetectedMlFeatures(int i);

    void setNumDetectedOfFeatures(int i);

    void setNumFactors(int i);

    void setNumMlPoseFactors(int i);

    void setNumMlProjectionFactors(int i);

    void setNumOfFactors(int i);

    void setNumStates(int i);

    void setOptimizationTime(float f);

    void setPose(Pose pose);

    void setStandstill(boolean z);

    void setUpdateTime(float f);

    void setVelocity(Vector3 vector3);
}
