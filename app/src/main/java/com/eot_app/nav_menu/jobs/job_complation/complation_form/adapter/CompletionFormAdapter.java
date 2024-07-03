package com.eot_app.nav_menu.jobs.job_complation.complation_form.adapter;

import static com.eot_app.utility.AppUtility.updateTime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_complation.JobCompletionActivity;
import com.eot_app.nav_menu.jobs.job_complation.JobCompletionAdpter;
import com.eot_app.nav_menu.jobs.job_complation.complation_form.BackgroundTaskExecutor;
import com.eot_app.nav_menu.jobs.job_detail.customform.MyAttachment;
import com.eot_app.nav_menu.jobs.job_detail.detail.CompletionAdpterJobDteails;
import com.eot_app.nav_menu.jobs.job_detail.detail.CompletionAdpterJobDteails1;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.MyFormInterFace;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.QuestionListAdapter;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.AnswerModel;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.OptionModel;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.QuesRspncModel;
import com.eot_app.utility.AppCenterLogs;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.DropdownListBean;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.SignatureView;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.settings.setting_db.JobTitle;
import com.eot_app.utility.util_interfaces.JoBServSuggAdpter;
import com.eot_app.utility.util_interfaces.MyAdapter;
import com.eot_app.utility.util_interfaces.NoDefaultSpinner;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class CompletionFormAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Object> item =new ArrayList<>();
    Context context;
    private final ArrayList<QuesRspncModel> duplicateCopy = new ArrayList<>();
    private final MyFormInterFace myFormInterFace;
    private boolean isLoadtoBottom = false;
    private String time = "", date = "";
    private MyAttachment myAttachment;
    private final ArrayList<QuesRspncModel> typeList1 = new ArrayList<>();
    private final int VIEW_TYPE_SINGLE = 0;
    private final int VIEW_TYPE_GROUPED = 1;
    private final int VIEW_TYPE_DEFAULT = 2;
    GroupedViewHolder groupedViewHolder;
    List<QuestionListAdapter> listOfChildAdapter = new ArrayList<>();
    QuesRspncModel question = null;
    int postionDefaultViewHolder = -1;
    ClickListener clickListener;
    Map<Integer,QuestionListAdapter> adapterPosition = new HashMap<>();
    Map<Integer,CompletionAdpterJobDteails1> attachAdapterPosition = new HashMap<>();
    BackgroundTaskExecutor backgroundTaskExecutor;
    boolean isTime24Format = false;
    String sDate="";
    String sTimeDate="";

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        Object item1 = item.get(position);

        if (item1 instanceof List) {
//            if(((List<QuesRspncModel>) item1).get(0).getType().isEmpty()
//            && ((List<QuesRspncModel>) item1).get(0).getType().equals("")){
//                return VIEW_TYPE_DEFAULT;
//            }else {
                return VIEW_TYPE_GROUPED;
//            }
        } else if (item1 instanceof QuesRspncModel) {
            if(((QuesRspncModel) item1).getType().equals("") && ((QuesRspncModel) item1).getType().isEmpty()){
                return VIEW_TYPE_DEFAULT;
            }else {
                return VIEW_TYPE_SINGLE;
            }

        } else {
            throw new IllegalArgumentException("Invalid item type");
        }
    }

    public CompletionFormAdapter(Context context, List<Object> item, MyFormInterFace myFormInterFace, ClickListener clickListener, BackgroundTaskExecutor backgroundTaskExecutor) {
        this.item = item;
        this.context = context;
        this.myFormInterFace = myFormInterFace;
        this.isLoadtoBottom = false;
        this.clickListener = clickListener;
        this.backgroundTaskExecutor = backgroundTaskExecutor;
        String currentDateTime = AppUtility.getDateByFormat(
                AppUtility.dateTimeByAmPmFormate("dd-MMM-yyyy hh:mm a", "dd-MMM-yyyy HH:mm"));
        String[] currentDateTimeArry = currentDateTime.split(" ");
        date = currentDateTimeArry[0];
        try {
            if (App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable() != null &&
                    App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("0"))
                time = currentDateTimeArry[1] + " " + currentDateTimeArry[2];
            else time = currentDateTimeArry[1] + "";

        } catch (Exception e) {
            AppCenterLogs.addLogToAppCenterOnAPIFail("CustomForm","","QuestionListAdapter(constructor)- "+e.getMessage(),"QuestionListAdapter","");
            e.getMessage();
        }
//        setIndex();
    }
    public void updateAdapter(ArrayList<Object> item){
        this.item = item;
        notifyDataSetChanged();
    }
    public ArrayList<QuesRspncModel> getTypeList() {
        typeList1.clear();
        ArrayList<QuesRspncModel> finalList = new ArrayList<>();
        for (Object item : item
             ) {
            if(item instanceof QuesRspncModel){
                typeList1.add((QuesRspncModel) item);
            }
        }
        finalList.addAll(typeList1);
        for (int key: adapterPosition.keySet()
             ) {
            finalList.addAll(adapterPosition.get(key).getTypeList());
        }
        return finalList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.question_list_layout, parent, false);
        switch (viewType) {
            case VIEW_TYPE_GROUPED:
                View groupedView = inflater.inflate(R.layout.grouped_question_list_layout, parent, false);
                groupedViewHolder = new GroupedViewHolder(groupedView);
                return groupedViewHolder;

            case VIEW_TYPE_SINGLE:
                View singleView = inflater.inflate(R.layout.question_list_layout, parent, false);
                return new SingleViewHolder(singleView);

            case VIEW_TYPE_DEFAULT:
                View defaultView = inflater.inflate(R.layout.grouped_question_default_layout, parent, false);
                return new DefaultViewHolder(defaultView);

            default:
                throw new IllegalArgumentException("Invalid view type");
        }
