package com.example.rnv_pr10_fct.ui.nextVisit;

import com.example.rnv_pr10_fct.data.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NextVisitMainViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    public NextVisitMainViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NextVisitMainViewModel(repository);
    }
}
