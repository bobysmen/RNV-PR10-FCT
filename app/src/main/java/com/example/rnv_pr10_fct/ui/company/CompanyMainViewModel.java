package com.example.rnv_pr10_fct.ui.company;

import com.example.rnv_pr10_fct.data.Repository;
import com.example.rnv_pr10_fct.data.local.model.Company;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class CompanyMainViewModel extends ViewModel {

    private final Repository repository;
    private LiveData<List<Company>> companies;
    private Company company;
    private boolean isEdit;

    public CompanyMainViewModel(Repository repository) {
        this.repository = repository;
        companies = repository.queryCompanies();
    }

    public LiveData<List<Company>> getCompanies(){
        return companies;
    }

    public String getNameCompany(Long id){
        return repository.getNameCompany(id);
    }

    public void insertCompany(Company company){
        repository.insertCompany(company);
    }

    public int updateCompany(Company company){
        return repository.updateCompany(company);
    }

    public int deleteCompany(Company company){
        return repository.deleteCompany(company);
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
