package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.eot_app.R;
import com.eot_app.UploadDocumentActivity;
import com.eot_app.databinding.ActivityAddJobEquipMentBinding;
import com.eot_app.nav_menu.audit.audit_list.audit_mvp.model.AuditList_Res;
import com.eot_app.nav_menu.audit.audit_list.equipment.model.EquipmentStatus;
import com.eot_app.nav_menu.audit.nav_scan.BarcodeScanActivity;
import com.eot_app.nav_menu.audit.nav_scan.UploadBarcodeViewModel;
import com.eot_app.nav_menu.client.clientlist.client_detail.site.sitelist.Site_model;
import com.eot_app.nav_menu.client.clientlist.client_detail.site.sitelist.editsite.editsitedb.SpinnerCountrySite;
import com.eot_app.nav_menu.jobs.add_job.adapters.FilterAdapterSites;
import com.eot_app.nav_menu.jobs.add_job.adapters.FilterCountry;
import com.eot_app.nav_menu.jobs.add_job.adapters.FilterStates;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.AddEditInvoiceItemActivity2;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.chat.img_crop_pkg.ImageCropFragment;
import com.eot_app.nav_menu.jobs.job_detail.documents.PathUtils;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.GenerateCodeDialog;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.JobEquipmentActivity;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.adpter_pkg.BrandAdapter;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.adpter_pkg.CateAdpter;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.adpter_pkg.EquipmentAdapter;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.adpter_pkg.GrpAdpter;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.adpter_pkg.SiteAdpter;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.adpter_pkg.SupplierAdapter;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.clientEqu.ClientEquRes;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.AddEquReq;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.BrandData;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.GetCatgData;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.GetSupplierData;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.GetgrpData;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp.AddEdit_QRCode_BarCode_Dialog;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp.AddJobEqu_Pc;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp.AddJobEqu_Pi;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp.AddJobEqu_View;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp.QRCOde_Barcode_Res_Model;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp.QR_Bar_Pc;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp.QR_Bar_Pi;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp.QR_Bar_View;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.JobEquPartRemarkRemarkActivity;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.JobEquRemarkRemarkActivity;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.Country;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.States;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.util_interfaces.MySpinnerAdapter;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.AddEditInvoiceItemActivity2.EQUIPMENTCONVERT;
import static com.eot_app.utility.AppConstant.brand;

public class AddJobEquipMentActivity extends UploadDocumentActivity implements TextWatcher, AddJobEqu_View, View.OnClickListener, RadioGroup.OnCheckedChangeListener, CheckBox.OnCheckedChangeListener, ImageCropFragment.MyDialogInterface, Spinner.OnItemSelectedListener, AddEdit_QRCode_BarCode_Dialog.QR_Bar_DataPass, QR_Bar_View, AddEquipNavigationDialog.NavigateToPage {

    private static final int BAR_CODE_REQUEST = 122;
    private final int EQUIPMENT_UPDATE_CODE = 141;
    String path = "";
    String type = "1";
    String servIntvalType = "";
    String comeFrom = "";
    String comeFrom1 = "";
    String equipmentId = "";
    int onClicked = 0;
    List<EquArrayModel> equipment_list = new ArrayList<>();
    TextView manuf_date_lable, warnty_date_lable, warnty_date_lable_start, purchase_date_lable, status_type, txt_date, txt_date_warnty, txt_date_warnty_start, txt_date_purchase, tv_label_attachment, upload_lable, click_here_txt /*hint_status_txt, status_txt*/;
    RadioButton radio_owner, radio_serv_prov;
    List<ClientEquRes> clientEquResList;
    RelativeLayout add_quote_item_layout;
    String cltId;
    ActivityAddJobEquipMentBinding binding;
    String isCnvtItemParts = "0";
    TextView tvService_inv_label;
    RadioGroup rediogrpForTag, rediogrp, rediogrp_interval;
    TextView tvLabelStep2, txt_addBarcode, txt_addQrcode, show_less, show_more, txt_specific_name, txt_basic_detail, txt_location_installed_date, txt_warranty, txt_more_details, txt_interval_notes;
    ImageView barcode_image, qrcode_image, img_barcode, img_Qrcode;
    String equipmentIdName;
    private String brandId = "", siteId = "", status = "", invId, supplierId = "";
    private CheckBox ch_equ_as_part;
    private View eq_view;
    private TextInputLayout auto_barnd_layout, equ_layout, equ_model_layout, equ_supplier_layout, equ_serial_layout, /*job_country_layout, job_state_layout,*/ /*equ_city_layout, equ_zip_layout,*/
            quote_notes_layout, catery_layout, equipment_layout, grp_layout, equ_adrs_layout, client_site_layout, custom_filed_header_1, custom_filed_header_2/*,supplier_layout*/;//equ_brand_layout
    private Button add_edit_item_Btn, clear_btn;
    private RelativeLayout image_with_tag;
    private boolean isTagSet = false;
    private RadioButton radio_before, radio_after;
    //    private View /*client_row,*/ site_row;
//    private Spinner status_Dp;
    private EditText edt_equ, edt_equ_model, edt_equ_supplier, /*edt_equ_service_interval,*/
            edt_equ_serial,/* edt_equ_city, edt_equ_zip,*/
            quote_notes_edt;//edt_equ_brand
    private AutoCompleteTextView /*auto_country*//*,supplier_txt*//*, auto_states,*/ edt_equ_adrs, auto_equipment, auto_catery, auto_grp, auto_brand, auto_client_site;
    private String ctry, state;
    //    private EditText auto_client;
    private AppCompatImageView img_attachment;
    private TextView image_txt, remove_txt;
    private AddJobEqu_Pi addJobEqu_pi;
    private CheckBox checkbox_barCode, checkbox_scan_insert;
    private String /*isBarcodeGenerate = "0",*/ isPart = "0";
    private String egId = "";
    private String ecId = "";
    private String jobId = "";
    private LinearLayout manuf_date_layout, date_purchase_layout, date_warnty_layout, date_warnty_layout_start/*, linearLayout_status*/;
    private LinearLayout lay, ll_show_more_less;
    private Job job;
    private InvoiceItemDataModel updateItemDataModel;
    AddEdit_QRCode_BarCode_Dialog addEditQrCodeBarCodeDialog;
    String barcodeString = "";
    String qrCodeString = "";
    Calendar manufCalendar = Calendar.getInstance();
    Calendar purchesCalendar = Calendar.getInstance();
    Calendar warntyCalendar = Calendar.getInstance();
    Calendar startWarntyCalendar = Calendar.getInstance();
    Calendar installCalendar = Calendar.getInstance();
    String countryNameById = "";
    String statenameById = "";
    String city = "";
    String zip = "";
    boolean isSerialNoSelected = false;
    EditText edt_day, edt_month, edt_year;
    RadioButton rdBtn_day, rdBtn_month, rdBtn_year;
    String interval = "", generateOption = "", scanCode = "";
    QR_Bar_Pi qrBarPi;
    EquArrayModel equipmentRes, equipment;
    ;

    /**
     * select date from picker & concanate current time
     */
    private final DatePickerDialog.OnDateSetListener datePickerListener = (view, selectedYear, selectedMonth, selectedDay) -> {
        String dateselect;
        try {
            DateFormat formatter = new SimpleDateFormat(AppConstant.DATE_FORMAT, Locale.US);//hh:mm:ss a
//            Date startDate = formatter.parse(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(selectedYear, selectedMonth, selectedDay);
            Date startDate = cal.getTime();
            assert startDate != null;
            dateselect = formatter.format(startDate);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 5, 0, -20);

            if (view.getTag().equals("Manufacture")) {
                Log.e("", "");
                manufCalendar.set(Calendar.YEAR, selectedYear);
                manufCalendar.set(Calendar.MONTH, selectedMonth);
                manufCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                setManufactureViews(dateselect, params);
            } else if (view.getTag().equals("Warranty")) {
                Log.e("", "");
                warntyCalendar.set(Calendar.YEAR, selectedYear);
                warntyCalendar.set(Calendar.MONTH, selectedMonth);
                warntyCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                setWarrntyViews(dateselect, params);
            } else if (view.getTag().equals("Warranty_start")) {
                Log.e("", "");
                startWarntyCalendar.set(Calendar.YEAR, selectedYear);
                startWarntyCalendar.set(Calendar.MONTH, selectedMonth);
                startWarntyCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                setWarrntyStartViews(dateselect);
            } else if (view.getTag().equals("Purchase")) {
                Log.e("", "");
                purchesCalendar.set(Calendar.YEAR, selectedYear);
                purchesCalendar.set(Calendar.MONTH, selectedMonth);
                purchesCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                setPurchaseViews(dateselect, params);
            } else if (view.getTag().equals("installed")) {
                Log.e("", "");
                installCalendar.set(Calendar.YEAR, selectedYear);
                installCalendar.set(Calendar.MONTH, selectedMonth);
                installCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                setInstalledViews(dateselect, params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    private List<GetCatgData> GetCatgDataList;
    private List<GetgrpData> GetgrpDataList;
    private List<BrandData> BrandDataList;
    private List<Site_model> clientSiteList = new ArrayList<>();
    private List<EquipmentStatus> equipmentStatusList;
    private List<GetSupplierData> supplierDataList;
    //    private TextInputLayout client_layout;
    private EditText custom_filed_txt_1, custom_filed_txt_2;
    /**
     * add equipment from audit list
     */
    private AuditList_Res audit;

    private void setPurchaseViews(String dateselect, LinearLayout.LayoutParams params) {
        purchase_date_lable.setText(dateselect);
        txt_date_purchase.setLayoutParams(params);
        txt_date_purchase.setVisibility(View.VISIBLE);
        txt_date_purchase.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.purchase_date));
        binding.purchaseDateCancel.setVisibility(View.VISIBLE);
    }

    private void setInstalledViews(String dateselect, LinearLayout.LayoutParams params) {
        binding.installedDateLable.setText(dateselect);
        binding.txtDateInstalled.setLayoutParams(params);
        binding.txtDateInstalled.setVisibility(View.VISIBLE);
        binding.txtDateInstalled.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.installed_date));
        binding.installedDateCancel.setVisibility(View.VISIBLE);
    }

    private void setWarrntyViews(String dateselect, LinearLayout.LayoutParams params) {
        warnty_date_lable.setText(dateselect);
        txt_date_warnty.setLayoutParams(params);
        txt_date_warnty.setVisibility(View.VISIBLE);
        txt_date_warnty.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.warranty_expiry_date));
        binding.warntyDateCancel.setVisibility(View.VISIBLE);
    }

    private void setWarrntyStartViews(String dateselect) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 5, 0, -20);

        warnty_date_lable_start.setText(dateselect);
        // to set installed date as current date
        setInstalledViews(dateselect, params);

        txt_date_warnty_start.setLayoutParams(params);
        txt_date_warnty_start.setVisibility(View.VISIBLE);
        txt_date_warnty_start.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.warranty_start_date));
        binding.warntyStartDateCancel.setVisibility(View.VISIBLE);
        if (updateItemDataModel != null && updateItemDataModel.getWarrantyType() != null && updateItemDataModel.getWarrantyValue() != null && !updateItemDataModel.getWarrantyValue().isEmpty() && !updateItemDataModel.getWarrantyType().isEmpty()) {
            String date = AppUtility.addDaysInDate(dateselect, Integer.parseInt(updateItemDataModel.getWarrantyValue()), updateItemDataModel.getWarrantyType(), AppConstant.DATE_FORMAT);
            setWarrntyViews(date, params);
        }
    }

    private void setManufactureViews(String dateselect, LinearLayout.LayoutParams params) {
        manuf_date_lable.setText(dateselect);
        txt_date.setLayoutParams(params);
        txt_date.setVisibility(View.VISIBLE);
        txt_date.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.manufacture_date));
        binding.manufDateCancel.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_job_equip_ment);
        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_add_equipment));
        initializeViewS();


       /* supplier_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s!=null&&s.length()>0){
                    addJobEqu_pi.getSupplierList(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    supplier_layout.setHintEnabled(true);
                } else if (s.length() <= 0) {
                    supplier_layout.setHintEnabled(false);
                }
            }
        });*/


    }


    private void initializeViewS() {
        custom_filed_header_1 = findViewById(R.id.custom_filed_header_1);
        custom_filed_header_2 = findViewById(R.id.custom_filed_header_2);
        custom_filed_txt_1 = findViewById(R.id.custom_filed_txt_1);
        custom_filed_txt_2 = findViewById(R.id.custom_filed_txt_2);
        if (App_preference.getSharedprefInstance().getCompanySettingsDetails().getEqupExtraField1Label() != null)
            custom_filed_txt_1.setHint(App_preference.getSharedprefInstance().getCompanySettingsDetails().getEqupExtraField1Label());

        if (App_preference.getSharedprefInstance().getCompanySettingsDetails().getEqupExtraField2Label() != null)
            custom_filed_txt_2.setHint(App_preference.getSharedprefInstance().getCompanySettingsDetails().getEqupExtraField2Label());


//        site_row = findViewById(R.id.site_row);
//        client_row = findViewById(R.id.client_row);
//        client_layout = findViewById(R.id.client_layout);
//        auto_client = findViewById(R.id.auto_client);
//        auto_client.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.client_mand) + " *");

        add_quote_item_layout = findViewById(R.id.add_quote_item_layout);
        AppUtility.setupUI(add_quote_item_layout, AddJobEquipMentActivity.this);

        eq_view = findViewById(R.id.eq_view);
        equ_layout = findViewById(R.id.equ_layout);
        equ_model_layout = findViewById(R.id.equ_model_layout);

        equ_supplier_layout = findViewById(R.id.equ_supplier_layout);
        // equ_brand_layout = findViewById(R.id.equ_brand_layout);
        equ_serial_layout = findViewById(R.id.equ_serial_layout);
      /*  job_country_layout = findViewById(R.id.job_country_layout);
        job_state_layout = findViewById(R.id.job_state_layout);*/


        // to show the note on the screen
        tvLabelStep2 = findViewById(R.id.tvLabelStep2);
        tvLabelStep2.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.step_2_note));


      /*  auto_country = findViewById(R.id.auto_country);
        auto_country.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.country));

        auto_states = findViewById(R.id.auto_states);
        auto_states.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.state));*/

      /*  equ_city_layout = findViewById(R.id.equ_city_layout);
        equ_zip_layout = findViewById(R.id.equ_zip_layout);*/
        quote_notes_layout = findViewById(R.id.quote_notes_layout);
        equ_adrs_layout = findViewById(R.id.equ_adrs_layout);

        edt_equ = findViewById(R.id.edt_equ);
        edt_equ.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment) + " *");

        edt_equ_model = findViewById(R.id.edt_equ_model);
        edt_equ_model.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.model_no));

        edt_equ_supplier = findViewById(R.id.edt_equ_supplier);
        edt_equ_supplier.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.supplier));

