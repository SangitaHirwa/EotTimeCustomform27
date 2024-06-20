package com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.UploadDocumentActivity;
import com.eot_app.lat_lng_sync_pck.LatLngSycn_Controller;
import com.eot_app.locations.LocationTracker;
import com.eot_app.login_next.FooterMenu;
import com.eot_app.nav_menu.audit.audit_list.equipment.model.EquipmentStatus;
import com.eot_app.nav_menu.audit.audit_list.equipment.remark.RemarkCustomFormFragment;
import com.eot_app.nav_menu.audit.audit_list.equipment.remark.RemarkQuestionListAdpter;
import com.eot_app.nav_menu.audit.audit_list.equipment.remark.remark_mvp.RemarkRequest;
import com.eot_app.nav_menu.audit.nav_scan.EquipmentDetailsActivity;
import com.eot_app.nav_menu.audit.nav_scan.JobEquReallocateActivity;
import com.eot_app.nav_menu.client.clientlist.client_detail.site.sitelist.editsite.editsitedb.SpinnerCountrySite;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.AddEditInvoiceItemActivity2;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.ReplaceItemEquipmentActivity;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.chat.fire_Base_Model.Chat_Send_Msg_Model;
import com.eot_app.nav_menu.jobs.job_detail.chat.img_crop_pkg.ImageCropFragment;
import com.eot_app.nav_menu.jobs.job_detail.customform.MyAttachment;
import com.eot_app.nav_menu.jobs.job_detail.customform.cstm_form_model.CustomFormList_Res;
import com.eot_app.nav_menu.jobs.job_detail.detail.CompletionAdpterJobDteails;
import com.eot_app.nav_menu.jobs.job_detail.detail.NotifyForEquipmentCount;
import com.eot_app.nav_menu.jobs.job_detail.detail.NotifyForEquipmentCountRemark;
import com.eot_app.nav_menu.jobs.job_detail.detail.NotifyForItemCount;
import com.eot_app.nav_menu.jobs.job_detail.detail.NotifyForRequestedItemList;
import com.eot_app.nav_menu.jobs.job_detail.documents.DocumentListAdapter;
import com.eot_app.nav_menu.jobs.job_detail.documents.PathUtils;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.MyFormInterFace;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.ans_model.Ans_Req;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.ans_model.Answer;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.form_ques_mvp.Que_View;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.form_ques_mvp.Que_pi;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.form_ques_mvp.Qus_pc;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.AnswerModel;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.QuesGetModel;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.QuesRspncModel;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.InvoiceItemList2Adpter;
import com.eot_app.nav_menu.jobs.job_detail.job_audit_remark_attchment_pkg.SelectedImageActivity;
import com.eot_app.nav_menu.jobs.job_detail.job_audit_remark_attchment_pkg.model_pkg.JobAuditSingleAttchReqModel;
import com.eot_app.nav_menu.jobs.job_detail.job_audit_remark_attchment_pkg.mvp.JobAudit_PI;
import com.eot_app.nav_menu.jobs.job_detail.job_audit_remark_attchment_pkg.mvp.JobAudit_Pc;
import com.eot_app.nav_menu.jobs.job_detail.job_audit_remark_attchment_pkg.mvp.JobAudit_View;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.EquipmentAttchmentList;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp.JobEquRemark_PC;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp.JobEquRemark_PI;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp.JobEquRemark_View;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.model.UpdateEquStatusReqModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.model.UpdateEquStatusResModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.model.UpdateSiteLocationReqModel;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.AddUpdateRquestedItemActivity;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.RequestedItemListAdapter;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.RequestedItemModel;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.CustomLinearLayoutManager;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.util_interfaces.Callback_AlertDialog;
import com.eot_app.utility.util_interfaces.MySpinnerAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class JobEquPartRemarkRemarkActivity extends UploadDocumentActivity implements
        EquipmentPartRemarkAdapter.OnEquipmentSelection, JobAudit_View,
        NotifyForItemCount, NotifyForEquipmentCountRemark, NotifyForEquipmentCount,
        View.OnClickListener, JobEquRemark_View, ImageCropFragment.MyDialogInterface, DocumentListAdapter.FileDesc_Item_Selected
        , Que_View, MyFormInterFace, MyAttachment, RadioGroup.OnCheckedChangeListener, RequestedItemListAdapter.DeleteItem, RequestedItemListAdapter.SelectedItemListener, NotifyForRequestedItemList {
    public static final int SINGLEATTCHMENT = 365;
    public static final int ADDPART = 333;
    final int EQUIPMENT_UPDATE_CODE = 141,
            DETAILSUPDATEFORUSERMANUAL = 142;
    public final static int REALLOCATE_LC = 152;
    private final ArrayList<Answer> answerArrayList = new ArrayList<>();
    private final List<MultipartBody.Part> docAns = new ArrayList<>();
    private final List<MultipartBody.Part> signAns = new ArrayList<>();
    private final ArrayList<String> signQueIdArray = new ArrayList<>();
    private final ArrayList<String> docQueIdArray = new ArrayList<>();
    ArrayList<Attachments> allAttachmentsList = new ArrayList<>();
    LocationTracker locationTracker;
    String path = "", jobId = "", cltId = "", equId = "";
    FragmentTransaction ft;
    RemarkCustomFormFragment myfragment;
    int position = 0;
    String titleNm = "";
    AppCompatTextView tv_label_status;
    int type = 1;
    private ArrayList<CustomFormList_Res> customFormLists = new ArrayList<>();
    private EquArrayModel equipment;
    private AppCompatTextView button_submit;
    AppCompatTextView tv_label_condition, add_equipment_layout, add_item_layout, tv_label_remark, tv_label_attachment,
            tv_label_part, tv_label_item, upload_lable, tv_label_customForm_que;
    private EditText edit_remarks;
    private Spinner status_spinner, equ_status_spinner;
    private TextView status_label, status_label1;
    boolean isAutoUpdatedRemark = false;
    String equStatusId;
    JobAudit_PI jobAuditPi;
    LinearLayout audit_status_relative, audit_status_relative_status;
    private TextView tv_equipment_name, tv_location;
    private JobEquRemark_PI jobEquimPi;
    private JobEquRemark_PC jobEquimPc;
    private int selectedCondition = -1;
    CardView attachment_card, part_cardview, item_cardview, cv_showRemark, cv_editRemark;
    private boolean REMARK_SUBMIT = false;
    RecyclerView recyclerView_attachment,
            recyclerView_customForm, recyclerView_part, recyclerView_item, recyclerView_requested_item, rv_showAttachment;
    LinearLayout formLayout;
    List<EquipmentStatus> equipmentStatusList = new ArrayList<>();
    List<EquipmentStatus> equipmentStatusList2 = new ArrayList<>();

    private Que_pi queAns_pi;
    private List<QuesRspncModel> quesRspncModelList = new ArrayList<>();
    private RemarkQuestionListAdpter questionListAdapter;
    EquipmentPartRemarkAdapter equipmentPartAdapter;
    private boolean isMandatoryNotFill;
    private ImageView attchmentView, deleteAttchment,/*show_requested_list,hide_requested_list,*/
            requested_item_flag;
    private Button addAttchment;
    private boolean isTagSet = false;
    private RadioGroup rediogrp;
    private RadioButton radio_before, radio_after;
    private RelativeLayout image_with_tag/*,requested_itemList_show_hide_rl*/;
    TextView image_txt, chip_txt, tv_text_for_replace, tv_replace, tv_text_for_repair, tv_repair, tv_text_for_reallocate, tv_reallocate, tv_text_for_discard, tv_discard, requested_item_txt, txt_no_item_found, btn_add_requested_item, txt_lbl_remark, txt_lbl_condition, txt_condition, txt_lbl_status, txt_status, txt_remark, btn_edit;
    ImageView deleteChip;
    private View chip_layout, ll_requested_item;
    Job mParam2;
    private DocumentListAdapter adapter;
    InvoiceItemList2Adpter invoice_list_adpter;
    public final static int ADD_ITEM_DATA = 1;
    boolean isRemarkUpdated = false;
    LinearLayout ll_replace, ll_repair, ll_reallocate, ll_discard;
    ConstraintLayout progressBar_itemRequest;
    List<RequestedItemModel> requestedItemList = new ArrayList<>();
    RequestedItemListAdapter requestedItemListAdapter;
    String brandName = "";
    CompletionAdpterJobDteails jobCompletionAdpter;
    boolean isAction = false, isEdit = false;
    String[] statusList;
    boolean isClickedReqItem = false;
    public static JobEquPartRemarkRemarkActivity jobEquPartRemarkRemarkActivity;

    public JobEquPartRemarkRemarkActivity getInstance() {
        return jobEquPartRemarkRemarkActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobEquPartRemarkRemarkActivity = this;
        setContentView(R.layout.activity_job_equ_remark);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initViews();
        listeners();
        statusListeners();
        setLanguage();
        setData();
        getCustomFormSLists();
    }


    /****Add Item and equipment as a part**/
    private void addEquipmentItem() {
        String locId = "";
        try {
            if (jobId != null && !jobId.equals("")) {
                Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
                if (job.getLocId() == null)
                    job.setLocId("0");
                else locId = job.getLocId();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String strEqu = new Gson().toJson(equipment);

        Intent intent = new Intent(this, AddEditInvoiceItemActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("invId", "");

        intent.putExtra("jobId", jobId);
        intent.putExtra("equipmentId", equipment.getEquId());
        intent.putExtra("equipmentIdName", equipment.getEqunm());
        intent.putExtra("equipmentType", equipment.getType());
        intent.putExtra("equipment", strEqu);
        intent.putExtra("locId", locId);
        intent.putExtra("comeFrom", "AddRemark");
        intent.putExtra("NONBILLABLE", false);
        intent.putExtra("getTaxMethodType", "0");
        intent.putExtra("getSingleTaxId", "0");
        startActivityForResult(intent, ADDPART);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_before:
                isTagSet = true;
                image_txt.setVisibility(View.VISIBLE);
                image_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.before));
                rediogrp.setVisibility(View.GONE);
                chip_layout.setVisibility(View.VISIBLE);
                chip_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.before));
                break;
            case R.id.radio_after:
                isTagSet = true;
                image_txt.setVisibility(View.VISIBLE);
                image_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.after));
                rediogrp.setVisibility(View.GONE);
                chip_layout.setVisibility(View.VISIBLE);
                chip_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.after));
                break;
        }

    }

    @Override
    public void getAnsId(String ansId) {
        if (customFormLists.size() > 0) {
            QuesGetModel quesGetModel = new QuesGetModel(ansId, customFormLists.get(0).getFrmId(),
                    ""
                    , equipment.getAudId(), equipment.getEquId());
            queAns_pi.getQuestions(quesGetModel);
        }
    }

    private void getCustomFormSLists() {
        Job auditmodel = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(equipment.getAudId());
        if (auditmodel != null) {
            ArrayList<String> jTitleId = null;
            try {
                jTitleId = new ArrayList<>();
                if (auditmodel.getEquArray() != null) {
                    jTitleId.add(equipment.getEcId());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            jobEquimPi.getCustomFormList(auditmodel, jTitleId);
        }
    }

    @Override
    public void questionlist(List<QuesRspncModel> quesRspncModelList) {
        Log.e("", "");
        this.quesRspncModelList = quesRspncModelList;
        if (quesRspncModelList.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView_customForm.setLayoutManager(linearLayoutManager);
            questionListAdapter = new RemarkQuestionListAdpter(
                    (ArrayList<QuesRspncModel>) quesRspncModelList, this,
                    this, this, true);
            recyclerView_customForm.setAdapter(questionListAdapter);
            recyclerView_customForm.setNestedScrollingEnabled(false);
            isFormPreFilled();
        }
    }

    private void isFormPreFilled() {
        if (questionListAdapter != null && questionListAdapter.getTypeList() != null) {
            ArrayList<QuesRspncModel> checkList = questionListAdapter.getTypeList();
            for (QuesRspncModel qm : checkList) {
                List<AnswerModel> ans = qm.getAns();
                if (ans != null && ans.size() > 0) {
                    AnswerModel model = ans.get(0);
                    if (model != null && !TextUtils.isEmpty(model.getValue())) {
                        //  isfilled = true;
                    }
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
    public void addfragmentDynamically(List<QuesRspncModel> quesRspncModelList, String optionid) {
        Log.e("", "");
        if (!quesRspncModelList.isEmpty()) {
            String queList = new Gson().toJson(quesRspncModelList);
            ft = getSupportFragmentManager().beginTransaction();
            myfragment = RemarkCustomFormFragment.newInstance("JobEquRemarkRemarkActivity", queList);
            ft.add(R.id.framlayout, myfragment, "Fragment add successFully.....").addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack("close");
            ft.commit();
            setTitlesForRemarkForm(true);
        }
    }


    public void setTitlesForRemarkForm(boolean actionBar) {
        if (actionBar) {
            if (customFormLists != null && customFormLists.size() > 0)
                setTitle(customFormLists.get(0).getFrmnm());
        } else {
            setTitles();
        }
    }

    @Override
    public void showOfflineAlert(String msg) {

    }

    @Override
    public void showofflineAlertchild() {

    }

    private String saveBitMap(View drawView) {

        File pictureFileDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap = getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        return filename;
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }

        view.draw(canvas);
        return returnedBitmap;
    }

    private void createEquipmentForJobAudit(RemarkRequest remarkRequest) {
        jobEquimPi.addNewRemark(remarkRequest, path, docAns, docQueIdArray, signAns, signQueIdArray, isAutoUpdatedRemark, equStatusId);
    }

    private void processImageAndUpload(RemarkRequest remarkRequest) {
        path = saveBitMap(image_with_tag);
        createEquipmentForJobAudit(remarkRequest);
    }

    @Override
    public void finishMuAvtivity() {

    }

    @Override
    public void formNotFound() {
        formLayout.setVisibility(View.GONE);
    }

    @Override
    public void finishErroroccur() {

    }

    @Override
    public void setRequestItemData(List<RequestedItemModel> requestItemData) {
        progressBar_itemRequest.setVisibility(View.GONE);
        if (requestItemData != null && requestItemData.size() > 0) {
            requested_item_flag.setVisibility(View.VISIBLE);
//            requested_itemList_show_hide_rl.setVisibility(View.VISIBLE);
            requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.up), null, null, null);
            recyclerView_requested_item.setVisibility(View.VISIBLE);
            txt_no_item_found.setVisibility(View.GONE);
            requested_item_txt.setClickable(true);
            requestedItemListAdapter.setReqItemList(requestItemData);
        } else {
//            requested_itemList_show_hide_rl.setVisibility(View.GONE);
            requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            requested_item_flag.setVisibility(View.GONE);
            requested_item_txt.setClickable(false);
            requestedItemListAdapter.setReqItemList(new ArrayList<>());
            recyclerView_requested_item.setVisibility(View.GONE);

        }
    }

    @Override
    public void notDtateFoundInRequestedItemList(String msg) {
        EotApp.getAppinstance().showToastmsg(msg);
        progressBar_itemRequest.setVisibility(View.GONE);
//        show_requested_list.setVisibility(View.VISIBLE);
        requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.down), null, null, null);
        recyclerView_requested_item.setVisibility(View.GONE);
//        hide_requested_list.setVisibility(View.GONE);
    }

    @Override
    public void deletedRequestData(String message, AddUpdateRequestedModel requestedModel) {
        EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey(message.trim()));
        jobEquimPi.getRequestedItemDataList(mParam2.getJobId());
        if (requestedModel != null) {
            if (requestedModel.getEbId() != null && !requestedModel.getEbId().equals("0") && !requestedModel.getEbId().equals("")) {
                brandName = AppDataBase.getInMemoryDatabase(this).brandDao().getBrandNameById(requestedModel.getEbId());
            }
            String msg =
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.field_user_made_some_changes_on_the_requested_item) + "\n" + LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_name) + ": " + requestedModel.getItemName() + "\n" +
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty) + ": " + requestedModel.getQty() + "\n" +
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.part_no) + ": " + requestedModel.getModelNo() + "\n" +
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand) + ": " + brandName;
            Chat_Send_Msg_Model chat_send_Msg_model = new Chat_Send_Msg_Model(
                    msg, "", AppUtility.getDateByMiliseconds(),
                    mParam2.getLabel(),
                    requestedModel.getJobId(), "1");
            if (jobEquimPi != null) {
                jobEquimPi.sendMsg(chat_send_Msg_model);
            }
        }
    }

    @Override
    public void setEquStatus(List<UpdateEquStatusResModel> resModel) {
        if (resModel != null) {
            String equStatusId = "";
            showRemarkSection();
            isEdit = true;
            setTitles();
            if (!resModel.isEmpty()) {
                equStatusId = resModel.get(0).getEquStatus();
                for (EquipmentStatus status : equipmentStatusList2) {
                    if (status.getEsId().equalsIgnoreCase(equStatusId)) {
                        status_label1.setText(status.getStatusText());
                        txt_status.setText(status.getStatusText());
                        equ_status_spinner.setSelection(-1);
                        for (int i = 0; i < statusList.length; i++) {
                            if (statusList[i].equals(status.getStatusText())) {
                                equ_status_spinner.setSelection(i);
                            }
                        }
                        if (status.getStatusText().equalsIgnoreCase("Discarded")) {
                            txt_status.setBackgroundColor(ContextCompat.getColor(this, R.color.light_red));
                        } else {
                            txt_status.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_yellow));
                        }
                        break;
                    }
                }
                updateEquStatus(equStatusId);
            }
        }
        AppUtility.progressBarDissMiss();
    }

    private void updateEquStatus(String equStatusId) {
        {
            Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
            if (job != null) {
                try {
                    for (EquArrayModel equipment1 : job.getEquArray()) {
                        if (equipment1.getEquId().equals(equipment.getEquId())) {
                            equipment1.setRemark(edit_remarks.getText().toString());
                            equipment1.setStatus(equStatusId);
                            equipment1.setAdr(equipment.getAdr());
                            equipment1.setCity(equipment.getCity());
                            equipment1.setCtry(equipment.getCtry());
                            equipment1.setSiteId(equipment.getSiteId());
                            equipment1.setState(equipment.getState());
                            equipment1.setZip(equipment.getZip());
                            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJob(job);
                            // for notifying job overview page
                            EotApp.getAppinstance().getJobFlagOverView();
                            EotApp.getAppinstance().getNotifyForEquipmentStatusList();
                            break;
                        } else {
                            // for updating equipment component
                            for (EquArrayModel equipmentArr : job.getEquArray()) {
                                if (equipmentArr.getEquId().equals(equipment.getParentId())) {
                                    for (EquArrayModel equipmentPart : equipmentArr.getEquComponent()) {
                                        if (equipmentPart.getEquId().equals(equipment.getEquId())) {
                                            equipmentPart.setStatus(equStatusId);
                                            equipmentPart.setRemark(edit_remarks.getText().toString());
                                            equipmentPart.setAdr(equipment.getAdr());
                                            equipmentPart.setCity(equipment.getCity());
                                            equipmentPart.setCtry(equipment.getCtry());
                                            equipmentPart.setSiteId(equipment.getSiteId());
                                            equipmentPart.setState(equipment.getState());
                                            equipmentPart.setZip(equipment.getZip());
                                            equipmentPart.setRemark(edit_remarks.getText().toString());
                                            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJob(job);
                                            // for notifying job overview page
                                            EotApp.getAppinstance().getJobFlagOverView();
                                            EotApp.getAppinstance().getNotifyForEquipmentStatusList();
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getCurrentEquipmentStatus(String statusId) {
        String statusName = "";
        if (App_preference.getSharedprefInstance().getEquipmentStatusList() != null) {

            for (EquipmentStatus equipmentStatus : App_preference.getSharedprefInstance().getEquipmentStatusList())
                if (equipmentStatus.getEsId().equals(statusId)) {
                    statusName = equipmentStatus.getStatusText();
                    break;
                }
        }
        return statusName;
    }

    private void initViews() {


        tv_label_status = findViewById(R.id.tv_label_status);
        tv_label_status.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equ_status));
        ll_requested_item = findViewById(R.id.ll_requested_item);
        requested_item_txt = findViewById(R.id.requested_item_txt);
        requested_item_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_requested));
        requested_item_txt.setOnClickListener(this);
        recyclerView_requested_item = findViewById(R.id.recyclerView_requested_item);
        progressBar_itemRequest = findViewById(R.id.progressBar_itemRequest);
        txt_no_item_found = findViewById(R.id.txt_no_item_found);
        txt_no_item_found.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_item_requested_found));
//        show_requested_list = findViewById(R.id.show_requested_list);
//        hide_requested_list = findViewById(R.id.hide_requested_list);
        requested_item_flag = findViewById(R.id.requested_item_flag);
//        requested_itemList_show_hide_rl = findViewById(R.id.requested_itemList_show_hide_rl);
//        show_requested_list.setOnClickListener(this);
//        hide_requested_list.setOnClickListener(this);
        btn_add_requested_item = findViewById(R.id.btn_add_requested_item);
        btn_add_requested_item.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
        btn_add_requested_item.setOnClickListener(this);
        ll_replace = findViewById(R.id.ll_replace);
        item_cardview = findViewById(R.id.item_cardview);
        part_cardview = findViewById(R.id.part_cardview);
        attachment_card = findViewById(R.id.attachment_card);
        add_item_layout = findViewById(R.id.add_item_layout);
        add_equipment_layout = findViewById(R.id.add_equipment_layout);
        tv_label_condition = findViewById(R.id.tv_label_condition);
        tv_label_remark = findViewById(R.id.tv_label_remark);
        edit_remarks = findViewById(R.id.edit_remarks);
        button_submit = findViewById(R.id.button_submit);
        status_spinner = findViewById(R.id.status_spinner);
        equ_status_spinner = findViewById(R.id.equ_status_spinner);
        audit_status_relative = findViewById(R.id.audit_status_relative);
        audit_status_relative_status = findViewById(R.id.audit_status_relative_status);
        status_label = findViewById(R.id.status_label);
        status_label1 = findViewById(R.id.status_label1);
        recyclerView_attachment = findViewById(R.id.recyclerView_attachment);
        recyclerView_part = findViewById(R.id.recyclerView_part);
        recyclerView_item = findViewById(R.id.recyclerView_item);

        tv_text_for_replace = findViewById(R.id.tv_text_for_replace);
        tv_text_for_replace.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.do_you_want_to_discard));
        tv_text_for_repair = findViewById(R.id.tv_text_for_repair);
        tv_repair = findViewById(R.id.tv_repair);
        tv_text_for_reallocate = findViewById(R.id.tv_text_for_reallocate);
        tv_reallocate = findViewById(R.id.tv_reallocate);
        tv_text_for_discard = findViewById(R.id.tv_text_for_discard);
        tv_discard = findViewById(R.id.tv_discard);
        ll_repair = findViewById(R.id.ll_repair);
        ll_discard = findViewById(R.id.ll_discard);
        ll_reallocate = findViewById(R.id.ll_reallocate);


        tv_text_for_repair.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.repair_action_msg));
        tv_repair.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.repair));
        tv_text_for_reallocate.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.reallocate_action_msg));
        tv_reallocate.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.reallocate));
        tv_text_for_discard.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discard_action_msg));
        tv_discard.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discard));

        tv_repair.setOnClickListener(this);
        tv_reallocate.setOnClickListener(this);
        tv_discard.setOnClickListener(this);

        tv_replace = findViewById(R.id.tv_replace);
        tv_replace.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.replace));
        tv_replace.setOnClickListener(this);

        tv_equipment_name = findViewById(R.id.tv_equipment_name);
        tv_location = findViewById(R.id.tv_location);

        tv_label_part = findViewById(R.id.tv_label_part);
        tv_label_part.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_equipment_parts));

        tv_label_item = findViewById(R.id.tv_label_item);
        tv_label_item.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.items));

        tv_label_attachment = findViewById(R.id.tv_label_attachment);
        upload_lable = findViewById(R.id.upload_lable);

        AppUtility.spinnerPopUpWindow(status_spinner);
        status_spinner.setSelected(false);

        AppUtility.spinnerPopUpWindow(equ_status_spinner);
        equ_status_spinner.setSelected(false);

        equipmentStatusList = App_preference.getSharedprefInstance().getLoginRes().getEquipmentStatus();
        if (equipmentStatusList != null) {
            String[] statusList = new String[equipmentStatusList.size()];
            int i = 0;
            for (EquipmentStatus status : equipmentStatusList) {
                statusList[i] = status.getStatusText();
                i++;
            }
            status_spinner.setAdapter(new MySpinnerAdapter(this, statusList));
        }
        equipmentStatusList2 = App_preference.getSharedprefInstance().getEquipmentStatusList();
        if (equipmentStatusList2 != null) {
            statusList = new String[equipmentStatusList2.size()];
            int i = 0;
            for (EquipmentStatus status : equipmentStatusList2) {
                statusList[i] = status.getStatusText();
                i++;
            }
            equ_status_spinner.setAdapter(new MySpinnerAdapter(this, statusList));
        }

        formLayout = findViewById(R.id.formLayout);
        recyclerView_customForm = findViewById(R.id.recyclerView_customForm);
        tv_label_customForm_que = findViewById(R.id.tv_label_customForm_que);
        tv_label_customForm_que.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_form));

        audit_status_relative.setOnClickListener(this);
        audit_status_relative_status.setOnClickListener(this);
        button_submit.setOnClickListener(this);

        upload_lable.setOnClickListener(this);
        add_equipment_layout.setOnClickListener(this);
        add_item_layout.setOnClickListener(this);

        jobEquimPi = new JobEquRemark_PC(this);
        jobEquimPc = new JobEquRemark_PC(this);
        jobAuditPi = new JobAudit_Pc(this);
        image_with_tag = findViewById(R.id.image_with_tag);
        image_txt = findViewById(R.id.image_txt);

        radio_before = findViewById(R.id.radio_before);
        radio_before.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.before));
        radio_before.setChecked(false);

        radio_after = findViewById(R.id.radio_after);
        radio_after.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.after));
        radio_after.setChecked(false);

        chip_txt = findViewById(R.id.chip_txt);
        deleteChip = findViewById(R.id.deleteChip);
        chip_layout = findViewById(R.id.chip_layout);
        deleteChip.setOnClickListener(this);
        rediogrp = findViewById(R.id.rediogrp);
        rediogrp.setOnCheckedChangeListener(this);

        cv_showRemark = findViewById(R.id.cv_showRemark);
        cv_editRemark = findViewById(R.id.cv_editRemark);
        rv_showAttachment = findViewById(R.id.rv_showAttachment);
        txt_lbl_remark = findViewById(R.id.txt_lbl_remark);
        txt_lbl_remark.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.remark_form));
        txt_lbl_condition = findViewById(R.id.txt_lbl_condition);
        txt_lbl_condition.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.condition) + ": ");
        txt_condition = findViewById(R.id.txt_condition);
        txt_status = findViewById(R.id.txt_status);
        txt_remark = findViewById(R.id.txt_remark);
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));

        btn_edit.setOnClickListener(this);

        // to notify when added item or
        EotApp.getAppinstance().setNotifyForItemCount(this);
        EotApp.getAppinstance().setNotifyForEquipmentCountRemark(this);
        EotApp.getAppinstance().setNotifyForEquipmentCount(this);
        EotApp.getAppinstance().setNotifyForRequestedItemList(this);

        String getDisCalculationType;
        if (jobId != null && !jobId.isEmpty()) {
            getDisCalculationType = AppDataBase.getInMemoryDatabase(this).jobModel().disCalculationType(jobId);
        } else {
            getDisCalculationType = App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
        }
        recyclerView_item.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL
                , false));
        invoice_list_adpter = new InvoiceItemList2Adpter(this, new ArrayList<>(), true, true, getDisCalculationType);//, this, this
        recyclerView_item.setAdapter(invoice_list_adpter);
        recyclerView_item.setNestedScrollingEnabled(false);
        recyclerView_requested_item.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL
                , false));
        requestedItemListAdapter = new RequestedItemListAdapter(requestedItemList, this, this, this);//, this, this
        recyclerView_requested_item.setAdapter(requestedItemListAdapter);
        recyclerView_requested_item.setNestedScrollingEnabled(false);

        if (App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsItemRequested() == 0) {
            ll_requested_item.setVisibility(View.VISIBLE);
        } else {
            ll_requested_item.setVisibility(View.GONE);
        }
        CustomLinearLayoutManager customLayoutManager = new CustomLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL
                , false);
        rv_showAttachment.setLayoutManager(customLayoutManager);
        jobCompletionAdpter = new CompletionAdpterJobDteails(new ArrayList<>(), pos -> {
            try {
                if (pos == 0 || pos == 1) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(App_preference.getSharedprefInstance().getBaseURL()
                            + "" + (allAttachmentsList.get(pos).getAttachFileName()))));

                } else {
                    try {
                        if (allAttachmentsList.size() > 0) {
                            String str = new Gson().toJson(allAttachmentsList);
                            Intent intent = new Intent(JobEquPartRemarkRemarkActivity.this, EquipmentAttchmentList.class);
                            intent.putExtra("list", str);
                            startActivity(intent);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        rv_showAttachment.setAdapter(jobCompletionAdpter);

        if (App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsItemRequested() == 0) {
            if (isAction) {
                ll_requested_item.setVisibility(View.VISIBLE);
                if (mParam2.getItemRequested() != null && mParam2.getItemRequested().equals("1")) {
                    requested_item_flag.setVisibility(View.VISIBLE);
//                requested_itemList_show_hide_rl.setVisibility(View.VISIBLE);
                    requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_drop_down), null, null, null);
                } else {
                    requested_item_flag.setVisibility(View.GONE);
//                requested_itemList_show_hide_rl.setVisibility(View.GONE);
                    requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            } else {
                ll_requested_item.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Set Form Question's
     *****/
    public void setList(ArrayList<CustomFormList_Res> customFormLists) {
        this.customFormLists = customFormLists;
        formLayout.setVisibility(View.VISIBLE);
        if (customFormLists.size() > 0) {
            queAns_pi = new Qus_pc(this);
            QuesGetModel quesGetModel = new QuesGetModel("-1", customFormLists.get(0).getFrmId(),
                    "", equipment.getAudId(), equipment.getEquId());

            queAns_pi.getQuestions(quesGetModel);
        }
    }

    @Override
    public void setEquipmentList(List<EquArrayModel> lists) {
        // set Equipment Part List
        Log.e("mParam2::", equId);
        Log.e("mParam2::", new Gson().toJson(lists));

        List<EquArrayModel> partList = new ArrayList<>();
        if (!lists.isEmpty()) {
            for (int i = 0; i < lists.size(); i++) {
                if (lists.get(i).getEquId().equalsIgnoreCase(equId)) {
                    partList.addAll(lists.get(i).getEquComponent());
                }
            }
            for (EquArrayModel eq :
                    partList) {
                eq.setParentName(equipment.getEqunm());
                eq.setParentId(equipment.getEquId());
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView_part.setLayoutManager(linearLayoutManager);
            equipmentPartAdapter = new EquipmentPartRemarkAdapter(this, partList, equipmentRes -> {
                Intent intent = new Intent(JobEquPartRemarkRemarkActivity.this, EquipmentDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("job_equip", equipmentRes);
                String str = new Gson().toJson(equipmentRes);
                intent.putExtra("job_equip_str", str);
                intent.putExtra("jobId", jobId);
                intent.putExtra("equipment", true);
                startActivityForResult(intent, DETAILSUPDATEFORUSERMANUAL);
            });
            equipmentPartAdapter.setEquipmentCurrentStatus(App_preference.getSharedprefInstance().getEquipmentStatusList());
            equipmentPartAdapter.setOnEquipmentSelection(this);
            recyclerView_part.setAdapter(equipmentPartAdapter);
            recyclerView_part.setNestedScrollingEnabled(false);
        }
    }

    @Override
    public void onClickContinuarEvent(Uri uri) {
        String path = "";
        String imageName = "";
        path = PathUtils.getRealPath(this, uri);
        if (!path.isEmpty()) {
            if (type == 1) {
                uploadRemarkAttchment(path, imageName);
            } else if (type == 2) {
                questionListAdapter.showAttchmentView(position, path, attchmentView, deleteAttchment, addAttchment);
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private void setLanguage() {
        add_equipment_layout.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
        add_item_layout.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));

        tv_label_condition.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.condition) + " *");
        tv_label_remark.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.remark));
        edit_remarks.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.remark_msg));
        button_submit.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.submit_btn));
        status_label.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.condition));
        status_label1.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equ_status));
        tv_label_attachment.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.attachment));
        upload_lable.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.expense_upload));
    }

    private void listeners() {
        status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCondition = position;

                String selectedValue = equipmentStatusList.get(position).getStatusText();
                status_label.setText(selectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void statusListeners() {
        equ_status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                equStatusId = equipmentStatusList2.get(position).getEsId();
                String selectedValue = equipmentStatusList2.get(position).getStatusText();
                status_label1.setText(selectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void uploadRemarkAttchment(String path, String imageName) {
        if (jobId != null && equipment.getEquId() != null) {
            JobAuditSingleAttchReqModel model = new JobAuditSingleAttchReqModel(jobId, equipment.getEquId(),
                    button_submit.getText().toString(), titleNm, path, "1");
            String str = new Gson().toJson(model);
            Intent intent = new Intent(this, SelectedImageActivity.class);
            intent.putExtra("attchment", str);
            intent.putExtra("image_name", imageName);
            startActivityForResult(intent, SINGLEATTCHMENT);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            if (isAction) {
                if (isEdit) {
                    hideRemarkSection();
                    isEdit = false;
                    setTitles();
                } else {
                    onBackPressed();
                }
            } else {
                onBackPressed();
            }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (isAction) {
            if (isEdit) {
                hideRemarkSection();
                isEdit = false;
                setTitles();
                /** For set update data*/
                if (isRemarkUpdated) {
                    setData();
                }
            } else {
                finish();
            }
        } else {
            finish();
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteChip:
                isTagSet = false;
                image_txt.setText("");
                image_txt.setVisibility(View.GONE);
                chip_layout.setVisibility(View.GONE);
                rediogrp.setVisibility(View.VISIBLE);
                radio_after.setChecked(false);
                radio_before.setChecked(false);
                break;
            case R.id.upload_lable:
                type = 1;
                selectFile(false);
                break;
            case R.id.add_equipment_layout:
                if (AppUtility.isInternetConnected()) {
                    addEquipmentItem();
                } else {
                    AppUtility.alertDialog(this, LanguageController.getInstance().
                            getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
                }
                break;
            case R.id.add_item_layout:
                addItem();
                break;
            case R.id.audit_status_relative:
                status_spinner.performClick();
                break;
            case R.id.audit_status_relative_status:
                equ_status_spinner.performClick();
                break;
            case R.id.tv_replace:
                submitChangesOnReplace();
                break;
            case R.id.button_submit:
                if (selectedCondition == -1) {
                    AppUtility.alertDialog((this), LanguageController.getInstance().
                                    getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey
                                    (AppConstant.status_required), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok),
                            "", () -> null);
                } else {

                    ansAnsQuesRspncModel();

                    /* *    if question is mandatory but not fill   ***/
                    if (isMandatoryNotFill) {
                        isMandatoryNotFill = false;
                        AppUtility.alertDialog(this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.fill_all_mandatory_questions), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
                        return;
                    }
                    String fomrId = "";
                    if (customFormLists.size() > 0) {
                        fomrId = customFormLists.get(0).getFrmId();
                    }
                    if (answerArrayList.size() > 0) {
                        Ans_Req ans_req = new Ans_Req(App_preference.getSharedprefInstance().getLoginRes().getUsrId(),
                                answerArrayList, fomrId, equipment.getAudId());
                        createNewRemarkRequest(ans_req);
                    } else {
                        createNewRemarkRequest(new Ans_Req());
                    }
                }

                break;

            case R.id.btn_add_requested_item:
//                show_requested_list.setVisibility(View.VISIBLE);
                recyclerView_requested_item.setVisibility(View.GONE);
//                hide_requested_list.setVisibility(View.GONE);
                txt_no_item_found.setVisibility(View.GONE);
                Intent intent2 = new Intent(this, AddUpdateRquestedItemActivity.class);
                intent2.putExtra("addReqItemInEqu", true);
                intent2.putExtra("jobId", mParam2.getJobId());
                intent2.putExtra("jobLabel", mParam2.getLabel());
                intent2.putExtra("equId", equipment.getEquId());
                startActivity(intent2);
                break;

            case R.id.requested_item_txt:
                if (!isClickedReqItem) {
                    if (AppUtility.isInternetConnected()) {
                        isClickedReqItem = true;
                        progressBar_itemRequest.setVisibility(View.VISIBLE);
                        if (jobEquimPi != null) {
                            jobEquimPi.getRequestedItemDataList(mParam2.getJobId());
                        }
                    } else {
                        onErrorMsg(LanguageController.getInstance().getMobileMsgByKey(AppConstant.offline_feature_alert));
                    }
                } else {
                    isClickedReqItem = false;
                    recyclerView_requested_item.setVisibility(View.GONE);
                    txt_no_item_found.setVisibility(View.GONE);
                    requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.down), null, null, null);
                }
//                    show_requested_list.setVisibility(View.GONE);
//                    hide_requested_list.setVisibility(View.VISIBLE);

                break;
           /* case R.id.hide_requested_list:
                show_requested_list.setVisibility(View.VISIBLE);
                recyclerView_requested_item.setVisibility(View.GONE);
                hide_requested_list.setVisibility(View.GONE);
                txt_no_item_found.setVisibility(View.GONE);
                break;*/
            case R.id.btn_edit:
                showRemarkSection();
                isEdit = true;
                setTitles();
                break;
            case R.id.tv_reallocate:
                Intent intent = new Intent(this, JobEquReallocateActivity.class);
                String newLocation = "";
                if (equipment != null) {
                    if (equipment.getAdr() != null && !equipment.getAdr().isEmpty()) {
                        newLocation = newLocation + "" + equipment.getAdr();
                    }
                    if (equipment.getCity() != null && !equipment.getCity().isEmpty()) {
                        newLocation = newLocation + ", " + equipment.getCity();
                    }
                    if (equipment.getState() != null && !equipment.getState().isEmpty() && !equipment.getCtry().isEmpty() && !equipment.getState().equals("0")) {
                        newLocation = newLocation + ", " + SpinnerCountrySite.getStatenameById((equipment.getCtry()), equipment.getState());
                    }
                    if (equipment.getCtry() != null && !equipment.getCtry().isEmpty() && !equipment.getCtry().equals("0")) {
                        newLocation = newLocation + ", " + SpinnerCountrySite.getCountryNameById(equipment.getCtry());
                    }
                    if (equipment.getZip() != null && !equipment.getZip().isEmpty()) {
                        newLocation = newLocation + ", " + equipment.getZip();
                    }
                }
                intent.putExtra("old_location", newLocation);
                intent.putExtra("equId", equipment.getEquId());
                intent.putExtra("clientId", equipment.getCltId());
                intent.putExtra("jobId", jobId);
                startActivityForResult(intent, REALLOCATE_LC);
                break;
            case R.id.tv_repair:
                if (jobId != null && !jobId.isEmpty() && equipment != null && !equipment.getEquId().isEmpty()) {
                    for (EquipmentStatus status : equipmentStatusList2) {
                        if (status.getStatusText().equalsIgnoreCase("In Repair")) {
                            String equStatus = status.getEsId();
                            UpdateEquStatusReqModel reqModel = new UpdateEquStatusReqModel(jobId, equipment.getEquId(), equStatus);
                            jobEquimPi.updateEquStatus(reqModel);
                            break;
                        }
                    }
                }
                break;
            case R.id.tv_discard:
                if (jobId != null && !jobId.isEmpty() && equipment != null && !equipment.getEquId().isEmpty()) {
                    for (EquipmentStatus status : equipmentStatusList2) {
                        if (status.getStatusText().equalsIgnoreCase("Discarded")) {
                            String equStatus = status.getEsId();
                            UpdateEquStatusReqModel reqModel = new UpdateEquStatusReqModel(jobId, equipment.getEquId(), equStatus);
                            jobEquimPi.updateEquStatus(reqModel);
                            break;
                        }
                    }
                }
                break;
        }
    }

    // perform the button submit click when replacing item for submitting the details if edited by the user
    private void submitChangesOnReplace() {
        if (!isRemarkUpdated && selectedCondition != -1) {
            AppUtility.alertDialog2(this, LanguageController.getInstance().
                            getMobileMsgByKey(AppConstant.dialog_alert),
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.do_you_want_to_save_remark),
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok),
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel),
                    new Callback_AlertDialog() {
                        @Override
                        public void onPossitiveCall() {
                            try {
                                isAutoUpdatedRemark = true;
                                ansAnsQuesRspncModel();
                                String fomrId = "";
                                if (customFormLists.size() > 0) {
                                    fomrId = customFormLists.get(0).getFrmId();
                                }
                                if (answerArrayList.size() > 0) {
                                    Ans_Req ans_req = new Ans_Req(App_preference.getSharedprefInstance().getLoginRes().getUsrId(),
                                            answerArrayList, fomrId, equipment.getAudId());
                                    createNewRemarkRequest(ans_req);
                                } else {
                                    createNewRemarkRequest(new Ans_Req());
                                }
                                // for navigating to step 1 screen
                                InvoiceItemDataModel invoiceItemDataModel = new InvoiceItemDataModel();
                                replaceEquipment(invoiceItemDataModel);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNegativeCall() {
                            // for navigating to step 1 screen
                            InvoiceItemDataModel invoiceItemDataModel = new InvoiceItemDataModel();
                            replaceEquipment(invoiceItemDataModel);

                        }
                    });
        } else {
            // for navigating to step 1 screen
            InvoiceItemDataModel invoiceItemDataModel = new InvoiceItemDataModel();
            replaceEquipment(invoiceItemDataModel);

        }

    }


    /****Add only equipment Item**/
    private void addItem() {

        String locId = "";
        try {
            if (jobId != null && !jobId.equals("")) {
                Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
                if (job.getLocId() == null)
                    job.setLocId("0");
                else locId = job.getLocId();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Intent intent = new Intent(this, AddEditInvoiceItemActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("jobId", jobId);
        intent.putExtra("locId", locId);
        intent.putExtra("invId", "");
        intent.putExtra("equipmentId", equipment.getEquId());
        intent.putExtra("equipmentType", equipment.getType());
        intent.putExtra("comeFrom", "AddRemarkItem");
        intent.putExtra("NONBILLABLE", false);
        intent.putExtra("getTaxMethodType", "0");
        intent.putExtra("getSingleTaxId", "0");
        startActivityForResult(intent, ADD_ITEM_DATA);
    }

    /****replace equipment Item**/
    private void replaceEquipment(InvoiceItemDataModel invoiceItemDataModel) {
        // for submitting the changes in condition


        String locId = "";
        try {
            if (jobId != null && !jobId.equals("")) {
                Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
                if (job.getLocId() == null)
                    job.setLocId("0");
                else locId = job.getLocId();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String strEqu = new Gson().toJson(equipment);
//        Intent intent = new Intent(this, ReplaceItemEquipmentActivity.class);
        Intent intent = new Intent(this, AddEditInvoiceItemActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("jobId", jobId);
        intent.putExtra("locId", locId);
        intent.putExtra("invId", "");
        intent.putExtra("equipment", strEqu);
//        intent.putExtra("InvoiceItemDataModel", invoiceItemDataModel);
//        intent.putExtra("comeFrom", "AddRemark");
        intent.putExtra("comeFrom", "AddRemarkReplace");
        intent.putExtra("NONBILLABLE", false);
        intent.putExtra("getTaxMethodType", "0");
        intent.putExtra("getSingleTaxId", "0");
        startActivity(intent);

    }

    /**
     * get Form Answer's
     *****/
    public void ansAnsQuesRspncModel() {
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
                            docAns.add(body);
                            docQueIdArray.add(quesRspncModelList.get(i).getQueId());

                            AnswerModel answerModels = new AnswerModel("0", ans);
                            ansArrayList.add(answerModels);
                            answer = new Answer(this.quesRspncModelList.get(i).getQueId(),
                                    this.quesRspncModelList.get(i).getType(), ansArrayList, this.quesRspncModelList.get(i).getFrmId());
                            answerArrayList.add(answer);

                        } else {
                         /*   AnswerModel answerModels = new AnswerModel("0", "");
                            ansArrayList.add(answerModels);
                            answer = new Answer(this.quesRspncModelList.get(i).getQueId(),
                                    this.quesRspncModelList.get(i).getType(), ansArrayList,
                                    this.quesRspncModelList.get(i).getFrmId());
                            answerArrayList.add(answer);*/
                        }

                    } else if (quesRspncModelList.get(i).getAns().size() == 0)
                        if (quesRspncModelList.get(i).getMandatory().equals("1"))
                            isMandatoryNotFill = true;
                    break;
                /* **case for Signature****/
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
                            RequestBody requestFile = RequestBody.create(file, MediaType.parse(mimeType));
                            // MultipartBody.Part is used to send also the actual file name
                            body = MultipartBody.Part.createFormData("signAns[]", file.getName()
                                    , requestFile);//ans.substring(ans.lastIndexOf(".")
                            signAns.add(body);
                            signQueIdArray.add(quesRspncModelList.get(i).getQueId());

                            AnswerModel docanswerModels = new AnswerModel("0", ans);
                            ansArrayList.add(docanswerModels);
                            answer = new Answer(this.quesRspncModelList.get(i).getQueId(),
                                    this.quesRspncModelList.get(i).getType(), ansArrayList, this.quesRspncModelList.get(i).getFrmId());
                            answerArrayList.add(answer);
                        } else {
                          /*  AnswerModel answerModels = new AnswerModel("0", "");
                            ansArrayList.add(answerModels);
                            answer = new Answer(this.quesRspncModelList.get(i).getQueId(),
                                    this.quesRspncModelList.get(i).getType(), ansArrayList,
                                    this.quesRspncModelList.get(i).getFrmId());
                            answerArrayList.add(answer);*/
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
                        answer = new Answer(this.quesRspncModelList.get(i).getQueId(),
                                this.quesRspncModelList.get(i).getType(), ansArrayList, quesRspncModelList.get(i).getFrmId());
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
                    if (quesRspncModelList.get(i).getAns() != null && quesRspncModelList.get(i).getAns().size() > 0) {
                        if (quesRspncModelList.get(i).getType().equals("5")) {
                            if (!TextUtils.isEmpty(quesRspncModelList.get(i).getAns().get(0).getValue())) {
                                long l = Long.parseLong(quesRspncModelList.get(i).getAns().get(0).getValue());
                                ans = AppUtility.getDate(l, "dd-MMM-yyyy");
                            }
                        } else if (quesRspncModelList.get(i).getType().equals("6")) {
                            if (!TextUtils.isEmpty(quesRspncModelList.get(i).getAns().get(0).getValue())) {
                                long l = Long.parseLong(quesRspncModelList.get(i).getAns().get(0).getValue());
                                ans = AppUtility.getDate(l,
                                        AppUtility.dateTimeByAmPmFormate("hh:mm a", "HH:mm"));
                            }
                        } else if (quesRspncModelList.get(i).getType().equals("7")) {
                            if (!TextUtils.isEmpty(quesRspncModelList.get(i).getAns().get(0).getValue())) {
                                long l = Long.parseLong(quesRspncModelList.get(i).getAns().get(0).getValue());
                                ans = AppUtility.getDate(l, AppUtility.dateTimeByAmPmFormate(
                                        "dd-MMM-yyyy hh:mm a", "dd-MMM-yyyy HH:mm"));
                            }
                        } else
                            ans = quesRspncModelList.get(i).getAns().get(0).getValue();
                        if (quesRspncModelList.get(i).getMandatory().equals("1"))
                            if (TextUtils.isEmpty(ans))
                                isMandatoryNotFill = true;

                        AnswerModel answerModel = new AnswerModel(key, ans);
                        ansArrayList.add(answerModel);
                        answer = new Answer(this.quesRspncModelList.get(i).getQueId(),
                                this.quesRspncModelList.get(i).getType(), ansArrayList,
                                customFormLists.get(0).getFrmId());
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
                                AnswerModel answerModel = new AnswerModel(key, ans);
                                ansArrayList.add(answerModel);
                                if (quesRspncModelList.get(i).getMandatory().equals("1"))
                                    if (TextUtils.isEmpty(ans))
                                        isMandatoryNotFill = true;
                            }
                    }
                    if (ansArrayList.size() > 0) {
                        answer = new Answer(this.quesRspncModelList.get(i).getQueId(),
                                this.quesRspncModelList.get(i).getType(), ansArrayList
                                , customFormLists.get(0).getFrmId());
                        answerArrayList.add(answer);
                    } else if (quesRspncModelList.get(i).getMandatory().equals("1")) {
                        isMandatoryNotFill = true;
                    } else {
                        answer = new Answer(this.quesRspncModelList.get(i).getQueId(),
                                this.quesRspncModelList.get(i).getType(), ansArrayList, customFormLists.get(0).getFrmId());
                        answerArrayList.add(answer);
                    }
                    break;
            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 145) {
            if (grantResults != null && grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (locationTracker != null) {
                    locationTracker.getCurrentLocation();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void getUpdatedLocation() {
        locationTracker = new LocationTracker(this, (isLocationUpdated, isPermissionAllowed) -> {
            if (isPermissionAllowed) {
                locationTracker.getCurrentLocation();
            } else {
                ActivityCompat.requestPermissions(JobEquPartRemarkRemarkActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        145);
            }
        });

    }

    private void createNewRemarkRequest(Ans_Req ans_req) {
        RemarkRequest remarkRequest = new RemarkRequest();
        remarkRequest.setAnswerArray(ans_req);
        remarkRequest.setAudId(equipment.getAudId());
        remarkRequest.setEquId(equipment.getEquId());
        remarkRequest.setRemark(edit_remarks.getText().toString());
        remarkRequest.setIsJob("1");
        remarkRequest.setStatus(selectedCondition + 1 + "");

        remarkRequest.setUsrId(App_preference.getSharedprefInstance().getLoginRes().getUsrId());

        if (LatLngSycn_Controller.getInstance().getLat() == null)
            remarkRequest.setLat("0.0");
        else remarkRequest.setLat(LatLngSycn_Controller.getInstance().getLat());

        if (LatLngSycn_Controller.getInstance().getLng() == null)
            remarkRequest.setLng("0.0");
        else remarkRequest.setLng(LatLngSycn_Controller.getInstance().getLng());


        remarkRequest.setStatus(getSelectedStatusId(selectedCondition));

        if (isTagSet) {
            processImageAndUpload(remarkRequest);
        } else {
            createEquipmentForJobAudit(remarkRequest);
        }

    }

    private String getSelectedStatusId(int position) {
        String esId = "";
        if (equipmentStatusList != null)
            esId = equipmentStatusList.get(position).getEsId();

        return esId;

    }

    private int getSelectedStatusPosition(String esID) {
        int position = -1;
        if (equipmentStatusList != null) {
            for (int i = 0; i < equipmentStatusList.size(); i++) {
                if (equipmentStatusList.get(i).getEsId().equals(esID)) {
                    return i;
                }
            }
        }
        return position;

    }

    @SuppressLint("SetTextI18n")
    private void setData() {

        getUpdatedLocation();
        Intent intent = getIntent();


        //equipment = intent.getParcelableExtra("equipment");
        if (intent.hasExtra("equipment")) {
            String strEquipment = intent.getExtras().getString("equipment");
            equipment = new Gson().fromJson(strEquipment, EquArrayModel.class);
        }
        if (isRemarkUpdated) {
            Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(mParam2.getJobId());
            isRemarkUpdated = false;
            if (job != null && job.getEquArray() != null) {
                Log.e("job Eq ::", new Gson().toJson(job.getEquArray()));
                for (EquArrayModel item :
                        job.getEquArray()) {
                    for (EquArrayModel partItem : item.getEquComponent()) {
                        if (equipment.getEquId().equalsIgnoreCase(partItem.getEquId())) {
                            equipment = partItem;
                            break;
                        }
                    }
                }
            }
        }

        try {
            if (getIntent().hasExtra("jobId")) {
                jobId = intent.getExtras().getString("jobId");
                Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
                if (job != null && job.getEquArray() != null) {
                    Log.e("job Eq ::", new Gson().toJson(job.getEquArray()));
                    for (EquArrayModel item :
                            job.getEquArray()) {
                        if (equipment.getEquId().equalsIgnoreCase(item.getEquId())) {
                            equipment = item;
                            break;
                        }
                    }
                }
            }
            if (getIntent().hasExtra("cltId")) {
                cltId = intent.getExtras().getString("cltId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // TODO
        // add permission for showing items

        for (FooterMenu serverList : App_preference.getSharedprefInstance().getLoginRes().getFooterMenu()) {
            if (serverList.isEnable.equals("1"))
                if ("set_itemMenuOdrNo".equals(serverList.getMenuField())) {
                    if (App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsItemVisible() == 0 &&
                            App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsItemEnable().equals("0")) {
                        if (isAction) {
                            item_cardview.setVisibility(View.VISIBLE);
                        } else {
                            item_cardview.setVisibility(View.GONE);
                        }
                    } else {
                        item_cardview.setVisibility(View.GONE);
                    }
                }
        }


        if (equipment != null) {
            if (!TextUtils.isEmpty(equipment.getStatus()) || equipment.getAttachments() != null && equipment.getAttachments().size() > 0) {
                btn_edit.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
            } else {
                btn_edit.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
            }

            equId = equipment.getEquId();
            mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);

            if (equipment.getType().equalsIgnoreCase("1")) {
                tv_label_status.setVisibility(View.VISIBLE);
                audit_status_relative_status.setVisibility(View.VISIBLE);
            } else {
                /** After discussion with Jit Sir Visible for all 23/4/24*/
                tv_label_status.setVisibility(View.VISIBLE);
                audit_status_relative_status.setVisibility(View.VISIBLE);
            }
            //for checking the sttaus of the equipment and hide replace part if duiscarded already

            if (isAction) {
                if (txt_status.getText().toString().equalsIgnoreCase("Discarded")) {
                    ll_replace.setVisibility(View.VISIBLE);
                    ll_discard.setVisibility(View.VISIBLE);
                    tv_replace.setEnabled(false);
                    tv_discard.setEnabled(false);
                    tv_discard.setBackgroundResource(R.drawable.disable_submit_btn);
                    tv_replace.setBackgroundResource(R.drawable.disable_submit_btn);
                    tv_text_for_replace.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discard_equipments_not_replaced));
                    tv_text_for_discard.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discard_equipments_not_discarded_again));
                } else {
                    ll_repair.setVisibility(View.VISIBLE);
                    ll_reallocate.setVisibility(View.VISIBLE);
                    ll_replace.setVisibility(View.VISIBLE);
                    ll_discard.setVisibility(View.VISIBLE);
                    tv_replace.setEnabled(true);
                    tv_discard.setEnabled(true);
                    tv_discard.setBackgroundResource(R.drawable.submit_btn);
                    tv_replace.setBackgroundResource(R.drawable.submit_btn);
                    tv_text_for_replace.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.do_you_want_to_discard));
                    tv_text_for_discard.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discard_action_msg));
                }
            } else {
                ll_replace.setVisibility(View.GONE);
                ll_repair.setVisibility(View.GONE);
                ll_reallocate.setVisibility(View.GONE);
                ll_discard.setVisibility(View.GONE);
            }

            Log.e("mParam2", new Gson().toJson(mParam2));
            Log.e("jobId", jobId);
            // set item data
            setItemListByJob(mParam2.getItemData());

            // to get equipment part list
            setEquipmentList(mParam2.getEquArray());
//            jobEquimPi.getEquipmentList(equipment.getType(), cltId, jobId, equipment.getEquId());

            // not for part of equipment
            if (equipment.getIsPart().equalsIgnoreCase("1")) {
                part_cardview.setVisibility(View.GONE);
                item_cardview.setVisibility(View.VISIBLE);
            }


            // set name
            if (equipment.getParentName() != null && !equipment.getParentName().isEmpty())
                tv_equipment_name.setText(equipment.getParentName() + " > " + equipment.getEqunm());
            else
                tv_equipment_name.setText(equipment.getEqunm());


            tv_location.setText(equipment.getLocation());

//            if (TextUtils.isEmpty(equipment.getStatus())
//                    || equipment.getStatus().equals("0"))
//                return;


            if (!TextUtils.isEmpty(equipment.getRemark())) {
                txt_remark.setVisibility(View.VISIBLE);
                isRemarkUpdated = true;
                edit_remarks.setText(equipment.getRemark());
                txt_remark.setText(equipment.getRemark());
            } else {
                txt_remark.setVisibility(View.GONE);
            }


            if (!TextUtils.isEmpty(equipment.getStatus()) && TextUtils.isDigitsOnly(equipment.getStatus())) {
                int selectedStatusPosition = getSelectedStatusPosition(equipment.getStatus());
                if (selectedStatusPosition > -1) {
                    isRemarkUpdated = true;
                    status_spinner.setSelection(selectedStatusPosition);
                }
            }
            if (equipment.getStatus() != null && !equipment.getStatus().equals("")) {
                txt_lbl_condition.setVisibility(View.VISIBLE);
                txt_condition.setVisibility(View.VISIBLE);
                for (EquipmentStatus status : equipmentStatusList) {
                    if (status.getEsId().equalsIgnoreCase(equipment.getStatus())) {
                        txt_condition.setText(status.getStatusText());
                    }
                }
            } else {
                txt_lbl_condition.setVisibility(View.GONE);
                txt_condition.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(equipment.getEquStatus()) && TextUtils.isDigitsOnly(equipment.getEquStatus())) {
                int selectedStatusPosition = getEquipmentStatusPosition(equipment.getEquStatus());
                if (selectedStatusPosition > -1) {
                    equ_status_spinner.setSelection(selectedStatusPosition);
                }
            }
            for (EquipmentStatus status : equipmentStatusList2) {
                if (status.getEsId().equalsIgnoreCase(equipment.getEquStatus())) {
                    if (status.getStatusText().equalsIgnoreCase("Discarded")) {
                        txt_status.setBackgroundColor(ContextCompat.getColor(this, R.color.light_red));
                    } else {
                        txt_status.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_yellow));
                    }
                    txt_status.setText(status.getStatusText());
                }
            }
            if (equipment != null && equipment.getAttachments() != null) {
                if (equipment.getAttachments().size() > 0) {
                    setAttachments(equipment.getAttachments());
                    button_submit.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update_btn));
                    REMARK_SUBMIT = true;
                } else {
                    rv_showAttachment.setVisibility(View.GONE);
                }
            } else {
                rv_showAttachment.setVisibility(View.GONE);
            }

        }
        if (intent.hasExtra("isAction")) {
            isAction = intent.getBooleanExtra("isAction", false);
            if (isAction) {
                isEdit = false;
                hideRemarkSection();
            } else {
                isEdit = true;
                showRemarkSection();
            }
        }
        setTitles();

    }

    private int getEquipmentStatusPosition(String esID) {
        int position = -1;
        if (equipmentStatusList2 != null) {
            for (int i = 0; i < equipmentStatusList2.size(); i++) {
                if (equipmentStatusList2.get(i).getEsId().equals(esID)) {
                    return i;
                }
            }
        }
        return position;

    }

    private void setAttachments(ArrayList<Attachments> attachments) {
        allAttachmentsList = attachments;
        if (equipment != null) {
            if (attachments != null) {
                if (attachments.size() > 0) {
                    rv_showAttachment.setVisibility(View.VISIBLE);
                } else {
                    rv_showAttachment.setVisibility(View.GONE);
                }
                if (adapter == null) {
                    recyclerView_attachment.setLayoutManager(new GridLayoutManager(this, 3));
                    adapter = new DocumentListAdapter(this, attachments, jobId, "1");
                    recyclerView_attachment.setAdapter(adapter);
                    jobCompletionAdpter.updateFileList(attachments);
                } else {
                    adapter.updateFileList(attachments, true);
                    jobCompletionAdpter.updateFileList(attachments);
                }
            }
        }
    }


    private void setTitles() {
        if (equipment.getRemark().equals("") && equipment.getStatus().equals("") && equipment.getAttachments() != null && equipment.getAttachments().size() == 0) {
            if (isAction) {
                if (isEdit) {
                    if (!TextUtils.isEmpty(equipment.getStatus()) || equipment.getAttachments() != null && equipment.getAttachments().size() > 0) {
                        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update_remark));
                    } else {
                        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_remark));
                    }
                } else {
                    setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.action));
                }
            } else {
                setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_remark));
            }
            titleNm = (LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_remark));
        } else {
            if (equipment != null && equipment.getEqunm() != null) {
                if (isAction) {
                    if (isEdit) {
                        if (!TextUtils.isEmpty(equipment.getStatus()) || equipment.getAttachments() != null && equipment.getAttachments().size() > 0) {
                            setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update_remark));
                        } else {
                            setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_remark));
                        }
                    } else {
                        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.action));
                    }
                } else {
                    setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_remark));
                }
                titleNm = LanguageController.getInstance().getMobileMsgByKey(AppConstant.remark_on) + " " + equipment.getEqunm();
            } else {
                setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update_remark));
                titleNm = LanguageController.getInstance().getMobileMsgByKey(AppConstant.update_remark);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String fileNameExt = "";
        if (requestCode == SINGLEATTCHMENT) {
            if (Objects.requireNonNull(data).getSerializableExtra("code") != null) {
                JobAuditSingleAttchReqModel myObject = (JobAuditSingleAttchReqModel) data.getSerializableExtra("code");
                // to add temporary element in list
                try {
                    if (myObject.getPath() != null) {
                        String bitmapString = "";
                        if (data.getBooleanExtra("isImage", false)) {
                            Bitmap bitmap = AppUtility.getBitmapFromPath(myObject.getPath());
                            bitmapString = AppUtility.BitMapToString(bitmap);
                        }
                        /* String fileNameExt = AppUtility.getFileNameWithExtension(myObject.getPath());*/
                        fileNameExt = data.getStringExtra("image_name");
                        Attachments obj = new Attachments("0", fileNameExt, fileNameExt, bitmapString);
                        ArrayList<Attachments> updateList = new ArrayList<>();
                        updateList.add(obj);
                        updateList.addAll(allAttachmentsList);
                        setAttachments(updateList);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // api call for uploading attachments
                jobAuditPi.uploadAttchmentOnserverForJobAudit(myObject, fileNameExt);
            }
        } else if (requestCode == ADD_ITEM_DATA) {
//            if (data.hasExtra("AddInvoiceItem")) {
            mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
            setItemListByJob(mParam2.getItemData());
//            }
        } else if (requestCode == ADDPART) {
            Log.e("OnResult", "PartAdded");
            // call api to refresh data
            jobEquimPi.getEquipmentList(equipment.getType(), cltId, jobId, equipment.getEquId());
        }
        if (requestCode == DETAILSUPDATEFORUSERMANUAL && resultCode == Activity.RESULT_OK) {
            if (adapter != null && jobEquimPi != null) {
                jobEquimPi.getEquipmentList(equipment.getType(), cltId, jobId, equipment.getEquId());
            }
        }
        if (requestCode == EQUIPMENT_UPDATE_CODE && resultCode == Activity.RESULT_OK) {
            if (adapter != null && jobEquimPi != null) {
                jobEquimPi.getEquipmentList(equipment.getType(), cltId, jobId, equipment.getEquId());
            }
        }
        if (requestCode == REALLOCATE_LC) {
            if (data != null) {
                String adrModel = data.getStringExtra("adr_model");
                Type listType = new TypeToken<UpdateSiteLocationReqModel>() {
                }.getType();
                UpdateSiteLocationReqModel updateData = new Gson().fromJson(adrModel, listType);
                equipment.setAdr(updateData.getAdr());
                equipment.setCity(updateData.getCity());
                equipment.setCtry(updateData.getCtry());
                equipment.setSiteId(updateData.getSiteId());
                equipment.setState(updateData.getState());
                equipment.setZip(updateData.getZip());
                updateEquStatus(txt_status.getText().toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRemarkUpdate(String message, InvoiceItemDataModel updateItemDataModel) {
        isRemarkUpdated = true;
        String remark_msg = !REMARK_SUBMIT ? LanguageController.getInstance().getMobileMsgByKey(AppConstant.euipment_remark_submit) : LanguageController.getInstance().getMobileMsgByKey(AppConstant.euipment_remark_update);
        EotApp.getAppinstance().getNotifyForEquipmentStatusList();
        // for not showing the toast when we are automatically updating remark on replace click with dialog
        if (!isAutoUpdatedRemark) {
            EotApp.getAppinstance().showToastmsg(remark_msg);
            onBackPressed();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCountItem();
        if (jobId != null && !jobId.isEmpty()) {
            String itemRequested = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getItemRequested(jobId);
            if (itemRequested != null && itemRequested.equals("1")) {
                requested_item_flag.setVisibility(View.VISIBLE);
                requested_item_txt.setClickable(true);
//                requested_itemList_show_hide_rl.setVisibility(View.VISIBLE);
                requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.down), null, null, null);
            } else {
                requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                requested_item_flag.setVisibility(View.GONE);
                requested_item_txt.setClickable(false);
            }
        }
    }

    @Override
    public void onErrorMsg(String msg) {
        AppUtility.error_Alert_Dialog(this, msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok)
                , () -> null);
    }

    @Override
    public void onSessionExpire(String message) {
        AppUtility.alertDialog(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title), message, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> {
            EotApp.getAppinstance().sessionExpired();
            return null;
        });
    }

    @Override
    public void attchmentUpload(String convert) {
        if (convert != null && !convert.isEmpty()) {
            ArrayList<Attachments> equipmentList = new ArrayList<>();
            jobEquimPi.getEquipmentList(equipment.getType(), cltId, jobId, equipment.getEquId());
            try {
                Type listType = new TypeToken<List<Attachments>>() {
                }.getType();
                equipmentList = new Gson().fromJson(convert, listType);
                if (equipment != null && equipment.getAttachments() != null && equipment.getAttachments().size() > 0) {
                    equipmentList.addAll(equipment.getAttachments());
                }
                Log.e("equipmentList", new Gson().toJson(equipmentList));


                if (equipmentList.size() > 0) {
                    // remove the temporary added item for showing loader
                    if (equipmentList != null && !equipmentList.isEmpty()) {
                        int position = -1;
                        for (int i = 0; i < equipmentList.size(); i++) {
                            if (equipmentList.get(i).getAttachmentId().equalsIgnoreCase("0")) {
                                position = i;
                                break;
                            }
                        }
                        if (position != -1)
                            equipmentList.remove(equipmentList.get(position));
                    }
                    allAttachmentsList = equipmentList;

                    setAttachments(equipmentList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setItemListByJob(List<InvoiceItemDataModel> itemList) {
        Log.e("itemList", new Gson().toJson(itemList));
        /* *** *Sort Item By name***/
        try {
            Collections.sort(itemList, (o1, o2) -> o1.getInm().compareTo(o2.getInm()));
            List<InvoiceItemDataModel> equipmentItemList = new ArrayList<>();
            if (itemList != null && invoice_list_adpter != null && itemList.size() > 0) {
                for (int i = 0; i < itemList.size(); i++) {
                    if (itemList.get(i).getEquId().equalsIgnoreCase(equId)) {
                        equipmentItemList.add(itemList.get(i));
                    }
                }
                invoice_list_adpter.updateitemlist(equipmentItemList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnItemClick_Document(Attachments attachments) {
        if (attachments != null)
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(App_preference.getSharedprefInstance().getBaseURL() + "" + attachments.getAttachFileName())));
    }

    /**
     * get Sub Question's Answer's List
     ******/
    public void getAnsList
    (ArrayList<Answer> answerArray, List<MultipartBody.Part> signAns,
     List<MultipartBody.Part> docAns, ArrayList<String> signQueIdArray, ArrayList<String> docQueIdArray) {
        Log.e("Size--->>", ">>>>>" + answerArray.size());
        if (customFormLists != null && customFormLists.size() > 0)
            for (Answer ans : answerArray) {
                ans.setFrmId(customFormLists.get(0).getFrmId());
            }
        this.signQueIdArray.addAll(signQueIdArray);
        this.docQueIdArray.addAll(docQueIdArray);
        this.signAns.addAll(signAns);
        this.docAns.addAll(docAns);
        answerArrayList.addAll(answerArray);
    }

    @Override
    public void selectFileWithoutAttchment(int position, ImageView attchmentView, ImageView
            deleteAttchment, Button addAttchment) {
        this.deleteAttchment = deleteAttchment;
        this.position = position;
        this.attchmentView = attchmentView;
        this.addAttchment = addAttchment;
        type = 2;
        selectFile(true);
    }

    @Override
    public void onDocumentSelected(String path, String name, boolean isImage) {
        super.onDocumentSelected(path, name, isImage);
        if (type == 1) {
            uploadRemarkAttchment(path, name);
        } else if (type == 2) {
            if (questionListAdapter != null)
                questionListAdapter.showAttchmentView(position, path, attchmentView, deleteAttchment, addAttchment);
        }
    }


    @Override
    public void updateCountItem() {
        Log.e("OnCreateRemark", "Notify Item Called");
        mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
        // for updating item data
        if (mParam2.getItemData() != null && mParam2.getItemData().size() > 0) {
            setItemListByJob(mParam2.getItemData());
        }
        // for updating eq data
        if (mParam2.getEquArray() != null && mParam2.getEquArray().size() > 0) {
            setEquipmentList(mParam2.getEquArray());
        }
    }

    @Override
    public void updateCountEquipment() {
        Log.e("OnCreateRemark", "Equipment Notify Called");
        mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
        // for updating item data
        if (mParam2.getItemData() != null && mParam2.getItemData().size() > 0) {
            setItemListByJob(mParam2.getItemData());
        }
        // for updating eq data
        if (mParam2.getEquArray() != null && mParam2.getEquArray().size() > 0) {
            setEquipmentList(mParam2.getEquArray());
        }
    }

    @Override
    public void onEquipmentSelected(int position, EquArrayModel equipmentRes, boolean isAction) {
        Log.e("getAllEquipments", "JobEqRemark JobEquipmentActivity");
        Intent intent = new Intent(this, JobEquPartRemarkRemarkActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        String strEqu = new Gson().toJson(equipmentRes);
        intent.putExtra("equipment", strEqu);
        intent.putExtra("jobId", jobId);
        intent.putExtra("cltId", cltId);
        intent.putExtra("positions", position);
        intent.putExtra("isGetData", "");
        intent.putExtra("isAction", isAction);
        startActivity(intent);
        finish();
    }

    @Override
    public void updateReqItemList(String api_name, String message, AddUpdateRequestedModel requestedModel) {
        switch (api_name) {
            case Service_apis.addItemRequest:
                EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey(message.trim()));
                requested_item_txt.setClickable(true);
                requested_item_flag.setVisibility(View.VISIBLE);
//                requested_itemList_show_hide_rl.setVisibility(View.VISIBLE);
                requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.down), null, null, null);
                if (requestedModel != null) {
                    String msg =
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_requested_by_the_field_user) + "\n" + LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_name) + ": " + requestedModel.getItemName() + "\n" +
                                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty) + ": " + requestedModel.getQty() + "\n" +
                                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.part_no) + ": " + requestedModel.getModelNo() + "\n" +
                                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand) + ": " + requestedModel.getBrandName();
                    Chat_Send_Msg_Model chat_send_Msg_model = new Chat_Send_Msg_Model(
                            msg, "", AppUtility.getDateByMiliseconds(),
                            requestedModel.getJobLabel(),
                            requestedModel.getJobId(), "1");
                    AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateRequestedItem("1", requestedModel.getJobId());
                    if (jobEquimPi != null) {
                        jobEquimPi.sendMsg(chat_send_Msg_model);
                    }
                }
                break;
            case Service_apis.updateItemRequest:
                EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey(message.trim()));
                if (requestedModel != null) {
                    String msg =
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.field_user_made_some_changes_on_the_requested_item) + "\n" + LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_name) + ": " + requestedModel.getItemName() + "\n" +
                                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty) + ": " + requestedModel.getQty() + "\n" +
                                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.part_no) + ": " + requestedModel.getModelNo() + "\n" +
                                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand) + ": " + requestedModel.getBrandName();
                    Chat_Send_Msg_Model chat_send_Msg_model = new Chat_Send_Msg_Model(
                            msg, "", AppUtility.getDateByMiliseconds(),
                            requestedModel.getJobLabel(),
                            requestedModel.getJobId(), "1");
                    if (jobEquimPi != null) {
                        jobEquimPi.sendMsg(chat_send_Msg_model);
                    }
                }
                break;
        }
    }

    @Override
    public void itemDelete(String irId, RequestedItemModel requestedModel) {
        if (AppUtility.isInternetConnected()) {
            progressBar_itemRequest.setVisibility(View.VISIBLE);
            AddUpdateRequestedModel requestedModel1 = new AddUpdateRequestedModel(requestedModel.getInm(), requestedModel.getEbId(), requestedModel.getQty(),
                    requestedModel.getModelNo(), "", requestedModel.getItemId(), mParam2.getJobId());
            jobEquimPi.deleteRequestedItem(irId, mParam2.getJobId(), requestedModel1);
        }
    }

    @Override
    public void itemSelected(RequestedItemModel updateRequestedItemModel) {
//        show_requested_list.setVisibility(View.VISIBLE);
        recyclerView_requested_item.setVisibility(View.GONE);
        requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.down), null, null, null);