//        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object question = item.get(position);
        if (question instanceof List) {
            // Handle grouped item
            List<QuesRspncModel> questionList = (List<QuesRspncModel>) question;
//            if(questionList.get(0).getSystemField().equals("1")){
//                ((DefaultViewHolder) holder).bindDefaultQuestion(questionList);
//            }else {
                ((GroupedViewHolder) holder).bindGroupedQuestions(questionList,position,(GroupedViewHolder) holder);
                if(adapterPosition.get(position)== null) {
                    GroupedViewHolder groupedViewHold = (GroupedViewHolder) holder;
                    groupedViewHold.questionListAdapter = new QuestionListAdapter(new ArrayList<>(), context, new MyFormInterFace() {
                        @Override
                        public void getAnsId(String ansId) {

                        }
                    }, true, position);

                    adapterPosition.put(position, groupedViewHold.questionListAdapter);
                }

//            }
        } else if (question instanceof QuesRspncModel) {
            // Handle single item
            if(((QuesRspncModel) question).getType().equals("") && ((QuesRspncModel) question).getType().isEmpty()){
                ((DefaultViewHolder) holder).bindDefaultQuestion((QuesRspncModel) question, position, (DefaultViewHolder) holder);
                JobCompletionActivity jobCompletionActivity = (JobCompletionActivity) context;
                jobCompletionActivity.setCompletionNotesPosition(position);
            }else {
                QuesRspncModel singleQuestion = (QuesRspncModel) question;
                ((SingleViewHolder) holder).bindSingleQuestion(singleQuestion, position, (SingleViewHolder) holder);
            }
        }
    }


    @Override
    public int getItemCount() {
        return item.size();
    }
    public class   SingleViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, View.OnClickListener{
        // private final ClientChat_View mlistener;
        TextView tvQuestion, tvDate, que_no, spinner_text, tvTime, tvTimeDate;//, txt_lable;//, textView1;
        EditText type_text, type_text_area, type_Number;
        LinearLayout linearCheck, linearDate, linearTime, linearDateTime;
        RelativeLayout linearSpinner;
        NoDefaultSpinner spinner;
        ImageView timeImg, dateImg;
        CheckBox checkbox_single;
        Button buttonAttchment;
        ImageView signature_set, edit_sign, delete_sign, add_sign, attchment_set, delete_attchment;
        LinearLayout signature_layout, Attchment_layout;
        TextView add_sign_lable, Attchment_lable, txt_upload;
        FrameLayout fm_multiattachment;
        RecyclerView attachmentRecyclerView;
        final Calendar myCalendar = Calendar.getInstance();
        View view;
        private  GridLayoutManager layoutManager;
        public  CompletionAdpterJobDteails1 jobCompletionAdpter;
        List<Attachments> getFileList_res = new ArrayList<>();
        //   private String img_url = "";

        public SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            //  this.mlistener = (ClientChat_View) context;
            Attchment_layout = itemView.findViewById(R.id.Attchment_layout);
            Attchment_lable = itemView.findViewById(R.id.Attchment_lable);
            buttonAttchment = itemView.findViewById(R.id.buttonAttchment);
            attchment_set = itemView.findViewById(R.id.attchment_set);
            delete_attchment = itemView.findViewById(R.id.delete_attchment);
            type_Number = itemView.findViewById(R.id.type_Number);
            signature_layout = itemView.findViewById(R.id.signature_layout);
            signature_set = itemView.findViewById(R.id.signature_set);
            //   signature_set.setOnClickListener(this);
            edit_sign = itemView.findViewById(R.id.edit_sign);
            delete_sign = itemView.findViewById(R.id.delete_sign);
            add_sign = itemView.findViewById(R.id.add_sign);
            add_sign_lable = itemView.findViewById(R.id.add_sign_lable);
            checkbox_single = itemView.findViewById(R.id.checkbox_single);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvDate = itemView.findViewById(R.id.tvDate);
            type_text = itemView.findViewById(R.id.type_text);
            type_text.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_your_ans));
            type_Number.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_your_ans));
            type_text_area = itemView.findViewById(R.id.type_text_area);
            type_text_area.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_your_ans));
            linearCheck = itemView.findViewById(R.id.linearCheck);
            linearDate = itemView.findViewById(R.id.linearDate);
            spinner = itemView.findViewById(R.id.dropdown_spinner);
            linearSpinner = itemView.findViewById(R.id.linearSpinner);
            que_no = itemView.findViewById(R.id.que_no);
            spinner_text = itemView.findViewById(R.id.spinner_text);
            //   txt_lable = itemView.findViewById(R.id.txt_lable);
            spinner_text.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.select_option));
            linearTime = itemView.findViewById(R.id.linearTime);
            tvTime = itemView.findViewById(R.id.tvTime);
            /**question type & for date time ***/
            linearDateTime = itemView.findViewById(R.id.linearDateTime);
            tvTimeDate = itemView.findViewById(R.id.tvTimeDate);
            timeImg = itemView.findViewById(R.id.timeImg);
            dateImg = itemView.findViewById(R.id.dateImg);
            view = itemView.findViewById(R.id.view);
            txt_upload = itemView.findViewById(R.id.txt_upload);
            txt_upload.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.expense_upload));
            fm_multiattachment = itemView.findViewById(R.id.fm_multiattachment);
            attachmentRecyclerView = itemView.findViewById(R.id.recyclerView);


            type_text.setOnTouchListener(this);
            type_text_area.setOnTouchListener(this);

        }
        // Implement as needed
        public void bindSingleQuestion(QuesRspncModel question, int position,SingleViewHolder holder) {
            holder.setIsRecyclable(false);

            /** Get Date from picker call back***/
            final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                 /*   myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);*/
                    /**type 5 for Time Type Question**/
                    if (view.getTag().equals("DateType5")) {

                        String month_String = String.valueOf((monthOfYear + 1));
                        String completeDate;
                        if (month_String.length() == 1) {
                            month_String = "0" + month_String;
                            completeDate = dayOfMonth + "-" + month_String + "-" + year;
                            // tvDate.setText(dayOfMonth + "-" + month_String + "-" + year + " ");
                        } else
                            completeDate = dayOfMonth + "-" + month_String + "-" + year;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        try {
                            Date parse = sdf.parse(completeDate.trim());
                            sDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(parse);
                            tvDate.setText(AppUtility.getDateByLang(sDate,false));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    /**type 7 for DateTime type Question***/
                    else if (view.getTag().equals("DateType7")) {
                        String monthString = String.valueOf((monthOfYear + 1));
                        if (monthString.length() == 1) {
                            monthString = "0" + monthString;
                        }
                        date = "";
                        date = (dayOfMonth + "-" + monthString + "-" + year + " ");
                        String newDateTime = date + " " + time;
                        SimpleDateFormat sdf = new SimpleDateFormat(
                                AppUtility.dateTimeByAmPmFormate("dd-MM-yyyy hh:mm a", "dd-MM-yyyy HH:mm"),Locale.ENGLISH);
                        try {
                            Date parseDate = sdf.parse(newDateTime);
                            sTimeDate = new SimpleDateFormat(AppUtility.dateTimeByAmPmFormate(
                                    "dd-MMM-yyyy hh:mm a",
                                    "dd-MMM-yyyy HH:mm"),Locale.ENGLISH).format(parseDate);
                            tvTimeDate.setText(AppUtility.getDateByLang(sTimeDate,true));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };


            type_Number.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (type_Number.getTag() != null) {
                        String pos = type_Number.getTag().toString();
                        int position = Integer.parseInt(pos);
                        List<AnswerModel> ans = question.getAns();
                        if (ans != null && ans.size() > 0) {
                            ans.set(0, new AnswerModel(ans.get(0).getKey(), s.toString()));
                            question.setAns(ans);
                        } else {
                            List<AnswerModel> answerModels = new ArrayList<>();
                            answerModels.add(new AnswerModel("0", s.toString()));
                            question.setAns(answerModels);
                        }
//                        addAns(question);

                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            buttonAttchment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag() != null) {
                        int position = Integer.parseInt(view.getTag().toString());
                        if (myAttachment != null)
                            myAttachment.selectFileWithoutAttchment(position, attchment_set, delete_attchment, buttonAttchment);
                        else if (context instanceof JobCompletionActivity) {
                            ((JobCompletionActivity) context).selectFile(position, attchment_set, delete_attchment, buttonAttchment,position);
                        }
                    }
                }
            });

            delete_attchment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag() != null) {
                        int pos = Integer.parseInt(view.getTag().toString());
                        attchment_set.setImageResource(android.R.color.transparent);
                        attchment_set.setVisibility(View.GONE);
                        buttonAttchment.setVisibility(View.VISIBLE);
                        delete_attchment.setVisibility(View.GONE);
                        buttonAttchment.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_attachment));
                        if (question.getAns() != null && question.getAns().size() > 0) {
                            question.getAns().get(0).setValue("");
                            question.getAns().get(0).setKey("");
                        }
                    }
                }
            });
            add_sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("Tag", view.getTag() + "");
                    getSignatureDialog(signature_set, view.getTag().toString(), add_sign, delete_sign);
                }
            });


            delete_sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (context instanceof FormQueAns_Activity) {
//                        ((FormQueAns_Activity) context).selectFile();
//                    }
                    int pos = Integer.parseInt(view.getTag().toString());
                    signature_set.setImageResource(android.R.color.transparent);
                    signature_set.setVisibility(View.GONE);
                    delete_sign.setVisibility(View.GONE);
                    add_sign.setVisibility(View.VISIBLE);
                    if (question.getAns() != null && question.getAns().size() > 0) {
                        question.getAns().get(0).setValue("");
                        question.getAns().get(0).setKey("");
                    }
                }
            });
            tvDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (tvDate.getTag() != null) {
                        String pos = tvDate.getTag().toString();
                        int position = Integer.parseInt(pos);
                        if (sDate != null && !sDate.isEmpty()) {
                            long startDate = 0;
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.US);
                                SimpleDateFormat tsdf = new SimpleDateFormat("hh:mm:ss", Locale.US);
                                String time = tsdf.format(Calendar.getInstance().getTime());
                                Date date = sdf.parse(sDate+" "+time);
                                startDate = date.getTime() / 1000;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            List<AnswerModel> answerModelList = new ArrayList<>();
                            if (question.getAns() != null && question.getAns().size() > 0)
                                answerModelList.add(new AnswerModel(question.getAns().get(0).getKey(),
                                        startDate + ""));
                            else
                                answerModelList.add(new AnswerModel("0", startDate + ""));
                            question.setAns(answerModelList);
                        }
//                        addAns(question);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            type_text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (type_text.getTag() != null) {
                        String pos = type_text.getTag().toString();
                        int position = Integer.parseInt(pos);
                        List<AnswerModel> ans = question.getAns();
                        if (ans != null && ans.size() > 0) {
                            ans.set(0, new AnswerModel(ans.get(0).getKey(), s.toString()));
                            question.setAns(ans);
                        } else {
                            List<AnswerModel> answerModels = new ArrayList<>();
                            answerModels.add(new AnswerModel("0", s.toString()));
                            question.setAns(answerModels);
                        }
//                        addAns(question);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            type_text_area.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (type_text_area.getTag() != null) {
                        String pos = type_text_area.getTag().toString();
                        int position = Integer.parseInt(pos);

                        List<AnswerModel> ans = question.getAns();
                        if (ans != null && ans.size() > 0) {
                            ans.set(0, new AnswerModel(ans.get(0).getKey(), s.toString()));
                            question.setAns(ans);
                        } else {
                            List<AnswerModel> answerModels = new ArrayList<>();
                            answerModels.add(new AnswerModel("0", s.toString()));
                            question.setAns(answerModels);
                        }
