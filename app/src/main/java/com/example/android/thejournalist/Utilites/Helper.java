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
}
