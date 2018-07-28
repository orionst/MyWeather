package com.example.viktor.myweather.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(indices = {@Index(value = {"cityname"})})
public class CityWeatherEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int idForecast;

    @NonNull
    @ColumnInfo(name = "cityname")
    private String cityname;

    @NonNull
    @ColumnInfo(name = "timestamp")
    private String timestamp;

    @ColumnInfo(name = "temperature")
    private float temperature;

    @ColumnInfo(name = "pressure")
    private float pressure;

    @ColumnInfo(name = "humidity")
    private int humidity;

    @ColumnInfo(name = "condition")
    private String condition;

    public CityWeatherEntity(int idForecast, @NonNull String cityname, @NonNull String timestamp, float temperature, float pressure, int humidity, String condition) {
        this.cityname = cityname;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.condition = condition;
    }

    @NonNull
    public int getIdForecast() {
        return idForecast;
    }

    @NonNull
    public String getCityname() {
        return cityname;
    }

    @NonNull
    public String getTimestamp() {
        return timestamp;
    }

    public float getTemperature() {
        return temperature;
    }

    public String getUITemperatureInC() {
        if (temperature == 0) {
            return "N/A";
        } else {
            return String.format("%.1f", temperature - 273.15f);
        }
    }

    public float getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getCondition() {
        return condition;
    }
}
