package ff_msgs;

import geometry_msgs.Point;
import geometry_msgs.Point32;
import geometry_msgs.Pose;
import java.util.List;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface DepthLandmarks extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n#\n# All rights reserved.\n#\n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n#\n#     http://www.apache.org/licenses/LICENSE-2.0\n#\n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# An observation of a handrail from a depth image.\n\nHeader header                                  # Image header, with time stamp\nuint32 camera_id                               # Image ID, associated with registration\nuint8 end_seen                                 # Whether the handrail endpoint was detected\nuint8 update_global_pose                       # Whether to update the global pose\ngeometry_msgs/Pose sensor_T_handrail           # Handrail center in the sensor frame\ngeometry_msgs/Point32[] sensor_t_line_points   # Detected line points\ngeometry_msgs/Point[] sensor_t_line_endpoints  # Detected line endpoints\ngeometry_msgs/Point32[] sensor_t_plane_points  # Detected plane points\nff_msgs/DepthLandmark[] landmarks              # List of landmarks seen TODO(rsoussan): This should be removed\n";
    public static final String _TYPE = "ff_msgs/DepthLandmarks";

    int getCameraId();

    byte getEndSeen();

    Header getHeader();

    List<DepthLandmark> getLandmarks();

    Pose getSensorTHandrail();

    List<Point> getSensorTLineEndpoints();

    List<Point32> getSensorTLinePoints();

    List<Point32> getSensorTPlanePoints();

    byte getUpdateGlobalPose();

    void setCameraId(int i);

    void setEndSeen(byte b);

    void setHeader(Header header);

    void setLandmarks(List<DepthLandmark> list);

    void setSensorTHandrail(Pose pose);

    void setSensorTLineEndpoints(List<Point> list);

    void setSensorTLinePoints(List<Point32> list);

    void setSensorTPlanePoints(List<Point32> list);

    void setUpdateGlobalPose(byte b);
}
