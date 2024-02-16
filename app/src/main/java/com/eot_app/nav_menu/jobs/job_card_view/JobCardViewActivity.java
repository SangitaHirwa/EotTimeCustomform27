package com.eot_app.nav_menu.jobs.job_card_view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.databinding.ActivityJobCardViewBinding;
import com.eot_app.eoteditor.EotEditor;
import com.eot_app.eoteditor.Utils;
import com.eot_app.nav_menu.appointment.Keepar;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.detail.adapter.JobCardAttachmentAdapter;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.JobCardAttachmentModel;
import com.eot_app.nav_menu.jobs.job_detail.documents.PathUtils;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_Pc;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_Pi;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_View;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.Invoice_Email_View;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.Invoice_Email_pc;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.Invoice_Email_pi;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.Get_Email_Message_Req_Modle;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.Get_Email_Message_Res_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.Get_Email_ReS_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.InvoiceEmaliTemplate;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.send_email_temp_model.Send_Email_ReS_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.itemlist_model.InvoiceItemDetailsModel;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.itemlist_mvp.ItemList_PI;
import com.eot_app.nav_menu.jobs.job_detail.job_detail_activity_presenter.Job_Detail_Activity_View;
import com.eot_app.nav_menu.jobs.job_detail.job_detail_activity_presenter.Job_Detail_Activity_pc;
import com.eot_app.nav_menu.jobs.job_detail.job_detail_activity_presenter.Job_Detail_Activity_pi;
import com.eot_app.nav_menu.quote.quote_invoice_pkg.quote_mvp_pkg.Quo_Invo_Pi;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.CompressImageInBack;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.settings.setting_db.FieldWorker;
import com.eot_app.utility.util_interfaces.MySpinnerAdapter;
import com.eot_app.utility.util_interfaces.OnImageCompressed;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hypertrack.hyperlog.HyperLog;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

