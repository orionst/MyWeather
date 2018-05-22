package com.example.viktor.myweather;

public final class WeatherPresenter {

    private static WeatherPresenter instance = null;

    private int temperature;
    private int pressure;
    private int humidity;
    private String city;

    private WeatherPresenter(String city) {
        this.city = city;
        getWeather();
    }

    public void getWeather() {
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

    public static WeatherPresenter getInstance(String city){
        if (instance == null){
            instance = new WeatherPresenter(city);
        }
        return instance;
    }


}
