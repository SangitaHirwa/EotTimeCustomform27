package com.eot_app.nav_menu.equipment.linkequip.linkMVP;

import com.eot_app.nav_menu.audit.audit_list.equipment.equipment_room_db.entity.EquipmentStatus;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;

import java.util.List;

public interface LinkEquipmentView {

    void setEquipmentList(List<EquArrayModel> list);

    void showHideProgressBar(boolean isShowProgress);

    void refreshEquipmentList(boolean isReturn, boolean equiAdd);

    void onSessionExpired(String msg);

    void setEquStatusList(List<EquipmentStatus> list);

    void updateLinkUnlinkEqu();
    void refreshEquList(boolean isReturn);
}
