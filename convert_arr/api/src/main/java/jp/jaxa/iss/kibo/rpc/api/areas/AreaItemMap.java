package jp.jaxa.iss.kibo.rpc.api.areas;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

public class AreaItemMap {
   private Map<Integer, AreaInfo> AreaInfoMap = new TreeMap();

   public AreaItemMap() {
      this.setAreaInfo(1, "", 1);
      this.setAreaInfo(2, "", 1);
      this.setAreaInfo(3, "", 1);
      this.setAreaInfo(4, "", 1);
   }

   public void setAreaInfo(int areaId, String itemName) {
      if (this.AreaInfoMap.containsKey(areaId)) {
         ((AreaInfo)this.AreaInfoMap.get(areaId)).setItemName(itemName);
      } else {
         AreaInfo newArea = new AreaInfo();
         newArea.setItemName(itemName);
         this.AreaInfoMap.put(areaId, newArea);
      }

   }

   public void setAreaInfo(int areaId, String itemName, int number) {
      if (this.AreaInfoMap.containsKey(areaId)) {
         ((AreaInfo)this.AreaInfoMap.get(areaId)).setItemName(itemName, number);
      } else {
         AreaInfo newArea = new AreaInfo();
         newArea.setItemName(itemName, number);
         this.AreaInfoMap.put(areaId, newArea);
      }

   }

   public JSONObject getAreaItemMapJson() throws Exception {
      JSONObject data = new JSONObject();
      JSONArray areas = new JSONArray();
      Iterator var3 = this.AreaInfoMap.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<Integer, AreaInfo> entry = (Entry)var3.next();
         JSONObject areaInfo = new JSONObject();
         areaInfo.put("area_id", entry.getKey());
         areaInfo.put("lost_item", ((AreaInfo)entry.getValue()).getItemName());
         areaInfo.put("num", ((AreaInfo)entry.getValue()).getNumber());
         areas.put(areaInfo);
      }

      data.put("area_item_map", areas);
      return data;
   }
}
