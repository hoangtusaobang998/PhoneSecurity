package com.devpro.phonesecurity.musicService;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.Serializable;
import java.security.Permission;

public class GetAction{

    public static void setVolum(Context context){
        AudioManager audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int volumMax=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volumMax,0);
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
    }

    public static void pickAudio(Context context,int code_mp3){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/mp3");
        Activity activity=(Activity) context;
        activity.startActivityForResult(intent,code_mp3);
    }
    public static int getSDK(){
        return Build.VERSION.SDK_INT;
    }

}
