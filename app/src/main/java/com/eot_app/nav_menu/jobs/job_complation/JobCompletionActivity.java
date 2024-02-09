package com.eot_app.nav_menu.jobs.job_complation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_complation.compla_model.NotifyForcompletion;
import com.eot_app.nav_menu.jobs.job_complation.complat_mvp.Compl_PC;
import com.eot_app.nav_menu.jobs.job_complation.complat_mvp.Compl_PI;
import com.eot_app.nav_menu.jobs.job_complation.complat_mvp.Compla_View;
import com.eot_app.nav_menu.jobs.job_complation.complation_form.BackgroundTaskExecutor;
import com.eot_app.nav_menu.jobs.job_complation.complation_form.adapter.CompletionFormAdapter;
import com.eot_app.nav_menu.jobs.job_db.CompliAnsArray;
import com.eot_app.nav_menu.jobs.job_db.IsMarkDoneWithJtid;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_db.JtId;
import com.eot_app.nav_menu.jobs.job_db.OfflieCompleQueAns;
import com.eot_app.nav_menu.jobs.job_db.OfflieCompleQueAns_Dao;
import com.eot_app.nav_menu.jobs.job_detail.detail.CompletionAdpterJobDteails1;
import com.eot_app.nav_menu.jobs.job_detail.documents.ActivityDocumentSaveUpload;
import com.eot_app.nav_menu.jobs.job_detail.documents.EditImageDialog;
import com.eot_app.nav_menu.jobs.job_detail.documents.PathUtils;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.NotifyForMultiDocAdd;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_Pc;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_Pi;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_View;
import com.eot_app.nav_menu.jobs.job_detail.documents.work_manager.UploadMultiImgWorker;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.MyFormInterFace;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.QuestionListAdapter;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.ans_model.Ans_Req;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.ans_model.Answer;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.form_ques_mvp.Que_View;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.form_ques_mvp.Que_pi;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.AllTypeFieldClone;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.AnswerModel;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.OptionModel;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.QuesGetModel;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.QuesRspncModel;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppCenterLogs;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.db.OfflineDataController;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.settings.setting_db.JobTitle;
import com.eot_app.utility.settings.setting_db.Suggestion;
import com.eot_app.utility.util_interfaces.Callback_AlertDialog;
import com.eot_app.utility.util_interfaces.JoBServSuggAdpter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hypertrack.hyperlog.HyperLog;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class JobCompletionActivity extends AppCompatActivity implements View.OnClickListener,Que_View
        , TextWatcher, Compla_View, Doc_Attch_View, JobCompletionAdpter.FileDesc_Item_Selected, NotifyForMultiDocAdd
        , CompletionAdpterJobDteails1.CallBAckFormAttchemntToCompl, CompletionFormAdapter.ClickListener, NotifyForcompletion {
    private final static int CAPTURE_IMAGE_GALLARY = 222;
    private static final int DOUCMENT_UPLOAD_CODE = 156;
    private static final int PHOTO_EDIT_CODE = 147;
    final String JOBCOMPLATIONTYPE = "";
    private final int CAMERA_CODE = 100;
    private final int ATTACHFILE_CODE = 101;
    private final StringBuffer desc = new StringBuffer();
    private final List<Suggestion> suggestionList = new ArrayList<>();
    Job jobData;
    JoBServSuggAdpter suggestionAdapter;
    String captureImagePath;
    boolean isFileImage = false;
    RecyclerView.LayoutManager layoutManager;
    Doc_Attch_Pi doc_attch_pi;
    String[] suggestionsArray = new String[suggestionList.size()];
    TextView cancel_txt, complHeader, save_txt, tv_label_des, txt_notSyncLabel;
    ImageView suggestion_img;
    TextView tvCancel, tvDone, tvFetchDes,tvtimestemp;
//    private EditText compedt;
    private Compl_PI complPi;
    private RecyclerView recyclerView, completionRecyclerView;
    private JobCompletionAdpter jobCompletionAdpter;
    private String jobId;
    private EditImageDialog currentDialog = null;
    private ArrayList<Attachments> fileList_res = new ArrayList<>();
    private ProgressBar progressBar;
    private Spinner job_suggestion_spinner;
    private WorkManager mWorkManager;
    private CompletionFormAdapter completionFormAdapter;
    private QuesGetModel quesGetModel;
    private Que_pi queAns_pi;
    private List<QuesRspncModel> quesRspncModelList = new ArrayList<>();
    private ImageView attchmentView, deleteAttchment;
    private Button addAttchment;
    int position = -1;
    int parentPositon = -1;
    String queId, jtId;
    Map<String, List<QuesRspncModel>> groupedQuestions = new HashMap<>();
    List<QuesRspncModel> singleQuestions = new ArrayList<>();
    List<QuesRspncModel> systemFieldQuestions = new ArrayList<>();
    List<QuesRspncModel> allFieldQuestions = new ArrayList<>();
    List<QuesRspncModel> filterList ;
    List<QuesRspncModel> removeItemPositon = new ArrayList<>();
    List<QuesRspncModel> questions = new ArrayList<>();
    List<QuesRspncModel> sortQuestinsList;
    ArrayList<Object> allItem = new ArrayList<>();
    private boolean isMandatoryNotFill;
    private final List<MultipartBody.Part> docAns = new ArrayList<>();
    private final List<MultipartBody.Part> signAns = new ArrayList<>();
    private final ArrayList<Answer> signQueIdArray = new ArrayList<>();
    private final ArrayList<Answer> docQueIdArray = new ArrayList<>();
    private List<String> docanspath=new ArrayList<>();
    private List<String> signanspath=new ArrayList<>();
    private final ArrayList<Answer> answerArrayList = new ArrayList<>();
    private List<Attachments> attchmentList ;
    private boolean isShowingImg = false, showOnlyImg = false;
    private LinearLayout ll_attachment,ll_completionForm;
    private List<IsMarkDoneWithJtid> isMarkDoneList = new ArrayList<>();
    private boolean isCompletion = false;
    BackgroundTaskExecutor backgroundTaskExecutor;
    ConstraintLayout cl_notSyncParent;
    int competionNotesPostion = -1;
    String complNotes ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_completion);
        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (getIntent().hasExtra("Complation")) {
                String jobDataStr = bundle.getString("Complation");
                Job jobData = new Gson().fromJson(jobDataStr, Job.class);
                showOnlyImg = bundle.getBoolean("showAttachment",false);
                jobId = jobData.getJobId();
                setJobData(jobData);
            }
        }
//        JobList jobList = new JobList();
//        jobList.setNotifyForCompletion(jobList);
        EotApp.getAppinstance().setNotifyForMultiDocAdd(this);
        EotApp.getAppinstance().setNotifyForcompletion(this);
        initializeMyViews();
    }

    public void setJobData(Job jobData){
        this.jobData = jobData;
    }
    public Job getJobData (){
        return jobData;
    }
    private void initializeAdapter() {
        ArrayList<Attachments> getFileList_res = new ArrayList<>();
        ArrayList<Object> allItem = new ArrayList<>();
        backgroundTaskExecutor = new BackgroundTaskExecutor();
        jobCompletionAdpter = new JobCompletionAdpter(this, getFileList_res, JobCompletionActivity.this, jobId
                ,( jaId, queId, jtId) -> {
            Log.e("", "");
            this.queId = queId;
            this.jtId = jtId;
            if(AppUtility.isInternetConnected()) {
                if (complPi != null) {
                    showDialogForRemoveAttch(jaId);
                }
            }else {
                    showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.offline_feature_alert));
            }
        });
        recyclerView.setAdapter(jobCompletionAdpter);
        completionFormAdapter = new CompletionFormAdapter(JobCompletionActivity.this, allItem, new MyFormInterFace() {
            @Override
            public void getAnsId(String ansId) {

            }
        },this,backgroundTaskExecutor);
        completionRecyclerView.setAdapter(completionFormAdapter);


    }

    public void showDialogForRemoveAttch(final String jaId) {
        AppUtility.alertDialog2(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.are_you_sure),
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.doc_remove),
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok),
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel), new Callback_AlertDialog() {
                    @Override
                    public void onPossitiveCall() {
                        try {
                            complPi.removeUploadAttchment(jaId, queId, jtId);
                        } catch (Exception ex) {
                            AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","showDialogForRemoveAttch()"+ex.getMessage(),"JobCompletionActivity","");
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onNegativeCall() {

                    }
                });
    }

    @Override
    public void uploadDocDelete(String msg,String queId, String jtId) {
//        doc_attch_pi.getAttachFileList(jobId, App_preference.getSharedprefInstance().getLoginRes().getUsrId(), "6",true);
        doc_attch_pi.getMultiAttachFileList(jobId, App_preference.getSharedprefInstance().getLoginRes().getUsrId(), "6",true,parentPositon,position, queId,jtId);
    }


    @Override
    public void sessionexpire(String msg) {
        AppUtility.alertDialog(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title),
                msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> {
                    EotApp.getAppinstance().sessionExpired();
                    return null;
                });
    }

    private void initializeMyViews() {
        tv_label_des = findViewById(R.id.tv_label_des);
        tv_label_des.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.completion_note));
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);
//        CustomLinearLayoutManager customLayoutManager = new CustomLinearLayoutManager(this, LinearLayoutManager.VERTICAL
//                , false);

        recyclerView.setLayoutManager(layoutManager);
        completionRecyclerView = findViewById(R.id.rv_completion_form);
        initializeAdapter();
        doc_attch_pi = new Doc_Attch_Pc(this);
