package com.devpro.phonesecurity.musicService;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.devpro.phonesecurity.listen.FingerprintListen;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

public class ConstansPin {
    public static final String KEY_PASS = "KEY_PASS";
    public static final String KEY_CODE = "KEY_CODE";
    public static final String KEY_POWER = "KEY_POWER";
    public static final int KEY_P = 999;
    public static final String KEY_FINGERPRIENT = "KEY_FINGERPRIENT";
    private static final String DATA = "DATA";
    public static final String NULLPOIN = null;
    public static final String NULL_STRING = "";


    public static String getString(Context context, String key) {
        return createSharedPreferences(context).getString(key, NULLPOIN);
    }

    public static void putString(Context context, String key, String path) {
        createSharedPreferences(context).edit().putString(key, path).apply();
    }

    public static boolean getBoolean(Context context, String key) {
        return createSharedPreferences(context).getBoolean(key, false);
    }

    public static void putBoolean(Context context, String key, boolean is) {
        createSharedPreferences(context).edit().putBoolean(key, is).apply();
    }

    private static final SharedPreferences createSharedPreferences(Context context) {
        return context.getSharedPreferences(DATA, Context.MODE_PRIVATE);
    }

    public static final Animation createAnimation(Context context, int id) {
        return AnimationUtils.loadAnimation(context, id);
    }

    public static void Fingerpint(Context context, FingerprintListen fingerprintListen) {
        KeyGenerator keyGenerator = null;
        KeyguardManager keyguardManager = null;
        FingerprintManager fingerprintManager = null;
        FingerprintManager.CryptoObject cryptoObject = null;
        Cipher cipher = null;
        KeyStore keyStore = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyguardManager =
                    (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
            fingerprintManager =
                    (FingerprintManager) context.getSystemService(FINGERPRINT_SERVICE);
            if (!keyguardManager.isKeyguardSecure()) {
            } else {
                try {
                    try {

                        keyStore = KeyStore.getInstance("AndroidKeyStore");

                        keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

                        keyStore.load(null);

                        keyGenerator.init(new

                                KeyGenParameterSpec.Builder(KEY_NAME,
                                KeyProperties.PURPOSE_ENCRYPT |
                                        KeyProperties.PURPOSE_DECRYPT)
                                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                                .setUserAuthenticationRequired(true)
                                .setEncryptionPaddings(
                                        KeyProperties.ENCRYPTION_PADDING_PKCS7)
                                .build());

                        //Generate the key//
                        keyGenerator.generateKey();

                    } catch (KeyStoreException
                            | NoSuchAlgorithmException
                            | NoSuchProviderException
                            | InvalidAlgorithmParameterException
                            | CertificateException
                            | IOException exc) {
                        exc.printStackTrace();
                        throw new FingerprintException(exc);
                    }
                } catch (FingerprintException e) {
                    e.printStackTrace();
                }
                try {
                    //Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
                    cipher = Cipher.getInstance(
                            KeyProperties.KEY_ALGORITHM_AES + "/"
                                    + KeyProperties.BLOCK_MODE_CBC + "/"
                                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
                } catch (NoSuchAlgorithmException |
                        NoSuchPaddingException e) {
                    throw new RuntimeException("Failed to get Cipher", e);
                }

                try {
                    keyStore.load(null);
                    SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                            null);
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    //Return true if the cipher has been initialized successfully//
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler helper = new FingerprintHandler(fingerprintListen, context);
                    helper.startAuth(fingerprintManager, cryptoObject);
                } catch (KeyPermanentlyInvalidatedException e) {


                } catch (KeyStoreException | CertificateException
                        | UnrecoverableKeyException | IOException
                        | NoSuchAlgorithmException | InvalidKeyException e) {
                    throw new RuntimeException("Failed to init Cipher", e);
                }

            }
        }
    }

    private static final String KEY_NAME = "HKey";

    private static class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }


    //Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", fileS);

}
