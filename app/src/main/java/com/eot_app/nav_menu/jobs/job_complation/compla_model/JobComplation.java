package com.eot_app.nav_menu.jobs.job_complation.compla_model;

import com.eot_app.nav_menu.jobs.job_db.IsMarkDoneConvrtr;
import com.eot_app.nav_menu.jobs.job_db.IsMarkDoneWithJtid;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.ans_model.Answer;
import com.eot_app.utility.App_preference;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by Sonam-11 on 2020-02-04.
 */
public class JobComplation {

    String jobId;
    String usrId;
    String complNote;
    List<Answer> answerArray;
    List<Answer> signQueIdArray;
    List<Answer> docQueIdArray;
    List<String> signAnsPath;
    List<String> docAnsPath;
    List<IsMarkDoneWithJtid> markDoneWithJtids;

    public JobComplation(String jobId, String complNote) {
        this.jobId = jobId;
        this.usrId = App_preference.getSharedprefInstance().
                getLoginRes().getUsrId();
        this.complNote = complNote;
    }

    public JobComplation(String jobId, String complNote, List<Answer> answerArray) {
        this.jobId = jobId;
        this.usrId = App_preference.getSharedprefInstance().
                getLoginRes().getUsrId();
        this.complNote = complNote;
        this.answerArray = answerArray;
    }

    public JobComplation(String jobId, String complNote, List<Answer> answerArray, List<String> signAnsPath, List<String> docAnsPath, List<Answer> signQueIdArray, List<Answer> docQueIdArray, List<IsMarkDoneWithJtid> markDoneWithJtids) {
        this.jobId = jobId;
        this.usrId = App_preference.getSharedprefInstance().
                getLoginRes().getUsrId();
        this.complNote = complNote;
        this.answerArray = answerArray;
        this.signAnsPath = signAnsPath;
        this.docAnsPath = docAnsPath;
        this.signQueIdArray = signQueIdArray;
        this.docQueIdArray = docQueIdArray;
        this.markDoneWithJtids = markDoneWithJtids;
    }
    public JobComplation(String jobId, String complNote, List<Answer> answerArray, List<Answer> signQueIdArray, List<Answer> docQueIdArray) {
        this.jobId = jobId;
        this.usrId = App_preference.getSharedprefInstance().
                getLoginRes().getUsrId();
        this.complNote = complNote;
        this.answerArray = answerArray;
        this.signQueIdArray = signQueIdArray;
        this.docQueIdArray = docQueIdArray;
    }

    public List<IsMarkDoneWithJtid> getMarkDoneWithJtids() {
        return markDoneWithJtids;
    }

    public void setMarkDoneWithJtids(List<IsMarkDoneWithJtid> markDoneWithJtids) {
        this.markDoneWithJtids = markDoneWithJtids;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getComplNote() {
        return complNote;
    }

    public void setComplNote(String complNote) {
        this.complNote = complNote;
    }

    public List<Answer> getAnswerArray() {
        return answerArray;
    }

    public void setAnswerArray(List<Answer> answerArray) {
        this.answerArray = answerArray;
    }

    public List<String> getSignAnsPath() {
        return signAnsPath;
    }

    public void setSignAnsPath(List<String> signAnsPath) {
        this.signAnsPath = signAnsPath;
    }

    public List<String> getDocAnsPath() {
        return docAnsPath;
    }

    public void setDocAnsPath(List<String> docAnsPath) {
        this.docAnsPath = docAnsPath;
    }

    public List<Answer> getSignQueIdArray() {
        return signQueIdArray;
    }

    public void setSignQueIdArray(List<Answer> signQueIdArray) {
        this.signQueIdArray = signQueIdArray;
    }

    public List<Answer> getDocQueIdArray() {
        return docQueIdArray;
    }

    public void setDocQueIdArray(List<Answer> docQueIdArray) {
        this.docQueIdArray = docQueIdArray;
    }
}
