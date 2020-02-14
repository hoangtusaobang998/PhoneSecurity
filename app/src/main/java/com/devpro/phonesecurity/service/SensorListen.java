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
import android.net.Uri;
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
    int pSwitchSet = 0;
    private MediaPlayer player;
    private MediaPlayer player_uri;

    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        player=MediaPlayer.create(this,R.raw.musicdefault);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(GetAction.uri_String.length()!=0){
            player_uri=MediaPlayer.create(this,Uri.parse(GetAction.uri_String));
        }
        else
            player_uri=MediaPlayer.create(this,R.raw.musicdefault);
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
        else{
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
            if(event.values[0]>1.0f || event.values[1]>1.0f){
                GetAction.setVolum(this);
                checkPlay();
            }
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                    GetAction.setVolum(this);
                    checkPlay();
                } else if (pSwitchSet == 1) {
                    GetAction.setVolum(this);
                    checkPlay();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void playMediaDefault(){
        if(player.isPlaying()==false){
            player.start();
        }
    }
    public void stopMediaDefault(){
        if(player.isPlaying()){
            player.stop();
        }
    }
    public void playMediaUri(){
        if(player_uri.isPlaying()==false){
            player_uri.start();
        }
    }
    public void stopMediaUri(){
        if(player_uri.isPlaying()){
            player_uri.stop();
        }
    }

    public void checkPlay(){
        if(GetAction.uri_String.length()!=0){
            playMediaUri();
        }
        else{
            playMediaDefault();
        }
    }
    public void checkStop(){
        if(GetAction.uri_String.length()!=0){
            stopMediaUri();
        }
        else
            stopMediaDefault();
    }

    @Override
    public void onDestroy() {
        sensorMan.unregisterListener(this);
        mSensorManager.unregisterListener(this);
        checkStop();
        super.onDestroy();
    }


}
