package com.example.rnv_pr10_fct.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rnv_pr10_fct.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        setupViews();
    }

    private void setupViews() {
        setupToolbar();
        setupNavigationDrawer();
    }

    private void setupNavigationDrawer() {
//        drawer = ActivityCompat.requireViewById(this, R.id.drawerLayout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.main_open_navigation_drawer, R.string.main_close_navigation_drawer);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        navigationView = ActivityCompat.requireViewById(this, R.id.navigationView);
//        navigationView.setNavigationItemSelectedListener(this);

        NavigationView navigationView =
                ActivityCompat.requireViewById(this, R.id.navigationView);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void setupToolbar() {
        toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        drawer = ActivityCompat.requireViewById(this, R.id.drawerLayout);
        setSupportActionBar(toolbar);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(
                        R.id.companyMainFragment, R.id.studentMainFragment)
                        .setDrawerLayout(drawer)
                        .build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        switch (menuItem.getItemId()){
//            case R.id.mnuCompany:
//                navigateToCompany();
//                menuItem.setChecked(true);
//                break;
//            case R.id.mnuStudent:
//                navigateToStudent();
//                menuItem.setChecked(true);
//                break;
//        }
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private void navigateToStudent() {
        Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.action_companyMainFragment_to_studentMainFragment);
    }

    private void navigateToCompany() {
        Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.action_studentMainFragment_to_companyMainFragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
