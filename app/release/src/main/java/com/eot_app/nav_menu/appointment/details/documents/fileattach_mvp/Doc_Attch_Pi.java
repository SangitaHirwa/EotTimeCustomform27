package com.eot_app.nav_menu.appointment.details.documents.fileattach_mvp;

/**
 * Created by ubuntu on 8/10/18.
 */

public interface Doc_Attch_Pi {
    void getAttachFileList(String jobId);

    void uploadDocuments(String job_Id, String file, String finalFname, String desc);

    void updateDocuments(String docId, String des);
}
