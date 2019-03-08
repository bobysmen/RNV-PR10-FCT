package com.example.rnv_pr10_fct.ui.nextVisit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rnv_pr10_fct.R;
import com.example.rnv_pr10_fct.data.RepositoryImpl;
import com.example.rnv_pr10_fct.data.local.AppDatabase;
import com.example.rnv_pr10_fct.data.local.model.Student;
import com.example.rnv_pr10_fct.data.local.model.Visit;
import com.example.rnv_pr10_fct.ui.student.StudentMainViewModel;
import com.example.rnv_pr10_fct.ui.student.StudentMainViewModelFactory;
import com.example.rnv_pr10_fct.ui.visit.VisitMainViewModel;
import com.example.rnv_pr10_fct.ui.visit.VisitMainViewModelFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NextVisitMainFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView lstNextVisit;
    private NextVisitMainAdapter listAdapter;
    private NextVisitMainViewModel viewModel;
    private StudentMainViewModel viewModelStudent;
    private TextView lblEmptyView;
    private SharedPreferences setting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.next_visit_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity(),
                new NextVisitMainViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).visitDao()))).get(NextVisitMainViewModel.class);

        viewModelStudent = ViewModelProviders.of(requireActivity(),
                new StudentMainViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).studentDao()))).get(StudentMainViewModel.class);
        viewModel.setEdit(false);

        setupViews(getView());
        observeStudent();
        observeNextVisit();
        setting = PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        setting.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        setting.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    private void observeStudent() {
        viewModelStudent.getStudents().observe(getViewLifecycleOwner(), students -> {

        });
    }

    private void observeNextVisit() {
        viewModel.getVisits().observe(this, visits -> {

            for(Visit visit: visits){
                visit.setNameStudent(viewModelStudent.getNameStudent(visit.getIdStudent()));
            }

            Collections.sort(visits, new Comparator<Visit>() {
                @Override
                public int compare(Visit o1, Visit o2) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyyHH:mm");
                    Date date1;
                    Date date2;

                    try {
                        date1 = simpleDateFormat.parse(o1.getDate()+o1.getStartTime());
                        date2 = simpleDateFormat.parse(o2.getDate()+o2.getStartTime());
                        if(o1.getIdStudent().equals(o2.getIdStudent())){
                            return date2.compareTo(date1);
                        }else{
                            return o1.getIdStudent().compareTo(o2.getIdStudent());
                        }
                    } catch (ParseException e) {
                        System.out.println("Error parse Date");
                        return 0;
                    }
                }
            });

            List<Visit> listAux = new ArrayList<>();

            Long studentIdAux = -1L;
            for(Visit visit: visits){
                if(!visit.getIdStudent().equals(studentIdAux)){
                    Calendar calendar = Calendar.getInstance();
                    String dateNextVisit = "";
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date date;
                    try {
                        date = df.parse(visit.getDate());
                        calendar.setTime(date);
                        calendar.add(Calendar.DAY_OF_YEAR, setting.getInt("seekBarPreference", 15));
                        dateNextVisit = df.format(calendar.getTime());
                        Visit nextVisit = new Visit(dateNextVisit, "", "", "", visit.getIdStudent());
                        nextVisit.setNameStudent(viewModelStudent.getNameStudent(visit.getIdStudent()));
                        listAux.add(nextVisit);
                    } catch (ParseException e) {
                        System.out.println("Error parse Date");
                    }

                }
                studentIdAux = visit.getIdStudent();
            }


            viewModelStudent.getStudents().observe(this, students -> {
                boolean visited;
                for(Student student:students){
                    visited=false;
                    for(Visit visit: listAux){
                        if(visit.getNameStudent().equals(student.getName())){
                            visited = true;
                        }
                    }

                    if(!visited){
                        Visit nextVisitAux = new Visit("", "", "", "", student.getId());
                        nextVisitAux.setNameStudent(student.getName());
                        listAux.add(nextVisitAux);
                    }
                }
            });

            listAdapter.submitList(listAux);
            lblEmptyView.setVisibility(visits.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void setupViews(View view) {
        lstNextVisit = ViewCompat.requireViewById(view, R.id.lstNextVisit);
        lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);

        lstNextVisit.setHasFixedSize(true);
        listAdapter = new NextVisitMainAdapter(position -> createVisit(listAdapter.getItem(position)));
        lstNextVisit.setAdapter(listAdapter);
        lstNextVisit.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.main_lst_columns)));
        lstNextVisit.setItemAnimator(new DefaultItemAnimator());
    }

    private void createVisit(Visit visit) {
        viewModel.setEdit(true);
        viewModel.setVisit(visit);
        Navigation.findNavController(getView()).navigate(R.id.visitDetails);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
