package p001jp.jaxa.iss.kibo.rpc.api;

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
import gov.nasa.arc.astrobee.android.p000gs.MessageType;
import gov.nasa.arc.astrobee.android.p000gs.StartGuestScienceService;
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
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.xmlrpc.serializer.ObjectArraySerializer;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.videoio.Videoio;
import org.ros.internal.transport.ConnectionHeaderFields;
import p001jp.jaxa.iss.kibo.rpc.api.areas.AreaItemMap;

/* renamed from: jp.jaxa.iss.kibo.rpc.api.KiboRpcApi */
public final class KiboRpcApi extends Activity {
    private static final String DEBUG_IMAGES_SAVE_DIR = "/immediate/DebugImages";
    private static final String EMULATOR_ROS_HOSTNAME = "hlp";
    private static final String NODE_NAME = "kibo_rpc_api";
    private static final URI ROS_MASTER_URI = URI.create("http://llp:11311");
    private static final String TARGET_IMAGES_SAVE_DIR = "/immediate/JudgeImages";
    private static KiboRpcApi instance = null;
    private final String AAR_VERSION = "5.0.0";
    private final int APPROACH_INTEVAL = 1000;
    private final int APPROACH_TIMES_SNAPSHOT_ISS = 2;
    private final int APPROACH_TIMES_SNAPSHOT_SIMULATION = 1;
    private final int BLINK_NUM = 2;
    private final float DISTANCE_THRESHOLD = 0.3f;
    private final double[] DOCKCAM_CAMERA_MATRIX_ISS = {753.51021d, 0.0d, 631.11512d, 0.0d, 751.3611d, 508.69621d, 0.0d, 0.0d, 1.0d};
    private final double[] DOCKCAM_CAMERA_MATRIX_SIMULATION = {661.783002d, 0.0d, 595.212041d, 0.0d, 671.508662d, 489.094196d, 0.0d, 0.0d, 1.0d};
    private final double[] DOCKCAM_DISTORTION_COEFFICIENTS_ISS = {-0.411405d, 0.17724d, -0.017145d, 0.006421d, 0.0d};
    private final double[] DOCKCAM_DISTORTION_COEFFICIENTS_SIMULATION = {-0.215168d, 0.044354d, 0.003615d, 0.005093d, 0.0d};
    private final float FLASH_LIGHT_MAX_IN_FINAL = 0.01f;
    private final float FLASH_LIGHT_MIN = 0.0f;
    private final float FLASH_LIGHT_REPORT_ROUNDING_COMPLETION = 0.05f;
    private final int LASER_WAIT_TIME = Videoio.CAP_IMAGES;
    private final boolean LIGHT_MODE_BACK = false;
    private final boolean LIGHT_MODE_FRONT = true;
    private final int MAX_IMAGES = 50;
    private final int MAX_IMAGE_SIZE = 1228800;
    private final double[] NAVCAM_CAMERA_MATRIX_ISS = {608.8073d, 0.0d, 632.53684d, 0.0d, 607.61439d, 549.08386d, 0.0d, 0.0d, 1.0d};
    private final double[] NAVCAM_CAMERA_MATRIX_SIMULATION = {523.10575d, 0.0d, 635.434258d, 0.0d, 534.765913d, 500.335102d, 0.0d, 0.0d, 1.0d};
    private final double[] NAVCAM_DISTORTION_COEFFICIENTS_ISS = {-0.212191d, 0.073843d, -9.18E-4d, 0.00189d, 0.0d};
    private final double[] NAVCAM_DISTORTION_COEFFICIENTS_SIMULATION = {-0.164787d, 0.020375d, -0.001572d, -3.69E-4d, 0.0d};
    private final String PAYLOAD_FINISH = "Mission Finish";
    private final String PAYLOAD_START = "Mission Start";
    private final String ROUNDING_COMPLETE = "rounding is complete";
    private final String SIGNAL_LIGHT_PERCHING_ARM = "Turn on the signal light and deploy the perching arm";
    private final String TARGET_RECOGNITION_COMPLETE = "Target Item recognition is complete";
    private final int TIMER_CHECK_RESOLUTION = 100;
    private final String TURN_ON_FOUND_PATTERN = "Turn on the found pattern";
    private final String TURN_ON_RECOGNITION_PATTERN = "Turn on the recognition pattern";
    private final String UNDOCK_ERROR = "Undock failed";
    private final String UNDOCK_FINISH = "Undock Finish";
    private final String UNDOCK_START = "Undock Start";
    private final int UNDOCK_TIMEOUT_ON_SIM = 100;
    private final int WAIT_AFTER_BLINK = 1000;
    private AreaItemMap areaItemMap;

    /* renamed from: df */
    private final DateFormat f3df = new SimpleDateFormat("yyyyMMdd hhmmssSSS");
    private RobotFactory factory;
    private GetterNode getterNode;
    private StartGuestScienceService gsService = null;
    private PlannerType plannerType = null;
    private boolean reportCompletion = false;
    private Robot robot;
    private RobotConfiguration robotConfiguration = new RobotConfiguration();
    private SetterNode setterNode;
    private boolean tookTargetItemSnap = false;

    private void sendExceptionMessage(String errmsg, Exception err) {
        Log.v("KiboRpcApi", "[Start] sendExceptionMessage");
        try {
            JSONObject data = new JSONObject();
            data.put(ConnectionHeaderFields.ERROR, "[" + err.getClass().getName() + "] " + errmsg);
            Log.e("KiboRpcApi", errmsg, err);
            this.gsService.sendData(MessageType.JSON, ObjectArraySerializer.DATA_TAG, data.toString());
        } catch (JSONException e) {
            this.gsService.sendData(MessageType.JSON, ObjectArraySerializer.DATA_TAG, e.getClass().getName());
            Log.e("KiboRpcApi", "[sendExceptionMessage] Failed to send error message.", e);
        }
        Log.v("KiboRpcApi", "[Finish] sendExceptionMessage");
    }

    private KiboRpcApi(StartGuestScienceService startGuestScienceService) {
        Log.v("KiboRpcApi", "[Start] KiboRpcApi");
        Log.i("KiboRpcApi", "[AARVersion] AAR version 5.0.0");
        configureRobot();
        DefaultRobotFactory defaultRobotFactory = new DefaultRobotFactory(this.robotConfiguration);
        this.factory = defaultRobotFactory;
        this.gsService = startGuestScienceService;
        try {
            this.robot = defaultRobotFactory.getRobot();
            this.getterNode = GetterNode.getInstance();
            this.setterNode = SetterNode.getInstance();
            this.areaItemMap = new AreaItemMap();
        } catch (AstrobeeException e) {
            sendExceptionMessage("[Constructor] Error with Astrobee", e);
        } catch (InterruptedException e2) {
            sendExceptionMessage("[Constructor] Connection Interrupted", e2);
        }
        Log.v("KiboRpcApi", "[Finish] KiboRpcApi");
    }

