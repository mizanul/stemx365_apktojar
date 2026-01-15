
/* Copyright (c) 2017, United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 *
 * All rights reserved.
 *
 * The Astrobee platform is licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package  jp.jaxa.iss.kibo.rpc.api;

import java.net.URI;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import java.io.IOException;
import gov.nasa.arc.astrobee.AstrobeeException;
import gov.nasa.arc.astrobee.AstrobeeRuntimeException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaPlayer;
import android.content.res.AssetFileDescriptor;

import gov.nasa.arc.astrobee.Kinematics;
import org.opencv.core.Mat;
import gov.nasa.arc.astrobee.types.FlashlightLocation;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileOutputStream;

import gov.nasa.arc.astrobee.Kinematics;
import gov.nasa.arc.astrobee.PendingResult;
import gov.nasa.arc.astrobee.Result;
import gov.nasa.arc.astrobee.Robot;
import gov.nasa.arc.astrobee.RobotFactory;
import gov.nasa.arc.astrobee.ros.DefaultRobotFactory;
import gov.nasa.arc.astrobee.ros.RobotConfiguration;
import gov.nasa.arc.astrobee.types.PlannerType;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.PoweredComponent;
import gov.nasa.arc.astrobee.types.Quaternion;
import gov.nasa.arc.astrobee.ros.internal.util.MessageType;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import jp.jaxa.iss.kibo.rpc.api.types.ImuResult;
import jp.jaxa.iss.kibo.rpc.api.types.MonoImage;
import jp.jaxa.iss.kibo.rpc.api.types.PointCloud;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.opencv.android.Utils;
import android.graphics.Bitmap.Config;

/**
 * A simple API implementation class that provides an easier way to work with the Astrobee API
 */

public class KiboRpcApi {
	
	private static final Log logger = LogFactory.getLog(KiboRpcApi.class);

    // Constants that represent the axis in a 3D world
    public static final int X_AXIS = 2;
    public static final int Y_AXIS = 3;
    public static final int Z_AXIS = 4;

    // Center of US Lab
    public static final Point CENTER_US_LAB = new Point(2, 0, 4.8);

    // Default values for the Astrobee's arm
    public static final float ARM_TILT_DEPLOYED_VALUE = 0f;
    public static final float ARM_TILT_STOWED_VALUE = 180f;
    public static final float ARM_PAN_DEPLOYED_VALUE = 0f;
    public static final float ARM_PAN_STOWED_VALUE = 0f;

    // Constants needed to connect with ROS master
    private static final URI ROS_MASTER_URI = URI.create("http://localhost:11311");
    private static final String EMULATOR_ROS_HOSTNAME = "localhost";

    // Set the name of the app as the node name
    private static final String NODE_NAME = "stemx_rpc_api";

    // The instance to access this class
    private static KiboRpcApi instance = null;
    private KiboRpcService gsService = null;
    private final DateFormat df = new SimpleDateFormat("yyyyMMdd hhmmssSSS");

    // Configuration that will keep data to connect with ROS master
    private RobotConfiguration robotConfiguration = new RobotConfiguration();

    // Instance that will create a robot with the given configuration
    private RobotFactory factory;

    // The robot itself
    private Robot robot;

    // The planner to be used (QP, TRAPEZOIDAL)
    private PlannerType plannerType = null;
    private boolean hasTakenSnapshot = false;

    private boolean mPlaying = false;
    private boolean finishing = false;
    private boolean calledMoveTo;

    //private MediaPlayer mPlayer = new MediaPlayer();

   private final double[] NAVCAM_CAMERA_MATRIX_SIMULATION = new double[]{ 523.10575, 0.0, 635.434258, 0.0, 534.765913, 500.335102, 0.0, 0.0, 1.0 };
   private final double[] NAVCAM_CAMERA_MATRIX_ISS = new double[]{ 608.8073, 0.0, 632.53684, 0.0, 607.61439, 549.08386, 0.0, 0.0, 1.0 };
   private final double[] NAVCAM_DISTORTION_COEFFICIENTS_SIMULATION = new double[]{ -0.164787, 0.020375, -0.001572, -3.69E-4, 0.0 };
   private final double[] NAVCAM_DISTORTION_COEFFICIENTS_ISS = new double[]{ -0.164787, 0.020375, -0.001572, -3.69E-4, 0.0 };
   private final double[] DOCKCAM_CAMERA_MATRIX_SIMULATION = new double[] { 661.783002, 0.0, 595.212041, 0.0, 671.508662, 489.094196, 0.0, 0.0, 1.0 };
   private final double[] DOCKCAM_CAMERA_MATRIX_ISS = new double[]{ 753.51021, 0.0, 631.11512, 0.0, 751.3611, 508.69621, 0.0, 0.0, 1.0 };
   private final double[] DOCKCAM_DISTORTION_COEFFICIENTS_SIMULATION = new double[] { -0.215168, 0.044354, 0.003615, 0.005093, 0.0 };
   private final double[] DOCKCAM_DISTORTION_COEFFICIENTS_ISS = new double[] { -0.411405, 0.17724, -0.017145, 0.006421, 0.0 };

   private final List<Point> POINT_POSITIONS;

   private boolean hasTargetSnapshot1 = false;
   private boolean hasTargetSnapshot2 = false;

   private JSONObject jsonResult = new JSONObject();
   private JSONArray jsonRobot = new JSONArray();
   private JSONArray jsonMove = new JSONArray();
   private JSONArray jsonBox = new JSONArray();
   private String build_number = "";

   private int nearbyTargetId;
   private boolean laserActivationFlag;

   protected ExternalValueHandler exHandler = ExternalValueHandler.getInstance();

    private void sendExceptionMessage(String errmsg, Exception err) {
      try {
         JSONObject data = new JSONObject();
         data.put("error", "[" + err.getClass().getName() + "] " + errmsg);
         this.gsService.sendData(MessageType.JSON, "data", data.toString());
      } catch (JSONException var4) {
         this.gsService.sendData(MessageType.JSON, "data", var4.getClass().getName());
         logger.error("KiboRpcApi [sendExceptionMessage] Failed to send error message.", var4);
      }

   }

    /**
     * Private constructor that prevents other objects from creating instances of this class.
     * Instances of this class must be provided by a static function (Singleton).
     *
     * DO NOT call any Astrobee API function inside this method since the API might not be ready
     * to issue commands.
     */
    private KiboRpcApi(KiboRpcService startGuestScienceService) {
         System.out.print(">>>>>>> API STARTED >>>11>>>>>>>");
         int build_number=exHandler.getBuildNumber();
         System.out.print(">>>>>>> build_number >>>>>>>" + build_number);

         this.nearbyTargetId = 0;
         this.laserActivationFlag = false;

         this.POINT_POSITIONS = new ArrayList<Point>() {
            {
                this.add(new Point(9.815, -9.806, 4.293));
                this.add(new Point(11.2746, -9.92284, 5.2988));
                this.add(new Point(10.612, -9.0709, 4.48));
                this.add(new Point(10.71, -7.7, 4.48));
                this.add(new Point(10.51, -6.7185, 5.1804));
                this.add(new Point(11.114, -7.9756, 5.3393));
                this.add(new Point(11.355, -8.9929, 4.7818));
                this.add(new Point(11.369, -8.5518, 4.48));
                this.add(new Point(11.143, -6.7607, 4.9654));
            }
        };
         this.calledMoveTo = false;
        // Set up ROS configuration
        configureRobot();
         System.out.print(">>>>>>> API STARTED >>22>>>>>>>>");
        // Get the factory in order to access the robot.
        factory = new DefaultRobotFactory(robotConfiguration);
        this.gsService = startGuestScienceService;
      //   this.gsService.sendData(MessageType.JSON, "data", "some name");
         System.out.print(">>>>>>> API STARTED >>> 33 >>>>>>>");
        try {
            // Get the robot
            System.out.println(">>>>>>> API STARTED >> 44 >>>>>>>>");
            robot = factory.getRobot();
            // Bitmap ret = this.gsService.getBitmapNavCam();
            // System.out.println(">>>>>>> API STARTED >> 555 >>>>>>>>");
            // boolean ud = this.gsService.undocked();
            // System.out.println(">>>>>>> API STARTED >> 666 >>>>>>>>");

        } catch (AstrobeeException e) {
            logger.error("Error with Astrobee");
        } catch (InterruptedException e) {
            logger.error("Connection Interrupted");
        }
    }

