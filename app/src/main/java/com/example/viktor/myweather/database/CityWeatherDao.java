package com.example.viktor.myweather.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CityWeatherDao {

    @Query("SELECT * FROM CityWeatherEntity WHERE cityname LIKE :cityname")
    List<CityWeatherEntity> getWeatherByCity(String cityname);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CityWeatherEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CityWeatherEntity> entities);

    @Update
    void update(CityWeatherEntity entity);

    @Delete
    void delete(CityWeatherEntity entity);
}
