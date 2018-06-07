package com.example.viktor.myweather;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.viktor.myweather.tools.FragmentsNavigator;
import com.example.viktor.myweather.tools.Parcel;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class DetailedActivity extends Activity implements FragmentsNavigator {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            // Если устройство перевернули в альбомную ориентацию, то надо это активити закрыть
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // Если это активити запускается первый раз (с каждым новым гербом первый раз)
            // то перенаправим параметр фрагменту
            DetailedFragment details = new DetailedFragment();
            details.setArguments(getIntent().getExtras());
            // Добавим фрагмент на активити
            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }

    @Override
    public void startFragment(Parcel parcel) {
        // Выполняем транзакцию по замене фрагмента
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(getFragmentManager().findFragmentById(android.R.id.content));
        ft.add(android.R.id.content, HourlyFragment.create(parcel));  // замена фрагмента
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack("");
        ft.commit();
    }
}
