package ff_msgs;

import org.ros.internal.message.Message;

public interface CameraState extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# CameraState message, *MUST* be kept in sync with camera portion of\n# rapid::ext::astrobee::TelemetryState\n\n# nav_cam, dock_cam, etc.\nstring camera_name\n\n# streaming to ground\nbool streaming\n\n# image width\nuint16 stream_width\n# image height\nuint16 stream_height\n# Rate in Hz\nfloat32 stream_rate\n\n# recording to disk\nbool recording\n\n# image width\nuint16 record_width\n# image height\nuint16 record_height\n# Rate in Hz\nfloat32 record_rate\n\n# only for sci cam\nfloat32 bandwidth\n";
    public static final String _TYPE = "ff_msgs/CameraState";

    float getBandwidth();

    String getCameraName();

    short getRecordHeight();

    float getRecordRate();

    short getRecordWidth();

    boolean getRecording();

    short getStreamHeight();

    float getStreamRate();

    short getStreamWidth();

    boolean getStreaming();

    void setBandwidth(float f);

    void setCameraName(String str);

    void setRecordHeight(short s);

    void setRecordRate(float f);

    void setRecordWidth(short s);

    void setRecording(boolean z);

    void setStreamHeight(short s);

    void setStreamRate(float f);

    void setStreamWidth(short s);

    void setStreaming(boolean z);
}
