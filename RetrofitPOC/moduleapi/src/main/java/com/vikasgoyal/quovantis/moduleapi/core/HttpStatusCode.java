package com.vikasgoyal.quovantis.moduleapi.core;

/**
 * This class holds the Http status codes which could possibly be thrown by the server apis.
 */
public interface HttpStatusCode {
    /**
     * A Request is timeout due to network failure or server not responding
     */
    int TIMEOUT_EXCEPTION = 300;
    /**
     * When input data does not match as expected by Service
     */
    int BAD_REQUEST = 400;
    /**
     * When access to a secure resource is denied, due to failure to establish authorisation
     */
    int UN_AUTHORIZED = 401;
    /**
     * When the operation intended by request is not permitted due to one or more condition in force
     */
    int FORBIDDEN = 403;
    /**
     * When the requested resource is not available on API or not found in Database etc
     */
    int NOT_FOUND = 404;
    /**
     * A failure in service's request processing logic
     */
    int INTERNAL_SERVER_ERROR = 500;

    /**
     * A failure in service's request processing logic
     */
    int INTERNAL_API_ERROR = 501;


}
