package com.eot_app.nav_menu.jobs.job_detail.reschedule;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.eot_app.R;
import com.eot_app.home_screens.NetworkUtill;
import com.eot_app.nav_menu.appointment.addupdate.AddAppointmentActivity;
import com.eot_app.nav_menu.jobs.add_job.Add_job_activity;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.reschedule.reschedule_mvp.RescheduleRequest;
import com.eot_app.nav_menu.jobs.job_detail.reschedule.reschedule_mvp.Reschedule_PC;
import com.eot_app.nav_menu.jobs.job_detail.reschedule.reschedule_mvp.Reschedule_PI;
import com.eot_app.nav_menu.jobs.job_detail.reschedule.reschedule_mvp.Reschedule_view;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Created by Mahendra Dabi on 21-08-2020.
 */
public class RescheduleActivity extends AppCompatActivity implements View.OnClickListener, Reschedule_view {
    Reschedule_PI reschedule_pi;
    String date_str, time_str, date_en, time_en, schdlStart, schdlFinish;
    private int year, month, day, mHour, mMinute;
    Button cancel_btn, submit_btn, date_time_clear_btn;
    TextView date_start, time_start, date_end, time_end, schel_start, schel_end;
    private Job job;
    final Calendar cStart = Calendar.getInstance();
    final Calendar cEnd = Calendar.getInstance();
    final Calendar cTStart = Calendar.getInstance();
    final Calendar cTEnd = Calendar.getInstance();
   boolean isTime24Format = false;
    View offline_Hide_show_cl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_reschedule);
        Toolbar toolbar = findViewById(R.id.toolbar_status_offline);
        setSupportActionBar(toolbar);
        offline_Hide_show_cl = findViewById(R.id.offlineStatusBar);
        NetworkUtill.registerNetworkReceiver(EotApp.getCurrentActivity(),offline_Hide_show_cl);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.reschedule));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.reschedule));
        initViews();

    }

    private void initViews() {

        date_time_clear_btn = findViewById(R.id.date_time_clear_btn);
        date_time_clear_btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.clear));

        schel_start = findViewById(R.id.schel_start);
        schel_start.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.shdl_start));

        schel_end = findViewById(R.id.schel_end);
        schel_end.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.shdl_end));

        date_start = findViewById(R.id.date_start);
        date_start.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.date_form));

        date_end = findViewById(R.id.date_end);
        date_end.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.date_form));

        time_start = findViewById(R.id.time_start);
        time_start.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.time_form));

        time_end = findViewById(R.id.time_end);
        time_end.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.time_form));


        submit_btn = findViewById(R.id.submit_btn);
        submit_btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.reschedule));

        cancel_btn = findViewById(R.id.button_cancel);
        cancel_btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel));


        date_time_clear_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);


        date_start.setOnClickListener(this);
        date_end.setOnClickListener(this);
        time_start.setOnClickListener(this);
        time_end.setOnClickListener(this);

        reschedule_pi = new Reschedule_PC(this);

        if (getIntent().hasExtra("job")) {
            String str = getIntent().getExtras().getString("job");
            job = new Gson().fromJson(str, Job.class);
            //  job = getIntent().getParcelableExtra("job");
            prefillTimes();
        }


    }

    private void prefillTimes() {
        if (job == null) return;
        setScheduleDates();
    }

    private void setScheduleDates() {
        if (job != null && !TextUtils.isEmpty(job.getSchdlStart())) {
            try {
                long longStartTime = Long.parseLong(job.getSchdlStart());
                String timeFormat = AppUtility.getDateWithFormate(longStartTime,
                        AppUtility.dateTimeByAmPmFormate("hh:mm a", "HH:mm"));
                time_start.setText(timeFormat);
                time_str = timeFormat;
                if (App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable() != null &&
                        App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("0")) {
                    String time_format = AppUtility.getDateWithFormates(longStartTime,
                            "HH:mm");
                    String[] datestr =  time_format.split(" ");
                    String[] time_ary_end = datestr[0].split(":");
                    cTStart.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time_ary_end[0]));
                    cTStart.set(Calendar.MINUTE, Integer.parseInt(time_ary_end[1]));

                }else{
                    String[] time_ary_str = timeFormat.split(":");
                    cTStart.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time_ary_str[0]));
                    cTStart.set(Calendar.MINUTE,Integer.parseInt(time_ary_str[1]));
                }

                String dateFormat = AppUtility.getDateWithFormate(longStartTime, "dd-MMM-yyyy");
                date_start.setText(dateFormat);
                date_str = dateFormat;

                String[] dat_ary_start = AppUtility.getDateWithFormates(longStartTime, "dd-MM-yyyy").split("-");
                if(dat_ary_start.length > 0) {
                    cStart.set(Calendar.YEAR, Integer.parseInt(dat_ary_start[2]));
                    cStart.set(Calendar.MONTH, Integer.parseInt(dat_ary_start[1])-1);
                    cStart.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dat_ary_start[0]));
                }

                long endTime = Long.parseLong(job.getSchdlFinish());
                timeFormat = AppUtility.getDateWithFormate(endTime,
                        AppUtility.dateTimeByAmPmFormate("hh:mm a", "HH:mm"));
                time_end.setText(timeFormat);
                time_en = timeFormat;

                if (App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable() != null &&
                        App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("0")) {
                    String time_format = AppUtility.getDateWithFormates(endTime,
                            "HH:mm");
                    String[] datestr =  time_format.split(" ");
                    String[] time_ary_end = datestr[0].split(":");
                    cTEnd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time_ary_end[0]));
                    cTEnd.set(Calendar.MINUTE, Integer.parseInt(time_ary_end[1]));

                }else{
                    String[] time_ary_end = timeFormat.split(":");
                    cTEnd.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time_ary_end[0]));
                    cTEnd.set(Calendar.MINUTE,Integer.parseInt(time_ary_end[1]));
                }

                dateFormat = AppUtility.getDateWithFormate(endTime, "dd-MMM-yyyy");
                String[] dat_ary_end = AppUtility.getDateWithFormates(endTime, "dd-MM-yyyy").split("-");
                if(dat_ary_end.length > 0) {
                    cEnd.set(Calendar.YEAR, Integer.parseInt(dat_ary_end[2]));
                    cEnd.set(Calendar.MONTH, Integer.parseInt(dat_ary_end[1])-1);
                    cEnd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dat_ary_end[0]));
                }
                date_end.setText(dateFormat);
                date_en = dateFormat;
            } catch (Exception ex) {

            }
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_time_clear_btn:
                setDateTimeEmptyTime();
                break;

            case R.id.button_cancel:
                onBackPressed();
                break;

            case R.id.submit_btn:
                rescheduleJob();
                break;

            case R.id.date_start:
                SelectDate();
                break;
            case R.id.date_end:
                SelectDate1();
                break;
            case R.id.time_start:
                SelectTime();
                break;
            case R.id.time_end:
                SelectTime1();
                break;
        }
    }


    private void setDateTimeEmptyTime() {
        date_start.setText("");
        time_start.setText("");
        date_end.setText("");
        time_end.setText("");
        date_start.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.date_form));
        time_start.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.time_form));
        date_end.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.date_form));
        time_end.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.time_form));
        date_str = time_str = date_en = time_en = "";
    }

    private void rescheduleJob() {
        if (job != null) {
            if (TextUtils.isEmpty(date_str) || TextUtils.isEmpty(time_str)
                    || TextUtils.isEmpty(date_en) || TextUtils.isEmpty(time_en)) {
                showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.schedule_date_required));
                return;
            } else if (TextUtils.isEmpty(date_str)) {
                schdlStart = schdlFinish = "";
                return;
            } else {
                schdlStart = date_str + " " + time_str;
                schdlFinish = date_en + " " + time_en;
            }

            if (!conditionCheck(schdlStart, schdlFinish)) {
                EotApp.getAppinstance().
                        showToastmsg(LanguageController.getInstance().getMobileMsgByKey(AppConstant.Sche_end_start_time));
            } else {
                if (!schdlStart.equals("") && (App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable()
                        != null && App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("1"))) {
                    Date startDate = null, endDate = null;
                    String[] words = time_str.split(":");
                    int t1 = Integer.valueOf(words[0]);
                    String[] words2 = time_str.split(":");
                    int t2 = Integer.valueOf(words2[0]);
                    try {
                        if (t1 != 12) {
                            startDate = new SimpleDateFormat("hh:mm", Locale.ENGLISH).parse(time_str);
                            time_str = "";
                            time_str = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(startDate);
                        } else {
                            time_str = "";
                            time_str = time_start.getText().toString() + " " + "PM";
                            //   time_str = time_str + " " + "PM";
                        }
                        if (t2 != 12) {
                            endDate = new SimpleDateFormat("hh:mm", Locale.ENGLISH).parse(time_en);
                            time_en = "";
                            time_en = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(endDate);
                        } else {
                            //time_en = time_en + " " + "PM";
                            time_en = "";
                            time_en = time_end.getText().toString() + " " + "PM";
                            //   time_en = time_en + " " + "PM";
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    schdlStart = date_str + " " + time_str;
                    schdlFinish = date_en + " " + time_en;
                }

                RescheduleRequest request = new RescheduleRequest(
                        job.getJobId(),
                        App_preference.getSharedprefInstance().getLoginRes().getUsrId(),
                        schdlFinish,
                        schdlStart
                );
                reschedule_pi.RescheduleJob(request);
            }
        }

    }


    //get start date
    private void SelectDate() {
        year = cStart.get(Calendar.YEAR);
        month = cStart.get(Calendar.MONTH);
        day = cStart.get(Calendar.DAY_OF_MONTH);
        showDialogPicker(R.id.date_start);
    }

    //get end date
    private void SelectDate1() {
        year = cEnd.get(Calendar.YEAR);
        month =cEnd.get(Calendar.MONTH);
        day = cEnd.get(Calendar.DAY_OF_MONTH);
        showDialogPicker(R.id.date_end);
    }

    //schedule start time
    private void SelectTime() {
        mHour = cTStart.get(Calendar.HOUR_OF_DAY);
        mMinute = cTStart.get(Calendar.MINUTE);
        showDialogPicker(R.id.time_start);
    }

    //schedule end time
    private void SelectTime1() {
        mHour = cTEnd.get(Calendar.HOUR_OF_DAY);
        mMinute = cTEnd.get(Calendar.MINUTE);
        showDialogPicker(R.id.time_end);
    }


    //start date,time must be grater than to end date time
    private boolean conditionCheck(String schdlStart, String schdlFinish) {
        Locale.getDefault().getDisplayLanguage();
        try {
            SimpleDateFormat gettingfmt = new SimpleDateFormat(
                    AppUtility.dateTimeByAmPmFormate("dd-MMM-yyyy hh:mm a",
                            "dd-MMM-yyyy HH:mm"), Locale.US);//, Locale.US
            Date date = gettingfmt.parse(schdlStart);
            date.getTime();

            Date date1 = gettingfmt.parse(schdlFinish);
            date1.getTime();
            if (date1.getTime() > date.getTime())
                return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void showErrorDialog(String msg) {
        AppUtility.error_Alert_Dialog(this, msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok)
                , new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return null;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    void showDialogPicker(int id) {
        if (App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable() != null
                && App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("0")) {
            isTime24Format = false;
        }else{
            isTime24Format = true;
        }
        switch (id) {
            case R.id.date_start:
                final DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        cStart.set(Calendar.YEAR, selectedYear);
                        cStart.set(Calendar.MONTH, selectedMonth);
                        cStart.set(Calendar.DAY_OF_MONTH, selectedDay);
                        String dateselect = "";
                        try {
                            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);//hh:mm:ss a
                            Date endDate = formatter.parse(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
                            dateselect = new SimpleDateFormat(AppConstant.DATE_FORMAT, Locale.US).format(endDate);
                            date_start.setText(dateselect);
                            date_en = date_str = dateselect;
                            date_end.setText(date_en);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                };
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(RescheduleActivity.this, datePickerListener1, year, month, day);
                datePickerDialog1.getDatePicker();
                datePickerDialog1.updateDate(year, month, day);
                datePickerDialog1.show();
                break;
               /* DatePickerDialog datePickerDialogSelectDate = new DatePickerDialog(this, AppUtility.InputDateSet(this, new Add_job_activity.DateTimeCallback() {
                    @Override
                    public void setDateTime(String dateTime) {
                        date_start.setText(dateTime);
                        date_end.setText(dateTime);
                        date_en = date_str = dateTime;
                    }
                }, LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_start_end_date)), year, month, day);
                datePickerDialogSelectDate.show();
                break;
*/
            case R.id.date_end:
                final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        cEnd.set(Calendar.YEAR, selectedYear);
                        cEnd.set(Calendar.MONTH, selectedMonth);
                        cEnd.set(Calendar.DAY_OF_MONTH, selectedDay);
                        String dateselect = "";
                        try {
                            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);//hh:mm:ss a
                            Date endDate = formatter.parse(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
                            dateselect = new SimpleDateFormat(AppConstant.DATE_FORMAT, Locale.US).format(endDate);
                            date_end.setText(dateselect);
                            date_en = dateselect;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(RescheduleActivity.this, datePickerListener, year, month, day);
                datePickerDialog.getDatePicker();
                datePickerDialog.updateDate(year, month, day);
                datePickerDialog.show();
                break;

               /* final DatePickerDialog datePickerDialog = new DatePickerDialog(this, AppUtility.CompareInputOutputDate(this, new Add_job_activity.DateTimeCallback() {
                    @Override
                    public void setDateTime(String dateTime) {
                        date_end.setText(dateTime);
                        date_en = dateTime;
                    }
                }, LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_due_start_date)), year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
                break;*/

            case R.id.time_start:

              /*  TimePickerDialog timePickerDialog = new TimePickerDialog(this, AppUtility.InputTimeSet(this, new Add_job_activity.DateTimeCallback() {
                    @Override
                    public void setDateTime(String dateTime) {
                        time_str = dateTime;
                        DecimalFormat formatter = new DecimalFormat("00");
                        String[] aa = dateTime.split(":");
                        time_start.setText((formatter.format(Integer.parseInt(aa[0]))) + ":" + aa[1]);
                    }
                }, LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_start_end_time)), mHour, mMinute, isTime24Format);
                timePickerDialog.show();
                break;*/
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        cTStart.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        cTStart.set(Calendar.MINUTE,minute);
                        String dateTime = AppUtility.updateTime(hourOfDay,minute);
                        time_str = dateTime;
                        DecimalFormat formatter = new DecimalFormat("00");
                        String[] aa = dateTime.split(":");
                        time_start.setText((formatter.format(Integer.parseInt(aa[0]))) + ":" + aa[1]);
                    }
                },mHour, mMinute,isTime24Format);
                timePickerDialog.show();
                break;
            case R.id.time_end:
                TimePickerDialog timePickerDialog1 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        cTEnd.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        cTEnd.set(Calendar.MINUTE,minute);
                        String dateTime = AppUtility.updateTime(hourOfDay,minute);
                        time_en = dateTime;
                        DecimalFormat formatter = new DecimalFormat("00");
                        String[] aa = dateTime.split(":");
                        time_end.setText((formatter.format(Integer.parseInt(aa[0]))) + ":" + aa[1]);
                    }
                },mHour, mMinute,isTime24Format);
                timePickerDialog1.show();
                break;
                /*TimePickerDialog timePickerDialog1 = new TimePickerDialog(this, AppUtility.OutPutTime(this, new Add_job_activity.DateTimeCallback() {
                    @Override
                    public void setDateTime(String dateTime) {
                        time_en = dateTime;
                        DecimalFormat formatter = new DecimalFormat("00");
                        String[] aa = dateTime.split(":");
                        time_end.setText((formatter.format(Integer.parseInt(aa[0]))) + ":" + aa[1]);
                    }
                }, LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_due_start_time)), mHour, mMinute, isTime24Format);
                timePickerDialog1.show();
                break;*/

        }
    }

    @Override
    public void onRescheduleCompleted(String startTime, String endTime) {
        Intent intent = new Intent();
        intent.putExtra("stime", startTime);
        intent.putExtra("etime", endTime);
        setResult(RESULT_OK, intent);
        finish();
    }
}
