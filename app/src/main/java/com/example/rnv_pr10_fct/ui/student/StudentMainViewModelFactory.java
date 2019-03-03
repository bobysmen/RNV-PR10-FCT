package com.example.rnv_pr10_fct.ui.student;

import com.example.rnv_pr10_fct.data.Repository;
import com.example.rnv_pr10_fct.ui.company.CompanyMainViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StudentMainViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    public StudentMainViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StudentMainViewModel(repository);
    }
}
