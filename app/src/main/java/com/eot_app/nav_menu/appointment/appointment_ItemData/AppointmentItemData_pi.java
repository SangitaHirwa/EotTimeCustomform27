package com.eot_app.nav_menu.appointment.appointment_ItemData;

import android.content.Context;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemAdd_RequestModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentUpdateItem_Req_Model;
import com.eot_app.nav_menu.appointment.details.RequirementGetheringListAdapter;

public interface AppointmentItemData_pi {
    void apiCallAddAppointmentItem(AppointmentItemAdd_RequestModel itemAddRequestModel);
    void apiCallUpdateAppointmentItem(AppointmentUpdateItem_Req_Model appointmentUpdateItem_req_model);
    void getItemFromServer(String appId, RequirementGetheringListAdapter requirementGetheringListAdapter);
}
