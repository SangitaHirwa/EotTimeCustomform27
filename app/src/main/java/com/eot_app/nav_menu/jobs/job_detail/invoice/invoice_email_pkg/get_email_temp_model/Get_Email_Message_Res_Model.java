package com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model;

import com.eot_app.nav_menu.appointment.Keepar;

import java.util.List;

public class Get_Email_Message_Res_Model {

    public String jobId;
    public String cltId;
    public String label;
    public String schdlStart;
    public String schdlFinish;
    public String kpr;
    public String type;
    public String status;
    public String  compid;
    public String nm;
    List<Keepar> keeper;
    List<Get_ChatUserList_Model> chatUser;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCltId() {
        return cltId;
    }

    public void setCltId(String cltId) {
        this.cltId = cltId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSchdlStart() {
        return schdlStart;
    }

    public void setSchdlStart(String schdlStart) {
        this.schdlStart = schdlStart;
    }

    public String getSchdlFinish() {
        return schdlFinish;
    }

    public void setSchdlFinish(String schdlFinish) {
        this.schdlFinish = schdlFinish;
    }

    public String getKpr() {
        return kpr;
    }

    public void setKpr(String kpr) {
        this.kpr = kpr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompid() {
        return compid;
    }

    public void setCompid(String compid) {
        this.compid = compid;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public List<Keepar> getKeeper() {
        return keeper;
    }

    public void setKeeper(List<Keepar> keeper) {
        this.keeper = keeper;
    }

    public List<Get_ChatUserList_Model> getChatUser() {
        return chatUser;
    }

    public void setChatUser(List<Get_ChatUserList_Model> chatUser) {
        this.chatUser = chatUser;
    }
}
