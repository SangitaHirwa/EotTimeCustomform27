package com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp;

import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.customform.cstm_form_model.CustomFormList_Res;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.RequestedItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sonam-11 on 22/9/20.
 */
public interface JobEquRemark_View {
    void onSessionExpire(String message);
    void setItemListByJob(List<InvoiceItemDataModel> itemList);

    void onRemarkUpdate(String message,InvoiceItemDataModel updateItemDataModel);

    void onErrorMsg(String msg);

    void setList(ArrayList<CustomFormList_Res> customFormLists);
    void setEquipmentList(List<EquArrayModel> customFormLists);

    void formNotFound();

    void finishErroroccur();
    void setRequestItemData(List<RequestedItemModel> requestItemData);
    void notDtateFoundInRequestedItemList(String msg);
    void deletedRequestData(String msg, AddUpdateRequestedModel requestedModel);
    void setRepairStatus();
    void setDiscardStatus();

}
