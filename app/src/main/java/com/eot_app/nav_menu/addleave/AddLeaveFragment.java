package com.eot_app.nav_menu.addleave;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.eot_app.R;
import com.eot_app.databinding.FragmentAddLeaveBinding;
import com.eot_app.nav_menu.quote.add_quotes_pkg.AddQuotes_Activity;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.util_interfaces.MySpinnerAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class AddLeaveFragment extends AppCompatActivity implements View.OnClickListener, TextWatcher,LeaveList_view{

    FragmentAddLeaveBinding binding;
    private UserLeave_pi userLeave_pi;
    List<LeaveUserModel> listuser;
    final Calendar mcurrentTime = Calendar.getInstance();
    final Calendar mEndTime = Calendar.getInstance();

    private String STARTSELCTEDATE = "", STARTSELCTETIME = " 12:00 AM", ENDSELCTETIME = " 11:59 PM", ENDSELCTEDATE = "";
    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            try {
                if(App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("0")) {
                    isTime24Format = false;
                }else {
                    isTime24Format = true;
                }
                if (view.getTag().equals("time_from")) {
                    myCalendar.set(Calendar.YEAR,selectedYear);
                    myCalendar.set(Calendar.MONTH,selectedMonth);
                    myCalendar.set(Calendar.DAY_OF_MONTH,selectedDay);
                    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);//hh:mm:ss a
                    Date startDate = formatter.parse(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
                    STARTSELCTEDATE = "";
                    assert startDate != null;
                    STARTSELCTEDATE = " " + new SimpleDateFormat(AppConstant.DATE_FORMAT, Locale.US).format(startDate);
                    binding.timeFrom.setText("");
                    binding.timeFrom.setText(STARTSELCTEDATE.concat(STARTSELCTETIME));
                    hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    minute = mcurrentTime.get(Calendar.MINUTE);
                    selectStartTime("START");


                } else if (view.getTag().equals("time_to")) {
                    myCalendar1.set(Calendar.YEAR,selectedYear);
                    myCalendar1.set(Calendar.MONTH,selectedMonth);
                    myCalendar1.set(Calendar.DAY_OF_MONTH,selectedDay);
                    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);//hh:mm:ss a
                    Date startDate = formatter.parse(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
                    ENDSELCTEDATE = "";
                    assert startDate != null;
                    ENDSELCTEDATE = " " + new SimpleDateFormat(AppConstant.DATE_FORMAT, Locale.US).format(startDate);
                    binding.timeTo.setText("");
                    binding.timeTo.setText(ENDSELCTEDATE.concat(ENDSELCTETIME));
                    hour = mEndTime.get(Calendar.HOUR_OF_DAY);
                    minute = mEndTime.get(Calendar.MINUTE);
                    selectStartTime("END");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    };
    private AddLeaveViewModel addLeaveViewModel;
    int year, month, day,hour,minute;
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar1 = Calendar.getInstance();
    boolean isTime24Format= false;
    public AddLeaveFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_leave));

        addLeaveViewModel = new ViewModelProvider(this).get(AddLeaveViewModel.class);
        addLeaveViewModel.getFinishActivity().observe(this, aBoolean -> {
            if (aBoolean) {
                try {
                    AppUtility.progressBarDissMiss();
                    setResult(RESULT_OK, new Intent());
                    finish();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                initilaleDateTimeset();
                binding.notesEdt.setText("");
                binding.reasonEdt.setText("");
            }
        });


        addLeaveViewModel.getShowDialogs().observe(this, s -> {
            AppUtility.progressBarDissMiss();
            showMyDialog(s);
        });

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_add_leave);
        binding.userName.setOnClickListener(this);
        userLeave_pi=new UserLeave_pc(this);
        userLeave_pi.getuserlist();
        setUiLables();
    }


    public void setuserlist()
    {
        int index = 0;
        String[] list=new String[listuser.size()];
        AppUtility.spinnerPopUpWindow(binding.languageSpinner);
        for (LeaveUserModel ulist : listuser) {
            list[index] = ulist.getLeaveType();
            index++;
        }
        binding.languageSpinner.setAdapter(new MySpinnerAdapter(this,list));
        binding.languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LeaveUserModel leaveUserModel=listuser.get(i);
                binding.userName.setText(leaveUserModel.getLeaveType());
                Log.d("userid",leaveUserModel.getLtId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void showMyDialog(String msg) {
        AppUtility.alertDialog(this, "",
                msg,
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
    }

    private void selectStartTime(final String tag) {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, (timePicker, selectedHour, selectedMinute) -> {
            String stime = null;
            if(App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("0")) {
                 stime = updateTimeS(selectedHour, selectedMinute);
            }else {
                stime = String.valueOf(selectedHour) + ':' +
                        selectedMinute + " ";
            }
                if (tag.equals("START")) {
                    mcurrentTime.set(Calendar.HOUR_OF_DAY,selectedHour);
                    mcurrentTime.set(Calendar.MINUTE,selectedMinute);
                    STARTSELCTETIME = "";
                    STARTSELCTETIME = " " + stime;
                    binding.timeFrom.setText(STARTSELCTEDATE.concat(STARTSELCTETIME));
                } else if (tag.equals("END")) {
                    mEndTime.set(Calendar.HOUR_OF_DAY,selectedHour);
                    mEndTime.set(Calendar.MINUTE,selectedMinute);
                    ENDSELCTETIME = "";
                    ENDSELCTETIME = " " + stime;
                    binding.timeTo.setText(ENDSELCTEDATE.concat(ENDSELCTETIME));
                }
            
        }, hour, minute, isTime24Format);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    public String updateTimeS(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        return String.valueOf(hours) + ':' +
                minutes + " " + timeSet;


    }


    private void setUiLables() {

        initilaleDateTimeset();
        AppUtility.setupUI(binding.parentLayout, this);

        binding.notesEdt.setText("");
        binding.reasonEdt.setText("");

        binding.notesEdt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.notes));
        binding.reasonEdt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_reason_leave));

        binding.submitButton.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_leave));
        createListners();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    private void initilaleDateTimeset() {
            if(App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("1")){
                STARTSELCTETIME = " 00:00";
                ENDSELCTETIME = " 23:59";
            }
        binding.timeFrom.setText(AppUtility.getDateByFormat(AppConstant.DATE_FORMAT).concat(" "+STARTSELCTETIME));
        binding.timeTo.setText(AppUtility.getDateByFormat(AppConstant.DATE_FORMAT).concat(" "+ENDSELCTETIME));
        listuser=new ArrayList<>();
        emptyfields();
    }

    private void createListners() {
        binding.timeFrom.setOnClickListener(this);
        binding.timeTo.setOnClickListener(this);

        Objects.requireNonNull(binding.addNotesLayout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(binding.addReasonLayout.getEditText()).addTextChangedListener(this);
        binding.submitButton.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time_from:
                selectStartDate("time_from");
                break;
            case R.id.time_to:
                SelectEndDate("time_to");
                break;
            case R.id.user_name:
                binding.languageSpinner.performClick();
                break;
            case R.id.submit_button:
                Date startDate, endDate;
                String s = "", e = "", datetiemform;
                if (App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("0")){
                    datetiemform = AppConstant.DATE_FORMAT+" hh:mm a";
                }else{
                    datetiemform = AppConstant.DATE_FORMAT+" HH:mm";
                }
                try {
                    startDate = new SimpleDateFormat(datetiemform, Locale.ENGLISH).parse(binding.timeFrom.getText().toString().trim());
                    endDate = new SimpleDateFormat(datetiemform, Locale.ENGLISH).parse(binding.timeTo.getText().toString().trim());
                    assert startDate != null;
                    s = new SimpleDateFormat(AppConstant.DATE_FORMAT+" hh:mm:ss a", Locale.ENGLISH).format(startDate);
                    assert endDate != null;
                    e = new SimpleDateFormat(AppConstant.DATE_FORMAT+" hh:mm:ss a", Locale.ENGLISH).format(endDate);
                    e = AppUtility.getDate(e);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                if (!AppUtility.compareTwoDatesForTimeSheet2(s, e, AppConstant.DATE_FORMAT+" hh:mm:ss a")) {
                    showMyDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.time_sheet_date_error));
                } else {
                    AppUtility.progressBarShow(this);
                    int id=0;
                    for (int i=0;i<listuser.size();i++)
                    {
                        if (listuser.get(i).getLeaveType().equals(binding.userName.getText().toString()))
                        {
                            id= Integer.parseInt(listuser.get(i).getLtId());
                        }
                    }
                    addLeaveViewModel.getLeaveApiCall(binding.reasonEdt.getText().toString().trim(), binding.notesEdt.getText().toString().trim()
                            , s, e,id);
                }
                break;
        }

    }

    private void selectStartDate(String tag) {
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setTag(tag);
        datePickerDialog.show();
    }
    private void SelectEndDate(String tag) {
        int year = myCalendar1.get(Calendar.YEAR);
        int month = myCalendar1.get(Calendar.MONTH);
        int dayOfMonth = myCalendar1.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePickerListener, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setTag(tag);
        datePickerDialog.show();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (charSequence.length() >= 1) {
            if (charSequence.hashCode() == binding.notesEdt.getText().hashCode())
                binding.addNotesLayout.setHintEnabled(true);
            if (charSequence.hashCode() == binding.reasonEdt.getText().hashCode())
                binding.addReasonLayout.setHintEnabled(true);
        } else if (charSequence.length() <= 0) {
            if (charSequence.hashCode() == binding.notesEdt.getText().hashCode())
                binding.addNotesLayout.setHintEnabled(false);
            if (charSequence.hashCode() == binding.reasonEdt.getText().hashCode())
                binding.addReasonLayout.setHintEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void emptyfields() {
        binding.notesEdt.setText("");
        binding.reasonEdt.setText("");
        binding.notesEdt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.notes));
        binding.reasonEdt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_reason_leave));
        binding.userName.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.select_leave_type));
        binding.addNotesLayout.setHintEnabled(false);
        binding.addReasonLayout.setHintEnabled(false);
    }

    @Override
    public void setuserlist(List<LeaveUserModel> list) {
        this.listuser=list;
        setuserlist();
    }
}