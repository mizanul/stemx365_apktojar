package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface CommandStamped extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Command Message, loosely based off of the RAPID Command.idl\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Command name\nstring cmd_name\n\n# Unique identifier for command = unique counter + participant + timestamp\nstring cmd_id\n\n# Source of the command, either operators, the system monitor or guest science\nstring cmd_src\n\n# Origin of the command, ground for operators, astrobee for another astrobee,\n# sys_monitor for fault responses, and guest_science for guest science\n# commands\nstring cmd_origin\n\n# Name of subsystem the command is going to (not used but kept to be consistant\n# with the command idl)\nstring subsys_name\n\n# Arguments for the command \nff_msgs/CommandArg[] args\n";
    public static final String _TYPE = "ff_msgs/CommandStamped";

    List<CommandArg> getArgs();

    String getCmdId();

    String getCmdName();

    String getCmdOrigin();

    String getCmdSrc();

    Header getHeader();

    String getSubsysName();

    void setArgs(List<CommandArg> list);

    void setCmdId(String str);

    void setCmdName(String str);

    void setCmdOrigin(String str);

    void setCmdSrc(String str);

    void setHeader(Header header);

    void setSubsysName(String str);
}
