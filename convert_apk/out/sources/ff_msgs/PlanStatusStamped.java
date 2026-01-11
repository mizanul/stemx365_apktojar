package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface PlanStatusStamped extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Plan status message. Based off of PlanStatus from DDS. Note that while in\n# ROS we use an unbounded array, we are actually limited to ~64 previous\n# status messages in the DDS type.\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Name of plan\nstring name\n\nint32 point                 # Current station or segment\nint32 command               # Current subcommand within station/segment or -1\nff_msgs/AckStatus status    # Status of the currently executing plan element\n\nff_msgs/Status[] history    # Completion status of the last 64 plan elements\n";
    public static final String _TYPE = "ff_msgs/PlanStatusStamped";

    int getCommand();

    Header getHeader();

    List<Status> getHistory();

    String getName();

    int getPoint();

    AckStatus getStatus();

    void setCommand(int i);

    void setHeader(Header header);

    void setHistory(List<Status> list);

    void setName(String str);

    void setPoint(int i);

    void setStatus(AckStatus ackStatus);
}
