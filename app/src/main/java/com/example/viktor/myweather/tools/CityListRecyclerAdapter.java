package com.example.viktor.myweather.tools;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viktor.myweather.R;
import com.example.viktor.myweather.forecasts.City;

import java.util.ArrayList;

public class CityListRecyclerAdapter extends RecyclerView.Adapter<CityListRecyclerAdapter.ViewHolder> {

    private ArrayList<City> cities;
    private String favoriteCity;
    private OnItemClickListener itemClickListener;  // Слушатель, будет устанавливаться извне

    public CityListRecyclerAdapter(ArrayList<City> cities, String favoriteCity) {
        this.cities = cities;
        this.favoriteCity = favoriteCity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String city = cities.get(position).getCityName();
        holder.textView.setText(city);
        holder.imgWeather.setImageResource(R.drawable.rain_s_cloudy);
        if (city.equals(favoriteCity)) {
            holder.imgCityFavorite.setVisibility(View.VISIBLE);
        } else {
            holder.imgCityFavorite.setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imgWeather;
        private ImageView imgCityFavorite;

        public ViewHolder(@NonNull final View item) {
            super(item);
            //if (getLayoutPosition() != RecyclerView.NO_POSITION) {
            imgCityFavorite = item.findViewById(R.id.image_city_favorite);
            textView = item.findViewById(R.id.city_name);
            imgWeather = item.findViewById(R.id.picture);

            // обработчик нажатий на этом ViewHolder
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
            //}
        }
    }

    // интерфейс для обработки нажатий как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void favoriteCityChanged(String favoriteCity) {
        this.favoriteCity = favoriteCity;
        notifyDataSetChanged();
    }

}
