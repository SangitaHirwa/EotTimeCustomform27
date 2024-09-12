package com.eot_app.nav_menu.jobs.job_list;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.eot_app.nav_menu.jobs.job_controller.ChatController;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobPositionGetWorker extends Worker {
   static List<Job> lists = new ArrayList<>();
    PositionSetListner positionSetListner ;
    int inprogress_pos = -1;
    int today_pos = -1;
    int nearToday_pos = -1;
    int unScheduleHeaderPos = -1;
    public JobPositionGetWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);}
   
    @NonNull
    @Override
    public Result doWork() {
        positionSetListner =  new JobList();
            if (lists != null) {
                for (int i = 0; i < lists.size(); i++) {
                    Job item = lists.get(i);
                    try {
                            if (!item.getTempId().equals(item.getJobId())) {
                                    if (item.getStatus().equals(AppConstant.In_Progress)) {
                                        inprogress_pos = i;
                                    }

                                    try {
                                        if (!TextUtils.isEmpty(item.getSchdlStart()) && today_pos == -1) {
                                            Long datelong = Long.parseLong(item.getSchdlStart()) * 1000;
                                            if (AppUtility.isToday(new Date(datelong))) {
                                                {
                                                    today_pos = i;
                                                }
                                            } else if (today_pos == -1) {
                                                Date date1 = new Date(System.currentTimeMillis());
                                                Date date2 = new Date(datelong);
                                                if (AppUtility.isAfterDay(date2, date1))
                                                    nearToday_pos = i;
                                            }
                                        }else{
                                            if(TextUtils.isEmpty(item.getSchdlStart()) && unScheduleHeaderPos == -1){
                                                unScheduleHeaderPos = i;
                                            }
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                            }

                    }
                    catch (Exception exception){
                            exception.printStackTrace();
                        }
            }
                    Log.e("JobPositionGetWorker", "setListData");
                    positionSetListner.setJobPosition(today_pos,inprogress_pos,unScheduleHeaderPos,nearToday_pos);
            }
        
        return Result.success();
    }
    public static void setListData(List<Job> jobLists) {
        lists = jobLists;
    }
    public interface PositionSetListner {
        void setJobPosition(int todayPos, int inProgressPos, int unScheduleHeaderPos,int nearTodayPos);
    }
}