     public void setBuildNumber(String num){
      System.out.println("=============== BUILD NUMBER==============" + num);
      build_number= num; 
   }

    /**
     * Static method that provides a unique instance of this class
     *
     * @return A unique instance of this class ready to use
     */
    public static KiboRpcApi getInstance(KiboRpcService startGuestScienceService) {
        if (instance == null) {
            instance = new KiboRpcApi(startGuestScienceService);
        }
        return instance;
    }

    /**
     * This method sets a default configuration for the robot
     */
    private void configureRobot() {
        // Populating robot configuration
        robotConfiguration.setMasterUri(ROS_MASTER_URI);
        robotConfiguration.setHostname(EMULATOR_ROS_HOSTNAME);
        robotConfiguration.setNodeName(NODE_NAME);
    }

    /**
     * This method shutdown the robot factory in order to allow java to close correctly.
     */
    public void shutdownFactory() {
        factory.shutdown();
    }

    /**
     * This method waits for a pending task and returns the result.
     *
     * @param pending Pending task being executed
     * @param printRobotPosition Should it print robot kinematics while waiting for task to be
     *                           completed?
     * @param timeout Number of seconds before stopping to wait for result (-1 for no timeout,
     *                0 will loop once).
     * @return Pending task result. NULL if an internal error occurred or request timeout.
     */
    public Result getCommandResult(PendingResult pending, boolean printRobotPosition, int timeout) {

        Result result = null;
        int counter = 0;

        try {
            Kinematics k;

            // Waiting until command is done.
            while (!pending.isFinished()) {
                if (timeout >= 0) {
                    // There is a timeout setting
                    if (counter > timeout)
                        return null;
                }

                if (printRobotPosition) {
                    // Meanwhile, let's get the positioning along the trajectory
                    k = robot.getCurrentKinematics();
                    System.out.print("Current Position: " + k.getPosition().toString());
                    System.out.print("Current Orientation" + k.getOrientation().toString());
                }

                // Wait 1 second before retrying
                pending.getResult(1000, TimeUnit.MILLISECONDS);
                counter++;
            }

            // Getting final result
            result = pending.getResult();

            // Print result in the log.
            printLogCommandResult(result);

        } catch (AstrobeeException e) {
            logger.error("Error with Astrobee");
        } catch (InterruptedException e) {
            logger.error("Connection Interrupted");
        } catch (TimeoutException e) {
            logger.error("Timeout connection");
        }
        
        // Return command execution result.
        return result;
    }

    /**
     * Get trusted data related to positioning and orientation for Astrobee
     *
     * @param timeout Number of seconds before canceling request
     * @return Trusted Kinematics, null if an internal error occurred or request timeout
     */
    public Kinematics getTrustedRobotKinematics(int timeout) {
        System.out.print("Waiting for robot to acquire position");

        // Variable that will keep all data related to positioning and movement.
        Kinematics k = null;

        int count = 0;

        // Waiting until we get a trusted kinematics
        while (count <= timeout || timeout == -1) {
            // Get kinematics
            k = robot.getCurrentKinematics();

            // Is it good?
            if (k.getConfidence() == Kinematics.Confidence.GOOD)
                // Don't wait anymore, move on.
                break;
            else
                k = null;

            // It's not good, wait a little bit and try again
            try {
                Thread.sleep(1000);
                count++;
            } catch (InterruptedException e) {
                logger.error("It was not possible to get a trusted kinematics. Sorry");
                return null;
            }
        }

        return k;
    }

    public Kinematics getTrustedRobotKinematics() {
        return getTrustedRobotKinematics(-1);
    }


    public Result flashlightControlFront(float brightness) {
      PendingResult pendingResult = null;

      try {
         System.out.println("setFlashlightBrightness >>>>>11>>>>>");
         System.out.println(FlashlightLocation.FRONT);
         System.out.println(brightness);
         pendingResult = this.robot.setFlashlightBrightness(FlashlightLocation.FRONT, brightness);
         System.out.println("setFlashlightBrightness >>>>22>>>>>>");
      } catch (AstrobeeRuntimeException var4) {
         this.sendExceptionMessage("[flashlightControlFront] Node not ready or dead.", var4);
         return null;
      }

      this.robot.setFlashlightBrightness(FlashlightLocation.FRONT, brightness);
      return this.getCommandResult(pendingResult, false, -1);
   }

   public Result flashlightControlBack(float brightness) {
      PendingResult pendingResult = null;

      try {
         pendingResult = this.robot.setFlashlightBrightness(FlashlightLocation.BACK, brightness);
      } catch (AstrobeeRuntimeException var4) {
         this.sendExceptionMessage("[flashlightControlBack] Node not ready or dead.", var4);
         return null;
      }

      return this.getCommandResult(pendingResult, false, -1);
   }



    /**
     * It moves Astrobee to the given point and rotate it to the given orientation.
     *
     * @param goalPoint   Absolute cardinal point (xyz)
     * @param orientation An instance of the Quaternion class.
     *                    You may want to use CENTER_US_LAB or CENTER_JEM as an example depending
     *                    on your initial position.
     * @return A Result instance carrying data related to the execution.
     * Returns null if the command was NOT execute as a result of an error
     */
    public Result moveTo3(Point goalPoint, Quaternion orientation, boolean printRobotPosition) {

        // Intent to set planner trapezoidal
        setPlanner(PlannerType.TRAPEZOIDAL);

        // Stop all motion
        Result result = stopAllMotion();
        if (result.hasSucceeded()) {
            // We stopped, do your stuff now
            System.out.print("Planner is " + plannerType.toString());

            System.out.print("Moving the bee");
            System.out.println("=============== MOVE TO RESULT SUCEEDED =============="); 
            System.out.println(jsonResult.toString()); 

            // Setting a simple movement command using the end point and the end orientation.
            PendingResult pending = robot.simpleMove6DOF(goalPoint, orientation);

            // Get the command execution result and send it back to the requester.
            result = getCommandResult(pending, printRobotPosition, -1);
            moveData(goalPoint, orientation);
        }
        
        return result;
    }

