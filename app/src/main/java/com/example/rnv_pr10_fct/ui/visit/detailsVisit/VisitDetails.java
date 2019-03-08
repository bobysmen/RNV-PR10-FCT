package com.example.rnv_pr10_fct.ui.visit.detailsVisit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.rnv_pr10_fct.R;
import com.example.rnv_pr10_fct.base.DatePickerDialogFragment;
import com.example.rnv_pr10_fct.base.TimePickerDialogFragment;
import com.example.rnv_pr10_fct.data.RepositoryImpl;
import com.example.rnv_pr10_fct.data.local.AppDatabase;
import com.example.rnv_pr10_fct.data.local.model.Student;
import com.example.rnv_pr10_fct.data.local.model.Visit;
import com.example.rnv_pr10_fct.databinding.VisitDetailsBinding;
import com.example.rnv_pr10_fct.ui.nextVisit.NextVisitMainViewModel;
import com.example.rnv_pr10_fct.ui.nextVisit.NextVisitMainViewModelFactory;
import com.example.rnv_pr10_fct.ui.student.StudentMainViewModel;
import com.example.rnv_pr10_fct.ui.student.StudentMainViewModelFactory;
import com.example.rnv_pr10_fct.ui.visit.VisitMainViewModel;
import com.example.rnv_pr10_fct.ui.visit.VisitMainViewModelFactory;
import com.example.rnv_pr10_fct.utils.TextViewUtils;
import com.google.android.material.textfield.TextInputLayout;

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
    private NextVisitMainViewModel viewModelNextVisit;


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
        viewModelNextVisit = ViewModelProviders.of(requireActivity(),
                new NextVisitMainViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).visitDao()))).get(NextVisitMainViewModel.class);

        setupViews();
        observeStudent();

        if (viewModelVisit.isEdit()) {
            fillFields(viewModelVisit.getVisit());
        }

        if (viewModelNextVisit.isEdit()) {
            fillFields(viewModelNextVisit.getVisit());
        }


        fieldsValidations();
    }

    private void fieldsValidations() {
        TextViewUtils.addAfterTextChangedListener(b.txtDateVisit, s -> checkString(b.txtDateVisit.getText().toString(), b.tilDateVisit));
        TextViewUtils.addAfterTextChangedListener(b.txtTimeStartVisit, s -> checkString(b.txtTimeStartVisit.getText().toString(), b.tilTimeStartVisit));
        TextViewUtils.addAfterTextChangedListener(b.txtTimeEndVisit, s -> checkString(b.txtTimeEndVisit.getText().toString(), b.tilTimeEndVisit));

    }

    private boolean checkString(String s, TextInputLayout textInputLayout) {
        if (TextUtils.isEmpty(s)) {
            textInputLayout.setError(getString(R.string.msgError_main_form));
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
            textInputLayout.setError("");
            return true;
        }
    }

    private void fillFields(Visit visit) {
        if (visit.getDate() != null) {
            b.txtDateVisit.setText(visit.getDate());
            b.txtTimeStartVisit.setText(visit.getStartTime());
            b.txtTimeEndVisit.setText(visit.getEndTime());
            b.txtComment.setText(visit.getComment());
        }
    }

    private void observeStudent() {
        viewModelStudent.getStudents().observe(this, students -> {
            listStudent = new ArrayList<>();
            listStudent.addAll(students);
            List<String> nameStudents = new ArrayList<>();
            for (Student student : students) {
                nameStudents.add(student.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nameStudents);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            b.spinnerStudent.setAdapter(dataAdapter);

            //Fill spinner
            if (viewModelVisit.isEdit()) {
                b.spinnerStudent.setSelection(dataAdapter.getPosition(viewModelVisit.getVisit().getNameStudent()));
            }
            if(viewModelNextVisit.isEdit()){
                b.spinnerStudent.setSelection(dataAdapter.getPosition(viewModelNextVisit.getVisit().getNameStudent()));
            }
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
                if (option.equals("Start")) {
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
                } else {
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
                String dateSelected = dayOfMonth + "/" + (month + 1) + "/" + year;
                b.txtDateVisit.setText(dateSelected);
            }
        });
        datePickerDialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void saveVisit() {
        if (validForm()) {
            if (viewModelVisit.isEdit()) {
                updateVisitViewModel();
                viewModelVisit.updateVisit(viewModelVisit.getVisit());
                getFragmentManager().popBackStack();
            }
            if(viewModelNextVisit.isEdit()) {
                idStudent = idStudent(nameStudentSelected);
                viewModelNextVisit.insertVisit(new Visit(b.txtDateVisit.getText().toString(), b.txtTimeStartVisit.getText().toString(), b.txtTimeEndVisit.getText().toString(), b.txtComment.getText().toString(), idStudent));
                getFragmentManager().popBackStack();
            }

            Toast.makeText(getContext(), getString(R.string.msg_saveSucces), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(), getString(R.string.msg_saveError), Toast.LENGTH_LONG).show();
        }
    }

    private Long idStudent(String nameStudentSelected) {
        Long result = -1L;
        for (Student student : listStudent) {
            if (student.getName().equals(nameStudentSelected)) {
                result = student.getId();
            }
        }
        return result;
    }

    private void updateVisitViewModel() {
        idStudent = idStudent(nameStudentSelected);
        viewModelVisit.getVisit().setDate(b.txtDateVisit.getText().toString());
        viewModelVisit.getVisit().setStartTime(b.txtTimeStartVisit.getText().toString());
        viewModelVisit.getVisit().setEndTime(b.txtTimeEndVisit.getText().toString());
        viewModelVisit.getVisit().setComment(b.txtComment.getText().toString());
        viewModelVisit.getVisit().setIdStudent(idStudent);
    }

    private boolean validForm() {
        if (checkString(b.txtDateVisit.getText().toString(), b.tilDateVisit) && checkString(b.txtTimeStartVisit.getText().toString(), b.tilTimeStartVisit) &&
                checkString(b.txtTimeEndVisit.getText().toString(), b.tilTimeEndVisit)) {
            return true;
        } else {
            checkString(b.txtDateVisit.getText().toString(), b.tilDateVisit);
            checkString(b.txtTimeStartVisit.getText().toString(), b.tilTimeStartVisit);
            checkString(b.txtTimeEndVisit.getText().toString(), b.tilTimeEndVisit);
            return false;
        }
    }
}
