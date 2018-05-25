package com.example.viktor.myweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        String text = getIntent().getExtras().getString(MainActivity.CITY); // получить данные из Intent
        WeatherPresenter presenter = new WeatherPresenter(text);
        presenter.checkWeather();
        boolean show_pres = getIntent().getExtras().getBoolean(MainActivity.SHOW_PRESSURE); // получить данные из Intent
        boolean show_hum = getIntent().getExtras().getBoolean(MainActivity.SHOW_HUMIDITY); // получить данные из Intent

        TextView textView = findViewById(R.id.cityTitleView);
        textView.setText(text);

        TextView temperatureView = findViewById(R.id.temperatureDetail);
        temperatureView.setText(((Integer) presenter.getTemperature()).toString());

        if (show_pres) {
            TextView pressureView = findViewById(R.id.pressureDetail);
            pressureView.setText(String.format("%s %s", getResources().getString(R.string.pressureDetailHeader), ((Integer) presenter.getPressure()).toString()));
        }

        if (show_hum) {
            TextView humidityView = findViewById(R.id.humidityDetail);
            humidityView.setText(String.format("%s %s", getResources().getString(R.string.humidityDetailHeader), ((Integer) presenter.getHumidity()).toString()));
        }
    }
}
