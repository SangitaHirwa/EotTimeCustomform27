package com.eot_app.nav_menu.jobs.job_complation.complation_form.model;

import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.AnswerModel;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.OptionModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompletionFormQuestionModel {

    @SerializedName("queId")
    private String queId;
    @SerializedName("parentId")
    private String parentId;
    @SerializedName("parentAnsId")
    private String parentAnsId;
    @SerializedName("frmId")
    private String frmId;
    @SerializedName("des")
    private String des;
    @SerializedName("type")
    private String type;
    @SerializedName("mandatory")
    private String mandatory;
    @SerializedName("frmType")
    private String frmType;
    @SerializedName("internalLabel")
    private String internalLabel;
    @SerializedName("queWidth")
    private String queWidth;
    @SerializedName("linkToLead")
    private String linkToLead;
    @SerializedName("linkToJob")
    private String linkToJob;
    @SerializedName("isShowOnSite")
    private String isShowOnSite;
    @SerializedName("isDisable")
    private String isDisable;
    @SerializedName("isLinkWithService")
    private String isLinkWithService;
    @SerializedName("jtId")
    private String jtId;
    @SerializedName("sysFieldType")
    private String sysFieldType;
    @SerializedName("linkTo")
    private String linkTo;
    @SerializedName("systemField")
    private String systemField;
    @SerializedName("isAnswered")
    private Integer isAnswered;
    @SerializedName("ans")
    private List<AnswerModel> ans;
    @SerializedName("opt")
    private List<OptionModel> opt;
    private int index;

    public String getIsLinkWithService() {
        return isLinkWithService;
    }

    public void setIsLinkWithService(String isLinkWithService) {
        this.isLinkWithService = isLinkWithService;
    }

    public String getSysFieldType() {
        return sysFieldType;
    }

    public void setSysFieldType(String sysFieldType) {
        this.sysFieldType = sysFieldType;
    }

    public String getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(String linkTo) {
        this.linkTo = linkTo;
    }

    public String getSystemField() {
        return systemField;
    }

    public void setSystemField(String systemField) {
        this.systemField = systemField;
    }

    public String getJtId() {
        return jtId;
    }

    public void setJtId(String jtId) {
        this.jtId = jtId;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentAnsId() {
        return parentAnsId;
    }

    public void setParentAnsId(String parentAnsId) {
        this.parentAnsId = parentAnsId;
    }

    public String getFrmId() {
        return frmId;
    }

    public void setFrmId(String frmId) {
        this.frmId = frmId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getFrmType() {
        return frmType;
    }

    public void setFrmType(String frmType) {
        this.frmType = frmType;
    }

    public String getInternalLabel() {
        return internalLabel;
    }

    public void setInternalLabel(String internalLabel) {
        this.internalLabel = internalLabel;
    }

    public String getQueWidth() {
        return queWidth;
    }

    public void setQueWidth(String queWidth) {
        this.queWidth = queWidth;
    }

    public String getLinkToLead() {
        return linkToLead;
    }

    public void setLinkToLead(String linkToLead) {
        this.linkToLead = linkToLead;
    }

    public String getLinkToJob() {
        return linkToJob;
    }

    public void setLinkToJob(String linkToJob) {
        this.linkToJob = linkToJob;
    }

    public String getIsShowOnSite() {
        return isShowOnSite;
    }

    public void setIsShowOnSite(String isShowOnSite) {
        this.isShowOnSite = isShowOnSite;
    }

    public String getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(String isDisable) {
        this.isDisable = isDisable;
    }

    public Integer getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(Integer isAnswered) {
        this.isAnswered = isAnswered;
    }

    public List<AnswerModel> getAns() {
        return ans;
    }

    public void setAns(List<AnswerModel> ans) {
        this.ans = ans;
    }

    public List<OptionModel> getOpt() {
        return opt;
    }

    public void setOpt(List<OptionModel> opt) {
        this.opt = opt;
    }

}

