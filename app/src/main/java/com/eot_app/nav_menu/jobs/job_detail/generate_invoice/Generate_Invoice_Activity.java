package com.eot_app.nav_menu.jobs.job_detail.generate_invoice;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eot_app.R;
import com.eot_app.login_next.FooterMenu;
import com.eot_app.nav_menu.jobs.job_card_view.JobCardViewActivity;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.AddEditInvoiceItemActivity2;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.detail.DialogJobCardDocuments;
import com.eot_app.nav_menu.jobs.job_detail.generate_invoice.invoice_adpter_pkg.Sipping_Adpter;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_db.location_tax_dao.TaxLocAdpter;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_db.location_tax_dao.TaxesLocation;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.Inv_Client_Address_model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.ShippingItem;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.Invoice_Email_Activity;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model.InvoiceEmaliTemplate;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.GenerateInvoiceItemAdpter;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.adapter.InvoiceTaxAdapter;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.itemlist_model.InvoiceItemDetailsModel;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.itemlist_model.TaxData;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.itemlist_mvp.ItemList_PC;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.itemlist_mvp.ItemList_PI;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.itemlist_mvp.ItemList_View;
import com.eot_app.nav_menu.quote.add_quotes_pkg.AddQuotes_Activity;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.util_interfaces.Callback_AlertDialog;
import com.eot_app.utility.util_interfaces.GetListData;
import com.eot_app.utility.util_interfaces.MyListItemSelected;
import com.eot_app.utility.util_interfaces.MyListItemSelectedLisT;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.Callable;

