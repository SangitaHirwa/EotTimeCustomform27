package com.eot_app.nav_menu.appointment.appointment_ItemData;

import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;
import com.eot_app.nav_menu.appointment.details.RequirementGetheringListAdapter;

import java.util.List;

public interface UpdateItemDataList_pi {
    void updateItemDataList(List<AppintmentItemDataModel> dataModelList, RequirementGetheringListAdapter requirementGetheringListAdapter);
    void setItemListByAppointment(List<AppointmentItemDataInMap>itemList);
    void dismissPullTorefresh();
}
