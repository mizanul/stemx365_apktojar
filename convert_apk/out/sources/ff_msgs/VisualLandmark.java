package ff_msgs;

import org.ros.internal.message.Message;

public interface VisualLandmark extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# A single observation of a feature, with an image coordinate\n# and known 3D coordinate\n\nfloat32 x # known 3D position x\nfloat32 y # known 3D position y\nfloat32 z # known 3D position z\nfloat32 u # feature image coordinate x\nfloat32 v # feature image coordinate y\n";
    public static final String _TYPE = "ff_msgs/VisualLandmark";

    float getU();

    float getV();

    float getX();

    float getY();

    float getZ();

    void setU(float f);

    void setV(float f);

    void setX(float f);

    void setY(float f);

    void setZ(float f);
}
