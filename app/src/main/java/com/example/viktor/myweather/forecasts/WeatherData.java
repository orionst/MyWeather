package com.example.viktor.myweather.forecasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.viktor.myweather.MyWeatherApplication;
import com.example.viktor.myweather.MyWeatherSingleton;
import com.example.viktor.myweather.R;
import com.example.viktor.myweather.database.CityWeatherEntity;
import com.example.viktor.myweather.forecasts.provider.model.ForecastRequest;
import com.example.viktor.myweather.forecasts.provider.model.WeatherRequest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherData extends BroadcastReceiver implements Observable {

    private final String OPEN_WEATHER_API_KEY = "c9b03892683779b5f3be88ab7a4d5b90";

    private static WeatherData weatherData;
    private List<Observer> observers;
    private float temperature;
    private int humidity;
    private int pressure;
    private String condition;

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
            observer.updateActualWeather(temperature, humidity, pressure, condition);
    }

    public void setMeasurements(float temperature, int humidity, int pressure, String condition) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.condition = condition;
        notifyObservers();
    }

    public void getAllActualWeather(Context context) {
        for (Observer observer : observers) {
            getActualWeather(observer, context);
        }
    }

    // загрузка акутальной погоды
    public void getActualWeather(final Observer observer, final Context context) {

//        Intent weatherUpdaterIntent = new Intent(context, WeatherUpdaterService.class);
//        weatherUpdaterIntent.putExtra("City", ((City) observer).getCityName());
//        context.startService(weatherUpdaterIntent);

        City cityObserver = (City) observer;
        ((MyWeatherApplication) context.getApplicationContext()).getWeatherProvider()
                .loadWeather(cityObserver.getCityName(), OPEN_WEATHER_API_KEY)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null) {
                            observer.updateActualWeather(response.body().getMain().getTemp(),
                                    response.body().getMain().getHumidity(),
                                    response.body().getMain().getPressure(),
                                    response.body().getWeather()[0].getMain());
                        } else {
                            observer.updateActualWeather(0,
                                    0,
                                    0,
                                    "Clear");
                            Toast.makeText(context, "Weather for "+((City) observer).getCityName()+" not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        Toast.makeText(context, R.string.error_weather_updating, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getAllWeatherForecast(Context context) {
        for (Observer observer : observers) {
            getWeatherForecast(observer, context);
        }
    }

    // загрузка прогноза погоды на 6 дней каждые 3 часа
    public void getWeatherForecast(final Observer observer, final Context context) {

        City cityObserver = (City) observer;
        ((MyWeatherApplication) context.getApplicationContext()).getWeatherProvider()
                .loadForecast(cityObserver.getCityName(), OPEN_WEATHER_API_KEY)
                .enqueue(new Callback<ForecastRequest>() {
                    @Override
                    public void onResponse(Call<ForecastRequest> call, Response<ForecastRequest> response) {
                        if (response.body() != null) {

                            List<CityWeatherEntity> entities = new ArrayList<>();
                            //закомментированно, так как данные теперь хранятся в БД
//                            observer.clearForecastWeatherNodes();

                            for (int i = 0; i < response.body().getCnt(); i++) {
                                com.example.viktor.myweather.forecasts.provider.model.List list = response.body().getList()[i];
                                //закомментированно, так как данные теперь хранятся в БД
//                                observer.addForecastWeatherNode(list.getMain().getTemp(),
//                                        list.getMain().getHumidity(),
//                                        list.getMain().getPressure(),
//                                        list.getWeather()[0].getMain(),
//                                        list.getDt_txt());

                                entities.add(new CityWeatherEntity(0,
                                        ((City) observer).getCityName(),
                                        list.getDt_txt(),
                                        list.getMain().getTemp(),
                                        list.getMain().getPressure(),
                                        list.getMain().getHumidity(),
                                        list.getWeather()[0].getMain()));
                          }
                            MyWeatherSingleton.get().getDatabase().cityWeatherDAO().insertAll(entities);
                        }
                    }

                    @Override
                    public void onFailure(Call<ForecastRequest> call, Throwable t) {
                        Toast.makeText(context, R.string.error_forecast_updating, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}