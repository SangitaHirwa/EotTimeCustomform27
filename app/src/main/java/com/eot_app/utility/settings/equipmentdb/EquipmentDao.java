package com.eot_app.utility.settings.equipmentdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;

@Dao
public interface EquipmentDao {
    @Insert(onConflict = REPLACE)
    void insertEquipmentList(List<Equipment> equipmentList);

    @Insert(onConflict = REPLACE)
    void insertSingleEquipmentList(Equipment equipmentList);

    @Query("select * from Equipment where equId=:equId")
    Equipment getEquipmentById(String equId);

    @Query("select * from Equipment where equId=:equId and parentId = '0' and type = :type")
    Equipment getParentEquipmentById(String equId, String type);

    @Query("select * from Equipment")
    List<Equipment> getAllEquipment();

    @Query("select * from Equipment where type=:type and parentId ='0'")
    List<Equipment> getAllTypeBaseEquipment(String type);

    @Query("select * from Equipment where type='2' and cltId = :cltId and parentId = '0'")
    List<Equipment> getAllClientEquipment( String cltId);

    @Query("select * from Equipment where barcode=:barcode or sno =:serialno")
    Equipment getEquipmentByBarcodeOrSerialNo(String barcode, String serialno);

    @Update
    void updateEquipment(Equipment model);

    @Query("delete from Equipment where isdelete = 0 ")
    void deleteEquipmentByIsDelete();

    @Query("delete from Equipment")
    void delete();

    @Query("select equId, equnm, mno, sno, status, type, statusUpdateDate, image, isPart from Equipment where equId=:equId and parentId ='0'")
    Equipment getEquipmentByEquipId(String equId);

    @Query("select equId, equnm, mno, sno, status, type, statusUpdateDate, image, isPart from Equipment where parentId=:parentEquId ")
    List<Equipment> getEquipmentsByParentEquipId(String parentEquId);
    @Query("Update  Equipment Set usrManualDoc = :usrManualDoc where equId=:equId ")
    void setUsrManualDoc(String equId, String usrManualDoc);
}
