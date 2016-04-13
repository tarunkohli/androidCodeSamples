package com.vikasgoyal.quovantis.moduleapi.core;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.retry.RetryPolicy;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * This class is a manages the creation of Api services and sessions.
 * Before using the ApiManger you need to define {@link ApiService}
 * in AndroidManifest
 */
public class ApiManager {

    public static final RetryPolicy NO_RETRY = new NoRetry();

    public static final Gson GSON_MAPPER = new Gson();
    private final ApiExecutor mApiExecutor;
    public static final int REQUEST_TIMEOUT = 60;
    // holds a reference to the singleton reference.
    private static ApiManager sInstance;
    private final ApiCache mCache;
    private String mBaseUrl;


    // singleton instance
    private ApiManager(String pBaseUrl) {
        mBaseUrl = pBaseUrl;
        mCache = new ApiCache();
        mApiExecutor = new ApiExecutor();
    }

    /**
     * Must be initialized in the application class
     * as all api required to use its instance
     *
     * @param pBaseUrl base url for api call
     */
    public static void init(String pBaseUrl) {
        if (sInstance == null) {
            sInstance = new ApiManager(pBaseUrl);
        }
    }

    /**
     * Get the singleton reference of the Api Manager
     *
     * @return the shared instance
     */
    public static ApiManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("ApiManager#init() must be called before ");
        }
        return sInstance;
    }

    /**
     * Creates the service implementation of the required type.
     *
     * @param <S>                    The type
     * @param clazz                  instance type
     * @param pIsAccessTokenRequired access token is required for api calling
     * @param baseUrl                if want to change api default baseUrl then pass it from here else pass null
     * @param canCache               if api can be cached then passed true from here else false
     * @return the required API service object reference
     */
    public <S> S getService(Class<S> clazz, boolean pIsAccessTokenRequired, String baseUrl, boolean canCache) {
        return createRetrofit(baseUrl, createOkHttpClient(pIsAccessTokenRequired, canCache), null).create(clazz);
    }

    /**
     * Creates the service implementation of the required type.
     *
     * @param pServiceBuilder Service Builder to be used when required to pass multiple
     *                        parameter in creating Api Service
     * @param <S>             Type of service
     * @return the required Service object reference
     */
    <S> S getService(ServiceBuilder<S> pServiceBuilder) {
        if (null == pServiceBuilder) {
            throw new IllegalArgumentException("Builder reference can't be null");
        }

        return createRetrofit(pServiceBuilder.mBaseUrl
                , createOkHttpClient(pServiceBuilder.mIsAccessTokenRequired, pServiceBuilder.mCanCache)
                , pServiceBuilder.mDeserializer)
                .create(pServiceBuilder.mClazz);
    }

    /**
     * Create callback handler as generic type of error should be handle at
     * one place so we passing response of api in converted ApiResponseListener
     *
     * @param mCallback ApiResponseListener of Result type response required
     * @param <RESULT>  Result object type
     * @return Callback<RESULT> type
     */
    public <RESULT> Callback<RESULT> addInCallbackHandler(ApiResponseListener<RESULT> mCallback) {
        return new ApiCallback<>(mCallback);
    }

    /**
     * Execute api call here and result will be pass in ApiResponseListener
     *
     * @param <RESULT>  Response result type
     * @param pTag
     * @param call      Call
     * @param pCallback ApiResponseListener
     * @return Future responsible for executing api request
     */
    public <RESULT> Future executeCall(String pTag, Call<RESULT> call, ApiResponseListener<RESULT> pCallback) {
        return mApiExecutor.execute(pTag, pCallback, call);
    }

    /**
     * Creates a new spice manager.
     *
     * @return a new instance of spice manager.
     */
    public SpiceManager createSpiceManager() {
        return new SpiceManager(ApiService.class);
    }

    /**
     * Creates and return the Rest adapter implementation
     *
     * @param baseUrl     the api baseUrl
     * @param pClient     Provide OkHttp client here
     * @param pGsonMapper Provide custom Gson mapper from here
     * @return the rest adapter instance for service creation
     */
    private Retrofit createRetrofit(String baseUrl, OkHttpClient pClient, Gson pGsonMapper) {
        return new Retrofit.Builder()
                .baseUrl(TextUtils.isEmpty(baseUrl) ? mBaseUrl : baseUrl)
                .addConverterFactory(GsonConverterFactory
                        .create(null != pGsonMapper ? pGsonMapper : GSON_MAPPER))
                .client(pClient)
                .build();
    }

    /**
     * Create OkHttp Client in case of providing custom headers in request
     *
     * @param pIsAccessTokenRequired if access token is required then need to pass it from here
     * @param canCache               If Api can be cached and on request for same api again it will get response from
     *                               cached data
     * @return OkHttpClient with required headers
     */
    private OkHttpClient createOkHttpClient(boolean pIsAccessTokenRequired, boolean canCache) {
        return new OkHttpClient.Builder()
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .cache(canCache ? mCache.getCache() : null)
                .addInterceptor(new ApiRequestInterceptor(pIsAccessTokenRequired))
                .build();
    }

    /**
     * To execute a call synchronous then call this method
     * Pass Call to execute call and response listener to listen result
     *
     * @param pCall     Call reference
     * @param pListener Callback listener
     * @param <T>       Response result type
     */
    public <T> void executeSyncCall(Call<T> pCall, ApiResponseListener<T> pListener) {
        try {
            Response<T> response = pCall.execute();
            if (null != pListener) {
                if (response.isSuccess()) {
                    pListener.onResponse(null, ApiUtils.parseApiResult(response));
                } else {
                    pListener.onResponse(ApiUtils.parseApiError(response), null);
                }
            }
        } catch (IOException pE) {
            if (null != pListener) {
                if (pE instanceof ApiException) {
                    pListener.onResponse((ApiException) pE, null);
                } else {
                    pListener.onResponse(ApiUtils.parseApiException(pE), null);
                }
            }
        }
    }

    /**
     * Clear cache when need to download for ne files
     */
    public void clearCache() {
        mCache.clearCache();
    }

    /**
     * No Retry policy
     */
    private static class NoRetry implements RetryPolicy {

        @Override
        public int getRetryCount() {
            return 0;
        }

        @Override
        public void retry(SpiceException e) {
        }

        @Override
        public long getDelayBeforeRetry() {
            return 0;
        }
    }

}
