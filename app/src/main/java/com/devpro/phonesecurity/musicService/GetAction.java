package com.devpro.phonesecurity.musicService;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.view.HomeActivity;

import java.io.Serializable;
import java.security.Permission;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class GetAction {
    public static final int requestCode_mp3 = 123;
    public static final int requestCode_Permission = 100;
    public static final String URI_MP3 = "URI_MP3";
    public static final int show_Music = 1;
    public static final int show_Permission = 2;
    public static final int show_pin=3;
    public static final String SERVICE_SENSOR = "SERVICE_SENSOR";
    public static final String SERVICE_POWER = "SERVICE_POWER";
    public static final String SERVICE_RUNNING="SERVICE_RUNNING";


    public static void setVolum(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setSpeakerphoneOn(true);
        assert audioManager != null;
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        int volumMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 2, 0);
    }

    public static void setNoVolum(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        assert audioManager != null;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
    }

    public static boolean CheckPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else
            return false;
    }

    public static void setPermision(Context context, String permisstion, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{permisstion}, requestCode);
        Activity activity = (Activity) context;
    }

    public static void pickAudio(Context context, int code_mp3) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent, code_mp3);


    }

    public static int getSDK() {
        return Build.VERSION.SDK_INT;
    }

    public static boolean checkServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void showDialogBell(final Context context, int layout, int btn_id1, int btn_id2, final int event) {
        final Dialog buider = new Dialog(context);
        buider.setContentView(layout);
        Button btn_MusicDefault = (Button) buider.findViewById(btn_id1);
        Button btn_MusicPhone = (Button) buider.findViewById(btn_id2);
        btn_MusicDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (event == GetAction.show_Music) {
                    ConstansPin.putString(context, GetAction.URI_MP3, ConstansPin.NULLPOIN);
                    buider.dismiss();
                } else if (event == GetAction.show_Permission) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                    intent.setData(uri);
                    Activity activity = (Activity) context;
                    activity.startActivityForResult(intent, GetAction.requestCode_Permission);
                    buider.dismiss();
                }
            }
        });
        btn_MusicPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (event == GetAction.show_Music) {
                    GetAction.pickAudio(context, GetAction.requestCode_mp3);
                    buider.dismiss();
                } else if (event == GetAction.show_Permission) {
                    buider.dismiss();
                }
            }
        });
        buider.getWindow().setBackgroundDrawable(null);
        buider.show();
    }


}
