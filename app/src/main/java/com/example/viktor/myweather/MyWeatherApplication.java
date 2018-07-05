package com.example.viktor.myweather;

import android.app.Application;

import com.example.viktor.myweather.forecasts.provider.OpenWeather;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyWeatherApplication extends Application {

    private OpenWeather weatherProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        MyWeatherSingleton.init(this);
        initRetorfit();
    }

    private void initRetorfit(){
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .client(createOkHttpClient())
                .build();
        weatherProvider = retrofit.create(OpenWeather.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    private OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder htClientBuilder = new OkHttpClient.Builder();
        htClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return htClientBuilder.build();
    }

    public OpenWeather getWeatherProvider() {
        return weatherProvider;
    }
}
