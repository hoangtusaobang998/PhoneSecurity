package com.devpro.phonesecurity.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();
        sensorMan.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
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
            if (mAccel > 0.5) {
                Toast.makeText(SensorListen.this, "Sensor Run Hua Bc", Toast.LENGTH_SHORT).show();
                //MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.siren);
                //mPlayer.start();
                if (mSwitchSet == 1) {


                }

            }
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                    //near
                    Toast.makeText(getApplicationContext(), "Gần", Toast.LENGTH_SHORT).show();
                } else if (pSwitchSet == 1) {
                    //startActivity(new Intent(MainActivity.this, EnterPin.class));
                    // finish();
                    //far
                    Toast.makeText(getApplicationContext(), "Xa", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        sensorMan.unregisterListener(this);
//        mSensorManager.unregisterListener(this);
    }
}
