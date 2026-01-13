/*
 * Decompiled with CFR 0.152.
 */
package jp.jaxa.iss.kibo.rpc.api;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import gov.nasa.arc.astrobee.AstrobeeException;
import gov.nasa.arc.astrobee.AstrobeeRuntimeException;
import gov.nasa.arc.astrobee.Kinematics;
import gov.nasa.arc.astrobee.PendingResult;
import gov.nasa.arc.astrobee.Result;
import gov.nasa.arc.astrobee.Robot;
import gov.nasa.arc.astrobee.RobotFactory;
import gov.nasa.arc.astrobee.android.gs.MessageType;
import gov.nasa.arc.astrobee.android.gs.StartGuestScienceService;
import gov.nasa.arc.astrobee.ros.DefaultRobotFactory;
import gov.nasa.arc.astrobee.ros.RobotConfiguration;
import gov.nasa.arc.astrobee.types.FlashlightLocation;
import gov.nasa.arc.astrobee.types.FlightMode;
import gov.nasa.arc.astrobee.types.PlannerType;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import jp.jaxa.iss.kibo.rpc.api.GetterNode;
import jp.jaxa.iss.kibo.rpc.api.SetterNode;
import jp.jaxa.iss.kibo.rpc.api.areas.AreaItemMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

public final class KiboRpcApi
extends Activity {
    private GetterNode getterNode;
    private SetterNode setterNode;
    private static final URI ROS_MASTER_URI = URI.create("http://llp:11311");
    private static final String EMULATOR_ROS_HOSTNAME = "hlp";
    private static final String NODE_NAME = "kibo_rpc_api";
    private static final String TARGET_IMAGES_SAVE_DIR = "/immediate/JudgeImages";
    private static final String DEBUG_IMAGES_SAVE_DIR = "/immediate/DebugImages";
    private static KiboRpcApi instance = null;
    private RobotConfiguration robotConfiguration = new RobotConfiguration();
    private RobotFactory factory;
    private Robot robot;
    private StartGuestScienceService gsService = null;
    private PlannerType plannerType = null;
    private final DateFormat df = new SimpleDateFormat("yyyyMMdd hhmmssSSS");
    private final String PAYLOAD_START = "Mission Start";
    private final String PAYLOAD_FINISH = "Mission Finish";
    private final String UNDOCK_START = "Undock Start";
    private final String UNDOCK_FINISH = "Undock Finish";
    private final String UNDOCK_ERROR = "Undock failed";
    private final String SIGNAL_LIGHT_PERCHING_ARM = "Turn on the signal light and deploy the perching arm";
    private final String TARGET_RECOGNITION_COMPLETE = "Target Item recognition is complete";
    private final String TURN_ON_FOUND_PATTERN = "Turn on the found pattern";
    private final String TURN_ON_RECOGNITION_PATTERN = "Turn on the recognition pattern";
    private final String ROUNDING_COMPLETE = "rounding is complete";
    private final String AAR_VERSION = "5.1.0";
    private final int APPROACH_TIMES_SNAPSHOT_SIMULATION = 1;
    private final int APPROACH_TIMES_SNAPSHOT_ISS = 2;
    private final int APPROACH_INTEVAL = 1000;
    private final int LASER_WAIT_TIME = 2000;
    private final int TIMER_CHECK_RESOLUTION = 100;
    private final int UNDOCK_TIMEOUT_ON_SIM = 100;
    private final int MAX_IMAGES = 50;
    private final int MAX_IMAGE_SIZE = 1228800;
    private final int BLINK_NUM = 2;
    private final int WAIT_AFTER_BLINK = 1000;
    private final float FLASH_LIGHT_MAX_IN_FINAL = 0.01f;
    private final float FLASH_LIGHT_MIN = 0.0f;
    private final float FLASH_LIGHT_REPORT_ROUNDING_COMPLETION = 0.05f;
    private final boolean LIGHT_MODE_FRONT = true;
    private final boolean LIGHT_MODE_BACK = false;
    private final double[] NAVCAM_CAMERA_MATRIX_SIMULATION = new double[]{523.10575, 0.0, 635.434258, 0.0, 534.765913, 500.335102, 0.0, 0.0, 1.0};
    private final double[] NAVCAM_CAMERA_MATRIX_ISS = new double[]{608.8073, 0.0, 632.53684, 0.0, 607.61439, 549.08386, 0.0, 0.0, 1.0};
    private final double[] NAVCAM_DISTORTION_COEFFICIENTS_SIMULATION = new double[]{-0.164787, 0.020375, -0.001572, -3.69E-4, 0.0};
    private final double[] NAVCAM_DISTORTION_COEFFICIENTS_ISS = new double[]{-0.212191, 0.073843, -9.18E-4, 0.00189, 0.0};
    private final double[] DOCKCAM_CAMERA_MATRIX_SIMULATION = new double[]{661.783002, 0.0, 595.212041, 0.0, 671.508662, 489.094196, 0.0, 0.0, 1.0};
    private final double[] DOCKCAM_CAMERA_MATRIX_ISS = new double[]{753.51021, 0.0, 631.11512, 0.0, 751.3611, 508.69621, 0.0, 0.0, 1.0};
    private final double[] DOCKCAM_DISTORTION_COEFFICIENTS_SIMULATION = new double[]{-0.215168, 0.044354, 0.003615, 0.005093, 0.0};
    private final double[] DOCKCAM_DISTORTION_COEFFICIENTS_ISS = new double[]{-0.411405, 0.17724, -0.017145, 0.006421, 0.0};
    private final float DISTANCE_THRESHOLD = 0.3f;
    private boolean reportCompletion = false;
    private boolean tookTargetItemSnap = false;
    private AreaItemMap areaItemMap;

    private void sendExceptionMessage(String errmsg, Exception err) {
        Log.v((String)"KiboRpcApi", (String)"[Start] sendExceptionMessage");
        try {
            JSONObject data = new JSONObject();
            data.put("error", (Object)("[" + err.getClass().getName() + "] " + errmsg));
            Log.e((String)"KiboRpcApi", (String)errmsg, (Throwable)err);
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
        }
        catch (JSONException e) {
            this.gsService.sendData(MessageType.JSON, "data", ((Object)((Object)e)).getClass().getName());
            Log.e((String)"KiboRpcApi", (String)"[sendExceptionMessage] Failed to send error message.", (Throwable)e);
        }
        Log.v((String)"KiboRpcApi", (String)"[Finish] sendExceptionMessage");
    }

    private KiboRpcApi(StartGuestScienceService startGuestScienceService) {
        Log.v((String)"KiboRpcApi", (String)"[Start] KiboRpcApi");
        Log.i((String)"KiboRpcApi", (String)"[AARVersion] AAR version 5.1.0");
        this.configureRobot();
        this.factory = new DefaultRobotFactory(this.robotConfiguration);
        this.gsService = startGuestScienceService;
        try {
            this.robot = this.factory.getRobot();
            this.getterNode = GetterNode.getInstance();
            this.setterNode = SetterNode.getInstance();
            this.areaItemMap = new AreaItemMap();
        }
        catch (AstrobeeException e) {
            this.sendExceptionMessage("[Constructor] Error with Astrobee", (Exception)((Object)e));
        }
        catch (InterruptedException e) {
            this.sendExceptionMessage("[Constructor] Connection Interrupted", e);
        }
        Log.v((String)"KiboRpcApi", (String)"[Finish] KiboRpcApi");
    }

    private void configureRobot() {
        Log.v((String)"KiboRpcApi", (String)"[Start] configureRobot");
        this.robotConfiguration.setMasterUri(ROS_MASTER_URI);
        this.robotConfiguration.setHostname(EMULATOR_ROS_HOSTNAME);
        this.robotConfiguration.setNodeName(NODE_NAME);
        Log.v((String)"KiboRpcApi", (String)"[Finish] configureRobot");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Result getCommandResult(PendingResult pending, boolean printRobotPosition, int timeout) {
        Log.v((String)"KiboRpcApi", (String)"[Start] getCommandResult");
        Result result = null;
        int counter = 0;
        try {
            while (!pending.isFinished()) {
                if (timeout >= 0) {
                    Log.v((String)"KiboRpcApi", (String)"[getCommandResult] Setting timeout");
                    if (counter > timeout) {
                        Log.v((String)"KiboRpcApi", (String)"[getCommandResult] return null");
                        Result var = null;
        return null;
                    }
                }
                if (printRobotPosition) {
                    Log.v((String)"KiboRpcApi", (String)"[getCommandResult] Meanwhile, let's get the positioning along the trajectory");
                    Kinematics k = this.getterNode.getCurrentKinematics();
                    if (k.getPosition() != null) {
                        Log.i((String)"KiboRpcApi", (String)("[getCommandResult] Current Position: " + k.getPosition().toString()));
                    }
                    if (k.getOrientation() != null) {
                        Log.i((String)"KiboRpcApi", (String)("[getCommandResult] Current Orientation: " + k.getOrientation().toString()));
                    }
                }
                pending.getResult(1000L, TimeUnit.MILLISECONDS);
                ++counter;
            }
            result = pending.getResult();
            this.printLogCommandResult(result);
        }
        catch (AstrobeeException e) {
            this.sendExceptionMessage("[getCommandResult] Error with Astrobee", (Exception)((Object)e));
        }
        catch (InterruptedException e) {
            this.sendExceptionMessage("[getCommandResult] Connection Interrupted", e);
        }
        catch (TimeoutException e) {
            this.sendExceptionMessage("[getCommandResult] Timeout connection", e);
        }
        finally {
            Log.v((String)"KiboRpcApi", (String)"[Finish] getCommandResult");
            return result;
        }
    }

    private void printLogCommandResult(Result result) {
        Log.i((String)"KiboRpcApi", (String)"[Start] printLogCommandResult");
        if (result != null) {
            if (result.getStatus() != null) {
                Log.i((String)"KiboRpcApi", (String)("[printLogCommandResult] Command status: " + result.getStatus().toString()));
            }
            if (!result.hasSucceeded()) {
                Log.e((String)"KiboRpcApi", (String)("[printLogCommandResult] Command message: " + result.getMessage()));
            }
            Log.i((String)"KiboRpcApi", (String)"[printLogCommandResult] Done.");
            Log.i((String)"KiboRpcApi", (String)"[Finish] printLogCommandResult");
        } else {
            Log.e((String)"KiboRpcApi", (String)"[printLogCommandResult] Invalid result.");
        }
    }

    private Result stopAllMotion() {
        Log.v((String)"KiboRpcApi", (String)"[Start] stopAllMotion");
        PendingResult pendingResult = null;
        try {
            pendingResult = this.robot.stopAllMotion();
        }
        catch (AstrobeeRuntimeException e) {
            this.sendExceptionMessage("[stopAllMotion] Node not ready or dead.", (Exception)((Object)e));
            return null;
        }
        Result result = this.getCommandResult(pendingResult, false, -1);
        Log.v((String)"KiboRpcApi", (String)"[Finish] stopAllMotion");
        return result;
    }

    private boolean setPlanner(PlannerType plannerType) {
        Log.v((String)"KiboRpcApi", (String)"[Start] setPlanner");
        PendingResult pendingResult = null;
        try {
            pendingResult = this.robot.setPlanner(plannerType);
        }
        catch (AstrobeeRuntimeException e) {
            this.sendExceptionMessage("[setPlanner] Node not ready or dead.", (Exception)((Object)e));
            return false;
        }
        Result result = this.getCommandResult(pendingResult, false, 5);
        if (result != null) {
            if (result.hasSucceeded()) {
                this.plannerType = plannerType;
                Log.v((String)"KiboRpcApi", (String)("[setPlanner] Planner is set to " + plannerType));
            }
            Log.v((String)"KiboRpcApi", (String)"[Finish] setPlanner");
            return result.hasSucceeded();
        }
        Log.e((String)"KiboRpcApi", (String)"[setPlanner] Invalid result.");
        return false;
    }

    public void shutdownFactory() {
        Log.v((String)"KiboRpcApi", (String)"[Start] shutdownFactory");
        this.factory.shutdown();
        Log.v((String)"KiboRpcApi", (String)"[Finish] shutdownFactory");
    }

    public static KiboRpcApi getInstance(StartGuestScienceService startGuestScienceService) {
        Log.v((String)"KiboRpcApi", (String)"[Start] getInstance");
        if (instance == null) {
            Log.v((String)"KiboRpcApi", (String)"[getInstance] Make instance");
            instance = new KiboRpcApi(startGuestScienceService);
        }
        Log.v((String)"KiboRpcApi", (String)"[Finish] getInstance");
        return instance;
    }

    private Kinematics getTrustedRobotKinematics() {
        Log.i((String)"KiboRpcApi", (String)"[Start] getTrustedRobotKinematics");
        Log.i((String)"KiboRpcApi", (String)"[getTrustedRobotKinematics] Waiting for robot to acquire position.");
        Kinematics k = null;
        long start_point = System.currentTimeMillis();
        long end_point = System.currentTimeMillis();
        while (end_point - start_point < 30000L) {
            k = this.getterNode.getCurrentKinematics();
            if (k.getConfidence() == Kinematics.Confidence.GOOD) {
                Log.v((String)"KiboRpcApi", (String)"[getTrustedRobotKinematics] Break loop");
                break;
            }
            k = null;
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e) {
                Log.e((String)"KiboRpcApi", (String)"[getTrustedRobotkinematics] It was not possible to get a trusted kinematics. Sorry.");
                return null;
            }
            end_point = System.currentTimeMillis();
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] getTrustedRobotKinematics");
        return k;
    }

    public Kinematics getRobotKinematics() {
        Log.i((String)"KiboRpcApi", (String)"[Start] getRobotKinematics");
        Log.i((String)"KiboRpcApi", (String)"[getRobotKinematics] Waiting for robot to acquire position.");
        Log.i((String)"KiboRpcApi", (String)"[Finish] getRobotKinematics");
        return this.getterNode.getCurrentKinematics();
    }

    public Bitmap getBitmapNavCam() {
        Log.i((String)"KiboRpcApi", (String)"[Start] getBitmapNavCam");
        Bitmap ret = this.getterNode.getBitmapNavCam();
        if (ret == null) {
            Log.i((String)"KiboRpcApi", (String)"[getBitmapNavCam] ret is null.");
            Log.e((String)"KiboRpcApi", (String)"[getBitmapNavCam] It was not possible to get a Bitmap from Nav Cam.");
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] getBitmapNavCam");
        return ret;
    }

    public Bitmap getBitmapDockCam() {
        Log.i((String)"KiboRpcApi", (String)"[Start] getBitmapDockCam");
        Bitmap ret = this.getterNode.getBitmapDockCam();
        if (ret == null) {
            Log.i((String)"KiboRpcApi", (String)"[getBitmapDockCam] ret is null.");
            Log.e((String)"KiboRpcApi", (String)"[getBitmapDockCam] It was not possible to get a Bitmap from Dock Cam.");
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] getBitmapDockCam");
        return ret;
    }

    public Mat getMatNavCam() {
        Log.i((String)"KiboRpcApi", (String)"[Start] getMatNavCam");
        Mat ret = this.getterNode.getMatNavCam();
        if (ret == null) {
            Log.i((String)"KiboRpcApi", (String)"[getMatNavCam] ret is null.");
            Log.e((String)"KiboRpcApi", (String)"[getMatNavCam] It was not possible to get a Mat from Nav Cam.");
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] getMatNavCam");
        return ret;
    }

    public Mat getMatDockCam() {
        Log.i((String)"KiboRpcApi", (String)"[Start] getMatDockCam");
        Mat ret = this.getterNode.getMatDockCam();
        if (ret == null) {
            Log.i((String)"KiboRpcApi", (String)"[getMatDockCam] ret is null.");
            Log.e((String)"KiboRpcApi", (String)"[getMatDockCam] It was not possible to get a Mat from Dock Cam.");
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] getMatDockCam");
        return ret;
    }

    public Result flashlightControlFront(float brightness) {
        Log.i((String)"KiboRpcApi", (String)"[Start] flashlightControlFront");
        if (!this.getterNode.getOnSimulation() && brightness > 0.01f) {
            Log.v((String)"KiboRpcApi", (String)"[flashlightControlFront] In the final, the maximum value of the light is set to 0.01.");
            brightness = 0.01f;
        }
        Log.i((String)"KiboRpcApi", (String)("Parameters: brightness == " + brightness));
        PendingResult pendingResult = null;
        try {
            pendingResult = this.robot.setFlashlightBrightness(FlashlightLocation.FRONT, brightness);
        }
        catch (AstrobeeRuntimeException e) {
            this.sendExceptionMessage("[flashlightControlFront] Node not ready or dead.", (Exception)((Object)e));
            return null;
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] flashlightControlFront");
        return this.getCommandResult(pendingResult, false, -1);
    }

    public Result flashlightControlBack(float brightness) {
        Log.i((String)"KiboRpcApi", (String)"[Start] flashlightControlBack");
        if (!this.getterNode.getOnSimulation() && brightness > 0.01f) {
            Log.v((String)"KiboRpcApi", (String)"[flashlightControlBack] In the final, the maximum value of the light is set to 0.01.");
            brightness = 0.01f;
        }
        Log.i((String)"KiboRpcApi", (String)("Parameters: brightness == " + brightness));
        PendingResult pendingResult = null;
        try {
            pendingResult = this.robot.setFlashlightBrightness(FlashlightLocation.BACK, brightness);
        }
        catch (AstrobeeRuntimeException e) {
            this.sendExceptionMessage("[flashlightControlBack] Node not ready or dead.", (Exception)((Object)e));
            return null;
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] flashlightControlBack");
        return this.getCommandResult(pendingResult, false, -1);
    }

    private Result flashlightControl(float brightness, boolean front) {
        FlashlightLocation flloc = FlashlightLocation.FRONT;
        if (front) {
            Log.i((String)"KiboRpcApi", (String)"[Start] flashlightControl: front");
            Log.i((String)"KiboRpcApi", (String)("Parameters: brightness == " + brightness));
            flloc = FlashlightLocation.FRONT;
        } else {
            Log.i((String)"KiboRpcApi", (String)"[Start] flashlightControl: back");
            Log.i((String)"KiboRpcApi", (String)("Parameters: brightness == " + brightness));
            flloc = FlashlightLocation.BACK;
        }
        PendingResult pendingResult = null;
        try {
            pendingResult = this.robot.setFlashlightBrightness(flloc, brightness);
        }
        catch (AstrobeeRuntimeException e) {
            this.sendExceptionMessage("[flashlightControlFront] Node not ready or dead.", (Exception)((Object)e));
            return null;
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] flashlightControlFront");
        return this.getCommandResult(pendingResult, false, -1);
    }

    public Result moveTo(Point goalPoint, Quaternion orientation, boolean printRobotPosition) {
        Log.i((String)"KiboRpcApi", (String)"[Start] moveTo");
        Log.i((String)"KiboRpcApi", (String)("Parameters: goalPoint == " + goalPoint + ", orientation == " + orientation + ", printRobotPosition == " + printRobotPosition));
        if (goalPoint == null) {
            Log.i((String)"KiboRpcApi", (String)("[moveTo] goalPoint == " + goalPoint));
            Log.e((String)"KiboRpcApi", (String)"[moveTo] goalPoint is invalid.");
            return null;
        }
        if (orientation == null) {
            Log.i((String)"KiboRpcApi", (String)("[moveTo] orientation == " + orientation));
            Log.e((String)"KiboRpcApi", (String)"[moveTo] orientation is invalid.");
            return null;
        }
        if (!this.setPlanner(PlannerType.TRAPEZOIDAL)) {
            Log.e((String)"KiboRpcApi", (String)"[moveTo] Cannot set planner.");
            return null;
        }
        Log.i((String)"KiboRpcApi", (String)("[moveTo] Planner is set to " + this.plannerType.toString() + "."));
        Result result = this.stopAllMotion();
        if (result == null) {
            Log.i((String)"KiboRpcApi", (String)("[moveTo] result == " + result));
            Log.e((String)"KiboRpcApi", (String)"[moveTo] Cannot stop all motion.");
            return null;
        }
        if (result.hasSucceeded()) {
            Log.i((String)"KiboRpcApi", (String)("[moveTo] Planner is " + this.plannerType.toString() + "."));
            Log.i((String)"KiboRpcApi", (String)"[moveTo] Moving the bee.");
            PendingResult pendingResult = null;
            try {
                pendingResult = this.robot.simpleMove6DOF(goalPoint, orientation);
            }
            catch (AstrobeeRuntimeException e) {
                this.sendExceptionMessage("[moveTo] Node not ready or dead.", (Exception)((Object)e));
                return null;
            }
            result = this.getCommandResult(pendingResult, printRobotPosition, -1);
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] moveTo");
        return result;
    }

    public Result relativeMoveTo(Point goalPoint, Quaternion orientation, boolean printRobotPosition) {
        Log.i((String)"KiboRpcApi", (String)"[Start] relativeMoveTo");
        Log.i((String)"KiboRpcApi", (String)("Parameters: goalPoint == " + goalPoint + ", orientation == " + orientation + ", printRobotPosition == " + printRobotPosition));
        if (goalPoint == null) {
            Log.i((String)"KiboRpcApi", (String)("[relativeMoveTo] goalPoint == " + goalPoint));
            Log.e((String)"KiboRpcApi", (String)"[relativeMoveTo] goalPoint is invalid.");
            return null;
        }
        if (orientation == null) {
            Log.i((String)"KiboRpcApi", (String)("[relativeMoveTo] orientation == " + orientation));
            Log.e((String)"KiboRpcApi", (String)"[relativeMoveTo] orientation is invalid.");
            return null;
        }
        Kinematics k = this.getTrustedRobotKinematics();
        if (k == null) {
            Log.i((String)"KiboRpcApi", (String)("[relativeMoveTo] k == " + k));
            Log.e((String)"KiboRpcApi", (String)"[relativeMoveTo] Cannot get robot kinematics.");
            return null;
        }
        Point currPosition = k.getPosition();
        Point endPoint = new Point(currPosition.getX() + goalPoint.getX(), currPosition.getY() + goalPoint.getY(), currPosition.getZ() + goalPoint.getZ());
        Log.i((String)"KiboRpcApi", (String)"[Finish] relativeMoveTo");
        return this.moveTo(endPoint, orientation, printRobotPosition);
    }

    private void setSignalStateFoundPattern() {
        try {
            JSONObject data = new JSONObject();
            Log.v((String)"KiboRpcApi", (String)"[Start] setSignalStateFoundPattern");
            this.setterNode.setSignalState((byte)4, 2);
            this.setterNode.setSignalState((byte)5, 1);
            this.setterNode.setSignalState((byte)3, 1);
            data.put("signal_light", (Object)"Turn on the found pattern");
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
            Log.v((String)"KiboRpcApi", (String)"[Finish] setSignalStateFoundPattern");
        }
        catch (JSONException e) {
            this.sendExceptionMessage("[setSignalStateFoundPattern] Internal error occurred. Unable to serialize data to JSON.", (Exception)((Object)e));
        }
        catch (Exception e) {
            this.sendExceptionMessage("[setSignalStateFoundPattern] Internal error occurred. Unable to send data to gds.", e);
        }
    }

    private void setSignalStateRecognitionPattern() {
        try {
            JSONObject data = new JSONObject();
            Log.v((String)"KiboRpcApi", (String)"[Start] setSignalStateRecognitionPattern");
            this.setterNode.setSignalState((byte)3, 1);
            data.put("signal_light", (Object)"Turn on the recognition pattern");
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
            Log.v((String)"KiboRpcApi", (String)"[Finish] setSignalStateRecognitionPattern");
        }
        catch (JSONException e) {
            this.sendExceptionMessage("[setSignalStateRecognitionPattern] Internal error occurred. Unable to serialize data to JSON.", (Exception)((Object)e));
        }
        catch (Exception e) {
            this.sendExceptionMessage("[setSignalStateRecognitionPattern] Internal error occurred. Unable to send data to gds.", e);
        }
    }

    public boolean startMission() {
        Log.i((String)"KiboRpcApi", (String)"[Start] startMission");
        boolean isSuccess = false;
        try {
            JSONObject data = new JSONObject();
            String time = this.df.format(new Date(System.currentTimeMillis()));
            data.put("t_stamp_undock", (Object)time);
            data.put("status", (Object)"Undock Start");
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
            Log.i((String)"KiboRpcApi", (String)("Undock Start: " + time));
            PendingResult pendingResult = this.robot.undock();
            int timeout = 100;
            if (!this.getterNode.getOnSimulation()) {
                Log.d((String)"KiboRpcApi", (String)"set timeout -1");
                timeout = -1;
            }
            Result result = this.getCommandResult(pendingResult, false, timeout);
            data = new JSONObject();
            if (result != null && result.hasSucceeded()) {
                data.put("status", (Object)"Undock Finish");
                isSuccess = true;
                time = this.df.format(new Date(System.currentTimeMillis()));
                Log.i((String)"KiboRpcApi", (String)("Undock Finish: " + time));
            } else {
                data.put("failed", (Object)"Undock failed");
                time = this.df.format(new Date(System.currentTimeMillis()));
                Log.e((String)"KiboRpcApi", (String)("Undock failed: " + time));
            }
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
            if (isSuccess) {
                time = this.df.format(new Date(System.currentTimeMillis()));
                data = new JSONObject();
                data.put("t_stamp_start", (Object)time);
                data.put("status", (Object)"Mission Start");
                this.gsService.sendData(MessageType.JSON, "data", data.toString());
                Log.i((String)"KiboRpcApi", (String)("Mission Start: " + time));
            }
        }
        catch (JSONException e) {
            this.sendExceptionMessage("[startMission] Internal error occurred. Unable to serialize data to JSON.", (Exception)((Object)e));
            return false;
        }
        catch (Exception e) {
            this.sendExceptionMessage("[startMission] Internal error occurred. Unable to send data to gds.", e);
            return false;
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] startMission");
        return isSuccess;
    }

    @Deprecated
    private Result setQuietMode() {
        try {
            Log.v((String)"KiboRpcApi", (String)"[Start] setQuietMode");
            Log.i((String)"KiboRpcApi", (String)"[setQuietMode] Change the flight mode to Quiet mode.");
            PendingResult pendingResult = this.robot.setOperatingLimits("iss_quiet", FlightMode.QUIET, 0.02f, 0.002f, 0.0174f, 0.0174f, 0.25f);
            Result result = this.getCommandResult(pendingResult, true, -1);
            Log.i((String)"KiboRpcApi", (String)"[setQuietMode] Changed flight mode to Quiet mode.");
            Log.v((String)"KiboRpcApi", (String)"[Finish] setQuietMode");
            return result;
        }
        catch (AstrobeeRuntimeException e) {
            this.sendExceptionMessage("[setQuietMode] Node not ready or dead.", (Exception)((Object)e));
            return null;
        }
    }

    @Deprecated
    private Result setLomoMode() {
        try {
            Log.v((String)"KiboRpcApi", (String)"[Start] setLomoMode");
            Log.i((String)"KiboRpcApi", (String)"[setLomoMode] Change the flight mode to Lomo mode.");
            PendingResult pendingResult = this.robot.setOperatingLimits("iss_lomo", FlightMode.NOMINAL, 0.15f, 0.0175f, 0.0873f, 0.1745f, 0.25f);
            Result result = this.getCommandResult(pendingResult, true, -1);
            Log.i((String)"KiboRpcApi", (String)"[setLomoMode] Changed flight mode to Lomo mode.");
            Log.v((String)"KiboRpcApi", (String)"[Finish] setLomoMode");
            return result;
        }
        catch (AstrobeeRuntimeException e) {
            this.sendExceptionMessage("[setLomoMode] Node not ready or dead.", (Exception)((Object)e));
            return null;
        }
    }

    public void notifyRecognitionItem() {
        Log.i((String)"KiboRpcApi", (String)"[Start] notifyRecognitionItem");
        try {
            Log.v((String)"KiboRpcApi", (String)"[notifyRecognitionItem] Target Item recognition is complete");
            JSONObject data = new JSONObject();
            data.put("status", (Object)"Target Item recognition is complete");
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
            if (!this.getterNode.getOnSimulation()) {
                this.setSignalStateRecognitionPattern();
                this.setterNode.setSignalState((byte)17, 1);
            }
        }
        catch (JSONException e) {
            this.sendExceptionMessage("[notifyRecognitionItem] Internal error was occurred. Unable to serialize data to JSON.", (Exception)((Object)e));
            return;
        }
        catch (Exception e) {
            this.sendExceptionMessage("[notifyRecognitionItem] Internal error was occurred. Unable to send data to gds.", e);
            return;
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] notifyRecognitionItem");
    }

    public boolean reportRoundingCompletion() {
        Log.i((String)"KiboRpcApi", (String)"[Start] reportRoundingCompletion");
        try {
            if (!this.reportCompletion) {
                Log.v((String)"KiboRpcApi", (String)"[reportRoundingCompletion] Make rounding is complete message");
                JSONObject data = new JSONObject();
                data.put("t_stamp_rounding_completion", (Object)this.df.format(new Date(System.currentTimeMillis())));
                data.put("status", (Object)"rounding is complete");
                JSONObject areamap = new JSONObject();
                areamap = this.areaItemMap.getAreaItemMapJson();
                Iterator jsonkey = areamap.keys();
                while (jsonkey.hasNext()) {
                    String keystr = (String)jsonkey.next();
                    data.put(keystr, (Object)areamap.getJSONArray(keystr));
                }
                Log.v((String)"KiboRpcApi", (String)"[reportRoundingCompletion] Do getRobotKinematics");
                Kinematics kinematics = this.getRobotKinematics();
                if (kinematics == null || kinematics.getPosition() == null || kinematics.getOrientation() == null) {
                    Log.e((String)"KiboRpcApi", (String)"[reportRoundingCompletion] It was not possible to get a kinematics.");
                    if (kinematics == null) {
                        Log.e((String)"KiboRpcApi", (String)"[reportRoundingCompletion] kinematics is null.");
                    }
                    if (kinematics.getPosition() == null) {
                        Log.e((String)"KiboRpcApi", (String)"[reportRoundingCompletion] kinematics.getPosition() is null.");
                    }
                    if (kinematics.getOrientation() == null) {
                        Log.e((String)"KiboRpcApi", (String)"[reportRoundingCompletion] kinematics.getOrientation() is null.");
                    }
                    return false;
                }
                String positionX = String.format("%.3f", kinematics.getPosition().getX());
                String positionY = String.format("%.3f", kinematics.getPosition().getY());
                String positionZ = String.format("%.3f", kinematics.getPosition().getZ());
                String orientationX = String.format("%.3f", Float.valueOf(kinematics.getOrientation().getX()));
                String orientationY = String.format("%.3f", Float.valueOf(kinematics.getOrientation().getY()));
                String orientationZ = String.format("%.3f", Float.valueOf(kinematics.getOrientation().getZ()));
                String orientationW = String.format("%.3f", Float.valueOf(kinematics.getOrientation().getW()));
                data.put("rounding_point_pos", (Object)("[ posX : " + positionX + ", posY : " + positionY + ", posZ " + positionZ + ", quaX: " + orientationX + ", quaY: " + orientationY + ", quaZ: " + orientationZ + ", quaW: " + orientationW + " ]"));
                this.gsService.sendData(MessageType.JSON, "data", data.toString());
                for (int i = 0; i < 2; ++i) {
                    Log.v((String)"KiboRpcApi", (String)("[reportRoundingCompletion] Count: " + i));
                    this.flashlightControl(0.05f, true);
                    this.flashlightControl(0.05f, false);
                    this.flashlightControl(0.0f, true);
                    this.flashlightControl(0.0f, false);
                }
                Thread.sleep(1000L);
                this.reportCompletion = true;
            }
        }
        catch (JSONException e) {
            this.sendExceptionMessage("[reportRoundingCompletion] Internal error was occurred. Unable to serialize data to JSON.", (Exception)((Object)e));
            this.reportCompletion = false;
            return false;
        }
        catch (Exception e) {
            this.sendExceptionMessage("[reportRoundingCompletion] Internal error was occurred.", e);
            this.reportCompletion = false;
            return false;
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] reportRoundingCompletion");
        return true;
    }

    public void takeTargetItemSnapshot() {
        Log.i((String)"KiboRpcApi", (String)"[Start] takeTargetItemSnapshot");
        try {
            if (!this.tookTargetItemSnap) {
                boolean result = true;
                int approach_time = 1;
                if (!this.getterNode.getOnSimulation()) {
                    Log.v((String)"KiboRpcApi", (String)"[takeTargetItemSnapshot] getOnSimulation: False");
                    Log.v((String)"KiboRpcApi", (String)"[takeTargetItemSnapshot] Set approach_time");
                    approach_time = 2;
                }
                if (!(result = this.takeSnapshot(approach_time))) {
                    Log.e((String)"KiboRpcApi", (String)"[takeTargetItemSnapshot] Fail to Take the targetItem Snapshot.");
                }
                Log.v((String)"KiboRpcApi", (String)"[takeTargetItemSnapshot] Make finish message");
                JSONObject data = new JSONObject();
                data.put("t_stamp_finish", (Object)this.df.format(new Date(System.currentTimeMillis())));
                data.put("status", (Object)"Mission Finish");
                this.gsService.sendData(MessageType.JSON, "data", data.toString());
                Log.v((String)"KiboRpcApi", (String)"[takeTargetItemSnapshot] Do getRobotKinematics");
                Kinematics kinematics = this.getRobotKinematics();
                if (kinematics != null && kinematics.getPosition() != null && kinematics.getOrientation() != null) {
                    String positionX = String.format("%.3f", kinematics.getPosition().getX());
                    String positionY = String.format("%.3f", kinematics.getPosition().getY());
                    String positionZ = String.format("%.3f", kinematics.getPosition().getZ());
                    String orientationX = String.format("%.3f", Float.valueOf(kinematics.getOrientation().getX()));
                    String orientationY = String.format("%.3f", Float.valueOf(kinematics.getOrientation().getY()));
                    String orientationZ = String.format("%.3f", Float.valueOf(kinematics.getOrientation().getZ()));
                    String orientationW = String.format("%.3f", Float.valueOf(kinematics.getOrientation().getW()));
                    data = new JSONObject();
                    data.put("found_point_pos", (Object)("[ posX : " + positionX + ", posY : " + positionY + ", posZ " + positionZ + ", quaX: " + orientationX + ", quaY: " + orientationY + ", quaZ: " + orientationZ + ", quaW: " + orientationW + " ]"));
                    this.gsService.sendData(MessageType.JSON, "data", data.toString());
                } else {
                    Log.e((String)"KiboRpcApi", (String)"[takeTargetItemSnapshot] It was not possible to get a kinematics.");
                    if (kinematics == null) {
                        Log.e((String)"KiboRpcApi", (String)"[takeTargetItemSnapshot] kinematics is null.");
                    }
                    if (kinematics.getPosition() == null) {
                        Log.e((String)"KiboRpcApi", (String)"[takeTargetItemSnapshot] kinematics.getPosition() is null.");
                    }
                    if (kinematics.getOrientation() == null) {
                        Log.e((String)"KiboRpcApi", (String)"[takeTargetItemSnapshot] kinematics.getOrientation() is null.");
                    }
                }
                if (!this.getterNode.getOnSimulation()) {
                    Log.v((String)"KiboRpcApi", (String)"[takeTargetItemSnapshot] getOnSimulation: False");
                    Log.v((String)"KiboRpcApi", (String)"[takeTargetItemSnapshot] Set SignalState");
                    this.setSignalStateFoundPattern();
                    this.setterNode.setSignalState((byte)17, 1);
                }
                this.tookTargetItemSnap = true;
            }
        }
        catch (JSONException e) {
            this.sendExceptionMessage("[takeTargetItemSnapshot] Internal error was occurred. Unable to serialize data to JSON.", (Exception)((Object)e));
            this.tookTargetItemSnap = false;
            return;
        }
        catch (Exception e) {
            this.sendExceptionMessage("[takeTargetItemSnapshot] Internal error was occurred.", e);
            this.tookTargetItemSnap = false;
            return;
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] takeTargetItemSnapshot");
    }

    private boolean takeSnapshot(int approach_times) {
        try {
            Log.v((String)"KiboRpcApi", (String)"[Start] takeSnapshot");
            Object data = null;
            for (int i = 1; i <= approach_times; ++i) {
                Log.v((String)"KiboRpcApi", (String)("[takeSnapshot] Count: " + i));
                long startTime = System.currentTimeMillis();
                if (i == 1) {
                    Log.v((String)"KiboRpcApi", (String)"[takeSnapshot] Make start message");
                }
                Log.v((String)"KiboRpcApi", (String)"[takeSnapshot] Make target snapshot");
                if (!this.getterNode.getOnSimulation()) {
                    Log.v((String)"KiboRpcApi", (String)"[takeSnapshot] getOnSimulation: False");
                    Log.v((String)"KiboRpcApi", (String)"[takeSnapshot] Save bitmap");
                    Bitmap image = this.getBitmapNavCam();
                    this.saveBitmap(image);
                }
                long currentTime = System.currentTimeMillis();
                while (currentTime - startTime <= 1000L) {
                    if (this.getterNode.getOnSimulation()) {
                        Log.v((String)"KiboRpcApi", (String)"[takeSnapshot] getOnSimulation: True");
                        Log.v((String)"KiboRpcApi", (String)"[takeSnapshot] Sleep inteval");
                        Thread.sleep(10L);
                    } else {
                        Log.v((String)"KiboRpcApi", (String)"[takeSnapshot] getOnSimulation: False");
                        Log.v((String)"KiboRpcApi", (String)"[takeSnapshot] Sleep inteval");
                        Thread.sleep(10L);
                    }
                    currentTime = System.currentTimeMillis();
                }
            }
            Log.v((String)"KiboRpcApi", (String)"[takeSnapshot] Make target finish message");
            Log.v((String)"KiboRpcApi", (String)"[Finish] takeSnapshot");
            return true;
        }
        catch (SecurityException e) {
            this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to access directory.", e);
            return false;
        }
        catch (IOException e) {
            this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to make image files.", e);
            return false;
        }
        catch (NullPointerException e) {
            this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Could not get Nav cam image.", e);
            return false;
        }
        catch (Exception e) {
            this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to send data to gds.", e);
            return false;
        }
    }

    private void saveBitmap(Bitmap saveImage) throws SecurityException, IOException, NullPointerException {
        Log.v((String)"KiboRpcApi", (String)"[Start] saveBitmap");
        String filepath = this.gsService.getGuestScienceDataBasePath() + TARGET_IMAGES_SAVE_DIR;
        File file = new File(filepath);
        if (!file.exists()) {
            Log.v((String)"KiboRpcApi", (String)"[saveBitmap] Make save directory");
            file.mkdir();
        }
        Date mDate = new Date();
        SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = "snapshot_" + fileNameDate.format(mDate) + ".png";
        String AttachName = file.getAbsolutePath() + "/" + fileName;
        FileOutputStream out = new FileOutputStream(AttachName);
        saveImage.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)out);
        out.flush();
        out.close();
        Log.v((String)"KiboRpcApi", (String)"[Finish] saveBitmap");
    }

    public double[][] getNavCamIntrinsics() {
        Log.i((String)"KiboRpcApi", (String)"[Start] getNavCamIntrinsics");
        double[][] camera_param = new double[2][];
        if (this.getterNode.getOnSimulation()) {
            Log.v((String)"KiboRpcApi", (String)"[getNavCamIntrinsics] getOnSimulation: True");
            Log.v((String)"KiboRpcApi", (String)"[getNavCamIntrinsics] Set simulation param");
            camera_param[0] = this.NAVCAM_CAMERA_MATRIX_SIMULATION;
            camera_param[1] = this.NAVCAM_DISTORTION_COEFFICIENTS_SIMULATION;
        } else {
            Log.v((String)"KiboRpcApi", (String)"[getNavCamIntrinsics] Set ISS param");
            camera_param[0] = this.NAVCAM_CAMERA_MATRIX_ISS;
            camera_param[1] = this.NAVCAM_DISTORTION_COEFFICIENTS_ISS;
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] getNavCamIntrinsics");
        return camera_param;
    }

    public double[][] getDockCamIntrinsics() {
        Log.i((String)"KiboRpcApi", (String)"[Start] getDockCamIntrinsics");
        double[][] camera_param = new double[2][];
        if (this.getterNode.getOnSimulation()) {
            Log.v((String)"KiboRpcApi", (String)"[getDockCamIntrinsics] getOnSimulation: True");
            Log.v((String)"KiboRpcApi", (String)"[getDockCamIntrinsics] Set simulation param");
            camera_param[0] = this.DOCKCAM_CAMERA_MATRIX_SIMULATION;
            camera_param[1] = this.DOCKCAM_DISTORTION_COEFFICIENTS_SIMULATION;
        } else {
            Log.v((String)"KiboRpcApi", (String)"[getDockCamIntrinsics] Set ISS param");
            camera_param[0] = this.DOCKCAM_CAMERA_MATRIX_ISS;
            camera_param[1] = this.DOCKCAM_DISTORTION_COEFFICIENTS_ISS;
        }
        Log.i((String)"KiboRpcApi", (String)"[Finish] getDockCamIntrinsics");
        return camera_param;
    }

    public void saveBitmapImage(Bitmap image, String imageName) {
        try {
            Log.i((String)"KiboRpcApi", (String)"[Start] saveBitmapImage");
            boolean checkArgs = true;
            if (image == null) {
                Log.e((String)"KiboRpcApi", (String)"[saveBitmapImage] image is null");
                checkArgs = false;
            }
            if (imageName == null) {
                Log.e((String)"KiboRpcApi", (String)"[saveBitmapImage] imageName is null");
                checkArgs = false;
            }
            if (checkArgs) {
                Log.i((String)"KiboRpcApi", (String)("Parameters: image, imageName == " + imageName));
                Log.v((String)"KiboRpcApi", (String)"[saveBitmapImage] getOnSimulation: True");
                Log.v((String)"KiboRpcApi", (String)"[saveBitmapImage] Check directry");
                String filepath = this.gsService.getGuestScienceDataBasePath() + DEBUG_IMAGES_SAVE_DIR;
                File file = new File(filepath);
                if (!file.exists()) {
                    Log.v((String)"KiboRpcApi", (String)"[saveBitmapImage] Make save directry");
                    file.mkdir();
                }
                File[] list = file.listFiles();
                int width = image.getWidth();
                int height = image.getHeight();
                int size = width * height;
                if (list.length >= 50) {
                    Log.e((String)"KiboRpcApi", (String)"[saveBitmapImage] Can't save more than 50 images");
                } else if (size > 1228800) {
                    Log.e((String)"KiboRpcApi", (String)"[saveBitmapImage] The size is too large.");
                } else {
                    Log.v((String)"KiboRpcApi", (String)"[saveBitmapImage] Save bitmap image");
                    this.saveImage(image, imageName, file);
                }
            }
            Log.i((String)"KiboRpcApi", (String)"[Finish] saveBitmapImage");
        }
        catch (Exception e) {
            this.sendExceptionMessage("[saveBitmapImage] Internal error was occurred.", e);
        }
    }

    public void saveMatImage(Mat image, String imageName) {
        try {
            Log.i((String)"KiboRpcApi", (String)"[Start] saveMatImage");
            boolean checkArgs = true;
            if (image == null) {
                Log.e((String)"KiboRpcApi", (String)"[saveMatImage] image is null");
                checkArgs = false;
            }
            if (imageName == null) {
                Log.e((String)"KiboRpcApi", (String)"[saveMatImage] imageName is null");
                checkArgs = false;
            }
            if (checkArgs) {
                Log.i((String)"KiboRpcApi", (String)("Parameters: image, imageName == " + imageName));
                Log.v((String)"KiboRpcApi", (String)"[saveMatImage] getOnSimulation: True");
                Log.v((String)"KiboRpcApi", (String)"[saveMatImage] Check directry");
                String filepath = this.gsService.getGuestScienceDataBasePath() + DEBUG_IMAGES_SAVE_DIR;
                File file = new File(filepath);
                if (!file.exists()) {
                    Log.v((String)"KiboRpcApi", (String)"[saveMatImage] make save directory");
                    file.mkdir();
                }
                File[] list = file.listFiles();
                int width = image.width();
                int height = image.height();
                int size = width * height;
                if (list.length >= 50) {
                    Log.e((String)"KiboRpcApi", (String)"[saveMatImage] Can't save more than 50 images.");
                } else if (size > 1228800) {
                    Log.e((String)"KiboRpcApi", (String)"[saveMatImage] The size is too large.");
                } else {
                    Log.v((String)"KiboRpcApi", (String)"[saveMatImage] Save mat image");
                    Bitmap bitmapImage = Bitmap.createBitmap((int)image.width(), (int)image.height(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap((Mat)image, (Bitmap)bitmapImage);
                    this.saveImage(bitmapImage, imageName, file);
                }
            }
            Log.i((String)"KiboRpcApi", (String)"[Finish] saveMatImage");
        }
        catch (Exception e) {
            this.sendExceptionMessage("[saveMatImage] Internal error was occurred.", e);
        }
    }

    private void saveImage(Bitmap image, String imageName, File file) throws SecurityException, IOException, NullPointerException {
        String AttachName = file.getAbsolutePath() + "/" + imageName;
        Log.v((String)"KiboRpcApi", (String)AttachName);
        FileOutputStream out = new FileOutputStream(AttachName);
        image.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)out);
        out.flush();
        out.close();
    }

    public void setAreaInfo(int areaId, String itemName) {
        this.setAreaInfo(areaId, itemName, 1);
    }

    public void setAreaInfo(int areaId, String itemName, int number) {
        try {
            Log.i((String)"KiboRpcApi", (String)"[Start] setAreaInfo");
            boolean checkArgs = true;
            if (itemName == null) {
                Log.e((String)"KiboRpcApi", (String)"[saveBitmapImage] itemName is null");
                itemName = "";
            }
            Log.i((String)"KiboRpcApi", (String)("Parameters: areaId == " + areaId + ", itemName == " + itemName + ", number == " + number));
            this.areaItemMap.setAreaInfo(areaId, itemName, number);
            JSONObject data = new JSONObject();
            data.put("area_id", areaId);
            data.put("lost_item", (Object)itemName);
            data.put("num", number);
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
            Log.i((String)"KiboRpcApi", (String)"[Finish] setAreaInfo");
        }
        catch (Exception e) {
            this.sendExceptionMessage("[setAreaInfo] Internal error was occurred.", e);
        }
    }
}

