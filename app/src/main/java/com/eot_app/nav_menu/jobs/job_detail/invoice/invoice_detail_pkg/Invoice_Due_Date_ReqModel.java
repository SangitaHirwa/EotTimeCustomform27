package com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg;

public class Invoice_Due_Date_ReqModel {
    private String invId;
    private String dueDate;

    public Invoice_Due_Date_ReqModel(String invId, String dueDate) {
        this.invId = invId;
        this.dueDate = dueDate;
    }
}
