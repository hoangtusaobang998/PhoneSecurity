package com.devpro.phonesecurity.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.devpro.phonesecurity.BuildConfig;
import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.listen.Play;
import com.devpro.phonesecurity.musicService.GetAction;
import com.devpro.phonesecurity.musicService.ConstansPin;
import com.devpro.phonesecurity.view.AlarmscreenActivity;
import com.devpro.phonesecurity.view.HomeActivity;
import com.devpro.phonesecurity.view.pinlock.PinLockActivity;

import java.io.File;

public class SensorListen extends Service implements SensorEventListener {

    private SensorManager sensorMan;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Sensor accelerometer;
    private static final int SENSOR_SENSITIVITY = 4;
    int pSwitchSet = 0;
    private MediaPlayer player;
    private NotificationManager manage;

    public static boolean isPlaySensor = false;

    @Override
    public void onCreate() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent it = new Intent(this, AlarmscreenActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1234, it, 0);
        String uri_Path = ConstansPin.getString(this, GetAction.URI_MP3);
        if (uri_Path == ConstansPin.NULLPOIN) {
            player = MediaPlayer.create(this, R.raw.musicdefault);
        } else {
            Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", new File(uri_Path));
            player = MediaPlayer.create(this, uri);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manage = getSystemService(NotificationManager.class);
        }
        final String CHANNEL_ID = "sersorListen_id";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel test = new NotificationChannel(CHANNEL_ID, "sersorListen_id", NotificationManager.IMPORTANCE_DEFAULT);

            assert manage != null;
            manage.createNotificationChannel(test);
            Notification.Builder notify = new Notification.Builder(this, CHANNEL_ID);
            notify.setContentTitle("Stop music")
                    .setContentText("")
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1, notify.build());
            sensorMan.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_UI);
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                Notification.Builder builder = new Notification.Builder(this);
                builder.setContentTitle("Stop music")
                        .setContentText("")
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentIntent(pendingIntent)
                        .build();
                manage.notify(1, builder.build());

            }
            sensorMan.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_UI);
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (event.values[0] > 1.0f || event.values[1] > 1.0f) {
                GetAction.setVolum(this);
                playMusic();

            }
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                    GetAction.setVolum(this);
                    playMusic();
                } else if (pSwitchSet == 1) {
                    GetAction.setVolum(this);
                    playMusic();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void playMusic() {
        if (player != null) {
            if (!player.isPlaying()) {
                isPlaySensor = true;
                player.setAudioStreamType(AudioManager.MODE_IN_COMMUNICATION);
                player.start();
                mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
                accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                Intent it = new Intent(getApplicationContext(), AlarmscreenActivity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                it.putExtra(AlarmscreenActivity.KEY_RUNNING, GetAction.SERVICE_SENSOR);
                startActivity(it);
                ConstansPin.putBoolean(this, GetAction.SERVICE_RUNNING, true);


            } else if (player.isPlaying()) {
                isPlaySensor = false;
                sensorMan.unregisterListener(this);
                mSensorManager.unregisterListener(this);
            }
        }
    }

    @Override
    public void onDestroy() {
        manage.cancel(1);
        sensorMan.unregisterListener(this);
        mSensorManager.unregisterListener(this);
        if (player != null)
            player.stop();
        super.onDestroy();
    }


}