     public Result moveTo(final Point goalPoint, final Quaternion orientation, final boolean printRobotPosition) {
        System.out.print("KiboRpcApi [Start] moveTo");
        System.out.print("KiboRpcApi Parameters: goalPoint == " + goalPoint + ", orientation == " + orientation + ", printRobotPosition == " + printRobotPosition);
        setPlanner(PlannerType.TRAPEZOIDAL);
        if (goalPoint == null) {
            System.out.print("KiboRpcApi [moveTo] goalPoint == " + goalPoint);
             logger.error("KiboRpcApi [moveTo] goalPoint is invalid.");
            return null;
        }
        if (orientation == null) {
            System.out.print("KiboRpcApi [moveTo] orientation == " + orientation);
             logger.error("KiboRpcApi [moveTo] orientation is invalid.");
            return null;
        }
        System.out.print(">>>>>>>>>>>>>>>>> PlannerType.TRAPEZOIDAL >>>>>>>>>>>>");
      //   if (!this.setPlanner(PlannerType.TRAPEZOIDAL)) {
      //        logger.error("KiboRpcApi [moveTo] Cannot set planner.");
      //       return null;
      //   }
        System.out.print("KiboRpcApi [moveTo] Planner is set to " + this.plannerType.toString() + ".");
        System.out.print(">>>>>>>>>>>>>>>>> STOP ALL MOTION >>>>>>>>>>>>");
        Result result = this.stopAllMotion();
        if (result == null) {
            System.out.print("ERROR:: KiboRpcApi [moveTo] Cannot stop all motion.");
            return null;
        }
        if (result.hasSucceeded()) {
            System.out.print(">>>>>>>>>>>>>>>>> STOP ALL MOTION SUCCEEDED>>>>>>>>>>>>");
            System.out.print("KiboRpcApi [moveTo] Planner is " + this.plannerType.toString() + ".");
            System.out.print("KiboRpcApi [moveTo] Moving the bee.");
            // PendingResult pendingResult = null;
            // try {
            //     pendingResult = this.robot.simpleMove6DOF(goalPoint, orientation);
            // }
            // catch (AstrobeeRuntimeException e) {
            //     this.sendExceptionMessage("[moveTo] Node not ready or dead.", (Exception)e);
            //     return null;
            // }
            // result = this.getCommandResult(pendingResult, printRobotPosition, -1);
            // if (this.gsService.getOnSimulation()) {
            //      logger.debug("KiboRpcApi [moveTo] OnSimulation: " + this.gsService.getOnSimulation());
            //     if (this.isActiveTarget()) {
            //          logger.debug("KiboRpcApi [moveTo] Set LaserActivationFlag: true");
            //         this.laserActivationFlag = true;
            //     }
            // }
            
            // Setting a simple movement command using the end point and the end orientation.
            PendingResult pending = robot.simpleMove6DOF(goalPoint, orientation);

            // Get the command execution result and send it back to the requester.
            result = getCommandResult(pending, printRobotPosition, -1);
            this.calledMoveTo = true;
        }
        System.out.print("KiboRpcApi [Finish] moveTo");
        return result;
    }
    

      private boolean isActiveTarget() {
         logger.debug("KiboRpcApi [Start] isActiveTarget");
        boolean isActive = false;
        final Kinematics k = this.getTrustedRobotKinematics();
        if (k == null) {
            System.out.print("KiboRpcApi [isActiveTarget] k == " + k);
             logger.error("KiboRpcApi [isActiveTarget] Cannot get robot kinematics.");
            return false;
        }
        final Point currPosition = k.getPosition();
        if (currPosition == null) {
            System.out.print("KiboRpcApi [isActiveTarget] currPosition == " + currPosition);
             logger.error("KiboRpcApi [isActiveTarget] Cannot get current position.");
            return false;
        }
        double smallerDistance = Double.MAX_VALUE;
        final List<Integer> activeTargets = this.gsService.getActiveTargets();
        for (final int activeTargetId : activeTargets) {
            final Point checkPoint = new Point(currPosition.getX() - this.POINT_POSITIONS.get(activeTargetId).getX(), currPosition.getY() - this.POINT_POSITIONS.get(activeTargetId).getY(), currPosition.getZ() - this.POINT_POSITIONS.get(activeTargetId).getZ());
            final double distance = Math.sqrt(Math.pow(checkPoint.getX(), 2.0) + Math.pow(checkPoint.getY(), 2.0) + Math.pow(checkPoint.getZ(), 2.0));
            if (distance <= 0.30000001192092896 && distance <= smallerDistance) {
                 logger.debug("KiboRpcApi [isActiveTarget] Near active target [target_id: " + activeTargetId + "]");
                 logger.debug("KiboRpcApi [isActiveTarget] Set nearbyTargetId [target_id: " + activeTargetId + "]");
                smallerDistance = distance;
                this.nearbyTargetId = activeTargetId;
                isActive = true;
            }
        }
         logger.debug("KiboRpcApi [Finish] isActiveTarget");
        return isActive;
    }
    

   public List<Integer> getActiveTargets() {
        System.out.print("KiboRpcApi [Start] getActiveTargets");
        final List<Integer> list = this.gsService.getActiveTargets();
        System.out.print("KiboRpcApi [Finish] getActiveTargets");
        return list;
    }

    /**
     * It moves Astrobee to the given point using a relative reference
     * and rotates it to the given orientation.
     *
     * @param goalPoint   The relative end point (relative to Astrobee)
     * @param orientation The absolute orientation
     * @return
     */
    public Result relativeMoveTo(Point goalPoint, Quaternion orientation, boolean printRobotPosition) {

        // Ger current position
        Kinematics k = getTrustedRobotKinematics(5);
        if (k == null) {
            return null;
        }

        Point currPosition = k.getPosition();

        Point endPoint = new Point(
                currPosition.getX() + goalPoint.getX(),
                currPosition.getY() + goalPoint.getY(),
                currPosition.getZ() + goalPoint.getZ()
        );

        return moveTo(endPoint, orientation, printRobotPosition);
    }

    public Result relativeMoveInAxis(int axis, double nMeters, Quaternion orientation, boolean printRobotPosition) {
        Point endPoint = new Point(
                axis == X_AXIS ? nMeters : 0,
                axis == Y_AXIS ? nMeters : 0,
                axis == Z_AXIS ? nMeters : 0
        );

        return relativeMoveTo(endPoint, orientation,printRobotPosition);
    }

    public Result stopAllMotion() {
        PendingResult pendingResult = robot.stopAllMotion();
        return getCommandResult(pendingResult, false, -1);
    }

    public Result dock() {
        PendingResult pendingResult = robot.dock(1);
        return getCommandResult(pendingResult, true, -1);
    }

    public Result undock() {
        PendingResult pendingResult = robot.undock();
        return getCommandResult(pendingResult, true, -1);
    }

    /**
     * An optional method used to print command execution results on the Android log
     * @param result
     */
    private void printLogCommandResult(Result result) {
        System.out.print("Command status: " + result.getStatus().toString());

        // In case command fails
        if (!result.hasSucceeded()) {
            logger.error("Command message: " + result.getMessage());
        }

        System.out.print("Done");
    }

    /**
     * Method to get the robot from this API Implementation.
     * @return
     */
    public Robot getRobot() {
        return robot;
    }

    public boolean setPlanner(PlannerType plannerType) {
        PendingResult pendingPlanner = robot.setPlanner(plannerType);
        Result result = getCommandResult(pendingPlanner, false, 5);
        if (result.hasSucceeded()) {
            System.out.print(">>>>>>>>>>>>>>>>> SET PLANNER SUCCEEDED >>>>>>>>>>>>");
            this.plannerType = plannerType;
            System.out.print("Planner set to " + plannerType);
        }

        return result.hasSucceeded();
    }

