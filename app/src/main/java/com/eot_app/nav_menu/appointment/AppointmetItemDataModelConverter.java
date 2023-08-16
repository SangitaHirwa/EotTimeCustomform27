package com.eot_app.nav_menu.appointment;

import androidx.room.TypeConverter;

import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class AppointmetItemDataModelConverter {
    @TypeConverter
    public static List<AppointmentItemDataInMap> toAppointmentItemDataModelData(String strdata) {
            Type mapType = new TypeToken<List<AppointmentItemDataInMap>>() {}.getType();
            return new Gson().fromJson(strdata, mapType);
        }

        @TypeConverter
        public static String toString(List<AppointmentItemDataInMap> data) {
            return new Gson().toJson(data);
        }
}