public class Generate_Invoice_Activity extends AppCompatActivity implements MyListItemSelected<InvoiceItemDataModel>,
        View.OnClickListener, MyListItemSelectedLisT<InvoiceItemDataModel>
        , ItemList_View, InvoiceItemObserver, GetListData {
    private final static int ADD_ITEM_DATA = 1;
    static public String jobId, invId = "", isJobInvoiced = "";
    Button pay_btn_inv, addInvoiceItem_btn;
    boolean islang;
    boolean isFABOpen = false;
    List<InvoiceItemDataModel> itemData_Details = new ArrayList<>();
    LinearLayoutManager layoutManager;
    TextView list_item_invoice_count, pay_txt, dueDate,txt_date,txt_ok,txt_cancel;
    EditText pay_edt_partial;
    TextView invoice_nm, invoice_adrs, inv_email, inv_total_amount, invoice_cre_dt, invoice_due_dt, tv_fab_email, tv_fab_print_invoice, tv_fab_add_new_item, tv_due_date, tv_create_date;
    DialogJobCardDocuments dialogJobCardDocuments;
    Inv_Client_Address_model client_address_model;
    ArrayList<InvoiceEmaliTemplate> templateList = new ArrayList<>();
    TextView tax_loc_lable, loc_txt, remove_txt_loc;
    RelativeLayout rl;
    private String locId = "0";
    private RecyclerView recyclerView_invoice, recyclerView_shippingitem;
    private GenerateInvoiceItemAdpter invoice_list_adpter;
    private Sipping_Adpter sipping_adpter;
    private RelativeLayout lay;
    private ImageView rm_invice_im;
    private LinearLayout rm_layout;
    private ArrayList<InvoiceItemDataModel> rm_DataItem = new ArrayList<>();
    private List<InvoiceItemDataModel> tempItemList = new ArrayList<>();
    private LinearLayout empty_invoice_lay;
    private InvoiceItemDetailsModel invoice_Details;
    private TextView empty_inv_txt;
    private SwipeRefreshLayout swiperefresh;
    private FloatingActionButton invoiceFab;
    private LinearLayout linearFabEmail, linearFabPrintInvoice, linearFabAddNewItem;
    private View backgroundView;
    private ActionBar actionBar;
    private ItemList_PI itemListPi;
    private int totalItemSize = 0;
    private Spinner loc_tax_dp;
    private List<TaxesLocation> taxList = new ArrayList<>();
    private ConstraintLayout cl_parent_calculation;
    private TextView txt_lbl_sub_total, txt_sub_total, txt_lbl_additional_discount, txt_additional_discount, txt_lbl_additional_discount1, txt_additional_discount1, txt_lbl_total, txt_total,txt_lbl_tax, txt_tax;
    InvoiceItemDetailsModel invResModelForDueDate;
    private RecyclerView rvShowTax;
    private InvoiceTaxAdapter invoiceTaxAdapter;
    private String getDisCalculationType,getTaxCalculationType;
    String End_Date = "End_Date";
    private String toJsonTemplate;
    static Dialog dialog;
    Job mjob;
    private final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            String dateselect = "";
            try {
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);//hh:mm:ss a
                Date dueDate = formatter.parse(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
                dateselect = new SimpleDateFormat(AppConstant.DATE_FORMAT, Locale.US).format(dueDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat dateFormat = new SimpleDateFormat(
                    AppUtility.dateTimeByAmPmFormate("hh:mm:ss a", "HH:mm:ss"), Locale.US);//append current time
            dateFormat.format(new Date());
            String tag = ((String) view.getTag());

            if (tag.equals(End_Date)) {
                txt_date.setText(dateselect);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_generate_invoice);

        //        set observer for callback
        EotApp.getAppinstance().setInvoiceItemObserver(this);

        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_invoice));

        actionBar = getSupportActionBar();

        getSupportActionBar().setElevation(0);  //remove shadow

        try {
            Bundle bundle = getIntent().getExtras();
            if (getIntent().hasExtra("JobId")) {
                jobId = bundle.getString("JobId");
            }
            if (getIntent().hasExtra("isJobInvoiced")) {
                isJobInvoiced = bundle.getString("isJobInvoiced");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        getTaxDisType(jobId);
        initializelables();
        intialize_UI_Views();
    }
    /**After discussion with Rani change validation of canInvoiceCreated by isJobInvoiced 12/04/2024**/
    private void getTaxDisType(String jobId){
        if(jobId != null && !jobId.equals("")){
           mjob = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(jobId);
            if(mjob.getIsJobInvoiced().equals("1")) {
                getDisCalculationType = AppDataBase.getInMemoryDatabase(this).jobModel().disCalculationType(jobId);
                getTaxCalculationType = AppDataBase.getInMemoryDatabase(this).jobModel().taxCalculationType(jobId);
            }else {
                getDisCalculationType= App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
                getTaxCalculationType= App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType();
            }
        }else{
            getDisCalculationType= App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
            getTaxCalculationType= App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType();
        }
    }
    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void setLocationTaxsList(final List<TaxesLocation> taxList) {
        this.taxList = taxList;
        AppUtility.spinnerPopUpWindow(loc_tax_dp);
        loc_tax_dp.setAdapter(new TaxLocAdpter(this, taxList));

        loc_tax_dp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showLocationDialog(taxList.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setInvoiceTmpList(ArrayList<InvoiceEmaliTemplate> templateList) {
        this.templateList = templateList;
    }

    private void showLocationDialog(final TaxesLocation taxesLocation) {
        AppUtility.alertDialog2(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.are_you_sure)
                , LanguageController.getInstance().getMobileMsgByKey(AppConstant.loc_tax_msg)

                , LanguageController.getInstance().getMobileMsgByKey(AppConstant.yes), LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel), new Callback_AlertDialog() {
                    @Override
                    public void onPossitiveCall() {
                        locId = taxesLocation.getLocId();
                        loc_txt.setText(taxesLocation.getLocation());
                        remove_txt_loc.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNegativeCall() {

                    }
                });

    }

    private void initializelables() {
        recyclerView_invoice = findViewById(R.id.recyclerView_invoice);
        swiperefresh = findViewById(R.id.swiperefresh);
        list_item_invoice_count = findViewById(R.id.list_item_invoice_count);
        rm_invice_im = findViewById(R.id.rm_invice_im);
        invoiceFab = findViewById(R.id.invoiceFab);
        linearFabEmail = findViewById(R.id.linearFabEmail);
//        linearFabPrintInvoice = findViewById(R.id.linearFabPrintInvoice);
        linearFabAddNewItem = findViewById(R.id.linearFabAddNewItem);

        backgroundView = findViewById(R.id.backgroundView);
        lay = findViewById(R.id.lay);

        addInvoiceItem_btn = findViewById(R.id.addInvoiceItem_btn);

        invoice_nm = findViewById(R.id.invoice_nm);
        invoice_adrs = findViewById(R.id.invoice_adrs);
        inv_email = findViewById(R.id.inv_email);
        inv_total_amount = findViewById(R.id.inv_total_amount);
        invoice_cre_dt = findViewById(R.id.invoice_cre_dt);
        invoice_due_dt = findViewById(R.id.invoice_due_dt);

        empty_invoice_lay = findViewById(R.id.empty_invoice_lay);
        empty_inv_txt = findViewById(R.id.empty_inv_txt);
        empty_inv_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_not_found));

        tv_fab_email = findViewById(R.id.tv_fab_email);
        tv_fab_email.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.preview_and_send_invoice));
        tv_fab_add_new_item = findViewById(R.id.tv_fab_add_new_item);
        tv_fab_add_new_item.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_new_item));

      /*  tv_fab_print_invoice = findViewById(R.id.tv_fab_print_invoice);
        tv_fab_print_invoice.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.print_invoice));*/

        tv_create_date = findViewById(R.id.tv_create_date);
        tv_create_date.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.invoice_date));      //change when json file updated.

        tv_due_date = findViewById(R.id.tv_due_date);
        tv_due_date.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.due_date));

        cl_parent_calculation = findViewById(R.id.cl_parent_calculation);
        txt_lbl_sub_total = findViewById(R.id.txt_lbl_sub_total);
        txt_lbl_sub_total.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.sub_total));
        txt_sub_total = findViewById(R.id.txt_sub_total);
        txt_lbl_additional_discount = findViewById(R.id.txt_lbl_additional_discount);
        txt_lbl_additional_discount.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.additional_discount));
        txt_additional_discount = findViewById(R.id.txt_additional_discount);
        txt_lbl_additional_discount1 = findViewById(R.id.txt_lbl_additional_discount1);
        txt_lbl_additional_discount1.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.additional_discount));
        txt_additional_discount1 = findViewById(R.id.txt_additional_discount1);
        txt_lbl_tax = findViewById(R.id.txt_lbl_tax);
        txt_lbl_tax.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.extra_items_total));
        txt_tax = findViewById(R.id.txt_tax);
        txt_lbl_total = findViewById(R.id.txt_lbl_total);
        txt_lbl_total.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.total_amount));
        txt_total = findViewById(R.id.txt_total);
        rvShowTax = findViewById(R.id.rv_tax);

        /**this view for shpping item**/
        recyclerView_shippingitem = findViewById(R.id.recyclerView_shippingitem);
    }

    private void intialize_UI_Views() {
/***this for invoice items**/
//        String getDisCalculationType;
//        if (jobId!=null&&!jobId.isEmpty())
//        {
//            getDisCalculationType=AppDataBase.getInMemoryDatabase(this).jobModel().disCalculationType(jobId);
//        }else{
//            getDisCalculationType= App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
//        }
        layoutManager = new LinearLayoutManager(this);
        recyclerView_invoice.setLayoutManager(layoutManager);

        invoice_list_adpter = new GenerateInvoiceItemAdpter(this, new ArrayList<InvoiceItemDataModel>(),
                getDisCalculationType,getTaxCalculationType,this);//, this, this
        recyclerView_invoice.setAdapter(invoice_list_adpter);

/**this view for shipping items***/
        layoutManager = new LinearLayoutManager(this);
        recyclerView_shippingitem.setLayoutManager(layoutManager);
        List<ShippingItem> shippingItemList = new ArrayList<>();
        sipping_adpter = new Sipping_Adpter(this,shippingItemList);
        recyclerView_shippingitem.setAdapter(sipping_adpter);


        /**false nested scrolling when multiple recyclerview used in single view**/
        recyclerView_invoice.setNestedScrollingEnabled(false);
        recyclerView_shippingitem.setNestedScrollingEnabled(false);

        rm_invice_im.setOnClickListener(this);

        invoiceFab.setOnClickListener(this);
        linearFabEmail.setOnClickListener(this);
      //  linearFabPrintInvoice.setOnClickListener(this);
        backgroundView.setOnClickListener(this);
        linearFabAddNewItem.setOnClickListener(this);


        lay.setVisibility(View.VISIBLE);

        pay_txt = findViewById(R.id.pay_txt);
        pay_txt.setOnClickListener(this);


        addInvoiceItem_btn.setOnClickListener(this);


        loc_tax_dp = findViewById(R.id.loc_tax_dp);
        tax_loc_lable = findViewById(R.id.tax_loc_lable);
        loc_txt = findViewById(R.id.loc_txt);
        remove_txt_loc = findViewById(R.id.remove_txt_loc);
        loc_txt.setOnClickListener(this);
        remove_txt_loc.setOnClickListener(this);

        tax_loc_lable.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.loca_tax_based));
        remove_txt_loc.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.remove));
        remove_txt_loc.setVisibility(View.GONE);

        loc_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.select));


        rl = findViewById(R.id.rl);
        if (App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsLocationEnable() != null && App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsLocationEnable().equals("1")) {
            rl.setVisibility(View.VISIBLE);
        } else {
            rl.setVisibility(View.GONE);
        }

        swiperefresh.setEnabled(false);

        /***getInvoiceDetail call karna padegi****/
        itemListPi = new ItemList_PC(this);
        itemListPi.getloctaxexList();
        itemListPi.getJobInvoicetemplateList();
        if (!isJobInvoiced.equals("") && isJobInvoiced.equals("0")) {
            itemListPi.getInvoiceFormobile(jobId);
        } else {
            itemListPi.getinvoicedetails(jobId);
        }

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        setTxtInsideView();

        /***Check Remove Item Permission****/
        if (App_preference.getSharedprefInstance().getLoginRes().getIsItemDeleteEnable().equals("0")) {
            rm_invice_im.setVisibility(View.GONE);
        } else {
            rm_invice_im.setVisibility(View.VISIBLE);
        }

