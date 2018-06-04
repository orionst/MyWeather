package com.example.viktor.myweather.forecasts;

import java.io.Serializable;

public class WeatherHistory implements Serializable {

    private int temperature;
    private int pressure;
    private float humidity;
    private String timeStamp;

    public WeatherHistory(int temperature, int pressure, float humidity, String timeStamp) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.timeStamp = timeStamp;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}

