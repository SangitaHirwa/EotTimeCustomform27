package com.eot_app.nav_menu.appointment.appointment_model;

public class AppointmentUpdateItem_Req_Model {
    private String addToCartServices;
    private  String leadId;
    private String ilmmId;

    public AppointmentUpdateItem_Req_Model(String addToCartServices, String leadId, String ilmmId) {
        this.addToCartServices = addToCartServices;
        this.leadId = leadId;
        this.ilmmId = ilmmId;
    }
}
