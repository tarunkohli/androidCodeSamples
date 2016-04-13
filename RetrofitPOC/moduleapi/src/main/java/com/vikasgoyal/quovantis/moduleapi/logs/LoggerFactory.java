package com.vikasgoyal.quovantis.moduleapi.logs;

public class LoggerFactory {

    private volatile static LoggerFactory sInstance;
    private ILogger mLogger;

    public LoggerFactory(ILogger pLogger) {
        mLogger = pLogger;
    }

    public static void init(ILogger mLogger) {
        if (null == sInstance) {
            sInstance = new LoggerFactory(mLogger);
        }
    }

    public static ILogger getLogger() {
        if (null == sInstance || sInstance.mLogger == null) {
            throw new IllegalStateException("Must Initialize LoggerFactory before using getLogger()");
        }
        return sInstance.mLogger;
    }

}
