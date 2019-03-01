package com.example.rnv_pr10_fct.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rnv_pr10_fct.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
