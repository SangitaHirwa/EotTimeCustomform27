package com.eot_app.nav_menu.equipment.linkequip.linkMVP.model;

public class EquipmentListReq {
    int index;
    int activeRecord = 1;
    int limit;
    String search;
    String expiry_dtf;
    String expiry_dtt;
    String cltId;
    String type;
    String audId;
    String isParent = "0";
    String contrId;
    String dateTime;

    public void setContrId(String contrId) {
        this.contrId = contrId;
    }

    public String getContrId() {
        return contrId;
    }

    public EquipmentListReq(int index, int limit, String type, String cltId, String dateTime ) {
        this.index = index;
        this.limit = limit;
        this.type = type;
        this.cltId = cltId;
        this.dateTime = dateTime;
    }

//    public EquipmentListReq(int index, int limit, String type, String cltId,String isParent ) {
//        this.index = index;
//        this.limit = limit;
//        this.type = type;
//        this.cltId = cltId;
//        this.isParent = isParent;
//    }
}
