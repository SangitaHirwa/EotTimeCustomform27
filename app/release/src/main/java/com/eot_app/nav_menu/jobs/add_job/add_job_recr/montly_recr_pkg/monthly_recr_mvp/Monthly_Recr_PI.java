package com.eot_app.nav_menu.jobs.add_job.add_job_recr.montly_recr_pkg.monthly_recr_mvp;

import com.eot_app.nav_menu.jobs.add_job.add_job_recr.daily_recr_pkg.daily_recur_model.DailyMsgReqModel;

/**
 * Created by Sona-11 on 23/3/21.
 */
public interface Monthly_Recr_PI {
    void getApiMontlyRecurMsg(DailyMsgReqModel dailyMsgReqModel);
//    void getEndDateForDayRadio(String startDate, String day, String month, String occurance, String defaultJobDateTime);
}
