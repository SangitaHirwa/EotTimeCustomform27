package com.eot_app.nav_menu.appointment.appointment_model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.TypeConverters;

import com.eot_app.nav_menu.appointment.details.documents.AppointmentTax;
import com.eot_app.nav_menu.appointment.details.documents.AppointmentTaxConverter;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_db.tax_dao.TaxConverter;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.Tax;

import java.io.Serializable;
import java.util.List;

public class AppintmentItemDataModel implements Parcelable {

    private String des;
    private String img;
    private String inm;
    private String pno;
    private String qty;
    private int jtId;
    private String rate;
    private String unit;
    //private String tax;
    @TypeConverters(AppointmentTaxConverter.class)
    private List<AppointmentTax> tax;
    private int ilmmId;
    private String itemId;
    private int leadId;
    private String hsncode;
    private int orderNo;
    private String taxamnt;
    private int dataType;
    private String discount;
    private int itemType;
    private String supplierCost;
    private String isBillable;
    private String isBillableChange;
    private String tempId;
    private String isLabourChild;
    private String labourTempId;
    private String isLabourParent;
    private String isPartParent;
    private String isPartChild;
    private String partTempId;
    private int isItemOrTitle;
    private String serialNo;
    private int isGrouped;
    public AppintmentItemDataModel(){

    }
    public AppintmentItemDataModel(String des, String img, String inm, String pno, String qty,
                                   String rate, String unit, List<AppointmentTax> tax,
                                   String itemId,  String hsncode, String taxamnt,
                                   int dataType, String discount, int itemType,
                                   String supplierCost, String isBillable, String isBillableChange,
                                   String tempId, String isPartParent, String isPartChild,
                                   String partTempId, int isItemOrTitle, String serialNo, int isGrouped) {
        this.des = des;
        this.img = img;
        this.inm = inm;
        this.pno = pno;
        this.qty = qty;
        this.rate = rate;
        this.unit = unit;
        this.tax = tax;
        this.itemId = itemId;
        this.hsncode = hsncode;
        this.taxamnt = taxamnt;
        this.dataType = dataType;
        this.discount = discount;
        this.itemType = itemType;
        this.supplierCost = supplierCost;
        this.isBillable = isBillable;
        this.isBillableChange = isBillableChange;
        this.tempId = tempId;
        this.isPartParent = isPartParent;
        this.isPartChild = isPartChild;
        this.partTempId = partTempId;
        this.isItemOrTitle = isItemOrTitle;
        this.serialNo = serialNo;
        this.isGrouped = isGrouped;
    }

    public static final Creator<AppintmentItemDataModel> CREATOR = new Creator<AppintmentItemDataModel>() {
        @Override
        public AppintmentItemDataModel createFromParcel(Parcel in) {
            return new AppintmentItemDataModel(in);
        }

        @Override
        public AppintmentItemDataModel[] newArray(int size) {
            return new AppintmentItemDataModel[size];
        }
    };


        protected AppintmentItemDataModel(Parcel in) {
            des = in.readString();
            img = in.readString();
            inm = in.readString();
            pno = in.readString();
            qty = in.readString();
            jtId = in.readInt();
            rate = in.readString();
            unit = in.readString();
            //tax = in.readString();
            tax = in.createTypedArrayList(AppointmentTax.CREATOR);
            ilmmId = in.readInt();
            itemId = in.readString();
            leadId = in.readInt();
            hsncode = in.readString();
            orderNo = in.readInt();
            taxamnt = in.readString();
            dataType = in.readInt();
            discount = in.readString();
            itemType = in.readInt();
            supplierCost = in.readString();
    }


    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInm() {
        return inm;
    }

    public void setInm(String inm) {
        this.inm = inm;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public int getJtId() {
        return jtId;
    }

    public void setJtId(int jtId) {
        this.jtId = jtId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<AppointmentTax> getTax() {
        return tax;
    }

    public void setTax(List<AppointmentTax> tax) {
        this.tax = tax;
    }

  /*  public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }*/

    public int getIlmmId() {
        return ilmmId;
    }

    public void setIlmmId(int ilmmId) {
        this.ilmmId = ilmmId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getLeadId() {
        return leadId;
    }

    public void setLeadId(int leadId) {
        this.leadId = leadId;
    }

    public String getHsncode() {
        return hsncode;
    }

    public void setHsncode(String hsncode) {
        this.hsncode = hsncode;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String  getTaxamnt() {
        return taxamnt;
    }

    public void setTaxamnt(String taxamnt) {
        this.taxamnt = taxamnt;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
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

    public String getIsLabourChild() {
        return isLabourChild;
    }

    public void setIsLabourChild(String isLabourChild) {
        this.isLabourChild = isLabourChild;
    }

    public String getLabourTempId() {
        return labourTempId;
    }

    public void setLabourTempId(String labourTempId) {
        this.labourTempId = labourTempId;
    }

    public String getIsLabourParent() {
        return isLabourParent;
    }

    public void setIsLabourParent(String isLabourParent) {
        this.isLabourParent = isLabourParent;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(des);
        parcel.writeString(img);
        parcel.writeString(inm);
        parcel.writeString(pno);
        parcel.writeString(qty);
        parcel.writeInt(jtId);
        parcel.writeString(rate);
        parcel.writeString(unit);
        parcel.writeTypedList(tax);
        parcel.writeInt(ilmmId);
        parcel.writeString(itemId);
        parcel.writeInt(leadId);
        parcel.writeString(hsncode);
        parcel.writeInt(orderNo);
        parcel.writeString(taxamnt);
        parcel.writeInt(dataType);
        parcel.writeString(discount);
        parcel.writeInt(itemType);
        parcel.writeString(supplierCost);
    }
}
