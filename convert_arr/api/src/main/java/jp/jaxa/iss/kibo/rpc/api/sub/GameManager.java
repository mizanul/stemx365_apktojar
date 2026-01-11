 

package jp.jaxa.iss.kibo.rpc.api.sub;

import gov.nasa.arc.astrobee.ros.internal.util.MessageType;

import org.json.JSONArray;
import java.util.Date;
import org.json.JSONException;
import gov.nasa.arc.astrobee.ros.internal.util.MessageType;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import org.ros.message.Duration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.text.SimpleDateFormat;
import org.ros.message.Time;
//import jp.jaxa.iss.kibo.rpc.api.GetterNode;
import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;
import java.text.DateFormat;

public class GameManager extends Thread
{
    private static final Log logger = LogFactory.getLog(GameManager.class);
    private static final long MISSION_TIME_LIMIT = 300L;
    private static final long ACTIVE_TIME_LIMIT = 120L;
    private static final int PHASE_INFO_CYCLE = 30;
    private final int WAIT_NODE_ONSTART_RETRY = 3;
    private final int WAIT_NODE_ONSTART_SLEEP_MS = 1000;
    private static GameManager instance;
    private final DateFormat df;
    private KiboRpcService gsService;
    //private GetterNode getterNode;
    private PhaseConfig phaseConfig;
    private int phaseNum;
    private Phase[] Phases;
    private boolean isLoop;
    private boolean isPhaseConfigLoaded;
    private boolean isMissionStart;
    private boolean isPhaseOver;
    private int targetPhaseIndex;
    private Time tmMissionStart;
    private Time tmMissionLimit;
    private Time tmActiveStart;
    private Time tmActiveLimit;
    private Time tmLastPhaseInfoNotice;
    private final Object mLock;
    
    private GameManager(final KiboRpcService startGuestScienceService) {
        this.df = new SimpleDateFormat("yyyyMMdd hhmmssSSS");
        this.phaseNum = 0;
        this.isLoop = true;
        this.isPhaseConfigLoaded = false;
        this.isMissionStart = false;
        this.isPhaseOver = false;
        this.targetPhaseIndex = -1;
        this.tmMissionStart = null;
        this.tmMissionLimit = null;
        this.tmActiveStart = null;
        this.tmActiveLimit = null;
        this.tmLastPhaseInfoNotice = null;
        this.mLock = new Object();
         logger.debug("Stemx365RpcApi [GameManager] initialization started.");
        try {
            this.gsService = startGuestScienceService;
            //this.getterNode = GetterNode.getInstance();
            int i = 1;
            while (!this.gsService.isStarted()) {
                Thread.sleep(1000L);
                logger.info("Stemx365RpcApi [GameManager] Wait for node started.. Retry " + i);
                if (3 <= i) {
                     logger.error("Stemx365RpcApi [GameManager] Wait for node started... Retry over!");
                    throw new Exception("[GameManager] Wait for node started... Retry over!");
                }
                ++i;
            }
            final String configDir = this.gsService.getGuestScienceDataBasePath();
            this.phaseConfig = new PhaseConfig(this.gsService, configDir);
            this.phaseNum = this.phaseConfig.getPhaseNum();
            this.Phases = this.phaseConfig.getPhases();
            this.isPhaseConfigLoaded = true;
             logger.debug("Stemx365RpcApi [GameManager] initialization succeeded.");
        }
        catch (Exception e) {
            this.sendFailedLoadPhaseConfig(e);
             logger.error("Stemx365RpcApi [GameManager] initialization failed.");
        }
    }
    
    public static GameManager getInstance(final KiboRpcService startGuestScienceService) {
        if (GameManager.instance == null) {
            GameManager.instance = new GameManager(startGuestScienceService);
        }
        return GameManager.instance;
    }
    
    @Override
    public void run() {
        while (this.isLoop) {
            if (this.isPhaseConfigLoaded && this.isMissionStart) {
                final Time now = this.getCurrentTime();
                if (this.tmActiveLimit == null) {
                    continue;
                }
                if (!this.isPhaseOver && this.tmActiveLimit.compareTo(now) < 0) {
                    if (this.switchNextPhase()) {
                        this.sendPhaseInfo();
                    }
                }
                else if (!this.isPhaseOver && this.Phases[this.targetPhaseIndex].getTargetActiveList().size() <= 0) {
                    if (this.switchNextPhase()) {
                        this.sendPhaseInfo();
                    }
                }
                else if (this.tmLastPhaseInfoNotice != null) {
                    final Duration duration = now.subtract(this.tmLastPhaseInfoNotice);
                    duration.normalize();
                    if (duration.secs >= 30) {
                        this.periodicPhaseInfoNotification();
                        this.tmLastPhaseInfoNotice.secs = now.secs;
                        this.tmLastPhaseInfoNotice.nsecs = now.nsecs;
                    }
                }
            }
            Thread.yield();
        }
    }
    
    public int getCurrentPhaseNumber() {
        synchronized (this.mLock) {
            if (this.targetPhaseIndex < 0) {
                return 0;
            }
            return this.targetPhaseIndex + 1;
        }
    }
    
