package com.example.viktor.myweather;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.viktor.myweather.tools.FragmentsNavigator;
import com.example.viktor.myweather.tools.Parcel;

public class MainActivity extends AppCompatActivity
        implements FragmentsNavigator, NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void startFragment(Parcel parcel) {
        // Выполняем транзакцию по замене фрагмента
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(getFragmentManager().findFragmentById(R.id.detailed_weather));
        ft.add(R.id.detailed_weather, HourlyFragment.create(parcel));  // замена фрагмента
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack("");
        ft.commit();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                super.onBackPressed();
            } else {
                getFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        int optionId = R.layout.content_main;

        if (id == R.id.nav_temperature_c) {

        } else if (id == R.id.nav_temperature_f) {

        } else if (id == R.id.about) {
//            optionId = R.layout.fragment_about;
        }

//        ViewGroup parent = findViewById(R.id.content_main);
//        parent.removeAllViews();
//        View newContent = getLayoutInflater().inflate(optionId, parent, false);
//        parent.addView(newContent);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
