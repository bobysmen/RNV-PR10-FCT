package com.example.rnv_pr10_fct.data.local;

import com.example.rnv_pr10_fct.data.local.model.Student;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StudentDao {

    @Query("Select * from Student")
    LiveData<List<Student>> queryStudents();

    @Query("Select name from Student where id=:id")
    String getNameStudent(Long id);

    @Insert
    long insertStudent(Student student);

    @Update
    int updateStudent(Student student);

    @Delete
    int deleteStudent(Student student);
}
