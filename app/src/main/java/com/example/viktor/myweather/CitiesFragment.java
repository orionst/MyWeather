package com.example.viktor.myweather;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viktor.myweather.forecasts.City;
import com.example.viktor.myweather.forecasts.WeatherData;
import com.example.viktor.myweather.tools.CityListRecyclerAdapter;
import com.example.viktor.myweather.tools.Parcel;

import java.util.ArrayList;

import static com.example.viktor.myweather.DetailedFragment.PARCEL;

public class CitiesFragment extends Fragment {

    boolean isExistDetailedView;
    Parcel currentParcel;
    ArrayList<City> cities;
    WeatherData weatherData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_cities_list, container, false);

        if (savedInstanceState != null) {
            currentParcel = (Parcel) savedInstanceState.getSerializable("CurrentCity");
            cities = (ArrayList<City>) savedInstanceState.getSerializable("CitiesList");
            weatherData = (WeatherData) savedInstanceState.getSerializable("WeatherData");
        } else {
            weatherData = new WeatherData();
            CharSequence[] citiesNames = getResources().getTextArray(R.array.Cities);
            cities = new ArrayList<>();
            for (CharSequence citiesName : citiesNames) {
                cities.add(new City(weatherData, citiesName.toString()));
            }
            weatherData.getAllWeatherForecast();
            currentParcel = new Parcel(cities.get(0));
        }

        if (!cities.isEmpty()) {
            RecyclerView recyclerView = layout.findViewById(R.id.cities_list_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(layout.getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            CityListRecyclerAdapter adapter = new CityListRecyclerAdapter(cities);
            recyclerView.setAdapter(adapter);

            adapter.SetOnItemClickListener(new CityListRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    currentParcel = new Parcel(cities.get(position));
                    showDetails(currentParcel);
                }
            });

            TextView emptyListView = layout.findViewById(R.id.empty_city_list);
            emptyListView.setVisibility(View.GONE);
        }

        FloatingActionButton fab = layout.findViewById(R.id.fab);
        // Обработка нажатия на плавающую кнопку
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Здесь вылетит Snackbar
                Snackbar.make(view, "Добавляем город в список", Snackbar.LENGTH_LONG).show();
            }
        });

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View detailedFrame = getActivity().findViewById(R.id.detailed_weather);
        isExistDetailedView = (detailedFrame != null) && (detailedFrame.getVisibility() == View.VISIBLE);

        if (isExistDetailedView) {
            showDetails(currentParcel);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("CurrentCity", currentParcel);
        outState.putSerializable("CitiesList", cities);
        outState.putSerializable("WeatherData", weatherData);
    }

    private void showDetails(Parcel parcel) {
        if (isExistDetailedView) {
            // Проверим, что детальный фрагмент существует в активити
            SimpleCityFragment detail = (SimpleCityFragment) getFragmentManager().findFragmentById(R.id.detailed_weather);
            // если есть необходимость, то выведем инфу о городе
            if (detail == null || !(detail instanceof DetailedFragment) || !detail.getParcel().getCity().equals(parcel.getCity())) {
                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.detailed_weather, DetailedFragment.create(parcel));  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailedActivity.class);

            intent.putExtra(PARCEL, parcel);
            startActivity(intent);
        }
    }
}
