package com.devpro.phonesecurity.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.setting.setcolor.SettingColorActivity;

public class SettingActivity extends AppCompatActivity {
    Button colorBtn;
    Switch fingerSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
        colorBtn = findViewById(R.id.colorBtn);
        fingerSwitch = findViewById(R.id.fingerSwitch);
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this , SettingColorActivity.class);
                startActivity(intent);
            }
        });
    }
}
