package com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Stock_Balance")
public class StockData {
    @PrimaryKey
    @NonNull
    private String sat_itemid;
    private String sat_usrid;

    private String balance;

    @NonNull
    public String getSat_usrid() {
        return sat_usrid;
    }

    public void setSat_usrid(@NonNull String sat_usrid) {
        this.sat_usrid = sat_usrid;
    }

    public String getSat_itemid() {
        return sat_itemid;
    }

    public void setSat_itemid(String sat_itemid) {
        this.sat_itemid = sat_itemid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
