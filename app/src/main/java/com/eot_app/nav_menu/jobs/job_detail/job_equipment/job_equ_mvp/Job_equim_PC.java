package com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_mvp;

import android.content.Context;
import android.util.Log;

import com.eot_app.activitylog.ActivityLogController;
import com.eot_app.activitylog.LogModel;
import com.eot_app.nav_menu.audit.audit_list.equipment.equipment_room_db.entity.EquipmentStatus;
import com.eot_app.nav_menu.jobs.job_controller.ChatController;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_db.JobListRequestModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.model.EquipmentStatusReq;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sonam-11 on 22/9/20.
 */
public class Job_equim_PC implements Job_equim_PI {
    private final Job_equim_View jobEquimView;
    private final int updatelimit;
    private int updateindex;
    private int count;


    public Job_equim_PC(Job_equim_View jobEquimView) {
        this.jobEquimView = jobEquimView;
        this.updatelimit = AppConstant.LIMIT_HIGH;
        this.updateindex = 0;
    }

    @Override
    public void getEquipmentList(String jobId) {
        AppUtility.progressBarDissMiss();
        Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
        if (job != null && job.getEquArray() != null) {
            Log.e("job Eq ::", new Gson().toJson(job.getEquArray()));
            jobEquimView.setEuqipmentList(job.getEquArray());
        }
    }

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
                                Type listType = new com.google.gson.reflect.TypeToken<List<Job>>() {
                                }.getType();
                                List<Job> data = new Gson().fromJson(convert, listType);
                                addRecordsToDB(data, jobId);
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                jobEquimView.onSessionExpired(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
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
            addRecordsToDB(new ArrayList<Job>(), jobId);
        }
    }
    public void addRecordsToDB(List<Job> data, String jobId) {
        if(data != null && data.size()>0) {
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
        }
        getEquipmentList(jobId);

    }

}
