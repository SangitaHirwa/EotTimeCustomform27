package com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_email_pkg.send_email_temp_model;

import java.util.ArrayList;

public class Send_Quote_Email_Req_Model {
   private String quotId;
    private String  message;
    private String subject;
    private String  to;
    private String  cc;
    private String  bcc;
    private String from;
    private String  fromnm;
    private String  isQuotePdfSend;
    private String tempId;
    private ArrayList<String> Attachment;


    public Send_Quote_Email_Req_Model(String quotId, String message, String subject, String to, String cc, String bcc,
                                      String from, String fromnm, String tempId, ArrayList<String> Attachment) {
        this.quotId = quotId;
        this.message = message;
        this.subject = subject;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.from = from;
        this.fromnm  = fromnm;
        this.tempId = tempId;
        this.Attachment = Attachment;
    }

    public String getQuotId() {
        return quotId;
    }

    public void setQuotId(String quotId) {
        this.quotId = quotId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromnm() {
        return fromnm;
    }

    public void setFromnm(String fromnm) {
        this.fromnm = fromnm;
    }

    public String getIsQuotePdfSend() {
        return isQuotePdfSend;
    }

    public void setIsQuotePdfSend(String isQuotePdfSend) {
        this.isQuotePdfSend = isQuotePdfSend;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public ArrayList<String> getAttachment() {
        return Attachment;
    }

    public void setAttachment(ArrayList<String> attachment) {
        Attachment = attachment;
    }
}
