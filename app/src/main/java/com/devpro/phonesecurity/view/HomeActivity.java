package com.devpro.phonesecurity.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.musicService.GetAction;
import com.devpro.phonesecurity.receiver.ReceiverBackground;
import com.devpro.phonesecurity.service.SensorListen;
import com.devpro.phonesecurity.view.pinlock.ConstansPin;

import java.net.Inet4Address;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView startImg , bellImg , chargeImg , pinImg , settingImg;
    private int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_home);
        mapping();
        startImg.setImageResource(R.drawable.ic_power_off);
        startImg.setOnClickListener(this);
        bellImg.setOnClickListener(this);
    }
    private void mapping(){
        startImg = findViewById(R.id.start);
        bellImg = findViewById(R.id.bell);
        chargeImg = findViewById(R.id.charge);
        pinImg = findViewById(R.id.pin);
        settingImg = findViewById(R.id.setting);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                if(GetAction.checkServiceRunning(SensorListen.class,this)){
                    startImg.setImageResource(R.drawable.ic_power_off);
                    Intent intent=new Intent(HomeActivity.this,SensorListen.class);
                    stopService(intent);
                }
                else{
                    startImg.setImageResource(R.drawable.ic_power_on);
                    Intent intent=new Intent(HomeActivity.this,SensorListen.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    }
                    else{
                        startService(intent);
                    }
                }

                break;
            case R.id.bell:
                if(GetAction.CheckPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                    GetAction.pickAudio(this,GetAction.requestCode_mp3);
                }
                else
                    GetAction.setPermision(this,Manifest.permission.READ_EXTERNAL_STORAGE,GetAction.requestCode_Permisstion);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==GetAction.requestCode_mp3 && resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();
            ConstansPin.putString(this,GetAction.URI_MP3,uri.toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
