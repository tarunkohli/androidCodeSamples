package com.vikasgoyal.quovantis.moduleapi.logs;

import android.util.Log;


/***
 * @author Rahul Rastogi
 */
public class Logger {

    private static final String LOG_PREFIX = "iFittr:";
    private static boolean mLogEnabled = false;

    private Logger() {
        //do nothing
    }

    public static void setLogEnabled(boolean logEnabled) {
        mLogEnabled = logEnabled;
    }

    public static boolean isLogEnabled() {
        return mLogEnabled;
    }

    public static void d(Object o, String msg) {
        if (mLogEnabled) {
            Log.d(o.getClass().toString(), LOG_PREFIX + msg);
        }
    }

    public static void e(Object o, String msg) {
        if (mLogEnabled) {
            Log.e(o.getClass().toString(), LOG_PREFIX + msg);
        }
    }

    public static void w(Object o, String msg) {
        if (mLogEnabled) {
            Log.w(o.getClass().toString(), LOG_PREFIX + msg);
        }
    }

    public static void i(Object o, String msg) {
        if (mLogEnabled) {
            Log.i(o.getClass().toString(), LOG_PREFIX + msg);
        }
    }

    public static void i(Class pTClass, String msg) {
        if (mLogEnabled) {
            Log.i(pTClass.getName(), LOG_PREFIX + msg);
        }
    }

    public static void v(Object o, String msg) {
        if (mLogEnabled) {
            Log.v(o.getClass().toString(), LOG_PREFIX + msg);
        }
    }

}
