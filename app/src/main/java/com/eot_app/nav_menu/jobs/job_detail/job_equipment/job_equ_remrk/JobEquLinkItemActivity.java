package com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.AddEditInvoiceItemActivity2;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.ReplaceItemEquipmentActivity;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.customform.cstm_form_model.CustomFormList_Res;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.AddJobEquipMentActivity;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.UpdateJobEquipMentActivity;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.adapter.LinkItemAdapter;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp.JobEquRemark_PC;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp.JobEquRemark_View;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.RequestedItemModel;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class JobEquLinkItemActivity extends AppCompatActivity implements JobEquRemark_View {

    RecyclerView rv_link_item;
    TextView txt_lbl;
    LinkItemAdapter linkItemAdapter;
    JobEquRemark_PC jobEquRemarkPc;
    String jobId, equId, comeFrom, invId, equipmentIdName, equipmentType;
    boolean isAddItem = false, isPartAdd = false;
    InvoiceItemDataModel updateItemDataModel;
    EquArrayModel equipment;
    public  static  JobEquLinkItemActivity jobEquLinkItemActivity;
    public JobEquLinkItemActivity getInstance(){
        return jobEquLinkItemActivity;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobEquLinkItemActivity = this;
        setContentView(R.layout.activity_equ_link_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.link_items));
        jobEquRemarkPc = new JobEquRemark_PC(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {

                // for adding item from equipment or adding equipment part with item
                if (getIntent().hasExtra("comeFrom")) {
                    comeFrom = bundle.getString("comeFrom");
                    equId = bundle.getString("equipmentId");
                    jobId = bundle.getString("edit_jobId");
                    if(comeFrom.equalsIgnoreCase("AddItem")){
                        isAddItem = true;
                    }else if(comeFrom.equalsIgnoreCase("AddRemark")){
                        isPartAdd = true;
                    }
                    /** For replace item to equipment*/
                    if(!isAddItem && !isPartAdd){
                        invId = bundle.getString("invId");
                        String string = bundle.getString("objectStr");
                        updateItemDataModel = new Gson().fromJson(string, InvoiceItemDataModel.class);
                        String strEquipment = getIntent().getExtras().getString("equipment");
                        equipment = new Gson().fromJson(strEquipment, EquArrayModel.class);
                    }else if(isPartAdd){
                        invId = bundle.getString("invId");
                        equipmentIdName = bundle.getString("equipmentIdName");
                        equipmentType = bundle.getString("equipmentType");
                        String string = bundle.getString("objectStr");
                        updateItemDataModel = new Gson().fromJson(string, InvoiceItemDataModel.class);
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
        initializeView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getItemList();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return true;
    }
    public void initializeView(){

        txt_lbl = findViewById(R.id.txt_lbl);
        txt_lbl.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_items_link_current_equipment));
        rv_link_item = findViewById(R.id.rv_link_item);
        linkItemAdapter = new LinkItemAdapter(new ArrayList<>(), this,((ijmmId, invoiceItemDataModel) -> {linkedItem(ijmmId);}));
        rv_link_item.setAdapter(linkItemAdapter);
    }
    public void getItemList(){
        AppUtility.progressBarShow(this);
        jobEquRemarkPc.getLinkItemList(jobId);
    }
    public void linkedItem(String ijmmId){
        jobEquRemarkPc.linkedItemAddToEqu(jobId,equId,ijmmId);
    }
    @Override
    public void onSessionExpire(String message) {

    }

    @Override
    public void setItemListByJob(List<InvoiceItemDataModel> itemList) {
        if(linkItemAdapter != null && itemList.size()>0){
            linkItemAdapter.updateList(itemList);
        }else{
            Toast.makeText(this,"No data found",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRemarkUpdate(String message,InvoiceItemDataModel updateItemDataModel) {
        Toast.makeText(this,LanguageController.getInstance().getServerMsgByKey(message.trim()), Toast.LENGTH_LONG).show();
        if(isAddItem) {
            new AddEditInvoiceItemActivity2().getInstance().finish();
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }else if(isPartAdd){
            new AddEditInvoiceItemActivity2().getInstance().finish();
            Intent intent = new Intent(JobEquLinkItemActivity.this, AddJobEquipMentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra("edit_jobId", jobId);
            intent.putExtra("invId", invId);
            intent.putExtra("comeFrom", comeFrom);
            intent.putExtra("equipmentId", equId);
            intent.putExtra("equipmentIdName", equipmentIdName);
            intent.putExtra("equipmentType", equipmentType);
            intent.putExtra("InvoiceItemDataModel", updateItemDataModel);
            try {
                intent.putExtra("objectStr", new Gson().toJson(updateItemDataModel));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            startActivityForResult(intent, 201);
            finish();
        }else {
            new ReplaceItemEquipmentActivity().getInstance().finish();
            Intent intent = new Intent(JobEquLinkItemActivity.this, UpdateJobEquipMentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            String strEqu = new Gson().toJson(equipment);
            intent.putExtra("edit_jobId", jobId);
            intent.putExtra("invId", invId);
            if(equipment.getIsPart().equalsIgnoreCase("1"))
                intent.putExtra("isPart", "1");
            intent.putExtra("comeFrom", comeFrom);
            intent.putExtra("equipment", strEqu);
            intent.putExtra("equipmentId", equipment.getEquId());
            intent.putExtra("InvoiceItemDataModel", updateItemDataModel);
            try {
                intent.putExtra("objectStr", new Gson().toJson(updateItemDataModel));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onErrorMsg(String msg) {

    }

    @Override
    public void setList(ArrayList<CustomFormList_Res> customFormLists) {

    }

    @Override
    public void setEquipmentList(List<EquArrayModel> customFormLists) {

    }

    @Override
    public void formNotFound() {

    }

    @Override
    public void finishErroroccur() {

    }

    @Override
    public void setRequestItemData(List<RequestedItemModel> requestItemData) {

    }

    @Override
    public void notDtateFoundInRequestedItemList(String msg) {

    }

    @Override
    public void deletedRequestData(String msg, AddUpdateRequestedModel requestedModel) {

    }
}
