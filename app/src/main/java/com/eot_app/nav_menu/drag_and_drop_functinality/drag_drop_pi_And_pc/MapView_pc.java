package com.eot_app.nav_menu.drag_and_drop_functinality.drag_drop_pi_And_pc;
import static com.eot_app.utility.AppUtility.getJsonObject;

import android.content.Context;

import androidx.annotation.NonNull;

import com.eot_app.nav_menu.drag_and_drop_functinality.model.DragAndDropMapModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.GetListModel;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
            map.put("jobId","100");
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
                                view.setDragDRopMapData(mapList);
                            }
                        }


                        @Override
                        public void onError(Throwable e) {

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
}