//        doc_attch_pi.getAttachFileList(jobId, App_preference.getSharedprefInstance().getLoginRes().getUsrId(), "6",true);

        cancel_txt = findViewById(R.id.cancel_txt);
        complHeader = findViewById(R.id.complHeader);
        save_txt = findViewById(R.id.save_txt);

        cancel_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel));
        complHeader.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.completion_details));

        ll_attachment = findViewById(R.id.ll_attachment);
        ll_completionForm = findViewById(R.id.ll_completionForm);
        cl_notSyncParent = findViewById(R.id.cl_notSyncParent);
        txt_notSyncLabel = findViewById(R.id.txt_notSyncLabel);
        txt_notSyncLabel.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.not_synced_at));


        suggestion_img = findViewById(R.id.suggestion_img);
        job_suggestion_spinner = findViewById(R.id.job_suggestion_spinner);
        suggestion_img.setOnClickListener(v -> {
            if (suggestionList != null && suggestionList.size() > 0)
                job_suggestion_spinner.performClick();
            else {
                AppUtility.alertDialog(JobCompletionActivity.this,
                        "", LanguageController.getInstance()
                                .getMobileMsgByKey(AppConstant.no_suggesstion)
                        , LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok),"", () -> null);
            }

        });
        viewClickListner();

        setComplationView();

        complPi = new Compl_PC(this);

        filterJobServices();

        if (AppUtility.isInternetConnected()) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
        if(showOnlyImg){
            ll_completionForm.setVisibility(View.GONE);
            ll_attachment.setVisibility(View.VISIBLE);
            setList(new ArrayList<>(),"",true);
        }else {
            ll_completionForm.setVisibility(View.VISIBLE);
            ll_attachment.setVisibility(View.GONE);
            setLists();
        }
    }

    private void filterJobServices() {
        try {
            if (jobData != null && jobData.getJtId() != null && jobData.getJtId().size() > 0) {
                if (suggestionList != null && suggestionList.size() > 0) {
                    suggestionList.clear();
                }

                for (JtId jtId : jobData.getJtId()) {
                    JobTitle jobTitle1 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobTitleModel().getJobTitleByid(jtId.getJtId());
                    if (jobTitle1 != null)
                        suggestionList.addAll(jobTitle1.getSuggestionList());
                }

                List<String> tempList = new ArrayList<>();
//                suggestionsArray = new String[suggestionList.size()];
                for (int i = 0; i < Objects.requireNonNull(suggestionList).size(); i++) {
                    try {
                        if (suggestionList.get(i).getComplNoteSugg() != null &&
                                suggestionList.get(i).getComplNoteSugg().length() > 0) {
                            tempList.add(suggestionList.get(i).getComplNoteSugg());
                            //     suggestionsArray[i] = suggestionList.get(i).getComplNoteSugg();
                        }
                    } catch (Exception e) {
                        AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","filterJobServices()"+e.getMessage(),"JobCompletionActivity","");
                        e.printStackTrace();
                    }
                }
                try {
                    suggestionsArray = new String[tempList.size()];
                    for (int i = 0; i < tempList.size(); i++) {
                        suggestionsArray[i] = tempList.get(i);
                    }
                    setSuggestionsArray(suggestionsArray);
                } catch (Exception e) {
                    AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","filterJobServices()"+e.getMessage(),"JobCompletionActivity","");
                    e.printStackTrace();
                }
//                try {
//                    if (suggestionAdapter == null) {
//                        AppUtility.spinnerPopUpWindow(job_suggestion_spinner);
//                        suggestionAdapter = new JoBServSuggAdpter(JobCompletionActivity.this, suggestionsArray
//                                , nm -> setSelectedSuggeston(nm));
//                        job_suggestion_spinner.setAdapter(suggestionAdapter);
//                    }
//                } catch (Exception e) {
//                    AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","filterJobServices()"+e.getMessage(),"JobCompletionActivity","");
//                    e.printStackTrace();
//                }
            }
        } catch (Exception exception) {
            AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","filterJobServices()"+exception.getMessage(),"JobCompletionActivity","");
            exception.printStackTrace();
        }
    }

    public void setSuggestionsArray(String[] suggestionsArray) {
        this.suggestionsArray = suggestionsArray;
    }

    public String[] getSuggestionsArray(){
        return suggestionsArray;
    }
    private void setSelectedSuggeston(String nm) {
        try {
//            String str = "";
//            int cursorpostion=compedt.getText().toString().length();
//            if (compedt.getText().toString().trim().length() > 0) {
//                str = compedt.getText().toString() + "\n" + nm;
//            } else {
//                str = nm;
//            }
//            compedt.setText(str);
//            str.replace("<br>","");
//            str.replace("null","");
//            compedt.setText(str);
//            compedt.setSelection(cursorpostion+nm.length()+1);
        } catch (Exception e) {
            AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","setSelectedSuggeston()"+e.getMessage(),"JobCompletionActivity","");
            e.printStackTrace();
        }
    }

    private void setComplationView() {
        if (TextUtils.isEmpty(jobData.getComplNote())) {
            save_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.save_btn));
        } else {
            save_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update));
        }
    }

    private void viewClickListner() {
        cancel_txt.setOnClickListener(this);
        save_txt.setOnClickListener(this);
    }
    boolean isfilled;
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_txt:
                if(isShowingImg){
                    ll_attachment.setVisibility(View.GONE);
                    ll_completionForm.setVisibility(View.VISIBLE);
                    isShowingImg = false;
                }
                else {
                AppUtility.hideSoftKeyboard(this);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
            }
                break;
            case R.id.save_txt:
                AppUtility.hideSoftKeyboard(this);
                for (QuesRspncModel item : completionFormAdapter.getTypeList()
                ) {
                        if(item.getType().isEmpty()){
                            if(item.getMandatory().equals("1"))
                                if(item.getAns().get(0).getValue().isEmpty())
                                    isMandatoryNotFill = true;
                            complNotes = item.getAns().get(0).getValue();
                        }
                }
//                complPi.addEditJobComplation(jobData.getJobId(), complNotes, compQueAns);


                ansAnsQuesRspncModel(completionFormAdapter.getTypeList());

                /**    if question is mandatory but not fill   ***/
                for (Object item :allItem
                     ) {
                    if (item instanceof QuesRspncModel) {
                        if (((QuesRspncModel) item).getType().equals("13") && ((QuesRspncModel) item).getMandatory().equals("1")){
                            String jobId = jobData.getJobId();
                            String queId = ((QuesRspncModel) item).getQueId();
                            String jtId = ((QuesRspncModel) item).getJtId();

                            List<Attachments> attachmentsList = AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsById(jobId,queId,jtId);
                            if (attachmentsList.size()<=0) {
                                isMandatoryNotFill = true;
                            }
                    }
                    } else if (item instanceof List) {
                        for (QuesRspncModel listItem : (ArrayList<QuesRspncModel>) item
                        ) {
                            if (((QuesRspncModel) listItem).getType().equals("13") && ((QuesRspncModel) listItem).getMandatory().equals("1")){
                                String jobId = jobData.getJobId();
                                String queId = ((QuesRspncModel) listItem).getQueId();
                                String jtId = ((QuesRspncModel) listItem).getJtId();

                                List<Attachments> attachmentsList = AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsById(jobId,queId,jtId);
                                if (attachmentsList.size()<=0) {
                                    isMandatoryNotFill = true;
                                }
                            }
                        }
                    }
                }
                if (isMandatoryNotFill) {
                    isMandatoryNotFill = false;
                    AppUtility.alertDialog(this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.fill_all_mandatory_questions), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return null;
                        }
                    });
                    return;
                }
//                if (!isfilled)
                    if (!emptyCheckFormValidation()) {
                        emptyAnsFieldError();
                        return;
                    }

                if (answerArrayList.size() > 0 || !complNotes.isEmpty()) {
                    Ans_Req ans_req = new Ans_Req(App_preference.getSharedprefInstance().getLoginRes().getUsrId(), answerArrayList,
                            App_preference.getSharedprefInstance().getJobCompletionForm().getFrmId(), jobData.getJobId());
                    if (!AppUtility.isInternetConnected())
                    {
                        List<QuesRspncModel> offLineQueAns = new ArrayList<>();
                         for (Object item : allItem
                             ) {
                            if(item instanceof QuesRspncModel){
                                offLineQueAns.add((QuesRspncModel) item);
                            }else if(item instanceof List){
                                offLineQueAns.addAll((List<QuesRspncModel>)item);
                            }
                        }
                        OfflieCompleQueAns offlieCompleQueAns = new OfflieCompleQueAns(jobData.getJobId(),offLineQueAns,isMarkDoneList);
                        OfflieCompleQueAns_Dao offlieCompleQueAnsDao = AppDataBase.getInMemoryDatabase(this).offline_completion_ans_dao();
                        offlieCompleQueAnsDao.inserOfflineAns(offlieCompleQueAns);
//                        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//                        if (fragments.isEmpty()||(fragments.size()<=1||(fragments.get(0)instanceof CustomFormFragment)))
//                        {
//                            setFormDraft("-1",answerArrayList);
//                        }else{
//                            for (int i=0;i<fragments.size();i++) {
//                                if (fragments.get(i) instanceof CustomFormFragment) {
//                                    String pid = ((CustomFormFragment) fragments.get(i)).optionid;
//                                    ((CustomFormFragment) fragments.get(i)).getAnsListSaveBtn();
//                                    ArrayList<Answer> childanswerArrayList = ((CustomFormFragment) fragments.get(i)).answerArrayList;
//                                    setFormDraft(pid, childanswerArrayList);
//                                }else{
//                                    continue;
//                                }
//                            }
//                        }
                    }
//                    queAns_pi.addAnswerWithAttachments(ans_req, signAns, docAns, signQueIdArray, docQueIdArray,docanspath,signanspath);
                    complPi.addEditJobComplation(jobData.getJobId(), complNotes, answerArrayList,signanspath,docanspath,signQueIdArray,docQueIdArray,isMarkDoneList);
                }
                if (AppUtility.isInternetConnected()) {
//                    AppDataBase.getInMemoryDatabase(this).customFormDao().deleterecode(customFormList.getFrmId(), jobId);
//                    Intent intent = new Intent();
//                    intent.putExtra("fmid", customFormList.getFrmId());
//                    intent.putExtra("isDelete", true);
//                    setResult(RESULT_OK, intent);
//                    if (answer != null)
//                        answer.clear();
                }
                break;
            case R.id.tvDone:
