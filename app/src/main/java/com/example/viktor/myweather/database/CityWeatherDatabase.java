package com.example.viktor.myweather.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CityWeatherEntity.class}, version = 1, exportSchema = false)
public abstract class CityWeatherDatabase extends RoomDatabase{
    public abstract CityWeatherDao cityWeatherDAO();
}
