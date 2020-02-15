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
import com.devpro.phonesecurity.listen.Play;
import com.devpro.phonesecurity.musicService.ConstansPin;
import com.devpro.phonesecurity.musicService.GetAction;
import com.devpro.phonesecurity.service.PlayerServicePower;
import com.devpro.phonesecurity.service.SensorListen;
import com.devpro.phonesecurity.view.AlarmscreenActivity;

public class ReceiverPower extends BroadcastReceiver {

    public static boolean wasScreenOn = false;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent intentService = new Intent(context, PlayerServicePower.class);
        if (ConstansPin.getBoolean(context, ConstansPin.KEY_POWER)) {
            if (action.equals(Intent.ACTION_POWER_CONNECTED)) {


            } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                wasScreenOn = true;
                if (!GetAction.checkServiceRunning(PlayerServicePower.class, context) && !GetAction.checkServiceRunning(SensorListen.class, context)) {
                    context.startService(intentService);
                }
            }
        } else {
            wasScreenOn = false;
        }
    }

    public static final void sendBroadcast(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        BroadcastReceiver broadcastReceiver = new ReceiverPower();
        context.registerReceiver(broadcastReceiver, intentFilter);
    }


}
