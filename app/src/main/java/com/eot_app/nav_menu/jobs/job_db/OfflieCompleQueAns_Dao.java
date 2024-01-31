package com.eot_app.nav_menu.jobs.job_db;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OfflieCompleQueAns_Dao {

    @Insert(onConflict = REPLACE)
    void inserOfflineAns(OfflieCompleQueAns offlineAns);
    @Query("select * from OfflieCompleQueAns")
    List<OfflieCompleQueAns> getAllComplQueAns();
    @Query("select * from OfflieCompleQueAns where jobId =:jobid")
    OfflieCompleQueAns getComplQueAnsById(String jobid);
    @Query("delete from OfflieCompleQueAns where jobId=:jobid")
    void deleteComplQueAnsById(String jobid);
}
