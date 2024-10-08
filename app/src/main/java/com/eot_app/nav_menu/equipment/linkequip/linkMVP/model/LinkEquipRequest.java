package com.eot_app.nav_menu.equipment.linkequip.linkMVP.model;

import java.util.List;

public class LinkEquipRequest {

    List<String> equId;
    String audId;
    String contrId;

    public List<String> getEquId() {
        return equId;
    }

    public void setEquId(List<String> equId) {
        this.equId = equId;
    }

    public String getAudId() {
        return audId;
    }

    public void setAudId(String audId) {
        this.audId = audId;
    }

    public String getContrId() {
        return contrId;
    }

    public void setContrId(String contrId) {
        this.contrId = contrId;
    }
}
