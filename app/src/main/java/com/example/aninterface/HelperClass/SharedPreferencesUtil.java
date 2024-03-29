package com.example.aninterface.HelperClass;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static final String PREF_NAME = "AppPreferences";
    private static final String PHONE_NUMBER_KEY = "PhoneNumber";

    private static final String USER_DETAILS = "UserDetails";
    private static final String USER_NAME = "UserName";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "Password";


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


    public static void saveUserName(Context context, String UserName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, UserName);
        editor.apply();
    }

    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, "No UserName Found");
    }

    public static void saveEmail(Context context, String Email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, Email);
        editor.apply();
    }

    public static String getEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL, "No Email Found");
    }


    public static void savePassword(Context context, String Password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, Password);
        editor.apply();
    }

    public static String getPassword(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSWORD, "No Password Found");
    }

}

