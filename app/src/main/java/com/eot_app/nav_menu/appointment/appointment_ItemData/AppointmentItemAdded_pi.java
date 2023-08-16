package com.eot_app.nav_menu.appointment.appointment_ItemData;

import android.content.Context;

import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;

import java.util.List;

public interface AppointmentItemAdded_pi {
    void getItemListByAppointmentFromDB(String appId);
    void onSessionExpire(String msg);
    void getItemFromServer(String appId, Context context);
}
