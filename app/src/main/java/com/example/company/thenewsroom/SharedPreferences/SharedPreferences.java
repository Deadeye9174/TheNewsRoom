package com.example.company.thenewsroom.SharedPreferences;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by jatin on 8/11/17.
 */

public class SharedPreferences {

    static String phone;
    static String password;

    /*static SharedPreferences getSharedPreferences(Context context)
    {
        //return PreferenceManager.getDefaultSharedPreferences(context);
    }*/

    public static String getPhone() {
        return phone;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPhone(String phone) {
        SharedPreferences.phone = phone;
    }

    public static void setPassword(String password) {
        SharedPreferences.password = password;
    }
}
