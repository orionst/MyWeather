package com.example.viktor.myweather;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.viktor.myweather.tools.FragmentsNavigator;
import com.example.viktor.myweather.tools.Parcel;

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

        final Parcel parcel = getParcel();

        final View layout = inflater.inflate(R.layout.fragment_detailed, container, false);

        final TextView cityView = layout.findViewById(R.id.cityTitleView);
        cityView.setText(parcel.getCity().getCityName());

        final TextView temperatureView = layout.findViewById(R.id.temperatureDetail);
        temperatureView.setText(((Integer) parcel.getCity().getTemperature()).toString());

        final Activity that = getActivity();
        final Button btnHourlyView = layout.findViewById(R.id.buttonHourlyWeather);
        btnHourlyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentsNavigator mListener = (FragmentsNavigator) that;
                mListener.startFragment(parcel);
            }
        });

        if (parcel.isShowPressure()) {
            final TextView pressureView = layout.findViewById(R.id.pressureDetail);
            pressureView.setText(String.format("%s %s", getResources().getString(R.string.pressureDetailHeader), ((Integer) parcel.getCity().getPressure()).toString()));
        }

        if (parcel.isShowHumidity()) {
            final TextView humidityView = layout.findViewById(R.id.humidityDetail);
            humidityView.setText(String.format("%s %s", getResources().getString(R.string.humidityDetailHeader), ((Float) parcel.getCity().getHumidity()).toString()));
        }

        return layout;
    }

    public Parcel getParcel() {
        Parcel parcel = (Parcel) getArguments().getSerializable(PARCEL);
        return parcel;
    }
}
