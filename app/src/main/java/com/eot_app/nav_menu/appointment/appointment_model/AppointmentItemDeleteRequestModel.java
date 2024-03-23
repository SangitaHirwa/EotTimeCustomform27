package com.eot_app.nav_menu.appointment.appointment_model;

public class AppointmentItemDeleteRequestModel {
    private String ilmmId;
   private String isItemOrTitle;
   private String leadId;

    public AppointmentItemDeleteRequestModel(String ilmmId, String isItemOrTitle,String leadId) {
        this.ilmmId = ilmmId;
        this.isItemOrTitle = isItemOrTitle;
        this.leadId = leadId;
    }

    public String getIlmmId() {
        return ilmmId;
    }

    public void setIlmmId(String ilmmId) {
        this.ilmmId = ilmmId;
    }

    public String getIsItemOrTitle() {
        return isItemOrTitle;
    }

    public void setIsItemOrTitle(String isItemOrTitle) {
        this.isItemOrTitle = isItemOrTitle;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }
}
