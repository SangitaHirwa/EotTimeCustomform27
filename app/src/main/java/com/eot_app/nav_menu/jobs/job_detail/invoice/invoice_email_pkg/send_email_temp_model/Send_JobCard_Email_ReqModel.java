package com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.send_email_temp_model;

import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.JobCardAttachmentModel;

import java.util.List;

public class Send_JobCard_Email_ReqModel {
    public String jobId;
    public String pdfPath;
    public String message;
    public String subject;
    public String to;
    public String cc;
    public String tempId;
    public String fwId;
    List<JobCardAttachmentModel> Attachment;

    public Send_JobCard_Email_ReqModel(String jobId, String pdfPath, String message, String subject, String to, String cc, String tempId, String fwId,List<JobCardAttachmentModel> Attachment) {
        this.jobId = jobId;
        this.pdfPath = pdfPath;
        this.message = message;
        this.subject = subject;
        this.to = to;
        this.cc = cc;
        this.tempId = tempId;
        this.fwId = fwId;
        this.Attachment =Attachment;
    }
}
