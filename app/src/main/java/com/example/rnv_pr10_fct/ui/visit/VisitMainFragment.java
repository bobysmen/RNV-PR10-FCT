package com.example.rnv_pr10_fct.ui.visit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rnv_pr10_fct.R;
import com.example.rnv_pr10_fct.data.RepositoryImpl;
import com.example.rnv_pr10_fct.data.local.AppDatabase;
import com.example.rnv_pr10_fct.data.local.model.Visit;
import com.example.rnv_pr10_fct.ui.student.StudentMainViewModel;
import com.example.rnv_pr10_fct.ui.student.StudentMainViewModelFactory;
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
import androidx.recyclerview.widget.RecyclerView;

public class VisitMainFragment extends Fragment {

    private VisitMainViewModel viewModel;
    private ItemTouchHelper swipeItemTouchHelper;
    private VisitMainAdapter listAdapter;
    private RecyclerView lstVisit;
    private TextView lblEmptyView;
    private FloatingActionButton fabAddVisit;
    private StudentMainViewModel viewModelStudend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.visit_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity(),
                new VisitMainViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).visitDao()))).get(VisitMainViewModel.class);

        viewModelStudend = ViewModelProviders.of(requireActivity(),
                new StudentMainViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).studentDao()))).get(StudentMainViewModel.class);
        swipeItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteVisit(listAdapter.getItem(viewHolder.getAdapterPosition()));
            }
        });

        setupViews(getView());
        observeVisit();
    }

    private void setupViews(View view) {
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);
        lstVisit = ViewCompat.requireViewById(view, R.id.lstVisit);
        fabAddVisit = ViewCompat.requireViewById(view, R.id.fabAddVisit);

        lblEmptyView.setOnClickListener(this::addNewVisit);
        fabAddVisit.setOnClickListener(this::addNewVisit);

        lstVisit.setHasFixedSize(true);
        listAdapter = new VisitMainAdapter(position -> editVisit(listAdapter.getItem(position)));
        lstVisit.setAdapter(listAdapter);
        lstVisit.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.main_lst_columns)));
        lstVisit.setItemAnimator(new DefaultItemAnimator());
    }

    private void editVisit(Visit visit) {
        viewModel.setEdit(true);
        viewModel.setVisit(visit);
        //TODO Navigation
    }

    private void observeVisit() {
        viewModel.getVisits().observe(this, visits -> {
            for(Visit visit: visits){
                visit.setNameStudent(viewModelStudend.getNameStudent(visit.getIdStudent()));
            }
            listAdapter.submitList(visits);
            lblEmptyView.setVisibility(visits.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void addNewVisit(View view) {
        viewModel.setEdit(false);
        Navigation.findNavController(view).navigate(R.id.visitDetails);
    }
}