//        hide_requested_list.setVisibility(View.GONE);
        Intent intent = new Intent(this, AddUpdateRquestedItemActivity.class);
        intent.putExtra("updateSelectedReqItem", updateRequestedItemModel);
        intent.putExtra("UpdateReqItem", true);
        intent.putExtra("jobId", mParam2.getJobId());
        intent.putExtra("jobLabel", mParam2.getLabel());
        intent.putExtra("equId", equipment.getEquId());
        startActivity(intent);
    }

    /**
     * Due to  separate edit remark section at 19 Mar 24, We intorduce new below method for hide and show View's
     * showRemarkSection :- For showing Remark
     * hideRemarkSection :- For editing Remark
     */
    public void showRemarkSection() {
        cv_showRemark.setVisibility(View.GONE);
        part_cardview.setVisibility(View.GONE);
        item_cardview.setVisibility(View.GONE);
        ll_replace.setVisibility(View.GONE);
        ll_repair.setVisibility(View.GONE);
        ll_reallocate.setVisibility(View.GONE);
        ll_discard.setVisibility(View.GONE);
        txt_status.setVisibility(View.GONE);
        ll_requested_item.setVisibility(View.GONE);
        attachment_card.setVisibility(View.VISIBLE);
        cv_editRemark.setVisibility(View.VISIBLE);
    }

    public void hideRemarkSection() {
        cv_showRemark.setVisibility(View.VISIBLE);
        part_cardview.setVisibility(View.GONE);
        item_cardview.setVisibility(View.VISIBLE);
        ll_reallocate.setVisibility(View.VISIBLE);
        ll_repair.setVisibility(View.VISIBLE);
        if (txt_status.getText().toString().equalsIgnoreCase("Discarded")) {
            ll_replace.setVisibility(View.VISIBLE);
            ll_discard.setVisibility(View.VISIBLE);
            tv_replace.setEnabled(false);
            tv_discard.setEnabled(false);
            tv_discard.setBackgroundResource(R.drawable.disable_submit_btn);
            tv_replace.setBackgroundResource(R.drawable.disable_submit_btn);
            tv_text_for_replace.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discard_equipments_not_replaced));
            tv_text_for_discard.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discard_equipments_not_discarded_again));
        } else {
            ll_repair.setVisibility(View.VISIBLE);
            ll_reallocate.setVisibility(View.VISIBLE);
            ll_replace.setVisibility(View.VISIBLE);
            ll_discard.setVisibility(View.VISIBLE);
            tv_replace.setEnabled(true);
            tv_discard.setEnabled(true);
            tv_discard.setBackgroundResource(R.drawable.submit_btn);
            tv_replace.setBackgroundResource(R.drawable.submit_btn);
            tv_text_for_replace.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.do_you_want_to_discard));
            tv_text_for_discard.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discard_action_msg));
        }
        txt_status.setVisibility(View.VISIBLE);
        if (App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsItemRequested() == 0) {
            ll_requested_item.setVisibility(View.VISIBLE);
            if (mParam2.getItemRequested() != null && mParam2.getItemRequested().equals("1")) {
                requested_item_flag.setVisibility(View.VISIBLE);
                requested_item_txt.setClickable(true);
//                requested_itemList_show_hide_rl.setVisibility(View.VISIBLE);
                requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.down), null, null, null);
            } else {
                requested_item_flag.setVisibility(View.GONE);
                requested_item_txt.setClickable(false);
//                requested_itemList_show_hide_rl.setVisibility(View.GONE);
                requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        } else {
            ll_requested_item.setVisibility(View.GONE);
        }
        attachment_card.setVisibility(View.GONE);
        cv_editRemark.setVisibility(View.GONE);
    }

}