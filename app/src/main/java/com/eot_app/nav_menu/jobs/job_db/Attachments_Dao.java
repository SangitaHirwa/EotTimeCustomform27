package com.eot_app.nav_menu.jobs.job_db;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;

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
    @Query("delete from Attachments where attachmentId=:attachmentId")
    void deleteAttachmentById(String attachmentId);
    @Query("delete from Attachments where isdelete = '0'")
    void deleteAttachments();
}
