package com.vikasgoyal.quovantis.moduleapi.core;

/**
 * Generic interface to provide response for api
 *
 * @param <RESULT> Result type required in response
 */
public interface ApiResponseListener<RESULT> {
    /**
     * On Api response callback method will be called
     * if their is any error from api than exception will be be fired
     * else result will be pass in response object
     *
     * @param pApiException       if execution of api is failed then proper exception wil be created for it.
     * @param pResponse Result will be pass in if api is success.
     */
    void onResponse(ApiException pApiException, RESULT pResponse);
}
