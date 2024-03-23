package com.eot_app.nav_menu.jobs.job_detail.detail.job_detail_presenter;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.eot_app.activitylog.ActivityLogController;
import com.eot_app.activitylog.LogModel;
import com.eot_app.nav_menu.custom_fileds.custom_model.CustOmFiledReqModel;
import com.eot_app.nav_menu.custom_fileds.custom_model.CustOmFiledResModel;
import com.eot_app.nav_menu.custom_fileds.custom_model.CustOmFormQuestionsReq;
import com.eot_app.nav_menu.custom_fileds.custom_model.CustOmFormQuestionsRes;
import com.eot_app.nav_menu.jobs.add_job.add_job_recr.DeleteReCur;
import com.eot_app.nav_menu.jobs.add_job.add_job_recr.RecurReqResModel;
import com.eot_app.nav_menu.jobs.job_controller.ChatController;
import com.eot_app.nav_menu.jobs.job_db.Attachments_Dao;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_db.JobListRequestModel;
import com.eot_app.nav_menu.jobs.job_detail.JobDetailActivity;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.chat.fire_Base_Model.Chat_Send_Msg_Model;
import com.eot_app.nav_menu.jobs.job_detail.detail.job_detail_view.JobDetail_view;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.CompletionDetails;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.CompletionDetailsPost;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.JobStatusModelNew;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.Jobdetail_status_res;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.GetFileList_req_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_db.model_pkg.ItembyJobModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.model.EquipmentStatusReq;
import com.eot_app.nav_menu.jobs.job_detail.job_status_pkg.JobStatus_Controller;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.RequestedItemModel;
import com.eot_app.nav_menu.jobs.joboffline_db.JobOfflineDataModel;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.db.OfflineDataController;
import com.eot_app.utility.language_support.LanguageController;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hypertrack.hyperlog.HyperLog;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aplite_pc302 on 6/25/18.
 */

public class JobDetail_pc implements JobDetail_pi {
    private final int updatelimit;
    JobDetail_view view;
    private int count;
    private int updateindex;
    private int updateindexAtttachment;
    JobStatusModelNew jobstatus;
    String jobId;
    private String img = "";
    private String startAttachmetSyncTime;


    public JobDetail_pc(JobDetail_view view) {
        this.view = view;
        LogModel logModel = ActivityLogController
                .getObj(ActivityLogController.JOB_MODULE, ActivityLogController.JOB_DETAILS, ActivityLogController.JOB_MODULE);
        ActivityLogController.saveOfflineTable(logModel);
        this.updateindex = 0;
        this.updateindexAtttachment = 0;
        this.updatelimit = AppConstant.LIMIT_MID;
    }