    public String getCorrectReport() {
        return this.phaseConfig.getCorrectReport();
    }
    
    public void startMission() {
         logger.debug("Stemx365RpcApi [GameManager] startMission");
        this.tmMissionStart = this.getCurrentTime();
        this.tmMissionLimit = new Time(this.tmMissionStart.secs, this.tmMissionStart.nsecs);
        final Time tmMissionLimit = this.tmMissionLimit;
        tmMissionLimit.secs += (int)300L;
        this.tmLastPhaseInfoNotice = new Time(this.tmMissionStart.secs, this.tmMissionStart.nsecs);
        this.isMissionStart = true;
        this.targetPhaseIndex = -1;
        this.switchNextPhase();
    }
    
    @Deprecated
    public void stopMission() {
         logger.debug("Stemx365RpcApi [GameManager] stopMission");
        this.isMissionStart = false;
        this.targetPhaseIndex = -1;
    }
    
    public List<Integer> getActiveTargets() {
        synchronized (this.mLock) {
            if (this.isMissionStart) {
                return this.Phases[this.targetPhaseIndex].getTargetActiveList();
            }
             logger.debug("Stemx365RpcApi [GameManager] mission not started. (in getActiveTargets())");
            return new ArrayList<Integer>();
        }
    }
    
    public boolean targetDeactivation(final int targetId) {
        synchronized (this.mLock) {
            if (!this.isMissionStart) {
                 logger.debug("Stemx365RpcApi [GameManager] mission not started. (in targetDeactivation())");
                return false;
            }
            try {
                this.Phases[this.targetPhaseIndex].setTargetActive(targetId, false);
                this.sendPhaseInfo();
                return true;
            }
            catch (Exception e) {
                 logger.error("Stemx365RpcApi [GameManager] Fail to deactivate the target.", (Throwable)e);
                return false;
            }
        }
    }
    
    public List<Long> getTimeRemaining() {
        synchronized (this.mLock) {
            final List<Long> list = new ArrayList<Long>();
            list.add(this.getActiveTimeRemain());
            list.add(this.getMissionTimeRemain());
            return list;
        }
    }
    
    private long getActiveTimeRemain() {
         logger.debug("Stemx365RpcApi [Start] getActiveTimeRemain");
        long ret = 0L;
        if (this.isMissionStart) {
            final Time now = this.getCurrentTime();
            if (this.tmActiveLimit.compareTo(now) > 0) {
                final Duration duration = this.tmActiveLimit.subtract(now);
                duration.normalize();
                ret = duration.totalNsecs() / 1000000L;
            }
        }
        else {
             logger.debug("Stemx365RpcApi [GameManager] mission not started. (in getActiveTimeRemain())");
        }
         logger.debug("Stemx365RpcApi [getActiveTimeRemain] ActiveTimeRemain: " + ret);
         logger.debug("Stemx365RpcApi [Finish] getActiveTimeRemain");
        return ret;
    }
    
    private long getMissionTimeRemain() {
         logger.debug("Stemx365RpcApi [Start] getMissionTimeRemain");
        long ret = 0L;
        if (this.isMissionStart) {
            final Time now = this.getCurrentTime();
            if (this.tmMissionLimit.compareTo(now) > 0) {
                final Duration duration = this.tmMissionLimit.subtract(now);
                duration.normalize();
                ret = duration.totalNsecs() / 1000000L;
            }
        }
        else {
             logger.debug("Stemx365RpcApi [GameManager] mission not started. (in getMissionTimeRemain())");
        }
         logger.debug("Stemx365RpcApi [getMissionTimeRemain] MissionTimeRemain: " + ret);
         logger.debug("Stemx365RpcApi [Finish] getMissionTimeRemain");
        return ret;
    }
    
    private Time getCurrentTime() {
        return this.gsService.getCurrentTime();
    }
    
    private boolean switchNextPhase() {
         logger.debug("Stemx365RpcApi [Start] switchNextPhase");
        synchronized (this.mLock) {
            final int curentIndex = this.targetPhaseIndex;
            if (this.targetPhaseIndex < 0) {
                this.targetPhaseIndex = 0;
            }
            else if (this.phaseNum == this.targetPhaseIndex + 1) {
                this.isPhaseOver = true;
                 logger.debug("Stemx365RpcApi [switchNextPhase] phase is over.");
            }
            else {
                ++this.targetPhaseIndex;
            }
            if (curentIndex < this.targetPhaseIndex) {
                this.tmActiveStart = this.getCurrentTime();
                this.tmActiveLimit = new Time(this.tmActiveStart.secs, this.tmActiveStart.nsecs);
                final Time tmActiveLimit = this.tmActiveLimit;
                tmActiveLimit.secs += (int)120L;
                final String msg1 = String.format("switching phase from %d to %d.", curentIndex + 1, this.targetPhaseIndex + 1);
                final String msg2 = String.format("phase(%d): %s", this.targetPhaseIndex + 1, this.Phases[this.targetPhaseIndex].toString());
                 logger.debug("Stemx365RpcApi [switchNextPhase] " + msg1);
                 logger.debug("Stemx365RpcApi [switchNextPhase] " + msg2);
                 logger.debug("Stemx365RpcApi [Finish] switchNextPhase");
                return true;
            }
             logger.debug("Stemx365RpcApi [switchNextPhase] Did not switch phases.");
             logger.debug("Stemx365RpcApi [Finish] switchNextPhase");
            return false;
        }
    }
    