 public boolean startMission() {
      System.out.print(">>>>>>>>>>>>>>>>> KRPC 4 MISSION START >>>>>>>>>>>>");
        System.out.print("KiboRpcApi [Start] startMission");
        boolean isSuccess = false;
        try {
            JSONObject data = new JSONObject();
            String time = this.df.format(new Date(System.currentTimeMillis()));
            data.put("t_stamp_undock", (Object)time);
            data.put("status", (Object)"Undock Start");
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
            System.out.print("KiboRpcApi Undock Start: " + time);
            final PendingResult pendingResult = this.robot.undock();
            int timeout = 100;
            if (!this.gsService.getOnSimulation()) {
                logger.debug("KiboRpcApi set timeout -1");
                timeout = -1;
            }
            final Result result = this.getCommandResult(pendingResult, false, timeout);
            data = new JSONObject();
            if (result != null && result.hasSucceeded()) {
                data.put("status", (Object)"Undock Finish");
                isSuccess = true;
                time = this.df.format(new Date(System.currentTimeMillis()));
                System.out.print("KiboRpcApi Undock Finish: " + time);
            }
            else {
                data.put("failed", (Object)"Undock failed");
                time = this.df.format(new Date(System.currentTimeMillis()));
                 logger.error("KiboRpcApi Undock failed: " + time);
            }
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
            if (isSuccess) {
                time = this.df.format(new Date(System.currentTimeMillis()));
                data = new JSONObject();
                data.put("t_stamp_start", (Object)time);
                data.put("status", (Object)"Mission Start");
                this.gsService.sendData(MessageType.JSON, "data", data.toString());
                System.out.print("KiboRpcApi Mission Start: " + time);
            }
            if (!this.calledMoveTo) {
                this.gsService.startMission();
            }
            else {
                 logger.debug("KiboRpcApi [startMission] astrobee is moved. Unable to retry the start mission");
            }
        }
        catch (JSONException e) {
            this.sendExceptionMessage("[startMission] Internal error occurred. Unable to serialize data to JSON.", (Exception)e);
            return false;
        }
        catch (Exception e2) {
            this.sendExceptionMessage("[startMission] Internal error occurred. Unable to send data to gds.", e2);
            return false;
        }
        System.out.print("KiboRpcApi [Finish] startMission");
        return isSuccess;
    }
    
    public boolean startMissionKrpc3() {
      try {

         System.out.print(">>>>>>>>>>>>>>>>> MISSION START >>>>>>>>>>>>");
         Point goalPoint = new Point(4.5, 3.5, 3.5);
         Quaternion orientation = new Quaternion( 0, 0, 0, 0);
         cameraData(goalPoint, orientation);

         Point goalPoint2 = new Point(4.5, 3.5, 3.5);
         Quaternion orientation2 = new Quaternion( 0, 0, 0, 0);
         robotData(goalPoint2, orientation2);

         Point goalPoint3 = new Point(4.5, 3.5, 3.5);
         Quaternion orientation3 = new Quaternion( 0, 0, 0, 0);
         boxData(goalPoint3, orientation3);

         JSONObject data = new JSONObject();
         data.put("t_stamp", this.df.format(new Date(System.currentTimeMillis())));
         data.put("status", "Undocking...");
         this.gsService.sendData(MessageType.JSON, "data", data.toString());

         this.robot.undock();

         for(int i = 1; i <= 20; ++i) {
            if (this.gsService.undocked()) {
               data.put("status", "Undock succeed");
               this.gsService.sendData(MessageType.JSON, "data", data.toString());
               break;
            }

            Thread.sleep(5000L);
            if (i >= 20) {
               data.put("status", "Undock error occurred");
               this.gsService.sendData(MessageType.JSON, "data", data.toString());
               throw new Exception("Undocking Timeout");
            }
         }

         data.put("t_stamp_start", this.df.format(new Date(System.currentTimeMillis())));
         data.put("status", "Mission Start");
         this.gsService.sendData(MessageType.JSON, "data", data.toString());
         return true;
      } catch (JSONException var3) {
         logger.error("[startMission] Internal error was occurred. Unable to serialize data to JSON.", var3);
      } catch (Exception var4) {
         logger.error("[startMission] Internal error was occurred. Unable to send data to gds.", var4);
      }

      return false;
   }

    public Result laserControl(boolean state) {
      PendingResult pendingResult = null;
      try {
         if (state) {
            pendingResult = this.robot.powerOnItem(PoweredComponent.LASER_POINTER);
            System.out.print(">>>>>>>>>>>>>>>>> laserControl 13333 >>>>>>>>>>>>");
            try {
               Thread.sleep(2000L);
            } catch (InterruptedException var4) {
               logger.error("KiboRpcApi [getTrustedRobotkinematics] It was not possible to get a trusted kinematics. Sorry.");
               return null;
            }
         } else {
            pendingResult = this.robot.powerOffItem(PoweredComponent.LASER_POINTER);
            System.out.print(">>>>>>>>>>>>>>>>> laserControl 2 >>>>>>>>>>>>");
         }
      } catch (AstrobeeRuntimeException var5) {
         this.sendExceptionMessage("[laserControl] Node not ready or dead.", var5);
      }

      return this.getCommandResult(pendingResult, false, -1);
   }
   
    public Bitmap getBitmapNavCam() {
      Bitmap ret = this.gsService.getBitmapNavCam();
      if (ret == null) {
         logger.error("KiboRpcApi [getBitmapNavCam] It was not possible to get a Bitmap from Nav Cam.");
      }

      return ret;
   }

    public Mat getMatNavCam() {
      Mat ret = this.gsService.getMatNavCam();
      if (ret == null) {
         logger.error("KiboRpcApi [getMatNavCam] It was not possible to get a Mat from Nav Cam.");
      }

      return ret;
   }

   public Mat getMatDockCam() {
      Mat ret = this.gsService.getMatDockCam();
      if (ret == null) {
         logger.error("KiboRpcApi [getMatDockCam] It was not possible to get a Mat from Dock Cam.");
      }

      return ret;
   }


   public Kinematics getRobotKinematics() {
      System.out.print("KiboRpcApi [getRobotKinematics] Waiting for robot to acquire position.");
      return this.gsService.getCurrentKinematics();
   }

   public ImuResult getImu() {
      ImuResult ret = this.gsService.getImu();
      if (ret == null) {
         logger.error("KiboRpcApi [getImu] It was not possible to get a Imu result data.");
      }

      return ret;
   }

    private void saveBitmap(Bitmap saveImage, String no) throws SecurityException, IOException, NullPointerException {
      String filepath = this.gsService.getGuestScienceDataBasePath() + "/immediate/JudgeImages";
      File file = new File(filepath);
      if (!file.exists()) {
         file.mkdir();
      }

      Date mDate = new Date();
      SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
      String fileName = "Target_image_" + fileNameDate.format(mDate) + "_" + no + ".png";
      String AttachName = file.getAbsolutePath() + "/" + fileName;
      FileOutputStream out = new FileOutputStream(AttachName);
      saveImage.compress(CompressFormat.PNG, 100, out);
      out.flush();
      out.close();
   }

    public void sendDiscoveredQR(int num, String value) {
      try {
         JSONObject data = new JSONObject();
         data.put("t_stamp_qr", this.df.format(new Date(System.currentTimeMillis())));
         data.put("status", "Discovered QR");
         data.put("qr_result", value);
         this.gsService.sendData(MessageType.JSON, "data", data.toString());
      } catch (JSONException var3) {
         this.sendExceptionMessage("[sendDiscoveredQR] Internal error was occurred. Unable to serialize data to JSON.", var3);
      } catch (Exception var4) {
         this.sendExceptionMessage("[sendDiscoveredQR] Internal error was occurred. Unable to send data to gds.", var4);
      }

   }

   public void sendDiscoveredQR(String value) {
      try {
         JSONObject data = new JSONObject();
         data.put("t_stamp_qr", this.df.format(new Date(System.currentTimeMillis())));
         data.put("status", "Discovered QR");
         data.put("qr_result", value);
         this.gsService.sendData(MessageType.JSON, "data", data.toString());
      } catch (JSONException var3) {
         this.sendExceptionMessage("[sendDiscoveredQR] Internal error was occurred. Unable to serialize data to JSON.", var3);
      } catch (Exception var4) {
         this.sendExceptionMessage("[sendDiscoveredQR] Internal error was occurred. Unable to send data to gds.", var4);
      }

   }

