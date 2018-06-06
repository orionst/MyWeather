package com.example.viktor.myweather.tools;

import com.example.viktor.myweather.forecasts.City;

import java.io.Serializable;

public class Parcel implements Serializable {
    private City city;
    private boolean showHumidity;
    private boolean showPressure;

    public City getCity() {
        return city;
    }

    public Parcel(City city) {
        this.city = city;
        this.showHumidity = true;
        this.showPressure = true;
    }

    public boolean isShowHumidity() {
        return showHumidity;
    }

    public boolean isShowPressure() {
        return showPressure;
    }
}