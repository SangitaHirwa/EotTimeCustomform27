package com.eot_app.nav_menu.jobs.job_complation;

import androidx.annotation.NonNull;

public class ImgPathWithTemp {

    String name ;
    String tempId;

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public ImgPathWithTemp(String name, String tempId) {
        this.name = name;
        this.tempId = tempId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }
}
