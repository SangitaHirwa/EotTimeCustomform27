package com.eot_app.nav_menu.jobs.add_job.add_job_recr.dateTime_pkg;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.eot_app.utility.AppConstant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeDiloag extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private final DateTimeCallBack dateTimeCallBack;
    final Calendar calendar = Calendar.getInstance();
    private final boolean StartRecrCheck;
    int year,month,day;
    boolean isNewCalanderInstance;
    public DateTimeDiloag(DateTimeCallBack dateTimeCallBack, boolean StartRecrCheck) {
        this.dateTimeCallBack = dateTimeCallBack;
        /***@StartRecrCheck use for Prevoius Date Disable for Start Date Filed*******/
        this.StartRecrCheck = StartRecrCheck;
    }
    public DateTimeDiloag(DateTimeCallBack dateTimeCallBack, boolean StartRecrCheck, int year,int month, int day, Boolean isNewCalanderInstance) {
        this.dateTimeCallBack = dateTimeCallBack;
        /***@StartRecrCheck use for Prevoius Date Disable for Start Date Filed*******/
        this.StartRecrCheck = StartRecrCheck;
        this.year = year;
        this.month = month;
        this.day = day;
        this.isNewCalanderInstance = isNewCalanderInstance;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(isNewCalanderInstance) {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        if (StartRecrCheck)
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        return datePickerDialog;
    }


    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DATE,day);
        String currentDateString = "";
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

            Date startDate = formatter.parse(dayOfMonth + "-" + (month + 1) + "-" + year);
            currentDateString = new SimpleDateFormat(AppConstant.DATE_FORMAT, Locale.getDefault()).format(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateTimeCallBack.getDateTimeFromPicker(currentDateString);
    }

}
