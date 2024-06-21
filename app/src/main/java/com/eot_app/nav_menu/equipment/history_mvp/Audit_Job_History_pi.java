package com.eot_app.nav_menu.equipment.history_mvp;

import java.util.List;

public interface Audit_Job_History_pi {

    void getEquipmentAduitHistory(String equId);

    void getEquipmentJobHistory(String equId,String filterType);
    void getEquipmentUpcomingJobHistory(String equId,String filterType);

    void getEquipmentAduitDetails(String audId);
    void getEqItemFromServer(String equId,String jobId);
    void getEqPartsFromServer(String equId);

    void getEquipmentJobDetails(String audId);

    void getApiForUploadAttchment(String equId, String usrManualDoc);
/** For link only one equipment isScannedEqu = 1*/
    void addAuditEquipment(List<String> equId, String jobId, String contrId, String isScannedEqu);
    void getAttachedEquipmentList();
}
