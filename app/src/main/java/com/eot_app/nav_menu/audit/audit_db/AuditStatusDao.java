package com.eot_app.nav_menu.audit.audit_db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.eot_app.nav_menu.audit.audit_list.audit_mvp.model.AuditList_Res;
import com.eot_app.nav_menu.audit.audit_model.AuditStatusModel;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface AuditStatusDao {
    @Insert(onConflict = REPLACE)
    void insert(List<AuditStatusModel> auditStatusList);

    @Query("select * from AuditStatusTable  ")
    List<AuditStatusModel> getAllAuditStatus();

    @Query("select * from AuditStatusTable where id=:id ")
    AuditStatusModel getImageForStatus(String id);
}
