package com.eot_app.nav_menu.appointment.details.documents;

import androidx.room.TypeConverter;

import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.Tax;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AppointmentTaxConverter {
    @TypeConverter
    public static List<AppointmentTax> toAppointmentTaxData(String strdata) {
        TypeToken<List<AppointmentTax>> typeToken = new TypeToken<List<AppointmentTax>>() {};
        List<AppointmentTax> appointmentTaxList = new Gson().fromJson(strdata, typeToken.getType());
        return strdata == null ? null : appointmentTaxList;
    }

    @TypeConverter
    public static String toStringData(List<AppointmentTax> data) {
        return data == null ? null : new Gson().toJson(data);
    }
}
