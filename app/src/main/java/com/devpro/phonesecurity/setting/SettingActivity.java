package com.devpro.phonesecurity.setting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.musicService.ConstansPin;
import com.devpro.phonesecurity.setting.setcolor.SettingColorActivity;

import static com.devpro.phonesecurity.musicService.ConstansPin.KEY_FINGERPRIENT;
import static com.devpro.phonesecurity.musicService.ConstansPin.NULLPOIN;

public class SettingActivity extends AppCompatActivity {
    RelativeLayout relativeLayout, fingerLayout;
    Button colorBtn;
    Switch fingerSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        colorBtn = findViewById(R.id.colorBtn);
        relativeLayout = findViewById(R.id.inforelative);
        fingerSwitch = findViewById(R.id.fingerSwitch);
        fingerLayout = findViewById(R.id.fingerLayout);
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SettingColorActivity.class);
                startActivityForResult(intent, 200);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
        if (ConstansPin.getString(this, KEY_FINGERPRIENT) != null) {
            fingerSwitch.setChecked(true);
        } else {
            fingerSwitch.setChecked(false);
        }

        on_of_F();

    }

    void on_of_F() {
        fingerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    ConstansPin.putString(SettingActivity.this, KEY_FINGERPRIENT, NULLPOIN);
                } else {
                    ConstansPin.putString(SettingActivity.this, KEY_FINGERPRIENT, "true");
                }
            }
        });

        fingerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (fingerSwitch.isChecked()) {
                    fingerSwitch.setChecked(false);
                    ConstansPin.putString(SettingActivity.this, KEY_FINGERPRIENT, NULLPOIN);
                } else {
                    ConstansPin.putString(SettingActivity.this, KEY_FINGERPRIENT, "true");
                    fingerSwitch.setChecked(true);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
