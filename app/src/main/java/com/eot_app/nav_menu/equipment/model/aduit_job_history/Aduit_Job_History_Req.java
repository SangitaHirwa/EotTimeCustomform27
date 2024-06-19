package com.eot_app.nav_menu.equipment.model.aduit_job_history;

/**
 * moduleType for aduitHistory=1
 * moduleType for jobHistory=0
 **/

public class Aduit_Job_History_Req {

    String equId;
    int limit = 50;
    int index = 0;
    String moduleType;
    //   filterType = 1-> service done, 2-> upcoming service, 3-> service overdue, 4-> insted of upcoming service
    String filterType;

    public Aduit_Job_History_Req(String equId, String moduleType) {
        this.equId = equId;
        this.moduleType = moduleType;
    }

    public Aduit_Job_History_Req(String equId, String moduleType, String filterType) {
        this.equId = equId;
        this.moduleType = moduleType;
        this.filterType = filterType;
    }


}
