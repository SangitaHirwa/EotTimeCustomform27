package com.eot_app.nav_menu.audit.audit_list.equipment.equipment_room_db.dao;



import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.eot_app.nav_menu.audit.audit_list.equipment.equipment_room_db.entity.EquipmentStatus;

import java.util.List;



@Dao
public interface EquipmentStatusDao {

    @Insert(onConflict = REPLACE)
    void insertEquipmentStatus(List<EquipmentStatus> equipmentStatusList);

    @Query("DELETE  from EquipmentStatus")
    void deleteEquipmentStatus();

    @Query("Select * from EquipmentStatus where isCondition = '1'")
    List<EquipmentStatus> getEquipmentStatus();
    @Query("Select * from EquipmentStatus where isCondition = '0'")
    List<EquipmentStatus> getEquipmentCondition();
}
