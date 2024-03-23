package com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model;

import androidx.annotation.NonNull;

import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 18/9/18.
 */

public class QuesRspncModel implements Serializable, Cloneable {
    private String queId;
    private String parentId;
    private String parentAnsId;
    private String frmId;
    private String des;
    private String type;
    private String mandatory;
    private boolean flag = false;
    private List<OptionModel> opt = null;
    private List<AnswerModel> ans = null;
    private int index;
    private String frmType;
    private String internalLabel;
    private String queWidth;

    private String linkToLead;

    private String linkToJob;

    private String isShowOnSite;

    private String isDisable;

    private String isLinkWithService;

    private String jtId;

//    private String sysFieldType;

    private String linkTo;

//    private String systemField  = "0";

    private Integer isAnswered;
    private String isMarkAsDone = "0";
    private List<Attachments> attachmentsList = new ArrayList<>();


    public QuesRspncModel() {
    }

    public QuesRspncModel(String queId, String parentId, String parentAnsId, String frmId, String des, String type, String mandatory, boolean flag, List<OptionModel> opt, List<AnswerModel> ans) {
        this.queId = queId;
        this.parentId = parentId;
        this.parentAnsId = parentAnsId;
        this.frmId = frmId;
        this.des = des;
        this.type = type;
        this.mandatory = mandatory;
        this.flag = flag;
        this.opt = opt;
        this.ans = ans;
    }

    public QuesRspncModel(String queId, String parentId, String parentAnsId, String frmId, String des, String type, String mandatory, boolean flag, List<OptionModel> opt, List<AnswerModel> ans, int index, String frmType, String internalLabel, String queWidth, String linkToLead, String linkToJob, String isShowOnSite, String isDisable, String isLinkWithService, String jtId, String sysFieldType, String linkTo, String systemField, Integer isAnswered, List<Attachments> attachmentsList) {
        this.queId = queId;
        this.parentId = parentId;
        this.parentAnsId = parentAnsId;
        this.frmId = frmId;
        this.des = des;
        this.type = type;
        this.mandatory = mandatory;
        this.flag = flag;
        this.opt = opt;
        this.ans = ans;
        this.index = index;
        this.frmType = frmType;
        this.internalLabel = internalLabel;
        this.queWidth = queWidth;
        this.linkToLead = linkToLead;
        this.linkToJob = linkToJob;
        this.isShowOnSite = isShowOnSite;
        this.isDisable = isDisable;
        this.isLinkWithService = isLinkWithService;
        this.jtId = jtId;
//        this.sysFieldType = sysFieldType;
        this.linkTo = linkTo;
//        this.systemField = systemField;
        this.isAnswered = isAnswered;
        this.attachmentsList = attachmentsList;
    }
    public String getIsMarkAsDone() {
        return isMarkAsDone;
    }

    public void setIsMarkAsDone(String isMarkAsDone) {
        this.isMarkAsDone = isMarkAsDone;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
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

    public List<OptionModel> getOpt() {
        return opt;
    }

    public void setOpt(List<OptionModel> opt) {
        this.opt = opt;
    }

    public List<AnswerModel> getAns() {
        return ans;
    }

    public void setAns(List<AnswerModel> ans) {
        this.ans = ans;
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

    public String getIsLinkWithService() {
        return isLinkWithService;
    }

    public void setIsLinkWithService(String isLinkWithService) {
        this.isLinkWithService = isLinkWithService;
    }

    public String getJtId() {
        return jtId;
    }

    public void setJtId(String jtId) {
        this.jtId = jtId;
    }

//    public String getSysFieldType() {
//        return sysFieldType;
//    }

//    public void setSysFieldType(String sysFieldType) {
//        this.sysFieldType = sysFieldType;
//    }

    public String getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(String linkTo) {
        this.linkTo = linkTo;
    }

//    public String getSystemField() {
//        return systemField;
//    }

//    public void setSystemField(String systemField) {
//        this.systemField = systemField;
//    }

    public Integer getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(Integer isAnswered) {
        this.isAnswered = isAnswered;
    }

    public List<Attachments> getAttachmentsList() {
        return attachmentsList;
    }

    public void setAttachmentsList(List<Attachments> attachmentsList) {
        this.attachmentsList = attachmentsList;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {

//        Object obj = super.clone();
//        QuesRspncModel quesRspncModel1 = (QuesRspncModel) obj;
//        quesRspncModel1.setQueId(this.getQueId());
//        quesRspncModel1.setParentId(this.getParentId());
//        quesRspncModel1.setParentAnsId(this.getParentAnsId());
//        quesRspncModel1.setFrmId(this.getFrmId());
//        quesRspncModel1.setDes(this.getDes());
//        quesRspncModel1.setType(this.getType());
//        quesRspncModel1.setMandatory(this.getMandatory());
//        quesRspncModel1.setFlag(this.isFlag());
//        quesRspncModel1.setOpt(this.getOpt());
//        quesRspncModel1.setAns(this.getAns());
//        quesRspncModel1.setIndex(this.getIndex());
//        quesRspncModel1.setFrmType(this.getFrmType());
//        quesRspncModel1.setInternalLabel(this.getInternalLabel());
//        quesRspncModel1.setQueWidth(this.getQueWidth());
//        quesRspncModel1.setLinkToLead(this.getLinkToLead());
//        quesRspncModel1.setLinkToJob(this.getLinkToJob());
//        quesRspncModel1.setIsShowOnSite(this.getIsShowOnSite());
//        quesRspncModel1.setIsDisable(this.getIsDisable());
//        quesRspncModel1.setIsLinkWithService(this.getIsLinkWithService());
//        quesRspncModel1.setJtId(this.getJtId());
//        quesRspncModel1.setSysFieldType(this.getSysFieldType());
//        quesRspncModel1.setLinkTo(this.getLinkTo());
//        quesRspncModel1.setSystemField(this.getSystemField());
//        quesRspncModel1.setIsAnswered(this.getIsAnswered());
        return super.clone();
    }
}
