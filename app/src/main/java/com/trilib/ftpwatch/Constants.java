package com.trilib.ftpwatch;

import androidx.appcompat.app.AppCompatDelegate;

import com.trilib.ftpwatch.utils.StorageUtil;


public class Constants {
    public static class IntentDataKey {
        public static final String OPTION_TYPE = "option_type";
    }
    public static class FTPConsts{
        public static final String NAME_ANONYMOUS="anonymous";
    }
    public static class PreferenceConsts{
        public static final String FILE_NAME ="settings";
        /**
         * this stands for a boolean value
         */
        public static final String ANONYMOUS_MODE="anonymous_mode";
        public static final boolean ANONYMOUS_MODE_DEFAULT=true;
        /**
         * this stands for a string value
         */
        public static final String ANONYMOUS_MODE_PATH="anonymous_mode_path";
        public static final String ANONYMOUS_MODE_PATH_DEFAULT= StorageUtil.getMainStoragePath();
        /**
         * this stands for a boolean value
         */
        public static final String ANONYMOUS_MODE_WRITABLE="anonymous_mode_writable";
        public static final boolean ANONYMOUS_MODE_WRITABLE_DEFAULT=true;

        /**
         * this stands for a boolean value
         */
        public static final String WAKE_LOCK="wake_lock";
        public static final boolean WAKE_LOCK_DEFAULT=false;
        /**
         * this stands for a int value
         */
        public static final String PORT_NUMBER="port_number";
        public static final int PORT_NUMBER_DEFAULT=5656;
        /**
         * this stands for a string value
         */
        public static final String CHARSET_TYPE ="charset_type";
        public static final String CHARSET_TYPE_DEFAULT = Charset.CHAR_UTF;
        /**
         * int value
         */
        public static final String LANGUAGE_SETTING="language_setting";
        public static final int LANGUAGE_FOLLOW_SYSTEM=0;
        public static final int LANGUAGE_SIMPLIFIED_CHINESE=1;
        public static final int LANGUAGE_ENGLISH=2;
        public static final int LANGUAGE_SETTING_DEFAULT=LANGUAGE_FOLLOW_SYSTEM;
        /**
         * int value
         */
        public static final String AUTO_STOP="auto_stop";
        public static final int AUTO_STOP_NONE=-1;
        public static final int AUTO_STOP_WIFI_DISCONNECTED=0;
        public static final int AUTO_STOP_AP_DISCONNECTED=1;
        public static final int AUTO_STOP_TIME_COUNT=2;
        public static final int AUTO_STOP_DEFAULT=AUTO_STOP_WIFI_DISCONNECTED;
        /**
         * int value
         */
        public static final String AUTO_STOP_VALUE="auto_stop_value";
        public static final int AUTO_STOP_VALUE_DEFAULT=600;

        public static final String SHOW_HIDDEN_FILES="show_hidden_files";
        public static final boolean SHOW_HIDDEN_FILES_DEFAULT=false;
    }

    public static class Charset{
        public static final String CHAR_UTF="UTF-8";
        public static final String CHAR_GBK="GBK";
        public static final String[] STRING_ARRAY = new String[] {CHAR_UTF, CHAR_GBK};

    }

    public static class RequestCode{
        public static final int CHARSET = 1;
        public static final int PORT = 2;
    }

}
