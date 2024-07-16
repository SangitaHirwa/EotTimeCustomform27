package com.eot_app.nav_menu.setting.setting_presenter;

import android.util.Log;

import com.eot_app.nav_menu.jobs.add_job.GetPoListResponse;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Setting_pc implements Setting_pi{
    Setting_view settingView;
    public Setting_pc(Setting_view setting_view) {
        settingView = setting_view;
    }
    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) {
        if (AppUtility.isInternetConnected()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("usrId", userId);
            jsonObject.addProperty("op", oldPassword);
            jsonObject.addProperty("np", newPassword);
            Log.e("","Change Password Request"+new Gson().toJson(jsonObject));
            ApiClient.getservices().eotServiceCall(Service_apis.changePassword, AppUtility.getApiHeaders(), jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                settingView.changePasswordSuccess(jsonObject.get("message").getAsString());
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                settingView.onSessionExpired(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }else {
                                settingView.changePasswordFailure(jsonObject.get("message").getAsString());
                            }
                        }


                        @Override
                        public void onError(Throwable e) {
                            settingView.changePasswordFailure(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }
}
