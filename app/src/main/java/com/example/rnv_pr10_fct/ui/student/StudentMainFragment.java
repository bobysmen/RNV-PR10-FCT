package com.example.rnv_pr10_fct.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rnv_pr10_fct.R;
import com.example.rnv_pr10_fct.data.RepositoryImpl;
import com.example.rnv_pr10_fct.data.local.AppDatabase;
import com.example.rnv_pr10_fct.data.local.model.Student;
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

public class StudentMainFragment extends Fragment {

    private StudentMainViewModel viewModel;
    private ItemTouchHelper swipeItemTouchHelper;
    private StudentMainAdapter listAdapter;
    private RecyclerView lstStudent;
    private TextView lblEmptyView;
    private FloatingActionButton fabAddStudent;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity(),
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
                viewModel.deleteStudent(listAdapter.getItem(viewHolder.getAdapterPosition()));
            }
        });

        setupViews(getView());
        observeStudents();
        swipeItemTouchHelper.attachToRecyclerView(lstStudent);
    }

    private void setupViews(View view) {
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);
        lstStudent = ViewCompat.requireViewById(view, R.id.lstStudent);
        fabAddStudent = ViewCompat.requireViewById(view, R.id.fabAddStudent);

        lblEmptyView.setOnClickListener(this::addNewStudent);
        fabAddStudent.setOnClickListener(this::addNewStudent);

        lstStudent.setHasFixedSize(true);
        listAdapter = new StudentMainAdapter(position -> editStudent(listAdapter.getItem(position)));
        lstStudent.setAdapter(listAdapter);
        lstStudent.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.main_lst_columns)));
        lstStudent.setItemAnimator(new DefaultItemAnimator());
    }

    private void editStudent(Student student) {
        viewModel.setEdit(true);
        viewModel.setStudent(student);
        Navigation.findNavController(getView()).navigate(R.id.studentDetails);
    }

    private void addNewStudent(View view) {
        viewModel.setEdit(false);
        Navigation.findNavController(view).navigate(R.id.studentDetails);
    }

    private void observeStudents() {
        viewModel.getStudents().observe(this, students -> {
            listAdapter.submitList(students);
            lblEmptyView.setVisibility(students.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }
}
