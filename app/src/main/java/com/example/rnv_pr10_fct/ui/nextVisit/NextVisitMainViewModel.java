package com.example.rnv_pr10_fct.ui.nextVisit;

import com.example.rnv_pr10_fct.data.Repository;
import com.example.rnv_pr10_fct.data.local.model.Visit;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class NextVisitMainViewModel extends ViewModel {

    private final Repository repository;
    private LiveData<List<Visit>> visits;
    private Visit visit;
    private boolean isEdit;

    public NextVisitMainViewModel(Repository repository) {
        this.repository = repository;
        visits = repository.queryVisits();
    }

    public LiveData<List<Visit>> getVisits(){
        return visits;
    }


    public void insertVisit(Visit visit){
        repository.insertVisit(visit);
    }

    public int updateVisit(Visit visit){
        return repository.updateVisit(visit);
    }

    public int deleteVisit(Visit visit){
        return repository.deleteVisit(visit);
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }
}
