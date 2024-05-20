package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp;

import com.eot_app.nav_menu.custom_fileds.custom_model.CustOmFiledResModel;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QR_Bar_Pc implements QR_Bar_Pi{
    QR_Bar_View view;

    public QR_Bar_Pc(QR_Bar_View view) {
        this.view = view;
    }


    @Override
    public void getBarCode(String barcode) {
        if (AppUtility.isInternetConnected()) {
            Map<String, String> model = new HashMap<>();
            model.put("barCode", barcode/*"X001O8WDWD"*/);
            String data = new Gson().toJson(model);
            ApiClient.getservices().eotServiceCall(Service_apis.autoGenerateBarcode,
                            AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                if (jsonObject.get("data") != null && !jsonObject.get("data").equals("")) {
                                    String convert = new Gson().toJson(jsonObject.get("data").getAsJsonObject());
                                    QRCOde_Barcode_Res_Model res_Model = new Gson().fromJson(convert, QRCOde_Barcode_Res_Model.class);
                                    QRCOde_Barcode_Res_Model qrcode_res_model = new QRCOde_Barcode_Res_Model();
                                    qrcode_res_model.setBarCode(res_Model.getBarCode());
                                    qrcode_res_model.setBarcodeImg(res_Model.getBarcodeImg());
                                    view.setBarCodeData(qrcode_res_model);
                                } else {
                                    view.alertShow(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                }
                            } else {
                                view.alertShow(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));

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
        }

    }

    @Override
    public void getQRCode(String qrCode) {
        if (AppUtility.isInternetConnected()) {
            Map<String, String> model = new HashMap<>();
            model.put("qrcode", qrCode);
            String data = new Gson().toJson(model);
            ApiClient.getservices().eotServiceCall(Service_apis.autoGenerateQrcode,
                            AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                if(jsonObject.get("data") != null && !jsonObject.get("data").equals("")) {
                                    String convert = new Gson().toJson(jsonObject.get("data").getAsJsonObject());
                                    QRCOde_Barcode_Res_Model res_Model = new Gson().fromJson(convert, QRCOde_Barcode_Res_Model.class);
                                    QRCOde_Barcode_Res_Model qrcode_res_model = new QRCOde_Barcode_Res_Model();
                                    qrcode_res_model.setQrcode(res_Model.getQrcode());
                                    qrcode_res_model.setQrcodeImg(res_Model.getQrcodeImg());
                                    view.setQRCodeData(qrcode_res_model);

                                }else {
                                    view.alertShow(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                }
                            }else {
                                view.alertShow(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
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
        }

    }

    @Override
    public void updateQRCode(String equId,String qrCode) {
        if (AppUtility.isInternetConnected()) {
            Map<String, String> model = new HashMap<>();
            model.put("equId", equId);
            model.put("qrcode", qrCode);
            String data = new Gson().toJson(model);
            ApiClient.getservices().eotServiceCall(Service_apis.updateQRCode,
                            AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                if(jsonObject.get("data") != null && !jsonObject.get("data").equals("")) {
                                    String convert = new Gson().toJson(jsonObject.get("data").getAsJsonObject());
                                    QRCOde_Barcode_Res_Model res_Model = new Gson().fromJson(convert, QRCOde_Barcode_Res_Model.class);
                                    QRCOde_Barcode_Res_Model qrcode_res_model = new QRCOde_Barcode_Res_Model();
                                    qrcode_res_model.setQrcode(res_Model.getQrcode());
                                    qrcode_res_model.setQrcodeImg(res_Model.getQrcodeImg());
                                    view.setQRCodeData(qrcode_res_model);

                                }else {
                                    view.alertShow(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                }
                            }else {
                                view.alertShow(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
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
        }
    }

    @Override
    public void updateBarcode(String equId,String barcode) {
        if (AppUtility.isInternetConnected()) {
            Map<String, String> model = new HashMap<>();
            model.put("equId", equId);
            model.put("barCode", barcode/*"X001O8WDWD"*/);
            String data = new Gson().toJson(model);
            ApiClient.getservices().eotServiceCall(Service_apis.updateBarcode,
                            AppUtility.getApiHeaders(), AppUtility.getJsonObject(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            if (jsonObject.get("success").getAsBoolean()) {
                                if (jsonObject.get("data") != null && !jsonObject.get("data").equals("")) {
                                    String convert = new Gson().toJson(jsonObject.get("data").getAsJsonObject());
                                    QRCOde_Barcode_Res_Model res_Model = new Gson().fromJson(convert, QRCOde_Barcode_Res_Model.class);
                                    QRCOde_Barcode_Res_Model qrcode_res_model = new QRCOde_Barcode_Res_Model();
                                    qrcode_res_model.setBarCode(res_Model.getBarCode());
                                    qrcode_res_model.setBarcodeImg(res_Model.getBarcodeImg());
                                    view.setBarCodeData(qrcode_res_model);
                                } else {
                                    view.alertShow(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                }
                            } else {
                                view.alertShow(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));

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
        }
    }
}
