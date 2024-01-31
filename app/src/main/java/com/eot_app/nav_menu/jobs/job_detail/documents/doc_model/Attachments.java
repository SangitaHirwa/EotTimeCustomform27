package com.eot_app.nav_menu.jobs.job_detail.documents.doc_model;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


/**
 * Created by ubuntu on 9/10/18.
 */
@Entity(indices = {@Index(value = "attachmentId", unique = true)})
public class Attachments implements Parcelable {

    @SerializedName("isLinked")
    private String isLinked;
    @SerializedName("attchParentId")
    private String attchParentId;
    @SerializedName("size")
    private int size;
    @SerializedName("attchOriginId")
    private String attchOriginId;
    @SerializedName("des")
    private String des;
    @SerializedName("att_docName")
    private String att_docName;
    @SerializedName("name")
    private String name;
    @SerializedName("attFolderNm")
    private String attFolderNm;
    @SerializedName("createdate")
    private String createdate;
    @SerializedName("type")
    private String type;
    @SerializedName("attachFileActualName")
    private String attachFileActualName;
    @SerializedName("attachThumnailFileName")
    private String attachThumnailFileName;
    @SerializedName("attachFileName")
    private String attachFileName;
    @SerializedName("userId")
    private String userId;
    @SerializedName("image_name")
    private String image_name;
    @SerializedName("deleteTable")
    private String deleteTable;
    @PrimaryKey
    @NonNull
    @SerializedName("attachmentId")
    private String attachmentId;
    @SerializedName("isFeedback")
    private String isFeedback;
    @SerializedName("jobId")
    private String jobId;
    @SerializedName("isdelete")
    private String isdelete;
    @Ignore
    private String complNote;
    String  bitmap="";
    @Ignore
    Bitmap bitmap1;
    @SerializedName("queId")
    private String queId;
    @SerializedName("jtId")
    private String jtId;

    //    private String attachmentId;
//    private String deleteTable;
//    private String userId;
//    private String image_name;
//    private String attachFileName;
//    private String des;
//    private String attachThumnailFileName;
//    private String attachFileActualName;
//    private String type;
//    private String createdate;

//    String att_docName;
//
//    @SerializedName("isLinked")
//    private String isLinked;
//    @SerializedName("attchParentId")
//    private String attchParentId;
//    @SerializedName("size")
//    private int size;
//    @SerializedName("attchOriginId")
//    private String attchOriginId;
//    @SerializedName("name")
//    private String name;
//    @SerializedName("attFolderNm")
//    private String attFolderNm;


    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

    public Attachments() {
    }

    public Attachments(String attachmentId, String deleteTable, String userId, String attachFileName, String attachFileActualName, String type, String createdate, String complNote) {
        this.attachmentId = attachmentId;
        this.deleteTable = deleteTable;
        this.userId = userId;
        this.attachFileName = attachFileName;
        this.attachFileActualName = attachFileActualName;
        this.type = type;
        this.createdate = createdate;
        this.complNote = complNote;
    }
    public Attachments(@NonNull String attachmentId, String imageName, String attachFileActualName, String attachThumnailFileName, String queId, String jtId, String des, String jobId, String type) {
        this.attachmentId = attachmentId;
        this.image_name = imageName;
        this.attachFileActualName = attachFileActualName;
        this.attachThumnailFileName = attachThumnailFileName;
        this.queId = queId;
        this.jtId = jtId;
        this.des = des;
        this.jobId = jobId;
        this.type = type;
    }
    public Attachments(@NonNull String attachmentId, String imageName, String attachFileActualName, String attachThumnailFileName, String queId, String jtId, String des, String jobId, String type,String bitmap) {
        this.attachmentId = attachmentId;
        this.image_name = imageName;
        this.attachFileActualName = attachFileActualName;
        this.attachThumnailFileName = attachThumnailFileName;
        this.queId = queId;
        this.jtId = jtId;
        this.des = des;
        this.jobId = jobId;
        this.type = type;
        this.bitmap = bitmap;
    }
    public Attachments(String attachmentId, String image_name, String attachFileActualName, String bitmap) {
        this.attachmentId = attachmentId;
        this.image_name = image_name;
        this.attachFileActualName = attachFileActualName;
        this.bitmap = bitmap;
    }
    public Attachments(String attachmentId, String image_name, String attachFileActualName, Bitmap bitmap) {
        this.attachmentId = attachmentId;
        this.image_name = image_name;
        this.attachFileActualName = attachFileActualName;
        this.bitmap1 = bitmap;
    }
    @SuppressLint("NewApi")
    protected Attachments(Parcel in) {
        attachmentId = in.readString();
        deleteTable = in.readString();
        userId = in.readString();
        image_name = in.readString();
        attachFileName = in.readString();
        des = in.readString();
        attachThumnailFileName = in.readString();
        attachFileActualName = in.readString();
        type = in.readString();
        createdate = in.readString();
        complNote = in.readString();
        bitmap = in.readString();
        att_docName = in.readString();
    }

