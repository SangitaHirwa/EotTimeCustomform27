package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Brand") // check user first name is not repeat.
public class BrandData {

    @PrimaryKey
    @NonNull
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
