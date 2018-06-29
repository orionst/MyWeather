package com.example.viktor.myweather.forecasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.LinkedList;
import java.util.List;

public class WeatherData extends BroadcastReceiver implements Observable {
    private static WeatherData weatherData;
    private List<Observer> observers;
    private int temperature;
    private float humidity;
    private int pressure;

    private WeatherData() {
        observers = new LinkedList<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public static WeatherData init() {
        if (weatherData == null) {
            weatherData = new WeatherData();
        }
        return weatherData;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update(temperature, humidity, pressure);
    }

    public void setMeasurements(int temperature, float humidity, int pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }

    public void getAllWeatherForecast(Context context) {
        for (Observer observer : observers) {
            getWeatherForecast(observer, context);
        }
    }

    public void getWeatherForecast(Observer observer, Context context) {

        Intent weatherUpdaterIntent = new Intent(context, WeatherUpdaterService.class);
        weatherUpdaterIntent.putExtra("City", ((City) observer).getCityName());
        context.startService(weatherUpdaterIntent);

        City cityObserver = (City) observer;
        if (cityObserver.getCityName().equals("Москва") || cityObserver.getCityName().equals("Moscow")) {
            observer.update(10, 20, 747);
        } else if (cityObserver.getCityName().equals("Санкт-Петербург") || cityObserver.getCityName().equals("Saint-Petersburg")) {
            observer.update(15, 35, 747);
        } else {
            observer.update(20, 10, 737);
        }

        for (int i = 0; i < 4; i++) {
            cityObserver.weatherHistory[i] = new WeatherHistory(10 + i * 2, 777, 32, "" + (i * 6) + ":00");
        }
    }

}