package p001jp.jaxa.iss.kibo.rpc.api.areas;

import java.util.Map;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: jp.jaxa.iss.kibo.rpc.api.areas.AreaItemMap */
public class AreaItemMap {
    private Map<Integer, AreaInfo> AreaInfoMap = new TreeMap();

    public AreaItemMap() {
        setAreaInfo(1, "", 1);
        setAreaInfo(2, "", 1);
        setAreaInfo(3, "", 1);
        setAreaInfo(4, "", 1);
    }

    public void setAreaInfo(int areaId, String itemName) {
        if (this.AreaInfoMap.containsKey(Integer.valueOf(areaId))) {
            this.AreaInfoMap.get(Integer.valueOf(areaId)).setItemName(itemName);
            return;
        }
        AreaInfo newArea = new AreaInfo();
        newArea.setItemName(itemName);
        this.AreaInfoMap.put(Integer.valueOf(areaId), newArea);
    }

    public void setAreaInfo(int areaId, String itemName, int number) {
        if (this.AreaInfoMap.containsKey(Integer.valueOf(areaId))) {
            this.AreaInfoMap.get(Integer.valueOf(areaId)).setItemName(itemName, number);
            return;
        }
        AreaInfo newArea = new AreaInfo();
        newArea.setItemName(itemName, number);
        this.AreaInfoMap.put(Integer.valueOf(areaId), newArea);
    }

    public JSONObject getAreaItemMapJson() throws Exception {
        JSONObject data = new JSONObject();
        JSONArray areas = new JSONArray();
        for (Map.Entry<Integer, AreaInfo> entry : this.AreaInfoMap.entrySet()) {
            JSONObject areaInfo = new JSONObject();
            areaInfo.put("area_id", entry.getKey());
            areaInfo.put("lost_item", entry.getValue().getItemName());
            areaInfo.put("num", entry.getValue().getNumber());
            areas.put(areaInfo);
        }
        data.put("area_item_map", areas);
        return data;
    }
}
