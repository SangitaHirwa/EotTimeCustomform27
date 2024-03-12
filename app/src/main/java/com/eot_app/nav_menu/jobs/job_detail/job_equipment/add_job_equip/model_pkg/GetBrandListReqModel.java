package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg;

public class GetBrandListReqModel {
 private int limit;
    private int index;
    private String  search = "";

    public GetBrandListReqModel(int limit, int index, String search) {
        this.limit = limit;
        this.index = index;
        this.search = search;
    }
}
