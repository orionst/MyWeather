package com.example.viktor.myweather;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.viktor.myweather.forecasts.City;
import com.example.viktor.myweather.forecasts.WeatherData;

import java.util.HashMap;

import static com.example.viktor.myweather.DetailedFragment.PARCEL;

public class CitiesFragment extends ListFragment{

    boolean isExistDetailedView;
    Parcel currentParcel;
    HashMap<String, City> cities;
    WeatherData weatherData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Cities,
                android.R.layout.simple_list_item_activated_1);

        setListAdapter(adapter);

        View detailedFrame = getActivity().findViewById(R.id.detailed_weather);
        isExistDetailedView = (detailedFrame != null) && (detailedFrame.getVisibility() == View.VISIBLE);

        if (savedInstanceState != null) {
            currentParcel = (Parcel) savedInstanceState.getSerializable("CurrentCity");
            cities = (HashMap<String, City>) savedInstanceState.getSerializable("CitiesList");
            weatherData = (WeatherData) savedInstanceState.getSerializable("WeatherData");
        } else {
            weatherData = new WeatherData();
            CharSequence[] citiesNames = getResources().getTextArray(R.array.Cities);
            cities = new HashMap<>();
            for (CharSequence citiesName : citiesNames) {
                cities.put(citiesName.toString(), new City(weatherData, citiesName.toString()));
            }
            currentParcel = new Parcel(cities.get(citiesNames[0].toString()));
        }

        if (isExistDetailedView) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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

    // Обработка выбора позиции
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        TextView cityNameView = (TextView) v;
        currentParcel =  new Parcel(cities.get(cityNameView.getText().toString()));
        showDetails(currentParcel);
    }

    private void showDetails(Parcel parcel) {
        if (isExistDetailedView) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            // Проверим, что детальный фрагмент существует в активити
            DetailedFragment detail = (DetailedFragment) getFragmentManager().findFragmentById(R.id.detailed_weather);
            // если есть необходимость, то выведем инфу о городе
            if (detail == null || !detail.getParcel().getCity().equals(parcel.getCity())) {

                // Создаем новый фрагмент, с текущей позицией, для вывода герба
                detail = DetailedFragment.create(parcel);

                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.detailed_weather, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            Intent  intent = new Intent();
            intent.setClass(getActivity(), DetailedActivity.class);

            intent.putExtra(PARCEL, parcel);
            startActivity(intent);
        }
    }
}