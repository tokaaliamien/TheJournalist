package com.example.android.thejournalist.Utilites;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Toka on 2018-07-01.
 */

public class Helper {
    public static void displayToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public static void displayLog(String classTag, String message) {
        Log.e(classTag, message);
    }

    public static boolean compare(String str1, String str2) {
        return (str1 == null ? str2 == null : str1.equals(str2));
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty() ? true : false;
    }
}
