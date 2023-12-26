package com.eot_app.nav_menu.jobs.job_card_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.eot_app.R;
import com.eot_app.databinding.ActivityJobCardViewBinding;
import com.eot_app.databinding.ActivityMainBinding;
import com.eot_app.databinding.DialogJobCardBinding;
import com.eot_app.nav_menu.appointment.details.AppointmentDetailsViewModel;
import com.eot_app.nav_menu.jobs.job_detail.JobDetailActivity;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.detail.adapter.JobCardAttachmentAdapter;
import com.eot_app.nav_menu.jobs.job_detail.detail.job_detail_presenter.JobDetail_pi;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.JobCardAttachmentModel;
import com.eot_app.nav_menu.jobs.job_detail.documents.ActivityDocumentSaveUpload;
import com.eot_app.nav_menu.jobs.job_detail.documents.PathUtils;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.GetFileList_Res;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_Pc;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_Pi;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_View;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.Invoice_Email_Activity;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.Invoice_Email_View;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.Invoice_Email_pc;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.Invoice_Email_pi;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobCardViewActivity extends AppCompatActivity  implements
        View.OnClickListener, Spinner.OnItemSelectedListener, Invoice_Email_View, JobCardAttachmentAdapter.Listener, Doc_Attch_View, Job_Detail_Activity_View {
    Job_Detail_Activity_pi detail_activity_pi=new Job_Detail_Activity_pc(this);
    private ItemList_PI itemListPi;
    Quo_Invo_Pi quo_invo_pi;
    String jobId = "";
    String tempId = "";
    String fwId = "";
    String[] fwList;
    ArrayList<InvoiceEmaliTemplate> templateList;
    String[] kprList;
    String quotId;
    private InvoiceItemDetailsModel invoice_Details;
    private Invoice_Email_pi invoice_email_pi;
    private JobCardAttachmentAdapter jobCardAttachmentAdapter;
    List<JobCardAttachmentModel> reqAttachmentList  = new ArrayList<>();
    List<JobCardAttachmentModel> list  = new ArrayList<>();
    private ArrayList<GetFileList_Res> fileList_res = new ArrayList<>();
    Doc_Attch_Pi doc_attch_pi;
    private final int ATTACHFILE_CODE = 101;
    private static final int DOUCMENT_UPLOAD_CODE = 156;
    ActivityJobCardViewBinding binding;
    CompressImageInBack compressImageInBack = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_job_card_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.preview_and_send_jobcard));
        invoice_email_pi = new Invoice_Email_pc(this, this);
        doc_attch_pi = new Doc_Attch_Pc(this);
        if(getIntent().hasExtra("DataForJobCardView")){
            jobId = getIntent().getStringExtra("JobId");
            fwList = getIntent().getStringArrayExtra("FwList");
            Type type = new TypeToken<List<InvoiceEmaliTemplate>>() {}.getType();
            templateList = new Gson().fromJson(getIntent().getStringExtra("toJsonTemplateString"),type);
        }

        initViews();

    }
    void initViews() {


        // Log.e("fwList",new Gson().toJson(fwList));

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
        if(!binding.cbSign.isChecked()){
            binding.cbSign.setChecked(true);
        }

        binding.sendJobcardBtn.setOnClickListener(this);
        binding.downloadJobcardBtn.setOnClickListener(this);
        binding.linearLayoutSignature.setOnClickListener(this);
        binding.linearLayoutTemplat.setOnClickListener(this);
        binding.templatDp.setOnItemSelectedListener(this);
        binding.signatureDp.setOnItemSelectedListener(this);
        binding.dropDownForCc.setOnClickListener(this);
        binding.removeTechSignature.setOnClickListener(this);

        binding.hintTemplateTxt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.select_template));
        binding.hintSignatureTxt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.tech_sign));

        binding.templatTxt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.select_template));
        binding.signatureTxt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.tech_sign));

        binding.inputLayoutEmailTo.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.to) + "*");
        binding.inputLayoutEmailCc.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cc));
        binding.inputLayoutEmailSubject.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.subject) + "*");
        binding.inputLayoutEmailMessage.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.message) + "*");
        binding.txtLblAddAttachment.setOnClickListener(this);


        if (jobId != null&&!jobId.isEmpty()) {
            binding.tvSendJobcardBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.send_job_card));
            binding.tvDownloadBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.download));
            binding.sendJobcardBtn.setVisibility(View.VISIBLE);

        }
        else {
            binding.tvDownloadBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.print_invoice));
            binding.sendJobcardBtn.setVisibility(View.GONE);
        }


        setEmailData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_jobcard_btn:
                if (jobId != null&&!jobId.isEmpty()) {
                    invoice_email_pi.sendJobCardEmailTemplate(jobId,
                            getIntent().getStringExtra("pdfPath"),
                            binding.jobCardEditor.getHtml(),
                            binding.edtEmailSubject.getText().toString(),
                            binding.edtEmailTo.getText().toString(),
                            binding.edtEmailCc.getText().toString(),tempId,fwId,reqAttachmentList);
                }
                break;
            case R.id.download_jobcard_btn:

                if (detail_activity_pi != null && jobId != null)
                    detail_activity_pi.printJobCard(jobId, tempId,fwId);
                else if (quo_invo_pi != null && quotId != null)
                    quo_invo_pi.generateQuotPDF(quotId,tempId);
                else if (itemListPi!=null&&invoice_Details != null) {
                    String isProformaInv = "0";
                    if (invoice_Details.getIsShowInList() != null && invoice_Details.getIsShowInList().equals("0"))
                        isProformaInv = "1";
                    else isProformaInv = "0";
                    itemListPi.getGenerateInvoicePdf(invoice_Details.getInvId(), isProformaInv,tempId);
                }
                break;
            case R.id.linearLayout_templat:
                binding.templatDp.performClick();

                break;
            case R.id.linearLayout_signature:
                binding.signatureDp.performClick();
                break;
            case R.id.txt_lblAddAttachment:
                if (AppUtility.askGalaryTakeImagePermiSsion(this)) {
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
    private void  setEmailData(){
        if (jobId != null) {
            invoice_email_pi.getJobCardEmailTemplate(jobId,tempId);
//            jobDetail_pi.getAttachFileList(jobId, "","");
            doc_attch_pi.getAttachFileList(jobId, "", "",true);
            jobCardAttachmentAdapter = new JobCardAttachmentAdapter(this);
        }
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
            setEmailData();
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
        binding.inputLayoutEmailTo.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.to) + "*");
        binding.inputLayoutEmailCc.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cc));
        binding.inputLayoutEmailSubject.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.subject) + "*");

        if (email_reS_model.getTo() != null && !email_reS_model.getTo().equals("")) {
            binding.edtEmailTo.setText(email_reS_model.getTo());
        }
        if (email_reS_model.getSubject() != null && !email_reS_model.getSubject().equals("")) {
            binding.edtEmailSubject.setText(email_reS_model.getSubject());
        }
        if (email_reS_model.getMessage() != null && !email_reS_model.getMessage().equals("")) {
            boolean checkedSing = binding.cbSign.isChecked();
            if(checkedSing){
               binding.jobCardEditor.setHtml(email_reS_model.getMessage());
            }else{
                setEmailReplacedMessage(email_reS_model.getMessage(),checkedSing);
            }
        }
        binding.cbSign.setOnCheckedChangeListener((compoundButton, b) -> {
              setEmailReplacedMessage(binding.jobCardEditor.getHtml(),b);
        });

