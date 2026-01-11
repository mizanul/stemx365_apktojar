package gov.nasa.arc.astrobee.internal;

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

public interface BaseRobot {
    PendingResult armPanAndTilt(float f, float f2, ActionType actionType);

    PendingResult autoReturn();

    PendingResult customGuestScience(String str, String str2);

    PendingResult dock(int i);

    PendingResult fault();

    PendingResult grabControl(String str);

    PendingResult gripperControl(boolean z);

    PendingResult idlePropulsion();

    PendingResult initializeBias();

    PendingResult loadNodelet(String str, String str2, String str3, String str4);

    PendingResult noOp();

    PendingResult pausePlan();

    PendingResult perch();

    PendingResult powerOffItem(PoweredComponent poweredComponent);

    PendingResult powerOnItem(PoweredComponent poweredComponent);

    PendingResult prepare();

    PendingResult reacquirePosition();

    PendingResult requestControl();

    PendingResult resetEkf();

    PendingResult runPlan();

    PendingResult setCamera(CameraName cameraName, CameraMode cameraMode, CameraResolution cameraResolution, float f, float f2);

    PendingResult setCameraRecording(CameraName cameraName, boolean z);

    PendingResult setCameraStreaming(CameraName cameraName, boolean z);

    PendingResult setCheckObstacles(boolean z);

    PendingResult setCheckZones(boolean z);

    PendingResult setDataToDisk();

    PendingResult setEnableAutoReturn(boolean z);

    PendingResult setEnableImmediate(boolean z);

    PendingResult setEnableReplan(boolean z);

    PendingResult setFlashlightBrightness(FlashlightLocation flashlightLocation, float f);

    PendingResult setHolonomicMode(boolean z);

    PendingResult setInertia(String str, float f, Vec3d vec3d, Mat33f mat33f);

    PendingResult setOperatingLimits(String str, FlightMode flightMode, float f, float f2, float f3, float f4, float f5);

    PendingResult setPlan();

    PendingResult setPlanner(PlannerType plannerType);

    PendingResult setTelemetryRate(TelemetryType telemetryType, float f);

    PendingResult setZones();

    PendingResult simpleMove6DOF(String str, Point point, Vec3d vec3d, Quaternion quaternion);

    PendingResult skipPlanStep();

    PendingResult startGuestScience(String str);

    PendingResult startRecording(String str);

    PendingResult stopAllMotion();

    PendingResult stopArm();

    PendingResult stopGuestScience(String str);

    PendingResult stopRecording();

    PendingResult stowArm();

    PendingResult switchLocalization(LocalizationMode localizationMode);

    PendingResult undock();

    PendingResult unloadNodelet(String str, String str2);

    PendingResult unperch();

    PendingResult unterminate();

    PendingResult wait(float f);

    PendingResult wake(int i);

    PendingResult wakeSafe(int i);
}
