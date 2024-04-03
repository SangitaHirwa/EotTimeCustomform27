package com.eot_app.nav_menu.jobs.job_detail.detail;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.eot_app.R;
import com.eot_app.databinding.DialogJobCardBinding;
import com.eot_app.nav_menu.jobs.job_detail.detail.adapter.JobCardAttachmentAdapter;
import com.eot_app.nav_menu.jobs.job_detail.detail.job_detail_presenter.JobDetail_pi;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.JobCardAttachmentModel;
import com.eot_app.nav_menu.jobs.job_detail.documents.ActivityDocumentSaveUpload;
import com.eot_app.nav_menu.jobs.job_detail.documents.PathUtils;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_Pc;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_Pi;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_View;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.Invoice_Email_Activity;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.Invoice_Email_View;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.Invoice_Email_pc;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.Invoice_Email_pi;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.Get_Email_Message_Res_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.Get_Email_ReS_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.InvoiceEmaliTemplate;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.send_email_temp_model.Send_Email_ReS_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.itemlist_model.InvoiceItemDetailsModel;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.itemlist_mvp.ItemList_PI;
import com.eot_app.nav_menu.jobs.job_detail.job_detail_activity_presenter.Job_Detail_Activity_pi;
import com.eot_app.nav_menu.quote.quote_invoice_pkg.quote_mvp_pkg.Quo_Invo_Pi;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.settings.setting_db.FieldWorker;
import com.eot_app.utility.util_interfaces.MySpinnerAdapter;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class DialogJobCardDocuments extends DialogFragment
        implements View.OnClickListener, Spinner.OnItemSelectedListener, Invoice_Email_View, JobCardAttachmentAdapter.Listener, Doc_Attch_View {

    Context activity;
    DialogJobCardBinding binding;
    Job_Detail_Activity_pi detail_activity_pi;
    private ItemList_PI itemListPi;
    Quo_Invo_Pi quo_invo_pi;
    String jobId = "";
    String tempId = "";
    String fwId = "";
    String[] fwList;
    ArrayList<InvoiceEmaliTemplate> templateList;
    String[] kprList;
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
    String quotId;
    private InvoiceItemDetailsModel invoice_Details;
    private Invoice_Email_pi invoice_email_pi;
    private JobDetail_pi jobDetail_pi;
    private JobCardAttachmentAdapter jobCardAttachmentAdapter;
    List<JobCardAttachmentModel> reqAttachmentList  = new ArrayList<>();
    List<JobCardAttachmentModel> list  = new ArrayList<>();
    private ArrayList<Attachments> fileList_res = new ArrayList<>();
    Doc_Attch_Pi doc_attch_pi;
    private final int ATTACHFILE_CODE = 101;
    private static final int DOUCMENT_UPLOAD_CODE = 156;
    public void setdetail_activity_pi(Job_Detail_Activity_pi detail_activity_pi) {
        this.detail_activity_pi = detail_activity_pi;
    }
    public void setitemListPi(ItemList_PI itemListPi) {
        this.itemListPi = itemListPi;
    }
    public void setinvoice_Details(InvoiceItemDetailsModel invoice_Details) {
        this.invoice_Details = invoice_Details;
    }
    public void setFwList(String[] fwList2) {
        this.fwList = fwList2;
        Log.e("fwList set",new Gson().toJson(fwList));
    }
    public void setquo_invo_pi(Quo_Invo_Pi quo_invo_pi) {
        this.quo_invo_pi=quo_invo_pi;
    }
    public void setInvoiceTmpList(ArrayList<InvoiceEmaliTemplate> templateList) {
        this.templateList = templateList;
        Log.e("TemplateData::", "setList:"+new Gson().toJson(this.templateList));
        if (templateList != null && templateList.size() > 0) {
            for (InvoiceEmaliTemplate model : templateList) {
                if (model.getDefaultTemp() != null && model.getDefaultTemp().equals("1")) {
                    tempId = model.getInvTempId();
                    break;
                }
            }
        }
        AppUtility.progressBarDissMiss();
    }

    @Override
    public void setChatDataList(Get_Email_Message_Res_Model chatDataList) {

    }

    public void setQuoteId(String quoteId) {
        this.quotId = quoteId;

    }

    public void setContext(Context activity) {
        this.activity = activity;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        invoice_email_pi = new Invoice_Email_pc(this, this.getContext());
//        jobDetail_pi = new JobDetail_pc(this);
        doc_attch_pi = new Doc_Attch_Pc(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_job_card, container, false);
        View view = binding.getRoot();
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Window window = getDialog().getWindow();
//        if(window == null) return;
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.height = 1000;
//        window.setAttributes(params);
        AppUtility.progressBarDissMiss();
    }

    @Override
    public void onPause() {
        super.onPause();
        AppUtility.progressBarDissMiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppUtility.progressBarDissMiss();
    }

    void initViews() {


        // Log.e("fwList",new Gson().toJson(fwList));

        if (fwList != null && fwList.length > 0) {
            kprList = new String[fwList.length];
            AppUtility.spinnerPopUpWindow(binding.signatureDp);
            int pos = 0;
            for (String id : fwList) {
                FieldWorker fieldWorker = AppDataBase.getInMemoryDatabase(getActivity()).fieldWorkerModel().getFieldWorkerByID(id);
                if(fieldWorker!=null)
                {
                    kprList[pos]=fieldWorker.getName()+" "+fieldWorker.getLnm();
                    if (pos == 0) {
                        setFwStatus(pos);
                    }
                }
                pos++;
            }
            binding.signatureDp.setAdapter(new MySpinnerAdapter(activity, kprList));
        }

        if (templateList != null && templateList.size() > 0) {
            binding.templateView.setVisibility(View.VISIBLE);
            AppUtility.spinnerPopUpWindow(binding.templatDp);
            String[] statusList = new String[templateList.size()];
            int pos = 0;
            for (InvoiceEmaliTemplate status : templateList) {
                statusList[pos] = status.getInputValue();
                pos++;
            }
            binding.templatDp.setAdapter(new MySpinnerAdapter(activity, statusList));
            for (int i = 0; i < templateList.size(); i++) {
                if (templateList.get(i).getDefaultTemp().equalsIgnoreCase("1"))
                    setEquStatus(i);
            }
        }
        else {
            binding.templateView.setVisibility(View.GONE);
        }


        binding.buttonSendEmail.setOnClickListener(this);
        binding.buttonView.setOnClickListener(this);
        binding.linearLayoutSignature.setOnClickListener(this);
        binding.linearLayoutTemplat.setOnClickListener(this);
        binding.templatDp.setOnItemSelectedListener(this);
        binding.signatureDp.setOnItemSelectedListener(this);

        binding.hintTemplateTxt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.select_template));
        binding.hintSignatureTxt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.tech_sign));

        binding.templatTxt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.select_template));
        binding.signatureTxt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.tech_sign));

        binding.inputLayoutEmailTo.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.to) + " *");
        binding.inputLayoutEmailCc.setHint(LanguageController.getInstance().getMobileMsgByKey(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cc)));
        binding.inputLayoutEmailSubject.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.subject) + " *");
        binding.inputLayoutEmailMessage.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.message) + " *");
        binding.txtLblAddAttachment.setOnClickListener(this);


        if (jobId != null&&!jobId.isEmpty()) {
            binding.buttonSendEmail.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.email_job_card));
            binding.buttonView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.print_job_card));
            binding.buttonSendEmail.setVisibility(View.VISIBLE);
            binding.llEmailType.setVisibility(View.VISIBLE);
        }
        else if(quotId!=null&&!quotId.isEmpty()){
            binding.buttonView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.print_quote));
            binding.buttonSendEmail.setVisibility(View.GONE);
            binding.llEmailType.setVisibility(View.GONE);
        }
        else {
            binding.buttonView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.print_invoice));
            binding.buttonSendEmail.setVisibility(View.GONE);
            binding.llEmailType.setVisibility(View.GONE);
        }

        setEmailData();

    }


    public void setData() {

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send_email:
                SpannableStringBuilder spnBuilder = new SpannableStringBuilder(binding.edtEmailMessage.getText());
                String spn = Html.toHtml(spnBuilder);
                Log.e("Email Text", ""+spn);
                if (jobId != null&&!jobId.isEmpty()) {
                    Intent intent = new Intent(activity, Invoice_Email_Activity.class);
                    intent.putExtra("jobId", jobId);
                    intent.putExtra("tempId", tempId);
                    intent.putExtra("fwid",fwId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    dismiss();
                }
                break;
            case R.id.button_view:
                dismiss();
                if (detail_activity_pi != null && jobId != null)
                    detail_activity_pi.printJobCard(jobId, tempId,fwId);
                else if (quo_invo_pi != null && quotId != null)
                    quo_invo_pi.generateQuotPDF(quotId,tempId);
                else if (itemListPi!=null&&invoice_Details != null) {
                    String isProformaInv = "0";
                    if (invoice_Details.getIsShowInList() != null && invoice_Details.getIsShowInList().equals("0"))
                        isProformaInv = "1";
                    else isProformaInv = "0";
               //     itemListPi.getGenerateInvoicePdf(invoice_Details.getInvId(), isProformaInv,tempId);
                }
                break;
            case R.id.linearLayout_templat:
                binding.templatDp.performClick();
                break;
            case R.id.linearLayout_signature:
                binding.signatureDp.performClick();
                break;
            case R.id.txt_lblAddAttachment:
                if (AppUtility.askGalaryTakeImagePermiSsion(getActivity())) {
                    takeimageFromGalary();//only for drive documents
                }
                else {
                    // Sdk version 33
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                        askTedPermission(2, AppConstant.galleryPermissions33);
                    }else {
                        askTedPermission(2, AppConstant.galleryPermissions);
                    }
                }
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.templat_Dp) {
            setEquStatus(position);
            setEmailData();
        }
        if (parent.getId() == R.id.signature_Dp) {
            setFwStatus(position);
        }
    }

    private void setFwStatus(int pos) {
        binding.hintSignatureTxt.setVisibility(View.VISIBLE);
        fwId = fwList[pos];
        String selectedValue = kprList[pos];
        binding.signatureTxt.setText(selectedValue);
    }

    private void setEquStatus(int pos) {
        binding.hintTemplateTxt.setVisibility(View.VISIBLE);
        tempId = templateList.get(pos).getInvTempId();
        String selectedValue = templateList.get(pos).getInputValue();
        binding.templatTxt.setText(selectedValue);
        if(templateList.get(pos).isTechSignature()&&jobId!=null){
            binding.signatureView.setVisibility(View.VISIBLE);
        }
        else {
            fwId="";
            binding.signatureView.setVisibility(View.GONE);
        }
    }
    private void  setEmailData(){
        if (jobId != null) {
            invoice_email_pi.getJobCardEmailTemplate(jobId,tempId,"");
//            jobDetail_pi.getAttachFileList(jobId, "","");
            doc_attch_pi.getAttachFileList(jobId, "", "",true, false);
            jobCardAttachmentAdapter = new JobCardAttachmentAdapter(this);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onGetEmailTempData(Get_Email_ReS_Model email_reS_model) {
        binding.inputLayoutEmailTo.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.to) + " *");
        binding.inputLayoutEmailCc.setHint(LanguageController.getInstance().getMobileMsgByKey(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cc)));
        binding.inputLayoutEmailSubject.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.subject) + " *");

        if (email_reS_model.getTo() != null && !email_reS_model.getTo().equals("")) {
            binding.edtEmailTo.setText(email_reS_model.getTo());
        }
        if (email_reS_model.getSubject() != null && !email_reS_model.getSubject().equals("")) {
            binding.edtEmailSubject.setText(email_reS_model.getSubject());
        }
        if (email_reS_model.getMessage() != null && !email_reS_model.getMessage().equals("")) {
            WebSettings mWebSettings = binding.eotEditor.getSettings();
            mWebSettings.setBuiltInZoomControls(true);
            binding.eotEditor.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            binding.eotEditor.setScrollbarFadingEnabled(false);
            binding.eotEditor.setHtml(email_reS_model.getMessage());
//            binding.edtEmailMessage.setText(email_reS_model.getMessage());
//            binding.edtEmailMessage.setText(Html.fromHtml(email_reS_model.getMessage()));
        }
