package com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg;

import android.content.Context;
import android.util.Log;

import com.eot_app.activitylog.ActivityLogController;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.JobCardAttachmentModel;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.Get_Email_Message_Res_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.Get_Email_ReQ_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.Get_Email_ReS_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.InvoiceEmaliTemplate;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.send_email_temp_model.Send_Email_ReQ_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.send_email_temp_model.Send_Email_ReS_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.send_email_temp_model.Send_JobCard_Email_ReqModel;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.send_email_temp_model.Send_Quote_Email_Req_Model;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Invoice_Email_pc implements Invoice_Email_pi {
    Invoice_Email_View email_view;
    Context context;

    public Invoice_Email_pc(Invoice_Email_View email_view,Context context) {
        this.email_view = email_view;
        this.context = context;
    }

    @Override
    public void getJobCardetemplateList() {
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.JOB_MODULE,
                    ActivityLogController.JOB_GET_INVOICE_TEMP,
                    ActivityLogController.JOB_MODULE
            );
            ApiClient.getservices().eotServiceCall2(Service_apis.getJobCardTemplates, AppUtility.getApiHeaders())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            try {
                                if (jsonObject.get("success").getAsBoolean()) {
                                    ArrayList<InvoiceEmaliTemplate> templateList = new ArrayList<>();
                                    InvoiceEmaliTemplate invoiceEmaliTemplate = new InvoiceEmaliTemplate();
                                    for (int i = 0; i < jsonObject.get("data").getAsJsonArray().size(); i++) {
                                        if (jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("cltTempNm")!= null &&
                                                !jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("cltTempNm").getAsString().equals("")) {
                                            invoiceEmaliTemplate = new InvoiceEmaliTemplate(jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("jcTempId").getAsString(),
                                                    jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("cltTempNm").getAsString()
                                                    , jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("defaultTemp").getAsString(),false);
                                        } else {

                                            invoiceEmaliTemplate = new InvoiceEmaliTemplate(jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("jcTempId").getAsString(),
                                                    jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("tempJson").getAsJsonObject().get("clientDetails").getAsJsonArray().get(0).getAsJsonObject().get("inputValue").getAsString()
                                                    , jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("defaultTemp").getAsString(),false);
                                        }
                                        templateList.add(invoiceEmaliTemplate);
                                    }
                                    if (templateList != null && templateList.size() > 0) {
                                        email_view.setInvoiceTmpList(templateList);
                                    }

                                } else {
                                    if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                        email_view.setSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                    } else {
                                        email_view.showErrorAlertDialog(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                    }
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    @Override
    public void getJobCardEmailMessageChatList(String jobId) {
        Map<String, String> hm = new HashMap<>();
        hm.put("jobId", jobId);


        JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(hm));

        if (AppUtility.isInternetConnected()) {

            AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.getDetailForCltToFwChat, AppUtility.getApiHeaders(), jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            AppUtility.progressBarDissMiss();
                            if (jsonObject.get("success").getAsBoolean()) {
                                Gson gson = new Gson();
                                if(jsonObject.get("data") != null) {
                                    Get_Email_Message_Res_Model msg_reS_model = gson.fromJson(jsonObject.get("data"), Get_Email_Message_Res_Model.class);
                                      email_view.setChatDataList(msg_reS_model);
                                }else {
                                    email_view.showErrorAlertDialog("ChatDataNull");
                                }
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                email_view.setSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            } else {
                                email_view.showErrorAlertDialog(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", e.getMessage());
                            AppUtility.progressBarDissMiss();
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();

                        }
                    });
        } else {
            EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network));
        }
    }

    @Override
    public void getJobInvoicetemplateList() {
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.JOB_MODULE,
                    ActivityLogController.JOB_GET_INVOICE_TEMP,
                    ActivityLogController.JOB_MODULE
            );
            ApiClient.getservices().eotServiceCall2(Service_apis.getInvoiceTemplates, AppUtility.getApiHeaders())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            try {
                                if (jsonObject.get("success").getAsBoolean()) {
                                    ArrayList<InvoiceEmaliTemplate> templateList = new ArrayList<>();
                                    for (int i = 0; i < jsonObject.get("data").getAsJsonArray().size(); i++) {
                                        InvoiceEmaliTemplate invoiceEmaliTemplate = new InvoiceEmaliTemplate();
                                        if (jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("cltTempNm")!= null &&
                                                !jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("cltTempNm").getAsString().equals("")) {
                                            invoiceEmaliTemplate = new InvoiceEmaliTemplate(jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("invTempId").getAsString(),
                                                    jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("cltTempNm").getAsString()
                                                    , jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("defaultTemp").getAsString(),false);
                                        } else {
                                            invoiceEmaliTemplate = new InvoiceEmaliTemplate(jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("invTempId").getAsString(),
                                                    jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("tempJson").getAsJsonObject().get("invDetail").getAsJsonArray().get(0).getAsJsonObject().get("inputValue").getAsString()
                                                    , jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("defaultTemp").getAsString(),false);
                                        }

                                        templateList.add(invoiceEmaliTemplate);
                                    }
                                    if (templateList != null && templateList.size() > 0) {
                                        email_view.setInvoiceTmpList(templateList);
                                    }

                                } else {
                                    if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                        email_view.setSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                    } else {
                                        email_view.showErrorAlertDialog(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                    }
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    @Override
    public void getQuotesInvoicetemplateList() {
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.JOB_MODULE,
                    ActivityLogController.JOB_GET_INVOICE_TEMP,
                    ActivityLogController.JOB_MODULE
            );
            ApiClient.getservices().eotServiceCall2(Service_apis.getQuotaTemplates, AppUtility.getApiHeaders())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            try {
                                if (jsonObject.get("success").getAsBoolean()) {
                                    ArrayList<InvoiceEmaliTemplate> templateList = new ArrayList<>();
                                    for (int i = 0; i < jsonObject.get("data").getAsJsonArray().size(); i++) {

                                        InvoiceEmaliTemplate invoiceEmaliTemplate = new InvoiceEmaliTemplate();
                                        if (jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("cltTempNm")!= null &&
                                                !jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("cltTempNm").getAsString().equals("")) {
                                            invoiceEmaliTemplate = new InvoiceEmaliTemplate(jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("quoTempId").getAsString(),
                                                    jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("cltTempNm").getAsString()
                                                    , jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("defaultTemp").getAsString(),false);
                                        } else {
                                            invoiceEmaliTemplate = new InvoiceEmaliTemplate(jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("quoTempId").getAsString(),
                                                    jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("tempJson").getAsJsonObject().get("quoDetail").getAsJsonArray().get(0).getAsJsonObject().get("inputValue").getAsString()
                                                    , jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("defaultTemp").getAsString(),false);
                                        }

                                        templateList.add(invoiceEmaliTemplate);
                                    }

                                    if (templateList != null && templateList.size() > 0) {
                                        email_view.setInvoiceTmpList(templateList);
                                    }

                                } else {
                                    if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                        email_view.setSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                    } else {
                                        email_view.showErrorAlertDialog(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                    }
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    @Override
    public void getInvoiceEmailTempApi(String invId, String isProformaInv) {
        Get_Email_ReQ_Model reQ_model = new Get_Email_ReQ_Model(invId, App_preference.getSharedprefInstance().getLoginRes().getCompId());
        reQ_model.setIsProformaInv(isProformaInv);
        JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(reQ_model));
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.JOB_MODULE,
                    ActivityLogController.JOB_GET_INVOICE_TEMP,
                    ActivityLogController.JOB_MODULE
            );
            AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.getInvoiceEmailTemplate, AppUtility.getApiHeaders(), jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            AppUtility.progressBarDissMiss();
                            if (jsonObject.get("success").getAsBoolean()) {
                                Gson gson = new Gson();
                                Get_Email_ReS_Model email_reS_model = gson.fromJson(jsonObject.get("data"), Get_Email_ReS_Model.class);
                                email_view.onGetEmailTempData(email_reS_model);
                            } else {
                                if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                    email_view.setSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                } else {
                                    email_view.showErrorAlertDialog(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", e.getMessage());
                            AppUtility.progressBarDissMiss();
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        } else {
            EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network));
        }
    }

    @Override
    public void sendInvoiceEmailTempApi(String invId, String compId, String messageInHtml, String emailSubject, String emailTo, String emailCc, String isProformaInv, String tempId,Object stripLink, List<JobCardAttachmentModel> Attachment) {
        Send_Email_ReQ_Model reQ_model = new Send_Email_ReQ_Model(invId, compId, messageInHtml, emailSubject, emailTo, emailCc,stripLink,Attachment);
        reQ_model.setIsProformaInv(isProformaInv);
        reQ_model.setTempId(tempId);
        JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(reQ_model));
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.JOB_MODULE,
                    ActivityLogController.JOB_SEND_INVOICE_TEMP,
                    ActivityLogController.JOB_MODULE
            );

            AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.sendInvoiceEmailTemplate, AppUtility.getApiHeaders(), jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            AppUtility.progressBarDissMiss();
                            if (jsonObject.get("success").getAsBoolean()) {
                                Gson gson = new Gson();
                                Send_Email_ReS_Model email_reS_model = gson.fromJson(jsonObject, Send_Email_ReS_Model.class);
                                email_view.onSendInvoiceEmail(email_reS_model);
                            } else {
                                if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                    email_view.setSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                } else {
                                    email_view.showErrorAlertDialog(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", e.getMessage());
                            AppUtility.progressBarDissMiss();
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        } else {
            EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network));
        }
    }

    @Override
    public boolean isInputFieldDataValid(String emailTo, String emailCc, String emailSubject, String emailMessage) {
        if (emailTo.isEmpty()) {
            email_view.showErrorAlertDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.enter_receipt_email_id));
            return false;
        } else if (!AppUtility.isValidMultipleEmail(emailTo)) {
            email_view.showErrorAlertDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.email_error));
            return false;
        } else if (!emailCc.isEmpty()) {
            if (!AppUtility.isValidMultipleEmail(emailCc)) {
                email_view.showErrorAlertDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_valid_email_in_cc));
                return false;
            }
        } else if (emailSubject.isEmpty()) {
            email_view.showErrorAlertDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.enter_subject));
            return false;
        } else if (emailMessage.isEmpty()) {
            email_view.showErrorAlertDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.input_text_email));
            return false;
        }
        return true;
    }

    @Override
    public void getQuotationEmailTemplate(String quotId,boolean attechmentUpload) {
        Map<String, String> hm = new HashMap<>();
        hm.put("quotId", quotId);
        hm.put("compId", App_preference.getSharedprefInstance().getLoginRes().getCompId());

        JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(hm));
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.JOB_MODULE,
                    ActivityLogController.JOB_GET_EMAIL_QOUTE,
                    ActivityLogController.JOB_MODULE
            );
            AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.getQuotationEmailTemplate, AppUtility.getApiHeaders(), jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            AppUtility.progressBarDissMiss();
                            if (jsonObject.get("success").getAsBoolean()) {
                                Gson gson = new Gson();
                                    Get_Email_ReS_Model email_reS_model = gson.fromJson(jsonObject.get("data"), Get_Email_ReS_Model.class);
                                    email_view.onGetEmailTempData(email_reS_model);
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                email_view.setSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            } else {
                                email_view.showErrorAlertDialog(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", e.getMessage());
                            AppUtility.progressBarDissMiss();
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        } else {
            EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network));
        }
    }

    @Override
    public void sendQuotationEmailTemplate(String quotId, String message, String subject, String to, String cc,
                                           String bcc, String from, String fromnm, String tempId, ArrayList<String> quoteAttachmentArray) {

        Send_Quote_Email_Req_Model hm = new Send_Quote_Email_Req_Model(quotId,message,subject,to,cc,bcc,
                from,fromnm,tempId,quoteAttachmentArray);
        JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(hm));
        if (AppUtility.isInternetConnected()) {
            ActivityLogController.saveActivity(
                    ActivityLogController.JOB_MODULE,
                    ActivityLogController.JOB_SEND_QUOTE_TEMP,
                    ActivityLogController.JOB_MODULE
            );
            AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.sendQuotationEmailTemplate, AppUtility.getApiHeaders(), jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            AppUtility.progressBarDissMiss();
                            if (jsonObject.get("success").getAsBoolean()) {
                                Gson gson = new Gson();
                                Send_Email_ReS_Model email_reS_model = gson.fromJson(jsonObject, Send_Email_ReS_Model.class);
//                                need to update this thing
                                email_view.onSendInvoiceEmail(email_reS_model);
                            } else {
                                email_view.showErrorAlertDialog(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", e.getMessage());
                            AppUtility.progressBarDissMiss();
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        } else {
            EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network));
        }


    }

    @Override
    public void getJobDocEmailTemplate(String jobId) {
        Map<String, String> hm = new HashMap<>();
        hm.put("jobId", jobId);

        JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(hm));

        if (AppUtility.isInternetConnected()) {

            AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.getJobDocEmailTemplate, AppUtility.getApiHeaders(), jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            AppUtility.progressBarDissMiss();
                            if (jsonObject.get("success").getAsBoolean()) {
                                Gson gson = new Gson();
                                Get_Email_ReS_Model email_reS_model = gson.fromJson(jsonObject.get("data"), Get_Email_ReS_Model.class);
                                email_view.onGetEmailTempData(email_reS_model);
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                email_view.setSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            } else {
                                email_view.showErrorAlertDialog(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", e.getMessage());
                            AppUtility.progressBarDissMiss();
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        } else {
            EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network));
        }
    }

    @Override
    public void sendJObDocEmailTemplate(String jobId, String pdfPath, String message, String subject, String to, String cc, String bcc, String from, String fromnm) {

        Map<String, String> hm = new HashMap<>();
        hm.put("jobId", jobId);
        hm.put("pdfPath", pdfPath);
        hm.put("message", message);
        hm.put("subject", subject);
        hm.put("to", to);
        hm.put("cc", cc);
        hm.put("moduleType", "2");//2 for appointment doc temp email send message

        JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(hm));
        if (AppUtility.isInternetConnected()) {

            AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.sendJobDocEmailTemplate, AppUtility.getApiHeaders(), jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            AppUtility.progressBarDissMiss();
                            if (jsonObject.get("success").getAsBoolean()) {
                                Gson gson = new Gson();
                                Send_Email_ReS_Model email_reS_model = gson.fromJson(jsonObject, Send_Email_ReS_Model.class);
//                                need to update this thing
                                email_view.onSendInvoiceEmail(email_reS_model);
                            } else {
                                email_view.showErrorAlertDialog(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", e.getMessage());
                            AppUtility.progressBarDissMiss();
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        } else {
            EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network));
        }

    }

    @Override
    public void getJobCardEmailTemplate(String jobId,String tempId, String chatUrl) {
        Map<String, String> hm = new HashMap<>();
        hm.put("jobId", jobId);
        hm.put("tempId", tempId);
        if(!chatUrl.equals(""))
        {
            hm.put("chatUrl", chatUrl);
        }
        JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(hm));

        if (AppUtility.isInternetConnected()) {

//            AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.getJobCardEmailTemplate, AppUtility.getApiHeaders(), jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            AppUtility.progressBarDissMiss();
                            if (jsonObject.get("success").getAsBoolean()) {
                                Gson gson = new Gson();
                                Get_Email_ReS_Model email_reS_model = gson.fromJson(jsonObject.get("data"), Get_Email_ReS_Model.class);
                                email_view.onGetEmailTempData(email_reS_model);
                            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                                email_view.setSessionExpire(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            } else {
                                email_view.showErrorAlertDialog(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", e.getMessage());
                            AppUtility.progressBarDissMiss();
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        } else {
            EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network));
        }
    }

    @Override
    public void sendJobCardEmailTemplate(String jobId, String pdfPath, String message, String subject, String to, String cc, String tempId,String fwid,List<JobCardAttachmentModel> cardAttachmentModelList) {
        Send_JobCard_Email_ReqModel hm = new Send_JobCard_Email_ReqModel(jobId,pdfPath,message,subject,to,cc,tempId,fwid,cardAttachmentModelList);
        JsonObject jsonObject = AppUtility.getJsonObject(new Gson().toJson(hm));
        if (AppUtility.isInternetConnected()) {

            AppUtility.progressBarShow(context);
            ApiClient.getservices().eotServiceCall(Service_apis.sendJobCardEmailTemplate, AppUtility.getApiHeaders(), jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject jsonObject) {
                            AppUtility.progressBarDissMiss();
                            if (jsonObject.get("success").getAsBoolean()) {
                                Gson gson = new Gson();
                                Send_Email_ReS_Model email_reS_model = gson.fromJson(jsonObject, Send_Email_ReS_Model.class);
//                                need to update this thing
                                email_view.onSendInvoiceEmail(email_reS_model);
                            } else {
                                email_view.showErrorAlertDialog(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("", e.getMessage());
                            AppUtility.progressBarDissMiss();
                        }

                        @Override
                        public void onComplete() {
                            AppUtility.progressBarDissMiss();
                        }
                    });
        } else {
            EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network));
        }
    }
}
