package com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.get_email_temp_model;

import java.util.List;

public class Get_Email_Message_Req_Modle {
    public String jobId;
    public String cltId;
    public String compId;
    public String cnm;
    public String label;
    public String sheduleEnd;
    public String staticJobId;
    List<String> usrId;
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

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCnm() {
        return cnm;
    }

    public void setCnm(String cnm) {
        this.cnm = cnm;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSheduleEnd() {
        return sheduleEnd;
    }

    public void setSheduleEnd(String sheduleEnd) {
        this.sheduleEnd = sheduleEnd;
    }

    public String getStaticJobId() {
        return staticJobId;
    }

    public void setStaticJobId(String staticJobId) {
        this.staticJobId = staticJobId;
    }

    public List<String> getUsrId() {
        return usrId;
    }

    public void setUsrId(List<String> usrId) {
        this.usrId = usrId;
    }

    public List<Get_ChatUserList_Model> getChatUser() {
        return chatUser;
    }

    public void setChatUser(List<Get_ChatUserList_Model> chatUser) {
        this.chatUser = chatUser;
    }
}
