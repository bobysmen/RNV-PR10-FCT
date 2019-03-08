package com.example.rnv_pr10_fct.data.local.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.SET_NULL;

@Entity(indices = @Index("idCompany"),
        foreignKeys = @ForeignKey(entity = Company.class,
                                    parentColumns = "id",
                                    childColumns = "idCompany",
                                    onDelete = SET_NULL,
                                    onUpdate = SET_NULL))
public class Student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "grade")
    private String grade;

    @ColumnInfo(name = "nameTutor")
    private String nameTutor;

    @ColumnInfo(name = "phoneTutor")
    private String phoneTutor;

    @ColumnInfo(name = "workHours")
    private String workHours;

    @ColumnInfo(name = "idCompany")
    private Long idCompany;

    @Ignore
    private String nameCompany;

    public Student(String name, String phone, String email, String grade, String nameTutor, String phoneTutor, String workHours, Long idCompany) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.grade = grade;
        this.nameTutor = nameTutor;
        this.phoneTutor = phoneTutor;
        this.workHours = workHours;
        this.idCompany = idCompany;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNameTutor() {
        return nameTutor;
    }

    public void setNameTutor(String nameTutor) {
        this.nameTutor = nameTutor;
    }

    public String getPhoneTutor() {
        return phoneTutor;
    }

    public void setPhoneTutor(String phoneTutor) {
        this.phoneTutor = phoneTutor;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }
}
