/*
 * Created by Shivani Vani
 */
package com.eot_app.services;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.eot_app.activitylog.ActivityLogController;
import com.eot_app.nav_menu.admin_fw_chat_pkg.sonam_user_user_chat_pkg.usertouser_model.UserChatListModelReq;
import com.eot_app.nav_menu.admin_fw_chat_pkg.sonam_user_user_chat_pkg.usertouser_model.UserChatModel;
import com.eot_app.nav_menu.appointment.dbappointment.Appointment;
import com.eot_app.nav_menu.appointment.list.model.AppointmentListReq;
import com.eot_app.nav_menu.audit.audit_list.audit_mvp.model.AuditListRequestModel;
import com.eot_app.nav_menu.audit.audit_list.audit_mvp.model.AuditList_Res;
import com.eot_app.nav_menu.client.client_db.Client;
import com.eot_app.nav_menu.client.client_db.Client_Request_model;
import com.eot_app.nav_menu.client.clientlist.client_detail.contact.client_dao.ContactData;
import com.eot_app.nav_menu.client.clientlist.client_detail.site.sitelist.Site_model;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_db.JobListRequestModel;
import com.eot_app.nav_menu.jobs.job_detail.customform.cstm_form_model.CustomFormListOffline;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.invoice.inventry_pkg.Inventry_ReQ_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.inventry_pkg.Inventry_ReS_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_db.location_tax_dao.TaxReqModel;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_db.location_tax_dao.TaxesLocation;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.Tax;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_model_pkg.TaxList_ReQ_Model;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.BrandData;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.GetBrandListReqModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.GetListModel;
import com.eot_app.time_shift_pkg.ShiftTimeReSModel;
import com.eot_app.time_shift_pkg.ShiftTimeReqModel;
import com.eot_app.utility.AppCenterLogs;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.db.OfflineDataController;
import com.eot_app.utility.settings.contractdb.ContractReq;
import com.eot_app.utility.settings.contractdb.ContractRes;
import com.eot_app.utility.settings.equipmentdb.Equipment;
import com.eot_app.utility.settings.equipmentdb.EquipmentListReq;
import com.eot_app.utility.util_interfaces.FIrstSyncPreference;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.hypertrack.hyperlog.HyperLog;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * * created by shivani vani in FEB 2022**
 * */

@SuppressLint("SpecifyJobSchedulerIdRange")
public class SyncDataJobService extends JobService {

    private final int updateLimit = AppConstant.LIMIT_HIGH;
    private int count;
    private int updateIndex;
    private String startJobSyncTime="";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(getClass().getName(), "onStartJob: ");
        Log.v("MainSync","Sync completed "+" --" +"onStartJob" );