      public void sendDiscoveredAR(String value) {
      try {
         JSONObject data = new JSONObject();
         data.put("t_stamp_qr", this.df.format(new Date(System.currentTimeMillis())));
         data.put("status", "Discovered QR");
         data.put("qr_result", value);
         this.gsService.sendData(MessageType.JSON, "data", data.toString());
      } catch (JSONException var3) {
         this.sendExceptionMessage("[sendDiscoveredQR] Internal error was occurred. Unable to serialize data to JSON.", var3);
      } catch (Exception var4) {
         this.sendExceptionMessage("[sendDiscoveredQR] Internal error was occurred. Unable to send data to gds.", var4);
      }

   }

public boolean takeSnapshot(int targetNumber, boolean hasTakenSnapshot, int approach_times) {
      try {
         System.out.print("KiboRpcApi [Start] takeSnapshot");
         if (hasTakenSnapshot) {
            System.out.print("KiboRpcApi [takeSnapshot] Make error message");
            String err_message = "takeSnapshot can execute once.";
            JSONObject data = new JSONObject();
            data.put("error", err_message);
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
            System.out.print("KiboRpcApi [Finish] takeSnapshot");
            return true;
         } else {
            for(int i = 1; i <= approach_times; ++i) {
               System.out.print("KiboRpcApi [takeSnapshot] Count: " + i);
               long startTime = System.currentTimeMillis();
               JSONObject data = new JSONObject();
               if (i == 1) {
                  System.out.print("KiboRpcApi [takeSnapshot] Make start message");
                  JSONObject data2 = new JSONObject();
                  if (targetNumber == 1) {
                     System.out.print("KiboRpcApi [takeSnapshot] Make target1 timestamp");
                     data2.put("t_stamp_snapshot1", this.df.format(new Date(System.currentTimeMillis())));
                     data2.put("status", "Taking snapshots of Target1 start");
                  } else {
                     System.out.print("KiboRpcApi [takeSnapshot] Make target2 timestamp");
                     data2.put("t_stamp_snapshot2", this.df.format(new Date(System.currentTimeMillis())));
                     data2.put("status", "Taking snapshots of Target2 start");
                  }

                  this.gsService.sendData(MessageType.JSON, "data", data2.toString());
               }

               if (!this.gsService.getOnSimulation()) {
                  System.out.print("KiboRpcApi [takeSnapshot] getOnSimulation: False");
                  System.out.print("KiboRpcApi [takeSnapshot] Save bitmap");
                  Bitmap image = this.getBitmapNavCam();
                  this.saveBitmap(image, String.valueOf(i));
               }

               if (targetNumber == 1) {
                  System.out.print("KiboRpcApi [takeSnapshot] Make target1 snapshot");
                  data.put("status", "Taking a snapshot of Target1");
               } else {
                  System.out.print("KiboRpcApi [takeSnapshot] Make target2 snapshot");
                  data.put("status", "Taking a snapshot of Target2");
               }

               this.gsService.sendData(MessageType.JSON, "data", data.toString());

               for(long currentTime = System.currentTimeMillis(); currentTime - startTime <= 1000L; currentTime = System.currentTimeMillis()) {
                  if (this.gsService.getOnSimulation()) {
                     System.out.print("KiboRpcApi [takeSnapshot] getOnSimulation: True");
                     System.out.print("KiboRpcApi [takeSnapshot] Sleep inteval");
                     Thread.sleep(10L);
                  } else {
                     System.out.print("KiboRpcApi [takeSnapshot] getOnSimulation: False");
                     System.out.print("KiboRpcApi [takeSnapshot] Sleep inteval");
                     Thread.sleep(10L);
                  }
               }
            }

            JSONObject data = new JSONObject();
            if (targetNumber == 1) {
               System.out.print("KiboRpcApi [takeSnapshot] Make target1 finish message");
               data.put("status", "Taking snapshots of Target1 finish");
            } else {
               System.out.print("KiboRpcApi [takeSnapshot] Make target2 finish message");
               data.put("status", "Taking snapshots of Target2 finish");
            }

            this.gsService.sendData(MessageType.JSON, "data", data.toString());
            this.robot.powerOffItem(PoweredComponent.LASER_POINTER);
            System.out.print("KiboRpcApi [Finish] takeSnapshot");
            return true;
         }
      } catch (JSONException var10) {
         this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to serialize data to JSON.", var10);
         return false;
      } catch (SecurityException var11) {
         this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to access directory.", var11);
         return false;
      } catch (IOException var12) {
         this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to make image files.", var12);
         return false;
      } catch (NullPointerException var13) {
         this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Could not get Nav cam image.", var13);
         return false;
      } catch (Exception var14) {
         this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to send data to gds.", var14);
         return false;
      }
   }


   public void saveMatImage(Mat image, String imageName) {
      try {
         System.out.print("KiboRpcApi [Start] saveMatImage");
         boolean checkArgs = true;
         if (image == null) {
            logger.error("KiboRpcApi [saveMatImage] image is null");
            checkArgs = false;
         }

         if (imageName == null) {
            logger.error("KiboRpcApi [saveMatImage] imageName is null");
            checkArgs = false;
         }

         if (checkArgs) {
            System.out.print("KiboRpcApi Parameters: image, imageName == " + imageName);
            if (this.gsService.getOnSimulation()) {
               String filepath = this.gsService.getGuestScienceDataBasePath() + "/immediate/DebugImages";
               File file = new File(filepath);
               if (!file.exists()) {
                  file.mkdir();
               }

               File[] list = file.listFiles();
               int width = image.width();
               int height = image.height();
               int size = width * height;
               if (list.length >= 50) {
                  logger.error("KiboRpcApi [saveMatImage] Can't save more than 50 images.");
               } else if (size > 1228800) {
                 logger.error("KiboRpcApi [saveMatImage] The size is too large.");
               } else {
                  System.out.print("KiboRpcApi [saveMatImage] Save mat image");
                  Bitmap bitmapImage = Bitmap.createBitmap(image.width(), image.height(), Config.ARGB_8888);
                  Utils.matToBitmap(image, bitmapImage);
                  this.saveImage(bitmapImage, imageName, file);
               }
            } else {
                System.out.print("KiboRpcApi [saveMatImage] getOnSimulation: False");
            }
         }

         System.out.print("KiboRpcApi [Finish] saveMatImage");
      } catch (Exception var11) {
         this.sendExceptionMessage("[saveMatImage] Internal error was occurred.", var11);
      }

   }

       public List<Long> getTimeRemaining() {
        System.out.print("KiboRpcApi [Start] getTimeRemaining");
        final List<Long> list = this.gsService.getTimeRemaining();
        System.out.print("KiboRpcApi [Finish] getTimeRemaining");
        return list;
    }
    

   private void saveImage(Bitmap image, String imageName, File file) throws SecurityException, IOException, NullPointerException {
      String AttachName = file.getAbsolutePath() + "/" + imageName;
      FileOutputStream out = new FileOutputStream(AttachName);
      image.compress(CompressFormat.PNG, 100, out);
      out.flush();
      out.close();
   }

    private void setSignalStateIncorrectPattern() {
         logger.debug("KiboRpcApi [Start] setSignalStateIncorrectPattern");
        //this.gsService.setSignalState((byte)7, 2);
         logger.debug("KiboRpcApi [Finish] setSignalStateIncorrectPattern");
    }

