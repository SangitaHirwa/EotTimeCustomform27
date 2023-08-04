package com.eot_app.nav_menu.appointment.appointment_ItemData;

import android.content.Context;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemAdd_RequestModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentUpdateItem_Req_Model;

public interface AppointmentItemData_pi {
    void apiCallAddAppointmentItem(AppointmentItemAdd_RequestModel itemAddRequestModel, Context context);
    void apiCallUpdateAppointmentItem(AppointmentUpdateItem_Req_Model appointmentUpdateItem_req_model, Context context);
    void getItemFromServer(String appId, Context context);
    AppointmentItemData_pi getInstanceData_ip();
}
