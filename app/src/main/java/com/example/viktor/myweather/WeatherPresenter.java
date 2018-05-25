package com.example.viktor.myweather;

public class WeatherPresenter {

    private int temperature;
    private int pressure;
    private int humidity;
    private String city;

    public WeatherPresenter(String city) {
        this.city = city;
    }

    public void checkWeather() {
        if (city.equals("Moscow")) {
            temperature = 23;
            pressure = 737;
            humidity = 28;
        } else if (city.equals("SPB")) {
            temperature = 33;
            pressure = 730;
            humidity = 40;
        } else {
            temperature = -15;
            pressure = 747;
            humidity = 5;
        }
    }

    public int getTemperature() {
        return temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

}
