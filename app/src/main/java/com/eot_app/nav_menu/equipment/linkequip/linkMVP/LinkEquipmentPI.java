package com.eot_app.nav_menu.equipment.linkequip.linkMVP;

import com.eot_app.nav_menu.equipment.linkequip.linkMVP.model.ContractEquipmentReq;

import java.util.List;

public interface LinkEquipmentPI {

    void getEquipmentList(String type, String cltId, String jobId);

    void getAttachedEquipmentList(String jobId, String contrId,boolean isReturn);

    void addAuditEquipment(List<String> equId, String jobId, String contrId);

    void linkUnlinkEquipment(List<String> equId, String audId, String contrId);

    void getContractList(ContractEquipmentReq req);

    void getEquipmentStatus();


}
