package com.eot_app.nav_menu.jobs.job_detail.invoice.inventry_pkg;

public class Inventry_ReQ_Model {
    int compId;
    String type;
    int limit;
    int index;
    String search;
    /**After discussion with jit sir replace isActive param with activeRecord param for inactive inventory item**/
//    String activeRecord;
    String isactive;
    String showInvoice;
    String dateTime;
    String withParts;

    public Inventry_ReQ_Model(int compId, String search, int limit, int index, String dateTime) {//
        this.compId = compId;
        this.type = "0";
        this.limit = limit;
        this.index = index;
        this.search = search;
        this.isactive = "1";
        this.showInvoice = "0";
        this.dateTime = dateTime;
        this.withParts= "1";
    }


}
