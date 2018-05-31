package com.example.viktor.myweather.forecasts;

import java.io.Serializable;

public class City implements Observer, Serializable{

    private String cityName;
    private int temperature;
    private float humidity;
    private int pressure;
    private WeatherData weatherData;

    public City(WeatherData weatherData, String cityName) {
        this.weatherData = weatherData;
        this.cityName = cityName;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(int temperature, float humidity, int pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
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

    public int getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public String getCityName() {
        return cityName;
    }

}
