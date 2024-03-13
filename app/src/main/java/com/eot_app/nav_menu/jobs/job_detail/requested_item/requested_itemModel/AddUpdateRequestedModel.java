package com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel;

public class AddUpdateRequestedModel {
    private String itemName;
    private String ebId;
    private String  qty;
    private String modelNo;
    private String equId;
    private String itemId;
    private String jobId;
   private String irId;


    public AddUpdateRequestedModel(String itemName, String ebId, String qty, String modelNo, String equId, String itemId, String jobId) {
        this.itemName = itemName;
        this.ebId = ebId;
        this.qty = qty;
        this.modelNo = modelNo;
        this.equId = equId;
        this.itemId = itemId;
        this.jobId = jobId;
    }

    public AddUpdateRequestedModel(String itemName, String ebId, String qty, String modelNo, String equId, String itemId, String jobId, String irId) {
        this.itemName = itemName;
        this.ebId = ebId;
        this.qty = qty;
        this.modelNo = modelNo;
        this.equId = equId;
        this.itemId = itemId;
        this.jobId = jobId;
        this.irId = irId;
    }
}
