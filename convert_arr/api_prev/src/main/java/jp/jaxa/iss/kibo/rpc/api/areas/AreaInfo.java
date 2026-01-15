/*
 * Decompiled with CFR 0.152.
 */
package jp.jaxa.iss.kibo.rpc.api.areas;

import org.json.JSONObject;

public class AreaInfo {
    private String itemName = "";
    private int number = 1;

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemName(String itemName, int number) {
        this.itemName = itemName;
        this.number = number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAreaInfoJson() throws Exception {
        JSONObject data = new JSONObject();
        data.put("lost_item", (Object)this.itemName);
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

