package com.devpro.phonesecurity.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.musicService.GetAction;
import com.devpro.phonesecurity.view.AlarmscreenActivity;

import static com.devpro.phonesecurity.musicService.GetAction.SERVICE_POWER;

public class PlayerServicePower extends Service {
    private static final String TAG = PlayerServicePower.class.getSimpleName();
    WindowManager mWindowManager;
    View mView;
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.musicdefault);
    }

    @Override
    public void onDestroy() {
        checkPlaying();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkPlaying();
        Intent it = new Intent(this, AlarmscreenActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        it.putExtra(SERVICE_POWER, SERVICE_POWER);
        startActivity(it);
        return START_NOT_STICKY;
    }

    private void checkPlaying() {
        if (mediaPlayer == null) {
            return;
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = null;
        } else {
            GetAction.setVolum(PlayerServicePower.this);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    private void showDialog(){
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        mView = View.inflate(getApplicationContext(), R.layout.activity_alarmscreen, null);
        mView.setTag(TAG);

        final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON ,
                PixelFormat.RGBA_8888);

        mView.setVisibility(View.VISIBLE);
        mWindowManager.addView(mView, mLayoutParams);

    }
}
