package com.eot_app.nav_menu.appointment.appointment_model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.TypeConverters;

import com.eot_app.nav_menu.appointment.details.documents.AppointmentTax;
import com.eot_app.nav_menu.appointment.details.documents.AppointmentTaxConverter;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_db.tax_dao.TaxConverter;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.Tax;

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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(des);
        dest.writeString(img);
        dest.writeString(inm);
        dest.writeString(pno);
        dest.writeString(qty);
        dest.writeInt(jtId);
        dest.writeString(rate);
        dest.writeString(unit);
        //dest.writeString(tax);
        dest.writeTypedList(tax);
        dest.writeInt(ilmmId);
        dest.writeString(itemId);
        dest.writeInt(leadId);
        dest.writeString(hsncode);
        dest.writeInt(orderNo);
        dest.writeString(taxamnt);
        dest.writeInt(dataType);
        dest.writeString(discount);
        dest.writeInt(itemType);
        dest.writeString(supplierCost);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getTaxamnt() {
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


}
