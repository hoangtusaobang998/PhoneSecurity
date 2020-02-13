package com.devpro.phonesecurity.musicService;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.security.Permission;

public class GetAction {
    private Context context;

    public GetAction(Context context) {
        this.context = context;
    }
    public void setVolum(){
        AudioManager audioManager= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int volumMax=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volumMax,0);
    }
    public boolean CheckPermission(String permission){
        if(ContextCompat.checkSelfPermission(context,permission)== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else
            return false;
    }
    public void setPermision(String permisstion,int requestCode){
        ActivityCompat.requestPermissions((Activity) context,new String[]{permisstion},requestCode);
    }

    public void pickAudio(int code_mp3){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/mp3");
        Activity activity=(Activity) context;
        activity.startActivityForResult(intent,code_mp3);
    }

}