//                        addAns(question);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            linearSpinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinner.performClick();
                    Log.e("TAG", "THAG");
                }
            });
            linearDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( !tvDate.getText().toString().isEmpty()) {
                        myCalendar.clear();
                        String inputTime = sDate;
                        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                        try {
                            Date date = inputFormat.parse(inputTime);
                            String outputTime = outputFormat.format(date);
                            String[] ary_tv_time = outputTime.split("-");
                            myCalendar.set(Calendar.YEAR, Integer.parseInt(ary_tv_time[2].trim()));
                            myCalendar.set(Calendar.MONTH, Integer.parseInt(ary_tv_time[1].trim())-1);
                            myCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ary_tv_time[0].trim()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    /**initialize Date picker***/
                    final DatePickerDialog datePickerDialog = new DatePickerDialog(context, datePicker, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setTag("DateType5");
                    datePickerDialog.show();
                }
            });
            tvTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (tvTime.getTag() != null) {
                        String pos = tvTime.getTag().toString();
                        int position = Integer.parseInt(pos);
                        if (s != null && s.toString().length() > 0) {
                            long startDate = 10;
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat(
                                        AppUtility.dateTimeByAmPmFormate("hh:mm a", "HH:mm"), Locale.US);
                                Date date = sdf.parse(s.toString());
                                startDate = date.getTime() / 1000;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            List<AnswerModel> answerModelList = new ArrayList<>();
                            if (question.getAns() != null && question.getAns().size() > 0)
                                answerModelList.add(new AnswerModel(question.getAns().get(0).getKey(),
                                        startDate + ""));
                            else
                                answerModelList.add(new AnswerModel("0", startDate + ""));
                            question.setAns(answerModelList);
                        }
//                        addAns(question);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            linearTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputTime = tvTime.getText().toString();
                    if(!tvTime.getText().toString().isEmpty()) {
                        if (App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable() != null
                                && App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("0")) {
                            SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

                            try {
                                Date date = inputFormat.parse(inputTime);
                                String outputTime = outputFormat.format(date);
                                String[] ary_tv_time = outputTime.split(":");
                                myCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ary_tv_time[0].trim()));
                                myCalendar.set(Calendar.MINUTE, Integer.parseInt(ary_tv_time[1].trim()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String[] ary_tv_time = inputTime.split(":");
                            myCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ary_tv_time[0].trim()));
                            myCalendar.set(Calendar.MINUTE, Integer.parseInt(ary_tv_time[1].trim()));
                        }
                    }
                    getTimeFromPicker(myCalendar, "TimeType6", tvTime);
                }
            });
            tvTimeDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (tvTimeDate.getTag() != null) {
                        String pos = tvTimeDate.getTag().toString();
                        int position = Integer.parseInt(pos);
                        if (sTimeDate != null && !sTimeDate.isEmpty()) {
                            long startDate = 0;
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat(
                                        AppUtility.dateTimeByAmPmFormate("dd-MMM-yyyy hh:mm a",
                                                "dd-MMM-yyyy HH:mm"), Locale.US);
                                Date date = sdf.parse(sTimeDate);
                                startDate = date.getTime() / 1000;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            List<AnswerModel> answerModelList = new ArrayList<>();
                            if (question.getAns() != null && question.getAns().size() > 0)
                                answerModelList.add(new AnswerModel(question.getAns().get(0).getKey(),
                                        startDate + ""));
                            else
                                answerModelList.add(new AnswerModel("0", startDate + ""));
                            question.setAns(answerModelList);
                        }
//                        addAns(question);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            dateImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!tvTimeDate.getText().toString().isEmpty()) {
                        String inputTime = "";
                        if (App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable() != null
                                && App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("0")) {
                            String[] ary_inputTime = AppUtility.get24HoursTimeFormate(sTimeDate).split(" ");
                             inputTime = ary_inputTime[0];
                        }else{
                            String[] ary_inputTime = sTimeDate.split(" ");
                            inputTime = ary_inputTime[0];
                        }

                            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                            try {
                                Date date = inputFormat.parse(inputTime);
                                String outputTime = outputFormat.format(date);
                                String[] ary_tv_time = outputTime.split("-");
                                myCalendar.set(Calendar.YEAR, Integer.parseInt(ary_tv_time[2].trim()));
                                myCalendar.set(Calendar.MONTH, Integer.parseInt(ary_tv_time[1].trim())-1);
                                myCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ary_tv_time[0].trim()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                    }
                    /**initialize Date picker***/
                    final DatePickerDialog datePickerDialog = new DatePickerDialog(context, datePicker, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setTag("DateType7");
                    datePickerDialog.show();
                }
            });


            timeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!tvTimeDate.getText().toString().isEmpty()) {
                        String[] ary_inputTime =sTimeDate.split(" ");
                        if (App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable() != null
                                && App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("0")) {
                            String inputTime = ary_inputTime[1] + " " + ary_inputTime[2];
                            SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

                            try {
                                Date date = inputFormat.parse(inputTime);
                                String outputTime = outputFormat.format(date);
                                String[] ary_tv_time = outputTime.split(":");
                                myCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ary_tv_time[0].trim()));
                                myCalendar.set(Calendar.MINUTE, Integer.parseInt(ary_tv_time[1].trim()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else {
                            String[] ary_tv_time = ary_inputTime[1].split(":");
                            myCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(ary_tv_time[0].trim()));
                            myCalendar.set(Calendar.MINUTE, Integer.parseInt(ary_tv_time[1].trim()));
                        }
                    }
                    getTimeFromPicker(myCalendar, "TimeType7", tvTimeDate);
                }
            });
            // Implement logic to bind single question to UI
