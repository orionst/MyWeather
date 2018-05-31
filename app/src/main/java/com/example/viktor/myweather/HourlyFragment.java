package com.example.viktor.myweather;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HourlyFragment extends Fragment {

    public static final String PARCEL = "parcel";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Parcel  parcel = getParcel();
        final View layout = inflater.inflate(R.layout.fragment_hourly, container, false);

        final LinearLayout layoutParent = layout.findViewById(R.id.containerHourly);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < 12; i++) {
            TextView textView = new TextView(inflater.getContext());
            textView.setText(""+i);
            textView.setLayoutParams(params);
            layoutParent.addView(textView);
        }

        return layout;
    }

    public Parcel getParcel() {
        Parcel parcel = (Parcel) getArguments().getSerializable(PARCEL);
        return parcel;
    }

}
