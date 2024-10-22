package com.eot_app.nav_menu.jobs.job_detail.customform.cstm_form_model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CustomFormListOfflineDao {

    @Insert
    void insert(CustomFormListOffline customFormListOffline);

    @Query("delete from CustomFormListOffline")
    void delete();

    @Query("Select * from customformlistoffline where jtid=:jtid")
    List<CustomFormListOffline> getFormByJtid(String jtid);

    @Query("Select * from customformlistoffline where jtId='-1'")
    List<CustomFormListOffline> getcommonForm();

    @Query("Select frmId from customformlistoffline where jtId=:jtid AND tab ='0'")
    String getFormIdByJtid(String jtid);

    @Query("Select * from customformlistoffline where jtId=:jtid AND tab ='0'")
    List<CustomFormListOffline> getCustomFormList(String jtid);
}
