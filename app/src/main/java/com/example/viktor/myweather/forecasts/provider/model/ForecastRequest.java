package com.example.viktor.myweather.forecasts.provider.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecastRequest {
    @SerializedName("cnt")
    @Expose
    private int cnt;
    @SerializedName("list")
    @Expose
    private List[] list;
    @SerializedName("cod")
    @Expose
    private int cod;

    public int getCnt() {
        return cnt;
    }

    public List[] getList() {
        return list;
    }

    public int getCod() {
        return cod;
    }
}
