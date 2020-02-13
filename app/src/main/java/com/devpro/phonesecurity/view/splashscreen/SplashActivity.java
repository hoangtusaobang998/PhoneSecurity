package com.devpro.phonesecurity.view.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.view.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(SplashActivity.this , HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