//            if ((question.getIndex() > 0)) {
//                //count++;
//               que_no.setVisibility(View.VISIBLE);
//                que_no.setText(question.getIndex() + ".");
//            } else {
//                que_no.setText("");
//                que_no.setVisibility(View.GONE);
//            }

            view.setVisibility(View.GONE);
            tvQuestion.setVisibility(View.VISIBLE);
            if (question.getMandatory().equals("1")) {
                tvQuestion.setText(question.getDes() + " *");
            } else {
                tvQuestion.setText(question.getDes());
            }
            try {
                tvQuestion.setTypeface(tvQuestion.getTypeface(), Typeface.NORMAL);
            } catch (Exception exception) {
                AppCenterLogs.addLogToAppCenterOnAPIFail("CustomForm","","onBindViewHolder "+exception.getMessage(),"QuestionListAdapter","");
                exception.printStackTrace();
            }

            switch (question.getType()) {
                /*****type 1 for text type quest*/
                case "1":
                    type_text.setVisibility(View.VISIBLE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.GONE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.GONE);
                    type_Number.setVisibility(View.GONE);
                    holder.que_no.setVisibility(View.VISIBLE);
                    holder.tvQuestion.setVisibility(View.VISIBLE);
                    fm_multiattachment.setVisibility(View.GONE);
                    txt_upload.setVisibility(View.GONE);


                    type_text.setTag(position);
//                    if (question.getAns().isEmpty())
//                        type_text.setText("");
//                    else
                    if (question.getAns().size() > 0)
                        type_text.setText(question.getAns().get(0).getValue());


                    break;
                /******TextArea***/
                case "2":
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.VISIBLE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.GONE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.GONE);
                    type_text_area.setTag(position);
                    type_Number.setVisibility(View.GONE);
                    holder.que_no.setVisibility(View.VISIBLE);
                    holder.tvQuestion.setVisibility(View.VISIBLE);
                    fm_multiattachment.setVisibility(View.GONE);
                    txt_upload.setVisibility(View.GONE);


                    if (question.getAns().isEmpty())
                        type_text_area.setText("");
                    else if (question.getAns().size() > 0)
                        type_text_area.setText(question.getAns().get(0).getValue());

                    break;
                /****checkbox type***/
                case "3":
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.VISIBLE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.GONE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.GONE);
                    type_Number.setVisibility(View.GONE);
                    holder.que_no.setVisibility(View.VISIBLE);
                    holder.tvQuestion.setVisibility(View.VISIBLE);
                    fm_multiattachment.setVisibility(View.GONE);
                    txt_upload.setVisibility(View.GONE);


                    setCheckBoxOption(holder, position);

                    break;
                /***dropdown type****/
                case "4":
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.VISIBLE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.GONE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.GONE);
                    type_Number.setVisibility(View.GONE);
                    holder.que_no.setVisibility(View.VISIBLE);
                    holder.tvQuestion.setVisibility(View.VISIBLE);
                    fm_multiattachment.setVisibility(View.GONE);
                    txt_upload.setVisibility(View.GONE);

                    setDropDownOptions(holder, position);
                    break;
                /***date type****/
                case "5":
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.VISIBLE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.GONE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.GONE);
                    type_Number.setVisibility(View.GONE);
                    holder.que_no.setVisibility(View.VISIBLE);
                    holder.tvQuestion.setVisibility(View.VISIBLE);
                    fm_multiattachment.setVisibility(View.GONE);
                    txt_upload.setVisibility(View.GONE);


                    tvDate.setTag(position);
                    if (question.getAns().isEmpty())
                        tvDate.setText("");
                    else if (question.getAns().size() > 0) {
                        try {
                            if (!(question.getAns().get(0).getValue()).equals("")) {
                                String[] dateConvert = AppUtility.getFormatedTime(question.
                                        getAns().get(0).getValue());
                                if (dateConvert!=null) {
                                    String s = dateConvert[0];
                                    String[] date = s.split(",");
                                    sDate = date[1].trim().replace(" ", "-");
                                    tvDate.setText(AppUtility.getDateByLang(sDate,false));
                                }else {
                                    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss",Locale.ENGLISH);
                                    Date date = dt.parse(question.getAns().get(0).getValue());
                                    SimpleDateFormat dt1 = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
                                    if (App_preference.getSharedprefInstance().getLoginRes().getIsAutoTimeZone().equals("1")) {
                                        dt1.setTimeZone(TimeZone.getTimeZone(App_preference.getSharedprefInstance().getLoginRes().getLoginUsrTz()));
                                    } else {
                                        dt1.setTimeZone(TimeZone.getDefault());
                                    }
                                    sDate = dt1.format(date);
                                    tvDate.setText(AppUtility.getDateByLang(sDate,false));
                                }

                            }
                        } catch (Exception ex) {
                            AppCenterLogs.addLogToAppCenterOnAPIFail("CustomForm","","onBindViewHolder(Q.5) "+ex.getMessage(),"QuestionListAdapter","");
                            ex.printStackTrace();
                        }
                    }
                    break;
                /*****Time type****/
                case "6":
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.VISIBLE);
                    linearDateTime.setVisibility(View.GONE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.GONE);
                    type_Number.setVisibility(View.GONE);
                    holder.que_no.setVisibility(View.VISIBLE);
                    holder.tvQuestion.setVisibility(View.VISIBLE);
                    fm_multiattachment.setVisibility(View.GONE);
                    txt_upload.setVisibility(View.GONE);

                    tvTime.setTag(position);
                    if (question.getAns().isEmpty())
                        tvTime.setText("");
                    else if (question.getAns().size() > 0) {
                        try {
                            if (!(question.getAns().get(0).getValue().equals(""))) {
                                String time = AppUtility.getDateWithFormate2((Long.parseLong(question.
                                                getAns().get(0).
                                                getValue()) * 1000),
                                        AppUtility.dateTimeByAmPmFormate(
                                                "hh:mm a", "HH:mm"));
                                tvTime.setText(time);

                            }
                        }catch (NumberFormatException nm){
                            AppCenterLogs.addLogToAppCenterOnAPIFail("CustomForm","","onBindViewHolder(Q.6) "+nm.getMessage(),"QuestionListAdapter","");
                            tvTime.setText(question.getAns().get(0).getValue());
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;
                /*****Date Time Type****/
                case "7":
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.VISIBLE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.GONE);
                    type_Number.setVisibility(View.GONE);
                    holder.que_no.setVisibility(View.VISIBLE);
                    holder.tvQuestion.setVisibility(View.VISIBLE);
                    fm_multiattachment.setVisibility(View.GONE);
                    txt_upload.setVisibility(View.GONE);

                    tvTimeDate.setTag(position);
                    if (question.getAns().isEmpty())
                        tvTimeDate.setText("");
                    else if (question.getAns().size() > 0) {
                        try {
                            if (!question.getAns().get(0).getValue().equals("")) {
                                Long dateLong = Long.parseLong(question.getAns().get(0).getValue());
                                String dateConvert = AppUtility.getDate(dateLong,
                                        AppUtility.dateTimeByAmPmFormate("dd-MMM-yyyy hh:mm a", "dd-MMM-yyyy HH:mm"));
                                sTimeDate = dateConvert;
                                tvTimeDate.setText(AppUtility.getDateByLang(sTimeDate,true));
                            }
                        } catch (NumberFormatException e) {
                            AppCenterLogs.addLogToAppCenterOnAPIFail("CustomForm","","onBindViewHolder(Q.7) "+e.getMessage(),"QuestionListAdapter","");
                            sTimeDate = question.getAns().get(0).getValue();
                            tvTimeDate.setText(question.getAns().get(0).getValue());
                        }catch (Exception e)
                        {
                            AppCenterLogs.addLogToAppCenterOnAPIFail("CustomForm","","onBindViewHolder(Q.7) "+e.getMessage(),"QuestionListAdapter","");
                            e.printStackTrace();
                        }
                    }
                    break;
                /***CheckBox for Only single option (True/false) ****/
                case "8":
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.GONE);
                    checkbox_single.setVisibility(View.VISIBLE);
                    signature_layout.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.GONE);
                    type_Number.setVisibility(View.GONE);
                    fm_multiattachment.setVisibility(View.GONE);
                    txt_upload.setVisibility(View.GONE);


                    tvQuestion.setVisibility(View.GONE);

                    if (question.getMandatory().equals("1")) {
                        checkbox_single.setText(question.getDes() + " *");
                    } else {
                        checkbox_single.setText(question.getDes());
                    }

                    if (question.getAns() != null && question.getAns().size() > 0) {
                        checkbox_single.setChecked(question.getAns().get(0).getValue().equals("1"));
                    }


                    checkbox_single.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (question.getAns() != null && question.getAns().size() > 0) {
                                if (question.getAns().get(0).getValue().equals("1"))
                                    question.getAns().get(0).setValue("0");
                                else question.getAns().get(0).setValue("1");
                            } else {
                                question.getAns().add(new AnswerModel("0", "1"));
                            }
                        }
                    });
                    break;
                /***Show Only Lable****/
                case "9":
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.GONE);
                    type_Number.setVisibility(View.GONE);
                    fm_multiattachment.setVisibility(View.GONE);
                    txt_upload.setVisibility(View.GONE);


                    try {
                        tvQuestion.setTypeface(tvQuestion.getTypeface(), Typeface.BOLD);
                    } catch (Exception exception) {
                        AppCenterLogs.addLogToAppCenterOnAPIFail("CustomForm","","onBindViewHolder(q.9) "+exception.getMessage(),"QuestionListAdapter","");
                        exception.printStackTrace();
                    }
                    que_no.setVisibility(View.VISIBLE);
                    tvQuestion.setVisibility(View.VISIBLE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.GONE);
                    break;
                /***For Signature***/
                case "10":
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.GONE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.VISIBLE);
                    Attchment_layout.setVisibility(View.GONE);
                    type_Number.setVisibility(View.GONE);
                    que_no.setVisibility(View.GONE);
                    tvQuestion.setVisibility(View.GONE);
                    fm_multiattachment.setVisibility(View.GONE);
                    txt_upload.setVisibility(View.GONE);

                    add_sign_lable.setText(que_no.getText().toString() + " " + tvQuestion.getText().toString());
                    add_sign.setTag(position);
                    delete_sign.setTag(position);


                    if (question.getAns().size() > 0 && !question.getAns().get(0).getValue().isEmpty()) {
                        signature_set.setVisibility(View.VISIBLE);
                        delete_sign.setVisibility(View.VISIBLE);
                        String path = question.getAns().get(0).getValue();
                        if (question.getAns().get(0).getValue().contains("/data/user/"))
                        {
                            File file=new File(path);
                            if (file.exists())
                            {
                                Glide.with(context).load(file)
                                        .thumbnail(Glide.with(context).load(R.raw.loader_eot))
                                        .placeholder(R.drawable.picture).into(signature_set);
                                signature_set.setScaleType(ImageView.ScaleType.FIT_XY);
                            }
                        }else {
                            Glide.with(context).load(App_preference.getSharedprefInstance().getBaseURL()
                                            +"uploads/comp"+App_preference.getSharedprefInstance().getLoginRes().getCompId()
                                            +"/customFormFiles/signature/thumbnail/"+question.getAns().get(0).getValue())
                                    .thumbnail(Glide.with(context).load(R.raw.loader_eot))
                                    .placeholder(R.drawable.picture).into(signature_set);
                            signature_set.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                    } else {
                        add_sign.setVisibility(View.VISIBLE);
                        delete_sign.setVisibility(View.GONE);
                        signature_set.setVisibility(View.GONE);
                    }
                    signature_set.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                //check the url has local selected attachment if yes than we are return not redirect and open it.
                                boolean exists = new File(question.getAns().get(0).getValue()).exists();
                                if (exists) return;
                            } catch (Exception e) {
                                AppCenterLogs.addLogToAppCenterOnAPIFail("CustomForm","","onBindViewHolder(q.10) "+e.getMessage(),"QuestionListAdapter","");
                                e.printStackTrace();
                            }

                            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(
                                            App_preference.getSharedprefInstance().getBaseURL() +
                                                    question.getAns().get(0).getValue()));
                            context.startActivity(browserIntent);

                        }
                    });


                    break;
                /***For Attachment***/
                case "11":
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.GONE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.GONE);
                    type_Number.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.VISIBLE);
                    Attchment_lable.setText(que_no.getText().toString() + " " + tvQuestion.getText().toString());
                    que_no.setVisibility(View.GONE);
                    tvQuestion.setVisibility(View.GONE);
                    fm_multiattachment.setVisibility(View.GONE);
                    txt_upload.setVisibility(View.GONE);
                    buttonAttchment.setTag(position);
                    delete_attchment.setTag(position);

                    if (question.getAns().size() > 0 && !question.getAns().get(0).getValue().isEmpty()) {
                        String fileurl= question.getAns().get(0).getValue();
                        if (fileurl.contains("/storage/emulated/")){
                            attchment_set.setVisibility(View.VISIBLE);
                            delete_attchment.setVisibility(View.VISIBLE);
                            buttonAttchment.setVisibility(View.GONE);
                            File imgFile = new  File(fileurl);
                            if(imgFile.exists()){
                                Glide.with(context).load(imgFile)
                                        .thumbnail(Glide.with(context).load(R.raw.loader_eot))
                                        .placeholder(R.drawable.picture).into(attchment_set);
                                attchment_set.setScaleType(ImageView.ScaleType.FIT_XY);
                            }
                        }else {
                            final String ext = question.getAns().get(0).getValue().
                                    substring((question.getAns().get(0).getValue().lastIndexOf(".")) + 1).toLowerCase();
                            attchment_set.setVisibility(View.VISIBLE);
                            delete_attchment.setVisibility(View.VISIBLE);
                            buttonAttchment.setVisibility(View.GONE);
                            //  buttonAttchment.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit_attchment));
                            if (!ext.isEmpty()) {
                                if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png")) {
                                    Glide.with(context).load(App_preference.getSharedprefInstance().getBaseURL()
                                                    +"uploads/comp"+App_preference.getSharedprefInstance().getLoginRes().getCompId()
                                                    +"/customFormFiles/thumbnail/"+question.getAns().get(0).getValue())
                                            .thumbnail(Glide.with(context).load(R.raw.loader_eot))
                                            .placeholder(R.drawable.picture).into(attchment_set);
                                    attchment_set.setScaleType(ImageView.ScaleType.FIT_XY);
                                } else if (ext.equals("doc") || ext.equals("docx")) {
                                    attchment_set.setImageResource(R.drawable.word);
                                    attchment_set.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                                } else if (ext.equals("pdf")) {
                                    attchment_set.setImageResource(R.drawable.pdf);
                                    attchment_set.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                                } else if (ext.equals("xlsx") || ext.equals("xls")) {
                                    attchment_set.setImageResource(R.drawable.excel);
                                    attchment_set.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                } else if (ext.equals("csv")) {
                                    attchment_set.setImageResource(R.drawable.csv);
                                    attchment_set.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                } else {
                                    attchment_set.setImageResource(R.drawable.doc);
                                    attchment_set.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                }
                            }
                        }
                    } else {
                        buttonAttchment.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_attachment));
                        delete_attchment.setVisibility(View.GONE);
                        attchment_set.setVisibility(View.GONE);
                    }

                    attchment_set.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //check the url has local selected attachment if yes than we are return not redirect and open it.
                            try {
                                boolean exists = new File(question.getAns().get(0).getValue()).exists();
                                if (exists) return;
                            } catch (Exception e) {
                                AppCenterLogs.addLogToAppCenterOnAPIFail("CustomForm","","onBindViewHolder(attechment_set) "+e.getMessage(),"QuestionListAdapter","");
                                e.printStackTrace();
                            }

                            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(App_preference.getSharedprefInstance().getBaseURL() +
                                            question.getAns().get(0).getValue()));
                            context.startActivity(browserIntent);
                        }
                    });


                    break;
                case "12":
                    type_Number.setVisibility(View.VISIBLE);
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.GONE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.GONE);
                    fm_multiattachment.setVisibility(View.GONE);
                    txt_upload.setVisibility(View.GONE);


                    type_Number.setTag(position);
                    if (question.getAns().isEmpty())
                        type_Number.setText("");
                    else if (question.getAns().size() > 0)
                        type_Number.setText(question.getAns().get(0).getValue());


                    break;
            case "13":
                    type_Number.setVisibility(View.GONE);
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.GONE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.GONE);
                    fm_multiattachment.setVisibility(View.VISIBLE);
                    txt_upload.setVisibility(View.VISIBLE);

                GridLayoutManager gridLayoutManager = new GridLayoutManager(context,3);
                attachmentRecyclerView.setLayoutManager(gridLayoutManager);
                jobCompletionAdpter = new CompletionAdpterJobDteails1((ArrayList<Attachments>) question.getAttachmentsList(), (JobCompletionActivity)context, (JobCompletionActivity)context,position, position);
