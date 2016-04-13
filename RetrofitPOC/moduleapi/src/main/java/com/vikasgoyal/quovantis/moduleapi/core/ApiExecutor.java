package com.vikasgoyal.quovantis.moduleapi.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;


/**
 * A executor which execute the api call and provide response or error on result
 * On failure of api an error will be pass in response else the result is passed
 */
public class ApiExecutor {
    public static final int REQUEST_POOL_SIZE = 5;

    private HashMap<String, Future<?>> mRequestQueueMap = new HashMap<>();
    private ExecutorService mRequestExecutorService = Executors.newFixedThreadPool(REQUEST_POOL_SIZE);

    /**
     * Execute Api Service in background and receive result for that
     * * @param <RESULT> Type of result required in response
     */

    <RESULT> Future execute(String pTag, ApiResponseListener<RESULT> pCallback, Call<RESULT> pCallService) {
        Future<?> task = mRequestExecutorService.submit(new Execute<>(pTag, pCallback, pCallService));
        mRequestQueueMap.put(pTag, task);
        return task;
    }

    private class Execute<RESULT> implements Runnable {
        /**
         * Create callback handler as generic type of error should be handle at
         * one place so we passing response of api in converted ApiResponseListener
         *
         * @param mCallback ApiResponseListener of Result type response required
         * @param <RESULT>  Result object type
         * @return Callback<RESULT> type
         */
        private final ApiResponseListener<RESULT> mCallback;

        /**
         * Call which need to be execute from executor
         */
        private final Call<RESULT> mCallService;

        /**
         * Tag will used to find reference of  future so we can cancel it on later
         */
        private final String mTag;

        private Execute(String pTag, ApiResponseListener<RESULT> pCallback, Call<RESULT> pCallService) {
            mTag = pTag;
            mCallback = pCallback;
            mCallService = pCallService;
        }

        @Override
        public void run() {
            try {
                Response<RESULT> response = mCallService.execute();
                if (response.isSuccess()) {
                    if (null != mCallback) {
                        mCallback.onResponse(null, ApiUtils.parseApiResult(response));
                    }
                } else {
                    if (null != mCallback) {
                        mCallback.onResponse(ApiUtils.parseApiError(response), null);
                    }
                }
            } catch (RuntimeException e) {
                if (null != mCallback) {
                    if (e instanceof ApiRuntimeException) {
                        ApiRuntimeException aRE = (ApiRuntimeException) e;
                        ApiException apiException = new ApiException(aRE.getErrorCode()
                                , aRE.getMessage());
                        apiException.setDetails(aRE.getDetails());
                        mCallback.onResponse(apiException, null);
                    } else {
                        mCallback.onResponse(ApiUtils.parseApiException(e), null);
                    }
                }
            } catch (IOException e) {
                if (null != mCallback) {
                    if (e instanceof ApiException) {
                        mCallback.onResponse((ApiException) e, null);
                    } else {
                        mCallback.onResponse(ApiUtils.parseApiException(e), null);
                    }
                }
            } finally {
                if (mRequestQueueMap.containsKey(mTag)) {
                    mRequestQueueMap.remove(mTag);
                }
            }
        }
    }

    /**
     * To Cancel the execution of request use this method
     */
    public void cancel(String pTag) {
        if (mRequestQueueMap.containsKey(pTag)) {
            Future future = mRequestQueueMap.get(pTag);
            future.cancel(true);
            mRequestQueueMap.remove(pTag);
        }
    }

}
