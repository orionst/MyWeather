package com.example.viktor.myweather.forecasts;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.viktor.myweather.R;

public class WeatherUpdaterService extends IntentService {
    int messageId=0;

    public WeatherUpdaterService() {
        super("WeatherUpdaterService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //makeNote("onHandleIntent");
        String city = (String) intent.getSerializableExtra("City");

        //получаем данные с погодного сервиса
        makeNote("Обновление погоды города " + city);
    }

    @Override
    public void onCreate() {
        //makeNote("onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //makeNote("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        //makeNote("onDestroy");
        super.onDestroy();
    }

    //выводит уведомление в статусбар
    private void makeNote(String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message);
        Intent resultIntent = new Intent(this, WeatherUpdaterService.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());
    }
}
