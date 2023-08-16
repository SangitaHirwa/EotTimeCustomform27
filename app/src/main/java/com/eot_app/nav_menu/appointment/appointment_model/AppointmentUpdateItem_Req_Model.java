package com.eot_app.nav_menu.appointment.appointment_model;

import java.io.Serializable;

public class AppointmentUpdateItem_Req_Model implements Serializable {
    private String addToCartServices;
    private  String leadId;
    private String ilmmId;

    public AppointmentUpdateItem_Req_Model(String addToCartServices, String leadId, String ilmmId) {
        this.addToCartServices = addToCartServices;
        this.leadId = leadId;
        this.ilmmId = ilmmId;
    }

    public String getAddToCartServices() {
        return addToCartServices;
    }

    public void setAddToCartServices(String addToCartServices) {
        this.addToCartServices = addToCartServices;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getIlmmId() {
        return ilmmId;
    }

    public void setIlmmId(String ilmmId) {
        this.ilmmId = ilmmId;
    }

}
