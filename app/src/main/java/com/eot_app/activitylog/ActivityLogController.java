package com.eot_app.activitylog;

import android.util.Log;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.db.OfflineDataController;
import com.google.gson.Gson;

// To manage the logs
public class  ActivityLogController implements LogContants {

    public static String getStringModuleCode(String code) {
        String name = "";
        switch (code) {
            case "1":
                name = "Login";
                break;
            case "5":
                name = "Job";
                break;
            case "2":
                name = "Client";
                break;
            case "13":
                name = "Audit";
                break;
            case "6":
                name = "Quote";
                break;
        }
        return name;
    }

    public static LogModel getObj(String moduelName, String apiName, String fromModule) {
        Log.e("", "");
        String message = "User visit in " + getStringModuleCode(moduelName) + " module and " + apiName + " from " + getStringModuleCode(fromModule);
        return new LogModel(moduelName, message);
    }

    public static void saveActivity(String moduelName, String apiName, String fromModule) {
        if (isLogEnabled()) {
            if (AppUtility.isInternetConnected()) {
                LogModel logModel = getObj(moduelName, apiName, fromModule);
                saveOfflineTable(logModel);
            }
        }
    }

    // added new api for inserting log data in bulk on 25 records
    public static void saveOfflineTable(LogModel logModel) {
        if (isLogEnabled()) {
            String param = new Gson().toJson(logModel);
            OfflineDataController.getInstance().addLogListInOfflineDB(Service_apis.insertUserActivityBulk,
                    param,
                    System.currentTimeMillis() + "");
        }
    }


    private static boolean isLogEnabled() {
        boolean isEnable = false;
        try {
            isEnable = App_preference.getSharedprefInstance().getLoginRes().getIsActivityLogEnable().equals("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEnable;
    }

}


