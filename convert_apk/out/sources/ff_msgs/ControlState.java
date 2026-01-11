package ff_msgs;

import geometry_msgs.Pose;
import geometry_msgs.Twist;
import org.ros.internal.message.Message;
import org.ros.message.Time;

public interface ControlState extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Full state vector containing Time, Pose, Vel, and Accel\n# \n# when {time}\n# flight_mode {string} - disctates, gains, tolerances, etc.\n# pose {Point position, Quaternion orientation}\n# twist {Vector3 linear, Vector3 angular}\n# accel {Vector3 linear, Vector3 angular}\n\ntime when\ngeometry_msgs/Pose pose\ngeometry_msgs/Twist twist\ngeometry_msgs/Twist accel\n";
    public static final String _TYPE = "ff_msgs/ControlState";

    Twist getAccel();

    Pose getPose();

    Twist getTwist();

    Time getWhen();

    void setAccel(Twist twist);

    void setPose(Pose pose);

    void setTwist(Twist twist);

    void setWhen(Time time);
}
