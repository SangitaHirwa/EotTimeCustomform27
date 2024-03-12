package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(indices = {@Index(value = "ebId", unique = true)}) // check user first name is not repeat.
public class BrandData {

    private String ebId;
    private String name;

    public String getEbId() {
        return ebId;
    }

    public void setEbId(String ebId) {
        this.ebId = ebId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