    private void configureRobot() {
        Log.v("KiboRpcApi", "[Start] configureRobot");
        this.robotConfiguration.setMasterUri(ROS_MASTER_URI);
        this.robotConfiguration.setHostname(EMULATOR_ROS_HOSTNAME);
        this.robotConfiguration.setNodeName(NODE_NAME);
        Log.v("KiboRpcApi", "[Finish] configureRobot");
    }

    private Result getCommandResult(PendingResult pending, boolean printRobotPosition, int timeout) {
        Log.v("KiboRpcApi", "[Start] getCommandResult");
        Result result = null;
        int counter = 0;
        while (true) {
            try {
                if (pending.isFinished()) {
                    result = pending.getResult();
                    printLogCommandResult(result);
                    break;
                }
                if (timeout >= 0) {
                    Log.v("KiboRpcApi", "[getCommandResult] Setting timeout");
                    if (counter > timeout) {
                        Log.v("KiboRpcApi", "[getCommandResult] return null");
                        break;
                    }
                }
                if (printRobotPosition) {
                    Log.v("KiboRpcApi", "[getCommandResult] Meanwhile, let's get the positioning along the trajectory");
                    Kinematics k = this.getterNode.getCurrentKinematics();
                    if (k.getPosition() != null) {
                        Log.i("KiboRpcApi", "[getCommandResult] Current Position: " + k.getPosition().toString());
                    }
                    if (k.getOrientation() != null) {
                        Log.i("KiboRpcApi", "[getCommandResult] Current Orientation: " + k.getOrientation().toString());
                    }
                }
                pending.getResult(1000, TimeUnit.MILLISECONDS);
                counter++;
            } catch (AstrobeeException e) {
                sendExceptionMessage("[getCommandResult] Error with Astrobee", e);
            } catch (InterruptedException e2) {
                sendExceptionMessage("[getCommandResult] Connection Interrupted", e2);
            } catch (TimeoutException e3) {
                sendExceptionMessage("[getCommandResult] Timeout connection", e3);
            } catch (Throwable th) {
            }
        }
        Log.v("KiboRpcApi", "[Finish] getCommandResult");
        return result;
    }

    private void printLogCommandResult(Result result) {
        Log.i("KiboRpcApi", "[Start] printLogCommandResult");
        if (result != null) {
            if (result.getStatus() != null) {
                Log.i("KiboRpcApi", "[printLogCommandResult] Command status: " + result.getStatus().toString());
            }
            if (!result.hasSucceeded()) {
                Log.e("KiboRpcApi", "[printLogCommandResult] Command message: " + result.getMessage());
            }
            Log.i("KiboRpcApi", "[printLogCommandResult] Done.");
            Log.i("KiboRpcApi", "[Finish] printLogCommandResult");
            return;
        }
        Log.e("KiboRpcApi", "[printLogCommandResult] Invalid result.");
    }

    private Result stopAllMotion() {
        Log.v("KiboRpcApi", "[Start] stopAllMotion");
        try {
            Result result = getCommandResult(this.robot.stopAllMotion(), false, -1);
            Log.v("KiboRpcApi", "[Finish] stopAllMotion");
            return result;
        } catch (AstrobeeRuntimeException e) {
            sendExceptionMessage("[stopAllMotion] Node not ready or dead.", e);
            return null;
        }
    }

    private boolean setPlanner(PlannerType plannerType2) {
        Log.v("KiboRpcApi", "[Start] setPlanner");
        try {
            Result result = getCommandResult(this.robot.setPlanner(plannerType2), false, 5);
            if (result != null) {
                if (result.hasSucceeded()) {
                    this.plannerType = plannerType2;
                    Log.v("KiboRpcApi", "[setPlanner] Planner is set to " + plannerType2);
                }
                Log.v("KiboRpcApi", "[Finish] setPlanner");
                return result.hasSucceeded();
            }
            Log.e("KiboRpcApi", "[setPlanner] Invalid result.");
            return false;
        } catch (AstrobeeRuntimeException e) {
            sendExceptionMessage("[setPlanner] Node not ready or dead.", e);
            return false;
        }
    }

    public void shutdownFactory() {
        Log.v("KiboRpcApi", "[Start] shutdownFactory");
        this.factory.shutdown();
        Log.v("KiboRpcApi", "[Finish] shutdownFactory");
    }

    public static KiboRpcApi getInstance(StartGuestScienceService startGuestScienceService) {
        Log.v("KiboRpcApi", "[Start] getInstance");
        if (instance == null) {
            Log.v("KiboRpcApi", "[getInstance] Make instance");
            instance = new KiboRpcApi(startGuestScienceService);
        }
        Log.v("KiboRpcApi", "[Finish] getInstance");
        return instance;
    }

    private Kinematics getTrustedRobotKinematics() {
        Log.i("KiboRpcApi", "[Start] getTrustedRobotKinematics");
        Log.i("KiboRpcApi", "[getTrustedRobotKinematics] Waiting for robot to acquire position.");
        Kinematics k = null;
        long start_point = System.currentTimeMillis();
        long end_point = System.currentTimeMillis();
        while (true) {
            if (end_point - start_point >= 30000) {
                break;
            }
            k = this.getterNode.getCurrentKinematics();
            if (k.getConfidence() == Kinematics.Confidence.GOOD) {
                Log.v("KiboRpcApi", "[getTrustedRobotKinematics] Break loop");
                break;
            }
            k = null;
            try {
                Thread.sleep(1000);
                end_point = System.currentTimeMillis();
            } catch (InterruptedException e) {
                Log.e("KiboRpcApi", "[getTrustedRobotkinematics] It was not possible to get a trusted kinematics. Sorry.");
                return null;
            }
        }
        Log.i("KiboRpcApi", "[Finish] getTrustedRobotKinematics");
        return k;
    }

    public Kinematics getRobotKinematics() {
        Log.i("KiboRpcApi", "[Start] getRobotKinematics");
        Log.i("KiboRpcApi", "[getRobotKinematics] Waiting for robot to acquire position.");
        Log.i("KiboRpcApi", "[Finish] getRobotKinematics");
        return this.getterNode.getCurrentKinematics();
    }

    public Bitmap getBitmapNavCam() {
        Log.i("KiboRpcApi", "[Start] getBitmapNavCam");
        Bitmap ret = this.getterNode.getBitmapNavCam();
        if (ret == null) {
            Log.i("KiboRpcApi", "[getBitmapNavCam] ret is null.");
            Log.e("KiboRpcApi", "[getBitmapNavCam] It was not possible to get a Bitmap from Nav Cam.");
        }
        Log.i("KiboRpcApi", "[Finish] getBitmapNavCam");
        return ret;
    }

    public Bitmap getBitmapDockCam() {
        Log.i("KiboRpcApi", "[Start] getBitmapDockCam");
        Bitmap ret = this.getterNode.getBitmapDockCam();
        if (ret == null) {
            Log.i("KiboRpcApi", "[getBitmapDockCam] ret is null.");
            Log.e("KiboRpcApi", "[getBitmapDockCam] It was not possible to get a Bitmap from Dock Cam.");
        }
        Log.i("KiboRpcApi", "[Finish] getBitmapDockCam");
        return ret;
    }

