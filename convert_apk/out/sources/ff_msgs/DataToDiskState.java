package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;
import std_msgs.Header;

public interface DataToDiskState extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n\n# Data to disk state message used to let ground operators know which topics\n# are currently being recorded.\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Name of the latest data to disk file uploaded from the ground\nstring name\n\n# Whether the data bagger is recording a bag or not\nbool recording\n\n# An array containing information about the topics being recorded\nff_msgs/SaveSettings[] topic_save_settings\n";
    public static final String _TYPE = "ff_msgs/DataToDiskState";

    Header getHeader();

    String getName();

    boolean getRecording();

    List<SaveSettings> getTopicSaveSettings();

    void setHeader(Header header);

    void setName(String str);

    void setRecording(boolean z);

    void setTopicSaveSettings(List<SaveSettings> list);
}
