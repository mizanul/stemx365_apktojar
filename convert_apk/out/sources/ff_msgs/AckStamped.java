package ff_msgs;

import org.ros.internal.message.Message;
import std_msgs.Header;

public interface AckStamped extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Message used to send an acknowledgement for commands received. Based off of\n# Ack in RAPID DDS\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Id of the command being acknowledged\nstring cmd_id\n\n# Status of the command\nff_msgs/AckStatus status\n\n# Completed status of the command\nff_msgs/AckCompletedStatus completed_status\n\n# If the command fails to execute, message will contain information on why it\n# failed.\nstring message\n";
    public static final String _TYPE = "ff_msgs/AckStamped";

    String getCmdId();

    AckCompletedStatus getCompletedStatus();

    Header getHeader();

    String getMessage();

    AckStatus getStatus();

    void setCmdId(String str);

    void setCompletedStatus(AckCompletedStatus ackCompletedStatus);

    void setHeader(Header header);

    void setMessage(String str);

    void setStatus(AckStatus ackStatus);
}
