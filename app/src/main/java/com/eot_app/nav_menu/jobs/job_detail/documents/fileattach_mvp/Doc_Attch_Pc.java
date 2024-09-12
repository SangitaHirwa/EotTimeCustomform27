package com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.eot_app.activitylog.ActivityLogController;
import com.eot_app.nav_menu.jobs.job_db.Attachments_Dao;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_db.JobListRequestModel;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.DocUpdateRequest;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.GetFileList_req_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.Get_Email_ReS_Model;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by ubuntu on 8/10/18.
 */

public class Doc_Attch_Pc implements Doc_Attch_Pi {
    private final int updatelimit;
    private final String CHECK_IN_TYPE = "1";
    private final String CHECK_OUT_TYPE = "2";
    Doc_Attch_View doc_attch_view;
    private int count;
    private int updateindex;
    private String startAttachmetSyncTime;

    public Doc_Attch_Pc(Doc_Attch_View doc_attch_view) {
        this.doc_attch_view = doc_attch_view;
        this.updateindex = 0;
        this.updatelimit = AppConstant.LIMIT_MID;
    }


    @Override
    public void getAttachFileList(final String jobId, final String usrId, final String type, boolean firstCall, boolean isFromAttachment) {

        GetFileList_req_Model getFileList_model ;
        if(isFromAttachment){
            getFileList_model = new GetFileList_req_Model(updateindex, updatelimit, jobId, usrId, type,"1");
        }else {
            getFileList_model = new GetFileList_req_Model(updateindex, updatelimit, jobId, usrId, type);
        }
        JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(getFileList_model));

