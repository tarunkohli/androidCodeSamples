package com.zoomvy.contact.core.logs;


import android.util.Log;


/**
 * Logger class create logs for app
 * it will print the logs on console with
 */
public class Logger {
    private final String mClassName;
    private final boolean mLoggable;
    private static boolean sIsDebug;


    public Logger(String mClassName) {
        this.mClassName = mClassName;
        boolean androidLog;
        try {
            Class.forName("android.util.Log");
            androidLog = true;
        } catch (ClassNotFoundException e) {
            // android logger not available, probably a test environment.
            androidLog = false;
        }

        this.mLoggable = sIsDebug && androidLog;
    }

    /**
     * To print the formatted debug message on console
     * @param format String format
     * @param arguments Arguments
     */
    public void debug(String format, Object... arguments) {
        if (mLoggable) {
            try {
                Log.d(mClassName, String.format(format, arguments));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * To print the formatted error on console
     * @param format String format
     * @param arguments Arguments
     */
    public void error(String format, Object... arguments) {
        if (mLoggable) {
            try {
                Log.e(mClassName, String.format(format, arguments));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * To print the formatted info on console
     * @param format String format
     * @param arguments Arguments
     */
    public void info(String format, Object... arguments) {
        if (mLoggable) {
            try {
                Log.i(mClassName, String.format(format, arguments));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * To print the formatted Verbose on console
     * @param format String format
     * @param arguments Arguments
     */
    public void verbose(String format, Object... arguments) {
        if (mLoggable) {
            try {
                Log.v(mClassName, String.format(format, arguments));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * To print the formatted warning message on console
     * @param format String format
     * @param arguments Arguments
     */
    public void warn(String format, Object... arguments) {
        if (mLoggable) {
            try {
                Log.w(mClassName, String.format(format, arguments));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setIsDebug(boolean isDebug) {
        sIsDebug = isDebug;
    }

    public static boolean isDebug() {
        return sIsDebug;
    }
}
