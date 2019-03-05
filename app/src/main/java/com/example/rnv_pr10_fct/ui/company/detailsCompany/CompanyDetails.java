package com.example.rnv_pr10_fct.ui.company.detailsCompany;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rnv_pr10_fct.R;
import com.example.rnv_pr10_fct.data.RepositoryImpl;
import com.example.rnv_pr10_fct.data.local.AppDatabase;
import com.example.rnv_pr10_fct.data.local.model.Company;
import com.example.rnv_pr10_fct.databinding.CompanyDetailsBinding;
import com.example.rnv_pr10_fct.ui.company.CompanyMainViewModel;
import com.example.rnv_pr10_fct.ui.company.CompanyMainViewModelFactory;
import com.example.rnv_pr10_fct.utils.TextViewUtils;
import com.example.rnv_pr10_fct.utils.ValidationsUtils;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class CompanyDetails extends Fragment {

    private final int MAX_CIF = 10;

    private CompanyDetailsBinding b;
    private CompanyMainViewModel viewModelCompany;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = CompanyDetailsBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelCompany = ViewModelProviders.of(requireActivity(),
                new CompanyMainViewModelFactory(
                        new RepositoryImpl(
                                AppDatabase.getInstance(requireContext().getApplicationContext()).companyDao()))).get(CompanyMainViewModel.class);
        setupViews();
        if(viewModelCompany.isEdit()){
            fillFields(viewModelCompany.getCompany());
        }
        fieldsValidations();

    }

    private void fillFields(Company company) {
        b.txtName.setText(company.getName());
        b.txtCif.setText(company.getCif());
        b.txtAddress.setText(company.getAddress());
        b.txtPhone.setText(company.getPhone());
        b.txtEmail.setText(company.getEmail());
        b.txtUrlLogo.setText(company.getUrlLogo());
        b.txtNameContact.setText(company.getContactName());
    }

    private void fieldsValidations() {
        TextViewUtils.addAfterTextChangedListener(b.txtName, s -> checkString(b.txtName.getText().toString(), b.tilName));
        TextViewUtils.addAfterTextChangedListener(b.txtCif, s -> checkCif(b.txtCif.getText().toString()));
        TextViewUtils.addAfterTextChangedListener(b.txtAddress, s -> checkString(b.txtAddress.getText().toString(), b.tilAddress));
        TextViewUtils.addAfterTextChangedListener(b.txtPhone, s -> checkPhone(b.txtPhone.getText().toString()));
        TextViewUtils.addAfterTextChangedListener(b.txtEmail, s -> checkEmail(b.txtEmail.getText().toString()));
        TextViewUtils.addAfterTextChangedListener(b.txtUrlLogo, s -> checkUrl(b.txtUrlLogo.getText().toString()));
        TextViewUtils.addAfterTextChangedListener(b.txtNameContact, s -> checkString(b.txtNameContact.getText().toString(), b.tilNameContact));
    }

    private boolean checkUrl(String s) {
        if(!ValidationsUtils.isValidUrl(s)){
            b.tilUrlLogo.setError(getString(R.string.msgError_main_form));
            return false;
        }else{
            b.tilUrlLogo.setErrorEnabled(false);
            b.tilUrlLogo.setError("");
            return true;
        }
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

    private boolean checkCif(String s) {
        if(!ValidationsUtils.isValidCif(s) || s.length() >= MAX_CIF){
            b.tilCif.setError(getString(R.string.msgError_main_form));
            return false;
        }else{
            b.tilCif.setErrorEnabled(false);
            b.tilCif.setError("");
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
        b.fabSaveCompany.setOnClickListener(v -> saveCompany());
        TextViewUtils.setOnImeActionDoneListener(b.txtNameContact, (v, event) -> saveCompany());
    }

    private boolean validForm(){
        if(checkString(b.txtName.getText().toString(), b.tilName) && checkCif(b.txtCif.getText().toString()) &&
                checkString(b.txtAddress.getText().toString(), b.tilAddress) &&
                checkPhone(b.txtPhone.getText().toString()) && checkEmail(b.txtEmail.getText().toString()) &&
                checkUrl(b.txtUrlLogo.getText().toString()) && checkString(b.txtNameContact.getText().toString(), b.tilNameContact)){
            return true;

        }else{
            checkString(b.txtName.getText().toString(), b.tilName);
            checkCif(b.txtCif.getText().toString());
            checkString(b.txtAddress.getText().toString(), b.tilAddress);
            checkPhone(b.txtPhone.getText().toString());
            checkEmail(b.txtEmail.getText().toString());
            checkUrl(b.txtUrlLogo.getText().toString());
            checkString(b.txtNameContact.getText().toString(), b.tilNameContact);
            return false;
        }
    }

    private void saveCompany() {
        if (validForm()) {
            if(viewModelCompany.isEdit()){
                updateCompanyViewModel();
                viewModelCompany.updateCompany(viewModelCompany.getCompany());
                getFragmentManager().popBackStack();
            }else{
                viewModelCompany.insertCompany(new Company(b.txtName.getText().toString(), b.txtCif.getText().toString(), b.txtAddress.getText().toString(), b.txtPhone.getText().toString(), b.txtEmail.getText().toString(), b.txtUrlLogo.getText().toString(), b.txtNameContact.getText().toString()));
                getFragmentManager().popBackStack();
            }

            Toast.makeText(getContext(), "Save successfully", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(getContext(), "Error, check fields", Toast.LENGTH_LONG).show();
        }
    }

    private void updateCompanyViewModel() {
        viewModelCompany.getCompany().setName(b.txtName.getText().toString());
        viewModelCompany.getCompany().setCif(b.txtCif.getText().toString());
        viewModelCompany.getCompany().setAddress(b.txtAddress.getText().toString());
        viewModelCompany.getCompany().setPhone(b.txtPhone.getText().toString());
        viewModelCompany.getCompany().setEmail(b.txtEmail.getText().toString());
        viewModelCompany.getCompany().setUrlLogo(b.txtUrlLogo.getText().toString());
        viewModelCompany.getCompany().setContactName(b.txtNameContact.getText().toString());
    }
}
