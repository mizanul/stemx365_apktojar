package ff_msgs;

import org.ros.internal.message.Message;

public interface AckStatus extends Message {
    public static final byte COMPLETED = 3;
    public static final byte EXECUTING = 1;
    public static final byte QUEUED = 0;
    public static final byte REQUEUED = 2;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Command status. Based off AckStatus in RAPID DDS\n\nuint8 QUEUED = 0      # Command is in a queue and waiting to be executed\nuint8 EXECUTING = 1   # Command is being executed\nuint8 REQUEUED = 2    # Command is paused and waiting to be restarted \nuint8 COMPLETED = 3   # Command is finished\n\nuint8 status          # Command status\n";
    public static final String _TYPE = "ff_msgs/AckStatus";

    byte getStatus();

    void setStatus(byte b);
}
