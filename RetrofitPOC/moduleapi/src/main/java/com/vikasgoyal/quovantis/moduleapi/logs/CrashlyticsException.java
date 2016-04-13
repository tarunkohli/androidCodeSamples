package com.vikasgoyal.quovantis.moduleapi.logs;

public class CrashlyticsException extends Exception {
    private final String mTag;

    public CrashlyticsException(String pTag, String pMessage) {
        super(pMessage);
        mTag = pTag;
    }

    public String getTag() {
        return mTag;
    }
}
