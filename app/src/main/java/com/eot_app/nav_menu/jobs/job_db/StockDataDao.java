package com.eot_app.nav_menu.jobs.job_db;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;

import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.StockData;
import java.util.List;

@Dao
public interface StockDataDao {
    @Insert(onConflict = REPLACE)
    void insertStockData(List<StockData> stockDataList);
}
