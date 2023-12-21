package com.eot_app.nav_menu.jobs.job_detail.documents.doc_model;

public class MultiDocUpdateRequest {

    String job_Id;
    String file;
    String finalFname;
    String desc;
    String type;
    String isAddAttachAsCompletionNote;
    boolean lastCall;

    public MultiDocUpdateRequest(String job_Id, String file, String finalFname, String desc, String type, String isAddAttachAsCompletionNote, boolean lastCall) {
        this.job_Id = job_Id;
        this.file = file;
        this.finalFname = finalFname;
        this.desc = desc;
        this.type = type;
        this.isAddAttachAsCompletionNote = isAddAttachAsCompletionNote;
        this.lastCall = lastCall;
    }

    public String getJob_Id() {
        return job_Id;
    }

    public void setJob_Id(String job_Id) {
        this.job_Id = job_Id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFinalFname() {
        return finalFname;
    }

    public void setFinalFname(String finalFname) {
        this.finalFname = finalFname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsAddAttachAsCompletionNote() {
        return isAddAttachAsCompletionNote;
    }

    public void setIsAddAttachAsCompletionNote(String isAddAttachAsCompletionNote) {
        this.isAddAttachAsCompletionNote = isAddAttachAsCompletionNote;
    }

    public boolean isLastCall() {
        return lastCall;
    }

    public void setLastCall(boolean lastCall) {
        this.lastCall = lastCall;
    }
}