//        this.email_reS_model = email_reS_model;
//
//        if (email_reS_model.getStripLink()!=null)
//            this.stripLink=email_reS_model.getStripLink();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setEmailReplacedMessage(String webMessage,Boolean b) {

        WebSettings mWebSettings = binding.jobCardEditor.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        if(b){
            String replacedMessage = webMessage.replace("<p id=\"esignUrl\"> </p>", "<p id=\"esignUrl\"> <a href=\"_eSign_\" style=\"color:#15a0b3;\">E-Sign</a></p>");
           binding.jobCardEditor.setHtml(replacedMessage);
        }else {
            String replacedMessage = webMessage.replaceAll("<a href=\"_eSign_\" style=\"color:#15a0b3;\">E-Sign</a>", "");
             binding.jobCardEditor.setHtml(replacedMessage);
        }

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
    @Override
    public void selectFile() {

    }

    @Override
    public void setList(ArrayList<GetFileList_Res> getFileList_res, String isAttachCompletionNotes, boolean firstCall) {

        Log.e("Attachment List", ""+getFileList_res.size()+""+isAttachCompletionNotes);
        list.clear();
        this.fileList_res = getFileList_res;
        JobCardAttachmentModel jobCardAttachmentModel = new JobCardAttachmentModel("-1","2",LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_card)+".pdf",true);
        list.add(jobCardAttachmentModel);
        for (GetFileList_Res item : fileList_res) {
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
        binding.rvJobCardAttachment.setLayoutManager(new LinearLayoutManager(this));
        binding.rvJobCardAttachment.setAdapter(jobCardAttachmentAdapter);
        if (jobCardAttachmentAdapter.getItemCount() <= 3) {
            binding.rvJobCardAttachment.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        } else {
            binding.rvJobCardAttachment.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.job_card_att_list_hieght);
        }

    }

    @Override
    public void addNewItemToAttachmentList(ArrayList<GetFileList_Res> getFileList_res, String isAttachCompletionNotes) {
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
                    JobCardAttachmentModel attachmentModel=new JobCardAttachmentModel(jobCardAttachmentModel.getId(),jobCardAttachmentModel.getType());
                    reqAttachmentList.add(attachmentModel);
                    break;
                }
            }

        }

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
                        GetFileList_Res obj=new GetFileList_Res("0",fileNameExt,fileNameExt,bitmapString);
                        ArrayList<GetFileList_Res> getFileList_res =new ArrayList<>();
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
                        } else {
                            uploadFileDialog(data.getData());
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
                Bitmap bitmap = AppUtility.getBitmapFromPath(savedImagePath);
                bitmapString = AppUtility.BitMapToString(bitmap);
            String[] split = fileNameExt.split("\\.");
            String imageNameWithOutExt = split[0];
            GetFileList_Res obj=new GetFileList_Res("0",fileNameExt,fileNameExt,bitmapString);
            ArrayList<GetFileList_Res> getFileList_res =new ArrayList<>();
            if (fileList_res != null) {
                getFileList_res.addAll(fileList_res);
            }
            getFileList_res.add(obj);

            setList(getFileList_res, "",true);

                try
                {
                    doc_attch_pi.uploadDocuments(jobId, savedImagePath,
                            imageNameWithOutExt,
                            "",
                            "2" ,
                            "0");
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


    //upload file dialog
    private void uploadFileDialog(Uri selectedFilePath) {
        try {
            compressImageInBack = new CompressImageInBack(this, new OnImageCompressed() {
                @Override
                public void onImageCompressed(Bitmap bitmap) {
                    String savedImagePath = compressImageInBack.getSavedImagePath();
                    if (savedImagePath != null) {
                        uploadDocumentsJobCard(savedImagePath);
                    }
                }
            }, selectedFilePath);
            compressImageInBack.setSaveBitmap(true);
            compressImageInBack.compressImageInBckg();

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Intent intent = new Intent(this, ActivityDocumentSaveUpload.class);
        intent.putExtra("uri", selectedFilePath);
        intent.putExtra("isImage", false);
        intent.putExtra("jobid", jobId);
        intent.putExtra("SAVEASCOMPLETION", false);
        startActivityForResult(intent, DOUCMENT_UPLOAD_CODE);*/
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
        //   EotApp.getAppinstance().notifyApiObserver(Service_apis.addAppointment);
        super.onBackPressed();
    }
    public void setdetail_activity_pi(Job_Detail_Activity_pi detail_activity_pi) {
        this.detail_activity_pi = detail_activity_pi;
    }
}
