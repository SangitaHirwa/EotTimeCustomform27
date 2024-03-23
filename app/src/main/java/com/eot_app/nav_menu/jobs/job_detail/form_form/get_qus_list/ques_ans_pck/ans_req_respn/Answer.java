package com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.ques_ans_pck.ans_req_respn;

import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.AnswerModel;
import com.eot_app.utility.App_preference;

import java.util.List;

/**
 * Created by ubuntu on 18/9/18.
 */

public class Answer {
    private String jtId;
    private String queId;
    private String type;
    private List<AnswerModel> ans;
    private String frmId;

    public Answer(String jtId, String queId, String type, List<AnswerModel> ans) {
        this.jtId = jtId;
        this.queId = queId;
        this.type = type;
        this.ans = ans;
        this.frmId = App_preference.getSharedprefInstance().getJobCompletionForm().getFrmId();
    }

    public String getJtId() {
        return jtId;
    }

    public void setJtId(String jtId) {
        this.jtId = jtId;
    }

    public String getFrmId() {
        return frmId;
    }

    public void setFrmId(String frmId) {
        this.frmId = frmId;
    }

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AnswerModel> getAns() {
        return ans;
    }

    public void setAns(List<AnswerModel> ans) {
        this.ans = ans;
    }

}