//                setUpdatedDesc(1);
                break;
            case R.id.tvCancel:
//                setUpdatedDesc(2);
                break;
            case R.id.tvFetchDes:
//                setUpdatedDesc(3);
                break;
            case R.id.tvtimestemp:
//                setUpdatedDesc(4);
                break;
        }
    }

    @Override
    public void selectFilesForCompletion(boolean isCompletion) {
        this.isCompletion =isCompletion;
//        if (!Utils.isOnline(this)) {
//            AppUtility.alertDialog(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title), LanguageController.getInstance().getMobileMsgByKey(AppConstant.feature_not_available), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
//        } else {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.bottom_image_chooser);
        TextView camera = dialog.findViewById(R.id.camera);
        assert camera != null;
        camera.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.camera));
        TextView gallery = dialog.findViewById(R.id.gallery);
        assert gallery != null;
        gallery.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.gallery));
        ////After discussion with Shubham and Jit Sir hide attachment all code 19/12/23
//            TextView drive_document = dialog.findViewById(R.id.drive_document);
        LinearLayout drive_layout = dialog.findViewById(R.id.driveLayout);
        drive_layout.setVisibility(View.GONE);
//            assert drive_document != null;
//            drive_document.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant              .document));
        camera.setOnClickListener(view -> {
            if (AppUtility.askCameraTakePicture(JobCompletionActivity.this)) {
                takePictureFromCamera();
            } else {
                // Sdk version 33
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                    askTedPermission(0, AppConstant.cameraPermissions33);
                }else {
                    askTedPermission(0, AppConstant.cameraPermissions);
                }
            }
            dialog.dismiss();
        });

        gallery.setOnClickListener(view -> {
            if (AppUtility.askGalaryTakeImagePermiSsion(JobCompletionActivity.this)) {
                getImageFromGallray(isCompletion);
            } else {
                // Sdk version 33
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                    askTedPermission(1, AppConstant.galleryPermissions33);
                }else {
                    askTedPermission(1, AppConstant.galleryPermissions);
                }
            }
            dialog.dismiss();
        });

        dialog.show();
    }
    @Override
    public void selectFiles() {
//        if (!Utils.isOnline(this)) {
//            AppUtility.alertDialog(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title), LanguageController.getInstance().getMobileMsgByKey(AppConstant.feature_not_available), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
//        } else {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.bottom_image_chooser);
        TextView camera = dialog.findViewById(R.id.camera);
        assert camera != null;
        camera.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.camera));
        TextView gallery = dialog.findViewById(R.id.gallery);
        assert gallery != null;
        gallery.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.gallery));
        ////After discussion with Shubham and Jit Sir hide attachment all code 19/12/23
//            TextView drive_document = dialog.findViewById(R.id.drive_document);
        LinearLayout drive_layout = dialog.findViewById(R.id.driveLayout);
        drive_layout.setVisibility(View.GONE);
//            assert drive_document != null;
//            drive_document.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant              .document));
        camera.setOnClickListener(view -> {
            if (AppUtility.askCameraTakePicture(JobCompletionActivity.this)) {
                takePictureFromCamera();
            } else {
                // Sdk version 33
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                    askTedPermission(0, AppConstant.cameraPermissions33);
                }else {
                    askTedPermission(0, AppConstant.cameraPermissions);
                }
            }
            dialog.dismiss();
        });

        gallery.setOnClickListener(view -> {
            if (AppUtility.askGalaryTakeImagePermiSsion(JobCompletionActivity.this)) {
                getImageFromGallray(false);
            } else {
                // Sdk version 33
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                    askTedPermission(1, AppConstant.galleryPermissions33);
                }else {
                    askTedPermission(1, AppConstant.galleryPermissions);
                }
            }
            dialog.dismiss();
        });
////After discussion with Shubham and Jit Sir hide attachment all code 19/12/23
//            drive_document.setOnClickListener(view -> {
//                if (AppUtility.askGalaryTakeImagePermiSsion(JobCompletionActivity.this)) {
//                    takeimageFromGalary();//only for drive documents
//                } else {
//                    // Sdk version 33
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
//                        askTedPermission(2, AppConstant.galleryPermissions33);
//                    }else {
//                        askTedPermission(2, AppConstant.galleryPermissions);
//                    }
//                }
//                dialog.dismiss();
//            });
        dialog.show();
