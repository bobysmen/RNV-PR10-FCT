package com.example.rnv_pr10_fct.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rnv_pr10_fct.R;
import com.example.rnv_pr10_fct.data.RepositoryImpl;
import com.example.rnv_pr10_fct.data.local.AppDatabase;
import com.example.rnv_pr10_fct.ui.company.CompanyMainViewModel;
import com.example.rnv_pr10_fct.ui.company.CompanyMainViewModelFactory;
import com.example.rnv_pr10_fct.ui.student.StudentMainViewModel;
import com.example.rnv_pr10_fct.ui.student.StudentMainViewModelFactory;
import com.example.rnv_pr10_fct.ui.visit.VisitMainViewModel;
import com.example.rnv_pr10_fct.ui.visit.VisitMainViewModelFactory;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;
    private SharedPreferences setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        setting = PreferenceManager.getDefaultSharedPreferences(this);
        setupNavigationGraph();
        setupViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setting.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        setting.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    private void setupNavigationGraph() {
        navController.getNavInflater();
        NavInflater navInflater = navController.getNavInflater();
        NavGraph navGraph = navInflater.inflate(R.navigation.main_navigation);
        int startDestinationResId = 0;

        switch (setting.getString(getResources().getString(R.string.pref_listKey), "nextVisits")){
            case "companies":
                startDestinationResId = R.id.companyMainFragment;
                break;
            case "students":
                startDestinationResId = R.id.studentMainFragment;
                break;
            case "visits":
                startDestinationResId = R.id.visitMainFragment;
                break;
            case "nextVisits":
                startDestinationResId = R.id.nextVisitMainFragment;
        }

        navGraph.setStartDestination(startDestinationResId);
        navController.setGraph(navGraph);
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
                        R.id.companyMainFragment, R.id.studentMainFragment, R.id.visitMainFragment, R.id.nextVisitMainFragment)
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

//    private void navigateToStudent() {
//        Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.action_companyMainFragment_to_studentMainFragment);
//    }
//
//    private void navigateToCompany() {
//        Navigation.findNavController(this, R.id.navHostFragment).navigate(R.id.action_studentMainFragment_to_companyMainFragment);
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuPreferences:
                navigateToPreference();
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    private void navigateToPreference() {
        navController.navigate(R.id.preference);
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
