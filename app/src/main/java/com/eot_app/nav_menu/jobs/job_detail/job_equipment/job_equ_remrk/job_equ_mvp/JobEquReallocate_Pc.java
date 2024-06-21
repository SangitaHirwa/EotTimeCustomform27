package com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp;

import android.content.Context;
import android.util.Log;

import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.clientEqu.ClientEquRes;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.model.UpdateEquStatusResModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.model.UpdateSiteLocationReqModel;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.Country;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.States;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.settings.equipmentdb.Equipment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JobEquReallocate_Pc implements JobEquReallocate_Pi{
    JobEquReallocate_View view;
    private final int updatelimit= AppConstant.LIMIT_HIGH;
    private int updateindex= 0;
    private int count;



    public JobEquReallocate_Pc(JobEquReallocate_View view) {
        this.view = view;
    }

    @Override
    public void updateLocation(UpdateSiteLocationReqModel reqModel) {
        if (AppUtility.isInternetConnected()) {
            Log.e("equipmentRelocate_req",new Gson().toJson(reqModel));
            AppUtility.progressBarShow((Context) view);
            ApiClient.getservices().eotServiceCall(Service_apis.equipmentRelocate, AppUtility.getApiHeaders(),
                            AppUtility.getJsonObject(new Gson().toJson(reqModel)))
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
                                view.setNewLocation(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            } else {
                                Log.e("msg",jsonObject.get("message").getAsString());
                                view.errorMsg(LanguageController.getInstance().getServerMsgByKey(AppConstant.went_wrong));
                            }
                        }


                        @Override
                        public void onError(@NotNull Throwable e) {
                            AppUtility.progressBarDissMiss();
                            Log.e("TAG", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                                    getEquipmentList();
                        }
                    });
        } else {
            networkError();
        }
    }
    public void networkError() {
        AppUtility.alertDialog(((Context) view), LanguageController.getInstance().
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
            auditListRequestModel.put("index", ""+updateindex);
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
                            if ((updateindex + updatelimit) <= count) {
                                updateindex += updatelimit;
                                getEquipmentList();
                            } else {
                                if (count != 0) {
                                    App_preference.getSharedprefInstance().setAllEquipmentSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                }
                                updateindex = 0;
                                count = 0;
                            }
                        }
                    });
        }
    }
}