        doBackgroundWork(params);
        return true;
    }

    @SuppressLint("MissingPermission")
    private void doBackgroundWork(final JobParameters params) {
        new Thread(() -> {
            OfflineDbSync();
            jobFinished(params, false);
        }).start();
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(getClass().getName(), "onStopJob: ");
        return true;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void startSyncFromStatus() {
        int status_no = App_preference.getSharedprefInstance().getFirstSyncState();
        switch (status_no) {
            case 0:
                getBrandList();
                break;
            case 1:
//               og.v("MainSync","startJobSyncTime"+" --" +startJobSyncTime);
                getAttachmentSyncService();//get attachment list
                break;
            case 2:
                startJobSyncTime=AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
                App_preference.getSharedprefInstance().setJobStartSyncTime(startJobSyncTime);
                Log.v("MainSync","startJobSyncTime"+" --" +startJobSyncTime);
                getJobSyncService();//get job list
                break;
            case 3:
                getClientSyncService();//sync client list
                break;
            case 4:
                getContactSyncService();//sync contact list
                break;
            case 5:
                getSiteSyncService();//get Site list
                break;
            case 6:
                getChatgrpUserSyncService();//get chat user list
                break;
            case 7:
                getAppointmentSyncService();//get appointment  list
                break;
            case 8:
                getInvoiceItemList();//get inventory item's
                break;
            case 9:
                getInvoiceTaxesList();//get taxes for invoice item's
                break;
            case 10:
                getAuditList();
                break;
            case 11:
                getContractList();
                break;
            case 12:
                getEquipmentList();
                break;
            case 13:
                getTaxLocations();
                break;
            case 14:
                getJobTimeShiftList();
                break;
            case 15:
                getCustomForm();
                break;
            case 16:
                goHomePage();
                App_preference.getSharedprefInstance().setFirstSyncState(0);
                stopSelf();
                break;
        }
    }

    private void goHomePage() {
        Intent local = new Intent();
        local.setAction("updateUi");
        this.sendBroadcast(local);
        Log.v("MainSync","Sync completed "+" --" +"All Sync Done");
    }

    private JsonObject getJsonObject(String params) {
        JsonParser parser = new JsonParser();
        return parser.parse(params).getAsJsonObject();
    }

    private void getAttachmentSyncService() {
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.LOGIN_MODULE,
                    ActivityLogController.LOGIN_JOB_SYNC,
                    ActivityLogController.LOGIN_MODULE
            );
            if (FIrstSyncPreference.getSharedprefInstance().getJobIndexValue()!=0)
            {
                updateIndex=FIrstSyncPreference.getSharedprefInstance().getJobIndexValue();
                FIrstSyncPreference.getSharedprefInstance().setJobIndexValue(0);
            }

            JobListRequestModel jobListRequestModel = new JobListRequestModel(Integer.parseInt(App_preference.getSharedprefInstance().getLoginRes().getUsrId()),
                    updateLimit, updateIndex);
            String data = new Gson().toJson(jobListRequestModel);
            Log.d("Apitimetracking","getUserJobList:-"+data);
            Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
            ApiClient.getservices().eotServiceCall(Service_apis.getSyncJobAttachments, AppUtility.getApiHeaders(), getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {
                        }
                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getUserJobList","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                            Log.e("responce:", jsonObject.toString());
                            if (jsonObject.get("success").getAsBoolean()) {
                                count = jsonObject.get("count").getAsInt();
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type listType = new TypeToken<List<Attachments>>() {
                                }.getType();
                                List<Attachments> data = new Gson().fromJson(convert, listType);
                                addAttachmentsInToDB(data);
                            }
                        }
                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("Network Error :", e.toString());
                    //        AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getUserJobList",e.getMessage(),"SyncDataJobService","");
                            errorMsg(e.toString());
                            FIrstSyncPreference.getSharedprefInstance().setJobIndexValue(updateIndex);
                        }
                        @Override
                        public void onComplete() {
                            if ((updateIndex + updateLimit) <= count) {
                                updateIndex += updateLimit;
                                getAttachmentSyncService();
                            } else {
//                                if (count != 0) {
//                                    Log.v("MainSync","startJobSyncTimeCI"+" --" +startJobSyncTime);
//                                    if(!startJobSyncTime.isEmpty()){
//                                        App_preference.getSharedprefInstance().setJobSyncTime(startJobSyncTime);
//                                    }
//                                    else{
//                                        App_preference.getSharedprefInstance().setJobSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
//                                    }
//                                    App_preference.getSharedprefInstance().setJobStartSyncTime("");
//                                    Log.v("MainSync","startJobSyncTimeC"+" --" +App_preference.getSharedprefInstance().getJobSyncTime());
//                                }
                                updateIndex = 0;
                                count = 0;
//                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().deleteJobByIsDelete();

//                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().deleteJobStatusNot(
//                                        "1","2","3","4","5","6","7","8","9","10","11","12"
//                                );
                                App_preference.getSharedprefInstance().setFirstSyncState(2);
                                startSyncFromStatus();
                                Log.v("MainSync","startJobSyncTimeCR"+" --" +App_preference.getSharedprefInstance().getJobSyncTime());
                                Log.v("MainSync","Sync completed "+" --" +"JobSync Done");
                            }
                        }
                    });
        }
    }
    private void getJobSyncService() {
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.LOGIN_MODULE,
                    ActivityLogController.LOGIN_JOB_SYNC,
                    ActivityLogController.LOGIN_MODULE
            );
            if (FIrstSyncPreference.getSharedprefInstance().getJobIndexValue()!=0)
            {
                updateIndex=FIrstSyncPreference.getSharedprefInstance().getJobIndexValue();
                FIrstSyncPreference.getSharedprefInstance().setJobIndexValue(0);
            }

            JobListRequestModel jobListRequestModel = new JobListRequestModel(Integer.parseInt(App_preference.getSharedprefInstance().getLoginRes().getUsrId()),
                    updateLimit, updateIndex, App_preference.getSharedprefInstance().getJobSyncTime());
            String data = new Gson().toJson(jobListRequestModel);
            Log.d("Apitimetracking","getUserJobList:-"+data);
            Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
            ApiClient.getservices().eotServiceCall(Service_apis.getUserJobList, AppUtility.getApiHeaders(), getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {
                        }
                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getUserJobList","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                            Log.e("responce:", jsonObject.toString());
                            if (jsonObject.get("success").getAsBoolean()) {
                                count = jsonObject.get("count").getAsInt();
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type listType = new TypeToken<List<Job>>() {
                                }.getType();
                                List<Job> data = new Gson().fromJson(convert, listType);
                                addJobInToDB(data);
                            }
                        }
                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("Network Error :", e.toString());
                    //        AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getUserJobList",e.getMessage(),"SyncDataJobService","");
                            errorMsg(e.toString());
                            FIrstSyncPreference.getSharedprefInstance().setJobIndexValue(updateIndex);
                        }
                        @Override
                        public void onComplete() {
                            if ((updateIndex + updateLimit) <= count) {
                                updateIndex += updateLimit;
                                getJobSyncService();
                            } else {
                                if (count != 0) {
                                    Log.v("MainSync","startJobSyncTimeCI"+" --" +startJobSyncTime);
                                    if(!startJobSyncTime.isEmpty()){
                                        App_preference.getSharedprefInstance().setJobSyncTime(startJobSyncTime);
                                    }
                                    else{
                                        App_preference.getSharedprefInstance().setJobSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                    }
                                    App_preference.getSharedprefInstance().setJobStartSyncTime("");
                                    Log.v("MainSync","startJobSyncTimeC"+" --" +App_preference.getSharedprefInstance().getJobSyncTime());
                                }
                                updateIndex = 0;
                                count = 0;
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().deleteJobByIsDelete();

//                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().deleteJobStatusNot(
//                                        "1","2","3","4","5","6","7","8","9","10","11","12"
//                                );
                                App_preference.getSharedprefInstance().setFirstSyncState(3);
                                startSyncFromStatus();
                                Log.v("MainSync","startJobSyncTimeCR"+" --" +App_preference.getSharedprefInstance().getJobSyncTime());
                                Log.v("MainSync","Sync completed "+" --" +"JobSync Done");
                            }
                        }
                    });
        }
    }

    private void addJobInToDB(List<Job> data) {
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().inserJob(data);
    }
    private void addAttachmentsInToDB(List<Attachments> data) {
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().insertAttachments(data);
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().deleteAttachments();
    }

    private void getAppointmentSyncService() {
        int userId = Integer.parseInt(App_preference.getSharedprefInstance().getLoginRes().getUsrId());

        if (FIrstSyncPreference.getSharedprefInstance().getAppoinmentIndexValue()!=0)
        {
            updateIndex=FIrstSyncPreference.getSharedprefInstance().getAppoinmentIndexValue();
            FIrstSyncPreference.getSharedprefInstance().setAppoinmentIndexValue(0);
        }
        AppointmentListReq model = new AppointmentListReq(
                userId,
                updateLimit, updateIndex
                , App_preference.getSharedprefInstance().getAppointmentSyncTime());
        String data = new Gson().toJson(model);
        Log.d("Data error", "error" + data);
        HyperLog.i("TAG", "Data error" + data);
        Log.d("Apitimetracking","getAppointmentUserList:-"+data);
        Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
        ApiClient.getservices().eotServiceCall(Service_apis.getAppointmentUserList, AppUtility.getApiHeaders(), getJsonObject(data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull JsonObject jsonObject) {
                        AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getAppointmentUserList","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                        if (jsonObject.get("success").getAsBoolean()) {
                            count = jsonObject.get("count").getAsInt();
                            String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                            Type listType = new TypeToken<List<Appointment>>() {
                            }.getType();
                            List<Appointment> data = new Gson().fromJson(convert, listType);
                            addAppointmentToDB(data);
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.d("error", e.getMessage());
                  //      AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getAppointmentUserList",e.getMessage(),"SyncDataJobService","");
                        HyperLog.i("TAG", "Data error" + e.getMessage());
                        Log.e("Network Error :", e.toString());
                        Log.e("Network Error :", e.getLocalizedMessage());
                        FIrstSyncPreference.getSharedprefInstance().setAppoinmentIndexValue(0);
                        errorMsg(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        if ((updateIndex + updateLimit) <= count) {
                            updateIndex += updateLimit;
                            getAppointmentSyncService();
                        } else {
                            if (count != 0) {
                                App_preference.getSharedprefInstance().setAppointmentSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                            }
                            updateIndex = 0;
                            count = 0;
                            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().deleteAppointmentByIsDelete();
                            App_preference.getSharedprefInstance().setFirstSyncState(8);
                            startSyncFromStatus();
                            Log.v("MainSync","Sync completed "+" --" +"appointment Sync Done");
                        }
                    }
                });
    }

    private void addAppointmentToDB(List<Appointment> data) {
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().insertAppointments(data);
    }

    /***get invoice Invetrt Item list****/
    private void getInvoiceItemList() {
        Log.e("data--->>>", "data--->>>");
        if (AppUtility.isInternetConnected()) {
            if (FIrstSyncPreference.getSharedprefInstance().getInvoiceItemIndexValue()!=0)
            {
                updateIndex=FIrstSyncPreference.getSharedprefInstance().getInvoiceItemIndexValue();
                FIrstSyncPreference.getSharedprefInstance().setInvoiceItemIndexValue(0);
            }
            Inventry_ReQ_Model inventry_model = new Inventry_ReQ_Model(Integer.parseInt(App_preference.getSharedprefInstance().getLoginRes().getCompId()),
                    "",

                    updateLimit, updateIndex, App_preference.getSharedprefInstance().getInventryItemSyncTime());//

            String data = new Gson().toJson(inventry_model);
            Log.d("Apitimetracking","getItemList:-"+data);
            Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
            ApiClient.getservices().eotServiceCall(Service_apis.getItemList, AppUtility.getApiHeaders(), getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getItemList","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                            if (jsonObject.get("success").getAsBoolean()) {
                                count = jsonObject.get("count").getAsInt();
                                Log.e("Count", "" + count);
                                if (count < 2000) {
                                    String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                    Type listType = new TypeToken<List<Inventry_ReS_Model>>() {
                                    }.getType();
                                    List<Inventry_ReS_Model> inventryitemlist = new Gson().fromJson(convert, listType);
                                    addInvoiceItemInDB(inventryitemlist);
                                    Log.e("Count", "" + count);
                                }
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("Network Error :", e.toString());
                   //         AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getItemList",e.getMessage(),"SyncDataJobService","");
                            Log.e("TAG : error----", e.getMessage());
                            FIrstSyncPreference.getSharedprefInstance().setInvoiceItemIndexValue(0);
                            errorMsg(e.toString());
                        }

                        @Override
                        public void onComplete() {
                            Log.e("TAG onComplete------", "onComplete");
//                            if (count < 2000) {
                            if (count < 2000 && (updateIndex + updateLimit) <= count) {
                                updateIndex += updateLimit;
                                getInvoiceItemList();
                            } else {
                                if (count != 0) {
                                    App_preference.getSharedprefInstance()
                                            .setInventryItemSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                }
                                updateIndex = 0;
                                count = 0;
                                App_preference.getSharedprefInstance().setFirstSyncState(9);
                                startSyncFromStatus();
                                Log.v("MainSync","Sync completed "+" --" +"Invoice Sync Done");
                            }
                        }
                    });
        }
    }


    private void addInvoiceTaxexListInDB(List<Tax> temptaxList) {
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).invoiceTaxDao().insertInvoiceTaxes(temptaxList);
    }

    private void addInvoiceItemInDB(List<Inventry_ReS_Model> inventryitemlist) {
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).invoiceItemDao().insertInvebtryItems(inventryitemlist);
    }

    /***get admin one-to-one chat group chat user list***/
    private void getChatgrpUserSyncService() {
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.LOGIN_MODULE,
                    ActivityLogController.LOGIN_SYNC_USER_LIST_CHAT,
                    ActivityLogController.LOGIN_MODULE
            );
            if (FIrstSyncPreference.getSharedprefInstance().getChatgroupIndexValue()!=0)
            {
                updateIndex=FIrstSyncPreference.getSharedprefInstance().getChatgroupIndexValue();
                FIrstSyncPreference.getSharedprefInstance().setChatGroupIndexValue(0);
            }
            UserChatListModelReq model = new UserChatListModelReq(Integer.parseInt(App_preference.getSharedprefInstance().getLoginRes().getCompId()),
                    updateLimit, updateIndex
                    , App_preference.getSharedprefInstance().getUsersSyncTime());
            String data = new Gson().toJson(model);
            Log.d("Apitimetracking","groupUserListForChat:-"+data);
            Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
            ApiClient.getservices().eotServiceCall(Service_apis.groupUserListForChat,
                    AppUtility.getApiHeaders(), getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            AppCenterLogs.addLogToAppCenterOnAPIFail("Api","groupUserListForChat","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                            Log.e("", "");
                            if (jsonObject.get("success").getAsBoolean()) {
                                count = jsonObject.get("count").getAsInt();
                                String jsonString = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type typeList = new TypeToken<List<UserChatModel>>() {
                                }.getType();
                                List<UserChatModel> chatList = new Gson().fromJson(jsonString, typeList);
                                addChatUserListDataToDB(chatList);
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("Network Error :", e.toString());
                        //    AppCenterLogs.addLogToAppCenterOnAPIFail("Api","groupUserListForChat",e.getMessage(),"SyncDataJobService","");
                          FIrstSyncPreference.getSharedprefInstance().setChatGroupIndexValue(0);
                            errorMsg(e.toString());
                        }

                        @Override
                        public void onComplete() {
                            if ((updateIndex + updateLimit) <= count) {
                                updateIndex += updateLimit;
                                getChatgrpUserSyncService();
                            } else {
                                if (count != 0) {
                                    App_preference.getSharedprefInstance().setUsersSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                }
                                updateIndex = 0;
                                count = 0;
                                App_preference.getSharedprefInstance().setFirstSyncState(7);
                                startSyncFromStatus();
                                Log.v("MainSync","Sync completed "+" --" +"Chat Grup Sync Done");

                            }
                        }
                    });
        }
    }

    private void getSiteSyncService() {
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.LOGIN_MODULE,
                    ActivityLogController.LOGIN_CLIENT_SITE_SYN,
                    ActivityLogController.LOGIN_MODULE
            );
            if (FIrstSyncPreference.getSharedprefInstance().getSiteIndexValue()!=0)
            {
                updateIndex=FIrstSyncPreference.getSharedprefInstance().getSiteIndexValue();
                FIrstSyncPreference.getSharedprefInstance().setSiteIndexValue(0);
            }
            Client_Request_model client_request_model = new Client_Request_model(Integer.parseInt(App_preference.getSharedprefInstance().getLoginRes().getCompId()),
                    updateLimit, updateIndex, App_preference.getSharedprefInstance().getSiteSyncTime());
