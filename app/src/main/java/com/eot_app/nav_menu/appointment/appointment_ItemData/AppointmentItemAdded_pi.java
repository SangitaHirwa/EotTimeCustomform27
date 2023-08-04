package com.eot_app.nav_menu.appointment.appointment_ItemData;

import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;

import java.util.List;

public interface AppointmentItemAdded_pi {
    void setItemAdded(List<AppintmentItemDataModel> updatedItemList1);
    void onSessionExpire(String msg);
    void setItemAdded();
}
