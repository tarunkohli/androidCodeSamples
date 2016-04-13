package com.vikasgoyal.quovantis.moduleapi.core;

public class ApiConstants {
    public static final String OK = "OK";
    public static final String NOK = "NOK";
    public static final int DELETED = 204;
    public static final int PATCH_SUCCESS = 205;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int RESOURCE_NOT_FOUND = 404;
    public static final int CONFLICT = 409;
    public static final int SERVER_ERROR = 500;
    public static final String JSON_PARSING_EXCEPTION = "Please try again";
    public static final int CREATED = 201;
    public static final int SUCCESS = 200;

    public interface Headers {
        String AUTHORIZATION = "Authorization";
    }

    public interface QueryParam {
        String USER_ID = "userid";
    }
}
