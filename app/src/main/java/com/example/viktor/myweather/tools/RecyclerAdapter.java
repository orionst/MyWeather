package com.example.viktor.myweather.tools;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viktor.myweather.R;
import com.example.viktor.myweather.forecasts.City;
import com.example.viktor.myweather.forecasts.WeatherHistory;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<WeatherHistory> cityHistory;
    private Resources resources;

    public RecyclerAdapter(City city, Resources resources) {
        this.cityHistory = city.getWeatherHistory();
        this.resources = resources;
    }

    public void setNewArray(City city) {
        this.cityHistory = city.getWeatherHistory();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(cityHistory.get(position).getTimeStamp());
        if (cityHistory.get(position).getCondition().equals("Clear")) {
            holder.picture.setImageResource(R.drawable.sunny);
        } else {
            holder.picture.setImageResource(R.drawable.rain_s_cloudy);
        }
        holder.temperature.setText(String.format("%s %s%s",
                resources.getString(R.string.temperatureDetailHeader),
                cityHistory.get(position).getTemperature(),
                resources.getString(R.string.marker_degree)));
        holder.pressure.setText(String.format("%s %s",
                resources.getString(R.string.pressureDetailHeader),
                Float.toString(cityHistory.get(position).getPressure())));
        holder.humidity.setText(String.format("%s %s",
                resources.getString(R.string.humidityDetailHeader),
                Float.toString(cityHistory.get(position).getHumidity())));
    }

    @Override
    public int getItemCount() {
        return cityHistory.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView picture;
        private TextView temperature;
        private TextView humidity;
        private TextView pressure;

        public ViewHolder(@NonNull final View item) {
            super(item);
            //if (getLayoutPosition() != RecyclerView.NO_POSITION) {
                textView = item.findViewById(R.id.time_stamp);
                picture = item.findViewById(R.id.picture);
                temperature = item.findViewById(R.id.temperatureHourly);
                humidity = item.findViewById(R.id.humidityHourly);
                pressure = item.findViewById(R.id.pressureHourly);
            //}
        }
    }
}

