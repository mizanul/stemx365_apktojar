package ff_msgs;

import org.ros.internal.message.Message;
import org.ros.message.Time;

public interface Performance extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# Statistics used to measure performance.\n\ntime stamp\nfloat32 count\nfloat32 last\nfloat32 min\nfloat32 max\nfloat32 mean\nfloat32 stddev\nfloat32 var\n";
    public static final String _TYPE = "ff_msgs/Performance";

    float getCount();

    float getLast();

    float getMax();

    float getMean();

    float getMin();

    Time getStamp();

    float getStddev();

    float getVar();

    void setCount(float f);

    void setLast(float f);

    void setMax(float f);

    void setMean(float f);

    void setMin(float f);

    void setStamp(Time time);

    void setStddev(float f);

    void setVar(float f);
}
