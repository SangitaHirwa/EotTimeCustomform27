package com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.eot_app.activitylog.ActivityLogController;
import com.eot_app.nav_menu.audit.audit_list.equipment.remark.remark_mvp.RemarkRequest;
import com.eot_app.nav_menu.jobs.job_controller.ChatController;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.chat.fire_Base_Model.Chat_Send_Msg_Model;
import com.eot_app.nav_menu.jobs.job_detail.customform.cstm_form_model.CustomFormList_Res;
import com.eot_app.nav_menu.jobs.job_detail.customform.cstm_form_model.FormList_Model_Req;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_db.model_pkg.ItembyJobModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.model.UpdateEquStatusReqModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.model.UpdateEquStatusResModel;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.RequestedItemModel;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.settings.equipmentdb.Equipment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.hypertrack.hyperlog.HyperLog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Sonam-11 on 22/9/20.
 */
public class JobEquRemark_PC implements JobEquRemark_PI {
    private final JobEquRemark_View jobEquimView;
    private final int updatelimit;
    private int count;
    private int updateindexCustomForm;
    int updateindexItem;
    private int updateindexEquipment;


    public JobEquRemark_PC(JobEquRemark_View jobEquimView) {
        this.jobEquimView = jobEquimView;
        this.updateindexCustomForm = 0;
        this.updateindexItem = 0;
        this.updateindexEquipment = 0;
        this.updatelimit = AppConstant.LIMIT_HIGH;
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
                                Type listType = new TypeToken<List<InvoiceItemDataModel>>() {
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

    /**
     * get Custom form List
     ******/
    @Override
    public void getCustomFormList(final Job equipmentRes, final ArrayList<String> jtId) {
        if (AppUtility.isInternetConnected()) {

            FormList_Model_Req formList_model = new FormList_Model_Req(updateindexCustomForm, updatelimit,
                    equipmentRes.getJobId(), jtId, "1");
            String jsonObjectdata = new Gson().toJson(formList_model);
            JsonParser parser = new JsonParser();
            final JsonObject jsonObject = parser.parse(jsonObjectdata).getAsJsonObject();

            ApiClient.getservices().eotServiceCall(Service_apis.getFormList, AppUtility.getApiHeaders(), jsonObject)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            Log.e("Responce", jsonObject.toString());
                            if (jsonObject.get("success").getAsBoolean() && jsonObject.get("data").getAsJsonArray().size() > 0) {
                                if (jsonObject.get("count") != null && jsonObject.get("count").getAsInt() > 0) {
                                    count = jsonObject.get("count").getAsInt();
                                    String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                    Type listType = new TypeToken<List<CustomFormList_Res>>() {
                                    }.getType();
                                    ArrayList<CustomFormList_Res> formList = new Gson().fromJson(convert, listType);
                                    jobEquimView.setList(formList);
                                }
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                jobEquimView.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            } else if (jsonObject.get("count") != null && jsonObject.get("count").getAsString().equals("0")) {
                                jobEquimView.formNotFound();
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("Error", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            if ((updateindexCustomForm + updatelimit) <= count) {
                                updateindexCustomForm += updatelimit;
                                getCustomFormList(equipmentRes, jtId);
                            }
                        }
                    });

        } else {
            networkError();
        }
    }

    @Override
    public void getEquipmentList(final String type, final String cltId, final String audId, String isParent) {


        if (AppUtility.isInternetConnected()) {

            HashMap<String, String> auditListRequestModel = new HashMap<>();
            auditListRequestModel.put("limit", "" + updatelimit);
            auditListRequestModel.put("index", "" + updateindexEquipment);
            auditListRequestModel.put("audId", "" + audId);
            auditListRequestModel.put("isJob", "1");
            auditListRequestModel.put("isParent", "1");


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
//                                Log.d("mahi", jsonObject.toString());
                                AppUtility.progressBarDissMiss();
                                if (jsonObject.get("success").getAsBoolean()) {
                                    count = jsonObject.get("count").getAsInt();
                                    String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                    Type listType = new TypeToken<List<EquArrayModel>>() {
                                    }.getType();
                                    List<EquArrayModel> data = new Gson().fromJson(convert, listType);
                                    if (data != null && data.size() > 0) {
                                        Log.e("EquipmentList::", new Gson().toJson(data));
                                        updateEquipmentDataInDb(audId, data);
                                    }

                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.e("", "");
                            Log.e("TAG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                            if ((updateindexEquipment + updatelimit) <= count) {
                                updateindexEquipment += updatelimit;
                                getEquipmentList(type, cltId, audId, isParent);
                            } else {
                                if (count != 0) {
                                    Log.v("MainSync", "startJobSyncTime AddJob" + " --" + App_preference.getSharedprefInstance().getJobSyncTime());
                                    App_preference.getSharedprefInstance().setJobSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                }
                                updateindexEquipment = 0;
                                count = 0;
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().deleteJobByIsDelete();
                            }
                        }
                    });
        }
    }

    private void updateEquipmentDataInDb(String jobId, List<EquArrayModel> data) {
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
            EotApp.getAppinstance().getNotifyForEquipmentCount();
            EotApp.getAppinstance().getNotifyForEquipmentCountRemark();
            EotApp.getAppinstance().getNotifyForEquipmentCountList();
            Log.e("EquipmentListLocal::", new Gson().toJson(job.getEquArray()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void getItemListByJobFromDB(String jobId) {
        Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
        try {
            if (job.getItemData() != null && job.getItemData() != null && job.getItemData().size() > 0) {
                jobEquimView.setItemListByJob(job.getItemData());
            } else {
                jobEquimView.setItemListByJob(new ArrayList<InvoiceItemDataModel>());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void addNewRemark(final RemarkRequest remarkRequest, String file, List<MultipartBody.Part> docAns, ArrayList<String> docQueIdArrays,
                             List<MultipartBody.Part> signAns,
                             ArrayList<String> signQueIdArrays,boolean isAutoUpdatedRemark,String equStatusId) {
        if (AppUtility.isInternetConnected()) {
            HyperLog.i("Param","Request of Remark :"+new Gson().toJson(remarkRequest)+" file path = "+ file+" Equipment status Id = "+equStatusId);
            HyperLog.i("", "JobEquRemark_PC: " + "addNewRemark:::: Start");

            String mimeType = "";
            MultipartBody.Part body = null;
            List<MultipartBody.Part> filesList = new ArrayList<>();
            if (!TextUtils.isEmpty(file)) {
                File file1 = new File(file);
                if (file1 != null) {
                    mimeType = URLConnection.guessContentTypeFromName(file1.getName());
                    if (mimeType == null) {
                        mimeType = file1.getName();
                    }
                    RequestBody requestFile = RequestBody.create(file1, MediaType.parse(mimeType));
                    // MultipartBody.Part is used to send also the actual file name
                    body = MultipartBody.Part.createFormData("ja[]", file1.getName()
                            //  + file.substring(file.lastIndexOf("."))
                            , requestFile);
                    filesList.add(body);
                }
            }
            remarkRequest.setEquStatus(equStatusId);
            RequestBody audId = RequestBody.create(remarkRequest.getAudId(), MultipartBody.FORM);
            RequestBody equId = RequestBody.create(remarkRequest.getEquId(), MultipartBody.FORM);
            RequestBody userId = RequestBody.create(App_preference.getSharedprefInstance().getLoginRes().getUsrId(), MultipartBody.FORM);
            RequestBody remark = RequestBody.create(remarkRequest.getRemark(), MultipartBody.FORM);
            RequestBody status = RequestBody.create(remarkRequest.getStatus(), MultipartBody.FORM);
            RequestBody lat = RequestBody.create(remarkRequest.getLat(), MultipartBody.FORM);
            RequestBody lng = RequestBody.create(remarkRequest.getLng(), MultipartBody.FORM);
            RequestBody isJob = RequestBody.create(remarkRequest.getIsJob(), MultipartBody.FORM);
            RequestBody equStatusBody = RequestBody.create(equStatusId, MultipartBody.FORM);
            String str = new Gson().toJson(remarkRequest.getAnswerArray().getAnswer());
            RequestBody answerArray = RequestBody.create(str, MultipartBody.FORM);

            String signIdArrayStr = new Gson().toJson(signQueIdArrays);
            RequestBody signQueIdArray = RequestBody.create(signIdArrayStr, MultipartBody.FORM);

            String docIdArrayStr = new Gson().toJson(docQueIdArrays);
            RequestBody docQueIdArray = RequestBody.create(docIdArrayStr, MultipartBody.FORM);


            ActivityLogController
                    .saveActivity(ActivityLogController
                                    .AUDIT_MODULE,
                            ActivityLogController.AUDIT_EQUIP,
                            ActivityLogController.AUDIT_REMARK);

            // when replace equipment in clicked
            if(!isAutoUpdatedRemark)
            AppUtility.progressBarShow((Context) jobEquimView);


            ApiClient.getservices().uploadAuditRemarkWithDocument(AppUtility.getApiHeaders(),
                    audId,
                    equId,
                    userId,
                    remark,
                    status,
                    lat,
                    lng,
                    isJob,
                    filesList,
                    docAns,
                    docQueIdArray,
                    answerArray,
                    signAns,
                    signQueIdArray,equStatusBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NotNull JsonObject jsonObject) {
                            try {
                                HyperLog.i("", "JobEquRemark_PC: " + "JsonObject:::: " + jsonObject.toString());

                                Log.d("mahi", jsonObject.toString());
                                if (jsonObject.get("success").getAsBoolean()) {
                                    String message = LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString());
                                    updateEquipmentStates(remarkRequest);
                                    jobEquimView.onRemarkUpdate(message, new InvoiceItemDataModel());
                                } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                    jobEquimView.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                } else {
                                    String message = LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString());
                                    jobEquimView.onErrorMsg(message);
                                }
                            } catch (Exception e) {
                                HyperLog.i("", "JobEquRemark_PC: " + "Exception:::: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Log.d("mahi", e.toString());
                            //  remark_view.onSessionExpire("");
                            HyperLog.i("", "JobEquRemark_PC: " + "onError:::: " + e.getMessage());

                            AppUtility.progressBarDissMiss();
                        }

                        @Override
                        public void onComplete() {
                            Log.d("mahi", "Completed");
                            getEquipmentList();
                            AppUtility.progressBarDissMiss();
                        }
                    });
        } else {
            networkError();
        }

    }

    private void updateEquipmentStates(RemarkRequest remarkRequest) {
        Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(remarkRequest.getAudId());
        if (job != null) {
            try {
                for (EquArrayModel equipment : job.getEquArray()) {
                    if (equipment.getEquId().equals(remarkRequest.getEquId())) {
                        equipment.setStatus(remarkRequest.getStatus());
                        equipment.setRemark(remarkRequest.getRemark());
                        equipment.setEquStatus(remarkRequest.getEquStatus());
                        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJob(job);
                        // for notifying job overview page
                        EotApp.getAppinstance().getJobFlagOverView();
                        // TODO don't have to show remark on main page
//                        EotApp.getAppinstance().getNotifyForEquipmentCount();
                        break;
                    } else {
                        // for updating equipment component
                        for (EquArrayModel equipmentPart : equipment.getEquComponent())
                            if (equipmentPart.getEquId().equals(remarkRequest.getEquId())) {
                                equipmentPart.setStatus(remarkRequest.getStatus());
                                equipmentPart.setRemark(remarkRequest.getRemark());
                                equipmentPart.setEquStatus(remarkRequest.getEquStatus());
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJob(job);
                                // for notifying job overview page
                                EotApp.getAppinstance().getJobFlagOverView();
                                // TODO don't have to show remark on main page
//                        EotApp.getAppinstance().getNotifyForEquipmentCount();
                                break;
                            }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getRequestedItemDataList(String jobId) {
        if (AppUtility.isInternetConnected()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("jobId", jobId);
            hashMap.put("limit",updatelimit+"");
            hashMap.put("index",updateindexItem+"");
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
                                    if(data != null && data.isEmpty()){
                                        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateRequestedItem("0",jobId);
                                    }
                                    jobEquimView.setRequestItemData(data);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                AppUtility.progressBarDissMiss();
                                jobEquimView.notDtateFoundInRequestedItemList(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
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
            networkError();
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
                                    jobEquimView.deletedRequestData(jsonObject.get("message").getAsString(),requestedModel);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                AppUtility.progressBarDissMiss();
                                jobEquimView.notDtateFoundInRequestedItemList(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
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
            networkError();
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
            networkError();
        }
    }

    @Override
    public void getLinkItemList(String jobId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("jobId", jobId);
        hashMap.put("limit",updatelimit+"");
        hashMap.put("index",updateindexItem+"");
        if (AppUtility.isInternetConnected()) {
        ApiClient.getservices().eotServiceCall(Service_apis.getLinkItem, AppUtility.getApiHeaders(),
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
                    String convert = jsonObject.get("data").getAsJsonArray().toString();
                    Type listType = new TypeToken<List<InvoiceItemDataModel>>() {
                    }.getType();
                    List<InvoiceItemDataModel> data = new Gson().fromJson(convert, listType);
                    jobEquimView.setItemListByJob(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                AppUtility.progressBarDissMiss();
                jobEquimView.notDtateFoundInRequestedItemList(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
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
} else {
            networkError();
        }
    }

    @Override
    public void linkedItemAddToEqu(String jobId, String equId, String ijmmId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("jobId", jobId);
        hashMap.put("equId",equId);
        hashMap.put("ijmmId",ijmmId);
        if (AppUtility.isInternetConnected()) {
            ApiClient.getservices().eotServiceCall(Service_apis.linkItemToEqup, AppUtility.getApiHeaders(),
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
                                    Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
                                    List<InvoiceItemDataModel> list = job.getItemData();
                                    InvoiceItemDataModel updateItemDataModel = new InvoiceItemDataModel();
                                    for (InvoiceItemDataModel item : list
                                         ) {
                                        if(item.getIjmmId().equalsIgnoreCase(ijmmId)){
                                            item.setEquId(equId);
                                            updateItemDataModel = item;
                                            break;
                                        }
                                    }
                                    AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJobitems(jobId,list);
                                    jobEquimView.onRemarkUpdate(jsonObject.get("message").getAsString(),updateItemDataModel);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                AppUtility.progressBarDissMiss();
                                jobEquimView.notDtateFoundInRequestedItemList(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
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
        } else {
            networkError();
        }
    }

    @Override
    public void updateEquStatus(UpdateEquStatusReqModel reqModel) {
        if (AppUtility.isInternetConnected()) {
            AppUtility.progressBarShow((Context) jobEquimView);
            ApiClient.getservices().eotServiceCall(Service_apis.updateEquStatus, AppUtility.getApiHeaders(),
                            AppUtility.getJsonObject(new Gson().toJson(reqModel)))
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
                                Type listType = new TypeToken<List<UpdateEquStatusResModel>>() {
                                }.getType();
                                List<UpdateEquStatusResModel> data = new Gson().fromJson(convert, listType);
                              jobEquimView.setEquStatus(data);
                            } else {
                                jobEquimView.setEquStatus(new ArrayList<UpdateEquStatusResModel>());
                            }
                        }


                        @Override
                        public void onError(@NotNull Throwable e) {
                            AppUtility.progressBarDissMiss();
                            Log.e("TAG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            networkError();
        }
    }

    public void networkError() {
        AppUtility.alertDialog(((Context) jobEquimView), LanguageController.getInstance().
                        getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey
                        (AppConstant.err_check_network), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok),
                "", () -> null);
    }
    public void getEquipmentList() {

        if (AppUtility.isInternetConnected()) {


            HashMap<String, String> auditListRequestModel = new HashMap<>();
            auditListRequestModel.put("search", "");
            auditListRequestModel.put("isActive", "");
            auditListRequestModel.put("limit", ""+updatelimit);
            auditListRequestModel.put("index", ""+updateindexCustomForm);
            auditListRequestModel.put("type", "");
            auditListRequestModel.put("dateTime", App_preference.getSharedprefInstance().getAllEquipmentSyncTime());


            String data = new Gson().toJson(auditListRequestModel);
            ApiClient.getservices().eotServiceCall(Service_apis.getAllEquipments, AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            Log.e("getAllEquipments", "AddJobPc");
                            try {
                                Log.d("mahi", jsonObject.toString());
                                if (jsonObject.get("success").getAsBoolean()) {
                                    count = jsonObject.get("count").getAsInt();
                                    String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                    Type listType = new TypeToken<List<Equipment>>() {
                                    }.getType();
                                    List<Equipment> equipmentList = new Gson().fromJson(convert, listType);
                                    if (equipmentList != null)
                                        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().insertEquipmentList(equipmentList);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", "");
                        }

                        @Override
                        public void onComplete() {
                            if ((updateindexCustomForm + updatelimit) <= count) {
                                updateindexCustomForm += updatelimit;
                                getEquipmentList();
                            } else {
                                if (count != 0) {
                                    App_preference.getSharedprefInstance().setAllEquipmentSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                }
                                updateindexCustomForm = 0;
                                count = 0;
                            }
                        }
                    });
        }
    }
}
