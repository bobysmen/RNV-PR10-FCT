package com.example.rnv_pr10_fct.data.local;

import android.content.Context;

import com.example.rnv_pr10_fct.data.local.model.Company;
import com.example.rnv_pr10_fct.data.local.model.Student;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Company.class, Student.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "DATABASE_NAME";
    private static volatile AppDatabase instance;

    public abstract CompanyDao companyDao();
    public abstract StudentDao studentDao();
    public abstract VisitDao visitDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
