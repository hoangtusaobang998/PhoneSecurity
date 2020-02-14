package com.devpro.phonesecurity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.devpro.phonesecurity.service.SensorListen;

public class ReceiverBackground extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent in=new Intent(context, SensorListen.class);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
            context.startForegroundService(in);
    }
}
