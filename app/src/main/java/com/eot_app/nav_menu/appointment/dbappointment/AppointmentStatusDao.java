package com.eot_app.nav_menu.appointment.dbappointment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.eot_app.nav_menu.appointment.appointment_model.AppointmentStatusModel;
import com.eot_app.nav_menu.audit.audit_model.AuditStatusModel;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface AppointmentStatusDao {
    @Insert(onConflict = REPLACE)
    void insert(List<AppointmentStatusModel> auditStatusList);

    @Query("select * from AppointmentStatusTable")
    List<AppointmentStatusModel> getAllAppointmentStatusList();

    @Query("select * from AppointmentStatusTable where id=:statusId")
    List<AppointmentStatusModel> getAppointmentStatusById(String statusId);
}
