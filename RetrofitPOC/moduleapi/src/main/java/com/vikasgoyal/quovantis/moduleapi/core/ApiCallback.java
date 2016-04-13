package com.vikasgoyal.quovantis.moduleapi.core;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Api response callback
 * On failure of api an error will be pass in response else the result is passed
 *
 * @param <RESULT> Type of result required in response
 */
public class ApiCallback<RESULT> implements Callback<RESULT> {
    /**
     * Create callback handler as generic type of error should be handle at
     * one place so we passing response of api in converted ApiResponseListener
     *
     * @param mCallback ApiResponseListener of Result type response required
     * @param <RESULT>  Result object type
     * @return Callback<RESULT> type
     */
    private final WeakReference<ApiResponseListener<RESULT>> mCallbackRef;

    public ApiCallback(ApiResponseListener<RESULT> pCallback) {
        mCallbackRef = new WeakReference<>(pCallback);
    }


    @Override
    public void onResponse(Call<RESULT> call, Response<RESULT> response) {
        ApiResponseListener<RESULT> callback = mCallbackRef.get();
        if (null != callback) {
            if (response.isSuccess()) {
                callback.onResponse(null, ApiUtils.parseApiResult(response));
            } else {
                callback.onResponse(ApiUtils.parseApiError(response), null);
            }
        }
    }

    @Override
    public void onFailure(Call<RESULT> call, Throwable t) {
        ApiResponseListener<RESULT> callback = mCallbackRef.get();
        if (null != callback) {
            if (t instanceof ApiException) {
                callback.onResponse((ApiException) t, null);
            } else {
                callback.onResponse(ApiUtils.parseApiException(t), null);
            }
        }
    }
}