package  jp.jaxa.iss.kibo.rpc.api;

import org.json.JSONObject;



public class ExternalValueHandler {
    private int buildNumber;
    private JSONObject jsonData;
    private static ExternalValueHandler instance = null;

      // Constructor
    private ExternalValueHandler() {
        this.buildNumber = 0; // Default build number
        this.jsonData = new JSONObject(); // Empty JSON object
    }

     public static ExternalValueHandler getInstance() {
        if (instance == null) {
            instance = new ExternalValueHandler();
        }
        return instance;
    }


    // Method to set build number
    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    // Method to get build number
    public int getBuildNumber() {
        return this.buildNumber;
    }

    // Method to set JSON data
    public void setJsonData(JSONObject jsonData) {
        this.jsonData = jsonData;
    }

    // Method to get JSON data
    public JSONObject getJsonData() {
        return this.jsonData;
    }

    public static void main(String[] args) {
        // Example usage
        ExternalValueHandler handler = new ExternalValueHandler();
        handler.setBuildNumber(123);
        
        JSONObject data = new JSONObject();
        data.put("key", "value");
        handler.setJsonData(data);
        
        System.out.println("Build Number: " + handler.getBuildNumber());
        System.out.println("JSON Data: " + handler.getJsonData().toString());
    }
}
