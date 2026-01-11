package jp.jaxa.iss.kibo.rpc.api.sub;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import jp.jaxa.iss.kibo.rpc.api.GetterNode;
import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;

public class PhaseConfig
{
    private static final Log logger = LogFactory.getLog(PhaseConfig.class);
    private static final String PHASE_CONFIG_FILE = "/incoming/random_phase_config.json";
    private static final int MAX_PHASE_NUM = 20;
    private final int retryTimes = 3;
    private final int retrySleepTimeMs = 1000;
    private int phaseNum;
    //private GetterNode getterNode;
    private KiboRpcService gsService;
    private Phase[] Phases;
    private String correctReport;
    
    public PhaseConfig(final KiboRpcService gsService, final String configDir) throws IOException, JSONException, InterruptedException {
        this.phaseNum = 0;
        this.Phases = new Phase[20];
        this.correctReport = null;
         logger.debug("Stemx365RpcApi [PhaseConfig] Start.");
        this.gsService = gsService;
        final boolean isSimulation = this.gsService.getOnSimulation();
         logger.debug("Stemx365RpcApi [PhaseConfig] isSimulation: " + isSimulation);
         logger.debug("Stemx365RpcApi [PhaseConfig] configDir: " + configDir);
        this.loadRandomPhaseConfig(isSimulation, configDir);
        logger.info("Stemx365RpcApi [PhaseConfig] created Phase is " + this.phaseNum);
         logger.debug("Stemx365RpcApi [PhaseConfig] Finish.");
    }
    
    public int getPhaseNum() {
        return this.phaseNum;
    }
    
    public Phase[] getPhases() {
        return this.Phases;
    }
    
    public String getCorrectReport() {
        return this.correctReport;
    }
    
    private void loadRandomPhaseConfig(final boolean isSimulation, final String configDir) throws IOException, JSONException, InterruptedException {
        JSONObject jsonObj = null;
        if (isSimulation) {
            jsonObj = this.gsService.getRandomPhasesConfig();
            int i = 1;
            while (true) {
                final int n = i;
                this.getClass();
                if (n > 3 || jsonObj.length() != 0) {
                    break;
                }
                this.getClass();
                Thread.sleep(1000L);
                logger.info("Stemx365RpcApi [PhaseConfig] loadRandomPhaseConfig Retry " + i);
                jsonObj = this.gsService.getRandomPhasesConfig();
                ++i;
            }
        }
        else {
            final String filePathName = configDir + "/incoming/random_phase_config.json";
            final String sentence = this.readFile(filePathName);
            jsonObj = new JSONObject(sentence);
        }
        logger.info("Stemx365RpcApi [PhaseConfig] loadRandomPhaseConfig " + jsonObj);
        this.parseRandomPhasesConfig(jsonObj);
    }

    private void parseRandomPhasesConfig(final JSONObject jsonObj) throws JSONException {
        final JSONArray items = jsonObj.getJSONArray("phases_info");
        final int itemsLength = items.length();
        if (itemsLength > 0) {
            int itemIdx = 0;
            for (int i = 0; i < 20; ++i) {
                final JSONArray jsonPhaseConfig = items.getJSONArray(itemIdx);
                this.Phases[i] = new Phase(jsonPhaseConfig);
                this.phaseNum = i + 1;
                if (++itemIdx >= itemsLength) {
                    itemIdx = 0;
                }
            }
        }
        for (int k = 0; k < this.phaseNum; ++k) {
            final String msg = String.format("Phases(%d): %s", k + 1, this.Phases[k].toString());
             logger.debug("Stemx365RpcApi [PhaseConfig] " + msg);
        }
        if (!this.gsService.getOnSimulation()) {
            this.correctReport = jsonObj.getString("qr_info");
             logger.debug("Stemx365RpcApi [PhaseConfig] parseRandomPhasesConfig qr_info: " + this.correctReport);
        }
    }
    
    private String readFile(final String file) throws IOException {
        try (final FileInputStream fi = new FileInputStream(file);
             final InputStreamReader in = new InputStreamReader(fi);
             final BufferedReader br = new BufferedReader(in)) {
            final StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }
}
