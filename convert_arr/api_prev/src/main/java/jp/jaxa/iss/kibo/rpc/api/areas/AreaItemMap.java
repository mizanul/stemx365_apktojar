/*
 * Decompiled with CFR 0.152.
 */
package jp.jaxa.iss.kibo.rpc.api.areas;

import java.util.Map;
import java.util.TreeMap;
import jp.jaxa.iss.kibo.rpc.api.areas.AreaInfo;
import org.json.JSONArray;
import org.json.JSONObject;

public class AreaItemMap {
    private Map<Integer, AreaInfo> AreaInfoMap = new TreeMap<Integer, AreaInfo>();

    public AreaItemMap() {
        this.setAreaInfo(1, "", 1);
        this.setAreaInfo(2, "", 1);
        this.setAreaInfo(3, "", 1);
        this.setAreaInfo(4, "", 1);
    }

    public void setAreaInfo(int areaId, String itemName) {
        if (this.AreaInfoMap.containsKey(areaId)) {
            this.AreaInfoMap.get(areaId).setItemName(itemName);
        } else {
            AreaInfo newArea = new AreaInfo();
            newArea.setItemName(itemName);
            this.AreaInfoMap.put(areaId, newArea);
        }
    }

    public void setAreaInfo(int areaId, String itemName, int number) {
        if (this.AreaInfoMap.containsKey(areaId)) {
            this.AreaInfoMap.get(areaId).setItemName(itemName, number);
        } else {
            AreaInfo newArea = new AreaInfo();
            newArea.setItemName(itemName, number);
            this.AreaInfoMap.put(areaId, newArea);
        }
    }

    public JSONObject getAreaItemMapJson() throws Exception {
        JSONObject data = new JSONObject();
        JSONArray areas = new JSONArray();
        for (Map.Entry<Integer, AreaInfo> entry : this.AreaInfoMap.entrySet()) {
            JSONObject areaInfo = new JSONObject();
            areaInfo.put("area_id", (Object)entry.getKey());
            areaInfo.put("lost_item", (Object)entry.getValue().getItemName());
            areaInfo.put("num", entry.getValue().getNumber());
            areas.put((Object)areaInfo);
        }
        data.put("area_item_map", (Object)areas);
        return data;
    }
}

