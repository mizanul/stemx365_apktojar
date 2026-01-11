package gov.nasa.arc.astrobee.internal;

import ff_msgs.CommandConstants;
import gov.nasa.arc.astrobee.PendingResult;
import gov.nasa.arc.astrobee.types.ActionType;
import gov.nasa.arc.astrobee.types.CameraMode;
import gov.nasa.arc.astrobee.types.CameraName;
import gov.nasa.arc.astrobee.types.CameraResolution;
import gov.nasa.arc.astrobee.types.FlashlightLocation;
import gov.nasa.arc.astrobee.types.FlightMode;
import gov.nasa.arc.astrobee.types.LocalizationMode;
import gov.nasa.arc.astrobee.types.Mat33f;
import gov.nasa.arc.astrobee.types.PlannerType;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.PoweredComponent;
import gov.nasa.arc.astrobee.types.Quaternion;
import gov.nasa.arc.astrobee.types.TelemetryType;
import gov.nasa.arc.astrobee.types.Vec3d;
import org.jboss.netty.handler.codec.rtsp.RtspHeaders;
import org.ros.internal.transport.ConnectionHeaderFields;

public abstract class BaseRobotImpl extends AbstractRobot implements BaseRobot {
    public PendingResult grabControl(String cookie) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_GRAB_CONTROL).addArgument("cookie", cookie);
        return publish(builder.build());
    }

    public PendingResult requestControl() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_REQUEST_CONTROL);
        return publish(builder.build());
    }

    public PendingResult fault() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_FAULT);
        return publish(builder.build());
    }

    public PendingResult initializeBias() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_INITIALIZE_BIAS);
        return publish(builder.build());
    }

    public PendingResult loadNodelet(String nodeletName, String managerName, String type, String bondId) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_LOAD_NODELET).addArgument("nodeletName", nodeletName).addArgument("managerName", managerName).addArgument(ConnectionHeaderFields.TYPE, type).addArgument("bondId", bondId);
        return publish(builder.build());
    }

    public PendingResult noOp() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_NO_OP);
        return publish(builder.build());
    }

    public PendingResult reacquirePosition() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_REACQUIRE_POSITION);
        return publish(builder.build());
    }

    public PendingResult resetEkf() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_RESET_EKF);
        return publish(builder.build());
    }

    public PendingResult switchLocalization(LocalizationMode mode) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SWITCH_LOCALIZATION).addArgument(RtspHeaders.Values.MODE, mode);
        return publish(builder.build());
    }

    public PendingResult unloadNodelet(String nodeletName, String managerName) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_UNLOAD_NODELET).addArgument("nodeletName", nodeletName).addArgument("managerName", managerName);
        return publish(builder.build());
    }

    public PendingResult unterminate() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_UNTERMINATE);
        return publish(builder.build());
    }

    public PendingResult wake(int berthNumber) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_WAKE).addArgument("berthNumber", berthNumber);
        return publish(builder.build());
    }

    public PendingResult wakeSafe(int berthNumber) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_WAKE_SAFE).addArgument("berthNumber", berthNumber);
        return publish(builder.build());
    }

    public PendingResult armPanAndTilt(float pan, float tilt, ActionType which) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_ARM_PAN_AND_TILT).addArgument("pan", pan).addArgument("tilt", tilt).addArgument("which", which);
        return publish(builder.build());
    }

    public PendingResult gripperControl(boolean open) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_GRIPPER_CONTROL).addArgument("open", open);
        return publish(builder.build());
    }

    public PendingResult stopArm() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_STOP_ARM);
        return publish(builder.build());
    }

    public PendingResult stowArm() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_STOW_ARM);
        return publish(builder.build());
    }

    public PendingResult setDataToDisk() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_DATA_TO_DISK);
        return publish(builder.build());
    }

    public PendingResult startRecording(String description) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_START_RECORDING).addArgument("description", description);
        return publish(builder.build());
    }

    public PendingResult stopRecording() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_STOP_RECORDING);
        return publish(builder.build());
    }

    public PendingResult customGuestScience(String apkName, String command) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_CUSTOM_GUEST_SCIENCE).addArgument("apkName", apkName).addArgument("command", command);
        return publish(builder.build());
    }

    public PendingResult startGuestScience(String apkName) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_START_GUEST_SCIENCE).addArgument("apkName", apkName);
        return publish(builder.build());
    }

    public PendingResult stopGuestScience(String apkName) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_STOP_GUEST_SCIENCE).addArgument("apkName", apkName);
        return publish(builder.build());
    }

    public PendingResult autoReturn() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_AUTO_RETURN);
        return publish(builder.build());
    }

    public PendingResult dock(int berthNumber) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_DOCK).addArgument("berthNumber", berthNumber);
        return publish(builder.build());
    }

    public PendingResult idlePropulsion() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_IDLE_PROPULSION);
        return publish(builder.build());
    }

    public PendingResult perch() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_PERCH);
        return publish(builder.build());
    }

    public PendingResult prepare() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_PREPARE);
        return publish(builder.build());
    }

    public PendingResult simpleMove6DOF(String referenceFrame, Point xyz, Vec3d xyzTolerance, Quaternion rot) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SIMPLE_MOVE6DOF).addArgument("referenceFrame", referenceFrame).addArgument("xyz", (Vec3d) xyz).addArgument("xyzTolerance", xyzTolerance).addArgument("rot", (Mat33f) rot);
        return publish(builder.build());
    }

    public PendingResult stopAllMotion() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_STOP_ALL_MOTION);
        return publish(builder.build());
    }

    public PendingResult undock() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_UNDOCK);
        return publish(builder.build());
    }

    public PendingResult unperch() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_UNPERCH);
        return publish(builder.build());
    }

    public PendingResult pausePlan() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_PAUSE_PLAN);
        return publish(builder.build());
    }

    public PendingResult runPlan() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_RUN_PLAN);
        return publish(builder.build());
    }

    public PendingResult setPlan() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_PLAN);
        return publish(builder.build());
    }

    public PendingResult skipPlanStep() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SKIP_PLAN_STEP);
        return publish(builder.build());
    }

    public PendingResult wait(float duration) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_WAIT).addArgument("duration", duration);
        return publish(builder.build());
    }

    public PendingResult powerOffItem(PoweredComponent which) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_POWER_OFF_ITEM).addArgument("which", which);
        return publish(builder.build());
    }

    public PendingResult powerOnItem(PoweredComponent which) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_POWER_ON_ITEM).addArgument("which", which);
        return publish(builder.build());
    }

    public PendingResult setCamera(CameraName cameraName, CameraMode cameraMode, CameraResolution resolution, float frameRate, float bandwidth) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_CAMERA).addArgument("cameraName", cameraName).addArgument("cameraMode", cameraMode).addArgument("resolution", resolution).addArgument("frameRate", frameRate).addArgument("bandwidth", bandwidth);
        return publish(builder.build());
    }

    public PendingResult setCameraRecording(CameraName cameraName, boolean record) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_CAMERA_RECORDING).addArgument("cameraName", cameraName).addArgument("record", record);
        return publish(builder.build());
    }

    public PendingResult setCameraStreaming(CameraName cameraName, boolean stream) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_CAMERA_STREAMING).addArgument("cameraName", cameraName).addArgument("stream", stream);
        return publish(builder.build());
    }

    public PendingResult setCheckObstacles(boolean checkObstacles) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_CHECK_OBSTACLES).addArgument("checkObstacles", checkObstacles);
        return publish(builder.build());
    }

    public PendingResult setCheckZones(boolean checkZones) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_CHECK_ZONES).addArgument("checkZones", checkZones);
        return publish(builder.build());
    }

    public PendingResult setEnableAutoReturn(boolean enableAutoReturn) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_ENABLE_AUTO_RETURN).addArgument("enableAutoReturn", enableAutoReturn);
        return publish(builder.build());
    }

    public PendingResult setEnableImmediate(boolean enableImmediate) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_ENABLE_IMMEDIATE).addArgument("enableImmediate", enableImmediate);
        return publish(builder.build());
    }

    public PendingResult setEnableReplan(boolean enableReplan) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_ENABLE_REPLAN).addArgument("enableReplan", enableReplan);
        return publish(builder.build());
    }

    public PendingResult setFlashlightBrightness(FlashlightLocation which, float brightness) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_FLASHLIGHT_BRIGHTNESS).addArgument("which", which).addArgument("brightness", brightness);
        return publish(builder.build());
    }

    public PendingResult setHolonomicMode(boolean enableHolonomic) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_HOLONOMIC_MODE).addArgument("enableHolonomic", enableHolonomic);
        return publish(builder.build());
    }

    public PendingResult setInertia(String name, float mass, Vec3d centerOfMass, Mat33f matrix) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_INERTIA).addArgument("name", name).addArgument("mass", mass).addArgument("centerOfMass", centerOfMass).addArgument("matrix", matrix);
        return publish(builder.build());
    }

    public PendingResult setOperatingLimits(String profileName, FlightMode flightMode, float targetLinearVelocity, float targetLinearAcceleration, float targetAngularVelocity, float targetAngularAcceleration, float collisionDistance) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_OPERATING_LIMITS).addArgument("profileName", profileName).addArgument("flightMode", flightMode).addArgument("targetLinearVelocity", targetLinearVelocity).addArgument("targetLinearAcceleration", targetLinearAcceleration).addArgument("targetAngularVelocity", targetAngularVelocity).addArgument("targetAngularAcceleration", targetAngularAcceleration).addArgument("collisionDistance", collisionDistance);
        return publish(builder.build());
    }

    public PendingResult setPlanner(PlannerType planner) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_PLANNER).addArgument("planner", planner);
        return publish(builder.build());
    }

    public PendingResult setTelemetryRate(TelemetryType telemetryName, float rate) {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_TELEMETRY_RATE).addArgument("telemetryName", telemetryName).addArgument("rate", rate);
        return publish(builder.build());
    }

    public PendingResult setZones() {
        CommandBuilder builder = makeCommandBuilder();
        builder.setName(CommandConstants.CMD_NAME_SET_ZONES);
        return publish(builder.build());
    }
}
