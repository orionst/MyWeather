package com.example.viktor.myweather;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        final Parcel parcel = getParcel();

        WeatherPresenter presenter = new WeatherPresenter(parcel.getCity().getCityName());
        presenter.checkWeather();

        final View layout = inflater.inflate(R.layout.fragment_detailed, container, false);

        final TextView cityView = layout.findViewById(R.id.cityTitleView);
        cityView.setText(parcel.getCity().getCityName());

        final TextView temperatureView = layout.findViewById(R.id.temperatureDetail);
        temperatureView.setText(((Integer) presenter.getTemperature()).toString());

        final Button buttonHourlyWeather = layout.findViewById(R.id.buttonHourlyWeather);
        buttonHourlyWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = layout.findViewById(R.id.buttonHourlyWeather);
                showHourlyWeather(parcel);
            }
        });

        if (parcel.isShowPressure()) {
            final TextView pressureView = layout.findViewById(R.id.pressureDetail);
            pressureView.setText(String.format("%s %s", getResources().getString(R.string.pressureDetailHeader), ((Integer) presenter.getPressure()).toString()));
        }

        if (parcel.isShowHumidity()) {
            final TextView humidityView = layout.findViewById(R.id.humidityDetail);
            humidityView.setText(String.format("%s %s", getResources().getString(R.string.humidityDetailHeader), ((Integer) presenter.getHumidity()).toString()));
        }

        return layout;
    }

    public Parcel getParcel() {
        Parcel parcel = (Parcel) getArguments().getSerializable(PARCEL);
        return parcel;
    }

    private void showHourlyWeather(Parcel parcel) {

//        HourlyFragment fragment2 = (HourlyFragment) getFragmentManager().findFragmentById(R.id.detailed_weather);
//        fragment2 = HourlyFragment.create(parcel);
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.detailed_weather, fragment2);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();


        // Проверим, что детальный фрагмент существует в активити
            HourlyFragment detail = new HourlyFragment();
            detail = HourlyFragment.create(parcel);
            // если есть необходимость, то выведем инфу о городе
                // Создаем новый фрагмент, с текущей позицией, для вывода герба

                // Выполняем транзакцию по замене фрагмента
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.detailed_weather, detail);  // замена фрагмента
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();

    }


}