public class JobCardViewActivity extends AppCompatActivity  implements
        View.OnClickListener, Spinner.OnItemSelectedListener, Invoice_Email_View, JobCardAttachmentAdapter.Listener, Doc_Attch_View, Job_Detail_Activity_View, EotEditor.OnTextChangeListener {
    Job_Detail_Activity_pi detail_activity_pi=new Job_Detail_Activity_pc(this);
    Quo_Invo_Pi quo_invo_pi;
    String jobId = "";
    String tempId = "";
    String fwId = "";
    String[] fwList;
    ArrayList<InvoiceEmaliTemplate> templateList;
    String[] kprList;
    String quotId;
    String invId;
    private InvoiceItemDetailsModel invoice_Details;
    private Invoice_Email_pi invoice_email_pi;
    private JobCardAttachmentAdapter jobCardAttachmentAdapter;
    List<JobCardAttachmentModel> reqAttachmentList  = new ArrayList<>();
    List<JobCardAttachmentModel> list  = new ArrayList<>();
    private ArrayList<Attachments> fileList_res = new ArrayList<>();
    ArrayList<String> arrUserId=new ArrayList<>();

    Doc_Attch_Pi doc_attch_pi;
    private final int ATTACHFILE_CODE = 101;
    private final int CAMERA_CODE = 100;
    private final static int CAPTURE_IMAGE_GALLARY = 222;

    private String captureImagePath;
    private static final int DOUCMENT_UPLOAD_CODE = 156;
    ActivityJobCardViewBinding binding;
    CompressImageInBack compressImageInBack = null;
    Boolean isImage;
    Boolean isChatCheck = false;
    Boolean isSignCheck = false;
    private static String htlmMessage="";
    private String isProformaInv = "0";
    private Get_Email_ReS_Model email_reS_model;
    private Object stripLink;

    ArrayList<String> quoteAttachmentArray=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_job_card_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        invoice_email_pi = new Invoice_Email_pc(this, JobCardViewActivity.this);
        jobCardAttachmentAdapter = new JobCardAttachmentAdapter(this);
        doc_attch_pi = new Doc_Attch_Pc(this);
        Bundle bundle = getIntent().getExtras();
        if(getIntent().hasExtra("JobId")){
            getSupportActionBar().setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.preview_and_send_jobcard));
            jobId = getIntent().getStringExtra("JobId");
            fwList = getIntent().getStringArrayExtra("FwList");
            Type type = new TypeToken<List<InvoiceEmaliTemplate>>() {}.getType();
            templateList = new Gson().fromJson(getIntent().getStringExtra("toJsonTemplateString"),type);
        } if (getIntent().hasExtra("invId")) {
            getSupportActionBar().setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.preview_and_send_invoice));
            invId = bundle.getString("invId");
            jobId = bundle.getString("jobId");
            invoice_Details = bundle.getParcelable("invoiceDetail");
            fwList = getIntent().getStringArrayExtra("FwList");
            Type type = new TypeToken<List<InvoiceEmaliTemplate>>() {}.getType();
            templateList = new Gson().fromJson(getIntent().getStringExtra("templateList"),type);
            if (bundle.getString("isShowInList") != null) {
                if (bundle.getString("isShowInList").equals("0"))
                    isProformaInv = "1";
                else isProformaInv = "0";
            }
            HyperLog.i("", "invoice intent received:" + invId);
        }
        if (getIntent().hasExtra("quotId")) {
            getSupportActionBar().setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.preview_and_send_quote));
            quotId = bundle.getString("quotId");
            Type type = new TypeToken<List<InvoiceEmaliTemplate>>() {}.getType();
            templateList = new Gson().fromJson(getIntent().getStringExtra("templateList"),type);
            HyperLog.i("", "quotation intent received:" + quotId);

        }

        if(invId==null && jobId != null && !jobId.isEmpty() ){
                invoice_email_pi.getJobCardEmailMessageChatList(jobId);
        }else if (invId != null && !invId.isEmpty()) {
            setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.email_invoice));
            invoice_email_pi.getInvoiceEmailTempApi(invId, isProformaInv);
        } else if (quotId != null && !quotId.isEmpty()) {
            setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.email_quotes));
            invoice_email_pi.getQuotationEmailTemplate(quotId,false);
        }
        initViews();

    }
    void initViews() {
        if(quotId != null && !quotId.isEmpty()){
            binding.signatureView.setVisibility(View.GONE);
            setList(fileList_res,"",false);
        }else {
            binding.signatureView.setVisibility(View.VISIBLE);
        }
        binding.rvJobCardAttachment.setLayoutManager(new LinearLayoutManager(this));
        binding.rvJobCardAttachment.setAdapter(jobCardAttachmentAdapter);
        if(jobId != null) {
            doc_attch_pi.getAttachFileList(jobId, "", "", true);
        }
        // Log.e("fwList",new Gson().toJson(fwList));
        binding.jobCardEditor.setVerticalScrollBarEnabled(false);
        if (fwList != null && fwList.length > 0) {
            kprList = new String[fwList.length];
            AppUtility.spinnerPopUpWindow(binding.signatureDp);
            int pos = 0;
            for (String id : fwList) {
                FieldWorker fieldWorker = AppDataBase.getInMemoryDatabase(this).fieldWorkerModel().getFieldWorkerByID(id);
                if(fieldWorker!=null)
                {
                    kprList[pos]=fieldWorker.getName()+" "+fieldWorker.getLnm();
                    if (pos == 0) {
                        setFwStatus(pos);
                    }
                }
                pos++;
            }
            binding.signatureDp.setAdapter(new MySpinnerAdapter(this, kprList));
        }

        if (templateList != null && templateList.size() > 0) {
            binding.templateView.setVisibility(View.VISIBLE);
            AppUtility.spinnerPopUpWindow(binding.templatDp);
            String[] statusList = new String[templateList.size()];
            int pos = 0;
            for (InvoiceEmaliTemplate status : templateList) {
                statusList[pos] = status.getInputValue();
                if (status.getDefaultTemp() != null && status.getDefaultTemp().equals("1")) {
                    tempId = status.getInvTempId();
                    break;
                }
                pos++;
            }
            binding.templatDp.setAdapter(new MySpinnerAdapter(this, statusList));
            for (int i = 0; i < templateList.size(); i++) {
                if (templateList.get(i).getDefaultTemp().equalsIgnoreCase("1"))
                    setEquStatus(i);
            }
        }
        else {
            binding.templateView.setVisibility(View.GONE);
        }

        binding.sendJobcardBtn.setOnClickListener(this);
        binding.downloadJobcardBtn.setOnClickListener(this);
        binding.linearLayoutSignature.setOnClickListener(this);
        binding.linearLayoutTemplat.setOnClickListener(this);
        binding.templatDp.setOnItemSelectedListener(this);
        binding.signatureDp.setOnItemSelectedListener(this);
        binding.dropDownForCc.setOnClickListener(this);
        binding.removeTechSignature.setOnClickListener(this);
        binding.jobCardEditor.setOnTextChangeListener(this);

        binding.cbSign.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.Send_Esign_pad_with_email));
        binding.cbChat.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.click_here_to_provide_chat_url));
        binding.hintTemplateTxt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.select_template));
        binding.hintSignatureTxt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.tech_sign));

        binding.templatTxt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.select_template));
        binding.signatureTxt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.tech_sign));

        binding.inputLayoutEmailTo.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.to));
        binding.inputLayoutEmailCc.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cc));
        binding.inputLayoutEmailSubject.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.subject));
        binding.inputLayoutEmailMessage.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.message));
        binding.txtLblAddAttachment.setOnClickListener(this);
        binding.tvDownloadBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.download));



        if (jobId != null&&!jobId.isEmpty() && invId == null) {
            binding.tvSendJobcardBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.send_job_card));
            binding.sendJobcardBtn.setVisibility(View.VISIBLE);

        }else if(invId != null && !invId.isEmpty()){
            binding.tvSendJobcardBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.email_invoice));
            binding.sendJobcardBtn.setVisibility(View.VISIBLE);
        }
        else {
            binding.tvSendJobcardBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.email_quotes));
            binding.sendJobcardBtn.setVisibility(View.VISIBLE);
        }


        setEmailData("");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_jobcard_btn:
                if (invoice_email_pi.isInputFieldDataValid(   binding.edtEmailTo.getText().toString(), binding.edtEmailCc.getText().toString()
                        , binding.edtEmailSubject.getText().toString(), binding.jobCardEditor.getHtml())) {
                    String messageInHtml =  binding.jobCardEditor.getHtml().replaceAll("\n", "<br>");
                    if (jobId != null && !jobId.isEmpty() && invId == null) {
                        invoice_email_pi.sendJobCardEmailTemplate(jobId,
                                getIntent().getStringExtra("pdfPath"),
                                binding.jobCardEditor.getHtml(),
                                binding.edtEmailSubject.getText().toString(),
                                binding.edtEmailTo.getText().toString(),
                                binding.edtEmailCc.getText().toString(), tempId, fwId, reqAttachmentList);
                    } else if (invId != null) {
                        invoice_email_pi.sendInvoiceEmailTempApi(invId,
                                App_preference.getSharedprefInstance().getLoginRes().getCompId(),
                                messageInHtml,
                                binding.edtEmailSubject.getText().toString(),
                                binding.edtEmailTo.getText().toString(),
                                binding.edtEmailCc.getText().toString(), isProformaInv, tempId,stripLink,reqAttachmentList);
                    } else if (quotId != null) {
                        invoice_email_pi.sendQuotationEmailTemplate(quotId,
                                messageInHtml,
                                binding.edtEmailSubject.getText().toString(),
                                binding.edtEmailTo.getText().toString(),
                                binding.edtEmailCc.getText().toString(),
                                "",
                                email_reS_model.getFrom(),
                                email_reS_model.getFromnm(), tempId,quoteAttachmentArray);
                    }
                }
                break;
            case R.id.download_jobcard_btn:
                if (detail_activity_pi != null && jobId != null && invId==null && !jobId.isEmpty())
                    detail_activity_pi.printJobCard(jobId, tempId,fwId);
                else if (detail_activity_pi != null && quotId != null)
                    detail_activity_pi.generateQuotPDF(quotId,tempId);
                else if (detail_activity_pi!=null&&invoice_Details != null) {
                    String isProformaInv = "0";
                    if (invoice_Details.getIsShowInList() != null && invoice_Details.getIsShowInList().equals("0"))
                        isProformaInv = "1";
                    else isProformaInv = "0";
                    detail_activity_pi.getGenerateInvoicePdf(invoice_Details.getInvId(), isProformaInv,tempId);
                }
                break;
            case R.id.linearLayout_templat:
                binding.templatDp.performClick();

                break;
            case R.id.linearLayout_signature:
                binding.signatureDp.performClick();
                break;
            case R.id.txt_lblAddAttachment:
                selectFiles();
               /* if (AppUtility.askGalaryTakeImagePermiSsion(this)) {
                    takeimageFromGalary();//only for drive documents
                }
                else {
                    // Sdk version 33
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                        askTedPermission(2, AppConstant.galleryPermissions33);
                    }else {
                        askTedPermission(2, AppConstant.galleryPermissions);
                    }
                }*/
                break;
            case R.id.drop_down_for_cc:
                if(binding.hideCCLayout.getVisibility()==View.VISIBLE){
                    binding.hideCCLayout.setVisibility(View.GONE);
                }else {
                    binding.hideCCLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.remove_techSignature:
                binding.signatureTxt.setText("");
                binding.hintSignatureTxt.setVisibility(View.GONE);
                binding.removeTechSignature.setVisibility(View.GONE);
        }
    }

    public void setInvoiceTmpList(ArrayList<InvoiceEmaliTemplate> templateList) {
    }

    @Override
    public void setChatDataList(Get_Email_Message_Res_Model message_res_modle) {
        Get_Email_Message_Req_Modle message_req_modle =  new Get_Email_Message_Req_Modle();
        message_req_modle.setJobId(message_res_modle.getJobId());
        message_req_modle.setCltId(message_res_modle.getCltId());
        message_req_modle.setCompId(App_preference.getSharedprefInstance().getLoginRes().getCompId());
        message_req_modle.setCnm(message_res_modle.getNm());
        message_req_modle.setLabel(message_res_modle.getLabel());
        message_req_modle.setSheduleEnd(message_res_modle.getSchdlFinish());
        message_req_modle.setStaticJobId(""+App_preference.getSharedprefInstance().getLoginRes().getStaticJobId());
        if(message_res_modle.getKeeper().size()>0){
            for(Keepar keepar:message_res_modle.getKeeper()){
                arrUserId.add(keepar.getUsrId());
            }
            message_req_modle.setUsrId(arrUserId);
        }
        message_req_modle.setChatUser(message_res_modle.getChatUser());

        String baseUrl1 =App_preference.getSharedprefInstance().getBaseURL();
        baseUrl1=baseUrl1.replace("/en/eotServices/","/en/customer/#/external/jobChat/");
        Gson gson=new Gson();
        String toJson = gson.toJson(message_req_modle);
        String chatUrl=baseUrl1+encodeBase64(toJson);
        setEmailData(chatUrl);

    }
    public String encodeBase64(String encodeMe){
        byte[] jsonBytes = encodeMe.getBytes();
        String base64String = Base64.encodeToString(jsonBytes, Base64.DEFAULT);
     /*   byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        String decodedJsonString = new String(decodedBytes);*/
        return base64String;
    }

    private void setFwStatus(int pos) {
        binding.hintSignatureTxt.setVisibility(View.VISIBLE);
        fwId = fwList[pos];
        String selectedValue = kprList[pos];
        binding.signatureTxt.setText(selectedValue);
        binding.removeTechSignature.setVisibility(View.VISIBLE);
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
    private void  setEmailData(String url) {
        if (url != null && !url.isEmpty()) {
            invoice_email_pi.getJobCardEmailTemplate(jobId, tempId, url);
        }
//            jobDetail_pi.getAttachFileList(jobId, "","");
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.templat_Dp) {
            setEquStatus(position);
            setEmailData("");
        }
        if (parent.getId() == R.id.signature_Dp) {
            setFwStatus(position);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onGetEmailTempData(Get_Email_ReS_Model email_reS_model) {
        this.email_reS_model = email_reS_model;
        if(quotId != null && !quotId.isEmpty()){
            fileList_res = email_reS_model.getAttachment();
            setList(fileList_res,"",false);
        }

        binding.tvLabelTo.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.to));
        binding.tvLabelCc.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cc));
        binding.tvLabelSub.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.subject));
        binding.tvLabelMsg.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.message));

        if (email_reS_model.getTo() != null && !email_reS_model.getTo().equals("")) {
            binding.edtEmailTo.setText(email_reS_model.getTo());
        }
        if (email_reS_model.getSubject() != null && !email_reS_model.getSubject().equals("")) {
            binding.edtEmailSubject.setText(email_reS_model.getSubject());
        }
        if (email_reS_model.getMessage() != null && !email_reS_model.getMessage().equals("")) {
            this.htlmMessage=email_reS_model.getMessage();
            binding.jobCardEditor.shouldBeClickable();
            getVisibilityForCheckBox(htlmMessage);
            boolean checkedSing = binding.cbSign.isChecked();
            boolean checkedChat= binding.cbChat.isChecked();
            if(checkedSing && checkedChat) {
                binding.jobCardEditor.setHtml(htlmMessage);
            }
            else{
                setEmailReplacedMessage(htlmMessage,checkedSing,checkedChat);
            }
        }  if (email_reS_model.getStripLink()!=null) {
            this.stripLink = email_reS_model.getStripLink();
        }
        binding.cbSign.setOnCheckedChangeListener((compoundButton, b) -> {
            setEmailReplacedMessage(binding.jobCardEditor.getHtml(),b,binding.cbChat.isChecked());
        });
        binding.cbChat.setOnCheckedChangeListener((compoundButton, b) -> {
            setEmailReplacedMessage(binding.jobCardEditor.getHtml(),binding.cbSign.isChecked(),b);
        });

   }

    private void getVisibilityForCheckBox(String mailMessage) {

        if(mailMessage.contains("esignUrl")){
            binding.cbSign.setVisibility(View.VISIBLE);
            binding.cbSign.setChecked(true);
            isSignCheck=true;
        }else {
            binding.cbSign.setChecked(false);
            binding.cbSign.setVisibility(View.GONE);
        }
        if(mailMessage.contains("chatUrl")){
            binding.cbChat.setVisibility(View.VISIBLE);
            binding.cbChat.setChecked(true);
            isChatCheck = true;
        }else {
            binding.cbChat.setChecked(false);
            binding.cbChat.setVisibility(View.GONE);
        }


    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setEmailReplacedMessage(String webMessage,Boolean sing, Boolean chat) {
        String editedMsg=webMessage;
        String msgForChat="";
        WebSettings mWebSettings = binding.jobCardEditor.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        if(isSignCheck) {
            if (sing) {
                String msgSignWithUrl = editedMsg.replace("<p id=\"esignUrl\"> </p>", "<p id=\"esignUrl\"> <a href=\"_eSign_\" style=\"color:#15a0b3;\">E-Sign</a></p>");
                editedMsg=msgSignWithUrl;
                binding.jobCardEditor.setHtml(editedMsg);
            } else {
                String msgSignWithOutUrl = editedMsg.replaceAll("<p id=\"esignUrl\"> <a href=\"_eSign_\" style=\"color:#15a0b3;\">E-Sign</a></p>", "<p id=\"esignUrl\"> </p>");
                editedMsg=msgSignWithOutUrl;
                binding.jobCardEditor.setHtml(editedMsg);
            }
        } if(isChatCheck){
            if(chat){
                String[] htmlMsgSplit = htlmMessage.split("<p");
                for(String msgContainP:htmlMsgSplit){
                    if(msgContainP.contains("Start chatting")){
                        String[] msgContainPSplit= msgContainP.split(" </p>");
                        for(String msgContainCloseP : msgContainPSplit){
                            if(msgContainCloseP.contains("Start chatting")){
                                msgForChat = "<p"+msgContainCloseP+" </p>";
                                break;
                            }
                        }
                        break;
                    }
                }
                String msgSignWithUrl = editedMsg.replaceAll("<p id=\"chatUrl\"> </p>", msgForChat);
                editedMsg=msgSignWithUrl;
                binding.jobCardEditor.setHtml(editedMsg);
            } else {
                String msgChatWithOutUrl = editedMsg.replaceAll("<p id=\"chatUrl\"> <a[^>]*>(.*?)</a> with Fieldworker assigned for your job. </p>", "<p id=\"chatUrl\"> </p>");
                editedMsg =msgChatWithOutUrl;
                binding.jobCardEditor.setHtml(editedMsg);
            }
        }

    }


    @Override
    public void onSendInvoiceEmail(Send_Email_ReS_Model email_reS_model) {
        AppUtility.alertDialog(this, "", LanguageController.getInstance().getServerMsgByKey(email_reS_model.getMessage()), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
            @Override
            public Boolean call() {
                onBackPressed();
                return null;
            }
        });
        AppUtility.hideSoftKeyboard(JobCardViewActivity.this);
    }

    @Override
    public void showErrorAlertDialog(String error) {
        AppUtility.alertDialog(this, "", LanguageController.getInstance().getServerMsgByKey(error), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return null;
            }
        });
    }

    @Override
    public void setSessionExpire(String msg) {
        Toast.makeText(this,msg , Toast.LENGTH_SHORT).show();

    }
    @Override
    public void selectFiles() {
        if (!Utils.isOnline(JobCardViewActivity.this)) {

            AppUtility.alertDialog(JobCardViewActivity.this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title),  LanguageController.getInstance().getMobileMsgByKey(AppConstant.feature_not_available), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return null;
                }
            });
        } else {
            final BottomSheetDialog dialog = new BottomSheetDialog(JobCardViewActivity.this);
            dialog.setContentView(R.layout.bottom_image_chooser);
            TextView camera = dialog.findViewById(R.id.camera);
            camera.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.camera));
            TextView gallery = dialog.findViewById(R.id.gallery);
            gallery.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.gallery));
            TextView drive_document = dialog.findViewById(R.id.drive_document);
            drive_document.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.document));
            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AppUtility.askCameraTakePicture(JobCardViewActivity.this)) {
                        takePictureFromCamera();
                    }else {
                        // Sdk version 33
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                            askTedPermission(0, AppConstant.cameraPermissions33);
                        }else {
                            askTedPermission(0, AppConstant.cameraPermissions);
                        }
                    }
                    dialog.dismiss();
                }
            });

            gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AppUtility.askGalaryTakeImagePermiSsion(JobCardViewActivity.this)) {
                        getImageFromGallray();
                    }else {
                        // Sdk version 33
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                            askTedPermission(1, AppConstant.galleryPermissions33);
                        }else {
                            askTedPermission(1, AppConstant.galleryPermissions);
                        }
                    }
                    dialog.dismiss();
                }
            });

            drive_document.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AppUtility.askGalaryTakeImagePermiSsion(JobCardViewActivity.this)) {
                        takeimageFromGalary();//only for drive documents
                    }else {
                        // Sdk version 33
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                            askTedPermission(2, AppConstant.galleryPermissions33);
                        }else {
                            askTedPermission(2, AppConstant.galleryPermissions);
                        }
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

    @Override
    public void selectFilesForCompletion(boolean isCompletion) {

    }

    private void takePictureFromCamera() {

        if (!AppUtility.askCameraTakePicture(JobCardViewActivity.this)) {
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
            e.printStackTrace();
        }

        Uri uri = FileProvider.getUriForFile(JobCardViewActivity.this, JobCardViewActivity.this.getApplicationContext().getPackageName() + ".provider", imageFile);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        List<ResolveInfo> resInfoList = JobCardViewActivity.this.getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            JobCardViewActivity.this.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(takePictureIntent, CAMERA_CODE); // IMAGE_CAPTURE = 0
    }
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
        return new File(image.getPath());
    }
    private void getImageFromGallray() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, CAPTURE_IMAGE_GALLARY);
    }
    @Override
    public void setList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes, boolean firstCall) {

        Log.e("Attachment List", ""+getFileList_res.size()+""+isAttachCompletionNotes);

        list.clear();
        this.fileList_res = getFileList_res;
        JobCardAttachmentModel jobCardAttachmentModel=null;
        if(getIntent().hasExtra("invId")){
            jobCardAttachmentModel = new JobCardAttachmentModel("-1","2","Invoice"+".pdf",true);
        }else if(getIntent().hasExtra("JobId") && invId == null){
             jobCardAttachmentModel = new JobCardAttachmentModel("-1","2",LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_card)+".pdf",true);
        }else if(getIntent().hasExtra("quotId")){
            jobCardAttachmentModel = new JobCardAttachmentModel("-1","2",LanguageController.getInstance().getMobileMsgByKey(AppConstant.quotes)+".pdf",true);
        }
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
        if(list!=null && list.size()>0 && list.get(0).getChecked()){
            reqAttachmentList.clear();
            reqAttachmentList.add(list.get(0));
            quoteAttachmentArray.add(list.get(0).getId());

        }
        jobCardAttachmentAdapter.updateList(list);
        if (jobCardAttachmentAdapter.getItemCount() <= 3) {
            binding.rvJobCardAttachment.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        } else {
            binding.rvJobCardAttachment.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.job_card_att_list_hieght);
        }

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
            setList(fileList_res, "",true);
        }

    }

    @Override
    public void addView() {

    }

    @Override
    public void finishActivityWithSetResult() {


    }

    @Override
    public void onSessionExpire(String msg) {

    }

    @Override
    public void setInvoiceDetails() {

    }

    @Override
    public void moreInvoiceOption(List<InvoiceItemDataModel> data) {

    }

    @Override
    public void onGetPdfPath(String pdfPath) {
        String path = App_preference.getSharedprefInstance().getBaseURL() + pdfPath;
        Intent openAnyType = new Intent(Intent.ACTION_VIEW);
        openAnyType.setData(Uri.parse(path));
        try {
            startActivity(openAnyType);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSignatureUpload(String signaturePath, String msg) {

    }

    @Override
    public void fileExtensionNotSupport(String msg) {

    }

    @Override
    public void onDocumentUpdate(String msg, boolean isSuccess) {

    }

    @Override
    public void hideProgressBar() {
        AppUtility.progressBarDissMiss();

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void addDocumentInQuote(boolean attechmentUpload) {
        invoice_email_pi.getQuotationEmailTemplate(quotId,attechmentUpload);
    }

    @Override
    public void cbClickListener(JobCardAttachmentModel jobCardAttachmentModel) {
      /*  String isJobCardPdfSend= "1";
        if(jobCardAttachmentModel.getId().equals("-1")){
            if(jobCardAttachmentModel.getChecked()){
                isJobCardPdfSend = "1";
            }else {
                isJobCardPdfSend = "0";
            }
        }else {*/
        if (jobCardAttachmentModel.getChecked()) {
            for (JobCardAttachmentModel item : list) {

                if (item.getId().equals(jobCardAttachmentModel.getId())) {
                    item.setChecked(jobCardAttachmentModel.getChecked());
                    JobCardAttachmentModel attachmentModel = new JobCardAttachmentModel(jobCardAttachmentModel.getId(), jobCardAttachmentModel.getType());
                    reqAttachmentList.add(attachmentModel);
                    quoteAttachmentArray.add(jobCardAttachmentModel.getId());
                    break;
                }
            }
        }else {
            quoteAttachmentArray.remove(jobCardAttachmentModel.getId());
            for (JobCardAttachmentModel item : reqAttachmentList) {
                if (item.getId().equals(jobCardAttachmentModel.getId())) {
                    reqAttachmentList.remove(item);
                    break;}
            }

        }

//            }


        for (JobCardAttachmentModel item : list
        ) {
            Log.e("Item status", item.getName()+" = "+item.getChecked());
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            /*case DOUCMENT_UPLOAD_CODE:
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

                        setList(getFileList_res, "",true);

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
                                    setList(fileList_res, "",true);
                                }
                                e.printStackTrace();
                            }
                        }
                    }
                break;*/
            case CAMERA_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        //get uri from current created path
                        if(captureImagePath!=null) {
                            File file = AppUtility.scaleToActualAspectRatio(captureImagePath, 1024f, 1024f);
                            if (file != null)
                                imageEditing(Uri.fromFile(file), true);

                        }  isImage=true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    return;
                }

                break;
            case CAPTURE_IMAGE_GALLARY:
                if (resultCode == RESULT_OK) {
                    Uri galreyImguriUri = data.getData();
                    //   String gallery_image_Path = com.eot_app.nav_menu.jobs.job_detail.documents.PathUtils.getPath(getActivity(), galreyImguriUri);
                    String gallery_image_Path = com.eot_app.nav_menu.jobs.job_detail.documents.PathUtils.getRealPath(JobCardViewActivity.this, galreyImguriUri);
                    String img_extension = gallery_image_Path.substring(gallery_image_Path.lastIndexOf("."));
                    /******('jpg','png','jpeg','pdf','doc','docx','xlsx','csv','xls'); supporting extensions*/
                    if (img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")) {
                        imageEditing(data.getData(), true);
                        isImage=true;
                    } else {
                        isImage=false;
                        uploadDocumentsJobCard(gallery_image_Path);
                    }
                } else {
                    return;
                }
                break;
            case ATTACHFILE_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri galreyImguriUri = data.getData();
                        //  String gallery_image_Path = PathUtils.getPath(getActivity(), galreyImguriUri);
                        String gallery_image_Path = PathUtils.getRealPath(this, galreyImguriUri);
                        String img_extension = gallery_image_Path.substring(gallery_image_Path.lastIndexOf("."));
                        //('jpg','png','jpeg','pdf','doc','docx','xlsx','csv','xls'); supporting extensions
                        if (img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")) {
                            imageEditing(data.getData(), true);
                            isImage=true;
                        } else {
                            isImage=false;
                            uploadDocumentsJobCard(gallery_image_Path);
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
            compressImageInBack = new CompressImageInBack(this, new OnImageCompressed() {
                @Override
                public void onImageCompressed(Bitmap bitmap) {
                    String savedImagePath = compressImageInBack.getSavedImagePath();
                    if (savedImagePath != null) {
                        uploadDocumentsJobCard(savedImagePath);
                    }
                }
            }, uri);
            compressImageInBack.setSaveBitmap(true);
            compressImageInBack.compressImageInBckg();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadDocumentsJobCard(String savedImagePath) {
        if (doc_attch_pi != null&&savedImagePath!=null) {
            String fileNameExt = AppUtility.getFileNameWithExtension(savedImagePath);
            String bitmapString="";
            if(isImage) {
                Bitmap bitmap = AppUtility.getBitmapFromPath(savedImagePath);
                bitmapString = AppUtility.BitMapToString(bitmap);
            }
            String[] split = fileNameExt.split("\\.");
            String imageNameWithOutExt = split[0];
            Attachments obj=new Attachments("0",fileNameExt,fileNameExt,bitmapString);
            ArrayList<Attachments> getFileList_res =new ArrayList<>();
            if (fileList_res != null) {
                getFileList_res.addAll(fileList_res);
            }
            getFileList_res.add(obj);

            setList(getFileList_res, "",true);

            try
            {
                if(quotId != null && !quotId.isEmpty()){
                           doc_attch_pi.uploadQuoteDocument(savedImagePath,imageNameWithOutExt,quotId,
                           "2","","");
                }else {
                    doc_attch_pi.uploadDocuments(jobId, savedImagePath,
                            imageNameWithOutExt,
                            "",
                            "2",
                            "0");
                }
            }
            catch (Exception e)
            {
                if (getFileList_res.size()==1) {
                    fileList_res.remove(getFileList_res.get(0));
                    setList(fileList_res, "",true);
                }
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quotes_menu, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void setdetail_activity_pi(Job_Detail_Activity_pi detail_activity_pi) {
        this.detail_activity_pi = detail_activity_pi;
    }

    @Override
    public void onTextChange(String text) {
        if(!text.contains("E-Sign")){
            binding.cbSign.setChecked(false);
            binding.cbSign.setVisibility(View.GONE);
        }
        if(!text.contains("Start chatting")) {
            binding.cbChat.setChecked(false);
            binding.cbChat.setVisibility(View.GONE);
        }
    }
}