    public Mat getMatNavCam() {
        Log.i("KiboRpcApi", "[Start] getMatNavCam");
        Mat ret = this.getterNode.getMatNavCam();
        if (ret == null) {
            Log.i("KiboRpcApi", "[getMatNavCam] ret is null.");
            Log.e("KiboRpcApi", "[getMatNavCam] It was not possible to get a Mat from Nav Cam.");
        }
        Log.i("KiboRpcApi", "[Finish] getMatNavCam");
        return ret;
    }

    public Mat getMatDockCam() {
        Log.i("KiboRpcApi", "[Start] getMatDockCam");
        Mat ret = this.getterNode.getMatDockCam();
        if (ret == null) {
            Log.i("KiboRpcApi", "[getMatDockCam] ret is null.");
            Log.e("KiboRpcApi", "[getMatDockCam] It was not possible to get a Mat from Dock Cam.");
        }
        Log.i("KiboRpcApi", "[Finish] getMatDockCam");
        return ret;
    }

    public Result flashlightControlFront(float brightness) {
        Log.i("KiboRpcApi", "[Start] flashlightControlFront");
        if (!this.getterNode.getOnSimulation() && brightness > 0.01f) {
            Log.v("KiboRpcApi", "[flashlightControlFront] In the final, the maximum value of the light is set to 0.01.");
            brightness = 0.01f;
        }
        Log.i("KiboRpcApi", "Parameters: brightness == " + brightness);
        try {
            PendingResult pendingResult = this.robot.setFlashlightBrightness(FlashlightLocation.FRONT, brightness);
            Log.i("KiboRpcApi", "[Finish] flashlightControlFront");
            return getCommandResult(pendingResult, false, -1);
        } catch (AstrobeeRuntimeException e) {
            sendExceptionMessage("[flashlightControlFront] Node not ready or dead.", e);
            return null;
        }
    }

    public Result flashlightControlBack(float brightness) {
        Log.i("KiboRpcApi", "[Start] flashlightControlBack");
        if (!this.getterNode.getOnSimulation() && brightness > 0.01f) {
            Log.v("KiboRpcApi", "[flashlightControlBack] In the final, the maximum value of the light is set to 0.01.");
            brightness = 0.01f;
        }
        Log.i("KiboRpcApi", "Parameters: brightness == " + brightness);
        try {
            PendingResult pendingResult = this.robot.setFlashlightBrightness(FlashlightLocation.BACK, brightness);
            Log.i("KiboRpcApi", "[Finish] flashlightControlBack");
            return getCommandResult(pendingResult, false, -1);
        } catch (AstrobeeRuntimeException e) {
            sendExceptionMessage("[flashlightControlBack] Node not ready or dead.", e);
            return null;
        }
    }

    private Result flashlightControl(float brightness, boolean front) {
        FlashlightLocation flloc;
        FlashlightLocation flashlightLocation = FlashlightLocation.FRONT;
        if (front) {
            Log.i("KiboRpcApi", "[Start] flashlightControl: front");
            Log.i("KiboRpcApi", "Parameters: brightness == " + brightness);
            flloc = FlashlightLocation.FRONT;
        } else {
            Log.i("KiboRpcApi", "[Start] flashlightControl: back");
            Log.i("KiboRpcApi", "Parameters: brightness == " + brightness);
            flloc = FlashlightLocation.BACK;
        }
        try {
            PendingResult pendingResult = this.robot.setFlashlightBrightness(flloc, brightness);
            Log.i("KiboRpcApi", "[Finish] flashlightControlFront");
            return getCommandResult(pendingResult, false, -1);
        } catch (AstrobeeRuntimeException e) {
            sendExceptionMessage("[flashlightControlFront] Node not ready or dead.", e);
            return null;
        }
    }

    public Result moveTo(Point goalPoint, Quaternion orientation, boolean printRobotPosition) {
        Log.i("KiboRpcApi", "[Start] moveTo");
        Log.i("KiboRpcApi", "Parameters: goalPoint == " + goalPoint + ", orientation == " + orientation + ", printRobotPosition == " + printRobotPosition);
        if (goalPoint == null) {
            Log.i("KiboRpcApi", "[moveTo] goalPoint == " + goalPoint);
            Log.e("KiboRpcApi", "[moveTo] goalPoint is invalid.");
            return null;
        } else if (orientation == null) {
            Log.i("KiboRpcApi", "[moveTo] orientation == " + orientation);
            Log.e("KiboRpcApi", "[moveTo] orientation is invalid.");
            return null;
        } else if (!setPlanner(PlannerType.TRAPEZOIDAL)) {
            Log.e("KiboRpcApi", "[moveTo] Cannot set planner.");
            return null;
        } else {
            Log.i("KiboRpcApi", "[moveTo] Planner is set to " + this.plannerType.toString() + ".");
            Result result = stopAllMotion();
            if (result == null) {
                Log.i("KiboRpcApi", "[moveTo] result == " + result);
                Log.e("KiboRpcApi", "[moveTo] Cannot stop all motion.");
                return null;
            }
            if (result.hasSucceeded()) {
                Log.i("KiboRpcApi", "[moveTo] Planner is " + this.plannerType.toString() + ".");
                Log.i("KiboRpcApi", "[moveTo] Moving the bee.");
                try {
                    result = getCommandResult(this.robot.simpleMove6DOF(goalPoint, orientation), printRobotPosition, -1);
                } catch (AstrobeeRuntimeException e) {
                    sendExceptionMessage("[moveTo] Node not ready or dead.", e);
                    return null;
                }
            }
            Log.i("KiboRpcApi", "[Finish] moveTo");
            return result;
        }
    }

    public Result relativeMoveTo(Point goalPoint, Quaternion orientation, boolean printRobotPosition) {
        Point point = goalPoint;
        Quaternion quaternion = orientation;
        boolean z = printRobotPosition;
        Log.i("KiboRpcApi", "[Start] relativeMoveTo");
        Log.i("KiboRpcApi", "Parameters: goalPoint == " + point + ", orientation == " + quaternion + ", printRobotPosition == " + z);
        if (point == null) {
            Log.i("KiboRpcApi", "[relativeMoveTo] goalPoint == " + point);
            Log.e("KiboRpcApi", "[relativeMoveTo] goalPoint is invalid.");
            return null;
        } else if (quaternion == null) {
            Log.i("KiboRpcApi", "[relativeMoveTo] orientation == " + quaternion);
            Log.e("KiboRpcApi", "[relativeMoveTo] orientation is invalid.");
            return null;
        } else {
            Kinematics k = getTrustedRobotKinematics();
            if (k == null) {
                Log.i("KiboRpcApi", "[relativeMoveTo] k == " + k);
                Log.e("KiboRpcApi", "[relativeMoveTo] Cannot get robot kinematics.");
                return null;
            }
            Point currPosition = k.getPosition();
            Point endPoint = new Point(currPosition.getX() + goalPoint.getX(), currPosition.getY() + goalPoint.getY(), currPosition.getZ() + goalPoint.getZ());
            Log.i("KiboRpcApi", "[Finish] relativeMoveTo");
            return moveTo(endPoint, quaternion, z);
        }
    }

