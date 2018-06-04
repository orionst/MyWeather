package com.example.viktor.myweather.forecasts;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class WeatherData implements Observable, Serializable {
    private List<Observer> observers;
    private int temperature;
    private float humidity;
    private int pressure;

    public WeatherData() {
        observers = new LinkedList<>();
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

    public void getAllWeatherForecast() {
        for (Observer observer : observers) {
            getWeatherForecast(observer);
        }
    }

    public void getWeatherForecast(Observer observer) {
        if (observer instanceof City) {
            if (((City) observer).getCityName().equals("Москва") || ((City) observer).getCityName().equals("Moscow")) {
                observer.update(10, 20, 747);

            }
            else if (((City) observer).getCityName().equals("Санкт-Петербург") || ((City) observer).getCityName().equals("Saint-Petersburg")) {
                observer.update(15, 35, 747);
            }
            else {
                observer.update(20, 10, 737);
            }

            for (int i = 0; i < 4; i++) {
                ((City) observer).weatherHistory[i] = new WeatherHistory(10, 777, 32, ""+(i*6)+":00");
            }
        }

    }

}