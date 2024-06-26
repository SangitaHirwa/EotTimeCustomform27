package com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Ignore;

import com.eot_app.R;
import com.eot_app.login_next.FooterMenu;
import com.eot_app.login_next.login_next_model.CompPermission;
import com.eot_app.nav_menu.appointment.appointment_ItemData.AppointmentItemAdded_pi;
import com.eot_app.nav_menu.appointment.appointment_ItemData.AppointmentItemData_pc;
import com.eot_app.nav_menu.appointment.appointment_ItemData.AppointmentItemData_pi;
import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentAddItem_Res;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemAdd_RequestModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentUpdateItem_Req_Model;
import com.eot_app.nav_menu.appointment.dbappointment.Appointment;
import com.eot_app.nav_menu.appointment.details.RequirementGetheringListAdapter;
import com.eot_app.nav_menu.appointment.details.documents.AppointmentTax;
import com.eot_app.nav_menu.jobs.add_job.Add_job_activity;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.AddInvoiceItemReqModel;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.StockData;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.mvp.AddEditInvoiceItem_PC;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.mvp.AddEditInvoiceItem_PI;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.mvp.AddEditInvoiceItem_View;
import com.eot_app.nav_menu.jobs.job_detail.generate_invoice.invoice_adpter_pkg.EquipItemObserver;
import com.eot_app.nav_menu.jobs.job_detail.invoice.Auto_Inventry_Adpter;
import com.eot_app.nav_menu.jobs.job_detail.invoice.add_edit_invoice_pkg.dropdown_item_pkg.Auto_Fieldworker_Adpter;
import com.eot_app.nav_menu.jobs.job_detail.invoice.add_edit_invoice_pkg.dropdown_item_pkg.Services_item_Adapter;
import com.eot_app.nav_menu.jobs.job_detail.invoice.inventry_pkg.Inventry_ReS_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.inventry_pkg.ItemParts;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.Tax;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.TaxComponents;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.ItemListPartAdpter;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.AddJobEquipMentActivity;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.JobEquLinkItemActivity;
import com.eot_app.nav_menu.jobs.joboffline_db.JobOfflineDataModel;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.db.OfflineDataController;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.language_support.Language_Preference;
import com.eot_app.utility.settings.jobtitle.TaxData;
import com.eot_app.utility.settings.setting_db.FieldWorker;
import com.eot_app.utility.settings.setting_db.JobTitle;
import com.eot_app.utility.settings.setting_db.Offlinetable;
import com.eot_app.utility.util_interfaces.Callback_AlertDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.rpc.context.AttributeContext;
import com.hypertrack.hyperlog.HyperLog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import static com.eot_app.nav_menu.jobs.job_detail.invoice2list.JoBInvoiceItemList2Activity.ADD_ITEM_DATA;
import static com.eot_app.utility.AppConstant.add;
import static com.eot_app.utility.AppConstant.flat_discount;
import static com.eot_app.utility.AppConstant.item;
import static com.eot_app.utility.AppConstant.qty;
import static com.eot_app.utility.AppConstant.serial_no;


public class AddEditInvoiceItemActivity2 extends AppCompatActivity implements AddEditInvoiceItem_View, TextWatcher, View.OnFocusChangeListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener, EquipItemObserver {
    public static final int EQUIPMENTCONVERT = 201;
    public static final int EQUIPMENTLINK = 202;
    RelativeLayout ll_note, ll_link_note;
    View nm_view, desc_view, qty_view, rate_view, supplier_view, disc_view, tax_view, amount_view, part_no_view, hsncode_view, unit_view, taxrateAmount_view, seroal_no_view, tax_rate_view;
    TextView tax_value_txt, tax_txt_hint, amount_value_txt, taxamount_txt_hint, taxamount_value_txt, amount_txt_hint, txt_lbl_not_matchSerialNo, btn_not_matchSerialNo;
    List<ItemParts> partsList = new ArrayList<>();
    String comeFrom = "";
    EquArrayModel equipment;
    String equipmentId = "";
    String equipmentIdName = "";
    String equipmentType = "";
    RelativeLayout add_quote_item_layout;
    TextView text_default, tv_label_part, txt_lbl_link, btn_link_item;
    CardView ll_part_items;
    RecyclerView recyclerView_part_item;
    ItemListPartAdpter invoice_list_adpter;
    String warrantyType = "";
    String warrantyValue = "";
    TextView tvLabelStep1, tv_skip;
    String isGrouped = "";
    String cltId = "";
    private String isBillableChange = "";//,isBillableChange="";
    private String invId = "", locId = "0";
    private double taxAmount = 0;
    private AddEditInvoiceItem_PI invoiceItemPi;
    private InvoiceItemDataModel updateItemDataModel;
    private String inm, itemId, dataType, itemType = "";
    private float total_tax = 0f;
    private boolean IS_ITEM_MANDATRY, IS_FW_MANDATRY, IS_SERVICES_MANDATRY;
    private int TAB_SELECT = 1;
    private AutoCompleteTextView autocomplete_item;
    private List<Tax> listFilter = new ArrayList<>();
    private EditText edt_item_desc, edt_item_qty, edt_item_rate, edt_item_supplier, edt_item_disc, edt_part_no, edt_hsnCode, edt_unit, edt_serialNo, edt_item_tax_rate;
    private TextInputLayout item_desc_layout, item_qty_layout, item_rate_layout, item_discount_layout, itemlayout, item_hsnCode_layout, item_partNo_layout, item_unit_layout, item_supplier_layout, serialNo_layout, item_tax_rate_layout;
    private Button add_edit_item_Btn;
    private TextView item_select, fw_select, service_select;
    private LinearLayout layout_fw_item, taxamount_layout, amount_layout;
    private RelativeLayout tax_layout;
    private boolean DP_OPEN = false;
    private List<JobTitle> servicesItemList = new ArrayList<>();
    private List<FieldWorker> fieldWorkerList = new ArrayList<>();
    private List<Inventry_ReS_Model> itemsList = new ArrayList<>();
    private String jobId = "";
    private String appId = "";
    private String jtId = "";

    private String isBillable = "1";
    private boolean addItemOnInvoice;
    private Job jobModel;
    private Appointment appointmentModel;
    private Boolean appointmentItemCountForFlag = false;
    private TextView convert_item_to_equi, txt_serialNo_hint;
    private Boolean jobItemCountForFlag = false;
    private boolean updateItem = false;
    private boolean ITEMSYNC = false;
    private RadioGroup rediogrp;
    private RadioButton radio_billable, radio_none_billable;
    private boolean NOITEMRELECT = false, NONBILLABLE = false;
    private ImageButton tax_cancel;

    private boolean firstTimeOnPage = true;
    private String getTaxMethodType = "", getSingleTaxId = "0", getSingleTaxRate = "0";
    private String getDisCalculationType, getTaxCalculationType;

    public final static int ADD_REQUIRED_ITEM = 5;
    public final static int UPDATE_ITEM = 398;
    private List<InvoiceItemDataModel> itemDataList = new ArrayList<>();
    private Bundle bundle;
    private AppointmentItemData_pi appointmentItemData_pi;
    private AppointmentTax appointmentTax;
    private AppintmentItemDataModel updateAppointmentItemData;
    private int isPartParent = 0, isPartChild = 0;
    private AppintmentItemDataModel appintmentItemDataModel;
    private AppintmentItemDataModel updateAppintmentItemDataModel;
    private int appDataType;
    private int appitemType;
    private int appjtId;
    private String appInm = "";
    private AppointmentUpdateItem_Req_Model itemAddRequestModel;
    private AppointmentAddItem_Res updateItem_res;
    CheckBox add_stock_checkBox, chiled_add_stock_checkBox;
    String isRemoveStock = "1", scanCode = "";
    String stkusrId = "0";
    boolean show_stock_checkbox = false;
    boolean isTaxUpdated = false, isSerialNoSelected = false;
    public static AddEditInvoiceItemActivity2 addEditInvoiceItemActivity2;
    LinearLayout ll_serialNo, ll_below_rd_serialNo;
    RadioGroup rd_group_serialNo;
    String itemId1, ijmmId, serialNo, generateOption, equDefaultType;
    List<InvoiceItemDataModel> serialNoList = new ArrayList<>();
    Inventry_ReS_Model invetoryItemData = new Inventry_ReS_Model();
    InvoiceItemDataModel invoiceItemDataModelWithIjmmId = new InvoiceItemDataModel();
    ImageView ivCross;
    String stockQty = "";
    String userId = "0";
    boolean isShowStockAlert = false;
    boolean isFromQty = false;
    boolean isFromQtyCancel = false;
    StockData stockData;
    String lastUpdateQty ="";

    public AddEditInvoiceItemActivity2 getInstance() {

        return addEditInvoiceItemActivity2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EotApp.getAppinstance().setEquipItemObserver(null);
        addEditInvoiceItemActivity2 = this;
        setContentView(R.layout.activity_edit__invoice);
//        initializelables();
        appointmentItemData_pi = new AppointmentItemData_pc();
        bundle = getIntent().getExtras();
//        initializelables();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {
                if (getIntent().hasExtra("NONBILLABLE")) {
                    NONBILLABLE = bundle.getBoolean("NONBILLABLE");
                }
                // for adding item from equipment or adding equipment part with item
                if (getIntent().hasExtra("comeFrom")) {
                    comeFrom = bundle.getString("comeFrom");
                    EotApp.getAppinstance().setEquipItemObserver(this);
                    if (comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan")) {
                        initializelables();
                        edt_item_qty.setEnabled(false);
                        cltId = bundle.getString("cltId");
                        scanCode = bundle.getString("scanCode");
                        generateOption = bundle.getString("generateOption");
                        equDefaultType = bundle.getString("equDefaultType");
                        Log.e("Option", generateOption);
                        ll_note.setVisibility(View.VISIBLE);
                        ll_link_note.setVisibility(View.GONE);
                        layout_fw_item.setVisibility(View.GONE);
                        ll_serialNo.setVisibility(View.GONE);
                        ll_below_rd_serialNo.setVisibility(View.GONE);
                        isSerialNoSelected = true;
                    } else {
                        cltId = bundle.getString("cltId");
                        show_stock_checkbox = true;
                        if (getIntent().hasExtra("equipment")) {
                            String strEquipment = getIntent().getExtras().getString("equipment");
                            equipment = new Gson().fromJson(strEquipment, EquArrayModel.class);
                        }
//                        equipment = bundle.getString("equipment");
                        equipmentId = bundle.getString("equipmentId");
                        equipmentIdName = bundle.getString("equipmentIdName");
                        equipmentType = bundle.getString("equipmentType");
                        initializelables();
                        if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
                            edt_item_qty.setEnabled(false);
                            ll_note.setVisibility(View.VISIBLE);
                            ll_link_note.setVisibility(View.GONE);
                            layout_fw_item.setVisibility(View.GONE);
                            ll_serialNo.setVisibility(View.GONE);
                            ll_below_rd_serialNo.setVisibility(View.GONE);
                            isSerialNoSelected = true;
                        } else if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkItem")) {
                            ll_link_note.setVisibility(View.VISIBLE);
                        }
                        layout_fw_item.setVisibility(View.GONE);
                    }
                }
                if (getIntent().hasExtra("locId")) {
                    locId = bundle.getString("locId");
                } else {
                    locId = "0";
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                locId = "0";
            }

            /*Add item***/
            if (getIntent().hasExtra("jobId")) {
                jobId = bundle.getString("jobId");
                invId = bundle.getString("invId");
                show_stock_checkbox = true;
                getTaxDisType(jobId);
                initializelables();
                addItemOnInvoice = bundle.getBoolean("addItemOnInvoice");
                getTaxMethodType = bundle.getString("getTaxMethodType");
                if (getTaxMethodType.equals("1")) {
                    getSingleTaxId = bundle.getString("getSingleTaxId");
                    getSingleTaxRate = bundle.getString("getSingleTaxRate");
                }
                setDefaultValuesForAddNewItem();

                // for hiding in case of generate invoice
                if (addItemOnInvoice) rediogrp.setVisibility(View.GONE);
                else rediogrp.setVisibility(View.VISIBLE);


                /* we have to get a new tax items from the server */
                invoiceItemPi.getTaxList();
            } else if (getIntent().hasExtra("InvoiceItemDataModel")) {
                jobId = bundle.getString("edit_jobId");
                invId = bundle.getString("invId");
                show_stock_checkbox = true;
                getTaxDisType(jobId);
                initializelables();
                addItemOnInvoice = bundle.getBoolean("addItemOnInvoice");
                updateItemDataModel = bundle.getParcelable("InvoiceItemDataModel");
                getTaxMethodType = bundle.getString("getTaxMethodType");
                if (getTaxMethodType.equals("1")) {
                    getSingleTaxId = bundle.getString("getSingleTaxId");
                    getSingleTaxRate = bundle.getString("getSingleTaxRate");
                }
                Log.e("InvoiceItemDataModel1", new Gson().toJson(updateItemDataModel));
                invoiceItemPi.getTaxList();
                goneViewsForUpdate();
            }
            if (getIntent().hasExtra("AddRequirmentGetheringItem")) {
                appId = bundle.getString("appId");
                getTaxDisTypeForApp(appId);
                initializelables();
                getTaxMethodType = bundle.getString("getTaxMethodType");
                if (getTaxMethodType.equals("1")) {
                    getSingleTaxId = bundle.getString("getSingleTaxId");
                    getSingleTaxRate = bundle.getString("getSingleTaxRate");
                }
                setDefaultValuesForAddNewItem();
                invoiceItemPi.getTaxList();

            } else if (getIntent().hasExtra("UpdateItemRequirmentGethering")) {
                bundle.getBoolean("UpdateItemRequirmentGethering");
                appId = bundle.getString("appId");
                getTaxDisTypeForApp(appId);
                initializelables();
                getTaxMethodType = bundle.getString("getTaxMethodType");
                if (getTaxMethodType.equals("1")) {
                    getSingleTaxId = bundle.getString("getSingleTaxId");
                    getSingleTaxRate = bundle.getString("getSingleTaxRate");
                }
                updateAppointmentItemData = getIntent().getParcelableExtra("updateItemDataOfApp");

                invoiceItemPi.getTaxList();
                goneViewsForUpdateAppItem();
            }
            if (getIntent().hasExtra("AddRequirmentGetheringItem") || getIntent().hasExtra("UpdateItemRequirmentGethering")) {
                getAppointmentById();
            } else {
                getJobById();
            }
        } else {
            return;
        }


        set_Title();
        autocomplete_item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                handler.removeCallbacks(input_finish_checker);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
                    if (!autocomplete_item.getText().toString().isEmpty()) {
                        ivCross.setVisibility(View.VISIBLE);
                    } else {
                        ivCross.setVisibility(View.GONE);
                    }
                }

                if (autocomplete_item.getTag() == null) {
                    Log.e("EmptyTag", "");
                } else {
                    switch (autocomplete_item.getTag().toString()) {
                        case "Item":
                            Log.e("", "");
                            if (editable.length() >= 1) {
                                if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
                                    if (ll_serialNo.getVisibility() == View.VISIBLE) {
                                        ll_serialNo.setVisibility(View.GONE);
                                    } else if (ll_below_rd_serialNo.getVisibility() == View.VISIBLE) {
                                        ll_below_rd_serialNo.setVisibility(View.GONE);
                                    }
                                }
                                itemlayout.setHintEnabled(true);
                                IS_ITEM_MANDATRY = true;
                                if (itemsList != null && AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).invoiceItemDao().getInventryItemList().size() == 0 && editable.length() >= 3) {
                                    if (!AppUtility.isInternetConnected() && !ITEMSYNC) {
                                        showDialogForLoadData();
                                        ITEMSYNC = true;
                                    } else {
                                        invoiceItemPi.getDataFromServer(editable.toString());
                                    }
                                }
                            } else if (editable.length() <= 0) {
                                itemlayout.setHintEnabled(false);
                                IS_ITEM_MANDATRY = false;
                            }
                            // for checking user stopped typing or not
//                            last_text_edit = System.currentTimeMillis();
//                            handler.postDelayed(input_finish_checker, delay);


                            // ADDED this code in handler by shivani
                            assert itemsList != null;
                            if (!itemsList.contains(editable)) {
                                inm = editable.toString();
                                setEmptyFieldsNonInventry();
                                if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
                                    if (editable.length() > 3) {
                                        ll_below_rd_serialNo.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_below_rd_serialNo.setVisibility(View.GONE);
                                    }
                                }
                            }
                            break;
                        case "Fw":
                            if (editable.length() >= 1) {
                                itemlayout.setHintEnabled(true);
                            } else if (editable.length() <= 0) {
                                itemlayout.setHintEnabled(false);
                                IS_FW_MANDATRY = false;
                            }
                            if (!fieldWorkerList.contains(editable)) {
                                setEmptyFields();
                            }
                            Log.e("", "");
                            break;
                        case "Services":
                            if (editable.length() >= 1) {
                                itemlayout.setHintEnabled(true);
                            } else if (editable.length() <= 0) {
                                itemlayout.setHintEnabled(false);
                                IS_SERVICES_MANDATRY = false;
                            }
                            if (!servicesItemList.contains(editable)) {
                                setEmptyFields();
                            }
                            Log.e("", "");
                            break;
                    }
                }
                Log.e("", "");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

