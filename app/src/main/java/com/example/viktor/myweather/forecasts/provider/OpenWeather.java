package com.example.viktor.myweather.forecasts.provider;

import com.example.viktor.myweather.forecasts.provider.model.ForecastRequest;
import com.example.viktor.myweather.forecasts.provider.model.WeatherRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String cityCountry, @Query("appid") String keyApi);

    @GET("data/2.5/forecast")
    Call<ForecastRequest> loadForecast(@Query("q") String cityCountry, @Query("appid") String keyApi);
}
