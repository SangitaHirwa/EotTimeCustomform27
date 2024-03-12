package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.brand_db;
import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.Tax;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.BrandData;

import java.util.List;

@Dao
public interface BrandDao {
    @Insert(onConflict = REPLACE)
    void insertBrandDate(List<BrandData> brandDataList);

    @Query("select * from Brand ")
    List<BrandData> getBrandDataList();
}
