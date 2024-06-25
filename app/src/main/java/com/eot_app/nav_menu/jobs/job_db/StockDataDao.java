package com.eot_app.nav_menu.jobs.job_db;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.StockData;
import java.util.List;

@Dao
public interface StockDataDao {
    @Insert(onConflict = REPLACE)
    void insertStockData(List<StockData> stockDataList);
    @Insert(onConflict = REPLACE)
    void insertStockSingleData(StockData stockData);
    @Query("select * from Stock_Balance where  sat_itemid = :sat_itemid")
    StockData getItemBalanceByItemId(String sat_itemid);

    @Query("select * from Stock_Balance")
    List<StockData> getAllStockData();
    @Query("UPDATE Stock_Balance SET balance = :balance WHERE sat_itemid = :sat_itemid")
    void updateStockData(String balance, String sat_itemid);
    @Query("delete from Stock_Balance")
    void delete();
}
