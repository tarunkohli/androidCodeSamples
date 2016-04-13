package com.vikasgoyal.quovantis.moduleapi.logs;


import android.util.Log;

import com.vikasgoyal.quovantis.moduleapi.BuildConfig;

/**
 * ApiLoggerImp will provide ILogger implementation on crashlytics server
 */
public class ApiLoggerImp implements ILogger {
    private boolean mLogEnabled;

    public ApiLoggerImp() {
        mLogEnabled = BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug");
    }

    /**
     * Log simple message on crashlytics server
     *
     * @param msg logger message
     */
    @Override
    public void log(String msg) {
        if (mLogEnabled) {
            Log.i("Api Logs", msg);
        }
    }

    /**
     * Log message with priority and tag
     *  @param tag      Tag for the log
     * @param msg      Message to print
     */
    @Override
    public void log(String tag, String msg) {
        if (mLogEnabled) {
            Log.i("Api Logs" + tag, msg);
        }
    }
}
