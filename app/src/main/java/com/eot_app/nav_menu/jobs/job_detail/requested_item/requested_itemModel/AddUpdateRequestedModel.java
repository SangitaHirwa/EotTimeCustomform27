package com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel;

public class AddUpdateRequestedModel {
    private String temName;
    private String brandId;
    private String  quantity;
    private String modelNo;
    private String equId;
    private String itemId;
    private String jobId;
   private String irId;


    public AddUpdateRequestedModel(String temName, String brandId, String quantity, String modelNo, String equId, String itemId, String jobId) {
        this.temName = temName;
        this.brandId = brandId;
        this.quantity = quantity;
        this.modelNo = modelNo;
        this.equId = equId;
        this.itemId = itemId;
        this.jobId = jobId;
    }

    public AddUpdateRequestedModel(String temName, String brandId, String quantity, String modelNo, String equId, String itemId, String jobId, String irId) {
        this.temName = temName;
        this.brandId = brandId;
        this.quantity = quantity;
        this.modelNo = modelNo;
        this.equId = equId;
        this.itemId = itemId;
        this.jobId = jobId;
        this.irId = irId;
    }
}
