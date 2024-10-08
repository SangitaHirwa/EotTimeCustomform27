package com.eot_app.nav_menu.audit.audit_list.equipment.equipment_room_db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "EquipmentStatus")
public class EquipmentStatus  {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "esId")
    private String esId;
    @ColumnInfo(name = "statusText")
    private String statusText;
    @ColumnInfo(name = "updatedate")
    private String updatedate;
    @ColumnInfo(name = "isDefault")
    private String isDefault;
    @ColumnInfo(name = "isCondition")
    private String isCondition; // 1 = Status , 0 = Condition;

    public String getEsId() {
        return esId;
    }

    public void setEsId(String esId) {
        this.esId = esId;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getIsCondition() {
        return isCondition;
    }

    public void setIsCondition(String isCondition) {
        this.isCondition = isCondition;
    }
}
