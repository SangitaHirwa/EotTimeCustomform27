package com.eot_app.nav_menu.jobs.job_complation.complation_form.model;

import com.google.gson.annotations.SerializedName;

public class Ans {
    @SerializedName("imgActualNm")
    
    private String imgActualNm;
    @SerializedName("key")
    
    private String key;
    @SerializedName("value")
    
    private String value;
    @SerializedName("valueThumb")
    
    private String valueThumb;

    public String getImgActualNm() {
        return imgActualNm;
    }

    public void setImgActualNm(String imgActualNm) {
        this.imgActualNm = imgActualNm;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueThumb() {
        return valueThumb;
    }

    public void setValueThumb(String valueThumb) {
        this.valueThumb = valueThumb;
    }

}
