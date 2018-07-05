package com.example.viktor.myweather.forecasts.provider.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//"main":{"temp":273.15,"pressure":1000,"humidity":99,"temp_min":273.15,"temp_max":273.15}
public class Main {
    @SerializedName("temp")
    @Expose
    private float temp;
    @SerializedName("pressure")
    @Expose
    private float pressure;
    @SerializedName("humidity")
    @Expose
    private int humidity;
    @SerializedName("temp_min")
    @Expose
    private float temp_min;
    @SerializedName("temp_max")
    @Expose
    private float temp_max;

    public float getTemp() {
        return temp;
    }

    public float getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }
}
