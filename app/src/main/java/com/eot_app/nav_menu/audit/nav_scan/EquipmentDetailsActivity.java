package com.eot_app.nav_menu.audit.nav_scan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.eot_app.R;
import com.eot_app.UploadDocumentActivity;
import com.eot_app.login_next.login_next_model.CompPermission;
import com.eot_app.nav_menu.audit.audit_list.audit_mvp.model.AuditList_Res;
import com.eot_app.nav_menu.audit.audit_list.equipment.model.Equipment_Res;
import com.eot_app.nav_menu.equipment.View.AuditDetailEquActivity;
import com.eot_app.nav_menu.equipment.View.JobdetailsEquActivity;
import com.eot_app.nav_menu.equipment.adpter.AdpterAuditHistory;
import com.eot_app.nav_menu.equipment.adpter.AdpterJobHistory;
import com.eot_app.nav_menu.equipment.history_mvp.Audit_Job_History_View;
import com.eot_app.nav_menu.equipment.history_mvp.Audit_Job_History_pc;
import com.eot_app.nav_menu.equipment.history_mvp.Audit_Job_History_pi;
import com.eot_app.nav_menu.equipment.model.aduit_job_history.Aduit_Job_History_Res;
import com.eot_app.nav_menu.jobs.add_job.Add_job_activity;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.InvoiceItemList2Adpter;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.EquipmentPartRemarkAdapter;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.RoundedImageView;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.settings.equipmentdb.Equipment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EquipmentDetailsActivity extends UploadDocumentActivity implements View.OnClickListener,
        Audit_Job_History_View, AdpterAuditHistory.OnAuditSelection, AdpterJobHistory.OnJobSelection {
    TextView equipment_name, barnd_name, model_no, serial_no,
            traiff_rate, warrenty_expiry_date, manufacture_date,
            purchase_date, installed_date, type, equipment_group;
    TextView barnd_name_detail, model_no_detail, serial_no_detail,
            traiff_rate_detail,
            warrenty_expiry_date_detail, manufacture_date_detail, purchase_date_detail, install_date_detail,
            type_detail, equipment_group_detail;
    RoundedImageView profile_img;
    InvoiceItemList2Adpter invoice_list_adpter;
    Button button_job, button_audit, go_to_addjob;
    LinearLayoutManager layoutManager, layoutManager1;
    private LinearLayout ll_provider;
    TextView tvUploadBarcode, tvNoteUploadBarcode, last_serv_date, serv_due_date_label,serv_due_date,last_serv_date_lable, equ_bar_code_num_lable, equ_bar_code_num_txt;
    private String equipmentID, path;
    private Audit_Job_History_pi equ_details_pc;
    private AdpterAuditHistory adapterAuditList;
    private AdpterJobHistory adpterJobList;
    RecyclerView auditList;
    RecyclerView jobList;
    private RecyclerView recyclerView_part;
    private RecyclerView recyclerView_item;
    private TextView aduit_history_txt, job_history_txt;
    private List<AuditList_Res> auditlist = new ArrayList<>();
    private List<Job> joblist = new ArrayList<>();
    CardView part_cardview, item_cardview;
    EquipmentPartRemarkAdapter equipmentPartAdapter;
    AppCompatTextView tv_network_error, tv_label_part, tv_label_item;
    private LinearLayout ll_audit_job;
    private LinearLayout job_ll, audit_ll;
    TextView custom_filed_1, custom_filed_2,
            custom_filed_txt_1, custom_filed_txt_2,supplier_txt,supplier;
    private boolean REFRESH = false;
    private String cltId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_equ_details));
        initializelables();

        if (getIntent().hasExtra("equipment")) {
            if (getIntent().hasExtra("job_equip")) {
                String str = getIntent().getExtras().getString("job_equip_str");
                EquArrayModel equipment = new Gson().fromJson(str, EquArrayModel.class);
                setJobEquipment(equipment);
                equ_details_pc.getEquipmentAduitHistory(equipment.getEquId());
                equ_details_pc.getEquipmentJobHistory(equipment.getEquId());
                equ_details_pc.getEqItemFromServer(equipment.getEquId(),getIntent().getStringExtra("jobId"));
                equ_details_pc.getEqPartsFromServer(equipment.getEquId());

            } else if (getIntent().hasExtra("audit_equip")) {
                String str = getIntent().getExtras().getString("audit_equip_str");
                Equipment_Res equipment = new Gson().fromJson(str, Equipment_Res.class);
                setAuditEquipment(equipment);
                equ_details_pc.getEquipmentAduitHistory(equipment.getEquId());
                equ_details_pc.getEquipmentJobHistory(equipment.getEquId());
                equ_details_pc.getEqItemFromServer(equipment.getEquId(),null);
                equ_details_pc.getEqPartsFromServer(equipment.getEquId());

            } else if (getIntent().hasExtra("equipment_id")) {
                Equipment equipment = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().getEquipmentById(getIntent().getStringExtra("equipment_id"));
                setEquipmentDetails(equipment);
                equ_details_pc.getEquipmentAduitHistory(equipment.getEquId());
                equ_details_pc.getEquipmentJobHistory(equipment.getEquId());
                equ_details_pc.getEqItemFromServer(equipment.getEquId(),null);
                equ_details_pc.getEqPartsFromServer(equipment.getEquId());

            }
            button_audit.setVisibility(View.GONE);
            button_job.setVisibility(View.GONE);
        } else
            setData();
    }

    @Override
    public void finishErroroccur(String msg) {
        showDilaog(msg);
    }

    private void initializelables() {
        custom_filed_1 = findViewById(R.id.custom_filed_1);
        custom_filed_2 = findViewById(R.id.custom_filed_2);
        custom_filed_txt_1 = findViewById(R.id.custom_filed_txt_1);
        custom_filed_txt_2 = findViewById(R.id.custom_filed_txt_2);
        supplier_txt = findViewById(R.id.supplier_txt);
        supplier_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.supplier));

        supplier = findViewById(R.id.supplier);

        if (App_preference.getSharedprefInstance().getCompanySettingsDetails().getEqupExtraField1Label() != null)
            custom_filed_1.setText(App_preference.getSharedprefInstance().getCompanySettingsDetails().getEqupExtraField1Label());

        if (App_preference.getSharedprefInstance().getCompanySettingsDetails().getEqupExtraField2Label() != null)
            custom_filed_2.setText(App_preference.getSharedprefInstance().getCompanySettingsDetails().getEqupExtraField2Label());


        audit_ll = findViewById(R.id.audit_ll);
        job_ll = findViewById(R.id.job_ll);
        audit_ll = findViewById(R.id.audit_ll);
        job_ll = findViewById(R.id.job_ll);

        equ_details_pc = new Audit_Job_History_pc(this);

        ll_audit_job = findViewById(R.id.ll_audit_job);
        tv_network_error = findViewById(R.id.tv_network_error);

        aduit_history_txt = findViewById(R.id.aduit_history_txt);
        job_history_txt = findViewById(R.id.job_history_txt);

        auditList = findViewById(R.id.audit_list);
        jobList = findViewById(R.id.job_list);

        auditList.setNestedScrollingEnabled(false);
        jobList.setNestedScrollingEnabled(false);

        layoutManager = new LinearLayoutManager(this);
        auditList.setLayoutManager(layoutManager);

        layoutManager1 = new LinearLayoutManager(this);
        jobList.setLayoutManager(layoutManager1);

        adapterAuditList = new AdpterAuditHistory(this);
        adapterAuditList.setOnAuditSelection(this);
        auditList.setAdapter(adapterAuditList);


        adpterJobList = new AdpterJobHistory(this);
        adpterJobList.setOnJobSelection(this);
        jobList.setAdapter(adpterJobList);


        tvUploadBarcode = findViewById(R.id.tv_upload_barcode);
        tvNoteUploadBarcode = findViewById(R.id.tv_barcode_note);

        tvUploadBarcode.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.capture_barcode));
        tvNoteUploadBarcode.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.barcode_capture_note));

        tvUploadBarcode.setOnClickListener(v -> {
            Intent intent = new Intent(EquipmentDetailsActivity.this, UploadBarcodeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent.putExtra("equipmentId", equipmentID));
        });

        button_job = findViewById(R.id.button_job);
        button_audit = findViewById(R.id.button_audit);
        go_to_addjob = findViewById(R.id.go_to_addjob);

        button_job.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.go_to_job));
        button_audit.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.go_to_audit));
        go_to_addjob.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.go_to_addjob));

        equipment_name = findViewById(R.id.equipment_name);
        profile_img = findViewById(R.id.profile_img);


        barnd_name = findViewById(R.id.barnd_name);
        barnd_name.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand_name));


        serv_due_date= findViewById(R.id.serv_due_date);
        serv_due_date_label = findViewById(R.id.serv_due_date_label);
        serv_due_date_label.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.service_due_date));

        model_no = findViewById(R.id.model_no);
        model_no.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.model_no));

        serial_no = findViewById(R.id.serial_no);
        serial_no.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.serial_no));

        traiff_rate = findViewById(R.id.traiff_rate);
        traiff_rate.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.tariff_rate));


        warrenty_expiry_date = findViewById(R.id.warrenty_expiry_date);
        warrenty_expiry_date.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.warranty_expiry_date));

        manufacture_date = findViewById(R.id.manufacture_date);
        manufacture_date.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.manufacture_date));

        purchase_date = findViewById(R.id.purchase_date);
        purchase_date.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.purchase_date));

        installed_date = findViewById(R.id.installed_date);
        installed_date.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.installed_date));

        type = findViewById(R.id.type);
        type.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.type));

        equipment_group = findViewById(R.id.equipment_group);
        equipment_group.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_group));

        barnd_name_detail = findViewById(R.id.barnd_name_detail);
        model_no_detail = findViewById(R.id.model_no_detail);
        serial_no_detail = findViewById(R.id.serial_no_detail);
        traiff_rate_detail = findViewById(R.id.traiff_rate_detail);
        warrenty_expiry_date_detail = findViewById(R.id.warrenty_expiry_date_detail);
        manufacture_date_detail = findViewById(R.id.manufacture_date_detail);
        purchase_date_detail = findViewById(R.id.purchase_date_detail);
        install_date_detail = findViewById(R.id.install_date_detail);

        type_detail = findViewById(R.id.type_detail);
        equipment_group_detail = findViewById(R.id.equipment_group_detail);

        ll_provider = findViewById(R.id.ll_provider);


        last_serv_date = findViewById(R.id.last_serv_date);
        last_serv_date_lable = findViewById(R.id.last_serv_date_lable);
        last_serv_date.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.last_serv_date));


        equ_bar_code_num_lable = findViewById(R.id.equ_bar_code_num_lable);
        equ_bar_code_num_txt = findViewById(R.id.equ_bar_code_num_txt);
        equ_bar_code_num_lable.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.barcode_num));

        button_job.setOnClickListener(this);
        button_audit.setOnClickListener(this);
        go_to_addjob.setOnClickListener(this);


        item_cardview = findViewById(R.id.item_cardview);
        part_cardview = findViewById(R.id.part_cardview);
        tv_label_part = findViewById(R.id.tv_label_part);
        tv_label_part.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_parts));

        tv_label_item = findViewById(R.id.tv_label_item);
        tv_label_item.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_items));

        recyclerView_part = findViewById(R.id.recyclerView_part);
        recyclerView_item = findViewById(R.id.recyclerView_item);


        ShowHideEqupHistory();
    }


    private void ShowHideEqupHistory() {
        CompPermission compPermission = App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0);
        if (compPermission.getEqupHistory() != null) {
            if (compPermission.getEqupHistory().equals("0")) {
                audit_ll.setVisibility(View.VISIBLE);
                job_ll.setVisibility(View.VISIBLE);
            } else {
                audit_ll.setVisibility(View.GONE);
                job_ll.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (REFRESH) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (path != null && !path.equals("")) {
            menu.findItem(R.id.menu_add_user_manual).setVisible(false);
            try {
                File file1 = new File(path);
                String setTextInItemmenu = LanguageController.getInstance().getMobileMsgByKey(AppConstant.view_user_mannual)
                        + " (" + file1.getName() + ")";
                menu.findItem(R.id.menu_view_user_manual).setTitle(setTextInItemmenu);
            } catch (Exception ex) {
                menu.findItem(R.id.menu_view_user_manual).setTitle(LanguageController.
                        getInstance().getMobileMsgByKey(AppConstant.view_user_mannual));
            }
        } else {
            menu.findItem(R.id.menu_add_user_manual).setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_user_mannual));
            menu.findItem(R.id.menu_view_user_manual).setVisible(false);
        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.menu_add_user_manual) {
            getDocumentsFromGalleryNotImage();
        } else if (item.getItemId() == R.id.menu_view_user_manual) {
            openAttchmentOnBrowser();
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.equi_details_menu, menu);
        return true;
    }

    private void setData() {
        String jobData = "";
        if (getIntent().hasExtra("JOBDATA")) {
            try {
                jobData = (getIntent().getExtras().getString("JOBDATA"));//new Gson().toJson
                Type listType = new TypeToken<List<Job>>() {
                }.getType();
                joblist = new Gson().fromJson(jobData, listType);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }else if (getIntent().hasExtra("jobids")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Type listType = new TypeToken<List<String>>() {
                        }.getType();
                        List<String> jobids=  new Gson().fromJson(getIntent().getStringExtra("jobids"), listType);
                        joblist = AppDataBase.getInMemoryDatabase(getApplication()).jobModel().getjobsbyjobids(jobids);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        String auditData;
        try {
            auditData = (getIntent().getExtras().getString("AUDITDATA"));// new Gson().toJson
            Type listType = new TypeToken<List<AuditList_Res>>() {
            }.getType();
            auditlist = new Gson().fromJson(auditData, listType);
        } catch (Exception exception) {
            exception.printStackTrace();
        }


        String codeText = getIntent().getStringExtra("codetext");
        if (auditlist != null && auditlist.size() > 0) {
            List<Equipment_Res> equArray = auditlist.get(0).getEquArray();
            if (equArray != null) {
                for (Equipment_Res equipment : equArray) {
                    if (equipment.getSno() != null && equipment.getSno().equals(codeText) ||
                            equipment.getBarcode() != null && equipment.getBarcode().equals(codeText)) {
                        setAuditEquipment(equipment);
                        equ_details_pc.getEquipmentAduitHistory(equipment.getEquId());
                        equ_details_pc.getEquipmentJobHistory(equipment.getEquId());
                        equ_details_pc.getEqItemFromServer(equipment.getEquId(),null);
                        equ_details_pc.getEqPartsFromServer(equipment.getEquId());
                        break;
                    }
                }
            }
        } else if (joblist != null && joblist.size() > 0) {
            List<EquArrayModel> equArrayModelList = joblist.get(0).getEquArray();
            if (equArrayModelList != null) {
                for (EquArrayModel equipment : equArrayModelList) {
                    if (equipment.getSno() != null && equipment.getSno().equals(codeText) ||
                            equipment.getBarcode() != null && equipment.getBarcode().equals(codeText)) {
                        setJobEquipment(equipment);
                        equ_details_pc.getEquipmentAduitHistory(equipment.getEquId());
                        equ_details_pc.getEquipmentJobHistory(equipment.getEquId());
                        equ_details_pc.getEqItemFromServer(equipment.getEquId(),joblist.get(0).getJobId());
                        equ_details_pc.getEqPartsFromServer(equipment.getEquId());
                        break;
                    }
                }
            }
        }

        if (joblist == null || joblist.size() == 0)
            button_job.setVisibility(View.GONE);

        if (auditlist == null || auditlist.size() == 0)
            button_audit.setVisibility(View.GONE);


        if (App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsJobAddOrNot() != 0) {//0
            go_to_addjob.setVisibility(View.GONE);
        } else {
            go_to_addjob.setVisibility(View.VISIBLE);
        }
    }

    private void setJobEquipment(EquArrayModel equipment) {
        equipmentID = equipment.getEquId();
        equipment_name.setText(equipment.getEqunm());

        barnd_name_detail.setText(equipment.getBrand());
        model_no_detail.setText(equipment.getMno());
        serial_no_detail.setText(equipment.getSno());
        custom_filed_txt_1.setText(equipment.getExtraField1());
        custom_filed_txt_2.setText(equipment.getExtraField2());
        supplier.setText(equipment.getSupplier());
        try {
            if (TextUtils.isEmpty(equipment.getRate()))
                traiff_rate_detail.setText("");
            else
                traiff_rate_detail.setText(AppUtility.getRoundoff_amount(String.valueOf(equipment.getRate())));
        } catch (Exception e) {
            e.printStackTrace();
        }


        equipment_group_detail.setText(equipment.getEquipment_group());


        setDateInView(warrenty_expiry_date_detail, equipment.getExpiryDate());
        setDateInView(manufacture_date_detail, equipment.getManufactureDate());
        setDateInView(purchase_date_detail, equipment.getPurchaseDate());
        setDateInView(install_date_detail, equipment.getInstalledDate());

        if (!TextUtils.isEmpty(equipment.getType())) {
            if (equipment.getType().equals("2"))
                ll_provider.setVisibility(View.GONE);
            else ll_provider.setVisibility(View.VISIBLE);
            type_detail.setText(getEquipmentType(equipment.getType()));
        }

        try {
            if (equipment.getLastJobDate() != null && !TextUtils.isEmpty(equipment.getLastJobDate())) {
                last_serv_date_lable.setText(AppUtility.getDateWithFormate(Long.parseLong(equipment.getLastJobDate()), "dd-MMM-yyyy hh:mm"));
            }
            // calculate service due date with the help of service interval value and type
            // days=0
            //month=1
            //year=2
            if(equipment.getServIntvalType()!=null&&equipment.getServIntvalValue()!=null)
                serv_due_date.setText(AppUtility.getServiceDueDate(equipment.getServIntvalType(),equipment.getServIntvalValue(), Long.parseLong(equipment.getLastJobDate())));

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (!TextUtils.isEmpty(equipment.getImage()))
            Glide.with(this).load(App_preference.getSharedprefInstance().getBaseURL() + equipment.getImage())
                    .thumbnail(Glide.with(this).load(R.raw.loader_eot)).placeholder(R.drawable.app_logo2).into(profile_img);


        if (!TextUtils.isEmpty(equipment.getUsrManualDoc())) {
            path = equipment.getUsrManualDoc();
        }

        try {
            if (equipment != null && equipment.getBarcode() != null)
                equ_bar_code_num_txt.setText(equipment.getBarcode());
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    private void setAuditEquipment(Equipment_Res equipment) {
        equipmentID = equipment.getEquId();
        equipment_name.setText(equipment.getEqunm());
        barnd_name_detail.setText(equipment.getBrand());
        model_no_detail.setText(equipment.getMno());
        serial_no_detail.setText(equipment.getSno());
        custom_filed_txt_1.setText(equipment.getExtraField1());
        custom_filed_txt_2.setText(equipment.getExtraField2());
        supplier.setText(equipment.getSupplier());
        try {
            if (TextUtils.isEmpty(equipment.getRate()))
                traiff_rate_detail.setText("");
            else
                traiff_rate_detail.setText(AppUtility.getRoundoff_amount(String.valueOf(equipment.getRate())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        equipment_group_detail.setText(equipment.getEquipment_group());


        setDateInView(warrenty_expiry_date_detail, equipment.getExpiryDate());
        setDateInView(manufacture_date_detail, equipment.getManufactureDate());
        setDateInView(purchase_date_detail, equipment.getPurchaseDate());
        setDateInView(install_date_detail, equipment.getInstalledDate());


        if (!TextUtils.isEmpty(equipment.getType())) {
            if (equipment.getType().equals("2"))
                ll_provider.setVisibility(View.GONE);
            else ll_provider.setVisibility(View.VISIBLE);
            type_detail.setText(getEquipmentType(equipment.getType()));
        }

        if (!TextUtils.isEmpty(equipment.getImage()))
            Glide.with(this).load(App_preference.getSharedprefInstance().getBaseURL() + equipment.getImage())
                    .thumbnail(Glide.with(this).load(R.raw.loader_eot)).placeholder(R.drawable.app_logo2).into(profile_img);

        if (!TextUtils.isEmpty(equipment.getUsrManualDoc())) {
            path = equipment.getUsrManualDoc();
        }

        try {
            if (equipment.getLastJobDate() != null && !TextUtils.isEmpty(equipment.getLastJobDate())) {
                last_serv_date_lable.setText(AppUtility.getDateWithFormate(Long.parseLong(equipment.getLastJobDate()), "dd-MMM-yyyy hh:mm"));

                // calculate service due date with the help of service interval value and type
                // days=0
                //month=1
                //year=2
                if(equipment.getServIntvalType()!=null&&equipment.getServIntvalValue()!=null)
                    serv_due_date.setText(AppUtility.getServiceDueDate(equipment.getServIntvalType(),equipment.getServIntvalValue(), Long.parseLong(equipment.getLastJobDate())));

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        try {
            if (equipment != null && equipment.getBarcode() != null)
                equ_bar_code_num_txt.setText(equipment.getBarcode());
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }


    /**
     * equipment details which is not linked with any job or audit
     */
    private void setEquipmentDetails(Equipment equipment) {
        equipmentID = equipment.getEquId();
        equipment_name.setText(equipment.getEqunm());
        barnd_name_detail.setText(equipment.getBrand());
        model_no_detail.setText(equipment.getMno());
        serial_no_detail.setText(equipment.getSno());
        custom_filed_txt_1.setText(equipment.getExtraField1());
        custom_filed_txt_2.setText(equipment.getExtraField2());
        supplier.setText(equipment.getSupplier());
        try {
            if (TextUtils.isEmpty(equipment.getRate()))
                traiff_rate_detail.setText("");
            else
                traiff_rate_detail.setText(AppUtility.getRoundoff_amount(String.valueOf(equipment.getRate())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        equipment_group_detail.setText(equipment.getGroupName());


        setDateInView(warrenty_expiry_date_detail, equipment.getExpiryDate());
        setDateInView(manufacture_date_detail, equipment.getManufactureDate());
        setDateInView(purchase_date_detail, equipment.getPurchaseDate());
        setDateInView(install_date_detail, equipment.getInstalledDate());


        if (!TextUtils.isEmpty(equipment.getType())) {
            if (equipment.getType().equals("2"))
                ll_provider.setVisibility(View.GONE);
            else ll_provider.setVisibility(View.VISIBLE);
            type_detail.setText(getEquipmentType(equipment.getType()));
        }

        if (!TextUtils.isEmpty(equipment.getImage()))
            Glide.with(this).load(App_preference.getSharedprefInstance().getBaseURL() + equipment.getImage())
                    .thumbnail(Glide.with(this).load(R.raw.loader_eot)).placeholder(R.drawable.app_logo2).into(profile_img);


        if (!TextUtils.isEmpty(equipment.getUsrManualDoc())) {
            path = equipment.getUsrManualDoc();
        }

        if (equipment.getCltId() != null && !TextUtils.isEmpty(equipment.getCltId())) {
            cltId = equipment.getCltId();
            if (App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsJobAddOrNot() != 0) {//0
                go_to_addjob.setVisibility(View.GONE);
            } else {
                go_to_addjob.setVisibility(View.VISIBLE);
            }
        }
        try {
            if (equipment != null && equipment.getBarcode() != null)
                equ_bar_code_num_txt.setText(equipment.getBarcode());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private String getEquipmentType(String type) {
        String typeLable = "";
        switch (type) {
            case "1":
                typeLable = LanguageController.getInstance().getMobileMsgByKey(AppConstant.my_equipment);
                break;
            case "2":
                typeLable = LanguageController.getInstance().getMobileMsgByKey(AppConstant.clients_eq);
                break;
        }

        return typeLable;
    }


    private void setDateInView(TextView tv, String dateString) {
        try {
            long longStartTime = Long.parseLong(dateString);
            String dateWithFormate = AppUtility.getDateWithFormate(longStartTime, "dd-MMM-yyyy");
            tv.setText(dateWithFormate);

        } catch (Exception ex) {
                ex.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_to_addjob:
                Intent open_add_job = new Intent(this, Add_job_activity.class);
                open_add_job.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                open_add_job.putExtra("equId", equipmentID);
                startActivity(open_add_job.putExtra("addJobByEquipment", cltId));
                break;
            case R.id.button_job:
                String jobData = "";
                if (getIntent().hasExtra("JOBDATA")) {
                    try {
                        jobData = (getIntent().getExtras().getString("JOBDATA"));//new Gson().toJson
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }else if (getIntent().hasExtra("jobids")){
                    try {
                        jobData=getIntent().getStringExtra("jobids");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                Intent jobintent = new Intent();
                jobintent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                jobintent.putExtra("JOBDATA", jobData);
                setResult(RESULT_OK, jobintent);
                finish();
                break;
            case R.id.button_audit:
                String auditData = "";
                try {
                    auditData = (getIntent().getExtras().getString("AUDITDATA"));// new Gson().toJson
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("AUDITDATA", auditData);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void openAttchmentOnBrowser() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(App_preference.getSharedprefInstance().getBaseURL() +
                "" + path)));
    }


    @Override
    public void uploadAttchView(String path) {
        this.path = path;
        REFRESH = true;
        invalidateOptionsMenu();

    }

    @Override
    public void onDocumentSelected(String path, String name, boolean isImage) {
        super.onDocumentSelected(path, name, isImage);
        equ_details_pc.getApiForUploadAttchment(equipmentID, path);
    }

    @Override
    public void onAuditSelected(String auditId) {
        if (auditId != null) {
            equ_details_pc.getEquipmentAduitDetails(auditId);
        }
    }

    @Override
    public void onJobSelected(String jobId) {
        if (jobId != null) {
            equ_details_pc.getEquipmentJobDetails(jobId);
        }
    }

    @Override
    public void setEquipmentAduitList(List<Aduit_Job_History_Res> aduit_res) {
        if (aduit_res != null && aduit_res.size() > 0) {
            adapterAuditList.setList(aduit_res);
            ll_audit_job.setVisibility(View.VISIBLE);
            tv_network_error.setVisibility(View.GONE);
        }
    }

    @Override
    public void setEquipmentJobList(List<Aduit_Job_History_Res> aduit_res) {
        if (aduit_res != null && aduit_res.size() > 0) {
            adpterJobList.setList(aduit_res);
            ll_audit_job.setVisibility(View.VISIBLE);
            tv_network_error.setVisibility(View.GONE);
        }
    }

    @Override
    public void setEquipmentItemList(List<InvoiceItemDataModel> data,String jobId) {
        if (data != null) {
            if (data.size() > 0) {
                item_cardview.setVisibility(View.VISIBLE);
            } else {
                item_cardview.setVisibility(View.GONE);
            }
            String getDisCalculationType;
            if (jobId!=null&&!jobId.isEmpty())
            {
                getDisCalculationType=AppDataBase.getInMemoryDatabase(this).jobModel().disCalculationType(jobId);
            }else{
                getDisCalculationType= App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
            }
            recyclerView_item.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL
                    , false));
            invoice_list_adpter = new InvoiceItemList2Adpter(this,data, true, true,getDisCalculationType);//, this, this
            recyclerView_item.setAdapter(invoice_list_adpter);
            recyclerView_item.setNestedScrollingEnabled(false);
        }
        else {
            item_cardview.setVisibility(View.GONE);
        }
    }

    @Override
    public void setEquipmentPartList(List<EquArrayModel> data) {
        if (data != null) {
            if (data.size() > 0) {
                part_cardview.setVisibility(View.VISIBLE);
            } else {
                part_cardview.setVisibility(View.GONE);
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView_part.setLayoutManager(linearLayoutManager);
            equipmentPartAdapter = new EquipmentPartRemarkAdapter(this, data);
            recyclerView_part.setAdapter(equipmentPartAdapter);
            recyclerView_part.setNestedScrollingEnabled(false);

        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getAduitSize(int size) {
        aduit_history_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_audit) + " (" + size + ")");

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getJobSize(int size) {
        job_history_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_service) + " (" + size + ")");
    }


    @Override
    public void setNetworkError(String message) {
        if (!TextUtils.isEmpty(message)) {
            ll_audit_job.setVisibility(View.GONE);
            tv_network_error.setVisibility(View.VISIBLE);
            tv_network_error.setText(message);
        }
    }

    @Override
    public void setJobDetails(Job job) {
        Intent intent = new Intent(this, JobdetailsEquActivity.class);
        intent.putExtra("Job_data", new Gson().toJson(job));
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    public void setAduditDetails(AuditList_Res auditList_res) {
        Intent intent = new Intent(this, AuditDetailEquActivity.class);
        intent.putExtra("audit_data", auditList_res);
        String audit_data_str = new Gson().toJson(auditList_res);
        intent.putExtra("audit_data_str", audit_data_str);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void showDilaog(String msg) {
        AppUtility.alertDialog(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert),
                msg,
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> true);
    }

    @Override
    public void sessionExpire(String msg) {
        AppUtility.alertDialog(this, LanguageController.getInstance().
                        getMobileMsgByKey(AppConstant.dialog_error_title),
                msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> {
                    EotApp.getAppinstance().sessionExpired();
                    return null;
                });
    }


}