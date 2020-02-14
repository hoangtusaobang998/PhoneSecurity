package com.devpro.phonesecurity.view.pinlock;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ConstansPin {
    public static final String KEY_PASS = "KEY_PASS";
    private static final String DATA = "DATA";
    public static final String NULLPOIN = null;
    public static final String NULL_STRING = "";

    public static String getString(Context context, String key) {
        return createSharedPreferences(context).getString(key, NULLPOIN);
    }

    public static void putString(Context context, String key,String path) {
        createSharedPreferences(context).edit().putString(key,path).apply();
    }

    private static final SharedPreferences createSharedPreferences(Context context) {
        return context.getSharedPreferences(DATA, Context.MODE_PRIVATE);
    }

    public static final Animation createAnimation(Context context, int id) {
        return AnimationUtils.loadAnimation(context, id);
    }

    //Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", fileS);

}
