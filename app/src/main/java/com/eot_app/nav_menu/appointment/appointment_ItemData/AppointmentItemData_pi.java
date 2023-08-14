package com.eot_app.nav_menu.appointment.appointment_ItemData;

import android.content.Context;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemAdd_RequestModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDeleteRequestModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentUpdateItem_Req_Model;
import com.eot_app.nav_menu.appointment.details.RequirementGetheringListAdapter;

public interface AppointmentItemData_pi {

    void apiCallUpdateAppointmentItem(AppointmentUpdateItem_Req_Model appointmentUpdateItem_req_model,
                                      AppointmentItemDataInMap appintmentItemDataModel, String appId,AppintmentItemDataModel modelForUpdate,
                                      Context context);
    void apiCallForDeleteItem(AppointmentItemDeleteRequestModel deleteRequestModel,
                                 String appId,Context context);
}
