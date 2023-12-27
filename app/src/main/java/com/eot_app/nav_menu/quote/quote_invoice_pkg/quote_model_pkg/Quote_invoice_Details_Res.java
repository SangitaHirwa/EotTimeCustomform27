package com.eot_app.nav_menu.quote.quote_invoice_pkg.quote_model_pkg;

import android.os.Parcel;
import android.os.Parcelable;

import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.ShippingItem;

import java.util.ArrayList;
import java.util.List;

public class Quote_invoice_Details_Res implements Parcelable {


    public static final Creator<Quote_invoice_Details_Res> CREATOR = new Creator<Quote_invoice_Details_Res>() {
        @Override
        public Quote_invoice_Details_Res createFromParcel(Parcel in) {
            return new Quote_invoice_Details_Res(in);
        }

        @Override
        public Quote_invoice_Details_Res[] newArray(int size) {
            return new Quote_invoice_Details_Res[size];
        }
    };
//    private String invId;
    private String parentId;
    private String compId;
    private String cltId;
    private String quotId;

    /*Remove Code,Createdate,Paid, invType variable after discuss with Rani Yadav for 2.92 release on 07 Dec 2023*/
//    private String code;

    /*Remove nm variable after discuss with Rani Yadav for 2.92 release on 26 Dec 2023*/
   /* private String nm;*/
    private String adr;
    private String pro;
    private String discount;
    private String total;
    private String note;
    private String pono;
    private String invDate;
    private String duedate;
//    private String createdate;
    private String label;
//    private String paid;
    private String cur;
//    private String invType;
    private String taxCalculationType;
    private String statusComment;

    public String getCommets() {
        return statusComment;
    }

    public void setCommets(String statusComment) {
        this.statusComment = statusComment;
    }

    private List<Quote_ItemData> itemData = new ArrayList<>();
    private List<ShippingItem> shippingItem = new ArrayList<>();
    private String isAddisDiscBefore;
    private String disCalculationType;
    public Quote_invoice_Details_Res() {

    }
    /*Remove invId variable after discuss with Rani Yadav for 2.92 release on 26 Dec 2023*/
    protected Quote_invoice_Details_Res(Parcel in) {
//        invId = in.readString();
        parentId = in.readString();
        compId = in.readString();
        cltId = in.readString();
        quotId = in.readString();
//        code = in.readString();
       // nm = in.readString();
        adr = in.readString();
        pro = in.readString();
        discount = in.readString();
        total = in.readString();
        note = in.readString();
        pono = in.readString();
        invDate = in.readString();
        duedate = in.readString();
//        createdate = in.readString();
        label = in.readString();
//        paid = in.readString();
        cur = in.readString();
//        invType = in.readString();
        taxCalculationType = in.readString();
        itemData = in.createTypedArrayList(Quote_ItemData.CREATOR);
        shippingItem = in.createTypedArrayList(ShippingItem.CREATOR);
        statusComment =  in.readString();
        isAddisDiscBefore =  in.readString();
        disCalculationType =  in.readString();
    }

    public static Creator<Quote_invoice_Details_Res> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(invId);
        dest.writeString(parentId);
        dest.writeString(compId);
        dest.writeString(cltId);
        dest.writeString(quotId);
//        dest.writeString(code);
      //  dest.writeString(nm);
        dest.writeString(adr);
        dest.writeString(pro);
        dest.writeString(discount);
        dest.writeString(total);
        dest.writeString(note);
        dest.writeString(pono);
        dest.writeString(invDate);
        dest.writeString(duedate);
//        dest.writeString(createdate);
        dest.writeString(label);
//        dest.writeString(paid);
        dest.writeString(cur);
//        dest.writeString(invType);
        dest.writeString(taxCalculationType);
        dest.writeTypedList(itemData);
        dest.writeTypedList(shippingItem);
        dest.writeString(statusComment);
        dest.writeString(isAddisDiscBefore);
        dest.writeString(disCalculationType);
    }

 /*   public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }*/

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCltId() {
        return cltId;
    }

    public void setCltId(String cltId) {
        this.cltId = cltId;
    }

    public String getQuotId() {
        return quotId;
    }

    public void setQuotId(String quotId) {
        this.quotId = quotId;
    }

    /*Remove Code,Createdate,Paid, invType variable after discuss with Rani Yadav for 2.92 release on 07 Dec 2023*/
   /* public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }*/

    /*Remove nm variable after discuss with Rani Yadav for 2.92 release on 26 Dec 2023*/
  /*  public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }*/

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono;
    }

    public String getInvDate() {
        return invDate;
    }

    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    /*Remove Code,Createdate,Paid, invType variable after discuss with Rani Yadav for 2.92 release on 07 Dec 2023*/
   /* public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }*/

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /*Remove Code,Createdate,Paid, invType variable after discuss with Rani Yadav for 2.92 release on 07 Dec 2023*/
   /* public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }*/

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    /*Remove Code,Createdate,Paid, invType variable after discuss with Rani Yadav for 2.92 release on 07 Dec 2023*/
    /*public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }*/

    public String getTaxCalculationType() {
        return taxCalculationType;
    }

    public void setTaxCalculationType(String taxCalculationType) {
        this.taxCalculationType = taxCalculationType;
    }

    public List<Quote_ItemData> getItemData() {
        return itemData;
    }

    public void setItemData(List<Quote_ItemData> itemData) {
        this.itemData = itemData;
    }

    public List<ShippingItem> getShippingItem() {
        return shippingItem;
    }

    public void setShippingItem(List<ShippingItem> shippingItem) {
        this.shippingItem = shippingItem;
    }

    public String getIsAddisDiscBefore() {
        return isAddisDiscBefore;
    }

    public void setIsAddisDiscBefore(String isAddisDiscBefore) {
        this.isAddisDiscBefore = isAddisDiscBefore;
    }

    public String getDisCalculationType() {
        return disCalculationType;
    }

    public void setDisCalculationType(String disCalculationType) {
        this.disCalculationType = disCalculationType;
    }
}