    private void setSignalStateFoundPattern() {
        try {
            JSONObject data = new JSONObject();
            Log.v("KiboRpcApi", "[Start] setSignalStateFoundPattern");
            this.setterNode.setSignalState((byte) 4, 2);
            this.setterNode.setSignalState((byte) 5, 1);
            this.setterNode.setSignalState((byte) 3, 1);
            data.put("signal_light", "Turn on the found pattern");
            this.gsService.sendData(MessageType.JSON, ObjectArraySerializer.DATA_TAG, data.toString());
            Log.v("KiboRpcApi", "[Finish] setSignalStateFoundPattern");
        } catch (JSONException e) {
            sendExceptionMessage("[setSignalStateFoundPattern] Internal error occurred. Unable to serialize data to JSON.", e);
        } catch (Exception e2) {
            sendExceptionMessage("[setSignalStateFoundPattern] Internal error occurred. Unable to send data to gds.", e2);
        }
    }

    private void setSignalStateRecognitionPattern() {
        try {
            JSONObject data = new JSONObject();
            Log.v("KiboRpcApi", "[Start] setSignalStateRecognitionPattern");
            this.setterNode.setSignalState((byte) 3, 1);
            data.put("signal_light", "Turn on the recognition pattern");
            this.gsService.sendData(MessageType.JSON, ObjectArraySerializer.DATA_TAG, data.toString());
            Log.v("KiboRpcApi", "[Finish] setSignalStateRecognitionPattern");
        } catch (JSONException e) {
            sendExceptionMessage("[setSignalStateRecognitionPattern] Internal error occurred. Unable to serialize data to JSON.", e);
        } catch (Exception e2) {
            sendExceptionMessage("[setSignalStateRecognitionPattern] Internal error occurred. Unable to send data to gds.", e2);
        }
    }

    public boolean startMission() {
        Log.i("KiboRpcApi", "[Start] startMission");
        boolean isSuccess = false;
        try {
            JSONObject data = new JSONObject();
            String time = this.f3df.format(new Date(System.currentTimeMillis()));
            data.put("t_stamp_undock", time);
            data.put("status", "Undock Start");
            this.gsService.sendData(MessageType.JSON, ObjectArraySerializer.DATA_TAG, data.toString());
            Log.i("KiboRpcApi", "Undock Start: " + time);
            PendingResult pendingResult = this.robot.undock();
            int timeout = 100;
            if (!this.getterNode.getOnSimulation()) {
                Log.d("KiboRpcApi", "set timeout -1");
                timeout = -1;
            }
            Result result = getCommandResult(pendingResult, false, timeout);
            JSONObject data2 = new JSONObject();
            if (result == null || !result.hasSucceeded()) {
                data2.put("failed", "Undock failed");
                String time2 = this.f3df.format(new Date(System.currentTimeMillis()));
                Log.e("KiboRpcApi", "Undock failed: " + time2);
            } else {
                data2.put("status", "Undock Finish");
                isSuccess = true;
                String time3 = this.f3df.format(new Date(System.currentTimeMillis()));
                Log.i("KiboRpcApi", "Undock Finish: " + time3);
            }
            this.gsService.sendData(MessageType.JSON, ObjectArraySerializer.DATA_TAG, data2.toString());
            if (isSuccess) {
                String time4 = this.f3df.format(new Date(System.currentTimeMillis()));
                JSONObject data3 = new JSONObject();
                data3.put("t_stamp_start", time4);
                data3.put("status", "Mission Start");
                this.gsService.sendData(MessageType.JSON, ObjectArraySerializer.DATA_TAG, data3.toString());
                Log.i("KiboRpcApi", "Mission Start: " + time4);
            }
            Log.i("KiboRpcApi", "[Finish] startMission");
            return isSuccess;
        } catch (JSONException e) {
            sendExceptionMessage("[startMission] Internal error occurred. Unable to serialize data to JSON.", e);
            return false;
        } catch (Exception e2) {
            sendExceptionMessage("[startMission] Internal error occurred. Unable to send data to gds.", e2);
            return false;
        }
    }

    @Deprecated
    private Result setQuietMode() {
        try {
            Log.v("KiboRpcApi", "[Start] setQuietMode");
            Log.i("KiboRpcApi", "[setQuietMode] Change the flight mode to Quiet mode.");
            Result result = getCommandResult(this.robot.setOperatingLimits("iss_quiet", FlightMode.QUIET, 0.02f, 0.002f, 0.0174f, 0.0174f, 0.25f), true, -1);
            Log.i("KiboRpcApi", "[setQuietMode] Changed flight mode to Quiet mode.");
            Log.v("KiboRpcApi", "[Finish] setQuietMode");
            return result;
        } catch (AstrobeeRuntimeException e) {
            sendExceptionMessage("[setQuietMode] Node not ready or dead.", e);
            return null;
        }
    }

    @Deprecated
    private Result setLomoMode() {
        try {
            Log.v("KiboRpcApi", "[Start] setLomoMode");
            Log.i("KiboRpcApi", "[setLomoMode] Change the flight mode to Lomo mode.");
            Result result = getCommandResult(this.robot.setOperatingLimits("iss_lomo", FlightMode.NOMINAL, 0.15f, 0.0175f, 0.0873f, 0.1745f, 0.25f), true, -1);
            Log.i("KiboRpcApi", "[setLomoMode] Changed flight mode to Lomo mode.");
            Log.v("KiboRpcApi", "[Finish] setLomoMode");
            return result;
        } catch (AstrobeeRuntimeException e) {
            sendExceptionMessage("[setLomoMode] Node not ready or dead.", e);
            return null;
        }
    }