    public static final Creator<Attachments> CREATOR = new Creator<Attachments>() {
        @Override
        public Attachments createFromParcel(Parcel in) {
            return new Attachments(in);
        }

        @Override
        public Attachments[] newArray(int size) {
            return new Attachments[size];
        }
    };

    public String getAttachThumnailFileName() {
        return attachThumnailFileName;
    }

    public void setAttachThumnailFileName(String attachThumnailFileName) {
        this.attachThumnailFileName = attachThumnailFileName;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public Attachments(String attachFileActualName) {
        this.attachFileActualName = attachFileActualName;
    }


    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getDeleteTable() {
        return deleteTable;
    }

    public void setDeleteTable(String deleteTable) {
        this.deleteTable = deleteTable;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAttachFileName() {
        return attachFileName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setAttachFileName(String attachFileName) {
        this.attachFileName = attachFileName;
    }

    public String getAttachFileActualName() {
        return attachFileActualName;
    }

    public void setAttachFileActualName(String attachFileActualName) {
        this.attachFileActualName = attachFileActualName;
    }

    public String getType() {
        return type;
    }

    public String getIsLinked() {
        return isLinked;
    }

    public void setIsLinked(String isLinked) {
        this.isLinked = isLinked;
    }

    public String getAttchParentId() {
        return attchParentId;
    }

    public void setAttchParentId(String attchParentId) {
        this.attchParentId = attchParentId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAttchOriginId() {
        return attchOriginId;
    }

    public void setAttchOriginId(String attchOriginId) {
        this.attchOriginId = attchOriginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttFolderNm() {
        return attFolderNm;
    }

    public void setAttFolderNm(String attFolderNm) {
        this.attFolderNm = attFolderNm;
    }

    public void setComplNote(String complNote) {
        this.complNote = complNote;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getComplNote() {
        return complNote;
    }

    public String getAtt_docName() {
        return att_docName;
    }

    public void setAtt_docName(String att_docName) {
        this.att_docName = att_docName;
    }

    public String getIsFeedback() {
        return isFeedback;
    }

    public void setIsFeedback(String isFeedback) {
        this.isFeedback = isFeedback;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }

    public String getJtId() {
        return jtId;
    }

    public void setJtId(String jtId) {
        this.jtId = jtId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressLint("NewApi")
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(attachmentId);
        parcel.writeString(deleteTable);
        parcel.writeString(userId);
        parcel.writeString(image_name);
        parcel.writeString(attachFileName);
        parcel.writeString(des);
        parcel.writeString(attachThumnailFileName);
        parcel.writeString(attachFileActualName);
        parcel.writeString(type);
        parcel.writeString(createdate);
        parcel.writeString(complNote);
        parcel.writeString(bitmap);
        parcel.writeString(att_docName);
    }

    public Bitmap getBitmap1() {
        return bitmap1;
    }

    public void setBitmap1(Bitmap bitmap1) {
        this.bitmap1 = bitmap1;
    }
}
