package  jp.jaxa.iss.kibo.rpc.api;

import gov.nasa.arc.astrobee.Result;
import gov.nasa.arc.astrobee.ros.gs.*;
import gov.nasa.arc.astrobee.ros.internal.util.MessageType;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;

import gov.nasa.arc.astrobee.Kinematics;
import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.android.OpenCVLoaderEx;
//import org.opencv.android.StaticHelper;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gov.nasa.arc.astrobee.ros.gs.types.ImuResult;
import gov.nasa.arc.astrobee.ros.gs.types.MonoImage;
import gov.nasa.arc.astrobee.ros.gs.types.PointCloud;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.xbill.DNS.ResolverConfig;
import jp.jaxa.iss.kibo.rpc.api.sub.StartGuestScienceService;

public class KiboRpcService extends StartGuestScienceService{
    private static final Log logger = LogFactory.getLog(KiboRpcService.class);
    static{ System.loadLibrary("opencv_java420"); }

    //static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    // The API implementation
    protected KiboRpcApi api = null;

    private final String LOCATION_USLAB = "us_lab";
    private final String LOCATION_JEM = "jem";
    private final String LOCATION_GRANITE = "granite";
    // Default location. Change this depending on testing location
    private String default_location = LOCATION_JEM;
    
	@Override
	public void onGuestScienceCustomCmd(String arg0) {
		/* Inform the Ground Data System (GDS)
         * that this app received a command. */
        sendReceivedCustomCommand("info");
        System.out.println("command: " + arg0);
        try {
            // Transform the String command into a JSON object so we can read it.
            JSONObject jCommand = new JSONObject(arg0);

            // Get the name of the command we received. See commands.xml files in res folder.
            String sCommand = jCommand.getString("name");

            // JSON object that will contain the data we will send back to the GSM and GDS
            JSONObject jResult = new JSONObject();

            // This variable will contain the result of the last successful or unsuccessful movement
            Result result;

            switch (sCommand) {
                case "doTrajectory":
                    // Execute trajectory
                    if (default_location == LOCATION_JEM || default_location == LOCATION_GRANITE) {
                        System.out.print(">>>>>>>>> doUndockMoveDock >>>>>>>>>");
                        result = doUndockMoveDock();
                    } else {
                        System.out.print(">>>>>>>>> doTrajectory >>>>>>>>>");
                        result = doTrajectory();
                    }
                    break;
                case "runPlan1":
                    try {
                        System.out.print(">>>>>>>>> runPlan1 >>>>>>>>>");
                        this.runPlan1();
                    } catch (Exception var10) {
                        sendData(MessageType.JSON, "data", "ERROR: Some exception occurred in runPlan1");
                    }
                    break;
                case "runPlan2":
                    try {
                        this.runPlan2();
                    } catch (Exception var10) {
                        sendData(MessageType.JSON, "data", "ERROR: Some exception occurred in runPlan2");
                    }
                    break; 
                case "runPlan3":
                    try {
                        this.runPlan3();
                    } catch (Exception var10) {
                        sendData(MessageType.JSON, "data", "ERROR: Some exception occurred in runPlan3");
                    }
                    break;                                             
                default:
                    // Inform GS Manager and GDS, then stop execution.
                    sendData(MessageType.JSON, "data", "ERROR: Unrecognized command");
                    return;
            }

            // if (result == null) {
            //     // There were no points to loop
            //     jResult.put("Summary", new JSONObject()
            //             .put("Status", "ERROR")
            //             .put("Message", "Trajectory was not defined"));
            // } else if (!result.hasSucceeded()) {
            //     // If a goal point failed.
            //     jResult.put("Summary", new JSONObject()
            //             .put("Status", result.getStatus())
            //             .put("Message", result.getMessage()));
            // } else {
            //     // Success!
            //     jResult.put("Summary", new JSONObject()
            //             .put("Status", result.getStatus())
            //             .put("Message", "DONE!"));
            // }

            // Send data to the GS manager to be shown on the Ground Data System.
            sendData(MessageType.JSON, "data", jResult.toString());

        } catch (JSONException e) {
            // Send an error message to the GSM and GDS
            sendData(MessageType.JSON, "data", "ERROR parsing JSON");
        } catch (Exception ex) {
            // Send an error message to the GSM and GDS
            sendData(MessageType.JSON, "data", "Unrecognized ERROR");
        }

	}

	@Override
	public void onGuestScienceStart() {
        System.setProperty("dns.server", "127.0.0.1");
        System.setProperty("dns.search", "iss");
        ResolverConfig.refresh();
        // if (!OpenCVLoaderEx.initDebug(true)) {
        //     logger.error("OpenCv Unable to load OpenCV");
        // } else {
        //     logger.info("OpenCv OpenCV loaded");
        // }
        //System.loadLibrary("opencv_java420"); 

        // Get a unique instance of the Astrobee API in order to command the robot.
        api = KiboRpcApi.getInstance(this);

        // Inform GDS that the app has been started.
        sendStarted("info");
	}

	@Override
	public void onGuestScienceStop() {
		// Stop the API
        api.shutdownFactory();

        // Inform GDS that this app stopped.
        sendStopped("info");

        // Shutdown GS-Stub and kill application.
        terminate();

	}

	/**
     * Undock Astrobee, perform a trajectory (previously defined) and dock again.
     *
     * @return
     */
    private Result doUndockMoveDock() {
        // Undock
        Result result = api.undock();
        if (!result.hasSucceeded()) {
            return result;
        }

        // Do trajectory
        result = doTrajectory();
        if (result == null || !result.hasSucceeded()) {
            // There were no points to loop or one goal failed
            return result;
        }

        // Dock
        result = api.dock();
        return result;
    }


   public String getGuestScienceDataBasePath(){
        return "/";
   }


    /**
     * Execute a simple trajectory from the previously specified points and orientations.
     *
     * @return A Result from the execution of the move command in the Astrobee API.
     */
    public Result doTrajectory() {
        return null;
    }

    protected void runPlan1() {
        
    }

    protected void runPlan2() {
        
    }

     protected void runPlan3() {
        
    }
}