//                jobCompletionAdpter = new CompletionAdpterJobDteails((ArrayList<Attachments>) new ArrayList<Attachments>(), (JobCompletionActivity)context, (JobCompletionActivity)context);
                attachmentRecyclerView.setAdapter(jobCompletionAdpter);
                txt_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((JobCompletionActivity) context).addAttachment(position,position,question.getQueId(), question.getJtId());
                    }
                });

                    break;

                default: {
                    type_text.setVisibility(View.GONE);
                    type_text_area.setVisibility(View.GONE);
                    linearCheck.setVisibility(View.GONE);
                    linearSpinner.setVisibility(View.GONE);
                    linearDate.setVisibility(View.GONE);
                    linearTime.setVisibility(View.GONE);
                    linearDateTime.setVisibility(View.GONE);
                    checkbox_single.setVisibility(View.GONE);
                    signature_layout.setVisibility(View.GONE);
                    Attchment_layout.setVisibility(View.GONE);
                }
            }
        }
        /**
         * Required for edittext scrolling
         */
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (view.getId() == R.id.type_text) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            } else if (view.getId() == R.id.type_text_area) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.signature_set) {
            }
        }

        /**
         * Take time from picker for Question Type 6 & 7
         ***/
        private void getTimeFromPicker(Calendar myCalendar, final String queType, final TextView textView) {
            if (App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable() != null
                    && App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("0")) {
                isTime24Format = false;
            }else{
                isTime24Format = true;
            }
            final String timeString;
            TimePickerDialog timePickerDialog = null;
            if (timePickerDialog == null) {
                timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String timeData = updateTime(hourOfDay, minute);
                                DecimalFormat formatter = new DecimalFormat("00");
                                String[] timeary = timeData.split(":");
                                timeData = ((formatter.format(Integer.parseInt(timeary[0]))) + ":" + timeary[1]);
                                if (queType.equals("TimeType6")) {
                                    textView.setText(timeData);
                                } else if (queType.equals("TimeType7")) {
                                    time = "";
                                    time = timeData;
                                    String newdateTime = date + " " + timeData;
                                    try {
                                        sTimeDate = new SimpleDateFormat(
                                                AppUtility.dateTimeByAmPmFormate("dd-MMM-yyyy hh:mm a",
                                                        "dd-MMM-yyyy HH:mm"),Locale.ENGLISH).format(
                                                new SimpleDateFormat(
                                                        AppUtility.dateTimeByAmPmFormate(
                                                                "dd-MM-yyyy hh:mm a", "dd-MM-yyyy HH:mm"),Locale.ENGLISH).parse(newdateTime));
                                        textView.setText(AppUtility.getDateByLang(sTimeDate,true));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), isTime24Format
                );
            }
            timePickerDialog.show();
        }

        private void getSignatureDialog(final ImageView signature_set, final String tag, final ImageView addSign, final ImageView deleteSign) {
            LinearLayout signt = null;
            File mypath;
            final SignatureView mSignature;
            final Button sbmt_btn, skip_btn;
            ImageView close_diaolg;
            final Dialog dialog = new Dialog(context);

            try {
                final String current;
                final String getPath;
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.customform_signature_layout);

                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                WindowManager.LayoutParams wlp = window.getAttributes();

                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                window.setAttributes(wlp);


                sbmt_btn = dialog.findViewById(R.id.sbmt_btn);
                skip_btn = dialog.findViewById(R.id.skip_btn);
                signt = dialog.findViewById(R.id.signt);
                close_diaolg = dialog.findViewById(R.id.close_diaolg);


                ContextWrapper cw = new ContextWrapper(context);
                File directory = cw.getDir(context.getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);
                current = "eot_" + AppUtility.getDateByMiliseconds() + ".png";
                mypath = new File(directory, current);
                getPath = mypath.getAbsolutePath();

                mSignature = new SignatureView(context);
                signt.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                skip_btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.clear));
                sbmt_btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok));

                sbmt_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        File mfile = mSignature.exportFile(getPath, current);
                        if (!mSignature.isSignatureEmpty()) {
                            setSignatureInView(mfile, tag, signature_set, addSign, deleteSign);//signature_set
                        } else {
                            signature_set.setImageResource(android.R.color.transparent);
                            signature_set.setVisibility(View.GONE);
                        }
                        dialog.dismiss();
                    }
                });

                skip_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSignature.clear();
                    }
                });

                close_diaolg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

            dialog.show();
        }


        /*****Show Attachment Visibility****/
        public void showAttchmentView(int pos, String path, final ImageView attachmentView, ImageView deleteAttchment, Button addAttachment) {//, ImageView attachmentView, ImageView deleteAttchment
            try {
                QuesRspncModel quesRspncModel = null;
               if( item.get(pos) instanceof QuesRspncModel){
                   quesRspncModel = (QuesRspncModel) item.get(pos);
                }
                File mfile = new File(path);
                if (mfile.exists()) {
                    List<AnswerModel> ans = quesRspncModel.getAns();
                    if (ans != null && ans.size() > 0) {
                        ans.set(0, new AnswerModel(ans.get(0).getKey(),
                                mfile + ""));
                        quesRspncModel.setAns(ans);
                    } else {
                        List<AnswerModel> answerModels = new ArrayList<>();
                        answerModels.add(new AnswerModel("0",
                                mfile + ""));
                        quesRspncModel.setAns(answerModels);
                    }


                    final String ext = quesRspncModel.getAns().get(0).getValue().
                            substring((quesRspncModel.getAns().get(0).getValue().lastIndexOf(".")) + 1).toLowerCase();
                    if (!ext.isEmpty()) {

                        addAttachment.setVisibility(View.GONE);
                        deleteAttchment.setVisibility(View.VISIBLE);
                        attachmentView.setVisibility(View.VISIBLE);
                        deleteAttchment.setTag(pos);

                        if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png")) {
                      /*  Glide.with(context).load(App_preference.getSharedprefInstance().getBaseURL()
                                + typeList.get(pos).getAns().get(0).getValue())
                                .addListener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        attachmentView.setClickable(true);
                                        return false;
                                    }
                                })
                                .placeholder(R.drawable.picture).into(attachmentView);*/
                            Bitmap myBitmap = BitmapFactory.decodeFile(mfile.getAbsolutePath());
                            attachmentView.setImageBitmap(myBitmap);
                            attachmentView.setScaleType(ImageView.ScaleType.FIT_XY);

                        } else if (ext.equals("doc") || ext.equals("docx")) {
                            attachmentView.setImageResource(R.drawable.word);
                            attachmentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        } else if (ext.equals("pdf")) {
                            attachmentView.setImageResource(R.drawable.pdf);
                            attachmentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                        } else if (ext.equals("xlsx") || ext.equals("xls")) {
                            attachmentView.setImageResource(R.drawable.excel);
                            attachmentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        } else if (ext.equals("csv")) {
                            attachmentView.setImageResource(R.drawable.csv);
                            attachmentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        } else {
                            attachmentView.setImageResource(R.drawable.doc);
                            attachmentView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        }
                    }
//                    addAns(quesRspncModel);
                } else {
                    addAttachment.setVisibility(View.VISIBLE);
                    deleteAttchment.setVisibility(View.GONE);
                    attachmentView.setVisibility(View.GONE);
//                    typeList1.remove(quesRspncModel);
                }
            } catch (Exception e) {
                addAttachment.setVisibility(View.VISIBLE);
                deleteAttchment.setVisibility(View.GONE);
                attachmentView.setVisibility(View.GONE);
                e.printStackTrace();
            }

        }

        /***set Signature in imageView****/
        private void setSignatureInView(final File mfile, final String tag, ImageView signature_set, ImageView addSign, ImageView deleteSign) {//, ImageView signature_set
            try {

                QuesRspncModel quesRspncModel = null;
                int pos = Integer.parseInt(tag);
                if( item.get(pos) instanceof QuesRspncModel){
                    quesRspncModel = (QuesRspncModel) item.get(pos);
                }
                if (mfile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(mfile.getAbsolutePath());
                    /********/
                    signature_set.setVisibility(View.VISIBLE);
                    signature_set.setImageBitmap(myBitmap);
                    signature_set.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    addSign.setVisibility(View.GONE);
                    deleteSign.setVisibility(View.VISIBLE);



                    List<AnswerModel> ans = quesRspncModel.getAns();

                    if (ans != null && ans.size() > 0) {
                        ans.set(0, new AnswerModel(ans.get(0).getKey(), mfile + ""));
                        quesRspncModel.setAns(ans);

                    } else {
                        List<AnswerModel> answerModels = new ArrayList<>();
                        answerModels.add(new AnswerModel("0",
                                mfile + ""));
                        quesRspncModel.setAns(answerModels);
                    }

//                    addAns(quesRspncModel);
                } else {
                    signature_set.setVisibility(View.GONE);
                    addSign.setVisibility(View.VISIBLE);
                    deleteSign.setVisibility(View.GONE);
//                    typeList1.remove(quesRspncModel);
                }
            } catch (Exception e) {
                signature_set.setVisibility(View.GONE);
                addSign.setVisibility(View.VISIBLE);
                deleteSign.setVisibility(View.GONE);
                e.printStackTrace();
            }

//        signature_set.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse(mfile + ""));
//                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//             context.startActivity(browserIntent);
//            }
//        });

        }

        private void setCheckBoxOption(SingleViewHolder holder, final int position) {
            if (holder.linearCheck.getChildCount() > 0)
                holder.linearCheck.removeAllViews();

            QuesRspncModel quesRspncModel = null;
            if( item.get(position) instanceof QuesRspncModel){
                quesRspncModel = (QuesRspncModel) item.get(position);
            }
            final QuesRspncModel finalquesRspncModel = quesRspncModel;
            for (final OptionModel optionModel : quesRspncModel.getOpt()) {
                final CheckBox checkBox = new CheckBox(context);
                checkBox.setText("");
                checkBox.setText(optionModel.getValue());
                checkBox.setTag(optionModel);


                checkBox.setTextAppearance(context, R.style.header_text_style2);
//                checkBox.setTypeface(ResourcesCompat.getFont(context, R.font.arimo_regular));
                checkBox.setButtonTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.txt_sub_color)));

                checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                holder.linearCheck.addView(checkBox);

                if (!finalquesRspncModel.getAns().isEmpty()) {
                    for (final AnswerModel answerModel : finalquesRspncModel.getAns()) {
                        if (optionModel.getKey().equals(answerModel.getKey())) {
                            checkBox.setChecked(true);
                            break;
                        }
                    }
                } else checkBox.setChecked(false);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        OptionModel selectedOptionModel = ((OptionModel) compoundButton.getTag());

                        if (compoundButton.isChecked()) {
                            if (((OptionModel) compoundButton.getTag()).getOptHaveChild().equals("1")) {
                                myFormInterFace.getAnsId(optionModel.getKey()); // its pending work
                            }
                            if (finalquesRspncModel.getAns() != null && finalquesRspncModel.getAns().size() > 0) {
                                List<AnswerModel> ans =finalquesRspncModel.getAns();
                                for (AnswerModel ansmodel : ans)
                                    if (ansmodel.getKey().equals(selectedOptionModel.getKey()))
                                        return;
                                ans.add(new AnswerModel(selectedOptionModel.getKey(), selectedOptionModel.getValue()));
                                finalquesRspncModel.setAns(ans);
                            } else {
                                List<AnswerModel> answerModelList = new ArrayList<>();
                                answerModelList.add(new AnswerModel(selectedOptionModel.getKey(), selectedOptionModel.getValue()));
                                finalquesRspncModel.setAns(answerModelList);
                            }
                        } else {
                            if (finalquesRspncModel.getAns() != null && finalquesRspncModel.getAns().size() > 0) {
                                List<AnswerModel> ans = finalquesRspncModel.getAns();
                                for (AnswerModel ansmodel : ans)
                                    if (ansmodel.getKey().equals(selectedOptionModel.getKey())) {
                                        ans.remove(ansmodel);
                                        finalquesRspncModel.setAns(ans);
                                        return;
                                    }
                            }
                        }
