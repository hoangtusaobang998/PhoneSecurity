package com.devpro.phonesecurity.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.musicService.FileUtils;
import com.devpro.phonesecurity.musicService.GetAction;
import com.devpro.phonesecurity.receiver.ReceiverBackground;
import com.devpro.phonesecurity.receiver.ReceiverPower;
import com.devpro.phonesecurity.service.PlayerServicePower;
import com.devpro.phonesecurity.service.SensorListen;
import com.devpro.phonesecurity.musicService.ConstansPin;
import com.devpro.phonesecurity.view.pinlock.PinLockActivity;

import java.net.Inet4Address;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView startImg,chargeImg;
    LinearLayout  bellLl,chargeLl , pinLl, settingLl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_home);
        mapping();
        startImg.setImageResource(R.drawable.ic_power_off);
        startImg.setOnClickListener(this);
        bellLl.setOnClickListener(this);
        chargeLl.setOnClickListener(this);
        pinLl.setOnClickListener(this);
        settingLl.setOnClickListener(this);
    }

    private void mapping() {
        startImg = findViewById(R.id.start);
        bellLl = findViewById(R.id.ll_bell);
        chargeImg = findViewById(R.id.charge);
        chargeLl=findViewById(R.id.ll_charge);
        pinLl = findViewById(R.id.ll_pin);
        settingLl = findViewById(R.id.ll_setting);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (GetAction.checkServiceRunning(SensorListen.class, this)) {
                    startImg.setImageResource(R.drawable.ic_power_off);
                    Intent intent = new Intent(HomeActivity.this, SensorListen.class);
                    stopService(intent);
                } else {
                    startImg.setImageResource(R.drawable.ic_power_on);
                    Intent intent = new Intent(HomeActivity.this, SensorListen.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    } else {
                        startService(intent);
                    }
                }

                break;
            case R.id.ll_bell:
                if (GetAction.CheckPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    GetAction.showDialogBell(this,R.layout.dialog_music,R.id.btn_musicdefault,R.id.btn_musiclocal,GetAction.show_Music);

                } else
                    GetAction.setPermision(this, Manifest.permission.READ_EXTERNAL_STORAGE, GetAction.requestCode_Permission);
                break;
            case R.id.ll_charge:
                if (!ReceiverPower.isRegisted) {
                    chargeImg.setImageResource(R.drawable.ic_charger_on);
                   ReceiverPower.sendBroadcast(this);
                }else {
                    chargeImg.setImageResource(R.drawable.ic_charger_off);
                    ReceiverPower.stopBroadcast(this);
                }
//                code....

                break;
            case R.id.ll_pin:
                startActivity(new Intent(this, PinLockActivity.class));
//                code...
                break;
            case R.id.ll_setting:
//                code...
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GetAction.requestCode_mp3 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            Log.e("URL", uri.toString());
            ConstansPin.putString(this, GetAction.URI_MP3, FileUtils.getPath(this, uri));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==GetAction.requestCode_Permission){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                GetAction.showDialogBell(this,R.layout.dialog_music,R.id.btn_musicdefault,R.id.btn_musiclocal,GetAction.show_Music);
            }
            else if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    boolean showRationale = shouldShowRequestPermissionRationale(String.valueOf(grantResults[0]));
                    if(!showRationale){
                        GetAction.showDialogBell(this,R.layout.dialog_detail,R.id.btn_setting,R.id.btn_done,GetAction.show_Permission);
                    }

                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(GetAction.checkServiceRunning(SensorListen.class,this)){
            GetAction.setVolum(this);
        }
        return super.onKeyDown(keyCode, event);
    }
}
