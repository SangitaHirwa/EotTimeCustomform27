package com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp;

import android.content.Context;
import android.util.Log;

import com.eot_app.nav_menu.jobs.job_detail.job_equipment.model.UpdateEquStatusResModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.model.UpdateSiteLocationReqModel;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JobEquReallocate_Pc implements JobEquReallocate_Pi{
    JobEquReallocate_View view;

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
}
