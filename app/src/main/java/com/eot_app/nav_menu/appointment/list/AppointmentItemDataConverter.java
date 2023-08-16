package com.eot_app.nav_menu.appointment.list;

import androidx.room.TypeConverter;

import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AppointmentItemDataConverter {
    @TypeConverter
    public static AppintmentItemDataModel toAppointmentItemDataModelData(String strdata) {
        Type mapType = new TypeToken<AppintmentItemDataModel>() {}.getType();
        return new Gson().fromJson(strdata, mapType);
    }

    @TypeConverter
    public static String toString(AppintmentItemDataModel data) {
        return new Gson().toJson(data);
    }
}
