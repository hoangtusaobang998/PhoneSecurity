package com.devpro.phonesecurity.listen;

public interface FingerprintListen {
    void onFailed();

    void onSussce();

    void onFailedMuch();

    void onAuthenticationHelp();
}
