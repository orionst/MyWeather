package com.example.viktor.myweather.forecasts;

public interface Observer {
    void updateActualWeather(float temperature, int humidity, float pressure, String condition);
    void addForecastWeatherNode(float temperature, int humidity, float pressure, String condition, String timestamp);
    void clearForecastWeatherNodes();
}