package com.example.viktor.myweather;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viktor.myweather.tools.Parcel;
import com.example.viktor.myweather.tools.RecyclerAdapter;

public class HourlyFragment extends Fragment implements ParcelInCityFragment {

    public static final String PARCEL = "parcel";

    Parcel parcel;

    // фабричный метод, создает фрагмент и передает параметр
    public static HourlyFragment create(Parcel parcel) {
        HourlyFragment f = new HourlyFragment();    // создание

        // передача параметра
        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(PARCEL)) {
            parcel = (Parcel) getArguments().getSerializable(PARCEL);
        } else {
            throw new IllegalArgumentException("Must be created through newInstance(...)");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Parcel parcel = getParcel();
        final View layout = inflater.inflate(R.layout.fragment_hourly, container, false);
        final TextView city_title = layout.findViewById(R.id.cityTitleView);
        city_title.setText(parcel.getCity().getCityName());

        RecyclerView recyclerView = layout.findViewById(R.id.hourly_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(layout.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(parcel.getCity(), getResources());
        recyclerView.setAdapter(adapter);

        return layout;
    }

    public Parcel getParcel() {
        Parcel parcel = this.parcel;
        return parcel;
    }

}