   public void notifyGoingToGoal() {
        System.out.print("KiboRpcApi [Start] notifyGoingToGoal");
        try {
             logger.debug("KiboRpcApi [notifyGoingToGoal] Start moving to the goal");
            final JSONObject data = new JSONObject();
            data.put("status", (Object)"Start moving to the goal");
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
            if (!this.gsService.getOnSimulation()) {
                this.setSignalStateIncorrectPattern();
                this.gsService.setSignalState((byte)17, 1);
            }
        }
        catch (JSONException e) {
            this.sendExceptionMessage("[notifyGoingToGoal] Internal error was occurred. Unable to serialize data to JSON.", (Exception)e);
            return;
        }
        catch (Exception e2) {
            this.sendExceptionMessage("[notifyGoingToGoal] Internal error was occurred. Unable to send data to gds.", e2);
            return;
        }
        System.out.print("KiboRpcApi [Finish] notifyGoingToGoal");
    }

   public boolean reportMissionCompletion(final String report) {
       JSONObject data= new JSONObject();

      String time = this.df.format(new Date(System.currentTimeMillis()));
      time = this.df.format(new Date(System.currentTimeMillis()));
      try {
         data.put("t_stamp_start", time);
         data.put("status", "Mission Complete");
      } catch (JSONException e) {
         //some exception handler code.
      }  
      jsonResult.put("reportMissionCompletion",data);
      // String path = "/home/simulator/processed/"+build_number+".json";
      // System.out.println("=============== MISSON COMPLETE=============="); 
      // jsonResult.put("tr",jsonMove);
      // jsonResult.put("robot", jsonRobot);
      // System.out.println(jsonResult.toString());
      // try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
      //          out.write(jsonResult.toString());
      // } catch (Exception e) {
      //    e.printStackTrace();
      // }
      return true;


      // try {
      //    if (!this.mPlaying) {
      //       this.mPlaying = true;
      //       Kinematics kinematics = this.getRobotKinematics();
      //       String positionX = String.format("%.3f", kinematics.getPosition().getX());
      //       String positionY = String.format("%.3f", kinematics.getPosition().getY());
      //       String positionZ = String.format("%.3f", kinematics.getPosition().getZ());
      //       String orientationX = String.format("%.3f", kinematics.getOrientation().getX());
      //       String orientationY = String.format("%.3f", kinematics.getOrientation().getY());
      //       String orientationZ = String.format("%.3f", kinematics.getOrientation().getZ());
      //       String orientationW = String.format("%.3f", kinematics.getOrientation().getW());
      //       JSONObject data = new JSONObject();
      //       data.put("m_play_pos", "[ posX : " + positionX + ", posY : " + positionY + ", posZ " + positionZ + ", quaX: " + orientationX + ", quaY: " + orientationY + ", quaZ: " + orientationZ + ", quaW: " + orientationW + " ]");
      //       this.gsService.sendData(MessageType.JSON, "data", data.toString());

      //       for(int i = 0; i < 2; ++i) {
      //          this.flashlightControlFront(1.0F);
      //          this.flashlightControlBack(1.0F);
      //          this.flashlightControlFront(0.0F);
      //          this.flashlightControlBack(0.0F);
      //       }

      //       Thread.sleep(1000L);
      //       AssetFileDescriptor fd = this.gsService.getBaseContext().getResources().openRawResourceFd(raw.astrobee);
      //       this.mPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
      //       this.mPlayer.prepare();
      //       this.mPlayer.setVolume(1.0F, 1.0F);
      //       JSONObject data2 = new JSONObject();
      //       data2.put("t_stamp_sound", this.df.format(new Date(System.currentTimeMillis())));
      //       data2.put("status", "playback started");
      //       this.gsService.sendData(MessageType.JSON, "data", data2.toString());
      //       this.mPlayer.start();
      //       this.mPlayer.setOnCompletionListener(new OnCompletionListener() {
      //          public void onCompletion(MediaPlayer mp) {
      //             this.mPlayer.stop();
      //             this.mPlayer.reset();
      //             this.mPlayer.release();
      //             this.mPlayer = null;

      //             try {
      //                JSONObject data = new JSONObject();
      //                data.put("status", "playback completed");
      //                //this.gsService.sendData(MessageType.JSON, "data", data.toString());
      //             } catch (Exception var3) {
      //                this.sendExceptionMessage("[setOnCompletionListener] Internal error was occurred. Unable to send data to gds.", var3);
      //             }

      //          }
      //       });
      //    }
      // } catch (Exception var14) {
      //    this.sendExceptionMessage("[reportMissionCompletion] Internal error was occurred. Unable to play sound.", var14);
      //    this.mPlaying = false;
      //    return false;
      // }

      // try {
      //    JSONObject data;
      //    if (!this.finishing) {
      //       this.finishing = true;
      //       data = new JSONObject();
      //       data.put("t_stamp_finish", this.df.format(new Date(System.currentTimeMillis())));
      //       data.put("status", "Mission Finish");
      //       this.gsService.sendData(MessageType.JSON, "data", data.toString());
      //    } else {
      //       data = new JSONObject();
      //       data.put("status", "Already Finished");
      //       this.gsService.sendData(MessageType.JSON, "data", data.toString());
      //    }

      //    return true;
      // } catch (JSONException var12) {
      //    this.sendExceptionMessage("[reportMissionCompletion] Internal error was occurred. Unable to serialize data to JSON.", var12);
      //    return false;
      // } catch (Exception var13) {
      //    this.sendExceptionMessage("[reportMissionCompletion] Internal error was occurred. Unable to send data to gds.", var13);
      //    return false;
      // }


   }


    private void saveBitmap(final Bitmap saveImage, final String phaseNumber, final String targetId) throws SecurityException, IOException, NullPointerException {
         logger.debug("KiboRpcApi [Start] saveBitmap");
        final String filepath = this.gsService.getGuestScienceDataBasePath() + "/immediate/JudgeImages";
        final File file = new File(filepath);
        if (!file.exists()) {
             logger.debug("KiboRpcApi [saveBitmap] Make save directory");
            file.mkdir();
        }
        final Date mDate = new Date();
        final SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
        final String fileName = "Phase_" + phaseNumber + "_Target_" + targetId + "_" + fileNameDate.format(mDate) + ".png";
        final String AttachName = file.getAbsolutePath() + "/" + fileName;
        final FileOutputStream out = new FileOutputStream(AttachName);
        saveImage.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)out);
        out.flush();
        out.close();
         logger.debug("KiboRpcApi [Finish] saveBitmap");
    }



