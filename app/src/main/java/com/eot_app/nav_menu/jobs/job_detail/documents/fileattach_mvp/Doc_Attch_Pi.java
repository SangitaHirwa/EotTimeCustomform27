package com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp;

/**
 * Created by ubuntu on 8/10/18.
 */

public interface Doc_Attch_Pi {
    void getAttachFileList(String jobId,String usrId,String type,boolean firstCall);

    void uploadDocuments(String job_Id, String file, String finalFname, String desc,String type,String isAddAttachAsCompletionNote);
    void uploadMultipleDocuments(String job_Id, String file, String finalFname, String desc,String type,String isAddAttachAsCompletionNote, boolean lastCall);


    void updateDocuments(String docId, String des, String rename,String isAddAttachAsCompletionNote, String jobId);
}

