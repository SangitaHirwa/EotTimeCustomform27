package com.eot_app.nav_menu.jobs.job_detail.requested_item;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eot_app.R;
import com.eot_app.nav_menu.audit.audit_list.equipment.model.EquipmentStatus;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.AddInvoiceItemReqModel;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.mvp.AddEditInvoiceItem_PC;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.mvp.AddEditInvoiceItem_PI;
import com.eot_app.nav_menu.jobs.job_detail.invoice.Auto_Inventry_Adpter;
import com.eot_app.nav_menu.jobs.job_detail.invoice.inventry_pkg.Inventry_ReS_Model;
import com.eot_app.nav_menu.jobs.job_detail.invoice.inventry_pkg.ItemParts;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.BrandData;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.db.OfflineDataController;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.util_interfaces.Callback_AlertDialog;
import com.eot_app.utility.util_interfaces.MySpinnerAdapter;
import com.eot_app.utility.util_interfaces.NoDefaultSpinner;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.hypertrack.hyperlog.HyperLog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AddUpdateRquestedItemActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener,AddUpdateReqItem_View {

    private AutoCompleteTextView autocomplete_item;
    private EditText edt_item_qty,edt_modelNo;
    private TextInputLayout  item_qty_layout,modelNo_layout,itemlayout;
    private TextView brand_txt;
    private NoDefaultSpinner brand_spinner;
    private LinearLayout linearLayout_brand;
    private AddUpdateReqItem_PI reqItemPi;
    List<BrandData> brandList1 = new ArrayList<>();
    private boolean IS_ITEM_MANDATRY,itemAdd;
    private Button add_edit_item_Btn;
    String[] brand_array =new String[brandList1.size()];
    LinkedHashMap<String,String> brand_mapArray =new LinkedHashMap<>(brandList1.size());
    private String inm,iQty,modelNo, jobId,brand_id,itemId;
    private String equId="";

    private List<Inventry_ReS_Model> itemsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_update_rquested_item_activity);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        if(getIntent().getBooleanExtra("addReqItem",false)){
            itemAdd = true;
            jobId = getIntent().getStringExtra("jobId");
            Objects.requireNonNull(getSupportActionBar()).setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add_request_Item));
        }
        initializelables();
        reqItemPi = new AddUpdateReqItem_PC(this);
        reqItemPi.getInventryItemList();
        reqItemPi.getBrandList();
    }
    private void initializelables() {
        itemlayout = findViewById(R.id.itemlayout);
        autocomplete_item = findViewById(R.id.autocomplete_item);
        autocomplete_item.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.items_name) + " *");
        item_qty_layout = findViewById(R.id.item_qty_layout);
        edt_item_qty = findViewById(R.id.edt_qty);
        brand_txt = findViewById(R.id.brand_txt);
        brand_txt.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand));
        brand_spinner = findViewById(R.id.brand_spinner);
        linearLayout_brand = findViewById(R.id.linearLayout_brand);
        linearLayout_brand.setOnClickListener(this);
        edt_item_qty.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty));
        modelNo_layout = findViewById(R.id.modelNo_layout);
        edt_modelNo = findViewById(R.id.edt_modelNo);
        add_edit_item_Btn = findViewById(R.id.add_edit_item_Btn);
        add_edit_item_Btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.request_Item));
        edt_modelNo.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.model_no));
        AppUtility.spinnerPopUpWindow(brand_spinner);
        brand_spinner.setOnItemSelectedListener(this);

        autocomplete_item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (autocomplete_item.getTag() == null) {
                    Log.e("EmptyTag", "");
                } else {
                    switch (autocomplete_item.getTag().toString()) {
                        case "Item":
                            Log.e("", "");
                            if (editable.length() >= 1) {

                                itemlayout.setHintEnabled(true);
                                IS_ITEM_MANDATRY = true;
                                if (itemsList != null &&
                                        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).invoiceItemDao().getInventryItemList().size() == 0
                                        && editable.length() >= 3) {
                                    if (!AppUtility.isInternetConnected()) {
                                        showDialogForLoadData();
                                    } else {
                                        reqItemPi.getDataFromServer(editable.toString());
                                    }
                                }
                            } else if (editable.length() <= 0) {
                                itemlayout.setHintEnabled(false);
                                IS_ITEM_MANDATRY = false;
                            }
                            assert itemsList != null;
                            if (!itemsList.contains(editable)) {
                                inm = editable.toString();
                            }
                            break;

                    }
                }
                Log.e("", "");
            }
        });

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linearLayout_brand:
                brand_spinner.performClick();
                break;
            case R.id.add_edit_item_Btn:
                if (itemAdd) {
                    itemAdd = false;
                    addReqItem();
                }
        }
    }

    private void addReqItem() {

        AddUpdateRequestedModel addeRequestModel = new AddUpdateRequestedModel(
                autocomplete_item.getText().toString(),brand_id,edt_item_qty.getText().toString(),edt_modelNo.getText().toString(),
                equId,itemId,jobId);
        if(AppUtility.isInternetConnected()){
            reqItemPi.addReqItemApi(addeRequestModel);
        }else {
            String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
            Gson gson = new Gson();
            String addItemReqest = gson.toJson(addeRequestModel);
            HyperLog.i("TAG addRequestItemReqest", new Gson().toJson(addeRequestModel));
            OfflineDataController.getInstance().addInOfflineDB(Service_apis.addItemOnJob, addItemReqest, dateTime);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      String brand_text= brand_array[position];
        brand_txt.setText(brand_text);
        for(Map.Entry entry : brand_mapArray.entrySet()){
            if(entry.getValue().equals(brand_text)){
                brand_id = entry.getKey()+"";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void showDialogForLoadData() {
        AppUtility.alertDialog2(this,
                "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_sync),
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok),
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel), new Callback_AlertDialog() {
                    @Override
                    public void onPossitiveCall() {
                        try {
                            if (AppUtility.isInternetConnected()) {
                                reqItemPi.getDataFromServer(autocomplete_item.getText().toString());
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

    @Override
    public void setItemdata(List<Inventry_ReS_Model> list) {
        Log.e("", "");
        this.itemsList = list;
        Log.v("ItemList::", new Gson().toJson(list));
        AppUtility.autocompletetextviewPopUpWindow(autocomplete_item);
        autocomplete_item.setTag("Item");
        Auto_Inventry_Adpter itemAdapter = new Auto_Inventry_Adpter(this,
                R.layout.custom_item_adpter, (ArrayList<Inventry_ReS_Model>) this.itemsList);
        autocomplete_item.setAdapter(itemAdapter);
        autocomplete_item.setThreshold(3);
        autocomplete_item.setOnItemClickListener((adapterView, view, position, l) -> {
            try {
                Log.v("ItemList::", new Gson().toJson(adapterView.getItemAtPosition(position)));
                setSelectedItemData(((Inventry_ReS_Model) adapterView.getItemAtPosition(position)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void setBrandList(List<BrandData> brandList) {
        Log.e("itemselected", new Gson().toJson(brandList));
         this.brandList1 = brandList;
        if (brandList1 != null) {

            brand_array = new String[brandList1.size()];
            int i = 0;
            for (BrandData brandData : brandList1) {
                brand_array[i] = brandData.getName();
                brand_mapArray.put(brandData.getEbId(),brandData.getName());
                i++;
            }
            if(brand_array.length <= 0){
                brand_array[0] = LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_brand);
            }
            brand_spinner.setAdapter(new MySpinnerAdapter(this, brand_array));
        }
    }

    private void setSelectedItemData(Inventry_ReS_Model itemselected) {

        Log.e("itemselected", new Gson().toJson(itemselected));
        inm = itemselected.getInm();
        iQty = itemselected.getQty();
        autocomplete_item.setText(inm);
        edt_item_qty.setText(iQty);
    }
    private void showDisError(String msg) {
        AppUtility.alertDialog(this, "", msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok),
                "", () -> true);
    }

}