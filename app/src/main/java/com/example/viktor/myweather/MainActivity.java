package com.example.viktor.myweather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.viktor.myweather.tools.FragmentsNavigator;
import com.example.viktor.myweather.tools.OnRecyclerAdapterUpdateListener;
import com.example.viktor.myweather.tools.Parcel;

public class MainActivity extends AppCompatActivity
        implements FragmentsNavigator, NavigationView.OnNavigationItemSelectedListener, OnRecyclerAdapterUpdateListener, ActivityCompat.OnRequestPermissionsResultCallback {

    TextView textNearbyTemperature;
    TextView textNearbyHumidity;
    SensorManager sensorManager;
    Sensor sensorTemperature;
    Sensor sensorHumidity;

    private static final int PERMISSION_REQUEST_CODE = 10;

    SensorEventListener listenerSensors = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor == sensorTemperature) {
                showNearbyTemperature(event);
            } else if (event.sensor == sensorHumidity) {
                showNearbyHumiduty(event);
            }
        }
    };

    private void showNearbyTemperature(SensorEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.nearby_temperature_title)).
                append(" ").
                append(event.values[0]);
        textNearbyTemperature.setText(stringBuilder.toString());
    }

    private void showNearbyHumiduty(SensorEvent event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.nearby_humidity_title)).
                append(" ").
                append(event.values[0]);
        textNearbyHumidity.setText(stringBuilder.toString());
    }

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

        //находим хеадер и в нем находим нужный view
        View navHeader = navigationView.getHeaderView(0);
        textNearbyTemperature = navHeader.findViewById(R.id.nearby_temperature);
        textNearbyHumidity = navHeader.findViewById(R.id.nearby_humidity);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (sensorHumidity != null) {
            sensorManager.registerListener(listenerSensors, sensorHumidity, sensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorTemperature != null) {
            sensorManager.registerListener(listenerSensors, sensorTemperature, sensorManager.SENSOR_DELAY_NORMAL);
        }

        // Проверим на пермиссии, и если их нет, запросим у пользователя
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else {
            // пермиссии нет, будем запрашивать у пользователя
            requestLocationPermissions();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerSensors, sensorTemperature);
        sensorManager.unregisterListener(listenerSensors, sensorHumidity);
    }

    @Override
    public void startFragment(Parcel parcel) {
        // Выполняем транзакцию по замене фрагмента
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.remove(getSupportFragmentManager().findFragmentById(R.id.detailed_weather));
//        ft.add(R.id.detailed_weather, HourlyFragment.create(parcel));  // замена фрагмента
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.addToBackStack("");
//        ft.commit();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        } else if (id == R.id.nav_get_weather_in_location) {
            optionId = R.layout.fragment_about;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detailed_weather, AboutFragment.create());  // замена фрагмента
        ft.commit();


//        ViewGroup parent = findViewById(R.id.content_main);
//        parent.removeAllViews();
//        View newContent = getLayoutInflater().inflate(optionId, parent, false);
//        parent.addView(newContent);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void OnCitiesListUpdate(String favoriteCity) {
        CitiesFragment fragment = (CitiesFragment) getSupportFragmentManager().findFragmentById(R.id.cities);
        if (fragment != null && fragment.isInLayout()) {
            fragment.reDrawCitiesList(favoriteCity);
        }
    }

    // Запрос пермиссии для геолокации
    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            // Запросим эти две пермиссии у пользователя
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }


}