//        this.email_reS_model = email_reS_model;
//
//        if (email_reS_model.getStripLink()!=null)
//            this.stripLink=email_reS_model.getStripLink();
    }

    @Override
    public void onSendInvoiceEmail(Send_Email_ReS_Model email_reS_model) {

    }

    @Override
    public void showErrorAlertDialog(String error) {

    }

    @Override
    public void setSessionExpire(String msg) {

    }

//    @Override
//    public void setButtonsUI(JobStatusModelNew jobstatus) {
//
//    }
//
//    @Override
//    public void setResultForChangeInJob(String update, String jobid) {
//
//    }
//
//    @Override
//    public void resetstatus(String status_no) {
//
//    }
//
//    @Override
//    public void setCustomFiledList(ArrayList<CustOmFormQuestionsRes> dataList) {
//
//    }
//
//    @Override
//    public void sessionExpire(String msg) {
//
//    }
//
//    @Override
//    public void setEuqipmentList(List<EquArrayModel> equArray) {
//
//    }
//
//    @Override
//    public void StopRecurPatternHide() {
//
//    }
//
//    @Override
//    public void setItemListByJob(List<InvoiceItemDataModel> itemList) {
//
//    }
//
//    @Override
//    public void setCompletionDetails(List<CompletionDetails> completionDetailsList) {
//
//    }

    @Override
    public void selectFiles() {

    }

    @Override
    public void selectFilesForCompletion(boolean isCompletion) {

    }

    @Override
    public void setList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes, boolean firstCall, boolean isOnline) {

        Log.e("Attachment List", ""+getFileList_res.size()+""+isAttachCompletionNotes);
        list.clear();
        this.fileList_res = getFileList_res;
        JobCardAttachmentModel jobCardAttachmentModel = new JobCardAttachmentModel("-1","2",LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_card)+".pdf",true);
        list.add(jobCardAttachmentModel);
        for (Attachments item : fileList_res) {
            final String ext = item.getImage_name().substring((item.getImage_name().lastIndexOf(".")) + 1).toLowerCase();
            String name ="";
            if(item.getAtt_docName()==null || item.getAtt_docName().isEmpty()){
                name = item.getAttachFileActualName();
            }else {
                name = item.getAtt_docName()+"."+ext;
            }
            list.add(new JobCardAttachmentModel(item.getAttachmentId(),"2",name,false));
        }
        jobCardAttachmentAdapter.updateList(list);
        binding.rvJobCardAttachment.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.rvJobCardAttachment.setAdapter(jobCardAttachmentAdapter);

    }

    @Override
    public void setMultiList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes, boolean firstCall, int parentPositon, int position, String queId, String jtId) {

    }

    @Override
    public void addNewItemToAttachmentList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes) {
        // remove the temporary added item for showing loader
        if (fileList_res != null&&!fileList_res.isEmpty()) {
            int position = -1;
            for (int i = 0; i < fileList_res.size(); i++) {
                if(fileList_res.get(i).getAttachmentId().equalsIgnoreCase("0")){
                    position=i;
                    break;
                }
            }
            if(position!=-1)
                fileList_res.remove(fileList_res.get(position));
        }
        // to add the new entry into existing list
        if (getFileList_res != null&&fileList_res!=null) {
            fileList_res.addAll(getFileList_res);
            setList(fileList_res, "",true, true);
        }

    }

    @Override
    public void addView() {

    }

    @Override
    public void onSessionExpire(String msg) {

    }

    @Override
    public void fileExtensionNotSupport(String msg) {

    }

    @Override
    public void onDocumentUpdate(String msg, boolean isSuccess) {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void cbClickListener(JobCardAttachmentModel jobCardAttachmentModel) {
        String isJobCardPdfSend= "1";
        if(jobCardAttachmentModel.getId().equals("-1")){
            if(jobCardAttachmentModel.getChecked()){
                isJobCardPdfSend = "1";
            }else {
                isJobCardPdfSend = "0";
            }
        }else {
            for (JobCardAttachmentModel item : list
            ) {
                if(item.getId().equals(jobCardAttachmentModel.getId())){
                    item.setChecked(jobCardAttachmentModel.getChecked());
                    break;
                }
            }

        }

        for (JobCardAttachmentModel item : list
        ) {
            Log.e("Item status", item.getName()+" = "+item.getChecked());
        }
    }
    private void takeimageFromGalary() {
        //allow upload file extension
        String[] mimeTypes = {"image/jpeg", "image/jpg", "image/png",
                "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",//.doc & .docx
                "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",//.xls & .xlsx
                "application/pdf",//pdf
                "text/csv", "text/plain"//csv
        };

        /* *only for document uploading */
        Intent documentIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        documentIntent.addCategory(Intent.CATEGORY_OPENABLE);
        documentIntent.setType("*/*");
        documentIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(documentIntent, ATTACHFILE_CODE);
    }
    private void askTedPermission(int type,String[] permissions) {
        TedPermission.with(EotApp.getAppinstance())
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                         if (type == 2)
                            takeimageFromGalary();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DOUCMENT_UPLOAD_CODE:
                if (resultCode == RESULT_OK)
                    if (doc_attch_pi != null&&data!=null) {
                        String fileNameExt = AppUtility.getFileNameWithExtension(data.getStringExtra("imgPath"));
                        String bitmapString="";
                        if(data.getBooleanExtra("isFileImage",false)){
                            Bitmap bitmap = AppUtility.getBitmapFromPath(data.getStringExtra("imgPath"));
                            bitmapString = AppUtility.BitMapToString(bitmap);
                        }
                        Attachments obj=new Attachments("0",fileNameExt,fileNameExt,bitmapString);
                        ArrayList<Attachments> getFileList_res =new ArrayList<>();
                        if (fileList_res != null) {
                            getFileList_res.addAll(fileList_res);
                        }
                        getFileList_res.add(obj);

                        setList(getFileList_res, "",true, true);

                        if(data.getStringExtra("fileName")!=null){
                            try
                            {
                                doc_attch_pi.uploadDocuments(jobId, data.getStringExtra("imgPath"),
                                        data.getStringExtra("fileName"),
                                        data.getStringExtra("desc"),
                                        data.getStringExtra("type") ,
                                        data.getStringExtra("isFromCmpletion"));
                            }
                            catch (Exception e)
                            {
                                if (getFileList_res.size()==1) {
                                    fileList_res.remove(getFileList_res.get(0));
                                    setList(fileList_res, "",true, true);
                                }
                                e.printStackTrace();
                            }
                        }
                    }
                break;
            case ATTACHFILE_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri galreyImguriUri = data.getData();
                        //  String gallery_image_Path = PathUtils.getPath(getActivity(), galreyImguriUri);
                        String gallery_image_Path = PathUtils.getRealPath(getActivity(), galreyImguriUri);
                        String img_extension = gallery_image_Path.substring(gallery_image_Path.lastIndexOf("."));
                        //('jpg','png','jpeg','pdf','doc','docx','xlsx','csv','xls'); supporting extensions
                        if (img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")) {
                            imageEditing(data.getData(), true);
                        } else {
                            uploadFileDialog(gallery_image_Path);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    return;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void imageEditing(Uri uri, boolean isImage) {
        try {
            Intent intent = new Intent(getActivity(), ActivityDocumentSaveUpload.class);
            intent.putExtra("uri", uri);
            intent.putExtra("isImage", true);
            intent.putExtra("jobid", jobId);
            intent.putExtra("SAVEASCOMPLETION", false);
            startActivityForResult(intent, DOUCMENT_UPLOAD_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //upload file dialog
    private void uploadFileDialog(final String selectedFilePath) {
        Intent intent = new Intent(getActivity(), ActivityDocumentSaveUpload.class);
        intent.putExtra("uri", selectedFilePath);
        intent.putExtra("isImage", false);
        intent.putExtra("jobid", jobId);
        intent.putExtra("SAVEASCOMPLETION", false);
        startActivityForResult(intent, DOUCMENT_UPLOAD_CODE);
    }
}
