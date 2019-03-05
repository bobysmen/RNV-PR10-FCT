package com.example.rnv_pr10_fct.ui.visit.detailsVisit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.rnv_pr10_fct.base.DatePickerDialogFragment;
import com.example.rnv_pr10_fct.base.TimePickerDialogFragment;
import com.example.rnv_pr10_fct.data.RepositoryImpl;
import com.example.rnv_pr10_fct.data.local.AppDatabase;
import com.example.rnv_pr10_fct.data.local.model.Student;
import com.example.rnv_pr10_fct.data.local.model.Visit;
import com.example.rnv_pr10_fct.databinding.VisitDetailsBinding;
import com.example.rnv_pr10_fct.ui.student.StudentMainViewModel;
import com.example.rnv_pr10_fct.ui.student.StudentMainViewModelFactory;
import com.example.rnv_pr10_fct.ui.visit.VisitMainViewModel;
import com.example.rnv_pr10_fct.ui.visit.VisitMainViewModelFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class VisitDetails extends Fragment {


    private VisitDetailsBinding b;
    private String nameStudentSelected;
    private StudentMainViewModel viewModelStudent;
    private ArrayList<Student> listStudent;
    private VisitMainViewModel viewModelVisit;
    private Long idStudent;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = VisitDetailsBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModelStudent = ViewModelProviders.of(requireActivity(),
                new StudentMainViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).studentDao()))).get(StudentMainViewModel.class);
        viewModelVisit = ViewModelProviders.of(requireActivity(),
                new VisitMainViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).visitDao()))).get(VisitMainViewModel.class);
        setupViews();
        observeStudent();
    }

    private void observeStudent() {
        viewModelStudent.getStudents().observe(this, students -> {
            listStudent = new ArrayList<>();
            listStudent.addAll(students);
            List<String> nameStudents = new ArrayList<>();
            nameStudents.add("Select Student");
            for(Student student: students){
                nameStudents.add(student.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nameStudents);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            b.spinnerStudent.setAdapter(dataAdapter);
        });
    }

    private void setupViews() {
        b.spinnerStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spn, View view, int position, long id) {
                nameStudentSelected = spn.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        b.txtDateVisit.setOnClickListener(v -> showDatePickerDialog());
        b.txtTimeStartVisit.setOnClickListener(v -> showTimePickerDialog("Start"));
        b.txtTimeEndVisit.setOnClickListener(v -> showTimePickerDialog("End"));
        b.fabSaveVisit.setOnClickListener(v -> saveVisit());
    }

    private void showTimePickerDialog(String option) {
        TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timePicker = hourOfDay + ":" + minute;
                if (option.equals("Start")){
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                    Date d = null;
                    try {
                        d = df.parse(timePicker);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);
                    cal.add(Calendar.MINUTE, 30);
                    String timeEndInitial = df.format(cal.getTime());
                    b.txtTimeStartVisit.setText(timePicker);
                    b.txtTimeEndVisit.setText(timeEndInitial);
                }else{
                    b.txtTimeEndVisit.setText(timePicker);
                }
            }
        });
        timePickerDialogFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }

    private void showDatePickerDialog() {
        DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateSelected = dayOfMonth + "/" + (month+1) + "/" + year;
                b.txtDateVisit.setText(dateSelected);
            }
        });
        datePickerDialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void saveVisit() {
        if(validForm()){
            if(viewModelVisit.isEdit()){
                updateVisitViewModel();
                viewModelVisit.updateVisit(viewModelVisit.getVisit());
                getFragmentManager().popBackStack();
            }else{
                idStudent = idStudent(nameStudentSelected);
                viewModelVisit.insertVisit(new Visit(b.txtDateVisit.getText().toString(), b.txtTimeStartVisit.getText().toString(), b.txtTimeEndVisit.getText().toString(), b.txtComment.getText().toString(), false, idStudent));
                getFragmentManager().popBackStack();
            }
        }
    }

    private Long idStudent(String nameStudentSelected) {
        Long result = -1L;
        for (Student student: listStudent){
            if(student.getName().equals(nameStudentSelected)){
                result = student.getId();
            }
        }
        return result;
    }

    private void updateVisitViewModel() {

    }

    private boolean validForm() {
        return true;
    }
}
