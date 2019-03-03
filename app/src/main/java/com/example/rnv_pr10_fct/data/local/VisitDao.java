package com.example.rnv_pr10_fct.data.local;

import com.example.rnv_pr10_fct.data.local.model.Visit;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface VisitDao {

    @Query("Select * from Visit")
    LiveData<List<Visit>> queryVisits();

    @Insert
    long insertVisit(Visit visit);

    @Update
    int updateVisit(Visit visit);

    @Delete
    int deleteVisit(Visit visit);

}
