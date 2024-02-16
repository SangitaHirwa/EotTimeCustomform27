package com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp;

import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;

import java.util.ArrayList;

/**
 * Created by ubuntu on 8/10/18.
 */

public interface Doc_Attch_View {
    void selectFiles();
    void selectFilesForCompletion(boolean isCompletion);

    void setList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes, boolean firstCall);
    void setMultiList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes, boolean firstCall, int parentPosition, int position, String queId, String jtId);
    void addNewItemToAttachmentList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes);

    void addView();

    void onSessionExpire(String msg);

    void fileExtensionNotSupport(String msg);

    void onDocumentUpdate(String msg, boolean isSuccess);

    void hideProgressBar();
    void showProgressBar();
    void addDocumentInQuote(boolean attechmentUpload);

    //   void setUploadNewDocList(ArrayList<Attachments> getFileList_res);
}