        if (AppUtility.isInternetConnected()) {


            if (!jobId.contains("Job")) {
                ActivityLogController.saveActivity(ActivityLogController.JOB_MODULE, ActivityLogController.JOB_DOCUMENT_LIST, ActivityLogController.JOB_MODULE);
                ApiClient.getservices().eotServiceCall(Service_apis.getJobAttachments, AppUtility.getApiHeaders(), jsonObject)

                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JsonObject>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                Log.e("FileList", "" + jsonObject.toString());
                                if (jsonObject.get("success").getAsBoolean()) {
                                    if (jsonObject.get("data").getAsJsonArray().size() > 0) {
                                        count = jsonObject.get("count").getAsInt();
                                        String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                        Type listType = new TypeToken<List<Attachments>>() {
                                        }.getType();
                                        ArrayList<Attachments> getFileList_res = new Gson().fromJson(convert, listType);
//                                        addAttachmentToDb(getFileList_res,jobId,firstCall,type,-1,-1,"","",true);
                                        doc_attch_view.setList(getFileList_res, "",firstCall,true);
                                    } else {
//                                        doc_attch_view.addView();
//                                        addAttachmentToDb(new ArrayList<>(),jobId,firstCall,type,-1,-1,"","",true);
                                        doc_attch_view.setList(new ArrayList<Attachments>(), "",firstCall,true);
                                    }
                                } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                    doc_attch_view.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("", e.getMessage());
                                // AppUtility.progressBarDissMiss();
                                doc_attch_view.hideProgressBar();
                            }

                            @Override
                            public void onComplete() {
                                doc_attch_view.hideProgressBar();
                                Log.e("onComplete", "onComplete");
                                //  AppUtility.progressBarDissMiss();
                                if ((updateindex + updatelimit) <= count) {
                                    updateindex += updatelimit;
                                    getAttachFileList(jobId, usrId, type,false, isFromAttachment);
                                }else {
                                    updateindex = 0;
                                }
                            }
                        });
            } else {
                //TODO
            }

        } else {
            doc_attch_view.setList(new ArrayList<>(), "",firstCall,false);
        }

    }
 @Override
    public void getMultiAttachFileList(final String jobId, final String usrId, final String type, boolean firstCall, int parentPosition, int position,String queId, String jtId, boolean isRefreshFromApi, boolean isFromAttachment) {
     startAttachmetSyncTime=AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
     App_preference.getSharedprefInstance().setAttachmentStartSyncTime(startAttachmetSyncTime);
     JobListRequestModel jobListRequestModel = new JobListRequestModel(Integer.parseInt(usrId), updatelimit, updateindex, App_preference.getSharedprefInstance().getAttachmentStartSyncTime(), jobId);
        JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(jobListRequestModel));

        if (AppUtility.isInternetConnected() && isRefreshFromApi) {


            
                ActivityLogController.saveActivity(ActivityLogController.JOB_MODULE, ActivityLogController.JOB_DOCUMENT_LIST, ActivityLogController.JOB_MODULE);
                ApiClient.getservices().eotServiceCall(Service_apis.getSyncJobAttachments, AppUtility.getApiHeaders(), jsonObject)

                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JsonObject>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(JsonObject jsonObject) {
                                Log.e("FileList", "" + jsonObject.toString());
                                if (jsonObject.get("success").getAsBoolean()) {
                                    if (jsonObject.get("data").getAsJsonArray().size() > 0) {
                                        count = jsonObject.get("count").getAsInt();
                                        String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                        Type listType = new TypeToken<List<Attachments>>() {
                                        }.getType();
                                        List<Attachments> attachments = new Gson().fromJson(convert, listType);
//                                        doc_attch_view.setMultiList(attachments, type,firstCall, parentPosition, position,queId, jtId);
                                        addAttachmentToDb(attachments,jobId,firstCall,type,parentPosition,position,queId,jtId,isFromAttachment);
                                    } else {
//                                        doc_attch_view.addView();
//                                        doc_attch_view.setMultiList(new ArrayList<Attachments>(), type,firstCall, parentPosition, position, queId, jtId);
                                        addAttachmentToDb(new ArrayList<>(),jobId,firstCall,type,parentPosition,position,queId,jtId,isFromAttachment);
                                    }
                                } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                    doc_attch_view.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("", e.getMessage());
                                // AppUtility.progressBarDissMiss();
                                doc_attch_view.hideProgressBar();
                            }

                            @Override
                            public void onComplete() {
                                doc_attch_view.hideProgressBar();
                                Log.e("onComplete", "onComplete");
                                //  AppUtility.progressBarDissMiss();
                                if ((updateindex + updatelimit) <= count) {
                                    updateindex += updatelimit;
                                    getMultiAttachFileList(jobId, usrId, type,false,parentPosition,position,queId, jtId,true,isFromAttachment);
                                }else {
                                    if (count != 0) {
                                        if(App_preference.getSharedprefInstance().getAttachmentStartSyncTime().isEmpty()
                                                &&startAttachmetSyncTime!=null && !startAttachmetSyncTime.isEmpty()){
                                            App_preference.getSharedprefInstance().setAttachmentStartSyncTime(startAttachmetSyncTime);
                                            Log.v("MainSync","startJobSyncTime JobList"+" --" +App_preference.getSharedprefInstance().getJobSyncTime());
                                        }
                                        else if(App_preference.getSharedprefInstance().getAttachmentStartSyncTime().isEmpty()){

                                            App_preference.getSharedprefInstance().setAttachmentStartSyncTime(startAttachmetSyncTime);
                                            Log.v("MainSync","startJobSyncTime JobList"+" --" +App_preference.getSharedprefInstance().getJobSyncTime());
                                        }
                                        else {
                                            App_preference.getSharedprefInstance().setAttachmentStartSyncTime(App_preference.getSharedprefInstance().getAttachmentStartSyncTime());
                                        }

                                    }
                                    updateindex = 0;
                                    getAttachFileList(jobId,usrId,type,true, isFromAttachment);
                                }
                            }
                        });

        } else {
            if(!AppUtility.isInternetConnected()) {
                networkDialog();
            }
            getAttachFileList(jobId,usrId,type,true, isFromAttachment);
            addAttachmentToDb(new ArrayList<>(),jobId,firstCall,type,parentPosition,position,queId,jtId,isFromAttachment);
        }

    }

    @Override
    public void uploadMultipleDocuments(String job_Id, String file, String finalFname, String desc, String type, String isAddAttachAsCompletionNote,boolean lastCall) {
        if (AppUtility.isInternetConnected()) {
            final String[] message_key = new String[1];
            ActivityLogController.saveActivity(ActivityLogController.JOB_MODULE, ActivityLogController.JOB_UPLOAD_DOC, ActivityLogController.JOB_MODULE);
            String mimeType = "";
            MultipartBody.Part body = null;
            File file1 = new File(file);
            if (file1 != null) {
                mimeType = URLConnection.guessContentTypeFromName(file1.getName());
                if (mimeType == null) {
                    mimeType = finalFname;
                }
                RequestBody requestFile = RequestBody.create(file1, MediaType.parse(mimeType));
                // MultipartBody.Part is used to send also the actual file name
                body = MultipartBody.Part.createFormData("ja", finalFname + file.substring(file.lastIndexOf(".")), requestFile);
            }
            final RequestBody jobId = RequestBody.create(job_Id, MultipartBody.FORM);
            final RequestBody queId = RequestBody.create("", MultipartBody.FORM);
            final RequestBody jtId = RequestBody.create("", MultipartBody.FORM);
            RequestBody docName = RequestBody.create(finalFname, MultipartBody.FORM);
            RequestBody descBody = RequestBody.create(desc, MultipartBody.FORM);
            RequestBody userId = RequestBody.create(App_preference.getSharedprefInstance().getLoginRes().getUsrId(), MultipartBody.FORM);
            RequestBody typeId = RequestBody.create(type, MultipartBody.FORM);
            RequestBody tempId = RequestBody.create("tempId", MultipartBody.FORM);
            RequestBody forMobile = RequestBody.create("1", MultipartBody.FORM);

            if (isAddAttachAsCompletionNote == null)
                isAddAttachAsCompletionNote = "0";

            RequestBody isAddAttachAsCompletionNoteBody = RequestBody.create(isAddAttachAsCompletionNote, MultipartBody.FORM);

            //     AppUtility.progressBarShow(((Fragment) doc_attch_view).getActivity());
            String finalIsAddAttachAsCompletionNote = isAddAttachAsCompletionNote;
            ApiClient.getservices().uploadDocements(AppUtility.getApiHeaders(),
                            jobId, queId, jtId,userId, descBody, typeId, docName,
                            isAddAttachAsCompletionNoteBody, tempId,forMobile,body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            Log.e("Responce", jsonObject.toString());
                            if (jsonObject.get("success").getAsBoolean()) {
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type listType = new TypeToken<List<Attachments>>() {
                                }.getType();
                                ArrayList<Attachments> docList = new Gson().fromJson(convert, listType);
//                                if (finalIsAddAttachAsCompletionNote.equals("1")) {
//                                    AppDataBase.getInMemoryDatabase(EotApp.getAppinstance())
//                                            .jobModel().updateComplitionNotes(docList.get(0).getComplNote(), job_Id);
//                                    EotApp.getAppinstance().notifyObserver("removeFW", "complition", docList.get(0).getComplNote());
//                                }
//                                doc_attch_view.addNewItemToAttachmentList(docList, finalIsAddAttachAsCompletionNote);
                                message_key[0] =jsonObject.get("message").getAsString();
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            } else {
                                EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            AppUtility.progressBarDissMiss();
                            Log.e("Error", e.getMessage());
                            EotApp.getAppinstance().showToastmsg(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                            if(lastCall) {
                                EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey(message_key[0]));
                                updateJobData(job_Id);
                            }


                        }
                    });
        } else {
            AppUtility.progressBarDissMiss();
            networkDialog();
        }
        Log.e("Worker","Run Interface attachment.............");
    }

    @Override
    public void uploadDocuments(final String job_Id, String file, String finalFname, String des, String type, String isAddAttachAsCompletionNote) {

        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(ActivityLogController.JOB_MODULE, ActivityLogController.JOB_UPLOAD_DOC, ActivityLogController.JOB_MODULE);
            String mimeType = "";
            MultipartBody.Part body = null;
            File file1 = new File(file);
            if (file1 != null) {
                mimeType = URLConnection.guessContentTypeFromName(file1.getName());
                if (mimeType == null) {
                    mimeType = finalFname;
                }
                RequestBody requestFile = RequestBody.create(file1, MediaType.parse(mimeType));
                // MultipartBody.Part is used to send also the actual file name
                body = MultipartBody.Part.createFormData("ja", finalFname + file.substring(file.lastIndexOf(".")), requestFile);
            }
            final RequestBody jobId = RequestBody.create(job_Id, MultipartBody.FORM);
            final RequestBody queId = RequestBody.create("", MultipartBody.FORM);
            final RequestBody jtId = RequestBody.create("", MultipartBody.FORM);
            RequestBody docName = RequestBody.create(finalFname, MultipartBody.FORM);
            RequestBody descBody = RequestBody.create(des, MultipartBody.FORM);
            RequestBody userId = RequestBody.create(App_preference.getSharedprefInstance().getLoginRes().getUsrId(), MultipartBody.FORM);
            RequestBody typeId = RequestBody.create(type, MultipartBody.FORM);
            RequestBody tempId = RequestBody.create("tempId", MultipartBody.FORM);
            RequestBody forMobile = RequestBody.create("1", MultipartBody.FORM);

            if (isAddAttachAsCompletionNote == null)
                isAddAttachAsCompletionNote = "0";

            RequestBody isAddAttachAsCompletionNoteBody = RequestBody.create(isAddAttachAsCompletionNote, MultipartBody.FORM);

            //     AppUtility.progressBarShow(((Fragment) doc_attch_view).getActivity());
            String finalIsAddAttachAsCompletionNote = isAddAttachAsCompletionNote;
            ApiClient.getservices().uploadDocements(AppUtility.getApiHeaders(),
                    jobId, queId, jtId,userId, descBody, typeId, docName,
                    isAddAttachAsCompletionNoteBody, tempId,forMobile,body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            Log.e("Responce", jsonObject.toString());
                            if (jsonObject.get("success").getAsBoolean()) {
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type listType = new TypeToken<List<Attachments>>() {
                                }.getType();
                                ArrayList<Attachments> docList = new Gson().fromJson(convert, listType);
                                if (finalIsAddAttachAsCompletionNote.equals("1")) {
                                    AppDataBase.getInMemoryDatabase(EotApp.getAppinstance())
                                            .jobModel().updateComplitionNotes(docList.get(0).getComplNote(), job_Id);
                                    EotApp.getAppinstance().notifyObserver("removeFW", "complition", docList.get(0).getComplNote());
                                }
                                doc_attch_view.addNewItemToAttachmentList(docList, finalIsAddAttachAsCompletionNote);
                                EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                updateJobData(job_Id);
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                doc_attch_view.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            } else {
                                doc_attch_view.fileExtensionNotSupport(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            doc_attch_view.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(e.getMessage()));
                            Log.e("Error", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        } else {
            AppUtility.progressBarDissMiss();
            networkDialog();
        }
    }


    private void updateJobData(String job_id) {
        try {
            Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(job_id);
            /****Notify JOB overView for Attachment Upload first time ****/
            if (job != null && Integer.parseInt(job.getAttachCount()) == 0) {
                job.setAttachCount("1");
                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJob(job);
                EotApp.getAppinstance().getJobFlagOverView();
            }
            EotApp.getAppinstance().getNotifyForAttchCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDocuments(String docId, String des, String rename,final String isAddAttachAsCompletionNote, final String jobId, String queId, String jtId) {
        if (AppUtility.isInternetConnected()) {

            DocUpdateRequest docUpdateRequest = new DocUpdateRequest(docId, des, rename,isAddAttachAsCompletionNote);
            String request = new Gson().toJson(docUpdateRequest);
            ApiClient.getservices().eotServiceCall(Service_apis.updateJObDocument,
                    AppUtility.getApiHeaders(), AppUtility.getJsonObject(request))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            try {
                                if (jsonObject.get("success").getAsBoolean()) {
                                    String message = LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString());
                                    if (isAddAttachAsCompletionNote.equals("1") && jsonObject.getAsJsonArray("data").size() > 0) {
                                        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance())
                                                .jobModel().updateComplitionNotes(jsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("complNote").getAsString(), jobId);
                                        EotApp.getAppinstance().notifyObserver("removeFW", "complition", jsonObject.getAsJsonArray("data").get(0).getAsJsonObject().get("complNote").getAsString());
                                    }
                                    doc_attch_view.onDocumentUpdate(message, true);
                                } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                    doc_attch_view.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                } else {
                                    String message = LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString());
                                    doc_attch_view.onDocumentUpdate(message, false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            //  remark_view.onSessionExpire("");
                            AppUtility.progressBarDissMiss();
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });

        } else {
            AppUtility.progressBarDissMiss();
            networkDialog();
        }
    }

    @Override
    public void uploadQuoteDocument(String file,String fileName, String quotId, String type, String usrId, String des) {
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(ActivityLogController.JOB_MODULE, ActivityLogController.JOB_UPLOAD_DOC, ActivityLogController.JOB_MODULE);
            String mimeType = "";
            MultipartBody.Part body = null;
            File file1 = new File(file);
            if (file1 != null) {
                mimeType = URLConnection.guessContentTypeFromName(file1.getName());
                if (mimeType == null) {
                    mimeType = fileName;
                }
                RequestBody requestFile = RequestBody.create(file1, MediaType.parse(mimeType));
                body = MultipartBody.Part.createFormData("qa", fileName + file.substring(file.lastIndexOf(".")), requestFile);
            }
            final RequestBody _quotId = RequestBody.create(quotId, MultipartBody.FORM);
            RequestBody descBody = RequestBody.create(des, MultipartBody.FORM);
            RequestBody docNm = RequestBody.create(fileName, MultipartBody.FORM);
            RequestBody userId = RequestBody.create(App_preference.getSharedprefInstance().getLoginRes().getUsrId(), MultipartBody.FORM);
            RequestBody typeId = RequestBody.create(type, MultipartBody.FORM);
            //     AppUtility.progressBarShow(((Fragment) doc_attch_view).getActivity());
            ApiClient.getservices().uploadQuoteDocuments(AppUtility.getApiHeaders(),
                            _quotId,userId,descBody,typeId,docNm,body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            Log.e("Responce", jsonObject.toString());
                            if (jsonObject.get("success").getAsBoolean()) {
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type listType = new TypeToken<List<Attachments>>() {
                                }.getType();
                                ArrayList<Attachments> docList = new Gson().fromJson(convert, listType);
                                doc_attch_view.addNewItemToAttachmentList(docList, "");
                               EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                doc_attch_view.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            } else {
                                doc_attch_view.fileExtensionNotSupport(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            AppUtility.progressBarDissMiss();
                            Log.e("Error", e.getMessage());
                            doc_attch_view.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(e.getMessage()));
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        } else {
            AppUtility.progressBarDissMiss();
            networkDialog();
        }
    }


    private void networkDialog() {
        try {
            Context mContext = null;
            if (doc_attch_view instanceof Fragment)
                mContext = (((Fragment) doc_attch_view).getActivity());
            else if (doc_attch_view instanceof Activity)
                mContext = (Activity) doc_attch_view;
            AppUtility.alertDialog(mContext, LanguageController.getInstance()
                    .getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().
                    getMobileMsgByKey(AppConstant.err_check_network), LanguageController.getInstance().
                    getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addAttachmentToDb(List<Attachments> data, String jobId,boolean firstCall,String isAttachCompletionNotes,int parentPosition, int position, String queId, String jtId, boolean isFromAttachment){
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                if(data.size()>0) {
                    Attachments_Dao attachments_dao = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao();

                    for (Attachments item : data
                         ) {
                        if(item.getAttachmentId() != null && item.getIsdelete().equalsIgnoreCase("1") && !item.getAttachmentId().contains("Attachment-")){
                            Attachments tempAttach = attachments_dao.getAttachmetById(item.getAttachmentId());
                            if (attachments_dao.isAttachment(item.getAttachmentId())) {
                                if (tempAttach.getBitmap() != null && !tempAttach.getBitmap().isEmpty())
                                    item.setBitmap(tempAttach.getBitmap());
                            }
                        } else if(item.getTempId() != null && item.getIsdelete().equalsIgnoreCase("1")) {
                            Attachments tempAttach = attachments_dao.getAttachmetByTempId(item.getTempId());
                            if (attachments_dao.isAttachment(item.getAttachmentId())) {
                                if (tempAttach.getBitmap() != null && !tempAttach.getBitmap().isEmpty())
                                    item.setBitmap(tempAttach.getBitmap());
                            }
                        }
                    }
                    attachments_dao.insertAttachments(data);
                    attachments_dao.deleteAttachments();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
//                            doc_attch_view.setList((ArrayList<Attachments>) attachments_dao.getAttachmentsByJobId(jobId), "",firstCall);
                            if(isFromAttachment){
                                doc_attch_view.setMultiList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAllAttachmentsOfJob(jobId), isAttachCompletionNotes, firstCall, parentPosition, position, queId, jtId);
                            }else {
                                doc_attch_view.setMultiList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAttachmentsByJobId(jobId), isAttachCompletionNotes, firstCall, parentPosition, position, queId, jtId);
                            }
                        }
                    });


                    for (Attachments item :
                            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAllAttachmentsOfJob(jobId)) {
                        String ImageName = "";
                        if (item.getBitmap() != null && item.getBitmap().isEmpty()) {
                            ImageName = item.getAttachFileActualName();
                            DowloadFile(item.getAttachThumnailFileName(),ImageName, item.getAttachmentId());
                        } else if ( item.getBitmap() != null && !new File(item.getBitmap()).exists()) {
                            String[] splitName = item.getBitmap().split("/");
                            ImageName = splitName[splitName.length-1];
                            DowloadFile(item.getAttachThumnailFileName(),ImageName, item.getAttachmentId());
                        }
                    }

                }else {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
//                            doc_attch_view.setList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAttachmentsByJobId(jobId), "",firstCall);
                            if(isFromAttachment){
                                doc_attch_view.setMultiList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAllAttachmentsOfJob(jobId), isAttachCompletionNotes, firstCall, parentPosition, position, queId, jtId);
                            }else {
                                doc_attch_view.setMultiList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAttachmentsByJobId(jobId), isAttachCompletionNotes, firstCall, parentPosition, position, queId, jtId);
                            }
                        }
                    });
                    for (Attachments item :
                            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAllAttachmentsOfJob(jobId)) {
                        String ImageName = "";
                        if (item.getBitmap() != null && item.getBitmap().isEmpty()) {
                            ImageName = item.getAttachFileActualName();
                            DowloadFile(item.getAttachThumnailFileName(),ImageName, item.getAttachmentId());
                        } else if ( item.getBitmap() != null && !new File(item.getBitmap()).exists()) {
                            String[] splitName = item.getBitmap().split("/");
                            ImageName = splitName[splitName.length-1];
                            DowloadFile(item.getAttachThumnailFileName(),ImageName, item.getAttachmentId());
                        }
                    }
                }
            }
        });
    }
    public void  DowloadFile(String endPoint, String imageName, String attachmentId){
        try {
            URL url =  new URL(App_preference.getSharedprefInstance().getBaseURL() + endPoint);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            String imagePath = AppUtility.downloadFile(imageName, image).getAbsolutePath();
            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().updateAttachment(imagePath, attachmentId);
        } catch (IOException e) {
            Log.e("Error","Error catch of JobDetail_pc 948 == "+ e.getMessage());
        }
    }
}
