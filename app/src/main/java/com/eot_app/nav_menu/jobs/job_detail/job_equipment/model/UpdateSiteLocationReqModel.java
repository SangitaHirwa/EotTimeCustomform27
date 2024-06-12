package com.eot_app.nav_menu.jobs.job_detail.job_equipment.model;

public class UpdateSiteLocationReqModel {
    private String equId;
    private String jobId;
    private String cltId;
    private String adr;
    private String ctry;
    private String state;
    private String city;
    private String zip;
    private String status;
    private String siteId;

    public String getEquId() {
        return equId;
    }

    public void setEquId(String equId) {
        this.equId = equId;
    }


    public String getCltId() {
        return cltId;
    }

    public void setCltId(String cltId) {
        this.cltId = cltId;
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public UpdateSiteLocationReqModel(String equId, String cltId,
                                      String adr, String ctry, String state, String city,
                                      String zip, String status, String siteId, String jobId) {
        this.equId = equId;
        this.cltId = cltId;
        this.adr = adr;
        this.ctry = ctry;
        this.state = state;
        this.city = city;
        this.zip = zip;
        this.status = status;
        this.siteId = siteId;
        this.jobId = jobId;
    }
}