public void reportPoint1Arrival() {
      try {
         System.out.print("KiboRpcApi [Start] reportPoint1Arrival");
         JSONObject data;
         if (this.finishing) {
            System.out.print("KiboRpcApi [reportPoint1Arrival] Already finished");
            data = new JSONObject();
            data.put("status", "Already Finished");
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
         } else {
            System.out.print("KiboRpcApi [reportPoint1Arrival] Do getRobotKinematics");
            data = new JSONObject();
            String time = this.df.format(new Date(System.currentTimeMillis()));
            time = this.df.format(new Date(System.currentTimeMillis()));
            data.put("t_stamp_start", time);
            data.put("status", "Arrived at Point-1");
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
            Kinematics kinematics = this.getRobotKinematics();
            if (kinematics != null && kinematics.getPosition() != null && kinematics.getOrientation() != null) {
               String positionX = String.format("%.3f", kinematics.getPosition().getX());
               String positionY = String.format("%.3f", kinematics.getPosition().getY());
               String positionZ = String.format("%.3f", kinematics.getPosition().getZ());
               String orientationX = String.format("%.3f", kinematics.getOrientation().getX());
               String orientationY = String.format("%.3f", kinematics.getOrientation().getY());
               String orientationZ = String.format("%.3f", kinematics.getOrientation().getZ());
               String orientationW = String.format("%.3f", kinematics.getOrientation().getW());
               JSONObject data2 = new JSONObject();
               data2.put("point1_pos", "[ posX : " + positionX + ", posY : " + positionY + ", posZ " + positionZ + ", quaX: " + orientationX + ", quaY: " + orientationY + ", quaZ: " + orientationZ + ", quaW: " + orientationW + " ]");
               this.gsService.sendData(MessageType.JSON, "data", data2.toString());
               this.finishing = true;
            } else {
               logger.error("KiboRpcApi [reportPoint1Arrival] It was not possible to get a kinematics.");
               if (kinematics == null) {
                  logger.error("KiboRpcApi [reportPoint1Arrival] kinematics is null.");
               }

               if (kinematics.getPosition() == null) {
                  logger.error("KiboRpcApi [reportPoint1Arrival] kinematics.getPosition() is null.");
               }

               if (kinematics.getOrientation() == null) {
                  logger.error("KiboRpcApi [reportPoint1Arrival] kinematics.getOrientation() is null.");
               }
            }
         }
      } catch (JSONException var12) {
         this.sendExceptionMessage("[reportPoint1Arrival] Internal error was occurred. Unable to serialize data to JSON.", var12);
      } catch (Exception var13) {
         this.sendExceptionMessage("[reportPoint1Arrival] Internal error was occurred. Unable to send data to gds.", var13);
         logger.error("KiboRpcApi [Finish] reportPoint1Arrival");
      }

   }



 
    public void takeTargetSnapshot(final int targetId) {
        System.out.print("KiboRpcApi [Start] takeTargetSnapshot");
        System.out.print("KiboRpcApi Parameters: targetId == " + targetId);
        try {
            final List<Integer> activeTargets = this.gsService.getActiveTargets();
            final int phaseNumber = this.gsService.getCurrentPhaseNumber();
            boolean result = true;
            if ((this.gsService.getOnSimulation() && activeTargets.contains(targetId) && this.nearbyTargetId == targetId) || (!this.gsService.getOnSimulation() && activeTargets.contains(targetId))) {
                int approach_time = 1;
                if (!this.gsService.getOnSimulation()) {
                     logger.debug("KiboRpcApi [takeTargetSnapshot] getOnSimulation: False");
                     logger.debug("KiboRpcApi [takeTargetSnapshot] Set approach_time");
                    approach_time = 2;
                }
                result = this.takeSnapshot(phaseNumber, targetId, approach_time);
            }
            else if (!activeTargets.contains(targetId)) {
                final JSONObject data = new JSONObject();
                 logger.error("KiboRpcApi [takeTargetSnapshot] Fail to deactivate the target.");
                data.put("snapshot_inactive", (Object)String.format("Target is inactive [ target_id: %d ]", targetId));
                data.put("target_info", (Object)this.getTargetInfoObject(phaseNumber, targetId));
                this.gsService.sendData(MessageType.JSON, "data", data.toString());
            }
            if (this.gsService.getOnSimulation() && this.nearbyTargetId != targetId) {
                 logger.error("[takeTargetSnapshot] target is unmatch. Expected target is %d.");
            }
            if (result) {
                this.laserControl(false);
                this.nearbyTargetId = 0;
                this.laserActivationFlag = false;
            }
        }
        catch (JSONException e) {
            this.sendExceptionMessage("[takeTargetSnapshot] Internal error was occurred. Unable to serialize data to JSON.", (Exception)e);
            return;
        }
        catch (Exception e2) {
            this.sendExceptionMessage("[takeTargetSnapshot] Internal error was occurred. Unable to send data to gds.", e2);
            return;
        }
        System.out.print("KiboRpcApi [Finish] takeTargetSnapshot");
    }
    


private boolean takeSnapshot(final int phaseNumber, final int targetId, final int approach_times) {
        try {
             logger.debug("KiboRpcApi [Start] takeSnapshot");
            JSONObject data = null;
            for (int i = 1; i <= approach_times; ++i) {
                 logger.debug("KiboRpcApi [takeSnapshot] Count: " + i);
                final long startTime = System.currentTimeMillis();
                if (i == 1) {
                    data = new JSONObject();
                     logger.debug("KiboRpcApi [takeSnapshot] Make start message");
                    data.put("t_stamp_snapshot", (Object)this.df.format(new Date(System.currentTimeMillis())));
                    data.put("status", (Object)"Taking snapshots of Target start");
                    data.put("target_info", (Object)this.getTargetInfoObject(phaseNumber, targetId));
                    this.gsService.sendData(MessageType.JSON, "data", data.toString());
                    if (!this.gsService.targetDeactivation(targetId)) {
                         logger.error("KiboRpcApi [takeSnapshot] Fail to deactivate the target.");
                        return false;
                    }
                }
                data = new JSONObject();
                 logger.debug("KiboRpcApi [takeSnapshot] Make target snapshot");
                data.put("status", (Object)"Taking a snapshot of Target");
                data.put("target_info", (Object)this.getTargetInfoObject(phaseNumber, targetId));
                this.gsService.sendData(MessageType.JSON, "data", data.toString());
                if (!this.gsService.getOnSimulation()) {
                     logger.debug("KiboRpcApi [takeSnapshot] getOnSimulation: False");
                     logger.debug("KiboRpcApi [takeSnapshot] Save bitmap");
                    final Bitmap image = this.getBitmapNavCam();
                    this.saveBitmap(image, String.valueOf(phaseNumber), String.valueOf(targetId));
                }
                for (long currentTime = System.currentTimeMillis(); currentTime - startTime <= 1000L; currentTime = System.currentTimeMillis()) {
                    if (this.gsService.getOnSimulation()) {
                         logger.debug("KiboRpcApi [takeSnapshot] getOnSimulation: True");
                         logger.debug("KiboRpcApi [takeSnapshot] Sleep inteval");
                        Thread.sleep(10L);
                    }
                    else {
                         logger.debug("KiboRpcApi [takeSnapshot] getOnSimulation: False");
                         logger.debug("KiboRpcApi [takeSnapshot] Sleep inteval");
                        Thread.sleep(10L);
                    }
                }
            }
            data = new JSONObject();
             logger.debug("KiboRpcApi [takeSnapshot] Make target finish message");
            data.put("status", (Object)"Taking snapshots of Target finish");
            data.put("target_info", (Object)this.getTargetInfoObject(phaseNumber, targetId));
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
             logger.debug("KiboRpcApi [Finish] takeSnapshot");
            return true;
        }
        catch (JSONException e) {
            this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to serialize data to JSON.", (Exception)e);
            return false;
        }
        catch (SecurityException e2) {
            this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to access directory.", e2);
            return false;
        }
        catch (IOException e3) {
            this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to make image files.", e3);
            return false;
        }
        catch (NullPointerException e4) {
            this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Could not get Nav cam image.", e4);
            return false;
        }
        catch (Exception e5) {
            this.sendExceptionMessage("[takeSnapshot] Internal error was occurred. Unable to send data to gds.", e5);
            return false;
        }
    }
    
   private JSONObject getTargetInfoObject(final int phaseNumber, final int targetId) throws JSONException {
        final JSONObject data = new JSONObject();
        data.put("target_id", targetId);
        data.put("phase_number", phaseNumber);
        return data;
    }


public void takeTarget1Snapshot() {
      System.out.print("KiboRpcApi [Start] takeTarget1Snapshot");
      int approach_time = 1;
      if (!this.gsService.getOnSimulation()) {
         System.out.print("KiboRpcApi [takeTarget1Snapshot] getOnSimulation: False");
         System.out.print("KiboRpcApi [takeTarget1Snapshot] Set approach_time");
         approach_time = 2;
      }

      boolean result = this.takeSnapshot(1, this.hasTargetSnapshot1, approach_time);
      this.hasTargetSnapshot1 |= result;
      System.out.print("KiboRpcApi [Finish] takeTarget1Snapshot");
   }