//Show tax
        invoiceTaxAdapter = new InvoiceTaxAdapter(this,new ArrayList<TaxData>());
        rvShowTax.setLayoutManager(new LinearLayoutManager(this));
        rvShowTax.setAdapter(invoiceTaxAdapter);

    }

    @Override
    public void setItemListByJob(final List<InvoiceItemDataModel> itemList) {
        this.tempItemList = itemList;
        if (rm_DataItem.size() > 0) {
            rm_DataItem.clear();
        }
        invoice_list_adpter.updateitemlist(itemList,invoice_Details.getIsAddisDiscBefore());
        setTxtInsideView();
        InvoiceNotFound(itemList.size() == 0);
        dismissPullTorefresh();
    }

    @Override
    public void setInvoiceDetails(InvoiceItemDetailsModel invResModel) {
        this.invoice_Details = invResModel;
        setItemDataList(invResModel);
    }

    private void InvoiceNotFound(boolean enable) {
        if (enable) {
            empty_invoice_lay.setVisibility(View.VISIBLE);
            recyclerView_invoice.setVisibility(View.GONE);
            empty_inv_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_invoice));
        } else {
            empty_invoice_lay.setVisibility(View.GONE);
            recyclerView_invoice.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMyListitemSeleted(InvoiceItemDataModel itemData) {
        editInvoiceItem(itemData);
    }


    @Override
    public void onMyListitem_Item_Seleted(ArrayList<InvoiceItemDataModel> itemDataRemove) {
        rm_DataItem = itemDataRemove;
        if (rm_DataItem.size() > 0) {
            rm_invice_im.setEnabled(true);
            rm_invice_im.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            rm_invice_im.setEnabled(false);
            rm_invice_im.setColorFilter(ContextCompat.getColor(this, R.color.txt_color));
        }
    }

    private void showLocationData(String locId) {
        TaxesLocation taxesLocation = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).taxesLocationDao().getTaxLocationByid(locId);
        loc_txt.setText(taxesLocation.getLocation());
        remove_txt_loc.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItemDataList(InvoiceItemDetailsModel invResModel) {
        try {
            this.invoice_Details = invResModel;
            this.itemData_Details = invResModel.getItemData();

            try {
                if (invResModel != null && invResModel.getLocId() != null) {
                    locId = invResModel.getLocId();
                    if (locId != null && !locId.equals("0")) {
                        showLocationData(locId);
                    } else if (App_preference.getSharedprefInstance().getLoginRes().getLocId() != null && !App_preference.getSharedprefInstance().getLoginRes().getLocId().equals("0")) {
                        locId = App_preference.getSharedprefInstance().getLoginRes().getLocId();
                        showLocationData(locId);
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            /**** *Sort Item By name***/
            if (invResModel.getItemData() != null) {
                Collections.sort(invResModel.getItemData(), new Comparator<InvoiceItemDataModel>() {
                    @Override
                    public int compare(InvoiceItemDataModel o1, InvoiceItemDataModel o2) {
                        return o1.getInm().compareTo(o2.getInm());
                    }
                });
            }

            if (rm_DataItem.size() > 0) {
                rm_DataItem.clear();
            }
            setInvoiceitemDetails(this.invoice_Details);
            sipping_adpter.updateShpiningItem(invoice_Details.getShippingItem(), App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType());
            rm_invice_im.setColorFilter(getResources().getColor(R.color.txt_color));
            rm_invice_im.setEnabled(false);
            setTxtInsideView();
            /*if (this.itemData_Details.size() == 0) {
                InvoiceNotFound("empty invoice");
            } else {
                setItemListByJob(invResModel.getItemData());
            }*/ 

            // this changes for labour item in invoice
            Job job = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(invResModel.getJobId());
            try {
                if (job.getItemData() != null && job.getItemData() != null && job.getItemData().size() > 0) {
                    setItemListByJob(job.getItemData());
                } else {
                    InvoiceNotFound("empty invoice");
                }
            } catch (Exception ex) {
                ex.getMessage();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void dismissPullTorefresh() {
        if (swiperefresh.isRefreshing()) {
            swiperefresh.setRefreshing(false);
        }
    }

    @Override
    public void InvoiceNotFound(String msg) {
        empty_invoice_lay.setVisibility(View.VISIBLE);
        empty_inv_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_not_found));
        show_Dialog(msg);
    }


    @Override
    public void errorActivityFinish(String msg) {
        AppUtility.alertDialog2(this, "", msg,
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok)
                , "", new Callback_AlertDialog() {
                    @Override
                    public void onPossitiveCall() {
                        finishActivity();
                    }

                    @Override
                    public void onNegativeCall() {

                    }
                });
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

    private void setInvoiceitemDetails(InvoiceItemDetailsModel invResModel) {
        invResModelForDueDate =invResModel;
        if (invResModel != null && invResModel.getInvId() != null) {
            invId = invResModel.getInvId();
        }

        client_address_model = new Gson().fromJson(invResModel.getInvClientAddress(), Inv_Client_Address_model.class);
        if (client_address_model != null) {
            invoice_nm.setText(client_address_model.getNm());
            String clientAddress = "";
            if (client_address_model.getAdr() != null && !client_address_model.getAdr().equals(""))
                clientAddress = client_address_model.getAdr() + "\n";
            if (client_address_model.getCity() != null && !client_address_model.getCity().equals(""))
                clientAddress = clientAddress + client_address_model.getCity() + "\n";
            if (client_address_model.getCountry() != null && !client_address_model.getCountry().equals(""))
                clientAddress = clientAddress + client_address_model.getCountry() + "\n";
            if (client_address_model.getMob() != null && !client_address_model.getMob().equals(""))
                clientAddress = clientAddress + client_address_model.getMob() + "\n";
            if (client_address_model.getGst() != null && client_address_model.getGst() != null && !client_address_model.getGst().isEmpty())
                clientAddress = clientAddress + client_address_model.getGst();

            invoice_adrs.setText(clientAddress);
        }

        inv_total_amount.setText(AppUtility.getRoundoff_amount(invResModel.getTotal() + ""));

        if (!invResModel.getInvDate().equals(""))
            invoice_cre_dt.setText(AppUtility.getDateWithFormate(Long.parseLong(invResModel.getInvDate()), "dd-MMM-yyyy"));
        if (!invResModel.getDuedate().equals(""))
            invoice_due_dt.setText(AppUtility.getDateWithFormate(Long.parseLong(invResModel.getDuedate()), "dd-MMM-yyyy"));
    }

    private String getSpiningTotal(String total) {
        double total_amount = Double.parseDouble(total);
        for (ShippingItem sppingitem : invoice_Details.getShippingItem()) {
            total_amount = total_amount + Double.parseDouble(AppUtility.getRoundoff_amount(sppingitem.getRate()));
        }
        return String.valueOf(total_amount);
    }

    @Override
    public void onSessionExpire(String msg) {
        AppUtility.alertDialog(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title), msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                EotApp.getAppinstance().sessionExpired();
                return null;
            }
        });
    }

    private void show_Dialog(String msg) {
        AppUtility.error_Alert_Dialog(this, msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok)
                , new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return null;
                    }
                });
    }

    private void removeApplyTaxtion() {
        locId = "0";
        loc_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.select));
        remove_txt_loc.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        String format="";
        switch (view.getId()) {
            case R.id.remove_txt_loc:
                removeApplyTaxtion();
                break;
            case R.id.loc_txt:
                if (taxList != null && taxList.size() > 0)
                    loc_tax_dp.performClick();
                else
                    show_Dialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.taxLoc_not_found));
                break;
            case R.id.pay_txt:
                pay_dialog();
                break;

            case R.id.rm_invice_im:
                if (rm_DataItem.size() > 0) {
                    removeSelectedItem();
                } else {
                    show_Dialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.remove_item_mandtry));
                }
                break;
            case R.id.invoiceFab:
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
                break;
            case R.id.linearFabEmail:
               /* if (invoice_Details != null) {
                    Intent emailIntent = new Intent(Generate_Invoice_Activity.this, Invoice_Email_Activity.class);
                    emailIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    emailIntent.putExtra("invId", invoice_Details.getInvId());
                    emailIntent.putExtra("compId", invoice_Details.getCompId());
                    emailIntent.putExtra("isShowInList", invoice_Details.getIsShowInList());
                    startActivity(emailIntent);
                }
                closeFABMenu();
                break;
            case R.id.linearFabPrintInvoice:
                if (templateList != null && !templateList.isEmpty() && templateList.size()>1) {
                    dialogJobCardDocuments = new DialogJobCardDocuments();
                    dialogJobCardDocuments.setContext(this);
                    dialogJobCardDocuments.setitemListPi(itemListPi);
                    dialogJobCardDocuments.setinvoice_Details(invoice_Details);
                    dialogJobCardDocuments.setInvoiceTmpList(templateList);
                    dialogJobCardDocuments.show(getSupportFragmentManager(), "dialog");
                    closeFABMenu();
                } else {
                    String tempId="";
                    if(templateList != null && !templateList.isEmpty() && templateList.size()==1){
                        tempId=templateList.get(0).getInvTempId();
                    }
                    linearFabPrintInvoice.setClickable(false);
                    if (invoice_Details != null) {
                        String isProformaInv = "0";
                        if (invoice_Details.getIsShowInList() != null && invoice_Details.getIsShowInList().equals("0"))
                            isProformaInv = "1";
                        else isProformaInv = "0";
                        itemListPi.getGenerateInvoicePdf(invoice_Details.getInvId(), isProformaInv,tempId);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            linearFabPrintInvoice.setClickable(true);
                        }
                    }, 500);
                    closeFABMenu();
                }*/
                 /** This change in ui for preview and email**/
                if (invoice_Details != null) {
                    if(templateList!=null && !templateList.isEmpty()){
                        toJsonTemplate = new Gson().toJson(templateList);
                    }
                    //Intent emailIntent = new Intent(Generate_Invoice_Activity.this, Invoice_Email_Activity.class);
                    Intent emailIntent = new Intent(Generate_Invoice_Activity.this, JobCardViewActivity.class);
                    emailIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    emailIntent.putExtra("invId", invoice_Details.getInvId());
                    emailIntent.putExtra("compId", invoice_Details.getCompId());
                    emailIntent.putExtra("jobId", invoice_Details.getJobId());
                    emailIntent.putExtra("invoiceDetail",invoice_Details);
                    emailIntent.putExtra("templateList",toJsonTemplate);
                    emailIntent.putExtra("isShowInList", invoice_Details.getIsShowInList());
                    if(mjob.getKpr()!=null)
                    {
                        String[] fwList2=mjob.getKpr().split(",");
                        emailIntent.putExtra("FwList",fwList2);
                    }
                    startActivity(emailIntent);
                }
                closeFABMenu();
                break;
        /*    case R.id.linearFabPrintInvoice:
                if (templateList != null && !templateList.isEmpty() && templateList.size()>1) {
                    dialogJobCardDocuments = new DialogJobCardDocuments();
                    dialogJobCardDocuments.setContext(this);
                    dialogJobCardDocuments.setitemListPi(itemListPi);
                    dialogJobCardDocuments.setinvoice_Details(invoice_Details);
                    dialogJobCardDocuments.setInvoiceTmpList(templateList);
                    dialogJobCardDocuments.show(getSupportFragmentManager(), "dialog");
                    closeFABMenu();
                } else {
                    String tempId="";
                    if(templateList != null && !templateList.isEmpty() && templateList.size()==1){
                        tempId=templateList.get(0).getInvTempId();
                    }
                    linearFabPrintInvoice.setClickable(false);
                    if (invoice_Details != null) {
                        String isProformaInv = "0";
                        if (invoice_Details.getIsShowInList() != null && invoice_Details.getIsShowInList().equals("0"))
                            isProformaInv = "1";
                        else isProformaInv = "0";
//                        itemListPi.getGenerateInvoicePdf(invoice_Details.getInvId(), isProformaInv,tempId);
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            linearFabPrintInvoice.setClickable(true);
                        }
                    }, 500);
                    closeFABMenu();
                }
                break;*/
            case R.id.backgroundView:
                closeFABMenu();
                break;
            case R.id.linearFabAddNewItem:
                addInvoiceItem();
                closeFABMenu();
                break;

            case R.id.date_end:
                SelectStartDate(End_Date);
                break;
            case R.id.cancel:
                dialog.dismiss();
                break;
            case R.id.ok_btn:
                if (!conditionCheck(AppUtility.getDateWithFormate(Long.parseLong(invResModelForDueDate.getInvDate()), "dd-MMM-yyyy"), txt_date.getText().toString())) {
                    EotApp.getAppinstance().
                            showToastmsg(LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_due_Invoice_date));
                } else {
                    invoice_due_dt.setText(txt_date.getText().toString());
                    dialog.dismiss();
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        Date date = dateFormat.parse(txt_date.getText().toString());
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
                        if (App_preference.getSharedprefInstance().getLoginRes().getIsAutoTimeZone().equals("1")) {
                            dateFormat1.setTimeZone(TimeZone.getTimeZone(App_preference.getSharedprefInstance().getLoginRes().getLoginUsrTz()));
                        } else {
                            dateFormat1.setTimeZone(TimeZone.getDefault());
                        }
                        String dueDateFormat = dateFormat1.format(date);
                        itemListPi.setDueDate(invResModelForDueDate.getInvId(), dueDateFormat);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
    private boolean conditionCheck(String schdlStart, String schdlFinish) {
        try {
            SimpleDateFormat gettingfmt = new SimpleDateFormat(AppConstant.DATE_FORMAT, Locale.US);//hh:mm:s a
            Date date = gettingfmt.parse(schdlStart);
            Objects.requireNonNull(date).getTime();
            Date date1 = gettingfmt.parse(schdlFinish);
            Objects.requireNonNull(date1).getTime();
            if (date1.getTime() > date.getTime() || date1.getTime() == date.getTime())
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private void SelectStartDate(String endDate) {
        Calendar myCalendar = Calendar.getInstance();
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Generate_Invoice_Activity.this, datePickerListener, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setTag(endDate);
        datePickerDialog.show();
    }

    private void addInvoiceItem() {
        Intent intent = new Intent(this, AddEditInvoiceItemActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("invId", invId);
        intent.putExtra("jobId", jobId);
        intent.putExtra("locId", locId);
        intent.putExtra("addItemOnInvoice", true);
        intent.putExtra("NONBILLABLE", true);
        intent.putExtra("getTaxMethodType", invoice_Details.getIsAddisDiscBefore());
        intent.putExtra("getSingleTaxId", SingleTaxId);
        startActivityForResult(intent, ADD_ITEM_DATA);
    }

    private void pay_dialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.pay_dialog_invoice);
        pay_btn_inv = dialog.findViewById(R.id.pay_btn_inv);
        pay_edt_partial = dialog.findViewById(R.id.pay_edt_partial);

        final TextView full_amount = dialog.findViewById(R.id.full_amount);
        final TextView partial_amount = dialog.findViewById(R.id.partial_amount);


        pay_btn_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (islang) {
                    EotApp.getAppinstance().setLocalLanguage("hi");
                } else {
                    EotApp.getAppinstance().setLocalLanguage("en");
                }
                islang = !islang;
                full_amount.setText(EotApp.getAppinstance().getString("pay_full_amount"));
                partial_amount.setText(EotApp.getAppinstance().getString("pay_partial_amount"));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setTxtInsideView() {
        int count = invoice_list_adpter.getItemCount();
        totalItemSize = count;
        list_item_invoice_count.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.list_item) + " (" + count + ")");

    }

    private void editInvoiceItem(InvoiceItemDataModel invoiceItemDataModel) {
        Intent intent = new Intent(this, AddEditInvoiceItemActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("InvoiceItemDataModel", invoiceItemDataModel);
        intent.putExtra("invId", invId);
        intent.putExtra("edit_jobId", jobId);
        intent.putExtra("locId", locId);
        intent.putExtra("addItemOnInvoice", true);
        intent.putExtra("NONBILLABLE", true);
        intent.putExtra("getTaxMethodType", invoice_Details.getIsAddisDiscBefore());
        intent.putExtra("getSingleTaxId", SingleTaxId);
        startActivityForResult(intent, ADD_ITEM_DATA);
    }

    private void removeSelectedItem() {

        AppUtility.alertDialog2(this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.invoice_remove),
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok),
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel), new Callback_AlertDialog() {
                    @Override
                    public void onPossitiveCall() {
                        try {
                            List<InvoiceItemDataModel> notSyncItemList = new ArrayList<>();

                            ArrayList<String> ijmmIdList = new ArrayList<>();

                            if (totalItemSize > rm_DataItem.size()) {
                                for (InvoiceItemDataModel str : rm_DataItem) {
                                    if (str.getIjmmId().equals("")) {
                                        tempItemList.remove(str);
                                        notSyncItemList.add(str);
                                    } else {
                                        tempItemList.remove(str);
                                        ijmmIdList.add(str.getIjmmId());
                                    }
                                }
                                rm_invice_im.setEnabled(false);
                                itemListPi.updareRmitemsInDB(jobId, tempItemList, ijmmIdList, notSyncItemList, true);
                            } else {
                                show_Dialog(
                                        LanguageController.getInstance().getMobileMsgByKey(AppConstant.remove_item_mandtry)
                                );
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    }

                    @Override
                    public void onNegativeCall() {

                    }
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        } else {
            if (requestCode == ADD_ITEM_DATA) {
                if (data.hasExtra("AddInvoiceItem")) {
                    itemListPi.getinvoicedetails(jobId);
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quotes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quotes_edit:
                getDueDateDailog();
                break;
            case android.R.id.home:
                onBackPressed();
                AppUtility.hideSoftKeyboard(this);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void getDueDateDailog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_generate_invoice_due_date);
        TextView invoiceDueDate = dialog.findViewById(R.id.invoiceDueDate);
        invoiceDueDate.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.invoice_Due_Date));
        dueDate=dialog.findViewById(R.id.date_end);
        txt_ok = dialog.findViewById(R.id.ok_btn);
        txt_ok.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok));
        txt_cancel = dialog.findViewById(R.id.cancel);
        txt_cancel.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel));
        txt_date = dialog.findViewById(R.id.date_taxt);
        txt_date.setText(AppUtility.getDateWithFormate(Long.parseLong(invResModelForDueDate.getDuedate()), "dd-MMM-yyyy"));
        dueDate.setOnClickListener(this);
        txt_ok.setOnClickListener(this);
        txt_cancel.setOnClickListener(this);
        dialog.show();
    }

    private void showFABMenu() {
        isFABOpen = true;
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg_color)));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#90ffffff")));
        backgroundView.setVisibility(View.VISIBLE);
        linearFabEmail.setVisibility(View.VISIBLE);
