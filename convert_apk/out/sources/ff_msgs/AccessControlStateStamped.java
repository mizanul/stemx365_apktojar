package ff_msgs;

import org.ros.internal.message.Message;
import std_msgs.Header;

public interface AccessControlStateStamped extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# State of the access control node. Loosely based off of AccessControlState.idl \n# from RAPID.\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Name of operator in control of the robot\nstring controller\n\n# String that the access control node generates upon receiving a request control\n# command. Cookie will be blank after a successful grab control command.\nstring cookie\n";
    public static final String _TYPE = "ff_msgs/AccessControlStateStamped";

    String getController();

    String getCookie();

    Header getHeader();

    void setController(String str);

    void setCookie(String str);

    void setHeader(Header header);
}
