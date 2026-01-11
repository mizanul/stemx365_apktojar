package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;
import org.ros.message.Time;

public interface Fault extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Fault message is used to provide all the information about an occurring fault\n\ntime time_of_fault        # Time when fault occurred\n\nuint32 id                 # id specifying fault\n\nstring msg                # string specifying why the fault occurred\n\nff_msgs/FaultData[] data  # Data used for fault analysis\n";
    public static final String _TYPE = "ff_msgs/Fault";

    List<FaultData> getData();

    int getId();

    String getMsg();

    Time getTimeOfFault();

    void setData(List<FaultData> list);

    void setId(int i);

    void setMsg(String str);

    void setTimeOfFault(Time time);
}
