package com.devpro.phonesecurity.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.receiver.ReceiverPower;
import com.devpro.phonesecurity.view.HomeActivity;

public class PowerService extends Service {
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        broadcastReceiver = new ReceiverPower();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent it = new Intent(this, HomeActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 12345, it, 0);
            final String CHANNEL_ID = "PowerListen_id";
            NotificationChannel test = new NotificationChannel(CHANNEL_ID, "sersorListen_id", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manage = getSystemService(NotificationManager.class);
            assert manage != null;
            manage.createNotificationChannel(test);
            Notification.Builder notify = null;

            notify = new Notification.Builder(this, CHANNEL_ID);

            notify.setContentTitle("Power Running")
                    .setContentText("")
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1, notify.build());
        }
        try {
            registerReceiver(broadcastReceiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
