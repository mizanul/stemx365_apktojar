package ff_msgs;

import org.ros.internal.message.Message;
import std_msgs.Header;

public interface AgentStateStamped extends Message {
    public static final String _DEFINITION = "# Copyright (c) 2017, United States Government, as represented by the\n# Administrator of the National Aeronautics and Space Administration.\n# \n# All rights reserved.\n# \n# The Astrobee platform is licensed under the Apache License, Version 2.0\n# (the \"License\"); you may not use this file except in compliance with the\n# License. You may obtain a copy of the License at\n# \n#     http://www.apache.org/licenses/LICENSE-2.0\n# \n# Unless required by applicable law or agreed to in writing, software\n# distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT\n# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the\n# License for the specific language governing permissions and limitations\n# under the License.\n#\n# State of the Astrobee, based off of rapid::ext::astrobee::AgentState\n\n# Header with timestamp\nstd_msgs/Header header\n\n# Operating state of the Astrobee\nff_msgs/OpState operating_state\n\n# Plan execution state. State is idle when there is no plan to be executed. Once\n# a plan is uploaded, the state change to paused. Upon a run plan command, the\n# state will change to executing. If a stop or pause plan command is received or\n# a fault occurs, the state will be set to pause. Once the plan is completed,\n# the state will go back to idle\nff_msgs/ExecState plan_execution_state\n\n# Guest science state. If a primary guest science apk is started, the state\n# will go from idle to executing. Once the primarty apk is finished, the state\n# will go back to idle\nff_msgs/ExecState guest_science_state\n\n# Mobility state of the Astrobee\nff_msgs/MobilityState mobility_state\n\n# Proximity to the dock when docking and undocking. Proximity to a handrail when\n# perching or unperching. 0 the rest of the time.\nfloat32 proximity\n\n# Name of profile configuration, i.e. Nominal, IgnoreObstacles, Faceforward,\n# Quiet, etc. Profiles specify stuff like target velocity and acceleration,\n# collision distance, etc.\nstring profile_name\n\n#Defines GN&C gains, hard limits, tolerances, etc.\nstring flight_mode\n\n# Maximum linear velocity to target while translating\nfloat32 target_linear_velocity\n# Maximum linear acceleration to target while translating\nfloat32 target_linear_accel\n# Maximum angular velocity to target while rotating\nfloat32 target_angular_velocity\n# Maximum angular acceleration to target while rotating\nfloat32 target_angular_accel\n# Minimum distance margin to maintain away from obstacles\nfloat32 collision_distance\n\n# Specifies whether the Astrobee is allowed to fly blind i.e. not faceforward\nbool holonomic_enabled\n\n# Specifies whether the Astrobee is checking for obstacles\nbool check_obstacles\n\n# Specifies whether the Astrobee is checking the keepin and keepout zones\nbool check_zones\n\n# Specifies whether the Astrobee is allowed to auto return. Please note,\n# Astrobee will only use this flags when deciding if it should auto return. If\n# the astrobee gets a command to auto return from the operator or guest science,\n# it will auto return without checking this flag\nbool auto_return_enabled\n\n# Specifies whether the choreographer should execute a segment immediately or\n# based on the time stamp in the segement\nbool immediate_enabled\n\n# Specifies the current planner being used\nstring planner\n\n# Specifies whether re-planning is allowed\nbool replanning_enabled\n\n# Specifies the current world being used\nstring world\n\n# Number of seconds since Unix Epoch\nuint32 boot_time\n";
    public static final String _TYPE = "ff_msgs/AgentStateStamped";

    boolean getAutoReturnEnabled();

    int getBootTime();

    boolean getCheckObstacles();

    boolean getCheckZones();

    float getCollisionDistance();

    String getFlightMode();

    ExecState getGuestScienceState();

    Header getHeader();

    boolean getHolonomicEnabled();

    boolean getImmediateEnabled();

    MobilityState getMobilityState();

    OpState getOperatingState();

    ExecState getPlanExecutionState();

    String getPlanner();

    String getProfileName();

    float getProximity();

    boolean getReplanningEnabled();

    float getTargetAngularAccel();

    float getTargetAngularVelocity();

    float getTargetLinearAccel();

    float getTargetLinearVelocity();

    String getWorld();

    void setAutoReturnEnabled(boolean z);

    void setBootTime(int i);

    void setCheckObstacles(boolean z);

    void setCheckZones(boolean z);

    void setCollisionDistance(float f);

    void setFlightMode(String str);

    void setGuestScienceState(ExecState execState);

    void setHeader(Header header);

    void setHolonomicEnabled(boolean z);

    void setImmediateEnabled(boolean z);

    void setMobilityState(MobilityState mobilityState);

    void setOperatingState(OpState opState);

    void setPlanExecutionState(ExecState execState);

    void setPlanner(String str);

    void setProfileName(String str);

    void setProximity(float f);

    void setReplanningEnabled(boolean z);

    void setTargetAngularAccel(float f);

    void setTargetAngularVelocity(float f);

    void setTargetLinearAccel(float f);

    void setTargetLinearVelocity(float f);

    void setWorld(String str);
}