public void takeTarget2Snapshot() {
      int approach_time = 10;
      if (!this.gsService.getOnSimulation()) {
         approach_time = 11;
      }

      boolean result = this.takeSnapshot(2, this.hasTargetSnapshot2, approach_time);
      this.hasTargetSnapshot2 |= result;
   }

   public double[][] getNavCamIntrinsics() {
      System.out.println("getNavCamIntrinsics >>>>777-A>>>>>>");
      double[][] camera_param = new double[2][];
      if (this.gsService.getOnSimulation()) {
         System.out.println("getNavCamIntrinsics >>>>888-A>>>>>>");
         camera_param[0] = this.NAVCAM_CAMERA_MATRIX_SIMULATION;
         camera_param[1] = this.NAVCAM_DISTORTION_COEFFICIENTS_SIMULATION;
      } else {
         System.out.println("getNavCamIntrinsics >>>>999-A>>>>>>");
         camera_param[0] = this.NAVCAM_CAMERA_MATRIX_ISS;
         camera_param[1] = this.NAVCAM_DISTORTION_COEFFICIENTS_ISS;
      }

      return camera_param;
   }

   public double[][] getDockCamIntrinsics() {
      double[][] camera_param = new double[2][];
      if (this.gsService.getOnSimulation()) {
         camera_param[0] = this.DOCKCAM_CAMERA_MATRIX_SIMULATION;
         camera_param[1] = this.DOCKCAM_DISTORTION_COEFFICIENTS_SIMULATION;
      } else {
         camera_param[0] = this.DOCKCAM_CAMERA_MATRIX_ISS;
         camera_param[1] = this.DOCKCAM_DISTORTION_COEFFICIENTS_ISS;
      }

      return camera_param;
   }



  public void saveBitmapImage(final Bitmap image, final String imageName) {
        try {
            System.out.print("KiboRpcApi [Start] saveBitmapImage");
            boolean checkArgs = true;
            if (image == null) {
                 logger.error("KiboRpcApi [saveBitmapImage] image is null");
                checkArgs = false;
            }
            if (imageName == null) {
                 logger.error("KiboRpcApi [saveBitmapImage] imageName is null");
                checkArgs = false;
            }
            if (checkArgs) {
                System.out.print("KiboRpcApi Parameters: image, imageName == " + imageName);
                if (this.gsService.getOnSimulation()) {
                     logger.debug("KiboRpcApi [saveBitmapImage] getOnSimulation: True");
                     logger.debug("KiboRpcApi [saveBitmapImage] Check directry");
                    final String filepath = this.gsService.getGuestScienceDataBasePath() + "/immediate/DebugImages";
                    final File file = new File(filepath);
                    if (!file.exists()) {
                         logger.debug("KiboRpcApi [saveBitmapImage] Make save directry");
                        file.mkdir();
                    }
                    final File[] list = file.listFiles();
                    final int width = image.getWidth();
                    final int height = image.getHeight();
                    final int size = width * height;
                    if (list.length >= 50) {
                         logger.error("KiboRpcApi [saveBitmapImage] Can't save more than 50 images");
                    }
                    else if (size > 1228800) {
                         logger.error("KiboRpcApi [saveBitmapImage] The size is too large.");
                    }
                    else {
                         logger.debug("KiboRpcApi [saveBitmapImage] Save bitmap image");
                        this.saveImage(image, imageName, file);
                    }
                }
                else {
                     logger.debug("KiboRpcApi [saveBitmapImage] getOnSimulation: False");
                }
            }
            System.out.print("KiboRpcApi [Finish] saveBitmapImage");
        }
        catch (Exception e) {
            this.sendExceptionMessage("[saveBitmapImage] Internal error was occurred.", e);
        }
    }
    


public Bitmap getBitmapDockCam() {
      Bitmap ret = this.gsService.getBitmapDockCam();
      if (ret == null) {
         System.out.print("KiboRpcApi [getBitmapDockCam] ret is null.");
      }

      System.out.print("KiboRpcApi [Finish] getBitmapDockCam");
      return ret;
   }

   public void boxData(Point goalPoint, Quaternion orientation) {

      JSONObject data = new JSONObject();
      
      JSONObject pdata = new JSONObject();
      pdata.put("x",goalPoint.getX());
      pdata.put("y",goalPoint.getY());
      pdata.put("z",goalPoint.getZ());
      data.put("p",pdata);
      JSONObject rdata = new JSONObject();
      rdata.put("x",orientation.getX());
      rdata.put("y",orientation.getY());
      rdata.put("z",orientation.getZ());
      data.put("s",rdata); 
      JSONObject cdata = new JSONObject();
      cdata.put("c",goalPoint.getX());
      cdata.put("o",goalPoint.getY());
      cdata.put("t",goalPoint.getZ());
      data.put("c",cdata);
      jsonBox.put(data);
      
      //System.out.println("=============== BOX JSON=============="); 
      //System.out.println(jsonResult.toString());  
      //System.out.println(pText);
   }

   public void robotData(Point goalPoint, Quaternion orientation) {

      JSONObject data = new JSONObject();

      JSONObject pdata = new JSONObject();
      pdata.put("x",goalPoint.getX());
      pdata.put("y",goalPoint.getY());
      pdata.put("z",goalPoint.getZ());
      data.put("p",pdata);
      JSONObject rdata = new JSONObject();
      rdata.put("x",orientation.getX());
      rdata.put("y",orientation.getY());
      rdata.put("z",orientation.getZ());
      data.put("r",rdata); 
      JSONObject cdata = new JSONObject();
      cdata.put("c",goalPoint.getX());
      cdata.put("o",goalPoint.getY());
      cdata.put("t",goalPoint.getZ());
      data.put("c",cdata);
      jsonRobot.put(data);

      //System.out.println("=============== ROBOT JSON=============="); 
      //System.out.println(jsonResult.toString());  
      //System.out.println(pText);
   }


   public void cameraData(Point goalPoint, Quaternion orientation) {
      JSONObject cm = new JSONObject(); 
      JSONObject data = new JSONObject();

      JSONObject pdata = new JSONObject();
      pdata.put("x",goalPoint.getX());
      pdata.put("y",goalPoint.getY());
      pdata.put("z",goalPoint.getZ());
      data.put("p",pdata);
      JSONObject rdata = new JSONObject();
      rdata.put("x",orientation.getX());
      rdata.put("y",orientation.getY());
      rdata.put("z",orientation.getZ());
      data.put("r",rdata); 
      JSONObject cdata = new JSONObject();
      cdata.put("fov",goalPoint.getX());
      cdata.put("aspect",goalPoint.getY());
      cdata.put("near",goalPoint.getZ());
      cdata.put("far",goalPoint.getZ());
      data.put("c",cdata);
      jsonResult.put("camera", data);
      //System.out.println("=============== CAMERA JSON=============="); 
      //System.out.println(jsonResult.toString());  
      //System.out.println(pText);
   }

   public void moveData(Point goalPoint, Quaternion orientation) {
      JSONObject data = new JSONObject();

      JSONObject pdata = new JSONObject();
      pdata.put("x",goalPoint.getX());
      pdata.put("y",goalPoint.getY());
      pdata.put("z",goalPoint.getZ());
      data.put("p",pdata);
      JSONObject rdata = new JSONObject();
      rdata.put("x",orientation.getX());
      rdata.put("y",orientation.getY());
      rdata.put("z",orientation.getZ());
      rdata.put("w",orientation.getW());
      data.put("r",rdata); 
      jsonMove.put(data);  
      //System.out.println("=============== MOVE JSON=============="); 
      //System.out.println(jsonMove.toString());  
      //System.out.println(pText);
   }

}
