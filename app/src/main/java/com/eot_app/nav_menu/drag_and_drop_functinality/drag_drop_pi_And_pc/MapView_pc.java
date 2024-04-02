package com.eot_app.nav_menu.drag_and_drop_functinality.drag_drop_pi_And_pc;
import static com.eot_app.utility.AppUtility.getJsonObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.eot_app.nav_menu.appointment.dbappointment.Appointment;
import com.eot_app.nav_menu.drag_and_drop_functinality.model.AddUpdateMapReqModel;
import com.eot_app.nav_menu.drag_and_drop_functinality.model.DragAndDropMapModel;
import com.eot_app.nav_menu.drag_and_drop_functinality.model.MapItemModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.GetListModel;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class MapView_pc implements MapView_pi {
    private final DragDropMap_View view;

    public MapView_pc(DragDropMap_View view) {
        this.view = view;
    }


    @Override
    public void getDragAndDropMapDataApi() {

        if (AppUtility.isInternetConnected()) {
            Map<String,String > map = new HashMap<>();
            map.put("jobId","103");
            String data = new Gson().toJson(map);
            ApiClient.getservices().eotServiceCall(Service_apis.getJobMap, AppUtility.getApiHeaders(),getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type listType = new TypeToken<List<DragAndDropMapModel>>() {
                                }.getType();
                                List<DragAndDropMapModel> mapList = new Gson().fromJson(convert, listType);
                                DragAndDropMapModel dragAndDropMapModel = mapList.get(0);
                                view.setDragDRopMapData(dragAndDropMapModel);
                            }
                        }


                        @Override
                        public void onError(Throwable e) {
                         e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            AppUtility.alertDialog((Context) view, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return null;
                }
            });
        }
    }

    @Override
    public void addUpdateMapItemCordinat(AddUpdateMapReqModel addUpdateList) {
        List<MapItemModel> mapItems1 = addUpdateList.getMapItems();
        Gson gson = new Gson();
        String mapItem = gson.toJson(mapItems1);
        if (AppUtility.isInternetConnected()) {
            RequestBody jobId = null;
            RequestBody mapId = null;
            RequestBody mapItems = null;
            RequestBody mapLength = null;
            RequestBody mapWidth = null;
            RequestBody title = null;
            try {
                jobId = RequestBody.create(addUpdateList.getJobId(), MultipartBody.FORM);
                mapId = RequestBody.create(addUpdateList.getMapId(), MultipartBody.FORM);
                mapItems = RequestBody.create(mapItem, MultipartBody.FORM);
                mapLength = RequestBody.create(addUpdateList.getMapLength(), MultipartBody.FORM);
                mapWidth = RequestBody.create(addUpdateList.getMapWidth(), MultipartBody.FORM);
                title = RequestBody.create(addUpdateList.getTitle(), MultipartBody.FORM);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String mimeType = "";
            MultipartBody.Part body = null;
            if (!TextUtils.isEmpty(addUpdateList.getMapImageUrl())) {
                File file1 = new File(addUpdateList.getMapImageUrl());
                if (file1 != null) {
                    mimeType = URLConnection.guessContentTypeFromName(file1.getName());
                    if (mimeType == null) {
                        mimeType = file1.getName();
                    }
                    RequestBody requestFile = RequestBody.create(file1, MediaType.parse(mimeType));
                    // MultipartBody.Part is used to send also the actual file name
                    body = MultipartBody.Part.createFormData("mapImageUrl", file1.getName() + addUpdateList.getMapImageUrl().substring(addUpdateList.getMapImageUrl().lastIndexOf(".")), requestFile);
                }
            }
            ApiClient.getservices().addUpdateMapData(AppUtility.getApiHeaders(),jobId,mapId,mapItems,mapLength,mapWidth,title,body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonObject());
                                DragAndDropMapModel dragAndDropMapModel   =  new Gson().fromJson(convert, DragAndDropMapModel.class);
//                                view.setDragDRopMapData(dragAndDropMapModel);
                                view.showToastmsg(jsonObject.get("message").toString());

                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("TAG : error----", e.getMessage());
                            AppUtility.progressBarDissMiss();
//                            addJobEquView.finishActivity();
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                            Log.e("TAG onComplete------", "onComplete");
                        }
                    });

         /*   String data = new Gson().toJson(addUpdateList);
            ApiClient.getservices().eotServiceCall(Service_apis.addUpdateMapData, AppUtility.getApiHeaders(),getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                String convert = new Gson().toJson(jsonObject.get("data").getAsJsonArray());
                                Type listType = new TypeToken<List<DragAndDropMapModel>>() {
                                }.getType();
                                List<DragAndDropMapModel> mapList = new Gson().fromJson(convert, listType);
                                view.setDragDRopMapData(mapList);
                            }
                        }


                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        } else {
            AppUtility.alertDialog((Context) view, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return null;
                }
            });*/


        }
    }
}
