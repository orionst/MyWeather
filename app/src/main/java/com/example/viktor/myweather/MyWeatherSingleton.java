package com.example.viktor.myweather;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.viktor.myweather.database.CityWeatherDatabase;

public final class MyWeatherSingleton {

    private static final String DATABASE_NAME = "MyWeatherDatabase";

    private static MyWeatherSingleton singleton;
    private CityWeatherDatabase dataBase;

    public MyWeatherSingleton(@NonNull Context context) {
        dataBase = Room.databaseBuilder(context, CityWeatherDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    static  void init(@NonNull Context context) {
        if (singleton == null) {
            singleton = new MyWeatherSingleton(context);
        }
    }

    public static MyWeatherSingleton get() {
        return singleton;
    }

    public CityWeatherDatabase getDatabase() {
        return dataBase;
    }
}
