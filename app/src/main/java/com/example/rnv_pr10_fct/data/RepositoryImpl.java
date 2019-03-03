package com.example.rnv_pr10_fct.data;

import android.os.AsyncTask;

import com.example.rnv_pr10_fct.data.local.CompanyDao;
import com.example.rnv_pr10_fct.data.local.StudentDao;
import com.example.rnv_pr10_fct.data.local.model.Company;
import com.example.rnv_pr10_fct.data.local.model.Student;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import androidx.lifecycle.LiveData;

public class RepositoryImpl implements Repository {

    private CompanyDao companyDao = null;
    private StudentDao studentDao = null;

    public RepositoryImpl(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public RepositoryImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public LiveData<List<Company>> queryCompanies() {
        return companyDao.queryCompanies();
    }

    @Override
    public void insertCompany(Company company) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> companyDao.insertCompany(company));
    }

    @Override
    public int updateCompany(Company company) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> companyDao.updateCompany(company));
        return 0;
    }

    @Override
    public int deleteCompany(Company company) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> companyDao.deleteCompany(company));
        return 0;
    }




    @Override
    public LiveData<List<Student>> queryStudents() {
        return studentDao.queryStudents();
    }

    @Override
    public long insertStudent(Student student) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> studentDao.insertStudent(student));
        return 0;
    }

    @Override
    public int updateStudent(Student student) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> studentDao.updateStudent(student));
        return 0;
    }

    @Override
    public int deleteStudent(Student student) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> studentDao.deleteStudent(student));
        return 0;
    }
}
