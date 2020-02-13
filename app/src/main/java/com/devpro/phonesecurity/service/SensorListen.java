package com.devpro.phonesecurity.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.musicService.GetAction;

public class SensorListen extends Service implements SensorEventListener {

    private SensorManager sensorMan;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Sensor accelerometer;
    private static final int SENSOR_SENSITIVITY = 4;
    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    int mSwitchSet, pSwitchSet = 0;
    int chargerFlag, chargerFlag1, chargerFlag2 = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String CHANNEL_ID = "sersorListen_id";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel test = new NotificationChannel(CHANNEL_ID, "sersorListen_id", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manage = getSystemService(NotificationManager.class);
            assert manage != null;
            manage.createNotificationChannel(test);
            Notification.Builder notify = new Notification.Builder(this, CHANNEL_ID);
            notify.setContentTitle("Messenger")
                    .setContentText("Running")
                    .setSmallIcon(R.drawable.ic_notifications);
            startForeground(1, notify.build());
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
            mGravity = event.values.clone();
            // Shake detection
            float x = mGravity[0];
            float y = mGravity[1];
            float z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            // Make this higher or lower according to how much
            // motion you want to detect
            if (mAccel > 10) {
                Toast.makeText(SensorListen.this, "Sensor Run Hua Bc", Toast.LENGTH_SHORT).show();
                GetAction.setVolum(this);
                GetAction.playMusicDefault(this);

            }
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                    GetAction.playMusicDefault(this);
                    Toast.makeText(getApplicationContext(), "Gần", Toast.LENGTH_SHORT).show();
                } else if (pSwitchSet == 1) {
                    GetAction.playMusicDefault(this);
                    Toast.makeText(getApplicationContext(), "Xa", Toast.LENGTH_SHORT).show();

                }
            }
        }
        else{
            onDestroy();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {
        Log.d("rr","as");
        sensorMan.unregisterListener(this);
        mSensorManager.unregisterListener(this);
        super.onDestroy();
        
    }
}
