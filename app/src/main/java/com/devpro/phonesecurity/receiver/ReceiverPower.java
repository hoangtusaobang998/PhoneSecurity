package com.devpro.phonesecurity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.musicService.GetAction;

public class ReceiverPower extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(Intent.ACTION_POWER_CONNECTED)){
            Toast.makeText(context , "Safety" , Toast.LENGTH_SHORT).show();
        }else if(action.equals(Intent.ACTION_POWER_DISCONNECTED)){
            Toast.makeText(context , "Warning" , Toast.LENGTH_SHORT).show();
            GetAction.setVolum(context);
            MediaPlayer mediaPlayer=MediaPlayer.create(context, R.raw.musicdefault);
            mediaPlayer.start();
        }
    }
}
