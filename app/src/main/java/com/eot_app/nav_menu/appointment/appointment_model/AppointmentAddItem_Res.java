package com.eot_app.nav_menu.appointment.appointment_model;

import android.os.Parcelable;

import com.eot_app.nav_menu.appointment.details.documents.AppointmentTax;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.Tax;

import java.util.List;

public class AppointmentAddItem_Res {
    private String itemId;
    private String inm;
    private int dataType;
    private int itemType;
    private String rate;
    private String supplierCost;
    private String serialNo;
    private String qty;
    private String discount;
    private int isGrouped;
    private List<Tax> tax;
    private String des;
    private String hsncode;
    private String pno;
    private String unit;
    private String isBillable;
    private String isBillableChange;
    private String tempId;
    private String ilmmId;
    private String isLabourParent;
    private double taxamnt;
    private String isPartParent;
    private String isPartChild;
    private String partTempId;
    private int isItemOrTitle;
    private String isLabourChild;
    private  String labourTempId;


    public AppointmentAddItem_Res(String itemId, String inm, int dataType, int itemType, String rate, String supplierCost, String serialNo, String qty, String discount, int isGrouped, List<Tax> tax, String des, String hsncode, String pno, String unit, String isBillable, String isBillableChange, String tempId, String ilmmId, String isLabourParent, double taxamnt, String isPartParent, String isPartChild, String partTempId, int isItemOrTitle) {
        this.itemId = itemId;
        this.inm = inm;
        this.dataType = dataType;
        this.itemType = itemType;
        this.rate = rate;
        this.supplierCost = supplierCost;
        this.serialNo = serialNo;
        this.qty = qty;
        this.discount = discount;
        this.isGrouped = isGrouped;
        this.tax = tax;
        this.des = des;
        this.hsncode = hsncode;
        this.pno = pno;
        this.unit = unit;
        this.isBillable = isBillable;
        this.isBillableChange = isBillableChange;
        this.tempId = tempId;
        this.ilmmId = ilmmId;
        this.isLabourParent = isLabourParent;
        this.taxamnt = taxamnt;
        this.isPartParent = isPartParent;
        this.isPartChild = isPartChild;
        this.partTempId = partTempId;
        this.isItemOrTitle = isItemOrTitle;
    }
    public AppointmentAddItem_Res(String itemId, String inm, int dataType,
                                  int itemType, String rate, String supplierCost,
                                  String serialNo, String qty, String discount,
                                  int isGrouped, List<Tax> tax, String des,
                                  String hsncode, String pno, String unit,
                                  String isBillableChange, String tempId, String ilmmId, String isLabourParent,
                                  String isLabourChild,String labourTempId,
                                  double taxamnt, String isPartParent, String isPartChild,
                                  String partTempId, int isItemOrTitle) {
        this.itemId = itemId;
        this.inm = inm;
        this.dataType = dataType;
        this.itemType = itemType;
        this.rate = rate;
        this.supplierCost = supplierCost;
        this.serialNo = serialNo;
        this.qty =qty;
        this.discount = discount;
        this.isGrouped = isGrouped;
        this.tax = tax;
        this.des = des;
        this.hsncode = hsncode;
        this.pno = pno;
        this.unit = unit;
        this.isBillableChange = isBillableChange;
        this.tempId = tempId;
        this.ilmmId = ilmmId;
        this.isLabourParent = isLabourParent;
        this.isLabourChild = isLabourChild;
        this.labourTempId = labourTempId;
        this.taxamnt = taxamnt;
        this.isPartParent = isPartParent;
        this.isPartChild = isPartChild;
        this.partTempId = partTempId;
        this.isItemOrTitle = isItemOrTitle;


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

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSupplierCost() {
        return supplierCost;
    }

    public void setSupplierCost(String supplierCost) {
        this.supplierCost = supplierCost;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getIsGrouped() {
        return isGrouped;
    }

    public void setIsGrouped(int isGrouped) {
        this.isGrouped = isGrouped;
    }

    public List<Tax> getTax() {
        return tax;
    }

    public void setTax(List<Tax> tax) {
        this.tax = tax;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getHsncode() {
        return hsncode;
    }

    public void setHsncode(String hsncode) {
        this.hsncode = hsncode;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIsBillable() {
        return isBillable;
    }

    public void setIsBillable(String isBillable) {
        this.isBillable = isBillable;
    }

    public String getIsBillableChange() {
        return isBillableChange;
    }

    public void setIsBillableChange(String isBillableChange) {
        this.isBillableChange = isBillableChange;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public String getIlmmId() {
        return ilmmId;
    }

    public void setIlmmId(String ilmmId) {
        this.ilmmId = ilmmId;
    }


    public String getIsLabourParent() {
        return isLabourParent;
    }

    public void setIsLabourParent(String isLabourParent) {
        this.isLabourParent = isLabourParent;
    }

    public double getTaxamnt() {
        return taxamnt;
    }

    public void setTaxamnt(double taxamnt) {
        this.taxamnt = taxamnt;
    }

    public String getIsPartParent() {
        return isPartParent;
    }

    public void setIsPartParent(String isPartParent) {
        this.isPartParent = isPartParent;
    }

    public String getIsPartChild() {
        return isPartChild;
    }

    public void setIsPartChild(String isPartChild) {
        this.isPartChild = isPartChild;
    }

    public String getPartTempId() {
        return partTempId;
    }

    public void setPartTempId(String partTempId) {
        this.partTempId = partTempId;
    }

    public int getIsItemOrTitle() {
        return isItemOrTitle;
    }

    public void setIsItemOrTitle(int isItemOrTitle) {
        this.isItemOrTitle = isItemOrTitle;
    }
}