//        if (jobModel != null && jobModel.getItemData().size() > 0) {
//            for (InvoiceItemDataModel item : jobModel.getItemData()
//            ) {
//
//                if (item.getDataType() != null && item.getDataType().equalsIgnoreCase("1") && item.getEquId() != null && item.getEquId().equalsIgnoreCase("0")) {
//                    if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark")) {
//                        ll_link_note.setVisibility(View.VISIBLE);
//                        break;
//                    } else if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkItem")) {
//                        ll_link_note.setVisibility(View.VISIBLE);
//                        break;
//                    } else {
//                        ll_link_note.setVisibility(View.GONE);
//                    }
//
//                } else {
//                    ll_link_note.setVisibility(View.GONE);
//                }
//            }
//        } else {
//            ll_link_note.setVisibility(View.GONE);
//        }
    }

    /**
     * After discussion with Rani change validation of canInvoiceCreated by isJobInvoiced 12/04/2024
     **/
    private void getTaxDisType(String jobId) {
        if (jobId != null && !jobId.equals("")) {
            Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
            if (job.getIsJobInvoiced().equals("1")) {
                getDisCalculationType = AppDataBase.getInMemoryDatabase(this).jobModel().disCalculationType(jobId);
                getTaxCalculationType = AppDataBase.getInMemoryDatabase(this).jobModel().taxCalculationType(jobId);
            } else {
                getDisCalculationType = App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
                getTaxCalculationType = App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType();
            }
        } else {
            getDisCalculationType = App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
            getTaxCalculationType = App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType();
        }
    }

    private void getTaxDisTypeForApp(String appId) {
        if (appId != null && !appId.equals("")) {
            Appointment appointment = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().getAppointmentById(appId);
            if (appointment.getItemData().size() > 0) {
                if (appointment.getDisCalculationType() != null && !appointment.getDisCalculationType().equals("") && appointment.getTaxCalculationType() != null && !appointment.getTaxCalculationType().equals("")) {
                    getDisCalculationType = appointment.getDisCalculationType();
                    getTaxCalculationType = appointment.getTaxCalculationType();
                } else {
                    getDisCalculationType = App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
                    getTaxCalculationType = App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType();
                }
            } else {
                getDisCalculationType = App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
                getTaxCalculationType = App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType();
            }
        } else {
            getDisCalculationType = App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
            getTaxCalculationType = App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType();
        }
    }

    private void goneViewsForUpdateAppItem() {
        layout_fw_item.setVisibility(View.GONE);
        try {
            if (updateAppointmentItemData.getTax().size() > 0) {
                List<AppointmentTax> filterSelectedTaxList = updateAppointmentItemData.getTax();
                for (Tax taxModel : listFilter) {
                    for (AppointmentTax taxselect : filterSelectedTaxList) {
                        if (taxModel.getTaxId().equals(String.valueOf(taxselect.getTaxId()))) {
                            if (Double.parseDouble(taxModel.getRate()) != Double.parseDouble(taxselect.getRate())) {
                                taxModel.setAppliedTax(LanguageController.getInstance().getMobileMsgByKey(AppConstant.applied_tax) + " (" + taxselect.getRate() + " %)");
                                taxModel.setSelect(true);
                                total_tax += Float.parseFloat(taxselect.getRate());
                            } else {
                                taxModel.setSelect(true);
                                total_tax += Float.parseFloat(taxselect.getRate());
                            }
                            taxModel.setOldTax(taxselect.getRate());

                            updateItem = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setItemFieldsForApp();
    }

    private void setItemFieldsForApp() {
        tax_value_txt.setText((String.valueOf(total_tax)));
//        calculateTaxRate();

        /* type 1 for inventry item  */
        switch (updateAppointmentItemData.getDataType()) {
            // for equipment items

            case 4:
            case 0:
            case 1:
                autocomplete_item.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.item));
                setTxtBkgColor(1);
                fw_service_filed_hide(1);
                IS_ITEM_MANDATRY = true;
                TAB_SELECT = 1;
                break;
            /* type 2 for FieldWorker item  */
            case 2:
                setTxtBkgColor(2);
                autocomplete_item.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.fieldworkers_name));
                fw_service_filed_hide(2);
                IS_FW_MANDATRY = true;
                TAB_SELECT = 2;
                break;
            case 3:
                setTxtBkgColor(3);
                autocomplete_item.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.services_name));
                fw_service_filed_hide(3);
                IS_SERVICES_MANDATRY = true;
                TAB_SELECT = 3;
                break;

            /* type 6 for LABOUR item  */
            case 6:

                break;
        }


        if (!updateAppointmentItemData.getInm().equals(""))
            autocomplete_item.setText(updateAppointmentItemData.getInm());
        else autocomplete_item.setText(appointmentModel.getTempId());
        autocomplete_item.setFocusableInTouchMode(false);
        autocomplete_item.dismissDropDown();
        itemlayout.setHintEnabled(true);

        edt_item_qty.setText(updateAppointmentItemData.getQty());
        edt_item_rate.setText(AppUtility.getRoundoff_amount(updateAppointmentItemData.getRate()));
        edt_item_supplier.setText(AppUtility.getRoundoff_amount((updateAppointmentItemData.getSupplierCost())));
        edt_item_disc.setText(updateAppointmentItemData.getDiscount());
        edt_item_desc.setText(updateAppointmentItemData.getDes());
        edt_part_no.setText(updateAppointmentItemData.getPno());
        edt_hsnCode.setHint(App_preference.getSharedprefInstance().getLoginRes().getHsnCodeLable());
        edt_hsnCode.setText(updateAppointmentItemData.getHsncode());
        edt_unit.setText(updateAppointmentItemData.getUnit());
        edt_serialNo.setText(updateAppointmentItemData.getSerialNo());

        itemId = updateAppointmentItemData.getItemId();
        appDataType = updateAppointmentItemData.getDataType();
        appInm = updateAppointmentItemData.getInm();
        appitemType = updateAppointmentItemData.getItemType();
        appjtId = updateAppointmentItemData.getJtId();
        try {
            if (!NONBILLABLE && updateAppointmentItemData.getDataType() == 1) {
                if (updateAppointmentItemData.getIsBillable() != null) {
                    isBillable = updateAppointmentItemData.getIsBillable();
                }
                if (updateAppointmentItemData.getIsBillableChange() != null)
                    isBillableChange = updateAppointmentItemData.getIsBillableChange();
                if (isBillable.equals("1")) {
                    radio_billable.setChecked(true);
                    radio_none_billable.setChecked(false);
                } else if (isBillable.equals("0")) {
                    radio_none_billable.setChecked(true);
                    radio_billable.setChecked(false);
                } else {
                    radio_none_billable.setChecked(false);
                    radio_billable.setChecked(false);
                }
            } else {
                rediogrp.setVisibility(View.GONE);
                isBillable = updateAppointmentItemData.getIsBillable();
                isBillableChange = updateAppointmentItemData.getIsBillableChange();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        firstTimeOnPage = false;
        total_Amount_cal();
        calculateTaxRate();
    }

    private void getAppointmentById() {
        appointmentModel = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().getAppointmentById(appId);
        if (appointmentModel == null) {
            HyperLog.i("", "getJobById(M) job model null found");
            finish();
        } else if (appointmentModel.getItemData() != null && appointmentModel.getItemData().size() == 0) {
            appointmentItemCountForFlag = true;
        }
    }

    private void showDialogForLoadData() {
        AppUtility.alertDialog2(this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_sync), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel), new Callback_AlertDialog() {
            @Override
            public void onPossitiveCall() {
                try {
                    if (AppUtility.isInternetConnected()) {
                        invoiceItemPi.getDataFromServer(autocomplete_item.getText().toString());
                    } else {
                        showDisError(LanguageController.getInstance().getMobileMsgByKey(AppConstant.network_error));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onNegativeCall() {
                Log.e("", "");
            }
        });
    }

    private void goneViewsForUpdate() {
        layout_fw_item.setVisibility(View.GONE);
        try {
            if (locId != null && !locId.equals("0") && updateItemDataModel.getTaxType() != null && updateItemDataModel.getTaxType().equals("2")) {
                applytaxBasesOnLoc();
            } else if (updateItemDataModel.getTax().size() > 0) {
                List<Tax> filterSelectedTaxList = updateItemDataModel.getTax();
                for (Tax taxModel : listFilter) {
                    for (Tax taxselect : filterSelectedTaxList) {
                        if (taxModel.getTaxId().equals(taxselect.getTaxId())) {
                            if (Double.parseDouble(taxModel.getRate()) != Double.parseDouble(taxselect.getRate())) {
                                taxModel.setAppliedTax(LanguageController.getInstance().getMobileMsgByKey(AppConstant.applied_tax) + " (" + taxselect.getRate() + " %)");
                                taxModel.setSelect(true);
                                total_tax += Float.parseFloat(taxselect.getRate());
                            } else {
                                taxModel.setSelect(true);
                                total_tax += Float.parseFloat(taxselect.getRate());
                            }
                            taxModel.setOldTax(taxselect.getRate());

                            updateItem = true;
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setItemFields();
    }


    private void setItemFields() {
        tax_value_txt.setText((String.valueOf(total_tax)));
        calculateTaxRate();

        /* type 1 for inventry item  */
        switch (updateItemDataModel.getDataType()) {
            // for equipment items

            case "4":
            case "1":
                autocomplete_item.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.item));
                setTxtBkgColor(1);
                fw_service_filed_hide(1);
                IS_ITEM_MANDATRY = true;
                TAB_SELECT = 1;
                break;
            /* type 2 for FieldWorker item  */
            case "2":
                setTxtBkgColor(2);
                autocomplete_item.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.fieldworkers_name));
                fw_service_filed_hide(2);
                IS_FW_MANDATRY = true;
                TAB_SELECT = 2;
                break;
            case "3":
                setTxtBkgColor(3);
                autocomplete_item.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.services_name));
                fw_service_filed_hide(3);
                IS_SERVICES_MANDATRY = true;
                TAB_SELECT = 3;
                break;

            /* type 6 for LABOUR item  */
            case "6":

                break;
        }


        if (!updateItemDataModel.getInm().equals(""))
            autocomplete_item.setText(updateItemDataModel.getInm());
        else autocomplete_item.setText(updateItemDataModel.getTempNm());
        autocomplete_item.setFocusableInTouchMode(false);
        autocomplete_item.dismissDropDown();
        itemlayout.setHintEnabled(true);
        chiled_add_stock_checkBox.setChecked(false);
        edt_item_qty.setText(updateItemDataModel.getQty());
        edt_item_rate.setText(AppUtility.getRoundoff_amount(updateItemDataModel.getRate()));
        edt_item_supplier.setText(AppUtility.getRoundoff_amount((updateItemDataModel.getSupplierCost())));
        edt_item_disc.setText(updateItemDataModel.getDiscount());
        edt_item_desc.setText(updateItemDataModel.getDes());
        edt_part_no.setText(updateItemDataModel.getPno());
        edt_hsnCode.setHint(App_preference.getSharedprefInstance().getLoginRes().getHsnCodeLable());
        edt_hsnCode.setText(updateItemDataModel.getHsncode());
        edt_unit.setText(updateItemDataModel.getUnit());
        edt_serialNo.setText(updateItemDataModel.getSerialNo());

        itemId = updateItemDataModel.getItemId();
        dataType = updateItemDataModel.getDataType();
        itemType = updateItemDataModel.getItemType();
        inm = updateItemDataModel.getInm();
        jtId = updateItemDataModel.getJtId();
        lastUpdateQty = updateItemDataModel.getQty();
        if (updateItemDataModel.getItemType() != null && updateItemDataModel.getItemType().equals("0")) {
            if (!itemType.isEmpty() && itemType.equals("0") && itemId != null && !itemId.isEmpty()) {
                stockData = AppDataBase.getInMemoryDatabase(this).stockDataDao().getItemBalanceByItemId(itemId);
            }
            if (updateItemDataModel.getIsRemoveStock() != null && updateItemDataModel.getIsRemoveStock().equals("0")) {
                add_stock_checkBox.setChecked(false);
                isRemoveStock = "0";
                chiled_add_stock_checkBox.setVisibility(View.GONE);
            } else {
                if (updateItemDataModel.getStkusrId() != null &&
                        updateItemDataModel.getStkusrId().equalsIgnoreCase(App_preference.getSharedprefInstance().getLoginRes().getUsrId()))
                {

                    chiled_add_stock_checkBox.setChecked(true);
                    stkusrId = updateItemDataModel.getStkusrId();
                    userId = stkusrId;

                } else {  chiled_add_stock_checkBox.setChecked(false);
                    stkusrId = updateItemDataModel.getStkusrId();
                    userId = stkusrId;
                }
                add_stock_checkBox.setChecked(true);
                isRemoveStock = "1";
                chiled_add_stock_checkBox.setVisibility(View.VISIBLE);
            }
            if (updateItemDataModel.getDataType() != null && updateItemDataModel.getDataType().equals(("3"))) {
                add_stock_checkBox.setVisibility(View.GONE);
                chiled_add_stock_checkBox.setVisibility(View.GONE);
            } else {
                add_stock_checkBox.setVisibility(View.VISIBLE);
            }
        } else {
            add_stock_checkBox.setVisibility(View.GONE);
        }
        try {
            if (!NONBILLABLE && updateItemDataModel.getDataType().equals("1")) {
                if (updateItemDataModel.getIsBillable() != null) {
                    isBillable = updateItemDataModel.getIsBillable();
                }
                if (updateItemDataModel.getIsBillableChange() != null)
                    isBillableChange = updateItemDataModel.getIsBillableChange();
                if (isBillable.equals("1")) {
                    radio_billable.setChecked(true);
                    radio_none_billable.setChecked(false);
                } else if (isBillable.equals("0")) {
                    radio_none_billable.setChecked(true);
                    radio_billable.setChecked(false);
                } else {
                    radio_none_billable.setChecked(false);
                    radio_billable.setChecked(false);
                }
            } else {
                rediogrp.setVisibility(View.GONE);
                isBillable = updateItemDataModel.getIsBillable();
                isBillableChange = updateItemDataModel.getIsBillableChange();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        firstTimeOnPage = false;
        total_Amount_cal();
        calculateTaxRate();
    }

    private void setEmptyFieldsNonInventry() {
        jtId = "";
        dataType = "1";
        itemType = "1";
        itemId = "";
        isBillable = "1";
        isBillableChange = "";
        total_tax = 0f;
        edt_item_desc.setText("");
        isShowStockAlert = false;
        edt_item_qty.setText("1");
        edt_item_rate.setText("0");
        edt_item_supplier.setText("0");
        edt_item_disc.setText("0");
        tax_value_txt.setText("0");
        amount_value_txt.setText("0");
        taxamount_value_txt.setText("0");
        edt_part_no.setText("0");
        edt_hsnCode.setText("0");
        edt_serialNo.setText("");

        Log.e("checkEmpty:;", "setEmptyFieldsNonInventry");
        setTaxDialogFiledsEmpty();
//        rediogrp.setVisibility(View.GONE);
    }

    private void set_Title() {
        if (bundle.getBoolean("UpdateItemRequirmentGethering")) {
            if (App_preference.getSharedprefInstance().getLoginRes().getIsItemEditEnable().equals("1")) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update_item));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update_btn));
                DP_OPEN = false;
            } else {
                EnableDisbleFields();
                Objects.requireNonNull(getSupportActionBar()).setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.view_details));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                add_edit_item_Btn.setVisibility(View.GONE);
            }
        } else if (updateItemDataModel == null) {
            if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace"))
                Objects.requireNonNull(getSupportActionBar()).setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.step_1) + " (" + LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_add_equipment) + ")");
            else
                Objects.requireNonNull(getSupportActionBar()).setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.addItem_screen_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {

                add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.next_btn));
            } else {
                add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.save_btn));
            }
        } else {
            /*Convert Item to Equipment Only for Inventory Item's*****/
            if (App_preference.getSharedprefInstance().getLoginRes().getIsEquipmentEnable().equals("1") && updateItemDataModel.getItemType().equals("0")) {
                for (FooterMenu serverList : App_preference.getSharedprefInstance().getLoginRes().getFooterMenu()) {
                    if (serverList.isEnable.equals("1") && serverList.getMenuField().equals("set_equipmentMenuOrdrNo")) {
                        if (updateItemDataModel.getDataType() != null && updateItemDataModel.getDataType().equalsIgnoreCase("3")) {
                            convert_item_to_equi.setVisibility(View.GONE);
                        } else {
                            convert_item_to_equi.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                }
            }

            /*this permission for Edit item***/
            if (App_preference.getSharedprefInstance().getLoginRes().getIsItemEditEnable().equals("1")) {
                if (updateItemDataModel.getDataType() != null && updateItemDataModel.getDataType().equalsIgnoreCase("3")) {
                    Objects.requireNonNull(getSupportActionBar()).setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update_service));
                } else {
                    Objects.requireNonNull(getSupportActionBar()).setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update_item));
                }
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update_btn));
                DP_OPEN = false;
            } else {
                EnableDisbleFields();
                Objects.requireNonNull(getSupportActionBar()).setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.view_details));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                add_edit_item_Btn.setVisibility(View.GONE);
            }


            if (updateItemDataModel != null && updateItemDataModel.getDataType() != null && updateItemDataModel.getDataType().equals("6")) {
                EnableDisbleFields();
                getSupportActionBar().setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.view_details));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                add_edit_item_Btn.setVisibility(View.GONE);
            }
        }
        firstTimeOnPage = false;
    }

    /**
     * User not editable all fields when admin not allow & not update
     */
    private void EnableDisbleFields() {
        autocomplete_item.setEnabled(false);
        edt_part_no.setEnabled(false);
        edt_unit.setEnabled(false);
        edt_item_desc.setEnabled(false);
        edt_item_qty.setEnabled(false);
        edt_item_rate.setEnabled(false);
        edt_item_supplier.setEnabled(false);
        edt_unit.setEnabled(false);
        edt_item_desc.setEnabled(false);
        tax_value_txt.setEnabled(false);
        amount_value_txt.setEnabled(false);
        edt_item_disc.setEnabled(false);
        taxamount_value_txt.setEnabled(false);
        edt_hsnCode.setEnabled(false);
        edt_serialNo.setEnabled(false);
        radio_billable.setEnabled(false);
        radio_none_billable.setEnabled(false);
    }

    @SuppressLint("SetTextI18n")
    private void initializelables() {
        tv_skip = findViewById(R.id.tv_skip);
        tv_skip.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.skip));
        tv_skip.setOnClickListener(this);
        tvLabelStep1 = findViewById(R.id.tvLabelStep1);
        tvLabelStep1.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.step_1_note_new));

        ll_note = findViewById(R.id.ll_note);
        ll_link_note = findViewById(R.id.ll_link_note);
        itemlayout = findViewById(R.id.itemlayout);
        layout_fw_item = findViewById(R.id.layout_fw_item);
        tv_label_part = findViewById(R.id.tv_label_part);
        tv_label_part.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.parts_s));
        ll_part_items = findViewById(R.id.ll_part_items);

        recyclerView_part_item = findViewById(R.id.recyclerView_part_item);
        recyclerView_part_item.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        invoice_list_adpter = new ItemListPartAdpter(this, new ArrayList<>(), true, true);//, this, this
        recyclerView_part_item.setAdapter(invoice_list_adpter);
        recyclerView_part_item.setNestedScrollingEnabled(false);

        autocomplete_item = findViewById(R.id.autocomplete_item);
        autocomplete_item.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.items_name) + " *");

        item_select = findViewById(R.id.item_select);
        item_select.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.items_name));

        fw_select = findViewById(R.id.fw_select);
        fw_select.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.fieldworkers));

        item_partNo_layout = findViewById(R.id.item_partNo_layout);
        edt_part_no = findViewById(R.id.edt_part_no);
        edt_part_no.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.part_no));

        item_desc_layout = findViewById(R.id.item_desc_layout);
        edt_item_desc = findViewById(R.id.edt_item_desc);
        edt_item_desc.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_desc));

        item_qty_layout = findViewById(R.id.item_qty_layout);
        edt_item_qty = findViewById(R.id.edt_item_qty);
        edt_item_qty.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty_hr));

        item_rate_layout = findViewById(R.id.item_rate_layout);
        edt_item_rate = findViewById(R.id.edt_item_rate);
        edt_item_rate.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.rate));

        item_unit_layout = findViewById(R.id.item_unit_layout);
        edt_unit = findViewById(R.id.edt_unit);
        edt_unit.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.unit));

        item_discount_layout = findViewById(R.id.item_discount_layout);
        edt_item_disc = findViewById(R.id.edt_item_disc);

        // direct discount case
        discount(getDisCalculationType);

        taxamount_layout = findViewById(R.id.taxamount_layout);
        taxamount_txt_hint = findViewById(R.id.taxamount_txt_hint);
        taxamount_txt_hint.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.tax_amount));

        tax_layout = findViewById(R.id.tax_layout);
        tax_txt_hint = findViewById(R.id.tax_txt_hint);
        tax_txt_hint.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.tax));

        amount_layout = findViewById(R.id.amount_layout);
        amount_value_txt = findViewById(R.id.amount_value_txt);
        amount_txt_hint = findViewById(R.id.amount_txt_hint);
        amount_txt_hint.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.amt));

        service_select = findViewById(R.id.service_select);
        service_select.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.services_name));


        item_supplier_layout = findViewById(R.id.item_supplier_layout);
        edt_item_supplier = findViewById(R.id.edt_item_supplier);
        edt_item_supplier.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.supplier_cost));

        serialNo_layout = findViewById(R.id.serialNo_layout);
        edt_serialNo = findViewById(R.id.edt_serialNo);
        edt_serialNo.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.serial_no));


        convert_item_to_equi = findViewById(R.id.convert_item_to_equi);
        convert_item_to_equi.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.convert_item_to_equ));

        rediogrp = findViewById(R.id.rediogrp);
        rediogrp.setOnCheckedChangeListener(this);
        radio_billable = findViewById(R.id.radio_billable);
        radio_none_billable = findViewById(R.id.radio_none_billable);
        text_default = findViewById(R.id.text_default);
        radio_none_billable.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.non_billable));
        radio_billable.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.billable));
        text_default.setText(" (" + LanguageController.getInstance().getMobileMsgByKey(AppConstant.text_default) + ")");

        add_stock_checkBox = findViewById(R.id.add_stock_checkBox);
        chiled_add_stock_checkBox = findViewById(R.id.chiled_add_stock_checkBox);
        add_stock_checkBox.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.remove_stock_from_inventory));
        chiled_add_stock_checkBox.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.adjust_stock));
        txt_lbl_link = findViewById(R.id.txt_lbl_link);
        txt_lbl_link.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.select_job_items_to_link_equipment));
        btn_link_item = findViewById(R.id.btn_link_item);
        btn_link_item.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.yes));
        btn_link_item.setOnClickListener(this);
        ll_serialNo = findViewById(R.id.ll_serialNo);
        ll_below_rd_serialNo = findViewById(R.id.ll_below_rd_serialNo);
        txt_serialNo_hint = findViewById(R.id.txt_serialNo_hint);
        txt_serialNo_hint.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.serial_no));
        rd_group_serialNo = findViewById(R.id.rd_group_serialNo);
        txt_lbl_not_matchSerialNo = findViewById(R.id.txt_lbl_not_matchSerialNo);
        txt_lbl_not_matchSerialNo.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.serial_no_not_matched));
        btn_not_matchSerialNo = findViewById(R.id.btn_not_matchSerialNo);
        btn_not_matchSerialNo.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.appointment_add_new_attach));
        btn_not_matchSerialNo.setOnClickListener(this);
        ivCross = findViewById(R.id.ivCross);
        ivCross.setOnClickListener(this);

        intializeViews();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.e("", "");
        switch (checkedId) {
            case R.id.radio_none_billable:
                nonBillableItem();
                break;
            case R.id.radio_billable:
                billableItem();
                break;
        }

    }

    private void nonBillableItem() {
        isBillable = "0";
        isBillableChange = "";
    }

    private void billableItem() {
        isBillableChange = isBillable = "1";
    }

    private void intializeViews() {
        item_hsnCode_layout = findViewById(R.id.item_hsnCode_layout);
        edt_hsnCode = findViewById(R.id.edt_hsnCode);


//        set focus change listener for round off the value.

        taxamount_value_txt = findViewById(R.id.taxamount_value_txt);

        add_edit_item_Btn = findViewById(R.id.add_edit_item_Btn);
        tax_value_txt = findViewById(R.id.tax_value_txt);

        tax_cancel = findViewById(R.id.tax_cancel);
        tax_cancel.setOnClickListener(this);


        nm_view = findViewById(R.id.nm_view);
        desc_view = findViewById(R.id.desc_view);
        qty_view = findViewById(R.id.qty_view);
        rate_view = findViewById(R.id.rate_view);
        supplier_view = findViewById(R.id.supplier_view);
        disc_view = findViewById(R.id.disc_view);
        tax_view = findViewById(R.id.tax_view);
        amount_view = findViewById(R.id.amount_view);
        taxrateAmount_view = findViewById(R.id.taxrateAmount_view);
        part_no_view = findViewById(R.id.part_no_view);
        hsncode_view = findViewById(R.id.hsncode_view);
        unit_view = findViewById(R.id.unit_view);
        seroal_no_view = findViewById(R.id.seroal_no_view);


        item_tax_rate_layout = findViewById(R.id.item_tax_rate_layout);
        edt_item_tax_rate = findViewById(R.id.edt_item_tax_rate);
        tax_rate_view = findViewById(R.id.tax_rate_view);
        edt_item_tax_rate.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.rate_inclu_tax));
        edt_item_tax_rate.setEnabled(false);

        add_quote_item_layout = findViewById(R.id.add_quote_item_layout);
        AppUtility.setupUI(add_quote_item_layout, AddEditInvoiceItemActivity2.this);

        Objects.requireNonNull(item_desc_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(item_qty_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(item_rate_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(item_supplier_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(item_discount_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(item_partNo_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(item_hsnCode_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(item_unit_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(serialNo_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(item_tax_rate_layout.getEditText()).addTextChangedListener(this);


        add_edit_item_Btn.setOnClickListener(this);
        edt_item_desc.addTextChangedListener(this);
        item_select.setOnClickListener(this);
        fw_select.setOnClickListener(this);
        service_select.setOnClickListener(this);

        tax_value_txt.setOnClickListener(this);
        edt_item_rate.setOnFocusChangeListener(this);
        edt_item_disc.setOnFocusChangeListener(this);
        amount_value_txt.setOnFocusChangeListener(this);
        autocomplete_item.setOnClickListener(this);
        convert_item_to_equi.setOnClickListener(view -> {
            if (AppUtility.isInternetConnected()) convertInEquip(updateItemDataModel);
            else
                showDisError(LanguageController.getInstance().getMobileMsgByKey(AppConstant.network_error));
        });

        autocomplete_item.addTextChangedListener(this);

        hideShowInvoiceItemDetails();

        /* this permission for EDIT item***/
        if (App_preference.getSharedprefInstance().getLoginRes().getIsItemEditEnable().equals("1")) {
            add_edit_item_Btn.setVisibility(View.VISIBLE);
        }

        // TODO removed by shivani
//        if (!NONBILLABLE) {
//            rediogrp.setVisibility(View.GONE);
//        }
        add_stock_checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                isRemoveStock = "0";
                stkusrId = "0";
                chiled_add_stock_checkBox.setVisibility(View.GONE);
                chiled_add_stock_checkBox.setChecked(false);
            } else {
                isRemoveStock = "1";
                chiled_add_stock_checkBox.setChecked(false);
                stkusrId = "0";
                chiled_add_stock_checkBox.setVisibility(View.VISIBLE);
            }
        });
        chiled_add_stock_checkBox.setOnClickListener(v -> {
            if (!chiled_add_stock_checkBox.isChecked()) {
                chiled_add_stock_checkBox.setChecked(false);
                if(userId.isEmpty() && userId.equals("0")) {
                    stkusrId = "0";
                }else if(userId.equalsIgnoreCase(App_preference.getSharedprefInstance().getLoginRes().getUsrId())){
                    stkusrId = "0";
                }else if(!userId.equalsIgnoreCase(App_preference.getSharedprefInstance().getLoginRes().getUsrId())){
                    stkusrId = userId;
                }
            } else {
                stockQty = edt_item_qty.getText().toString();
                getStockBalance();
            }
        });
        invoiceItemPi = new AddEditInvoiceItem_PC(this);
        invoiceItemPi.getInventryItemList();
    }

    private void getStockBalance() {
        boolean showDialog = false;
//            Log.e("stockData", stockData.toString());
            if(stockData != null) {
                if (userId.isEmpty()) {
                    stockQty = edt_item_qty.getText().toString().trim();
                    showDialog = stockQty != null && !stockQty.isEmpty() && Integer.parseInt(stockQty) > Integer.parseInt(stockData.getBalance());
                } else if (userId.equalsIgnoreCase(App_preference.getSharedprefInstance().getLoginRes().getUsrId()))
                {
                    stockQty = edt_item_qty.getText().toString().trim();
                    if(!updateItemDataModel.getQty().isEmpty() && !stockQty.isEmpty()) {
                        stockQty = String.valueOf(Integer.parseInt(stockQty) - Integer.parseInt(updateItemDataModel.getQty()));
                    }
                    showDialog = stockQty != null && !stockQty.isEmpty() && Integer.parseInt(stockQty) > Integer.parseInt(stockData.getBalance());
                } else if (!userId.equalsIgnoreCase(App_preference.getSharedprefInstance().getLoginRes().getUsrId()))
                {
                    stockQty ="0";
                    if(isFromQty){
                        stockQty = edt_item_qty.getText().toString().trim();
                    }
                    if(updateItemDataModel != null && !updateItemDataModel.getQty().isEmpty() && !stockQty.isEmpty()) {

                        if(isFromQty){
                            stockQty = String.valueOf( Integer.parseInt(stockQty));
                        }else {
                            stockQty = updateItemDataModel.getQty();
                        }
                    }
                    showDialog = stockQty != null && !stockQty.isEmpty() && Integer.parseInt(stockQty) > Integer.parseInt(stockData.getBalance());
                }
                else{
                    chiled_add_stock_checkBox.setChecked(false);
                }

                if (showDialog) {
                        AppUtility.alertDialog2(this,
                                "",
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.stock_remove_alert_msg),
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok),
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel),
                                new Callback_AlertDialog() {
                                    @Override
                                    public void onPossitiveCall() {
                                        chiled_add_stock_checkBox.setChecked(true);
                                        stkusrId = App_preference.getSharedprefInstance().getLoginRes().getUsrId();
                                        stockQty = edt_item_qty.getText().toString().trim();
                                        lastUpdateQty = edt_item_qty.getText().toString().trim();
                                        isFromQty = false;
                                    }

                                    @Override
                                    public void onNegativeCall() {
                                        if(isFromQty){
                                            isFromQtyCancel = true;
                                            isFromQty = false;
                                            if(updateItemDataModel != null){
                                                edt_item_qty.setText(updateItemDataModel.getQty());
                                            }else {
                                                edt_item_qty.setText("1");
                                            }
                                        }else {
                                            chiled_add_stock_checkBox.setChecked(false);
                                            if (userId.isEmpty() || userId.equals("0")) {
                                                stkusrId = "0";
                                            }
                                        }
                                    }
                                });
                } else {
                    lastUpdateQty = edt_item_qty.getText().toString();
                    chiled_add_stock_checkBox.setChecked(true);
                    stkusrId = App_preference.getSharedprefInstance().getLoginRes().getUsrId();
                }
            }else{
                chiled_add_stock_checkBox.setChecked(true);
                stkusrId = App_preference.getSharedprefInstance().getLoginRes().getUsrId();
            }
    }

    /**
     * Convert Item to Equipment
     *****/
    private void convertInEquip(InvoiceItemDataModel updateItemDataModel) {
        AppUtility.progressBarDissMiss();
        if (Integer.parseInt(updateItemDataModel.getItemConvertCount()) >= Integer.parseInt(updateItemDataModel.getQty())) {
            AppUtility.alertDialog2(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.are_you_sure), LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_convrt_count), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel), new Callback_AlertDialog() {
                @Override
                public void onPossitiveCall() {
                    sentDataForEquip(updateItemDataModel);
                }

                @Override
                public void onNegativeCall() {
                }
            });
        } else {
            sentDataForEquip(updateItemDataModel);
        }
    }

    private void sentDataForEquip(InvoiceItemDataModel updateItemDataModel) {
        Intent intent = new Intent(AddEditInvoiceItemActivity2.this, AddJobEquipMentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("edit_jobId", jobId);
        intent.putExtra("invId", invId);
        intent.putExtra("comeFrom", comeFrom);
        intent.putExtra("equipmentId", equipmentId);
        intent.putExtra("equipmentIdName", equipmentIdName);
        intent.putExtra("equipmentType", equipmentType);
        intent.putExtra("InvoiceItemDataModel", updateItemDataModel);
        try {
            intent.putExtra("objectStr", new Gson().toJson(updateItemDataModel));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark")) {
            String strEqu = new Gson().toJson(equipment);
            if (equipment.getIsPart().equalsIgnoreCase("1")) {
                intent.putExtra("isPart", "1");
            }
            intent.putExtra("equipment", strEqu);
        }

        if (comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan")) {
            if (equDefaultType != null && !equDefaultType.isEmpty()) {
                intent.putExtra("equDefaultType", equDefaultType);
            }
        }
        intent.putExtra("isSerialNoSelected", isSerialNoSelected);
        intent.putExtra("generateOption", generateOption);
        intent.putExtra("scanCode", scanCode);
        startActivityForResult(intent, EQUIPMENTCONVERT);
        if (!isSerialNoSelected) {
            finish();
        }
    }

    private void sentDataForEquipWithoutItem() {
        Intent intent = new Intent(AddEditInvoiceItemActivity2.this, AddJobEquipMentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("edit_jobId", jobId);
        intent.putExtra("invId", invId);
        intent.putExtra("cltId", cltId);
        intent.putExtra("comeFrom1", "AddPartWithoutItem");
        intent.putExtra("comeFrom", comeFrom);
        if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark")) {
            String strEqu = new Gson().toJson(equipment);
            if (equipment.getIsPart().equalsIgnoreCase("1")) {
                intent.putExtra("isPart", "1");
            }
            intent.putExtra("equipment", strEqu);
        }
        if (comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan")) {
            if (equDefaultType != null && !equDefaultType.isEmpty()) {
                intent.putExtra("equDefaultType", equDefaultType);
            }
        }
        intent.putExtra("equipmentId", equipmentId);
        intent.putExtra("equipmentIdName", equipmentIdName);
        intent.putExtra("equipmentType", equipmentType);
        intent.putExtra("isSerialNoSelected", isSerialNoSelected);
        intent.putExtra("generateOption", generateOption);
        intent.putExtra("scanCode", scanCode);
        startActivityForResult(intent, EQUIPMENTCONVERT);
    }

    /**
     * Move Link Item Screen
     */
    private void linkItem() {
        if (comeFrom.equalsIgnoreCase("AddRemarkItem")) {
            Intent intent = new Intent(AddEditInvoiceItemActivity2.this, JobEquLinkItemActivity.class);
            intent.putExtra("edit_jobId", jobId);
            intent.putExtra("comeFrom", "AddItem");
            intent.putExtra("equipmentId", equipmentId);
            startActivity(intent);
//        startActivityForResult(intent, EQUIPMENTLINK);
        } else {
            Intent intent = new Intent(AddEditInvoiceItemActivity2.this, JobEquLinkItemActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            intent.putExtra("edit_jobId", jobId);
            intent.putExtra("invId", invId);
            intent.putExtra("comeFrom", comeFrom);
            intent.putExtra("equipmentId", equipmentId);
            intent.putExtra("equipmentIdName", equipmentIdName);
            intent.putExtra("equipmentType", equipmentType);
            intent.putExtra("InvoiceItemDataModel", updateItemDataModel);
            try {
                intent.putExtra("objectStr", new Gson().toJson(updateItemDataModel));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            startActivity(intent);
        }
    }

    @Override
    public void setItemdataFromServer(List<Inventry_ReS_Model> list) {

    }

    /**
     * When we convert item as a new equipment, add as a part or replace as a equipment.
     * 1.Item have multiple serial number then serial number list will be show.
     * 2.Item have only one serial number user will be move to next scree.
     * 3.Item have no serial number showing all detail and it will be add as a new item for converting.
     * When we will add new item showing all detail.
     */
    @Override
    public void setItemdata(List<Inventry_ReS_Model> list) {
        Log.e("", "");
        this.itemsList = list;
        Log.v("ItemList::", new Gson().toJson(list));
        AppUtility.autocompletetextviewPopUpWindow(autocomplete_item);
        autocomplete_item.setTag("Item");
        Auto_Inventry_Adpter itemAdapter = new Auto_Inventry_Adpter(this, R.layout.custom_item_adpter, (ArrayList<Inventry_ReS_Model>) this.itemsList);
        autocomplete_item.setAdapter(itemAdapter);
        autocomplete_item.setThreshold(3);
        autocomplete_item.setOnItemClickListener((adapterView, view, position, l) -> {
            try {
                Log.v("ItemList::", new Gson().toJson(adapterView.getItemAtPosition(position)));
//                For getting serial no of Items
                if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
                    serialNoList.clear();
                    Inventry_ReS_Model invItem = (Inventry_ReS_Model) adapterView.getItemAtPosition(position);
                    Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
                    for (InvoiceItemDataModel item : job.getItemData()) {
                        if (item.getItemId().equalsIgnoreCase(invItem.getItemId()) && !item.getSerialNo().isEmpty() && item.getItemConvertCount().equals("0")) {
                            serialNoList.add(item);
                        }
                    }
                    invetoryItemData = invItem;
                    setSelectedItemData(invItem);
                    if (serialNoList.size() == 1) {
                        isSerialNoSelected = true;
                        itemId1 = serialNoList.get(0).getItemId();
                        ijmmId = serialNoList.get(0).getIjmmId();
                        serialNo = serialNoList.get(0).getSerialNo();
                        String data = new Gson().toJson(serialNoList.get(0));
                        setSelectedItemData(CastToInv_Res_Model(data));
                        ll_serialNo.setVisibility(View.GONE);
                        ll_below_rd_serialNo.setVisibility(View.GONE);
                        add_edit_item_Btn.performClick();
                    } else if (serialNoList.size() > 1) {
                        ll_serialNo.setVisibility(View.VISIBLE);
                        ll_below_rd_serialNo.setVisibility(View.GONE);
                        setRadioButton(serialNoList);
                    } else {
                        ll_serialNo.setVisibility(View.GONE);
                        ll_below_rd_serialNo.setVisibility(View.VISIBLE);
                    }
                } else {
                    setSelectedItemData(((Inventry_ReS_Model) adapterView.getItemAtPosition(position)));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EQUIPMENTCONVERT) {
            try {
                Log.e("AddEquipment", comeFrom);
                if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
                    if (!isSerialNoSelected) {
                        Intent intent = new Intent();
                        setResult(333, intent);
                        finish();
                    }
                }else if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") ){
                    Intent intent = new Intent();
                    setResult(333, intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*** add item from dropdown*/
    private void setSelectedItemData(Inventry_ReS_Model itemselected) {
        // to remove the callback on item selection
//        handler.removeCallbacks(input_finish_checker);

        Log.e("itemselected", new Gson().toJson(itemselected));
        /*For Item Parts on Item************/
        if (itemselected.getParts() != null && !itemselected.getParts().isEmpty()) {

            partsList.clear();
            partsList.addAll(itemselected.getParts());
            // for setting quantity as 1 default
            for (ItemParts item : partsList) {
                item.setQty("1");
                if (item.getRate().isEmpty()) item.setRate("0");
                if (item.getDiscount().isEmpty()) item.setDiscount("0");
            }
            invoice_list_adpter.updateitemlist(partsList);
            ll_part_items.setVisibility(View.VISIBLE);
            isGrouped = "1";
        } else {
            isGrouped = "0";
            ll_part_items.setVisibility(View.GONE);
        }

        if (itemselected.getWarrantyType() != null && itemselected.getWarrantyValue() != null) {
            warrantyValue = itemselected.getWarrantyValue();
            warrantyType = itemselected.getWarrantyType();
            Log.e("checkEmpty:;", warrantyValue + " " + warrantyType);

        }

        /* 1 For Selected tax on Item************/
        if (itemselected.getTaxType() != null && itemselected.getTaxType().equals("1")) {
            setDefaultTax(itemselected.getTax());
        } else /* 2 For Apply tax on Item************/
            if (itemselected.getTaxType() != null && itemselected.getTaxType().equals("2")) {
                applytaxBasesOnLoc();
            } else  /* 0 For no selection************/
                if (itemselected.getTaxType() != null && itemselected.getTaxType().equals("0")) {
                    Log.e("selection", "0 For no selection");
                }
        itemId = itemselected.getItemId();
        inm = itemselected.getInm();
        itemId = itemselected.getItemId();
        dataType = "1";
        itemType = "0";
        jtId = "";
        try {
            if (itemselected.getIsBillable() != null) isBillable = itemselected.getIsBillable();
            if (itemselected.getIsBillableChange() != null)
                isBillableChange = itemselected.getIsBillableChange();

            if (isBillable.equals("1")) {
                radio_billable.setChecked(true);
                // radio_none_billable.setChecked(false);
            } else if (isBillable.equals("0")) {
                radio_none_billable.setChecked(true);
                // radio_billable.setChecked(false);
            } else {
                radio_none_billable.setChecked(false);
                radio_billable.setChecked(false);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /* quantity always be 1 initially */
        edt_item_qty.setText("1");
        edt_part_no.setText(itemselected.getPno());
        edt_hsnCode.setText(itemselected.getHsncode());
        edt_item_desc.setText(itemselected.getIdes());
        edt_unit.setText(itemselected.getUnit());
        edt_serialNo.setText(itemselected.getSerialNo());
        lastUpdateQty = "1";

        if (!itemType.isEmpty() && itemType.equals("0") && itemId != null && !itemId.isEmpty()) {
             stockData = AppDataBase.getInMemoryDatabase(this).stockDataDao().getItemBalanceByItemId(itemId);
        }

        if (show_stock_checkbox) {
            add_stock_checkBox.setVisibility(View.VISIBLE);
            if (add_stock_checkBox.isChecked()) {
                chiled_add_stock_checkBox.setVisibility(View.VISIBLE);
            } else {
                chiled_add_stock_checkBox.setVisibility(View.GONE);
            }
        }
        if (!add_stock_checkBox.isChecked()) {
            isRemoveStock = "0";
            chiled_add_stock_checkBox.setVisibility(View.GONE);
        } else {
            isRemoveStock = "1";
            chiled_add_stock_checkBox.setVisibility(View.VISIBLE);
            if(stockData != null) {
                stockQty = edt_item_qty.getText().toString();
                if(stockQty != null && !stockQty.isEmpty() && Integer.parseInt(stockQty) > Integer.parseInt(stockData.getBalance())){
                    chiled_add_stock_checkBox.setChecked(false);
                    stkusrId = "0";
                }else {
                    chiled_add_stock_checkBox.setChecked(true);
                    stkusrId = App_preference.getSharedprefInstance().getLoginRes().getUsrId();
                }
            }
        }
        Log.v("Rate:::", itemselected.getRate());
        if (itemselected.getRate().isEmpty()) {
            edt_item_rate.setText("0");
        } else {
            edt_item_rate.setText(AppUtility.getRoundoff_amount(itemselected.getRate()));
        }
        if (itemselected.getSupplierCost().isEmpty()) {
            edt_item_supplier.setText("0");
        } else {
            edt_item_supplier.setText(AppUtility.getRoundoff_amount(itemselected.getSupplierCost()));
        }
        if (getDisCalculationType.equals("1")) {
            if (itemselected.getDiscount().isEmpty()) {
                itemselected.setDiscount("0");
            }
            double calculaterateDis = 0;
            double qty = Double.parseDouble(edt_item_qty.getText().toString());
            double rate = Double.parseDouble(edt_item_rate.getText().toString());
            double dis = Double.parseDouble(itemselected.getDiscount());
            calculaterateDis = (rate * qty * dis) / 100;
            edt_item_disc.setText(AppUtility.getRoundoff_amount("" + calculaterateDis));
        } else {
            edt_item_disc.setText(AppUtility.getRoundoff_amount(itemselected.getDiscount()));
        }
        total_Amount_cal();

        // TODO removed by shivani
//        if (!NONBILLABLE)
//            rediogrp.setVisibility(View.VISIBLE);

//        radio_billable.setChecked(true);
//        radio_none_billable.setChecked(true);

    }

    /********* Apply tax on Item based on Location************/
    private void applytaxBasesOnLoc() {

        try {
            if (locId != null && !locId.equals("0")) {
                List<Tax> locTaxList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).invoiceTaxDao().getTaxListByLocId(locId);
                if (locTaxList != null && locTaxList.size() == 1) {
                    for (Tax tax1 : listFilter) {
                        if (tax1.getLocId() == null) tax1.setLocId("0");
                        if (tax1.getLocId().equals(locId)) {
                            tax1.setSelect(true);
                            total_tax = Float.parseFloat(tax1.getRate()) + total_tax;
                            break;
                        }
                    }
                }
            }

            tax_value_txt.setText((String.valueOf(total_tax)));
            calculateTaxRate();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void setDefaultTax(List<Tax> tax_default_list) {
        for (Tax tax1 : listFilter) {
            for (Tax tax2 : tax_default_list) {
                if (tax1.getStatus().equals("1")) {
                    if (tax1.getTaxId().equals(tax2.getTaxId())) {
//                    tax1.setRate(tax2.getRate());
                        tax1.setSelect(true);
                        total_tax = Float.parseFloat(tax1.getRate()) + total_tax;
                        break;
                    }
                }
            }
        }

        tax_value_txt.setText((String.valueOf(total_tax)));
        calculateTaxRate();

        Log.e("", "");
    }


    @Override
    public void setFieldWorKerList(List<FieldWorker> fieldWorkerList) {
        this.fieldWorkerList = fieldWorkerList;
        AppUtility.autocompletetextviewPopUpWindow(autocomplete_item);
        ShowFwList(fieldWorkerList);
        final Auto_Fieldworker_Adpter countryAdapter = new Auto_Fieldworker_Adpter(this, R.layout.custom_adapter_item_layout, (ArrayList<FieldWorker>) fieldWorkerList);
        autocomplete_item.setAdapter(countryAdapter);
        autocomplete_item.setThreshold(1);
        autocomplete_item.setOnItemClickListener((adapterView, view, i, l) -> {
            itemId = ((FieldWorker) adapterView.getItemAtPosition(i)).getUsrId();
            itemlayout.setHintEnabled(true);
            setFieldWorkerData(((FieldWorker) adapterView.getItemAtPosition(i)));
            IS_FW_MANDATRY = true;
        });
    }

    /**
     * fillter already added FW's
     **/
    private void ShowFwList(List<FieldWorker> fieldWorkerList) {
        if (jobModel != null && jobModel.getItemData() != null) {
            for (InvoiceItemDataModel itemData : jobModel.getItemData()) {
                for (FieldWorker fw : fieldWorkerList) {
                    if (itemData.getDataType().equals("2") && fw.getUsrId().equals(itemData.getItemId())) {
                        fieldWorkerList.remove(fw);
                        break;
                    }
                }
            }
        }
    }

    private void setFieldWorkerData(FieldWorker fieldWorkerselected) {
        itemId = fieldWorkerselected.getUsrId();
        dataType = "2";
        itemType = "";
        inm = "";
        /* quantity always be 1 initially */
        isShowStockAlert = false;
        edt_item_qty.setText("1");
        edt_item_rate.setText("0");
        edt_item_supplier.setText("0");
        edt_item_disc.setText("0");
        tax_value_txt.setText("0");
        Log.e("checkEmpty:;", "setFieldWorkerData");
        warrantyType = "";
        warrantyValue = "";
        isGrouped = "0";
        total_Amount_cal();
    }

    @Override
    public void setJobtitleList(List<JobTitle> servicesItemList) {
        this.servicesItemList = servicesItemList;
        ShowServicesList(servicesItemList);
        AppUtility.autocompletetextviewPopUpWindow(autocomplete_item);
        autocomplete_item.setTag("Services");
        final Services_item_Adapter services_item_adapter = new Services_item_Adapter(this, R.layout.custom_adapter_item_layout, (ArrayList<JobTitle>) servicesItemList);
        autocomplete_item.setAdapter(services_item_adapter);
        autocomplete_item.setThreshold(1);
        autocomplete_item.setOnItemClickListener((adapterView, view, i, l) -> {
            itemlayout.setHintEnabled(true);
            setServiceData(((JobTitle) adapterView.getItemAtPosition(i)));
        });

    }

    private void setServiceData(JobTitle serViceItem) {
        jtId = serViceItem.getJtId();
        IS_SERVICES_MANDATRY = true;
        dataType = "3";
        itemType = "";
        itemId = serViceItem.getJtId();
        inm = serViceItem.getName();
        isBillable = "1";

        if (serViceItem.getQty() != null && !serViceItem.getQty().isEmpty()) {
            edt_item_qty.setText(serViceItem.getQty()); // quantity always be 1 initially
        } else {
            edt_item_qty.setText("1"); // quantity always be 1 initially
        }
        edt_item_rate.setText(serViceItem.getLabour());
        edt_item_supplier.setText("0");
        edt_item_disc.setText("0");
        rediogrp.setVisibility(View.GONE);
        isBillableChange = "";

        Log.e("checkEmpty:;", "setServiceData");
        warrantyType = "";
        warrantyValue = "";
        isGrouped = "0";
        setServicesTax(serViceItem.getTaxData());
        total_Amount_cal();
    }

    /**
     * set tax value for specifiec lable when set default job service tax amount
     */
    private void setServicesTax(List<TaxData> taxDataList) {
        for (Tax tax : listFilter) {
            for (TaxData taxData : taxDataList) {
                if (tax.getStatus().equals("1")) {
                    if (tax.getTaxId().equals(taxData.getTaxId())) {
                        /*In previous app***/
//                    tax.setRate(taxData.getRate());
                        tax.setSelect(true);
                        total_tax = Float.parseFloat(tax.getRate()) + total_tax;
                        break;
                    }
                }

            }
        }
        tax_value_txt.setText((String.valueOf(total_tax)));
        calculateTaxRate();
    }

    /**
     * not show alredy exiting services in services  dropdown
     */
    private void ShowServicesList(List<JobTitle> servicesItemList) {
        if (jobModel != null && jobModel.getItemData() != null) {
            for (InvoiceItemDataModel itemData : jobModel.getItemData()) {
                for (JobTitle jobTitle : servicesItemList) {
                    if (itemData.getDataType().equals("3") && jobTitle.getJtId().equals(itemData.getJtId())) {
                        servicesItemList.remove(jobTitle);
                        break;
                    }
                }
            }
        }
    }


    /******Add taxe's when add new Item ****/
    @Override
    public void setTaxList(List<Tax> taxList) {
        listFilter = new ArrayList<>();


        for (Tax tax : taxList) {

//            if (tax.getIsactive().equals("1") && tax.getShow_Invoice().equals("1")) {
            if (tax.getRate() == null && tax.getPercentage() == null) {
                tax.setRate("0");
            } else if (tax.getPercentage() != null) {
                tax.setRate(tax.getPercentage());
            } else {
                tax.setRate("0");
            }


            if (locId.equals("0")) {
                listFilter.add(tax);
            } else if (locId.equals(tax.getLocId()) || tax.getLocId().equals("0")) {
                listFilter.add(tax);
            }

            // listFilter.add(tax);
            Log.e("", "");
//            }
        }

        Collections.sort(listFilter, (o1, o2) -> o2.getLabel().compareTo(o1.getLabel()));
        Log.e("", "");

    }


    @Override
    public void afterTextChanged(Editable s) {

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.autocomplete_item:
                if (DP_OPEN && autocomplete_item.getTag().equals("Services")) {
                    if (servicesItemList.size() > 0) autocomplete_item.showDropDown();
                    else {
                        showDisError(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_services_use));
                    }
                } else if (DP_OPEN && autocomplete_item.getTag().equals("Fw")) {
                    if (fieldWorkerList.size() > 0) autocomplete_item.showDropDown();
                    else {
                        showDisError(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_fieldworker_use));
                    }
                } else if (DP_OPEN) {
                    autocomplete_item.showDropDown();
                }
                break;
            case R.id.add_edit_item_Btn:
                add_edit_item_Btn.setEnabled(false);
                checkMandtryFileds();
                new Handler().postDelayed(() -> add_edit_item_Btn.setEnabled(true), 500);

                break;
            case R.id.item_select:
                autocomplete_item.setTag("Item");
                add_stock_checkBox.setVisibility(View.GONE);
                chiled_add_stock_checkBox.setVisibility(View.GONE);
                invoiceItemPi.getInventryItemList();
                setTxtBkgColor(1);
                fw_service_filed_hide(1);
                break;
            case R.id.fw_select:
                autocomplete_item.setTag("Fw");
                setTxtBkgColor(2);
                invoiceItemPi.getFwList();
                fw_service_filed_hide(2);
                break;
            case R.id.tax_value_txt:
                showDialogTax();
                break;
            case R.id.service_select:
                autocomplete_item.setTag("Services");
                setTxtBkgColor(3);
                invoiceItemPi.getJobServiceTittle();
                add_stock_checkBox.setVisibility(View.GONE);
                chiled_add_stock_checkBox.setVisibility(View.GONE);
                fw_service_filed_hide(3);
                break;
            case R.id.tv_skip:
                isSerialNoSelected = false;
                sentDataForEquipWithoutItem();
                break;
            case R.id.tax_cancel:
                for (Tax tax : listFilter) {
                    tax.setSelect(false);
                }
                taxId = "0";
                float localtax = getTotalApplyTax();
                total_tax = localtax;
                tax_value_txt.setText((String.valueOf(localtax)));
                Log.d("settax5", String.valueOf(total_tax) + getTotalApplyTax());
                calculateTaxRate();
                total_Amount_cal();
                break;
            case R.id.btn_link_item:
                linkItem();
                break;
            case R.id.btn_not_matchSerialNo:
                isSerialNoSelected = false;
                if (invetoryItemData != null && !invetoryItemData.getItemId().isEmpty()) {
                    setSelectedItemData(invetoryItemData);
                } else {
                    setEmptyFieldsNonInventry();
                }
                ll_serialNo.setVisibility(View.GONE);
                ll_below_rd_serialNo.setVisibility(View.VISIBLE);
                break;
            case R.id.ivCross:
                autocomplete_item.setText("");
                ll_serialNo.setVisibility(View.GONE);
                ll_below_rd_serialNo.setVisibility(View.GONE);
                break;
            default:
                if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
                    isSerialNoSelected = true;
                    for (InvoiceItemDataModel item : serialNoList) {
                        if (item.getSerialNo().equals(((RadioButton) view).getText())) {
                            itemId1 = item.getItemId();
                            ijmmId = item.getIjmmId();
                            serialNo = item.getSerialNo();
                            String data = new Gson().toJson(item);
                            setSelectedItemData(CastToInv_Res_Model(data));
                        }
                    }
                }
                break;
        }
    }

    /**
     * After discussion with Rani change validation of canInvoiceCreated by isJobInvoiced 12/04/2024
     **/
    String taxId = "0";
    String taxRate = "0";

    private void checkMandtryFileds() {
        if (!IS_ITEM_MANDATRY && TAB_SELECT == 1) {
            if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")  || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkItem")){
                AppUtility.alertDialog(this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.request_item_empty), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
            }else {
                AppUtility.alertDialog(this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_empty), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
            }
        } else if (!IS_FW_MANDATRY && TAB_SELECT == 2) {
            AppUtility.alertDialog(this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.fw_valid), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
        } else if (!IS_SERVICES_MANDATRY && TAB_SELECT == 3) {
            AppUtility.alertDialog(this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.service_error), AppConstant.ok, "", () -> null);
        } else if (isSerialNoSelected && serialNoList.size() > 1 && itemId1 == null && ijmmId == null) {
            AppUtility.alertDialog(this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.select_serial_no), AppConstant.ok, "", () -> null);
        } else {

            List<Tax> taxListFilter = new ArrayList<>();
            for (Tax tax : listFilter) {
                if (tax.isSelect()) {
                    if (getIntent().hasExtra("InvoiceItemDataModel")) {
                        if (isTaxUpdated) {
                            taxListFilter.add(tax);
                            getTaxIdForCheckSigleTax(tax);
                        } else {
                            if (getIntent().hasExtra("InvoiceItemDataModel")) {
                                if (updateItemDataModel.getTax() != null && updateItemDataModel.getTax().size() > 0) {
                                    Tax addTax = new Tax();
                                    addTax.setTaxId(updateItemDataModel.getTax().get(0).getTaxId());
                                    addTax.setLabel(updateItemDataModel.getTax().get(0).getLabel());
                                    addTax.setStatus(updateItemDataModel.getTax().get(0).getStatus());
                                    addTax.setRate(updateItemDataModel.getTax().get(0).getRate());
                                    addTax.setLocId(updateItemDataModel.getTax().get(0).getLocId());
                                    addTax.setTaxComponents(updateItemDataModel.getTax().get(0).getTaxComponents());
                                    taxListFilter.add(addTax);
                                    getTaxIdForCheckSigleTax(addTax);
                                }
                            }
                        }
                    } else {
                        taxListFilter.add(tax);
                        getTaxIdForCheckSigleTax(tax);
                    }
                }
            }
            if(!userId.equals("0") && !userId.isEmpty()) {
                if (stockData != null ) {
                    String balance = "";
                    if (!edt_item_qty.getText().toString().isEmpty()) {
                        stockQty = edt_item_qty.getText().toString().trim();
                        if (userId.isEmpty() || !userId.equals("0") && !userId.equalsIgnoreCase(App_preference.getSharedprefInstance().getLoginRes().getUsrId())) {
                            balance = String.valueOf(Integer.parseInt(stockData.getBalance()) -
                                    Integer.parseInt(String.valueOf(Integer.parseInt(stockQty))));
                        } else {
                            stockQty = String.valueOf(Integer.parseInt(stockQty) - Integer.parseInt(updateItemDataModel.getQty()));
                            balance = String.valueOf(Integer.parseInt(stockData.getBalance()) -
                                    Integer.parseInt(stockQty));
                        }
                    }
                    stockData.setSat_usrid(stockData.getSat_usrid());
                    AppDataBase.getInMemoryDatabase(this).stockDataDao().updateStockData(balance, stockData.getSat_itemid());
                } else {
                    StockData data = new StockData(App_preference.getSharedprefInstance().getLoginRes().getUsrId(),
                            itemId, "-" + edt_item_qty.getText().toString().trim());
                    AppDataBase.getInMemoryDatabase(this).stockDataDao().insertStockSingleData(data);
                }
            }else {
                if(chiled_add_stock_checkBox.isChecked()){
                    StockData data = new StockData(App_preference.getSharedprefInstance().getLoginRes().getUsrId(),
                            itemId, "-" + edt_item_qty.getText().toString().trim());
                    AppDataBase.getInMemoryDatabase(this).stockDataDao().insertStockSingleData(data);
                }
            }

            if (jobModel != null && jobModel.getIsJobInvoiced().equals("1")) {
                if (getTaxMethodType.equals("1")) {
                    if (!taxId.equals(getSingleTaxId)) {
                        AppUtility.alertDialog(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.are_you_sure), LanguageController.getInstance().getMobileMsgByKey(AppConstant.tax_change_msg), AppConstant.yes, AppConstant.no, new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                AppUpdateJob(taxListFilter);
                                return null;
                            }
                        });
                    } else {
                        AppUpdateJob(taxListFilter);
                    }
                } else {
                    AppUpdateJob(taxListFilter);
                }
            } else {
                AppUpdateJob(taxListFilter);
            }
        }

    }

    public void AppUpdateJob(List<Tax> taxListFilter) {
        if (edt_item_rate.getText().length() <= 0) {
            edt_item_rate.setText("0");
        }
        if (edt_item_disc.getText().length() <= 0) {
            edt_item_disc.setText("0");
        }
        if (bundle.getBoolean("AddRequirmentGetheringItem")) {
            addItemOnAppointment();
        } else if (bundle.getBoolean("UpdateItemRequirmentGethering")) {
            updateItemsOnAppointment();
        } else if (updateItemDataModel == null) {
            addItemsOnJob(taxListFilter);
        } else {
            updateItemsOnJob(taxListFilter);
        }
    }

    private void updateItemsOnAppointment() {
        try {
            HyperLog.i("TAG", "addItemsOnJob(M) started");
            List<AppointmentTax> apptaxList = new ArrayList<>();
            List<Tax> taxListFilter = new ArrayList<>();
            for (Tax tax : listFilter) {
                if (tax.isSelect()) {
                    if (getIntent().hasExtra("UpdateItemRequirmentGethering")) {
                        if (isTaxUpdated) {
                            taxListFilter.add(tax);
                            getTaxIdForCheckSigleTax(tax);
                        } else {
                            if (getIntent().hasExtra("UpdateItemRequirmentGethering")) {
                                if (updateAppointmentItemData.getTax() != null && updateAppointmentItemData.getTax().size() > 0) {
                                    Tax addTax = new Tax();
                                    addTax.setTaxId("" + updateAppointmentItemData.getTax().get(0).getTaxId());
                                    addTax.setLabel(updateAppointmentItemData.getTax().get(0).getLabel());
                                    addTax.setRate(updateAppointmentItemData.getTax().get(0).getRate());
                                    addTax.setTaxComponents(updateAppointmentItemData.getTax().get(0).getTaxComponents());
                                    taxListFilter.add(addTax);
                                    getTaxIdForCheckSigleTax(addTax);
                                }
                            }
                        }
                    } else {
                        taxListFilter.add(tax);
                        getTaxIdForCheckSigleTax(tax);
                    }
                }
            }
            if (taxListFilter.size() > 0) {
                appointmentTax = new AppointmentTax(Integer.parseInt(taxListFilter.get(0).getTaxId()), taxListFilter.get(0).getRate(), taxListFilter.get(0).getLabel(), taxListFilter.get(0).getTaxComponents());
                apptaxList.add(appointmentTax);
            }
//            else{
//                appointmentTax = new AppointmentTax(Integer.parseInt("0"),
//                        "0", "0", taxListFilter);
//            }
//            apptaxList.add(appointmentTax);

            int isPartParent = 0;
            String partTempId = "";
            if (partsList != null && !partsList.isEmpty()) {
                isPartParent = 1;
                partTempId = AppUtility.getRandomUUID();
            }
            if (updateAppointmentItemData.getDataType() == 0) {
                dataType = "0";
            }
            if (itemType.equals("")) {
                itemType = "0";
            }
            if (isGrouped.equals("")) {
                isGrouped = "0";
            }
            if (edt_serialNo.getText().toString().equals("")) {
                edt_serialNo.setText("0");
            }
            if (edt_item_qty.getText().toString().isEmpty()) {
                edt_item_qty.setText("0");
            }
            updateAppintmentItemDataModel = new AppintmentItemDataModel(edt_item_desc.getText().toString(), "", appInm, edt_part_no.getText().toString(), edt_item_qty.getText().toString(), edt_item_rate.getText().toString(), edt_unit.getText().toString(), apptaxList, itemId, edt_hsnCode.getText().toString(), String.valueOf(taxAmount), updateAppointmentItemData.getDataType(), edt_item_disc.getText().toString(), updateAppointmentItemData.getItemType(), edt_item_supplier.getText().toString(), isBillable, isBillableChange, appointmentModel.getTempId(), String.valueOf(isPartParent), String.valueOf(isPartChild), partTempId, updateAppointmentItemData.getIsItemOrTitle(), edt_serialNo.getText().toString(), Integer.parseInt(isGrouped), updateAppointmentItemData.getIlmmId());


            updateItem_res = new AppointmentAddItem_Res(itemId, appInm, updateAppointmentItemData.getDataType(), updateAppointmentItemData.getItemType(), edt_item_rate.getText().toString(), edt_item_supplier.getText().toString(), edt_serialNo.getText().toString(), edt_item_qty.getText().toString(), edt_item_disc.getText().toString(), Integer.parseInt(isGrouped), apptaxList, edt_item_desc.getText().toString(), edt_hsnCode.getText().toString(), edt_part_no.getText().toString(), edt_unit.getText().toString(), isBillableChange, appointmentModel.getTempId(), String.valueOf(updateAppointmentItemData.getIlmmId()), updateAppointmentItemData.getIsLabourParent(), updateAppointmentItemData.getIsLabourChild(), updateAppointmentItemData.getLabourTempId(), taxAmount, String.valueOf(isPartParent), String.valueOf(isPartChild), partTempId, updateAppointmentItemData.getDataType(), String.valueOf(appjtId));
            // *add api for appointment item**/
            try {
                if (!isBillable.equals("")) {
                    updateAppointmentItemData.setIsBillable(isBillable);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            String updateItem = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().getUpdatedItemData(appId);
            Type listType = new TypeToken<List<AppointmentItemDataInMap>>() {
            }.getType();
            List<AppointmentItemDataInMap> addItemList = new Gson().fromJson(updateItem, listType);
            for (AppointmentItemDataInMap itemData : addItemList) {
                AppintmentItemDataModel itemData1 = itemData.getItemData();
                if (itemData1.getIlmmId() == updateAppointmentItemData.getIlmmId()) {
                    addItemList.remove(itemData);
                    break;
                }
            }
            AppointmentItemDataInMap itemDataInMap = new AppointmentItemDataInMap(String.valueOf(updateAppointmentItemData.getIlmmId()), updateAppintmentItemDataModel);
            addItemList.add(itemDataInMap);
            String updateInDB = new Gson().toJson(addItemList);
            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().updateAppointmentItem(appId, updateInDB);


            if (AppUtility.isInternetConnected()) {
                List<AppointmentAddItem_Res> addItem_resList = new ArrayList<>();
                addItem_resList.add(updateItem_res);
                String addToservice = new Gson().toJson(addItem_resList);
                AppointmentUpdateItem_Req_Model updateItem_req_model = new AppointmentUpdateItem_Req_Model(addToservice, String.valueOf(updateAppointmentItemData.getLeadId()), String.valueOf(updateAppointmentItemData.getIlmmId()));
                Intent intent = new Intent();
                intent.putExtra("updateDataReqModel", updateItem_req_model);
                intent.putExtra("updateDataForDB", itemDataInMap);
                intent.putExtra("modelForUpdate", updateAppointmentItemData);
                intent.putExtra("onlineUpdate", true);
                setResult(RESULT_OK, intent);

            } else {
                updateSyncAppItem(updateItem_res);
                Intent intent = new Intent();
                intent.putExtra("offlineUpdate", true);
                setResult(RESULT_OK, intent);
            }


            finish_Activity();
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperLog.i("TAG", "addItemsOnJob(M) exception:" + ex.toString());
        }
    }

    private void updateSyncAppItem(AppointmentAddItem_Res updateItem_res) {
        try {

            List<AppointmentAddItem_Res> addItem_resList = new ArrayList<>();
            addItem_resList.add(updateItem_res);
            String jsonAddDataInApi = new Gson().toJson(addItem_resList);
            AppointmentUpdateItem_Req_Model itemupdateRequestModel = new AppointmentUpdateItem_Req_Model(jsonAddDataInApi, appointmentModel.getLeadId(), String.valueOf(updateAppointmentItemData.getIlmmId()));
            String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
            Gson gson = new Gson();
            String updateAppointmentReqest = gson.toJson(itemupdateRequestModel);
            HyperLog.i("TAG addJobReqest", new Gson().toJson(itemAddRequestModel));
            OfflineDataController.getInstance().addInOfflineDB(Service_apis.updateItemOnAppointment, updateAppointmentReqest, dateTime);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void addItemOnAppointment() {
        try {
            HyperLog.i("TAG", "addItemsOnJob(M) started");
            List<AppointmentTax> apptaxList = new ArrayList<>();
            List<Tax> taxListFilter = new ArrayList<>();
            for (Tax tax : listFilter) {
                if (tax.isSelect()) {
                    if (getIntent().hasExtra("UpdateItemRequirmentGethering")) {
                        if (isTaxUpdated) {
                            taxListFilter.add(tax);
                            getTaxIdForCheckSigleTax(tax);
                        } else {
                            if (getIntent().hasExtra("UpdateItemRequirmentGethering")) {
                                if (updateAppointmentItemData.getTax() != null && updateAppointmentItemData.getTax().size() > 0) {
                                    Tax addTax = new Tax();
                                    addTax.setTaxId("" + updateAppointmentItemData.getTax().get(0).getTaxId());
                                    addTax.setLabel(updateAppointmentItemData.getTax().get(0).getLabel());
                                    addTax.setRate(updateAppointmentItemData.getTax().get(0).getRate());
                                    addTax.setTaxComponents(updateAppointmentItemData.getTax().get(0).getTaxComponents());
                                    taxListFilter.add(addTax);
                                    getTaxIdForCheckSigleTax(addTax);
                                }
                            }
                        }


                    } else {
                        taxListFilter.add(tax);
                        getTaxIdForCheckSigleTax(tax);
                    }
                }
            }
            if (taxListFilter.size() > 0) {
                appointmentTax = new AppointmentTax(Integer.parseInt(taxListFilter.get(0).getTaxId()), taxListFilter.get(0).getRate(), taxListFilter.get(0).getLabel(), taxListFilter.get(0).getTaxComponents());
                apptaxList.add(appointmentTax);
            }
//            else{
//                appointmentTax = new AppointmentTax(Integer.parseInt("0"),
//                        "0", "0", taxListFilter);
//            }
//            apptaxList.add(appointmentTax);

            int isPartParent = 0;
            String partTempId = "";
            if (partsList != null && !partsList.isEmpty()) {
                isPartParent = 1;
                partTempId = AppUtility.getRandomUUID();
            }
            if (edt_serialNo.getText().toString().equals("")) {
                edt_serialNo.setText("0");
            }
            if (itemType.equals("")) {
                itemType = "1";
            }
            if (jtId.equals("")) {
                jtId = "0";
            }
            if (edt_item_qty.getText().toString().isEmpty()) {
                edt_item_qty.setText("0");
            }

            appintmentItemDataModel = new AppintmentItemDataModel(edt_item_desc.getText().toString(), "", inm, edt_part_no.getText().toString(), edt_item_qty.getText().toString(), edt_item_rate.getText().toString(), edt_unit.getText().toString(), apptaxList, itemId, edt_hsnCode.getText().toString(), String.valueOf(taxAmount), Integer.parseInt(dataType), edt_item_disc.getText().toString(), Integer.parseInt(itemType), edt_item_supplier.getText().toString(), isBillable, isBillableChange, appointmentModel.getTempId(), String.valueOf(isPartParent), String.valueOf(isPartChild), partTempId, Integer.parseInt(dataType), edt_serialNo.getText().toString(), Integer.parseInt(isGrouped), 0);


            AppointmentAddItem_Res addItem_res = new AppointmentAddItem_Res(itemId, inm, Integer.parseInt(dataType), Integer.parseInt(itemType), edt_item_rate.getText().toString(), edt_item_supplier.getText().toString(), edt_serialNo.getText().toString(), edt_item_qty.getText().toString(), edt_item_disc.getText().toString(), Integer.parseInt(isGrouped), apptaxList, edt_item_desc.getText().toString(), edt_hsnCode.getText().toString(), edt_part_no.getText().toString(), edt_unit.getText().toString(), isBillableChange, appointmentModel.getTempId(), "", "", "", "", taxAmount, String.valueOf(isPartParent), String.valueOf(isPartChild), partTempId, Integer.parseInt(dataType), jtId);
            // *add api for appointment item**/
            try {
                if (!isBillable.equals("")) {
                    // appintmentItemDataModel.setIsBillable(isBillable);
                }
                if (addItemOnInvoice) {
                    //  appintmentItemDataModel.setIsBillable("1");
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }

            /**Code For Offline Online**/
            List<AppointmentItemDataInMap> itemDataInMaps = new ArrayList<>();
            AppointmentItemDataInMap itemDataInMap = new AppointmentItemDataInMap("", appintmentItemDataModel);
            itemDataInMaps.add(itemDataInMap);
            List<AppointmentAddItem_Res> addItem_resList = new ArrayList<>();
            addItem_resList.add(addItem_res);

            List<AppointmentItemDataInMap> itemDataInMaps1 = new ArrayList<>(itemDataInMaps);
            if (appointmentModel != null && appointmentModel.getItemData() != null) {
                itemDataInMaps1.addAll(appointmentModel.getItemData());
            }
            String jsonAddDataDB1 = new Gson().toJson(itemDataInMaps1);
            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().updateAppointmentItem(appId, jsonAddDataDB1);
            appointmentModel.setItemData(itemDataInMaps1);
            addItemWithAppointmentSync(addItem_resList);


            finish_Activity();
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperLog.i("TAG", "addItemsOnJob(M) exception:" + ex.toString());
        }
    }

    private void addItemWithAppointmentSync(List<AppointmentAddItem_Res> itemDataList) {
        try {
            boolean noItem = false;
            List<Offlinetable> offlinetableList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).offlinemodel().getOfflinetablesById(Service_apis.addItemOnAppointment);
            if (offlinetableList != null && offlinetableList.size() > 0) {
                HyperLog.i("TAG", "addItemWithJobSync(M) item found");

                for (Offlinetable offlinetableModel : offlinetableList) {

                    AppointmentItemAdd_RequestModel reqModel = new Gson().fromJson(offlinetableModel.getParams(), AppointmentItemAdd_RequestModel.class);
                    if (reqModel.getAppId().equals(appId)) {

                        HyperLog.i("TAG", "addItemWithJobSync(M) item found 1");
                        HyperLog.i("TAG reqModel1", new Gson().toJson(reqModel));

                        offlinetableModel.setParams(new Gson().toJson(reqModel));
                        HyperLog.i("TAG offlinetableModel", new Gson().toJson(offlinetableModel));

                        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).offlinemodel().update(offlinetableModel);
                        noItem = true;

                    }
                }
            }
            if (!noItem) {
                List<AppointmentAddItem_Res> addItem_resList = new ArrayList<>();
                addItem_resList.addAll(itemDataList);
                String jsonAddDataInApi = new Gson().toJson(addItem_resList);
                AppointmentItemAdd_RequestModel itemAddRequestModel = new AppointmentItemAdd_RequestModel(jsonAddDataInApi, appointmentModel.getLeadId(), appId);
                String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
                Gson gson = new Gson();
                String addAppointmentReqest = gson.toJson(itemAddRequestModel);
                HyperLog.i("TAG addJobReqest", new Gson().toJson(itemAddRequestModel));
                OfflineDataController.getInstance().addInOfflineDB(Service_apis.addItemOnAppointment, addAppointmentReqest, dateTime);
            } else {
                List<AppointmentAddItem_Res> addItem_resList = new ArrayList<>();
                addItem_resList.addAll(itemDataList);
                String jsonAddDataInApi = new Gson().toJson(addItem_resList);
                AppointmentItemAdd_RequestModel itemAddRequestModel = new AppointmentItemAdd_RequestModel(jsonAddDataInApi, appointmentModel.getLeadId(), appId);
                String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
                Gson gson = new Gson();
                String addAppointmentReqest = gson.toJson(itemAddRequestModel);
                HyperLog.i("TAG addJobReqest", new Gson().toJson(itemAddRequestModel));
                OfflineDataController.getInstance().addInOfflineDB(Service_apis.addItemOnAppointment, addAppointmentReqest, dateTime);

            }

        } catch (Exception e) {
            HyperLog.i("TAG", "addItemWithJobSync(M) exception:" + e.toString());

            e.printStackTrace();
        }

    }

    private void addItemsOnJob(List<Tax> taxListFilter) {
        try {
            HyperLog.i("TAG", "addItemsOnJob(M) started");

            // for creating a unique id for the item and its part so that we can identify
            // whether the item is having parts and which are its parts


            int isPartParent = 0;
            String partTempId = "";
            if (partsList != null && !partsList.isEmpty()) {
                isPartParent = 1;
                partTempId = AppUtility.getRandomUUID();
            }
            if (edt_item_qty.getText().toString().isEmpty()) {
                edt_item_qty.setText("0");
            }
            InvoiceItemDataModel addItemDataModel = new InvoiceItemDataModel(autocomplete_item.getText().toString().trim(), inm, itemId, dataType, itemType, edt_item_rate.getText().toString().trim(),
                    edt_item_qty.getText().toString().trim(), edt_item_disc.getText().toString().trim(),
                    edt_item_desc.getText().toString().trim(), edt_hsnCode.getText().toString().trim(),
                    edt_part_no.getText().toString().trim(), edt_unit.getText().toString().trim(),
                    taxamount_value_txt.getText().toString().trim(), edt_item_supplier.getText().toString().trim(),
                    taxListFilter, jtId, edt_serialNo.getText().toString().trim(), isBillableChange, equipmentId,
                    "", partTempId, isPartParent, 0, isRemoveStock, stkusrId);

            try {
                if (!isBillable.equals("")) {
                    addItemDataModel.setIsBillable(isBillable);
                }
                if (addItemOnInvoice) {
                    addItemDataModel.setIsBillable("1");
                }
                if (isSerialNoSelected) {
                    addItemDataModel.setIjmmId(ijmmId);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            itemDataList.add(addItemDataModel);


            // for adding parts
            // changes by shivani

            //TODO need to be checked to be done later
            for (int i = 0; i < partsList.size(); i++) {
                itemDataList.add(new InvoiceItemDataModel(partsList.get(i).getInm(), partsList.get(i).getInm(), partsList.get(i).getItemId(), dataType, partsList.get(i).getType(), partsList.get(i).getRate(), partsList.get(i).getQty(), partsList.get(i).getDiscount(), partsList.get(i).getDes(), partsList.get(i).getHsncode(), partsList.get(i).getPno(), partsList.get(i).getUnit(), "0", partsList.get(i).getSupplierCost(), partsList.get(i).getTax(), "", partsList.get(i).getSerialNo(), partsList.get(i).getIsBillableChange(), equipmentId, partsList.get(i).getIsBillable(), partTempId, 0, 1, isRemoveStock, stkusrId));
            }
            if (!isSerialNoSelected) {
//            Add  item's in job**
                if (jobModel.getJobId().equals(jobModel.getTempId())) {
                    // for updating the local database
                    if (jobModel != null && jobModel.getItemData() != null) {
                        itemDataList.addAll(jobModel.getItemData());
                    }
                    AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJobitems(jobId, itemDataList);
                    HyperLog.i("TAG  1", new Gson().toJson(itemDataList));
                    // changed by shivani on 27 june
                    addItemWitoutJobSync(itemDataList);
                } else {
                    // for updating the local database with all the items
                    List<InvoiceItemDataModel> itemDataListall = new ArrayList<>(itemDataList);

                    if (jobModel != null && jobModel.getItemData() != null) {
                        HyperLog.i("TAG  23", new Gson().toJson(jobModel.getItemData()));
                        itemDataListall.addAll(jobModel.getItemData());
                    }
                    AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJobitems(jobModel.getJobId(), itemDataListall);

                    // set item list in job model as well
                    jobModel.setItemData(itemDataListall);

                    HyperLog.i("TAG  2", new Gson().toJson(itemDataListall));
                    HyperLog.i("TAG  2", new Gson().toJson(itemDataList));
                    // changed by shivani on 27 june previously only addItemDataModel was sending
                    addItemWithJobSync(itemDataList);
                }
            }
            HyperLog.i("TAG", "addItemsOnJob(M) finished");

            // for notifying job overview page
            EotApp.getAppinstance().getJobFlagOverView();
//            EotApp.getAppinstance().getNotifyForItemCount();

            if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
                Log.e("checkEmpty:;", warrantyValue + " " + warrantyType);
                addItemDataModel.setWarrantyType(warrantyType);
                addItemDataModel.setWarrantyValue(warrantyValue);
                addItemDataModel.setIsGrouped(isGrouped);
                addItemDataModel.setInm(autocomplete_item.getText().toString().trim());
                if (isSerialNoSelected) {
                    convertInEquip(addItemDataModel);
                } else {
                    AppUtility.progressBarShow(this);
                    invoiceItemDataModelWithIjmmId = addItemDataModel;
                }
            } else {
                finish_Activity();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperLog.i("TAG", "addItemsOnJob(M) exception:" + ex.toString());
        }

    }


    private void addItemWithJobSync(List<InvoiceItemDataModel> addItemDataModel) {
        try {
            HyperLog.i("TAG", "addItemWithJobSync(M) start");

            boolean noItem = false;
            List<Offlinetable> offlinetableList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).offlinemodel().getOfflinetablesById(Service_apis.addItemOnJob);
            if (offlinetableList != null && offlinetableList.size() > 0) {
                HyperLog.i("TAG", "addItemWithJobSync(M) item found");

                for (Offlinetable offlinetableModel : offlinetableList) {
                    AddInvoiceItemReqModel reqModel = new Gson().fromJson(offlinetableModel.getParams(), AddInvoiceItemReqModel.class);
                    if (reqModel.getJobId().equals(jobId)) {

                        HyperLog.i("TAG", "addItemWithJobSync(M) item found 1");
                        HyperLog.i("TAG reqModel1", new Gson().toJson(reqModel));

                        reqModel.getItemData().addAll(addItemDataModel);
                        HyperLog.i("TAG reqModel2", new Gson().toJson(reqModel));

                        offlinetableModel.setParams(new Gson().toJson(reqModel));
                        HyperLog.i("TAG offlinetableModel", new Gson().toJson(offlinetableModel));

                        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).offlinemodel().update(offlinetableModel);
                        noItem = true;
                        break;
                    }
                }

                if (!noItem) {
                    List<InvoiceItemDataModel> list = new ArrayList<>(addItemDataModel);
                    AddInvoiceItemReqModel object = new AddInvoiceItemReqModel(jobModel.getJobId(), list, addItemOnInvoice, locId);
                    String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
                    Gson gson = new Gson();
                    String addJobReqest = gson.toJson(object);
                    HyperLog.i("TAG addJobReqest", new Gson().toJson(object));
                    OfflineDataController.getInstance().addInOfflineDB(Service_apis.addItemOnJob, addJobReqest, dateTime);
                }
                //(24-aug-23) Add "else" condition, because when "noItem" is true its not run api only store data in offline table.
                else {
                    OfflineDataController.getInstance().getTotalRequest();
                }
                EotApp.getAppinstance().notifyApiItemAddEdit_Observer(Service_apis.addItemOnJob, jobId);
            } else {
                HyperLog.i("TAG", "addItemWithJobSync(M) item added in offline quque");

                List<InvoiceItemDataModel> list = new ArrayList<>(addItemDataModel);
                AddInvoiceItemReqModel object = new AddInvoiceItemReqModel(jobModel.getJobId(), list, addItemOnInvoice, locId);
                String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
                Gson gson = new Gson();
                String addJobReqest = gson.toJson(object);
                OfflineDataController.getInstance().addInOfflineDB(Service_apis.addItemOnJob, addJobReqest, dateTime);
                EotApp.getAppinstance().notifyApiItemAddEdit_Observer(Service_apis.addItemOnJob, jobId);
            }
            HyperLog.i("TAG", "addItemWithJobSync(M) finish");
        } catch (Exception e) {
            HyperLog.i("TAG", "addItemWithJobSync(M) exception:" + e.toString());

            e.printStackTrace();
        }


    }


    /***Add Item's Without Job Id**/
    private void addItemWitoutJobSync(List<InvoiceItemDataModel> itemDataList) {
        try {
            HyperLog.i("TAG", "addItemWitoutJobSync(M) start");

            JobOfflineDataModel jobOfflineDataModel = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobOfflineDao().getJobofflineDataForInvoice(Service_apis.addItemOnJob, jobModel.getTempId());
            if (jobOfflineDataModel != null) {
                AddInvoiceItemReqModel addInvoiceItemReqModel = new Gson().fromJson(jobOfflineDataModel.getParams(), AddInvoiceItemReqModel.class);
                addInvoiceItemReqModel.setItemData(itemDataList);
                jobOfflineDataModel.setParams(new Gson().toJson(addInvoiceItemReqModel));
                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobOfflineDao().update(jobOfflineDataModel);
                //  HyperLog.i(" Item added:", "addItemWitoutJobSync----");
                HyperLog.i("TAG", "addItemWitoutJobSync(M) addItemWitoutJobSync");

            } else {
                String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
                Gson gson = new Gson();
                AddInvoiceItemReqModel addInvoiceItem = new AddInvoiceItemReqModel(jobId, itemDataList, addItemOnInvoice, locId);
                JobOfflineDataModel model = new JobOfflineDataModel(Service_apis.addItemOnJob, gson.toJson(addInvoiceItem), dateTime, jobModel.getTempId());
                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobOfflineDao().insertJobOfflineData(model);
                HyperLog.i("TAG", "addItemWitoutJobSync(M) addItemWitoutJobSync else part");

            }
            HyperLog.i("TAG", "addItemWitoutJobSync(M) finish");


        } catch (Exception ex) {
            ex.printStackTrace();
            HyperLog.i("TAG", "addItemWitoutJobSync(M) exception:" + ex.toString());
        }

    }

    private void updateItemsOnJob(List<Tax> taxListFilter) {
        try {
            HyperLog.i("TAG", "updateItemsOnJob(M) start");


            if (edt_item_qty.getText().toString().isEmpty()) {
                edt_item_qty.setText("0");
            }
            int isPartParent = 0, isPartChild = 0;

            if (updateItemDataModel.getParentId() != null && !updateItemDataModel.getParentId().isEmpty() && !updateItemDataModel.getParentId().equalsIgnoreCase("0")) {
                isPartChild = 1;
            } else if (updateItemDataModel.getParentId() != null && updateItemDataModel.getParentId().equalsIgnoreCase("0") && updateItemDataModel.getIsPartTempId() != null && !updateItemDataModel.getIsPartTempId().isEmpty()) {
                isPartParent = 1;
            }
            InvoiceItemDataModel updateItemModel = new InvoiceItemDataModel(autocomplete_item.getText().toString().trim(), inm, updateItemDataModel.getIjmmId(), itemId, dataType, itemType, edt_item_rate.getText().toString().trim(), edt_item_qty.getText().toString().trim(), edt_item_disc.getText().toString().trim(), edt_item_desc.getText().toString().trim(), edt_hsnCode.getText().toString().trim(), edt_part_no.getText().toString().trim(), edt_unit.getText().toString().trim(), taxamount_value_txt.getText().toString().trim(), edt_item_supplier.getText().toString().trim(), taxListFilter, jtId, edt_serialNo.getText().toString().trim(), updateItemDataModel.getItemConvertCount(), isBillableChange, equipmentId, updateItemDataModel.getIsPartTempId(), isPartParent, isPartChild, updateItemDataModel.getGroupId(), isRemoveStock
                    //        , updateItemDataModel.getIsBillable()
                    , stkusrId);

            try {
                if (!isBillable.equals("")) {
                    updateItemModel.setIsBillable(isBillable);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }


            List<InvoiceItemDataModel> itemDataList = new ArrayList<>();
            /*not sync item update**/
            if (updateItemDataModel.getDataType().equals("1") || (updateItemDataModel.getDataType().equals("2")) && updateItemDataModel.getItemType().equals("0") || updateItemDataModel.getItemType().equals("")) {

                HyperLog.i("TAG", "updateItemsOnJob(M) not sync item update");

                /*check inventry item **/
                if (jobModel != null && jobModel.getItemData() != null) {
                    HyperLog.i("TAG", "updateItemsOnJob(M) check inventry item ");

                    for (InvoiceItemDataModel tempModel : jobModel.getItemData()) {
                        if (tempModel.getItemId().equals(updateItemDataModel.getItemId())) {
                            jobModel.getItemData().remove(tempModel);
                            break;
                        }
                    }
                }
            } else if (updateItemDataModel.getDataType().equals("1") && updateItemDataModel.getItemType().equals("1")) {
                /*check Non- inventry item **/
                HyperLog.i("TAG", "updateItemsOnJob(M) check Non- inventry item ");

                if (jobModel != null && jobModel.getItemData() != null) {
                    for (InvoiceItemDataModel tempModel : jobModel.getItemData()) {
                        if (tempModel.getInm().equals(updateItemDataModel.getInm())) {
                            jobModel.getItemData().remove(tempModel);
                            break;
                        }
                    }
                }
            } else if (updateItemDataModel.getDataType().equals("3")) {
                /* Job services **/
                HyperLog.i("TAG", "updateItemsOnJob(M) Job services case");

                if (jobModel != null && jobModel.getItemData() != null) {
                    for (InvoiceItemDataModel tempModel : jobModel.getItemData()) {
                        if (tempModel.getItemId().equals(updateItemDataModel.getItemId())) {
                            jobModel.getItemData().remove(tempModel);
                            break;
                        }
                    }
                }
            }
            itemDataList.add(updateItemModel);
            if (jobModel != null && jobModel.getItemData() != null) {
                itemDataList.addAll(jobModel.getItemData());
            }


            /*update Invoice item's in job***/
            AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJobitems(jobId, itemDataList);


            /*UPDATE Item Without Job Sync**/
            if (jobModel.getJobId().equals(jobModel.getTempId())) {
                updateItemWithoutJobSync(updateItemModel);
            } else {
                if (updateItemDataModel.getIjmmId().equals("")) {
                    /*Item Update Without Sync***/
                    notSyncItemUpdate(updateItemModel);
                } else {
                    /*Item update After Sync**/
                    updateSyncItem(updateItemModel);
                }
            }
            // for notifying job overview page
            EotApp.getAppinstance().getJobFlagOverView();
            EotApp.getAppinstance().getNotifyForItemCount();

            HyperLog.i("TAG", "updateItemsOnJob(M) finish");

            finish_Activity();
        } catch (Exception ex) {
            ex.printStackTrace();
            HyperLog.i("TAG", "updateItemsOnJob(M) exception:" + ex.toString());

        }

    }

    /***Item update After Sync**/
    private void updateSyncItem(InvoiceItemDataModel updateItemModel) {
        try {
            List<Offlinetable> offlinetableList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).offlinemodel().getOfflinetablesById(Service_apis.updateItemInJobMobile);
            List<InvoiceItemDataModel> tempList = new ArrayList<>();
            tempList.add(updateItemModel);
            if (offlinetableList.size() > 0) {
                for (Offlinetable offLineModel : offlinetableList) {
                    AddInvoiceItemReqModel updateItemReqModel = new Gson().fromJson(offLineModel.getParams(), AddInvoiceItemReqModel.class);
                    if (updateItemReqModel.getJobId().equals(jobId)) {
                        updateItemReqModel.getItemData().clear();
                        updateItemReqModel.setItemData(tempList);
                        offLineModel.setParams(new Gson().toJson(updateItemReqModel));
                        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).offlinemodel().update(offLineModel);
                        break;
                    }
                }
            } else {
                AddInvoiceItemReqModel updateItemReqModel = new AddInvoiceItemReqModel(jobId, tempList, addItemOnInvoice, locId);
                String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
                Gson gson = new Gson();
                String addJobReqest = gson.toJson(updateItemReqModel);
                OfflineDataController.getInstance().addInOfflineDB(Service_apis.updateItemInJobMobile, addJobReqest, dateTime);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /***Job Sync But Item Not Sync Update
     * @param  updateItemModel**/
    private void notSyncItemUpdate(InvoiceItemDataModel updateItemModel) {
        try {
            List<Offlinetable> offlineTableList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).offlinemodel().getOfflinetablesById(Service_apis.addItemOnJob);

            if (offlineTableList != null && offlineTableList.size() > 0) {
                for (Offlinetable offLineModel : offlineTableList) {
                    AddInvoiceItemReqModel addItemReqModel = new Gson().fromJson(offLineModel.getParams(), AddInvoiceItemReqModel.class);
                    if (addItemReqModel.getJobId().equals(jobId)) {
                        for (InvoiceItemDataModel tempModel : addItemReqModel.getItemData()) {
                            if (tempModel.getTempNm().equals(updateItemDataModel.getTempNm())) {
                                addItemReqModel.getItemData().remove(tempModel);
                                addItemReqModel.getItemData().add(updateItemModel);
                                offLineModel.setParams(new Gson().toJson(addItemReqModel));
                                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).offlinemodel().update(offLineModel);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (Exception ec) {
            ec.printStackTrace();
        }
    }

    private void updateItemWithoutJobSync(InvoiceItemDataModel updateItemModel) {
        try {
            HyperLog.i("TAG", "updateItemWithoutJobSync(M) start");
            JobOfflineDataModel jobOfflineDataModel = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobOfflineDao().getJobofflineDataForInvoice(Service_apis.addItemOnJob, jobModel.getTempId());
            List<InvoiceItemDataModel> tempItemList = new ArrayList<>();
            if (jobOfflineDataModel != null) {
                AddInvoiceItemReqModel addInvoiceItemReqModel = new Gson().fromJson(jobOfflineDataModel.getParams(), AddInvoiceItemReqModel.class);
                for (InvoiceItemDataModel tempModel : addInvoiceItemReqModel.getItemData()) {
                    if (tempModel.getTempNm().equals(updateItemDataModel.getTempNm())) {
                        addInvoiceItemReqModel.getItemData().remove(tempModel);
                        addInvoiceItemReqModel.getItemData().add(updateItemModel);
                        jobOfflineDataModel.setParams(new Gson().toJson(addInvoiceItemReqModel));
                        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobOfflineDao().update(jobOfflineDataModel);
                        break;
                    }
                }
            } else {
                tempItemList.add(updateItemModel);
                String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
                Gson gson = new Gson();
                AddInvoiceItemReqModel addInvoiceItem = new AddInvoiceItemReqModel(jobId, tempItemList, addItemOnInvoice, locId);
                JobOfflineDataModel model = new JobOfflineDataModel(Service_apis.addItemOnJob, gson.toJson(addInvoiceItem), dateTime, jobModel.getTempId());
                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobOfflineDao().insertJobOfflineData(model);
            }
            HyperLog.i("TAG", "updateItemWithoutJobSync(M) finish");
        } catch (Exception e) {
            e.printStackTrace();
            HyperLog.i("TAG", "updateItemWithoutJobSync(M) exception:" + e.toString());
        }
    }

    private void fw_service_filed_hide(int type) {
        switch (type) {
            case 1:/* Show/enable all fields when inventry/Non inventry item Added */
                item_supplier_layout.setVisibility(View.VISIBLE);
                supplier_view.setVisibility(View.VISIBLE);

                item_hsnCode_layout.setVisibility(View.VISIBLE);
                hsncode_view.setVisibility(View.VISIBLE);

                item_partNo_layout.setVisibility(View.VISIBLE);
                part_no_view.setVisibility(View.VISIBLE);

                item_unit_layout.setVisibility(View.VISIBLE);
                unit_view.setVisibility(View.VISIBLE);

                item_discount_layout.setVisibility(View.VISIBLE);
                disc_view.setVisibility(View.VISIBLE);

                serialNo_layout.setVisibility(View.VISIBLE);
                seroal_no_view.setVisibility(View.VISIBLE);

                item_tax_rate_layout.setVisibility(View.VISIBLE);
                tax_rate_view.setVisibility(View.VISIBLE);
                break;

            case 2:/* hide supplier cost,han code,part no  when fieldworker added for Item */
                item_supplier_layout.setVisibility(View.GONE);
                supplier_view.setVisibility(View.GONE);

                item_hsnCode_layout.setVisibility(View.GONE);
                hsncode_view.setVisibility(View.GONE);

                item_partNo_layout.setVisibility(View.GONE);
                part_no_view.setVisibility(View.GONE);

                item_unit_layout.setVisibility(View.GONE);
                unit_view.setVisibility(View.GONE);

                item_discount_layout.setVisibility(View.VISIBLE);
                disc_view.setVisibility(View.VISIBLE);

                serialNo_layout.setVisibility(View.GONE);
                seroal_no_view.setVisibility(View.GONE);

                item_tax_rate_layout.setVisibility(View.VISIBLE);
                tax_rate_view.setVisibility(View.VISIBLE);
                break;

            case 3:/* hide supplier cost,han code,part no & unit  when Job service(title) added for Item */
                item_supplier_layout.setVisibility(View.GONE);
                supplier_view.setVisibility(View.GONE);

                disc_view.setVisibility(View.VISIBLE);
                item_discount_layout.setVisibility(View.VISIBLE);

                item_hsnCode_layout.setVisibility(View.VISIBLE);
                hsncode_view.setVisibility(View.VISIBLE);

                item_partNo_layout.setVisibility(View.VISIBLE);
                part_no_view.setVisibility(View.VISIBLE);

                item_unit_layout.setVisibility(View.VISIBLE);
                unit_view.setVisibility(View.VISIBLE);

                serialNo_layout.setVisibility(View.VISIBLE);
                seroal_no_view.setVisibility(View.VISIBLE);

                item_tax_rate_layout.setVisibility(View.VISIBLE);
                tax_rate_view.setVisibility(View.VISIBLE);
                break;
        }
        hideShowInvoiceItemDetails();
    }


    private void getJobById() {
        jobModel = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
        if (jobModel == null) {
            HyperLog.i("", "getJobById(M) job model null found");
            finish();
        } else if (jobModel.getItemData() != null && jobModel.getItemData().size() == 0) {
            jobItemCountForFlag = true;
        }
    }

    private void finish_Activity() {
        if (!NOITEMRELECT) {
            try {
                if (locId != null && !locId.equals("0") && jobModel != null && jobModel.getLocId() != null && !locId.equals(jobModel.getLocId())) {
                    jobModel.setLocId(locId);
                    AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateJob(jobModel);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (getIntent().hasExtra("AddRequirmentGetheringItem")) {
            Intent intent = new Intent();
            // intent.putExtra("AddItemData",appintmentItemDataModel);
            setResult(ADD_REQUIRED_ITEM, intent);

        } else if (bundle.getBoolean("addItemOnInvoice")) {
            Intent intent = new Intent();
            intent.putExtra("AddInvoiceItem", "AddItem");
            setResult(ADD_ITEM_DATA, intent);
        }

        /*Notify Job Overview For Item Flag call only when Item First time Added****/
        if (jobItemCountForFlag) {
            EotApp.getAppinstance().getJobFlagOverView();
        }
        this.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDialogTax() {
        if (listFilter.size() > 0) {
            try {
                Collections.sort(listFilter, (tax, t1) -> t1.getLabel().compareTo(tax.getLabel()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            final Dialog dialog = new Dialog(this);
            dialog.setCancelable(false);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.tax_dialog_layout2);
            //   final LinearLayout root = dialog.findViewById(R.id.root);
            final RadioGroup radioGroup = dialog.findViewById(R.id.radio_tax);
            TextView txtRateHeading = dialog.findViewById(R.id.txtRateHeading);
            txtRateHeading.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.tax_rate));
            TextView selected_tax_nm = dialog.findViewById(R.id.selected_tax_nm);
            /*           add all taxes into the views */
            generateDynamicView(listFilter, radioGroup, selected_tax_nm);
            Button cancel_btn = dialog.findViewById(R.id.cancel_btn);
            cancel_btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.close));
            cancel_btn.setOnClickListener(v -> {
                float localtax = getTotalApplyTax();
                total_tax = localtax;
                if (getIntent().hasExtra("InvoiceItemDataModel") || getIntent().hasExtra("UpdateItemRequirmentGethering")) {
                    if (isTaxUpdated) {
                        tax_value_txt.setText((String.valueOf(localtax)));
                    } else {
                        if (getIntent().hasExtra("InvoiceItemDataModel")) {
                            if (updateItemDataModel.getTax() != null && updateItemDataModel.getTax().size() > 0) {
                                tax_value_txt.setText("" + Float.parseFloat(updateItemDataModel.getTax().get(0).getRate()));
                            }
                        } else if (getIntent().hasExtra("UpdateItemRequirmentGethering")) {
                            if (updateAppointmentItemData.getTax() != null && updateAppointmentItemData.getTax().size() > 0) {
                                tax_value_txt.setText("" + Float.parseFloat(updateAppointmentItemData.getTax().get(0).getRate()));
                            }
                        }
                    }
                } else {
                    tax_value_txt.setText((String.valueOf(localtax)));
                }
                calculateTaxRate();
                total_Amount_cal();
                dialog.dismiss();
            });
            dialog.show();
        }
    }


    private float getTotalApplyTax() {
        float applytax = 0;
        try {
            for (Tax selectedtax : listFilter) {
                if (selectedtax.isSelect()) {
                    if (updateItem) {
                        if (selectedtax.getOldTax() != null && !selectedtax.getOldTax().equals(""))
                            applytax += Float.parseFloat(selectedtax.getOldTax());
                    } else {
                        applytax += Float.parseFloat(selectedtax.getRate());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return applytax;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void generateDynamicView(final List<Tax> listFilter, RadioGroup radioGroup, final TextView selected_tax_nm) {
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        Typeface typeface = getResources().getFont(R.font.arimo_bold);
        for (int i = 0; i < listFilter.size(); i++) {
            Tax tax = listFilter.get(i);
            RadioButton radioButton = new RadioButton(this);
            if (listFilter.get(i).isSelect()) {
                radioButton.setChecked(true);
            }
            radioButton.setId(i);
            radioButton.setTag(tax.getTaxId());
            radioButton.setText(tax.getLabel());
            radioButton.setTextSize(11);
            radioButton.setTypeface(typeface);
            radioButton.setTextColor(Color.parseColor("#8C9293"));
            radioButton.setBackground(ContextCompat.getDrawable(this, R.drawable.bottom_line));
            radioButton.setHintTextColor(Color.BLACK);

            if (tax.getRate() != null) {
                if (tax.getRate().isEmpty()) {
                    radioButton.setText(tax.getLabel() + " (0 %)");
                    if (tax.getStatus().equals("0")) {
                        radioButton.setEnabled(false);
//                        radioButton.setText(Html.fromHtml("<font color= \"#EBEBE4\">" +tax.getLabel() + " (0 %) </font>"+"<font color= \"#ff0000\">" + "("+LanguageController.getInstance().getMobileMsgByKey(AppConstant.inactive_radio_btn)+") </font>") );
                        radioButton.setText(tax.getLabel() + " (0 %) (" + LanguageController.getInstance().getMobileMsgByKey(AppConstant.inactive_radio_btn) + ")");
                        radioButton.setTextColor(Color.parseColor("#EBEBE4"));
                    }
                } else {
                    radioButton.setText(tax.getLabel() + " (" + tax.getRate() + "%)");
                    if (tax.getStatus().equals("0")) {
                        radioButton.setEnabled(false);
//                        radioButton.setText(Html.fromHtml("<font color= \"#EBEBE4\">" +tax.getLabel() + " (" + tax.getRate() + "%) "+ "</font>"+"<font color= \"#ff0000\">" + "("+LanguageController.getInstance().getMobileMsgByKey(AppConstant.inactive_radio_btn)+") </font>") );
                        radioButton.setText(tax.getLabel() + " (" + tax.getRate() + "%) " + "(" + LanguageController.getInstance().getMobileMsgByKey(AppConstant.inactive_radio_btn) + ")");
                        radioButton.setTextColor(Color.parseColor("#EBEBE4"));
                    }
                }
            }
            if (updateItem) {
                tax.setOldTax(tax.getRate());
            }
            if (tax.getStatus().equals("0")) {

                if (updateItemDataModel != null && updateItemDataModel.getTax() != null && updateItemDataModel.getTax().size() > 0 && updateItemDataModel.getTax().get(0).getTaxId().equals(tax.getTaxId()) || updateAppintmentItemDataModel != null && updateAppintmentItemDataModel.getTax() != null && updateAppintmentItemDataModel.getTax().size() > 0 && String.valueOf(updateAppintmentItemDataModel.getTax().get(0).getTaxId()).equals(tax.getTaxId())) {
                    radioGroup.addView(radioButton);
                }

            } else {
                radioGroup.addView(radioButton);
            }
            setSelectedTaxLable(selected_tax_nm);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            for (Tax tax : listFilter) {
                tax.setSelect(false);
            }
            RadioButton radioButton = group.findViewById(checkedId);
            int position = radioButton.getId();// String tagId=radioButton.getTag().toString();
            listFilter.get(position).setSelect(true);
            setSelectedTaxLable(selected_tax_nm);
            isTaxUpdated = true;
        });
    }

   /* @SuppressLint("SetTextI18n")
    private void generateDynamicViews(final List<Tax> listFilter, LinearLayout root, final TextView selected_tax_nm) {
        // final Set<String> tempTaxList = new HashSet<>();
        for (final Tax tax : listFilter) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.tax_adpter_layout2, null);
            final RadioButton checkbox = itemView.findViewById(R.id.checkbox);
            TextView appliedTax = itemView.findViewById(R.id.appliedTax);
            View view = itemView.findViewById(R.id.view);


            *//*This For Search Key***//*
            if (tax.getAppliedTax() != null && !tax.getAppliedTax().equals("") && appliedTax != null) {
                appliedTax.setVisibility(View.VISIBLE);
                appliedTax.setText(tax.getAppliedTax());
                view.setVisibility(View.VISIBLE);
            } else if (appliedTax != null) {
                appliedTax.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                checkbox.setBackground(getResources().getDrawable(R.drawable.view));
            } else {
                view.setVisibility(View.GONE);
                checkbox.setBackground(getResources().getDrawable(R.drawable.view));
            }


//            if (tax.isSelect())
//                tempTaxList.add(tax.getLabel());

            checkbox.setChecked(false);

            checkbox.setOnClickListener(v -> {
                *//*if (tempTaxList.contains(tax.getLabel())) {
                    tempTaxList.remove(tax.getLabel());
                    tax.setSelect(false);
                } else {

                    tempTaxList.add(tax.getLabel());
                    tax.setSelect(true);

                }*//*

                boolean t = false;
                for (Tax temptax : listFilter) {
                    if (temptax.isSelect() && !temptax.getLabel().equalsIgnoreCase(tax.getLabel())) {
                        t = true;
                        break;
                    }
                }

                if (!t) {
                    tax.setSelect(!tax.isSelect());
                    checkbox.setChecked(tax.isSelect());
                } else {
                    checkbox.setChecked(false);
                }

//                   else  if (tax.isSelect()) {
//                            tax.setSelect(false);
//                        }

                if (updateItem) {
                    tax.setOldTax(tax.getRate());
                }


                setSelectedTaxLable(selected_tax_nm);
            });

            checkbox.setChecked(tax.isSelect());

            checkbox.setHintTextColor(Color.BLACK);
            if (tax.getRate() != null) {
                if (tax.getRate().isEmpty()) {
                    checkbox.setText(tax.getLabel() + " (0 %)");
                } else {
                    checkbox.setText(tax.getLabel() + " (" + tax.getRate() + "%)");
                }
            }
            root.addView(itemView);
        }
        setSelectedTaxLable(selected_tax_nm);

    }
*/

    void setSelectedTaxLable(TextView selected_tax_nm) {
//        if (tempTaxList.size() == 0) {
//            selected_tax_nm.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.selected_tax_lable));
//        } else if (tempTaxList.size() >= 3)
//            selected_tax_nm.setText(tempTaxList.size() + " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.items_selected));
//        else
        // selected_tax_nm.setText(tempTaxList.toString().replace("[", "").replace("]", ""));
        for (Tax tax : listFilter) {
            if (tax.isSelect()) {
                selected_tax_nm.setText(tax.getLabel());
                break;
            } else {
                selected_tax_nm.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.selected_tax_lable));
            }
        }

    }


    /**
     * set background color when tab change item/fw/services
     */
    private void setTxtBkgColor(int selected_option) {

        item_select.setBackgroundResource(R.drawable.item_tab_off_bkg);
        item_select.setTextColor(getResources().getColor(R.color.colorPrimary));
        fw_select.setBackgroundResource(R.drawable.item_tab_off_bkg);
        fw_select.setTextColor(getResources().getColor(R.color.colorPrimary));
        service_select.setBackgroundResource(R.drawable.item_tab_off_bkg);
        service_select.setTextColor(getResources().getColor(R.color.colorPrimary));
        if (selected_option == 1) {//for item
            item_select.setBackgroundResource(R.drawable.item_tab_on_bkg);
            item_select.setTextColor(getResources().getColor(R.color.white));
            autocomplete_item.setText("");
            autocomplete_item.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.items_name) + " *");
            DP_OPEN = false;
            TAB_SELECT = 1;

            // for hiding in case of generate invoice
            if (addItemOnInvoice) rediogrp.setVisibility(View.GONE);
            else rediogrp.setVisibility(View.VISIBLE);


            hideShowInvoiceItemDetails();

        } else if (selected_option == 2) {//for fieldworker
            fw_select.setBackgroundResource(R.drawable.item_tab_on_bkg);
            fw_select.setTextColor(getResources().getColor(R.color.white));
            autocomplete_item.setText("");
            autocomplete_item.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.fieldworkers_name) + " *");
            DP_OPEN = true;
            TAB_SELECT = 2;
            ITEMSYNC = false;
            rediogrp.setVisibility(View.GONE);
            hideShowInvoiceItemDetails();
        } else {//for services
            service_select.setBackgroundResource(R.drawable.item_tab_on_bkg);
            service_select.setTextColor(getResources().getColor(R.color.white));
            autocomplete_item.setText("");
            autocomplete_item.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.services_name) + " *");
            DP_OPEN = true;
            TAB_SELECT = 3;
            ITEMSYNC = false;
            rediogrp.setVisibility(View.GONE);

            hideShowInvoiceItemDetails();
        }
    }


    /**
     * hide/show add item field accroding to admin authentication
     */
    private void hideShowInvoiceItemDetails() {
        CompPermission compPermission = App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0);
        if (compPermission.getModuleId().equals("1")) {
            if (compPermission.getDescription().equals("1")) {
                item_desc_layout.setVisibility(View.GONE);
                desc_view.setVisibility(View.GONE);
            }
            if (compPermission.getRate().equals("1")) {
                item_rate_layout.setVisibility(View.GONE);
                rate_view.setVisibility(View.GONE);
            }
            if (compPermission.getSupplierCost().equals("1")) {
                item_supplier_layout.setVisibility(View.GONE);
                supplier_view.setVisibility(View.GONE);
            }
            if (compPermission.getDiscount().equals("1")) {
                item_discount_layout.setVisibility(View.GONE);
                disc_view.setVisibility(View.GONE);
            }
            tax_layout = findViewById(R.id.tax_layout);
            if (compPermission.getTax().equals("1")) {
                tax_layout.setVisibility(View.GONE);
                tax_view.setVisibility(View.GONE);
            }
            if (compPermission.getAmount().equals("1")) {
                amount_layout.setVisibility(View.GONE);
                amount_view.setVisibility(View.GONE);
            }
            if (compPermission.getPno().equals("1")) {
                item_partNo_layout.setVisibility(View.GONE);
                part_no_view.setVisibility(View.GONE);
            }
            if (compPermission.getHsncode().equals("1")) {
                item_hsnCode_layout.setVisibility(View.GONE);
                hsncode_view.setVisibility(View.GONE);
            }
            if (compPermission.getUnit().equals("1")) {
                item_unit_layout.setVisibility(View.GONE);
                unit_view.setVisibility(View.GONE);
            }
            taxamount_layout = findViewById(R.id.taxamount_layout);
            if (compPermission.getTaxamnt().equals("1")) {
                taxamount_layout.setVisibility(View.GONE);
                taxrateAmount_view.setVisibility(View.GONE);
            }
            if (compPermission.getSerialNo().equals("1")) {
                serialNo_layout.setVisibility(View.GONE);
                seroal_no_view.setVisibility(View.GONE);
            }
            if (compPermission.getIsRateIncludingTax().equals("1")) {
                item_tax_rate_layout.setVisibility(View.GONE);
                tax_rate_view.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            if (v instanceof EditText) {
                ((EditText) v).setText(AppUtility.getRoundoff_amount(((EditText) v).getText().toString()));
            } else if (v instanceof TextView) {
                ((TextView) v).setText(AppUtility.getRoundoff_amount(((TextView) v).getText().toString()));
            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() >= 1) {
            if (charSequence.hashCode() == edt_item_desc.getText().hashCode())
                item_desc_layout.setHintEnabled(true);
            if (charSequence.hashCode() == edt_item_qty.getText().hashCode()) {
                item_qty_layout.setHintEnabled(true);
                if (chiled_add_stock_checkBox.isChecked()) {
                            if(!isFromQtyCancel) {
                                isFromQty = true;
                            getStockBalance();
                        }else {
                            stockQty = charSequence.toString();
                            isFromQtyCancel = false;
                        }

                }
            }
            if (charSequence.hashCode() == edt_item_rate.getText().hashCode()) {
                item_rate_layout.setHintEnabled(true);
                if (!firstTimeOnPage) {
                    calculateTaxRate();
                }
            }
            if (charSequence.hashCode() == edt_item_supplier.getText().hashCode())
                item_supplier_layout.setHintEnabled(true);
            if (charSequence.hashCode() == edt_item_disc.getText().hashCode()) {
                item_discount_layout.setHintEnabled(true);
                /* discount must not be gratter than 100 */
                if (getDisCalculationType != null && getDisCalculationType.equals("0")) {
                    if (!edt_item_disc.getText().toString().isEmpty() && Float.parseFloat(edt_item_disc.getText().toString()) > 100) {
                        showDisError(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discountError));
                        edt_item_disc.setText("0");
                    }
                } else if (getDisCalculationType != null && getDisCalculationType.equals("1")) {
                    if (!edt_item_disc.getText().toString().isEmpty() && !edt_item_rate.getText().toString().isEmpty() && Float.parseFloat(edt_item_disc.getText().toString()) > Float.parseFloat(edt_item_rate.getText().toString())) {
                        showDisError(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discountError));
                        edt_item_disc.setText("0");
                    }
                } else {
                    if (!edt_item_disc.getText().toString().isEmpty() && Float.parseFloat(edt_item_disc.getText().toString()) > 100) {
                        showDisError(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discountError));
                        edt_item_disc.setText("0");
                    }
                }
                if (!firstTimeOnPage) {
                    calculateTaxRate();
                }
            }
            if (charSequence.hashCode() == edt_part_no.getText().hashCode())
                item_partNo_layout.setHintEnabled(true);
            if (charSequence.hashCode() == edt_hsnCode.getText().hashCode())
                item_hsnCode_layout.setHintEnabled(true);
            if (charSequence.hashCode() == edt_unit.getText().hashCode())
                item_unit_layout.setHintEnabled(true);
            if (charSequence.hashCode() == edt_serialNo.getText().hashCode())
                serialNo_layout.setHintEnabled(true);
            if (charSequence.hashCode() == edt_item_tax_rate.getText().hashCode())
                item_tax_rate_layout.setHintEnabled(true);
            if (!firstTimeOnPage) {
                total_Amount_cal();
            }
        } else if (charSequence.length() <= 0) {
            if (charSequence.hashCode() == edt_item_desc.getText().hashCode())
                item_desc_layout.setHintEnabled(false);
            if (charSequence.hashCode() == edt_item_qty.getText().hashCode())
                item_qty_layout.setHintEnabled(false);
            if (charSequence.hashCode() == edt_item_rate.getText().hashCode()) {
                item_rate_layout.setHintEnabled(false);
                if (!firstTimeOnPage) {
                    calculateTaxRate();
                }
            }
            if (charSequence.hashCode() == edt_item_supplier.getText().hashCode())
                item_supplier_layout.setHintEnabled(false);
            if (charSequence.hashCode() == edt_item_disc.getText().hashCode()) {
                item_discount_layout.setHintEnabled(false);
                if (!firstTimeOnPage) {
                    calculateTaxRate();
                }
            }
            if (charSequence.hashCode() == edt_part_no.getText().hashCode())
                item_partNo_layout.setHintEnabled(false);
            if (charSequence.hashCode() == edt_hsnCode.getText().hashCode())
                item_hsnCode_layout.setHintEnabled(false);
            if (charSequence.hashCode() == edt_unit.getText().hashCode())
                item_unit_layout.setHintEnabled(false);
            if (charSequence.hashCode() == edt_serialNo.getText().hashCode())
                serialNo_layout.setHintEnabled(false);
            if (charSequence.hashCode() == edt_item_tax_rate.getText().hashCode())
                item_tax_rate_layout.setHintEnabled(false);
            if (!firstTimeOnPage) {
                total_Amount_cal();
            }
        }
    }

    private void showDisError(String msg) {
        AppUtility.alertDialog(this, "", msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> true);
    }

    /**
     * tax calculation for current add/edit item
     */
    private void total_Amount_cal() {

//       String getDisCalculationType = AppDataBase.getInMemoryDatabase(this).jobModel().disCalculationType(jobId);
//       String getTaxCalculationType = App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType();
        discount(getDisCalculationType);
        String qty = "", rate = "", dis = "";
        if (!edt_item_qty.getText().toString().equals("")) {
            qty = edt_item_qty.getText().toString();
        }
        if (!edt_item_rate.getText().toString().equals("")) {
            rate = edt_item_rate.getText().toString();
        }
        if (!edt_item_disc.getText().toString().equals("")) {
            dis = edt_item_disc.getText().toString();
        }
        Map<String, String> result = AppUtility.getCalculatedAmountForDiscount(qty, rate, dis, listFilter, getTaxCalculationType, getDisCalculationType, updateItem);
        taxamount_value_txt.setText(AppUtility.getRoundoff_amount(result.get("Tax")));
        amount_value_txt.setText(AppUtility.getRoundoff_amount(result.get("Amount")));

    }


    private void calculateTaxRate() {
        if (!App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsRateIncludingTax().equals("1")) {
//            String getDisCalculationType = AppDataBase.getInMemoryDatabase(this).jobModel().disCalculationType(jobId);
//            String getTaxCalculationType = App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType();
            discount(getDisCalculationType);
            String rate = "", dis = "";

            /* check of amount calculation */
            try {

                if (!edt_item_rate.getText().toString().equals("")) {
                    rate = edt_item_rate.getText().toString();
                }
                if (!edt_item_disc.getText().toString().equals("")) {
                    dis = edt_item_disc.getText().toString();
                }
                String result = AppUtility.getCalculatedTax(rate, dis, listFilter, getTaxCalculationType, getDisCalculationType, updateItem);
                edt_item_tax_rate.setText(result);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    @Override
    public void onBackPressed() {
        NOITEMRELECT = true;
        finish_Activity();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            AppUtility.hideSoftKeyboard(AddEditInvoiceItemActivity2.this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEmptyFields() {
        dataType = "";
        itemType = "";
        itemId = "";
        isBillable = "1";
        isBillableChange = "";
        total_tax = 0f;
        edt_item_desc.setText("");
        edt_item_qty.setText("1");
        edt_item_rate.setText("0");
        edt_item_supplier.setText("0");
        edt_item_disc.setText("0");
        tax_value_txt.setText("0");
        amount_value_txt.setText("0");
        taxamount_value_txt.setText("0");
        edt_part_no.setText("0");
        edt_hsnCode.setText("0");
        edt_serialNo.setText("");
        warrantyType = "";
        warrantyValue = "";
        isGrouped = "0";
        Log.e("checkEmpty:;", "setEmptyFields");
        rediogrp.setVisibility(View.GONE);
        IS_SERVICES_MANDATRY = false;
        setTaxDialogFiledsEmpty();
    }

    private void setTaxDialogFiledsEmpty() {
        for (Tax tax : listFilter) {
            tax.setSelect(false);
        }
    }

    /**** set default values 0 when new item added*/
    private void setDefaultValuesForAddNewItem() {
        /* quantity always be 1 initially*/
        edt_item_qty.setText("1");
        edt_item_rate.setText("0");
        edt_item_supplier.setText("0");
        edt_item_disc.setText("0");
        tax_value_txt.setText("0");
        amount_value_txt.setText("0");
        taxamount_value_txt.setText("0");
        edt_item_tax_rate.setText("0");
        edt_hsnCode.setHint(App_preference.getSharedprefInstance().getLoginRes().getHsnCodeLable());
        Log.e("checkEmpty:;", "setDefaultValuesForAddNewItem");
        warrantyType = "";
        warrantyValue = "";
        isGrouped = "0";
        firstTimeOnPage = false;
    }

    private void discount(String discount) {
        try {
            if (discount.equals("0"))
                edt_item_disc.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discount) + " (%)");
            else if (discount.equals("1"))
                edt_item_disc.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.discount));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private void getTaxIdForCheckSigleTax(Tax tax) {
        if (App_preference.getSharedprefInstance().getLoginRes().getTaxShowType().equals("2")) {
            if (tax.getTaxComponents() != null && tax.getTaxComponents().size() > 0) {
                for (TaxComponents tax2 : tax.getTaxComponents()) {
                    taxId = tax2.getTaxId();
                }
            } else {
                taxId = tax.getTaxId();
            }
        } else {
            taxId = tax.getTaxId();
            if (tax.getRate() != null && !tax.getRate().isBlank() && tax.getRate() != "0") {
                taxRate = tax.getRate();
            } else {
                taxRate = tax.getPercentage();
            }
        }
    }

    /**
     * Dynamically create Radio button for Serial number When user Add new equipment by scanner
     */
    private void setRadioButton(List<InvoiceItemDataModel> serialNoList) {
        rd_group_serialNo.removeAllViews();
        for (int i = 0; i < serialNoList.size(); i++) {
            RadioButton rdbtn = new RadioButton(this);
            rdbtn.setId(View.generateViewId());
            rdbtn.setText(serialNoList.get(i).getSerialNo());
            rdbtn.setTextSize(12);
            rdbtn.setTextAppearance(this, R.style.style_thrid);
            rdbtn.setTextColor(getResources().getColor(R.color.txt_sub_color));
            rdbtn.setOnClickListener(this);
            rd_group_serialNo.addView(rdbtn);
        }
    }

    private Inventry_ReS_Model CastToInv_Res_Model(String data) {
        return new Gson().fromJson(data, Inventry_ReS_Model.class);
    }

    @Override
    public void onObserveCallBack(String jobData) {
        if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
            if (jobData != null && !jobData.isEmpty()) {
                Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
                for (InvoiceItemDataModel item : job.getItemData()) {
                    if (item.getIjmmId().equalsIgnoreCase(jobData)) {
                        invoiceItemDataModelWithIjmmId.setItemId(item.getItemId());
                        invoiceItemDataModelWithIjmmId.setIjmmId(item.getIjmmId());
                        break;
                    }
                }
                convertInEquip(invoiceItemDataModelWithIjmmId);
            } else {
                Toast.makeText(this, LanguageController.getInstance().getServerMsgByKey(AppConstant.went_wrong), Toast.LENGTH_SHORT).show();
                AppUtility.progressBarDissMiss();
            }
        }
    }
}

