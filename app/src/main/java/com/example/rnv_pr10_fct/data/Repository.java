package com.example.rnv_pr10_fct.data;

import com.example.rnv_pr10_fct.data.local.model.Company;
import com.example.rnv_pr10_fct.data.local.model.Student;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface Repository {


    LiveData<List<Company>> queryCompanies();
    void insertCompany(Company company);
    long insertCompanies(Company... companies);
    int updateCompany(Company company);
    int deleteCompany(Company company);

    LiveData<List<Student>> queryStudents();
    long insertStudent(Student student);
    long insertStudents(Student... students);
    int updateStudent(Student student);
    int deleteStudent(Student student);
}
