package com.vikasgoyal.quovantis.moduleapi.core;

import android.text.TextUtils;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p/>
 * This class would intercept the outgoing requests and adds the required custom headers.
 * Moreover This class won't allow a request to execute if the access token is required and is expired.
 * <p/>
 * The class is used in {@link ApiManager} to set in created OkHttp client for api
 * if any api required the acces token then this valriable will have true else it will have false
 *
 * @author Vikas Goyal
 * @version 1.0 updated on 17th Feb. 2016
 * @see ApiRequestInterceptor#ApiRequestInterceptor(boolean)
 * @see ApiManager#createOkHttpClient(boolean, boolean)
 * @see ApiRequestInterceptor#intercept(Chain)
 */
public class ApiRequestInterceptor implements Interceptor {
    private String mDummyToken = "96082-5H6RN-BCWN4-JCTT0-PYIML-5HYU3";
    // Headers to be set in the outgoing requests
    public static final String HEADER_ACCEPT = "Accept";
    public static final String AUTHORIZATION = "Authorization";

    // The corresponding values to the headers.
    public static final String APPLICATION_JSON = "application/json";

    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String CACHE_AGE = "public, max-age=";


    // flag indicating whether the access token is required in the outgoing requests
    private boolean mIsAccessTokenRequired;

    /**
     * Creates a new request interceptor.
     *
     * @param pIsAccessTokenRequired flag to indicate if access token is required for this request
     */
    public ApiRequestInterceptor(boolean pIsAccessTokenRequired) {
        mIsAccessTokenRequired = pIsAccessTokenRequired;
    }

    /**
     * Intercepts the outgoing requests and allows to add the required custom headers.
     * {@inheritDoc}
     */
    @Override
    public Response intercept(Chain chain) throws IOException {

        // Get the api request object
        Request apiRequest = chain.request();

        // Grab a reference to the new request builder in order to add custom headers
        Request.Builder builder = apiRequest.newBuilder();

        // Checks if the header doesn't already contains the Content-Type Header
        if (!TextUtils.isEmpty(apiRequest.header("Content-Type"))) {
            // populate in case no such header is present
            builder.addHeader("Content-Type", APPLICATION_JSON);
        }
        builder.addHeader(CACHE_CONTROL, CACHE_AGE + (24 * 60 * 60 * 60));//Set Cache for 24 hours long
        // Add the custom headers
        builder.addHeader(HEADER_ACCEPT, APPLICATION_JSON);

        // Checks if the access token is required in the request and it is also available in preferences
        String authToken = mDummyToken; // For realtime project user some utility manager which provide the access token here
        if (mIsAccessTokenRequired && !TextUtils.isEmpty(authToken)) {
            builder.addHeader(AUTHORIZATION, authToken);
        }

        Request request = builder.build();
        long t1 = System.nanoTime();
        //Print Api request body on logs
        ApiUtils.printRequestBody(String.valueOf(apiRequest.url()), request);

        // now the request is ready to proceed towards server :)
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        //Print Api Response body on console and return the resulted response
        response = ApiUtils.handleApiResponse(response
                , (t2 - t1)/* print the api execution time on console */);

        return response;
    }

}
