package com.eot_app.nav_menu.appointment.dbappointment;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import com.eot_app.nav_menu.appointment.AppointmentAttachmentConverter;
import com.eot_app.nav_menu.appointment.AppointmetItemDataModelConverter;
import com.eot_app.nav_menu.appointment.Keepar;
import com.eot_app.nav_menu.appointment.KeeparConverter;
import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;
import com.eot_app.nav_menu.appointment.details.AppointmentAttachment;
import com.eot_app.nav_menu.appointment.details.documents.AppointmentTaxConverter;
import com.eot_app.nav_menu.equipment.model.Keeper;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.typeconver_pkg.InvoiceItemDataModelConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = "Appointment")
public class Appointment implements Parcelable  {

    private String tempId;
    @PrimaryKey
    @NonNull
    private String appId;
    private String cltId;
    private String label;
    private String des;
    private String type;
    @TypeConverters(KeeparConverter.class)
    private List<Keepar> kpr;
    private String athr;
    private String schdlStart;
    private String schdlFinish;
    private String nm;
    private String email;
    private String mob1;
    private String mob2;
    private String adr;
    private String city;
    private String state;
    private String ctry;
    private String zip;
    private String createDate;
    private String updateDate;
    private String siteId;
    private String conId;
    private String compid;
    private String lat;
    private String lng;
    private String landmark;
    private String status;
    private String isdelete;
    private String cnm;
    private String snm;
    private String quotId;
    private String quotLabel;
    private String jobId;
    private String jobLabel;
    private String attachCount;
    private String parentId;
    private String leadId;
    private String isStatusShow;
    @TypeConverters(AppointmetItemDataModelConverter.class)
    private List<AppointmentItemDataInMap> itemData;
    private String taxCalculationType;
    private String disCalculationType;
   /* @TypeConverters(AppointmentAttachmentConverter.class)
    private List<AppointmentAttachment> attachments;*/
    /*@TypeConverters(AppointmetItemDataModelConverter.class)
    private Map<String ,AppintmentItemDataModel> itemData;*/
    // private String itemData;

    public Appointment() {
        kpr = null;
    }

    protected Appointment(Parcel in) {
        tempId = in.readString();
        appId = in.readString();
        cltId = in.readString();
        label = in.readString();
        des = in.readString();
        type = in.readString();
        kpr= new ArrayList<>();
        in.readList(kpr,Keepar.class.getClassLoader());
        athr = in.readString();
        schdlStart = in.readString();
        schdlFinish = in.readString();
        nm = in.readString();
        email = in.readString();
        mob1 = in.readString();
        mob2 = in.readString();
        adr = in.readString();
        city = in.readString();
        state = in.readString();
        ctry = in.readString();
        zip = in.readString();
        createDate = in.readString();
        updateDate = in.readString();
        siteId = in.readString();
        conId = in.readString();
        compid = in.readString();
        lat = in.readString();
        lng = in.readString();
        landmark = in.readString();
        status = in.readString();
        isdelete = in.readString();
        cnm = in.readString();
        snm = in.readString();
        quotId = in.readString();
        quotLabel = in.readString();
        jobId = in.readString();
        jobLabel = in.readString();
        attachCount = in.readString();
        parentId = in.readString();
        leadId = in.readString();
        isStatusShow = in.readString();
        itemData = new ArrayList<>();
        in.readList(itemData, AppointmentItemDataInMap.class.getClassLoader());
        taxCalculationType = in.readString();
        disCalculationType = in.readString();
       /* attachments = new ArrayList<>();
        in.readList(attachments,AppointmentAttachment.class.getClassLoader());*/
    }
     /*   itemData = new HashMap<>();
        in.readMap(itemData, AppintmentItemDataModel.class.getClassLoader());*/



    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    public String getAttachCount() {
        return attachCount;
    }

