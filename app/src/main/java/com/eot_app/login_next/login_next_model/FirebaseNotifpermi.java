package com.eot_app.login_next.login_next_model;

import com.google.gson.annotations.SerializedName;

public class FirebaseNotifpermi {
    @SerializedName("addJob")
    private String addJob;
    @SerializedName("jobChat")
    private String jobChat;
    @SerializedName("jobStatus")
    private String jobStatus;
    @SerializedName("jobClientChat")
    private String jobClientChat;

    public String getAddJob() {
        return addJob;
    }

    public void setAddJob(String addJob) {
        this.addJob = addJob;
    }

    public String getJobChat() {
        return jobChat;
    }

    public void setJobChat(String jobChat) {
        this.jobChat = jobChat;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobClientChat() {
        return jobClientChat;
    }

    public void setJobClientChat(String jobClientChat) {
        this.jobClientChat = jobClientChat;
    }
}