    @Override
    public void getAttachFileList(final String jobId, final String usrId, final String type) {
        try {
//            GetFileList_req_Model getFileList_model = new GetFileList_req_Model(updateindexAtttachment, updatelimit, jobId, usrId, type);
//            JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(getFileList_model));

            startAttachmetSyncTime=AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
            App_preference.getSharedprefInstance().setAttachmentStartSyncTime(startAttachmetSyncTime);
            JobListRequestModel jobListRequestModel = new JobListRequestModel(Integer.parseInt(usrId), updatelimit, updateindex, App_preference.getSharedprefInstance().getAttachmentStartSyncTime(), jobId);
            JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(jobListRequestModel));

            if (AppUtility.isInternetConnected()) {

                ActivityLogController.saveActivity(ActivityLogController.JOB_MODULE, ActivityLogController.JOB_DOCUMENT_LIST, ActivityLogController.JOB_MODULE);
                ApiClient.getservices().eotServiceCall(Service_apis.getSyncJobAttachments, AppUtility.getApiHeaders(), jsonObject)

                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JsonObject>() {
                            @Override
                            public void onSubscribe(@NotNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NotNull JsonObject jsonObject) {
                                Log.e("FileList", "" + jsonObject.toString());
                                if (jsonObject.get("success").getAsBoolean()) {
                                    if (jsonObject.get("data").getAsJsonArray().size() > 0) {
                                        try {
                                            count = jsonObject.get("count").getAsInt();
                                            String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                            Type listType = new TypeToken<List<Attachments>>() {
                                            }.getType();
                                            ArrayList<Attachments> getFileList_res = new Gson().fromJson(convert, listType);
                                            addAttachmentToDb(getFileList_res,jobId);
                                        } catch (Exception exception) {
                                            exception.printStackTrace();
                                        }
                                    } else {
                                        addAttachmentToDb(new ArrayList<>(),jobId);
                                    }
                                } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                    //  view.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                }
                            }

                            @Override
                            public void onError(@NotNull Throwable e) {
                                Log.e("", e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                Log.e("onComplete", "onComplete");
                                Log.e("onComplete", "updateindexAtttachment:"+updateindexAtttachment);
                                Log.e("onComplete", "count:"+count);
                                Log.e("onComplete", "updatelimit:"+updatelimit);
                                if ((updateindexAtttachment + updatelimit) <= count) {
                                    Log.e("onComplete", "second time call");
                                    updateindexAtttachment +=updatelimit;
                                    getAttachFileList(jobId, usrId, type);
                                } else {
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
                                }
                            }
                        });
            }else {
                addAttachmentToDb(new ArrayList<>(),jobId);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void getItemFromServer(String jobId) {
        if (AppUtility.isInternetConnected()) {
            ItembyJobModel model = new ItembyJobModel(jobId);//, App_preference.getSharedprefInstance().getJobSyncTime()
            String data = new Gson().toJson(model);
            ApiClient.getservices().eotServiceCall(Service_apis.getItemFromJob, AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type listType = new com.google.common.reflect.TypeToken<List<InvoiceItemDataModel>>() {
                                }.getType();
                                List<InvoiceItemDataModel> data = new Gson().fromJson(convert, listType);
                                if (data.size() > 0) {
                                    addgetItemFromJobToDB(data, jobId);
                                }
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            getItemListByJobFromDB(jobId);
                        }
                    });
        }
    }
    private void addgetItemFromJobToDB(List<InvoiceItemDataModel> data, String jobId) {
        if (data != null && data.size() > 0) {
            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJobitems(jobId, data);
        }
    }
    @Override
    public void getItemListByJobFromDB(String jobId) {
        Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
        try {
            if (job.getItemData() != null && job.getItemData() != null && job.getItemData().size() > 0) {
                view.setItemListByJob(job.getItemData());
            } else {
                view.setItemListByJob(new ArrayList<>());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void stopRecurpattern(String jobId) {

        ApiClient.getservices().eotServiceCall(Service_apis.deleteRecur, AppUtility.getApiHeaders(),
                AppUtility.getJsonObject(new Gson().toJson(new DeleteReCur(jobId))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NotNull JsonObject jsonObject) {
                        Log.e("", "");
                        if (jsonObject.get("success").getAsBoolean()) {
                            EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey("recur_deleted"));
                            view.StopRecurPatternHide();
                            Log.e("", "");
                        } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                            view.sessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                        } else {
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.e("TAG", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG", " e.getMessage()");
                    }
                });

    }

    private void showErrorDialog(String msg) {
        AppUtility.error_Alert_Dialog(((Fragment) view).getActivity(), msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok)
                , () -> null);
    }

    @Override
    synchronized public void changeJobStatusAlertInvisible(String jobid, String type, JobStatusModelNew jobStatus, String lat, String lng
            , String isMailSentToClt, String isLeaderChgKprsStatus, String jobLable ,String jobType
    ) {
        HyperLog.i("JobDetail_pc", "changeJobStatusAlertInvisible(M) start");

        this.jobstatus = jobStatus;
        this.jobId = jobid;


        // to remove double calling of api for same status in case of double tap
        try{
            // to check the current status of job
            String currentJobStatus =AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobStatusByJobId(jobid);
            Log.e("CurrentStatus::",currentJobStatus);
            HyperLog.e("TAG","CurrentStatusJob:: jobId :: "+jobId +"Status :: "+currentJobStatus);

            if(jobstatus!=null
                    &&jobstatus.getKey()!=null
                    &&jobstatus.getKey().equalsIgnoreCase(currentJobStatus)) {
                return;
            }
            //TODO
            // 7 is for in progress and 2 is for accepted
            // for resolving the issue , job  getting accepted after in progress
            if(jobstatus!=null
                    &&jobstatus.getKey()!=null &&currentJobStatus!=null
                    &&currentJobStatus.equalsIgnoreCase("7")
                    &&jobstatus.getKey().equalsIgnoreCase("2")) {
                return;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
        Gson gson = new Gson();
        Jobdetail_status_res request = new Jobdetail_status_res(jobId,
                App_preference.getSharedprefInstance().getLoginRes().getUsrId(), type, jobstatus.getStatus_no(),
                dateTime, lat, lng, isMailSentToClt, isLeaderChgKprsStatus,jobLable,jobType);
        String data = gson.toJson(request);
        Job jobData = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobid);
        /* **JOB status change Before JOB sync**/
        if (jobData != null && jobData.getJobId() != null && jobData.getTempId() != null && jobData.getJobId().equals(jobData.getTempId())) {
            JobOfflineDataModel jobOfflineDataModel = new JobOfflineDataModel(Service_apis.changeJobStatus, data, dateTime, jobData.getTempId());
            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobOfflineDao().insertJobOfflineData(jobOfflineDataModel);
            // HyperLog.i("", "Job Not Sync -----" + "Add in Job table");
            HyperLog.i("JobDetail_pc", "Job status saved IN LOCAL TABLE with temp id");
            HyperLog.i("JobDetail_pc", "ob status saved In Local Table");
        } else {
            OfflineDataController.getInstance().addInOfflineDB(Service_apis.changeJobStatus, data, dateTime);
            HyperLog.i("JobDetail_pc", "Job status saved on with job id");
            HyperLog.i("JobDetail_pc", "Job status saved In Offline Table");
        }


        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJobByStatus(jobId, jobstatus.getStatus_no(),
                AppUtility.getDateByMiliseconds());
        HyperLog.i("JobDetail_pc", "Local DB updated with selected job status");


        if (jobStatus.getStatus_no().equals(AppConstant.In_Progress)) {
            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().setOtherToPending(jobId, AppConstant.In_Progress, AppConstant.New_On_Hold);
            HyperLog.i("JobDetail_pc", "Local DB refresh for other In progress job");
        }
        view.setResultForChangeInJob("Update", jobid);
        HyperLog.i("JobDetail_pc", "callback completion of local DB update the view on list");

        //data and UI reflect after offline save
        if (jobStatus.getStatus_no().equals(AppConstant.Cancel) || jobStatus.getStatus_no().equals(AppConstant.Reject)||jobstatus.getStatus_no().equals("10")) {
            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().deleteJobById(jobId);
            ChatController.getInstance().removeListnerByJobID(jobid);
            ((JobDetailActivity) ((Fragment) view).getActivity()).finishActivityWithSetResult();
            HyperLog.i("JobDetail_pc", "delete job with status cancel or reject");

        } else {
            HyperLog.i("JobDetail_pc", "Local DB updated with selected job status");
            view.setButtonsUI(jobstatus);
        }

        LogModel logModel = ActivityLogController
                .getObj(ActivityLogController.JOB_MODULE, ActivityLogController.JOB_STATUS, ActivityLogController.JOB_MODULE);
        ActivityLogController.saveOfflineTable(logModel);

        HyperLog.i("JobDetail_pc", "changeJobStatusAlertInvisible(M) Completed");

    }


    @Override
    public String getStatusName(String status) {
        JobStatusModelNew jobStatusModel = JobStatus_Controller.getInstance().getStatusObjectById(status);
        if (jobStatusModel != null) {
            if (jobStatusModel.getImg() != null)
                img = jobStatusModel.getImg();
            if (jobStatusModel.getStatus_name() != null && jobStatusModel.getStatus_name().equals("New")) {
                return "Not Started";
            } else {
                return jobStatusModel.getStatus_name();
            }
        }
        return "";
    }

    @Override
    public String getImg() {
        return img;
    }

    @Override
    public void getEquipmentList(String jobId) {
        Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
        if (job != null && job.getEquArray() != null) {
            view.setEuqipmentList(job.getEquArray());
        }
    }

   /* @Override
    public void refreshList(String auditID, final String jobId) {
        if (AppUtility.isInternetConnected()) {
            HashMap<String, String> auditListRequestModel = new HashMap<>();
            auditListRequestModel.put("limit", ""+updatelimit);
            auditListRequestModel.put("index", ""+updateindex);
            auditListRequestModel.put("audId", ""+auditID);
            auditListRequestModel.put("isJob", "1");
            auditListRequestModel.put("isParent", "1");

//            AuditEquipmentRequestModel auditListRequestModel = new AuditEquipmentRequestModel(auditID,
//                    updatelimit, updateindex, "");
//            auditListRequestModel.setIsJob(1);

            String data = new Gson().toJson(auditListRequestModel);
            ApiClient.getservices().eotServiceCall(Service_apis.getEquipmentList, AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {
                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            Log.e("", "");
                            try {
                                Log.d("mahi", jsonObject.toString());
                                if (jsonObject.get("success").getAsBoolean()) {
                                    count = jsonObject.get("count").getAsInt();
                                    String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                    Type listType = new com.google.common.reflect.TypeToken<List<EquArrayModel>>() {
                                    }.getType();
                                    List<EquArrayModel> data = new Gson().fromJson(convert, listType);

                                    updateEuipmentInDB(data, jobId);

                                    view.setEuqipmentList(data);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("", "");
                        }

                        @Override
                        public void onComplete() {
                            if ((updateindex + updatelimit) <= count) {
                                updateindex += updatelimit;
                            } else {
                                App_preference.getSharedprefInstance().setEquipmentSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                updateindex = 0;
                                count = 0;
                            }
                        }
                    });
        } else {
            getEquipmentList(jobId);

        }
    }*/


    @Override
    public void getEquipmentStatus() {
        if (AppUtility.isInternetConnected()) {
            EquipmentStatusReq equipmentListReq = new EquipmentStatusReq();
            String data = new Gson().toJson(equipmentListReq);
            ApiClient.getservices().eotServiceCall(Service_apis.getEquipmentStatus, AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                App_preference.getSharedprefInstance().setEquipmentStatusList("");
                                // store locally
                                String convertString = jsonObject.get("data").getAsJsonArray().toString();
                                App_preference.getSharedprefInstance().setEquipmentStatusList(convertString);
                                EotApp.getAppinstance().getNotifyForEquipmentStatusList();
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private void updateEuipmentInDB(List<EquArrayModel> data, String jobId) {
        try {
            Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
            /* *****Notify JOB overView for Equipmetn Added first time ****/
            if (job.getEquArray() != null && job.getEquArray().size() == 0) {
                job.setEquArray(data);
                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJobequipArray(jobId, data);
                EotApp.getAppinstance().getJobFlagOverView();
            } else {
                /* **Refresh job Table in Exiting Equ. lisy***/
                job.setEquArray(data);
                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJobequipArray(jobId, data);
                if (job.getEquArray() != null && job.getEquArray().size() == 0)
                    EotApp.getAppinstance().getJobFlagOverView();
            }
            getEquipmentList(jobId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public JobStatusModelNew getJobStatusObject(String statusId) {
        JobStatusModelNew jobStatusModel = JobStatus_Controller.getInstance().getStatusObjectById(statusId);
        if (jobStatusModel != null)
            return jobStatusModel;
        else return new JobStatusModelNew();
    }

    @Override
    public boolean isOldStaus(String status_no, String jobId) {
        if (jobId != null && !jobId.equals("")) {
            Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
            if (job != null && job.getStatus() != null && status_no != null && !status_no.equals("")) {
                return AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId).getStatus().equals(status_no);
            }
        }
        return true;
    }

    @Override
    public void setJobCurrentStatus(String jobid) {
        Job item_job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobid);
        view.resetstatus(item_job.getStatus());
    }

    @Override
    public boolean checkContactHideOrNot() {
        try {
            if (App_preference.getSharedprefInstance().getLoginRes().getIsHideContact().equals("1")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void getCustomFieldQues(final String jobId) {
        if (AppUtility.isInternetConnected()) {
            ApiClient.getservices().eotServiceCall(Service_apis.getFormDetail, AppUtility.getApiHeaders(),
                    AppUtility.getJsonObject(new Gson().toJson(new CustOmFiledReqModel("1"))))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                String convert = jsonObject.get("data").getAsJsonObject().toString();
                                CustOmFiledResModel resModel = new Gson().fromJson(convert, CustOmFiledResModel.class);
                                getQuestByParntId(resModel.getFrmId(), jobId);
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                view.sessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }


                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("TAG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            // joblist_view.setRefereshPullOff();
        }
    }

    @Override
    public void getQuestByParntId(String formId, String jobId) {
        CustOmFormQuestionsReq model = new CustOmFormQuestionsReq(formId, jobId);
        if (AppUtility.isInternetConnected()) {
            ApiClient.getservices().eotServiceCall(Service_apis.getQuestionsByParentId, AppUtility.getApiHeaders(),
                    AppUtility.getJsonObject(new Gson().toJson(model)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                String convert = jsonObject.get("data").getAsJsonArray().toString();
                                Type listType = new TypeToken<List<CustOmFormQuestionsRes>>() {
                                }.getType();
                                ArrayList<CustOmFormQuestionsRes> data = new Gson().fromJson(convert, listType);
                                view.setCustomFiledList(data);
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                view.sessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }


                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("TAG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }
    ArrayList<CompletionDetails> data = new ArrayList<>();
    @Override
    public void getJobCompletionDetails(String jobId) {
        if (AppUtility.isInternetConnected()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("jobId", jobId);
            hashMap.put("usrId", App_preference.getSharedprefInstance().getLoginRes().getUsrId());
            hashMap.put("type", "1");
            ApiClient.getservices().eotServiceCall(Service_apis.getJobCompletionNOte, AppUtility.getApiHeaders(),
                    AppUtility.getJsonObject(new Gson().toJson(hashMap)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                try {

                                    String convert = jsonObject.get("data").getAsJsonArray().toString();
                                    Type listType = new TypeToken<List<CompletionDetails>>() {
                                    }.getType();
                                    data = new Gson().fromJson(convert, listType);
                                    view.setCompletionDetails(data);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                view.sessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }


                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("TAG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        }else {
            view.setOfflineData();
        }
    }


    @Override
    public void addJobCompletionDetails(String jobId,CompletionDetailsPost.CompletionDetail obj,int logType) {
        if (AppUtility.isInternetConnected()) {

            List< CompletionDetailsPost.CompletionDetail> list = new ArrayList<>();
            list.add(obj);

            CompletionDetailsPost completionDetailsPost = new CompletionDetailsPost(logType,
                    App_preference.getSharedprefInstance().getLoginRes().getUsrId(),list,jobId);

             AppUtility.progressBarShow((((Fragment) view).getActivity()));

            ApiClient.getservices().addCompletionDetails( AppUtility.getApiHeaders(),
                    completionDetailsPost)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                try {
//                                    String convert = jsonObject.get("data").getAsJsonArray().toString();
                                    getJobCompletionDetails(jobId);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                view.sessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }


                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("TAG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
        else
            networkDialog();
    }

    @Override
    public void getRecureDataList(String jobId, String recurType) {
        if (AppUtility.isInternetConnected()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("jobId", jobId);
            hashMap.put("recurType", recurType);
            ApiClient.getservices().eotServiceCall(Service_apis.getRecurDataOfJob, AppUtility.getApiHeaders(),
                            AppUtility.getJsonObject(new Gson().toJson(hashMap)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            AppUtility.progressBarDissMiss();
                            if (jsonObject.get("success").getAsBoolean()) {
                                try {

                                    String convert = jsonObject.get("data").getAsJsonObject().toString();
                                    Type listType = new TypeToken<RecurReqResModel>() {
                                    }.getType();
                                    RecurReqResModel data = new Gson().fromJson(convert, listType);
                                    view.setRecurData(data);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                AppUtility.progressBarDissMiss();
                                view.notDataFoundInRecureData(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }


                        @Override
                        public void onError(@NotNull Throwable e) {
                            AppUtility.progressBarDissMiss();
                            Log.e("TAG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        }
        else
            networkDialog();
    }

    @Override
    public void getRequestedItemDataList(String jobId) {
        if (AppUtility.isInternetConnected()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("jobId", jobId);
            hashMap.put("limit",updatelimit+"");
            hashMap.put("index",updateindex+"");
            ApiClient.getservices().eotServiceCall(Service_apis.getListItemRequest, AppUtility.getApiHeaders(),
                            AppUtility.getJsonObject(new Gson().toJson(hashMap)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                try {

                                    String convert = jsonObject.get("data").getAsJsonArray().toString();
                                    Type listType = new TypeToken<List<RequestedItemModel>>() {
                                    }.getType();
                                    List<RequestedItemModel> data = new Gson().fromJson(convert, listType);
                                        view.setRequestItemData(data);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                AppUtility.progressBarDissMiss();
                                view.notDtateFoundInRequestedItemList(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }


                        @Override
                        public void onError(@NotNull Throwable e) {
                            AppUtility.progressBarDissMiss();
                            Log.e("TAG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        }
        else
            networkDialog();
    }

    @Override
    public void deleteRequestedItem(String irId, String jobId, AddUpdateRequestedModel requestedModel) {
        if (AppUtility.isInternetConnected()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("irIds",irId);
            hashMap.put("jobId", jobId);

            ApiClient.getservices().eotServiceCall(Service_apis.deleteItemRequest, AppUtility.getApiHeaders(),
                            AppUtility.getJsonObject(new Gson().toJson(hashMap)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            AppUtility.progressBarDissMiss();
                            if (jsonObject.get("success").getAsBoolean()) {
                                try {
                                    view.deletedRequestData(jsonObject.get("message").getAsString(),requestedModel);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                AppUtility.progressBarDissMiss();
                                view.notDtateFoundInRequestedItemList(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }


                        @Override
                        public void onError(@NotNull Throwable e) {
                            AppUtility.progressBarDissMiss();
                            Log.e("TAG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        }
        else
            networkDialog();
    }

    private void networkDialog() {
        AppUtility.alertDialog((((Fragment) view).getActivity()), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
    }

    /**
     * Load Job list from server when Pull to refresh
     */
    @Override
    synchronized public void loadFromServer(String jobId) {
        if (AppUtility.isInternetConnected()) {

            LogModel logModel = ActivityLogController
                    .getObj(ActivityLogController.JOB_MODULE, ActivityLogController.JOB_LIST, ActivityLogController.JOB_MODULE);
            ActivityLogController.saveOfflineTable(logModel);

            JobListRequestModel jobListRequestModel = new JobListRequestModel(Integer.parseInt(App_preference.getSharedprefInstance().getLoginRes().getUsrId()),
                    updatelimit, updateindex, App_preference.getSharedprefInstance().getJobSyncTime());


            // for storing the date time when the api call started
            String startJobSyncTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);

            String data = new Gson().toJson(jobListRequestModel);
            ApiClient.getservices().eotServiceCall(Service_apis.getUserJobList, AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                count = jsonObject.get("count").getAsInt();
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type listType = new TypeToken<List<Job>>() {
                                }.getType();
                                List<Job> data = new Gson().fromJson(convert, listType);
                                addRecordsToDB(data, jobId);
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                view.sessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }


                        @Override
                        public void onError(Throwable e) {
                            Log.e("TAG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            if ((updateindex + updatelimit) <= count) {
                                updateindex += updatelimit;
                                loadFromServer(jobId);
                            } else {
                                if (count != 0) {
                                    if(App_preference.getSharedprefInstance().getJobStartSyncTime().isEmpty()
                                            &&startJobSyncTime!=null && !startJobSyncTime.isEmpty()){
//                                        App_preference.getSharedprefInstance().setJobSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                        App_preference.getSharedprefInstance().setJobSyncTime(startJobSyncTime);
                                        Log.v("MainSync","startJobSyncTime JobList"+" --" +App_preference.getSharedprefInstance().getJobSyncTime());
                                    }
                                    else if(App_preference.getSharedprefInstance().getJobStartSyncTime().isEmpty()){
//                                        App_preference.getSharedprefInstance().setJobSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                        App_preference.getSharedprefInstance().setJobSyncTime(startJobSyncTime);
                                        Log.v("MainSync","startJobSyncTime JobList"+" --" +App_preference.getSharedprefInstance().getJobSyncTime());
                                    }
                                    else {
                                        App_preference.getSharedprefInstance().setJobSyncTime(App_preference.getSharedprefInstance().getJobStartSyncTime());
                                    }
//                                    App_preference.getSharedprefInstance().setJobSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                }
                                updateindex = 0;
                                count = 0;
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().deleteJobByIsDelete();
                            }
                        }
                    });
        }else {
            view.setOfflineData();
        }
    }
    public void addRecordsToDB(List<Job> data, String jobId) {
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().inserJob(data);
//        for add/remove listener.
        for (Job item : data) {
            if (item.getIsdelete().equals("0")
                    || item.getStatus().equals(AppConstant.Cancel)
                    || item.getStatus().equals(AppConstant.Closed)
                    || item.getStatus().equals(AppConstant.Reject)) {
                ChatController.getInstance().removeListnerByJobID(item.getJobId());
            } else {
                ChatController.getInstance().registerChatListner(item);
            }
        }
        getEquipmentList(jobId);
        view.setOfflineData();
    }

    @Override
    public void sendMsg(Chat_Send_Msg_Model chat_send_Msg_model) {
        if (AppUtility.isInternetConnected()) {
            FirebaseFirestore.getInstance().collection(ChatController.getInstance().getChatPath(chat_send_Msg_model.getJobCode(), chat_send_Msg_model.getJobId()))
                    .add(chat_send_Msg_model)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.e("Message Send", documentReference.getId());
                            /*
                             *update read count for all fieldworkers except me***/
                            ChatController.getInstance().increseUnreadCountforAll(chat_send_Msg_model.getJobCode(), chat_send_Msg_model.getJobId());
                            /*
                             * function call for offline user push notification.**/
                            ChatController.getInstance().getAllUserOffLineDataList(chat_send_Msg_model);
                            /*
                             *function call for desktop notification**/
                            ChatController.getInstance().sendNotificationToAdmins(chat_send_Msg_model);
                            /*
                             *function call for increase job count**/
                            ChatController.getInstance().notifyWebForIncreaseCount("jobCount", "teamChat");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Msg Not Send", e.getMessage());
                        }
                    });
        } else {
            networkDialog();
        }
    }
    public void addAttachmentToDb(List<Attachments> data, String jobId){
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
                    view.setList((ArrayList<Attachments>) attachments_dao.getAttachmentsByJobId(jobId), "");
                }
            });

                    for (Attachments item :
              AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAttachmentsByJobId(jobId)) {
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
                    view.setList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAttachmentsByJobId(jobId), "");
                }
            });
            for (Attachments item :
                    AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAttachmentsByJobId(jobId)) {
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
