package com.example.viktor.myweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        TextView textCity = findViewById(R.id.city_name);
        TextView textBody = findViewById(R.id.textAlert);

        // Заполняем данными из уведомления
        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String title = intent.getStringExtra("city");
            String body = intent.getStringExtra("text");
            textCity.setText(title);
            textBody.setText(body);
        }

    }
}
