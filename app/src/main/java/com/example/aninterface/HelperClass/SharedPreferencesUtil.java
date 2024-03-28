package com.example.aninterface.HelperClass;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static final String PREF_NAME = "AppPreferences";
    private static final String PHONE_NUMBER_KEY = "PhoneNumber";

    public static void savePhoneNumber(Context context, String phoneNumber) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE_NUMBER_KEY, phoneNumber);
        editor.apply();
    }

    public static String getPhoneNumber(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PHONE_NUMBER_KEY, "No Number Found");
    }
}

