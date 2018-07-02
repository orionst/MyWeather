package com.example.viktor.myweather.forecasts;

import java.io.Serializable;

public class WeatherHistory implements Serializable {

    private float temperature;
    private float pressure;
    private int humidity;
    private String timeStamp;
    private String condition;

    public WeatherHistory(float temperature, float pressure, int humidity, String condition, String timeStamp) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.timeStamp = timeStamp;
        this.condition = condition;
    }

    public String getTemperature() {
        return String.format("%.1f", temperature - 273.15f);
    }

    public float getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getCondition() {
        return condition;
    }
}
