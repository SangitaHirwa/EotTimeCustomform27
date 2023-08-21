package com.eot_app.nav_menu.appointment.appointment_ItemData;

import android.content.Context;
import android.util.Log;

import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;
import com.eot_app.nav_menu.appointment.appointment_model.ItemByAppointmentId;
import com.eot_app.nav_menu.appointment.dbappointment.Appointment;
import com.eot_app.nav_menu.appointment.details.AppointmentDetailsActivity;
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

public class AppointmentItemAdded_pc implements AppointmentItemAdded_pi{
    UpdateItemDataList_pi dataList_pi;

    public AppointmentItemAdded_pc(UpdateItemDataList_pi dataList_pi) {
        this.dataList_pi = dataList_pi;
    }

    @Override
    public void getItemListByAppointmentFromDB(String appId) {
        Appointment appointment = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().getAppointmentById(appId);
        try {
            if (appointment.getItemData() != null && appointment.getItemData() != null ) {
                ArrayList<AppointmentItemDataInMap> itemList = new ArrayList<>(appointment.getItemData());
                dataList_pi.setItemListByAppointment(itemList);
            } else {
                dataList_pi.setItemListByAppointment(new ArrayList<>());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public void onSessionExpire(String msg) {

    }

    @Override
    public void getItemFromServer(String appId, Context context) {
        ItemByAppointmentId byAppointmentId=new ItemByAppointmentId(appId);
        JsonObject jsonObject =  AppUtility.getJsonObject(new Gson().toJson(byAppointmentId));
        if (AppUtility.isInternetConnected()) {
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

                                  if(updatedItemList.size()==1){
                                      AppintmentItemDataModel itemData = updatedItemList.get(0).getItemData();
                                      int leadId = itemData.getLeadId();
                                      AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().updateAppointmentLeadID(appId,String.valueOf(leadId));
                                  }
                                String itemJson = new Gson().toJson(updatedItemList);
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().updateAppointmentItem(appId,itemJson);

                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                               onSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            AppUtility.progressBarDissMiss();
                            Log.e("", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            getItemListByAppointmentFromDB(appId);
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
