package com.example.rnv_pr10_fct.ui.student;

import com.example.rnv_pr10_fct.data.Repository;
import com.example.rnv_pr10_fct.data.local.model.Student;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class StudentMainViewModel extends ViewModel {

    private final Repository repository;
    private LiveData<List<Student>> students;
    private Student student;
    private boolean isEdit;

    public StudentMainViewModel(Repository repository) {
        this.repository = repository;
        students = repository.queryStudents();
    }

    public LiveData<List<Student>> getStudents(){
        return students;
    }

    public void insertStudent(Student student){
        repository.insertStudent(student);
    }

    public int updateStudent(Student student){
        return repository.updateStudent(student);
    }

    public int deleteStudent(Student student){
        return repository.deleteStudent(student);
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
