package com.vikasgoyal.quovantis.moduleapi.core;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Custom exception for the standard error responses from server.
 * As documented in the API, The error response structure is same for all errors,
 * and contains an error_code which can be mapped to a specific error
 */
public class ApiRuntimeException extends RuntimeException {

    @SerializedName("error_code")
    private int mErrorCode;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("details")
    private List<Object> mDetails;


    /**
     * Creates a new Api Exception.
     *
     * @param errorCode A constant string code for the specific error.
     * @param message   A string message describing the error as a whole.
     */
    public ApiRuntimeException(int errorCode, String message) {
        mErrorCode = errorCode;
        mMessage = message;
    }

    /**
     * A constant string code for the specific error.
     *
     * @return the error code . Example {@code API_KEY_REQUIRED}
     */
    public int getErrorCode() {
        return mErrorCode;
    }

    /**
     * A string message describing the error as a whole.
     *
     * @return the string message. Example {@code Access Denied}
     */
    @Override
    public String getMessage() {
        return mMessage;
    }

    public List<Object> getDetails() {
        return mDetails;
    }
}
