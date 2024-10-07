package com.eot_app.nav_menu.jobs.job_detail.job_equipment.model;

import com.eot_app.nav_menu.audit.audit_list.equipment.remark.remark_mvp.RemarkRequest;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.ans_model.Ans_Req;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RemarkModel_Offline extends Ans_Req {

    RemarkRequest remarkRequest;
    String file;
    List<String> dosanspath;
    List<String> signanspath;
    ArrayList<String> signQueIdArray;
    ArrayList<String> docQueIdArray;



    public RemarkModel_Offline(RemarkRequest remarkRequest, List<String> docAns, List<String> signAns, ArrayList<String> signQueIdArray, ArrayList<String> docQueIdArray, String file) {
        this.remarkRequest=remarkRequest;
        this.dosanspath = docAns;
        this.signanspath = signAns;
        this.signQueIdArray = signQueIdArray;
        this.docQueIdArray = docQueIdArray;
        this.file = file;
    }

    public RemarkRequest getAns_req() {
        return remarkRequest;
    }

    public void setAns_req(RemarkRequest remarkRequest) {
        this.remarkRequest = remarkRequest;
    }

    public List<String> getDosanspath() {
        return dosanspath;
    }

    public void setDosanspath(List<String> dosanspath) {
        this.dosanspath = dosanspath;
    }

    public List<String> getSignanspath() {
        return signanspath;
    }

    public void setSignanspath(List<String> signanspath) {
        this.signanspath = signanspath;
    }

    public ArrayList<String> getSignQueIdArray() {
        return signQueIdArray;
    }

    public void setSignQueIdArray(ArrayList<String> signQueIdArray) {
        this.signQueIdArray = signQueIdArray;
    }

    public ArrayList<String> getDocQueIdArray() {
        return docQueIdArray;
    }

    public void setDocQueIdArray(ArrayList<String> docQueIdArray) {
        this.docQueIdArray = docQueIdArray;
    }
    public RemarkRequest getRemarkRequest() {
        return remarkRequest;
    }

    public void setRemarkRequest(RemarkRequest remarkRequest) {
        this.remarkRequest = remarkRequest;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
