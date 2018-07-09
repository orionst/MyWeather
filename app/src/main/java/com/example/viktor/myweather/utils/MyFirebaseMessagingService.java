package com.example.viktor.myweather.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.viktor.myweather.AlertActivity;
import com.example.viktor.myweather.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    int messageId = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String TAG = "PushMessageService";
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Data: " + remoteMessage.getData());

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            setAlertMessage(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(), remoteMessage.getData());
        }
    }


    private void setAlertMessage(String title, String message, Map<String, String> data) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(String.format("Push [%s]", title))
                .setContentText(message);
        // Сделаем так, чтобы при клике на уведомление приложение не только открылось,
        // но и заполнились бы данные в активити.
        Intent resultIntent = new Intent(this, AlertActivity.class);
        for (Map.Entry<String, String> o: data.entrySet()) {
            resultIntent.putExtra(o.getKey(), o.getValue());
        }
        //resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());
    }
}