//        }
    }

    private void askTedPermission(int type, String[] permissions) {
        TedPermission.with(EotApp.getAppinstance())
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (type == 0)
                            takePictureFromCamera();
                        else if (type == 1)
                            getImageFromGallray(false);
                        ////After discussion with Shubham and Jit Sir hide attachment all code 19/12/23
//                        else if (type == 2)
//                            takeimageFromGalary();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [SettingActivity] > [Permission]")
                .setPermissions(permissions)
                .check();
    }

    @Override
    public void OnItemClick_Document(Attachments attachments) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(App_preference.getSharedprefInstance().getBaseURL() + "" + attachments.getAttachFileName())));
    }

    @Override
    public void openAttachmentDialog() {
        selectFiles();
    }

    @Override
    public void setList(ArrayList<Attachments> getFileList_res, String isAttachCommpletionNotes, boolean firstCall) {
        progressBar.setVisibility(View.GONE);
        if (currentDialog != null) {
            currentDialog.dismiss();
            currentDialog = null;
        }
//        if (getFileList_res.size() == 1)
//            this.fileList_res.addAll(getFileList_res);
//        else
            this.fileList_res = getFileList_res;
        if (jobCompletionAdpter != null) {
            (jobCompletionAdpter).updateFileList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsByJobId(jobId),firstCall,"","");
            }
    }

    @Override
    public void setMultiList(ArrayList<Attachments> attachments, String isAttachCompletionNotes, boolean firstCall, int parentPositon, int position, String queId, String jtId) {
        AppDataBase.getInMemoryDatabase(this).attachments_dao().insertAttachments(attachments);
        AppDataBase.getInMemoryDatabase(this).attachments_dao().deleteAttachments();
        onDocumentSelected("","",false,parentPositon, position, queId, jtId);
    }

    @Override
    public void addNewItemToAttachmentList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes) {
        if (isFileImage) {
            String bitmapString = "";
            // remove the temporary added item for showing loader
            if (fileList_res != null && !fileList_res.isEmpty()) {
                int position = -1;
                for (int i = 0; i < fileList_res.size(); i++) {
                    if (fileList_res.get(i).getAttachmentId().equalsIgnoreCase("0")) {
                        position = i;
                        break;
                    }
                }
                if (position != -1) {
                    bitmapString = fileList_res.get(position).getBitmap();
                    fileList_res.remove(fileList_res.get(position));
                }
            }
            // to add the new entry into existing list
            if (getFileList_res != null) {
                assert fileList_res != null;
                getFileList_res.addAll(fileList_res);
                getFileList_res.get(0).setBitmap(bitmapString);
                setList(getFileList_res, isAttachCompletionNotes,true);
            }
        }
    }

    @Override
    public void addView() {
        // recyclerView.setVisibility(View.VISIBLE);
        //  nolist_linear.setVisibility(View.GONE);
    }

    @Override
    public void questionlist(List<QuesRspncModel> quesRspncModelList) {
        if(AppUtility.isInternetConnected()) {
            this.quesRspncModelList = quesRspncModelList;
            if (quesRspncModelList.size() > 0) {
                AppCenterLogs.addLogToAppCenterOnAPIFail("Customform", "", "listsize- " + quesRspncModelList.size(), "FormQueAns_Activity", "");
                HyperLog.i("customform", "Questionlist() ::" + quesRspncModelList.size());
                String getfrombyid = App_preference.getSharedprefInstance().getJobCompletionForm().getFrmId();
                Type listType = new TypeToken<List<Answer>>() {
                }.getType();
//            answer = new Gson().fromJson(getfrombyid, listType);
//
//
//            if (answer!=null) {
//                for (int i = 0; i < quesRspncModelList.size(); i++) {
//                    for (int j = 0; j < answer.size(); j++) {
//                        if (quesRspncModelList.get(i).getQueId().equals(answer.get(j).getQueId())) {
//                            quesRspncModelList.get(i).setAns(answer.get(j).getAns());
//                        }
//                    }
//                }
//            }
//                setAdapeter(quesRspncModelList);
                isFormPreFilled();
            } else {
//            em_layout.setVisibility(View.VISIBLE);
//            rl.setVisibility(View.GONE);
//            AppCenterLogs.addLogToAppCenterOnAPIFail("CustomForm","","questionlist()-> listsize - "+quesRspncModelList.size(),"FormQueAns_Activity","");
//            HyperLog.i("customform","Questionlist()else ::"+quesRspncModelList.size());
//            nolist_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_form_list));
            }
        }else {
//            setAdapeter(App_preference.getSharedprefInstance().getJobCompletionFormFields());
        }
    }
    HashMap<String,Boolean> groupedAddedList = new HashMap<>();
    private void setLists(){
        List<String> addedKeys = new ArrayList<>();
        List<QuesRspncModel> filterList ;
        Set<IsMarkDoneWithJtid> isMarkDoneWithJtids = new HashSet<>();
//        List<Attachments> list11 = AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsById(jobData.getJobId(),"384","");
        boolean isNotSynced = false;
        if(!AppUtility.isInternetConnected()){
            OfflieCompleQueAns notSyncedList = AppDataBase.getInMemoryDatabase(this).offline_completion_ans_dao().getComplQueAnsById(jobData.getJobId());
            if(notSyncedList != null){
                cl_notSyncParent.setVisibility(View.VISIBLE);
               filterList = notSyncedList.getAllQuestionAnswer();
               isMarkDoneWithJtids.addAll(notSyncedList.getIsMarkDoneWithJtId());
               isNotSynced = true;
            }else {
                cl_notSyncParent.setVisibility(View.GONE);
                filterList = App_preference.getSharedprefInstance().getJobCompletionFormFields();
                 isMarkDoneWithJtids = new HashSet<>(jobData.getIsMarkDoneWithJtId());
            }
        }else {
            cl_notSyncParent.setVisibility(View.GONE);
            filterList = App_preference.getSharedprefInstance().getJobCompletionFormFields();
             isMarkDoneWithJtids = new HashSet<>(jobData.getIsMarkDoneWithJtId());
        }

        //        List<QuesRspncModel> offlineCompletionFormList = App_preference.getSharedprefInstance().getJobCompletionFormFields();
//        Gson gson = new Gson();
//        Type questionListType = new TypeToken<List<QuesRspncModel>>(){}.getType();
//        List<QuesRspncModel> filterList = gson.fromJson(jsonArrayString, questionListType);
//        List<QuesRspncModel> filterList = DetailFragment.getInstance().getCompFormList();

        if(isMarkDoneWithJtids.size()> 0){
            isMarkDoneList.addAll(isMarkDoneWithJtids);
        }
//        Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
        if(jobData.getCompliAnsArray()!=null && !jobData.getCompliAnsArray().isEmpty() && !isNotSynced){
            for (CompliAnsArray ansItem : jobData.getCompliAnsArray()
                 ) {
                for (QuesRspncModel questItem : filterList
                     ) {
//                    if(!questItem.getType().equals("13")) {
                    if(questItem.getQueId().equals(ansItem.getQueId())){
                            if (questItem.getAns().size() == 0) {
                                AnswerModel answerModel = new AnswerModel(ansItem.getKey(), ansItem.getValue(), ansItem.getJtId());
                                List<AnswerModel> answerModels = new ArrayList<>();
                                answerModels.add(answerModel);
                                questItem.setAns(answerModels);
                            } else {
                                AnswerModel answerModel = new AnswerModel(ansItem.getKey(), ansItem.getValue(), ansItem.getJtId());
                                List<AnswerModel> answerModels = new ArrayList<>();
                                answerModels.addAll(questItem.getAns());
                                answerModels.add(answerModel);
                                questItem.setAns(answerModels);
                            }
                        }
                        }
//                else {
//                        List<Attachments> list = new ArrayList<>();
//                        if(questItem.getIsLinkWithService().equals("0")){
//                            list.addAll(AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsById(jobData.getJobId(), questItem.getQueId(),questItem.getJtId()));
//                            questItem.setAttachmentsList(list);
//                        }else if(questItem.getIsLinkWithService().equals("1") && !questItem.getJtId().isEmpty()){
//                            list.addAll(AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsById(jobData.getJobId(), questItem.getQueId(), questItem.getJtId()));
//                            questItem.setAttachmentsList(list);
//                        }
//                    }
//                }
            }
        }
        if(AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsByJobId(jobData.getJobId()) != null){
            for (QuesRspncModel questItem : filterList
            ) {
                if(questItem.getType().equals("13")) {
                    List<Attachments> list = new ArrayList<>();
                    if(questItem.getIsLinkWithService().equals("0")){
                        list.addAll(AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsById(jobData.getJobId(), questItem.getQueId(),questItem.getJtId()));
                        questItem.setAttachmentsList(list);
                    }else if(questItem.getIsLinkWithService().equals("1") && !questItem.getJtId().isEmpty()){
                        list.addAll(AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsById(jobData.getJobId(), questItem.getQueId(), questItem.getJtId()));
                        questItem.setAttachmentsList(list);
                    }
                }
            }
        }
        List<JtId> jtIdList = jobData.getJtId();
        for(QuesRspncModel takeItem : filterList){
            if(!takeItem.getJtId().isEmpty()){
                for (JtId jtid: jtIdList
                ) {
                    if(takeItem.getJtId().equals(jtid.getJtId())){
                        questions.add(takeItem);
                    }
                }
            }else {
                questions.add(takeItem);
            }
        }
        for (int i= 0 ;i< questions.size() ; i++) {
            String jtid = questions.get(i).getJtId();
            if (jtid.equals("") && jtid.isEmpty() && questions.get(i).getIsLinkWithService().equals("0") && !questions.get(i).getType().isEmpty()) {
                // Handle single item
                singleQuestions.add(questions.get(i));
            } else if (questions.get(i).getIsLinkWithService().equals("1") && questions.get(i).getJtId().isEmpty()) {
                if(allFieldQuestions.size()>0){
                    removeItemPositon.add(questions.get(i));
                }
                allFieldQuestions.add(questions.get(i));

            } else if (questions.get(i).getType().equals("") && questions.get(i).getType().isEmpty()) {
//                if (groupedQuestions.containsKey("SystemField")) {
//                    groupedQuestions.get("SystemField").add(questions.get(i));
//                    removeItemPositon.add(questions.get(i));
//                } else {
//                    List<QuesRspncModel> questionList = new ArrayList<>();
//                    questionList.add(questions.get(i));
//                    groupedQuestions.put("SystemField", questionList);
//                }
//                if(questions.get(i).getSysFieldType().equals("1")){
                    String notes=jobData.getComplNote().replace("null","");
                    notes.replace("<br>","");
                    AnswerModel ans = new AnswerModel("0",notes);
                    List<AnswerModel> list = new ArrayList<>();
                    list.add(ans);
                    questions.get(i).setAns(list);
//                }
//                if(systemFieldQuestions.size()>0){
//                    removeItemPositon.add(questions.get(i));
//                }
                systemFieldQuestions.add(questions.get(i));

            } else {
                // Handle grouped item
                if (groupedQuestions.containsKey(jtid)) {
                    groupedQuestions.get(jtid).add(questions.get(i));
                    removeItemPositon.add(questions.get(i));
                } else {
                    List<QuesRspncModel> questionList = new ArrayList<>();
                    questionList.add(questions.get(i));
                    groupedQuestions.put(jtid, questionList);
                }
            }
        }
        for (JtId jtId : jobData.getJtId()
             ) {
            if(isNotKey(jtId.getJtId())){
                addedKeys.add(jtId.getJtId());
            }
        }
        for (String key: addedKeys
             ) {
            groupedQuestions.put(key, new ArrayList<QuesRspncModel>());
        }
        for (String key : groupedQuestions.keySet()){
            for (QuesRspncModel allFieldItem : allFieldQuestions) {
                if(!key.equals("SystemField")) {
                    try {
                       AllTypeFieldClone allTypeFieldClone =  new AllTypeFieldClone(allFieldItem);
                       AllTypeFieldClone cloned = (AllTypeFieldClone) allTypeFieldClone.clone();
                       cloned.getQuesRspncModel().setJtId(key);
                       if(allFieldItem.getType().equals("3")){
                           boolean ansFilled = false;
                           for (AnswerModel ans : allFieldItem.getAns()
                           ) {
                               if (ans.getJtId().equals(key)) {
                                   if(!ansFilled) {
                                       AnswerModel answerModel = new AnswerModel(ans.getKey(), ans.getValue(), ans.getJtId());
                                       List<AnswerModel> listAns = new ArrayList<>();
                                       listAns.add(answerModel);
                                       cloned.getQuesRspncModel().setAns(listAns);
                                       ansFilled = true;
                                   }else {
                                       AnswerModel answerModel = new AnswerModel(ans.getKey(), ans.getValue(), ans.getJtId());
                                       cloned.getQuesRspncModel().getAns().add(answerModel);
                                   }

                               }
                           }
                           if(!ansFilled){
                               AnswerModel answerModel = new AnswerModel("0", "", key);
                               List<AnswerModel> listAns = new ArrayList<>();
                               listAns.add(answerModel);
                               cloned.getQuesRspncModel().setAns(listAns);
                           }
                       }else if(allFieldItem.getType().equals("13")){
                           List<Attachments> list = new ArrayList<>();
                           list.addAll(AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsById(jobData.getJobId(), cloned.getQuesRspncModel().getQueId(),cloned.getQuesRspncModel().getJtId()));
                           cloned.getQuesRspncModel().setAttachmentsList(list);
                       }else {
                           for (AnswerModel ans : allFieldItem.getAns()
                           ) {
                               if (ans.getJtId().equals(key)) {
                                   AnswerModel answerModel = new AnswerModel(ans.getKey(), ans.getValue(), ans.getJtId());
                                   List<AnswerModel> listAns = new ArrayList<>();
                                   listAns.add(answerModel);
                                   cloned.getQuesRspncModel().setAns(listAns);
                                   break;
                               } else {
                                   AnswerModel answerModel = new AnswerModel("0", "", key);
                                   List<AnswerModel> listAns = new ArrayList<>();
                                   listAns.add(answerModel);
                                   cloned.getQuesRspncModel().setAns(listAns);
                               }
                           }
                       }
                        groupedQuestions.get(key).add(cloned.getQuesRspncModel());
                    }catch (Exception e){

                    }
//                    QuesRspncModel quesRspncModel = new QuesRspncModel(allFieldItem.getQueId(),
//                            allFieldItem.getParentId(),
//                            allFieldItem.getParentAnsId(),
//                            allFieldItem.getFrmId(),
//                            allFieldItem.getDes(),
//                            allFieldItem.getFrmType(),
//                            allFieldItem.getMandatory(),
//                            false,
//                            allFieldItem.getOpt(),
//                            allFieldItem.getAns(),
//                            allFieldItem.getIndex(),
//                            allFieldItem.getFrmType(),
//                            allFieldItem.getInternalLabel(),
//                            allFieldItem.getQueWidth(),
//                            allFieldItem.getLinkToLead(),
//                            allFieldItem.getLinkToJob(),
//                            allFieldItem.getIsShowOnSite(),
//                            allFieldItem.getIsDisable(),
//                            allFieldItem.getIsLinkWithService(),
//                            key,
//                            allFieldItem.getSysFieldType(),
//                            allFieldItem.getLinkTo(),
//                            allFieldItem.getSystemField(),
//                            allFieldItem.getIsAnswered()
//                    );


                }
            }
        }
        sortQuestinsList = questions;
        sortQuestinsList.removeAll(removeItemPositon);
        allItem.clear();
        boolean isAllField = false;
        for (QuesRspncModel item : sortQuestinsList
        ) {
//            if(item.getSystemField().equals("1")){
//                allItem.addAll(systemFieldQuestions);
//                break;
//            }
            for (QuesRspncModel systemItem : systemFieldQuestions){
                if(item.getType().equals("")&& item.getType().isEmpty()){
                    allItem.addAll(systemFieldQuestions);
                    setCompletionNotesPosition(allItem.size()-1);
                    break;
                }
            }
            for (QuesRspncModel singleItem: singleQuestions
            ) {
                if(singleItem.getQueId().equals(item.getQueId())){
                    allItem.add(singleItem);
                    break;
                }
            }


                for (String key : groupedQuestions.keySet()
                ) {
//                if(item.getSystemField().equals("1")){
//                    allItem.add(groupedQuestions.get("SystemField"));
//                    break;
//                }else
                    if(isNotAdded(key)) {
                        for (QuesRspncModel queItem : groupedQuestions.get(key)
                        ) {
                            if (queItem.getQueId().equals(item.getQueId())) {
                                setServiceMarkasDone(groupedQuestions.get(key));
                                allItem.add(groupedQuestions.get(key));
                                groupedAddedList.put(key, true);
                                isAllField = true;
                                break;
                            }
                        }

                        if (key.equals(item.getJtId())) {
                            if (!isAllField) {
                                setServiceMarkasDone(groupedQuestions.get(key));
                                allItem.add(groupedQuestions.get(item.getJtId()));
                                groupedAddedList.put(key, true);
                                break;
                            }
                        }
                    }
                }
            }
        if(App_preference.getSharedprefInstance().getLoginRes().getIsCompleShowMarkDone().equals("1")) {
            for (String key : addedKeys
            ) {
                if (isNotAdded(key)) {
                    if (groupedQuestions.get(key).size() > 0) {
                        setServiceMarkasDone(groupedQuestions.get(key));
                        allItem.add(groupedQuestions.get(key));
                    } else {
                        QuesRspncModel quesRspncModel = new QuesRspncModel("temp", "", "", "", "", "-1", "", false, new ArrayList<OptionModel>(), new ArrayList<AnswerModel>(), -1, "", "", "", "", "", "", "", "", key, "", "", "", -1, new ArrayList<Attachments>());
                        groupedQuestions.get(key).add(quesRspncModel);
                        setServiceMarkasDone(groupedQuestions.get(key));
                        allItem.add(groupedQuestions.get(key));
                    }
                }
            }
        }
        Log.e("All Item",""+allItem.toString());
        if(groupedQuestions.size()>0) {
            backgroundTaskExecutor.createInstance(groupedQuestions.size());
        }
        completionFormAdapter.updateAdapter(allItem);
//        CompletionFormAdapter.DefaultViewHolder defaultViewHolder = (CompletionFormAdapter.DefaultViewHolder) completionRecyclerView.findViewHolderForAdapterPosition(completionFormAdapter.getPostionDefaultViewHolder());
//        defaultViewHolder.jobCompletionAdpter.updateFileList((ArrayList<Attachments>) DetailFragment.getInstance().getCompAttachmentList(),true);
//        completionFormAdapter = new CompletionFormAdapter(JobCompletionActivity.this, allItem, new MyFormInterFace() {
//            @Override
//            public void getAnsId(String ansId) {
//
//            }
//        });
//        completionRecyclerView.setAdapter(completionFormAdapter);
    }

    public boolean isNotAdded(String key){
        boolean isNot  = true;
        for(String item : groupedAddedList.keySet()){
            if(item.equals(key)){
                isNot = false;
                break;
            }
        }
        return isNot;
    }
    public boolean isNotKey(String jtid){
        boolean isNot = true;
        for (String key : groupedQuestions.keySet()
             ) {
            if(key.equals(jtid)){
                isNot = false;
                break;
            }
        }
        return isNot;
    }

    private void isFormPreFilled() {
        if (completionFormAdapter != null && completionFormAdapter.getTypeList() != null) {
            ArrayList<QuesRspncModel> checkList = completionFormAdapter.getTypeList();
            for (QuesRspncModel qm : checkList) {
                List<AnswerModel> ans = qm.getAns();
                if (ans != null && ans.size() > 0) {
                    AnswerModel model = ans.get(0);
                    if (model != null && !TextUtils.isEmpty(model.getValue()))
                        isfilled = true;
                }
            }
        }
    }

    @Override
    public void onSubmitSuccess(String msg) {

    }

    @Override
    public void onSubmitSuccessOffline() {

    }

    @Override
    public void addfragmentDynamically(List<QuesRspncModel> quesRspncModelList, String option) {

    }

    @Override
    public void onSessionExpire(String msg) {
        AppUtility.alertDialog(this, LanguageController.getInstance().getMobileMsgByKey              (AppConstant.dialog_error_title), msg, LanguageController.getInstance().getMobileMsgByKey            (AppConstant.ok), "",
                () -> {
                    EotApp.getAppinstance().sessionExpired();
                    return null;
                });
    }

    @Override
    public void showOfflineAlert(String msg) {

    }

    @Override
    public void showofflineAlertchild() {

    }

    @Override
    public void finishMuAvtivity() {

    }

    @Override
    public void fileExtensionNotSupport(String msg) {
    }

    @Override
    public void onDocumentUpdate(String msg, boolean isSuccess) {
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * get image from camera & edit & croping functinallity
     */
    private void takePictureFromCamera() {
        if (!AppUtility.askCameraTakePicture(JobCompletionActivity.this)) {
            return;
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Eot Directory");

        if (!path.exists()) {
            path.mkdir();
        }

        File imageFile = null;
        try {
            imageFile = createImageFile();
        } catch (IOException e) {
            AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","takePictureFromCamera()"+e.getMessage(),"JobCompletionActivity","");
            e.printStackTrace();
        }

        Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", imageFile);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            this.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        this.startActivityForResult(takePictureIntent, CAMERA_CODE); // IMAGE_CAPTURE = 0
    }

    private void getImageFromGallray(boolean isCompletion) {
//        if(isCompletion){
//            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(galleryIntent, ATTACHFILE_CODE);
//        }else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            this.startActivityForResult(galleryIntent, CAPTURE_IMAGE_GALLARY);
//        }


    }
////After discussion with Shubham and Jit Sir hide attachment all code 19/12/23
//    private void takeimageFromGalary() {
//        //allow upload file extension
//        String[] mimeTypes = {"image/jpeg", "image/jpg", "image/png",
//                "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",//.doc & .docx
//                "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",//.xls & .xlsx
//                "application/pdf",//pdf
//                "text/csv", "text/plain"//csv
//        };
//
//        /* *only for document uploading */
//        Intent documentIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        documentIntent.addCategory(Intent.CATEGORY_OPENABLE);
//        documentIntent.setType("*/*");
//        documentIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//        startActivityForResult(documentIntent, ATTACHFILE_CODE);
//    }

    private File createImageFile() throws IOException {
        Calendar calendar = Calendar.getInstance();
        long imageFileName = calendar.getTime().getTime();
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Eot Directory");

        File directoryPath = new File(storageDir.getPath());
        File image = File.createTempFile(
                String.valueOf(imageFileName),  /* prefix */
                ".jpg",         /* suffix */
                directoryPath   /* directory */
        );
        captureImagePath = image.getAbsolutePath();
        App_preference.getSharedprefInstance().setCapturePath(captureImagePath);
        return new File(image.getPath());
    }


    @Override
    public void updateDetailJob(String note) {
        Intent intent = new Intent();
        if (jobData != null) jobData.setComplNote(note);
        intent.putExtra("note", note);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DOUCMENT_UPLOAD_CODE:
                if (resultCode == RESULT_OK)
                    if (doc_attch_pi != null){
                        String fileNameExt = AppUtility.getFileNameWithExtension(data.getStringExtra("imgPath"));
                        String bitmapString = "";
                        if (data.getBooleanExtra("isFileImage", false)) {
                            Bitmap bitmap = AppUtility.getBitmapFromPath(data.getStringExtra("imgPath"));
                            bitmapString = AppUtility.BitMapToString(bitmap);
                        }
                        Attachments attachments = new Attachments("TempAttach-"+data.getStringExtra("fileName"),fileNameExt,fileNameExt,data.getStringExtra("imgPath"),queId, jtId,"",jobData.getJobId(),"6",bitmapString);
                        AppDataBase.getInMemoryDatabase(this).attachments_dao().insertSingleAttachments(attachments);
//                        Attachments obj = new Attachments("0", fileNameExt, fileNameExt, bitmapString);
//                        ArrayList<Attachments> updateList = new ArrayList<>();
//
//                        if (fileList_res != null) {
//                            updateList.addAll(fileList_res);
//                        }
//                        updateList.add(obj);
                        String isAttach = data.getStringExtra("isAttach");
                        isFileImage = data.getBooleanExtra("isFileImage", false);
                        onDocumentSelected("","",false,parentPositon, position,this.queId, this.jtId);
                        if (isAttach.equals("1") && isFileImage) {
                            CompletionFormAdapter.DefaultViewHolder defaultViewHolderr = (CompletionFormAdapter.DefaultViewHolder) completionRecyclerView.findViewHolderForAdapterPosition(competionNotesPostion);
                            if(data.getStringExtra("desc") != null && !data.getStringExtra("desc").isEmpty()) {
                               try {
                                   if (defaultViewHolderr.compedt != null) {
                                       desc.append(defaultViewHolderr.compedt.getText().toString().trim());
                                       defaultViewHolderr.compedt.getText().clear();
                                       if (!TextUtils.isEmpty(desc))
                                           desc.append(System.lineSeparator());

                                       if (data.getStringExtra("desc") != null)
                                           desc.append(data.getStringExtra("desc"));
                                       defaultViewHolderr.compedt.setText(desc);
                                       String tempnotes = desc.toString().replace("null", "");
                                       defaultViewHolderr.compedt.setText(tempnotes);
                                       defaultViewHolderr.compedt.setSelection(defaultViewHolderr.compedt.getText().toString().length());
                                       desc.setLength(0);
                                   }
                               }catch (Exception e){
                                   Log.e("Exception",e.getMessage());
                                    for (Object item :
                                         allItem) {
                                        if(item instanceof QuesRspncModel){
                                            if(((QuesRspncModel) item).getType().equals("")){

                                                desc.append(((QuesRspncModel) item).getAns().get(0).getValue().toString().trim());
                                                if (!TextUtils.isEmpty(desc))
                                                    desc.append(System.lineSeparator());

                                                if (data.getStringExtra("desc") != null)
                                                    desc.append(data.getStringExtra("desc"));

                                                String tempnotes = desc.toString().replace("null", "");
                                                ((QuesRspncModel) item).getAns().get(0).setValue(tempnotes);
                                                desc.setLength(0);
                                            }
                                        }
                                    }
                                }
                            }
//                            setList(updateList, "",true);
                        }
                        if (data.getStringExtra("fileName") != null) {
                            try {
//                                doc_attch_pi.uploadDocuments(jobId, data.getStringExtra("imgPath"),
//                                        data.getStringExtra("fileName"),
//                                        data.getStringExtra("desc"),
//                                        data.getStringExtra("type"),
//                                        data.getStringExtra("isAttach"));
                                OfflineDataController.getInstance().addInOfflineDB(Service_apis.upload_document, AppUtility.getParam(jobId, this.queId, this.jtId,data.getStringExtra("imgPath"),
                                        data.getStringExtra("fileName"),
                                        data.getStringExtra("desc"),
                                        data.getStringExtra("type"),
                                        data.getStringExtra("isAttach"), true, false,this.parentPositon, this.position), AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                            } catch (Exception e) {
                                AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","onActivityResult()"+e.getMessage(),"JobCompletionActivity","");
//                                if (updateList.size() == 1) {
//                                    fileList_res.remove(updateList.get(0));
//                                    setList(fileList_res, "",true);
//                                }
                                e.printStackTrace();
                            }
                        }
                    }
                break;
            case CAMERA_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                            File file = AppUtility.scaleToActualAspectRatio(App_preference.getSharedprefInstance().getCapturePath(), 1024f, 1024f);
                            if (file != null) {
                                imageEditing(Uri.fromFile(file), true);
                            }
                    } catch (Exception e) {
                        AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","onActivityResult()-->CAMERA_CODE"+e.getMessage(),"JobCompletionActivity","");
                        e.printStackTrace();
                    }
                } else {
                    return;
                }
                break;
            case CAPTURE_IMAGE_GALLARY:
                if (resultCode == RESULT_OK) {
                    try {
                        boolean isMultipleImages = false;

                        Uri galreyImguriUri = data.getClipData().getItemAt(0).getUri();
                        //  String gallery_image_Path = PathUtils.getPath(getActivity(), galreyImguriUri);
                        String gallery_image_Path = PathUtils.getRealPath(this, galreyImguriUri);
                        String img_extension = gallery_image_Path.substring(gallery_image_Path.lastIndexOf("."));
                        if(data.getClipData().getItemCount()>1){
                            isMultipleImages = true;
                        }else {
                            isMultipleImages = false;
                        }
                        //('jpg','png','jpeg','pdf','doc','docx','xlsx','csv','xls'); supporting extensions
                        if (img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")) {
                            if(!isMultipleImages) {
                                imageEditing(data.getClipData().getItemAt(0).getUri(), true);
                            }
                            else {
                                uploadMultipleImges(data,true, jobId, this.queId, this.jtId);
                            }
                        } else {
                            if(!isMultipleImages) {
                                uploadFileDialog(gallery_image_Path);
                            }
                            else {
                                uploadMultipleImges(data,false, jobId, this.queId, this.jtId);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    return;
                }
                break;
            ////After discussion with Shubham and Jit Sir hide attachment all code 19/12/23
            case ATTACHFILE_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri galreyImguriUri = data.getData();
                        String gallery_image_Path = PathUtils.getRealPath(this, galreyImguriUri);
                        String img_extension = gallery_image_Path.substring(gallery_image_Path.lastIndexOf("."));
                        //('jpg','png','jpeg','pdf','doc','docx','xlsx','csv','xls'); supporting extensions
                        if (img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")) {
                            imageEditing(data.getData(), true);
                        } else {
                            String filename = gallery_image_Path.substring(gallery_image_Path.lastIndexOf("/") + 1);
                            onDocumentSelected(gallery_image_Path, filename, false,parentPositon, position, this.queId, this.jtId);
                        }
                    } catch (Exception e) {
                        AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","onActivityResult()-->ATTACHFILE_CODE"+e.getMessage(),"JobCompletionActivity","");
                        e.printStackTrace();
                    }
                } else {
                    return;
                }
                break;
            case PHOTO_EDIT_CODE:
                Log.v("Image File Name ", "Activity Result :::::");
                if (data != null && data.hasExtra("path")) {
                    String path = data.getStringExtra("path");
                    String name = data.getStringExtra("name");
                    onDocumentSelected(path, name, true,parentPositon, position, this.queId, this.jtId);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //upload image edit highlighting feature for image
    private void imageEditing(Uri uri, boolean isImage) {
        try {
//            if(isCompletion){
//                Intent intent = new Intent(this, ActivityEditImageDialog.class);
//                intent.putExtra("uri", uri);
//                intent.putExtra("allowOffline", true);
//                startActivityForResult(intent, PHOTO_EDIT_CODE);
//            }
//            else {
                Intent intent = new Intent(this, ActivityDocumentSaveUpload.class);
                intent.putExtra("uri", uri);
                intent.putExtra("isImage", true);
                intent.putExtra("jobid", jobId);
                intent.putExtra("SAVEASCOMPLETION", true);
                this.startActivityForResult(intent, DOUCMENT_UPLOAD_CODE);
//            }
        } catch (Exception e) {
            AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","imageEditing()"+e.getMessage(),"JobCompletionActivity","");
            e.printStackTrace();
        }
    }

    //upload file dialog
    private void uploadFileDialog(final String selectedFilePath) {
        Intent intent = new Intent(this, ActivityDocumentSaveUpload.class);
        intent.putExtra("uri", selectedFilePath);
        intent.putExtra("isImage", false);
        intent.putExtra("jobid", jobId);
        intent.putExtra("SAVEASCOMPLETION", true);
        this.startActivityForResult(intent, DOUCMENT_UPLOAD_CODE);
    }

    public void setUpdatedDesc(String desc, String queId, String jtId) {
        if(doc_attch_pi != null) {
            doc_attch_pi.getMultiAttachFileList(jobId, App_preference.getSharedprefInstance().getLoginRes().getUsrId(), "6",true,-1,-1,queId,jtId);
        }
//        StringBuffer data = new StringBuffer(compedt.getText().toString().trim());
//        if (!TextUtils.isEmpty(desc))
//            data.append(System.lineSeparator());
//        data.append(desc);
////        compedt.setText(data);
//        compedt.setText(AppUtility.html2text(data.toString()));
//        data.setLength(0);
    }

    public Map<String,String> setUpdatedDesc(EditText compedt,int type) {
        HashMap<String,String> value = new HashMap<>();
        String s;
        int pos = 0;
        int cursorpostion=compedt.getSelectionEnd();
        CharSequence enteredText = compedt.getText().toString();
        CharSequence cursorToStart=  enteredText.subSequence(0,cursorpostion);
        CharSequence cursorToEnd = enteredText.subSequence(cursorpostion, enteredText.length());
        StringBuffer stringBuffer=new StringBuffer(cursorToStart);
        if (type == 1)
            stringBuffer.append(" "+"\u2705");
        else if(type==2)
            stringBuffer.append(" "+"\u274c");
        else if (type == 3
                &&  jobData.getDesWithoutHtml() != null
                && !jobData.getDesWithoutHtml().isEmpty()){
            Spanned spannedText = Html.fromHtml(getJobData().getDesWithoutHtml());
            String plainText = TextUtils.replace(spannedText, new String[] {"<br>"}, new CharSequence[] {"\n"}).toString();
            /*  s=AppUtility.html2text(getJobData().getDes());*/
            stringBuffer.append(" " + plainText);
        } else if (type==4)
            stringBuffer.append(" "+AppUtility.getCurrentDateByFormats(AppConstant.DATE_FORMAT+" hh:mm a"));

        StringBuffer finalstring = stringBuffer.append(cursorToEnd);
        compedt.setText(finalstring);
        value.put("Data", finalstring.toString());

        //setting cursor possition
        if (type==1||type==2) {
            compedt.setSelection(cursorpostion+2);
            pos = cursorpostion+2;
        } else if(type==3){
            s=AppUtility.html2text(jobData.getDes());
            if(s.isEmpty()) {
                compedt.setSelection(cursorpostion + s.length());
                pos = cursorpostion + s.length();
            }
            else {
                compedt.setSelection(cursorpostion + s.length() + 1);
                pos = cursorpostion+s.length()+1;
            }
        }else if (type==4)
        {
            String time=AppUtility.getCurrentDateByFormats(AppConstant.DATE_FORMAT+" hh:mm a");
            compedt.setSelection(cursorpostion+time.length()+1);
            pos = cursorpostion+time.length()+1;
        }
        stringBuffer.setLength(0);
        value.put("pos",String.valueOf(pos));
        return value;

    }

    private void uploadMultipleImges(Intent data,boolean imgPath,String jobId, String queId, String jtId){
        String [] imgPathArray = new String[data.getClipData().getItemCount()];
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(()->{
            for(int i =0; i<data.getClipData().getItemCount();i++) {
                Uri uri = data.getClipData().getItemAt(i).getUri();
                String fileNameExt = AppUtility.getFileNameWithExtension(PathUtils.getRealPath(this, uri));
                String[] fileName = fileNameExt.split("\\.");
                imgPathArray[i] = PathUtils.getRealPath(this, uri);
//                Bitmap bitmap = AppUtility.getBitmapFromPath(PathUtils.getRealPath(this, uri));
//                String bitmapString = AppUtility.BitMapToString(bitmap);
//                Attachments obj=new Attachments("0",fileNameExt,fileNameExt,bitmap);
//                ArrayList<Attachments> getFileList_res =new ArrayList<>();
//                if (fileList_res != null) {
//                    getFileList_res.clear();
//                    getFileList_res.addAll(fileList_res);
//                }
//                getFileList_res.add(obj);
                Attachments attachments = new Attachments("TempAttach-"+fileName[0],fileNameExt,fileNameExt,imgPathArray[i],queId, jtId,"",jobData.getJobId(),"6");
                AppDataBase.getInMemoryDatabase(this).attachments_dao().insertSingleAttachments(attachments);

                new Handler(Looper.getMainLooper()).post(()->{
                    onDocumentSelected("","",false,parentPositon,position,queId,jtId);
                });
            }
            new Handler(Looper.getMainLooper()).post(()->{
                uploadOffline(imgPathArray,true,false,jobId,queId,jtId);
            });

        });



    }

    public void uploadOffline(String[] imgPathArray,boolean imgPath, boolean isCompNotes, String jobId, String queId, String jtId){
        Data.Builder builder = new Data.Builder();
        builder.putStringArray("imgPathArray",imgPathArray);
        builder.putBoolean("imgPath",true);
        builder.putBoolean("isCompNotes",true);
        builder.putString("jobId",jobId);
        builder.putString("queId",queId);
        builder.putString("jtId",jtId);
        builder.putBoolean("isAttachmentSection",false);
        builder.putInt("parentPosition",parentPositon);
        builder.putInt("position",position);

        mWorkManager = WorkManager.getInstance(this);

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadMultiImgWorker.class)
                .setInputData(builder.build())
                .build();
        mWorkManager.enqueue(request);

        mWorkManager.getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if(workInfo.equals(WorkInfo.State.SUCCEEDED)){
                    Log.d("Worker", "==================== success");
                }
            }
        });
    }

    @Override
    public void updateMultiDoc(String apiName, String jobId, int parentPositon, int position, String queId, String jtId) {
        switch (apiName) {
            case Service_apis.upload_document:
                if(doc_attch_pi != null) {
                    doc_attch_pi.getMultiAttachFileList(jobId, App_preference.getSharedprefInstance().getLoginRes().getUsrId(), "6",true, parentPositon, position,queId, jtId);
                }
                break;
        }
    }
    public void selectFile(int position, ImageView attchmentView, ImageView deleteAttchment, Button addAttchment, int parentPostion) {//, ImageView attchmentView, ImageView item_View
        this.deleteAttchment = deleteAttchment;
        this.position = position;
        this.attchmentView = attchmentView;
        this.addAttchment = addAttchment;
        this.parentPositon = parentPostion;


        //parent class function in UploadDocumentActivity called for image and attachment selection
        selectFilesForCompletion(true);

    }
    public void addAttachment(int position, int parentPositon, String queId, String jtId){
        this.position = position;
        this.parentPositon = parentPositon;
        this.queId = queId;
        this.jtId = jtId;
        openAttachmentDialog();
    }


    public void onClickContinuarEvent(Uri uri) {
        String path = "";
        //    path = PathUtils.getPath(this, uri);
        path = PathUtils.getRealPath(this, uri);
        if (!path.isEmpty()) {

            CompletionFormAdapter.SingleViewHolder singleViewHolder = (CompletionFormAdapter.SingleViewHolder) completionRecyclerView.findViewHolderForAdapterPosition(position);
            singleViewHolder.showAttchmentView(position, path, attchmentView, deleteAttchment, addAttchment);//, attchmentView,deleteAttchment
        }
    }

    public void onDocumentSelected(String path, String name, boolean isImage, int parentPositon, int position, String queId, String jtId) {
        Log.e("AdapterPostion",""+position);
        this.parentPositon = parentPositon;
        this.position = position;

        if(isShowingImg || showOnlyImg){
            hideView(isShowingImg,jobData.getJobId(),queId,jtId,this.position,this.parentPositon);
        }
        if(parentPositon != -1 || position != -1 ) {
            if (completionFormAdapter != null && allItem.size()>0) {
                if (allItem.get(this.parentPositon) instanceof QuesRspncModel) {
                    CompletionFormAdapter.SingleViewHolder singleViewHolder = (CompletionFormAdapter.SingleViewHolder) completionRecyclerView.findViewHolderForAdapterPosition(this.parentPositon);
                    if (singleViewHolder != null) {
                        singleViewHolder.jobCompletionAdpter.updateFileList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsById(jobData.getJobId(), queId, jtId));
                    }
//                singleViewHolder.showAttchmentView(position, path, attchmentView, deleteAttchment, addAttchment);//, attchmentView,deleteAttchment
                } else if (allItem.get(this.parentPositon) instanceof List) {
                    CompletionFormAdapter.GroupedViewHolder groupedViewHolder = (CompletionFormAdapter.GroupedViewHolder) completionRecyclerView.findViewHolderForAdapterPosition(this.parentPositon);
//                groupedViewHolder.questionListAdapter.showAttchmentView(position,path,attchmentView,deleteAttchment,addAttchment);
                    if (groupedViewHolder != null) {
                        QuestionListAdapter.ViewHolder viewHolder = (QuestionListAdapter.ViewHolder) groupedViewHolder.rv_groupItem.findViewHolderForAdapterPosition(this.position);
                        viewHolder.jobCompletionAdpter.updateFileList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsById(jobData.getJobId(), queId, jtId));
                    }
                }
            }
        }

        this.isCompletion = false;
    }

    public void ansAnsQuesRspncModel(List<QuesRspncModel> quesRspncModelList) {

//        signAns.clear();
//        docAns.clear();
//        dosanspath.clear();
//        signanspath.clear();
//        signQueIdArray.clear();
//        docQueIdArray.clear();
        for (int i = 0; i < quesRspncModelList.size(); i++) {
            String key = "";
            String ans = "";
            ArrayList<AnswerModel> ansArrayList = new ArrayList<>();
            Answer answer = null;
            switch (quesRspncModelList.get(i).getType()) {

                case "11":
                    if (quesRspncModelList.get(i).getAns().size() > 0) {
                        ans = quesRspncModelList.get(i).getAns().get(0).getValue();
                        if (quesRspncModelList.get(i).getMandatory().equals("1"))
                            if (TextUtils.isEmpty(ans))
                                isMandatoryNotFill = true;

                        String mimeType = "";
                        MultipartBody.Part body = null;
                        File file = new File(ans);

                        if (file != null && file.exists()) {
                            mimeType = URLConnection.guessContentTypeFromName(file.getName());
                            if (mimeType == null) {
                                mimeType = file.getName();
                            }
                            RequestBody requestFile = RequestBody.create(file, MediaType.parse(mimeType));
                            // MultipartBody.Part is used to send also the actual file name
                            body = MultipartBody.Part.createFormData("docAns[]", file.getName()
                                    , requestFile);//ans.substring(ans.lastIndexOf(".")
                            docanspath.add(ans);
                            docAns.add(body);
                            docQueIdArray.add(new Answer(quesRspncModelList.get(i).getQueId(),quesRspncModelList.get(i).getJtId()));

                            AnswerModel answerModels = new AnswerModel("0", ans);
                            ansArrayList.add(answerModels);
                            answer = new Answer(quesRspncModelList.get(i).getJtId(),quesRspncModelList.get(i).getQueId(),
                                    quesRspncModelList.get(i).getType(), ansArrayList);
                            answerArrayList.add(answer);

                        } else {
//                            AnswerModel answerModels = new AnswerModel("0", "");
//                            ansArrayList.add(answerModels);
//                            answer = new Answer(this.quesRspncModelList.get(i).getQueId(),
//                                    this.quesRspncModelList.get(i).getType(), ansArrayList,
//                                    this.quesRspncModelList.get(i).getFrmId());
//                            answerArrayList.add(answer);
                        }

                    } else if (quesRspncModelList.get(i).getAns().size() == 0)
                        if (quesRspncModelList.get(i).getMandatory().equals("1"))
                            isMandatoryNotFill = true;
                    break;
//                **case for Signature***
                case "10":
                    Log.e("", "");
                    if (quesRspncModelList.get(i).getAns().size() > 0) {
                        ans = quesRspncModelList.get(i).getAns().get(0).getValue();
                        if (quesRspncModelList.get(i).getMandatory().equals("1"))
                            if (TextUtils.isEmpty(ans))
                                isMandatoryNotFill = true;

                        String mimeType = "";
                        MultipartBody.Part body = null;
                        File file = new File(ans);

                        if (file != null && file.exists()) {
                            mimeType = URLConnection.guessContentTypeFromName(file.getName());
                            if (mimeType == null) {
                                mimeType = file.getName();
                            }
                            RequestBody requestFile = RequestBody.create(file,MediaType.parse(mimeType));
                            // MultipartBody.Part is used to send also the actual file name
                            body = MultipartBody.Part.createFormData("signAns[]", file.getName()
                                    , requestFile);//ans.substring(ans.lastIndexOf(".")
                            signanspath.add(ans);
                            signAns.add(body);
                            signQueIdArray.add(new Answer(quesRspncModelList.get(i).getQueId(),quesRspncModelList.get(i).getJtId()));

                            AnswerModel docanswerModels = new AnswerModel("0", ans);
                            ansArrayList.add(docanswerModels);
                            answer = new Answer(quesRspncModelList.get(i).getJtId(),quesRspncModelList.get(i).getQueId(),
                                    quesRspncModelList.get(i).getType(), ansArrayList);
                            answerArrayList.add(answer);
                        } else {
//                            AnswerModel answerModels = new AnswerModel("0", "");
//                            ansArrayList.add(answerModels);
//                            answer = new Answer(this.quesRspncModelList.get(i).getQueId(),
//                                    this.quesRspncModelList.get(i).getType(), ansArrayList,
//                                    this.quesRspncModelList.get(i).getFrmId());
//                            answerArrayList.add(answer);
                        }
                    } else if (quesRspncModelList.get(i).getAns().size() == 0)
                        if (quesRspncModelList.get(i).getMandatory().equals("1"))
                            isMandatoryNotFill = true;

                    break;

                case "8":
                    if (quesRspncModelList.get(i).getAns() != null && quesRspncModelList.get(i).getAns().size() > 0) {
                        ans = quesRspncModelList.get(i).getAns().get(0).getValue();
                        AnswerModel answerModel = new AnswerModel(quesRspncModelList.get(i).getAns().get(0).getKey(), quesRspncModelList.get(i).getAns().get(0).getValue());
                        ansArrayList.add(answerModel);
                        answer = new Answer(quesRspncModelList.get(i).getJtId(),quesRspncModelList.get(i).getQueId(), quesRspncModelList.get(i).getType(), ansArrayList);
                        answerArrayList.add(answer);
                    }

                    if (quesRspncModelList.get(i).getMandatory().equals("1"))
                        if (ans.equals("0"))
                            isMandatoryNotFill = true;
                    break;
                case "2":
                case "5":
                case "6":
                case "7":
                case "1":
                case "12":
                    if (quesRspncModelList.get(i).getAns() != null && quesRspncModelList.get(i).getAns().size() > 0) {
                        if (quesRspncModelList.get(i).getType().equals("5")) {
                            if (!TextUtils.isEmpty(quesRspncModelList.get(i).getAns().get(0).getValue())) {
                                long l = Long.parseLong(quesRspncModelList.get(i).getAns().get(0).getValue());
                                String date = AppUtility.getDates(l, "yyyy-MMM-dd hh:mm:ss");
                                ans = date;
                            }
                        } else if (quesRspncModelList.get(i).getType().equals("6")) {
                            if (!TextUtils.isEmpty(quesRspncModelList.get(i).getAns().get(0).getValue())) {
                                long l = Long.parseLong(quesRspncModelList.get(i).getAns().get(0).getValue());
                                String date = AppUtility.getDates(l,
                                        AppUtility.dateTimeByAmPmFormate("hh:mm a", "HH:mm"));
                                ans = date;
                            }
                        } else if (quesRspncModelList.get(i).getType().equals("7")) {
                            if (!TextUtils.isEmpty(quesRspncModelList.get(i).getAns().get(0).getValue())) {
                                long l = Long.parseLong(quesRspncModelList.get(i).getAns().get(0).getValue());
                                String date = AppUtility.getDates(l, AppUtility.dateTimeByAmPmFormate(
                                        "dd-MMM-yyyy hh:mm a","dd-MMM-yyyy HH:mm"));
                                ans = date;
                            }
                        } else
                            ans = quesRspncModelList.get(i).getAns().get(0).getValue();
                        if (quesRspncModelList.get(i).getMandatory().equals("1"))
                            if (TextUtils.isEmpty(ans))
                                isMandatoryNotFill = true;

                        AnswerModel answerModel = new AnswerModel(key, ans);
                        ansArrayList.add(answerModel);
                        answer = new Answer(quesRspncModelList.get(i).getJtId(),quesRspncModelList.get(i).getQueId(),
                                quesRspncModelList.get(i).getType(), ansArrayList);
                        answerArrayList.add(answer);

                    } else if (quesRspncModelList.get(i).getMandatory().equals("1")) {
                        isMandatoryNotFill = true;
                    }
                    break;
                case "4":
                case "3":
                    if (quesRspncModelList.get(i).getAns() != null && quesRspncModelList.get(i).getAns().size() > 0) {
                        List<AnswerModel> ans1 = quesRspncModelList.get(i).getAns();
                        if (ans1 != null)
                            for (AnswerModel am : ans1) {
                                    key = am.getKey();
                                    ans = am.getValue();
                                    if(!ans.isEmpty()) {
                                        AnswerModel answerModel = new AnswerModel(key, ans);
                                        ansArrayList.add(answerModel);
                                    }
                                    if (quesRspncModelList.get(i).getMandatory().equals("1"))
                                        if (TextUtils.isEmpty(ans))
                                            isMandatoryNotFill = true;
                            }
                    }
                    if (ansArrayList.size() > 0) {
                        answer = new Answer(quesRspncModelList.get(i).getJtId(),quesRspncModelList.get(i).getQueId(),
                                quesRspncModelList.get(i).getType(), ansArrayList);
                        answerArrayList.add(answer);
                    } else if (quesRspncModelList.get(i).getMandatory().equals("1")) {
                        isMandatoryNotFill = true;
                    } else {
                        answer = new Answer(quesRspncModelList.get(i).getJtId(),quesRspncModelList.get(i).getQueId(),
                                quesRspncModelList.get(i).getType(), ansArrayList);
                        answerArrayList.add(answer);
                    }
                    break;
            }
        }
    }
    private void emptyAnsFieldError() {
        AppUtility.alertDialog(this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.enter_ans), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return null;
            }
        });
    }
    private boolean emptyCheckFormValidation() {
        boolean isChangeDetected = false;
        if (completionFormAdapter != null && completionFormAdapter.getTypeList() != null) {
            ArrayList<QuesRspncModel> checkList = completionFormAdapter.getTypeList();
            for (QuesRspncModel qm : checkList) {
                List<AnswerModel> ans = qm.getAns();
                if (ans != null && ans.size() > 0) {
                    AnswerModel model = ans.get(0);
                    if (model != null && !TextUtils.isEmpty(model.getValue()))
                        isChangeDetected = true;
                }
            }
        }
        if(complNotes != null && !complNotes.isEmpty()){
            isChangeDetected = true;
        }
        return isChangeDetected;
    }

    @Override
    public void hideView(boolean hide,String jobId, String queId, String jtId, int posiotion, int parentPostion) {
        isShowingImg = hide;
        this.queId = queId;
        this.jtId = jtId;
        this.position = posiotion;
        this.parentPositon = parentPostion;
        ll_attachment.setVisibility(View.VISIBLE);
        ll_completionForm.setVisibility(View.GONE);
        if(showOnlyImg){
            jobCompletionAdpter.updateFileList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsByJobId(jobId), true, queId, jtId);
        }
        else {
            jobCompletionAdpter.updateFileList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(this).attachments_dao().getAttachmentsByQueId(jobId, queId), true, queId, jtId);
        }

    }

    @Override
    public void onBackPressed() {
        if(isShowingImg){
            ll_attachment.setVisibility(View.GONE);
            ll_completionForm.setVisibility(View.VISIBLE);
            isShowingImg = false;
        }else {
            AppUtility.hideSoftKeyboard(this);
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void setMarkAsDoneService(String jtid, String status) {
        for (IsMarkDoneWithJtid item : isMarkDoneList
        ) {
            if (item.getJtId().equals(jtid)) {
                item.setStatus(status);
                break;
            }
        }
    }
    public void setServiceMarkasDone(List<QuesRspncModel> list){

            for (IsMarkDoneWithJtid item : isMarkDoneList
            ) {
                if(list.size()>0) {
                    if (list.get(0).getJtId().equals(item.getJtId())) {
                        list.get(0).setIsMarkAsDone(item.getStatus());
                    }
                }

        }
    }

    @Override
    public void upateForCompletion(String apiName, String jobId) {

    }
    public void showErrorDialog(String msg) {
        AppUtility.error_Alert_Dialog(this, msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok)
                , () -> null);
    }
    public void setCompletionNotesPosition(int i){
        this.competionNotesPostion = i;
    }
}