    public void notifyRecognitionItem() {
        Log.i("KiboRpcApi", "[Start] notifyRecognitionItem");
        try {
            Log.v("KiboRpcApi", "[notifyRecognitionItem] Target Item recognition is complete");
            JSONObject data = new JSONObject();
            data.put("status", "Target Item recognition is complete");
            this.gsService.sendData(MessageType.JSON, ObjectArraySerializer.DATA_TAG, data.toString());
            if (!this.getterNode.getOnSimulation()) {
                setSignalStateRecognitionPattern();
                this.setterNode.setSignalState((byte) 17, 1);
            }
            Log.i("KiboRpcApi", "[Finish] notifyRecognitionItem");
        } catch (JSONException e) {
            sendExceptionMessage("[notifyRecognitionItem] Internal error was occurred. Unable to serialize data to JSON.", e);
        } catch (Exception e2) {
            sendExceptionMessage("[notifyRecognitionItem] Internal error was occurred. Unable to send data to gds.", e2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x01b2, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x01b3, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x01bc, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x01bd, code lost:
        sendExceptionMessage("[reportRoundingCompletion] Internal error was occurred.", r0);
        r1.reportCompletion = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x01c5, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x01c6, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x01c7, code lost:
        r2 = false;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x01bc A[ExcHandler: Exception (r0v3 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:1:0x000c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean reportRoundingCompletion() {
        /*
            r16 = this;
            r1 = r16
            java.lang.String r0 = "%.3f"
            java.lang.String r2 = "KiboRpcApi"
            java.lang.String r3 = "[Start] reportRoundingCompletion"
            android.util.Log.i(r2, r3)
            r3 = 0
            boolean r4 = r1.reportCompletion     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r5 = 1
            if (r4 != 0) goto L_0x01b5
            java.lang.String r4 = "[reportRoundingCompletion] Make rounding is complete message"
            android.util.Log.v(r2, r4)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r4.<init>()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r6 = "t_stamp_rounding_completion"
            java.text.DateFormat r7 = r1.f3df     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.util.Date r8 = new java.util.Date     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r8.<init>(r9)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r7 = r7.format(r8)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r4.put(r6, r7)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r6 = "status"
            java.lang.String r7 = "rounding is complete"
            r4.put(r6, r7)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r6.<init>()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            jp.jaxa.iss.kibo.rpc.api.areas.AreaItemMap r7 = r1.areaItemMap     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            org.json.JSONObject r7 = r7.getAreaItemMapJson()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r6 = r7
            java.util.Iterator r7 = r6.keys()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
        L_0x0046:
            boolean r8 = r7.hasNext()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            if (r8 == 0) goto L_0x005b
            java.lang.Object r8 = r7.next()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            org.json.JSONArray r9 = r6.getJSONArray(r8)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r4.put(r8, r9)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            goto L_0x0046
        L_0x005b:
            java.lang.String r7 = "[reportRoundingCompletion] Do getRobotKinematics"
            android.util.Log.v(r2, r7)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            gov.nasa.arc.astrobee.Kinematics r7 = r16.getRobotKinematics()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            if (r7 == 0) goto L_0x018e
            gov.nasa.arc.astrobee.types.Point r8 = r7.getPosition()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            if (r8 == 0) goto L_0x018e
            gov.nasa.arc.astrobee.types.Quaternion r8 = r7.getOrientation()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            if (r8 == 0) goto L_0x018e
            java.lang.Object[] r8 = new java.lang.Object[r5]     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            gov.nasa.arc.astrobee.types.Point r9 = r7.getPosition()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            double r9 = r9.getX()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Double r9 = java.lang.Double.valueOf(r9)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r8[r3] = r9     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r8 = java.lang.String.format(r0, r8)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Object[] r9 = new java.lang.Object[r5]     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            gov.nasa.arc.astrobee.types.Point r10 = r7.getPosition()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            double r10 = r10.getY()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Double r10 = java.lang.Double.valueOf(r10)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r9[r3] = r10     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r9 = java.lang.String.format(r0, r9)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Object[] r10 = new java.lang.Object[r5]     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            gov.nasa.arc.astrobee.types.Point r11 = r7.getPosition()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            double r11 = r11.getZ()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Double r11 = java.lang.Double.valueOf(r11)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r10[r3] = r11     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r10 = java.lang.String.format(r0, r10)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Object[] r11 = new java.lang.Object[r5]     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            gov.nasa.arc.astrobee.types.Quaternion r12 = r7.getOrientation()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            float r12 = r12.getX()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Float r12 = java.lang.Float.valueOf(r12)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r11[r3] = r12     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r11 = java.lang.String.format(r0, r11)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Object[] r12 = new java.lang.Object[r5]     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            gov.nasa.arc.astrobee.types.Quaternion r13 = r7.getOrientation()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            float r13 = r13.getY()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Float r13 = java.lang.Float.valueOf(r13)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r12[r3] = r13     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r12 = java.lang.String.format(r0, r12)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Object[] r13 = new java.lang.Object[r5]     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            gov.nasa.arc.astrobee.types.Quaternion r14 = r7.getOrientation()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            float r14 = r14.getZ()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Float r14 = java.lang.Float.valueOf(r14)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r13[r3] = r14     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r13 = java.lang.String.format(r0, r13)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Object[] r14 = new java.lang.Object[r5]     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            gov.nasa.arc.astrobee.types.Quaternion r15 = r7.getOrientation()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            float r15 = r15.getW()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.Float r15 = java.lang.Float.valueOf(r15)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r14[r3] = r15     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r0 = java.lang.String.format(r0, r14)     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r14 = "rounding_point_pos"
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            r15.<init>()     // Catch:{ JSONException -> 0x01c6, Exception -> 0x01bc }
            java.lang.String r3 = "[ posX : "
            r15.append(r3)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r15.append(r8)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            java.lang.String r3 = ", posY : "
            r15.append(r3)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r15.append(r9)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            java.lang.String r3 = ", posZ "
            r15.append(r3)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r15.append(r10)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            java.lang.String r3 = ", quaX: "
            r15.append(r3)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r15.append(r11)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            java.lang.String r3 = ", quaY: "
            r15.append(r3)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r15.append(r12)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            java.lang.String r3 = ", quaZ: "
            r15.append(r3)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r15.append(r13)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            java.lang.String r3 = ", quaW: "
            r15.append(r3)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r15.append(r0)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            java.lang.String r3 = " ]"
            r15.append(r3)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            java.lang.String r3 = r15.toString()     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r4.put(r14, r3)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            gov.nasa.arc.astrobee.android.gs.StartGuestScienceService r0 = r1.gsService     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            gov.nasa.arc.astrobee.android.gs.MessageType r3 = gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            java.lang.String r8 = "data"
            java.lang.String r9 = r4.toString()     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r0.sendData((gov.nasa.arc.astrobee.android.p000gs.MessageType) r3, (java.lang.String) r8, (java.lang.String) r9)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r0 = 0
        L_0x0158:
            r3 = 2
            if (r0 >= r3) goto L_0x0186
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r3.<init>()     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            java.lang.String r8 = "[reportRoundingCompletion] Count: "
            r3.append(r8)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r3.append(r0)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            android.util.Log.v(r2, r3)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r3 = 1028443341(0x3d4ccccd, float:0.05)
            r1.flashlightControl(r3, r5)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r8 = 0
            r1.flashlightControl(r3, r8)     // Catch:{ JSONException -> 0x0183, Exception -> 0x01bc }
            r3 = 0
            r1.flashlightControl(r3, r5)     // Catch:{ JSONException -> 0x0183, Exception -> 0x01bc }
            r1.flashlightControl(r3, r8)     // Catch:{ JSONException -> 0x0183, Exception -> 0x01bc }
            int r0 = r0 + 1
            goto L_0x0158
        L_0x0183:
            r0 = move-exception
            r2 = r8
            goto L_0x01c8
        L_0x0186:
            r8 = 1000(0x3e8, double:4.94E-321)
            java.lang.Thread.sleep(r8)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            r1.reportCompletion = r5     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            goto L_0x01b5
        L_0x018e:
            java.lang.String r0 = "[reportRoundingCompletion] It was not possible to get a kinematics."
            android.util.Log.e(r2, r0)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            if (r7 != 0) goto L_0x019a
            java.lang.String r0 = "[reportRoundingCompletion] kinematics is null."
            android.util.Log.e(r2, r0)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
        L_0x019a:
            gov.nasa.arc.astrobee.types.Point r0 = r7.getPosition()     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            if (r0 != 0) goto L_0x01a5
            java.lang.String r0 = "[reportRoundingCompletion] kinematics.getPosition() is null."
            android.util.Log.e(r2, r0)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
        L_0x01a5:
            gov.nasa.arc.astrobee.types.Quaternion r0 = r7.getOrientation()     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
            if (r0 != 0) goto L_0x01b0
            java.lang.String r0 = "[reportRoundingCompletion] kinematics.getOrientation() is null."
            android.util.Log.e(r2, r0)     // Catch:{ JSONException -> 0x01b2, Exception -> 0x01bc }
        L_0x01b0:
            r2 = 0
            return r2
        L_0x01b2:
            r0 = move-exception
            r2 = 0
            goto L_0x01c8
        L_0x01b5:
            java.lang.String r0 = "[Finish] reportRoundingCompletion"
            android.util.Log.i(r2, r0)
            return r5
        L_0x01bc:
            r0 = move-exception
            java.lang.String r2 = "[reportRoundingCompletion] Internal error was occurred."
            r1.sendExceptionMessage(r2, r0)
            r2 = 0
            r1.reportCompletion = r2
            return r2
        L_0x01c6:
            r0 = move-exception
            r2 = r3
        L_0x01c8:
            java.lang.String r3 = "[reportRoundingCompletion] Internal error was occurred. Unable to serialize data to JSON."
            r1.sendExceptionMessage(r3, r0)
            r1.reportCompletion = r2
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p001jp.jaxa.iss.kibo.rpc.api.KiboRpcApi.reportRoundingCompletion():boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x01bf, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x01c0, code lost:
        sendExceptionMessage("[takeTargetItemSnapshot] Internal error was occurred.", r0);
        r1.tookTargetItemSnap = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x01c8, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x01c9, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x01ca, code lost:
        r2 = false;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x01bf A[ExcHandler: Exception (r0v3 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:1:0x000e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void takeTargetItemSnapshot() {
        /*
            r20 = this;
            r1 = r20
            java.lang.String r0 = "data"
            java.lang.String r2 = "%.3f"
            java.lang.String r3 = "KiboRpcApi"
            java.lang.String r4 = "[Start] takeTargetItemSnapshot"
            android.util.Log.i(r3, r4)
            r4 = 0
            boolean r5 = r1.tookTargetItemSnap     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            if (r5 != 0) goto L_0x01b8
            r5 = 1
            r6 = 1
            jp.jaxa.iss.kibo.rpc.api.GetterNode r7 = r1.getterNode     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            boolean r7 = r7.getOnSimulation()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.String r8 = "[takeTargetItemSnapshot] getOnSimulation: False"
            if (r7 != 0) goto L_0x0027
            android.util.Log.v(r3, r8)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.String r7 = "[takeTargetItemSnapshot] Set approach_time"
            android.util.Log.v(r3, r7)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            r6 = 2
        L_0x0027:
            boolean r7 = r1.takeSnapshot(r6)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            r5 = r7
            if (r5 != 0) goto L_0x0033
            java.lang.String r7 = "[takeTargetItemSnapshot] Fail to Take the targetItem Snapshot."
            android.util.Log.e(r3, r7)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
        L_0x0033:
            java.lang.String r7 = "[takeTargetItemSnapshot] Make finish message"
            android.util.Log.v(r3, r7)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            r7.<init>()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.String r9 = "t_stamp_finish"
            java.text.DateFormat r10 = r1.f3df     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.util.Date r11 = new java.util.Date     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            r11.<init>(r12)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.String r10 = r10.format(r11)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            r7.put(r9, r10)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.String r9 = "status"
            java.lang.String r10 = "Mission Finish"
            r7.put(r9, r10)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            gov.nasa.arc.astrobee.android.gs.StartGuestScienceService r9 = r1.gsService     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            gov.nasa.arc.astrobee.android.gs.MessageType r10 = gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.String r11 = r7.toString()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            r9.sendData((gov.nasa.arc.astrobee.android.p000gs.MessageType) r10, (java.lang.String) r0, (java.lang.String) r11)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.String r9 = "[takeTargetItemSnapshot] Do getRobotKinematics"
            android.util.Log.v(r3, r9)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            gov.nasa.arc.astrobee.Kinematics r9 = r20.getRobotKinematics()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            r10 = 1
            if (r9 == 0) goto L_0x016c
            gov.nasa.arc.astrobee.types.Point r11 = r9.getPosition()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            if (r11 == 0) goto L_0x016c
            gov.nasa.arc.astrobee.types.Quaternion r11 = r9.getOrientation()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            if (r11 == 0) goto L_0x016c
            java.lang.Object[] r11 = new java.lang.Object[r10]     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            gov.nasa.arc.astrobee.types.Point r12 = r9.getPosition()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            double r12 = r12.getX()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.Double r12 = java.lang.Double.valueOf(r12)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            r11[r4] = r12     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.String r11 = java.lang.String.format(r2, r11)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.Object[] r12 = new java.lang.Object[r10]     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            gov.nasa.arc.astrobee.types.Point r13 = r9.getPosition()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            double r13 = r13.getY()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.Double r13 = java.lang.Double.valueOf(r13)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            r12[r4] = r13     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.String r12 = java.lang.String.format(r2, r12)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.Object[] r13 = new java.lang.Object[r10]     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            gov.nasa.arc.astrobee.types.Point r14 = r9.getPosition()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            double r14 = r14.getZ()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.Double r14 = java.lang.Double.valueOf(r14)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            r13[r4] = r14     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.String r13 = java.lang.String.format(r2, r13)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.Object[] r14 = new java.lang.Object[r10]     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            gov.nasa.arc.astrobee.types.Quaternion r15 = r9.getOrientation()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            float r15 = r15.getX()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.Float r15 = java.lang.Float.valueOf(r15)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            r14[r4] = r15     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.String r14 = java.lang.String.format(r2, r14)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.Object[] r15 = new java.lang.Object[r10]     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            gov.nasa.arc.astrobee.types.Quaternion r16 = r9.getOrientation()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            float r16 = r16.getY()     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.Float r16 = java.lang.Float.valueOf(r16)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            r15[r4] = r16     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.String r15 = java.lang.String.format(r2, r15)     // Catch:{ JSONException -> 0x01c9, Exception -> 0x01bf }
            java.lang.Object[] r4 = new java.lang.Object[r10]     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            gov.nasa.arc.astrobee.types.Quaternion r17 = r9.getOrientation()     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            float r17 = r17.getZ()     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.Float r17 = java.lang.Float.valueOf(r17)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r16 = 0
            r4[r16] = r17     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.String r4 = java.lang.String.format(r2, r4)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r17 = r5
            java.lang.Object[] r5 = new java.lang.Object[r10]     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            gov.nasa.arc.astrobee.types.Quaternion r18 = r9.getOrientation()     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            float r18 = r18.getW()     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.Float r18 = java.lang.Float.valueOf(r18)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r16 = 0
            r5[r16] = r18     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.String r2 = java.lang.String.format(r2, r5)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r5.<init>()     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r7 = r5
            java.lang.String r5 = "found_point_pos"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r10.<init>()     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r19 = r6
            java.lang.String r6 = "[ posX : "
            r10.append(r6)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r10.append(r11)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.String r6 = ", posY : "
            r10.append(r6)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r10.append(r12)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.String r6 = ", posZ "
            r10.append(r6)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r10.append(r13)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.String r6 = ", quaX: "
            r10.append(r6)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r10.append(r14)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.String r6 = ", quaY: "
            r10.append(r6)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r10.append(r15)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.String r6 = ", quaZ: "
            r10.append(r6)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r10.append(r4)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.String r6 = ", quaW: "
            r10.append(r6)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r10.append(r2)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.String r6 = " ]"
            r10.append(r6)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.String r6 = r10.toString()     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r7.put(r5, r6)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            gov.nasa.arc.astrobee.android.gs.StartGuestScienceService r5 = r1.gsService     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            gov.nasa.arc.astrobee.android.gs.MessageType r6 = gov.nasa.arc.astrobee.android.p000gs.MessageType.JSON     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.String r10 = r7.toString()     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r5.sendData((gov.nasa.arc.astrobee.android.p000gs.MessageType) r6, (java.lang.String) r0, (java.lang.String) r10)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            goto L_0x0192
        L_0x016c:
            r17 = r5
            r19 = r6
            java.lang.String r0 = "[takeTargetItemSnapshot] It was not possible to get a kinematics."
            android.util.Log.e(r3, r0)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            if (r9 != 0) goto L_0x017c
            java.lang.String r0 = "[takeTargetItemSnapshot] kinematics is null."
            android.util.Log.e(r3, r0)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
        L_0x017c:
            gov.nasa.arc.astrobee.types.Point r0 = r9.getPosition()     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            if (r0 != 0) goto L_0x0187
            java.lang.String r0 = "[takeTargetItemSnapshot] kinematics.getPosition() is null."
            android.util.Log.e(r3, r0)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
        L_0x0187:
            gov.nasa.arc.astrobee.types.Quaternion r0 = r9.getOrientation()     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            if (r0 != 0) goto L_0x0192
            java.lang.String r0 = "[takeTargetItemSnapshot] kinematics.getOrientation() is null."
            android.util.Log.e(r3, r0)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
        L_0x0192:
            jp.jaxa.iss.kibo.rpc.api.GetterNode r0 = r1.getterNode     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            boolean r0 = r0.getOnSimulation()     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            if (r0 != 0) goto L_0x01b1
            android.util.Log.v(r3, r8)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            java.lang.String r0 = "[takeTargetItemSnapshot] Set SignalState"
            android.util.Log.v(r3, r0)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r20.setSignalStateFoundPattern()     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            jp.jaxa.iss.kibo.rpc.api.SetterNode r0 = r1.setterNode     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r2 = 17
            java.lang.Byte r2 = java.lang.Byte.valueOf(r2)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            r4 = 1
            r0.setSignalState(r2, r4)     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
        L_0x01b1:
            r0 = 1
            r1.tookTargetItemSnap = r0     // Catch:{ JSONException -> 0x01b5, Exception -> 0x01bf }
            goto L_0x01b8
        L_0x01b5:
            r0 = move-exception
            r2 = 0
            goto L_0x01cb
        L_0x01b8:
            java.lang.String r0 = "[Finish] takeTargetItemSnapshot"
            android.util.Log.i(r3, r0)
            return
        L_0x01bf:
            r0 = move-exception
            java.lang.String r2 = "[takeTargetItemSnapshot] Internal error was occurred."
            r1.sendExceptionMessage(r2, r0)
            r2 = 0
            r1.tookTargetItemSnap = r2
            return
        L_0x01c9:
            r0 = move-exception
            r2 = r4
        L_0x01cb:
            java.lang.String r3 = "[takeTargetItemSnapshot] Internal error was occurred. Unable to serialize data to JSON."
            r1.sendExceptionMessage(r3, r0)
            r1.tookTargetItemSnap = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p001jp.jaxa.iss.kibo.rpc.api.KiboRpcApi.takeTargetItemSnapshot():void");
    }

    private boolean takeSnapshot(int approach_times) {
        try {
            Log.v("KiboRpcApi", "[Start] takeSnapshot");
            for (int i = 1; i <= approach_times; i++) {
                Log.v("KiboRpcApi", "[takeSnapshot] Count: " + i);
                long startTime = System.currentTimeMillis();
                if (i == 1) {
                    Log.v("KiboRpcApi", "[takeSnapshot] Make start message");
                }
                Log.v("KiboRpcApi", "[takeSnapshot] Make target snapshot");
                if (!this.getterNode.getOnSimulation()) {
                    Log.v("KiboRpcApi", "[takeSnapshot] getOnSimulation: False");
                    Log.v("KiboRpcApi", "[takeSnapshot] Save bitmap");
                    saveBitmap(getBitmapNavCam());
                }
                for (long currentTime = System.currentTimeMillis(); currentTime - startTime <= 1000; currentTime = System.currentTimeMillis()) {
                    if (this.getterNode.getOnSimulation()) {
                        Log.v("KiboRpcApi", "[takeSnapshot] getOnSimulation: True");
                        Log.v("KiboRpcApi", "[takeSnapshot] Sleep inteval");
                        Thread.sleep(10);
                    } else {
                        Log.v("KiboRpcApi", "[takeSnapshot] getOnSimulation: False");
                        Log.v("KiboRpcApi", "[takeSnapshot] Sleep inteval");
                        Thread.sleep(10);
                    }
                }
            }
            Log.v("KiboRpcApi", "[takeSnapshot] Make target finish message");
            Log.v("KiboRpcApi", "[Finish] takeSnapshot");
            return true;
        } catch (SecurityException e) {
            sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to access directory.", e);
            return false;
        } catch (IOException e2) {
            sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to make image files.", e2);
            return false;
        } catch (NullPointerException e3) {
            sendExceptionMessage("[takeSnapshot] Internal error was occurred. Could not get Nav cam image.", e3);
            return false;
        } catch (Exception e4) {
            sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to send data to gds.", e4);
            return false;
        }
    }

    private void saveBitmap(Bitmap saveImage) throws SecurityException, IOException, NullPointerException {
        Log.v("KiboRpcApi", "[Start] saveBitmap");
        File file = new File(this.gsService.getGuestScienceDataBasePath() + TARGET_IMAGES_SAVE_DIR);
        if (!file.exists()) {
            Log.v("KiboRpcApi", "[saveBitmap] Make save directory");
            file.mkdir();
        }
        Date mDate = new Date();
        SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
        FileOutputStream out = new FileOutputStream(file.getAbsolutePath() + "/" + ("snapshot_" + fileNameDate.format(mDate) + ".png"));
        saveImage.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        out.close();
        Log.v("KiboRpcApi", "[Finish] saveBitmap");
    }

    public double[][] getNavCamIntrinsics() {
        Log.i("KiboRpcApi", "[Start] getNavCamIntrinsics");
        double[][] camera_param = new double[2][];
        if (this.getterNode.getOnSimulation()) {
            Log.v("KiboRpcApi", "[getNavCamIntrinsics] getOnSimulation: True");
            Log.v("KiboRpcApi", "[getNavCamIntrinsics] Set simulation param");
            camera_param[0] = this.NAVCAM_CAMERA_MATRIX_SIMULATION;
            camera_param[1] = this.NAVCAM_DISTORTION_COEFFICIENTS_SIMULATION;
        } else {
            Log.v("KiboRpcApi", "[getNavCamIntrinsics] Set ISS param");
            camera_param[0] = this.NAVCAM_CAMERA_MATRIX_ISS;
            camera_param[1] = this.NAVCAM_DISTORTION_COEFFICIENTS_ISS;
        }
        Log.i("KiboRpcApi", "[Finish] getNavCamIntrinsics");
        return camera_param;
    }

    public double[][] getDockCamIntrinsics() {
        Log.i("KiboRpcApi", "[Start] getDockCamIntrinsics");
        double[][] camera_param = new double[2][];
        if (this.getterNode.getOnSimulation()) {
            Log.v("KiboRpcApi", "[getDockCamIntrinsics] getOnSimulation: True");
            Log.v("KiboRpcApi", "[getDockCamIntrinsics] Set simulation param");
            camera_param[0] = this.DOCKCAM_CAMERA_MATRIX_SIMULATION;
            camera_param[1] = this.DOCKCAM_DISTORTION_COEFFICIENTS_SIMULATION;
        } else {
            Log.v("KiboRpcApi", "[getDockCamIntrinsics] Set ISS param");
            camera_param[0] = this.DOCKCAM_CAMERA_MATRIX_ISS;
            camera_param[1] = this.DOCKCAM_DISTORTION_COEFFICIENTS_ISS;
        }
        Log.i("KiboRpcApi", "[Finish] getDockCamIntrinsics");
        return camera_param;
    }

    public void saveBitmapImage(Bitmap image, String imageName) {
        try {
            Log.i("KiboRpcApi", "[Start] saveBitmapImage");
            boolean checkArgs = true;
            if (image == null) {
                Log.e("KiboRpcApi", "[saveBitmapImage] image is null");
                checkArgs = false;
            }
            if (imageName == null) {
                Log.e("KiboRpcApi", "[saveBitmapImage] imageName is null");
                checkArgs = false;
            }
            if (checkArgs) {
                Log.i("KiboRpcApi", "Parameters: image, imageName == " + imageName);
                if (this.getterNode.getOnSimulation()) {
                    Log.v("KiboRpcApi", "[saveBitmapImage] getOnSimulation: True");
                    Log.v("KiboRpcApi", "[saveBitmapImage] Check directry");
                    File file = new File(this.gsService.getGuestScienceDataBasePath() + DEBUG_IMAGES_SAVE_DIR);
                    if (!file.exists()) {
                        Log.v("KiboRpcApi", "[saveBitmapImage] Make save directry");
                        file.mkdir();
                    }
                    File[] list = file.listFiles();
                    int size = image.getWidth() * image.getHeight();
                    if (list.length >= 50) {
                        Log.e("KiboRpcApi", "[saveBitmapImage] Can't save more than 50 images");
                    } else if (size > 1228800) {
                        Log.e("KiboRpcApi", "[saveBitmapImage] The size is too large.");
                    } else {
                        Log.v("KiboRpcApi", "[saveBitmapImage] Save bitmap image");
                        saveImage(image, imageName, file);
                    }
                } else {
                    Log.v("KiboRpcApi", "[saveBitmapImage] getOnSimulation: False");
                }
            }
            Log.i("KiboRpcApi", "[Finish] saveBitmapImage");
        } catch (Exception e) {
            sendExceptionMessage("[saveBitmapImage] Internal error was occurred.", e);
        }
    }

    public void saveMatImage(Mat image, String imageName) {
        try {
            Log.i("KiboRpcApi", "[Start] saveMatImage");
            boolean checkArgs = true;
            if (image == null) {
                Log.e("KiboRpcApi", "[saveMatImage] image is null");
                checkArgs = false;
            }
            if (imageName == null) {
                Log.e("KiboRpcApi", "[saveMatImage] imageName is null");
                checkArgs = false;
            }
            if (checkArgs) {
                Log.i("KiboRpcApi", "Parameters: image, imageName == " + imageName);
                if (this.getterNode.getOnSimulation()) {
                    Log.v("KiboRpcApi", "[saveMatImage] getOnSimulation: True");
                    Log.v("KiboRpcApi", "[saveMatImage] Check directry");
                    File file = new File(this.gsService.getGuestScienceDataBasePath() + DEBUG_IMAGES_SAVE_DIR);
                    if (!file.exists()) {
                        Log.v("KiboRpcApi", "[saveMatImage] make save directory");
                        file.mkdir();
                    }
                    File[] list = file.listFiles();
                    int size = image.width() * image.height();
                    if (list.length >= 50) {
                        Log.e("KiboRpcApi", "[saveMatImage] Can't save more than 50 images.");
                    } else if (size > 1228800) {
                        Log.e("KiboRpcApi", "[saveMatImage] The size is too large.");
                    } else {
                        Log.v("KiboRpcApi", "[saveMatImage] Save mat image");
                        Bitmap bitmapImage = Bitmap.createBitmap(image.width(), image.height(), Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(image, bitmapImage);
                        saveImage(bitmapImage, imageName, file);
                    }
                } else {
                    Log.v("KiboRpcApi", "[saveMatImage] getOnSimulation: False");
                }
            }
            Log.i("KiboRpcApi", "[Finish] saveMatImage");
        } catch (Exception e) {
            sendExceptionMessage("[saveMatImage] Internal error was occurred.", e);
        }
    }

    private void saveImage(Bitmap image, String imageName, File file) throws SecurityException, IOException, NullPointerException {
        String AttachName = file.getAbsolutePath() + "/" + imageName;
        Log.v("KiboRpcApi", AttachName);
        FileOutputStream out = new FileOutputStream(AttachName);
        image.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        out.close();
    }

    public void setAreaInfo(int areaId, String itemName) {
        setAreaInfo(areaId, itemName, 1);
    }

    public void setAreaInfo(int areaId, String itemName, int number) {
        try {
            Log.i("KiboRpcApi", "[Start] setAreaInfo");
            if (itemName == null) {
                Log.e("KiboRpcApi", "[saveBitmapImage] itemName is null");
                itemName = "";
            }
            Log.i("KiboRpcApi", "Parameters: areaId == " + areaId + ", itemName == " + itemName + ", number == " + number);
            this.areaItemMap.setAreaInfo(areaId, itemName, number);
            JSONObject data = new JSONObject();
            data.put("area_id", areaId);
            data.put("lost_item", itemName);
            data.put("num", number);
            this.gsService.sendData(MessageType.JSON, ObjectArraySerializer.DATA_TAG, data.toString());
            Log.i("KiboRpcApi", "[Finish] setAreaInfo");
        } catch (Exception e) {
            sendExceptionMessage("[setAreaInfo] Internal error was occurred.", e);
        }
    }
}
