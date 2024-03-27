package com.eot_app.nav_menu.jobs.job_detail.detail.job_detail_presenter;

import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.chat.fire_Base_Model.Chat_Send_Msg_Model;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.CompletionDetailsPost;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.JobStatusModel;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.JobStatusModelNew;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;

/**
 * Created by aplite_pc302 on 6/25/18.
 */

public interface JobDetail_pi {
    void changeJobStatusAlertInvisible(String jobId, String type, JobStatusModelNew status, String lat, String lng,
                                       String isMailSentToClt,String isLeaderChgKprsStatus, String jobLable ,String jobType);//,String cltMailConfirmEnable);

    String getStatusName(String status);
    boolean isOldStaus(String status_no, String jobId);
    void setJobCurrentStatus(String jobid);
    boolean checkContactHideOrNot();
    JobStatusModelNew getJobStatusObject(String statusId);
    void getCustomFieldQues(String jobId);
    void getQuestByParntId(String formId, String jobId);
    void stopRecurpattern(String jobId);
    void getAttachFileList(String jobId, String usrId, String type);
    void getItemFromServer(final String jobId);
    void getItemListByJobFromDB(String jobId);
    String getImg();
    void getEquipmentList(String auditId);
    void getEquipmentStatus();
    void getJobCompletionDetails(String jobId);
    void addJobCompletionDetails(String jobId, CompletionDetailsPost.CompletionDetail obj,int logType);
    void loadFromServer(String jobId);
    void getRecureDataList(String jobId, String recurType);
    void getRequestedItemDataList(String jobId);
    void deleteRequestedItem(String irId, String jobId, AddUpdateRequestedModel requestedModel);
    void sendMsg(Chat_Send_Msg_Model chat_send_Msg_model);
    void pauseResumeRecurr(Job job,String recurStatus);
}
