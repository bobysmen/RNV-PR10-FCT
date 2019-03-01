package com.example.rnv_pr10_fct.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationsUtils {

    private ValidationsUtils(){

    }

    public static boolean isValidCif(String cif){
        return !TextUtils.isEmpty(cif) && !(cif.length() <= 0 || cif.length() >= 10);
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhone(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
    }

    public static boolean isValidUrl(String url) {
        return !TextUtils.isEmpty(url) && Patterns.WEB_URL.matcher(url).matches();
    }
}
