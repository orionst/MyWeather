package com.example.viktor.myweather;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class StartDetailedActivity implements View.OnClickListener{

    private Activity sourceActivity;

    public StartDetailedActivity(Activity sourceActivity) {
        this.sourceActivity = sourceActivity;
    }

    @Override
    public void onClick(View view) {
        EditText city = sourceActivity.findViewById(R.id.cityName);
        Switch show_hum = sourceActivity.findViewById(R.id.switchHumidity);
        Switch show_pres = sourceActivity.findViewById(R.id.switchPressure);

        Intent intent = new Intent(sourceActivity, DetailedActivity.class);
        intent.putExtra(MainActivity.CITY, city.getText().toString());
        intent.putExtra(MainActivity.SHOW_HUMIDITY, show_hum.isChecked());
        intent.putExtra(MainActivity.SHOW_PRESSURE, show_pres.isChecked());
        sourceActivity.startActivity(intent);
    }
}
