package com.devpro.phonesecurity.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.WindowManager;
import android.widget.ImageView;

import com.devpro.phonesecurity.R;

public class HomeActivity extends AppCompatActivity {

    ImageView startImg , bellImg , chargeImg , pinImg , settingImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_home);
        setClick();
    }

    private void setClick() {
        startImg = findViewById(R.id.start);
        bellImg = findViewById(R.id.bell);
        chargeImg = findViewById(R.id.charge);
        pinImg = findViewById(R.id.pin);
        settingImg = findViewById(R.id.setting);
    }
}
