package com.eot_app.nav_menu.jobs.job_detail.job_equipment.model;

public class UpdateEquStatusReqModel {
   private String jobId;
    private String equId;
    private String equStatus;

    public UpdateEquStatusReqModel(String jobId, String equId, String equStatus) {
        this.jobId = jobId;
        this.equId = equId;
        this.equStatus = equStatus;
    }
}