//                        addAns(finalquesRspncModel);
                    }
                });
            }

        }
        public void setDropDownOptions(SingleViewHolder holder, final int position) {
            QuesRspncModel quesRspncModel = null;
            if( item.get(position) instanceof QuesRspncModel){
                quesRspncModel = (QuesRspncModel) item.get(position);
            }
            final QuesRspncModel finalquesRspncModel = quesRspncModel;
            if (finalquesRspncModel.getAns().isEmpty())
                holder.spinner_text.setText("");
            else if (finalquesRspncModel.getAns().size() > 0)
                holder.spinner_text.setText(finalquesRspncModel.getAns().get(0).getValue());


            final MyAdapter<OptionModel> spinnerAdapter = new MyAdapter<>(context, R.layout.custom_adapter_item_layout, finalquesRspncModel.getOpt());
            holder.spinner.setAdapter(spinnerAdapter);


            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                    List<AnswerModel> ans = new ArrayList<>();

                    ans.add(new AnswerModel(((DropdownListBean) parent.getItemAtPosition(pos)).getKey(),
                            ((DropdownListBean) parent.getItemAtPosition(pos)).getName()));

                    finalquesRspncModel.setAns(ans);

                    String text = ((DropdownListBean) parent.getItemAtPosition(pos)).getName();
                    holder.spinner_text.setText(text);

                    if (((OptionModel) parent.getItemAtPosition(pos)).getOptHaveChild().equals("1")) {
                        myFormInterFace.getAnsId(((DropdownListBean) parent.getItemAtPosition(pos)).getKey());
                    }
//                    addAns(finalquesRspncModel);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }

    }

    private boolean isInList(QuesRspncModel question) {
        boolean isIt = false;

        for (QuesRspncModel item : typeList1
        ) {
            if(item.getQueId().equals(question.getQueId())){
                isIt = true;
                break;
            }
        }
        return isIt;
    }

