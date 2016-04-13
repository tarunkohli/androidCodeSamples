package com.vikasgoyal.quovantis.moduleapi.core.multipart;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.Pair;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vikasgoyal.quovantis.moduleapi.core.ApiConstants;
import com.vikasgoyal.quovantis.moduleapi.core.ApiException;
import com.vikasgoyal.quovantis.moduleapi.core.ApiResponseListener;
import com.vikasgoyal.quovantis.moduleapi.core.ApiUtils;
import com.vikasgoyal.quovantis.moduleapi.core.HttpStatusCode;
import com.vikasgoyal.quovantis.moduleapi.logs.Logger;

import org.apache.http.entity.mime.MultipartEntity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class MultipartClient<RESULT> {
    public enum Method {POST, GET}

    private Gson mGson;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private final MultiPartExecutor mExecutor;

    static {
        System.setProperty("http.keepAlive", "false");
    }

    /**
     * Constructor
     */
    public MultipartClient() {
        mGson = new GsonBuilder().serializeNulls().create();
        mExecutor = new MultiPartExecutor();
    }

    /**
     * Method to make Multipart call for the provided Method
     *
     * @param method    Method type{@link Method}
     * @param pRequest  MultipartRequest
     * @param pCallback ApiResponseListener
     */
    public void makeMultipartCall(final Method method, final MultipartRequest pRequest, final ApiResponseListener<RESULT> pCallback) {
        mExecutor.execute(pRequest.getUrl(), new Runnable() {
            @Override
            public void run() {
                switch (method) {
                    case POST:
                        try {
                            RESULT result = callByPost(pRequest);
                            postResult(pCallback, null, result);
                        } catch (ApiException pE) {
                            Logger.d(this, "Http: POST: URL:" + pRequest.getUrl() + " REQUEST:" + pRequest
                                    + " RESPONSE:" + pE.getMessage() + "RESULT:" + " STATUS:" + pE.getErrorCode());
                            postResult(pCallback, pE, null);
                        }
                        break;
                    case GET:
                        break;
                }
            }
        });
    }

    private void postResult(final ApiResponseListener<RESULT> pCallback, final ApiException pFinalApiException, final RESULT pFinalData) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                pCallback.onResponse(pFinalApiException, pFinalData);
            }
        });
    }

    private RESULT callByPost(MultipartRequest pRequest) throws ApiException {

        HttpResult result = callPostMultipart(pRequest);
        int statusCode = result.getStatusCode();
        if ((ApiConstants.SUCCESS == statusCode || ApiConstants.CREATED == statusCode) &&
                !TextUtils.isEmpty(result.getHttpString())) {
            try {
                return (RESULT) mGson.fromJson(result.getHttpString(), pRequest.getClazz());
            } catch (Exception e) {
                throw new ApiException(HttpStatusCode.INTERNAL_API_ERROR, e.getMessage());
            }
        } else {
            String errorMsg = ApiUtils.parseApiErrorCode(statusCode, result.getHttpString());
            throw new ApiException(HttpStatusCode.INTERNAL_API_ERROR, TextUtils.isEmpty(errorMsg) ? "Api Error" : errorMsg);
        }
    }

    private HttpResult callPostMultipart(MultipartRequest pRequest) throws ApiException {
        HttpResult httpResult = new HttpResult();
        HttpURLConnection httpUrlConnection = null;
        try {
            URL url = new URL(pRequest.getUrl());
            httpUrlConnection = (HttpURLConnection) url.openConnection();

            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);

            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            Map<String, String> headers = pRequest.getHeaders();
            for (String key : headers.keySet()) {
                httpUrlConnection.setRequestProperty(key, headers.get(key));
            }
            Pair<String, MultipartEntity> entity = pRequest.getEntity();
            httpUrlConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + entity.first);

            DataOutputStream outputStream = new DataOutputStream(httpUrlConnection.getOutputStream());
            entity.second.writeTo(outputStream);

            outputStream.close();
            httpUrlConnection.connect();
            httpResult.setStatusCode(httpUrlConnection.getResponseCode());
            verifySessionExpired(httpUrlConnection.getResponseCode());
            if (httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String result = readStream(httpUrlConnection.getInputStream());
                httpResult.setHttpString(result);
            } else {
                String result = readStream(httpUrlConnection.getInputStream());
                httpResult.setHttpString(result);
            }

        } catch (IOException pE) {
            throw new ApiException(400, pE.getMessage());
        } catch (NullPointerException ex) {
            throw new ApiException(400, "Internal Api error");
        } finally {
            if (null != httpUrlConnection) {
                httpUrlConnection.disconnect();
            }
        }
        return httpResult;
    }

    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    private void verifySessionExpired(int pStatusCode) {
        if (pStatusCode != -1 && pStatusCode == HttpStatusCode.FORBIDDEN) {
            /*List<AuthFailedListener> uiListeners = EventManager.getInstance()
                    .getUiListeners(AuthFailedListener.class);
            if (uiListeners != null && !uiListeners.isEmpty()) {
                uiListeners.get(0).onAuthenticationFailed(new ApiException(HttpStatusCode.FORBIDDEN));
            }*/
            // Handle Authentication failure from here
        }
    }

}
