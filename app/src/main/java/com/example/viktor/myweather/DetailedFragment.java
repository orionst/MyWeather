package com.example.viktor.myweather;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailedFragment extends Fragment {

    public static final String PARCEL = "parcel";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_detailed, container, false);

        Parcel parcel = getParcel();

        TextView cityView = layout.findViewById(R.id.cityTitleView);
        cityView.setText(parcel.getCityName());

        WeatherPresenter presenter = new WeatherPresenter(parcel.getCityName());
        presenter.checkWeather();

        TextView temperatureView = layout.findViewById(R.id.temperatureDetail);
        temperatureView.setText(((Integer) presenter.getTemperature()).toString());

        if (parcel.isShowPressure()) {
            TextView pressureView = layout.findViewById(R.id.pressureDetail);
            pressureView.setText(String.format("%s %s", getResources().getString(R.string.pressureDetailHeader), ((Integer) presenter.getPressure()).toString()));
        }

        if (parcel.isShowHumidity()) {
            TextView humidityView = layout.findViewById(R.id.humidityDetail);
            humidityView.setText(String.format("%s %s", getResources().getString(R.string.humidityDetailHeader), ((Integer) presenter.getHumidity()).toString()));
        }

        return layout;
    }

    public Parcel getParcel() {
        Parcel parcel = (Parcel) getArguments().getSerializable(PARCEL);
        return parcel;
    }

}
