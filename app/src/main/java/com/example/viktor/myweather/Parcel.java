package com.example.viktor.myweather;

import java.io.Serializable;

public class Parcel implements Serializable {
    private String cityName;
    private boolean showHumidity;
    private boolean showPressure;

    public String getCityName() {
        return cityName;
    }

    public Parcel(String cityName) {
        this.cityName = cityName;
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