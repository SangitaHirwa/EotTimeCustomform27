package com.eot_app.nav_menu.audit.audit_model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.eot_app.utility.DropdownListBean;

import java.io.Serializable;

@Entity(tableName = "AuditStatusTable")
public class AuditStatusModel implements DropdownListBean, Serializable {
    @PrimaryKey
    @NonNull
    String id = "0";
    String text = "Not_Started";
    private String url;
    private String urlBitmap;
    private String img;
    private String isDefault;
    private String isStatusShow;
    private String isFwSelect;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlBitmap() {
        return urlBitmap;
    }

    public void setUrlBitmap(String urlBitmap) {
        this.urlBitmap = urlBitmap;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getIsStatusShow() {
        return isStatusShow;
    }

    public void setIsStatusShow(String isStatusShow) {
        this.isStatusShow = isStatusShow;
    }

    public String getIsFwSelect() {
        return isFwSelect;
    }

    public void setIsFwSelect(String isFwSelect) {
        this.isFwSelect = isFwSelect;
    }

    @Override
    public String getKey() {
        return getId();
    }

    @Override
    public String getName() {
        return getText();
    }
}
