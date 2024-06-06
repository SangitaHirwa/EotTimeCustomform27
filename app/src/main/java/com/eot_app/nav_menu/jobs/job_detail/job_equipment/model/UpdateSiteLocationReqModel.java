package com.eot_app.nav_menu.jobs.job_detail.job_equipment.model;

public class UpdateSiteLocationReqModel {
    private String[] equIds;
    private String type;
    private String cltId;
    private String brId;
    private String adr;
    private String ctry;
    private String state;
    private String city;
    private String zip;
    private String status;
    private String siteId;

    public String[] getEquIds() {
        return equIds;
    }

    public void setEquIds(String[] equIds) {
        this.equIds = equIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCltId() {
        return cltId;
    }

    public void setCltId(String cltId) {
        this.cltId = cltId;
    }

    public String getBrId() {
        return brId;
    }

    public void setBrId(String brId) {
        this.brId = brId;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getCtry() {
        return ctry;
    }

    public void setCtry(String ctry) {
        this.ctry = ctry;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public UpdateSiteLocationReqModel(String[] equIds, String type, String cltId, String brId,
                                      String adr, String ctry, String state, String city,
                                      String zip, String status, String siteId) {
        this.equIds = equIds;
        this.type = type;
        this.cltId = cltId;
        this.brId = brId;
        this.adr = adr;
        this.ctry = ctry;
        this.state = state;
        this.city = city;
        this.zip = zip;
        this.status = status;
        this.siteId = siteId;
    }
}
