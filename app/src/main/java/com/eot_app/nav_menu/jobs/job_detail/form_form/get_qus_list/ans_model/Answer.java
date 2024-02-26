package com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.ans_model;

import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.AnswerModel;
import com.eot_app.utility.App_preference;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ubuntu on 19/9/18.
 */

public class Answer implements Serializable {
    //use for delete useless answers.
    private String encounterRoot;
    private String queId;
    private String type;
    private List<AnswerModel> ans = null;
    private String frmId;
    private String jtId;

    public Answer(String queId, String jtId) {
        this.queId = queId;
        this.jtId = jtId;
    }

    public Answer(String queId, String type, List<AnswerModel> ans) {
        this.queId = queId;
        this.type = type;
        this.ans = ans;
    }

    public Answer(String queId, String type, List<AnswerModel> ans, String frmId) {
        this.queId = queId;
        this.type = type;
        this.ans = ans;
        this.frmId = frmId;
    }

    public Answer(String jtId, String queId, String type, List<AnswerModel> ans) {
        this.jtId = jtId;
        this.queId = queId;
        this.type = type;
        this.ans = ans;
        this.frmId = App_preference.getSharedprefInstance().getJobCompletionForm().getFrmId();
    }

    public String getEncounterRoot() {
        return encounterRoot;
    }

    public void setEncounterRoot(String encounterRoot) {
        this.encounterRoot = encounterRoot;
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

    public String getFrmId() {
        return frmId;
    }

    public void setFrmId(String frmId) {
        this.frmId = frmId;
    }

    public String getJtId() {
        return jtId;
    }

    public void setJtId(String jtId) {
        this.jtId = jtId;
    }
}
