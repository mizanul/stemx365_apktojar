package ff_msgs;

import org.ros.internal.message.Message;

public interface AckCompletedStatus extends Message {
    public static final byte BAD_SYNTAX = 2;
    public static final byte CANCELED = 4;
    public static final byte EXEC_FAILED = 3;
    public static final byte NOT = 0;

    /* renamed from: OK */
    public static final byte f0OK = 1;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Completed command status. Based on AckCompletedStatus from RAPID DDS\n\nuint8 NOT = 0           # Command not completed\nuint8 OK = 1            # Command completed successfully\nuint8 BAD_SYNTAX = 2    # Command not recognized, bad parameters, etc.\nuint8 EXEC_FAILED = 3   # Command failed to execute\nuint8 CANCELED = 4      # Command was canceled by operator\n\n# Completed command status\nuint8 status\n";
    public static final String _TYPE = "ff_msgs/AckCompletedStatus";

    byte getStatus();

    void setStatus(byte b);
}
