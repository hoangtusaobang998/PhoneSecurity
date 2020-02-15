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
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.listen.Play;
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
    ImageView startImg, chargeImg, pin;
    LinearLayout bellLl, chargeLl, pinLl, settingLl;
    private RelativeLayout background;

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
        background = findViewById(R.id.background);
        pin = findViewById(R.id.pin);
        bellLl = findViewById(R.id.ll_bell);
        chargeImg = findViewById(R.id.charge);
        chargeLl = findViewById(R.id.ll_charge);
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
                    background.setBackgroundResource(R.drawable.background_home);
                } else {
                    background.setBackgroundResource(R.drawable.background_gradien_on);
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
                    GetAction.showDialogBell(this, R.layout.dialog_music, R.id.btn_musicdefault, R.id.btn_musiclocal, GetAction.show_Music);

                } else
                    GetAction.setPermision(this, Manifest.permission.READ_EXTERNAL_STORAGE, GetAction.requestCode_Permission);
                break;
            case R.id.ll_charge:

                if (ConstansPin.getBoolean(this, ConstansPin.KEY_POWER)) {
                    ConstansPin.putBoolean(this, ConstansPin.KEY_POWER, false);
                    chargeImg.setImageResource(R.drawable.ic_charger_off);
                } else {
                    ConstansPin.putBoolean(this, ConstansPin.KEY_POWER, true);
                    chargeImg.setImageResource(R.drawable.ic_charger_on);
                    ReceiverPower.sendBroadcast(this);
                }


                break;
            case R.id.ll_pin:

                if (ConstansPin.getString(HomeActivity.this, ConstansPin.KEY_PASS) != ConstansPin.NULLPOIN) {
                    pin.setImageResource(R.drawable.ic_password);
                    ConstansPin.putString(HomeActivity.this, ConstansPin.KEY_PASS, ConstansPin.NULLPOIN);
                } else {
                    startActivity(new Intent(this, PinLockActivity.class));
                }

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
        if (requestCode == GetAction.requestCode_Permission) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GetAction.showDialogBell(this, R.layout.dialog_music, R.id.btn_musicdefault, R.id.btn_musiclocal, GetAction.show_Music);
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    boolean showRationale = shouldShowRequestPermissionRationale(String.valueOf(grantResults[0]));
                    if (!showRationale) {
                        GetAction.showDialogBell(this, R.layout.dialog_detail, R.id.btn_setting, R.id.btn_done, GetAction.show_Permission);
                    }

                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (GetAction.checkServiceRunning(SensorListen.class, this)) {
            GetAction.setVolum(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ConstansPin.getString(HomeActivity.this, ConstansPin.KEY_PASS) != ConstansPin.NULLPOIN) {
            pin.setImageResource(R.drawable.ic_password_on);
        } else {
            pin.setImageResource(R.drawable.ic_password);
        }
        if (ConstansPin.getBoolean(this, ConstansPin.KEY_POWER)) {
            chargeImg.setImageResource(R.drawable.ic_charger_on);
        }
    }

}
