package com.eot_app.nav_menu.appointment;

import androidx.room.TypeConverter;

import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;
import com.eot_app.nav_menu.appointment.details.AppointmentAttachment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AppointmentAttachmentConverter {
   /* @TypeConverter
    public static List<AppointmentAttachment> toAppointmentAttachment(String strdata) {
        Type mapType = new TypeToken<List<AppointmentAttachment>>() {}.getType();
        return new Gson().fromJson(strdata, mapType);
    }

    @TypeConverter
    public static String toString(List<AppointmentAttachment> data) {
        return new Gson().toJson(data);
    }*/
}