    private void sendExceptionMessage(final String errmsg, final Exception err) {
         logger.debug("Stemx365RpcApi [GameManager] sendExceptionMessage start.");
        try {
            final JSONObject data = new JSONObject();
            data.put("error", (Object)("[" + err.getClass().getName() + "] " + errmsg));
             logger.error("Stemx365RpcApi [GameManager] " + errmsg, (Throwable)err);
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
        }
        catch (JSONException e) {
            this.gsService.sendData(MessageType.JSON, "data", e.getClass().getName());
             logger.error("Stemx365RpcApi [GameManager] sendExceptionMessage error.", (Throwable)e);
        }
         logger.debug("Stemx365RpcApi [GameManager] sendExceptionMessage finish.");
    }
    
    private void sendFailedLoadPhaseConfig(final Exception err) {
         logger.debug("Stemx365RpcApi [GameManager] sendFailedLoadPhaseConfig start.");
        try {
            final JSONObject data = new JSONObject();
            data.put("failed", (Object)"Failed to load phase config");
             logger.error("GameManager Failed to load phase config", (Throwable)err);
            this.gsService.sendData(MessageType.JSON, "data", data.toString());
        }
        catch (JSONException e) {
            this.sendExceptionMessage("[GameManager] 'Failed to load phase config' send error.", (Exception)e);
        }
         logger.debug("Stemx365RpcApi [GameManager] sendFailedLoadPhaseConfig finish.");
    }
    
    private void sendPhaseInfo() {
         logger.debug("Stemx365RpcApi [Start] sendPhaseInfo");
        if (this.isMissionStart) {
            try {
                final JSONObject data = new JSONObject();
                data.put("phase_number", this.targetPhaseIndex + 1);
                data.put("t_stamp_phase", (Object)this.df.format(new Date(System.currentTimeMillis())));
                data.put("active_time", (Object)this.getActiveTime());
                final List<Integer> l = this.Phases[this.targetPhaseIndex].getTargetActiveList();
                final int[] array = new int[l.size()];
                for (int i = 0; i < array.length; ++i) {
                    array[i] = l.get(i);
                }
                final JSONArray jsonArray = new JSONArray((Object)array);
                data.put("active_targets", (Object)jsonArray);
                this.gsService.sendData(MessageType.JSON, "data", data.toString());
                 logger.debug("Stemx365RpcApi sendPhaseInfo: " + data.toString());
            }
            catch (JSONException e) {
                this.sendExceptionMessage("[GameManager] Failed to send a phase info.", (Exception)e);
            }
        }
        else {
             logger.debug("Stemx365RpcApi [GameManager] mission not started. (in sendPhaseInfo())");
        }
         logger.debug("Stemx365RpcApi [Finish] sendPhaseInfo");
    }
    
    private void periodicPhaseInfoNotification() {
         logger.debug("Stemx365RpcApi [Start] periodicPhaseInfoNotification");
        if (this.isMissionStart) {
            try {
                final JSONObject data = new JSONObject();
                data.put("periodic_ph_no", this.targetPhaseIndex + 1);
                data.put("periodic_t_stamp", (Object)this.df.format(new Date(System.currentTimeMillis())));
                data.put("periodic_act_time", (Object)this.getActiveTime());
                final List<Integer> l = this.Phases[this.targetPhaseIndex].getTargetActiveList();
                final int[] array = new int[l.size()];
                for (int i = 0; i < array.length; ++i) {
                    array[i] = l.get(i);
                }
                final JSONArray jsonArray = new JSONArray((Object)array);
                data.put("periodic_act_trgts", (Object)jsonArray);
                this.gsService.sendData(MessageType.JSON, "data", data.toString());
                 logger.debug("Stemx365RpcApi sendPhaseInfo: " + data.toString());
            }
            catch (JSONException e) {
                this.sendExceptionMessage("[GameManager] Failed to send a phase info.", (Exception)e);
            }
        }
        else {
             logger.debug("Stemx365RpcApi [GameManager] mission not started. (in periodicPhaseInfoNotification())");
        }
         logger.debug("Stemx365RpcApi [Finish] periodicPhaseInfoNotification");
    }
    
    private String getActiveTime() {
        final Time now = this.getCurrentTime();
        final Duration duration = now.subtract(this.tmActiveStart);
        duration.normalize();
        final int min = duration.secs / 60;
        final int sec = duration.secs % 60;
        final int msec = duration.nsecs / 1000000;
        return String.format("%02d:%02d.%03d", min, sec, msec);
    }
    
    static {
        GameManager.instance = null;
    }
}
