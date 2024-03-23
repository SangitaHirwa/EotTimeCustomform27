package com.eot_app.nav_menu.appointment.appointment_ItemData;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemAdd_RequestModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDeleteRequestModel;
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

    UpdateItemDataList_pi updateItemDataList_pi = new AppointmentDetailsActivity();
    AppointmentItemAdded_pi itemAdded_pi=new AppointmentItemAdded_pc(updateItemDataList_pi);
    public final static int ADD_REQUIRED_ITEM = 5;
    Context context;


    @Override
    public void apiCallUpdateAppointmentItem(AppointmentUpdateItem_Req_Model appointmentUpdateItem_req_model
            ,AppointmentItemDataInMap itemDataModelForDB,String appId,AppintmentItemDataModel modelForUpdate,Context context) {
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

                                String updateItem = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().getUpdatedItemData(appId);
                                Type listType = new TypeToken<List<AppointmentItemDataInMap>>() {
                                }.getType();
                                List<AppointmentItemDataInMap > addItemList = new Gson().fromJson(updateItem, listType);
                                for (AppointmentItemDataInMap itemData : addItemList) {
                                    AppintmentItemDataModel itemData1 = itemData.getItemData();
                                    if(itemData1.getIlmmId()==modelForUpdate.getIlmmId()){
                                        addItemList.remove(itemData);
                                        break;
                                    }
                                }
                                addItemList.add(itemDataModelForDB);

                                String updateInDB = new Gson().toJson(addItemList);
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().updateAppointmentItem(appId,updateInDB);

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
                            itemAdded_pi.getItemListByAppointmentFromDB(appId);
                            AppUtility.progressBarDissMiss();
                        }
                    });

        } else {
            netWork_erroR();
        }

    }

    @Override
    public void apiCallForDeleteItem(AppointmentItemDeleteRequestModel deleteRequestModel,
                                    String appId,Context context) {
        JsonObject jsonObject =  AppUtility.getJsonObject(new Gson().toJson(deleteRequestModel));
        if (AppUtility.isInternetConnected()) {
            AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.deleteItemOnAppointment,AppUtility.getApiHeaders(),jsonObject)
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

                                String removeDeletedItem = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().getUpdatedItemData(appId);
                                Type listType = new TypeToken<List<AppointmentItemDataInMap>>() {
                                }.getType();
                                List<AppointmentItemDataInMap > addItemList = new Gson().fromJson(removeDeletedItem, listType);
                                List<AppointmentItemDataInMap > removeItemList=new ArrayList<>();
                                for (AppointmentItemDataInMap itemData : addItemList) {
                                    String ilmmId= itemData.getIlmmId();
                                    if(ilmmId.equals(deleteRequestModel.getIlmmId())){
                                        removeItemList.add(itemData);
                                        break;
                                    }
                                }
                                addItemList.removeAll(removeItemList);
                                String deletedDtaRemoved = new Gson().toJson(addItemList);
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().updateAppointmentItem(appId,deletedDtaRemoved);

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
                            itemAdded_pi.getItemListByAppointmentFromDB(appId);
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

