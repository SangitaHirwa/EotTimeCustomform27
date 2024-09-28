package com.eot_app.nav_menu.equipment.linkequip.linkMVP;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.eot_app.activitylog.ActivityLogController;
import com.eot_app.activitylog.LogModel;
import com.eot_app.nav_menu.audit.audit_list.equipment.equipment_room_db.entity.EquipmentStatus;
import com.eot_app.nav_menu.equipment.linkequip.linkMVP.model.ContractEquipmentReq;
import com.eot_app.nav_menu.equipment.linkequip.linkMVP.model.EquipmentListReq;
import com.eot_app.nav_menu.equipment.linkequip.linkMVP.model.LinkEquipRequest;
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
import com.eot_app.utility.db.OfflineDataController;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.settings.equipmentdb.Equipment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LinkEquipmentPC implements LinkEquipmentPI {
    int index;
    int limit;
    int count;

    int updatelimit;
    int updateindex;
    int countlimit;

    LinkEquipmentView view;

    List<EquArrayModel> listLinked = new ArrayList<>();
    List<EquArrayModel> list = new ArrayList<>();
    List<EquArrayModel> contractList = new ArrayList<>();

    public LinkEquipmentPC(LinkEquipmentView linkEquipmentView ) {
        this.view = linkEquipmentView;
        this.limit = AppConstant.LIMIT_HIGH;
        this.updatelimit = AppConstant.LIMIT_HIGH;
//        getEquipmentStatus();

    }

    @Override
    public void getEquipmentList(final String type, final String contrId, final String jobId) {
        view.showHideProgressBar(true);
        if (AppUtility.isInternetConnected()) {

            if (index == 0) list.clear();
//            EquipmentListReq equipmentListReq = new EquipmentListReq(
//                    index,
//                    limit,
//                    type,
//                    cltId,
//                    App_preference.getSharedprefInstance().getAllEquipmentSyncTime()
//            );
            com.eot_app.utility.settings.equipmentdb.EquipmentListReq equipmentListReq = new com.eot_app.utility.settings.equipmentdb.EquipmentListReq(
                    limit, index, "", App_preference.getSharedprefInstance().getAllEquipmentSyncTime());
            JsonObject jsonObject = AppUtility.jsonToStingConvrt(equipmentListReq);
            ApiClient.getservices().eotServiceCall(
                    Service_apis.getAllEquipments,
                    AppUtility.getApiHeaders(),
                    jsonObject
            ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull JsonObject jsonObject) {
                            Log.e("getAllEquipments", "LinkPc");

                            if (jsonObject != null)
                                if (jsonObject.get("success").getAsBoolean()) {
                                    {
                                        count = jsonObject.get("count").getAsInt();
                                        String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                        Type listType = new TypeToken<List<Equipment>>() {
                                        }.getType();
                                        List<Equipment> equipList = new Gson().fromJson(convert, listType);
                                        if (equipList != null)
                                            if (equipList != null) {
                                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().insertEquipmentList(equipList);
                                            }

                                    }
                                }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            view.showHideProgressBar(false);
                        }

                        @Override
                        public void onComplete() {
                            if ((index + limit) <= count) {
                                index += limit;
                                getEquipmentList(type, contrId, jobId);
                            } else {
                                if (count != 0) {
                                    App_preference.getSharedprefInstance().setAllEquipmentSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                }
                                index = 0;
                                count = 0;
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().deleteEquipmentByIsDelete();
                                getAttachedEquipmentList(type, jobId, contrId,false);

                            }

                        }
                    });
        } else{
            getAttachedEquipmentList(type, jobId, contrId,false);
        }

    }

    @Override
    public void getAttachedEquipmentList(String type, String jobId, final String contrId, boolean isReturn) {

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
                                List<Job> jobData = new Gson().fromJson(convert, listType);
                                addRecordsToDB(jobData);
//                                if(jobId != null) {
//                                    Job jobsById = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
//                                    if(jobsById!=null) {
//                                        List<EquArrayModel> data = jobsById.getEquArray();
//                                        if (data != null)
//                                            listLinked.addAll(data);
//                                    }
//                                }
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                view.onSessionExpired(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showHideProgressBar(false);

                        }

                        @Override
                        public void onComplete() {
                            if ((updateindex + updatelimit) <= count) {
                                updateindex += updatelimit;

                                getAttachedEquipmentList(type, jobId,contrId,false);

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
                                view.showHideProgressBar(false);
                                updateindex = 0;
                                count = 0;
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().deleteJobByIsDelete();
                            }

                            if(isReturn){
                                view.refreshEquList(isReturn);
                            }
                            mergeTheListForLinkedEquipment(type, contrId, jobId);

                        }
                    });
        } else {
            if(isReturn){
                view.refreshEquList(isReturn);
            }
            mergeTheListForLinkedEquipment(type, contrId, jobId);
        }

      }

    private void addRecordsToDB(List<Job> data) {
            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().inserJob(data);
            AppDataBase.getInMemoryDatabase(EotApp.getCurrentActivity()).jobModel().deleteJobByIsDelete();
    }

    @Override
    public void linkUnlinkEquipment(List<String> equId, String audId, String contrId) {
        if (AppUtility.isInternetConnected()) {

            HashMap hashMap = new HashMap();
            hashMap.put("equId", equId);
            hashMap.put("audId", audId);
            hashMap.put("contrId", contrId);

            String data = new Gson().toJson(hashMap);

            ApiClient.getservices().eotServiceCall(
                    Service_apis.addAuditEquipment,
                    AppUtility.getApiHeaders(),
                    AppUtility.getJsonObject(data)
            ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                view.updateLinkUnlinkEqu();
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                view.onSessionExpired(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else networkError();
    }

    @Override
    public void addAuditEquipment(List<String> linkEquIdList, String audId, String contrId) {
//        if (AppUtility.isInternetConnected()) {
        Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(audId);

            view.showHideProgressBar(true);
            String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
            LinkEquipRequest linkEquipRequest = new LinkEquipRequest();
            linkEquipRequest.setAudId(audId);
            if(job != null){
                linkEquipRequest.setContrId(job.getContrId());
            }else {
                linkEquipRequest.setContrId(contrId);
            }
            linkEquipRequest.setEquId(linkEquIdList);
//            HashMap hashMap = new HashMap();
//            hashMap.put("equId", equId);
//            hashMap.put("audId", audId);
//            hashMap.put("contrId", contrId);
//            String data = new Gson().toJson(hashMap);
            String data = new Gson().toJson(linkEquipRequest);
            Log.e("Request Param", Service_apis.addAuditEquipment + " " + data);


            List<EquArrayModel> requestForLinkStatus = new ArrayList<>();
            if(job != null) {
                if(job.getEquArray() != null && job.getEquArray().size() > 0){
                    for (EquArrayModel equipment : job.getEquArray()
                         ) {
                        if(equipment.getStatus() != null && equipment.getStatus().equals("") ){
                            for (String linkEquipment : linkEquIdList
                            ) {
                                if(equipment.getEquId().equals(linkEquipment)){
                                    linkEquIdList.remove(equipment.getEquId());
                                    requestForLinkStatus.add(equipment);
                                    break;
                                }
                            }
                        }else {
                            if(linkEquIdList.contains(equipment.getEquId())) {
                                linkEquIdList.remove(equipment.getEquId());
                            }
                            requestForLinkStatus.add(equipment);
                        }

                    }
                    if(linkEquIdList.size() > 0){
                        for (String linkItem : linkEquIdList
                             ) {
                            List<Equipment> partEquipmentList = new ArrayList<>();
                            Equipment equipment = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().getEquipmentByEquipId(linkItem);
                            if(equipment != null) {
                                partEquipmentList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().getEquipmentsByParentEquipId(linkItem);
                                if(partEquipmentList != null && partEquipmentList.size()>0) {
                                    partEquipmentList.add(equipment);
                                }else {
                                    partEquipmentList = new ArrayList<>();
                                    partEquipmentList.add(equipment);
                                }
                                for (Equipment item :
                                        partEquipmentList) {
                                    item.setRemark("");
                                    item.setStatus("");
                                }
                                String addDataString = new Gson().toJson(partEquipmentList);
                                Type listType = new TypeToken<List<EquArrayModel>>() {
                                }.getType();
                                List<EquArrayModel> addData = new Gson().fromJson(addDataString, listType);
                                requestForLinkStatus.addAll(addData);
                            }
                        }
                    }
                } else {
                    for (String linkItem : linkEquIdList
                    ) {
                        List<Equipment> partEquipmentList = new ArrayList<>();
                        Equipment equipment = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().getEquipmentByEquipId(linkItem);
                        if(equipment != null) {
                            partEquipmentList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().getEquipmentsByParentEquipId(linkItem);
                            if(partEquipmentList != null && partEquipmentList.size()>0) {
                                partEquipmentList.add(equipment);
                            }else {
                                partEquipmentList = new ArrayList<>();
                                partEquipmentList.add(equipment);
                            }
                            for (Equipment item :
                                    partEquipmentList) {
                                item.setRemark("");
                                item.setStatus("");
                            }
                            String addDataString = new Gson().toJson(partEquipmentList);
                            Type listType = new TypeToken<List<EquArrayModel>>() {
                            }.getType();
                            List<EquArrayModel> addData = new Gson().fromJson(addDataString, listType);
                            requestForLinkStatus.addAll(addData);
                        }
                    }
                }
                String addDataString = new Gson().toJson(requestForLinkStatus);
                Log.e("Request Param", Service_apis.addAuditEquipment + " local data ==" + addDataString);
                job.setEquArray(requestForLinkStatus);
                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().inserSingleJob(job);
                OfflineDataController.getInstance().addInOfflineDB(Service_apis.addAuditEquipment,data,dateTime);
            }

        if (!AppUtility.isInternetConnected()) {
            view.refreshEquipmentList(true, true);
        }

//            ApiClient.getservices().eotServiceCall(
//                    Service_apis.addAuditEquipment,
//                    AppUtility.getApiHeaders(),
//                    AppUtility.getJsonObject(data)
//            ).subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<JsonObject>() {
//                        @Override
//                        public void onSubscribe(@NonNull Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(@NonNull JsonObject jsonObject) {
//                            if (jsonObject.get("success").getAsBoolean()) {
//                                view.refreshEquipmentList(true,true);
//                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
//                                view.onSessionExpired(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
//                            }
//                        }
//
//                        @Override
//                        public void onError(@NonNull Throwable e) {
//                            view.showHideProgressBar(false);
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });

//        } else networkError();
    }

//    @Override
//    public void getContractList(final ContractEquipmentReq req) {
//        if (AppUtility.isInternetConnected()) {
//
//            req.setIndex(index);
//            req.setLimit(limit);
//
//            if (index == 0) contractList.clear();
//            view.showHideProgressBar(true);
//            JsonObject jsonObject = AppUtility.jsonToStingConvrt(req);
//            ApiClient.getservices().eotServiceCall(
//                    Service_apis.getContractEquipmentList,
//                    AppUtility.getApiHeaders(),
//                    jsonObject
//                    ).subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<JsonObject>() {
//                        @Override
//                        public void onSubscribe(@NonNull Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(@NonNull JsonObject jsonObject) {
//
//                            if (jsonObject.get("success").getAsBoolean()) {
//
//                                count = jsonObject.get("count").getAsInt();
//                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
//                                Type listType = new TypeToken<List<EquArrayModel>>() {
//                                }.getType();
//                                List<EquArrayModel> sliceList = new Gson().fromJson(convert, listType);
//                                if (sliceList != null)
//                                    contractList.addAll(sliceList);
//                                Log.d("equipmentList", jsonObject.toString());
//                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
//                                view.onSessionExpired(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
//                            }
//                        }
//
//                        @Override
//                        public void onError(@NonNull Throwable e) {
//                            view.showHideProgressBar(false);
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            if ((index + limit) <= count) {
//                                index += limit;
////                                getContractList(req);
//                            } else {
//                                index = 0;
//                                count = 0;
//                                getAttachedEquipmentList(req.getType(), req.getJobId(), req.getContrId(),false);
//                            }
//                        }
//                    });
//        } else
//
//            networkError();
//
//    }

    @Override
    public void getEquipmentStatus() {
//        if (AppUtility.isInternetConnected()) {
//            view.showHideProgressBar(true);
//            EquipmentStatusReq equipmentListReq = new EquipmentStatusReq();
//            String data = new Gson().toJson(equipmentListReq);
//            ApiClient.getservices().eotServiceCall(Service_apis.getEquipmentStatus, AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<JsonObject>() {
//                        @Override
//                        public void onSubscribe(@NotNull Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(@NotNull JsonObject jsonObject) {
//                            if (jsonObject.get("success").getAsBoolean()) {
//                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
//                                Type listType = new TypeToken<List<EquipmentStatus>>() {
//                                }.getType();
//                                List<EquipmentStatus> equipmentStatusList = new Gson().fromJson(convert, listType);
//                                view.setEquStatusList(equipmentStatusList);
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            view.showHideProgressBar(false);
//                        }
//
//                        @Override
//                        public void onComplete() {
//                        }
//                    });
//        }
    }

    private void mergeTheListForLinkedEquipment(String type, String contrId,String jobId) {
        Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
        List<Equipment> equipmentList = new ArrayList<>();

        if(contrId != null && !contrId.equals("")){
            List<EquArrayModel> finalList = new ArrayList<>();
            if(type != null && type.equals("1")) {
                equipmentList.clear();
                List<String> contractEquipDataList = new AppUtility().getContrctEquipment(contrId);
                for (String equipId: contractEquipDataList
                     ) {
                    Equipment equipment = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().getParentEquipmentById(equipId);
                    if(equipment != null) {
                        equipmentList.add(equipment);
                    }
                }
                if(equipmentList != null && equipmentList.size() >0){
                    listLinked = job.getEquArray();
                    String filterList = new Gson().toJson(equipmentList);
                    Type listType = new TypeToken<List<EquArrayModel>>() {
                    }.getType();
                    finalList = new Gson().fromJson(filterList, listType);
                    Collections.sort(finalList,(o1, o2) -> o1.getEqunm().compareTo(o2.getEqunm()));
                    if(listLinked != null && listLinked.size() > 0) {
                        for (EquArrayModel linkedEquip : listLinked){
                            for (EquArrayModel equipment : finalList){
                                if(equipment.getEquId().equals(linkedEquip.getEquId())){
                                    equipment.setLinkStatus(1);
                                    if(linkedEquip.getStatus() != null && !linkedEquip.getStatus().equals("")){
                                        equipment.setIsRemarkAdd(1);
                                    }
                                    break;
                                }
                            }

                        }
                    }
                }
                view.setEquipmentList(finalList);
            }else if(type != null && type.equals("2")){
                    equipmentList.clear();
                    List<String> contractEquipDataList = new AppUtility().getContrctEquipment(contrId);
                    for (String equipId: contractEquipDataList
                    ) {
                        Equipment equipment = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().getParentEquipmentById(equipId);
                        if(equipment != null) {
                            equipmentList.add(equipment);
                        }
                    }
                    if(equipmentList != null && equipmentList.size() >0){
                        listLinked = job.getEquArray();
                        String filterList = new Gson().toJson(equipmentList);
                        Type listType = new TypeToken<List<EquArrayModel>>() {
                        }.getType();
                        finalList = new Gson().fromJson(filterList, listType);
                        Collections.sort(finalList,(o1, o2) -> o1.getEqunm().compareTo(o2.getEqunm()));
                        if(listLinked != null && listLinked.size() > 0) {
                            for (EquArrayModel linkedEquip : listLinked){
                                for (EquArrayModel equipment : finalList){
                                    if(equipment.getEquId().equals(linkedEquip.getEquId())){
                                        equipment.setLinkStatus(1);
                                        if(linkedEquip.getStatus() != null && !linkedEquip.getStatus().equals("")){
                                            equipment.setIsRemarkAdd(1);
                                        }
                                        break;
                                    }
                                }

                            }
                        }
                    }
                    view.setEquipmentList(finalList);
            }

        }else {
            if(type != null && type.equals("1")) {
                equipmentList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().getAllTypeBaseEquipment(type);
            }else if(type != null && type.equals("2")){
                if(job != null && job.getContrId() != null && !job.getContrId().equals("")){
                    equipmentList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().getAllClientEquipment(job.getCltId());
                }else {
                    if(job != null && job.getSiteId() != null && !job.getSiteId().equals("")){
                        equipmentList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().getAllClientEquipment(job.getSiteId());
                    }
                }
            }
                listLinked = job.getEquArray();
            String filterList = new Gson().toJson(equipmentList);
            Type listType = new TypeToken<List<EquArrayModel>>() {
            }.getType();
            List<EquArrayModel> finalList = new Gson().fromJson(filterList, listType);
            Collections.sort(finalList,(o1, o2) -> o1.getEqunm().compareTo(o2.getEqunm()));
                if(listLinked != null && listLinked.size() > 0) {
                    for (EquArrayModel linkedEquip : listLinked){
                        for (EquArrayModel equipment : finalList){
                            if(equipment.getEquId().equals(linkedEquip.getEquId())){
                                equipment.setLinkStatus(1);
                                if(linkedEquip.getStatus() != null && !linkedEquip.getStatus().equals("")){
                                    equipment.setIsRemarkAdd(1);
                                }
                                break;
                            }
                        }

                    }
                }

                    view.setEquipmentList(finalList);
                
        }
//        if (listLinked.size() > 0) {
//            if (mergeContract) {
//                for (EquArrayModel model : listLinked)
//                    for (EquArrayModel inModel : contractList)
//                        if (model.getEquId().equals(inModel.getEquId())) {
//                            inModel.setLinkStatus(1);
//                            if (!TextUtils.isEmpty(model.getStatus()))
//                                inModel.setIsRemarkAdd(1);
//                            if (model != null && model.getBarcode() != null)
//                                inModel.setBarcode(model.getBarcode());
//                        }
//            } else {
//                for (EquArrayModel model : listLinked)
//                    for (EquArrayModel inModel : list)
//                        if (model.getEquId().equals(inModel.getEquId())) {
//                            inModel.setLinkStatus(1);
//                            if (!TextUtils.isEmpty(model.getStatus()))
//                                inModel.setIsRemarkAdd(1);
//                            if (model != null && model.getBarcode() != null)
//                                inModel.setBarcode(model.getBarcode());
//                        }
//            }
//
//        }
//        if (mergeContract)
//            view.setEquipmentList(contractList);
//        else
//            view.setEquipmentList(list);
        view.showHideProgressBar(false);
    }

    private void networkError() {
        AppUtility.alertDialog(((Context) view), LanguageController.getInstance().
                        getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey
                        (AppConstant.err_check_network), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok),
                "", () -> null);

    }
}
