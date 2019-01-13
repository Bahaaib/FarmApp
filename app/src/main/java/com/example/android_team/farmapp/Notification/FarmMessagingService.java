package com.example.android_team.farmapp.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.android_team.farmapp.HomeActivity;
import com.example.android_team.farmapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FarmMessagingService extends FirebaseMessagingService {

    private final String NOTIFICATION_CHANNEL_ID = "channel_id";
    private NotificationManager notificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //Init notification-Activity transaction intent..
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent notificationIntent = PendingIntent.getActivity(this, 0,
                intent, 0);


        //Create Notification for Lollipop devices to Nougat..
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setTicker("Hearty365")
                .setContentIntent(notificationIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(this.getResources().getString(R.string.notification_title))
                .setContentText(this.getResources().getString(R.string.notification_body))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(this.getResources().getString(R.string.notification_body)));

        notificationManager.notify(1, notificationBuilder.build());


        //Create Notification for Oreo devices and higher..
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription(this.getResources().getString(R.string.notification_daily_description));
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


    }
    
}
