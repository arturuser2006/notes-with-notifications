package com.example.notes.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.example.notes.R;
import com.example.notes.viewmodel.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("HUFHDEFUYVGHEUDGHV");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ID", "My channel", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "ID")
                    .setSmallIcon(R.drawable.notepad)
                    .setContentTitle("Заметки")
                    .setContentText("У вас есть невыполненная заметка");

            Notification notification = builder.build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(1, notification);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.notepad)
                    .setContentTitle("Заметки")
                    .setContentText("У вас есть невыполненная заметка");

            Notification notification = builder.build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);
        }
    }
}
