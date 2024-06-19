package com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model;

public class GetStockListReqModel {
    private int limit;
    private int index;
    private String  dateTime;
    int usrId;
    private String  search = "";


    public GetStockListReqModel(int limit, int index, String dateTime, int usrId, String search) {
        this.limit = limit;
        this.index = index;
        this.dateTime = dateTime;
        this.usrId = usrId;
        this.search = search;
    }
}