//    private void addAns(QuesRspncModel question){
//        if(typeList1.size()==0){
//            typeList1.add(question);
//        } else if (isInList(question)) {
//
//            for(QuesRspncModel item : typeList1){
//                if(item.getQueId().equals(question.getQueId())){
//                    if(item.getAns().get(0).getValue().isEmpty()){
//                        typeList1.remove(item);
//                        break;
//                    }else {
//                        if(question.getType().equals("3")||question.getType().equals("4")) {
//                            item.setAns(question.getAns());
//                        }
//                        else {
//                            item.getAns().get(0).setKey(question.getAns().get(0).getKey());
//                            item.getAns().get(0).setValue(question.getAns().get(0).getValue());
//                        }
//                        typeList1.add(item);
//                    }
//                }
//            }
//        }else {
//            typeList1.add(question);
//        }
//    }

    public class   GroupedViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, View.OnClickListener{

        TextView tv_groupedHeader, tv_completeBtn, tv_markBtn;
        ConstraintLayout cl_btnMark, cl_btnCompelete;
        public RecyclerView rv_groupItem;
        public QuestionListAdapter questionListAdapter;
        public ImageView img_markBtn, img_crossBtn;
        boolean isDone = false;

        public GroupedViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_groupedHeader = itemView.findViewById(R.id.tv_groupedHeader);
            tv_completeBtn = itemView.findViewById(R.id.tv_completeBtn);
            cl_btnMark = itemView.findViewById(R.id.cl_btnMark);
            cl_btnCompelete = itemView.findViewById(R.id.cl_btnCompelete);
            rv_groupItem = itemView.findViewById(R.id.rv_groupItem);
            img_crossBtn = itemView.findViewById(R.id.img_crossBtn);
            img_markBtn = itemView.findViewById(R.id.img_markBtn);
            tv_markBtn = itemView.findViewById(R.id.tv_markBtn);



        }
        // Implement as needed
        public void bindGroupedQuestions(List<QuesRspncModel> questionList, int postion,GroupedViewHolder holder) {
            holder.setIsRecyclable(false);
            // Implement logic to bind grouped questions to UI
            if(App_preference.getSharedprefInstance().getLoginRes().getIsCompleShowMarkDone().equals("0")){
                cl_btnMark.setVisibility(View.GONE);
            }else {
                if(questionList.get(0).getIsMarkAsDone().equals("0")){
                    cl_btnMark.setBackground(context.getResources().getDrawable(R.drawable.bg_mark_as_completion_cancel));
                    img_markBtn.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.green1)));
                    img_crossBtn.setVisibility(View.GONE);
                    tv_markBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.mark_as_done));
                    tv_markBtn.setTextColor(context.getResources().getColor(R.color.green1));
                    isDone = false;
                }else {
                    cl_btnMark.setBackground(context.getResources().getDrawable(R.drawable.bg_mark_as_completion_done));
                    img_markBtn.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                    img_crossBtn.setVisibility(View.VISIBLE);
                    tv_markBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.done));
                    tv_markBtn.setTextColor(context.getResources().getColor(R.color.white));
                    isDone =true;
                }
            }

            JobTitle jobTitle = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobTitleModel().getJobTitleByid(questionList.get(0).getJtId());
            holder.tv_groupedHeader.setText(jobTitle.getTitle());

