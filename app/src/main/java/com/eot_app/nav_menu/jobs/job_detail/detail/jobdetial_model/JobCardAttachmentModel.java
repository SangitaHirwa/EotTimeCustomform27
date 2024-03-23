package com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model;

import java.io.Serializable;

public class JobCardAttachmentModel implements Serializable {

    String id;
    String type;
    String name ;
    Boolean isChecked;

    public JobCardAttachmentModel(String id, String type, String name, Boolean isChecked) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.isChecked = isChecked;
    }

    public JobCardAttachmentModel(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
