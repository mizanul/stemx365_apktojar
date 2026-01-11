package jp.jaxa.iss.kibo.rpc.api.sub;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONArray;

public class Phase
{
    private static final Log logger = LogFactory.getLog(Phase.class);
    private static final int MAX_TARGET_NUM = 6;
    private int targetNum;
    private Target[] Targets;
    
    public Phase(final JSONArray jsonPhaseConfig) throws JSONException {
        this.targetNum = 0;
        this.Targets = new Target[6];
        this.parseRandomPhaseConfig(jsonPhaseConfig);
    }
    
    private void parseRandomPhaseConfig(final JSONArray jsonPhaseConfig) throws JSONException {
        for (int i = 0; i < 6 && i < jsonPhaseConfig.length(); ++i) {
            final JSONObject jsonTargetConfig = jsonPhaseConfig.getJSONObject(i);
            final int targetId = jsonTargetConfig.getInt("target_id");
            final boolean active = jsonTargetConfig.getBoolean("active");
            this.Targets[i] = new Target(targetId, active);
            this.targetNum = i + 1;
        }
         logger.debug("Phase [parseRandomPhaseConfig] created targets is " + this.targetNum);
    }
    
    public List<Integer> getTargetActiveList() {
        final List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < this.targetNum; ++i) {
            if (this.Targets[i].getActive()) {
                list.add(this.Targets[i].getId());
            }
        }
        return list;
    }
    
    public void setTargetActive(final int targetId, final boolean active) {
        for (int i = 0; i < this.targetNum; ++i) {
            if (targetId == this.Targets[i].getId()) {
                this.Targets[i].setActive(active);
            }
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.targetNum; ++i) {
            if (i > 0) {
                sb.append(this.Targets[i].toString());
            }
            sb.append(this.Targets[i].toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
