package com.devpro.phonesecurity.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.service.SensorListen;

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
                if(i==0){
                    i=1;
                    Intent intent=new Intent(HomeActivity.this,SensorListen.class);
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        startForegroundService(intent);
                    }
                    else{
                        startService(intent);
                    }
                    startImg.setImageResource(R.drawable.ic_power_on);
                }
                else if(i==1){
                    i=0;
                    Intent intent=new Intent(HomeActivity.this,SensorListen.class);
                    stopService(intent);
                    startImg.setImageResource(R.drawable.ic_power_off);
                }
                break;

        }
    }
}
