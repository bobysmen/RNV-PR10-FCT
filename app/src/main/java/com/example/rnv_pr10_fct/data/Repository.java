package com.example.rnv_pr10_fct.data;

import com.example.rnv_pr10_fct.data.local.model.Company;
import com.example.rnv_pr10_fct.data.local.model.Student;
import com.example.rnv_pr10_fct.data.local.model.Visit;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface Repository {


    LiveData<List<Company>> queryCompanies();
    void insertCompany(Company company);
    int updateCompany(Company company);
    int deleteCompany(Company company);

    LiveData<List<Student>> queryStudents();
    String getNameStudent(Long id);
    long insertStudent(Student student);
    int updateStudent(Student student);
    int deleteStudent(Student student);


    LiveData<List<Visit>> queryVisits();
    long insertVisit(Visit visit);
    int updateVisit(Visit visit);
    int deleteVisit(Visit visit);
}
