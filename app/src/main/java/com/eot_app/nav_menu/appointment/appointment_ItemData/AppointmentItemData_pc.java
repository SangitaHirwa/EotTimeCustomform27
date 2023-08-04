package com.eot_app.nav_menu.appointment.appointment_ItemData;

import android.content.Context;
import android.util.Log;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemAdd_RequestModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentUpdateItem_Req_Model;
import com.eot_app.nav_menu.appointment.appointment_model.ItemByAppointmentId;
import com.eot_app.nav_menu.appointment.details.AppointmentDetailsActivity;
import com.eot_app.nav_menu.appointment.details.RequirementGetheringListAdapter;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AppointmentItemData_pc implements AppointmentItemData_pi {
    AppointmentItemAdded_pi itemAdded_pi=new AppointmentItemAdded_pc();
    UpdateItemDataList_pi updateItemDataList_pi = new AppointmentDetailsActivity();

    Context context;

    public AppointmentItemData_pc(Context context) {
        this.context = context;
    }

    @Override
    public void apiCallAddAppointmentItem(AppointmentItemAdd_RequestModel itemAddRequestModel) {
        JsonObject jsonObject =  AppUtility.getJsonObject(new Gson().toJson(itemAddRequestModel));
        if (AppUtility.isInternetConnected()) {
          AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.addItemOnAppointment,AppUtility.getApiHeaders(),jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            Log.e("Responce--->>>", "" + jsonObject.toString());
                            if (jsonObject.get("success").getAsBoolean()) {
                                String convert = new Gson().toJson(jsonObject.get("data"));
                                //itemAdded_pi.setItemAdded();
                                EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                itemAdded_pi.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            AppUtility.progressBarDissMiss();
                            Log.e("", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });

        } else {
            netWork_erroR();
        }
    }

    @Override
    public void apiCallUpdateAppointmentItem(AppointmentUpdateItem_Req_Model appointmentUpdateItem_req_model) {
        JsonObject jsonObject =  AppUtility.getJsonObject(new Gson().toJson(appointmentUpdateItem_req_model));
        if (AppUtility.isInternetConnected()) {
            AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.updateItemOnAppointment,AppUtility.getApiHeaders(),jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            Log.e("Responce--->>>", "" + jsonObject.toString());
                            if (jsonObject.get("success").getAsBoolean()) {
                                String convert = new Gson().toJson(jsonObject.get("data"));
                                EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                itemAdded_pi.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            AppUtility.progressBarDissMiss();
                            Log.e("", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });

        } else {
            netWork_erroR();
        }

    }

    @Override
    public void getItemFromServer(String appId, RequirementGetheringListAdapter requirementGetheringListAdapter) {
        ItemByAppointmentId byAppointmentId=new ItemByAppointmentId(appId);
        JsonObject jsonObject =  AppUtility.getJsonObject(new Gson().toJson(byAppointmentId));
        if (AppUtility.isInternetConnected()) {
            AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.getItemFromServer,AppUtility.getApiHeaders(),jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            Log.e("Responce--->>>", "" + jsonObject.toString());
                            if (jsonObject.get("success").getAsBoolean()) {
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonObject().get("itemData"));
                                Type listType = new TypeToken<List<AppointmentItemDataInMap>>() {
                                }.getType();
                                List<AppointmentItemDataInMap> updatedItemList = new Gson().fromJson(convert, listType);
                                List<AppintmentItemDataModel> tempItemList = new ArrayList<>();
                                for (AppointmentItemDataInMap itemData : updatedItemList) {
                                    AppintmentItemDataModel itemData1 = itemData.getItemData();
                                    tempItemList.add(itemData1);
                                }
                                String itemJson = new Gson().toJson(tempItemList);
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().updateAppointmentItem(appId,itemJson);

                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                itemAdded_pi.onSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            AppUtility.progressBarDissMiss();
                            Log.e("", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            String addedItemData = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().getUpdatedItemData(appId);
                            Type listType = new TypeToken<List<AppintmentItemDataModel>>() {
                            }.getType();
                            List<AppintmentItemDataModel> addedItemList = new Gson().fromJson(addedItemData, listType);
                            List<AppintmentItemDataModel> tempItemList = new ArrayList<>();
                            tempItemList.addAll(addedItemList);
                            updateItemDataList_pi.updateItemDataList(tempItemList,requirementGetheringListAdapter);
                            AppUtility.progressBarDissMiss();
                        }
                    });

        } else {
            netWork_erroR();
        }

    }

    private void netWork_erroR() {
        AppUtility.alertDialog((EotApp.getAppinstance()), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return null;
            }
        });
    }


}

