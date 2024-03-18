package com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp;

import com.eot_app.nav_menu.audit.audit_list.equipment.remark.remark_mvp.RemarkRequest;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.chat.fire_Base_Model.Chat_Send_Msg_Model;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by Sonam-11 on 22/9/20.
 */
public interface JobEquRemark_PI {
    void getCustomFormList(final Job equipmentRes, final ArrayList<String> jtId);

    void addNewRemark(RemarkRequest remarkRequest, String file, List<MultipartBody.Part> docAns, ArrayList<String> docQueIdArrays,
                      List<MultipartBody.Part> signAns, ArrayList<String> signQueIdArrays,
                      boolean isAutoUpdatedRemark,String equStatusId);

    void getEquipmentList(String type, String cltId, String audId,String isParent);

    void getItemFromServer(final String jobId);
    void getItemListByJobFromDB(String jobId);
    void getRequestedItemDataList(String jobId);
    void deleteRequestedItem(String irId, String jobId, AddUpdateRequestedModel requestedModel);
    void sendMsg(Chat_Send_Msg_Model chat_send_Msg_model);

}
