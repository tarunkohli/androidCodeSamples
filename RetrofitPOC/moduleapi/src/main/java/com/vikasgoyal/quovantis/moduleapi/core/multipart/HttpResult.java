package com.vikasgoyal.quovantis.moduleapi.core.multipart;


/**
 * This class represents the general http call response.
 */
public class HttpResult {

    private String httpString;
    private int statusCode;

    public String getHttpString() {
        return httpString;
    }

    public void setHttpString(String httpString) {
        this.httpString = httpString;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}