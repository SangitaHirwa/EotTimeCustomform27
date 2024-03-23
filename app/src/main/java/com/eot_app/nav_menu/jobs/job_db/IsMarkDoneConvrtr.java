package com.eot_app.nav_menu.jobs.job_db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Sonam-11 on 22/9/20.
 */
public class IsMarkDoneConvrtr {
    @TypeConverter
    public static List<IsMarkDoneWithJtid> toequpArray(String strdata) {
        Type listType = new TypeToken<List<IsMarkDoneWithJtid>>() {
        }.getType();
        List<IsMarkDoneWithJtid> data = new Gson().fromJson(strdata, listType);
        return strdata == null ? null : data;
    }

    @TypeConverter
    public static String toStringequpArray(List<IsMarkDoneWithJtid> data) {
        return data == null ? null : new Gson().toJson(data);
    }
}
