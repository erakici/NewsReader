package com.intelygenz.esrarakici.newsreader.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by esrarakici on 28/04/2017.
 */

public class PreferencesHelper {
    private static final String PREF_KEY_DB_VERSION= "PREF_KEY_DB_VERSION";
    private static final String PREF_KEY_DATA_URL= "PREF_KEY_DATA_URL";
    private final SharedPreferences mPrefs;
    private static PreferencesHelper mInstance;

    public static PreferencesHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PreferencesHelper(context);
        }
        return mInstance;
    }
    private PreferencesHelper(Context context) {
        mPrefs = context.getSharedPreferences(AppContstants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }


    public int getDbVersion() {
        return mPrefs.getInt(PREF_KEY_DB_VERSION, 1);
    }


    public int increaseDbVersion() {
        int version = getDbVersion();
        mPrefs.edit().putInt(PREF_KEY_DB_VERSION, ++version).apply();
        return version;
    }

    public String getDataUrl() {
        return mPrefs.getString(PREF_KEY_DATA_URL, AppContstants.DATA_URL);
    }
    public void setDataUrl(String val){
        mPrefs.edit().putString(PREF_KEY_DATA_URL, val).apply();
    }


}
