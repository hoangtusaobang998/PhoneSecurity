package com.devpro.phonesecurity.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.musicService.GetAction;
import com.devpro.phonesecurity.service.PlayerServicePower;

public class ReceiverPower extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent intentService = new Intent(context, PlayerServicePower.class);
        if (action.equals(Intent.ACTION_POWER_CONNECTED)) {

            context.stopService(intentService);

        } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            if (!GetAction.checkServiceRunning(PlayerServicePower.class, context)) {
                if (GetAction.getSDK() >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intentService);
                } else {
                    context.startService(intentService);
                }
            }
        }
    }

    //ReceiverPower.sendBroadcast(this);
    public static final void sendBroadcast(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        BroadcastReceiver broadcastReceiver = new ReceiverPower();
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

}
