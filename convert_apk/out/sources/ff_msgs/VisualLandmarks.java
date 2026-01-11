package ff_msgs;

import geometry_msgs.Pose;
import java.util.List;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface VisualLandmarks extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# The observation from a camera image, of image coordinates\n# and associated 3D coordinates. Used for sparse mapping and AR tags.\n\nHeader header # header with timestamp\nuint32 camera_id # image ID, associated with registration pulse\ngeometry_msgs/Pose pose # estimated camera pose from features\nff_msgs/VisualLandmark[] landmarks # list of all landmarks\n";
    public static final String _TYPE = "ff_msgs/VisualLandmarks";

    int getCameraId();

    Header getHeader();

    List<VisualLandmark> getLandmarks();

    Pose getPose();

    void setCameraId(int i);

    void setHeader(Header header);

    void setLandmarks(List<VisualLandmark> list);

    void setPose(Pose pose);
}
