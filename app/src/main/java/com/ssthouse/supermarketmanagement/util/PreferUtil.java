package com.ssthouse.supermarketmanagement.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ssthouse on 16/9/2.
 */
public class PreferUtil {

    private static PreferUtil mInstance;
    private Application mContext;

    private static final String PREFER_FILE_NAME = "preference";
    private static final String KEY_STAFF_NAME = "staffName";

    public static PreferUtil getInstance(Activity context) {
        if (mInstance == null)
            mInstance = new PreferUtil(context);
        return mInstance;
    }

    private PreferUtil(Activity context) {
        this.mContext = context.getApplication();
    }


    public void setStaffName(String staffName) {
        setString(KEY_STAFF_NAME, staffName);
    }

    public String getStaffName(){
        return getString(KEY_STAFF_NAME, "Aaron Alkins");
    }

    /************************
     * base function
     ********************************/
    public boolean getBoolean(String keyStr, boolean defaultValue) {
        return mContext.getSharedPreferences(PREFER_FILE_NAME, Context.MODE_PRIVATE)
                .getBoolean(keyStr, defaultValue);
    }

    public void setBoolean(String keyStr, boolean value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(PREFER_FILE_NAME, Context.MODE_PRIVATE)
                .edit();
        editor.putBoolean(keyStr, value)
                .apply();
    }

    public String getString(String keyStr, String defaultValue) {
        return mContext.getSharedPreferences(PREFER_FILE_NAME, Context.MODE_PRIVATE)
                .getString(keyStr, defaultValue);
    }

    public void setString(String keyStr, String value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(PREFER_FILE_NAME, Context.MODE_PRIVATE)
                .edit();
        editor.putString(keyStr, value)
                .apply();
    }

}