//        linearFabPrintInvoice.setVisibility(View.VISIBLE);


        for (FooterMenu serverList : App_preference.getSharedprefInstance().getLoginRes().getFooterMenu()) {
            // both fw and mobile app permission should be granted
            if (serverList.isEnable.equals("1"))
                if ("set_itemMenuOdrNo".equals(serverList.getMenuField())) {
                    if (App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsItemVisible() == 0
                            &&
                            App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsItemEnable().equals("0")) {
                        linearFabAddNewItem.setVisibility(View.VISIBLE);
                        linearFabEmail.animate().translationY(getResources().getDimension(R.dimen.standard_100));
//                      linearFabPrintInvoice.animate().translationY(getResources().getDimension(R.dimen.standard_100));
                        linearFabAddNewItem.animate().translationY(getResources().getDimension(R.dimen.standard_55));
                    } else {
                        linearFabAddNewItem.setVisibility(View.GONE);
                        linearFabEmail.animate().translationY(getResources().getDimension(R.dimen.standard_55));
//                        linearFabPrintInvoice.animate().translationY(getResources().getDimension(R.dimen.standard_55));
                    }
                }
        }


    }

    private void closeFABMenu() {
        isFABOpen = false;
        linearFabEmail.animate().translationY(0);
        linearFabAddNewItem.animate().translationY(0);

        linearFabEmail.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    backgroundView.setVisibility(View.GONE);
                    linearFabEmail.setVisibility(View.GONE);
//                    linearFabPrintInvoice.setVisibility(View.GONE);
                    linearFabAddNewItem.setVisibility(View.GONE);
                    actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    @Override
    public void onObserveCallBack(String totalAmount) {
        if (itemListPi != null) {
            inv_total_amount.setText(AppUtility.getRoundoff_amount(totalAmount + ""));
            txt_total.setText(AppUtility.getRoundoff_amount(""+totalAmount));
        }
    }
    Double totalOfShippingItem = 0.0;
    String SingleTaxId="0";

    @Override
    public void setCalculation(Double Subtotal, List<TaxData> listTax,boolean isShippingData,String SingleTaxId) {
        String additionalDiscount =invoice_Details.getDiscount();

        if(totalItemSize==0){
            cl_parent_calculation.setVisibility(View.GONE);
        }else {
            cl_parent_calculation.setVisibility(View.VISIBLE);
        }
        if(isShippingData){
            totalOfShippingItem = Subtotal;
        }

        if(invoice_Details.getIsAddisDiscBefore().equals("0")) {
            Log.e("Invoice additional type", "after "+invoice_Details.getIsAddisDiscBefore());
            txt_additional_discount.setVisibility(View.GONE);
            txt_lbl_additional_discount.setVisibility(View.GONE);
            txt_additional_discount1.setVisibility(View.VISIBLE);
            txt_lbl_additional_discount1.setVisibility(View.VISIBLE);
            rvShowTax.setVisibility(View.VISIBLE);
            if (additionalDiscount.equals("0.0000") || additionalDiscount.equals("")) {
                txt_lbl_additional_discount1.setVisibility(View.GONE);
                txt_additional_discount1.setVisibility(View.GONE);
            } else {
                txt_additional_discount1.setText("-" + AppUtility.getRoundoff_amount(additionalDiscount));
            }
            if(totalOfShippingItem == 0){
                txt_lbl_tax.setVisibility(View.GONE);
                txt_tax.setVisibility(View.GONE);
            }else {
                txt_tax.setText(AppUtility.getRoundoff_amount(""+totalOfShippingItem));
                txt_lbl_tax.setVisibility(View.VISIBLE);
                txt_tax.setVisibility(View.VISIBLE);
            }
            if(listTax.size()<= 0){
                rvShowTax.setVisibility(View.GONE);
            }else {
                rvShowTax.setVisibility(View.VISIBLE);
            }
            txt_sub_total.setText(AppUtility.getRoundoff_amount("" + Subtotal));
            invoiceTaxAdapter.setList(listTax);
            String total = String.valueOf(Double.parseDouble(invoice_Details.getTotal()));
            txt_total.setText(AppUtility.getRoundoff_amount(total));
            inv_total_amount.setText(AppUtility.getRoundoff_amount(total));
        }
        else if(invoice_Details.getIsAddisDiscBefore().equals("1")){
            Log.e("Invoice additional type", "before "+invoice_Details.getIsAddisDiscBefore());
            this.SingleTaxId = SingleTaxId;
            Double taxRate = 0.0;
            txt_additional_discount.setVisibility(View.VISIBLE);
            txt_lbl_additional_discount.setVisibility(View.VISIBLE);
            txt_additional_discount1.setVisibility(View.GONE);
            txt_lbl_additional_discount1.setVisibility(View.GONE);
            rvShowTax.setVisibility(View.VISIBLE);
            if (additionalDiscount.equals("0.0000") || additionalDiscount.equals("")) {
                txt_lbl_additional_discount.setVisibility(View.GONE);
                txt_additional_discount.setVisibility(View.GONE);
            } else {
                txt_additional_discount.setText("-" + AppUtility.getRoundoff_amount(additionalDiscount));
            }
            if(totalOfShippingItem == 0){
                txt_lbl_tax.setVisibility(View.GONE);
                txt_tax.setVisibility(View.GONE);
            }else {
                txt_tax.setText(AppUtility.getRoundoff_amount(""+totalOfShippingItem));
                txt_lbl_tax.setVisibility(View.GONE);
                txt_tax.setVisibility(View.GONE);
            }
            txt_sub_total.setText(AppUtility.getRoundoff_amount("" + Subtotal));
            List<TaxData> showTaxList = new ArrayList<>();
            for (TaxData tax: listTax
            ) {
                taxRate = tax.getRate();
                TaxData taxData = new TaxData();
                taxData.setRate(taxRate);
                taxData.setTaxAmount(tax.getTaxAmount());
                taxData.setLabel(tax.getLabel());
                showTaxList.add(taxData);
            }
            Double total = Subtotal - Double.parseDouble(additionalDiscount);;
            Double taxTotal = 0.0;
            for (TaxData changeAmt : showTaxList){
                Double taxAmount = total*changeAmt.getRate()/100;
                changeAmt.setTaxAmount(taxAmount);
                taxTotal = taxTotal +taxAmount;
            }
            invoiceTaxAdapter.setList(showTaxList);
            total = total+taxTotal+totalOfShippingItem;
            txt_total.setText(AppUtility.getRoundoff_amount(""+total));
            inv_total_amount.setText(AppUtility.getRoundoff_amount(""+total));
        }

    }
}






