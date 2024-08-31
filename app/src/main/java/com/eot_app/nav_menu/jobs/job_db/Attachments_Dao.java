package com.eot_app.nav_menu.jobs.job_db;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Dao
public interface  Attachments_Dao {

    @Insert(onConflict = REPLACE)
    void insertAttachments(List<Attachments> attachmentsList);
    @Insert(onConflict = REPLACE)
    void insertSingleAttachments(Attachments attachments);
    @Query("select * from Attachments where jobId = :jobId and queId =:queId and jtId= :jtId")
    List<Attachments> getAttachmentsById(String jobId, String queId, String jtId);
    @Query("select * from Attachments where jobId = :jobId and type = '6'")
    List<Attachments> getAttachmentsByJobId(String jobId);
    @Query("select * from Attachments where jobId = :jobId")
    List<Attachments> getAllAttachmentsOfJob(String jobId);
    @Query("select * from Attachments where jobId = :jobId and queId = :queId")
    List<Attachments> getAttachmentsByQueId(String jobId, String queId);
    @Query("delete from Attachments where attachmentId=:tempId and tempId = :tempId")
    void deleteAttachmentById(String tempId);
    @Query("delete from Attachments where isdelete = '0'")
    void deleteAttachments();
    @Query("delete from Attachments")
    void deleteTable();
    @Query("UPDATE Attachments SET bitmap = :bitmap WHERE attachmentId =:attachmentId")
    void updateAttachment(String bitmap, String attachmentId);
    @Query("UPDATE Attachments SET image_name = :image_name , attachFileActualName = :attachFileActualName , bitmap = :bitmap, isUri = :isUri   WHERE tempId =:tempId")
    void updateAttachmentByTempId(String tempId, String image_name,  String attachFileActualName, String bitmap,boolean isUri);

    @Query("Select * from Attachments  WHERE tempId =:tempId")
    Attachments getAttachmetByTempId( String tempId);
    @Query("Select * from Attachments  WHERE attachmentId =:attachmentId")
    Attachments getAttachmetById( String attachmentId);

    @Query("Update Attachments SET attachmentId = :attachmentId  WHERE tempId =:tempId")
    void updateAttachmentIdByTempId( String attachmentId,String tempId);

    @Query("SELECT EXISTS(select * from Attachments where attachmentId = :attachmentId)")
    boolean isAttachment(String attachmentId);

    @Query("select bitmap from Attachments where attachmentId = :tempId")
    String getBitmap(String tempId);
}
