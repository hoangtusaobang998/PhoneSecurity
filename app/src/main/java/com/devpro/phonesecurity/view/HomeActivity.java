package com.devpro.phonesecurity.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.WindowManager;
import android.widget.ImageView;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.receiver.ReceiverPower;

public class HomeActivity extends AppCompatActivity {

    ImageView startImg , bellImg , chargeImg , pinImg , settingImg;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_home);
        setClick();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        broadcastReceiver = new ReceiverPower();
        registerReceiver(broadcastReceiver , intentFilter);
    }

    private void setClick() {
        startImg = findViewById(R.id.start);
        bellImg = findViewById(R.id.bell);
        chargeImg = findViewById(R.id.charge);
        pinImg = findViewById(R.id.pin);
        settingImg = findViewById(R.id.setting);
    }
}
