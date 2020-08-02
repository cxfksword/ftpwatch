package com.trilib.ftpwatch.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.trilib.ftpwatch.Constants;

public class SettingManager {
    private SharedPreferences setting;

    public  SettingManager(SharedPreferences setting) {
        this.setting = setting;
    }

    public static SettingManager build(ContextWrapper context) {
        return new SettingManager(context.getSharedPreferences(Constants.PreferenceConsts.FILE_NAME, Context.MODE_PRIVATE));
    }

    public static SettingManager build(Context context) {
        return new SettingManager(context.getSharedPreferences(Constants.PreferenceConsts.FILE_NAME, Context.MODE_PRIVATE));
    }

    public String getString(String key, String defaultVal) {
        String val = defaultVal;
        try {
            val = this.setting.getString(key, defaultVal);
        } catch (Exception e) {
        }

        return val;
    }

    public int getInt(String key, int defaultVal) {
        int val = defaultVal;
        try {
            val = this.setting.getInt(key, defaultVal);
        } catch (Exception e) {
        }

        return val;
    }

    public boolean getBoolean(String key, boolean defaultVal) {
        boolean val = defaultVal;
        try {
            val = this.setting.getBoolean(key, defaultVal);
        } catch (Exception e) {
        }

        return val;
    }

    public SharedPreferences.Editor edit() {
        return this.setting.edit();
    }

}
