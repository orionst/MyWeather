package com.example.viktor.myweather;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HourlyFragment extends Fragment {

    public static final String PARCEL = "parcel";

    Parcel parcel;

    private final String[] strings = {"0:00", "6:00", "12:00", "18:00"};

    // фабричный метод, создает фрагмент и передает параметр
//    public static HourlyFragment create(Parcel parcel) {
//        HourlyFragment f = new HourlyFragment();    // создание
//
//        // передача параметра
//        Bundle args = new Bundle();
//        args.putSerializable(PARCEL, parcel);
//        f.setArguments(args);
//        return f;
//    }

//    @SuppressLint("ValidFragment")
//    public HourlyFragment(Parcel parcel) {
//        Bundle args = new Bundle();
//        args.putSerializable(PARCEL, parcel);
//        setArguments(args);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Parcel parcel = getParcel();
        final View layout = inflater.inflate(R.layout.fragment_hourly, container, false);

        RecyclerView recyclerView = layout.findViewById(R.id.hourly_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(layout.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(parcel.getCity(), getResources());
        recyclerView.setAdapter(adapter);

        return layout;
    }

    public Parcel getParcel() {
        Parcel parcel = (Parcel) this.parcel;
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }
}
