package com.example.rnv_pr10_fct.ui.company;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rnv_pr10_fct.R;
import com.example.rnv_pr10_fct.data.RepositoryImpl;
import com.example.rnv_pr10_fct.data.local.AppDatabase;
import com.example.rnv_pr10_fct.data.local.model.Company;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CompanyMainFragment extends Fragment {

    private CompanyMainViewModel viewModel;
    private TextView lblEmptyView;
    private CompanyMainAdapter listAdapter;
    private FloatingActionButton fabAddCompany;
    private ItemTouchHelper swipeItemTouchHelper;
    private RecyclerView lstCompany;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.company_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity(),
                new CompanyMainViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).companyDao()))).get(CompanyMainViewModel.class);
        swipeItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteCompany(listAdapter.getItem(viewHolder.getAdapterPosition()));
            }
        });

        setupViews(getView());
        observeCompanies();
        swipeItemTouchHelper.attachToRecyclerView(lstCompany);
    }

    private void observeCompanies() {
        viewModel.getCompanies().observe(this, companies -> {
            listAdapter.submitList(companies);
            lblEmptyView.setVisibility(companies.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void setupViews(View view) {
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);
        lstCompany = ViewCompat.requireViewById(view, R.id.lstCompany);
        fabAddCompany = ViewCompat.requireViewById(view, R.id.fabAddCompany);

        lblEmptyView.setOnClickListener(this::addNewCompany);
        fabAddCompany.setOnClickListener(this::addNewCompany);

        lstCompany.setHasFixedSize(true);
        listAdapter = new CompanyMainAdapter(position -> editCompany(listAdapter.getItem(position)));
        lstCompany.setAdapter(listAdapter);
        lstCompany.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.main_lst_columns)));
        lstCompany.setItemAnimator(new DefaultItemAnimator());
    }

    private void editCompany(Company company) {
        viewModel.setEdit(true);
        viewModel.setCompany(company);
        Navigation.findNavController(getView()).navigate(R.id.companyDetails);
    }

    private void addNewCompany(View view) {
        viewModel.setEdit(false);
        Navigation.findNavController(view).navigate(R.id.companyDetails);
    }
}
