package com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel;

import java.io.Serializable;

public class RequestedItemModel implements Serializable {
    private String id;
    private String itemId;
    private String inm;
    private String modelNo;
    private String qty;
    private String stockBal;
    private String ebId;

    public RequestedItemModel(String id, String itemId, String inm, String modelNo, String qty, String stockBal) {
        this.id = id;
        this.itemId = itemId;
        this.inm = inm;
        this.modelNo = modelNo;
        this.qty = qty;
        this.stockBal = stockBal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getInm() {
        return inm;
    }

    public void setInm(String inm) {
        this.inm = inm;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getStockBal() {
        return stockBal;
    }

    public void setStockBal(String stockBal) {
        this.stockBal = stockBal;
    }

    public String getEbId() {
        return ebId;
    }

    public void setEbId(String ebId) {
        this.ebId = ebId;
    }
}
