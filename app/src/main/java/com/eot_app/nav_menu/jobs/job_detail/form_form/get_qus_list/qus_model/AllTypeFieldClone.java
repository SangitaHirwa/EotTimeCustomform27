package com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model;

import androidx.annotation.NonNull;

import org.checkerframework.checker.units.qual.A;

import java.util.List;

public class AllTypeFieldClone implements  Cloneable{

    public AllTypeFieldClone(QuesRspncModel quesRspncModel) {
        this.quesRspncModel = quesRspncModel;
    }

    public QuesRspncModel quesRspncModel = new QuesRspncModel();

    public QuesRspncModel getQuesRspncModel() {
        return quesRspncModel;
    }

    public void setQuesRspncModel(QuesRspncModel quesRspncModel) {
        this.quesRspncModel = quesRspncModel;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        AllTypeFieldClone allTypeFieldClone =(AllTypeFieldClone)super.clone();
        allTypeFieldClone.setQuesRspncModel((QuesRspncModel) allTypeFieldClone.getQuesRspncModel().clone());
//        allTypeFieldClone.quesRspncModel.setQueId(quesRspncModel.getQueId());
//        allTypeFieldClone.quesRspncModel.setParentId(quesRspncModel.getParentId());
//        allTypeFieldClone.quesRspncModel.setParentAnsId(quesRspncModel.getParentAnsId());
//        allTypeFieldClone.quesRspncModel.setFrmId(quesRspncModel.getFrmId());
//        allTypeFieldClone.quesRspncModel.setDes(quesRspncModel.getDes());
//        allTypeFieldClone.quesRspncModel.setType(quesRspncModel.getType());
//        allTypeFieldClone.quesRspncModel.setMandatory(quesRspncModel.getMandatory());
//        allTypeFieldClone.quesRspncModel.setFlag(quesRspncModel.isFlag());
//        allTypeFieldClone.quesRspncModel.setOpt(quesRspncModel.getOpt());
//        allTypeFieldClone.quesRspncModel.setAns(quesRspncModel.getAns());
//        allTypeFieldClone.quesRspncModel.setIndex(quesRspncModel.getIndex());
//        allTypeFieldClone.quesRspncModel.setFrmType(quesRspncModel.getFrmType());
//        allTypeFieldClone.quesRspncModel.setInternalLabel(quesRspncModel.getInternalLabel());
//        allTypeFieldClone.quesRspncModel.setQueWidth(quesRspncModel.getQueWidth());
//        allTypeFieldClone.quesRspncModel.setLinkToLead(quesRspncModel.getLinkToLead());
//        allTypeFieldClone.quesRspncModel.setLinkToJob(quesRspncModel.getLinkToJob());
//        allTypeFieldClone.quesRspncModel.setIsShowOnSite(quesRspncModel.getIsShowOnSite());
//        allTypeFieldClone.quesRspncModel.setIsDisable(quesRspncModel.getIsDisable());
//        allTypeFieldClone.quesRspncModel.setIsLinkWithService(quesRspncModel.getIsLinkWithService());
//        allTypeFieldClone.quesRspncModel.setJtId(quesRspncModel.getJtId());
//        allTypeFieldClone.quesRspncModel.setSysFieldType(quesRspncModel.getSysFieldType());
//        allTypeFieldClone.quesRspncModel.setLinkTo(quesRspncModel.getLinkTo());
//        allTypeFieldClone.quesRspncModel.setSystemField(quesRspncModel.getSystemField());
//        allTypeFieldClone.quesRspncModel.setIsAnswered(quesRspncModel.getIsAnswered());

        return allTypeFieldClone;
    }
}
