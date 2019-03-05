package com.example.rnv_pr10_fct.ui.student.detailsStudent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.rnv_pr10_fct.R;
import com.example.rnv_pr10_fct.data.RepositoryImpl;
import com.example.rnv_pr10_fct.data.local.AppDatabase;
import com.example.rnv_pr10_fct.data.local.model.Company;
import com.example.rnv_pr10_fct.data.local.model.Student;
import com.example.rnv_pr10_fct.databinding.StudentDetailsBinding;
import com.example.rnv_pr10_fct.ui.company.CompanyMainViewModel;
import com.example.rnv_pr10_fct.ui.company.CompanyMainViewModelFactory;
import com.example.rnv_pr10_fct.ui.student.StudentMainViewModel;
import com.example.rnv_pr10_fct.ui.student.StudentMainViewModelFactory;
import com.example.rnv_pr10_fct.utils.TextViewUtils;
import com.example.rnv_pr10_fct.utils.ValidationsUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class StudentDetails extends Fragment {


    private StudentDetailsBinding b;
    private CompanyMainViewModel viewModelCompany;
    private String nameCompanySelect;
    private StudentMainViewModel viewModelStudent;
    private List<Company> listCompanies;
    private Long idCompany;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = StudentDetailsBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelCompany = ViewModelProviders.of(requireActivity(),
                new CompanyMainViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).companyDao()))).get(CompanyMainViewModel.class);
        viewModelStudent = ViewModelProviders.of(requireActivity(),
                new StudentMainViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).studentDao()))).get(StudentMainViewModel.class);
        setupViews();
        observeCompanies();
        if(viewModelStudent.isEdit()){
            fillFields(viewModelStudent.getStudent());
        }
        fieldsValidations();
    }

    private void fieldsValidations() {
        TextViewUtils.addAfterTextChangedListener(b.txtName, s -> checkString(b.txtName.getText().toString(), b.tilName));
        TextViewUtils.addAfterTextChangedListener(b.txtPhone, s -> checkPhone(b.txtPhone.getText().toString()));
        TextViewUtils.addAfterTextChangedListener(b.txtEmail, s -> checkEmail(b.txtEmail.getText().toString()));
        TextViewUtils.addAfterTextChangedListener(b.txtGrade, s -> checkString(b.txtGrade.getText().toString(), b.tilGrade));
        TextViewUtils.addAfterTextChangedListener(b.txtNameTutor, s -> checkString(b.txtNameTutor.getText().toString(), b.tilNameTutor));
        TextViewUtils.addAfterTextChangedListener(b.txtPhoneTutor, s -> checkPhoneTutor(b.txtPhoneTutor.getText().toString()));
        TextViewUtils.addAfterTextChangedListener(b.txtWorkHours, s -> checkString(b.txtWorkHours.getText().toString(), b.tilWorkHours));
    }

    private void fillFields(Student student) {
        b.txtName.setText(student.getName());
        b.txtPhone.setText(student.getPhone());
        b.txtEmail.setText(student.getEmail());
        b.txtGrade.setText(student.getGrade());
        b.txtNameTutor.setText(student.getNameTutor());
        b.txtPhoneTutor.setText(student.getPhoneTutor());
        b.txtWorkHours.setText(student.getWorkHours());
    }


    private boolean checkEmail(String s) {
        if(!ValidationsUtils.isValidEmail(s)){
            b.tilEmail.setError(getString(R.string.msgError_main_form));
            return false;
        }else{
            b.tilEmail.setErrorEnabled(false);
            b.tilEmail.setError("");
            return true;
        }
    }

    private boolean checkPhone(String s) {
        if(!ValidationsUtils.isValidPhone(s)){
            b.tilPhone.setError(getString(R.string.msgError_main_form));
            return false;
        }else{
            b.tilPhone.setErrorEnabled(false);
            b.tilPhone.setError("");
            return true;
        }
    }

    private boolean checkPhoneTutor(String s) {
        if(!ValidationsUtils.isValidPhone(s)){
            b.tilPhoneTutor.setError(getString(R.string.msgError_main_form));
            return false;
        }else{
            b.tilPhoneTutor.setErrorEnabled(false);
            b.tilPhoneTutor.setError("");
            return true;
        }
    }


    private boolean checkString(String s, TextInputLayout textInputLayout) {
        if(TextUtils.isEmpty(s)){
            textInputLayout.setError(getString(R.string.msgError_main_form));
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
            textInputLayout.setError("");
            return true;
        }
    }

    private void setupViews() {
        b.spinnerCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spn, View view, int position, long id) {
                nameCompanySelect = spn.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        b.fabSaveStudent.setOnClickListener(v -> saveStudent());
    }


    private void observeCompanies() {
        viewModelCompany.getCompanies().observe(this, companies -> {
            listCompanies = new ArrayList<>();
            listCompanies.addAll(companies);
            List<String> nameCompanies = new ArrayList<>();
            nameCompanies.add("Select Company");
            for (Company company: companies){
                nameCompanies.add(company.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nameCompanies);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            b.spinnerCompany.setAdapter(dataAdapter);

            if(viewModelStudent.isEdit()){
                String nameCompanySpinner = "";
                for(Company company: companies){
                    if(viewModelStudent.getStudent().getIdCompany() == company.getId()){
                        nameCompanySpinner = company.getName();
                    }else{
                        nameCompanySpinner = "Select Company";
                    }
                }

                b.spinnerCompany.setSelection(dataAdapter.getPosition(nameCompanySpinner));
            }
        });
    }

    private void saveStudent() {
        if (validForm()) {
            if(viewModelStudent.isEdit()){
                updateStudentViewModel();
                viewModelStudent.updateStudent(viewModelStudent.getStudent());
                getFragmentManager().popBackStack();
            }else{
                idCompany = idCompany(nameCompanySelect);
                viewModelStudent.insertStudent(new Student(b.txtName.getText().toString(), b.txtPhone.getText().toString(), b.txtEmail.getText().toString(), b.txtGrade.getText().toString(), b.txtNameTutor.getText().toString(), b.txtPhoneTutor.getText().toString(), b.txtWorkHours.getText().toString(), idCompany));
                getFragmentManager().popBackStack();
            }

            Toast.makeText(getContext(), "Save successfully", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getContext(), "Error, check fields", Toast.LENGTH_LONG).show();
        }
    }

    private Long idCompany(String nameCompanySelect) {
        Long result = -1L;
        for (Company company: listCompanies){
            if(company.getName().equals(nameCompanySelect)){
                result = company.getId();
            }
        }
        return result;
    }

    private boolean validForm() {
        if( checkString(b.txtName.getText().toString(), b.tilName) && checkPhone(b.txtPhone.getText().toString()) &&
                checkEmail(b.txtEmail.getText().toString()) && checkString(b.txtGrade.getText().toString(), b.tilGrade) &&
                checkString(b.txtNameTutor.getText().toString(), b.tilNameTutor) && checkPhoneTutor(b.txtPhoneTutor.getText().toString()) &&
                checkString(b.txtWorkHours.getText().toString(), b.tilWorkHours)){
            return true;
        }else{
            checkString(b.txtName.getText().toString(), b.tilName);
            checkPhone(b.txtPhone.getText().toString());
            checkEmail(b.txtEmail.getText().toString());
            checkString(b.txtGrade.getText().toString(), b.tilGrade);
            checkString(b.txtNameTutor.getText().toString(), b.tilNameTutor);
            checkPhoneTutor(b.txtPhoneTutor.getText().toString());
            checkString(b.txtWorkHours.getText().toString(), b.tilWorkHours);
            return false;
        }
    }

    private void updateStudentViewModel() {
        idCompany = idCompany(nameCompanySelect);
        viewModelStudent.getStudent().setName(b.txtName.getText().toString());
        viewModelStudent.getStudent().setPhone(b.txtPhone.getText().toString());
        viewModelStudent.getStudent().setEmail(b.txtEmail.getText().toString());
        viewModelStudent.getStudent().setGrade(b.txtGrade.getText().toString());
        viewModelStudent.getStudent().setNameTutor(b.txtNameTutor.getText().toString());
        viewModelStudent.getStudent().setPhoneTutor(b.txtPhoneTutor.getText().toString());
        viewModelStudent.getStudent().setWorkHours(b.txtWorkHours.getText().toString());
        viewModelStudent.getStudent().setIdCompany(idCompany);

    }
}
