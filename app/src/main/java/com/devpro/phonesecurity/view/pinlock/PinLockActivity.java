package com.devpro.phonesecurity.view.pinlock;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devpro.phonesecurity.R;
import com.devpro.phonesecurity.adapter.CustomPassAdapter;
import com.devpro.phonesecurity.listen.FingerprintListen;
import com.devpro.phonesecurity.musicService.ConstansPin;
import com.devpro.phonesecurity.musicService.GetAction;
import com.devpro.phonesecurity.service.PlayerServicePower;
import com.devpro.phonesecurity.service.SensorListen;
import com.devpro.phonesecurity.view.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import static com.devpro.phonesecurity.musicService.ConstansPin.KEY_CODE;
import static com.devpro.phonesecurity.musicService.ConstansPin.KEY_FINGERPRIENT;
import static com.devpro.phonesecurity.musicService.ConstansPin.KEY_P;
import static com.devpro.phonesecurity.musicService.ConstansPin.KEY_PASS;
import static com.devpro.phonesecurity.musicService.ConstansPin.NULLPOIN;
import static com.devpro.phonesecurity.receiver.ReceiverPower.wasScreenOn;

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
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == KEY_P) {
            checkP();
        }
    }

    @SuppressLint("WrongViewCast")
    private void mapped() {
        img_fingerprint = findViewById(R.id.img_fingerprint);
        list_phone = new ArrayList<>();
        view_pass = findViewById(R.id.gridpass);
        txt_passi = findViewById(R.id.txt_pass);
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
        if (ConstansPin.getString(this, KEY_FINGERPRIENT) != NULLPOIN) {
            img_fingerprint.setVisibility(View.VISIBLE);
            checkP();
        } else {

        }

    }

    private void checkP() {
        if (!GetAction.CheckPermission(this, Manifest.permission.USE_FINGERPRINT)) {
            GetAction.setPermision(this, Manifest.permission.USE_FINGERPRINT, KEY_P);
        } else {
            ConstansPin.Fingerpint(this, this);
        }
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
                txt_passi.setTextColor(getResources().getColor(R.color.white));
                txt_passi.setText(R.string.pass);
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


                        AlertDialog.Builder builder = new AlertDialog.Builder(PinLockActivity.this);
                        builder.setTitle(getString(R.string.confirm_two));
                        builder.setMessage(getString(R.string.confirm_two_one) + finalIs_pass);
                        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                passwords = finalIs_pass;
                                passwordsconfirm = finalIs_pass;
                                txt_passi.setText(getString(R.string.passcomfic));
                                dialog.cancel();
                            }
                        });

                        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                        for (int i = 0; i < password.length; i++) {
                            password[i] = "";
                        }
                        setnulldrawble();

                    } else {

                        if (passwordsShe == null) {
                            if (passwordsconfirm.equals(finalIs_pass)) {

                                ConstansPin.putString(PinLockActivity.this, KEY_PASS, finalIs_pass);
                                Toast.makeText(PinLockActivity.this, getString(R.string.pass_sussce), Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();

                            } else {
                                for (int i = 0; i < password.length; i++) {
                                    password[i] = "";
                                }
                                setnulldrawble();
                                txt_passi.setText(getString(R.string.nopassconfirm));
                            }

                        } else {
                            if (finalIs_pass.equals(passwordsShe)) {

                                if (getIntent().getIntExtra(KEY_CODE, 2) == 1) {
                                    //Off pass
                                    setnulldrawble();
                                    for (int i = 0; i < password.length; i++) {
                                        password[i] = "";
                                    }

                                } else {

                                    //code
                                    onSusscePass();
                                    //
                                }
                            } else {
                                for (int i = 0; i < password.length; i++) {
                                    password[i] = "";
                                }
                                setnulldrawble();
                                txt_passi.setTextColor(Color.parseColor("#FF2323"));
                                txt_passi.setText(getString(R.string.nopasscomfic));
                                syntax_error++;
                                if (syntax_error == 5) {

                                }
                                if (syntax_error == 6) {
                                    finish();
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

    @Override
    public void onFailed() {
        img_fingerprint.setImageResource(R.drawable.ic_fingerprint_red);
        txt_passi.setText(getString(R.string.fingfailed));
    }

    @Override
    public void onSussce() {
        img_fingerprint.setImageResource(R.drawable.ic_fingerprint_sussce);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopService();
                finish();
            }
        }, 350);
    }

    @Override
    public void onFailedMuch() {
        img_fingerprint.setVisibility(View.GONE);
        txt_passi.setText(getString(R.string.retry));
    }

    @Override
    public void onAuthenticationHelp() {

    }

    private void onSusscePass() {
        stopService();
        finish();
    }

    private void stopService() {
        wasScreenOn = false;
        if (GetAction.checkServiceRunning(SensorListen.class, this)) {
            Intent intent = new Intent(PinLockActivity.this, SensorListen.class);
            stopService(intent);
        }
        if (GetAction.checkServiceRunning(PlayerServicePower.class, this)) {
            Intent intent = new Intent(PinLockActivity.this, PlayerServicePower.class);
            stopService(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (passwords != NULLPOIN) {

        } else {
            super.onBackPressed();
        }
    }
}
