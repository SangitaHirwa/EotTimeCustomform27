package com.eot_app.nav_menu.addleave;

import android.app.Application;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.eot_app.common_api_contr.ApiCalServerReqRes;
import com.eot_app.common_api_contr.ApiRequestresponce;
import com.eot_app.nav_menu.jobs.job_controller.ChatController;
import com.eot_app.nav_menu.userleave_list_pkg.UserLeaveResModel;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by Sona-11 on 28/10/21.
 */
public class AddLeaveViewModel extends AndroidViewModel implements ApiCalServerReqRes {
    private final MutableLiveData<Boolean> finishActivity = new MutableLiveData<>();
    private final MutableLiveData<String> showDialogs = new MutableLiveData<>();

    public AddLeaveViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getFinishActivity() {
        return finishActivity;
    }

    public MutableLiveData<String> getShowDialogs() {
        return showDialogs;
    }

    public void getLeaveApiCall(String reason, String note, String startDateTime, String finishDateTime,int ltId) {
        LeaveReqModel leaveReqModel = new LeaveReqModel(reason, note, startDateTime, finishDateTime,ltId);
        ApiRequestresponce requestor = new ApiRequestresponce(this, LEAVEReqCode);
        requestor.sendReqOnServerGetRes(Service_apis.addLeave, leaveReqModel);
    }

    @Override
    public void onSuccess(Object successObject, int requestCode) {
        JsonObject jsonObject = (JsonObject) successObject;

        if (requestCode == LEAVEReqCode) {
            if (jsonObject.get("success").getAsBoolean()) {
                EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()));
                String transVarModel = new Gson().toJson(jsonObject.get("data").getAsJsonObject());
                UserLeaveResModel userLeaveResModel = new Gson().fromJson(transVarModel, UserLeaveResModel.class);
                String s1 = (String.valueOf(Html.fromHtml("<b>" + App_preference.getSharedprefInstance().getLoginRes().getFnm()+" "+App_preference.getSharedprefInstance().getLoginRes().getLnm()+ "</b>")));

                String tempMsg = "";
                String startDate =  AppUtility.getDateWithFormate(Long.parseLong(userLeaveResModel.getStartDateTime()),
                        "MMMM dd");
                String finishtDate =  AppUtility.getDateWithFormate(Long.parseLong(userLeaveResModel.getFinishDateTime()),
                       "MMMM dd");
                String [] splitStartDate = startDate.trim().split(" ");
                String [] splitFinishDate = finishtDate.trim().split(" ");
                if(splitStartDate[1].equalsIgnoreCase("01")||splitStartDate[1].equalsIgnoreCase("21")||splitStartDate[1].equalsIgnoreCase("31")){
                    startDate = startDate+"st";
                }else if(splitStartDate[1].equalsIgnoreCase("02")||splitStartDate[1].equalsIgnoreCase("22")){
                    startDate = startDate+"nd";
                }else if(splitStartDate[1].equalsIgnoreCase("03")||splitStartDate[1].equalsIgnoreCase("23")){
                    startDate = startDate+"rd";
                }else {
                    startDate = startDate+"th";
                }
                if(splitFinishDate[1].equalsIgnoreCase("01")||splitFinishDate[1].equalsIgnoreCase("21")||splitFinishDate[1].equalsIgnoreCase("31")){
                    finishtDate = finishtDate+"st";
                }else if(splitFinishDate[1].equalsIgnoreCase("02")||splitFinishDate[1].equalsIgnoreCase("22")){
                    finishtDate = finishtDate+"nd";
                }else if(splitFinishDate[1].equalsIgnoreCase("03")||splitFinishDate[1].equalsIgnoreCase("23")){
                    finishtDate = finishtDate+"rd";
                }else {
                    finishtDate = finishtDate+"th";
                }
                if(startDate.equalsIgnoreCase(finishtDate)){
                    tempMsg = s1+" has submitted a request for leave on "+startDate;
                }else {
                    tempMsg = s1+" has submitted a request for leave From "+startDate+" to "+finishtDate;
                }


                ChatController.getInstance().notifyWeBforNew("LEAVE", "ReqLeave", App_preference.getSharedprefInstance().getLoginRes().getUsrId(), tempMsg, "");
                finishActivity.setValue(true);

            } else if (jsonObject.get("statusCode") != null && jsonObject.get("statusCode").getAsString().equals(AppConstant.SESSION_EXPIRE)) {
                EotApp.getAppinstance().sessionExpired();
            } else {
                String start = "", end = "", myMSg = null;
                try {
                    String transVarModel = new Gson().toJson(jsonObject.get("transVar").getAsJsonObject());
                    LeaveReSModel dailyMsgResModel = new Gson().fromJson(transVarModel, LeaveReSModel.class);
                    myMSg = "";
                    end = "{{endDateTime}}";
                    start = "{{startDateTime}}";

                    if (LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()).contains("{{startDateTime}}")) {
                        myMSg = LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()).replace(start, dailyMsgResModel.getStartDateTime());

                    }
                    if (LanguageController.getInstance().getServerMsgByKey(jsonObject.get("message").getAsString()).contains("{{endDateTime}}")) {
                        myMSg = myMSg.replace(end, dailyMsgResModel.getEndDateTime());
                    }
                } catch (Exception jsonSyntaxException) {
                    jsonSyntaxException.printStackTrace();
                }
                showMyDialog(myMSg);
            }
        }
    }

    private void showMyDialog(String msg) {
        showDialogs.setValue(msg);
    }

    @Override
    public void onError(Throwable errorObject, int requestCode) {
        EotApp.getAppinstance().showToastmsg(errorObject.getMessage());
        finishActivity.setValue(true);

        try {
            AppUtility.progressBarDissMiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
