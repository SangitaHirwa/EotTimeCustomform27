package com.eot_app.nav_menu.jobs.job_detail.requested_item;

import static com.eot_app.utility.AppUtility.getJsonObject;

import android.content.Context;
import android.util.Log;

import com.eot_app.nav_menu.jobs.job_detail.invoice.inventry_pkg.Inventry_ReQ_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.inventry_pkg.Inventry_ReS_Model;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.BrandData;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.GetListModel;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
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

public class AddUpdateReqItem_PC implements AddUpdateReqItem_PI {
    private final AddUpdateRquestedItemActivity rquestedItemActivity;
    private final int updatelimit;
    private int count;
    private int updateindex;
    List<Inventry_ReS_Model> finalList = new ArrayList<>();

    public AddUpdateReqItem_PC(AddUpdateRquestedItemActivity rquestedItemActivity) {
        this.rquestedItemActivity = rquestedItemActivity;
        this.updatelimit = AppConstant.LIMIT_HIGH;
    }

    @Override
    public void getInventryItemList() {
        rquestedItemActivity.setItemdata(AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).invoiceItemDao().getInventryItemList());

    }
    @Override
    synchronized public void getDataFromServer(final String search) {
        Log.e("data--->>>", "data--->>>");
        if (AppUtility.isInternetConnected()) {
            //   AppUtility.progressBarShow((Context) invoiceItem_view);
            Inventry_ReQ_Model inventry_model = new Inventry_ReQ_Model(
                    Integer.parseInt(App_preference.getSharedprefInstance().getLoginRes().getCompId()),
                    search,
                    updatelimit, updateindex, "");//

            String data = new Gson().toJson(inventry_model);

            ApiClient.getservices().eotServiceCall(Service_apis.getItemList, AppUtility.getApiHeaders(), getJsonObject(data))
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
                                Type listType = new TypeToken<List<Inventry_ReS_Model>>()  {
                                }.getType();
                                List<Inventry_ReS_Model> inventryitemlist = new Gson().fromJson(convert, listType);
                                finalList.clear();
                                finalList.addAll(inventryitemlist);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("TAG : error----", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            Log.e("TAG onComplete------", "onComplete");
                            AppUtility.progressBarDissMiss();
                            if ((updateindex + updatelimit) <= count) {
                                updateindex += updatelimit;
                                getDataFromServer(search);
                            } else {
                                if (count != 0) {
                                    App_preference.getSharedprefInstance()
                                            .setInventryItemSyncTime(AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                }
                                updateindex = 0;
                                count = 0;
                                rquestedItemActivity.setItemdata(finalList);
                            }
                        }
                    });
        }
    }
    @Override
    public void getBrandList() {
        List<BrandData> brandDataList = AppDataBase.getInMemoryDatabase(rquestedItemActivity).brandDao().getBrandDataList();
        rquestedItemActivity.setBrandList(brandDataList);
    }

    @Override
    public void updateReqItemApi(AddUpdateRequestedModel addeRequestModel) {
        Log.e("data--->>>", "data--->>>");
            String data = new Gson().toJson(addeRequestModel);
            AppUtility.progressBarShow(rquestedItemActivity);
            ApiClient.getservices().eotServiceCall(Service_apis.updateItemRequest, AppUtility.getApiHeaders(), getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                rquestedItemActivity.showMessage(String.valueOf(jsonObject.get("message")));
                            }else {
                                rquestedItemActivity.showAlertDailog();
                            }
                        }

                        @Override
                        public void onError(Throwable e) { rquestedItemActivity.showAlertDailog();

                            Log.e("TAG : error----", e.getMessage());
                        }
                        @Override
                        public void onComplete() {
                            Log.e("TAG onComplete------", "onComplete");
                            AppUtility.progressBarDissMiss();
                        }
                    });
        }

}
