package com.example.viktor.myweather.forecasts;

import java.io.Serializable;
import java.util.ArrayList;

public class City implements Observer, Serializable{

    private String cityName;
    private float temperature;
    private int humidity;
    private float pressure;
    private String condition;
    private ArrayList<WeatherHistory> weatherHistory;

    public City(WeatherData weatherData, String cityName) {
        this.cityName = cityName;
        this.condition = "";
        weatherData.registerObserver(this);
        weatherHistory = new ArrayList<>();
    }

    @Override
    public void updateActualWeather(float temperature, int humidity, float pressure, String condition) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.condition = condition;
    }

    @Override
    public void addForecastWeatherNode(float temperature, int humidity, float pressure, String condition, String timestamp) {
        weatherHistory.add(new WeatherHistory(temperature, pressure, humidity, condition, timestamp));
    }

    @Override
    public void clearForecastWeatherNodes() {
        weatherHistory.clear();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!City.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final City other = (City) obj;
        if ((this.cityName == null) ? (other.getCityName() != null) : !this.cityName.equals(other.cityName)) {
            return false;
        }
        return true;
    }

    public String getTemperature() {
        if (temperature == 0) {
            return "N/A";
        } else {
            return String.format("%.1f", temperature - 273.15f);
        }
    }

    public int getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCondition() {
        return condition;
    }

    public ArrayList<WeatherHistory> getWeatherHistory() {
        return weatherHistory;
    }

}
