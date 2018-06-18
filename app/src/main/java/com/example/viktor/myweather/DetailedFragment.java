package com.example.viktor.myweather;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viktor.myweather.tools.Parcel;
import com.example.viktor.myweather.tools.RecyclerAdapter;

public class DetailedFragment extends Fragment implements SimpleCityFragment {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Parcel parcel = getParcel();

        final View layout = inflater.inflate(R.layout.fragment_detailed_hourly, container, false);

        final TextView cityView = layout.findViewById(R.id.city_name);
        cityView.setText(parcel.getCity().getCityName());

        final TextView temperatureView = layout.findViewById(R.id.temperatureDetail);
        temperatureView.setText(String.format("%s %s%s",
                getString(R.string.temperatureDetailHeader),
                ((Integer) parcel.getCity().getTemperature()).toString(),
                getString(R.string.marker_degree)));

        Toolbar toolbar = layout.findViewById(R.id.detailed_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пунктов меню         ​
        switch (item.getItemId()) {
            case R.id.action_settings:
                Snackbar.make(getView(), "Пример меню", Snackbar.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public Parcel getParcel() {
        Parcel parcel = (Parcel) getArguments().getSerializable(PARCEL);
        return parcel;
    }
}
