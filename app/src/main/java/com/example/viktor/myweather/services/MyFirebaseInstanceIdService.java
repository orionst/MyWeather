package com.example.viktor.myweather.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Получить ключ установки приложения на устройство
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String TAG = "PushIDService";
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    // Метод отправки ключа в вашу БД
    private void sendRegistrationToServer(String refreshedToken){
    }


}
