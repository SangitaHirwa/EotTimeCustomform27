package com.eot_app.nav_menu.jobs.job_db;

import androidx.room.TypeConverter;

import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.QuesRspncModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by Sonam-11 on 22/9/20.
 */
public class AllQuestionAnswerConverter {
    @TypeConverter
    public static List<QuesRspncModel> toequpArray(String strdata) {
        Type listType = new TypeToken<List<QuesRspncModel>>() {
        }.getType();
        List<QuesRspncModel> data = new Gson().fromJson(strdata, listType);
        return strdata == null ? null : data;
    }

    @TypeConverter
    public static String toStringequpArray(List<QuesRspncModel> data) {
        return data == null ? null : new Gson().toJson(data);
    }
}
