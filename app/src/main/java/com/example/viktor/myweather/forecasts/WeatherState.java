package com.example.viktor.myweather.forecasts;

abstract class WeatherState {
    float temperature;
    int humidity;
    int pressure;
    String condition;

    public String getTemperature() {
        return String.format("%.1f", temperature - 273.15f);
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public String getCondition() {
        return condition;
    }

}
