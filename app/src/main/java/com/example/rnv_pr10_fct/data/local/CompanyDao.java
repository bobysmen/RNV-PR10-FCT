package com.example.rnv_pr10_fct.data.local;

import com.example.rnv_pr10_fct.data.local.model.Company;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CompanyDao {

    @Query("Select * from Company")
    LiveData<List<Company>> queryCompanies();

    @Query("Select name from Company where id=:id")
    String getNameCompany(Long id);

    @Insert
    void insertCompany(Company company);

    @Update
    int updateCompany(Company company);

    @Delete
    int deleteCompany(Company company);
}
