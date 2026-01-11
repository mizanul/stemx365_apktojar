package ff_msgs;

import org.ros.internal.message.Message;
import std_msgs.Header;

public interface LocalizationState extends Message {
    public static final int BIAS_WAITING_FOR_FILTER = 5;
    public static final int DISABLED = 1;
    public static final int INITIALIZING = 0;
    public static final int LOCALIZING = 2;
    public static final int RESET_WAITING_FOR_FILTER = 6;
    public static final int SWITCH_WAITING_FOR_FILTER = 4;
    public static final int SWITCH_WAITING_FOR_PIPELINE = 3;
    public static final int UNSTABLE = 7;
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n#\n# All rights reserved.\n#\n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n#\n#     http://www.apache.org/licenses/LICENSE-2.0\n#\n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# The state of the localization system\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Tee current state\nint32 state                                 # Current state\nint32 INITIALIZING                    = 0   # Waiting on dependencies\nint32 DISABLED                        = 1   # Localization disabled\nint32 LOCALIZING                      = 2   # Localization enabled\nint32 SWITCH_WAITING_FOR_PIPELINE     = 3   # Waiting for pipeline to stabilize\nint32 SWITCH_WAITING_FOR_FILTER       = 4   # Waiting for filter to stabilize\nint32 BIAS_WAITING_FOR_FILTER         = 5   # Waiting for bias estimation\nint32 RESET_WAITING_FOR_FILTER        = 6   # Waiting for EKF stability\nint32 UNSTABLE                        = 7   # Fallback pipeline unstable\n\n# A human readable version of the (event) -> [state] transition\nstring fsm_event\nstring fsm_state\n\n# The current localization pipeline being used\nff_msgs/LocalizationPipeline pipeline\n";
    public static final String _TYPE = "ff_msgs/LocalizationState";

    String getFsmEvent();

    String getFsmState();

    Header getHeader();

    LocalizationPipeline getPipeline();

    int getState();

    void setFsmEvent(String str);

    void setFsmState(String str);

    void setHeader(Header header);

    void setPipeline(LocalizationPipeline localizationPipeline);

    void setState(int i);
}
