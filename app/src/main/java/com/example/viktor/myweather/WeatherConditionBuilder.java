package com.example.viktor.myweather;

import android.content.res.Resources;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeatherConditionBuilder {

    Resources resources;

    public WeatherConditionBuilder(Resources resources) {
        this.resources = resources;
    }

    public String getTodayDate() {
        String dateTitle = resources.getString(R.string.date_title);
        return String.format("%s %s", dateTitle, new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime()));
    }

}
