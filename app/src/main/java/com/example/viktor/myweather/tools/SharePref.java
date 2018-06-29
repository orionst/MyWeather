package com.example.viktor.myweather.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    private static SharePref sharePref = new SharePref();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String CITY_FAVORITE = "myweather_settings";

    private SharePref() {}

    public static SharePref getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharePref;
    }

    public void saveFavoriteCity(String placeObjStr) {
        editor.putString(CITY_FAVORITE, placeObjStr);
        editor.commit();
    }

    public String getFavoriteCity() {
        return sharedPreferences.getString(CITY_FAVORITE, "");
    }

    public void removeFavoriteCity() {
        editor.remove(CITY_FAVORITE);
        editor.commit();
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }

}