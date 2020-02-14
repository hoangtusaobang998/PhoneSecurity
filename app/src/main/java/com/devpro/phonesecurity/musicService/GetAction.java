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
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.view.HomeActivity;

import java.io.Serializable;
import java.security.Permission;

import static android.content.Context.ACTIVITY_SERVICE;

public class GetAction{
    public static final int requestCode_mp3=123;
    public static final int requestCode_Permission=100;
    public static final String URI_MP3="URI_MP3";





    public static void setVolum(Context context){
        AudioManager audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setSpeakerphoneOn(true);
        assert audioManager != null;
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        int volumMax=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,10,0);
    }
    public static void setNoVolum(Context context){
        AudioManager audioManager=(AudioManager)  context.getSystemService(Context.AUDIO_SERVICE);

        assert audioManager != null;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
    }

    public static boolean CheckPermission(Context context,String permission){
        if(ContextCompat.checkSelfPermission(context,permission)== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else
            return false;
    }
    public static void setPermision(Context context,String permisstion,int requestCode){
        ActivityCompat.requestPermissions((Activity) context,new String[]{permisstion},requestCode);
        Activity activity=(Activity) context;
    }
    public static void pickAudio(Context context,int code_mp3){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        Activity activity=(Activity) context;
        activity.startActivityForResult(intent,code_mp3);


    }
    public static int getSDK(){
        return Build.VERSION.SDK_INT;
    }

    public static boolean checkServiceRunning(Class<?> serviceClass,Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (serviceClass.getName().equals(service.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }

    public static void showDialogBell(final Context context){
        Dialog buider=new Dialog(context);
        buider.setContentView(R.layout.dialog_music);
        Button btn_MusicDefault=(Button) buider.findViewById(R.id.btn_musicdefault);
        Button btn_MusicPhone=(Button) buider.findViewById(R.id.btn_musiclocal);
        btn_MusicDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstansPin.putString(context,GetAction.URI_MP3,ConstansPin.NULLPOIN);
            }
        });
        btn_MusicPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAction.pickAudio(context, GetAction.requestCode_mp3);
            }
        });
        buider.getWindow().setBackgroundDrawable(null);

        buider.show();
    }

}
