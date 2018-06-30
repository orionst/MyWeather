package com.example.viktor.myweather.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.viktor.myweather.forecasts.City;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class SharePref {

    private SharePref() {}

    public static void saveFavoriteCity(Context context, String placeObjStr) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.SHARED, Activity.MODE_PRIVATE).edit();
        editor.putString(Constants.CITY_FAVORITE, placeObjStr);
        editor.apply();
    }

    public static String getFavoriteCity(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.CITY_FAVORITE, Constants.EMPTY_FAVORITE_CITY);
    }

    public static void removeFavoriteCity(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.SHARED, Activity.MODE_PRIVATE).edit();
        editor.remove(Constants.CITY_FAVORITE);
        editor.apply();
    }


    public static void saveCitiesList(Context context, ArrayList<City> placeObjStr) {
        Set<String> placeObjStrSet = new HashSet<>(placeObjStr.size());
        for (City o: placeObjStr) {
            placeObjStrSet.add(o.getCityName());
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.SHARED, Activity.MODE_PRIVATE).edit();
        editor.putStringSet(Constants.CITIES_LIST, placeObjStrSet);
        editor.apply();
    }

    public static Set getCitiesList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED, Activity.MODE_PRIVATE);
        return sharedPreferences.getStringSet(Constants.CITIES_LIST, null);
    }

    public static void removeCitiesList(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.SHARED, Activity.MODE_PRIVATE).edit();
        editor.remove(Constants.CITIES_LIST);
        editor.apply();
    }


    public static void clearAll(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.SHARED, Activity.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

}