//            Client_Request_model client_request_model = new Client_Request_model(Integer.parseInt(App_preference.getSharedprefInstance().getLoginRes().getCompId()),
//                    5000, 0, App_preference.getSharedprefInstance().getSiteSyncTime());

            String data = new Gson().toJson(client_request_model);
            Log.d("Apitimetracking","getClientSiteSink:-"+data);
            Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
            ApiClient.getservices().eotServiceCall(Service_apis.getClientSiteSink, AppUtility.getApiHeaders(), getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getClientSiteSink","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                            Log.e("SiteDataList", jsonObject.toString());
                            if (jsonObject.get("success").getAsBoolean()) {
                                count = jsonObject.get("count").getAsInt();
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type listType = new TypeToken<List<Site_model>>() {
                                }.getType();
                                List<Site_model> data = new Gson().fromJson(convert, listType);
                                addSiteDataToDB(data);
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("Network Error :", e.toString());
                       //     AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getClientSiteSink",e.getMessage(),"SyncDataJobService","");
                          FIrstSyncPreference.getSharedprefInstance().setSiteIndexValue(0);
                            errorMsg(e.toString());
                        }

                        @Override
                        public void onComplete() {
                            if ((updateIndex + updateLimit) <= count) {
                                updateIndex += updateLimit;
                                getSiteSyncService();
                            } else {
                                if (count != 0) {
                                    App_preference.getSharedprefInstance().setSiteSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                }
                                updateIndex = 0;
                                count = 0;
                                /* ***very very important sonam**/
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).sitemodel().deleteSiteByIsDelete();


                                App_preference.getSharedprefInstance().setFirstSyncState(6);
                                startSyncFromStatus();
                                Log.v("MainSync","Sync completed "+" --" +"site Sync Done");

                            }
                        }
                    });
        }
    }

    private void addSiteDataToDB(List<Site_model> data) {
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).sitemodel().insertSiteList(data);
    }

    private void addChatUserListDataToDB(List<UserChatModel> data) {
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).userChatModel().insertChatUserList(data);
    }

    private void getContactSyncService() {
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.LOGIN_MODULE,
                    ActivityLogController.LOGIN_CLIENT_CONTACT_SYN,
                    ActivityLogController.LOGIN_MODULE
            );
            if (FIrstSyncPreference.getSharedprefInstance().getContactIndexValue()!=0)
            {
                updateIndex=FIrstSyncPreference.getSharedprefInstance().getContactIndexValue();
                FIrstSyncPreference.getSharedprefInstance().setContactIndexValue(0);
            }
            Client_Request_model client_request_model = new Client_Request_model(Integer.parseInt(App_preference.getSharedprefInstance().getLoginRes().getCompId()),
                    updateLimit, updateIndex, App_preference.getSharedprefInstance().getContactSyncTime());

            String data = new Gson().toJson(client_request_model);
            Log.d("Apitimetracking","getClientContactSink"+data);
            Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
            ApiClient.getservices().eotServiceCall(Service_apis.getClientContactSink, AppUtility.getApiHeaders(), getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getClientContactSink","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                            if (jsonObject.get("success").getAsBoolean()) {
                                count = jsonObject.get("count").getAsInt();
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type listType = new TypeToken<List<ContactData>>() {
                                }.getType();
                                List<ContactData> data = new Gson().fromJson(convert, listType);
                                addContactDataToDB(data);
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("Network Error :", e.toString());
                        //    AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getClientContactSink",e.getMessage(),"SyncDataJobService","");
                            FIrstSyncPreference.getSharedprefInstance().setContactIndexValue(0);
                            errorMsg(e.toString());
                        }

                        @Override
                        public void onComplete() {
                            if ((updateIndex + updateLimit) <= count) {
                                updateIndex += updateLimit;
                                getContactSyncService();
                            } else {
                                if (count != 0) {
                                    App_preference.getSharedprefInstance().setContactSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                }
                                updateIndex = 0;
                                count = 0;
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).contactModel().deleteContactByIsDelete();
                                App_preference.getSharedprefInstance().setFirstSyncState(5);
                                startSyncFromStatus();
                                Log.v("MainSync","Sync completed "+" --" +"Contact Sync Done");

                            }
                        }
                    });
        }
    }

    private void addContactDataToDB(List<ContactData> data) {
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).contactModel().insertContactList(data);
    }

    private void getClientSyncService() {
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.LOGIN_MODULE,
                    ActivityLogController.LOGIN_CLIENT_SYNC,
                    ActivityLogController.LOGIN_MODULE
            );
            if (FIrstSyncPreference.getSharedprefInstance().getclilentIndexValue()!=0)
            {
                updateIndex=FIrstSyncPreference.getSharedprefInstance().getclilentIndexValue();
                FIrstSyncPreference.getSharedprefInstance().setclientIndexValue(0);
            }
            Client_Request_model client_request_model = new Client_Request_model(
                    Integer.parseInt(App_preference.getSharedprefInstance().getLoginRes().getCompId()),
                    updateLimit, updateIndex, App_preference.getSharedprefInstance().getClientSyncTime());

            String data = new Gson().toJson(client_request_model);
            Log.d("Apitimetracking","getClientSink:-"+data);
            Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
            ApiClient.getservices().eotServiceCall(Service_apis.getClientSink, AppUtility.getApiHeaders(), getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getClientSink","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                            if (jsonObject.get("success").getAsBoolean()) {
                                count = jsonObject.get("count").getAsInt();
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type listType = new TypeToken<List<Client>>() {
                                }.getType();
                                List<Client> data = new Gson().fromJson(convert, listType);
                                addClientDataToDB(data);
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("Network Error :", e.toString());
                         //   AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getClientSink",e.getMessage(),"SyncDataJobService","");
                            FIrstSyncPreference.getSharedprefInstance().setclientIndexValue(0);
                            errorMsg(e.toString());
                        }

                        @Override
                        public void onComplete() {
                            if ((updateIndex + updateLimit) <= count) {
                                updateIndex += updateLimit;
                                getClientSyncService();
                            } else {
                                if (count != 0) {
                                    App_preference.getSharedprefInstance().setClientSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                }
                                updateIndex = 0;
                                count = 0;
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).clientModel().deleteClientByIsDelete();
                                App_preference.getSharedprefInstance().setFirstSyncState(4);
                                startSyncFromStatus();
                                Log.v("MainSync","Sync completed "+" --" +"Client Sync Done");
                            }
                        }
                    });
        }
    }

    private void addClientDataToDB(List<Client> data) {
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).clientModel().insertUser(data);
    }

    private void getInvoiceTaxesList() {
        if (FIrstSyncPreference.getSharedprefInstance().getInvoiceTextIndexValue()!=0)
        {
            updateIndex=FIrstSyncPreference.getSharedprefInstance().getInvoiceTextIndexValue();
            FIrstSyncPreference.getSharedprefInstance().setInvoiceTextIndexValue(0);
        }
        TaxList_ReQ_Model model = new TaxList_ReQ_Model(App_preference.getSharedprefInstance().getLoginRes().getCompId()
                , updateLimit, updateIndex, App_preference.getSharedprefInstance().getInventryTaxesSyncTime());
        String data = new Gson().toJson(model);
        Log.d("Apitimetracking","getTaxList:-"+data);
        Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
        ApiClient.getservices().eotServiceCall(Service_apis.getTaxList, AppUtility.getApiHeaders(), getJsonObject(data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull JsonObject jsonObject) {
                        AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getTaxList","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                        if (jsonObject.get("success").getAsBoolean()) {
                            count = jsonObject.get("count").getAsInt();
                            String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                            Type listType = new TypeToken<List<Tax>>() {
                            }.getType();
                            List<Tax> temptaxList = new Gson().fromJson(convert, listType);
                            addInvoiceTaxexListInDB(temptaxList);
                            Log.e("data----", "");
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.e("Network Error :", e.toString());
                     //   AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getTaxList",e.getMessage(),"SyncDataJobService","");
                        Log.e("ERROR", e.getMessage());
                        FIrstSyncPreference.getSharedprefInstance().setInvoiceTextIndexValue(0);
                        errorMsg(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("", "");
                        if ((updateIndex + updateLimit) <= count) {
                            updateIndex += updateLimit;
                            getInvoiceTaxesList();
                        } else {
                            if (count != 0) {
                                App_preference.getSharedprefInstance().setInventryTaxesSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                            }
                            updateIndex = 0;
                            count = 0;
                            App_preference.getSharedprefInstance().setFirstSyncState(10);
                            startSyncFromStatus();
                        }
                    }
                });
    }

    private void getCustomForm()
    {
        if (FIrstSyncPreference.getSharedprefInstance().getCustomIndexValue()!=0)
        {
            updateIndex=FIrstSyncPreference.getSharedprefInstance().getCustomIndexValue();
            FIrstSyncPreference.getSharedprefInstance().setCustomIndexValue(0);
        }
         JsonObject jsonObject=new JsonObject();
         jsonObject.addProperty("updateIndex",updateIndex);
         jsonObject.addProperty("updateLimit",updateLimit);
         jsonObject.addProperty("datetime",App_preference.getSharedprefInstance().getShiftTimeSyncTime());

        String data = new Gson().toJson(jsonObject);
        Log.d("Apitimetracking","getFormList:-"+data);
        Log.d("time",AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
       ApiClient.getservices().eotServiceCall(Service_apis.getFormList,AppUtility.getApiHeaders(),AppUtility.getJsonObject(data))
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<JsonObject>() {
                   @Override
                   public void onSubscribe(@NotNull Disposable d) {
                   }

                   @Override
                   public void onNext(@NotNull JsonObject jsonObject) {
                       if (jsonObject.get("success").getAsBoolean() && jsonObject.get("data").getAsJsonArray().size() > 0) {
                           if (jsonObject.get("count") != null && jsonObject.get("count").getAsInt() > 0) {
                               count = jsonObject.get("count").getAsInt();
                               String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                               Type listType = new TypeToken<List<CustomFormListOffline>>() {
                               }.getType();
                               ArrayList<CustomFormListOffline> formList = new Gson().fromJson(convert, listType);
                               Executor executors=Executors.newSingleThreadExecutor();
                               executors.execute(new Runnable() {
                                   @Override
                                   public void run() {
                                       AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).customFormListOfflineDao().delete();
                                       for (int i=0;i<formList.size();i++)
                                       {
                                           String jtId = formList.get(i).getJtId();
                                           if(jtId!=null){
                                               String[] split = jtId.split(",");
                                               if (split.length>1){
                                                   for(int j=0;j<split.length;j++) {
                                                       CustomFormListOffline customForms = formList.get(i);
                                                       CustomFormListOffline customFormListOffline = new CustomFormListOffline(customForms.getFrmId(),split[j].trim(),
                                                               customForms.getFrmnm(), customForms.getEvent(), customForms.getTab(), customForms.getMandatory(), customForms.getTotalQues());

                                                       AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).customFormListOfflineDao().insert(customFormListOffline);
                                                   }
                                               }else {
                                                   AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).customFormListOfflineDao().insert(formList.get(i));
                                               }
                                           }
                                       }
                                   }
                               });
                           }
                       }
                   }

                   @Override
                   public void onError(@NotNull Throwable e) {
                       Log.e("Network Error :", e.toString());
                       FIrstSyncPreference.getSharedprefInstance().setCustomIndexValue(0);
                       errorMsg(e.toString());
                   }

                   @Override
                   public void onComplete() {
                       if ((updateIndex + updateLimit) <= count) {
                           updateIndex += updateLimit;
                           getCustomForm();
                       } else {
                           if (count != 0) {
                               App_preference.getSharedprefInstance().setShiftTimeSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                           }
                           updateIndex = 0;
                           count = 0;
                           App_preference.getSharedprefInstance().setFirstSyncState(16);
                           startSyncFromStatus();
                           Log.v("MainSync","Sync completed "+" --" +"job time Sync Done");
                       }
                   }
               });
    }

    private void getJobTimeShiftList() {
        ShiftTimeReqModel equipmentListReq = new ShiftTimeReqModel(
                App_preference.getSharedprefInstance().getShiftTimeSyncTime(), updateLimit, updateIndex);
        String data = new Gson().toJson(equipmentListReq);
        Log.d("Apitimetracking","getShiftList:- "+data);
        Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
        ApiClient.getservices().eotServiceCall(Service_apis.getShiftList, AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull JsonObject jsonObject) {
                        AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getShiftList","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                        Log.e("TaxLOcationList:", jsonObject.toString());
                        if (jsonObject.get("success").getAsBoolean()) {
                            count = jsonObject.get("count").getAsInt();
                            String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                            Type listType = new TypeToken<List<ShiftTimeReSModel>>() {
                            }.getType();
                            List<ShiftTimeReSModel> equipmentList = new Gson().fromJson(convert, listType);
                            if (equipmentList != null)
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).shiftTimeDao().insertAllShiftTimeList(equipmentList);
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.e("Network Error :", e.toString());
                 //       AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getShiftList",e.getMessage(),"SyncDataJobService","");
                        /* *****/
                        errorMsg(e.toString());

                    }

                    @Override
                    public void onComplete() {
                        if ((updateIndex + updateLimit) <= count) {
                            updateIndex += updateLimit;
                            getJobTimeShiftList();
                        } else {
                            if (count != 0) {
                                App_preference.getSharedprefInstance().setShiftTimeSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                            }
                            updateIndex = 0;
                            count = 0;
                            App_preference.getSharedprefInstance().setFirstSyncState(15);
                            startSyncFromStatus();
                            Log.v("MainSync","Sync completed "+" --" +"job time Sync Done");

                        }
                    }
                });


    }


    private void getTaxLocations() {
        TaxReqModel equipmentListReq = new TaxReqModel(
                App_preference.getSharedprefInstance().getTaxLocationSyncTime());
        String data = new Gson().toJson(equipmentListReq);
        Log.d("Apitimetracking:- ","getAllEquipments"+data);
        Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
        ApiClient.getservices().eotServiceCall(Service_apis.getLocationList, AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull JsonObject jsonObject) {
                        AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getLocationList","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                        Log.e("TaxLOcationList:", jsonObject.toString());
                        if (jsonObject.get("success").getAsBoolean()) {
                            count = jsonObject.get("count").getAsInt();
                            String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                            Type listType = new TypeToken<List<TaxesLocation>>() {
                            }.getType();
                            List<TaxesLocation> equipmentList = new Gson().fromJson(convert, listType);
                            if (equipmentList != null)
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).taxesLocationDao().insertAllLocationList(equipmentList);
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.e("Network Error :", e.toString());
                     //   AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getLocationList",e.getMessage(),"SyncDataJobService","");
                        /* *****/
                        errorMsg(e.toString());

                    }

                    @Override
                    public void onComplete() {
                        if ((updateIndex + updateLimit) <= count) {
                            updateIndex += updateLimit;
                            getTaxLocations();
                        } else {
                            if (count != 0) {
                                App_preference.getSharedprefInstance().setTaxLocationSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                            }
                            updateIndex = 0;
                            count = 0;
                            App_preference.getSharedprefInstance().setFirstSyncState(14);
                            startSyncFromStatus();
                            Log.v("MainSync","Sync completed "+" --" +"tax location Sync Done");

                        }
                    }
                });
    }

    private void getEquipmentList() {
        if (FIrstSyncPreference.getSharedprefInstance().getEquipmentIndexValue()!=0)
        {
            updateIndex=FIrstSyncPreference.getSharedprefInstance().getEquipmentIndexValue();
            FIrstSyncPreference.getSharedprefInstance().setEquipmentIndexValue(0);
        }
        EquipmentListReq equipmentListReq = new EquipmentListReq(
                updateLimit, updateIndex, "", App_preference.getSharedprefInstance().getAllEquipmentSyncTime());
        String data = new Gson().toJson(equipmentListReq);
        Log.d("Apitimetracking","getAllEquipments:-"+data);
        Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
        ApiClient.getservices().eotServiceCall(Service_apis.getAllEquipments, AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull JsonObject jsonObject) {
                        Log.e("getAllEquipments", "Sync");
                        AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getAllEquipments","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                        Log.d("equipmentlist", jsonObject.toString());
                        if (jsonObject.get("success").getAsBoolean()) {
                            count = jsonObject.get("count").getAsInt();
                            String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                            Type listType = new TypeToken<List<Equipment>>() {
                            }.getType();
                            List<Equipment> equipmentList = new Gson().fromJson(convert, listType);
                            if (equipmentList != null)
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().insertEquipmentList(equipmentList);

                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.e("Network Error :", e.toString());
                     //   AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getAllEquipments",e.getMessage(),"SyncDataJobService","");
                        /* *****/
                        FIrstSyncPreference.getSharedprefInstance().setEquipmentIndexValue(0);
                        errorMsg(e.toString());

                    }

                    @Override
                    public void onComplete() {
                        if ((updateIndex + updateLimit) <= count) {
                            updateIndex += updateLimit;
                            getEquipmentList();
                        } else {
                            if (count != 0) {
                                App_preference.getSharedprefInstance().setAllEquipmentSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                            }
                            updateIndex = 0;
                            count = 0;
                            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().deleteEquipmentByIsDelete();
                            App_preference.getSharedprefInstance().setFirstSyncState(13);
                            startSyncFromStatus();
                            Log.v("MainSync","Sync completed "+" --" +"quipment Sync Done");

                        }
                    }
                });
    }

    /***get Contract List****/
    private void getContractList() {
        if (FIrstSyncPreference.getSharedprefInstance().getContractIndexValue()!=0)
        {
            updateIndex=FIrstSyncPreference.getSharedprefInstance().getContractIndexValue();
            FIrstSyncPreference.getSharedprefInstance().setContractIndexValue(0);
        }
        ContractReq contractReq = new ContractReq(
                updateLimit, updateIndex, "", App_preference.getSharedprefInstance().getContractSyncTime());
        String data = new Gson().toJson(contractReq);
        Log.d("Apitimetracking","getContractList:-"+data);
        Log.d("Apitimetracking","time"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
        ApiClient.getservices().eotServiceCall(Service_apis.getContractList, AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull JsonObject jsonObject) {
                        AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getContractList","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                        Log.d("contractList", jsonObject.toString());
                        if (jsonObject.get("success").getAsBoolean()) {
                            count = jsonObject.get("count").getAsInt();
                            String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                            Type listType = new TypeToken<List<ContractRes>>() {
                            }.getType();
                            List<ContractRes> contractList = new Gson().fromJson(convert, listType);
                            if (contractList != null)
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).contractDao().insertContractList(contractList);
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.e("Network Error :", e.toString());
                        /* *****/
                  //      AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getContractList",e.getMessage(),"SyncDataJobService","");
                        FIrstSyncPreference.getSharedprefInstance().setContractIndexValue(0);
                        errorMsg(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        if ((updateIndex + updateLimit) <= count) {
                            updateIndex += updateLimit;
                            getContractList();
                        } else {
                            if (count != 0) {
                                App_preference.getSharedprefInstance().setContractSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                            }
                            updateIndex = 0;
                            count = 0;
                            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).contractDao().deleteContractByIsDelete();
                            App_preference.getSharedprefInstance().setFirstSyncState(12);
                            startSyncFromStatus();
                            Log.v("MainSync","Sync completed "+" --" +"contract Sync Done");

                        }
                    }
                });
    }

    private void getAuditList() {
        if (FIrstSyncPreference.getSharedprefInstance().getAuditIndexValue()!=0)
        {
            updateIndex=FIrstSyncPreference.getSharedprefInstance().getAuditIndexValue();
            FIrstSyncPreference.getSharedprefInstance().setAuditIndexValue(0);
        }
        AuditListRequestModel auditListRequestModel = new AuditListRequestModel(Integer.parseInt(App_preference.getSharedprefInstance().getLoginRes().getUsrId()),
                updateLimit, updateIndex, App_preference.getSharedprefInstance().getAuditSyncTime());
        String data = new Gson().toJson(auditListRequestModel);
        Log.d("Apitimetracking","getAuditList:-"+data);
        Log.d("Apitimetracking","time:-"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
        ApiClient.getservices().eotServiceCall(Service_apis.getAuditList, AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull JsonObject jsonObject) {
                        Log.d("mahi", jsonObject.toString());
                        AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getAuditList","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                        if (jsonObject.get("success").getAsBoolean()) {
                            count = jsonObject.get("count").getAsInt();
                            String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                            Type listType = new TypeToken<List<AuditList_Res>>() {
                            }.getType();
                            List<AuditList_Res> auditList = new Gson().fromJson(convert, listType);
                            Log.e("data----", "");
                            addAuditListInDB(auditList);
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.e("Network Error :", e.toString());
               //         AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getAuditList",e.getMessage(),"SyncDataJobService","");
                        FIrstSyncPreference.getSharedprefInstance().setAuditIndexValue(0);
                        errorMsg(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("", "");
                        if ((updateIndex + updateLimit) <= count) {
                            updateIndex += updateLimit;
                            getAuditList();
                        } else {
                            if (count != 0) {
                                App_preference.getSharedprefInstance().setAuditSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                            }
                            updateIndex = 0;
                            count = 0;
                            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).auditDao().deleteJobByIsDelete();
                            //     AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).auditDao().deleteAuditStatusNot();
                            App_preference.getSharedprefInstance().setFirstSyncState(11);
                            startSyncFromStatus();
                            Log.v("MainSync","Sync completed "+" --" +"Audit List Sync Done");
                        }
                    }
                });
    }

    private void addAuditListInDB(List<AuditList_Res> auditList) {
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).auditDao().inserAuditList(auditList);
    }

    public void errorMsg(String errorMessage) {
            Intent local = new Intent();
            local.setAction("showAlert");
            this.sendBroadcast(local);
    }


   /* public void retryCall() {
        int status_no = App_preference.getSharedprefInstance().getFirstSyncState();
        if (status_no == 0) {
            OfflineDbSync();
        } else {
            startSyncFromStatus();
        }
    }*/

    public void OfflineDbSync() {
        OfflineDataController.getInstance().fromFirstTimeSyncCall((success_no, msg) -> startSyncFromStatus());
    }
   /**get Brand dataList**/
   private void getBrandList() {
       if (FIrstSyncPreference.getSharedprefInstance().getAuditIndexValue()!=0)
       {
           updateIndex=FIrstSyncPreference.getSharedprefInstance().getAuditIndexValue();
           FIrstSyncPreference.getSharedprefInstance().setAuditIndexValue(0);
       }
       GetBrandListReqModel brandListReqModel = new GetBrandListReqModel(updateLimit,updateIndex,"");
       String data = new Gson().toJson(brandListReqModel);
       Log.d("Apitimetracking","getContractList:-"+data);
       Log.d("Apitimetracking","time"+AppUtility.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss"));
       ApiClient.getservices().eotServiceCall(Service_apis.getBrandList, AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Observer<JsonObject>() {
                   @Override
                   public void onSubscribe(@NotNull Disposable d) {

                   }

                   @Override
                   public void onNext(@NotNull JsonObject jsonObject) {
                       AppCenterLogs.addLogToAppCenterOnAPIFail("Api","getBrandList","","SyncDataJobService",String.valueOf(jsonObject.get("success").getAsBoolean()));
                       Log.d("getBrandList", jsonObject.toString());
                       if (jsonObject.get("success").getAsBoolean()) {
                           count = jsonObject.get("count").getAsInt();
                           String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                           Type listType = new TypeToken<List<BrandData>>() {
                           }.getType();
                           List<BrandData> brandList = new Gson().fromJson(convert, listType);
                           if (brandList != null) {
//                               AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).
                           }
                       }
                   }

                   @Override
                   public void onError(@NotNull Throwable e) {
                       Log.e("Network Error :", e.toString());
                       /* *****/
                       errorMsg(e.toString());
                   }

                   @Override
                   public void onComplete() {
                       if ((updateIndex + updateLimit) <= count) {
                           updateIndex += updateLimit;
                           getBrandList();
                       } else {
                           if (count != 0) {
                               App_preference.getSharedprefInstance().setContractSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                           }
                           updateIndex = 0;
                           count = 0;
                          /* AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).contractDao().deleteContractByIsDelete();*/
                           App_preference.getSharedprefInstance().setFirstSyncState(1);
                           startSyncFromStatus();
                           Log.v("MainSync","Sync completed "+" --" +"contract Sync Done");

                       }
                   }
               });
   }
}