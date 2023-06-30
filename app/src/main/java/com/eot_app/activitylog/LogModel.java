package com.eot_app.activitylog;

import com.eot_app.utility.AppUtility;

public class LogModel {
    int device = 4;
    String module;
    String msg;
    String date= AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm");

    public LogModel(String module, String msg) {
        this.module = module;
        this.msg = msg;
    }
}
