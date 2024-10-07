package com.eot_app.utility.settings.equipmentdb;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;

import java.util.ArrayList;

@Entity(tableName = "Equipment")
public class Equipment {
    @PrimaryKey
    @NonNull
    private String equId;
    private String cltId;
    private String parentId;
    private String equnm;
    private String nm;
    private String mno;
    private String sno;
    private String brand;
    private String rate;
    private String supId;
    private String supplier;
    private String notes;
    private String expiryDate;
    private String manufactureDate;
    private String purchaseDate;
    private String barcode;
    private String isusable;
    private String barcodeImg;
    private String adr;
    private String city;
    private String state;
    private String ctry;
    private String zip;
    private String status;
    private String type;
    private String ecId;
    private String egId;
    private String ebId;
    private String isdelete;
    private String image;
    private String isDisable;
    private String lastAuditLabel;
    private String lastAuditDate;
    private String equStatusOnAudit;
    private String lastAuditId;
    private String lastJobLabel;
    private String lastJobDate;
    private String equStatusOnJob;
    private String lastJobId;
    private String groupName;
    private String extraField1;
    private String extraField2;
    private String installedDate;
    private String qrcode;
    private String qrcodeImg;
    private String servIntvalType;
    private String servIntvalValue;
    private String isPart;
    public  String statusUpdateDate = "";
    public  String equRemarkCondition = "";
    public  String siteId = "";
    @Ignore
    public  String equStatus = "";
    @Ignore
    public String remark = "";
    @Ignore
    private ArrayList<EquArrayModel> equComponent = new ArrayList<>();
    @Ignore
    private ArrayList<Attachments> attachments = new ArrayList<>();

    public String getIsPart() {
        return isPart;
    }

    public void setIsPart(String isPart) {
        this.isPart = isPart;
    }

    public String getServIntvalType() {
        return servIntvalType;
    }

    public void setServIntvalType(String servIntvalType) {
        this.servIntvalType = servIntvalType;
    }

    public String getServIntvalValue() {
        return servIntvalValue;
    }

    public void setServIntvalValue(String servIntvalValue) {
        this.servIntvalValue = servIntvalValue;
    }

    public String getInstalledDate() {
        return installedDate;
    }

    public void setInstalledDate(String installedDate) {
        this.installedDate = installedDate;
    }

    public String getExtraField1() {
        return extraField1;
    }

    public void setExtraField1(String extraField1) {
        this.extraField1 = extraField1;
    }

    public String getExtraField2() {
        return extraField2;
    }

    public void setExtraField2(String extraField2) {
        this.extraField2 = extraField2;
    }
    private String usrManualDoc;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getEquId() {
        return equId;
    }

    public void setEquId(String equId) {
        this.equId = equId;
    }

    public String getCltId() {
        return cltId;
    }

    public void setCltId(String cltId) {
        this.cltId = cltId;
    }

    public String getEqunm() {
        return equnm;
    }

