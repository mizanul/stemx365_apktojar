package p001jp.jaxa.iss.kibo.rpc.api.areas;

import org.json.JSONObject;

/* renamed from: jp.jaxa.iss.kibo.rpc.api.areas.AreaInfo */
public class AreaInfo {
    private String itemName = "";
    private int number = 1;

    public void setItemName(String itemName2) {
        this.itemName = itemName2;
    }

    public void setItemName(String itemName2, int number2) {
        this.itemName = itemName2;
        this.number = number2;
    }

    public void setNumber(int number2) {
        this.number = number2;
    }

    public String getAreaInfoJson() throws Exception {
        JSONObject data = new JSONObject();
        data.put("lost_item", this.itemName);
        data.put("num,", this.number);
        return data.toString();
    }

    public String getItemName() {
        return this.itemName;
    }

    public int getNumber() {
        return this.number;
    }
}
