package com.example.viktor.myweather.forecasts;

public interface Observer {
    void update (int temperature, float humidity, int pressure);
}