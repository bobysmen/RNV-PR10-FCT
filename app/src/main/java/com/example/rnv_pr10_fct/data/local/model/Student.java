package com.example.rnv_pr10_fct.data.local.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index("idCompany"),
        foreignKeys = @ForeignKey(entity = Company.class,
                                    parentColumns = "id",
                                    childColumns = "idCompany"))
public class Student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

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
    private long idCompany;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(long idCompany) {
        this.idCompany = idCompany;
    }
}
