package com.eot_app.nav_menu.appointment.appointment_model;

import java.util.List;

public class AppointmentItemAdd_RequestModel {
   private String addToCartServices;
    private  String leadId;
    private String appId;


    public AppointmentItemAdd_RequestModel(String addToCartServices, String leadId, String appId) {
        this.addToCartServices = addToCartServices;
        this.leadId = leadId;
        this.appId = appId;
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
