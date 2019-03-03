package com.example.rnv_pr10_fct.ui.visit;

import com.example.rnv_pr10_fct.data.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class VisitMainViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    public VisitMainViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new VisitMainViewModel(repository);
    }
}