    public void setAttachCount(String attachCount) {
        this.attachCount = attachCount;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobLabel() {
        return jobLabel;
    }

    public void setJobLabel(String jobLabel) {
        this.jobLabel = jobLabel;
    }

    public String getQuotId() {
        return quotId;
    }

    public void setQuotId(String quotId) {
        this.quotId = quotId;
    }

    public String getQuotLabel() {
        return quotLabel;
    }

    public void setQuotLabel(String quotLabel) {
        this.quotLabel = quotLabel;
    }

    public String getSnm() {
        return snm;
    }

    public void setSnm(String snm) {
        this.snm = snm;
    }

    public String getCnm() {
        return cnm;
    }

    public void setCnm(String cnm) {
        this.cnm = cnm;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }


    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCltId() {
        return cltId;
    }

    public void setCltId(String cltId) {
        this.cltId = cltId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Keepar> getKpr() {
        return kpr;
    }

    public void setKpr(List<Keepar> kpr) {
        this.kpr = kpr;
    }

    public String getAthr() {
        return athr;
    }

    public void setAthr(String athr) {
        this.athr = athr;
    }

    public String getSchdlStart() {
        return schdlStart;
    }

    public void setSchdlStart(String schdlStart) {
        this.schdlStart = schdlStart;
    }

    public String getSchdlFinish() {
        return schdlFinish;
    }

    public void setSchdlFinish(String schdlFinish) {
        this.schdlFinish = schdlFinish;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMob1() {
        return mob1;
    }

    public void setMob1(String mob1) {
        this.mob1 = mob1;
    }

    public String getMob2() {
        return mob2;
    }

    public void setMob2(String mob2) {
        this.mob2 = mob2;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getCompid() {
        return compid;
    }

    public void setCompid(String compid) {
        this.compid = compid;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getIsStatusShow() {
        return isStatusShow;
    }

    public void setIsStatusShow(String isStatusShow) {
        this.isStatusShow = isStatusShow;
    }

    public List<AppointmentItemDataInMap> getItemData() {
        return itemData;
    }

    public void setItemData(List<AppointmentItemDataInMap> itemData) {
        this.itemData = itemData;
    }

    public String getTaxCalculationType() {
        return taxCalculationType;
    }

    public void setTaxCalculationType(String taxCalculationType) {
        this.taxCalculationType = taxCalculationType;
    }

    public String getDisCalculationType() {
        return disCalculationType;
    }

    public void setDisCalculationType(String disCalculationType) {
        this.disCalculationType = disCalculationType;
    }

    /*  public List<AppointmentAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AppointmentAttachment> attachments) {
        this.attachments = attachments;
    }*/

    /*   public Map<String, AppintmentItemDataModel> getItemData() {
        return itemData;
    }

    public void setItemData(Map<String, AppintmentItemDataModel> itemData) {
        this.itemData = itemData;
    }
    public AppintmentItemDataModel getAppintmentItemDataModel(String key) {
        return itemData.get(key);
    }*/

    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<Appointment> CREATORS = new Parcelable.Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };
    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeString(tempId);
        parcel.writeString(appId);
        parcel.writeString(cltId);
        parcel.writeString(label);
        parcel.writeString(des);
        parcel.writeString(type);
        parcel.writeList(kpr);
        parcel.writeString(athr);
        parcel.writeString(schdlStart);
        parcel.writeString(schdlFinish);
        parcel.writeString(nm);
        parcel.writeString(email);
        parcel.writeString(mob1);
        parcel.writeString(mob2);
        parcel.writeString(adr);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(ctry);
        parcel.writeString(zip);
        parcel.writeString(createDate);
        parcel.writeString(updateDate);
        parcel.writeString(siteId);
        parcel.writeString(conId);
        parcel.writeString(compid);
        parcel.writeString(lat);
        parcel.writeString(lng);
        parcel.writeString(landmark);
        parcel.writeString(status);
        parcel.writeString(isdelete);
        parcel.writeString(cnm);
        parcel.writeString(snm);
        parcel.writeString(quotId);
        parcel.writeString(quotLabel);
        parcel.writeString(jobId);
        parcel.writeString(jobLabel);
        parcel.writeString(attachCount);
        parcel.writeString(parentId);
        parcel.writeString(leadId);
        parcel.writeString(isStatusShow);
        parcel.writeList(itemData);
        parcel.writeString(taxCalculationType);
        parcel.writeString(disCalculationType);
       /* parcel.writeList(attachments);*/
        /*   parcel.writeMap(itemData);*/
    }

  /*public String getItemData() {
        return itemData;
    }

    public void setItemData(String itemData) {
        this.itemData = itemData;
    }*/
}