//                    holder.questionListAdapter = new QuestionListAdapter((ArrayList<QuesRspncModel>) quesRspncModelList, (JobCompletionActivity) context,
//                            new MyFormInterFace() {
//                                @Override
//                                public void getAnsId(String ansId) {
//
//                                }
//                            }, true, postion);
//
//                    ((JobCompletionActivity) context).runOnUiThread(()->{
//                        rv_groupItem.setAdapter(holder.questionListAdapter);
//                    });
                if(!questionList.get(0).getQueId().equals("temp")) {
                    backgroundTaskExecutor.excuteTask(() -> {
                        List<QuesRspncModel> quesRspncModelList = new ArrayList<>();
                        quesRspncModelList.addAll(questionList);


                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                rv_groupItem.setAdapter(adapterPosition.get(postion));
                                adapterPosition.get(postion).UpdateList((ArrayList<QuesRspncModel>) quesRspncModelList);
                            }
                        });


                    });
                }


            holder.cl_btnMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isDone) {
                        cl_btnMark.setBackground(context.getResources().getDrawable(R.drawable.bg_mark_as_completion_done));
                        img_markBtn.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                        img_crossBtn.setVisibility(View.VISIBLE);
                        tv_markBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.done));
                        tv_markBtn.setTextColor(context.getResources().getColor(R.color.white));
                        questionList.get(0).setIsMarkAsDone("1");
                        clickListener.setMarkAsDoneService(questionList.get(0).getJtId(),"1");
                        isDone =true;}
                    else {
                        cl_btnMark.setBackground(context.getResources().getDrawable(R.drawable.bg_mark_as_completion_cancel));
                        img_markBtn.setImageTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.green1)));
                        img_crossBtn.setVisibility(View.GONE);
                        tv_markBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.mark_as_done));
                        tv_markBtn.setTextColor(context.getResources().getColor(R.color.green1));
                        questionList.get(0).setIsMarkAsDone("0");
                        clickListener.setMarkAsDoneService(questionList.get(0).getJtId(),"0");
                        isDone = false;
                    }
                }
            });

        }

        /**
         * Required for edittext scrolling
         */
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (view.getId() == R.id.type_text) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            } else if (view.getId() == R.id.type_text_area) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.signature_set) {
            }
        }

    }
    public class   DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, View.OnClickListener{
        ImageView suggestion_img;
        TextView tvCancel, tvDone, tvFetchDes,tvtimestemp, tv_label_des, txt_upload ;
        public EditText compedt;
        private RecyclerView recyclerView;
        private Spinner job_suggestion_spinner;
        List<QuesRspncModel> questionList;
        LinearLayout ll_completion, ll_attachment;
        QuesRspncModel question;
        JoBServSuggAdpter suggestionAdapter;
        private  GridLayoutManager layoutManager;
        public  CompletionAdpterJobDteails jobCompletionAdpter;
        List<Attachments> getFileList_res = new ArrayList<>();


        public DefaultViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_label_des = itemView.findViewById(R.id.tv_label_des);
            tv_label_des.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.completion_note));

            recyclerView = itemView.findViewById(R.id.recyclerView);

            compedt = itemView.findViewById(R.id.compedt);
            compedt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.completion_note));

            tvCancel = itemView.findViewById(R.id.tvCancel);
            tvCancel.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel));

            tvDone = itemView.findViewById(R.id.tvDone);
            tvDone.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.done));

            tvFetchDes = itemView.findViewById(R.id.tvFetchDes);
            tvFetchDes.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.fetch_des));

            tvtimestemp=itemView.findViewById(R.id.tvtimestemp);
            tvtimestemp.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.timestemp));
            suggestion_img = itemView.findViewById(R.id.suggestion_img);
            job_suggestion_spinner = itemView.findViewById(R.id.job_suggestion_spinner);

            ll_completion = itemView.findViewById(R.id.ll_compNotes);
            ll_attachment = itemView.findViewById(R.id.ll_attachment);
            txt_upload = itemView.findViewById(R.id.txt_upload);
            txt_upload.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.expense_upload));
            recyclerView = itemView.findViewById(R.id.recyclerView);

            compedt.setOnTouchListener(this);

        }
        public void bindDefaultQuestion(QuesRspncModel question, int position, DefaultViewHolder holder) {
            holder.setIsRecyclable(false);
            QuesRspncModel finalQuestion = question;
            if(finalQuestion.getMandatory().equals("1")){
                tv_label_des.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.completion_note)+"*");
            }else {
                tv_label_des.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.completion_note));
            }
            JobCompletionActivity jobCompletionActivity = (JobCompletionActivity) context;
            if (question.getType().equals("") && question.getType().isEmpty()) {

                ll_completion.setVisibility(View.VISIBLE);
                ll_attachment.setVisibility(View.GONE);
                if(App_preference.getSharedprefInstance().getLoginRes().getCompleMoFldVisiblty().getFetchdesc().equals("0")){
                    tvFetchDes.setVisibility(View.GONE);
                }else {
                    tvFetchDes.setVisibility(View.VISIBLE);
                }
                if(App_preference.getSharedprefInstance().getLoginRes().getCompleMoFldVisiblty().getDone().equals("0")){
                    tvDone.setVisibility(View.GONE);
                }else {
                    tvDone.setVisibility(View.VISIBLE);
                }
                if(App_preference.getSharedprefInstance().getLoginRes().getCompleMoFldVisiblty().getCancel().equals("0")){
                    tvCancel.setVisibility(View.GONE);
                }else {
                    tvCancel.setVisibility(View.VISIBLE);
                }
                if(App_preference.getSharedprefInstance().getLoginRes().getCompleMoFldVisiblty().getTimestamp().equals("0")){
                    tvtimestemp.setVisibility(View.GONE);
                }else {
                    tvtimestemp.setVisibility(View.VISIBLE);
                }
                if(App_preference.getSharedprefInstance().getLoginRes().getCompleMoFldVisiblty().getSuggesbtn().equals("0")){
                    suggestion_img.setVisibility(View.GONE);
                }else {
                    suggestion_img.setVisibility(View.VISIBLE);
                    try {
                        if (suggestionAdapter == null) {
                            AppUtility.spinnerPopUpWindow(job_suggestion_spinner);
                            suggestionAdapter = new JoBServSuggAdpter(jobCompletionActivity, jobCompletionActivity.getSuggestionsArray()
                                    , nm -> setSelectedSuggeston(nm));
                            job_suggestion_spinner.setAdapter(suggestionAdapter);
                        }
                    } catch (Exception e) {
                        AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","filterJobServices()"+e.getMessage(),"JobCompletionActivity","");
                        e.printStackTrace();
                    }
                }

//                int i = 0;
//                for (QuesRspncModel item : this.questionList
//                ) {
//                    if (item.getSysFieldType().equals("1")) {
//                        question = item;
                compedt.setTag(position);
                if(finalQuestion.getAns() != null && finalQuestion.getAns().size() > 0) {
                    compedt.setText(finalQuestion.getAns().get(0).getValue());
                }
//                    }
//                    i++;
//                }

                layoutManager = new GridLayoutManager(context, 2);
                recyclerView.setLayoutManager(layoutManager);

                tvCancel.setOnClickListener(this);
                tvDone.setOnClickListener(this);
                tvFetchDes.setOnClickListener(this);
                tvtimestemp.setOnClickListener(this);
                //    compleLayout.getEditText().addTextChangedListener(this);



                compedt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (compedt.getTag() != null) {
                            String pos = compedt.getTag().toString();
                            int position = Integer.parseInt(pos);
                            List<AnswerModel> ans = finalQuestion.getAns();
                            if (ans != null && ans.size() > 0) {
                                ans.set(0, new AnswerModel(ans.get(0).getKey(), s.toString()));
                                finalQuestion.setAns(ans);
                            } else {
                                List<AnswerModel> answerModels = new ArrayList<>();
                                answerModels.add(new AnswerModel("0", s.toString()));
                                finalQuestion.setAns(answerModels);
                            }
//                            addAns(finalQuestion);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                suggestion_img.setOnClickListener(v -> {
                    if (jobCompletionActivity.getSuggestionsArray() != null && jobCompletionActivity.getSuggestionsArray().length > 0)
                        job_suggestion_spinner.performClick();
                    else {
                        AppUtility.alertDialog(jobCompletionActivity,
                                "", LanguageController.getInstance()
                                        .getMobileMsgByKey(AppConstant.no_suggesstion)
                                , LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok),"", () -> null);
                    }

                });
//            viewClickListner();
//
//            setComplationView();
//
//            complPi = new Compl_PC(this);
//
//            filterJobServices();

            }
            else {
                setPostionDefaultViewHolder(position);
                ll_completion.setVisibility(View.GONE);
                ll_attachment.setVisibility(View.VISIBLE);
//                GridLayoutManager gridLayoutManager = new GridLayoutManager(context,3);
//                recyclerView.setLayoutManager(gridLayoutManager);
//                jobCompletionAdpter = new CompletionAdpterJobDteails((ArrayList<Attachments>) DetailFragment.getInstance().getCompAttachmentList(), (JobCompletionActivity)context, (JobCompletionActivity)context);
////                jobCompletionAdpter = new CompletionAdpterJobDteails((ArrayList<Attachments>) new ArrayList<Attachments>(), (JobCompletionActivity)context, (JobCompletionActivity)context);
//                recyclerView.setAdapter(jobCompletionAdpter);
//                txt_upload.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        ((JobCompletionActivity) context).openAttachmentDialog();
//                    }
//                });

            }
        }
        /**
         * Required for edittext scrolling
         */

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (view.getId() == R.id.type_text) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
            } else if (view.getId() == R.id.type_text_area) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
            }
//            else if(view.getId() == R.id.compedt){
//                view.getParent().requestDisallowInterceptTouchEvent(true);
//            }
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            Map<String, String> value;
            switch (v.getId()) {
                case R.id.tvDone:
                    value = new JobCompletionActivity().setUpdatedDesc(compedt,1);
                    compedt.setText(value.get("Data"));
                    compedt.setSelection(Integer.parseInt(value.get("pos")));
                    break;
                case R.id.tvCancel:
//                    setUpdatedDesc(2);
                    value = new JobCompletionActivity().setUpdatedDesc(compedt,2);
                    compedt.setText(value.get("Data"));
                    compedt.setSelection(Integer.parseInt(value.get("pos")));
                    break;
                case R.id.tvFetchDes:
                    JobCompletionActivity context1 = (JobCompletionActivity) context;
                    value = context1.setUpdatedDesc(compedt,3);
                    compedt.setText(value.get("Data"));
                    compedt.setSelection(Integer.parseInt(value.get("pos")));
                    break;
                case R.id.tvtimestemp:
                    value = new JobCompletionActivity().setUpdatedDesc(compedt,4);
                    compedt.setText(value.get("Data"));
                    compedt.setSelection(Integer.parseInt(value.get("pos")));
                    break;
            }
        }

        private void setSelectedSuggeston(String nm) {
            try {
                String str = "";
                int cursorpostion=compedt.getText().toString().length();
                if (compedt.getText().toString().trim().length() > 0) {
                    str = compedt.getText().toString() + "\n" + nm;
                } else {
                    str = nm;
                }
                compedt.setText(str);
                str.replace("<br>","");
                str.replace("null","");
                compedt.setText(str);
                compedt.setSelection(cursorpostion+nm.length()+1);
            } catch (Exception e) {
                AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","setSelectedSuggeston()"+e.getMessage(),"JobCompletionActivity","");
                e.printStackTrace();
            }
        }

    }

    private void setIndex() {

//        for (Object item1 : item
//        ) {
//            typeList.add ( (QuesRspncModel) item1);
//        }
//        if (typeList != null && typeList.size() > 0) {
//            int i = 0;
//            for (QuesRspncModel custOmFormQuestionsRes : typeList) {
//                if (!custOmFormQuestionsRes.getType().equals("9")) {
//                    i++;
//                    custOmFormQuestionsRes.setIndex(i);
//                } else custOmFormQuestionsRes.setIndex(0);
//            }
//        }
    }
    public void setPostionDefaultViewHolder(int postionDefaultViewHolder){
        this.postionDefaultViewHolder = postionDefaultViewHolder;
    }
    public int getPostionDefaultViewHolder(){
        return postionDefaultViewHolder;
    }

    public interface ClickListener {
        public void setMarkAsDoneService(String jtid, String status);
    }
}