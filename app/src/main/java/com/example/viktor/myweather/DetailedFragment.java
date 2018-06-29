package com.example.viktor.myweather;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viktor.myweather.tools.OnRecyclerAdapterUpdateListener;
import com.example.viktor.myweather.tools.Parcel;
import com.example.viktor.myweather.tools.RecyclerAdapter;
import com.example.viktor.myweather.tools.SharePref;

public class DetailedFragment extends android.support.v4.app.Fragment implements ParcelInCityFragment {

    public static final String PARCEL = "parcel";
    Parcel parcel;
    ImageView imgCityFavorite;

    private OnRecyclerAdapterUpdateListener raListener;

    SharePref sharedPreferences;

    // фабричный метод, создает фрагмент и передает параметр
    public static DetailedFragment create(Parcel parcel) {
        DetailedFragment f = new DetailedFragment();    // создание

        // передача параметра
        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            raListener = (OnRecyclerAdapterUpdateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnRecyclerAdapterUpdateListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View layout = inflater.inflate(R.layout.fragment_detailed_hourly, container, false);

        sharedPreferences = SharePref.getInstance(getContext());

        parcel = getParcel();

        final TextView cityView = layout.findViewById(R.id.city_name);
        cityView.setText(parcel.getCity().getCityName());

        final TextView temperatureView = layout.findViewById(R.id.temperatureDetail);
        temperatureView.setText(String.format("%s %s%s",
                getString(R.string.temperatureDetailHeader),
                ((Integer) parcel.getCity().getTemperature()).toString(),
                getString(R.string.marker_degree)));

        imgCityFavorite = layout.findViewById(R.id.image_city_favorite);
        if (parcel.getCity().getCityName().equals(sharedPreferences.getFavoriteCity())) {
            imgCityFavorite.setVisibility(View.VISIBLE);
        }

        Toolbar toolbar = layout.findViewById(R.id.detailed_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        if (getActivity() instanceof DetailedActivity) {
            ((DetailedActivity) getActivity()).setSupportActionBar(toolbar);
        } else {
            ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        }
        setHasOptionsMenu(true);

        RecyclerView recyclerView = layout.findViewById(R.id.hourly_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(layout.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(parcel.getCity(), getResources());
        recyclerView.setAdapter(adapter);

//        закомментировано, так как отказался от отдельного фрагмента детальной погоды
//        final Activity that = getActivity();
//        final Button btnHourlyView = layout.findViewById(R.id.buttonHourlyWeather);
//        btnHourlyView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentsNavigator mListener = (FragmentsNavigator) that;
//                mListener.startFragment(parcel);
//            }
//        });

//        if (parcel.isShowPressure()) {
//            final TextView pressureView = layout.findViewById(R.id.pressureDetail);
//            pressureView.setText(String.format("%s %s", getResources().getString(R.string.pressureDetailHeader), ((Integer) parcel.getCity().getPressure()).toString()));
//        }
//
//        if (parcel.isShowHumidity()) {
//            final TextView humidityView = layout.findViewById(R.id.humidityDetail);
//            humidityView.setText(String.format("%s %s", getResources().getString(R.string.humidityDetailHeader), ((Float) parcel.getCity().getHumidity()).toString()));
//        }

        return layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        if (parcel.getCity().getCityName().equals(sharedPreferences.getFavoriteCity())) {
            menu.findItem(R.id.action_set_favorite_city).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пунктов меню         ​
        switch (item.getItemId()) {
            case R.id.action_set_favorite_city:
                if (item.isChecked()) {
                    sharedPreferences.saveFavoriteCity("");
                    imgCityFavorite.setVisibility(View.INVISIBLE);
                    refreshCitiesList("");
                } else {
                    sharedPreferences.saveFavoriteCity(parcel.getCity().getCityName());
                    imgCityFavorite.setVisibility(View.VISIBLE);
                    refreshCitiesList(parcel.getCity().getCityName());
                }
                item.setChecked(!item.isChecked());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refreshCitiesList(String favoriteCity) {
        raListener.OnCitiesListUpdate(favoriteCity);
    }

    public Parcel getParcel() {
        Parcel parcel = (Parcel) getArguments().getSerializable(PARCEL);
        return parcel;
    }
}
