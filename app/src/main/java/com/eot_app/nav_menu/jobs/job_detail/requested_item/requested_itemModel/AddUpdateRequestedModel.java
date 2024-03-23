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
   private String label;
   private String brandName;


    public AddUpdateRequestedModel(String itemName, String ebId, String qty, String modelNo, String equId, String itemId, String jobId) {
        this.itemName = itemName;
        this.ebId = ebId;
        this.qty = qty;
        this.modelNo = modelNo;
        this.equId = equId;
        this.itemId = itemId;
        this.jobId = jobId;
    }

    public AddUpdateRequestedModel(String itemName, String ebId, String qty, String modelNo, String equId, String itemId,
                                   String jobId, String irId, String jobLabel, String brandName) {
        this.itemName = itemName;
        this.ebId = ebId;
        this.qty = qty;
        this.modelNo = modelNo;
        this.equId = equId;
        this.itemId = itemId;
        this.jobId = jobId;
        this.irId = irId;
        this.label = jobLabel;
        this.brandName = brandName;
    }

    public AddUpdateRequestedModel(String itemName, String ebId, String qty, String modelNo, String equId, String itemId, String jobId,String irId) {
        this.itemName = itemName;
        this.ebId = ebId;
        this.qty = qty;
        this.modelNo = modelNo;
        this.equId = equId;
        this.itemId = itemId;
        this.jobId = jobId;
        this.irId = irId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getEbId() {
        return ebId;
    }

    public void setEbId(String ebId) {
        this.ebId = ebId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getEquId() {
        return equId;
    }

    public void setEquId(String equId) {
        this.equId = equId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getIrId() {
        return irId;
    }

    public void setIrId(String irId) {
        this.irId = irId;
    }

    public String getJobLabel() {
        return label;
    }

    public void setJobLabel(String jobLabel) {
        this.label = jobLabel;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