//        edt_equ_service_interval = findViewById(R.id.edt_equ_service_interval);

        //  edt_equ_brand = findViewById(R.id.edt_equ_brand);
        edt_equ_serial = findViewById(R.id.edt_equ_serial);
        edt_equ_serial.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.serial_no));

        edt_equ_adrs = findViewById(R.id.edt_equ_adrs);
        edt_equ_adrs.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.address));

       /* edt_equ_city = findViewById(R.id.edt_equ_city);
        edt_equ_city.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.city));

        edt_equ_zip = findViewById(R.id.edt_equ_zip);
        edt_equ_zip.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.zip));*/

        quote_notes_edt = findViewById(R.id.quote_notes_edt);
        quote_notes_edt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.notes));

        add_edit_item_Btn = findViewById(R.id.add_edit_item_Btn);
        add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_add_equipment));

        manuf_date_lable = findViewById(R.id.manuf_date_lable);
        manuf_date_lable.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.manufacture_date));

        warnty_date_lable = findViewById(R.id.warnty_date_lable);
        warnty_date_lable.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.warranty_expiry_date));

        warnty_date_lable_start = findViewById(R.id.warnty_date_lable_start);
        warnty_date_lable_start.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.warranty_start_date));

        purchase_date_lable = findViewById(R.id.purchase_date_lable);
        purchase_date_lable.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.purchase_date));


        binding.installedDateLable.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.installed_date));

        catery_layout = findViewById(R.id.catery_layout);
        equipment_layout = findViewById(R.id.equipment_layout);

        auto_catery = findViewById(R.id.auto_catery);
        auto_catery.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_category));

        auto_equipment = findViewById(R.id.auto_equipment);
        auto_equipment.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.parent_equipment));

        grp_layout = findViewById(R.id.grp_layout);
        auto_grp = findViewById(R.id.auto_grp);
        auto_grp.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_group));

        auto_barnd_layout = findViewById(R.id.auto_barnd_layout);

        auto_brand = findViewById(R.id.auto_brand);
        auto_brand.setHint(LanguageController.getInstance().getMobileMsgByKey(brand));


        radio_owner = findViewById(R.id.radio_owner);
        radio_owner.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.my_equipment));
        radio_owner.setOnCheckedChangeListener(this);


        radio_serv_prov = findViewById(R.id.radio_serv_prov);
        radio_serv_prov.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.clients_eq));
        radio_serv_prov.setOnCheckedChangeListener(this);

        tvService_inv_label = findViewById(R.id.tvService_inv_label);
        tvService_inv_label.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.service_interval_type));

       /* checkbox_barCode = findViewById(R.id.checkbox_barCode);
        checkbox_barCode.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.auto_gen_bar_code));

        checkbox_scan_insert = findViewById(R.id.checkbox_scan_barCode);
        checkbox_scan_insert.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.scan_insert_barcode));

        binding.etBarcode.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.insert_barcode));
        binding.tvScanBarcode.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.scan));
*/
        status_type = findViewById(R.id.type);
        status_type.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.type));

        txt_date = findViewById(R.id.txt_date);
        txt_date_warnty = findViewById(R.id.txt_date_warnty);
        txt_date_warnty_start = findViewById(R.id.txt_date_warnty_start);


        txt_date_purchase = findViewById(R.id.txt_date_purchase);

        manuf_date_layout = findViewById(R.id.manuf_date_layout);
        date_purchase_layout = findViewById(R.id.date_purchase_layout);
        date_warnty_layout = findViewById(R.id.date_warnty_layout);
        date_warnty_layout_start = findViewById(R.id.date_warnty_layout_start);

        tv_label_attachment = findViewById(R.id.tv_label_attachment);
        tv_label_attachment.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_images));

        lay = findViewById(R.id.lay);

        upload_lable = findViewById(R.id.upload_lable);
        upload_lable.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.expense_upload));

        click_here_txt = findViewById(R.id.click_here_txt);
        click_here_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.doc_here));

        img_attachment = findViewById(R.id.img_attachment);
        ch_equ_as_part = findViewById(R.id.ch_equ_as_part);
        ch_equ_as_part.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equ_as_a_part));

        client_site_layout = findViewById(R.id.client_site_layout);

        auto_client_site = findViewById(R.id.auto_client_site);
        auto_client_site.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.address));

//         equ_adrs_layout.setClickable(false);
        edt_equ_adrs.setClickable(false);
        edt_equ_adrs.setFocusable(false);
        auto_client_site.setFocusable(false);
        auto_client_site.setClickable(false);
//        supplier_layout=findViewById(R.id.Supllier_layout);

//        status_Dp = findViewById(R.id.status_Dp);

//        hint_status_txt = findViewById(R.id.hint_status_txt);
//        hint_status_txt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.status_radio_btn));


//        status_txt = findViewById(R.id.status_txt);
//        status_txt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.status_radio_btn));

//        supplier_txt = findViewById(R.id.supplier_txt);
//        supplier_txt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.supplier));

//        linearLayout_status = findViewById(R.id.linearLayout_status);

        image_with_tag = findViewById(R.id.image_with_tag);
        image_txt = findViewById(R.id.image_txt);

        remove_txt = findViewById(R.id.remove_txt);
        remove_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.remove_the_image_tag));
        remove_txt.setOnClickListener(this);
        binding.dateInstalledLayout.setOnClickListener(this);

        rediogrp = findViewById(R.id.rediogrp);
        rediogrp.setOnCheckedChangeListener(this);

        rediogrp_interval = findViewById(R.id.rediogrp_interval);
        rediogrp_interval.setOnCheckedChangeListener(this);

        /**Add barcode and QRcode**/
        txt_addBarcode = findViewById(R.id.txt_addBarcode);
        img_barcode = findViewById(R.id.img_barcode);
        txt_addBarcode.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_barcode));
        txt_addQrcode = findViewById(R.id.txt_addQrcode);
        img_Qrcode = findViewById(R.id.img_Qrcode);
        txt_addQrcode.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_QR_Code));

        binding.manufDateCancel.setOnClickListener(this);
        binding.warntyDateCancel.setOnClickListener(this);
        binding.purchaseDateCancel.setOnClickListener(this);
        binding.installedDateCancel.setOnClickListener(this);
        binding.warntyStartDateCancel.setOnClickListener(this);
        txt_addBarcode.setOnClickListener(this);
        txt_addQrcode.setOnClickListener(this);

        /**Add new text filed for new ui changes**/
        show_less = findViewById(R.id.show_less);
        show_more = findViewById(R.id.show_more);
        txt_specific_name = findViewById(R.id.txt_specific_name);
        txt_basic_detail = findViewById(R.id.txt_basic_detail);
        txt_location_installed_date = findViewById(R.id.txt_location_installed_date);
        txt_warranty = findViewById(R.id.txt_warranty);
        txt_more_details = findViewById(R.id.txt_more_details);
        ll_show_more_less = findViewById(R.id.ll_show_more_less);

        show_less.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.show_less_details));
        show_more.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.show_more_details));
        txt_specific_name.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_assign_specific_name));
        txt_basic_detail.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.basic_equ_details));
        txt_location_installed_date.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.loc_and_instal_date));
        txt_warranty.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.warranty));
        txt_more_details.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.more_details));
        show_less.setOnClickListener(this);
        show_more.setOnClickListener(this);

        radio_before = findViewById(R.id.radio_before);
        radio_before.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.before));
        radio_before.setChecked(false);

        radio_after = findViewById(R.id.radio_after);
        radio_after.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.after));
        radio_after.setChecked(false);

        rediogrpForTag = findViewById(R.id.rediogrpForTag);
        rediogrpForTag.setOnCheckedChangeListener(this);

        edt_day = findViewById(R.id.edt_day);
        edt_month = findViewById(R.id.edt_month);
        edt_year = findViewById(R.id.edt_year);

        rdBtn_day = findViewById(R.id.radio_day);
        rdBtn_month = findViewById(R.id.radio_month);
        rdBtn_year = findViewById(R.id.radio_year);
        clear_btn = findViewById(R.id.clear_btn);
        txt_interval_notes = findViewById(R.id.txt_interval_notes);
        txt_interval_notes.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.interval_notes));
        clear_btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.clear));
        clear_btn.setOnClickListener(this);
        apicalling();

        /*get Item Data For Item to equipment convert*****/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (getIntent().hasExtra("InvoiceItemDataModel")) {
                setDataIntoViews(bundle);
                //updateItemDataModel = bundle.getParcelable("InvoiceItemDataModel");
                String string = bundle.getString("objectStr");

                // for adding parts with item set data in the views
//                equipmentIdName = bundle.getString("equipmentIdName");
                isSerialNoSelected = bundle.getBoolean("isSerialNoSelected", false);
                updateItemDataModel = new Gson().fromJson(string, InvoiceItemDataModel.class);
                setItemDefaultData();
                if (equipmentId != null && !equipmentId.isEmpty()) {
                    auto_equipment.setEnabled(false);
                    auto_equipment.setText(equipmentIdName);
                    equipment_layout.setHintEnabled(true);
                }
                // for adding parts with item set data in the views
                if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
                    setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.step_2) + " (" + LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_add_equipment) + ")");
                    tvLabelStep2.setVisibility(View.VISIBLE);

                    if (getIntent().hasExtra("equipment")) {
                        String strEquipment = getIntent().getExtras().getString("equipment");
                        equipment = new Gson().fromJson(strEquipment, EquArrayModel.class);
                    }