    public void setEqunm(String equnm) {
        this.equnm = equnm;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getIsusable() {
        return isusable;
    }

    public void setIsusable(String isusable) {
        this.isusable = isusable;
    }

    public String getBarcodeImg() {
        return barcodeImg;
    }

    public void setBarcodeImg(String barcodeImg) {
        this.barcodeImg = barcodeImg;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCtry() {
        return ctry;
    }

    public void setCtry(String ctry) {
        this.ctry = ctry;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEcId() {
        return ecId;
    }

    public void setEcId(String ecId) {
        this.ecId = ecId;
    }

    public String getEgId() {
        return egId;
    }

    public void setEgId(String egId) {
        this.egId = egId;
    }

    public String getEbId() {
        return ebId;
    }

    public void setEbId(String ebId) {
        this.ebId = ebId;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(String isDisable) {
        this.isDisable = isDisable;
    }

    public String getLastAuditLabel() {
        return lastAuditLabel;
    }

    public void setLastAuditLabel(String lastAuditLabel) {
        this.lastAuditLabel = lastAuditLabel;
    }

    public String getLastAuditDate() {
        return lastAuditDate;
    }

    public void setLastAuditDate(String lastAuditDate) {
        this.lastAuditDate = lastAuditDate;
    }

    public String getEquStatusOnAudit() {
        return equStatusOnAudit;
    }

    public void setEquStatusOnAudit(String equStatusOnAudit) {
        this.equStatusOnAudit = equStatusOnAudit;
    }

    public String getLastAuditId() {
        return lastAuditId;
    }

    public void setLastAuditId(String lastAuditId) {
        this.lastAuditId = lastAuditId;
    }

    public String getLastJobLabel() {
        return lastJobLabel;
    }

    public void setLastJobLabel(String lastJobLabel) {
        this.lastJobLabel = lastJobLabel;
    }

    public String getLastJobDate() {
        return lastJobDate;
    }

    public void setLastJobDate(String lastJobDate) {
        this.lastJobDate = lastJobDate;
    }

    public String getEquStatusOnJob() {
        return equStatusOnJob;
    }

    public void setEquStatusOnJob(String equStatusOnJob) {
        this.equStatusOnJob = equStatusOnJob;
    }

    public String getLastJobId() {
        return lastJobId;
    }

    public void setLastJobId(String lastJobId) {
        this.lastJobId = lastJobId;
    }

    public String getUsrManualDoc() {
        return usrManualDoc;
    }

    public void setUsrManualDoc(String usrManualDoc) {
        this.usrManualDoc = usrManualDoc;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcodeImg() {
        return qrcodeImg;
    }

    public void setQrcodeImg(String qrcodeImg) {
        this.qrcodeImg = qrcodeImg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ArrayList<EquArrayModel> getEquComponent() {
        return equComponent;
    }

    public void setEquComponent(ArrayList<EquArrayModel> equComponent) {
        this.equComponent = equComponent;
    }

    public ArrayList<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachments> attachments) {
        this.attachments = attachments;
    }

    public String getStatusUpdateDate() {
        return statusUpdateDate;
    }

    public void setStatusUpdateDate(String statusUpdateDate) {
        this.statusUpdateDate = statusUpdateDate;
    }

    public String getEquRemarkCondition() {
        return equRemarkCondition;
    }

    public void setEquRemarkCondition(String equRemarkCondition) {
        this.equRemarkCondition = equRemarkCondition;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
//{"equId":"21896","parentId":"0","cltId":"25560","siteId":"25905","equnm":"0 aaa","mno":"","sno":"","brand":"","rate":"0.0000","supId":"0","supplier":"","notes":"","expiryDate":"","manufactureDate":"1718920766","purchaseDate":"1718920766","barcode":"500-122343-500","isusable":"1","barcodeImg":"uploads\/comp500\/equBarcode\/20240704120135_810.png","adr":"Indore","city":"","state":"21","ctry":"101","zip":"","status":"2446","type":"1","ecId":"106","egId":"0","ebId":"0","isdelete":"1","groupName":"","snm":"self","isPart":"0","extraField1":"","extraField2":"","usrManualDoc":"","installedDate":"1718920766","warrantyStartDate":"1718994599","servIntvalType":"0","servIntvalValue":"0","archive":"0","equCondition":"2442","equExpiryDate":"","parentNm":"","nm":"TestclientTest","isDisable":"0","subPartCount":"2","qrcode":"21896-361959-500","qrcodeImg":"uploads\/comp500\/equBarcode\/20240716122027_774.png","image":"assets\/img\/equipment_default.png","lastAssignUsr":[],"lastAuditLabel":"","lastAuditDate":"","equStatusOnAudit":"","lastAudit_id":"","serviceDuedate":"","lastJobLabel":"abc-282","lastJobDate":"1722859872","equStatusOnJob":"2442","lastJob_id":"37199"}