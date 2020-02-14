package com.devpro.phonesecurity.view.pinlock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.adapter.CustomPassAdapter;
import com.devpro.phonesecurity.listen.FingerprintListen;
import com.devpro.phonesecurity.musicService.ConstansPin;
import com.devpro.phonesecurity.musicService.GetAction;
import com.devpro.phonesecurity.service.SensorListen;

import java.util.ArrayList;
import java.util.List;

import static com.devpro.phonesecurity.musicService.ConstansPin.KEY_PASS;

public class PinLockActivity extends AppCompatActivity implements FingerprintListen {

    private GridView view_pass;
    private CustomPassAdapter adapter_pass;
    private List<String> list_phone = new ArrayList<>();
    private ImageView p1, p2, p3, p4, p5, p6;
    private TextView txt_passi;
    private String[] password = {"", "", "", "", "", ""};
    private static final int P1 = 0;
    private static final int P2 = 1;
    private static final int P3 = 2;
    private static final int P4 = 3;
    private static final int P5 = 4;
    private static final int P6 = 5;
    private static final int[] i = {0, 0, 0, 0};
    private String passwords = null;
    private String passwordsconfirm = null;
    private String passwordsShe = null;
    private int syntax_error = 0;
    private ImageView img_fingerprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_pin_lock);
        mapped();
        addlist();
        clickitem();
        if (!GetAction.CheckPermission(this, Manifest.permission.USE_FINGERPRINT)) {
            GetAction.setPermision(this, Manifest.permission.USE_FINGERPRINT, 999);
        } else {
            ConstansPin.Fingerpint(this, this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("WrongViewCast")
    private void mapped() {
        img_fingerprint = findViewById(R.id.img_fingerprint);
        list_phone = new ArrayList<>();
        view_pass = findViewById(R.id.gridpass);
        adapter_pass = new CustomPassAdapter();
        view_pass.setAdapter(adapter_pass);
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        p3 = findViewById(R.id.p3);
        p4 = findViewById(R.id.p4);
        p5 = findViewById(R.id.p5);
        p6 = findViewById(R.id.p6);
        passwordsShe = ConstansPin.getString(this, KEY_PASS);
        passwords = ConstansPin.getString(this, KEY_PASS);

    }

    private void addlist() {
        for (int i = 1; i <= 10; i++) {
            list_phone.add(i + "");
        }
        list_phone.add("0");
        list_phone.add("11");
        adapter_pass.setContext(this);
        adapter_pass.setListphone(list_phone);
    }

    private void clickitem() {
        final AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final float vol = 0.5f;
        adapter_pass.setClickListent(new CustomPassAdapter.ClickListent() {
            @Override
            public void clickitem(String pass) {
                //txt_passi.setText(R.string.pass);
                clickadditemlisten(pass);
                am.playSoundEffect(AudioManager.FX_KEY_CLICK, vol);
            }

            @Override
            public void clickitemclear() {
                clickclearitemlisten();
                am.playSoundEffect(AudioManager.FX_KEY_CLICK, vol);
            }
        });
    }

    private void clickadditemlisten(String pass) {
        Log.e("TAG", password.length + "");
        if (password[0].isEmpty()) {
            password[0] = pass;
            show_pass();
            set_add_pass_icon(P1);
            return;
        }

        if (password[1].isEmpty()) {
            password[1] = pass;
            show_pass();
            set_add_pass_icon(P2);
            return;
        }
        if (password[2].isEmpty()) {
            password[2] = pass;
            show_pass();
            set_add_pass_icon(P3);
            return;
        }

        if (password[3].isEmpty()) {
            password[3] = pass;
            show_pass();
            set_add_pass_icon(P4);
            return;
        }

        if (password[4].isEmpty()) {
            password[4] = pass;
            show_pass();
            set_add_pass_icon(P5);
            return;
        }

        if (password[5].isEmpty()) {
            password[5] = pass;
            show_pass();
            set_add_pass_icon(P6);
            return;
        }

    }

    private void clickclearitemlisten() {
        Log.e("TAG", password.length + "");
        if (!password[5].isEmpty()) {
            password[5] = "";
            setnullIconDrawable(p6);
            show_pass();
            return;
        }
        if (!password[4].isEmpty()) {
            password[4] = "";
            setnullIconDrawable(p5);
            show_pass();
            return;
        }
        if (!password[3].isEmpty()) {
            password[3] = "";
            setnullIconDrawable(p4);
            show_pass();
            return;
        }
        if (!password[2].isEmpty()) {
            password[2] = "";
            setnullIconDrawable(p3);
            show_pass();
            return;
        }
        if (!password[1].isEmpty()) {
            password[1] = "";
            setnullIconDrawable(p2);
            show_pass();
            return;
        }
        if (!password[0].isEmpty()) {
            password[0] = "";
            setnullIconDrawable(p1);
            show_pass();
            return;
        }

    }

    private void show_pass() {
        String is_pass = "";
        for (String pass : password) {
            is_pass += pass;
        }
        Log.e("i", is_pass.length() + "");
        if (is_pass.length() == 6) {
            final String finalIs_pass = is_pass;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Code ...
                    if (passwords == null) {

                        for (int i = 0; i < password.length; i++) {
                            password[i] = "";
                        }
                        setnulldrawble();


                    } else {
                        if (passwordsShe == null) {
                            if (passwordsconfirm.equals(finalIs_pass)) {


                            } else {
                                for (int i = 0; i < password.length; i++) {
                                    password[i] = "";
                                }
                                setnulldrawble();
                                //txt_passi.setText(getString(R.string.nopassconfirm));
                            }

                        } else {
                            if (finalIs_pass.equals(passwordsShe)) {

                                if (getIntent().getIntExtra("KEY_CODE", 2) == 1) {

                                    setnulldrawble();
                                    for (int i = 0; i < password.length; i++) {
                                        password[i] = "";
                                    }

                                } else {
                                    //
                                }
                            } else {
                                for (int i = 0; i < password.length; i++) {
                                    password[i] = "";
                                }
                                setnulldrawble();
                                //txt_passi.setText(getString(R.string.nopasscomfic));
                                syntax_error++;
                                if (syntax_error == 5) {

                                }
                                if (syntax_error == 6) {


                                }
                            }
                        }
                    }

                }
            }, 100);
        }
        if (is_pass.length() == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Code ...
                }
            }, 100);
        }
    }

    private void set_add_pass_icon(int i) {
        switch (i) {
            case P1: {
                setIconDrawable(p1);
                break;
            }
            case P2: {
                setIconDrawable(p2);
                break;
            }
            case P3: {
                setIconDrawable(p3);
                break;
            }
            case P4: {
                setIconDrawable(p4);
                break;
            }
            case P5: {
                setIconDrawable(p5);
                break;
            }
            case P6: {
                setIconDrawable(p6);
                break;
            }
        }
    }

    private void setIconDrawable(View view) {
        view.startAnimation(ConstansPin.createAnimation(this, R.anim.animation_scale_pass));
        ((ImageView) view).setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.custom_pass_one));
    }

    private void setnullIconDrawable(View view) {
        view.startAnimation(ConstansPin.createAnimation(this, R.anim.animation_scale_pass_one));
        ((ImageView) view).setImageDrawable(null);
    }

    private void setnulldrawble() {
        setnullIconDrawable(p1);
        setnullIconDrawable(p2);
        setnullIconDrawable(p3);
        setnullIconDrawable(p4);
        setnullIconDrawable(p5);
        setnullIconDrawable(p6);
    }

    @Override
    public void onFailed() {
        img_fingerprint.setImageResource(R.drawable.ic_fingerprint_red);
    }

    @Override
    public void onSussce() {
        img_fingerprint.setImageResource(R.drawable.ic_fingerprint_sussce);
    }

    @Override
    public void onFailedMuch() {

    }

    @Override
    public void onAuthenticationHelp() {

    }
}
