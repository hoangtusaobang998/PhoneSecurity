package com.devpro.phonesecurity.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.musicService.ConstansPin;
import com.devpro.phonesecurity.service.SensorListen;
import com.devpro.phonesecurity.view.pinlock.PinLockActivity;

public class AlarmscreenActivity extends AppCompatActivity {
    private RelativeLayout background;
    private ImageView start;
    private TextView title;
    private Handler handler;
    private boolean isRun = false;
    public static final String KEY_RUNNING = "KEY_RUNNING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmscreen);
//        if (getIntent().getExtras().get(KEY_RUNNING) != null) {
//
//        } else {
//            finish();
//        }
        mapped();
        click();
        new Thread(new Run()).start();
    }

    private void mapped() {
        background = (RelativeLayout) findViewById(R.id.background);
        start = (ImageView) findViewById(R.id.start);
        title = (TextView) findViewById(R.id.title);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == -1) {
                    background.setBackgroundResource(R.drawable.background_home);
                    return false;
                }
                if (msg.what % 2 == 0) {
                    background.setBackgroundResource(R.drawable.background_gradien_on);
                } else {
                    background.setBackgroundResource(R.drawable.background_home);
                }
                return false;
            }
        });
    }

    private void click() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRun = true;
                if (ConstansPin.getString(AlarmscreenActivity.this, ConstansPin.KEY_PASS) == ConstansPin.NULLPOIN) {
                    Intent intent = new Intent(AlarmscreenActivity.this, SensorListen.class);
                    stopService(intent);
                    finish();
                } else {
                    Intent intent = new Intent(AlarmscreenActivity.this, PinLockActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private class Run implements Runnable {

        @Override
        public void run() {
            for (int i = 0; true; i++) {
                if (isRun) {
                    handler.sendEmptyMessage(-1);
                    return;
                }
                handler.sendEmptyMessage(i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isRun) {
            super.onBackPressed();
        }
    }
}
