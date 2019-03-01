package com.example.rnv_pr10_fct.ui.company;

import com.example.rnv_pr10_fct.data.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CompanyMainViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    public CompanyMainViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CompanyMainViewModel(repository);
    }
}