//                    if (isSerialNoSelected) {
//                        ch_equ_as_part.setEnabled(true);
//                        add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_add_equipment));
//                        generateOption = bundle.getString("generateOption");
//                        scanCode = bundle.getString("scanCode");
//                        ch_equ_as_part.setChecked(false);
//                        isPart = "0";
//                    } else {
                    //Add Equipment by new item added.
                    if (comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan")) {
                        add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_add_equipment));
                        ch_equ_as_part.setEnabled(true);
                        ch_equ_as_part.setChecked(false);
                        generateOption = bundle.getString("generateOption");
                        scanCode = bundle.getString("scanCode");
                        isPart = "0";
                    } else if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace") && equipment != null) {
                        add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_replace_equipment));
                        if (equipment.getIsPart() != null && equipment.getIsPart().equalsIgnoreCase("1")) {
                            ch_equ_as_part.setEnabled(false);
                            ch_equ_as_part.setChecked(true);
                            isPart = "1";
                        } else {
                            ch_equ_as_part.setVisibility(View.GONE);
                            isPart = "0";
                        }
                    } else {
                        ch_equ_as_part.setEnabled(false);
                        add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_add_equipment_part));
                        ch_equ_as_part.setChecked(true);
                        isPart = "1";
                    }
//                    }
//                    equipment_layout.setVisibility(View.VISIBLE);
//                    eq_view.setVisibility(View.VISIBLE);

                }

                // for checking if the item is
                if (updateItemDataModel.getIsGrouped() != null && updateItemDataModel.getIsGrouped().equalsIgnoreCase("1") && isPart.equalsIgnoreCase("0")) {
                    AppUtility.alertDialog(AddJobEquipMentActivity.this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.are_you_sure), LanguageController.getInstance().getMobileMsgByKey(AppConstant.do_you_want_t), LanguageController.getInstance().getMobileMsgByKey(AppConstant.yes), LanguageController.getInstance().getMobileMsgByKey(AppConstant.no), () -> {
                        isCnvtItemParts = "1";
                        return true;
                    });
                } else {
                    isCnvtItemParts = "0";
                }
            }
            // for adding parts without item set data in the views
            if (getIntent().hasExtra("comeFrom1") && bundle.getString("comeFrom1").equalsIgnoreCase("AddPartWithoutItem")) {
                setDataIntoViews(bundle);
                isSerialNoSelected = bundle.getBoolean("isSerialNoSelected", false);
                cltId = bundle.getString("cltId");
//                equipmentIdName = bundle.getString("equipmentIdName");
                job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
                addJobEqu_pi.getClientSiteListServer(cltId);
                siteId = job.getSiteId();
                setJobData(job);
                if (equipmentId != null && !equipmentId.isEmpty()) {
                    auto_equipment.setEnabled(false);
                    auto_equipment.setText(equipmentIdName);
                    equipment_layout.setHintEnabled(true);
                }
                tvLabelStep2.setVisibility(View.GONE);
                ch_equ_as_part.setEnabled(false);
                if (isSerialNoSelected) {
                    ch_equ_as_part.setChecked(false);
                    isPart = "0";
                } else {
                    ch_equ_as_part.setChecked(true);
                    isPart = "1";
                }

                // for adding parts with item set data in the views
                if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark") || comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
                    setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.step_2) + " (" + LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_add_equipment) + ")");

                    if (getIntent().hasExtra("equipment")) {
                        String strEquipment = getIntent().getExtras().getString("equipment");
                        equipment = new Gson().fromJson(strEquipment, EquArrayModel.class);
                    }

                    if (comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan")) {
                        add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_add_equipment));
                        ch_equ_as_part.setEnabled(true);
                        ch_equ_as_part.setChecked(false);
                        generateOption = bundle.getString("generateOption");
                        scanCode = bundle.getString("scanCode");
                        isPart = "0";
                    } else if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace") && equipment != null) {
                        add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_replace_equipment));
                        if (equipment.getIsPart() != null && equipment.getIsPart().equalsIgnoreCase("1")) {
                            ch_equ_as_part.setEnabled(false);
                            ch_equ_as_part.setChecked(true);
                            isPart = "1";
                        } else {
                            ch_equ_as_part.setVisibility(View.GONE);
                            isPart = "0";
                        }
                    } else {
                        ch_equ_as_part.setEnabled(false);
                        add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_add_equipment_part));
                        ch_equ_as_part.setChecked(true);
                        isPart = "1";
                    }

                }

            } else if (getIntent().hasExtra("auditId")) {
                cltId = getIntent().getStringExtra("cltId");
                audit = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).auditDao().getAuditsEquipmentList(getIntent().getStringExtra("auditId"));
                if (audit != null) {
                    siteId = audit.getSiteId();
                    if (audit.getCltId().equals("0")) {
                        setCompanySettingAdrs();
                    } else {
                        addJobEqu_pi.getClientSiteListServer(cltId);
                        setAuditdata(audit);
                    }
                }
            } else {
                /*add new Equipment***/
                if (getIntent().hasExtra("JobId")) {
                    jobId = getIntent().getStringExtra("JobId");
                    cltId = getIntent().getStringExtra("cltId");
                    job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
                    addJobEqu_pi.getClientSiteListServer(cltId);
                    siteId = job.getSiteId();
                    setJobData(job);
                }
            }
            job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
        }


        // To set the current date as warranty start date
        setWarrntyStartViews(AppUtility.getCurrentDateByFormat(AppConstant.DATE_FORMAT));


    }

    @Override
    protected void onResume() {
        super.onResume();
        //For Generating Scan Code
        if (comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") && isSerialNoSelected) {
            qrBarPi = new QR_Bar_Pc(this);
            if (generateOption.equalsIgnoreCase(LanguageController.getInstance().getMobileMsgByKey(AppConstant.barcode))) {
                qrBarPi.getBarCode(scanCode);
            } else if (generateOption.equalsIgnoreCase(LanguageController.getInstance().getMobileMsgByKey(AppConstant.qrcode))) {
                qrBarPi.getQRCode(scanCode);
            }
        }
        //get equipment list
        addJobEqu_pi.getEquipmentList(jobId);

        setTextViews();
    }

    private void apicalling() {
        addJobEqu_pi = new AddJobEqu_Pc(this);
        addJobEqu_pi.getCountryList();
        List<BrandData> brandLists = App_preference.getSharedprefInstance().getBrandList();
        if (brandLists != null) {
            setBrandList(brandLists);
        } else {
            addJobEqu_pi.getBrandList();
        }
        List<GetgrpData> getgrplists = App_preference.getSharedprefInstance().getgrplist();
        if (getgrplists != null) {
            setGrpList(getgrplists);
        } else {
            addJobEqu_pi.getGrpList();
        }
        List<GetCatgData> getcatglists = App_preference.getSharedprefInstance().getcatglist();
        if (getcatglists != null) {
            setCategList(getcatglists);
        } else {
            addJobEqu_pi.getCageryList();
        }
        List<EquipmentStatus> geteqstatuslists = App_preference.getSharedprefInstance().geteqstatuslist();
        if (geteqstatuslists != null) {
            setEquStatusList(geteqstatuslists);
        } else {
            addJobEqu_pi.getEquStatusList();
        }
        //  addJobEqu_pi.getSupplierList("");
    }

    private void setDataIntoViews(Bundle bundle) {
        jobId = bundle.getString("edit_jobId");
        invId = bundle.getString("invId");
        comeFrom = bundle.getString("comeFrom");
        comeFrom1 = bundle.getString("comeFrom1");
        equipmentId = bundle.getString("equipmentId");
        equipmentIdName = bundle.getString("equipmentIdName");
        if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace") || comeFrom != null && comeFrom.equalsIgnoreCase("AddPartWithoutItem") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark")) {
            if (getIntent().hasExtra("equipment")) {
                String strEquipment = getIntent().getExtras().getString("equipment");
                equipment = new Gson().fromJson(strEquipment, EquArrayModel.class);
            }
            equipmentId = equipment.getEquId();
            if (equipment.getIsPart() != null && equipment.getIsPart().equalsIgnoreCase("1") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark")) {
                if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark")) {
                    equipmentIdName = equipment.getEqunm();
                } else {
                    equipmentIdName = equipment.getParentName();
                }
                equipment_layout.setVisibility(View.VISIBLE);
                eq_view.setVisibility(View.VISIBLE);
            }
            if (equipment.getType() != null && !equipment.getType().isEmpty()) {
                type = equipment.getType();
                try {
                    if (type.equalsIgnoreCase("1")) {
                        radio_owner.setChecked(true);
                        radio_serv_prov.setChecked(false);
                    } else {
                        radio_serv_prov.setChecked(true);
                        radio_owner.setChecked(false);
                    }
                    radio_owner.setEnabled(false);
                    radio_serv_prov.setEnabled(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        // for setting the equipment type as the main equipment type and non editable
        if (bundle.getString("equipmentType") != null && !bundle.getString("equipmentType").isEmpty()) {
            type = bundle.getString("equipmentType");
            if (type.equalsIgnoreCase("1")) radio_owner.setChecked(true);
            else if (type.equalsIgnoreCase("2")) radio_serv_prov.setChecked(true);

            radio_owner.setEnabled(false);
            radio_serv_prov.setEnabled(false);
        }

    }

    private void setJobData(Job job) {
        try {
            if (job != null) {
                String newLocation = "";
//                auto_client.setText(job.getNm());
              /*  auto_country.setText(SpinnerCountrySite.getCountryNameById(job.getCtry()));
                auto_states.setText(SpinnerCountrySite.getStatenameById(job.getCtry(), job.getState()));*/
//                edt_equ_city.setText(job.getCity());
                if (job.getAdr() != null && !job.getAdr().isEmpty()) {
                    newLocation = newLocation + "" + job.getAdr();
                }
                if (job.getCity() != null && !job.getCity().isEmpty()) {
                    city = job.getCity();
                    newLocation = newLocation + ", " + city;
                }
                if (job.getState() != null && !job.getState().isEmpty() && !job.getCtry().isEmpty() && !job.getState().equals("0")) {
                    statenameById = SpinnerCountrySite.getStatenameById((job.getCtry()), job.getState());
                    newLocation = newLocation + ", " + statenameById;
                }
                if (job.getCtry() != null && !job.getCtry().isEmpty() && !job.getCtry().equals("0")) {
                    countryNameById = SpinnerCountrySite.getCountryNameById(job.getCtry());
                    newLocation = newLocation + ", " + countryNameById;
                }
                if (job.getCity() != null && !job.getCity().isEmpty()) {
                    zip = job.getZip();
                    newLocation = newLocation + ", " + zip;
                }
                edt_equ_adrs.setText(job.getAdr());
//                edt_equ_zip.setText(job.getZip());
                auto_client_site.setText(Html.fromHtml("<font color='#4C000000'>" + job.getSnm() + "</font>" + "<br>" + newLocation));
                client_site_layout.setHintEnabled(true);
//                client_layout.setHintEnabled(true);
//                job_country_layout.setHintEnabled(true);
//                client_layout.setHintEnabled(true);
//                job_state_layout.setHintEnabled(true);
//                equ_adrs_layout.setHintEnabled(true);
//                equ_city_layout.setHintEnabled(true);
//                equ_zip_layout.setHintEnabled(true);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void setAuditdata(AuditList_Res audit) {
        if (audit != null) {
            String newLocation = "";

//            auto_client.setText(audit.getNm());
         /*   auto_country.setText(SpinnerCountrySite.getCountryNameById(audit.getCtry()));
            auto_states.setText(SpinnerCountrySite.getStatenameById(audit.getCtry(), audit.getState()));*/
//            edt_equ_city.setText(audit.getCity());
            if (audit.getAdr() != null && !audit.getAdr().isEmpty()) {
                newLocation = newLocation + "" + audit.getAdr();
            }
            if (audit.getCity() != null && !audit.getCity().isEmpty()) {
                city = audit.getCity();
                newLocation = newLocation + ", " + city;
            }
            if (audit.getState() != null && !audit.getState().isEmpty() && !audit.getCtry().isEmpty() && !audit.getState().equals("0")) {
                statenameById = SpinnerCountrySite.getStatenameById((audit.getCtry()), audit.getState());
                newLocation = newLocation + ", " + statenameById;
            }
            if (audit.getCtry() != null && !audit.getCtry().isEmpty() && !audit.getCtry().equals("0")) {
                countryNameById = SpinnerCountrySite.getCountryNameById(audit.getCtry());
                newLocation = newLocation + ", " + countryNameById;
            }
            if (audit.getCity() != null && !audit.getCity().isEmpty()) {
                zip = audit.getZip();
                newLocation = newLocation + ", " + zip;
            }
            edt_equ_adrs.setText(audit.getAdr());
//            edt_equ_zip.setText(audit.getZip());
            auto_client_site.setText(Html.fromHtml("<font color='#4C000000'>" + job.getSnm() + "</font>" + "<br>" + newLocation));
            client_site_layout.setHintEnabled(true);
//            client_layout.setHintEnabled(true);
//            job_country_layout.setHintEnabled(true);
//            client_layout.setHintEnabled(true);
//            job_state_layout.setHintEnabled(true);
//            equ_adrs_layout.setHintEnabled(true);
//            equ_city_layout.setHintEnabled(true);
//            equ_zip_layout.setHintEnabled(true);
        }
    }

    private void setItemDefaultData() {
        try {
            if (updateItemDataModel != null) {
                edt_equ.setText(updateItemDataModel.getInm());

                edt_equ_model.setText(updateItemDataModel.getPno());
                edt_equ_supplier.setText(updateItemDataModel.getSupplierCost());


                edt_equ_serial.setText(updateItemDataModel.getSerialNo());
                quote_notes_edt.setText(updateItemDataModel.getDes());
                quote_notes_layout.setHintEnabled(true);

                if (jobId != null) {
                    job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
                    if (job != null) {
                        if (job.getCltId() != null)
                            addJobEqu_pi.getClientSiteListServer(job.getCltId());
                        if (job.getSiteId() != null) siteId = job.getSiteId();
                        setJobData(job);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void setTextViews() {

        // edt_equ_brand.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand));
        textChangeListner();
        emptyBrandCheck();
        emptyGroupCheck();
        emptyCatryCheck();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void emptyGroupCheck() {
        auto_grp.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            auto_grp.requestFocus();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (GetgrpDataList != null && GetgrpDataList.size() > 0) {
                    if (event.getRawX() >= (auto_grp.getRight() - auto_grp.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (onClicked == 0) {
                            hideKeybaord(auto_grp);
                            auto_grp.showDropDown();
                            onClicked = 1;
                            return true;
                        } else {
                            hideKeybaord(auto_grp);
                            auto_grp.dismissDropDown();
                            onClicked = 0;
                            return true;
                        }

                    } else {
                        auto_grp.showDropDown();
                        showSoftKeyboard(auto_grp);
                        return true;
                    }
                } else {
                    auto_grp.setInputType(InputType.TYPE_NULL);
                    AppUtility.alertDialog(AddJobEquipMentActivity.this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_empty_equipment_Group), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
                }
            }

            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void emptyCatryCheck() {
        auto_catery.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            auto_catery.requestFocus();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (GetCatgDataList != null && GetCatgDataList.size() > 0) {
                    if (event.getRawX() >= (auto_catery.getRight() - auto_catery.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (onClicked == 0) {
                            hideKeybaord(auto_catery);
                            auto_catery.showDropDown();
                            onClicked = 1;
                            return true;
                        } else {
                            hideKeybaord(auto_catery);
                            auto_catery.dismissDropDown();
                            onClicked = 0;
                            return true;
                        }
                    } else {
                        auto_catery.showDropDown();
                        showSoftKeyboard(auto_catery);
                        return true;
                    }
                } else {
                    auto_catery.setInputType(InputType.TYPE_NULL);
                    AppUtility.alertDialog(AddJobEquipMentActivity.this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_empty_equipment_Category), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
                }
            }

            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void emptyBrandCheck() {
        auto_brand.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            auto_brand.requestFocus();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (BrandDataList != null && BrandDataList.size() > 0) {
                    if (event.getRawX() >= (auto_brand.getRight() - auto_brand.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (onClicked == 0) {
                            auto_brand.showDropDown();
                            onClicked = 1;
                            return true;
                        } else {
                            hideKeybaord(auto_brand);
                            auto_brand.dismissDropDown();
                            onClicked = 0;
                            return true;
                        }
                    } else {
                        auto_brand.showDropDown();
                        showSoftKeyboard(auto_brand);
                        return true;
                    }

                } else {
                    auto_brand.setInputType(InputType.TYPE_NULL);
                    AppUtility.alertDialog(AddJobEquipMentActivity.this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_empty_equipment_Brand), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
                }
            }

            return false;
        });
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    public void showSoftKeyboard(View view) {
        if (view.isFocused()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void textChangeListner() {
        Objects.requireNonNull(equ_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(equ_model_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(equ_supplier_layout.getEditText()).addTextChangedListener(this);
        //  equ_brand_layout.getEditText().addTextChangedListener(this);
        Objects.requireNonNull(equ_serial_layout.getEditText()).addTextChangedListener(this);
      /*  Objects.requireNonNull(equ_city_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(equ_zip_layout.getEditText()).addTextChangedListener(this);*/
        Objects.requireNonNull(quote_notes_layout.getEditText()).addTextChangedListener(this);
//        Objects.requireNonNull(equ_adrs_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(custom_filed_header_1.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(custom_filed_header_2.getEditText()).addTextChangedListener(this);

        catery_layout.setOnClickListener(this);
        equipment_layout.setOnClickListener(this);
        grp_layout.setOnClickListener(this);

        auto_equipment.setOnClickListener(this);
        auto_catery.setOnClickListener(this);
        auto_grp.setOnClickListener(this);
        auto_brand.setOnClickListener(this);

        manuf_date_layout.setOnClickListener(this);
        date_purchase_layout.setOnClickListener(this);
        date_warnty_layout.setOnClickListener(this);
        date_warnty_layout_start.setOnClickListener(this);

        add_edit_item_Btn.setOnClickListener(this);
        /* binding.tvScanBarcode.setOnClickListener(this);
         */
        rediogrp.setOnCheckedChangeListener(this);
//        checkbox_barCode.setOnCheckedChangeListener(this);
//        checkbox_scan_insert.setOnCheckedChangeListener(this);
        ch_equ_as_part.setOnCheckedChangeListener(this);
//        supplier_layout.setOnClickListener(this);
//        upload_lable.setOnClickListener(this);
        lay.setOnClickListener(this);
        client_site_layout.setOnClickListener(this);

        auto_client_site.setOnClickListener(this);

//        status_Dp.setOnItemSelectedListener(this);
//        linearLayout_status.setOnClickListener(this);
    }

    private void seteqstatuslists(List<EquipmentStatus> equipmentStatuses) {
        if (equipmentStatuses != null && equipmentStatuses.size() > 0) {
//            AppUtility.spinnerPopUpWindow(status_Dp);
            String[] statusList = new String[equipmentStatuses.size()];
            int i = 0;
            for (EquipmentStatus status : equipmentStatuses) {
                statusList[i] = status.getStatusText();
                i++;
                if (status.getStatusText().equals("Deployed")) {
                    /*set Default Equipment Status DEPLOYED****/
                    setDefaultEquStatus(status);
                }
            }

//            status_Dp.setAdapter(new MySpinnerAdapter(this, statusList));
        }
    }


    @Override
    public void setEquStatusList(final List<EquipmentStatus> equipmentStatusLists) {
        if (equipmentStatusList == null) {
            this.equipmentStatusList = equipmentStatusLists;
            seteqstatuslists(equipmentStatusLists);
        }
        try {
            String s = new Gson().toJson(equipmentStatusLists);
            App_preference.getSharedprefInstance().seteqstatuslist(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setDefaultEquStatus(EquipmentStatus statusModel) {
//        hint_status_txt.setVisibility(View.VISIBLE);
        status = statusModel.getEsId();
        String selectedValue = statusModel.getStatusText();
//        status_txt.setText(selectedValue);
    }

    private void setEquStatus(int pos) {
//        hint_status_txt.setVisibility(View.VISIBLE);
        status = equipmentStatusList.get(pos).getEsId();
        String selectedValue = equipmentStatusList.get(pos).getStatusText();
//        status_txt.setText(selectedValue);
    }

    @Override
    public void setClientSiteList(List<Site_model> siteModelList) {
        if (siteModelList != null && siteModelList.size() > 0) {
            AppUtility.autocompletetextviewPopUpWindow(auto_client_site);
            this.clientSiteList = siteModelList;
//            auto_client_site.setText(siteModelList.get(0).getSnm());
            siteId = siteModelList.get(0).getSiteId();
            client_site_layout.setHintEnabled(true);

            FilterAdapterSites filterSites = new FilterAdapterSites(this, R.layout.custom_adpter_item_layout_new, (ArrayList<Site_model>) siteModelList, true);
            auto_client_site.setAdapter(filterSites);
            auto_client_site.setThreshold(1);
            auto_client_site.setOnItemClickListener((adapterView, view, i, l) -> {
                siteId = ((Site_model) adapterView.getItemAtPosition(i)).getSiteId();
                client_site_layout.setHintEnabled(true);
                Site_model sitetData = (Site_model) (adapterView.getAdapter().getItem(i));
                if (sitetData != null) {
                    String newLocation = "";
                    if (sitetData.getAdr() != null && !sitetData.getAdr().isEmpty()) {
                        newLocation = newLocation + "" + sitetData.getAdr();
                    }
                    if (sitetData.getCity() != null && !sitetData.getCity().isEmpty()) {
                        city = sitetData.getCity();
                        newLocation = newLocation + ", " + city;
                    }
                    if (sitetData.getState() != null && !sitetData.getState().isEmpty() && !sitetData.getCtry().isEmpty() && !sitetData.getState().equals("0")) {
                        statenameById = SpinnerCountrySite.getStatenameById((sitetData.getCtry()), sitetData.getState());
                        newLocation = newLocation + ", " + statenameById;
                    }
                    if (sitetData.getCtry() != null && !sitetData.getCtry().isEmpty() && !sitetData.getCtry().equals("0")) {
                        countryNameById = SpinnerCountrySite.getCountryNameById(sitetData.getCtry());
                        newLocation = newLocation + ", " + countryNameById;
                    }
                    if (sitetData.getZip() != null && !sitetData.getZip().isEmpty()) {
                        zip = sitetData.getZip();
                        newLocation = newLocation + ", " + zip;
                    }
                    auto_client_site.setText(Html.fromHtml("<font color='#4C000000'>" + siteModelList.get(0).getSnm() + "</font>" + "<br>" + newLocation));
                    edt_equ_adrs.setText(sitetData.getAdr());
//                    equ_adrs_layout.setHintEnabled(true);
                }
            });

            auto_client_site.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() >= 1) {
                        client_site_layout.setHintEnabled(true);
                    } else if (charSequence.length() <= 0) {
                        client_site_layout.setHintEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    @Override
    public void setClientSiteListServer(List<ClientEquRes> clientEquRes) {
        this.clientEquResList = clientEquRes;

        final SiteAdpter siteAdpter = new SiteAdpter(this, R.layout.custom_adpter_item_layout_new, (ArrayList<ClientEquRes>) clientEquRes, true);
        auto_client_site.setAdapter(siteAdpter);
        auto_client_site.setThreshold(1);
        auto_client_site.setOnItemClickListener((adapterView, view, i, l) -> {

            siteId = ((ClientEquRes) adapterView.getItemAtPosition(i)).getSiteId();
            client_site_layout.setHintEnabled(true);
            ClientEquRes sitetData = (ClientEquRes) (adapterView.getAdapter().getItem(i));
            if (sitetData != null) {
                String newLocation = "";
                if (sitetData.getAdr() != null && !sitetData.getAdr().isEmpty()) {
                    newLocation = newLocation + "" + sitetData.getAdr();
                }
                if (sitetData.getCity() != null && !sitetData.getCity().isEmpty()) {
                    city = sitetData.getCity();
                    newLocation = newLocation + ", " + city;
                }
                if (sitetData.getState() != null && !sitetData.getState().isEmpty() && !sitetData.getCtry().isEmpty() && !sitetData.getState().equals("0")) {
                    statenameById = SpinnerCountrySite.getStatenameById((sitetData.getCtry()), sitetData.getState());
                    newLocation = newLocation + ", " + statenameById;
                }
                if (sitetData.getCtry() != null && !sitetData.getCtry().isEmpty() && !sitetData.getCtry().equals("0")) {
                    countryNameById = SpinnerCountrySite.getCountryNameById(sitetData.getCtry());
                    newLocation = newLocation + ", " + countryNameById;
                }
                if (sitetData.getZip() != null && !sitetData.getZip().isEmpty()) {
                    zip = sitetData.getZip();
                    newLocation = newLocation + ", " + zip;
                }
                auto_client_site.setText(Html.fromHtml("<font color='#4C000000'>" + sitetData.getSnm() + "</font>" + "<br>" + newLocation));
                edt_equ_adrs.setText(sitetData.getAdr());
//                equ_adrs_layout.setHintEnabled(true);
//                equ_adrs_layout.setClickable(false);
                edt_equ_adrs.setClickable(false);
            }
        });

        auto_client_site.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    client_site_layout.setHintEnabled(true);
                } else if (charSequence.length() <= 0) {
                    client_site_layout.setHintEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
     /*   if (adapterView.getId() == R.id.status_Dp) {
            setEquStatus(i);
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void removeTagData() {
        isTagSet = false;
        image_txt.setVisibility(View.GONE);
        remove_txt.setVisibility(View.GONE);
        rediogrpForTag.setVisibility(View.VISIBLE);
        radio_after.setChecked(false);
        radio_before.setChecked(false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.tv_scan_barcode:
                Intent intent = new Intent(AddJobEquipMentActivity.this, BarcodeScanActivity.class);
                intent.putExtra("comeFrom", "AddEquipment");
                startActivityForResult(intent, BAR_CODE_REQUEST);
                break;*/
            case R.id.remove_txt:
                removeTagData();
                break;
            case R.id.client_site_layout:
                auto_client_site.showDropDown();
                break;
//            case R.id.linearLayout_status:
//                status_Dp.performClick();
//                break;
//            case R.id.supplier_txt:
//                supplier_txt.performClick();
//                break;
            case R.id.lay:
                selectFile(true);
                break;
            case R.id.add_edit_item_Btn:
                if (isTagSet) {
                    processImageAndUpload();
                } else {
                    createEquipmentForJobAudit();
                }
                break;
            case R.id.auto_catery:
                if (GetCatgDataList != null && GetCatgDataList.size() > 0)
                    auto_catery.showDropDown();
                break;
            case R.id.auto_equipment:
                if (equipment_list != null && equipment_list.size() > 0)
                    auto_equipment.showDropDown();
                break;
            case R.id.auto_client_site:
//                if (clientSiteList != null && clientSiteList.size() > 0)
                auto_client_site.showDropDown();
                break;
            case R.id.auto_grp:
                if (GetgrpDataList != null && GetgrpDataList.size() > 0) auto_grp.showDropDown();
                break;
            case R.id.auto_brand:
                if (BrandDataList != null && BrandDataList.size() > 0) auto_brand.showDropDown();
                break;
            case R.id.manuf_date_layout:
                SelectManufDate("Manufacture");
                break;
            case R.id.date_purchase_layout:
                SelectPurchaseDate("Purchase");
                break;
            case R.id.date_warnty_layout:
                SelectWarntyDate("Warranty");
                break;
            case R.id.date_warnty_layout_start:
                SelectWarntyStartDate("Warranty_start");
                break;
            case R.id.date_installed_layout:
                SelectInstalleDate("installed");
                break;
            case R.id.manuf_date_cancel:
                if (!manuf_date_lable.getText().toString().equals("")) manuf_date_lable.setText("");
                binding.manufDateCancel.setVisibility(View.GONE);
                break;
            case R.id.warnty_date_cancel:
                if (!warnty_date_lable.getText().toString().equals(""))
                    warnty_date_lable.setText("");
                binding.warntyDateCancel.setVisibility(View.GONE);
                break;
            case R.id.purchase_date_cancel:
                if (!purchase_date_lable.getText().toString().equals(""))
                    purchase_date_lable.setText("");
                binding.purchaseDateCancel.setVisibility(View.GONE);
                break;
            case R.id.installed_date_cancel:
                if (!binding.installedDateLable.getText().toString().equals(""))
                    binding.installedDateLable.setText("");
                binding.installedDateCancel.setVisibility(View.GONE);
                break;
            case R.id.warnty_start_date_cancel:
                if (!warnty_date_lable_start.getText().toString().equals(""))
                    warnty_date_lable_start.setText("");
                binding.warntyStartDateCancel.setVisibility(View.GONE);
                break;
            case R.id.txt_addBarcode:
                if (barcode_image == null) {
                    addEditQrCodeBarCodeDialog = new AddEdit_QRCode_BarCode_Dialog(this);
                    Bundle bundle = new Bundle();
                    bundle.putString("barCode", barcodeString);
                    addEditQrCodeBarCodeDialog.setArguments(bundle);
                    addEditQrCodeBarCodeDialog.show(getSupportFragmentManager(), "");
                } else {
                    addEditQrCodeBarCodeDialog = new AddEdit_QRCode_BarCode_Dialog(this);
                    Bundle bundle = new Bundle();
                    bundle.putString("barCode", barcodeString);
                    addEditQrCodeBarCodeDialog.setArguments(bundle);
                    addEditQrCodeBarCodeDialog.show(getSupportFragmentManager(), "");
                }
                break;
            case R.id.txt_addQrcode:
                if (qrcode_image == null) {
                    addEditQrCodeBarCodeDialog = new AddEdit_QRCode_BarCode_Dialog(this);
                    Bundle bundle = new Bundle();
                    bundle.putString("qrcode", qrCodeString);
                    addEditQrCodeBarCodeDialog.setArguments(bundle);
                    addEditQrCodeBarCodeDialog.show(getSupportFragmentManager(), "");
                } else {
                    addEditQrCodeBarCodeDialog = new AddEdit_QRCode_BarCode_Dialog(this);
                    Bundle bundle = new Bundle();
                    bundle.putString("qrcode", qrCodeString);
                    addEditQrCodeBarCodeDialog.setArguments(bundle);
                    addEditQrCodeBarCodeDialog.show(getSupportFragmentManager(), "");
                }
                break;
            case R.id.show_less:
                ll_show_more_less.setVisibility(View.GONE);
                show_more.setVisibility(View.VISIBLE);
                break;
            case R.id.show_more:
                ll_show_more_less.setVisibility(View.VISIBLE);
                show_more.setVisibility(View.GONE);
                break;

            case R.id.clear_btn:
                edt_day.setText("");
                edt_month.setText("");
                edt_year.setText("");
                rdBtn_day.setChecked(false);
                rdBtn_month.setChecked(false);
                rdBtn_year.setChecked(false);
                edt_day.setVisibility(View.GONE);
                edt_month.setVisibility(View.GONE);
                edt_year.setVisibility(View.GONE);
                rediogrp.clearCheck();
                clear_btn.setVisibility(View.GONE);
                break;
        }
    }

    private void SelectInstalleDate(String tag) {
        int year = installCalendar.get(Calendar.YEAR);
        int month = installCalendar.get(Calendar.MONTH);
        int dayOfMonth = installCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddJobEquipMentActivity.this, datePickerListener, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setTag(tag);
        datePickerDialog.show();
    }

    private void SelectWarntyStartDate(String tag) {
        int year = startWarntyCalendar.get(Calendar.YEAR);
        int month = startWarntyCalendar.get(Calendar.MONTH);
        int dayOfMonth = startWarntyCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddJobEquipMentActivity.this, datePickerListener, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setTag(tag);
        datePickerDialog.show();
    }

    private void SelectWarntyDate(String tag) {
        int year = warntyCalendar.get(Calendar.YEAR);
        int month = warntyCalendar.get(Calendar.MONTH);
        int dayOfMonth = warntyCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddJobEquipMentActivity.this, datePickerListener, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setTag(tag);
        datePickerDialog.show();
    }

    private void SelectPurchaseDate(String tag) {
        int year = purchesCalendar.get(Calendar.YEAR);
        int month = purchesCalendar.get(Calendar.MONTH);
        int dayOfMonth = purchesCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddJobEquipMentActivity.this, datePickerListener, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setTag(tag);
        datePickerDialog.show();
    }

    private void SelectManufDate(String tag) {
        int year = manufCalendar.get(Calendar.YEAR);
        int month = manufCalendar.get(Calendar.MONTH);
        int dayOfMonth = manufCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddJobEquipMentActivity.this, datePickerListener, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setTag(tag);
        datePickerDialog.show();
    }

    private void createEquipmentForJobAudit() {
        add_edit_item_Btn.setEnabled(false);
        if (rdBtn_day.isChecked()) {
            interval = edt_day.getText().toString();
        } else if (rdBtn_month.isChecked()) {
            interval = edt_month.getText().toString();
        } else if (rdBtn_year.isChecked()) {
            interval = edt_year.getText().toString();
        } else {
            interval = "";
        }
        if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
            createEqquipmentRequestForReplace();
        } else {
            createEqquipmentRequest();
        }
        new Handler().postDelayed(() -> add_edit_item_Btn.setEnabled(true), 500);
    }

    public void createEqquipmentRequest() {

        String countryname, statename;
       /* countryname = auto_country.getText().toString();
        statename = auto_states.getText().toString();*/
        ctry = addJobEqu_pi.cntryId(countryNameById);
        state = addJobEqu_pi.statId(ctry, statenameById);
        SimpleDateFormat stf = new SimpleDateFormat("hh:mm:ss", Locale.US);
        String formatetime = stf.format(Calendar.getInstance().getTime());

        if (updateItemDataModel == null) {

            /*Convert item to EQUIPMENT*****/
            if (isPart.equalsIgnoreCase("1")) {
                isCnvtItemParts = "0";
            }

            if (addJobEqu_pi.RequiredFields(edt_equ.getText().toString().trim())) {
                if (comeFrom1 != null && comeFrom1.equalsIgnoreCase("AddPartWithoutItem")) {
                    addJobEqu_pi.convertItemToequip(new AddEquReq(type, egId, ecId, zip, city, edt_equ_adrs.getText().toString().trim(), ctry, state, /*isBarcodeGenerate,*/
                            quote_notes_edt.getText().toString().trim(), purchase_date_lable.getText().toString().trim() + " " + formatetime, manuf_date_lable.getText().toString().trim() + " " + formatetime, warnty_date_lable.getText().toString().trim() + " " + formatetime, "", edt_equ_serial.getText().toString().trim(), edt_equ_model.getText().toString().trim(), brandId, edt_equ.getText().toString().trim(), jobId, job.getCltId(), job.getContrId(), "", "", siteId, isPart, status, invId, custom_filed_txt_1.getText().toString().trim(), custom_filed_txt_2.getText().toString().trim(), servIntvalType, interval, isCnvtItemParts, binding.installedDateLable.getText().toString().trim() + " " + formatetime, supplierId, ""), path, barcodeString, qrCodeString, equipmentId);
                } else if (audit != null) {
                    addJobEqu_pi.addNewEquipment(new AddEquReq(type, egId, ecId, zip, city, edt_equ_adrs.getText().toString().trim(), ctry, state, /*isBarcodeGenerate,*/
                            quote_notes_edt.getText().toString().trim(), purchase_date_lable.getText().toString().trim() + " " + formatetime, manuf_date_lable.getText().toString().trim() + " " + formatetime, warnty_date_lable.getText().toString().trim() + " " + formatetime, "", edt_equ_serial.getText().toString().trim(), edt_equ_model.getText().toString().trim(), brandId, edt_equ.getText().toString().trim(), audit.getAudId(), audit.getCltId(), audit.getContrId(), siteId, isPart, status, custom_filed_txt_1.getText().toString().trim(), custom_filed_txt_2.getText().toString().trim(), servIntvalType, interval, supplierId), path, barcodeString, qrCodeString, binding.installedDateLable.getText().toString().trim() + " " + formatetime, equipmentId);
                } else {
                    addJobEqu_pi.addNewEquipment(new AddEquReq(type, egId, ecId, zip, city, edt_equ_adrs.getText().toString().trim(), ctry, state, /*isBarcodeGenerate,*/
                            quote_notes_edt.getText().toString().trim(), purchase_date_lable.getText().toString().trim() + " " + formatetime, manuf_date_lable.getText().toString().trim() + " " + formatetime, warnty_date_lable.getText().toString().trim() + " " + formatetime, "", edt_equ_serial.getText().toString().trim(), edt_equ_model.getText().toString().trim(), brandId, edt_equ.getText().toString().trim(), jobId, job.getCltId(), job.getContrId(), siteId, isPart, status, custom_filed_txt_1.getText().toString().trim(), custom_filed_txt_2.getText().toString().trim(), servIntvalType, interval, supplierId), path, barcodeString, qrCodeString, binding.installedDateLable.getText().toString().trim() + " " + formatetime, equipmentId);
                }
            }
        } else {
            /*Convert item to EQUIPMENT*****/
            if (isPart.equalsIgnoreCase("1")) {
                isCnvtItemParts = "0";
            }
            if (addJobEqu_pi.RequiredFields(edt_equ.getText().toString().trim())) {
                addJobEqu_pi.convertItemToequip(new AddEquReq(type, egId, ecId, zip, city, edt_equ_adrs.getText().toString().trim(), ctry, state, /*isBarcodeGenerate,*/
                        quote_notes_edt.getText().toString().trim(), purchase_date_lable.getText().toString().trim() + " " + formatetime, manuf_date_lable.getText().toString().trim() + " " + formatetime, warnty_date_lable.getText().toString().trim() + " " + formatetime, "", edt_equ_serial.getText().toString().trim(), edt_equ_model.getText().toString().trim(), brandId, edt_equ.getText().toString().trim(), jobId, job.getCltId(), job.getContrId(), updateItemDataModel.getItemId(), updateItemDataModel.getRate(), siteId, isPart, status, invId, custom_filed_txt_1.getText().toString().trim(), custom_filed_txt_2.getText().toString().trim(), servIntvalType, interval, isCnvtItemParts, binding.installedDateLable.getText().toString().trim() + " " + formatetime, supplierId, updateItemDataModel.getIjmmId()), path, barcodeString, qrCodeString, equipmentId);
            }
        }
    }

    public void createEqquipmentRequestForReplace() {

        String countryname, statename;
     /*   countryname = auto_country.getText().toString();
        statename =auto_states.getText().toString();*/
        ctry = addJobEqu_pi.cntryId(countryNameById);
        state = addJobEqu_pi.statId(ctry, statenameById);

        if (updateItemDataModel == null) {
            if (addJobEqu_pi.RequiredFields(edt_equ.getText().toString().trim())) {
                if (comeFrom1 != null && comeFrom1.equalsIgnoreCase("AddPartWithoutItem")) {
                    String parentId = "";
                    if (isPart.equalsIgnoreCase("1")) {
                        parentId = equipment.getParentId();
                        isCnvtItemParts = "0";
                    }
                    addJobEqu_pi.convertItemToequip(new AddEquReq(type, egId, ecId, zip, city, edt_equ_adrs.getText().toString().trim(), ctry, state, /*isBarcodeGenerate,*/
                            quote_notes_edt.getText().toString().trim(), purchase_date_lable.getText().toString().trim(), manuf_date_lable.getText().toString().trim(), warnty_date_lable.getText().toString().trim(), "", edt_equ_serial.getText().toString().trim(), edt_equ_model.getText().toString().trim(), brandId, edt_equ.getText().toString().trim(), jobId, job.getCltId(), job.getContrId(), "", "", siteId, isPart, status, invId, custom_filed_txt_1.getText().toString().trim(), custom_filed_txt_2.getText().toString().trim(), servIntvalType, interval, "1", equipmentId, isCnvtItemParts, binding.installedDateLable.getText().toString().trim(), supplierId, ""), path, barcodeString, qrCodeString, parentId);
                } else if (audit != null) {
                    addJobEqu_pi.addNewEquipment(new AddEquReq(type, egId, ecId, zip, city, edt_equ_adrs.getText().toString().trim(), ctry, state, /*isBarcodeGenerate,*/
                            quote_notes_edt.getText().toString().trim(), purchase_date_lable.getText().toString().trim(), manuf_date_lable.getText().toString().trim(), warnty_date_lable.getText().toString().trim(), "", edt_equ_serial.getText().toString().trim(), edt_equ_model.getText().toString().trim(), brandId, edt_equ.getText().toString().trim(), audit.getAudId(), audit.getCltId(), audit.getContrId(), siteId, isPart, status, custom_filed_txt_1.getText().toString().trim(), custom_filed_txt_2.getText().toString().trim(), servIntvalType, interval, supplierId), path, barcodeString, barcodeString, binding.installedDateLable.getText().toString().trim(), equipmentId);
                } else {
                    addJobEqu_pi.addNewEquipment(new AddEquReq(type, egId, ecId, zip, city, edt_equ_adrs.getText().toString().trim(), ctry, state, /*isBarcodeGenerate,*/
                            quote_notes_edt.getText().toString().trim(), purchase_date_lable.getText().toString().trim(), manuf_date_lable.getText().toString().trim(), warnty_date_lable.getText().toString().trim(), "", edt_equ_serial.getText().toString().trim(), edt_equ_model.getText().toString().trim(), brandId, edt_equ.getText().toString().trim(), jobId, job.getCltId(), job.getContrId(), siteId, isPart, status, custom_filed_txt_1.getText().toString().trim(), custom_filed_txt_2.getText().toString().trim(), servIntvalType, interval, supplierId), path, barcodeString, barcodeString, binding.installedDateLable.getText().toString().trim(), equipmentId);
                }
            }
        } else {
            /*Convert item to EQUIPMENT*****/
            if (addJobEqu_pi.RequiredFields(edt_equ.getText().toString().trim())) {
                String parentId = "";
                if (isPart.equalsIgnoreCase("1")) {
                    parentId = equipment.getParentId();
                    isCnvtItemParts = "0";
                }
                addJobEqu_pi.convertItemToequip(new AddEquReq(type, egId, ecId, zip, city, edt_equ_adrs.getText().toString().trim(), ctry, state, /*isBarcodeGenerate,*/
                        quote_notes_edt.getText().toString().trim(), purchase_date_lable.getText().toString().trim(), manuf_date_lable.getText().toString().trim(), warnty_date_lable.getText().toString().trim(), "", edt_equ_serial.getText().toString().trim(), edt_equ_model.getText().toString().trim(), brandId, edt_equ.getText().toString().trim(), jobId, job.getCltId(), job.getContrId(), updateItemDataModel.getItemId(), updateItemDataModel.getRate(), siteId, isPart, status, invId, custom_filed_txt_1.getText().toString().trim(), custom_filed_txt_2.getText().toString().trim(), servIntvalType, interval, "1", equipmentId, isCnvtItemParts, binding.installedDateLable.getText().toString().trim(), supplierId, updateItemDataModel.getIjmmId()), path, barcodeString, qrCodeString, parentId);
            }
        }
    }

    @Override
    public void finishActivity() {
        Log.e("AddEquipment", "FinishAffinity");
        if (comeFrom != null && comeFrom.equalsIgnoreCase("JobListScan") || comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
            boolean isAddPart = false;
//            if(comeFrom != null && comeFrom.equalsIgnoreCase("AddRemark")){
//                isAddPart =true;
//            }
            AddEquipNavigationDialog addEquipNavigationDialog = new AddEquipNavigationDialog(this, isAddPart);
            addEquipNavigationDialog.show(getSupportFragmentManager(), "");
        } else {
            this.finish();
        }
    }

    @Override
    public void sessionExpire(String msg) {
        AppUtility.alertDialog(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title), msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> {
            EotApp.getAppinstance().sessionExpired();
            return null;
        });
    }

    @Override
    public void addExpenseSuccesFully(String msg) {
        AppUtility.progressBarDissMiss();
        EotApp.getAppinstance().getNotifyForEquipmentCountRemark();
        Log.e("AddEquipment", "AddExpense");
        EotApp.getAppinstance().showToastmsg(msg);
        if (comeFrom != null && !comeFrom.equalsIgnoreCase("JobListScan") && !comeFrom.equalsIgnoreCase("AddRemarkReplace") && !comeFrom.equalsIgnoreCase("AddRemark")) {
            new Handler().postDelayed(() -> {
                if (comeFrom != null && !comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
                    if (new JobEquRemarkRemarkActivity().getInstance() != null) {
                        new JobEquRemarkRemarkActivity().getInstance().finish();
                    }
                    if (new JobEquPartRemarkRemarkActivity().getInstance() != null) {
                        new JobEquPartRemarkRemarkActivity().getInstance().finish();
                    }
                    /*navigate to the job equipment screen *****/
                    Intent intent = new Intent();
                    setResult(EQUIPMENT_UPDATE_CODE, intent);
                    finish();

                } else {

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    /*Refresh list for Appoinment*****/
                    finish();
                }
            }, 500);


        }
    }

    @Override
    public void convertEquipment(String msg) {
        Log.e("AddEquipment", "convertEquipment");

        EotApp.getAppinstance().showToastmsg(msg);
        Intent intent = new Intent();
        setResult(EQUIPMENTCONVERT, intent);
        finish();
    }


    @Override
    public void setCountryError(String msg) {
        showErrorDialog(msg);
    }

    @Override
    public void setStateError(String msg) {
        showErrorDialog(msg);
    }

    @Override
    public void setEuqipmentList(List<EquArrayModel> equArray) {
        for (int i = 0; i < equArray.size(); i++) {
            if (equArray.get(i).getIsPart().equalsIgnoreCase("0")) {
                equipment_list.add(equArray.get(i));
            }
        }
        Log.e("equipment_list", new Gson().toJson(equipment_list));
        final EquipmentAdapter countryAdapter = new EquipmentAdapter(this, R.layout.custom_adpter_item_layout_new, (ArrayList<EquArrayModel>) equipment_list);
        auto_equipment.setAdapter(countryAdapter);
        auto_equipment.setThreshold(1);
        auto_equipment.setOnItemClickListener((adapterView, view, i, l) -> equipmentId = ((EquArrayModel) adapterView.getItemAtPosition(i)).getEquId());

        auto_equipment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    equ_layout.setHintEnabled(true);
                } else if (charSequence.length() <= 0) {
                    equ_layout.setHintEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void showErrorDialog(String msg) {
        AppUtility.error_Alert_Dialog(this, msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), () -> null);
    }

    private void setCompanySettingAdrs() {
       /* client_layout.setVisibility(View.GONE);
        client_row.setVisibility(View.GONE);*/
        client_site_layout.setVisibility(View.GONE);
//        site_row.setVisibility(View.GONE);

//        auto_country.setText(SpinnerCountrySite.getCountryNameById(App_preference.getSharedprefInstance().getCompanySettingsDetails().getCtry()));
        /*Important ******/
        //   addJobEqu_pi.getStateList((App_preference.getSharedprefInstance().getCompanySettingsDetails().getCtry()));
//        auto_states.setText(SpinnerCountrySite.getStatenameById(App_preference.getSharedprefInstance().getCompanySettingsDetails().getCtry(), App_preference.getSharedprefInstance().getCompanySettingsDetails().getState()));
//        edt_equ_city.setText(App_preference.getSharedprefInstance().getCompanySettingsDetails().getCity());
//        edt_equ_city.setText(App_preference.getSharedprefInstance().getCompanySettingsDetails().getCity());


        //state = (App_preference.getSharedprefInstance().getCompanySettingsDetails().getState());
        // ctry = (App_preference.getSharedprefInstance().getCompanySettingsDetails().getCtry());

//        job_country_layout.setHintEnabled(true);
//        job_state_layout.setHintEnabled(true);
//        equ_adrs_layout.setHintEnabled(true);
//        equ_city_layout.setHintEnabled(true);
//        equ_zip_layout.setHintEnabled(true);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
           /* case R.id.checkbox_barCode:
                isBarcodeGenerate = buttonView.isChecked() ? "1" : "0";
                if (isChecked) {
                    binding.checkboxScanBarCode.setChecked(false);
                    binding.scanBarcodeLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.checkbox_scan_barCode:
                if (isChecked) {
                    binding.checkboxBarCode.setChecked(false);
                    binding.scanBarcodeLayout.setVisibility(View.VISIBLE);
                }
                break;
*/
            case R.id.ch_equ_as_part:
                isPart = buttonView.isChecked() ? "1" : "0";
                if (buttonView.isChecked()) {
                    equipment_layout.setVisibility(View.VISIBLE);
                    eq_view.setVisibility(View.VISIBLE);
                } else {
                    equipment_layout.setVisibility(View.GONE);
                    eq_view.setVisibility(View.GONE);
                }
                break;

            // for equipment type

            case R.id.radio_owner:
                if (isChecked) {
                    type = "1";
                }
            case R.id.radio_serv_prov:
                if (isChecked) {
                    type = "2";
                }
                break;
        }
    }

    /*  @Override
      protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          if (data != null) {
              if (requestCode == BAR_CODE_REQUEST) {
                  if (data.getStringExtra("code") != null) {
                      String barcode = data.getStringExtra("code");
                      binding.etBarcode.setText(barcode);
                  }
              }
          }


      }
  */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_before:
                isTagSet = true;
                image_txt.setVisibility(View.VISIBLE);
                image_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.before));
                rediogrpForTag.setVisibility(View.GONE);
                remove_txt.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_after:
                isTagSet = true;
                image_txt.setVisibility(View.VISIBLE);
                image_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.after));
                rediogrpForTag.setVisibility(View.GONE);
                remove_txt.setVisibility(View.VISIBLE);
                break;

            // send service due date with the help of service interval value and type
            // days=0
            //month=1
            //year=2
            case R.id.radio_day:
                servIntvalType = "0";
                edt_day.setVisibility(View.VISIBLE);
                edt_day.setText("");
                edt_month.setText("");
                edt_year.setText("");
                edt_day.requestFocus();
                edt_month.setVisibility(View.GONE);
                edt_year.setVisibility(View.GONE);
                clear_btn.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_month:
                servIntvalType = "1";
                edt_day.setVisibility(View.GONE);
                edt_month.setVisibility(View.VISIBLE);
                edt_day.setText("");
                edt_month.setText("");
                edt_year.setText("");
                edt_month.requestFocus();
                edt_year.setVisibility(View.GONE);
                clear_btn.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_year:
                servIntvalType = "2";
                edt_day.setVisibility(View.GONE);
                edt_month.setVisibility(View.GONE);
                edt_year.setVisibility(View.VISIBLE);
                edt_day.setText("");
                edt_month.setText("");
                edt_year.setText("");
                edt_year.requestFocus();
                clear_btn.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (comeFrom != null && comeFrom.equalsIgnoreCase("AddRemarkReplace")) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            } else {
                onBackPressed();
            }
            return true;
        }
        return true;
    }

    private void setCatergs(List<GetCatgData> cateList) {
        final CateAdpter countryAdapter = new CateAdpter(this, R.layout.custom_adpter_item_layout_new, (ArrayList<GetCatgData>) cateList);
        auto_catery.setAdapter(countryAdapter);
        auto_catery.setThreshold(1);
        auto_catery.setOnItemClickListener((adapterView, view, i, l) -> {
            ecId = ((GetCatgData) adapterView.getItemAtPosition(i)).getEcId();
            // catery_layout.setHintEnabled(true);
        });

        auto_catery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    catery_layout.setHintEnabled(true);
                } else if (charSequence.length() <= 0) {
                    catery_layout.setHintEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void setCategList(List<GetCatgData> cateList) {
        if (GetCatgDataList == null) {
            this.GetCatgDataList = cateList;
            setCatergs(cateList);
        }
        try {
            String s = new Gson().toJson(cateList);
            App_preference.getSharedprefInstance().setcatglist(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setbrands(List<BrandData> brandList) {
        final BrandAdapter countryAdapter = new BrandAdapter(this, R.layout.custom_adpter_item_layout_new, (ArrayList<BrandData>) brandList);
        auto_brand.setAdapter(countryAdapter);
        auto_brand.setThreshold(1);
        auto_brand.setOnItemClickListener((adapterView, view, i, l) -> {
            //     egId = ((GetgrpData) adapterView.getItemAtPosition(i)).getEgId();
//                addJobEqu_pi.getStateList(((Country) adapterView.getItemAtPosition(i)).getId());
            brandId = ((BrandData) adapterView.getItemAtPosition(i)).getEbId();
            auto_barnd_layout.setHintEnabled(true);
        });

        auto_brand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    auto_barnd_layout.setHintEnabled(true);
                } else if (charSequence.length() <= 0) {
                    auto_barnd_layout.setHintEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void setBrandList(List<BrandData> brandList) {
        if (BrandDataList == null) {
            this.BrandDataList = brandList;
            setbrands(brandList);
        }
        try {
            String s = new Gson().toJson(brandList);
            App_preference.getSharedprefInstance().setBrandLists(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setgrplists(List<GetgrpData> cateList) {
        final GrpAdpter countryAdapter = new GrpAdpter(this, R.layout.custom_adpter_item_layout_new, (ArrayList<GetgrpData>) cateList);
        auto_grp.setAdapter(countryAdapter);
        auto_grp.setThreshold(1);
        auto_grp.setOnItemClickListener((adapterView, view, i, l) -> {
            egId = ((GetgrpData) adapterView.getItemAtPosition(i)).getEgId();
//                addJobEqu_pi.getStateList(((Country) adapterView.getItemAtPosition(i)).getId());
            grp_layout.setHintEnabled(true);
        });

        auto_grp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    grp_layout.setHintEnabled(true);
                } else if (charSequence.length() <= 0) {
                    grp_layout.setHintEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void setGrpList(List<GetgrpData> cateList) {
        if (GetgrpDataList == null) {
            this.GetgrpDataList = cateList;
            setgrplists(cateList);
        }
        try {
            String s = new Gson().toJson(cateList);
            App_preference.getSharedprefInstance().setGrpList(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setCountryList(List<Country> countryList) {
//        AppUtility.autocompletetextviewPopUpWindow(job_country_layout);
        final FilterCountry countryAdapter = new FilterCountry(this, R.layout.custom_adapter_item_layout, (ArrayList<Country>) countryList);
       /* auto_country.setAdapter(countryAdapter);
        auto_country.setThreshold(1);
        auto_country.setOnItemClickListener((adapterView, view, i, l) -> {
            ctry = ((Country) adapterView.getItemAtPosition(i)).getId();
            addJobEqu_pi.getStateList(((Country) adapterView.getItemAtPosition(i)).getId());
            job_country_layout.setHintEnabled(true);
        });*/

       /* auto_country.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    job_country_layout.setHintEnabled(true);
                } else if (charSequence.length() <= 0) {
                    job_country_layout.setHintEnabled(false);
                }
                auto_states.setText("");
                ctry = "";
                state = "";
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
    }


    @Override
    public void setStateList(List<States> statesList) {
        //AppUtility.autocompletetextviewPopUpWindow(job_state_layout);
        FilterStates stateAdapter = new FilterStates(this, R.layout.custom_adapter_item_layout, (ArrayList<States>) statesList);
       /* auto_states.setAdapter(stateAdapter);
        auto_states.setThreshold(0);
        auto_states.setOnItemClickListener((adapterView, view, i, l) -> {
            state = ((States) adapterView.getItemAtPosition(i)).getId();
            job_state_layout.setHintEnabled(true);
        });


        auto_states.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    job_state_layout.setHintEnabled(true);
                } else if (charSequence.length() <= 0) {
                    job_state_layout.setHintEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
    }

    @Override
    public void setsupplierList(List<GetSupplierData> supplierDataList) {
        if (supplierDataList != null && supplierDataList.size() > 0) {
            if (this.supplierDataList != null) this.supplierDataList.clear();
            this.supplierDataList = supplierDataList;
            final SupplierAdapter stateAdapter = new SupplierAdapter(this, R.layout.custom_adapter_item_layout, (ArrayList<GetSupplierData>) supplierDataList);
            if (stateAdapter != null) {
           /* supplier_txt.setAdapter(stateAdapter);
            supplier_txt.setThreshold(1);*/
            } else {
                stateAdapter.setnewlist(supplierDataList);
            }
           /* supplier_txt.setOnItemClickListener((adapterView, view, i, l) -> {
                supplierId=supplierDataList.get(i).getSupId();
            });*/
//            supplier_layout.setHintEnabled(true);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (charSequence.length() >= 1) {
            if (charSequence.hashCode() == edt_equ.getText().hashCode())
                equ_layout.setHintEnabled(true);
            if (charSequence.hashCode() == edt_equ_model.getText().hashCode())
                equ_model_layout.setHintEnabled(true);

            if (charSequence.hashCode() == edt_equ_supplier.getText().hashCode())
                equ_supplier_layout.setHintEnabled(true);
//            if (charSequence.hashCode() == edt_equ_brand.getText().hashCode())
//                equ_brand_layout.setHintEnabled(true);
            if (charSequence.hashCode() == edt_equ_serial.getText().hashCode())
                equ_serial_layout.setHintEnabled(true);
           /* if (charSequence.hashCode() == edt_equ_city.getText().hashCode())
                equ_city_layout.setHintEnabled(true);
            if (charSequence.hashCode() == edt_equ_zip.getText().hashCode())
                equ_zip_layout.setHintEnabled(true);*/
            if (charSequence.hashCode() == quote_notes_edt.getText().hashCode())
                quote_notes_layout.setHintEnabled(true);
//            if (charSequence.hashCode() == edt_equ_adrs.getText().hashCode())
//                equ_adrs_layout.setHintEnabled(true);
            if (charSequence.hashCode() == custom_filed_txt_1.getText().hashCode())
                custom_filed_header_1.setHintEnabled(true);
            if (charSequence.hashCode() == custom_filed_txt_2.getText().hashCode())
                custom_filed_header_2.setHintEnabled(true);
        } else if (charSequence.length() <= 0) {
            /*Floating hint Disable after text enter**/
            if (charSequence.hashCode() == edt_equ.getText().hashCode())
                equ_layout.setHintEnabled(false);
            if (charSequence.hashCode() == edt_equ_model.getText().hashCode())
                equ_model_layout.setHintEnabled(false);


            if (charSequence.hashCode() == edt_equ_supplier.getText().hashCode())
                equ_supplier_layout.setHintEnabled(false);
//            if (charSequence.hashCode() == edt_equ_brand.getText().hashCode())
//                equ_brand_layout.setHintEnabled(false);
            if (charSequence.hashCode() == edt_equ_serial.getText().hashCode())
                equ_serial_layout.setHintEnabled(false);
           /* if (charSequence.hashCode() == edt_equ_city.getText().hashCode())
                equ_city_layout.setHintEnabled(false);
            if (charSequence.hashCode() == edt_equ_zip.getText().hashCode())
                equ_zip_layout.setHintEnabled(false);*/
            if (charSequence.hashCode() == quote_notes_edt.getText().hashCode())
                quote_notes_layout.setHintEnabled(false);
//            if (charSequence.hashCode() == edt_equ_adrs.getText().hashCode())
//                equ_adrs_layout.setHintEnabled(false);
            if (charSequence.hashCode() == custom_filed_txt_1.getText().hashCode())
                custom_filed_header_1.setHintEnabled(true);
            if (charSequence.hashCode() == custom_filed_txt_2.getText().hashCode())
                custom_filed_header_2.setHintEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void SelectStartDate(String tag) {
        Calendar myCalendar = Calendar.getInstance();
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddJobEquipMentActivity.this, datePickerListener, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setTag(tag);
        datePickerDialog.show();
    }

    @Override
    public void setEquReqError(String msg) {
        showDialogs(msg);
    }

    private void showDialogs(String msg) {
        AppUtility.error_Alert_Dialog(this, msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), () -> null);
    }


    @Override
    public void onClickContinuarEvent(Uri uri) {
        //  path = PathUtils.getPath(this, uri);
        path = PathUtils.getRealPath(this, uri);
        if (!path.isEmpty()) {
            File file = new File(path);
            if (file != null && file.exists()) {
                img_attachment.setVisibility(View.VISIBLE);
                Picasso.get().load(file).into(img_attachment);
                image_txt.setText("");
                remove_txt.setVisibility(View.GONE);
                radio_after.setChecked(false);
                radio_before.setChecked(false);
                rediogrpForTag.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDocumentSelected(String paths, String name, boolean isImage) {
        //   super.onDocumentSelected(paths, isImage);
        try {
            //   if (!path.isEmpty()) {
            path = paths;
            final String ext = path.substring(path.lastIndexOf("."));
            img_attachment.setVisibility(View.VISIBLE);

            if (ext.equals(".doc") || ext.equals(".docx")) {
                img_attachment.setImageResource(R.drawable.word);
                img_attachment.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else if (ext.equals(".pdf")) {
                img_attachment.setImageResource(R.drawable.pdf);
                img_attachment.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else if (ext.equals(".xlsx") || ext.equals(".xls")) {
                img_attachment.setImageResource(R.drawable.excel);
                img_attachment.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else if (ext.equals(".csv")) {
                img_attachment.setImageResource(R.drawable.csv);
                img_attachment.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else {
                File file = new File(path);
                if (file != null && file.exists()) {
                    img_attachment.setVisibility(View.VISIBLE);
                    Picasso.get().load(file).into(img_attachment);
                }
                img_attachment.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                rediogrpForTag.setVisibility(View.VISIBLE);
                radio_after.setChecked(false);
                radio_before.setChecked(false);
                image_txt.setText("");
                remove_txt.setVisibility(View.GONE);
                rediogrpForTag.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processImageAndUpload() {
        path = saveBitMap(image_with_tag);
        createEquipmentForJobAudit();
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


    @Override
    public void onDataPass(QRCOde_Barcode_Res_Model data) {
        try {
            if (data != null && data.getBarCode() != null && !data.getBarCode().isBlank() && data.getBarcodeImg() != null && !data.getBarcodeImg().isBlank()) {
                txt_addBarcode.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit_Barcode));
                txt_addBarcode.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.edit), null, null, null);
                img_barcode.setVisibility(View.VISIBLE);
                Picasso.get().load(App_preference.getSharedprefInstance().getBaseURL() + data.getBarcodeImg()).placeholder(R.drawable.ic_profile).error(R.drawable.ic_profile).into(img_barcode);
                barcodeString = data.getBarCode();
            } else if (data != null && data.getQrcode() != null && !data.getQrcode().isBlank() && !data.getQrcode().isBlank() && data.getQrcodeImg() != null && !data.getQrcodeImg().isBlank()) {
                txt_addQrcode.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit_QR_Code));
                txt_addQrcode.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.edit), null, null, null);
                img_Qrcode.setVisibility(View.VISIBLE);
                Picasso.get().load(App_preference.getSharedprefInstance().getBaseURL() + data.getQrcodeImg()).placeholder(R.drawable.ic_profile).error(R.drawable.ic_profile).into(img_Qrcode);
                qrCodeString = data.getQrcode();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void setBarCodeData(QRCOde_Barcode_Res_Model res_Model) {
        onDataPass(res_Model);
    }

    @Override
    public void setQRCodeData(QRCOde_Barcode_Res_Model res_Model) {
        onDataPass(res_Model);
    }

    @Override
    public void alertShow(String msg) {

    }

    @Override
    public void toastShow(String msg) {

    }

    @Override
    public void setEuqipment(EquArrayModel equArray) {
        equipmentRes = equArray;
    }

    @Override
    public void setNavigation(boolean isActionPage) {
        if (new AddEditInvoiceItemActivity2().getInstance() != null) {
            new AddEditInvoiceItemActivity2().getInstance().finish();
        }
        if (comeFrom != null && !comeFrom.equalsIgnoreCase("AddRemark")) {
            if (new JobEquRemarkRemarkActivity().getInstance() != null) {
                new JobEquRemarkRemarkActivity().getInstance().finish();
            }
            if (new JobEquPartRemarkRemarkActivity().getInstance() != null) {
                new JobEquPartRemarkRemarkActivity().getInstance().finish();
            }
        }
        if (isActionPage) {
            if (equipmentRes.getIsPart() != null) {
                if (equipmentRes.getIsPart().equalsIgnoreCase("1")) {
                    Intent intent = new Intent(this, JobEquPartRemarkRemarkActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    String strEqu = new Gson().toJson(equipmentRes);
                    intent.putExtra("equipment", strEqu);
                    intent.putExtra("jobId", jobId);
                    intent.putExtra("cltId", cltId);
                    intent.putExtra("positions", "");
                    intent.putExtra("isGetData", "");
                    intent.putExtra("isAction", isActionPage);
//                    startActivityForResult(intent, new JobEquipmentActivity().EQUIPMENT_UPDATE_CODE);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, JobEquRemarkRemarkActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    String strEqu = new Gson().toJson(equipmentRes);
                    intent.putExtra("equipment", strEqu);
                    intent.putExtra("jobId", jobId);
                    intent.putExtra("cltId", cltId);
                    intent.putExtra("positions", "");
                    intent.putExtra("isGetData", "");
                    intent.putExtra("isAction", isActionPage);
//                    startActivityForResult(intent,new JobEquipmentActivity().EQUIPMENT_UPDATE_CODE);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(this, JobEquRemarkRemarkActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                String strEqu = new Gson().toJson(equipmentRes);
                intent.putExtra("equipment", strEqu);
                intent.putExtra("jobId", jobId);
                intent.putExtra("cltId", cltId);
                intent.putExtra("positions", "");
                intent.putExtra("isGetData", "");
                intent.putExtra("isAction", isActionPage);
//                startActivityForResult(intent, new JobEquipmentActivity().EQUIPMENT_UPDATE_CODE);
                startActivity(intent);
            }
        }

        this.finish();
    }
}