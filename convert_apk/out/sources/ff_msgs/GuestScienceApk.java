package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;

public interface GuestScienceApk extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Message used to contain information about a guest science apk\n\n# Full apk name\nstring apk_name\n\n# Short (human readable) name of the apk\nstring short_name\n\n# Whether the apk is primary or secondary\nbool primary\n\n# List of commands the apk will accept\nff_msgs/GuestScienceCommand[] commands\n";
    public static final String _TYPE = "ff_msgs/GuestScienceApk";

    String getApkName();

    List<GuestScienceCommand> getCommands();

    boolean getPrimary();

    String getShortName();

    void setApkName(String str);

    void setCommands(List<GuestScienceCommand> list);

    void setPrimary(boolean z);

    void setShortName(String str);
}
